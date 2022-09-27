package ac.th.itsci.durable.app.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.th.itsci.durable.app.manager.StaffManager;
import ac.th.itsci.durable.app.manager.UserManager;
import ac.th.itsci.durable.entity.Login;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.util.PasswordUtil;
import ac.th.itsci.durable.util.ResponseObj;



@Controller
@RequestMapping(path = "/applogin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	private static String SALT = "123456";
/*
	@RequestMapping(value = "/user/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj doRegisterstaff(@RequestBody Map<String, String> map) {
		String message = "";
		Staff staff = null;
		
		try {
			int id_staff = Integer.parseInt(map.get("id_staff"));
			String staff_name = map.get("staff_name");
			String staff_lastname = map.get("staff_lastname");
			String email = map.get("email");
			String id_card = map.get("id_card");
			String birthday = map.get("birthday");
			String phone_number = map.get("phone_number");
			String staff_status = map.get("staff_status");
			String image_staff = map.get("image_staff");
			int id_major = Integer.parseInt(map.get("id_major"));
			
			// String password = map.get("password");
			// phone_number = PasswordUtil.getInstance().createPassword(phone_number, SALT);

			System.out.println(id_staff + " " + staff_name + " " + staff_lastname + " " + email + " " + id_card + " "
					+ birthday + " " + phone_number + " " + staff_status + " " + image_staff);
			staff = new staff (id_staff,staff_name,staff_lastname,email,id_card,birthday,phone_number,staff_status,image_staff);
			UserManager rm = new UserManager();
			major m = new major();
			m = rm.listbymajor(id_major);		
			staff.setMajor(m);
			message = rm.insertstaff(staff);	
			return new ResponseObj(200, staff);
		} catch (Exception e) {
			e.printStackTrace();
			message = "Please try again....";
			return new ResponseObj(500, staff);
		}
	}

	@RequestMapping(value = "/user/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_editprofile(@RequestBody Map<String, String> map) {
		String message = "";
		User user = null;
		try {
			String id = map.get("id");
			String firstname = map.get("firstname");
			String lastname = map.get("lastname");
			String email = map.get("email");
			String password = map.get("password");
			password = PasswordUtil.getInstance().createPassword(password, SALT);

			System.out.println(id + " " + firstname + " " + lastname + " " + email + " " + password);
			user = new User(id, firstname, lastname, email, password);
			UserManager rm = new UserManager();
			message = rm.editUser(user);
			return new ResponseObj(200, user);
		} catch (Exception e) {
			e.printStackTrace();
			message = "Please try again....";
			return new ResponseObj(500, user);
		}
	}
*/

	@PostMapping(path = "/user/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_login(@RequestBody Map<String, String> map) 
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String message = "";
		Login login = null;
		try {
			String id = map.get("username");
			String password = map.get("password");
			password = PasswordUtil.getInstance().createPassword(password, SALT);

			login = new Login(id, password, "");
			UserManager rm = new UserManager();
			message = rm.doHibernateLogin(login);
			if ("login success".equals(message)) {
				return new ResponseObj(200, "1");
			} else {
				return new ResponseObj(200, "0");
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = "Please try again....";
			return new ResponseObj(500, "0");
		}
	}
	@PostMapping(path = "/staff/liststaffbyusername")
	public @ResponseBody ResponseObj getStafbyUsername(@RequestBody Map<String, String> map ,HttpServletRequest request) {
		Staff staff = null;
		try {
			String username = map.get("username");
			StaffManager sm = new StaffManager();
	
			//staff =  sm.listStaffbyusername("admin@gmail.com");
			staff =  sm.listStaffbyusername2(username);
			System.out.println();
			return new ResponseObj(200, staff);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, staff);
	}
	
	@PostMapping(value = "/staff/liststaffbyusername2")
	public @ResponseBody ResponseObj getStafbyUsername2(@RequestBody Map<String, String> map ) {
		Staff staff = null;
		try {
			//String username = map.get("username");
			StaffManager sm = new StaffManager();
	
			staff =  sm.listStaffbyusername("admin@gmail.com");
			//staff =  sm.listStaffbyusername(username);
			System.out.println(staff.toString());
			return new ResponseObj(200, staff);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, staff);
	}
	@PostMapping(value = "/user/list")
	public @ResponseBody ResponseObj do_listUsers(HttpServletRequest request) {
		List<Staff> staff = null;
		try {
			UserManager rm = new UserManager();
			staff = rm.listAllUsers();
			System.out.println(staff.toString());
			return new ResponseObj(200, staff);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, staff);
	}

	/*
	@RequestMapping(value = "/user/listlogin", method = RequestMethod.POST)
	public @ResponseBody ResponseObj do_listLogin(HttpServletRequest request) {
		List<Login> login = null;
		try {
			UserManager rm = new UserManager();
			login = rm.listAllLogin();
			System.out.println(login.toString());
			return new ResponseObj(200, login);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, login);
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_login(@RequestBody Map<String, String> map) {
		String message = "";
		Login login = null;
		try {
			String id = map.get("username");
			String password = map.get("password");
			password = PasswordUtil.getInstance().createPassword(password, SALT);

			login = new Login(id, password, "");
			UserManager rm = new UserManager();
			message = rm.doHibernateLogin(login);
			if ("login success".equals(message)) {
				return new ResponseObj(200, "1");
			} else {
				return new ResponseObj(200, "0");
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = "Please try again....";
			return new ResponseObj(500, "0");
		}
	}
	*/
	
	/*
	@RequestMapping(value = "/user/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_delete(@RequestBody Map<String, String> map) {
		String message = "";
		User user = null;
		try {
			String id = map.get("id");

			UserManager rm = new UserManager();
			user = rm.getUserProfile(id);
			message = rm.deleteUser(user);
			if ("successfully delete".equals(message)) {
				return new ResponseObj(200, "1");
			} else {
				return new ResponseObj(200, "0");
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = "Please try again....";
			return new ResponseObj(500, "0");
		}
	}

	@RequestMapping(value = "/user/getprofile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_getProfile(@RequestBody Map<String, String> map) {
		User user = null;
		try {
			String id = map.get("id");

			UserManager rm = new UserManager();
			user = rm.getUserProfile(id);

			if (user != null) {
				return new ResponseObj(200, user);
			} else {
				return new ResponseObj(200, null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseObj(500, null);
		}
	}*/

}
