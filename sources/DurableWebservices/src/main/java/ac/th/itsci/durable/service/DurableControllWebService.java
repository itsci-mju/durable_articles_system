package ac.th.itsci.durable.service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public interface DurableControllWebService {
	
	public ModelAndView callLoadEditDurableControll(ServletRequest request, HttpSession session, Model md);
	
	public ModelAndView callLoadDurableControllPage(ServletRequest request, HttpSession session, Model md);
	
	public ModelAndView callAddDurableControll(ServletRequest request, HttpSession session, Model md, ServletResponse response, @RequestParam("durable_img") MultipartFile file);
	
	public ModelAndView callEditDurableControll(ServletRequest request, HttpSession session, Model md, ServletResponse response, @RequestParam("durable_img") MultipartFile file);
	
	public ModelAndView callGetDurableControlByYear(ServletRequest request, HttpSession session, Model md);
	
	public ModelAndView callLoadDurableControllDetail(ServletRequest request, HttpSession session, Model md);
	
	public ModelAndView callSearchDurableControll(ServletRequest request, HttpSession session, Model md);
	
	public ModelAndView callloadlistdurablecontrolbyyear(ServletRequest request, HttpSession session, Model md);
	
	public ModelAndView do_generatedurablecode(ServletRequest request, HttpSession session, Model md);
	
}
