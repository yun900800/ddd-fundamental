package org.ddd.fundamental.share.infrastructure.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public final class HibernateUtils {
    private  static final Logger LOGGER = LoggerFactory.getLogger(HibernateUtils.class);

    public static void doInHibernate(HibernateTransactionConsumer callable, SessionFactory sessionFactory) {
        Session session = null;
        Transaction txn = null;
        try {
            session = sessionFactory.openSession();
            callable.beforeTransactionCompletion();
            txn = session.beginTransaction();

            callable.accept(session);
            if ( !txn.getRollbackOnly() ) {
                txn.commit();
            }
            else {
                try {
                    txn.rollback();
                }
                catch (Exception e) {
                    LOGGER.error( e, ()->"Rollback failure");
                }
            }
        } catch (Throwable t) {
            if ( txn != null && txn.isActive() ) {
                try {
                    txn.rollback();
                }
                catch (Exception e) {
                    LOGGER.error( e,()->"Rollback failure");
                }
            }
            throw t;
        } finally {
            callable.afterTransactionCompletion();
            if (session != null) {
                session.close();
            }
        }
    }
}
