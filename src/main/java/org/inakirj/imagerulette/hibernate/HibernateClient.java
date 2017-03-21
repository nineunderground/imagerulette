package org.inakirj.imagerulette.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.inakirj.imagerulette.hibernate.entities.Picture;

/**
 * 
 * @author inaki
 *
 */
public class HibernateClient {

    private static SessionFactory factory;
    private Session session;

    public HibernateClient() {
	try {
	    factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	    session = factory.openSession();
	} catch (Throwable ex) {
	    System.err.println("Failed to create sessionFactory object." + ex);
	}
    }

    /**
     * Test connection.
     */
    public void testConnection() {
	Picture forestHoney = new Picture();
	forestHoney.setUrl("www.vaadin.com");
	createPicture(forestHoney);
	listPicture();
    }

    /**
     * Creates the picture.
     *
     * @param forestHoney
     *            the forest honey
     */
    private void createPicture(Picture forestHoney) {
	Transaction tx = null;
	try {
	    tx = session.beginTransaction();
	    session.save(forestHoney);
	    tx.commit();
	} catch (RuntimeException e) {
	    if (tx != null && tx.isActive()) {
		try {
		    // Second try catch as the rollback could fail as well
		    tx.rollback();
		} catch (HibernateException e1) {
		    // logger.debug("Error rolling back transaction");
		}
		// throw again the first exception
		throw e;
	    }
	}
    }

    /**
     * List picture.
     */
    @SuppressWarnings("unchecked")
    private void listPicture() {
	Transaction tx = null;
	try {
	    tx = session.beginTransaction();
	    List<Picture> employees = session.createQuery("FROM Picture").list();
	    for (Iterator<Picture> iterator = employees.iterator(); iterator.hasNext();) {
		Picture employee = iterator.next();
		System.out.print("Id: " + employee.getId());
		System.out.print("Url: " + employee.getUrl());
	    }
	    tx.commit();
	} catch (HibernateException e) {
	    if (tx != null)
		tx.rollback();
	    e.printStackTrace();
	} finally {
	    session.close();
	}

    }

    /**
     * Close connection.
     */
    public void closeConnection() {
	if (session.isOpen()) {
	    session.close();
	}
    }

    // private static void listHoney() {
    // Transaction tx = null;
    // Session session = SessionFactoryUtil.getInstance().getCurrentSession();
    // try {
    // tx = session.beginTransaction();
    // List honeys = session.createQuery(“select h from Honey as h”)
    // .list();
    // for (Iterator iter = honeys.iterator(); iter.hasNext();) {
    // Honey element = (Honey) iter.next();
    // logger.debug(“{}”, element);
    // }
    // tx.commit();
    // } catch (RuntimeException e) {
    // if (tx != null && tx.isActive()) {
    // try {
    // // Second try catch as the rollback could fail as well
    // tx.rollback();
    // } catch (HibernateException e1) {
    // logger.debug(“Error rolling back transaction”);
    // }
    // // throw again the first exception
    // throw e;
    // }

    // private static void deleteHoney(Honey honey) {
    // Transaction tx = null;
    // Session session = SessionFactoryUtil.getInstance().getCurrentSession();
    // try {
    // tx = session.beginTransaction();
    // session.delete(honey);
    // tx.commit();
    // } catch (RuntimeException e) {
    // if (tx != null && tx.isActive()) {
    // try {
    // // Second try catch as the rollback could fail as well
    // tx.rollback();
    // } catch (HibernateException e1) {
    // logger.debug(“Error rolling back transaction”);
    // }
    // // throw again the first exception
    // throw e;
    // }
    // }
    // }

}
