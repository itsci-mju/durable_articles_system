package ac.th.itsci.durable.app.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.Column;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate5.HibernateJdbcException;

import ac.th.itsci.durable.entity.Durable;
import ac.th.itsci.durable.entity.Login;
import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.Room;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.entity.inform_repair;
import ac.th.itsci.durable.entity.verifyinform;
import ac.th.itsci.durable.util.ConnectionDB;
import ac.th.itsci.durable.util.HibernateConnection;

public class InformManager {
	private static String SALT = "123456";

	/*
	 * public String insertinform(inform_repair Inform_Repair) { try {
	 * SessionFactory sessionFactory =
	 * HibernateJdbcException.doHibernateConnection();
	 * 
	 * Session session = sessionFactory.openSession(); Transaction t =
	 * session.beginTransaction();
	 * 
	 * session.saveOrUpdate(Inform_Repair); t.commit(); session.close(); return
	 * "successfully saved"; } catch (Exception e) { return
	 * "failed to save Inform_Repair"; } }
	 */

	public String editinform(inform_repair Inform_Repair) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();

			Session session = sessionFactory.openSession();
			Transaction t = session.beginTransaction();
			session.update(Inform_Repair);
			t.commit();
			session.close();
			return "successfully saved";
		} catch (Exception e) {
			return "failed to save Inform_Repair";
		}
	}public int insertinformsql(inform_repair ir) {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		String pattern = "YYYY-MM-dd HH:mm:ss";
		Calendar c = Calendar.getInstance();
		Date DD = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			//String dateif = sdf.format(ir.getDateinform());
		
			Statement stmt = con.createStatement();
			String sql = "INSERT INTO inform_repair(Informid,Informtype,dateinform,details,picture_inform,id_staff,durable_code)\n"
					+ "                    VALUES('" + ir.getInformid() + "', '" + ir.getInformtype() + "',"
							+ " '" + sdf.format(DD) + "', '" + ir.getDetails() + "', '" + ir.getPicture_inform() + "', "
							+ "'" + ir.getStaff().getId_staff()+ "', '" + ir.getDurable().getDurable_code() + "');";
			int result = stmt.executeUpdate(sql);
			con.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public int editinformsql(inform_repair ir) {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		String pattern = "YYYY-MM-dd HH:mm:ss";
		Calendar c = Calendar.getInstance();
		Date DD = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {		

			Statement stmt = con.createStatement();
			String sql = "update inform_repair set Informid = '" + ir.getInformid()
					+ "', Informtype =  '" + ir.getInformtype() + "', dateinform =  '" + sdf.format(DD)  + "', details =  '"
					+ ir.getDetails() + "', picture_inform =  '" + ir.getPicture_inform() + "', id_staff =  '"
					+ ir.getStaff().getId_staff() + "', durable_code =  '" + ir.getDurable().getDurable_code() + "' where Informid = '" + ir.getInformid()  + "' ";
			int result = stmt.executeUpdate(sql);
			con.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public inform_repair getinform_repair(String durable_code) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			session.beginTransaction();
			inform_repair ir = (inform_repair) session
					.createQuery(
							"Select ir From inform_repair ir where ir.durable.durable_code = '" + durable_code + "'")
					.getSingleResult();
			session.close();

			return ir;

		} catch (Exception e) {
			return null;
		}
	}
	public inform_repair getinform_repair2(String durablecode) {
		inform_repair ir = new inform_repair();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "Select ir.Informid,ir.Informtype,ir.dateinform,ir.details,ir.picture_inform,ir.id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ ",s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password,ir.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ "	,d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ "	,d.note,d.room_number,r.build,r.room_name,r.floor\n"
					+ " from inform_repair ir inner join staff s on ir.id_staff = s.id_staff inner join durable d on ir.durable_code = d.durable_code inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username\n"
					+ " left join room r on r.room_number = d.room_number where ir.durable_code = '" + durablecode + "' order by  ir.Informid  ASC;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String Informid = rs.getString(1);
				String Informtype = rs.getString(2);
				String dateinform = rs.getString(3);
				String details = rs.getString(4);
				String picture_inform = rs.getString(5);
				Calendar date_inform = Calendar.getInstance();
				String idate[] = dateinform.split(" ");
				String idate1[] = idate[0].split("-");
				String iTime[] = idate[1].split(":");
				date_inform.set(Integer.parseInt(idate1[0]), Integer.parseInt(idate1[1]) - 1, Integer.parseInt(idate1[2]),
						Integer.parseInt(iTime[0]), Integer.parseInt(iTime[1]), Integer.parseInt(iTime[2]));
				
				/* เริ่ม staff */
				int staff_id_staff = rs.getInt(6);
				String Id_card = rs.getString(7);
				String Staff_name = rs.getString(8);
				String Staff_lastname = rs.getString(9);
				String Staff_status = rs.getString(10);
				String Email = rs.getString(11);
				String Brithday = rs.getString(12);
				String Phone_number = rs.getString(13);
				String Image_staff = rs.getString(14);
				int idmajor = rs.getInt(15);
				String majorname = rs.getString(16);
				String usernames = rs.getString(17);
				String status = rs.getString(18);
				String password = rs.getString(19);
				Major m = new Major(idmajor, majorname);
				Login l = new Login(usernames, password, status);
				Staff s = new Staff(staff_id_staff, Id_card, Staff_name, Staff_lastname, Staff_status, Email, Brithday,
						Phone_number, Image_staff, m, l);
				/* ปิด staff */
				
				/* เริ่ม durable */
				String durable_code = rs.getString(20);
				String Durable_name = rs.getString(21);
				String Durable_number = rs.getString(22);
				String Durable_brandname = rs.getString(23);
				String Durable_model = rs.getString(24);
				String Durable_price = rs.getString(25);
				String Durable_statusnow = rs.getString(26);
				String Responsible_person = rs.getString(27);
				String Durable_image = rs.getString(28);
				String Durable_Borrow_Status = rs.getString(29);
				String Durable_entrancedate = rs.getString(30);
				String durablenote = rs.getString(31);
				String Room_number = rs.getString(32);
				String Room_name = rs.getString(33);
				String Build = rs.getString(34);
				String floor = rs.getString(35);

				Room r = new Room(Room_number, Room_name, Build, floor, m);

				Durable d = new Durable(durable_code, Durable_name, Durable_number, Durable_brandname, Durable_model,
						Durable_price, Durable_statusnow, Responsible_person, Durable_image, Durable_Borrow_Status,
						Durable_entrancedate, durablenote, m, r);
				/* ปิด durable */
			
				 ir = new inform_repair(Informid,Informtype,date_inform,details,picture_inform,s,d);

			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ir;
	}


	public inform_repair getinform_repairbyid(String Informid) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			session.beginTransaction();
			inform_repair ir = (inform_repair) session
					.createQuery("Select ir From inform_repair ir where Informid = '" + Informid + "'")
					.getSingleResult();
			session.close();

			return ir;

		} catch (Exception e) {
			return null;
		}
	}
	public inform_repair getinform_repairbyid2(String Inform_id) {
		inform_repair ir = new inform_repair();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "Select ir.Informid,ir.Informtype,ir.dateinform,ir.details,ir.picture_inform,ir.id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ ",s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password,ir.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ "	,d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ "	,d.note,d.room_number,r.build,r.room_name,r.floor\n"
					+ " from inform_repair ir inner join staff s on ir.id_staff = s.id_staff inner join durable d on ir.durable_code = d.durable_code inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username\n"
					+ " left join room r on r.room_number = d.room_number where ir.Informid = '" + Inform_id + "';";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String Informid = rs.getString(1);
				String Informtype = rs.getString(2);
				String dateinform = rs.getString(3);
				String details = rs.getString(4);
				String picture_inform = rs.getString(5);
				Calendar date_inform = Calendar.getInstance();
				String idate[] = dateinform.split(" ");
				String idate1[] = idate[0].split("-");
				String iTime[] = idate[1].split(":");
				date_inform.set(Integer.parseInt(idate1[0]), Integer.parseInt(idate1[1]) - 1, Integer.parseInt(idate1[2]),
						Integer.parseInt(iTime[0]), Integer.parseInt(iTime[1]), Integer.parseInt(iTime[2]));
				
				/* เริ่ม staff */
				int staff_id_staff = rs.getInt(6);
				String Id_card = rs.getString(7);
				String Staff_name = rs.getString(8);
				String Staff_lastname = rs.getString(9);
				String Staff_status = rs.getString(10);
				String Email = rs.getString(11);
				String Brithday = rs.getString(12);
				String Phone_number = rs.getString(13);
				String Image_staff = rs.getString(14);
				int idmajor = rs.getInt(15);
				String majorname = rs.getString(16);
				String usernames = rs.getString(17);
				String status = rs.getString(18);
				String password = rs.getString(19);
				Major m = new Major(idmajor, majorname);
				Login l = new Login(usernames, password, status);
				Staff s = new Staff(staff_id_staff, Id_card, Staff_name, Staff_lastname, Staff_status, Email, Brithday,
						Phone_number, Image_staff, m, l);
				/* ปิด staff */
				
				/* เริ่ม durable */
				String durable_code = rs.getString(20);
				String Durable_name = rs.getString(21);
				String Durable_number = rs.getString(22);
				String Durable_brandname = rs.getString(23);
				String Durable_model = rs.getString(24);
				String Durable_price = rs.getString(25);
				String Durable_statusnow = rs.getString(26);
				String Responsible_person = rs.getString(27);
				String Durable_image = rs.getString(28);
				String Durable_Borrow_Status = rs.getString(29);
				String Durable_entrancedate = rs.getString(30);
				String durablenote = rs.getString(31);
				String Room_number = rs.getString(32);
				String Room_name = rs.getString(33);
				String Build = rs.getString(34);
				String floor = rs.getString(35);

				Room r = new Room(Room_number, Room_name, Build, floor, m);

				Durable d = new Durable(durable_code, Durable_name, Durable_number, Durable_brandname, Durable_model,
						Durable_price, Durable_statusnow, Responsible_person, Durable_image, Durable_Borrow_Status,
						Durable_entrancedate, durablenote, m, r);
				/* ปิด durable */
			
				 ir = new inform_repair(Informid,Informtype,date_inform,details,picture_inform,s,d);

			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ir;
	}

	public int getMaxinformID() {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		int id = 0;
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT Max(Informid) from inform_repair;";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				id = rs.getInt(1);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id + 1;
	}

	public Staff getstaff(int staffid) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();

			session.beginTransaction();
			Staff staff = (Staff) session.createQuery("From Staff where id_staff = " + staffid).getSingleResult();
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

	public List<inform_repair> list_informrepairbymajor(String majorid) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			session.beginTransaction();
			List<inform_repair> durable = session.createQuery(
					"Select ir from inform_repair ir join staff s on ir.staff.id_staff = s.id_staff where s.major.id_major = "
							+ majorid + " ")
					.list();
			session.close();

			return durable;

		} catch (Exception e) {
			return null;
		}
	}

	public List<inform_repair> list_informrepairbymajor2(String majorid) {
		List<inform_repair> list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "Select ir.Informid,ir.Informtype,ir.dateinform,ir.details,ir.picture_inform,ir.id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ ",s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password,ir.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ "	,d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ "	,d.note,d.room_number,r.build,r.room_name,r.floor\n"
					+ " from inform_repair ir inner join staff s on ir.id_staff = s.id_staff inner join durable d on ir.durable_code = d.durable_code inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username\n"
					+ " left join room r on r.room_number = d.room_number where s.id_major = '" + majorid + "'and  ir.Informid NOT IN (SELECT vi.informid FROM verifyinform vi);";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String Informid = rs.getString(1);
				String Informtype = rs.getString(2);
				String dateinform = rs.getString(3);
				String details = rs.getString(4);
				String picture_inform = rs.getString(5);
				Calendar date_inform = Calendar.getInstance();
				String idate[] = dateinform.split(" ");
				String idate1[] = idate[0].split("-");
				String iTime[] = idate[1].split(":");
				date_inform.set(Integer.parseInt(idate1[0]), Integer.parseInt(idate1[1]) - 1, Integer.parseInt(idate1[2]),
						Integer.parseInt(iTime[0]), Integer.parseInt(iTime[1]), Integer.parseInt(iTime[2]));
				
				/* เริ่ม staff */
				int staff_id_staff = rs.getInt(6);
				String Id_card = rs.getString(7);
				String Staff_name = rs.getString(8);
				String Staff_lastname = rs.getString(9);
				String Staff_status = rs.getString(10);
				String Email = rs.getString(11);
				String Brithday = rs.getString(12);
				String Phone_number = rs.getString(13);
				String Image_staff = rs.getString(14);
				int idmajor = rs.getInt(15);
				String majorname = rs.getString(16);
				String usernames = rs.getString(17);
				String status = rs.getString(18);
				String password = rs.getString(19);
				Major m = new Major(idmajor, majorname);
				Login l = new Login(usernames, password, status);
				Staff s = new Staff(staff_id_staff, Id_card, Staff_name, Staff_lastname, Staff_status, Email, Brithday,
						Phone_number, Image_staff, m, l);
				/* ปิด staff */
				
				/* เริ่ม durable */
				String durable_code = rs.getString(20);
				String Durable_name = rs.getString(21);
				String Durable_number = rs.getString(22);
				String Durable_brandname = rs.getString(23);
				String Durable_model = rs.getString(24);
				String Durable_price = rs.getString(25);
				String Durable_statusnow = rs.getString(26);
				String Responsible_person = rs.getString(27);
				String Durable_image = rs.getString(28);
				String Durable_Borrow_Status = rs.getString(29);
				String Durable_entrancedate = rs.getString(30);
				String durablenote = rs.getString(31);
				String Room_number = rs.getString(32);
				String Room_name = rs.getString(33);
				String Build = rs.getString(34);
				String floor = rs.getString(35);

				Room r = new Room(Room_number, Room_name, Build, floor, m);

				Durable d = new Durable(durable_code, Durable_name, Durable_number, Durable_brandname, Durable_model,
						Durable_price, Durable_statusnow, Responsible_person, Durable_image, Durable_Borrow_Status,
						Durable_entrancedate, durablenote, m, r);
				/* ปิด durable */
			
				inform_repair ir = new inform_repair(Informid,Informtype,date_inform,details,picture_inform,s,d);
				list.add(ir);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	public List<verifyinform> list_verifyinform(String majorid) {
		List<verifyinform> list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT vi.verify_id,vi.verify_date,vi.verify_detail,vi.verify_status,vi.informid,ir.Informtype,ir.dateinform,ir.details,ir.picture_inform,ir.id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ ",s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password,ir.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ ",d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ ",d.note,d.room_number,r.build,r.room_name,r.floor FROM verifyinform vi left join inform_repair ir on  vi.informid =ir.Informid inner join staff s on ir.id_staff = s.id_staff inner join durable d on ir.durable_code = d.durable_code inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username\n"
					+ "left join room r on r.room_number = d.room_number where s.id_major =  '" + majorid + "'and (vi.verify_status ='ส่งซ่อม' or vi.verify_status = 'ซ่อมเอง') AND vi.verify_id Not IN (SELECT rd.verify_id FROM repair_durable rd  where vi.verify_id = rd.verify_id);";
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

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<verifyinform> list_verifyinformnotinmaintenance(String majorid) {
		List<verifyinform> list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT vi.verify_id,vi.verify_date,vi.verify_detail,vi.verify_status,vi.informid,ir.Informtype,ir.dateinform,ir.details,ir.picture_inform,ir.id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ ",s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password,ir.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ ",d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ ",d.note,d.room_number,r.build,r.room_name,r.floor FROM verifyinform vi left join inform_repair ir on  vi.informid =ir.Informid inner join staff s on ir.id_staff = s.id_staff inner join durable d on ir.durable_code = d.durable_code inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username\n"
					+ "left join room r on r.room_number = d.room_number where s.id_major =  '" + majorid + "'and (vi.verify_status ='ดี' or vi.verify_status = 'แทงจำหน่าย' or vi.verify_status = 'ชำรุด') ;";
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

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<inform_repair> listAll_informrepair() {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			session.beginTransaction();
			List<inform_repair> durable = session
					.createQuery("Select ir from inform_repair ir join staff s on ir.staff.id_staff = s.id_staff ")
					.list();
			session.close();

			return durable;

		} catch (Exception e) {
			return null;
		}
	}

	public List<inform_repair> listinformrepairNOTIN(String majorname) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			session.beginTransaction();
			List<inform_repair> durable = session.createQuery(
					"Select ir from inform_repair ir join staff s on ir.staff.id_staff = s.id_staff join major m on s.major.id_major = m.id_major where ir.Informid NOT IN\n"
							+ "(SELECT v.inform_repair.Informid FROM verifyInform v ) and m.major_name = '" + majorname
							+ "' ")
					.list();
			session.close();

			return durable;

		} catch (Exception e) {
			return null;
		}
	}
	public List<inform_repair> listinformrepairNOTIN2(String major_name) {
		List<inform_repair> list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "Select ir.Informid,ir.Informtype,ir.dateinform,ir.details,ir.picture_inform,ir.id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ ",s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password,ir.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ "	,d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ "	,d.note,d.room_number,r.build,r.room_name,r.floor\n"
					+ " from inform_repair ir inner join staff s on ir.id_staff = s.id_staff inner join durable d on ir.durable_code = d.durable_code inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username\n"
					+ " left join room r on r.room_number = d.room_number where m.major_name = '" + major_name + "'and  ir.Informid NOT IN (SELECT vi.informid FROM verifyinform vi);";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String Informid = rs.getString(1);
				String Informtype = rs.getString(2);
				String dateinform = rs.getString(3);
				String details = rs.getString(4);
				String picture_inform = rs.getString(5);
				Calendar date_inform = Calendar.getInstance();
				String idate[] = dateinform.split(" ");
				String idate1[] = idate[0].split("-");
				String iTime[] = idate[1].split(":");
				date_inform.set(Integer.parseInt(idate1[0]), Integer.parseInt(idate1[1]) - 1, Integer.parseInt(idate1[2]),
						Integer.parseInt(iTime[0]), Integer.parseInt(iTime[1]), Integer.parseInt(iTime[2]));
				
				/* เริ่ม staff */
				int staff_id_staff = rs.getInt(6);
				String Id_card = rs.getString(7);
				String Staff_name = rs.getString(8);
				String Staff_lastname = rs.getString(9);
				String Staff_status = rs.getString(10);
				String Email = rs.getString(11);
				String Brithday = rs.getString(12);
				String Phone_number = rs.getString(13);
				String Image_staff = rs.getString(14);
				int idmajor = rs.getInt(15);
				String majorname = rs.getString(16);
				String usernames = rs.getString(17);
				String status = rs.getString(18);
				String password = rs.getString(19);
				Major m = new Major(idmajor, majorname);
				Login l = new Login(usernames, password, status);
				Staff s = new Staff(staff_id_staff, Id_card, Staff_name, Staff_lastname, Staff_status, Email, Brithday,
						Phone_number, Image_staff, m, l);
				/* ปิด staff */
				
				/* เริ่ม durable */
				String durable_code = rs.getString(20);
				String Durable_name = rs.getString(21);
				String Durable_number = rs.getString(22);
				String Durable_brandname = rs.getString(23);
				String Durable_model = rs.getString(24);
				String Durable_price = rs.getString(25);
				String Durable_statusnow = rs.getString(26);
				String Responsible_person = rs.getString(27);
				String Durable_image = rs.getString(28);
				String Durable_Borrow_Status = rs.getString(29);
				String Durable_entrancedate = rs.getString(30);
				String durablenote = rs.getString(31);
				String Room_number = rs.getString(32);
				String Room_name = rs.getString(33);
				String Build = rs.getString(34);
				String floor = rs.getString(35);

				Room r = new Room(Room_number, Room_name, Build, floor, m);

				Durable d = new Durable(durable_code, Durable_name, Durable_number, Durable_brandname, Durable_model,
						Durable_price, Durable_statusnow, Responsible_person, Durable_image, Durable_Borrow_Status,
						Durable_entrancedate, durablenote, m, r);
				/* ปิด durable */
			
				inform_repair ir = new inform_repair(Informid,Informtype,date_inform,details,picture_inform,s,d);
				list.add(ir);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<inform_repair> listinformrepairIN(String majorname) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
			Session session = sessionFactory.openSession();
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			session.beginTransaction();
			List<inform_repair> durable = session.createQuery(
					"Select ir from inform_repair ir join staff s on ir.staff.id_staff = s.id_staff join major m on s.major.id_major = m.id_major where ir.Informid  IN\n"
							+ "(SELECT v.inform_repair.Informid FROM verifyInform v )  and m.major_name = '" + majorname
							+ "' ")
					.list();
			session.close();

			return durable;

		} catch (Exception e) {
			return null;
		}
	}
	public List<verifyinform> listinformrepairIN2(String major_name) {
		List<verifyinform> list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT vi.verify_id,vi.verify_date,vi.verify_detail,vi.verify_status,vi.informid,ir.Informtype,ir.dateinform,ir.details,ir.picture_inform,ir.id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ ",s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password,ir.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ ",d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ ",d.note,d.room_number,r.build,r.room_name,r.floor FROM verifyinform vi left join inform_repair ir on  vi.informid =ir.Informid inner join staff s on ir.id_staff = s.id_staff inner join durable d on ir.durable_code = d.durable_code inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username\n"
					+ "left join room r on r.room_number = d.room_number where m.major_name = '" + major_name + "'and (vi.verify_status ='ส่งซ่อม' or vi.verify_status = 'ซ่อมเอง') AND vi.verify_id Not IN (SELECT rd.verify_id FROM repair_durable rd  where vi.verify_id = rd.verify_id) ;";
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

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	public List<verifyinform> listinformrepairINnotmaintenance(String major_name) {
		List<verifyinform> list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT vi.verify_id,vi.verify_date,vi.verify_detail,vi.verify_status,vi.informid,ir.Informtype,ir.dateinform,ir.details,ir.picture_inform,ir.id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ ",s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password,ir.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ ",d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ ",d.note,d.room_number,r.build,r.room_name,r.floor FROM verifyinform vi left join inform_repair ir on  vi.informid =ir.Informid inner join staff s on ir.id_staff = s.id_staff inner join durable d on ir.durable_code = d.durable_code inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username\n"
					+ "left join room r on r.room_number = d.room_number where m.major_name = '" + major_name + "' and (vi.verify_status ='ดี' or vi.verify_status = 'แทงจำหน่าย' or vi.verify_status = 'ชำรุด');";
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

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	public List<inform_repair> listinformrepairIN3(String major_name) {
		List<inform_repair> list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "Select ir.Informid,ir.Informtype,ir.dateinform,ir.details,ir.picture_inform,ir.id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ ",s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password,ir.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ "	,d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ "	,d.note,d.room_number,r.build,r.room_name,r.floor\n"
					+ " from inform_repair ir inner join staff s on ir.id_staff = s.id_staff inner join durable d on ir.durable_code = d.durable_code inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username\n"
					+ " left join room r on r.room_number = d.room_number where m.major_name = '" + major_name + "'and  ir.Informid  IN (SELECT vi.informid FROM verifyinform vi);";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String Informid = rs.getString(1);
				String Informtype = rs.getString(2);
				String dateinform = rs.getString(3);
				String details = rs.getString(4);
				String picture_inform = rs.getString(5);
				Calendar date_inform = Calendar.getInstance();
				String idate[] = dateinform.split(" ");
				String idate1[] = idate[0].split("-");
				String iTime[] = idate[1].split(":");
				date_inform.set(Integer.parseInt(idate1[0]), Integer.parseInt(idate1[1]) - 1, Integer.parseInt(idate1[2]),
						Integer.parseInt(iTime[0]), Integer.parseInt(iTime[1]), Integer.parseInt(iTime[2]));
				
				/* เริ่ม staff */
				int staff_id_staff = rs.getInt(6);
				String Id_card = rs.getString(7);
				String Staff_name = rs.getString(8);
				String Staff_lastname = rs.getString(9);
				String Staff_status = rs.getString(10);
				String Email = rs.getString(11);
				String Brithday = rs.getString(12);
				String Phone_number = rs.getString(13);
				String Image_staff = rs.getString(14);
				int idmajor = rs.getInt(15);
				String majorname = rs.getString(16);
				String usernames = rs.getString(17);
				String status = rs.getString(18);
				String password = rs.getString(19);
				Major m = new Major(idmajor, majorname);
				Login l = new Login(usernames, password, status);
				Staff s = new Staff(staff_id_staff, Id_card, Staff_name, Staff_lastname, Staff_status, Email, Brithday,
						Phone_number, Image_staff, m, l);
				/* ปิด staff */
				
				/* เริ่ม durable */
				String durable_code = rs.getString(20);
				String Durable_name = rs.getString(21);
				String Durable_number = rs.getString(22);
				String Durable_brandname = rs.getString(23);
				String Durable_model = rs.getString(24);
				String Durable_price = rs.getString(25);
				String Durable_statusnow = rs.getString(26);
				String Responsible_person = rs.getString(27);
				String Durable_image = rs.getString(28);
				String Durable_Borrow_Status = rs.getString(29);
				String Durable_entrancedate = rs.getString(30);
				String durablenote = rs.getString(31);
				String Room_number = rs.getString(32);
				String Room_name = rs.getString(33);
				String Build = rs.getString(34);
				String floor = rs.getString(35);

				Room r = new Room(Room_number, Room_name, Build, floor, m);

				Durable d = new Durable(durable_code, Durable_name, Durable_number, Durable_brandname, Durable_model,
						Durable_price, Durable_statusnow, Responsible_person, Durable_image, Durable_Borrow_Status,
						Durable_entrancedate, durablenote, m, r);
				/* ปิด durable */
			
				inform_repair ir = new inform_repair(Informid,Informtype,date_inform,details,picture_inform,s,d);
				list.add(ir);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public Durable getdurable_in_informrepair(String durablecode) {
		Durable d = null;
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "select d.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ ",d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate,d.note,d.id_major,m.major_name,d.room_number,r.build,r.room_name,r.floor \n"
					+ "from durable d inner join major m on d.id_major = m.id_major  left join room r on r.room_number = d.room_number\n"
					+ " where d.durable_code  in(SELECT ir.durable_code FROM inform_repair ir  where ir.Informid NOT IN (SELECT vi.informid FROM verifyinform vi) and ir.durable_code = '" + durablecode + "');";
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

				 d = new Durable(durable_code, Durable_name, Durable_number, Durable_brandname, Durable_model,
						Durable_price, Durable_statusnow, Responsible_person, Durable_image, Durable_Borrow_Status,
						Durable_entrancedate, durablenote, m, r);
				/* ปิด durable */

			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return d;
	}
	
	public Durable getdurable_in_informrepairandnotrepair(String durablecode) {
		Durable d = null;
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "select d.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ ",d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate,d.note,d.id_major,m.major_name,d.room_number,r.build,r.room_name,r.floor \n"
					+ "from durable d inner join major m on d.id_major = m.id_major  left join room r on r.room_number = d.room_number\n"
					+ " where d.durable_code  in(SELECT ir.durable_code FROM inform_repair ir  where  ir.Informid  IN (SELECT vi.informid FROM verifyinform vi where ir.Informid = vi.informid and vi.verify_id Not IN (SELECT rd.verify_id FROM repair_durable rd  where vi.verify_id = rd.verify_id)) and ir.durable_code =   '" + durablecode + "');";
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

				 d = new Durable(durable_code, Durable_name, Durable_number, Durable_brandname, Durable_model,
						Durable_price, Durable_statusnow, Responsible_person, Durable_image, Durable_Borrow_Status,
						Durable_entrancedate, durablenote, m, r);
				/* ปิด durable */

			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return d;
	}

}
