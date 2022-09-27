package ac.th.itsci.durable.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ac.th.itsci.durable.entity.Login;
import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.repo.LoginRepository;
import ac.th.itsci.durable.repo.StaffRepository;
import ac.th.itsci.durable.util.PasswordUtil;
import ac.th.itsci.durable.util.ResponseObj;

@Controller
@RequestMapping(path = "/staff")
public class StaffController {


	@Autowired
	private StaffRepository mStaffRepository;
	
	@Autowired
	private LoginRepository mLoginRepository;

	Properties properties = new Properties();

	private static String SALT = "123456";

	@PostMapping(path = "/checkStaffbyIDcard")
	public @ResponseBody ResponseObj checkStaffByidCard(@RequestBody String idCard) {

		String idCardReplace = idCard.replaceAll("\"", "");
		Staff s = mStaffRepository.StaffDetailByIDcard(idCardReplace);

		if (s != null) {
			return new ResponseObj(200, "0");
		} else {
			return new ResponseObj(200, "1");
		}
	}

	@PostMapping(path = "/staffdetail")
	public @ResponseBody ResponseObj login(@RequestBody String username)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String str = username.replaceAll("\"", "");

		String[] staff = mStaffRepository.StaffDetailByUsername(str);
		Staff staffdetail = new Staff();
		String id_major = "";

		for (String s : staff) {

			String[] array = s.split(",");

			staffdetail.setId_card(URLEncoder.encode(array[0], "UTF-8"));
			staffdetail.setBrithday(URLEncoder.encode(array[1], "UTF-8"));
			staffdetail.setEmail(URLEncoder.encode(array[2], "UTF-8"));
			staffdetail.setImage_staff(URLEncoder.encode(array[3], "UTF-8"));
			staffdetail.setPhone_number(URLEncoder.encode(array[4], "UTF-8"));
			staffdetail.setStaff_lastname(URLEncoder.encode(array[5], "UTF-8"));
			staffdetail.setStaff_name(URLEncoder.encode(array[6], "UTF-8"));
			staffdetail.setStaff_status(URLEncoder.encode(array[7], "UTF-8"));
			id_major = URLEncoder.encode(array[8], "UTF-8");

		}

		String[] major = mStaffRepository.MajorDetailByID(Integer.parseInt(id_major));

		Major majors = new Major();

		for (String n : major) {
			String[] majordetail = n.split(",");
			majors.setID_Major(Integer.parseInt(majordetail[0]));
			majors.setMajor_Name(URLEncoder.encode(majordetail[1], "UTF-8"));

		}
		staffdetail.setMajor(majors);

		if (staff.length == 0) {
			return new ResponseObj(500, "not fount");
		} else {
			return new ResponseObj(200, staffdetail);
		}

	}

	@GetMapping("/images/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request)
			throws IOException {
		String contentType = null;
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
		properties.load(inputStream);
		String uriUploadImageStaff = properties.getProperty("uriUploadImageStaff");

		Path fileStorageLocations = Paths.get(uriUploadImageStaff).toAbsolutePath().resolve(fileName)
				.normalize();

		Resource resource = new UrlResource(fileStorageLocations.toUri());

		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@PostMapping(path = "/updateProfile")
	public @ResponseBody ResponseObj updateprodiles(@RequestBody Staff staff)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		Staff s = staff;

		String stffname = URLDecoder.decode(s.getStaff_name(), "UTF-8");
		String lastname = URLDecoder.decode(s.getStaff_lastname(), "UTF-8");
		String Brithday = URLDecoder.decode(s.getBrithday(), "UTF-8");
		
		System.out.println(s.getId_card());

		int resultupdate = mStaffRepository.updateProfile(stffname, lastname, s.getEmail(), Brithday,
				s.getPhone_number(), s.getId_card());

		if (resultupdate == 1) {
			Staff staffupdate = mStaffRepository.StaffDetailByIDcard(s.getId_card());

			staffupdate.setId_card(staffupdate.getId_card());
			staffupdate.setStaff_name(URLEncoder.encode(staffupdate.getStaff_name(), "UTF-8"));
			staffupdate.setStaff_lastname(URLEncoder.encode(staffupdate.getStaff_lastname(), "UTF-8"));
			staffupdate.setBrithday(URLEncoder.encode(staffupdate.getBrithday(), "UTF-8"));
			staffupdate.setStaff_status(URLEncoder.encode(staffupdate.getStaff_status(), "UTF-8"));
			staffupdate.setEmail(staffupdate.getEmail());
			staffupdate.setPhone_number(staffupdate.getPhone_number());
			staffupdate.setImage_staff(URLEncoder.encode(staffupdate.getImage_staff(), "UTF-8"));
			staffupdate.getMajor().setID_Major(staffupdate.getMajor().getID_Major());
			staffupdate.getMajor().setMajor_Name(URLEncoder.encode(staffupdate.getMajor().getMajor_Name(), "UTF-8"));
			
			return new ResponseObj(200, staffupdate);
		} else {
			return new ResponseObj(500, " ");
		}

	}

	@PostMapping(path = "/register")
	public @ResponseBody ResponseObj doregister(@RequestBody Staff staff)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		Login login = new Login();
		login.setUsername(staff.getLogin().getUsername());
		login.setPassword(PasswordUtil.getInstance().createPassword(staff.getLogin().getPassword(), SALT));
		login.setStatus(staff.getLogin().getStatus());
		Login result = mLoginRepository.save(login);
		
		if (result != null) {
			
			List<Staff> s = (List<Staff>) mStaffRepository.findAll();
						
			Staff staffs = new Staff();
			staffs.setId_staff((s.size()+1));
			staffs.setId_card(staff.getId_card());
			staffs.setStaff_name(URLDecoder.decode(staff.getStaff_name(), "UTF-8"));
			staffs.setStaff_lastname(URLDecoder.decode(staff.getStaff_lastname(), "UTF-8"));
			staffs.setStaff_status(URLDecoder.decode(staff.getStaff_status(), "UTF-8"));
			staffs.setEmail(staff.getEmail());
			staffs.setBrithday(URLDecoder.decode(staff.getBrithday(), "UTF-8"));
			staffs.setPhone_number(staff.getPhone_number());
			staffs.setImage_staff(staff.getImage_staff());

			Major major = new Major();
			major.setID_Major(staff.getMajor().getID_Major());
			major.setMajor_Name(URLDecoder.decode(staff.getMajor().getMajor_Name(), "UTF-8"));

			staffs.setLogin(result);
			staffs.setMajor(major);

			mStaffRepository.save(staffs);

			return new ResponseObj(200, "success");
		} else {
			return new ResponseObj(500,"fail");
		}

		
	}

	@RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
	public @ResponseBody ResponseObj addtype(@RequestParam("file") MultipartFile file)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		if (!file.isEmpty()) {

			try {
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
				properties.load(inputStream);
				String uriUploadImageStaff = properties.getProperty("uriUploadImageStaff");

				String originalname = file.getOriginalFilename();
				String idcard = originalname.replaceAll(".jpg", "");

				String path = uriUploadImageStaff + "/" + originalname;

				File convertFile = new File(path);
				convertFile.createNewFile();

				FileOutputStream fout = new FileOutputStream(convertFile);
				fout.write(file.getBytes());
				fout.close();

				mStaffRepository.UpdateStaffImage("/staff/images/" + originalname, idcard);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return new ResponseObj(200, "upload sucess");
	}
}
