package ac.th.itsci.durable.service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

public interface VerifyWebService {
	public ModelAndView call_getAllVerify(HttpSession session);
	
	public ModelAndView call_createVerify(ServletRequest request, HttpSession session);
	
	public ModelAndView call_updateVerify(ServletRequest request, HttpSession session);
}
