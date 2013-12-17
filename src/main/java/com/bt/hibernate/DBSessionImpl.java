package com.bt.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

@Component
@ContextConfiguration(locations = {"classpath:/com.bt/applicationContext.xml" , "classpath:hibernate.cfg.xml"})
public class DBSessionImpl implements DBSession {


    @Autowired
    private SessionFactory factory;

    public void beginTransaction() {
        factory.getCurrentSession().beginTransaction();
    }

    public void abortTransaction() {
        if(factory.getCurrentSession().getTransaction() != null) {
            factory.getCurrentSession().getTransaction().rollback();
        }
    }

}
