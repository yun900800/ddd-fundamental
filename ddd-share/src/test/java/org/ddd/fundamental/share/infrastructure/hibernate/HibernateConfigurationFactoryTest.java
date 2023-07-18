package org.ddd.fundamental.share.infrastructure.hibernate;


import org.ddd.fundamental.share.infrastructure.JavaUuidGenerator;
import org.ddd.fundamental.share.infrastructure.persistence.hibernate.Courses;
import org.ddd.fundamental.share.infrastructure.persistence.hibernate.StringIdentifier;
import org.ddd.fundamental.share.infrastructure.utils.HibernateUtils;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class HibernateConfigurationFactoryTest {

    @MockBean
    private ResourcePatternResolver resourceResolver;

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
    private HibernateConfigurationFactory hibernateConfigurationFactory;

    @Autowired
    private JavaUuidGenerator javaUuidGenerator;

    @Test
    public void testHibernateConfigurationFactoryNotNull() {
        Assert.assertNotNull(hibernateConfigurationFactory);
    }

    @Test
    public void testDataSource() throws IOException {
        DataSource dataSource = hibernateConfigurationFactory.dataSource(
                "localhost",3106,"domain","innovation","pa" +
                        "");
        Assert.assertNotNull(dataSource);
    }

    @Test
    public void testExecuteSQL() throws IOException, SQLException {
        DataSource dataSource = hibernateConfigurationFactory.dataSource(
                "localhost",3306,"devnote","root","rootpassword");
        Connection connection = dataSource.getConnection();
        executeStatement(connection,"insert into courses(id,name,duration) values('1','yun900800','50') ");
        List<String> courses = selectColumnList(connection,"select * from courses",String.class);
        Assert.assertEquals(courses.size(),1);
        executeStatement(connection,"drop table courses ");
    }

    @Test
    public void testSessionFactory() throws IOException {
        DataSource dataSource = hibernateConfigurationFactory.dataSource(
                "localhost",3306,"devnote","root","rootpassword");
        LocalSessionFactoryBean sessionFactory = hibernateConfigurationFactory.sessionFactory("fundamental",dataSource);
        Assert.assertNotNull(sessionFactory);
    }

    @Test
    public void testExecuteSessionFactory() throws IOException {
        DataSource dataSource = hibernateConfigurationFactory.dataSource(
                "localhost",3306,"devnote","root","rootpassword");
        LocalSessionFactoryBean sessionFactory = hibernateConfigurationFactory.sessionFactory("fundamental",dataSource);
        sessionFactory.setPackagesToScan("org.ddd.fundamental");
        sessionFactory.afterPropertiesSet();
        HibernateUtils.doInHibernate((session)->{
            Courses courses = new Courses();
            courses.setId(new StringIdentifier(javaUuidGenerator.generate()));
            courses.setName("yun900800");
            courses.setDuration("50");
            session.persist(courses);
            String hql = "FROM Courses AS E";
            Query query = session.createQuery(hql);
            List<Courses> results = query.list();
            Assert.assertEquals(results.size(),1);
            Assert.assertEquals(results.get(0).getName(),"yun900800");
            session.flush();
        },sessionFactory.getObject());
    }

    protected void executeStatement(Connection connection, String sql) {
        try {
            try(Statement statement = connection.createStatement()) {
                statement.execute(sql);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    protected <T> List<T> selectColumnList(Connection connection, String sql, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        try {
            try(Statement statement = connection.createStatement()) {
                statement.setQueryTimeout(1);
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    result.add(clazz.cast(resultSet.getObject(1)));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }
}

