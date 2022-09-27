package ac.th.itsci.durable.service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

public interface StaffWebService {
		public ModelAndView call_login(ServletRequest request, HttpSession session);
		
		public ModelAndView call_logout(ServletRequest request, HttpSession session);
		
		public ModelAndView call_checkStaffSession(HttpSession session);
}
