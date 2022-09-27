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

import '../manager/Repairdurable_manager.dart';
import '../manager/durable_manager.dart';
import 'package:buddhist_datetime_dateformat_sns/buddhist_datetime_dateformat_sns.dart';
import '../manager/verify_manager.dart';
import '../model/InformRepair_model.dart';
import '../model/durable_model.dart';
import '../model/repairdurable_model.dart';
import '../model/staff_model.dart';
import '../splash.dart';
import 'Edit_InformRepair_page.dart';

import 'List_Repair_History_page.dart';
import 'List_repairADMIN_page.dart';
import 'List_repair_page.dart';
import 'View_verifybymajor_page.dart';
import 'home_page.dart';
import 'login_page.dart';
import 'my_drawer_header.dart';

class ViewRepairHistory_Page extends StatefulWidget {
  @override
  ViewRepairHistory_PageState createState() => ViewRepairHistory_PageState();
}

class ViewRepairHistory_PageState extends State<ViewRepairHistory_Page> {
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
  String? repairid;
  RepairDurable? getRepair;
  var noteController = TextEditingController();
  DateTime now = DateTime.now();
  var formatter = DateFormat.yMMMMd();
  var time =  DateFormat.jm();


  void findUser() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    durable_manager dm = durable_manager();
    verify_manager vm = verify_manager();
    Repairdurable_manager rdm = Repairdurable_manager();
    codedurable = preferences.getString('durable_code');
    staff = preferences.getString('Staff')!;
    repairid = preferences.getString('repairid')!;

    Map<String, dynamic> map = jsonDecode(staff);
    s = Staff.fromJson(map);


    rdm.getdurablerepairHistory(codedurable!,repairid!).then((value) => {
      getRepair = value,
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
        title: const Text('ประวัติการส่งซ่อมครุภัณฑ์'),
        leading: IconButton(
          onPressed: () {
            Navigator.of(context)
                .push(MaterialPageRoute(builder: (context) => ListRepairHistory_Page()));
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
      body: SingleChildScrollView(
        child: Builder(
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
                      child: Text("รหัสครุภัณฑ์ : " + d!.Durable_code,style: TextStyle(fontSize: 18)),
                      alignment: Alignment.centerLeft,
                    ),
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  Padding(
                    padding: const EdgeInsets.only(left: 10),
                    child: Align(
                      child: Text("ชื่อครุภัณฑ์ : " + d!.Durable_name,style: TextStyle(fontSize: 18)),
                      alignment: Alignment.centerLeft,
                    ),
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  _heading("ข้อมูลการแจ้งซ่อม"),
                  SizedBox(
                    height: 5,
                  ),
                  _detailsCard(),
                  SizedBox(
                    height: 10,
                  ),
                  _heading("ข้อมูลการซ่อม"),
                  SizedBox(
                    height: 5,
                  ),
                  _detailsCard2(),
                  SizedBox(
                    height: 10,
                  ),
                ],
              ),
            );
          },
        ),
      ),
      bottomNavigationBar: CurvedNavigationBar(
        key: navigationKey,
        color: Colors.indigo,
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
  Widget _heading(String heading) {
    return Container(
      width: MediaQuery.of(context).size.width * 0.80,
      child: Text(
        heading,
        style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
      ),
    );
  }
  Widget _detailsCard() {
    var informdate =    formatter.formatInBuddhistCalendarThai(getRepair!.verifyinform_.informrepair.dateinform) ;
    var informtime = DateFormat('kk:mm').format(getRepair!.verifyinform_.informrepair.dateinform);
    return Padding(
      padding: const EdgeInsets.all(10.0),
      child: Card(
        elevation: 2,
        child: Column(
          children: [
            ListTile(
              //leading: const Icon(Icons.event_seat,color: Colors.indigo),
              title: Text("อาการเสีย : " + getRepair!.verifyinform_.informrepair.details.toString() ),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              //leading: const Icon(Icons.key,color: Colors.indigo),
              title: Text("วันที่แจ้งซ่อม : " + informdate+" "+informtime +" น."),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              // leading: const Icon(Icons.branding_watermark_sharp,color: Colors.indigo),
              title: Text("ผู้แจ้ง : " + getRepair!.verifyinform_.informrepair.staff.Staff_status.toString()),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
          ],
        ),
      ),
    );
  }

  Widget _detailsCard2() {
    return Padding(
      padding: const EdgeInsets.all(10.0),
      child: Card(
        elevation: 2,
        child: Column(
          children: [
            ListTile(
              //leading: const Icon(Icons.event_seat,color: Colors.indigo),
              title: Text("รายละเอียด : " + getRepair!.repair_detail.toString() ),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              //leading: const Icon(Icons.key,color: Colors.indigo),
              title: Text("จำนวนเงิน : " + getRepair!.repair_charges),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              // leading: const Icon(Icons.branding_watermark_sharp,color: Colors.indigo),
              title: Text("บริษัทที่เข้ารับการซ๋อม : " + getRepair!.company_.companyname.toString()),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              // leading: const Icon(Icons.branding_watermark_sharp,color: Colors.indigo),
              title: Text("วันที่ซ่อมเสร็จ : " + getRepair!.Date_of_repair.toString()),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
          ],
        ),
      ),
    );
  }

  Future<void> convertcode(String code,String repairid) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    List<String> listcode = code.split(".PNG");
    String durable_code = listcode[0].replaceAll(':', '/');
    preferences.setString('durable_code', durable_code);
    preferences.setString('repairid', repairid);
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => Edit_Inform_Page()));
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
                child: Text("รายการแจ้งซ่อมครุภัณฑ์",style: TextStyle(color: Colors.black,fontSize: 16)),
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
                child: Text("ข้อมูลครุภัณฑ์",style: TextStyle(color: Colors.black,fontSize: 16)),
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
