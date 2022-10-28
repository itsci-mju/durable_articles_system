import 'dart:convert';
import 'dart:io';

import 'package:curved_navigation_bar/curved_navigation_bar.dart';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'package:intl/intl.dart';
import 'package:logger/logger.dart';

import 'package:project_durable/model/veify_model.dart';
import 'package:project_durable/model/verifyInform_model.dart';
import 'package:project_durable/pages/scan_page.dart';
import 'package:project_durable/pages/viewdurable.dart';

import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../manager/durable_manager.dart';
import 'package:buddhist_datetime_dateformat_sns/buddhist_datetime_dateformat_sns.dart';
import '../manager/verify_manager.dart';
import '../model/InformRepair_model.dart';
import '../model/durable_model.dart';
import '../model/repairdurable_model.dart';
import '../model/staff_model.dart';
import '../splash.dart';
import 'Edit_InformRepair_page.dart';

import 'List_repairADMIN_page.dart';
import 'List_repair_page.dart';
import 'View_Repair_History_page.dart';
import 'View_verifybymajor_page.dart';
import 'home_page.dart';
import 'login_page.dart';
import 'my_drawer_header.dart';

class ListRepairHistory_Page extends StatefulWidget {
  @override
  ListRepairHistory_PageState createState() => ListRepairHistory_PageState();
}

class ListRepairHistory_PageState extends State<ListRepairHistory_Page> {
  var log = Logger();
  bool isLoading = true;
  bool hide = true;
  var emailController = TextEditingController();
  var passController = TextEditingController();
  String? codedurable;
  Durable? d;
  String staff = "";
  Staff? s;
  String? Majorlogin;
  List<Verify>? v;

  List<RepairDurable>? listRepairComplete;
  var noteController = TextEditingController();

  DateTime now = DateTime.now();
  var formatter = DateFormat.yMMMMd();

  void findUser() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    durable_manager dm = durable_manager();
    verify_manager vm = verify_manager();
    codedurable = preferences.getString('durable_code');
    staff = preferences.getString('Staff')!;
    Map<String, dynamic> map = jsonDecode(staff);
    s = Staff.fromJson(map);

    vm.listRepairHistory(codedurable!).then((value) => {
          listRepairComplete = value,
          setState(() {
            isLoading = false;
          }),
        });

    dm.getdurablebyCode(codedurable.toString()).then((value) => {
          d = value,
          setState(() {
            isLoading = false;
          }),
        });

    setState(() {
      if (s!.major.ID_Major.toString() == "1") {
        Majorlogin = "IT";
      }
      if (s!.major.ID_Major.toString() == "999") {
        Majorlogin = "SCI";
      }
    });
  }

  showAlert() async {
    Alert(
        context: context,
        type: AlertType.info,
        title: "แจ้งเตือน",
        desc: "ท่านต้องการออกจากระบบหรือไม่ ?",
        buttons: [
          DialogButton(
            child: const Text("ยืนยัน",
                style: TextStyle(color: Colors.white, fontSize: 20)),
            onPressed: () {
              logout();
            },
            width: 130,
          ),
          DialogButton(
            child: const Text("ยกเลิก",
                style: TextStyle(color: Colors.white, fontSize: 20)),
            onPressed: () {
              setState(() {
                index = 0;
              });
              Navigator.pop(context);
            },
            width: 130,
          ),
        ]).show();
  }

  final navigationKey = GlobalKey<CurvedNavigationBarState>();
  int index = 0;
  final screens = [
    HomePage(),
    QRViewExample(),
    Login_Page(),
  ];

  @override
  void initState() {
    super.initState();
    findUser();
  }

  final List<String> statusdurable = [
    'ดี',
    'ชำรุด',
    'แทงจำหน่าย',
  ];
  String? selectedValuestatus;
  String? selectedValueyears;

  @override
  Widget build(BuildContext context) {
    if (isLoading) {
      return const Center(
        child: CircularProgressIndicator(),
      );
    }
    Color colors = Colors.black54;
    final items = <Widget>[
      const Icon(Icons.home, size: 30, color: Colors.white),
      const Icon(Icons.camera_alt, size: 30, color: Colors.white),
      const Icon(Icons.logout, size: 30, color: Colors.white),
    ];

    String? img;
    String durableimg = d == null ? "" : d!.Durable_image.toString();
    if (durableimg != "-") {
      img =
          "http://www.itsci.mju.ac.th/DurableWebservices/file/durable_image/" +
              durableimg;
    } else {
      img =
          "https://w7.pngwing.com/pngs/29/173/png-transparent-null-pointer-symbol-computer-icons-pi-miscellaneous-angle-trademark.png";
    }
    return Scaffold(
      appBar: AppBar(
        title: const Text('ประวัติการซ่อมครุภัณฑ์',
            style: TextStyle(
                fontSize: 25, fontWeight: FontWeight.bold)),
        leading: IconButton(
          onPressed: () {
            Navigator.pop(context);
          },
          icon: const Icon(Icons.keyboard_backspace),
        ),
      ),
      endDrawer: Drawer(
        child: SingleChildScrollView(
          child: Column(
            children: [
              MyHeaderDrawer(),
              MyDrawerList(),
            ],
          ),
        ),
      ),
      resizeToAvoidBottomInset: false,
      body: Builder(
        builder: (BuildContext context) {
          return Center(
            child: Column(
              children: [
                SizedBox(
                  height: 10,
                ),
                Padding(
                  padding: const EdgeInsets.only(left: 10),
                  child: Align(
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text("รหัสครุภัณฑ์ : ",style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
                        Flexible(child: Text(d!.Durable_code,style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold,color: Colors.indigoAccent))),
                      ],
                    ),
                    alignment: Alignment.centerLeft,
                  ),
                ),
                SizedBox(
                  height: 10,
                ),
                Padding(
                  padding: const EdgeInsets.only(left: 10),
                  child: Align(
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text("ชื่อครุภัณฑ์ : ", style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
                        Flexible(child: Text(d!.Durable_name, style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold,color: Colors.indigoAccent))),
                      ],
                    ),
                    alignment: Alignment.centerLeft,
                  ),
                ),
                SizedBox(
                  height: 10,
                ),
                Expanded(
                    child: listRepairComplete!.length == 0
                        ? Center(child: Text("ยังไม่มีประวัติการซ่อม",style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)))
                        : buildDurablerepaircomplete(listRepairComplete!)),
              ],
            ),
          );
        },
      ),
      bottomNavigationBar: CurvedNavigationBar(
        key: navigationKey,
        color: Colors.orange.shade900,
        backgroundColor: Colors.transparent,
        height: 50,
        animationCurve: Curves.easeInOut,
        animationDuration: const Duration(milliseconds: 300),
        index: index,
        items: items,
        onTap: (index) => setState(() {
          this.index = index;
          if (index.toString() == "0") {
            Navigator.of(context)
                .push(MaterialPageRoute(builder: (context) => screens[index]));
          }
          if (index.toString() == "1") {
            Navigator.of(context)
                .push(MaterialPageRoute(builder: (context) => screens[index]));
          }
          if (index.toString() == "2") {
            showAlert();
          }
        }),
      ),
      backgroundColor: Colors.grey[100],
    );
  }

  Widget buildDurablerepaircomplete(List<RepairDurable> durables) =>
      ListView.builder(
        itemCount: listRepairComplete == null ? 0 : listRepairComplete!.length,
        itemBuilder: (context, index) {
          final durable = durables[index];
          int i = index+1;
          String? img;
          if (durable.durable.Durable_image.toString() != "-") {
            img =
                "http://www.itsci.mju.ac.th/DurableWebservices/file/durable_image/" +
                    durable.durable.Durable_image.toString();
          } else {
            img =
                "https://w7.pngwing.com/pngs/29/173/png-transparent-null-pointer-symbol-computer-icons-pi-miscellaneous-angle-trademark.png";
          }
          /* final splitted = durable.dateinform.toString().split(' ');
      final time = splitted[1].split('.');
      log.e(splitted[0].toString() +" "+  time[0].toString());

*/
          var timerepair1 = durable.repair_date.split(' ') ;
          var timerepair = timerepair1[1].substring(0,5);
          var showDate = formatter
              .formatInBuddhistCalendarThai(durable.verifyinform_.verify_date);

          return Card(
            child: Padding(
              padding: const EdgeInsets.only(top: 5, bottom: 5),
              child: Expanded(
                child: ListTile(
                  subtitle: SizedBox(
                    child: Column(
                      children: [
                        Row(
                          children: [
                            const Text("รายการซ่อมครั้งที่ : ",
                                style: TextStyle(fontSize:25,fontWeight: FontWeight.bold)),
                            Text(i.toString(), style: TextStyle(fontSize:25,color: Colors.indigo,fontWeight: FontWeight.bold)),
                          ],
                        ),
                        Row(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text("วันที่ซ่อม : ",
                                style: TextStyle(fontSize:25,fontWeight: FontWeight.bold)),
                            Flexible(child: Text(durable.Date_of_repair.toString()+" "+timerepair+" น.", style: TextStyle(fontSize:25,color: Colors.indigo,fontWeight: FontWeight.bold))),
                          ],
                        ),
                        Row(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text("รายละเอียดการซ่อม : ",
                                style: TextStyle(fontSize:25,fontWeight: FontWeight.bold)),
                            Flexible(child: Text(durable.repair_detail, style: TextStyle(fontSize:25,color: Colors.indigo,fontWeight: FontWeight.bold))),
                          ],
                        ),
                        Row(
                          children: [
                            const Text("จำนวนเงิน : ",
                                style: TextStyle(fontSize:25,fontWeight: FontWeight.bold)),
                            Text(durable.repair_charges, style: TextStyle(fontSize:25,color: Colors.indigo,fontWeight: FontWeight.bold)),
                          ],
                        ),
                        Row(
                          children: [
                            const Text("สถานะ : ",
                                style: TextStyle(fontSize:25,fontWeight: FontWeight.bold)),
                            durable.Repair_status == "ดี"
                                ? Text(durable.Repair_status,
                                    style: TextStyle(
                                        fontSize:25,
                                        color: Colors.blueAccent,
                                        fontWeight: FontWeight.bold))
                                : durable.Repair_status == "ชำรุด"
                                    ? Text(durable.Repair_status,
                                        style: TextStyle(
                                            fontSize:25,
                                            color: Colors.red,
                                            fontWeight: FontWeight.bold))
                                    : Text(durable.Repair_status,
                                        style: TextStyle(
                                            fontSize:25,
                                            color: Colors.orange,
                                            fontWeight: FontWeight.bold)),
                          ],
                        ),
                      ],
                    ),
                  ),
                  isThreeLine: true,
                  /*trailing: Icon(
                Icons.create,
                // color: Colors.green.shade700
              ),*/
                  onTap: () {
                     convertcode(durable.durable.Durable_code.toString(),durable.repair_id.toString());
                  },
                ),
              ),
            ),
          );
        },
      );

  Future<void> convertcode(String code,String repairid) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    List<String> listcode = code.split(".PNG");
    String durable_code = listcode[0].replaceAll(':', '/');
    preferences.setString('durable_code', durable_code);
    preferences.setString('repairid', repairid);
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => ViewRepairHistory_Page()));
  }
  Widget MyDrawerList() {
    return Positioned(
      bottom: 0.0,
      child: Container(
        padding: const EdgeInsets.only(top: 15),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            menuItem(),
            Majorlogin =="SCI"? menuItem2():Container(),
          ],
        ),
      ),
    );
  }
  Widget menuItem(){
    return Material(
      child: InkWell(
        onTap: () {
          if (Majorlogin =="IT") {
            Navigator.of(context).push(
                MaterialPageRoute(builder: (context) => List_repair_page() ));
          }
          if (Majorlogin == "SCI") {
            Navigator.of(context).push(
                MaterialPageRoute(builder: (context) => List_repairadmin_page() ));
          }

        },
        child: Padding(
          padding: EdgeInsets.all(10),
          child: Row(
            children: const [
              Expanded(
                  child: Icon(
                    Icons.build,
                    size: 20,
                    color: Colors.black,
                  )
              ),
              Expanded(
                flex: 3,
                child: Text("รายการแจ้งซ่อมครุภัณฑ์",style: TextStyle(color: Colors.black,fontSize: 25)),
              ),
            ],
          ),
        ),
      ),
    );
  }
  Widget menuItem2(){
    return Material(
      child: InkWell(
        onTap: () {
          Navigator.of(context).push(
              MaterialPageRoute(builder: (context) => View_verifybymajor_page() ));
        },
        child: Padding(
          padding: EdgeInsets.all(10),
          child: Row(
            children: const [
              Expanded(
                  child: Icon(
                    Icons.inbox,
                    size: 20,
                    color: Colors.black,
                  )
              ),
              Expanded(
                flex: 3,
                child: Text("ข้อมูลครุภัณฑ์",style: TextStyle(color: Colors.black,fontSize: 25)),
              ),
            ],
          ),
        ),
      ),
    );
  }
  void logout() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => Splash()));
    preferences.clear();
    var log = Logger();
    log.e("คุณออกจากระบบแล้ว");
  }
}
