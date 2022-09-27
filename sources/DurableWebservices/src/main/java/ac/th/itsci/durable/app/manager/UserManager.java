package ac.th.itsci.durable.app.manager;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ac.th.itsci.durable.app.controller.PasswordUtil;
import ac.th.itsci.durable.entity.Login;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.util.HibernateConnection;


public class UserManager {
	private static String SALT = "123456";

	public String doHibernateLogin(Login login) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();

			session.beginTransaction();
			List<Login> user = session.createQuery("From Login where username ='" + login.getUsername()+"'").list();
			System.out.println(login.getUsername());
			session.close();

			if (user.size() == 1) {
				 String password =
				 PasswordUtil.getInstance().createPassword(user.get(0).getPassword(), SALT);
				if (login.getPassword().equals(user.get(0).getPassword())) {
					return "login success";
				} else {
					return "username or password does't match";
				}
			} else {
				return "username or password does't match";
			}
		} catch (Exception e) {
			return "Please try again...";
		}
	}

	public List<Staff> listAllUsers() {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();

			session.beginTransaction();
			List<Staff> staff = session.createQuery("From Staff").list();
			session.close();

			return staff;

		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Login> listAllLogin() {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();

			session.beginTransaction();
			List<Login> login = session.createQuery("From Login").list();
			session.close();

			return login;

		} catch (Exception e) {
			return null;
		}
	}

}
