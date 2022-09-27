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
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
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
import org.springframework.web.multipart.MultipartFile;

import ac.th.itsci.durable.entity.Borrower;
import ac.th.itsci.durable.repo.BorrowerRepository;
import ac.th.itsci.durable.util.ResponseObj;

@Controller
@RequestMapping(path = "/borrowers")
public class BorrowerController {

	private static final Logger logger = LoggerFactory.getLogger(BorrowerController.class);


	@Autowired
	BorrowerRepository mBorrowerRepository;

	Properties properties = new Properties();

	
	@PostMapping(path = "/checkBorrower")
	public @ResponseBody ResponseObj checkBorrower(@RequestBody String idcard)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String idcards = idcard.replaceAll("\"", "");

		Borrower borrower = mBorrowerRepository.checkBorrower(idcards);
		if (borrower == null) {
			return new ResponseObj(500, "not found");
		} else {
			borrower.setBorrower_name(URLEncoder.encode(borrower.getBorrower_name(), "UTF-8"));
			borrower.setBorrower_picture(URLEncoder.encode(borrower.getBorrower_picture(), "UTF-8"));
			return new ResponseObj(200, borrower);
		}

	}

	@PostMapping(path = "/saveBorrowers")
	public @ResponseBody ResponseObj saveBorrower(@RequestBody String borrower)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		JSONObject jsonObject = new JSONObject(borrower);
		System.out.println(borrower);
		String borrower_name = jsonObject.getString("borrower_name");
		String idcard_borrower = jsonObject.getString("idcard_borrower");
		String telephonenumber = jsonObject.getString("telephonenumber");
	
		Borrower borrowers = new Borrower();
		borrowers.setBorrower_name(URLDecoder.decode(borrower_name,"UTF-8"));
		borrowers.setIdcard_borrower(idcard_borrower);
		borrowers.setTelephonenumber(telephonenumber);
		Borrower b = mBorrowerRepository.save(borrowers);
	
		return new ResponseObj(200, b.getIdcard_borrower());
	}

	@GetMapping("/images/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request)
			throws IOException {

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
		properties.load(inputStream);
		String urlUpdateBorrowInProperties = properties.getProperty("uriUploadImageBorrower");

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

	@RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
	public @ResponseBody ResponseObj addtype(@RequestParam("file") MultipartFile file)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		if (!file.isEmpty()) {

			try {

				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
				properties.load(inputStream);
				String urlUpdateBorrowInProperties = properties.getProperty("uriUploadImageBorrower");

				String originalname = file.getOriginalFilename();
				String img = originalname.replaceAll("Borrowers.", "");

				String borrowerimg = img.replaceAll(".jpg", "");
				String path = urlUpdateBorrowInProperties +"/"+ originalname;

				File convertFile = new File(path);
				convertFile.createNewFile();

				FileOutputStream fout = new FileOutputStream(convertFile);
				fout.write(file.getBytes());
				fout.close();

				mBorrowerRepository.updateBorrower_picture("/borrowers/images/" + originalname, borrowerimg);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return new ResponseObj(200, "upload sucess");
	}

}
