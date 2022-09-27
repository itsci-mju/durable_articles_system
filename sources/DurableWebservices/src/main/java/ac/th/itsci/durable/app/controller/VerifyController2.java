package ac.th.itsci.durable.app.controller;

import java.text.SimpleDateFormat;
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

import ac.th.itsci.durable.app.manager.DurableManager;
import ac.th.itsci.durable.app.manager.VerifyManager;
import ac.th.itsci.durable.entity.Durable;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.entity.Verify;
import ac.th.itsci.durable.entity.VerifyDurable;
import ac.th.itsci.durable.entity.verifyinform;
import ac.th.itsci.durable.util.ResponseObj;

@Controller
@RequestMapping(path = "/appverify", method = RequestMethod.POST)
public class VerifyController2 {
	private static String SALT = "123456";

	@PostMapping(path = "/verify/addform", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj doverifyform(@RequestBody Map<String, String> map) {
		int message = 0;
		VerifyDurable vd = null;

		try {
			String durable_status = map.get("durable_status");
			String save_date = map.get("save_date");
			String note = map.get("note");
			String picture_verify = map.get("picture_verify");
			int id_staff = Integer.parseInt(map.get("id_staff"));
			String years = map.get("years");
			String durable_code = map.get("durable_code");
			Date date_now = new Date();
			String DATE_FORMAT = "dd MMMM yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));

			vd = new VerifyDurable(sdf.format(date_now), durable_status, note, picture_verify);
			VerifyManager vm = new VerifyManager();
			Staff s = new Staff();
			s = vm.getstaff2(id_staff);

			Verify v = new Verify();
			v = vm.getverify2(years);

			Durable d = new Durable();
			d = vm.getDurable2(durable_code);

			vd.getPk().setStaff(s);
			vd.getPk().setVerify(v);
			vd.getPk().setDurable(d);
			message = vm.insertverifyform2(vd);
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
	@PostMapping(path = "/verify/updateform", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj doupdateverifyform(@RequestBody Map<String, String> map) {
		int message = 0;
		VerifyDurable vd = null;

		try {
			String durable_status = map.get("durable_status");
			String save_date = map.get("save_date");
			String note = map.get("note");
			String picture_verify = map.get("picture_verify");
			int id_staff = Integer.parseInt(map.get("id_staff"));
			String years = map.get("years");
			String durable_code = map.get("durable_code");
			Date date_now = new Date();
			String DATE_FORMAT = "dd MMMM yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));

			vd = new VerifyDurable(sdf.format(date_now), durable_status, note, picture_verify);
			VerifyManager vm = new VerifyManager();
			Staff s = new Staff();
			s = vm.getstaff2(id_staff);

			Verify v = new Verify();
			v = vm.getverify2(years);

			Durable d = new Durable();
			d = vm.getDurable2(durable_code);

			vd.getPk().setStaff(s);
			vd.getPk().setVerify(v);
			vd.getPk().setDurable(d);
			message = vm.updateverifyform(vd);
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

	// Testlistyears
	@PostMapping(path = "/verify/testyearlist")
	public @ResponseBody ResponseObj testyearlist(HttpServletRequest request) {
		List<Verify> verify = null;
		try {
			VerifyManager vm = new VerifyManager();
			verify = vm.listyears();

			System.out.println(verify.toString());
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}

	// TESTcountalldurable
	@PostMapping(path = "/verify/countdurable")
	public @ResponseBody ResponseObj testcountalldurable(HttpServletRequest request) {
		int verify = 0;
		try {
			VerifyManager vm = new VerifyManager();
			verify = vm.countalldurable("วิชาเทคโนโลยีสารสนเทศ");

			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}

	@PostMapping(path = "/verifybymajor/countalldurable")
	public @ResponseBody ResponseObj do_countalldurabler(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		int verify = 0;
		try {
			String major_name = map.get("major_name");

			VerifyManager vm = new VerifyManager();
			verify = vm.countalldurable(major_name);
			System.out.println(String.valueOf(verify));
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}

	// TESTcountdurabled
	@PostMapping(path = "/verify/countdurabled")
	public @ResponseBody ResponseObj testcountdurabled(HttpServletRequest request) {
		int verify = 0;
		try {
			VerifyManager vm = new VerifyManager();
			verify = vm.countdurabled("วิชาเทคโนโลยีสารสนเทศ", "2566");

			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}

	@PostMapping(path = "/verifybymajor/countdurabled")
	public @ResponseBody ResponseObj do_countdurabled(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		int verify = 0;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			verify = vm.countdurabled(major_name, year);
			System.out.println(String.valueOf(verify));
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}

	// TESTcountnotdurable
	@PostMapping(path = "/verify/countnotdurabled")
	public @ResponseBody ResponseObj testcountnotdurabled(HttpServletRequest request) {
		int verify = 0;
		try {
			VerifyManager vm = new VerifyManager();
			verify = vm.countnotdurable("วิชาเทคโนโลยีสารสนเทศ", "2566");

			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}

	@PostMapping(path = "/verifybymajor/countnotdurabled")
	public @ResponseBody ResponseObj do_countnotdurabled(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		int verify = 0;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			verify = vm.countnotdurable(major_name, year);
			System.out.println(String.valueOf(verify));
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}
	@PostMapping(path = "/verifybymajor/countverifystatus")
	public @ResponseBody ResponseObj do_countverifystatus(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		int verify = 0;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			verify = vm.countverifystatusgood(major_name, year);
			System.out.println(String.valueOf(verify));
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}
	@RequestMapping(value = "/verifybymajor/listverifystatus", method = RequestMethod.POST)
	public @ResponseBody ResponseObj do_listverifystatus(@RequestBody Map<String, String> map ,HttpServletRequest request) {
		List<VerifyDurable> durable = null;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			durable =  vm.verifystatusgood(major_name,year);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	
	@PostMapping(path = "/verifybymajor/countverifystatus2")
	public @ResponseBody ResponseObj do_countverifystatus2(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		int verify = 0;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			verify = vm.countverifystatus2(major_name, year);
			System.out.println(String.valueOf(verify));
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}
	@RequestMapping(value = "/verifybymajor/listverifystatus2", method = RequestMethod.POST)
	public @ResponseBody ResponseObj do_listverifystatus2(@RequestBody Map<String, String> map ,HttpServletRequest request) {
		List<VerifyDurable> durable = null;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			durable =  vm.verifystatus2(major_name,year);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	@PostMapping(path = "/verifybymajor/countverifystatus3")
	public @ResponseBody ResponseObj do_countverifystatus3(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		int verify = 0;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			verify = vm.countverifystatus3(major_name, year);
			System.out.println(String.valueOf(verify));
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}
	@RequestMapping(value = "/verifybymajor/listverifystatus3", method = RequestMethod.POST)
	public @ResponseBody ResponseObj do_listverifystatus3(@RequestBody Map<String, String> map ,HttpServletRequest request) {
		List<VerifyDurable> durable = null;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			durable =  vm.verifystatus3(major_name,year);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	@PostMapping(path = "/verifybymajor/countverifystatus4")
	public @ResponseBody ResponseObj do_countverifystatus4(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		int verify = 0;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			verify = vm.countverifystatus4(major_name, year);
			System.out.println(String.valueOf(verify));
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}
	@RequestMapping(value = "/verifybymajor/listverifystatus4", method = RequestMethod.POST)
	public @ResponseBody ResponseObj do_listverifystatus4(@RequestBody Map<String, String> map ,HttpServletRequest request) {
		List<verifyinform> durable = null;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			durable =  vm.verifystatus4(major_name,year);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}

	@PostMapping(path = "/verify/listyears")
	public @ResponseBody ResponseObj do_listyears(@RequestBody Map<String, String> map, HttpServletRequest request) {
		List<Verify> verify = null;
		try {
			VerifyManager vm = new VerifyManager();
			verify = vm.getlistyears();
			System.out.println(verify.toString());
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}

	@PostMapping(path = "/verify/verifyinverifydurable", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_verifyinverifydurable(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		Verify verify = null;
		String success = "1";
		String fail = "0";
		try {
			String durable_code = map.get("durable_code");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			verify = vm.getverify_in_verifydurable(durable_code,year);
			System.out.println(verify.toString());
			if (verify == null) {
				return new ResponseObj(500, fail);
			} else if (verify != null) {
				return new ResponseObj(200, success);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, fail);
	}

	@PostMapping(path = "/VerifyDurable/getverifybydurablecode", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_getverifybydurablecode(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		VerifyDurable verify = null;
		try {
			String durable_code = map.get("durable_code");
			VerifyManager vm = new VerifyManager();
			verify = vm.getverifydurablebycode(durable_code);
			System.out.println(verify.toString());
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}
	
	
	@PostMapping(path = "/VerifyDurable/getverifybydurablecurrent", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_getverifybydurablecurrent(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		VerifyDurable verify = null;
		try {
			String durable_code = map.get("durable_code");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			verify = vm.getverifydurablecurrent(durable_code,year);
			System.out.println(verify.toString());
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}
	
	
}
