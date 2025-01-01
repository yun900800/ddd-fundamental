package org.ddd.fundamental.infra.query;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CriteriaTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCriteriaCreate(){

        Criteria<User> criteria = new Criteria<>();
        criteria.add(Restrictions.eq("age", 25, true));
        criteria.add(Restrictions.like("name", "John", true));
        criteria.add(Restrictions.or(
                Restrictions.gt("salary", 50000, true),
                Restrictions.isNull("salary", false)
        ));
        userRepository.findAll(criteria);
    }
}
