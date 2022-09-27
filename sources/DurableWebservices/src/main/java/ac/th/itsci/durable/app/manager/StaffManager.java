package ac.th.itsci.durable.app.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.persistence.Column;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ac.th.itsci.durable.entity.Login;
import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.util.ConnectionDB;
import ac.th.itsci.durable.util.HibernateConnection;

public class StaffManager {
	private static String SALT = "123456";

	public Staff listStaffbyusername(String username) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();

			session.beginTransaction();
			Staff staff = (Staff) session.createQuery("From Staff where username = '" + username + "'")
					.getSingleResult();
			session.close();

			return staff;

		} catch (Exception e) {
			return null;
		}
	}

	public Staff listStaffbyusername2(String username) {
		Staff s = new Staff();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "select id_staff,id_card,staff_name,staff_lastname,staff_status,email,brithday,phone_number,image_staff,s.id_major,s.username,m.major_name,l.status,l.password from Staff s inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username WHERE s.username= '"
					+ username + "'  ;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id_staff = rs.getInt(1);
				String Id_card = rs.getString(2);
				String Staff_name = rs.getString(3);
				String Staff_lastname = rs.getString(4);
				String Staff_status = rs.getString(5);
				String Email = rs.getString(6);
				String Brithday = rs.getString(7);
				String Phone_number = rs.getString(8);
				String Image_staff = rs.getString(9);
				int idmajor = rs.getInt(10);
				String usernames = rs.getString(11);
				String majorname = rs.getString(12);
				String status = rs.getString(13);
				String password = rs.getString(14);
				Major m = new Major();
				m.setID_Major(idmajor);
				m.setMajor_Name(majorname);
				Login l = new Login();
				l.setUsername(usernames);
				l.setPassword(password);
				l.setStatus(status);
				s = new Staff(id_staff, Id_card, Staff_name, Staff_lastname, Staff_status, Email, Brithday,
						Phone_number, Image_staff, m, l);
			}
			// System.out.println(sql);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return s;
	}

	/*
	 * public String insertdurable(durable durable) { try { SessionFactory
	 * sessionFactory = HibernateConnection.doHibernateConnection();
	 * 
	 * Session session = sessionFactory.openSession(); Transaction t =
	 * session.beginTransaction(); session.saveOrUpdate(durable); t.commit();
	 * session.close(); return "successfully saved"; } catch (Exception e) { return
	 * "failed to save major"; } }
	 * 
	 * 
	 * public List<durable> listAlldurable() { try { SessionFactory sessionFactory =
	 * HibernateConnection.doHibernateConnection(); Session session =
	 * sessionFactory.openSession();
	 * 
	 * session.beginTransaction(); List<durable> durable =
	 * session.createQuery("From durable").list(); session.close();
	 * 
	 * return durable;
	 * 
	 * } catch (Exception e) { return null; } }
	 */

}
