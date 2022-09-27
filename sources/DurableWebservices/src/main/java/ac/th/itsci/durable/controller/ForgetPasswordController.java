package ac.th.itsci.durable.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.th.itsci.durable.entity.Login;
import ac.th.itsci.durable.repo.LoginRepository;
import ac.th.itsci.durable.repo.StaffRepository;
import ac.th.itsci.durable.util.PasswordUtil;
import ac.th.itsci.durable.util.ResponseObj;

@Controller
@RequestMapping(path = "/forgetpassword")
public class ForgetPasswordController {
	
	@Autowired
	StaffRepository staffrepositpry;
	
	@Autowired
	LoginRepository loginrepository;
	
	private static String SALT = "123456";

	@PostMapping(path = "/forgetpasswords")
	public @ResponseBody ResponseObj forgetpasswords(@RequestBody String json)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String[] jsonSplite = json.replace("\"", "").replace(",", " ").split(" ");
		String email = URLDecoder.decode(jsonSplite[0],"UTF-8");
		String nameLastname = URLDecoder.decode(jsonSplite[1],"UTF-8")+" "+URLDecoder.decode(jsonSplite[2],"UTF-8");
		
		String[] staff = staffrepositpry.StaffDetailByUsername(email);
		
		if(staff.length == 0) {
			return new ResponseObj(500, "0");
		}else {
			String[] staffs = staff[0].replace(",", " ").split(" ");
			if(nameLastname.equalsIgnoreCase(staffs[6]+" "+staffs[5])) {
				return new ResponseObj(200, "2"+","+URLEncoder.encode(email,"UTF-8"));
			}else {
				return new ResponseObj(500, "1");
			}
		}
	}
	
	@PostMapping(path = "/resetpassword")
	public @ResponseBody ResponseObj resetpassword(@RequestBody String json)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String[] jsonSplite = json.replace("\"", "").replace(",", " ").split(" ");
		String username = URLDecoder.decode(jsonSplite[0],"UTF-8");
		String password = URLDecoder.decode(jsonSplite[1],"UTF-8");
		
		Login login = loginrepository.CheckUsername(URLDecoder.decode(username, "UTF-8"));
		
		String passwordEncription = PasswordUtil.getInstance().createPassword(URLDecoder.decode(password, "UTF-8"), SALT);
		
		if(login.getPassword().equalsIgnoreCase(passwordEncription)) {
			return new ResponseObj(500,"0");
		}else {
			int result = loginrepository.updatepassword(passwordEncription, login.getUsername());
			if(result == 0) {
				return new ResponseObj(500,"1");
			}else if(result == 1) {
				Login logins = new Login(); 
				logins.setUsername(URLDecoder.decode(username, "UTF-8"));
				logins.setPassword(passwordEncription);
				logins.setStatus("offline");
				return new ResponseObj(200,logins);
			}
		}
		return null;
	}

}
