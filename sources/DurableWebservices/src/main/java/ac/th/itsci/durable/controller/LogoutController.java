package ac.th.itsci.durable.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.th.itsci.durable.entity.Login;
import ac.th.itsci.durable.repo.LoginRepository;
import ac.th.itsci.durable.util.ResponseObj;

@Controller
@RequestMapping(path = "/logout")
public class LogoutController {
	
	@Autowired
	private LoginRepository mLoginRepository;
	
	@PostMapping(path = "/logouts")
	public @ResponseBody ResponseObj logout(@RequestBody Login login)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		Login logins = login;

		int dologins = mLoginRepository.logout(login.getStatus(), login.getUsername());

		if (dologins == 0) {
			return new ResponseObj(500, "Invalid id/Password.");
		} else {
			
			return new ResponseObj(200, logins);
		}
	}

}
