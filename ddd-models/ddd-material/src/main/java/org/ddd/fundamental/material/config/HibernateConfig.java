package org.ddd.fundamental.material.config;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class HibernateConfig {

    //这里配置blaze框架的keyset分页
    //参考一下文章 https://vladmihalcea.com/keyset-pagination-jpa-hibernate/
    // https://vladmihalcea.com/keyset-pagination-spring/
    @Bean
    public CriteriaBuilderFactory criteriaBuilderFactory(EntityManagerFactory entityManagerFactory){
        CriteriaBuilderConfiguration config = Criteria.getDefault();
        CriteriaBuilderFactory cbf = config.createCriteriaBuilderFactory(entityManagerFactory);
        return cbf;
    }
}
