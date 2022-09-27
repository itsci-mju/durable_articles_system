package ac.th.itsci.durable.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ac.th.itsci.durable.app.manager.StaffManager;
import ac.th.itsci.durable.app.manager.UserManager;
import ac.th.itsci.durable.entity.Login;
import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.repo.LoginRepository;
import ac.th.itsci.durable.repo.StaffRepository;
import ac.th.itsci.durable.util.PasswordUtil;
import ac.th.itsci.durable.util.ResponseObj;


@Controller
@RequestMapping(path = "/login", method = RequestMethod.POST)
public class LoginController {

	@Autowired
	private LoginRepository mLoginRepository;

	@Autowired
	private StaffRepository mStaffRepository;

	private static String SALT = "123456";

	@PostMapping(path = "/logindetail")
	public @ResponseBody ResponseObj login(@RequestBody Login login)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		Login logins = login;

		String password = logins.getPassword();

		int dologins = mLoginRepository.logins(login.getStatus(), login.getUsername(), password);

		if (dologins == 0) {
			return new ResponseObj(500, "Invalid id/Password.");
		} else {
			logins.setPassword(password);
			return new ResponseObj(200, logins);
		}
	}

	@PostMapping(path = "/checkusername")
	public @ResponseBody ResponseObj checkUsername(@RequestBody String username)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		Login l = mLoginRepository.CheckUsername(username.replaceAll("\"", ""));

		if (l == null) {
			return new ResponseObj(200, "");
		} else {
			return new ResponseObj(500, "");
		}
	}


	@PostMapping(path = "/updatepasswords")
	public @ResponseBody ResponseObj doupdatepassword(@RequestBody String data)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String datas = data.replaceAll("\"", "");
		String[] updatepassword = datas.split(",");

		String oldpass = PasswordUtil.getInstance().createPassword(updatepassword[1], SALT);

		Login login = mLoginRepository.CheckUsername(updatepassword[0]);

		if (login.getPassword().equalsIgnoreCase(oldpass)) {
			String newpass = PasswordUtil.getInstance().createPassword(updatepassword[2], SALT);

			int result = mLoginRepository.updatepassword(newpass, login.getUsername());

			Login logins = mLoginRepository.CheckUsername(login.getUsername());
			logins.setUsername(logins.getUsername());
			logins.setPassword(updatepassword[2]);
			logins.setStatus(logins.getStatus());
			return new ResponseObj(200, logins);

		} else {
			return new ResponseObj(500, "not success");
		}
	}
	
}
