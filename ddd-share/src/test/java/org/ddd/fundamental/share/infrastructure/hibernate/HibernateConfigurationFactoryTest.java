package org.ddd.fundamental.share.infrastructure.hibernate;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.ResourcePatternResolver;
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
    }

    @Autowired
    private HibernateConfigurationFactory hibernateConfigurationFactory;

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
                "localhost",3306,"domain","sa","");
        Connection connection = dataSource.getConnection();
        executeStatement(connection,"insert into courses(id,name,duration) values('1','yun900800','50') ");
        List<String> courses = selectColumnList(connection,"select * from courses",String.class);
        Assert.assertEquals(courses.size(),1);
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

class Courses {
    private String id;

    private String name;

    private String duration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
