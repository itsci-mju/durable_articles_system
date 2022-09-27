package ac.th.itsci.durable.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import ac.th.itsci.durable.entity.*;

public class HibernateConnection {
	static String url = "jdbc:mysql://localhost:3306/durablewebservice?characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=false";
	static String uname = "root";
	static String pwd = "123456";
	public static SessionFactory sessionFactory;
	public static SessionFactory doHibernateConnection() {
	Properties database = new Properties();
	//database.setProperty("hibernate.hbm2ddl.auto", "create");
	database.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
	database.setProperty("hibernate.connection.username", uname);
	database.setProperty("hibernate.connection.password", pwd);
	database.setProperty("hibernate.connection.url", url);
	database.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
	Configuration cfg = new Configuration().setProperties(database).addPackage("ac.th.itsci.durable.entity")
			.addAnnotatedClass(AssignType.class)
			.addAnnotatedClass(BillOfLading.class)
			.addAnnotatedClass(Borrower.class)
			.addAnnotatedClass(Borrowing.class).addAnnotatedClass(Committee.class).addAnnotatedClass(CommitteeID.class)
			.addAnnotatedClass(Company.class)
			.addAnnotatedClass(Document.class)
			.addAnnotatedClass(Durable.class)
			.addAnnotatedClass(DurableControll.class)
			.addAnnotatedClass(DurableType.class)
			.addAnnotatedClass(InspectionSchedule.class)
			.addAnnotatedClass(Item.class)
			.addAnnotatedClass(Login.class)
			.addAnnotatedClass(Major.class)
			.addAnnotatedClass(Personnel.class)
			.addAnnotatedClass(PersonnelAssign.class)
			.addAnnotatedClass(PersonnelAssign.class)
			.addAnnotatedClass(Position.class)
			.addAnnotatedClass(PurchaseOrderDocument.class)
			.addAnnotatedClass(ReceiveOrderDocument.class)
			.addAnnotatedClass(inform_repair.class)
			.addAnnotatedClass(verifyinform.class)
			.addAnnotatedClass(RepairDurable.class)
			.addAnnotatedClass(RequestOrderItemList.class)
			.addAnnotatedClass(RequestOrderItemListID.class)
			.addAnnotatedClass(RequisitionDocument.class)
			.addAnnotatedClass(RequisitionItem.class)
			.addAnnotatedClass(RequisitionItemID.class)
			.addAnnotatedClass(Room.class)
			.addAnnotatedClass(Staff.class)
			.addAnnotatedClass(Verify.class).addAnnotatedClass(VerifyDurable.class)
			.addAnnotatedClass(VerifyDurableID.class);	
	StandardServiceRegistryBuilder ssrb =new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
	sessionFactory = cfg.buildSessionFactory(ssrb.build());
	return sessionFactory;
	}
}
