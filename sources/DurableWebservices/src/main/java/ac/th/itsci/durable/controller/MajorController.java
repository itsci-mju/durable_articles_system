package ac.th.itsci.durable.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.th.itsci.durable.entity.Major;
import ac.th.itsci.durable.repo.MajorRepository;
import ac.th.itsci.durable.util.ResponseObj;


@Controller
@RequestMapping(path="/major")
public class MajorController {
	
	@Autowired
	MajorRepository mMajorRepository;
	
	@PostMapping(path="/majorlist" , consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj getRoom() {
		
		List<Major> list = mMajorRepository.getAllMajor();
		List<Major> majorList = new ArrayList<Major>();	
		
		if(list.size()==0 || list == null){
			return new ResponseObj(500,"not found");
		}else {
			for(int i = 0 ; i < list.size() ; i++) {
				Major m = new Major();				
				try {	
					m.setID_Major(list.get(i).getID_Major());
					m.setMajor_Name(URLEncoder.encode(list.get(i).getMajor_Name(),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				majorList.add(m);
			}
				
			return new ResponseObj(200,majorList);
		}
	}
}
