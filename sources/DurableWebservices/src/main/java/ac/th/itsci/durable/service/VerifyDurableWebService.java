package ac.th.itsci.durable.service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

public interface VerifyDurableWebService {
	
	public ModelAndView call_listDurableProfileByYear(ServletRequest request, HttpSession session);

	public ModelAndView call_update_VerifyDurable(ServletRequest request, HttpSession session);
	
	public ModelAndView call_loadListDurableByYear(HttpSession session);
}
