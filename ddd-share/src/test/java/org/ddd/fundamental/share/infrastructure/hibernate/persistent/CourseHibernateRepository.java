package org.ddd.fundamental.share.infrastructure.hibernate.persistent;

import org.ddd.fundamental.share.infrastructure.hibernate.HibernateRepository;
import org.ddd.fundamental.share.infrastructure.persistence.hibernate.Courses;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CourseHibernateRepository extends HibernateRepository<Courses> {

    public CourseHibernateRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Courses.class);
    }
}
