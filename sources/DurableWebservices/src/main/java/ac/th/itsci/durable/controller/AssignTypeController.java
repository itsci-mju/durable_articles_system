package ac.th.itsci.durable.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ac.th.itsci.durable.repo.AssignTypeRepository;
import ac.th.itsci.durable.repo.PersonnelAssignRepository;
import ac.th.itsci.durable.repo.PersonnelRepository;

import ac.th.itsci.durable.entity.*;

@Controller
public class AssignTypeController {

	@Autowired
	PersonnelRepository personnelRepo;

	@Autowired
	AssignTypeRepository assignTypeRepo;

	@Autowired
	PersonnelAssignRepository personnelAssignRepo;
	
//	@GetMapping(value="/test_re")
//	public ModelAndView test_re(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("index");
//		try {
//			RequisitionManager rm = new RequisitionManager();
//			
//			List<RequisitionItem> r = rm.get_requisition_list("คณะวิทยาศาสตร์","2020");
//			
//			
//			System.out.println(r.size());
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return mav;
//	}

	@GetMapping(value = "/do_loadAssignSignaturePage")
	public ModelAndView do_loadAssignSignaturePage(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("AddAssign");

		try {
			request.setCharacterEncoding("UTF-8");

			List<AssignType> assignTypes = assignTypeRepo.get_allAssignType();
			List<Personnel> personnels = personnelRepo.get_allPersonnel();

			List<PersonnelAssign> purchase_order_request_1 = personnelAssignRepo.get_personnelByAssignType("doc_1_1");
			List<PersonnelAssign> purchase_order_request_2 = personnelAssignRepo.get_personnelByAssignType("doc_1_2");
			List<PersonnelAssign> purchase_order_request_3 = personnelAssignRepo.get_personnelByAssignType("doc_1_3");
			List<PersonnelAssign> purchase_order_request_4 = personnelAssignRepo.get_personnelByAssignType("doc_1_4");
			
			List<PersonnelAssign> purchase_order_1 = personnelAssignRepo.get_personnelByAssignType("doc_2_1");
			List<PersonnelAssign> purchase_order_2 = personnelAssignRepo.get_personnelByAssignType("doc_2_2");
			List<PersonnelAssign> purchase_order_3 = personnelAssignRepo.get_personnelByAssignType("doc_2_3");
			List<PersonnelAssign> purchase_order_6 = personnelAssignRepo.get_personnelByAssignType("doc_2_6");
			
			List<PersonnelAssign> receive_order_2 = personnelAssignRepo.get_personnelByAssignType("doc_3_2");
			
			List<PersonnelAssign> bill_of_lading_1 = personnelAssignRepo.get_personnelByAssignType("doc_4_1");
			List<PersonnelAssign> bill_of_lading_2 = personnelAssignRepo.get_personnelByAssignType("doc_4_2");
			List<PersonnelAssign> bill_of_lading_3 = personnelAssignRepo.get_personnelByAssignType("doc_4_3");
			List<PersonnelAssign> bill_of_lading_4 = personnelAssignRepo.get_personnelByAssignType("doc_4_4");
			List<PersonnelAssign> bill_of_lading_5 = personnelAssignRepo.get_personnelByAssignType("doc_4_5");

			mav.addObject("assignType", assignTypes);
			mav.addObject("all", personnels);
			
			mav.addObject("doc_1_1", purchase_order_request_1);
			mav.addObject("doc_1_2", purchase_order_request_2);
			mav.addObject("doc_1_3", purchase_order_request_3);
			mav.addObject("doc_1_4", purchase_order_request_4);
			
			mav.addObject("doc_2_1", purchase_order_1);
			mav.addObject("doc_2_2", purchase_order_2);
			mav.addObject("doc_2_3", purchase_order_3);
			mav.addObject("doc_2_6", purchase_order_6);
			
			mav.addObject("doc_3_2", receive_order_2);
			
			mav.addObject("doc_4_1", bill_of_lading_1);
			mav.addObject("doc_4_2", bill_of_lading_2);
			mav.addObject("doc_4_3", bill_of_lading_3);
			mav.addObject("doc_4_4", bill_of_lading_4);
			mav.addObject("doc_4_5", bill_of_lading_5);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@PostMapping(value = "do_addAssignType")
	public ModelAndView do_addAssignTypeCof(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("AddAssign");
		String message = "";
		int result = 0;
		try {
			request.setCharacterEncoding("UTF-8");
			String personnel_id = request.getParameter("personnel_id");
			String assign_id = request.getParameter("assing_id");

			AssignType assignType = new AssignType();
			assignType.setAssignType_id(assign_id);

			Personnel personnel = new Personnel();
			personnel.setPersonnel_id(personnel_id);

			PersonnelAssignID personnelAssignID = new PersonnelAssignID();
			personnelAssignID.setAssignType(assignType);
			personnelAssignID.setPersonnel(personnel);

			PersonnelAssign personnelAssign = new PersonnelAssign();
			personnelAssign.setPersonnelAssign(personnelAssignID);

			result = personnelAssignRepo.insert_assignPersonnel(
					personnelAssign.getPersonnelAssign().getAssignType().getAssignType_id(),
					personnelAssign.getPersonnelAssign().getPersonnel().getPersonnel_id());

			if (result > 0) {
				message = "กําหนดหน้าที่การเซ็นเอกสารสําเร็จ";
			}

		} catch (Exception e) {
			message = "ข้อผิดพลาด :: ไม่สามารถกําหนดหน้าที่การเซ็นเอกสารได้";
		}

		mav = do_loadAssignSignaturePage(request, session, md);
		mav.addObject("message", message);

		return mav;
	}

	@PostMapping(value = "do_removeAssign")
	public ModelAndView do_removeAssign(ServletRequest request, HttpSession session, Model md) {
		ModelAndView mav = new ModelAndView("AddAssign");
		String message = "";
		int result = 0;
		try {
			request.setCharacterEncoding("UTF-8");
			String personnel_id = request.getParameter("personnel_id");
			String assign_id = request.getParameter("assign_id");

			AssignType assignType = new AssignType();
			assignType.setAssignType_id(assign_id);

			Personnel personnel = new Personnel();
			personnel.setPersonnel_id(personnel_id);

			PersonnelAssignID personnelAssignID = new PersonnelAssignID();
			personnelAssignID.setAssignType(assignType);
			personnelAssignID.setPersonnel(personnel);

			PersonnelAssign personnelAssign = new PersonnelAssign();
			personnelAssign.setPersonnelAssign(personnelAssignID);

			result = personnelAssignRepo.remove_assignPersonnel(
					personnelAssign.getPersonnelAssign().getAssignType().getAssignType_id(),
					personnelAssign.getPersonnelAssign().getPersonnel().getPersonnel_id());
			
			if(result > 0) {
				message = "ลบหน้าที่การเซ็นเอกสารสําเร็จ";
			}

		} catch (Exception e) {
			message = "ข้อผิดพลาด :: ไม่สามารถลบหน้าที่การเซ็นเอกสารได้";
		}
		
		mav = do_loadAssignSignaturePage(request, session, md);
		mav.addObject("message", message);
		
		return mav;
	}

}
