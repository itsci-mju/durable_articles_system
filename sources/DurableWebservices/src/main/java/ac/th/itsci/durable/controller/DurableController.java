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
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ac.th.itsci.durable.entity.*;
import ac.th.itsci.durable.repo.*;
import ac.th.itsci.durable.service.EncodeService;
import ac.th.itsci.durable.util.ResponseObj;

@RestController
@Controller
@RequestMapping(path = "/durable")
public class DurableController {

	@Autowired
	DurableRepository mDurableRepository;

	@Autowired
	StaffRepository mStaffRepository;

	@Autowired
	RoomRepository mRoomRepository;
	
	@Autowired
	private EncodeService encodeservice;
	
	Properties properties = new Properties();

	@PostMapping(path = "/detail")
	public @ResponseBody ResponseObj Detail(@RequestBody String durablequery)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String durablequerys = durablequery.replaceAll("\"", "");
		String[] data = durablequerys.split(",");

		String durablecode = URLDecoder.decode(data[0], "UTF-8");
		int majorID = Integer.parseInt(data[1]);

		Durable durable = mDurableRepository.getDetailDurableByMajor(durablecode, majorID);

		Durable durables = new Durable();

		if (durable == null) {
			return new ResponseObj(500, "notfount");
		} else {
			if (durable.getNote() == null) {
				durables.setNote("-");
			} else  {
				durables.setNote(URLEncoder.encode(durable.getNote(),"UTF-8"));
			}
			
			if (durable.getDurable_code().equalsIgnoreCase("") || durable.getDurable_code() == null) {
				durables.setDurable_code("-");
			} else {
				durables.setDurable_code(URLEncoder.encode(durable.getDurable_code(), "UTF-8"));
			}
			if (durable.getDurable_Borrow_Status().equalsIgnoreCase("") || durable.getDurable_Borrow_Status() == null) {
				durables.setDurable_Borrow_Status("-");
			} else {
				durables.setDurable_Borrow_Status(URLEncoder.encode(durable.getDurable_Borrow_Status(), "UTF-8"));
			}
			if (durable.getDurable_brandname().equalsIgnoreCase("") || durable.getDurable_brandname() == null) {
				durables.setDurable_brandname("-");
			} else {
				durables.setDurable_brandname(URLEncoder.encode(durable.getDurable_brandname(), "UTF-8"));
			}
			if (durable.getDurable_entrancedate().equalsIgnoreCase("") || durable.getDurable_entrancedate() == null) {
				durables.setDurable_entrancedate("-");
			} else {
				durables.setDurable_entrancedate(URLEncoder.encode(durable.getDurable_entrancedate(), "UTF-8"));
			}
			if (durable.getDurable_image() == null || durable.getDurable_image().equalsIgnoreCase("")) {
				durables.setDurable_image("-");
			} else {
				durables.setDurable_image(URLEncoder.encode(durable.getDurable_image(), "UTF-8"));
			}
			if (durable.getDurable_model().equalsIgnoreCase("") || durable.getDurable_model() == null) {
				durables.setDurable_model("-");
			} else {
				durables.setDurable_model(URLEncoder.encode(durable.getDurable_model(), "UTF-8"));
			}
			if (durable.getDurable_name().equalsIgnoreCase("") || durable.getDurable_name() == null) {
				durables.setDurable_name("-");
			} else {
				durables.setDurable_name(URLEncoder.encode(durable.getDurable_name(), "UTF-8"));
			}
			if (durable.getDurable_number().equalsIgnoreCase("") || durable.getDurable_number() == null) {
				durables.setDurable_number("-");
			} else {
				durables.setDurable_number(URLEncoder.encode(durable.getDurable_number(), "UTF-8"));
			}
			if (durable.getDurable_price().equalsIgnoreCase("") || durable.getDurable_price() == null) {
				durables.setDurable_price("-");
			} else {
				durables.setDurable_price(URLEncoder.encode(durable.getDurable_price(), "UTF-8"));
			}
			if (durable.getDurable_statusnow().equalsIgnoreCase("") || durable.getDurable_statusnow() == null) {
				durables.setDurable_statusnow("-");
			} else {
				durables.setDurable_statusnow(URLEncoder.encode(durable.getDurable_statusnow(), "UTF-8"));
			}
			if (durable.getResponsible_person().equalsIgnoreCase("") || durable.getResponsible_person() == null) {
				durables.setResponsible_person("-");
			} else {
				durables.setResponsible_person(URLEncoder.encode(durable.getResponsible_person(), "UTF-8"));
			}
			// Room room = mRoomRepository.RoomDetailByRoomID(d[11]);

			durables.setRoom(new Room(URLEncoder.encode(durable.getRoom().getRoom_number(), "UTF-8"),
					URLEncoder.encode(durable.getRoom().getRoom_name(), "UTF-8"),
					URLEncoder.encode(durable.getRoom().getBuild(), "UTF-8"),
					URLEncoder.encode(durable.getRoom().getFloor(), "UTF-8")));

			durables.setMajor(new Major(durable.getMajor().getID_Major(),
					URLEncoder.encode(durable.getMajor().getMajor_Name(), "UTF-8")));

		}
		return new ResponseObj(200, durables);
	}

	@PostMapping(path = "/responsiblepersonlist")
	public @ResponseBody ResponseObj responsible_personlists(@RequestBody int idmajor)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		int idmajors = idmajor;
		System.out.println(idmajor);

		List<String> Responsibleperson_Detail = mDurableRepository.getResponsibleperson_Detail(idmajors);

		if (Responsibleperson_Detail.size() == 0) {
			return new ResponseObj(500, "");
		} else {
			List<String> listperson = new ArrayList<>();
			for (int i = 0; i < Responsibleperson_Detail.size(); i++) {
				if (Responsibleperson_Detail.get(i).equalsIgnoreCase("")
						|| "".equalsIgnoreCase(Responsibleperson_Detail.get(i))) {
					listperson.add("-");
				} else {
					listperson.add(URLEncoder.encode(Responsibleperson_Detail.get(i), "UTF-8"));
				}
			}

			return new ResponseObj(200, listperson);
		}
	}


	@GetMapping("/images/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
		// Load file as Resource

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
		properties.load(inputStream);
		String uriUploadImageDurable = properties.getProperty("uriUploadImageDurable");

		Path fileStorageLocations = Paths.get(uriUploadImageDurable).toAbsolutePath().resolve(fileName)
				.normalize();

		Resource resource = new UrlResource(fileStorageLocations.toUri());

		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
	public @ResponseBody ResponseObj addtype(@RequestParam("file") MultipartFile file)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		if (!file.isEmpty()) {

			
			try {
				
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
				properties.load(inputStream);
				String uriUploadImageDurable = properties.getProperty("uriUploadImageDurable");
				
				String originalname = file.getOriginalFilename();
				String durableCode = originalname.replaceAll("_", "/");

				String durableCode2 = durableCode.replaceAll(".jpg", "");

				String path = uriUploadImageDurable+ "/" + originalname;

				File convertFile = new File(path);
				convertFile.createNewFile();

				FileOutputStream fout = new FileOutputStream(convertFile);
				fout.write(file.getBytes());
				fout.close();

				mDurableRepository.UpdateDurableImage("/durable/images/" + originalname, durableCode2);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseObj(200, "upload sucess");
	}

	@PostMapping(path = "/durableList", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj AllDataDurable(@RequestBody String json)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		List<Durable> listdata = null;
		List<Durable> durableList = mDurableRepository.getDurable_DetailByIDmajor(Integer.parseInt(json));

		if (durableList.size() == 0) {
			return new ResponseObj(500,"not found data");
		} else {
			listdata = (List<Durable>) encodeservice.EncodeDurable(durableList);			
			return new ResponseObj(200, listdata);
		}
	}

	@PostMapping(path = "/updatedurable", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj updateDurableData(@RequestBody Durable durable)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		Durable d = durable;

//		System.out.println("durable endate :" + URLDecoder.decode(d.getDurable_entrancedate(), "UTF-8"));

		Durable durabledetail = new Durable();

		durabledetail.setDurable_code(URLDecoder.decode(d.getDurable_code(), "UTF-8"));
		durabledetail.setDurable_name(URLDecoder.decode(d.getDurable_name(), "UTF-8"));
		durabledetail.setDurable_number(URLDecoder.decode(d.getDurable_number(), "UTF-8"));
		durabledetail.setDurable_brandname(URLDecoder.decode(d.getDurable_brandname(), "UTF-8"));
		durabledetail.setDurable_model(URLDecoder.decode(d.getDurable_model(), "UTF-8"));
		durabledetail.setDurable_price(URLDecoder.decode(d.getDurable_price(), "UTF-8"));
		durabledetail.setDurable_statusnow(URLDecoder.decode(d.getDurable_statusnow(), "UTF-8"));
		durabledetail.setResponsible_person(URLDecoder.decode(d.getResponsible_person(), "UTF-8"));
		durabledetail.setDurable_Borrow_Status(URLDecoder.decode(d.getDurable_Borrow_Status(), "UTF-8"));
		durabledetail.setDurable_entrancedate(URLDecoder.decode(d.getDurable_entrancedate(), "UTF-8"));

		
		int result = mDurableRepository.UpdateDurableData(durabledetail.getDurable_name(),
				durabledetail.getDurable_number(), durabledetail.getDurable_brandname(),
				durabledetail.getDurable_model(), durabledetail.getDurable_price(),
				durabledetail.getDurable_statusnow(), durabledetail.getResponsible_person(),
				durabledetail.getDurable_entrancedate(), durabledetail.getDurable_Borrow_Status(),
				d.getRoom().getRoom_number(),"-", durabledetail.getDurable_code());

		if (result == 1) {
			return new ResponseObj(200, "update durable success");
		} else {
			return new ResponseObj(500, "dont update data");
		}

	}
}
