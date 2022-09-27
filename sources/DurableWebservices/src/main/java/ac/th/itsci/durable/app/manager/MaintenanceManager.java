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

import ac.th.itsci.durable.entity.Company;
import ac.th.itsci.durable.entity.Durable;
import ac.th.itsci.durable.entity.Login;
import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.RepairDurable;
import ac.th.itsci.durable.entity.Room;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.entity.VerifyDurable;
import ac.th.itsci.durable.entity.inform_repair;
import ac.th.itsci.durable.entity.verifyinform;
import ac.th.itsci.durable.util.ConnectionDB;
import ac.th.itsci.durable.util.HibernateConnection;



public class MaintenanceManager {
	private static String SALT = "123456";

	public int insertRepairDurable(RepairDurable r) {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		try {
			Statement stmt = con.createStatement();
			String sql = "INSERT INTO repair_durable(repair_id,date_of_repair,repair_status,picture_invoice,picture_quatation,picture_repair,picture_repairreport,repair_charges,repair_date,\n"
					+ "repair_detail,repair_title,company_id,durable_code,verify_id)"
					+ " VALUES ('" +r.getRepair_id() + "', '" + r.getDate_of_repair() + "', '" + r.getRepair_status() + "', '"
					+r.getPicture_invoice()  + "', '" + r.getPicture_quatation() +"', '" + r.getPicture_repair() +"', '" + r.getPicture_repairreport() +
					"', '" + r.getRepair_charges() + "', '" + r.getRepair_date() + "', '" + r.getRepair_detail() + "', '" + r.getRepair_title() +
					"', '" + r.getCompany().getCompany_id() + "', '" + r.getDurable().getDurable_code() + "', '" + r.getVerifyinform().getVerify_id() +"');";
			int result = stmt.executeUpdate(sql);

			con.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public int getMaxrepairdurableID() {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		int id = 0;
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT Max(repair_id) from repair_durable;";
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
	
	public List<RepairDurable> listRepairComplete(String major_name) {
		List<RepairDurable> list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT rd.repair_id,rd.date_of_repair,rd.repair_status,rd.picture_invoice,rd.picture_quatation,"
					+ "rd.picture_repair,rd.picture_repairreport,rd.repair_charges,rd.repair_date,rd.repair_detail,rd.repair_title,rd.company_id"
					+ ",c.companyname,c.address,c.tell,d.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price"
					+ ",d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ ",d.note,d.room_number,r.build,r.room_name,r.floor,vi.verify_id,vi.verify_date,vi.verify_detail,vi.verify_status,vi.informid,ir.Informtype"
					+ ",ir.dateinform,ir.details,ir.picture_inform,ir.id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday"
					+ ",s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password"
					+ " FROM repair_durable rd inner join company c on rd.company_id = c.company_id "
					+ " inner join durable d on rd.durable_code = d.durable_code "
					+ " left join room r on r.room_number = d.room_number"
					+ " left join verifyinform vi on rd.verify_id = vi.verify_id left join inform_repair ir on  vi.informid =ir.Informid "
					+ " inner join staff s on ir.id_staff = s.id_staff inner join login l on s.username = l.username inner join major m on s.id_major = m.id_major"
					+ " where m.major_name = '"+major_name+"';";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int repairid  = rs.getInt(1);
				String Date_of_repair = rs.getString(2);
				String Repair_status = rs.getString(3);
				String picture_invoice = rs.getString(4);
				String picture_quatation = rs.getString(5);
				String picture_repair = rs.getString(6);
				String picture_repairreport = rs.getString(7);
				String repair_charges = rs.getString(8);
				String repair_date = rs.getString(9);
				String repair_detail = rs.getString(10);
				String repair_title = rs.getString(11);
				
				int company_id = rs.getInt(12);
				String companyname = rs.getString(13);
				String address = rs.getString(14);
				String tell = rs.getString(15);
				
				Company company = new Company(company_id,companyname,address,tell);
				
				/* เริ่ม durable */
				String durable_code = rs.getString(16);
				String Durable_name = rs.getString(17);
				String Durable_number = rs.getString(18);
				String Durable_brandname = rs.getString(19);
				String Durable_model = rs.getString(20);
				String Durable_price = rs.getString(21);
				String Durable_statusnow = rs.getString(22);
				String Responsible_person = rs.getString(23);
				String Durable_image = rs.getString(24);
				String Durable_Borrow_Status = rs.getString(25);
				String Durable_entrancedate = rs.getString(26);
				String durablenote = rs.getString(27);
				String Room_number = rs.getString(28);
				String Room_name = rs.getString(29);
				String Build = rs.getString(30);
				String floor = rs.getString(31);

			
				/* ปิด durable */
				
				int verifyid = rs.getInt(32);
				String verifydate = rs.getString(33);
				String verifydetail = rs.getString(34);
				String verifystatus = rs.getString(35);
				
				Calendar date_verify = Calendar.getInstance();
				String vdate[] = verifydate.split(" ");
				String vdate1[] = vdate[0].split("-");
				String vTime[] = vdate[1].split(":");
				date_verify.set(Integer.parseInt(vdate1[0]), Integer.parseInt(vdate1[1]) - 1, Integer.parseInt(vdate1[2]),
						Integer.parseInt(vTime[0]), Integer.parseInt(vTime[1]), Integer.parseInt(vTime[2]));

				String Informid = rs.getString(36);
				String Informtype = rs.getString(37);
				String dateinform = rs.getString(38);
				String details = rs.getString(39);
				String picture_inform = rs.getString(40);
				Calendar date_inform = Calendar.getInstance();
				String idate[] = dateinform.split(" ");
				String idate1[] = idate[0].split("-");
				String iTime[] = idate[1].split(":");
				date_inform.set(Integer.parseInt(idate1[0]), Integer.parseInt(idate1[1]) - 1, Integer.parseInt(idate1[2]),
						Integer.parseInt(iTime[0]), Integer.parseInt(iTime[1]), Integer.parseInt(iTime[2]));
				
				/* เริ่ม staff */
				int staff_id_staff = rs.getInt(41);
				String Id_card = rs.getString(42);
				String Staff_name = rs.getString(43);
				String Staff_lastname = rs.getString(44);
				String Staff_status = rs.getString(45);
				String Email = rs.getString(46);
				String Brithday = rs.getString(47);
				String Phone_number = rs.getString(48);
				String Image_staff = rs.getString(49);
				int idmajor = rs.getInt(50);
				String majorname = rs.getString(51);
				String usernames = rs.getString(52);
				String status = rs.getString(53);
				String password = rs.getString(54);
				Major m = new Major(idmajor, majorname);
				Login l = new Login(usernames, password, status);
				Staff s = new Staff(staff_id_staff, Id_card, Staff_name, Staff_lastname, Staff_status, Email, Brithday,
						Phone_number, Image_staff, m, l);
				/* ปิด staff */
				
				Room r = new Room(Room_number, Room_name, Build, floor, m);

				Durable d = new Durable(durable_code, Durable_name, Durable_number, Durable_brandname, Durable_model,
						Durable_price, Durable_statusnow, Responsible_person, Durable_image, Durable_Borrow_Status,
						Durable_entrancedate, durablenote, m, r);
			
				inform_repair ir = new inform_repair(Informid,Informtype,date_inform,details,picture_inform,s,d);
				
				verifyinform vi = new verifyinform(verifyid,date_verify,verifystatus,verifydetail,ir);

				
				RepairDurable rd = new RepairDurable(repairid,repair_date,repair_title,repair_charges,repair_detail
						,picture_invoice,picture_repairreport,picture_quatation,picture_repair
						,Date_of_repair,Repair_status,d,company,vi);
				list.add(rd);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public RepairDurable getRepairdurable(String durablecode,String _verifyid) {
		RepairDurable rd = null;
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT rd.repair_id,rd.date_of_repair,rd.repair_status,rd.picture_invoice,rd.picture_quatation,"
					+ "rd.picture_repair,rd.picture_repairreport,rd.repair_charges,rd.repair_date,rd.repair_detail,rd.repair_title,rd.company_id"
					+ ",c.companyname,c.address,c.tell,d.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price"
					+ ",d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ ",d.note,d.room_number,r.build,r.room_name,r.floor,vi.verify_id,vi.verify_date,vi.verify_detail,vi.verify_status,vi.informid,ir.Informtype"
					+ ",ir.dateinform,ir.details,ir.picture_inform,ir.id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday"
					+ ",s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password"
					+ " FROM repair_durable rd inner join company c on rd.company_id = c.company_id "
					+ " inner join durable d on rd.durable_code = d.durable_code "
					+ " left join room r on r.room_number = d.room_number"
					+ " left join verifyinform vi on rd.verify_id = vi.verify_id left join inform_repair ir on  vi.informid =ir.Informid "
					+ " inner join staff s on ir.id_staff = s.id_staff inner join login l on s.username = l.username inner join major m on s.id_major = m.id_major"
					+ " where rd.durable_code = '"+durablecode+"' and rd.verify_id = '"+_verifyid+"' ;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int repairid  = rs.getInt(1);
				String Date_of_repair = rs.getString(2);
				String Repair_status = rs.getString(3);
				String picture_invoice = rs.getString(4);
				String picture_quatation = rs.getString(5);
				String picture_repair = rs.getString(6);
				String picture_repairreport = rs.getString(7);
				String repair_charges = rs.getString(8);
				String repair_date = rs.getString(9);
				String repair_detail = rs.getString(10);
				String repair_title = rs.getString(11);
				
				int company_id = rs.getInt(12);
				String companyname = rs.getString(13);
				String address = rs.getString(14);
				String tell = rs.getString(15);
				
				Company company = new Company(company_id,companyname,address,tell);
				
				/* เริ่ม durable */
				String durable_code = rs.getString(16);
				String Durable_name = rs.getString(17);
				String Durable_number = rs.getString(18);
				String Durable_brandname = rs.getString(19);
				String Durable_model = rs.getString(20);
				String Durable_price = rs.getString(21);
				String Durable_statusnow = rs.getString(22);
				String Responsible_person = rs.getString(23);
				String Durable_image = rs.getString(24);
				String Durable_Borrow_Status = rs.getString(25);
				String Durable_entrancedate = rs.getString(26);
				String durablenote = rs.getString(27);
				String Room_number = rs.getString(28);
				String Room_name = rs.getString(29);
				String Build = rs.getString(30);
				String floor = rs.getString(31);

			
				/* ปิด durable */
				
				int verifyid = rs.getInt(32);
				String verifydate = rs.getString(33);
				String verifydetail = rs.getString(34);
				String verifystatus = rs.getString(35);
				
				Calendar date_verify = Calendar.getInstance();
				String vdate[] = verifydate.split(" ");
				String vdate1[] = vdate[0].split("-");
				String vTime[] = vdate[1].split(":");
				date_verify.set(Integer.parseInt(vdate1[0]), Integer.parseInt(vdate1[1]) - 1, Integer.parseInt(vdate1[2]),
						Integer.parseInt(vTime[0]), Integer.parseInt(vTime[1]), Integer.parseInt(vTime[2]));

				String Informid = rs.getString(36);
				String Informtype = rs.getString(37);
				String dateinform = rs.getString(38);
				String details = rs.getString(39);
				String picture_inform = rs.getString(40);
				Calendar date_inform = Calendar.getInstance();
				String idate[] = dateinform.split(" ");
				String idate1[] = idate[0].split("-");
				String iTime[] = idate[1].split(":");
				date_inform.set(Integer.parseInt(idate1[0]), Integer.parseInt(idate1[1]) - 1, Integer.parseInt(idate1[2]),
						Integer.parseInt(iTime[0]), Integer.parseInt(iTime[1]), Integer.parseInt(iTime[2]));
				
				/* เริ่ม staff */
				int staff_id_staff = rs.getInt(41);
				String Id_card = rs.getString(42);
				String Staff_name = rs.getString(43);
				String Staff_lastname = rs.getString(44);
				String Staff_status = rs.getString(45);
				String Email = rs.getString(46);
				String Brithday = rs.getString(47);
				String Phone_number = rs.getString(48);
				String Image_staff = rs.getString(49);
				int idmajor = rs.getInt(50);
				String majorname = rs.getString(51);
				String usernames = rs.getString(52);
				String status = rs.getString(53);
				String password = rs.getString(54);
				Major m = new Major(idmajor, majorname);
				Login l = new Login(usernames, password, status);
				Staff s = new Staff(staff_id_staff, Id_card, Staff_name, Staff_lastname, Staff_status, Email, Brithday,
						Phone_number, Image_staff, m, l);
				/* ปิด staff */
				
				Room r = new Room(Room_number, Room_name, Build, floor, m);

				Durable d = new Durable(durable_code, Durable_name, Durable_number, Durable_brandname, Durable_model,
						Durable_price, Durable_statusnow, Responsible_person, Durable_image, Durable_Borrow_Status,
						Durable_entrancedate, durablenote, m, r);
			
				inform_repair ir = new inform_repair(Informid,Informtype,date_inform,details,picture_inform,s,d);
				
				verifyinform vi = new verifyinform(verifyid,date_verify,verifystatus,verifydetail,ir);

				
				 rd = new RepairDurable(repairid,repair_date,repair_title,repair_charges,repair_detail
						,picture_invoice,picture_repairreport,picture_quatation,picture_repair
						,Date_of_repair,Repair_status,d,company,vi);
				
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rd;
	}
	
	
	public List<RepairDurable> listRepairCompletebyidmajor(String id_major) {
		List<RepairDurable> list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT rd.repair_id,rd.date_of_repair,rd.repair_status,rd.picture_invoice,rd.picture_quatation,"
					+ "rd.picture_repair,rd.picture_repairreport,rd.repair_charges,rd.repair_date,rd.repair_detail,rd.repair_title,rd.company_id"
					+ ",c.companyname,c.address,c.tell,d.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price"
					+ ",d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ ",d.note,d.room_number,r.build,r.room_name,r.floor,vi.verify_id,vi.verify_date,vi.verify_detail,vi.verify_status,vi.informid,ir.Informtype"
					+ ",ir.dateinform,ir.details,ir.picture_inform,ir.id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday"
					+ ",s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password"
					+ " FROM repair_durable rd inner join company c on rd.company_id = c.company_id "
					+ " inner join durable d on rd.durable_code = d.durable_code "
					+ " left join room r on r.room_number = d.room_number"
					+ " left join verifyinform vi on rd.verify_id = vi.verify_id left join inform_repair ir on  vi.informid =ir.Informid "
					+ " inner join staff s on ir.id_staff = s.id_staff inner join login l on s.username = l.username inner join major m on s.id_major = m.id_major"
					+ " where m.id_major = '"+id_major+"';";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int repairid  = rs.getInt(1);
				String Date_of_repair = rs.getString(2);
				String Repair_status = rs.getString(3);
				String picture_invoice = rs.getString(4);
				String picture_quatation = rs.getString(5);
				String picture_repair = rs.getString(6);
				String picture_repairreport = rs.getString(7);
				String repair_charges = rs.getString(8);
				String repair_date = rs.getString(9);
				String repair_detail = rs.getString(10);
				String repair_title = rs.getString(11);
				
				int company_id = rs.getInt(12);
				String companyname = rs.getString(13);
				String address = rs.getString(14);
				String tell = rs.getString(15);
				
				Company company = new Company(company_id,companyname,address,tell);
				
				/* เริ่ม durable */
				String durable_code = rs.getString(16);
				String Durable_name = rs.getString(17);
				String Durable_number = rs.getString(18);
				String Durable_brandname = rs.getString(19);
				String Durable_model = rs.getString(20);
				String Durable_price = rs.getString(21);
				String Durable_statusnow = rs.getString(22);
				String Responsible_person = rs.getString(23);
				String Durable_image = rs.getString(24);
				String Durable_Borrow_Status = rs.getString(25);
				String Durable_entrancedate = rs.getString(26);
				String durablenote = rs.getString(27);
				String Room_number = rs.getString(28);
				String Room_name = rs.getString(29);
				String Build = rs.getString(30);
				String floor = rs.getString(31);

			
				/* ปิด durable */
				
				int verifyid = rs.getInt(32);
				String verifydate = rs.getString(33);
				String verifydetail = rs.getString(34);
				String verifystatus = rs.getString(35);
				
				Calendar date_verify = Calendar.getInstance();
				String vdate[] = verifydate.split(" ");
				String vdate1[] = vdate[0].split("-");
				String vTime[] = vdate[1].split(":");
				date_verify.set(Integer.parseInt(vdate1[0]), Integer.parseInt(vdate1[1]) - 1, Integer.parseInt(vdate1[2]),
						Integer.parseInt(vTime[0]), Integer.parseInt(vTime[1]), Integer.parseInt(vTime[2]));

				String Informid = rs.getString(36);
				String Informtype = rs.getString(37);
				String dateinform = rs.getString(38);
				String details = rs.getString(39);
				String picture_inform = rs.getString(40);
				Calendar date_inform = Calendar.getInstance();
				String idate[] = dateinform.split(" ");
				String idate1[] = idate[0].split("-");
				String iTime[] = idate[1].split(":");
				date_inform.set(Integer.parseInt(idate1[0]), Integer.parseInt(idate1[1]) - 1, Integer.parseInt(idate1[2]),
						Integer.parseInt(iTime[0]), Integer.parseInt(iTime[1]), Integer.parseInt(iTime[2]));
				
				/* เริ่ม staff */
				int staff_id_staff = rs.getInt(41);
				String Id_card = rs.getString(42);
				String Staff_name = rs.getString(43);
				String Staff_lastname = rs.getString(44);
				String Staff_status = rs.getString(45);
				String Email = rs.getString(46);
				String Brithday = rs.getString(47);
				String Phone_number = rs.getString(48);
				String Image_staff = rs.getString(49);
				int idmajor = rs.getInt(50);
				String majorname = rs.getString(51);
				String usernames = rs.getString(52);
				String status = rs.getString(53);
				String password = rs.getString(54);
				Major m = new Major(idmajor, majorname);
				Login l = new Login(usernames, password, status);
				Staff s = new Staff(staff_id_staff, Id_card, Staff_name, Staff_lastname, Staff_status, Email, Brithday,
						Phone_number, Image_staff, m, l);
				/* ปิด staff */
				
				Room r = new Room(Room_number, Room_name, Build, floor, m);

				Durable d = new Durable(durable_code, Durable_name, Durable_number, Durable_brandname, Durable_model,
						Durable_price, Durable_statusnow, Responsible_person, Durable_image, Durable_Borrow_Status,
						Durable_entrancedate, durablenote, m, r);
			
				inform_repair ir = new inform_repair(Informid,Informtype,date_inform,details,picture_inform,s,d);
				
				verifyinform vi = new verifyinform(verifyid,date_verify,verifystatus,verifydetail,ir);

				
				RepairDurable rd = new RepairDurable(repairid,repair_date,repair_title,repair_charges,repair_detail
						,picture_invoice,picture_repairreport,picture_quatation,picture_repair
						,Date_of_repair,Repair_status,d,company,vi);
				list.add(rd);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	public List<RepairDurable> listRepairdurablehistory(String durablecode) {
		List<RepairDurable> list = new ArrayList<>();
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT rd.repair_id,rd.date_of_repair,rd.repair_status,rd.picture_invoice,rd.picture_quatation,\n"
					+ "rd.picture_repair,rd.picture_repairreport,rd.repair_charges,rd.repair_date,rd.repair_detail,rd.repair_title,rd.company_id\n"
					+ "	,c.companyname,c.address,c.tell,d.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ "		,d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ "				,d.note,d.room_number,r.build,r.room_name,r.floor,vi.verify_id,vi.verify_date,vi.verify_detail,vi.verify_status,vi.informid,ir.Informtype\n"
					+ "			,ir.dateinform,ir.details,ir.picture_inform,ir.id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ "				,s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password\n"
					+ "				 FROM repair_durable rd right join company c on rd.company_id = c.company_id \n"
					+ "					 right join durable d on rd.durable_code = d.durable_code \n"
					+ "				 right join room r on r.room_number = d.room_number\n"
					+ "					 right join verifyinform vi on rd.verify_id = vi.verify_id right join inform_repair ir on  vi.informid =ir.Informid \n"
					+ "				right join staff s on ir.id_staff = s.id_staff right join login l on s.username = l.username inner join major m on s.id_major = m.id_major\n"
					+ "				 where rd.durable_code ='"+durablecode+"' ;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int repairid  = rs.getInt(1);
				String Date_of_repair = rs.getString(2);
				String Repair_status = rs.getString(3);
				String picture_invoice = rs.getString(4);
				String picture_quatation = rs.getString(5);
				String picture_repair = rs.getString(6);
				String picture_repairreport = rs.getString(7);
				String repair_charges = rs.getString(8);
				String repair_date = rs.getString(9);
				String repair_detail = rs.getString(10);
				String repair_title = rs.getString(11);
				
				int company_id = rs.getInt(12);
				String companyname = rs.getString(13);
				String address = rs.getString(14);
				String tell = rs.getString(15);
				
				Company company = new Company(company_id,companyname,address,tell);
				
				/* เริ่ม durable */
				String durable_code = rs.getString(16);
				String Durable_name = rs.getString(17);
				String Durable_number = rs.getString(18);
				String Durable_brandname = rs.getString(19);
				String Durable_model = rs.getString(20);
				String Durable_price = rs.getString(21);
				String Durable_statusnow = rs.getString(22);
				String Responsible_person = rs.getString(23);
				String Durable_image = rs.getString(24);
				String Durable_Borrow_Status = rs.getString(25);
				String Durable_entrancedate = rs.getString(26);
				String durablenote = rs.getString(27);
				String Room_number = rs.getString(28);
				String Room_name = rs.getString(29);
				String Build = rs.getString(30);
				String floor = rs.getString(31);

			
				/* ปิด durable */
				
				int verifyid = rs.getInt(32);
				String verifydate = rs.getString(33);
				String verifydetail = rs.getString(34);
				String verifystatus = rs.getString(35);
				
				Calendar date_verify = Calendar.getInstance();
				String vdate[] = verifydate.split(" ");
				String vdate1[] = vdate[0].split("-");
				String vTime[] = vdate[1].split(":");
				date_verify.set(Integer.parseInt(vdate1[0]), Integer.parseInt(vdate1[1]) - 1, Integer.parseInt(vdate1[2]),
						Integer.parseInt(vTime[0]), Integer.parseInt(vTime[1]), Integer.parseInt(vTime[2]));

				String Informid = rs.getString(36);
				String Informtype = rs.getString(37);
				String dateinform = rs.getString(38);
				String details = rs.getString(39);
				String picture_inform = rs.getString(40);
				Calendar date_inform = Calendar.getInstance();
				String idate[] = dateinform.split(" ");
				String idate1[] = idate[0].split("-");
				String iTime[] = idate[1].split(":");
				date_inform.set(Integer.parseInt(idate1[0]), Integer.parseInt(idate1[1]) - 1, Integer.parseInt(idate1[2]),
						Integer.parseInt(iTime[0]), Integer.parseInt(iTime[1]), Integer.parseInt(iTime[2]));
				
				/* เริ่ม staff */
				int staff_id_staff = rs.getInt(41);
				String Id_card = rs.getString(42);
				String Staff_name = rs.getString(43);
				String Staff_lastname = rs.getString(44);
				String Staff_status = rs.getString(45);
				String Email = rs.getString(46);
				String Brithday = rs.getString(47);
				String Phone_number = rs.getString(48);
				String Image_staff = rs.getString(49);
				int idmajor = rs.getInt(50);
				String majorname = rs.getString(51);
				String usernames = rs.getString(52);
				String status = rs.getString(53);
				String password = rs.getString(54);
				Major m = new Major(idmajor, majorname);
				Login l = new Login(usernames, password, status);
				Staff s = new Staff(staff_id_staff, Id_card, Staff_name, Staff_lastname, Staff_status, Email, Brithday,
						Phone_number, Image_staff, m, l);
				/* ปิด staff */
				
				Room r = new Room(Room_number, Room_name, Build, floor, m);

				Durable d = new Durable(durable_code, Durable_name, Durable_number, Durable_brandname, Durable_model,
						Durable_price, Durable_statusnow, Responsible_person, Durable_image, Durable_Borrow_Status,
						Durable_entrancedate, durablenote, m, r);
			
				inform_repair ir = new inform_repair(Informid,Informtype,date_inform,details,picture_inform,s,d);
				
				verifyinform vi = new verifyinform(verifyid,date_verify,verifystatus,verifydetail,ir);

				
				RepairDurable rd = new RepairDurable(repairid,repair_date,repair_title,repair_charges,repair_detail
						,picture_invoice,picture_repairreport,picture_quatation,picture_repair
						,Date_of_repair,Repair_status,d,company,vi);
				list.add(rd);
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	public RepairDurable getRepairdurablehistory(String durablecode,String repair_id) {
		RepairDurable rd = null;
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT rd.repair_id,rd.date_of_repair,rd.repair_status,rd.picture_invoice,rd.picture_quatation,\n"
					+ "rd.picture_repair,rd.picture_repairreport,rd.repair_charges,rd.repair_date,rd.repair_detail,rd.repair_title,rd.company_id\n"
					+ "	,c.companyname,c.address,c.tell,d.durable_code,d.durable_name,d.durable_number,d.durable_brandname,d.durable_model,d.durable_price\n"
					+ "		,d.durable_statusnow,d.responsible_person,d.durable_image,d.durable_borrow_status,d.durable_entrancedate\n"
					+ "				,d.note,d.room_number,r.build,r.room_name,r.floor,vi.verify_id,vi.verify_date,vi.verify_detail,vi.verify_status,vi.informid,ir.Informtype\n"
					+ "			,ir.dateinform,ir.details,ir.picture_inform,ir.id_staff,s.id_card,s.staff_name,s.staff_lastname,s.staff_status,s.email,s.brithday\n"
					+ "				,s.phone_number,s.image_staff,s.id_major,m.major_name,s.username,l.status,l.password\n"
					+ "				 FROM repair_durable rd right join company c on rd.company_id = c.company_id \n"
					+ "					 right join durable d on rd.durable_code = d.durable_code \n"
					+ "				 right join room r on r.room_number = d.room_number\n"
					+ "					 right join verifyinform vi on rd.verify_id = vi.verify_id right join inform_repair ir on  vi.informid =ir.Informid \n"
					+ "				right join staff s on ir.id_staff = s.id_staff right join login l on s.username = l.username inner join major m on s.id_major = m.id_major\n"
					+ "				 where rd.durable_code ='"+durablecode+"' and  rd.repair_id= '"+repair_id+"';";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int repairid  = rs.getInt(1);
				String Date_of_repair = rs.getString(2);
				String Repair_status = rs.getString(3);
				String picture_invoice = rs.getString(4);
				String picture_quatation = rs.getString(5);
				String picture_repair = rs.getString(6);
				String picture_repairreport = rs.getString(7);
				String repair_charges = rs.getString(8);
				String repair_date = rs.getString(9);
				String repair_detail = rs.getString(10);
				String repair_title = rs.getString(11);
				
				int company_id = rs.getInt(12);
				String companyname = rs.getString(13);
				String address = rs.getString(14);
				String tell = rs.getString(15);
				
				Company company = new Company(company_id,companyname,address,tell);
				
				/* เริ่ม durable */
				String durable_code = rs.getString(16);
				String Durable_name = rs.getString(17);
				String Durable_number = rs.getString(18);
				String Durable_brandname = rs.getString(19);
				String Durable_model = rs.getString(20);
				String Durable_price = rs.getString(21);
				String Durable_statusnow = rs.getString(22);
				String Responsible_person = rs.getString(23);
				String Durable_image = rs.getString(24);
				String Durable_Borrow_Status = rs.getString(25);
				String Durable_entrancedate = rs.getString(26);
				String durablenote = rs.getString(27);
				String Room_number = rs.getString(28);
				String Room_name = rs.getString(29);
				String Build = rs.getString(30);
				String floor = rs.getString(31);

			
				/* ปิด durable */
				
				int verifyid = rs.getInt(32);
				String verifydate = rs.getString(33);
				String verifydetail = rs.getString(34);
				String verifystatus = rs.getString(35);
				
				Calendar date_verify = Calendar.getInstance();
				String vdate[] = verifydate.split(" ");
				String vdate1[] = vdate[0].split("-");
				String vTime[] = vdate[1].split(":");
				date_verify.set(Integer.parseInt(vdate1[0]), Integer.parseInt(vdate1[1]) - 1, Integer.parseInt(vdate1[2]),
						Integer.parseInt(vTime[0]), Integer.parseInt(vTime[1]), Integer.parseInt(vTime[2]));

				String Informid = rs.getString(36);
				String Informtype = rs.getString(37);
				String dateinform = rs.getString(38);
				String details = rs.getString(39);
				String picture_inform = rs.getString(40);
				Calendar date_inform = Calendar.getInstance();
				String idate[] = dateinform.split(" ");
				String idate1[] = idate[0].split("-");
				String iTime[] = idate[1].split(":");
				date_inform.set(Integer.parseInt(idate1[0]), Integer.parseInt(idate1[1]) - 1, Integer.parseInt(idate1[2]),
						Integer.parseInt(iTime[0]), Integer.parseInt(iTime[1]), Integer.parseInt(iTime[2]));
				
				/* เริ่ม staff */
				int staff_id_staff = rs.getInt(41);
				String Id_card = rs.getString(42);
				String Staff_name = rs.getString(43);
				String Staff_lastname = rs.getString(44);
				String Staff_status = rs.getString(45);
				String Email = rs.getString(46);
				String Brithday = rs.getString(47);
				String Phone_number = rs.getString(48);
				String Image_staff = rs.getString(49);
				int idmajor = rs.getInt(50);
				String majorname = rs.getString(51);
				String usernames = rs.getString(52);
				String status = rs.getString(53);
				String password = rs.getString(54);
				Major m = new Major(idmajor, majorname);
				Login l = new Login(usernames, password, status);
				Staff s = new Staff(staff_id_staff, Id_card, Staff_name, Staff_lastname, Staff_status, Email, Brithday,
						Phone_number, Image_staff, m, l);
				/* ปิด staff */
				
				Room r = new Room(Room_number, Room_name, Build, floor, m);

				Durable d = new Durable(durable_code, Durable_name, Durable_number, Durable_brandname, Durable_model,
						Durable_price, Durable_statusnow, Responsible_person, Durable_image, Durable_Borrow_Status,
						Durable_entrancedate, durablenote, m, r);
			
				inform_repair ir = new inform_repair(Informid,Informtype,date_inform,details,picture_inform,s,d);
				
				verifyinform vi = new verifyinform(verifyid,date_verify,verifystatus,verifydetail,ir);

				
				 rd = new RepairDurable(repairid,repair_date,repair_title,repair_charges,repair_detail
						,picture_invoice,picture_repairreport,picture_quatation,picture_repair
						,Date_of_repair,Repair_status,d,company,vi);
	
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rd;
	}
	
	public int updateRepairDurable(RepairDurable rd) {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "UPDATE repair_durable\n"
					+ "SET\n"
					+ "date_of_repair = '" + rd.getDate_of_repair() + "',\n"
					+ "repair_status = '" + rd.getRepair_status() + "',\n"
					+ "repair_charges = '" + rd.getRepair_charges() + "',\n"
					+ "repair_detail = '" + rd.getRepair_detail() + "',\n"
					+ "company_id = '" + rd.getCompany().getCompany_id() + "'\n"
					+ "WHERE durable_code = '" + rd.getDurable().getDurable_code() + "' AND verify_id = '" + rd.getVerifyinform().getVerify_id() + "' ;";
			int result = stmt.executeUpdate(sql);

			con.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	
	
}
