package ac.th.itsci.durable.app.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.Column;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ac.th.itsci.durable.entity.Durable;
import ac.th.itsci.durable.entity.Login;
import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.Room;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.entity.Verify;
import ac.th.itsci.durable.entity.VerifyDurable;
import ac.th.itsci.durable.entity.VerifyDurableID;
import ac.th.itsci.durable.util.ConnectionDB;
import ac.th.itsci.durable.util.HibernateConnection;

public class DurableManager {
	private static String SALT = "123456";

	public List<Durable> listAlldurablebymajor(String majorid, String roomnumber) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			session.beginTransaction();
			List<Durable> durable = session
					.createQuery("From Durable where id_major = '" + majorid + "' and room_number='" + roomnumber + "'")
					.list();
			session.close();

			return durable;

		} catch (Exception e) {
			return null;
		}
	}

	public List<VerifyDurable> listAlldurableverify(String majorid, String roomnumber, String years) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			session.beginTransaction();
			List<VerifyDurable> durable = session.createQuery(
					"Select vd From VerifyDurable vd right join  vd.pk.durable d on vd.pk.durable.Durable_code = d.Durable_code"
							+ " right join vd.pk.verify v on vd.pk.verify.Years = v.Years"
							+ " where d.major.ID_Major = '" + majorid + "' and d.room.Room_number='" + roomnumber
							+ "' and vd.pk.verify.Years = '" + years + "'")
					.list();
			session.close();

			return durable;

		} catch (Exception e) {
			return null;
		}
	}

	public List<VerifyDurable> listAlldurableverify2(String majorid, String roomnumber, String year) {
		List<VerifyDurable> list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "Select vd.durable_status,vd.save_date,vd.note,vd.staff_id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ " ,s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password,vd.verify_years,v.date_end,v.date_start\n"
					+ " ,vd.durable_durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ ",d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ ",d.note,d.room_number,r.build,r.room_name,r.floor\n" + " ,vd.picture_verify\n"
					+ "From Verify_Durable vd right join  durable d on vd.durable_durable_code = d.Durable_code right join verify v on vd.verify_years = v.years \n"
					+ " inner join staff s on s.id_staff = vd.staff_id_staff inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username \n"
					+ " left join room r on r.room_number = d.room_number \n" + " where d.id_major = '" + majorid
					+ "' and d.room_number= '" + roomnumber + "' and vd.verify_years = '" + year + "' and d.durable_statusnow != 'แทงจำหน่าย';";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String Durable_status = rs.getString(1);
				String Save_date = rs.getString(2);
				String note = rs.getString(3);
				/* เริ่ม staff */
				int staff_id_staff = rs.getInt(4);
				String Id_card = rs.getString(5);
				String Staff_name = rs.getString(6);
				String Staff_lastname = rs.getString(7);
				String Staff_status = rs.getString(8);
				String Email = rs.getString(9);
				String Brithday = rs.getString(10);
				String Phone_number = rs.getString(11);
				String Image_staff = rs.getString(12);
				int idmajor = rs.getInt(13);
				String majorname = rs.getString(14);
				String usernames = rs.getString(15);
				String status = rs.getString(16);
				String password = rs.getString(17);
				Major m = new Major(idmajor, majorname);
				Login l = new Login(usernames, password, status);
				Staff s = new Staff(staff_id_staff, Id_card, Staff_name, Staff_lastname, Staff_status, Email, Brithday,
						Phone_number, Image_staff, m, l);
				/* ปิด staff */
				/**************************/
				/* เริ่ม verify */
				String years = rs.getString(18);
				String dateend = rs.getString(19);
				String datestart = rs.getString(20);
				Calendar date_end = Calendar.getInstance();
				String edate[] = dateend.split(" ");
				String edate1[] = edate[0].split("-");
				String eTime[] = edate[1].split(":");
				date_end.set(Integer.parseInt(edate1[0]), Integer.parseInt(edate1[1]) - 1, Integer.parseInt(edate1[2]),
						Integer.parseInt(eTime[0]), Integer.parseInt(eTime[1]), Integer.parseInt(eTime[2]));
				Date date_end2 = date_end.getTime();

				Calendar date_start = Calendar.getInstance();
				String sdate[] = datestart.split(" ");
				String sdate1[] = sdate[0].split("-");
				String sTime[] = sdate[1].split(":");

				date_start.set(Integer.parseInt(sdate1[0]), Integer.parseInt(sdate1[1]) - 1,
						Integer.parseInt(sdate1[2]), Integer.parseInt(sTime[0]), Integer.parseInt(sTime[1]),
						Integer.parseInt(sTime[2]));
				Date date_start2 = date_start.getTime();

				Verify v = new Verify(years, date_end2, date_start2);
				/* ปิด verify */
				/**************************/
				/* เริ่ม durable */
				String durable_durable_code = rs.getString(21);
				String Durable_name = rs.getString(22);
				String Durable_number = rs.getString(23);
				String Durable_brandname = rs.getString(24);
				String Durable_model = rs.getString(25);
				String Durable_price = rs.getString(26);
				String Durable_statusnow = rs.getString(27);
				String Responsible_person = rs.getString(28);
				String Durable_image = rs.getString(29);
				String Durable_Borrow_Status = rs.getString(30);
				String Durable_entrancedate = rs.getString(31);
				String durablenote = rs.getString(32);
				String Room_number = rs.getString(33);
				String Room_name = rs.getString(34);
				String Build = rs.getString(35);
				String floor = rs.getString(36);

				Room r = new Room(Room_number, Room_name, Build, floor, m);

				Durable d = new Durable(durable_durable_code, Durable_name, Durable_number, Durable_brandname,
						Durable_model, Durable_price, Durable_statusnow, Responsible_person, Durable_image,
						Durable_Borrow_Status, Durable_entrancedate, durablenote, m, r);
				/* ปิด durable */
				String picture_verify = rs.getString(37);

				VerifyDurableID pk = new VerifyDurableID(d, s, v);

				VerifyDurable vd = new VerifyDurable(Save_date, Durable_status, note, picture_verify, pk);
				list.add(vd);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Durable> listAllnotdurableverify(String majorid, String roomnumber, String years) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			session.beginTransaction();

			List<Durable> durable = session.createQuery(
					"select d from Durable d where d.durable_code NOT IN  (select vd.pk.durable.durable_code from VerifyDurable vd where vd.pk.verify.years ='"
							+ years + "') and d.room.room_number= '" + roomnumber + "' and d.major.ID_Major = '"
							+ majorid + "' ")
					.list();
			session.close();

			return durable;

		} catch (Exception e) {
			return null;
		}
	}

	public List<Durable> listAllnotdurableverify2(String majorid, String roomnumber, String years) {
		List<Durable> list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "select d.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ ",d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate,d.note,d.id_major,m.major_name,d.room_number,r.build,r.room_name,r.floor \n"
					+ " from durable d inner join major m on d.id_major = m.id_major  left join room r on r.room_number = d.room_number \n"
					+ " where d.durable_code NOT IN  (select vd.durable_durable_code from verify_durable vd where vd.verify_years = '"
					+ years + "' ) \n" + " and d.room_number= '" + roomnumber + "' and d.ID_Major = '" + majorid
					+ "' and d.durable_statusnow != 'แทงจำหน่าย';";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				/* เริ่ม durable */
				String durable_code = rs.getString(1);
				String Durable_name = rs.getString(2);
				String Durable_number = rs.getString(3);
				String Durable_brandname = rs.getString(4);
				String Durable_model = rs.getString(5);
				String Durable_price = rs.getString(6);
				String Durable_statusnow = rs.getString(7);
				String Responsible_person = rs.getString(8);
				String Durable_image = rs.getString(9);
				String Durable_Borrow_Status = rs.getString(10);
				String Durable_entrancedate = rs.getString(11);
				String durablenote = rs.getString(12);
				int idmajor = rs.getInt(13);
				String majorname = rs.getString(14);
				String Room_number = rs.getString(15);
				String Room_name = rs.getString(16);
				String Build = rs.getString(17);
				String floor = rs.getString(18);

				Major m = new Major(idmajor, majorname);
				Room r = new Room(Room_number, Room_name, Build, floor, m);

				Durable d = new Durable(durable_code, Durable_name, Durable_number, Durable_brandname, Durable_model,
						Durable_price, Durable_statusnow, Responsible_person, Durable_image, Durable_Borrow_Status,
						Durable_entrancedate, durablenote, m, r);
				/* ปิด durable */

				list.add(d);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Durable> listdurablenotverifyadmin(String roomnumber, String years) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			session.beginTransaction();

			List<Durable> durable = session.createQuery(
					"select d from Durable d where d.durable_code NOT IN  (select vd.pk.durable.durable_code from VerifyDurable vd where vd.pk.verify.years ='"
							+ years + "') and d.room.room_number= '" + roomnumber + "' ")
					.list();
			session.close();

			return durable;

		} catch (Exception e) {
			return null;
		}
	}

	public List<Durable> listdurablenotverifyadmin2(String roomnumber, String years) {
		List<Durable> list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "select d.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ ",d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate,d.note,d.id_major,m.major_name,d.room_number,r.build,r.room_name,r.floor \n"
					+ " from durable d inner join major m on d.id_major = m.id_major  left join room r on r.room_number = d.room_number \n"
					+ " where d.durable_code NOT IN  (select vd.durable_durable_code from verify_durable vd where vd.verify_years = '"
					+ years + "' ) \n" + " and d.room_number= '" + roomnumber + "'  and d.durable_statusnow != 'แทงจำหน่าย';";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				/* เริ่ม durable */
				String durable_code = rs.getString(1);
				String Durable_name = rs.getString(2);
				String Durable_number = rs.getString(3);
				String Durable_brandname = rs.getString(4);
				String Durable_model = rs.getString(5);
				String Durable_price = rs.getString(6);
				String Durable_statusnow = rs.getString(7);
				String Responsible_person = rs.getString(8);
				String Durable_image = rs.getString(9);
				String Durable_Borrow_Status = rs.getString(10);
				String Durable_entrancedate = rs.getString(11);
				String durablenote = rs.getString(12);
				int idmajor = rs.getInt(13);
				String majorname = rs.getString(14);
				String Room_number = rs.getString(15);
				String Room_name = rs.getString(16);
				String Build = rs.getString(17);
				String floor = rs.getString(18);

				Major m = new Major(idmajor, majorname);
				Room r = new Room(Room_number, Room_name, Build, floor, m);

				Durable d = new Durable(durable_code, Durable_name, Durable_number, Durable_brandname, Durable_model,
						Durable_price, Durable_statusnow, Responsible_person, Durable_image, Durable_Borrow_Status,
						Durable_entrancedate, durablenote, m, r);
				/* ปิด durable */

				list.add(d);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<VerifyDurable> listdurableverifyadmin(String roomnumber, String years) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			session.beginTransaction();

			List<VerifyDurable> durable = session.createQuery(
					"Select vd From VerifyDurable vd right join  durable d on vd.pk.durable.durable_code = d.durable_code"
							+ " right join verify v on vd.pk.verify.years = v.years" + " where   d.room.room_number='"
							+ roomnumber + "' and vd.pk.verify.years = '" + years + "'")
					.list();
			session.close();

			return durable;

		} catch (Exception e) {
			return null;
		}
	}

	public List<VerifyDurable> listdurableverifyadmin2(String roomnumber, String year) {
		List<VerifyDurable> list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "Select vd.durable_status,vd.save_date,vd.note,vd.staff_id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ " ,s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password,vd.verify_years,v.date_end,v.date_start\n"
					+ " ,vd.durable_durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ ",d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ ",d.note,d.room_number,r.build,r.room_name,r.floor\n" + " ,vd.picture_verify\n"
					+ "From Verify_Durable vd right join  durable d on vd.durable_durable_code = d.Durable_code right join verify v on vd.verify_years = v.years \n"
					+ " inner join staff s on s.id_staff = vd.staff_id_staff inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username \n"
					+ " left join room r on r.room_number = d.room_number  where  d.room_number= '" + roomnumber
					+ "' and vd.verify_years = '" + year + "' and d.durable_statusnow != 'แทงจำหน่าย';";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String Durable_status = rs.getString(1);
				String Save_date = rs.getString(2);
				String note = rs.getString(3);
				/* เริ่ม staff */
				int staff_id_staff = rs.getInt(4);
				String Id_card = rs.getString(5);
				String Staff_name = rs.getString(6);
				String Staff_lastname = rs.getString(7);
				String Staff_status = rs.getString(8);
				String Email = rs.getString(9);
				String Brithday = rs.getString(10);
				String Phone_number = rs.getString(11);
				String Image_staff = rs.getString(12);
				int idmajor = rs.getInt(13);
				String majorname = rs.getString(14);
				String usernames = rs.getString(15);
				String status = rs.getString(16);
				String password = rs.getString(17);
				Major m = new Major(idmajor, majorname);
				Login l = new Login(usernames, password, status);
				Staff s = new Staff(staff_id_staff, Id_card, Staff_name, Staff_lastname, Staff_status, Email, Brithday,
						Phone_number, Image_staff, m, l);
				/* ปิด staff */
				/**************************/
				/* เริ่ม verify */
				String years = rs.getString(18);
				String dateend = rs.getString(19);
				String datestart = rs.getString(20);
				Calendar date_end = Calendar.getInstance();
				String edate[] = dateend.split(" ");
				String edate1[] = edate[0].split("-");
				String eTime[] = edate[1].split(":");
				date_end.set(Integer.parseInt(edate1[0]), Integer.parseInt(edate1[1]) - 1, Integer.parseInt(edate1[2]),
						Integer.parseInt(eTime[0]), Integer.parseInt(eTime[1]), Integer.parseInt(eTime[2]));
				Date date_end2 = date_end.getTime();

				Calendar date_start = Calendar.getInstance();
				String sdate[] = datestart.split(" ");
				String sdate1[] = sdate[0].split("-");
				String sTime[] = sdate[1].split(":");

				date_start.set(Integer.parseInt(sdate1[0]), Integer.parseInt(sdate1[1]) - 1,
						Integer.parseInt(sdate1[2]), Integer.parseInt(sTime[0]), Integer.parseInt(sTime[1]),
						Integer.parseInt(sTime[2]));
				Date date_start2 = date_start.getTime();

				Verify v = new Verify(years, date_end2, date_start2);
				/* ปิด verify */
				/**************************/
				/* เริ่ม durable */
				String durable_durable_code = rs.getString(21);
				String Durable_name = rs.getString(22);
				String Durable_number = rs.getString(23);
				String Durable_brandname = rs.getString(24);
				String Durable_model = rs.getString(25);
				String Durable_price = rs.getString(26);
				String Durable_statusnow = rs.getString(27);
				String Responsible_person = rs.getString(28);
				String Durable_image = rs.getString(29);
				String Durable_Borrow_Status = rs.getString(30);
				String Durable_entrancedate = rs.getString(31);
				String durablenote = rs.getString(32);
				String Room_number = rs.getString(33);
				String Room_name = rs.getString(34);
				String Build = rs.getString(35);
				String floor = rs.getString(36);

				Room r = new Room(Room_number, Room_name, Build, floor, m);

				Durable d = new Durable(durable_durable_code, Durable_name, Durable_number, Durable_brandname,
						Durable_model, Durable_price, Durable_statusnow, Responsible_person, Durable_image,
						Durable_Borrow_Status, Durable_entrancedate, durablenote, m, r);
				/* ปิด durable */
				String picture_verify = rs.getString(37);

				VerifyDurableID pk = new VerifyDurableID(d, s, v);

				VerifyDurable vd = new VerifyDurable(Save_date, Durable_status, note, picture_verify, pk);
				list.add(vd);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public Durable getDurable(String durablecode) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			session.beginTransaction();
			Durable durable = (Durable) session.createQuery("From durable where durable_code = '" + durablecode + "'")
					.getSingleResult();
			session.close();

			return durable;

		} catch (Exception e) {
			return null;
		}
	}

	public Durable getDurable2(String durablecode) {
		Durable d = new Durable();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "select  d.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ "	,d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate,d.note\n"
					+ ",d.id_major,m.major_name,d.room_number,r.build,r.room_name,r.floor  from  durable d inner join major m on d.id_major = m.id_major  left join room r on r.room_number = d.room_number\n"
					+ " WHERE d.durable_code= '"+ durablecode + "'  ;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String durable_code = rs.getString(1);
				String Durable_name = rs.getString(2);
				String Durable_number = rs.getString(3);
				String Durable_brandname = rs.getString(4);
				String Durable_model = rs.getString(5);
				String Durable_price = rs.getString(6);
				String Durable_statusnow = rs.getString(7);
				String Responsible_person = rs.getString(8);
				String Durable_image = rs.getString(9);
				String Durable_Borrow_Status = rs.getString(10);
				String Durable_entrancedate = rs.getString(11);
				String durablenote = rs.getString(12);
				int idmajor = rs.getInt(13);
				String majorname = rs.getString(14);
				String Room_number = rs.getString(15);
				String Room_name = rs.getString(16);
				String Build = rs.getString(17);
				String floor = rs.getString(18);

				Major m = new Major(idmajor, majorname);
				Room r = new Room(Room_number, Room_name, Build, floor, m);

				d = new Durable(durable_code, Durable_name, Durable_number, Durable_brandname, Durable_model,
						Durable_price, Durable_statusnow, Responsible_person, Durable_image, Durable_Borrow_Status,
						Durable_entrancedate, durablenote, m, r);
			}
			// System.out.println(sql);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return d;
	}

	public String insertdurable(Durable durable) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			Session session = sessionFactory.openSession();
			Transaction t = session.beginTransaction();
			session.saveOrUpdate(durable);
			t.commit();
			session.close();
			return "successfully saved";
		} catch (Exception e) {
			return "failed to save major";
		}
	}

	public List<Durable> listAlldurable() {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			session.beginTransaction();
			List<Durable> durable = session.createQuery("From durable").list();
			session.close();

			return durable;

		} catch (Exception e) {
			return null;
		}
	}
	public List<Durable> ListallDurable2() {
		List<Durable> list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "select  d.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ "	,d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate,d.note\n"
					+ ",d.id_major,m.major_name,d.room_number,r.build,r.room_name,r.floor  from  durable d inner join major m on d.id_major = m.id_major  left join room r on r.room_number = d.room_number\n"
					+ ";";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String durable_code = rs.getString(1);
				String Durable_name = rs.getString(2);
				String Durable_number = rs.getString(3);
				String Durable_brandname = rs.getString(4);
				String Durable_model = rs.getString(5);
				String Durable_price = rs.getString(6);
				String Durable_statusnow = rs.getString(7);
				String Responsible_person = rs.getString(8);
				String Durable_image = rs.getString(9);
				String Durable_Borrow_Status = rs.getString(10);
				String Durable_entrancedate = rs.getString(11);
				String durablenote = rs.getString(12);
				int idmajor = rs.getInt(13);
				String majorname = rs.getString(14);
				String Room_number = rs.getString(15);
				String Room_name = rs.getString(16);
				String Build = rs.getString(17);
				String floor = rs.getString(18);

				Major m = new Major(idmajor, majorname);
				Room r = new Room(Room_number, Room_name, Build, floor, m);

				Durable d = new Durable(durable_code, Durable_name, Durable_number, Durable_brandname, Durable_model,
						Durable_price, Durable_statusnow, Responsible_person, Durable_image, Durable_Borrow_Status,
						Durable_entrancedate, durablenote, m, r);
				list.add(d);
			}
			// System.out.println(sql);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	

}
