import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:logger/logger.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../manager/login_manager.dart';
import '../model/staff_model.dart';


class MyHeaderDrawer extends StatefulWidget {
  @override
  _MyHeaderState createState() => _MyHeaderState();
}

class _MyHeaderState extends State<MyHeaderDrawer> {
  String staff = "";
  bool isLoading = true;
  Staff? s;
  String? name;

  void findUser() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    login_manager lm = login_manager();

    var log = Logger();


    setState(() {
      staff = preferences.getString('Staff')!;
      Map<String, dynamic> map = jsonDecode(staff);
      s = Staff.fromJson(map);
      name = s!.Staff_name.toString();
      isLoading = false;
    });
    log.e("ผู้ใช้:"+s!.toString());

  }
  @override
  void initState() {
    super.initState();
    findUser();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.indigo,
      width: double.infinity,
      height: 200,
      padding: EdgeInsets.only(top: 25.0),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Container(
            margin: EdgeInsets.only(bottom: 5),
            height: 80,
            decoration: const BoxDecoration(
                shape: BoxShape.circle,
                image: DecorationImage(
                    image: NetworkImage(
                        "https://i.pinimg.com/originals/76/80/4f/76804f67ba38f85e4802d250e5b15504.jpg"))),
          ),

           Text(s==null? "":s!.Staff_name+"  "+s!.Staff_lastname,style: TextStyle(color: Colors.white, fontSize: 20) ),
          Text(s==null? "":s!.Email,style: TextStyle(color: Colors.white60, fontSize: 14) ),
           Text(s==null? "":"สถานะ : "+s!.Staff_status,style: TextStyle(color: Colors.white60, fontSize: 14)),
        ],
      ),

    );
  }



}
