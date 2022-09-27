package ac.th.itsci.durable.service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public interface DurableWebService {
	
	public ModelAndView callDurableList(String major_id, String room_id, HttpSession session, ServletRequest request);
	
	public ModelAndView call_UpdateDurableDetail(ServletRequest request, HttpSession session);
	
	public ModelAndView call_PrintQrCodeDurable (ServletRequest request, HttpSession session, HttpServletResponse response);
	
	public ModelAndView call_ImportFileDurable (ServletRequest request, HttpServletRequest requesth,MultipartFile[] file_excel);
	
	public void call_ExportFileExcelDurable (ServletRequest request, HttpServletResponse response, HttpSession session);
}
