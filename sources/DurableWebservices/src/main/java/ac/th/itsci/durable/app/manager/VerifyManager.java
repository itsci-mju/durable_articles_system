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
import ac.th.itsci.durable.entity.inform_repair;
import ac.th.itsci.durable.entity.verifyinform;
import ac.th.itsci.durable.util.ConnectionDB;
import ac.th.itsci.durable.util.HibernateConnection;





public class VerifyManager {
	private static String SALT = "123456";
	
	
	public String insertverifyform(VerifyDurable verify_durable) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();

			Session session = sessionFactory.openSession();
			Transaction t = session.beginTransaction();
			session.saveOrUpdate(verify_durable);
			t.commit();
			session.close();
			return "successfully saved";
		} catch (Exception e) {
			return "failed to save verifyform";
		}
	}
	public int insertverifyform2(VerifyDurable vd) {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "INSERT INTO verify_durable (durable_status,save_date,note,staff_id_staff,verify_years,durable_durable_code,picture_verify)"
					+ " VALUES ('" + vd.getDurable_status() + "', '" + vd.getSave_date() + "', '" + vd.getNote() + "', '"
					+ vd.getPk().getStaff().getId_staff() + "', '" + vd.getPk().getVerify().getYears() + "', '" + vd.getPk().getDurable().getDurable_code()+ "', '" 
					+ vd.getPicture_verify()+  "');";
			int result = stmt.executeUpdate(sql);

			con.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}
	public int updateverifyform(VerifyDurable vd) {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "UPDATE verify_durable\n"
					+ "SET\n"
					+ "durable_status = '" + vd.getDurable_status() + "',\n"
					+ "save_date = '" + vd.getSave_date() + "',\n"
					+ "note = '" + vd.getNote() + "',\n"
					+ "verify_years = '" + vd.getPk().getVerify().getYears() + "'\n"
					+ "WHERE staff_id_staff = '" + vd.getPk().getStaff().getId_staff() + "' AND verify_years = '" + vd.getPk().getVerify().getYears() + "' AND durable_durable_code = '" + vd.getPk().getDurable().getDurable_code()+ "';";
			int result = stmt.executeUpdate(sql);

			con.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public Staff getstaff(int staffid) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();

			session.beginTransaction();
			Staff staff = (Staff) session.createQuery("From Staff where id_staff = "+staffid).getSingleResult();
			session.close();

			return staff;

		} catch (Exception e) {
			return null;
		}
	}
	public Staff getstaff2(int staffid) {
		Staff s = new Staff();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "select id_staff,id_card,staff_name,staff_lastname,staff_status,email,brithday,phone_number,image_staff,s.id_major,s.username,m.major_name,l.status,l.password from Staff s inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username WHERE s.id_staff= "
					+ staffid + "  ;";
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
	
	public Verify getverify(String years) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			session.beginTransaction();
			Verify verify = (Verify) session.createQuery("SELECT v From Verify v where Years = '"+years+"'").getSingleResult();
			session.close();

			return verify;

		} catch (Exception e) {
			return null;
		}
	}
	public Verify getverify2(String year) {
		Verify s = new Verify();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "select years,date_end,date_start from verify where years = '"+year+"';";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String years = rs.getString(1);
				String dateend = rs.getString(2);
				String datestart = rs.getString(3);
				Calendar date_end = Calendar.getInstance();
				String edate[] = dateend.split(" ");
				String edate1[] = edate[0].split("-");
				String eTime[] = edate[1].split(":");
				date_end.set(Integer.parseInt(edate1[0]), Integer.parseInt(edate1[1]) - 1,
						Integer.parseInt(edate1[2]), Integer.parseInt(eTime[0]), Integer.parseInt(eTime[1]), Integer.parseInt(eTime[2]));
				Date date_end2 =  date_end.getTime();
			
				
				
				Calendar date_start = Calendar.getInstance();
				String sdate[] = datestart.split(" ");
				String sdate1[] = sdate[0].split("-");
				String sTime[] = sdate[1].split(":");
	
				date_start.set(Integer.parseInt(sdate1[0]), Integer.parseInt(sdate1[1]) - 1,
						Integer.parseInt(sdate1[2]), Integer.parseInt(sTime[0]), Integer.parseInt(sTime[1]), Integer.parseInt(sTime[2]));
				Date date_start2 =  date_start.getTime();
				
				 s = new Verify(years, date_end2, date_start2);

			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return s;
	}
	
	public Durable getdurable(String durablecode) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			session.beginTransaction();
			Durable durable = (Durable) session.createQuery("SELECT d From Durable d where Durable_code = '"+durablecode+"'").getSingleResult();
			
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

	public List<Verify> listyears() {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			session.beginTransaction();
			List<Verify> verify = session.createQuery("From Verify order by Years DESC").list();
			
			session.close();

			return verify;

		} catch (Exception e) {
			return null;
		}
	}
	public List<Verify> getlistyears() {
		List<Verify> list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "select years,date_end,date_start from verify order by years DESC";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String years = rs.getString(1);
				String dateend = rs.getString(2);
				String datestart = rs.getString(3);
				Calendar date_end = Calendar.getInstance();
				String edate[] = dateend.split(" ");
				String edate1[] = edate[0].split("-");
				String eTime[] = edate[1].split(":");
				date_end.set(Integer.parseInt(edate1[0]), Integer.parseInt(edate1[1]) - 1,
						Integer.parseInt(edate1[2]), Integer.parseInt(eTime[0]), Integer.parseInt(eTime[1]), Integer.parseInt(eTime[2]));
				Date date_end2 =  date_end.getTime();
			
				
				
				Calendar date_start = Calendar.getInstance();
				String sdate[] = datestart.split(" ");
				String sdate1[] = sdate[0].split("-");
				String sTime[] = sdate[1].split(":");
	
				date_start.set(Integer.parseInt(sdate1[0]), Integer.parseInt(sdate1[1]) - 1,
						Integer.parseInt(sdate1[2]), Integer.parseInt(sTime[0]), Integer.parseInt(sTime[1]), Integer.parseInt(sTime[2]));
				Date date_start2 =  date_start.getTime();
				
				Verify s = new Verify(years, date_end2, date_start2);
				list.add(s);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	
	
	public int countalldurable(String majorname) {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		
		int id = 0;
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT count(d.id_major) FROM Durable d where d.id_major IN (SELECT m.id_major from Major m where m.major_name = '"+majorname+"')";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				id = rs.getInt(1);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id ;
	}
	public int countdurabled(String majorname,String year) {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		int id = 0;
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT count(d.id_major) FROM durable d where d.id_major IN (SELECT m.id_major from major m where m.major_name = '"+majorname+"')"
					+ "and d.durable_code  IN (SELECT v.durable_durable_code from verify_durable v where verify_years ='"+year+"')";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				id = rs.getInt(1);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id ;
	}
	
	public int countnotdurable(String majorname,String year) {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		int id = 0;
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT count(d.id_major) FROM durable d where d.id_major IN (SELECT m.id_major from Major m where m.major_name = '"+majorname+"')"
					+ "and d.durable_code NOT IN (SELECT v.durable_durable_code from verify_durable v where verify_years ='"+year+"')";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				id = rs.getInt(1);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id ;
	}
	public int countverifystatusgood(String majorname,String year) {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		int id = 0;
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT COUNT(vd.durable_status) FROM verify_durable vd left join durable d on vd.durable_durable_code = d.durable_code \n"
					+ "left join major m on d.id_major = m.id_major \n"
					+ "WHERE vd.durable_status = 'ดี' and vd.verify_years = '"+year+"' and m.major_name = '"+majorname+"';";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				id = rs.getInt(1);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id ;
	}
	public List<VerifyDurable> verifystatusgood(String major_name, String year) {
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
					+ ",d.note,d.room_number,r.build,r.room_name,r.floor,vd.picture_verify\n"
					+ "From Verify_Durable vd right join  durable d on vd.durable_durable_code = d.Durable_code right join verify v on vd.verify_years = v.years \n"
					+ " inner join staff s on s.id_staff = vd.staff_id_staff inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username \n"
					+ " left join room r on r.room_number = d.room_number  where  vd.durable_status = 'ดี' and vd.verify_years = '"+year+"' and m.major_name = '"+major_name+"';";
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
	
	public int countverifystatus2(String majorname,String year) {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		int id = 0;
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT COUNT(vd.durable_status) FROM verify_durable vd left join durable d on vd.durable_durable_code = d.durable_code \n"
					+ "left join major m on d.id_major = m.id_major \n"
					+ "WHERE vd.durable_status = 'ชำรุด' and vd.verify_years = '"+year+"' and m.major_name = '"+majorname+"';";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				id = rs.getInt(1);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id ;
	}
	public List<VerifyDurable> verifystatus2(String major_name, String year) {
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
					+ ",d.note,d.room_number,r.build,r.room_name,r.floor,vd.picture_verify\n"
					+ "From Verify_Durable vd right join  durable d on vd.durable_durable_code = d.Durable_code right join verify v on vd.verify_years = v.years \n"
					+ " inner join staff s on s.id_staff = vd.staff_id_staff inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username \n"
					+ " left join room r on r.room_number = d.room_number  where  vd.durable_status = 'ชำรุด' and vd.verify_years = '"+year+"' and m.major_name = '"+major_name+"';";
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
	
	
	public int countverifystatus3(String majorname,String year) {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		int id = 0;
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT COUNT(vd.durable_status) FROM verify_durable vd left join durable d on vd.durable_durable_code = d.durable_code \n"
					+ "left join major m on d.id_major = m.id_major \n"
					+ "WHERE vd.durable_status = 'แทงจำหน่าย' and vd.verify_years = '"+year+"' and m.major_name = '"+majorname+"';";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				id = rs.getInt(1);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id ;
	}
	public List<VerifyDurable> verifystatus3(String major_name, String year) {
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
					+ ",d.note,d.room_number,r.build,r.room_name,r.floor,vd.picture_verify\n"
					+ "From Verify_Durable vd right join  durable d on vd.durable_durable_code = d.Durable_code right join verify v on vd.verify_years = v.years \n"
					+ " inner join staff s on s.id_staff = vd.staff_id_staff inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username \n"
					+ " left join room r on r.room_number = d.room_number  where  vd.durable_status = 'แทงจำหน่าย' and vd.verify_years = '"+year+"' and m.major_name = '"+major_name+"';";
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
	
	public int countverifystatus4(String majorname,String year) {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		int id = 0;
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT COUNT(vi.verify_id) FROM verifyinform vi left join inform_repair ir on  vi.informid =ir.Informid  inner join staff s on ir.id_staff = s.id_staff\n"
					+ "  left join durable d on ir.durable_code = d.durable_code left join verify_durable vd on d.durable_code = vd.durable_durable_code \n"
					+ "  inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username\n"
					+ "left join room r on r.room_number = d.room_number where m.major_name = '"+majorname+"' and vd.verify_years = '"+year+"' and \n"
					+ "(vi.verify_status ='ส่งซ่อม' or vi.verify_status = 'ซ่อมเอง') AND vi.verify_id Not IN (SELECT rd.verify_id FROM repair_durable rd  where vi.verify_id = rd.verify_id) ;";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				id = rs.getInt(1);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id ;
	}
	public List<verifyinform> verifystatus4(String major_name,String year) {
		List<verifyinform> list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = " SELECT vi.verify_id,vi.verify_date,vi.verify_detail,vi.verify_status,vi.informid,ir.Informtype,ir.dateinform,ir.details,ir.picture_inform,ir.id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ "	,s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password,ir.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ "	,d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ "	,d.note,d.room_number,r.build,r.room_name,r.floor FROM  verifyinform vi left join inform_repair ir on  vi.informid =ir.Informid  inner join staff s on ir.id_staff = s.id_staff\n"
					+ "	left join durable d on ir.durable_code = d.durable_code left join verify_durable vd on d.durable_code = vd.durable_durable_code \n"
					+ "	inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username\n"
					+ "	left join room r on r.room_number = d.room_number where m.major_name = '"+major_name+"' and vd.verify_years = '"+year+"' and \n"
					+ "	(vi.verify_status ='ส่งซ่อม' or vi.verify_status = 'ซ่อมเอง') AND vi.verify_id Not IN (SELECT rd.verify_id FROM repair_durable rd  where vi.verify_id = rd.verify_id) ;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int verifyid = rs.getInt(1);
				String verifydate = rs.getString(2);
				String verifydetail = rs.getString(3);
				String verifystatus = rs.getString(4);
				
				Calendar date_verify = Calendar.getInstance();
				String vdate[] = verifydate.split(" ");
				String vdate1[] = vdate[0].split("-");
				String vTime[] = vdate[1].split(":");
				date_verify.set(Integer.parseInt(vdate1[0]), Integer.parseInt(vdate1[1]) - 1, Integer.parseInt(vdate1[2]),
						Integer.parseInt(vTime[0]), Integer.parseInt(vTime[1]), Integer.parseInt(vTime[2]));

				String Informid = rs.getString(5);
				String Informtype = rs.getString(6);
				String dateinform = rs.getString(7);
				String details = rs.getString(8);
				String picture_inform = rs.getString(9);
				Calendar date_inform = Calendar.getInstance();
				String idate[] = dateinform.split(" ");
				String idate1[] = idate[0].split("-");
				String iTime[] = idate[1].split(":");
				date_inform.set(Integer.parseInt(idate1[0]), Integer.parseInt(idate1[1]) - 1, Integer.parseInt(idate1[2]),
						Integer.parseInt(iTime[0]), Integer.parseInt(iTime[1]), Integer.parseInt(iTime[2]));
				
				/* เริ่ม staff */
				int staff_id_staff = rs.getInt(10);
				String Id_card = rs.getString(11);
				String Staff_name = rs.getString(12);
				String Staff_lastname = rs.getString(13);
				String Staff_status = rs.getString(14);
				String Email = rs.getString(15);
				String Brithday = rs.getString(16);
				String Phone_number = rs.getString(17);
				String Image_staff = rs.getString(18);
				int idmajor = rs.getInt(19);
				String majorname = rs.getString(20);
				String usernames = rs.getString(21);
				String status = rs.getString(22);
				String password = rs.getString(23);
				Major m = new Major(idmajor, majorname);
				Login l = new Login(usernames, password, status);
				Staff s = new Staff(staff_id_staff, Id_card, Staff_name, Staff_lastname, Staff_status, Email, Brithday,
						Phone_number, Image_staff, m, l);
				/* ปิด staff */
				
				/* เริ่ม durable */
				String durable_code = rs.getString(24);
				String Durable_name = rs.getString(25);
				String Durable_number = rs.getString(26);
				String Durable_brandname = rs.getString(27);
				String Durable_model = rs.getString(28);
				String Durable_price = rs.getString(29);
				String Durable_statusnow = rs.getString(30);
				String Responsible_person = rs.getString(31);
				String Durable_image = rs.getString(32);
				String Durable_Borrow_Status = rs.getString(33);
				String Durable_entrancedate = rs.getString(34);
				String durablenote = rs.getString(35);
				String Room_number = rs.getString(36);
				String Room_name = rs.getString(37);
				String Build = rs.getString(38);
				String floor = rs.getString(39);

				Room r = new Room(Room_number, Room_name, Build, floor, m);

				Durable d = new Durable(durable_code, Durable_name, Durable_number, Durable_brandname, Durable_model,
						Durable_price, Durable_statusnow, Responsible_person, Durable_image, Durable_Borrow_Status,
						Durable_entrancedate, durablenote, m, r);
				/* ปิด durable */
			
				inform_repair ir = new inform_repair(Informid,Informtype,date_inform,details,picture_inform,s,d);
				
				verifyinform vi = new verifyinform(verifyid,date_verify,verifystatus,verifydetail,ir);
				list.add(vi);
			}
			// System.out.println(sql);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public Verify getverify_in_verifydurable(String durablecode,String year) {
		Verify v = null;
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT years,date_end,date_start FROM verify where years in (SELECT vd.verify_years FROM verify_durable vd where vd.durable_durable_code = '"+durablecode+"' and vd.verify_years='"+year+"');";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String years = rs.getString(1);
				String dateend = rs.getString(2);
				String datestart = rs.getString(3);
				Calendar date_end = Calendar.getInstance();
				String edate[] = dateend.split(" ");
				String edate1[] = edate[0].split("-");
				String eTime[] = edate[1].split(":");
				date_end.set(Integer.parseInt(edate1[0]), Integer.parseInt(edate1[1]) - 1,
						Integer.parseInt(edate1[2]), Integer.parseInt(eTime[0]), Integer.parseInt(eTime[1]), Integer.parseInt(eTime[2]));
				Date date_end2 =  date_end.getTime();
			
				
				
				Calendar date_start = Calendar.getInstance();
				String sdate[] = datestart.split(" ");
				String sdate1[] = sdate[0].split("-");
				String sTime[] = sdate[1].split(":");
	
				date_start.set(Integer.parseInt(sdate1[0]), Integer.parseInt(sdate1[1]) - 1,
						Integer.parseInt(sdate1[2]), Integer.parseInt(sTime[0]), Integer.parseInt(sTime[1]), Integer.parseInt(sTime[2]));
				Date date_start2 =  date_start.getTime();
				
				 v = new Verify(years, date_end2, date_start2);

			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return v;
	}
	
	public VerifyDurable getverifydurablebycode(String durablecode) {
		VerifyDurable vd = null;
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "Select vd.durable_status,vd.save_date,vd.note,vd.staff_id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ " ,s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password,vd.verify_years,v.date_end,v.date_start\n"
					+ " ,vd.durable_durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ ",d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ ",d.note,d.room_number,r.build,r.room_name,r.floor,vd.picture_verify\n"
					+ "From Verify_Durable vd right join  durable d on vd.durable_durable_code = d.Durable_code right join verify v on vd.verify_years = v.years \n"
					+ " inner join staff s on s.id_staff = vd.staff_id_staff inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username \n"
					+ " left join room r on r.room_number = d.room_number  where durable_durable_code = '" + durablecode + "'; ";
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

				 vd = new VerifyDurable(Save_date, Durable_status, note, picture_verify, pk);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return vd;
	}
	
	public VerifyDurable getverifydurablecurrent(String durablecode,String year) {
		VerifyDurable vd = null;
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "Select vd.durable_status,vd.save_date,vd.note,vd.staff_id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ " ,s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password,vd.verify_years,v.date_end,v.date_start\n"
					+ " ,vd.durable_durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ ",d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ ",d.note,d.room_number,r.build,r.room_name,r.floor,vd.picture_verify\n"
					+ "From Verify_Durable vd right join  durable d on vd.durable_durable_code = d.Durable_code right join verify v on vd.verify_years = v.years \n"
					+ " inner join staff s on s.id_staff = vd.staff_id_staff inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username \n"
					+ " left join room r on r.room_number = d.room_number  where durable_durable_code = '" + durablecode + "' and vd.verify_years = '" + year + "' ; ";
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

				 vd = new VerifyDurable(Save_date, Durable_status, note, picture_verify, pk);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return vd;
	}
	
	
}
