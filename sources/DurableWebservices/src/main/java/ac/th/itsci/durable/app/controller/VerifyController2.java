package ac.th.itsci.durable.app.controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ac.th.itsci.durable.app.manager.DurableManager;
import ac.th.itsci.durable.app.manager.VerifyManager;
import ac.th.itsci.durable.entity.Durable;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.entity.Verify;
import ac.th.itsci.durable.entity.VerifyDurable;
import ac.th.itsci.durable.entity.verifyinform;
import ac.th.itsci.durable.util.ResponseObj;

@Controller
@RequestMapping(path = "/appverify", method = RequestMethod.POST)
public class VerifyController2 {
	private static String SALT = "123456";

	@PostMapping(path = "/verify/addform", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj doverifyform(@RequestBody Map<String, String> map
			/*,@RequestParam("file") MultipartFile file,ServletRequest request*/) {
		int message = 0;
		VerifyDurable vd = null;

		try {
			String durable_status = map.get("durable_status");
			String save_date = map.get("save_date");
			String note = map.get("note");
			String durable_image = map.get("picture_verify");
			int id_staff = Integer.parseInt(map.get("id_staff"));
			String years = map.get("years");
			String durablecode = map.get("durable_code");
			Date date_now = new Date();
			String DATE_FORMAT = "dd MMMM yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
			if (durable_image.equals("")) {
				durable_image=("-");
			} else {
			}
			
			vd = new VerifyDurable(sdf.format(date_now), durable_status, note, durable_image);
			VerifyManager vm = new VerifyManager();
			Staff s = new Staff();
			s = vm.getstaff2(id_staff);

			Verify v = new Verify();
			v = vm.getverify2(years);

			Durable d = new Durable();
			d = vm.getDurable2(durablecode);

			vd.getPk().setStaff(s);
			vd.getPk().setVerify(v);
			
			vd.getPk().setDurable(d);
			
			
			message = vm.insertverifyform2(vd);
			/*if (!file.isEmpty()) {
				String original_file_name = file.getOriginalFilename();
				String type_image = original_file_name.substring(original_file_name.lastIndexOf("."));
				durable_image = durablecode.replaceAll("/", "_") + "" + type_image;
				System.out.println(durable_image);

				String path = request.getServletContext().getRealPath("/") + "file/durable_image";

				File uploadPic = convert(file, path + "/" + durable_image);

				BufferedImage image = ImageIO.read(uploadPic);
				int width = 0;
				int height = 0;

				if (image.getWidth() > image.getHeight()) {
					width = 400;
					height = 400;
				} else {
					width = 400;
					height = 400;
				}

				BufferedImage imageWrite = getScaledInstance(image, width, height,
						RenderingHints.VALUE_RENDER_QUALITY, true);

				if (type_image.equalsIgnoreCase(".png")) {
					ImageIO.write(imageWrite, "png", new File(path + "/" + durable_image));
				} else if (type_image.equalsIgnoreCase(".jpeg")) {
					ImageIO.write(imageWrite, "jpeg", new File(path + "/" + durable_image));
				} else {
					ImageIO.write(imageWrite, "jpg", new File(path + "/" + durable_image));
				}
			} else {
				durable_image = "-";
			}*/
			
			
			if (message == 1) {
				return new ResponseObj(200, "1");
			} else {
				return new ResponseObj(200, "0");
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = 0;
			return new ResponseObj(500, "0");
		}
	}
	
	@PostMapping(path = "/verify/uploadimage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public @ResponseBody ResponseObj douploadimage(@RequestParam("file") MultipartFile file,@RequestParam("duralbe_code") String code,ServletRequest request) {
		int message = 0;
		VerifyDurable vd = null;

		try {
			String durable_image = code;
			
			String durablecode = "test";
		
			if (code.equals("")) {
				code=("-");
			} else {
			}

			if (!file.isEmpty()) {
				String original_file_name = file.getOriginalFilename();
				String type_image = original_file_name.substring(original_file_name.lastIndexOf("."));
				durable_image = code.replaceAll("/", "_") + "" + ".jpg";
				System.out.println(durable_image);

				String path = request.getServletContext().getRealPath("/") + "file/durable_image";
					
				File uploadPic = convert(file, path + "/" + durable_image);

				BufferedImage image = ImageIO.read(uploadPic);
				int width = 0;
				int height = 0;

				if (image.getWidth() > image.getHeight()) {
					width = 400;
					height = 400;
				} else {
					width = 400;
					height = 400;
				}

				BufferedImage imageWrite = getScaledInstance(image, width, height,
						RenderingHints.VALUE_RENDER_QUALITY, true);

				if (type_image.equalsIgnoreCase(".png")) {
					ImageIO.write(imageWrite, "jpg", new File(path + "/" + durable_image));
				} else if (type_image.equalsIgnoreCase(".jpeg")) {
					ImageIO.write(imageWrite, "jpg", new File(path + "/" + durable_image));
				} else {
					ImageIO.write(imageWrite, "jpg", new File(path + "/" + durable_image));
				}
			} else {
				durable_image = "-";
			}
			
			
			if (message == 1) {
				return new ResponseObj(200, "1");
			} else {
				return new ResponseObj(200, "0");
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = 0;
			return new ResponseObj(500, "0");
		}
	}
	@PostMapping(path = "/verify/uploadimage2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public @ResponseBody ResponseObj douploadimage2(@RequestParam("duralbe_code") String code,ServletRequest request) {
		int message = 0;
		VerifyDurable vd = null;

		try {
			//System.out.println(file);
			String durable_image = code;
			durable_image = code.replaceAll("/", "_") + "" + ".jpg";
			String durablecode = "test";
		
		    File obraz = new File(request.getServletContext().getRealPath("/") + "file/inform_repair/"+durable_image);
		    BufferedImage image2 = ImageIO.read(obraz);
			
			
			if (code.equals("")) {
				code=("-");
			} else {
			}
				String type_image = ".jpg";
				/*String original_file_name = file.getOriginalFilename();
				String type_image = original_file_name.substring(original_file_name.lastIndexOf("."));
				durable_image = code.replaceAll("/", "_") + "" + ".jpg";
				System.out.println(durable_image);*/

				String path = request.getServletContext().getRealPath("/") + "file/durable_image";
		
				//File uploadPic = convert(file, path + "/" + durable_image);

				BufferedImage image = ImageIO.read(obraz);
				int width = 0;
				int height = 0;

				if (image.getWidth() > image.getHeight()) {
					width = 400;
					height = 400;
				} else {
					width = 400;
					height = 400;
				}

				BufferedImage imageWrite = getScaledInstance(image2, width, height,
						RenderingHints.VALUE_RENDER_QUALITY, true);

				if (type_image.equalsIgnoreCase(".png")) {
					ImageIO.write(imageWrite, "jpg", new File(path + "/" + durable_image));
				} else if (type_image.equalsIgnoreCase(".jpeg")) {
					ImageIO.write(imageWrite, "jpg", new File(path + "/" + durable_image));
				} else {
					ImageIO.write(imageWrite, "jpg", new File(path + "/" + durable_image));
				}
			
			
			if (message == 1) {
				return new ResponseObj(200, "1");
			} else {
				return new ResponseObj(200, "0");
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = 0;
			return new ResponseObj(500, "0");
		}
	}
	
	
	public static BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint,
			boolean higherQuality) {
		int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = (BufferedImage) img;
		int w, h;
		if (higherQuality) {
			w = img.getWidth();
			h = img.getHeight();
		} else {
			w = targetWidth;
			h = targetHeight;
		}
		do {
			if (higherQuality && w > targetWidth) {
				w /= 2;
				if (w < targetWidth) {
					w = targetWidth;
				}
			}
			if (higherQuality && h > targetHeight) {
				h /= 2;
				if (h < targetHeight) {
					h = targetHeight;
				}
			}
			BufferedImage tmp = new BufferedImage(w, h, type);
			Graphics2D g2 = tmp.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, hint);
			g2.drawImage(ret, 0, 0, w, h, null);
			g2.dispose();
			ret = tmp;
		} while (w != targetWidth || h != targetHeight);
		return ret;
	}
	public static File convert(MultipartFile file, String path) throws IOException {
		System.out.println(path);
		File convFile = new File(path);
		System.out.println(path + file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
	@PostMapping(path = "/verify/updateform", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj doupdateverifyform(@RequestBody Map<String, String> map) {
		int message = 0;
		VerifyDurable vd = null;

		try {
			String durable_status = map.get("durable_status");
			String save_date = map.get("save_date");
			String note = map.get("note");
			String picture_verify = map.get("picture_verify");
			int id_staff = Integer.parseInt(map.get("id_staff"));
			String years = map.get("years");
			String durable_code = map.get("durable_code");
			Date date_now = new Date();
			String DATE_FORMAT = "dd MMMM yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, new Locale("th", "TH"));
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
			vd = new VerifyDurable(sdf.format(date_now), durable_status, note, picture_verify);
			VerifyManager vm = new VerifyManager();
			Staff s = new Staff();
			s = vm.getstaff2(id_staff);

			Verify v = new Verify();
			v = vm.getverify2(years);

			Durable d = new Durable();
			d = vm.getDurable2(durable_code);

			vd.getPk().setStaff(s);
			vd.getPk().setVerify(v);
			vd.getPk().setDurable(d);
			message = vm.updateverifyform(vd);
			if (message == 1) {
				return new ResponseObj(200, "1");
			} else {
				return new ResponseObj(200, "0");
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = 0;
			return new ResponseObj(500, "0");
		}
	}

	// Testlistyears
	@PostMapping(path = "/verify/testyearlist")
	public @ResponseBody ResponseObj testyearlist(HttpServletRequest request) {
		List<Verify> verify = null;
		try {
			VerifyManager vm = new VerifyManager();
			verify = vm.listyears();

			System.out.println(verify.toString());
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}

	// TESTcountalldurable
	@PostMapping(path = "/verify/countdurable")
	public @ResponseBody ResponseObj testcountalldurable(HttpServletRequest request) {
		int verify = 0;
		try {
			VerifyManager vm = new VerifyManager();
			verify = vm.countalldurable("วิชาเทคโนโลยีสารสนเทศ");

			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}

	@PostMapping(path = "/verifybymajor/countalldurable")
	public @ResponseBody ResponseObj do_countalldurabler(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		int verify = 0;
		try {
			String major_name = map.get("major_name");

			VerifyManager vm = new VerifyManager();
			verify = vm.countalldurable(major_name);
			System.out.println(String.valueOf(verify));
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}

	// TESTcountdurabled
	@PostMapping(path = "/verify/countdurabled")
	public @ResponseBody ResponseObj testcountdurabled(HttpServletRequest request) {
		int verify = 0;
		try {
			VerifyManager vm = new VerifyManager();
			verify = vm.countdurabled("วิชาเทคโนโลยีสารสนเทศ", "2566");

			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}

	@PostMapping(path = "/verifybymajor/countdurabled")
	public @ResponseBody ResponseObj do_countdurabled(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		int verify = 0;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			verify = vm.countdurabled(major_name, year);
			System.out.println(String.valueOf(verify));
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}

	// TESTcountnotdurable
	@PostMapping(path = "/verify/countnotdurabled")
	public @ResponseBody ResponseObj testcountnotdurabled(HttpServletRequest request) {
		int verify = 0;
		try {
			VerifyManager vm = new VerifyManager();
			verify = vm.countnotdurable("วิชาเทคโนโลยีสารสนเทศ", "2566");

			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}

	@PostMapping(path = "/verifybymajor/countnotdurabled")
	public @ResponseBody ResponseObj do_countnotdurabled(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		int verify = 0;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			verify = vm.countnotdurable(major_name, year);
			System.out.println(String.valueOf(verify));
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}
	@PostMapping(path = "/verifybymajor/countverifystatus")
	public @ResponseBody ResponseObj do_countverifystatus(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		int verify = 0;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			verify = vm.countverifystatusgood(major_name, year);
			System.out.println(String.valueOf(verify));
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}
	@RequestMapping(value = "/verifybymajor/listverifystatus", method = RequestMethod.POST)
	public @ResponseBody ResponseObj do_listverifystatus(@RequestBody Map<String, String> map ,HttpServletRequest request) {
		List<VerifyDurable> durable = null;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			durable =  vm.verifystatusgood(major_name,year);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	
	@PostMapping(path = "/verifybymajor/countverifystatus2")
	public @ResponseBody ResponseObj do_countverifystatus2(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		int verify = 0;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			verify = vm.countverifystatus2(major_name, year);
			System.out.println(String.valueOf(verify));
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}
	@RequestMapping(value = "/verifybymajor/listverifystatus2", method = RequestMethod.POST)
	public @ResponseBody ResponseObj do_listverifystatus2(@RequestBody Map<String, String> map ,HttpServletRequest request) {
		List<VerifyDurable> durable = null;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			durable =  vm.verifystatus2(major_name,year);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	@PostMapping(path = "/verifybymajor/countverifystatus3")
	public @ResponseBody ResponseObj do_countverifystatus3(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		int verify = 0;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			verify = vm.countverifystatus3(major_name, year);
			System.out.println(String.valueOf(verify));
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}
	@RequestMapping(value = "/verifybymajor/listverifystatus3", method = RequestMethod.POST)
	public @ResponseBody ResponseObj do_listverifystatus3(@RequestBody Map<String, String> map ,HttpServletRequest request) {
		List<VerifyDurable> durable = null;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			durable =  vm.verifystatus3(major_name,year);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	@PostMapping(path = "/verifybymajor/countverifystatus4")
	public @ResponseBody ResponseObj do_countverifystatus4(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		int verify = 0;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			verify = vm.countverifystatus4(major_name, year);
			System.out.println(String.valueOf(verify));
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}
	@RequestMapping(value = "/verifybymajor/listverifystatus4", method = RequestMethod.POST)
	public @ResponseBody ResponseObj do_listverifystatus4(@RequestBody Map<String, String> map ,HttpServletRequest request) {
		List<verifyinform> durable = null;
		try {
			String major_name = map.get("major_name");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			durable =  vm.verifystatus4(major_name,year);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}

	@PostMapping(path = "/verify/listyears")
	public @ResponseBody ResponseObj do_listyears(@RequestBody Map<String, String> map, HttpServletRequest request) {
		List<Verify> verify = null;
		try {
			VerifyManager vm = new VerifyManager();
			verify = vm.getlistyears();
			System.out.println(verify.toString());
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}

	@PostMapping(path = "/verify/verifyinverifydurable", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_verifyinverifydurable(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		Verify verify = null;
		String success = "1";
		String fail = "0";
		try {
			String durable_code = map.get("durable_code");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			verify = vm.getverify_in_verifydurable(durable_code,year);
			System.out.println(verify.toString());
			if (verify == null) {
				return new ResponseObj(500, fail);
			} else if (verify != null) {
				return new ResponseObj(200, success);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, fail);
	}

	@PostMapping(path = "/VerifyDurable/getverifybydurablecode", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_getverifybydurablecode(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		VerifyDurable verify = null;
		try {
			String durable_code = map.get("durable_code");
			VerifyManager vm = new VerifyManager();
			verify = vm.getverifydurablebycode(durable_code);
			System.out.println(verify.toString());
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}
	
	
	@PostMapping(path = "/VerifyDurable/getverifybydurablecurrent", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_getverifybydurablecurrent(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		VerifyDurable verify = null;
		try {
			String durable_code = map.get("durable_code");
			String year = map.get("year");
			VerifyManager vm = new VerifyManager();
			verify = vm.getverifydurablecurrent(durable_code,year);
			System.out.println(verify.toString());
			return new ResponseObj(200, verify);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verify);
	}
	
	
}
