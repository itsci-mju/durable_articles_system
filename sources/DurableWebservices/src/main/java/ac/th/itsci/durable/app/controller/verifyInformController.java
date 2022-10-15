package ac.th.itsci.durable.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.th.itsci.durable.app.manager.InformManager;
import ac.th.itsci.durable.app.manager.VerifyManager;
import ac.th.itsci.durable.app.manager.verifyInformManager;
import ac.th.itsci.durable.entity.Durable;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.entity.Verify;
import ac.th.itsci.durable.entity.VerifyDurable;
import ac.th.itsci.durable.entity.inform_repair;
import ac.th.itsci.durable.entity.verifyinform;
import ac.th.itsci.durable.util.ResponseObj;

@Controller
@RequestMapping(path = "/appverifyinform", method = RequestMethod.POST)
public class verifyInformController {
	private static String SALT = "123456";

	@RequestMapping(value = "/verifyinform/addform", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_addverifyinform(@RequestBody Map<String, String> map) {
		int message = 0;
		verifyinform vir = null;

		try {
			String Informid = map.get("Informid");
			Calendar c = Calendar.getInstance();
			
			String verify_status = map.get("verify_status");
			String details = map.get("verify_detail");
			if(details == "") {
				details = "-";
			}
			InformManager im = new InformManager();
			verifyInformManager vim = new verifyInformManager();
			int id = vim.getMaxverifyinformID();

			vir = new verifyinform(id, c, verify_status, details);

			inform_repair ir = new inform_repair();
			ir = im.getinform_repairbyid2(Informid);
			vir.setInform_repair(ir);

			message = vim.insertverifyinform2(vir);
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
	
	@RequestMapping(path = "/verifyinform/getverifyinformbyid", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_getverifyinformbyidinform(@RequestBody Map<String, String> map) {
		verifyinform verify = null;
		try {
			String informid = map.get("informid");
			verifyInformManager vm = new verifyInformManager();
			verify = vm.getverifyinformbyid2(informid);
			System.out.println(verify.toString());
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}

}
