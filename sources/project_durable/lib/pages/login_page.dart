import 'dart:convert';
import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:logger/logger.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../manager/login_manager.dart';
import '../model/durable_model.dart';
import '../model/staff_model.dart';
import '../utils/form_helper.dart';
import 'home_page.dart';

class Login_Page extends StatefulWidget {
  @override
  _Login_PageState createState() => _Login_PageState();
}

class _Login_PageState extends State<Login_Page> {
  bool isLoading = false;
  bool hide = true;
  var emailController = TextEditingController();
  var passController = TextEditingController();

  showAlert(String alert) async{
    Alert(context: context,
        type: AlertType.error,
        title: "แจ้งเตือนการเข้าสู่ระบบ",desc: alert,
    buttons: [
      DialogButton(
          child: const Text("ตกลง",
          style: TextStyle(color: Colors.white,fontSize: 20)),
          onPressed: ()=> Navigator.pop(context),
      width: 120,)
    ]).show();
  }
  @override
  Widget build(BuildContext context) {
   return GestureDetector(
     onTap: () => FocusScope.of(context).unfocus(),
     child: Scaffold(
       resizeToAvoidBottomInset: false,
       backgroundColor: Colors.orange.shade900,
       body: Stack(
         children: [
           Container(
             alignment: Alignment.center,
             margin: EdgeInsets.only(top: 550),
             width: double.infinity,
             height: 600,
             decoration: BoxDecoration(
               color: Colors.white,
                   borderRadius: BorderRadius.only(topRight: Radius.circular(40),topLeft: Radius.circular(40))
             ),
           ),
           Container(
             alignment: Alignment.center,
             padding: EdgeInsets.symmetric(horizontal: 20,vertical: 20),
             margin: EdgeInsets.only(top: 350,left: 50,right: 50),
             width: double.infinity,
             height: 350,
             decoration: BoxDecoration(
                 color: Colors.white,
                 borderRadius: BorderRadius.circular(12),
                 boxShadow: [
                   BoxShadow(
                       color: Colors.black38,
                       spreadRadius: 0.1,
                       blurRadius: 5
                   )
                 ]
             ),
             child: Column(
               children: [
                 Align(
                   alignment: Alignment.centerLeft,
                   child: Text("เข้าสู่ระบบ",style: TextStyle(fontSize: 24,fontWeight: FontWeight.bold),),
                 ),
                 SizedBox(height: 20,),
                 TextField(
                   controller: emailController,
                   decoration: InputDecoration(
                     hintText: "อีเมล",
                     prefixIcon: Icon(Icons.email),
                     border: OutlineInputBorder(
                       borderRadius: BorderRadius.circular(20)
                     )
                   ),

                 ),
                 SizedBox(height: 20,),
                 TextField(
                   obscureText: hide,
                   controller: passController,
                   decoration: InputDecoration(

                       hintText: "รหัสผ่าน",
                       suffixIcon: IconButton(
                         onPressed: (){
                           setState(() {
                             hide = !hide;
                           });
                         },
                         icon: hide?Icon(Icons.visibility_off):Icon(Icons.visibility),
                       ),
                       prefixIcon: Icon(Icons.lock),
                       border: OutlineInputBorder(
                           borderRadius: BorderRadius.circular(20)
                       )
                   ),
                 ),
                 SizedBox(height: 20,),
                 ElevatedButton(
                     style: TextButton.styleFrom(
                       backgroundColor: Colors.blueAccent,
                       padding: EdgeInsets.symmetric(horizontal: 25)
                     ),
                     onPressed: () async {
                       login_manager lm = new login_manager();
                       String result = await lm.doLogin(emailController.text,passController.text) as String ;

                       if (result.toString() == "1") {
                         WidgetsFlutterBinding.ensureInitialized();
                         SharedPreferences preferences = await SharedPreferences.getInstance();
                         Staff staff = await lm.getStafbyUsername(emailController.text);

                         var log = Logger();
                         log.e(staff.Staff_name);
                         preferences.setString('username', emailController.text);
                         preferences.setString('Staff',jsonEncode(staff.toMap()));



                         setState(() {
                           isLoading = true;
                         });

                         Future.delayed(Duration(seconds: 3),(){
                           setState(() {
                             isLoading = false;
                             Navigator.of(context).push(
                                 MaterialPageRoute(builder: (context) => HomePage() ));
                             emailController.text="";
                             passController.text = "";
                           });
                         });

                       }
                       else if(result.toString() == "0"){
                         showAlert("กรุณากรอกข้อมูลให้ถูกต้อง");
                       }else {

                       }

                     }, child: isLoading?Row(
                   mainAxisAlignment: MainAxisAlignment.center,
                   children:  [
                     CircularProgressIndicator(color: Colors.white),
                     SizedBox(width: 15),
                     Container(
                         height: 45,
                         child: Column(
                           mainAxisAlignment: MainAxisAlignment.center,
                           children: [
                             const Text("กำลังเข้าสู่ระบบ..."),
                           ],
                         )
                     ),
                   ],
                 ): Container(
                 height: 45,
                     child: Column(
                       mainAxisAlignment: MainAxisAlignment.center,
                       children: [
                         const Text("เข้าสู่ระบบ"),
                       ],
                     )
                     )),
               ],
             ),
           ),
           Positioned(
              top: 50,left: 90,
               child: Column(
                 crossAxisAlignment: CrossAxisAlignment.start,
                 children: const [
                   Padding(
                     padding: EdgeInsets.only(left: 25),
                     child: Image(
                       image: AssetImage('assets/maejologo.png'),
                       width: 200,

                     ),
                   ),

             Text("ระบบจัดการครุภัณฑ์",style: TextStyle(fontSize: 30,color: Colors.white,fontWeight: FontWeight.bold),
             ),
                 Padding(
                   padding: EdgeInsets.only(left: 10),
                   child: Text("คณะวิทยาศาสตร์  มหาวิทยาลัยแม่โจ้",style:
                     TextStyle(color: Colors.white,fontWeight: FontWeight.w300,fontSize: 17),),
                 )
           ],))
         ],
       ),
     ),
   );
  }
}

