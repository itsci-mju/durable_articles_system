package ac.th.itsci.durable.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.th.itsci.durable.entity.*;
import ac.th.itsci.durable.repo.RoomRepository;
import ac.th.itsci.durable.util.ResponseObj;

@Controller
@RequestMapping(path="/room")
public class RoomController {
	
	@Autowired
	RoomRepository Mroomrepository;
	
	@PostMapping(path="/RoomAllDetail" , consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj getRoom() {
		
		List<Room> list = Mroomrepository.getAllRoom();
		List<Room> listRoomDetail = new ArrayList<Room>();		
		
		if(list == null) {
			System.out.println("not found");
			return new ResponseObj(500,"not found");
		}else {
			for(int i = 0 ; i < list.size() ; i++) {
				Room r = new Room();
				try {
					if(list.get(i).getBuild() == null) {
						System.out.println("Build Null");
						r.setBuild("-");
					}else {
						r.setBuild(URLEncoder.encode(list.get(i).getBuild(),"UTF-8"));
					}
					if(list.get(i).getFloor() == null) {
						System.out.println("getFloor Null");
						r.setFloor("-");
					}else {
						r.setFloor(URLEncoder.encode(list.get(i).getFloor(),"UTF-8"));
					}
					if(list.get(i).getRoom_name() == null) {
						System.out.println("getRoom_name Null");
						r.setRoom_name("-");
					}else {
						r.setRoom_name(URLEncoder.encode(list.get(i).getRoom_name(),"UTF-8"));
					}
					if(list.get(i).getRoom_number() == null) {
						System.out.println("getRoom_number Null");
						r.setRoom_number("-");
					}else {
						r.setRoom_number(URLEncoder.encode(list.get(i).getRoom_number(),"UTF-8"));
					}
					
//					r.setMajor(new Major(list.get(i).getMajor().getID_Major(),URLEncoder.encode(list.get(i).getMajor().getMajor_Name(),"UTF-8")));
//					listRoomDetail.add(r);		
//					System.out.println(listRoomDetail.get(0).getMajor().getMajor_Name());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			 } 
					return new ResponseObj(200,listRoomDetail);
		}
			
	}
	
	@PostMapping(path="/RoomAllDetailByMajor" , consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObj getRoomAllDetailByMajor(@RequestBody int idmajor) 
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		List<Room> list = Mroomrepository.room_getAllRoomByMajor(idmajor);	
		List<Room> listRoomDetail = new ArrayList<Room>();		
		
		System.out.println(idmajor);
		
		if(list.size() == 0) {
			return new ResponseObj(500,"not found");
		}else {
			for(int i = 0 ; i < list.size() ; i++) {
				Room r = new Room();
				try {
					if(list.get(i).getBuild() == null) {
						System.out.println("Build Null");
						r.setBuild("-");
					}else {
						r.setBuild(URLEncoder.encode(list.get(i).getBuild(),"UTF-8"));
					}
					if(list.get(i).getFloor() == null) {
						System.out.println("getFloor Null");
						r.setFloor("-");
					}else {
						r.setFloor(URLEncoder.encode(list.get(i).getFloor(),"UTF-8"));
					}
					if(list.get(i).getRoom_name() == null) {
						System.out.println("getRoom_name Null");
						r.setRoom_name("-");
					}else {
						r.setRoom_name(URLEncoder.encode(list.get(i).getRoom_name(),"UTF-8"));
					}
					if(list.get(i).getRoom_number() == null) {
						System.out.println("getRoom_number Null");
						r.setRoom_number("-");
					}else {
						r.setRoom_number(URLEncoder.encode(list.get(i).getRoom_number(),"UTF-8"));
					}
					
//					r.setMajor(new Major(list.get(i).getMajor().getID_Major(),URLEncoder.encode(list.get(i).getMajor().getMajor_Name(),"UTF-8")));
					listRoomDetail.add(r);		
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			 }
					return new ResponseObj(200,listRoomDetail);
		}
			
	}
}
