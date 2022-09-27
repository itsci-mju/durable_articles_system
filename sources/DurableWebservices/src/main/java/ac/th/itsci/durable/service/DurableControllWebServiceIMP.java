package ac.th.itsci.durable.service;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ac.th.itsci.durable.entity.Company;
import ac.th.itsci.durable.entity.Durable;
import ac.th.itsci.durable.entity.DurableControll;
import ac.th.itsci.durable.entity.DurableType;
import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.Room;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.entity.VerifyDurable;
import ac.th.itsci.durable.repo.CompanyRepository;
import ac.th.itsci.durable.repo.DurableControllRepository;
import ac.th.itsci.durable.repo.DurableRepository;
import ac.th.itsci.durable.repo.DurableTypeRepository;
import ac.th.itsci.durable.repo.MajorRepository;
import ac.th.itsci.durable.repo.RoomRepository;
import ac.th.itsci.durable.repo.VerifyDurableRepository;

@Service
public class DurableControllWebServiceIMP implements DurableControllWebService {

	@Autowired
	DurableControllRepository durableControllRepo;

	@Autowired
	DurableRepository durableRepo;

	@Autowired
	RoomRepository roomRepo;

	@Autowired
	CompanyRepository companyRepo;

	@Autowired
	VerifyDurableRepository verifyDurableRepo;

	@Autowired
	MajorRepository majorRepo;

	@Autowired
	DurableTypeRepository durableTypeRepo;

	DurableWebServiceIMP durablewebserIMP;

	public List<String> getRidOfDuplicateRoom(int id_major) {
		List<String> roomnumber = new ArrayList<>();
		Set<String> listRoomString = new HashSet<String>();
		roomnumber = roomRepo.RoomAllByIdmajor(id_major);
		for (String r : roomnumber) {
			listRoomString.add(r);
		}
		roomnumber.clear();
		roomnumber.addAll(listRoomString);
		return roomnumber;
	}

	public List<String> getRidOfDuplicateStaff(int id_major) {
		List<String> responsible_personList = new ArrayList<>();
		Set<String> responsible_personString = new HashSet<String>();
		responsible_personList = durableRepo.getAllResponsible_person(id_major);
		for (String s : responsible_personList) {
			responsible_personString.add(s);
		}
		responsible_personList.clear();
		responsible_personList.addAll(responsible_personString);
		return responsible_personList;
	}

	public List<String> getRidOfDuplicateCompany() {
		List<String> company_list = new ArrayList<>();
		Set<String> company_String = new HashSet<String>();
		company_list = companyRepo.get_allCompany();
		for (String s : company_list) {
			company_String.add(s);
		}
		company_list.clear();
		company_list.addAll(company_String);
		return company_list;

	}

	private static final int IMG_WIDTH = 200;
	private static final int IMG_HEIGHT = 200;

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

	public ModelAndView callSearchDurableControll(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listqrcode");
		return mav;
	}

	public ModelAndView callLoadEditDurableControll(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("editDurableControllPage");

		try {
			request.setCharacterEncoding("UTF-8");

			Staff staffDetail = (Staff) session.getAttribute("staffSession");

			String durable_code = request.getParameter("did");

			List<String> roomBeans = this.getRidOfDuplicateRoom(staffDetail.getMajor().getID_Major());
			List<String> responsible_personList = this.getRidOfDuplicateStaff(staffDetail.getMajor().getID_Major());
			List<String> company_name = companyRepo.get_allCompany();
			List<DurableType> durable_type = durableTypeRepo.listDurable_type();

			mav = listDurble_type("editDurableControllPage");
			Durable durable_id = new Durable();
			durable_id.setDurable_code(durable_code);
			try {

				DurableControll durable = durableControllRepo.get_durableControll(durable_id.getDurable_code());

				if (!durable.getDurable_money_type().equals("เงินงบประมาณ")
						&& !durable.getDurable_money_type().equals("รายได้")
						&& !durable.getDurable_money_type().equals("เงินบริจาค/ช่วยเหลือ")
						&& !durable.getDurable_money_type().equals("-")) {
					mav.addObject("othercheck", "oc");
				}

				mav.addObject("check_data", "1");
				mav.addObject("durable", durable);
			} catch (Exception e) {
				Durable durable = durableRepo.get_durableByDurableCode(durable_code);
				mav.addObject("durable", durable);
				mav.addObject("check_data", "2");
			}

			mav.addObject("durable_type", durable_type);
			mav.addObject("roomBeanAll", roomBeans);
			mav.addObject("ownerBeanAll", responsible_personList);
			mav.addObject("company_name", company_name);

			session.setAttribute("status", "1");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;

	}

	public ModelAndView callLoadDurableControllPage(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("addDurableControllPage");
		try {
			request.setCharacterEncoding("UTF-8");

			Staff staffDetail = (Staff) session.getAttribute("staffSession");
			List<Room> roomBeans = roomRepo.get_allRoomByMajor(staffDetail.getMajor().getID_Major());
			List<String> responsible_personList = this.getRidOfDuplicateStaff(staffDetail.getMajor().getID_Major());
			List<String> company_list = this.getRidOfDuplicateCompany();
			List<DurableType> durable_type = durableTypeRepo.listDurable_type();

			mav = listDurble_type("addDurableControllPage");
			mav.addObject("durable_type", durable_type);
			mav.addObject("roomBeanAll", roomBeans);
			mav.addObject("ownerBeanAll", responsible_personList);
			mav.addObject("company_list", company_list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	public ModelAndView callAddDurableControll(ServletRequest request, HttpSession session, Model md,
			ServletResponse response, @RequestParam("durable_img") MultipartFile file) {
		ModelAndView mav = new ModelAndView("listqrcode");
		int result_durable = 0;
		int result_company = 0;
		int result_control = 0;
		String message = "";
		try {
			request.setCharacterEncoding("UTF-8");

			Staff staffDetail = (Staff) session.getAttribute("staffSession");

			String numdurable = request.getParameter("numdurable");
			String namedurable = request.getParameter("namedurable");
			String brand = request.getParameter("brand");
			String detail = request.getParameter("detail").trim();
			String pricedurable = request.getParameter("pricedurable");
			String datepicker = request.getParameter("datepicker");
			String room = request.getParameter("room_id");
			String owner = request.getParameter("owner_id");
			String status = request.getParameter("status");
			String note = request.getParameter("note").trim();
			String budget_year = "";

			String durable_type = request.getParameter("durable_types");
			String durable_serial_number = request.getParameter("durable_serial_number").trim();
			String durable_money_type = request.getParameter("durable_money_type");
			String durable_optain_type = request.getParameter("durable_optain_type");
			String durable_petition_number = request.getParameter("durable_petition_number");
			int durable_life_time = Integer.parseInt(request.getParameter("durable_life_time"));
			Double depreciation_rate = Double.parseDouble(request.getParameter("depreciation_rate"));

			String company_name = request.getParameter("company_name");
			String address = request.getParameter("address").trim();
			String tell = request.getParameter("tell");
			String durable_image = "";
			if (durable_money_type.equals("อื่นๆ")) {
				durable_money_type = request.getParameter("durable_money_type_text");
			}

			SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat simpleDateFormatThai = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
			Date dateStart = null;
			String dateStirngStartNew = null;

			Date dateStartNew = null;
			dateStart = simpleDateFormatThai.parse(datepicker);
			dateStirngStartNew = simpledate.format(dateStart);
			dateStartNew = simpledate.parse(dateStirngStartNew);

			if (dateStartNew.getMonth() + 1 >= 10) {
				budget_year = ((dateStartNew.getYear() + 1900)+543) + 1 + "";
			} else {
				budget_year = ((dateStartNew.getYear() + 1900)+543) + "";
			}

			String group_add_check = request.getParameter("group_add_check");

			Company company = new Company();

			Company company_check_duplicate = companyRepo.company_duplicate_check(company_name);
			int company_id = (companyRepo.get_countCompanyID() + 1);

			if (company_check_duplicate != null) {
				company.setCompany_id(company_check_duplicate.getCompany_id());
				company.setCompanyname(company_name);
				company.setAddress(address.trim());
				company.setTell(tell);
			} else {
				company.setCompany_id(company_id);
				company.setCompanyname(company_name);
				company.setAddress(address.trim());
				company.setTell(tell);
				try {
					companyRepo.save(company);
				} catch (Exception e) {
					result_company = -1;
				}

			}

			Durable durableDetail = new Durable();

			durableDetail.setDurable_name(namedurable);
			durableDetail.setDurable_number(numdurable);
			durableDetail.setDurable_brandname(brand);
			durableDetail.setDurable_model(detail);
			durableDetail.setDurable_price(pricedurable);
			durableDetail.setDurable_statusnow(status);
			durableDetail.setResponsible_person(owner);

			durableDetail.setDurable_Borrow_Status("คืน");
			durableDetail.setDurable_entrancedate(datepicker);
			durableDetail.setNote(note);
			durableDetail.setMajor(staffDetail.getMajor());

			Room roomDetail = new Room();
			roomDetail.setRoom_number(room);

			durableDetail.setRoom(roomDetail);

			DurableControll durableControll = new DurableControll();
			durableControll.setDurable_serial_number(durable_serial_number);
			durableControll.setDurable_type(durable_type);
			durableControll.setDurable_petition_number(durable_petition_number);
			durableControll.setDurable_life_time(durable_life_time);
			durableControll.setDurable_optain_type(durable_optain_type);
			durableControll.setDurable_money_type(durable_money_type);
			durableControll.setBudget_year(budget_year);
			durableControll.setDepreciation_rate(depreciation_rate);
			durableControll.setCompany(company);

			if (group_add_check != null) {
				String[] durablecode = request.getParameterValues("durablecode");
				for (int i = 0; i < durablecode.length; i++) {
					if (!file.isEmpty()) {
						String original_file_name = file.getOriginalFilename();
						String type_image = original_file_name.substring(original_file_name.lastIndexOf("."));
						durable_image = durablecode[i].replaceAll("/", "_") + "" + type_image;
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
					}

					durableDetail.setDurable_code(durablecode[i]);
					durableControll.setDurable_code(durablecode[i]);

					if (durable_image.equals("")) {
						durableDetail.setDurable_image("-.png");
					} else {
						durableDetail.setDurable_image(durable_image);
					}

					try {
						result_durable += durableRepo.recordDurable(durableDetail.getDurable_code(),
								durableDetail.getDurable_Borrow_Status(), durableDetail.getDurable_brandname(),
								durableDetail.getDurable_entrancedate(), durableDetail.getDurable_image(),
								durableDetail.getDurable_model(), durableDetail.getDurable_name(),
								durableDetail.getDurable_number(), durableDetail.getDurable_price(),
								durableDetail.getDurable_statusnow(), durableDetail.getResponsible_person(),
								durableDetail.getNote(), durableDetail.getMajor().getID_Major(),
								durableDetail.getRoom().getRoom_number());

						try {
							result_control += durableControllRepo.insert_durableControll(
									durableControll.getDurable_life_time(), durableControll.getDurable_money_type(),
									durableControll.getDurable_optain_type(),
									durableControll.getDurable_petition_number(),
									durableControll.getDurable_serial_number(), durableControll.getDurable_type(),
									durableControll.getDurable_code(), durableControll.getCompany().getCompany_id(),
									durableControll.getBudget_year(), durableControll.getDepreciation_rate());
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (Exception e) {

					}

					if (result_control == durablecode.length && result_durable == durablecode.length) {
						message = "เพิ่มข้อมูลสำเร็จ";
					} else {
						message = "เพิ่มข้อมูลไม่สำเร็จ";
					}
				}

			} else {

				String durablecode = request.getParameter("durablecode");
				// upload durable image
				if (!file.isEmpty()) {
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
				}

				durableDetail.setDurable_code(durablecode);
				durableControll.setDurable_code(durablecode);

				if (durable_image.equals("")) {
					durableDetail.setDurable_image("-");
				} else {
					durableDetail.setDurable_image(durable_image);
				}

				try {
					result_durable = durableRepo.recordDurable(durableDetail.getDurable_code(),
							durableDetail.getDurable_Borrow_Status(), durableDetail.getDurable_brandname(),
							durableDetail.getDurable_entrancedate(), durableDetail.getDurable_image(),
							durableDetail.getDurable_model(), durableDetail.getDurable_name(),
							durableDetail.getDurable_number(), durableDetail.getDurable_price(),
							durableDetail.getDurable_statusnow(), durableDetail.getResponsible_person(),
							durableDetail.getNote(), durableDetail.getMajor().getID_Major(),
							durableDetail.getRoom().getRoom_number());

					try {
						result_control = durableControllRepo.insert_durableControll(
								durableControll.getDurable_life_time(), durableControll.getDurable_money_type(),
								durableControll.getDurable_optain_type(), durableControll.getDurable_petition_number(),
								durableControll.getDurable_serial_number(), durableControll.getDurable_type(),
								durableControll.getDurable_code(), durableControll.getCompany().getCompany_id(),
								durableControll.getBudget_year(), durableControll.getDepreciation_rate());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (result_durable != 0 && result_control != 0 && result_company != 0) {
					message = "เพิ่มข้อมูลสำเร็จ";
				} else {
					message = "เพิ่มข้อมูลไม่สำเร็จ";
				}

			}

			String chk = "1";
			mav = callDurableList("", "", session, request, chk);

			session.setAttribute("value", 1);
			session.setAttribute("message", message);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	public ModelAndView do_generatedurablecode(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("addDurableControllPage");
		try {
			request.setCharacterEncoding("UTF-8");
			String durablecode = request.getParameter("durablecode");
			String numdurable = request.getParameter("numdurable");
			String namedurable = request.getParameter("namedurable");
			String brand = request.getParameter("brand");
			String detail = request.getParameter("detail").trim();
			String pricedurable = request.getParameter("pricedurable");
			String entranceDate = request.getParameter("datepicker");
			String room = request.getParameter("room_id");
			String owner = request.getParameter("owner_id");
			String status = request.getParameter("status");
			String note = request.getParameter("note").trim();

			String durable_type = request.getParameter("durable_types");
			String durable_serial_number = request.getParameter("durable_serial_number").trim();
			String durable_money_type = request.getParameter("durable_money_type");
			String durable_optain_type = request.getParameter("durable_optain_type");
			String durable_petition_number = request.getParameter("durable_petition_number");

			String company_name = request.getParameter("company_name");

			DurableControll durable_data_form = new DurableControll();
			List<Durable> durable_group = new ArrayList<>();
			String durable_code_group = "";
			String[] durable_code_split = null;

			durable_data_form.setDurable_code(durablecode);
			durable_data_form.setDurable_number(numdurable);
			durable_data_form.setDurable_name(namedurable);
			durable_data_form.setDurable_brandname(brand);
			durable_data_form.setDurable_model(detail);
			durable_data_form.setDurable_price(pricedurable);
			Room roombean = new Room();
			roombean.setRoom_number(room);
			durable_data_form.setRoom(roombean);
			durable_data_form.setResponsible_person(owner);
			durable_data_form.setDurable_statusnow(status);
			durable_data_form.setNote(note);
			durable_data_form.setDurable_type(durable_type);
			durable_data_form.setDurable_serial_number(durable_serial_number);
			durable_data_form.setDurable_money_type(durable_money_type);
			durable_data_form.setDurable_optain_type(durable_optain_type);
			durable_data_form.setDurable_petition_number(durable_petition_number);

			Company company = new Company();
			company.setCompanyname(company_name);
			durable_data_form.setCompany(company);

			if (durablecode.contains("(")) {
				durable_code_split = durablecode.split("\\(", 2);
				System.out.println(durable_code_split[1]);
				durable_code_group = durable_code_split[0].substring(durable_code_split[0].length() - 7);
			} else {
				durable_code_group = durablecode.substring(durablecode.length() - 7);
			}

			String durable_code_groups[] = durable_code_group.split("/");

			for (int i = 0; i < Integer.parseInt(numdurable); i++) {

				Durable durable_group_id = new Durable();

				durable_code_group = "" + (Integer.parseInt(durable_code_groups[0]) + i);

				if (durable_code_group.length() == 1) {
					durable_code_group = "000" + durable_code_group;
				} else if (durable_code_group.length() == 2) {
					durable_code_group = "00" + durable_code_group;
				} else if (durable_code_group.length() == 3) {
					durable_code_group = "0" + durable_code_group;
				}
				if (durablecode.contains("(")) {
					durable_group_id.setDurable_code(durablecode.substring(0, 12) + "" + durable_code_group + "/"
							+ durable_code_groups[1] + "(" + durable_code_split[1]);
				} else {
					durable_group_id.setDurable_code(
							durablecode.substring(0, 12) + "" + durable_code_group + "/" + durable_code_groups[1]);
				}

				if (i != 0) {
					durable_group.add(durable_group_id);
				}

				System.out.println("durable_code_group : " + durable_group_id.getDurable_code());
			}

			mav = callLoadDurableControllPage(request, session, md);
			mav.addObject("group_check", "1");
			mav.addObject("durable_data_form", durable_data_form);
			mav.addObject("durable_group_code", durable_group);
			mav.addObject("entranceDate", entranceDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	public ModelAndView callEditDurableControll(ServletRequest request, HttpSession session, Model md,
			ServletResponse response, @RequestParam("durable_img") MultipartFile file) {
		ModelAndView mav = new ModelAndView();
		int result_update_durable = 0;
		int result_update_company = 0;
		int result_update_control = 0;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			Staff staffDetail = (Staff) session.getAttribute("staffSession");

			String durablecode = request.getParameter("durablecode");
			String numdurable = request.getParameter("numdurable");
			String namedurable = request.getParameter("namedurable");
			String brand = request.getParameter("brand");
			String detail = request.getParameter("detail").trim();
			String pricedurable = request.getParameter("pricedurable");

			String room = request.getParameter("room_id");
			String owner = request.getParameter("owner_id");
			String status = request.getParameter("status");
			String note = request.getParameter("note").trim();

			// String durable_controll_id = request.getParameter("dcid");

			String durable_type = request.getParameter("durable_types");
			String durable_serial_number = request.getParameter("durable_serial_number").trim();
			String durable_money_type = request.getParameter("durable_money_type");
			String durable_optain_type = request.getParameter("durable_optain_type");

			String durable_petition_number = request.getParameter("durable_petition_number");
			int durable_life_time = Integer.parseInt(request.getParameter("durable_life_time"));
			String budget_year = "";
			// request.getParameter("budget_year")
			Double depreciation_rate = Double.parseDouble(request.getParameter("depreciation_rate"));

			if (durable_optain_type == null || durable_optain_type.equals("")) {
				durable_optain_type = "-";
			}

			if (durable_money_type == null || durable_optain_type.equals("")) {
				durable_money_type = "-";
			} else if (durable_money_type.equals("อื่นๆ")) {
				durable_money_type = request.getParameter("durable_money_type_text");
			}

			String company_name = request.getParameter("company_name");
			String address = request.getParameter("address").trim();
			String tell = request.getParameter("tell");

			String datepicker = request.getParameter("datepicker");
			try {
				SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat simpleDateFormatThai = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
				Date dateStart = null;
				String dateStirngStartNew = null;

				Date dateStartNew = null;
				dateStart = simpleDateFormatThai.parse(datepicker);
				dateStirngStartNew = simpledate.format(dateStart);
				dateStartNew = simpledate.parse(dateStirngStartNew);

				if (dateStartNew.getMonth() + 1 >= 10) {
					budget_year = ((dateStartNew.getYear() + 1900)+543) + 1 + "";
				} else {
					budget_year = ((dateStartNew.getYear() + 1900)+543) + "";
				}

			} catch (Exception e) {
				e.printStackTrace();
				String years = (Calendar.getInstance().get(Calendar.YEAR)+543) + "";
				budget_year = years;
			}
			// String company_address_id = request.getParameter("caid");

			// int company_id = Integer.parseInt(request.getParameter("cid"));

			Company company = new Company();
			// CompanyAddress companyAddress = new CompanyAddress();

			int company_id = companyRepo.get_countCompanyID();

			if (company_id == 0) {
				company_id += 1;
				company.setCompany_id(company_id);
			} else {
				company.setCompany_id(company_id + 1);
			}

			Company company_check_duplicate = companyRepo.company_duplicate_check(company_name);

			if (company_check_duplicate != null) {
				System.out.println(company_check_duplicate.getCompany_id());
				company.setCompany_id(company_check_duplicate.getCompany_id());
				company.setCompanyname(company_name);
				company.setAddress(address.trim());
				company.setTell(tell);
				companyRepo.save(company);
			} else {
				company.setCompanyname(company_name);
				company.setAddress(address.trim());
				company.setTell(tell);
				companyRepo.save(company);
			}

			DurableControll durableControll = new DurableControll();

			String durable_image = "";

			// upload image
			if (!file.isEmpty()) {
				String original_file_name = file.getOriginalFilename();
				String type_image = original_file_name.substring(original_file_name.lastIndexOf("."));

				durable_image = durablecode.replaceAll("/", "_") + "" + type_image;
				System.out.println(durable_image);

				String path = request.getServletContext().getRealPath("/") + "file/durable_image";

				Path paths = Paths.get(path, durable_image);
				System.out.println(paths);
//				try {
//					Files.write(paths, file.getBytes());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}

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

				BufferedImage imageWrite = getScaledInstance(image, width, height, RenderingHints.VALUE_RENDER_QUALITY,
						true);
				// String pathM =
				// request.getServletContext().getRealPath("/")+"/file/durable_image/" ;

				if (type_image.equalsIgnoreCase(".png")) {
					ImageIO.write(imageWrite, "png", new File(path + "/" + durable_image));
				} else if (type_image.equalsIgnoreCase(".jpeg")) {
					ImageIO.write(imageWrite, "jpeg", new File(path + "/" + durable_image));
				} else {
					ImageIO.write(imageWrite, "jpg", new File(path + "/" + durable_image));
				}

			} else {
				durable_image = request.getParameter("old_img");
				System.out.println("old_img" + durable_image);
			}

			durableControll.setDurable_code(durablecode);
			durableControll.setDurable_serial_number(durable_serial_number);
			durableControll.setDurable_type(durable_type);
			durableControll.setDurable_petition_number(durable_petition_number);
			durableControll.setDurable_life_time(durable_life_time);
			durableControll.setDurable_optain_type(durable_optain_type);
			durableControll.setDurable_money_type(durable_money_type);
			durableControll.setCompany(company);
			durableControll.setBudget_year(budget_year);
			durableControll.setDepreciation_rate(depreciation_rate);

			List<Room> roomList = roomRepo.getAllRoom();
			Room roomDetail = new Room();
			for (Room r : roomList) {
				if (r.getRoom_number().equalsIgnoreCase(room)) {
					roomDetail.setRoom_number(r.getRoom_number());
					roomDetail.setRoom_name(r.getRoom_name());
					roomDetail.setBuild(r.getBuild());
					roomDetail.setFloor(r.getFloor());
				}
			}

			Durable durableDetail = new Durable();
			durableDetail.setDurable_code(durablecode);
			durableDetail.setDurable_name(namedurable);
			durableDetail.setDurable_number(numdurable);
			durableDetail.setDurable_brandname(brand);
			durableDetail.setDurable_model(detail);
			durableDetail.setDurable_price(pricedurable);
			durableDetail.setDurable_statusnow(status);
			durableDetail.setResponsible_person(owner);
			durableDetail.setDurable_image(durable_image);
			durableDetail.setDurable_Borrow_Status("คืน");
			durableDetail.setDurable_entrancedate(datepicker);
			durableDetail.setNote(note);
			durableDetail.setMajor(staffDetail.getMajor());
			durableDetail.setRoom(roomDetail);

			String message = "";
			result_update_company = companyRepo.update_company(company.getCompany_id(), company.getCompanyname(),
					company.getAddress(), company.getTell());

			result_update_control = durableControllRepo.update_durableControll(durableControll.getDurable_code(),
					durableControll.getDurable_money_type(), durableControll.getDurable_optain_type(),
					durableControll.getDurable_petition_number(), durableControll.getDurable_serial_number(),
					durableControll.getDurable_type(), durableControll.getCompany().getCompany_id(),
					durableControll.getDurable_life_time(), durableControll.getBudget_year(),
					durableControll.getDepreciation_rate());

			result_update_durable = durableRepo.update_durable(durableDetail.getDurable_code(),
					durableDetail.getDurable_Borrow_Status(), durableDetail.getDurable_brandname(),
					durableDetail.getDurable_entrancedate(), durableDetail.getDurable_image(),
					durableDetail.getDurable_model(), durableDetail.getDurable_name(),
					durableDetail.getDurable_number(), durableDetail.getDurable_price(),
					durableDetail.getDurable_statusnow(), durableDetail.getResponsible_person(),
					durableDetail.getNote(), staffDetail.getMajor().getID_Major(), roomDetail.getRoom_number());

			if (result_update_company != -1 && result_update_durable != -1 && result_update_control != -1) {
				message = "แก้ไขข้อมูลสำเร็จ";
			} else {
				message = "แก้ไขข้อมูลไม่สำเร็จ";
			}

			String major_id = (String) session.getAttribute("major_old");
			String room_number = (String) session.getAttribute("room_id_old");
			System.out.println(major_id + " :m" + " r:" + room_number);

			String chk = "1";

			mav = callDurableList(major_id, room_number, session, request, chk);

			session.setAttribute("value", 1);
			session.setAttribute("message", message);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;

	}

	public ModelAndView callDurableList(String major_id, String room_id, HttpSession session, ServletRequest request,
			String chk) {
		ModelAndView mav = new ModelAndView("listqrcode");
		try {
			Staff staffDetail = (Staff) session.getAttribute("staffSession");
			request.setCharacterEncoding("UTF-8");
			// list data room all for select room in jsp
			List<String> roomAllForSelect = new ArrayList<>();
			Set<String> roomAllForSelectSet = new HashSet<String>();
			// list data room for edit durable
			List<String> roomAllForEdit = new ArrayList<>();
			// list data owner for edit durable
			Set<String> ownerBeanAll = new HashSet<>();
			// list data room for create select room string
			List<Room> listRoom;
			// list data durable for show in jsp
			List<Durable> durableList = null;
			// list data durable don't have room
			List<Durable> durableNoRoom = new ArrayList<>();
			// list data durable by room;

			if (staffDetail.getStaff_status().equalsIgnoreCase("เจ้าหน้าที่ส่วนกลาง")) {
				if (major_id != null) {
					if (room_id.equalsIgnoreCase("-")) {
						mav = callListMajorAndRoom(chk);
						mav.addObject("value", 1);
						mav.addObject("message", "กรุณาเลือกสาขา");
						mav.addObject("roomNumber", "-");
					} else {
						if (room_id.equalsIgnoreCase("-")) {
							durableList = durableRepo.getDurable_DetailByIDmajor(Integer.parseInt(major_id));

							for (Durable d : durableList) {
								ownerBeanAll.add(d.getResponsible_person());
							}

							if (durableList.size() == 0) {
								mav = callListMajorAndRoom(chk);
								List<Major> test = (List<Major>) mav.getModel().get("listMajor");

								for (int i = 0; i < test.size(); i++) {
									if (test.get(i).getID_Major() == Integer.parseInt(major_id)) {
										Collections.swap(test, 0, i);
									}
								}

								listRoom = roomRepo.room_getAllRoomByMajor(Integer.parseInt(major_id));
								for (Room room : listRoom) {
									roomAllForSelectSet.add(room.getRoom_number());
								}

								roomAllForSelect.addAll(roomAllForSelectSet);
								roomAllForSelect.add("-");
								for (int i = 0; i < roomAllForSelect.size(); i++) {
									if (roomAllForSelect.get(i).equalsIgnoreCase("-")) {
										Collections.swap(roomAllForSelect, 0, i);
									}
								}
								if (major_id.equalsIgnoreCase(String.valueOf(staffDetail.getMajor().getID_Major()))) {
									mav.addObject("type", 0);
								} else {
									mav.addObject("type", 1);
								}

								mav.addObject("roomNumber", roomAllForSelect);
								mav.addObject("listMajor", test);
								mav.addObject("listdurableall", null);
								mav.addObject("dataNotfound", 0);
							} else {
								mav = callListMajorAndRoom(chk);
								List<Major> test = (List<Major>) mav.getModel().get("listMajor");

								for (int i = 0; i < test.size(); i++) {
									if (test.get(i).getID_Major() == Integer.parseInt(major_id)) {
										Collections.swap(test, 0, i);
									}
								}

								listRoom = roomRepo.room_getAllRoomByMajor(Integer.parseInt(major_id));
								for (Room room : listRoom) {
									roomAllForSelectSet.add(room.getRoom_number());
									roomAllForEdit.add(room.getRoom_number());
								}

								roomAllForSelect.addAll(roomAllForSelectSet);
								roomAllForSelect.add("-");
								for (int i = 0; i < roomAllForSelect.size(); i++) {
									if (roomAllForSelect.get(i).equalsIgnoreCase("-")) {
										Collections.swap(roomAllForSelect, 0, i);
									}
								}
								if (major_id.equalsIgnoreCase(String.valueOf(staffDetail.getMajor().getID_Major()))) {
									mav.addObject("type", 0);
								} else {
									mav.addObject("type", 1);
								}
								mav.addObject("roomSelectEdit", roomAllForEdit);
								mav.addObject("roomNumber", roomAllForSelect);
								mav.addObject("listMajor", test);
								mav.addObject("ownerBeanAll", ownerBeanAll);
								mav.addObject("listdurableall", durableList);
							}
						} else {
							durableList = durableRepo.getDurable_DetailByRoom(room_id);
							System.out.println(durableList.size());
							for (Durable d : durableList) {
								ownerBeanAll.add(d.getResponsible_person());
							}
							if (durableList.size() == 0) {
								mav = callListMajorAndRoom(chk);
								List<Major> test = (List<Major>) mav.getModel().get("listMajor");

								for (int i = 0; i < test.size(); i++) {
									if (test.get(i).getID_Major() == Integer.parseInt(major_id)) {
										Collections.swap(test, 0, i);
									}
								}
								listRoom = roomRepo.room_getAllRoomByMajor(Integer.parseInt(major_id));
								for (Room room : listRoom) {
									roomAllForSelectSet.add(room.getRoom_number());
								}

								roomAllForSelect.addAll(roomAllForSelectSet);
								for (int i = 0; i < roomAllForSelect.size(); i++) {
									if (roomAllForSelect.get(i).equalsIgnoreCase(room_id)) {
										Collections.swap(roomAllForSelect, 0, i);
									}
								}
								if (major_id.equalsIgnoreCase(String.valueOf(staffDetail.getMajor().getID_Major()))) {
									mav.addObject("type", 0);
								} else {
									mav.addObject("type", 1);
								}
								mav.addObject("roomNumber", roomAllForSelect);
								mav.addObject("listMajor", test);
								mav.addObject("listdurableall", null);
								mav.addObject("dataNotfound", 0);
							} else {
								mav = callListMajorAndRoom(chk);
								List<Major> test = (List<Major>) mav.getModel().get("listMajor");

								for (int i = 0; i < test.size(); i++) {
									if (test.get(i).getID_Major() == Integer.parseInt(major_id)) {
										Collections.swap(test, 0, i);
									}
								}

								listRoom = roomRepo.room_getAllRoomByMajor(Integer.parseInt(major_id));
								for (Room room : listRoom) {
									roomAllForSelectSet.add(room.getRoom_number());
									roomAllForEdit.add(room.getRoom_number());
								}

								roomAllForSelect.addAll(roomAllForSelectSet);
								for (int i = 0; i < roomAllForSelect.size(); i++) {
									if (roomAllForSelect.get(i).equalsIgnoreCase(room_id)) {
										Collections.swap(roomAllForSelect, 0, i);
									}
								}
								if (major_id.equalsIgnoreCase(String.valueOf(staffDetail.getMajor().getID_Major()))) {
									mav.addObject("type", 0);
								} else {
									mav.addObject("type", 1);
								}

								mav.addObject("ownerBeanAll", ownerBeanAll);
								mav.addObject("roomNumber", roomAllForSelect);
								mav.addObject("roomSelectEdit", roomAllForEdit);
								mav.addObject("listMajor", test);
								mav.addObject("listdurableall", durableList);
							}
						}
					}
				} else {
					mav = callListMajorAndRoom(chk);
					mav.addObject("roomNumber", "-");
//					List<Room> roomAll = roomRepo.getAllRoom();
//					List<Major> majorAll = majorRepo.getAllMajor();
//					mav.addObject("roomNumber", roomAll);

				}
			} else {

				listRoom = roomRepo.room_getAllRoomByMajor(staffDetail.getMajor().getID_Major());
				durableList = durableRepo.getAllDurableByIdMajor(staffDetail.getMajor().getID_Major());

				// durableList = durableRepo.getSearchDurable("", "", "");
				// set data room all for select room in jsp
				roomAllForSelectSet.add("-");
				for (Room room : listRoom) {
					roomAllForSelectSet.add(room.getRoom_number());
					roomAllForEdit.add(room.getRoom_number());
				}
				// set data owner for edit durable
				for (Durable d : durableList) {
					ownerBeanAll.add(d.getResponsible_person());
				}

				if (room_id == null) {
					if (durableList.size() == 0) {
						mav.addObject("listdurableall", null);
					} else {
						// check durable room == null
						for (Durable d : durableList) {
							if (d.getRoom() == null) {
								roomAllForSelectSet.add("ยังไม่ได้ระบุห้อง");
							}
						}
						roomAllForSelect.addAll(roomAllForSelectSet);

						for (int i = 0; i < roomAllForSelect.size(); i++) {
							if (roomAllForSelect.get(i).equalsIgnoreCase("-")) {
								Collections.swap(roomAllForSelect, 0, i);
							} else if (roomAllForSelect.get(i).equalsIgnoreCase("ยังไม่ได้ระบุห้อง")) {
								Collections.swap(roomAllForSelect, roomAllForSelect.size() - 1, i);
							}
						}
						mav.addObject("listdurableall", durableList);
						mav.addObject("roomBeanAll", roomAllForSelect);
						mav.addObject("roomSelectEdit", roomAllForEdit);
						mav.addObject("ownerBeanAll", ownerBeanAll);
					}
				} else {
					request.setCharacterEncoding("UTF-8");
					String durable_code = request.getParameter("durable_code");
					String durable_name = request.getParameter("durable_name");
					session.setAttribute("durable_code_search", durable_code);
					session.setAttribute("durable_name_search", durable_name);
					System.out.println(durable_code + " " + durable_name);

					if (room_id.equalsIgnoreCase("-")) {
						if (durable_code != "" && durable_name == "") {
							// durableList = durableRepo.getSearchDurable(durable_code, null, null);
							durableList = durableRepo.getSearchDurableByDurableCodeNoRoom(durable_code,
									staffDetail.getMajor().getID_Major());
							System.out.println("search by code");
						} else if (durable_name != "" && durable_code == "") {
							durableList = durableRepo.getSearchDurableByNameNoRoom(durable_name,
									staffDetail.getMajor().getID_Major());
							// durableList = durableRepo.getSearchDurable(null, durable_name, null);
							System.out.println("search by name");
						} else {
							durableList = durableRepo.getAllDurableByIdMajor(staffDetail.getMajor().getID_Major());
							// durableList = durableRepo.getSearchDurable(durable_code, durable_name, null);
							System.out.println("search by code and name");
						}

					} else {
						if (room_id.equalsIgnoreCase("ยังไม่ได้ระบุห้อง")) {
							durableList = durableRepo.getAllDurableByIdMajor(staffDetail.getMajor().getID_Major());
							for (Durable d : durableList) {
								if (d.getRoom() == null) {
									durableNoRoom.add(d);
								}
							}
							System.out.println("el");
							durableList.clear();
							durableList.addAll(durableNoRoom);
						} else {
							if (durable_code != "" && durable_name == "") {
								// durableList = durableRepo.getSearchDurable(durable_code, null, room_id);
								durableList = durableRepo.getSearchDurableByDurableCodeAndRoom(durable_code, room_id);
								System.out.println("search by code");
							} else if (durable_name != "" && durable_code == "") {
								// durableList = durableRepo.getSearchDurable(null, durable_name, room_id);
								durableList = durableRepo.getSearchDurableByNameAndRoom(durable_name, room_id);
								System.out.println("search by name");

							} else if (durable_name == "" && durable_code == "") {
								// durableList = durableRepo.getSearchDurable(null, null, room_id);
								durableList = durableRepo.getDurable_DetailByRoom(room_id);
								System.out.println("search by room_id");
							} else {
								durableList = durableRepo.getSearchDurable(durable_code, durable_name, room_id);
							}
						}
					}

					// save data for after edit

					// check durable room == null
					for (Durable d : durableList) {
						if (d.getRoom() == null) {
							roomAllForSelectSet.add("ยังไม่ได้ระบุห้อง");
						}
					}
					roomAllForSelect.addAll(roomAllForSelectSet);

					for (int i = 0; i < roomAllForSelect.size(); i++) {
						if (room_id.equalsIgnoreCase(roomAllForSelect.get(i))) {
							Collections.swap(roomAllForSelect, 0, i);
						}
					}

					if (durableList.size() == 0) {
						mav.addObject("listdurableall", null);
					} else {
						mav.addObject("listdurableall", durableList);
					}

					mav.addObject("roomBeanAll", roomAllForSelect);
					mav.addObject("roomSelectEdit", roomAllForEdit);
					mav.addObject("ownerBeanAll", ownerBeanAll);
					mav.addObject("search_code", durable_code);
					mav.addObject("durable_name", durable_name);
				}
				mav.addObject("type", 0);
			}
			mav.addObject("major_old", major_id);
			mav.addObject("room_id_old", room_id);
			session.setAttribute("major_old", major_id);
			session.setAttribute("room_id_old", room_id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	public ModelAndView callGetDurableControlByYear(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listDurableControlByYear");
		try {

			Staff staffDetail = (Staff) session.getAttribute("staffSession");
			String year = request.getParameter("year");
			List<DurableControll> durableControll = null;

			mav = callloadlistdurablecontrolbyyear(request, session, md);

			if (staffDetail.getStaff_status().equals("เจ้าหน้าที่สาขา")) {
				durableControll = durableControllRepo.get_durableControllByYearAndMajor(year,
						staffDetail.getMajor().getID_Major());
			} else {
				int major_id = Integer.parseInt(request.getParameter("major_id"));

				durableControll = durableControllRepo.get_durableControllByYearAndMajor(year, major_id);
				List<Room> selectMajorRoom = roomRepo.get_allRoomByMajor(major_id);

				mav.addObject("mid", request.getParameter("major_id"));
				mav.addObject("roomNumber", selectMajorRoom);
			}

			mav.addObject("year", year);

			if (durableControll.size() == 0) {
				mav.addObject("message", "ไม่พบข้อมูล");
			}

			mav.addObject("durableControll", durableControll);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	public ModelAndView callloadlistdurablecontrolbyyear(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listDurableControlByYear");
		try {
			Staff staffSession = (Staff) session.getAttribute("staffSession");
			String chk = "0";
			List<Room> roomByMajor = roomRepo.get_allRoomByMajor(staffSession.getMajor().getID_Major());

			if (staffSession.getMajor().getID_Major() == 999) {
				mav = callListMajorAndRoom(chk);
			} else {
				mav.addObject("roomNumberMajor", roomByMajor);
			}

		} catch (Exception e) {

		}

		return mav;
	}

	public ModelAndView callLoadDurableControllDetail(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("durableControllDetail");
		try {
			String durable_code = request.getParameter("did");
			DurableControll durableControll = new DurableControll();
			durableControll = durableControllRepo.get_durableControll(durable_code);

			if (!durableControll.getDurable_money_type().equals("เงินงบประมาณ")
					&& !durableControll.getDurable_money_type().equals("รายได้")
					&& !durableControll.getDurable_money_type().equals("เงินบริจาค/ช่วยเหลือ")
					&& !durableControll.getDurable_money_type().equals("-")) {
				mav.addObject("othercheck", "oc");
			}
			request.setAttribute("durable_controll", durableControll);

			mav.addObject("durable", durableControll);

			if (durableControll.getDurable_life_time() == 0) {
				mav.addObject("message", "กรุณาแก้ไขข้อมูลครุภัณฑ์");
				mav = callLoadEditDurableControll(request, session, md);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	public ModelAndView listDurble_type(String page) {
		ModelAndView mav = new ModelAndView(page);

		List<DurableType> durable_type = durableTypeRepo.listDurable_type();

		JSONObject json = new JSONObject();

		for (int i = 0; i < durable_type.size(); i++) {
			JSONArray jsonArray = new JSONArray();
			jsonArray.put(durable_type.get(i).getDurable_depreciation());
			jsonArray.put(durable_type.get(i).getDurable_life_time());
			json.put(String.valueOf(durable_type.get(i).getDurable_type_name()), jsonArray);
		}

		JSONObject jsonDurableType = new JSONObject();
		jsonDurableType.put("durable_type", json);

		String jsonText = jsonDurableType.toString();
		String durable_type_bean = jsonText.replaceAll("\"", "\'");

		System.out.println(durable_type_bean);

		mav = listCompany(page);

		mav.addObject("durable_type_bean", durable_type_bean);
		return mav;
	}

	public ModelAndView listCompany(String page) {
		ModelAndView mav = new ModelAndView(page);

		List<Company> company_list = companyRepo.list_company();

		JSONObject json = new JSONObject();

		for (Company c : company_list) {
			JSONArray jsonArray = new JSONArray();
			jsonArray.put(c.getAddress());
			jsonArray.put(c.getTell());
			json.put(String.valueOf(c.getCompanyname()), jsonArray);
		}

		JSONObject jsonCompanyAdd = new JSONObject();
		jsonCompanyAdd.put("company_add", json);

		String jsonText = jsonCompanyAdd.toString();
		String company_add_bean = jsonText.replaceAll("\"", "\'");

		System.out.println(company_add_bean);

		mav.addObject("company_add_bean", company_add_bean);

		return mav;
	}

	// callListRoom
	public ModelAndView callListMajorAndRoom(String chk) {

		ModelAndView modelAndView = new ModelAndView();

		if (chk.equals("1")) {
			modelAndView = new ModelAndView("listqrcode");
		} else {
			modelAndView = new ModelAndView("listDurableControlByYear");
		}

		List<Major> listMajor = majorRepo.getAllMajor();
		List<Room> listRoom = roomRepo.getAllRoom();

		JSONObject json = new JSONObject();

		for (int i = 0; i < listMajor.size(); i++) {
			JSONArray jsonArray = new JSONArray();
			for (int j = 0; j < listRoom.size(); j++) {
				if (listMajor.get(i).getID_Major() == listRoom.get(j).getMajor().getID_Major()) {
					jsonArray.put(listRoom.get(j).getRoom_number());
				}
			}
			json.put(String.valueOf(listMajor.get(i).getID_Major()), jsonArray);
		}

		JSONObject jsonMajor = new JSONObject();
		jsonMajor.put("major", json);

		String jsonText = jsonMajor.toString();
		String replaceAll = jsonText.replaceAll("\"", "\'");

		listMajor.add(new Major(99, "-"));

		for (int i = 0; i < listMajor.size(); i++) {
			if (listMajor.get(i).getMajor_Name().equalsIgnoreCase("-")) {
				Collections.swap(listMajor, 0, i);
			}
		}

		System.out.println(replaceAll);

		modelAndView.addObject("listMajor", listMajor);
		modelAndView.addObject("roomBeanAll", replaceAll);
		return modelAndView;
	}

}
