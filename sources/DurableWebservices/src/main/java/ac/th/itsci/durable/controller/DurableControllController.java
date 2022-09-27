package ac.th.itsci.durable.controller;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ac.th.itsci.durable.service.DurableControllWebService;

import java.util.*;

@Controller
public class DurableControllController {

	@Autowired
	DurableControllWebService durableControllWebService;

	@GetMapping(value = "/do_loadDurableControllPage")
	public ModelAndView do_loadDurableControllPage(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = durableControllWebService.callLoadDurableControllPage(request, session, md);
		return mav;
	}

	@PostMapping(value = "/do_loadEditDurableControll")
	public ModelAndView do_loadEditDurableControll(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = durableControllWebService.callLoadEditDurableControll(request, session, md);
		return mav;
	}

	@PostMapping(value = "/do_addDurableControll")
	public ModelAndView do_addDurableControll(ServletRequest request, HttpSession session, Model md, ServletResponse response, @RequestParam("durable_img") MultipartFile file) {
		ModelAndView mav = durableControllWebService.callAddDurableControll(request, session, md, response, file);
		return mav;
	}

	@PostMapping(value = "/do_editDurableControll")
	public ModelAndView do_editDurableControll(ServletRequest request, HttpSession session, Model md, ServletResponse response, @RequestParam("durable_img") MultipartFile file) {
		ModelAndView mav = durableControllWebService.callEditDurableControll(request, session, md, response, file);
		return mav;
	}

	@GetMapping(value = "/do_loadlistdurablecontrolbyyear")
	public ModelAndView do_loadlistdurablecontrolbyyear(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = durableControllWebService.callloadlistdurablecontrolbyyear(request, session, md);
		return mav;
	}
	
	

	@PostMapping(value = "/do_getDurableControllByYear")
	public ModelAndView do_getDurableControllByYear(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = durableControllWebService.callGetDurableControlByYear(request, session, md);
		return mav;
	}
	
	@PostMapping(value="/do_loadDurableControllDetail")
	public ModelAndView do_loadDurableControllDetail(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = durableControllWebService.callLoadDurableControllDetail(request, session, md);
		return mav;
	}
	
	@PostMapping(value="/do_generatedurablecode")
	public ModelAndView do_generatedurablecode(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = durableControllWebService.do_generatedurablecode(request, session, md);
		return mav;
	}
	
	
	
	
	
}
