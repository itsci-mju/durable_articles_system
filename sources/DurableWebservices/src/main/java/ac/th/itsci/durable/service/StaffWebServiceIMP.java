package ac.th.itsci.durable.service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import ac.th.itsci.durable.entity.Login;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.repo.LoginRepository;
import ac.th.itsci.durable.repo.StaffRepository;
import ac.th.itsci.durable.util.PasswordUtil;

@Service
public class StaffWebServiceIMP implements StaffWebService {

	@Autowired
	LoginRepository loginRepo;

	@Autowired
	StaffRepository staffRepo;

	@Override
	public ModelAndView call_login(ServletRequest request, HttpSession session) {
		ModelAndView mav = null;
		try {
			String SALT = "123456";
			request.setCharacterEncoding("UTF-8");

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String passwords = PasswordUtil.getInstance().createPassword(password, SALT);

			Login login_verify = loginRepo.login_by_username_password(username, passwords);

			if (login_verify == null) {
				session.setAttribute("value", 1);
				session.setAttribute("message", "ไม่สามารถเข้าสู๋ระบบได้กรุณาตรวจสอบ ชื่อผู้ใช้ หรือรหัสผ่าน  !!!");
				mav = new ModelAndView("index");
			} else if (login_verify != null) {
				Staff staffBean = staffRepo.get_staff_detail_by_username(login_verify.getUsername());
				session.setAttribute("staffSession", staffBean);
				mav = new ModelAndView("home");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@Override
	public ModelAndView call_logout(ServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("index");

		try {
			session.invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@Override
	public ModelAndView call_checkStaffSession(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Staff staffDetail = (Staff) session.getAttribute("staffSession");
		if (staffDetail != null) {
			mav = new ModelAndView("home");
		} else {
			mav = new ModelAndView("index");
		}
		return mav;
	}

}
