package ac.th.itsci.durable.app.controller;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import ac.th.itsci.durable.app.manager.InformManager;
import ac.th.itsci.durable.app.manager.VerifyManager;
import ac.th.itsci.durable.entity.Durable;
import ac.th.itsci.durable.entity.RepairDurable;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.entity.Verify;
import ac.th.itsci.durable.entity.VerifyDurable;
import ac.th.itsci.durable.entity.inform_repair;
import ac.th.itsci.durable.entity.verifyinform;
import ac.th.itsci.durable.util.ResponseObj;



@Controller
@RequestMapping(path = "/appinfromrepair", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
public class InformController2 {
	private static String SALT = "123456";

	@RequestMapping(value = "/inform/addform", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_addinform(@RequestBody Map<String, String> map) {
		int message = 0;
		inform_repair ir = null;
		try {
			Calendar c = Calendar.getInstance();
			String Informtype = map.get("Informtype");
			String details = map.get("details");
			String picture_inform = map.get("picture_inform");
			int id_staff = Integer.parseInt(map.get("id_staff"));
			String durable_code = map.get("durable_code");

			InformManager im = new InformManager();
			String id = String.valueOf(im.getMaxinformID());
			System.out.println(id);
			

			Staff s = new Staff();
			s = im.getstaff2(id_staff);
			

			Durable d = new Durable();
			
			DurableManager dm = new DurableManager();
			d = dm.getDurable2(durable_code);
			
			ir = new inform_repair(id, Informtype, c, details, picture_inform,s,d);
			message = im.insertinformsql(ir);
			return new ResponseObj(200, ir);
		} catch (Exception e) {
			e.printStackTrace();
			message = -1;
			return new ResponseObj(500, ir);
		}
	}

	@RequestMapping(value = "/inform/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj do_editinform(@RequestBody Map<String, String> map) {
		int message = 0;
		inform_repair ir = null;
		try {
			String Informid = map.get("Informid");
			String Informtype = map.get("Informtype");
			String dateinform = map.get("dateinform");
			String details = map.get("details");
			String picture_inform = map.get("picture_inform");
			int id_staff = Integer.parseInt(map.get("id_staff"));
			String durable_code = map.get("durable_code");

			DateFormat formatter;
			Date date;
			formatter = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
			date = (Date) formatter.parse(dateinform);
			Calendar cal = Calendar.getInstance();
			
			cal.setTime(date);
			cal.setTimeZone(TimeZone.getTimeZone("GMT+7"));
			InformManager im = new InformManager();

			ir = new inform_repair(Informid, Informtype, cal, details, picture_inform);

			Staff s = new Staff();
			s = im.getstaff(id_staff);
			ir.setStaff(s);

			Durable d = new Durable();
			DurableManager dm = new DurableManager();
			d = dm.getDurable2(durable_code);
			ir.setDurable(d);

			message = im.editinformsql(ir);
			return new ResponseObj(200, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = -1;
			return new ResponseObj(500, message);
		}
	}
	@PostMapping(path = "/inform/uploadimageinform", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public @ResponseBody ResponseObj douploadimageinform(@RequestParam("file") MultipartFile file,@RequestParam("duralbe_code") String code,ServletRequest request) {
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

				String path = request.getServletContext().getRealPath("/") + "file/inform_repair";
				//String path = request.getServletContext().getContextPath("/")+ "file/durable_image";
				//String path = "D:\\JAVA\\DurableWebservices\\src\\main\\webapp\\file\\durable_image";
				
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
	@RequestMapping(value = "/inform_repair/deleteinform_repair", method = RequestMethod.POST)
	public @ResponseBody ResponseObj do_deleteinform_repair(@RequestBody Map<String, String> map) {
		int message = 0;
		inform_repair inform = null;
		try {
			String id = map.get("Informid");

			InformManager im = new InformManager();
			inform = im.getinformidbycode(id);
			message = im.deleteinform_repairsql(id);
			
			return new ResponseObj(200, message);
		
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseObj(500, -1);
		}
	}
	
	@RequestMapping(value = "/inform_repair/getinform_repair", method = RequestMethod.POST)
	public @ResponseBody ResponseObj getinform_repair(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		inform_repair inform_repair = null;
		try {
			String durable_code = map.get("durable_code");
			InformManager ir = new InformManager();

			inform_repair = ir.getinform_repair2(durable_code);
			System.out.println(inform_repair.toString());
			return new ResponseObj(200, inform_repair);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, inform_repair);
	}

	// TEST
	@RequestMapping(value = "/inform_repair/getinform_repair2", method = RequestMethod.POST)
	public @ResponseBody ResponseObj getdurabletest(HttpServletRequest request) {
		inform_repair inform_repair = null;
		try {
			String durablecode = "วท.4140-003-0336/61";
			InformManager ir = new InformManager();

			inform_repair = ir.getinform_repair2(durablecode);

			System.out.println(inform_repair.toString());
			return new ResponseObj(200, inform_repair);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, inform_repair);
	}

	@RequestMapping(value = "/informrepair/getlistformrepair", method = RequestMethod.POST)
	public @ResponseBody ResponseObj getinformrepair(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		List<inform_repair> durable = null;
		try {
			String idmajor = map.get("major_id");
			InformManager im = new InformManager();

			// durable = im.listAll_informrepair("1");
			durable = im.list_informrepairbymajor2(idmajor);
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	
	@RequestMapping(value = "/informrepair/getlistformrepairverifyed", method = RequestMethod.POST)
	public @ResponseBody ResponseObj getinformrepairverifyed(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		List<verifyinform> verifyinform = null;
		try {
			String idmajor = map.get("major_id");
			InformManager im = new InformManager();

			// durable = im.listAll_informrepair("1");
			verifyinform = im.list_verifyinform(idmajor);
			System.out.println(verifyinform.toString());
			return new ResponseObj(200, verifyinform);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, verifyinform);
	}
	
	@RequestMapping(value = "/informrepair/getAlllistformrepair", method = RequestMethod.POST)
	public @ResponseBody ResponseObj getAlllistinformrepair(@RequestBody Map<String, String> map,
			HttpServletRequest request) {
		List<inform_repair> durable = null;
		try {
			InformManager im = new InformManager();

			durable = im.listAll_informrepair();
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	//TEST
	@RequestMapping(value = "/informrepair/getAlllistformrepairtest", method = RequestMethod.POST)
	public @ResponseBody ResponseObj getAlllistinformrepair_test(HttpServletRequest request) {
		List<inform_repair> durable = null;
		try {
			InformManager im = new InformManager();

			durable = im.listAll_informrepair();
			System.out.println(durable.toString());
			return new ResponseObj(200, durable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, durable);
	}
	
		@RequestMapping(value = "/informrepair/listformrepairNOTIN", method = RequestMethod.POST)
		public @ResponseBody ResponseObj list_informrepairNOTIN(@RequestBody Map<String, String> map,HttpServletRequest request) {
			List<inform_repair> durable = null;
			try {
				InformManager im = new InformManager();
				String major_name = map.get("major_name");
				durable = im.listinformrepairNOTIN2(major_name);
				System.out.println(durable.toString());
				return new ResponseObj(200, durable);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseObj(500, durable);
		}
		@RequestMapping(value = "/informrepair/ttest", method = RequestMethod.POST)
		public @ResponseBody ResponseObj list_informrepairNOTINtest(HttpServletRequest request) {
			List<inform_repair> durable = null;
			try {
				InformManager im = new InformManager();

				durable = im.listinformrepairNOTIN("วิชาเทคโนโลยีสารสนเทศ");
				System.out.println(durable.toString());
				return new ResponseObj(200, durable);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseObj(500, durable);
		}
		
		@RequestMapping(value = "/informrepair/listformrepairIN", method = RequestMethod.POST)
		public @ResponseBody ResponseObj list_informrepairIN(@RequestBody Map<String, String> map,HttpServletRequest request) {
			List<verifyinform> durable = null;
			try {
				InformManager im = new InformManager();
				String major_name = map.get("major_name");
				durable = im.listinformrepairIN2(major_name);
				System.out.println(durable.toString());
				return new ResponseObj(200, durable);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseObj(500, durable);
		}
		@RequestMapping(value = "/informrepair/listformrepairINnotinmaintenance", method = RequestMethod.POST)
		public @ResponseBody ResponseObj list_informrepairINnotinmaintenance(@RequestBody Map<String, String> map,HttpServletRequest request) {
			List<RepairDurable> durable = null;
			try {
				InformManager im = new InformManager();
				String major_name = map.get("major_name");
				durable = im.listinformrepairINnotmaintenance(major_name);
				System.out.println(durable.toString());
				return new ResponseObj(200, durable);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseObj(500, durable);
		}

		@PostMapping(path = "/informrepair/getdurableininformrepair", consumes = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ResponseObj do_getdurableininformrepair(@RequestBody Map<String, String> map,
				HttpServletRequest request) {
			Durable durable = null;
			String success = "1";
			String fail = "0";
			try {
				String durable_code = map.get("durable_code");
				InformManager im = new InformManager();
				durable = im.getdurable_in_informrepair(durable_code);
				System.out.println(durable.toString());
				if (durable == null) {
					return new ResponseObj(500, fail);
				} else if (durable != null) {
					return new ResponseObj(200, success);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseObj(500, fail);
		}
		@PostMapping(path = "/informrepair/getdurableininformrepair2", consumes = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ResponseObj do_getdurableininformrepair2(@RequestBody Map<String, String> map,
				HttpServletRequest request) {
			Durable durable = null;
			String success = "1";
			String fail = "0";
			try {
				String durable_code = map.get("durable_code");
				InformManager im = new InformManager();
				durable = im.getdurable_in_informrepair(durable_code);
				System.out.println(durable.toString());
			
					return new ResponseObj(200, durable);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseObj(500, durable);
		}
		
		@PostMapping(path = "/informrepair/getdurableininformrepairnotrepair", consumes = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ResponseObj do_getdurableininformrepairnotrepair(@RequestBody Map<String, String> map,
				HttpServletRequest request) {
			Durable durable = null;
			String success = "1";
			String fail = "0";
			try {
				String durable_code = map.get("durable_code");
				InformManager im = new InformManager();
				durable = im.getdurable_in_informrepairandnotrepair(durable_code);
				System.out.println(durable.toString());
				if (durable == null) {
					return new ResponseObj(500, fail);
				} else if (durable != null) {
					return new ResponseObj(200, success);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseObj(500, fail);
		}
		
		
		@RequestMapping(value = "/informrepair/getlistverifynotinmaintenance", method = RequestMethod.POST)
		public @ResponseBody ResponseObj getverifynotinmaintenance(@RequestBody Map<String, String> map,
				HttpServletRequest request) {
			List<RepairDurable> verifyinform = null;
			try {
				String idmajor = map.get("major_id");
				InformManager im = new InformManager();
				verifyinform = im.listinformrepairINnotmaintenanceID(idmajor);
				System.out.println(verifyinform.toString());
				return new ResponseObj(200, verifyinform);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseObj(500, verifyinform);
		}
}
