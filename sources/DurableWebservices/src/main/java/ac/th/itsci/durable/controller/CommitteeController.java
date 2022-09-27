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

import ac.th.itsci.durable.entity.Committee;
import ac.th.itsci.durable.entity.CommitteeID;
import ac.th.itsci.durable.entity.Document;
import ac.th.itsci.durable.entity.Personnel;
import ac.th.itsci.durable.entity.Position;
import ac.th.itsci.durable.repo.CommitteeRepository;
import ac.th.itsci.durable.repo.PersonnelRepository;

@Controller
public class CommitteeController {
	@Autowired
	CommitteeRepository committeeRepo;

	@Autowired
	PersonnelRepository personnelRepo;
	
//	@PostMapping(value = "/do_addCommittee")
//	public ModelAndView do_addCommittee(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("addPurchaseOrderRequestPage");
//		String message = "";
//		try {
//			request.setCharacterEncoding("UTF-8");
//			String committee_id = request.getParameter("committee");
//
//			Personnel personnel = new Personnel();
//			CommitteeID committeeID = new CommitteeID();
//			Committee committee = new Committee();
//			List<Committee> committee_list = new ArrayList<>();
//
//			if (session.getAttribute("committees") != null) {
//				committee_list = (List<Committee>) session.getAttribute("committees");
//				committee_list.get(0).setCommittee_status("ประธานกรรมการ");
//			}
//
//			personnel = personnelRepo.get_personnelByID(committee_id);
//			committeeID.setPersonnel(personnel);
//			committee.setCommittee(committeeID);
//			committee.setCommittee_status("กรรมการ");
//
//			committee_list.add(committee);
//			message = "เพิ่มรายชื่อกรรมการสําเร็จ";
//			
//			session.setAttribute("committees", committee_list);
//		} catch (Exception e) {
//			message = "เพิ่มรายชื่อกรรมการไม่สําเร็จ";
//		}
//		
//
//		mav.addObject("message", message);
//
//		return mav;
//	}
//
//	@GetMapping(value = "/do_removeCommittee")
//	public ModelAndView do_removeCimittee(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("addPurchaseOrderRequestPage");
//		try {
//			request.setCharacterEncoding("UTF-8");
//
//			String committee_index = request.getParameter("committee_index");
//			List<Committee> committees = (List<Committee>) session.getAttribute("committees");
//			committees.remove(Integer.parseInt(committee_index));
//
//			if (committees.size() == 1) {
//				committees.get(0).setCommittee_status("กรรมการ");
//			} else {
//				if (committee_index.equals("0")) {
//					committees.get(0).setCommittee_status("ประธานกรรมการ");
//				}
//			}
//
//			String message = "นํากรรมการออกเสร็จสิ้น";
//			
//			
//			mav.addObject("message", message);
//			session.setAttribute("committees", committees);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}
//
//	@PostMapping(value = "/do_addCommittee_old_update")
//	public ModelAndView do_addCommittee_old_update(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("editPurchaseOrderRequestPage");
//		String message = "";
//		try {
//			request.setCharacterEncoding("UTF-8");
//			String committee_id = request.getParameter("committee_list");
//
//			Personnel personnel = new Personnel();
//			CommitteeID committeeID = new CommitteeID();
//			Committee committee = new Committee();
//			List<Committee> committee_list = (List<Committee>) session.getAttribute("committee_update");
//			
//			if (committee_list.size() != 0) {
//				committee_list = (List<Committee>) session.getAttribute("committee_update");
//				committee_list.get(0).setCommittee_status("ประธานกรรมการ");
//			}
//
//			personnel = personnelRepo.get_personnelByID(committee_id);
//			committeeID.setPersonnel(personnel);
//			committee.setCommittee(committeeID);
//			committee.setCommittee_status("กรรมการ");
//
//			committee_list.add(committee);
//			message = "เพิ่มรายชื่อกรรมการสําเร็จ";
//
//			session.setAttribute("committee_update", committee_list);
//		} catch (Exception e) {
//			message = "เพิ่มรายชื่อกรรมการไม่สําเร็จ";
//		}
//		
//
//		mav.addObject("message", message);
//
//		return mav;
//	}
//
//	@GetMapping(value = "/do_removeCommittee_update")
//	public ModelAndView do_removeCommittee_update(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("editPurchaseOrderRequestPage");
//		try {
//			request.setCharacterEncoding("UTF-8");
//
//			String committee_index = request.getParameter("committee_index");
//			List<Committee> committees = (List<Committee>) session.getAttribute("committee_update");
//			committees.remove(Integer.parseInt(committee_index));
//
//			if (committees.size() == 1) {
//				committees.get(0).setCommittee_status("กรรมการ");
//			} else {
//				if (committee_index.equals("0")) {
//					committees.get(0).setCommittee_status("ประธานกรรมการ");
//				}
//			}
//
//			String message = "นํากรรมการออกเสร็จสิ้น";
//			mav.addObject("message", message);
//			session.setAttribute("committee_update", committees);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}

//	@PostMapping(value = "/do_addCommittee_new")
//	public ModelAndView do_addCommittee(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("addPurchaseOrderRequestPage");
//
//		try {
//
//			request.setCharacterEncoding("UTF-8");
//
//			String committee_new_prefix = request.getParameter("committee_new_prefix");
//			String committee_new_name = request.getParameter("committee_new_name");
//			String committee_new_lname = request.getParameter("committee_new_lname");
//			String committee_new_position = request.getParameter("committee_new_position");
//
//			Personnel personnel = new Personnel();
//			Position position = new Position();
//
//			personnel.setPersonnel_prefix(committee_new_prefix);
//			personnel.setPersonnel_firstName(committee_new_name);
//			personnel.setPersonnel_lastName(committee_new_lname);
//			position.setPosition_name(committee_new_position);
//			personnel.setPosition(position);
//
//			String message = "";
//
//			if (session.getAttribute("committees") == null) {
//				List<Personnel> committees = new ArrayList<>();
//				committees.add(personnel);
//				session.setAttribute("committees", committees);
//				message = "เพิ่มรายชื่อกรรมการสําเร็จ";
//			} else {
//				List<Personnel> committees = (List<Personnel>) session.getAttribute("committees");
//				if (committees.size() > 2) {
//					message = "รายชื่อกรรมการไม่สามารถเพิ่มได้เกิน 3 คน !";
//				} else {
//					committees.add(personnel);
//					session.setAttribute("committees", committees);
//					message = "เพิ่มรายชื่อกรรมการสําเร็จ";
//				}
//
//			}
//
//			// save session from document
//			String doc_id = request.getParameter("doc_id");
//			String plan_id = request.getParameter("plan_id");
//			String plan_name = request.getParameter("plan_name");
//			String depart_id = request.getParameter("depart_id");
//			String depart_name = request.getParameter("depart_name");
//			String fund_id = request.getParameter("fund_id");
//			String fund_name = request.getParameter("fund_name");
//			String money_used = request.getParameter("money_used");
//			String work_name = request.getParameter("work_name");
//			String budget = request.getParameter("budget");
//			String date = request.getParameter("date");
//			String doc_title = request.getParameter("doc_title");
//			String doc_dear = request.getParameter("doc_dear");
//			String doc_title_describe = request.getParameter("doc_title_describe");
//			String doc_reason_title = request.getParameter("doc_reason_title");
//			String doc_reason_describe = request.getParameter("doc_reason_describe");
//			String price_txt = request.getParameter("price_txt");
//
//			Document document = new Document(doc_id, plan_id, plan_name, depart_id, depart_name, fund_id, fund_name,
//					money_used, work_name, budget, null, doc_title, doc_dear, doc_title_describe, doc_reason_title,
//					doc_reason_describe, null);
//			document.setPrice_txt(price_txt);
//
//			session.setAttribute("document", document);
//			session.setAttribute("date", date);
//			// end session
//
//			mav.addObject("message", message);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}
//	
//	@PostMapping(value = "/do_addCommittee_new_update")
//	public ModelAndView do_addCommittee_update(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("editPurchaseOrderRequestPage");
//
//		try {
//
//			request.setCharacterEncoding("UTF-8");
//
//			String committee_new_prefix = request.getParameter("committee_new_prefix");
//			String committee_new_name = request.getParameter("committee_new_name");
//			String committee_new_lname = request.getParameter("committee_new_lname");
//			String committee_new_position = request.getParameter("committee_new_position");
//
//			Personnel personnel = new Personnel();
//			Position position = new Position();
//
//			personnel.setPersonnel_prefix(committee_new_prefix);
//			personnel.setPersonnel_firstName(committee_new_name);
//			personnel.setPersonnel_lastName(committee_new_lname);
//			position.setPosition_name(committee_new_position);
//			personnel.setPosition(position);
//
//			String message = "";
//
//			if (session.getAttribute("committees_update") == null) {
//				List<Personnel> committees = new ArrayList<>();
//				committees.add(personnel);
//				session.setAttribute("committees_update", committees);
//				message = "เพิ่มรายชื่อกรรมการสําเร็จ";
//			} else {
//				List<Personnel> committees = (List<Personnel>) session.getAttribute("committees_update");
//				if (committees.size() > 2) {
//					message = "รายชื่อกรรมการไม่สามารถเพิ่มได้เกิน 3 คน !";
//				} else {
//					committees.add(personnel);
//					session.setAttribute("committees_update", committees);
//					message = "เพิ่มรายชื่อกรรมการสําเร็จ";
//				}
//
//			}
//
//			// save session from document
//			String doc_id = request.getParameter("doc_id");
//			String plan_id = request.getParameter("plan_id");
//			String plan_name = request.getParameter("plan_name");
//			String depart_id = request.getParameter("depart_id");
//			String depart_name = request.getParameter("depart_name");
//			String fund_id = request.getParameter("fund_id");
//			String fund_name = request.getParameter("fund_name");
//			String money_used = request.getParameter("money_used");
//			String work_name = request.getParameter("work_name");
//			String budget = request.getParameter("budget");
//			String date = request.getParameter("date");
//			String doc_title = request.getParameter("doc_title");
//			String doc_dear = request.getParameter("doc_dear");
//			String doc_title_describe = request.getParameter("doc_title_describe");
//			String doc_reason_title = request.getParameter("doc_reason_title");
//			String doc_reason_describe = request.getParameter("doc_reason_describe");
//			String price_txt = request.getParameter("price_txt");
//
//			String requisition_old = request.getParameter("requisition_old");
//			String cof_old = request.getParameter("cof_old");
//			String acc_old = request.getParameter("acc_old");
//			String old_secretary = request.getParameter("old_secretary");
//			Document document = new Document(doc_id, plan_id, plan_name, depart_id, depart_name, fund_id, fund_name,
//					money_used, work_name, budget, null, doc_title, doc_dear, doc_title_describe, doc_reason_title,
//					doc_reason_describe, null);
//			
//			document.setPrice_txt(price_txt);
//			document.setChief_of_procurement(cof_old);
//			document.setRequest_order_person(requisition_old);
//			document.setAccounting_officer(acc_old);
//			document.setSecretary(old_secretary);
//
//
//			session.setAttribute("document", document);
//			session.setAttribute("date", date);
//			// end session
//
//			mav.addObject("message", message);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}

//	@PostMapping(value = "/do_addCommittee_old")
//	public ModelAndView do_addCommittee_old(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("addPurchaseOrderRequestPage");
//		try {
//			request.setCharacterEncoding("UTF-8");
//
//			String committee_old_id = request.getParameter("committee");
//
//			// Committee committee =
//			// committeeRepo.get_committeeByPersonnelID(committee_old_id);
//
//			Personnel personnel = personnelRepo.get_PersonnelByPersonnelId(committee_old_id);
//
//			String message = "";
//			if (committee_old_id.equalsIgnoreCase("-")) {
//				message = "กรุณาเลือกรายชื่อกรรมการ";
//			} else {
//				if (session.getAttribute("committees") == null) {
//					List<Personnel> committees = new ArrayList<>();
//					// committees.add(committee.getCommittee().getPersonnel());
//					committees.add(personnel);
//					session.setAttribute("committees", committees);
//					message = "เพิ่มรายชื่อกรรมการสําเร็จ";
//				} else {
//					List<Personnel> committees = (List<Personnel>) session.getAttribute("committees");
//					if (committees.size() > 2) {
//						message = "รายชื่อกรรมการไม่สามารถเพิ่มได้เกิน 3 คน !";
//					} else {
//						// committees.add(committee.getCommittee().getPersonnel());
//						boolean check_duplicate = false;
//						for (Personnel p : committees) {
//							if (p.getPersonnel_id().equalsIgnoreCase(committee_old_id)) {
//								check_duplicate = true;
//								break;
//							}
//						}
//						if (check_duplicate == false) {
//							committees.add(personnel);
//							message = "เพิ่มรายชื่อกรรมการสําเร็จ";
//						} else {
//							message = "รายชื่อกรรมการซํ้า !!";
//						}
//
//						session.setAttribute("committees", committees);
//
//					}
//
//				}
//			}
//
//			// save session from document
//
//			String doc_id = request.getParameter("doc_id");
//			String plan_id = request.getParameter("plan_id");
//			String plan_name = request.getParameter("plan_name");
//			String depart_id = request.getParameter("depart_id");
//			String depart_name = request.getParameter("depart_name");
//			String fund_id = request.getParameter("fund_id");
//			String fund_name = request.getParameter("fund_name");
//			String money_used = request.getParameter("money_used");
//			String work_name = request.getParameter("work_name");
//			String budget = request.getParameter("budget");
//			String date = request.getParameter("date");
//			String doc_title = request.getParameter("doc_title");
//			String doc_dear = request.getParameter("doc_dear");
//			String doc_title_describe = request.getParameter("doc_title_describe");
//			String doc_reason_title = request.getParameter("doc_reason_title");
//			String doc_reason_describe = request.getParameter("doc_reason_describe");
//			String price_txt = request.getParameter("price_txt");
//
//			Document document = new Document(doc_id, plan_id, plan_name, depart_id, depart_name, fund_id, fund_name,
//					money_used, work_name, budget, null, doc_title, doc_dear, doc_title_describe, doc_reason_title,
//					doc_reason_describe, null);
//			document.setPrice_txt(price_txt);
//
//			session.setAttribute("document", document);
//			session.setAttribute("date", date);
//			// end session
//
//			mav.addObject("message", message);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}
//
//	@PostMapping(value = "/do_addCommittee_old_update")
//	public ModelAndView do_addCommittee_old_update(ServletRequest request, HttpSession session, Model md) {
//		ModelAndView mav = new ModelAndView("editPurchaseOrderRequestPage");
//		try {
//			request.setCharacterEncoding("UTF-8");
//
//			String committee_old_id = request.getParameter("committee_old");
//
//			//Committee committee = committeeRepo.get_committeeByPersonnelID(committee_old_id);
//			Personnel personnel = personnelRepo.get_PersonnelByPersonnelId(committee_old_id);
//			
//
//			String message = "";
//
//			if (session.getAttribute("committees_update") == null) {
//				List<Personnel> committees = new ArrayList<>();
//				committees.add(personnel);
//				session.setAttribute("committees_update", committees);
//				message = "เพิ่มรายชื่อกรรมการสําเร็จ";
//			} else {
//				List<Personnel> committees = (List<Personnel>) session.getAttribute("committees_update");
//				if (committees.size() > 2) {
//					message = "รายชื่อกรรมการไม่สามารถเพิ่มได้เกิน 3 คน !";
//				} else {
//					boolean ch_dup = false;
//					for(Personnel p : committees) {
//						if(p.getPersonnel_id().equals(personnel.getPersonnel_id())) {
//							ch_dup = true;
//							break;
//						}
//					}
//					if(ch_dup) {
//						message = "รายชื่อกรรมการซํ้า !!";
//					}else {
//						committees.add(personnel);
//						message = "เพิ่มรายชื่อกรรมการสําเร็จ";
//					}
//				
//					session.setAttribute("committees_update", committees);
//					
//				}
//
//			}
//
//			// save session from document
//			String doc_id = request.getParameter("doc_id");
//			String plan_id = request.getParameter("plan_id");
//			String plan_name = request.getParameter("plan_name");
//			String depart_id = request.getParameter("depart_id");
//			String depart_name = request.getParameter("depart_name");
//			String fund_id = request.getParameter("fund_id");
//			String fund_name = request.getParameter("fund_name");
//			String money_used = request.getParameter("money_used");
//			String work_name = request.getParameter("work_name");
//			String budget = request.getParameter("budget");
//			String date = request.getParameter("date");
//			String doc_title = request.getParameter("doc_title");
//			String doc_dear = request.getParameter("doc_dear");
//			String doc_title_describe = request.getParameter("doc_title_describe");
//			String doc_reason_title = request.getParameter("doc_reason_title");
//			String doc_reason_describe = request.getParameter("doc_reason_describe");
//			String price_txt = request.getParameter("price_txt");
//
//			String requisition_old = request.getParameter("requisition_old");
//			String cof_old = request.getParameter("cof_old");
//			String acc_old = request.getParameter("acc_old");
//			String old_secretary = request.getParameter("old_secretary");
//			Document document = new Document(doc_id, plan_id, plan_name, depart_id, depart_name, fund_id, fund_name,
//					money_used, work_name, budget, null, doc_title, doc_dear, doc_title_describe, doc_reason_title,
//					doc_reason_describe, null);
//
//			document.setPrice_txt(price_txt);
//			document.setChief_of_procurement(cof_old);
//			document.setRequest_order_person(requisition_old);
//			document.setAccounting_officer(acc_old);
//			document.setSecretary(old_secretary);
//
//			session.setAttribute("document", document);
//			session.setAttribute("date", date);
//			// end session
//
//			mav.addObject("message", message);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mav;
//	}}
}
