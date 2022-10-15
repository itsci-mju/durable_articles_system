package ac.th.itsci.durable.app.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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
import ac.th.itsci.durable.entity.VerifyDurable;
import ac.th.itsci.durable.entity.inform_repair;
import ac.th.itsci.durable.entity.verifyinform;
import ac.th.itsci.durable.util.ConnectionDB;
import ac.th.itsci.durable.util.HibernateConnection;




public class verifyInformManager {
	private static String SALT = "123456";
	
	
	public String insertverifyinform(verifyinform verifyinform) {
		try {
			SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();

			Session session = sessionFactory.openSession();
			Transaction t = session.beginTransaction();

			session.saveOrUpdate(verifyinform);
			t.commit();
			session.close();
			return "successfully saved";
		} catch (Exception e) {
			return "failed to save Inform_Repair";
		}
	}
	
	public int insertverifyinform2(verifyinform vi) {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		String pattern = "YYYY-MM-dd HH:mm:ss";
		Calendar c = Calendar.getInstance();
		Date DD = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		try {
			Statement stmt = con.createStatement();
			String sql = "INSERT INTO verifyinform (verify_id,verify_date,verify_detail,verify_status,informid)"
					+ " VALUES ('" + vi.getVerify_id() + "', '" + sdf.format(DD) + "', '" + vi.getVerify_detail() + "', '"
					+ vi.getVerify_status() + "', '" + vi.getInform_repair().getInformid() + "');";
			int result = stmt.executeUpdate(sql);

			con.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}
	public int updateverifyinform(String verifydetail,String verifystatus,String verifyid) {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "UPDATE verifyinform\n"
					+ "SET verify_detail = '"+verifydetail+"',\n"
					+ "verify_status = '"+verifystatus+"'\n"
					+ "WHERE verify_id = '"+verifyid+"';";
			int result = stmt.executeUpdate(sql);

			con.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}
	
	public int getMaxverifyinformID() {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		int id = 0;
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT Max(verify_id) from verifyinform;";
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
	
	public verifyinform getverifyinformbyid(String verify_id) {
		verifyinform vi = new verifyinform();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT vi.verify_id,vi.verify_date,vi.verify_detail,vi.verify_status,vi.informid,ir.Informtype,ir.dateinform,ir.details,ir.picture_inform,ir.id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ ",s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password,ir.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ ",d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ ",d.note,d.room_number,r.build,r.room_name,r.floor FROM verifyinform vi left join inform_repair ir on  vi.informid =ir.Informid inner join staff s on ir.id_staff = s.id_staff inner join durable d on ir.durable_code = d.durable_code inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username\n"
					+ "left join room r on r.room_number = d.room_number where vi.verify_id = '"+verify_id+"';";
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
				
				 vi = new verifyinform(verifyid,date_verify,verifystatus,verifydetail,ir);
			}
			// System.out.println(sql);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return vi;
	}
	
	public verifyinform getverifyinformbyid2(String informid) {
		verifyinform vi = null;
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT vi.verify_id,vi.verify_date,vi.verify_detail,vi.verify_status,vi.informid,ir.Informtype,ir.dateinform,ir.details,ir.picture_inform,ir.id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ ",s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password,ir.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ ",d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ ",d.note,d.room_number,r.build,r.room_name,r.floor FROM verifyinform vi left join inform_repair ir on  vi.informid =ir.Informid inner join staff s on ir.id_staff = s.id_staff inner join durable d on ir.durable_code = d.durable_code inner join major m on s.id_major = m.id_major inner join login l on s.username = l.username\n"
					+ "left join room r on r.room_number = d.room_number where vi.informid = '"+informid+"';";
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
				
				 vi = new verifyinform(verifyid,date_verify,verifystatus,verifydetail,ir);
			}
			// System.out.println(sql);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return vi;
	}
	
	
}
