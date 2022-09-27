package ac.th.itsci.durable.app.manager;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ac.th.itsci.durable.entity.Room;
import ac.th.itsci.durable.util.HibernateConnection;



public class RoomManager {
	private static String SALT = "123456";
	public List<Room> listroombymajor(String majorid) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();

			session.beginTransaction();
			List<Room> room = session.createQuery("From Room where id_major = '" + majorid+"' group by room_number").list();
			session.close();

			return room;

		} catch (Exception e) {
			return null;
		}
	}
	public List<Room> listallroom() {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();

			session.beginTransaction();
			List<Room> room = session.createQuery("From Room ").list();
			session.close();

			return room;

		} catch (Exception e) {
			return null;
		}
	}
	
	

}
