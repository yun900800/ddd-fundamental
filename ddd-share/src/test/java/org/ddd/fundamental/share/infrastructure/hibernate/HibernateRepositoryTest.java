package org.ddd.fundamental.share.infrastructure.hibernate;

import org.ddd.fundamental.share.domain.Service;
import org.ddd.fundamental.share.domain.creteria.Criteria;
import org.ddd.fundamental.share.domain.creteria.Filters;
import org.ddd.fundamental.share.domain.creteria.Order;
import org.ddd.fundamental.share.infrastructure.JavaUuidGenerator;
import org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq.RabbitMqDomainEventsConsumer;
import org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq.RabbitMqPublisher;
import org.ddd.fundamental.share.infrastructure.hibernate.persistent.CourseHibernateRepository;
import org.ddd.fundamental.share.infrastructure.persistence.hibernate.Courses;
import org.ddd.fundamental.share.infrastructure.persistence.hibernate.StringIdentifier;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest( includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class),
        excludeAutoConfiguration ={RabbitMqDomainEventsConsumer.class}
)
public class HibernateRepositoryTest {

    @Autowired
    private CourseHibernateRepository courseHibernateRepository;

    @Autowired
    private SessionFactory sessionFactory;

    @MockBean
    private RabbitMqDomainEventsConsumer rabbitMqDomainEventsConsumer;

    @MockBean
    RabbitMqPublisher rabbitMqPublisher;

    @Autowired
    private JavaUuidGenerator javaUuidGenerator;

    @Test
    public void testCourseHibernateRepositoryNotNull() {
        Assert.assertNotNull(courseHibernateRepository);
    }

    @Before
    public void setUp() {
        for (int i = 0 ; i < 20; i++) {
            String index = javaUuidGenerator.generate();
            StringIdentifier identifier = new StringIdentifier(javaUuidGenerator.generate());
            String duration = i + 100 +"";
            courseHibernateRepository.persist(new Courses(identifier,"yun"+index,duration));
        }
    }

    @Test
    public void testSaveCourses() {
        StringIdentifier identifier = new StringIdentifier(javaUuidGenerator.generate());
        courseHibernateRepository.persist(new Courses(identifier,"yun","100"));

        Optional<Courses> optionalCourses = courseHibernateRepository.byId(identifier);
        Assert.assertEquals(optionalCourses.get().getDuration(),"100");
    }

    @Test
    public void testQueryAll() {
        List<Courses> coursesList = courseHibernateRepository.all();
        Assert.assertEquals(20,coursesList.size());
    }

    @Test
    public void testQueryByCriteria() {
        HashMap<String,String> map = new HashMap<>();
        map.put("field","duration");
        map.put("operator","<");
        map.put("value","105");
        List<HashMap<String,String>> filterList = new ArrayList<>();
        filterList.add(map);
        Filters filters = Filters.fromValues(filterList);
        Order order = Order.none();
        Criteria criteria = new Criteria(filters,order);
        List<Courses> coursesList = courseHibernateRepository.byCriteria(criteria);
        Assert.assertEquals(5,coursesList.size());
    }
}
