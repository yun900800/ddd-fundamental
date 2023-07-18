package org.ddd.fundamental.share.infrastructure.hibernate;

import org.ddd.fundamental.share.domain.creteria.Criteria;
import org.ddd.fundamental.share.domain.creteria.Filters;
import org.ddd.fundamental.share.domain.creteria.Order;
import org.ddd.fundamental.share.infrastructure.JavaUuidGenerator;
import org.ddd.fundamental.share.infrastructure.config.EnvironmentConfig;
import org.ddd.fundamental.share.infrastructure.persistence.hibernate.Courses;
import org.ddd.fundamental.share.infrastructure.persistence.hibernate.StringIdentifier;
import org.ddd.fundamental.share.infrastructure.utils.HibernateUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
public class HibernateCriteriaConverterTest {

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @MockBean
    private ResourcePatternResolver resourceResolver;

    @Autowired
    private ApplicationContext applicationContext;


    @TestConfiguration
    static class HibernateConfigurationFactoryImplTestContextConfiguration {
        @Bean
        public HibernateConfigurationFactory hibernateConfigurationFactory(ResourcePatternResolver resourceResolver) {
            return new HibernateConfigurationFactory(resourceResolver);
        }

        @Bean
        public JavaUuidGenerator javaUuidGenerator(){
            return new JavaUuidGenerator();
        }
    }

    @Autowired
    private JavaUuidGenerator javaUuidGenerator;

    @Autowired
    private HibernateConfigurationFactory hibernateConfigurationFactory;

    @Before
    public void setUp() {

    }

    @Test
    public void testApplicationContextNotNull() {
        Assert.assertNotNull(applicationContext);
    }

    @Test
    public void testCreateHibernateCriteriaConverter() {
        HibernateCriteriaConverter hibernateCriteriaConverter = new HibernateCriteriaConverter(
                criteriaBuilder
        );
        Assert.assertNotNull(hibernateCriteriaConverter);
    }

    @Test
    public void testHibernateCriteriaConverterConvert() throws IOException {
        DataSource dataSource = hibernateConfigurationFactory.dataSource(
                "localhost",3306,"domain","sa","");
        LocalSessionFactoryBean sessionFactory = hibernateConfigurationFactory.sessionFactory("fundamental",dataSource);
        sessionFactory.setPackagesToScan("org.ddd.fundamental");
        sessionFactory.afterPropertiesSet();
        HibernateCriteriaConverter hibernateCriteriaConverter = new HibernateCriteriaConverter(
                sessionFactory.getObject().getCriteriaBuilder()
        );
        CriteriaQuery<Courses> eqQuery = hibernateCriteriaConverter.convert(createEqCriteria(), Courses.class);
        CriteriaQuery<Courses> noEQuery = hibernateCriteriaConverter.convert(createNoEqCriteria(), Courses.class);
        CriteriaQuery<Courses> gtEQuery = hibernateCriteriaConverter.convert(createGreatThanCriteria(), Courses.class);
        CriteriaQuery<Courses> ltEQuery = hibernateCriteriaConverter.convert(createLessThanCriteria(), Courses.class);
        CriteriaQuery<Courses> containsEQuery = hibernateCriteriaConverter.convert(createContainsCriteria(), Courses.class);
        CriteriaQuery<Courses> notContainsEQuery = hibernateCriteriaConverter.convert(createNotContainsCriteria(), Courses.class);
        HibernateUtils.doInHibernate((session)->{
            Courses courses = new Courses();
            courses.setId(new StringIdentifier(javaUuidGenerator.generate()));
            courses.setName("yun900800");
            courses.setDuration("50");
            session.persist(courses);
            Courses courses1 = new Courses();
            courses1.setId(new StringIdentifier(javaUuidGenerator.generate()));
            courses1.setName("yun9008009");
            courses1.setDuration("60");
            session.persist(courses1);
            List<Courses> courseList = session.createQuery(eqQuery).getResultList();
            Assert.assertEquals(1,courseList.size());
            courseList = session.createQuery(noEQuery).getResultList();
            Assert.assertEquals(1,courseList.size());
            courseList = session.createQuery(gtEQuery).getResultList();
            Assert.assertEquals(2,courseList.size());
            courseList = session.createQuery(ltEQuery).getResultList();
            Assert.assertEquals(1,courseList.size());
            courseList = session.createQuery(containsEQuery).getResultList();
            Assert.assertEquals(1,courseList.size());
            courseList = session.createQuery(notContainsEQuery).getResultList();
            Assert.assertEquals(0,courseList.size());
        },sessionFactory.getObject());

    }

    private Criteria createNoEqCriteria() {
        HashMap<String,String> map = new HashMap<>();
        map.put("field","name");
        map.put("operator","!=");
        map.put("value","yun900800");
        List<HashMap<String,String>> filterList = new ArrayList<>();
        filterList.add(map);
        Filters filters = Filters.fromValues(filterList);
        Order order = Order.none();
        Criteria criteria = new Criteria(filters,order);
        return criteria;
    }

    private Criteria createGreatThanCriteria() {
        HashMap<String,String> map = new HashMap<>();
        map.put("field","duration");
        map.put("operator",">");
        map.put("value","40");
        List<HashMap<String,String>> filterList = new ArrayList<>();
        filterList.add(map);
        Filters filters = Filters.fromValues(filterList);
        Order order = Order.none();
        Criteria criteria = new Criteria(filters,order);
        return criteria;
    }

    private Criteria createLessThanCriteria() {
        HashMap<String,String> map = new HashMap<>();
        map.put("field","duration");
        map.put("operator","<");
        map.put("value","55");
        List<HashMap<String,String>> filterList = new ArrayList<>();
        filterList.add(map);
        Filters filters = Filters.fromValues(filterList);
        Order order = Order.none();
        Criteria criteria = new Criteria(filters,order);
        return criteria;
    }

    private Criteria createContainsCriteria() {
        HashMap<String,String> map = new HashMap<>();
        map.put("field","name");
        map.put("operator","CONTAINS");
        map.put("value","9008009");
        List<HashMap<String,String>> filterList = new ArrayList<>();
        filterList.add(map);
        Filters filters = Filters.fromValues(filterList);
        Order order = Order.none();
        Criteria criteria = new Criteria(filters,order);
        return criteria;
    }

    private Criteria createNotContainsCriteria() {
        HashMap<String,String> map = new HashMap<>();
        map.put("field","name");
        map.put("operator","NOT_CONTAINS");
        map.put("value","900");
        List<HashMap<String,String>> filterList = new ArrayList<>();
        filterList.add(map);
        Filters filters = Filters.fromValues(filterList);
        Order order = Order.none();
        Criteria criteria = new Criteria(filters,order);
        return criteria;
    }

    private Criteria createEqCriteria() {
        HashMap<String,String> map = new HashMap<>();
        map.put("field","name");
        map.put("operator","=");
        map.put("value","yun900800");
        HashMap<String,String> map1 = new HashMap<>();
        map1.put("field","duration");
        map1.put("operator","=");
        map1.put("value","50");

        List<HashMap<String,String>> filterList = new ArrayList<>();
        filterList.add(map);
        filterList.add(map1);
        Filters filters = Filters.fromValues(filterList);
        Order order = Order.none();
        Criteria criteria = new Criteria(filters,order);
        return criteria;
    }
}
