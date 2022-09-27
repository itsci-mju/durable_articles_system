package ac.th.itsci.durable.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.th.itsci.durable.app.manager.DurableManager;
import ac.th.itsci.durable.entity.Durable;
import ac.th.itsci.durable.entity.VerifyDurable;
import ac.th.itsci.durable.util.ResponseObj;


@Controller
@RequestMapping(path = "/appdurable", method = RequestMethod.POST)
public class DurableController2 {
	private static String SALT = "123456";

	@RequestMapping(value = "/durable/listdurablebymajor", method = RequestMethod.POST)
	public @ResponseBody ResponseObj do_listdurablebymajor(@RequestBody Map<String, String> map ,HttpServletRequest request) {
		List<Durable> durable = null;
		try {
			String majorid = map.get("major_id");
			String room_number = map.get("room_number");
			DurableManager dm = new DurableManager();
			durable =  dm.listAlldurablebymajor(majorid,room_number);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	
	@RequestMapping(value = "/verify/listverifydurable", method = RequestMethod.POST)
	public @ResponseBody ResponseObj do_listverifydurable(@RequestBody Map<String, String> map ,HttpServletRequest request) {
		List<VerifyDurable> durable = null;
		try {
			String majorid = map.get("major_id");
			String room_number = map.get("room_number");
			String years = map.get("years");
			DurableManager dm = new DurableManager();
			durable =  dm.listAlldurableverify2(majorid,room_number,years);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	
	@RequestMapping(value = "/verify/listnotverifydurable", method = RequestMethod.POST)
	public @ResponseBody ResponseObj do_listnotverifydurable(@RequestBody Map<String, String> map ,HttpServletRequest request) {
		List<Durable> durable = null;
		try {
			String majorid = map.get("major_id");
			String room_number = map.get("room_number");
			String years = map.get("years");
			DurableManager dm = new DurableManager();
			durable =  dm.listAllnotdurableverify2(majorid,room_number,years);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	//GET LIST DURABLE NOT VERIFY
	@RequestMapping(value = "/verify/listdurablenotverify_admin", method = RequestMethod.POST)
	public @ResponseBody ResponseObj do_listdurablenotverify_admin(@RequestBody Map<String, String> map ,HttpServletRequest request) {
		List<Durable> durable = null;
		try {
			String room_number = map.get("room_number");
			String years = map.get("years");
			DurableManager dm = new DurableManager();
			durable =  dm.listdurablenotverifyadmin2(room_number,years);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	//GET LIST DURABLE VERIFYED
		@RequestMapping(value = "/verify/listdurableverifyed_admin", method = RequestMethod.POST)
		public @ResponseBody ResponseObj do_listdurableverifyed_admin(@RequestBody Map<String, String> map ,HttpServletRequest request) {
			List<VerifyDurable> durable = null;
			try {
				String room_number = map.get("room_number");
				String years = map.get("years");
				DurableManager dm = new DurableManager();
				durable =  dm.listdurableverifyadmin2(room_number,years);
				System.out.println(durable.toString());
				return new ResponseObj(200, durable);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseObj(500, durable);
		}
	//Testlistalldurable
	@RequestMapping(value = "/durable/listdurablebymajortest", method = RequestMethod.POST)
	public @ResponseBody ResponseObj do_listdurablebymajortest(HttpServletRequest request) {
		List<Durable> durable = null;
		try {
			DurableManager dm = new DurableManager();	
			durable =  dm.listAllnotdurableverify("1","1101","2566");
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}

	@RequestMapping(value = "/durable/getdurable", method = RequestMethod.POST)
	public @ResponseBody ResponseObj getdurable(@RequestBody Map<String, String> map ,HttpServletRequest request) {
		Durable durable = null;
		try {
			String durable_code = map.get("durable_code");
			DurableManager dm = new DurableManager();

			durable = dm.getDurable2(durable_code);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	
	//testgetdurablebyscan
	@RequestMapping(value = "/durable/getdurabletest", method = RequestMethod.POST)
	public @ResponseBody ResponseObj getdurabletest(HttpServletRequest request) {
		Durable durable = null;
		try {
			//String username = map.get("username");
			DurableManager dm = new DurableManager();
	
			durable = dm.getDurable("ธก.7110-002-0003/35");
			//staff =  sm.listStaffbyusername(username);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	
	@RequestMapping(value = "/durable/listAlldurable", method = RequestMethod.POST)
	public @ResponseBody ResponseObj do_listAlldurable(HttpServletRequest request) {
		List<Durable> durable = null;
		try {
			DurableManager dm = new DurableManager();
			durable = dm.listAlldurable();
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	

}
