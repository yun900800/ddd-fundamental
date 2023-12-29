package org.ddd.fundamental.repository.fundamental;

import org.aspectj.weaver.ast.Or;
import org.ddd.fundamental.repository.core.exception.PersistenceException;
import org.ddd.fundamental.repository.core.repository.impl.RepositoryBase;
import org.ddd.fundamental.repository.fundamental.model.OrderItemModel;
import org.ddd.fundamental.repository.fundamental.model.OrderModel;
import org.ddd.fundamental.repository.order.Order;
import org.ddd.fundamental.repository.order.OrderItem;
import org.ddd.fundamental.repository.order.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("orderRepositoryImpl")
public class OrderRepositoryImpl extends RepositoryBase<Long, Order>
        implements OrderRepository {

    @Autowired
    private OrderFundamentalRepository orderFundamentalRepository;

    @Autowired
    private OrderItemFundamentalRepository orderItemFundamentalRepository;

    @Override
    public void persistNewCreated(Order order) throws PersistenceException {
        if (order == null) {
            throw new PersistenceException();
        }
        try {
            OrderModel orderModel = this.ofOrderModel(order);
            List<OrderItemModel> orderEntryDataEntities = this.ofOrderItemModel(order);
            orderModel = orderFundamentalRepository.save(orderModel);
            for (OrderItemModel orderItemModel: orderEntryDataEntities) {
                orderItemModel.setOrderId(orderModel.getId());
            }
            orderItemFundamentalRepository.saveAll(orderEntryDataEntities);
            order.setId(orderModel.getId());
        } catch (Exception e) {
            throw new PersistenceException();
        }
    }

    /**
     *
     * @param order
     * @return
     */
    private OrderModel ofOrderModel(Order order) {
        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(order, orderModel);
        orderModel.setOrderStatus(order.getOrderStatus().getStatus());
        return orderModel;
    }

    private List<OrderItemModel> ofOrderItemModel(Order order) {
        List<OrderItemModel> orderItemModels = order.getOrderItems().stream().map(v->{
            OrderItemModel orderItemModel = new OrderItemModel();
            orderItemModel.setOrderId(order.getId());
            orderItemModel.setId(v.getId());
            BeanUtils.copyProperties(v, orderItemModel);
            return orderItemModel;
        }).collect(Collectors.toList());
        return orderItemModels;
    }
    private List<OrderItemModel> ofOrderItemDirtyModel(Order order) {
        List<OrderItemModel> orderItemModels = order.getOrderItems().stream().filter(v->v.isDirty()).map(v->{
            OrderItemModel orderItemModel = new OrderItemModel();
            orderItemModel.setOrderId(order.getId());
            orderItemModel.setId(v.getId());
            BeanUtils.copyProperties(v, orderItemModel);
            return orderItemModel;
        }).collect(Collectors.toList());
        return orderItemModels;
    }

    @Override
    public void persistDeleted(Order order) throws PersistenceException {
        if (order == null) {
            throw new PersistenceException();
        }
        try {
//            OrderDataEntity orderDataEntity = this.ofOrderData(oder);
//            List<OrderEntryDataEntity> orderEntryDataEntities = this.ofOrderEntryData(oder);
//            this.orderMapper.delete(orderDataEntity);
//            this.orderEntryMapper.delete(orderEntryDataEntities);
        } catch (Exception e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void persistChanged(Order order) throws PersistenceException {
        if (order == null) {
            throw new PersistenceException();
        }
        if (order.isDirty()) {
            OrderModel orderModel = this.ofOrderModel(order);
            orderModel.setId(order.getId());
            orderFundamentalRepository.save(orderModel);
        }
        List<OrderItemModel> orderItemModels = this.ofOrderItemDirtyModel(order);
        orderItemFundamentalRepository.saveAll(orderItemModels);
    }

    @Override
    public Order findBy(Long id) throws RuntimeException {
        Optional<OrderModel> optionalOrderModel = orderFundamentalRepository.findById(id);
        if (!optionalOrderModel.isPresent()) {
            return null;
        }
        List<OrderItemModel> orderItemModels = findByOrderId(id);
        OrderModel orderModel = optionalOrderModel.get();
        Order order = Order.load(orderModel.getName(),
                orderModel.getDescription(), orderModel.getOrderStatus());
        List<OrderItem> orderItems = orderItemModels.stream().map(v->{
            OrderItem orderItem = OrderItem.create(v.getName(),v.getItemAmount(),v.getQuantity(),v.getDescription());
            orderItem.setId(v.getId());
            return orderItem;
        }).collect(Collectors.toList());
        order.addOrderItems(orderItems);
        order.setId(id);
        return order;
    }

    public List<OrderItemModel> findByOrderId(Long id){
        OrderItemModel queryItemModel = new OrderItemModel();
        queryItemModel.setQuantity(null);
        queryItemModel.setOrderId(id);
        Example<OrderItemModel> example = Example.of(queryItemModel);
        List<OrderItemModel> orderItemModels = orderItemFundamentalRepository.findAll(example);
        return orderItemModels;
    }

}
