package org.ddd.fundamental.app.service.product;

import org.ddd.fundamental.app.api.client.OkHttpClientUtils;
import org.ddd.fundamental.app.model.Product;
import org.ddd.fundamental.app.repository.user.ProductRepository;
import org.ddd.fundamental.share.domain.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductService {

    private  static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private static final int REQUEST_QTY = 100000;

    private static final int THREAD_COUNT = 500;

    private static final Long INIT_VALUE = 500L;


    private AtomicInteger cacheSelectCount = new AtomicInteger();

    private AtomicInteger natchHitSelectCount = new AtomicInteger();

    private AtomicInteger dbSelectCount = new AtomicInteger();

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private RedisTemplate<String,Long> redisLongValueTemplate;

    private ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);

    private Long handleProductDecrement(String id) {
        Long productCount = redisLongValueTemplate.opsForValue().get(id);
        if (null != productCount) {
            if (productCount > 0) {
                //这里涉及到先改缓存还是先改数据库的问题,
                //如果先修改数据库,那么高并发的情况下,后续的线程更有可能打到数据库, 同时后续线程击中缓存的概率更高
                //如果先修改缓存,那么打到数据库的概率小,但是不够保险,
                //这段代码在高并发的场景下也会出现内存数据为负数的情况
                long ret = redisLongValueTemplate.opsForValue().increment(id,-1);
                if (ret < 10) {
                    Long count = redisLongValueTemplate.opsForValue().get(id);
                    LOGGER.info("当前还有库存:{}",count);
                }
                //这里的这个值表示还剩下多少库存,并且这个扣库存的操作是原子性的
                LOGGER.info("redis 扣库存返回值:{}",ret);
                if (ret >= 0) {
                    natchHitSelectCount.getAndIncrement();
                    //redisTemplate.opsForValue().set(id, value-1+"");
                    productRepository.decrement(id);
                } else  {
                    return 0L;
                }


            }
            return productCount;
        }
        return null;
    }

    /**
     * 没有缓存的话直接打到数据库20000请求大概15s
     * 有缓存的话下降到了9s,先修改缓存,然后修改数据库会降低超卖的概率
     * @param id
     * @return
     */
    @Transactional(transactionManager = "transactionManager")
    public int snatchProduct(String id) {
        Long count = handleProductDecrement(id);
        if (null!= count && count>0) {
            //记录缓存集中的信息
            return 1;
        } else if (null != count && count <=0) {
            LOGGER.info("已经抢完啦");
            cacheSelectCount.getAndIncrement();
            return 0;
        }

        synchronized (this) {
            count = handleProductDecrement(id);
            if (null!= count && count>0) {
                return 1;
            }
            dbSelectCount.getAndIncrement();
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (!optionalProduct.isPresent()) {
                return 0;
            }
            Product product = optionalProduct.get();
            if (product.getCount() > 0) {
                //productRepository.decrement(id);
                productRepository.decrementByOptimistic(id);
                long updateCount  = product.getCount();
                //将数据库查询出来的数据保存到缓存
                //redisTemplate.opsForValue().set(id, updateCount+"");
                if (updateCount == INIT_VALUE) {
                    redisLongValueTemplate.opsForValue().set(id,updateCount-1);
                } else{
                    redisLongValueTemplate.opsForValue().increment(id,-1);
                }

                return 1;
            }
            return 0;
        }

    }

    @Transactional(transactionManager = "transactionManager")
    public void initProductCount() {
        String id = "77e50cf7-de9b-4a58-84b9-aee1a219734d";
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setCount(INIT_VALUE);
            productRepository.save(product);
            productRepository.flush();
        }
    }

    /**
     * 从缓存中获取产品数据
     * @param id
     * @return
     */
    public Long getProductFromCache(String id) {

        String strCount = redisTemplate.opsForValue().get(id);
        if (null != strCount) {
            Long value = Long.valueOf(strCount);
            if (value > 0) {
                redisTemplate.opsForValue().set(id, value-1+"");
            }
            return value;
        }
        return null;
    }

    @Transactional(transactionManager = "transactionManager")
    public int batchSnatchProduct() {
        //initProductCount();
        redisTemplate.delete("77e50cf7-de9b-4a58-84b9-aee1a219734d");
        CountDownLatch latch = new CountDownLatch(REQUEST_QTY);
        long startTime = System.currentTimeMillis();
        for (int i = 0 ;i <REQUEST_QTY; i++) {
            service.submit(()->{
                try {
                    String json = "{}";
                    String productId = "77e50cf7-de9b-4a58-84b9-aee1a219734d";
                    String url = "http://localhost:9000/snatchProduct/"+productId;
                    String ret = OkHttpClientUtils.post(url,json);
                    latch.countDown();
                } catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        long cost = System.currentTimeMillis() - startTime;
        LOGGER.info("请求处理时间:{}s",new BigDecimal(cost).divide(BigDecimal.valueOf(1000L),2,BigDecimal.ROUND_DOWN).toBigInteger());


        LOGGER.info("接口的吞吐量是:{}/s",new BigDecimal(REQUEST_QTY*1000).divide(BigDecimal.valueOf(cost),
                2,BigDecimal.ROUND_DOWN).toBigInteger());
        LOGGER.info("每个请求平均耗时是:{}",BigDecimal.valueOf(cost).divide(BigDecimal.valueOf(REQUEST_QTY),
                2,BigDecimal.ROUND_DOWN).toBigInteger());
        LOGGER.info("hit to cache count:{}",cacheSelectCount.get());
        LOGGER.info("hit to db count:{}",dbSelectCount.get());
        LOGGER.info("snatch hit to cache count:{}",natchHitSelectCount.get());
        cacheSelectCount.set(0);
        dbSelectCount.set(0);
        natchHitSelectCount.set(0);
        return 1;
    }
}
