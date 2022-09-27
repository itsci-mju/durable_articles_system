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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import ac.th.itsci.durable.entity.Company;
import ac.th.itsci.durable.entity.Durable;
import ac.th.itsci.durable.entity.RepairDurable;
import ac.th.itsci.durable.repo.DurableRepository;
import ac.th.itsci.durable.repo.RepairRepository;
import ac.th.itsci.durable.util.ResponseObj;

@RestController
@Controller 
@RequestMapping(path = "/repair")
public class RepairController {

	private static final Logger logger = LoggerFactory.getLogger(RepairController.class);
	
	@Autowired
	RepairRepository mRepairRepository;
	
	@Autowired
	DurableRepository mDurableRepository;
	
	Properties properties = new Properties();
	
	@PostMapping(path = "/doSaveRapair")
	public @ResponseBody ResponseObj responsible_personlists(@RequestBody String repair)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String data = repair.replaceAll("\"", "");
		String[] datarepari = data.split(",");
		
		Durable durable = mDurableRepository.detaildurablebydurablecodes(URLDecoder.decode(datarepari[0],"UTF-8"));
		
		mDurableRepository.UpdateDurableStatus("ชำรุด",durable.getDurable_code());
				
		RepairDurable repairs = new RepairDurable();
		
		repairs.setDate_of_repair(URLDecoder.decode(datarepari[1],"UTF-8"));
		repairs.setRepair_title(URLDecoder.decode(datarepari[2],"UTF-8"));
		repairs.setRepair_detail(URLDecoder.decode(datarepari[3],"UTF-8"));
		repairs.setRepair_status(URLDecoder.decode(datarepari[4],"UTF-8"));
		repairs.setDurable(durable); 
		
		mRepairRepository.save(repairs);
		
		System.out.println(repairs.getDurable().getDurable_code());
		String id = mRepairRepository.getIdRepair(repairs.getDurable().getDurable_code(), repairs.getDate_of_repair());
		
		return new ResponseObj(200,id);
	}
	
	@PostMapping(path = "/RepairDurableList")
	public @ResponseBody ResponseObj RepairDurableLists(@RequestBody String durablecode)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String replaceString  = durablecode.replaceAll("\"", "");
		String Durablecode = URLDecoder.decode(replaceString,"UTF-8");
		
		List<RepairDurable> repairdurable = mRepairRepository.getListRepair(Durablecode);
		List<RepairDurable> lRepairDurable = new ArrayList<>();
		if(repairdurable.size() == 0) {
			return new ResponseObj(500,"not found");
		}else {
			for(int i = 0 ; i < repairdurable.size() ; i++) {
				RepairDurable re = new RepairDurable();
				
				if(repairdurable.get(i).getRepair_date() == null) {
					repairdurable.get(i).setRepair_date("-");
				}
				if(repairdurable.get(i).getRepair_title() == null) {
					repairdurable.get(i).setRepair_title("-");
				}
				if(repairdurable.get(i).getRepair_charges() == null || repairdurable.get(i).getRepair_charges().equalsIgnoreCase("")) {
					repairdurable.get(i).setRepair_charges("-");
				}
				if(repairdurable.get(i).getRepair_detail() == null) {
					repairdurable.get(i).setRepair_detail("-");
				}
				if(repairdurable.get(i).getPicture_invoice() == null) {
					repairdurable.get(i).setPicture_invoice("-");
				}
				if(repairdurable.get(i).getPicture_repairreport() == null || repairdurable.get(i).getPicture_repairreport().equalsIgnoreCase("")) {
					repairdurable.get(i).setPicture_repairreport("-");
				}
				if(repairdurable.get(i).getPicture_quatation() == null || repairdurable.get(i).getPicture_quatation().equalsIgnoreCase("")) {
					repairdurable.get(i).setPicture_quatation("-");
				}
				if(repairdurable.get(i).getPicture_repair() == null || repairdurable.get(i).getPicture_repair().equalsIgnoreCase("")) {
					repairdurable.get(i).setPicture_repair("-");
				}
				if(repairdurable.get(i).getDate_of_repair() == null || repairdurable.get(i).getDate_of_repair().equalsIgnoreCase("")) {
					repairdurable.get(i).setDate_of_repair("-");
				}
				re.setRepair_id(repairdurable.get(i).getRepair_id());
				re.setRepair_date(URLEncoder.encode(repairdurable.get(i).getRepair_date(),"UTF-8"));
				re.setRepair_title(URLEncoder.encode(repairdurable.get(i).getRepair_title(),"UTF-8"));
				re.setRepair_charges(URLEncoder.encode(repairdurable.get(i).getRepair_charges(),"UTF-8"));
				re.setRepair_detail(URLEncoder.encode(repairdurable.get(i).getRepair_detail(),"UTF-8"));
				re.setPicture_invoice(URLEncoder.encode(repairdurable.get(i).getPicture_invoice(),"UTF-8"));
				re.setPicture_repairreport(URLEncoder.encode(repairdurable.get(i).getPicture_repairreport(),"UTF-8"));
				re.setPicture_quatation(URLEncoder.encode(repairdurable.get(i).getPicture_quatation(),"UTF-8"));
				re.setPicture_repair(URLEncoder.encode(repairdurable.get(i).getPicture_repair(),"UTF-8"));
				re.setDate_of_repair(URLEncoder.encode(repairdurable.get(i).getDate_of_repair(),"UTF-8"));
				re.setRepair_status(repairdurable.get(i).getRepair_status());
				
				if(repairdurable.get(i).getCompany() == null) {
					re.setCompany(null);
				}else {
					re.setCompany(new Company(repairdurable.get(i).getCompany().getCompany_id(),
						URLEncoder.encode(repairdurable.get(i).getCompany().getCompanyname(),"UTF-8")));
				}
				
				
				re.setDurable(new Durable(URLEncoder.encode(repairdurable.get(i).getDurable().getDurable_code(),"UTF-8"),
					     URLEncoder.encode(repairdurable.get(i).getDurable().getDurable_name(),"UTF-8"),
					     URLEncoder.encode(repairdurable.get(i).getDurable().getDurable_number(),"UTF-8"),
					     URLEncoder.encode(repairdurable.get(i).getDurable().getDurable_brandname(),"UTF-8"),
					     URLEncoder.encode(repairdurable.get(i).getDurable().getDurable_model(),"UTF-8"),
					     URLEncoder.encode(repairdurable.get(i).getDurable().getDurable_price(),"UTF-8"),
					     URLEncoder.encode(repairdurable.get(i).getDurable().getDurable_statusnow(),"UTF-8"),
					     URLEncoder.encode(repairdurable.get(i).getDurable().getResponsible_person(),"UTF-8"),
					     "-",
					     URLEncoder.encode(repairdurable.get(i).getDurable().getDurable_Borrow_Status(),"UTF-8"),
					     URLEncoder.encode(repairdurable.get(i).getDurable().getDurable_entrancedate(),"UTF-8")));
				 
				 lRepairDurable.add(re);
			}
		}
		
		return new ResponseObj(200,lRepairDurable);
	}
	
	@RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
	public @ResponseBody ResponseObj addtype (@RequestParam("file") MultipartFile file)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		if (!file.isEmpty()) {

			try {
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
				properties.load(inputStream);
				String urlUpdateBorrowInProperties = properties.getProperty("uriUploadImageRepair");
				
				String originalname = file.getOriginalFilename();
				String repairid = originalname.replaceAll("_", "/");
				
				String replaceid = repairid.replaceAll(".jpg", "");
				String idrepair = replaceid.replaceAll("repair.", "");
								
				String path = urlUpdateBorrowInProperties+ "/" +originalname;
				
				File convertFile = new File(path);
				convertFile.createNewFile();
			
				FileOutputStream fout = new FileOutputStream(convertFile);
				fout.write(file.getBytes());
				fout.close();
					
				mRepairRepository.updateRepairImage("/repair/images/"+originalname, Integer.parseInt(idrepair));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		return new ResponseObj(200, "upload sucess");
	}
	
	
	@GetMapping("/images/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request)
			throws IOException {

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
		properties.load(inputStream);
		String urlUpdateBorrowInProperties = properties.getProperty("uriUploadImageRepair");

		Path fileStorageLocations = Paths.get(urlUpdateBorrowInProperties).toAbsolutePath().resolve(fileName)
				.normalize();

		Resource resource = new UrlResource(fileStorageLocations.toUri());

		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}

		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@RequestMapping(value = "/checkrepair", method = RequestMethod.POST)
	public @ResponseBody ResponseObj checkrepairs(@RequestBody String durablecodes)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		String replac = durablecodes.replaceAll("\"", "");
		String durablecode = URLDecoder.decode(replac,"UTF-8");
	
		RepairDurable re = mRepairRepository.Repair_check(durablecode);
		
		if(re == null) {
			return new ResponseObj(500, "");
		}else {
			return new ResponseObj(200, "");
		}
		
		
	}
	
}
