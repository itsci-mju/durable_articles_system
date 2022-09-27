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

import ac.th.itsci.durable.app.manager.MajorManager;
import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.util.ResponseObj;



@Controller
@RequestMapping(path = "/appmajor", method = RequestMethod.POST)
public class MajorController2 {
	private static String SALT = "123456";

	//Testlistroombymajor
	@RequestMapping(value = "/major/Testlistmajor", method = RequestMethod.POST)
	public @ResponseBody ResponseObj Testlistmajor(HttpServletRequest request) {
		List<Major> m = null;
		try {
			MajorManager mm = new MajorManager();	
			m =  mm.listallmajor();
			System.out.println(m.toString());
			return new ResponseObj(200, m);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, m);
	}
	
	@RequestMapping(value = "/major/listmajor", method = RequestMethod.POST)
	public @ResponseBody ResponseObj do_listmajor(@RequestBody Map<String, String> map ,HttpServletRequest request) {
		List<Major> m = null;
		try {
			MajorManager mm = new MajorManager();	
			m =  mm.listallmajor();
			System.out.println(m.toString());
			return new ResponseObj(200, m);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, m);
	}

}
