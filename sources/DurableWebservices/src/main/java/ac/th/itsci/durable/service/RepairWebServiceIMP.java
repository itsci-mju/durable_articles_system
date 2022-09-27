package ac.th.itsci.durable.service;

import java.util.Collections;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.entity.RepairDurable;
import ac.th.itsci.durable.entity.Staff;
import ac.th.itsci.durable.repo.MajorRepository;
import ac.th.itsci.durable.repo.RepairRepository;

@Service
public class RepairWebServiceIMP implements RepairWebService{

	@Autowired
	RepairRepository repairRepo;
	
	@Autowired
	MajorRepository majorRepo;
	
	@Override
	public ModelAndView call_listDilapidatedDurable(ServletRequest request, HttpSession session) {
		Staff staffDetail = (Staff) session.getAttribute("staffSession");
		ModelAndView mav = new ModelAndView("listdilapidateddurable");
		List<RepairDurable> dilapidatedDurableBeans = null;
		List<Major> listMajor = majorRepo.getAllMajor();
		try {
			request.setCharacterEncoding("UTF-8");
			String major_id = request.getParameter("major_id");

			if (major_id == null) {
				dilapidatedDurableBeans = repairRepo.repair_getAllRepairByMajor(staffDetail.getMajor().getID_Major());
			} else {
				dilapidatedDurableBeans = repairRepo.repair_getAllRepairByMajor(Integer.parseInt(major_id));
				for (int i = 0; i < listMajor.size(); i++) {
					if (listMajor.get(i).getID_Major() == Integer.parseInt(major_id)) {
						Collections.swap(listMajor, 0, i);
					}
				}
			}

			if (dilapidatedDurableBeans.size() == 0) {
				mav.addObject("list_dilapidatedDurable", null);
				mav.addObject("listMajor", listMajor);
			} else {
				for (int i = 0; i < dilapidatedDurableBeans.size(); i++) {
					if (dilapidatedDurableBeans.get(i).getRepair_status().equalsIgnoreCase("0")) {
						dilapidatedDurableBeans.get(i).setRepair_status("รอการยืนยันการซ่อมแซม");
					}
				}
				mav.addObject("list_dilapidatedDurable", dilapidatedDurableBeans);
				mav.addObject("listMajor", listMajor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

}
