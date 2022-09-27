package ac.th.itsci.durable.controller;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ac.th.itsci.durable.entity.Company;
import ac.th.itsci.durable.entity.Durable;
import ac.th.itsci.durable.entity.DurableControll;
import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.RepairDurable;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.repo.CompanyRepository;
import ac.th.itsci.durable.repo.DurableControllRepository;
import ac.th.itsci.durable.repo.DurableRepository;
import ac.th.itsci.durable.repo.MajorRepository;
import ac.th.itsci.durable.repo.RepairRepository;

import java.text.DecimalFormat;
import java.util.*;

@Controller
public class MaintenanceController {

	@Autowired
	DurableRepository dcRepo;

	@Autowired
	RepairRepository rdRepo;

	@Autowired
	DurableControllRepository dccRepo;

	@Autowired
	CompanyRepository comRepo;

	@Autowired
	MajorRepository majorRepo;

	@GetMapping(value = "/do_loadListMaintenance")
	public ModelAndView do_loadMaintenanceList(ServletRequest request, HttpSession session, Model md, String major_id) {
		ModelAndView mav = new ModelAndView("listMaintenanceDurablePage");
		try {
			Staff staffDetail = (Staff) session.getAttribute("staffSession");
			List<DurableControll> durable_maintenance = new ArrayList<>();
			List<Major> major = new ArrayList<>();
			if (staffDetail.getStaff_status().equalsIgnoreCase("เจ้าหน้าที่ส่วนกลาง")) {
				Major ma = new Major();
				ma.setID_Major(0);
				ma.setMajor_Name("-");
				major = majorRepo.getAllMajor();
				major.add(ma);
				Collections.swap(major, 0, major.size() - 1);
				
				if (major_id != null) {
					if (!major_id.equalsIgnoreCase("0")) {
						durable_maintenance = dccRepo.get_MaintenanceDurableByMajor(Integer.parseInt(major_id));
					} else {
						durable_maintenance = dccRepo.get_MaintenanceDurable();
					}
				} else {
					durable_maintenance = dccRepo.get_MaintenanceDurable();
				}
				mav.addObject("major_id", major_id);
				mav.addObject("major", major);
			} else {
				durable_maintenance = dccRepo.get_MaintenanceDurableByMajor(staffDetail.getMajor().getID_Major());
			}

			mav.addObject("durable_maintenance", durable_maintenance);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_searchMaintenance")
	public ModelAndView do_searchMaintenance(ServletRequest request, HttpSession sesison, Model md) {
		ModelAndView mav = new ModelAndView("listMaintenanceDurablePage");
		try {
			request.setCharacterEncoding("UTF-8");
			String major_id = request.getParameter("major_id");

			mav = do_loadMaintenanceList(request, sesison, md, major_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@GetMapping(value = "/do_loadMaintenanceDetail")
	public ModelAndView do_loadMaintenanceDetail(ServletRequest request, HttpSession session, Model md,
			String durable_c) {
		ModelAndView mav = new ModelAndView("maintenanceDetailPage");
		try {
			request.setCharacterEncoding("UTF-8");
			String durable_code = request.getParameter("durable_code");
			Durable durable = new Durable();
			durable.setDurable_code(durable_code);
			List<RepairDurable> maintenance_detail = null;
			Double price = 0.0;
			if (durable_c != null) {
				durable.setDurable_code(durable_c);
				maintenance_detail = rdRepo.getMaintenanceDurable_detail(durable.getDurable_code());
			} else {
				maintenance_detail = rdRepo.getMaintenanceDurable_detail(durable.getDurable_code());
			}
			String pattern = "##.##";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);

			for (RepairDurable rd : maintenance_detail) {
				price += Double.parseDouble(rd.getRepair_charges());
				rd.setRepair_charges(decimalFormat.format(Double.parseDouble(rd.getRepair_charges())));
			}

			mav.addObject("price", decimalFormat.format(price));
			mav.addObject("maintenance_detail", maintenance_detail);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@PostMapping(value = "/do_loadAddMaintenance")
	public ModelAndView do_loadAddMaintenance(ServletRequest request, HttpSession session, Model md, String did) {
		ModelAndView mav = new ModelAndView("addMaintenancePage");
		try {
			request.setCharacterEncoding("UTF-8");
			String durable_code = request.getParameter("durable_code");
			if(did != null) {
				durable_code = did;
			}
			System.out.println(durable_code);
			DurableControll durable = new DurableControll();
			durable.setDurable_code(durable_code);

			DurableControll durable_data = dccRepo.get_durableControll(durable.getDurable_code());
			
			List<RepairDurable> maintenance_detail = null;
			maintenance_detail = rdRepo.getMaintenanceDurable_detail(durable.getDurable_code());
			
			Double price = 0.0;
			String pattern = "##.##";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			
			for (RepairDurable rd : maintenance_detail) {
				price += Double.parseDouble(rd.getRepair_charges());
				rd.setRepair_charges(decimalFormat.format(Double.parseDouble(rd.getRepair_charges())));
			}

			mav.addObject("price", decimalFormat.format(price));

			mav.addObject("maintenance_detail",maintenance_detail);
			mav.addObject("durable_data", durable_data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_loadEditMaintenance")
	public ModelAndView do_loadEditMaintenance(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("editMaintenancePage");
		try {
			request.setCharacterEncoding("UTF-8");
			String repair_id = request.getParameter("repair_id");

			RepairDurable rd = new RepairDurable();
			rd.setRepair_id(Integer.parseInt(repair_id));

			RepairDurable repairDurable = new RepairDurable();
			repairDurable = rdRepo.getMaintenanceDetailById(rd.getRepair_id());

			DurableControll durable_controll = dccRepo
					.get_durableControll(repairDurable.getDurable().getDurable_code());
			mav.addObject("durable_type", durable_controll);
			mav.addObject("repair_Durable", repairDurable);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@PostMapping(value = "/do_EditMaintenance")
	public ModelAndView do_EditMaintenance(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("addMaintenancePage");
		String message = "";
		try {
			request.setCharacterEncoding("UTF-8");
			String durable_code = request.getParameter("durablecode");
			String date = request.getParameter("datepicker");
			String maintenance_detail = request.getParameter("maintenance_detail").trim();
			String maintenance_price = request.getParameter("maintenance_price");
			String company = request.getParameter("company");
			String repair_id = request.getParameter("repair_id");

			RepairDurable rd = new RepairDurable();
			rd.setRepair_id(Integer.parseInt(repair_id));
			rd.setDate_of_repair(date);
			rd.setRepair_detail(maintenance_detail);
			rd.setRepair_charges(maintenance_price);

			Durable d = new Durable();
			d.setDurable_code(durable_code);

			rd.setDurable(d);

			Company com_name_check = comRepo.company_duplicate_check(company);
			int result = 0;
			if (com_name_check == null) {
				int com_id = comRepo.get_countCompanyID() + 1;
				Company c = new Company();
				c.setCompany_id(com_id);
				c.setCompanyname(company);
				comRepo.save(c);
				rd.setCompany(c);
			} else {
				rd.setCompany(com_name_check);
			}

			result = rdRepo.update_maintenance(rd.getRepair_id(), rd.getDate_of_repair(), rd.getRepair_charges(),
					rd.getRepair_detail(), rd.getCompany().getCompany_id(), rd.getDurable().getDurable_code());

			if (result != 0) {
				message = "แก้ไขข้อมูลการซ่อมบํารุงสําเร็จ";
			} else {
				message = "ไม่สามารถแก้ไขข้อมูลประวัติการซ่อมบํารุงได้";
			}

			session.setAttribute("messages", message);

			mav = do_loadAddMaintenance(request, session, md, d.getDurable_code());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@PostMapping(value = "/do_AddMaintenance")
	public ModelAndView do_AddMaintenance(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("listMaintenanceDurablePage");
		String message = "";
		try {
			request.setCharacterEncoding("UTF-8");
			String durable_code = request.getParameter("durablecode");
			String date = request.getParameter("datepicker");
			String maintenance_detail = request.getParameter("maintenance_detail").trim();
			String maintenance_price = request.getParameter("maintenance_price");
			String company = request.getParameter("company");

			Durable durable = new Durable();
			durable.setDurable_code(durable_code);

			RepairDurable rd = new RepairDurable();
			int id = rdRepo.count_repair();
			rd.setRepair_id(id+1);
			rd.setDate_of_repair(date);
			rd.setRepair_detail(maintenance_detail);
			rd.setRepair_charges(maintenance_price);

			durable = dcRepo.getDurableByCode(durable.getDurable_code());

			int com_id = comRepo.get_countCompanyID();

			Company c = new Company();
			Company com_name_check = comRepo.company_duplicate_check(company);
			
			if(com_name_check != null) {
				c.setCompany_id(com_name_check.getCompany_id());
			}else {
				c.setCompany_id(com_id + 1);
				c.setCompanyname(company);
				comRepo.save(c);
			}
		
			rd.setDurable(durable);
			rd.setCompany(c);
			int result = 1;
			try {
				rdRepo.add_maintenance(rd.getRepair_id(), rd.getDate_of_repair(), rd.getRepair_charges(), rd.getRepair_detail(), c.getCompany_id(), rd.getDurable().getDurable_code());
			} catch (Exception e) {
				result = 0;
				e.printStackTrace();
			}

			if (result == 1) {
				message = "บันทึกประวัติการซ่อมบํารุงสําเร็จ";
			} else {
				message = "ข้อผิดพลาด : ไม่สามารถบันทึกประวัติการซ่อมบํารุงได้";
			}
			
			session.setAttribute("messages", message);
			mav = do_loadAddMaintenance(request, session, md, durable.getDurable_code());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

}
