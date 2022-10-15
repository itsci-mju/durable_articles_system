package ac.th.itsci.durable.app.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.th.itsci.durable.app.manager.CompanyManager;
import ac.th.itsci.durable.app.manager.InformManager;
import ac.th.itsci.durable.app.manager.MaintenanceManager;
import ac.th.itsci.durable.app.manager.RoomManager;
import ac.th.itsci.durable.app.manager.VerifyManager;
import ac.th.itsci.durable.app.manager.verifyInformManager;
import ac.th.itsci.durable.entity.Company;
import ac.th.itsci.durable.entity.Durable;
import ac.th.itsci.durable.entity.RepairDurable;
import ac.th.itsci.durable.entity.Room;
import ac.th.itsci.durable.entity.inform_repair;
import ac.th.itsci.durable.entity.verifyinform;
import ac.th.itsci.durable.util.ConnectionDB;
import ac.th.itsci.durable.util.ResponseObj;



@Controller
@RequestMapping(path = "/apprepairdurable", method = RequestMethod.POST)
public class MaintenanceController2 {
	private static String SALT = "123456";

	@RequestMapping(value = "/repairdurable/addrepairdurable", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_addrepairdurable(@RequestBody Map<String, String> map) {
		int message = 0;
		RepairDurable rd = null;

		try {
			String companyname = map.get("companyname");
			String repair_charges = map.get("repaircharges");
			String repair_detail = map.get("repair_detail");
			String repair_status = map.get("repair_status");
			String durable_code = map.get("durable_code");
			String verify_id = map.get("verify_id");
			
			Date date_now = new Date();
			String DATE_FORMAT = "dd MMMM yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
			CompanyManager cm = new CompanyManager();
			
			Company company = new Company();
			company = cm.getcompanybyname(companyname);
			if(company == null) {
				 company = new Company(cm.getMaxcompanyID(),companyname,"-","-");
				cm.insertcompany(company);
			}else if(company != null){
				System.out.println("COMPANY NOT NULL");
			}
					
			verifyInformManager vim = new verifyInformManager();
			if(repair_status.equals("ไม่สามารถซ่อมได้")) {
				vim.updateverifyinform(repair_detail,repair_status,verify_id);
			}
			VerifyManager vm = new VerifyManager();
			Durable d = new Durable();
			d = vm.getDurable2(durable_code);
			

			
			verifyinform vi = new verifyinform();
			vi = vim.getverifyinformbyid(verify_id);
			
			MaintenanceManager mm = new MaintenanceManager();
			rd = new RepairDurable(mm.getMaxrepairdurableID(),"-","-",repair_charges,repair_detail,"-","-",
					"-","-",sdf.format(date_now),repair_status,d,company,vi);
			
			mm.insertRepairDurable(rd);

			if (message == 1) {
				return new ResponseObj(200, "1");
			} else {
				return new ResponseObj(200, "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = 0;
			return new ResponseObj(500, "0");
		}
	}
	
	@RequestMapping(value = "/repairdurable/updaterepairdurable", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_updaterepairdurable(@RequestBody Map<String, String> map) {
		int message = 0;
		RepairDurable rd = null;

		try {
			String companyname = map.get("companyname");
			String repair_charges = map.get("repaircharges");
			String repair_detail = map.get("repair_detail");
			String repair_status = map.get("repair_status");
			String durable_code = map.get("durable_code");
			String verify_id = map.get("verify_id");
			
			Date date_now = new Date();
			String DATE_FORMAT = "dd MMMM yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));
			
			CompanyManager cm = new CompanyManager();
			
			Company company = new Company();
			company = cm.getcompanybyname(companyname);
			if(company == null) {
				 company = new Company(cm.getMaxcompanyID(),companyname,"-","-");
				cm.insertcompany(company);
			}else if(company != null){
				System.out.println("COMPANY NOT NULL");
			}
					
			VerifyManager vm = new VerifyManager();
			Durable d = new Durable();
			d = vm.getDurable2(durable_code);
			
			verifyInformManager vim = new verifyInformManager();
			
			verifyinform vi = new verifyinform();
			vi = vim.getverifyinformbyid(verify_id);
			
			MaintenanceManager mm = new MaintenanceManager();
			rd = new RepairDurable(mm.getMaxrepairdurableID(),"-","-",repair_charges,repair_detail,"-","-",
					"-","-",sdf.format(date_now),repair_status,d,company,vi);
			
			mm.updateRepairDurable(rd);

			if (message == 1) {
				return new ResponseObj(200, "1");
			} else {
				return new ResponseObj(200, "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = 0;
			return new ResponseObj(500, "0");
		}
	}
	@RequestMapping(value = "/repairdurable/listRepairComplete", method = RequestMethod.POST)
	public @ResponseBody ResponseObj list_RepairCompleteADMIN(@RequestBody Map<String, String> map,HttpServletRequest request) {
		List<RepairDurable> durable = null;
		try {
			MaintenanceManager mm = new MaintenanceManager();
			String major_name = map.get("major_name");
			durable = mm.listRepairComplete(major_name);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	@RequestMapping(value = "/repairdurable/listRepairCompleteMajorid", method = RequestMethod.POST)
	public @ResponseBody ResponseObj list_RepairComplete(@RequestBody Map<String, String> map,HttpServletRequest request) {
		List<RepairDurable> durable = null;
		try {
			MaintenanceManager mm = new MaintenanceManager();
			String major_id = map.get("major_id");
			durable = mm.listRepairCompletebyidmajor(major_id);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	@RequestMapping(value = "/repairdurable/listRepairHistory", method = RequestMethod.POST)
	public @ResponseBody ResponseObj list_RepairHistory(@RequestBody Map<String, String> map,HttpServletRequest request) {
		List<RepairDurable> durable = null;
		try {
			MaintenanceManager mm = new MaintenanceManager();
			String durable_code = map.get("durable_code");
			durable = mm.listRepairdurablehistory(durable_code);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	@RequestMapping(value = "/repairdurable/getRepairHistory", method = RequestMethod.POST)
	public @ResponseBody ResponseObj get_RepairHistory(@RequestBody Map<String, String> map,HttpServletRequest request) {
		RepairDurable rd = null;
		try {
			MaintenanceManager mm = new MaintenanceManager();
			String durable_code = map.get("durable_code");
			String repair_id = map.get("repair_id");
			rd = mm.getRepairdurablehistory(durable_code,repair_id);
			System.out.println(rd.toString());
			return new ResponseObj(200, rd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, rd);
	}
	
	@PostMapping(path = "/repairdurable/getRepairdurable", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_getRepairdurable(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		RepairDurable rd = null;

		try {
			String durable_code = map.get("durable_code");
			String verify_id = map.get("verify_id");
			MaintenanceManager mm = new MaintenanceManager();
			rd = mm.getRepairdurable(durable_code, verify_id);
			System.out.println(rd.toString());
		
				return new ResponseObj(200, rd);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, rd);
	}

}
