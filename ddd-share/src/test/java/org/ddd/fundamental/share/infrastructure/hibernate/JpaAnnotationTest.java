package org.ddd.fundamental.share.infrastructure.hibernate;

import org.ddd.fundamental.share.infrastructure.JavaUuidGenerator;
import org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq.RabbitMqDomainEventsConsumer;
import org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq.RabbitMqPublisher;
import org.ddd.fundamental.share.infrastructure.hibernate.persistent.TestHibernateConfiguration;
import org.ddd.fundamental.share.infrastructure.persistence.hibernate.Courses;
import org.ddd.fundamental.share.infrastructure.persistence.hibernate.StringIdentifier;
import org.ddd.fundamental.share.infrastructure.utils.HibernateUtils;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Import(TestHibernateConfiguration.class)
public class JpaAnnotationTest {

    @MockBean
    private RabbitMqDomainEventsConsumer rabbitMqDomainEventsConsumer;

    @MockBean
    RabbitMqPublisher rabbitMqPublisher;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private JavaUuidGenerator javaUuidGenerator;

    @Test
    public void testSessionFactory() {
        Assert.assertNotNull(sessionFactory);
        HibernateUtils.doInHibernate((session)->{
            Assert.assertNotNull(session);
            StringIdentifier identifier = new StringIdentifier(javaUuidGenerator.generate());
            Courses courses = new Courses(identifier,"myCourses","150");
            session.persist(courses);
            session.flush();
            Courses courses1 = session.load(Courses.class,identifier);
            Assert.assertEquals("150",courses1.getDuration());
        },sessionFactory);
    }
}
