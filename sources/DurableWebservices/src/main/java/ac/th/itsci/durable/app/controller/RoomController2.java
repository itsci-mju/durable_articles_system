package ac.th.itsci.durable.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.th.itsci.durable.app.manager.RoomManager;
import ac.th.itsci.durable.entity.Room;
import ac.th.itsci.durable.util.ResponseObj;



@Controller
@RequestMapping(path = "/approom", method = RequestMethod.POST)
public class RoomController2 {
	private static String SALT = "123456";

	//Testlistroombymajor
	@PostMapping(path = "/Testlistroombymajor")
	public @ResponseBody ResponseObj Testlistroombymajor(HttpServletRequest request) {
		List<Room> room = null;
		try {
			RoomManager rm = new RoomManager();	
			room =  rm.listroombymajor("1");
			System.out.println(room.toString());
			return new ResponseObj(200, room);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, room);
	}
	
	@PostMapping(path = "/listroombymajor")
	public @ResponseBody ResponseObj do_listroombymajor(@RequestBody Map<String, String> map ,HttpServletRequest request) {
		List<Room> room = null;
		try {
			String majorid = map.get("major_id");
			RoomManager rm = new RoomManager();	
			room =  rm.listroombymajor(majorid);
			System.out.println(room.toString());
			return new ResponseObj(200, room);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, room);
	}
	
	@PostMapping(path = "/listallroom")
	public @ResponseBody ResponseObj do_listallroom(HttpServletRequest request) {
		List<Room> room = null;
		try {
			RoomManager rm = new RoomManager();	
			room =  rm.listallroom();
			System.out.println(room.toString());
			return new ResponseObj(200, room);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseObj(500, room);
	}

}
