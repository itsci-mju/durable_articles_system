package ac.th.itsci.durable.service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

public interface RepairWebService {
		public ModelAndView call_listDilapidatedDurable(ServletRequest request, HttpSession session);
}
