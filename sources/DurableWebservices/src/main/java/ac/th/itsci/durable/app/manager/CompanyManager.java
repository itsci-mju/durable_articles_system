package ac.th.itsci.durable.app.manager;

import java.sql.Connection;
import java.sql.ResultSet;
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
import ac.th.itsci.durable.entity.Durable;
import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.Room;
import ac.th.itsci.durable.entity.verifyinform;
import ac.th.itsci.durable.util.ConnectionDB;
import ac.th.itsci.durable.util.HibernateConnection;



public class CompanyManager {
	private static String SALT = "123456";

	public int insertcompany(Company c) {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		try {
			Statement stmt = con.createStatement();
			String sql = "INSERT INTO company(company_id,address,companyname,tell)"
					+ " VALUES ('" +c.getCompany_id() + "', '" + c.getAddress() + "', '" + c.getCompanyname() + "', '"
					+c.getTell()  + "');";
			int result = stmt.executeUpdate(sql);

			con.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}
	
	public int getMaxcompanyID() {
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();
		int id = 0;
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT Max(company_id) from company;";
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
	
	public Company getcompanybyname(String company_name) {
		Company c = null;
		ConnectionDB condb = new ConnectionDB();
		Connection con = condb.getConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT company_id,companyname,address,tell FROM company where companyname ='" + company_name + "';";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				/* เริ่ม durable */
				int company_id = rs.getInt(1);
				String companyname = rs.getString(2);
				String address = rs.getString(3);
				String tell = rs.getString(4);

				 c = new Company(company_id,companyname,address,tell);
				}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return c;
	}



}
