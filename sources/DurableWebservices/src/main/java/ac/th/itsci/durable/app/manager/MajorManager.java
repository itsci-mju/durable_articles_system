package ac.th.itsci.durable.app.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ac.th.itsci.durable.entity.Company;
import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.verifyinform;
import ac.th.itsci.durable.util.ConnectionDB;
import ac.th.itsci.durable.util.HibernateConnection;



public class MajorManager {
	private static String SALT = "123456";
	public List<Major> listallmajor() {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();

			session.beginTransaction();
			List<Major> major = session.createQuery("From Major ").list();
			session.close();

			return major;

		} catch (Exception e) {
			return null;
		}
	}



}
