import 'dart:convert';
import 'dart:io';

import 'package:buddhist_datetime_dateformat_sns/buddhist_datetime_dateformat_sns.dart';
import 'package:curved_navigation_bar/curved_navigation_bar.dart';
import 'package:dropdown_button2/dropdown_button2.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_switch/flutter_switch.dart';
import 'package:intl/intl.dart';
import 'package:logger/logger.dart';
import 'package:project_durable/model/major_model.dart';
import 'package:project_durable/model/repairdurable_model.dart';
import 'package:project_durable/model/veify_model.dart';
import 'package:project_durable/pages/scan_page.dart';
import 'package:project_durable/pages/viewdurable.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:image_picker/image_picker.dart';
import '../Strings.dart';
import '../manager/Repairdurable_manager.dart';
import '../manager/durable_manager.dart';
import '../manager/inform_manager.dart';
import '../manager/login_manager.dart';
import '../manager/major_manager.dart';
import '../manager/verify_manager.dart';
import '../model/InformRepair_model.dart';
import '../model/durable_model.dart';
import '../model/staff_model.dart';
import '../model/verifyInform_model.dart';
import '../splash.dart';
import 'Edit_ maintenance_Page.dart';
import 'Edit_InformRepair_page.dart';
import 'List_repair_page.dart';
import 'Verify_Repair_Page.dart';
import 'View_verifybymajor_page.dart';
import 'create_ maintenance_Page.dart';
import 'home_page.dart';
import 'login_page.dart';
import 'my_drawer_header.dart';

class List_repairadmin_page extends StatefulWidget {
  @override
  _Listrepairaddmin_PageState createState() => _Listrepairaddmin_PageState();
}

class _Listrepairaddmin_PageState extends State<List_repairadmin_page> {
  var log = Logger();
  bool isLoading = true;
  bool hide = true;
  var emailController = TextEditingController();
  var passController = TextEditingController();
  String? codedurable;
  Durable? d;
  String staff = "";
  String? Majorlogin;
  Staff? s;
  List<Verify>? v;
  List<inform_repair>? listinformrepairnotin;
  List<verifyinform>? listinformrepairin;
  List<verifyinform>? listinformrepairinnotmaintenance;
  List<RepairDurable>? listRepairComplete;
  bool isVisiblenotverify = false;
  bool isVisibleverify = true;

  var noteController = TextEditingController();
  List<Major>? m;
  DateTime now = DateTime.now();
  var formatter = DateFormat.yMMMMd();
  String? selectedValue;
  bool statusswitch = false;

  Future<void> getFirstRoom() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();

    staff = preferences.getString('Staff')!;

    Map<String, dynamic> map = jsonDecode(staff);
    s = Staff.fromJson(map);

    setState(() {
      if (s!.major.ID_Major.toString() == "1") {
        Majorlogin = "IT";
      }
      if (s!.major.ID_Major.toString() == "999") {
        Majorlogin = "SCI";
      }
    });
  }

  void findUser() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    durable_manager dm = durable_manager();
    inform_manager im = inform_manager();
    major_manager mm = major_manager();
    Repairdurable_manager mdm = Repairdurable_manager();
    codedurable = preferences.getString('durable_code');
    staff = preferences.getString('Staff')!;
    Map<String, dynamic> map = jsonDecode(staff);
    s = Staff.fromJson(map);

    setState(() {
      if (s!.major.ID_Major.toString() == "999") {
        selectedValue = 'วิชาเทคโนโลยีสารสนเทศ';
      }
    });

    mm.listALLmajor().then((value) => {
          m = value,
          setState(() {
            isLoading = false;
          }),
          log.e(m.toString()),
        });

    im.listinformnotin(selectedValue.toString()).then((value) => {
          listinformrepairnotin = value,
          setState(() {
            isLoading = false;
          }),
        });

    im.listinformin(selectedValue.toString()).then((value) => {
          listinformrepairin = value,
          setState(() {
            isLoading = false;
          }),
        });

    im.listinforminnotmaintenanadmin(selectedValue.toString()).then((value) => {
      listinformrepairinnotmaintenance = value,
      setState(() {
        isLoading = false;
      }),
    });

    mdm.listRepairComplete(selectedValue.toString()).then((value) => {
      listRepairComplete = value,
      setState(() {
        isLoading = false;
      }),
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
    imageCache!.clear();
    imageCache!.clearLiveImages();
    findUser();
    getFirstRoom();
  }

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


    return DefaultTabController(
      length: 4,
      child: Scaffold(
        appBar: AppBar(
          title: const Text('รายการแจ้งซ่อมครุภัณฑ์'),
          leading: IconButton(
            onPressed: () {
              Navigator.of(context)
                  .push(MaterialPageRoute(builder: (context) => HomePage()));
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
                  SizedBox(height: 10.0),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Column(
                        children: const [
                          Padding(
                            padding: EdgeInsets.only(left: 10.0),
                            child: Align(
                              alignment: Alignment.topLeft,
                              child: Text("สาขา :",
                                  style: TextStyle(fontSize: 20)),
                            ),
                          ),
                        ],
                      ),
                      SizedBox(width: 10.0),
                      Column(
                        children: [
                          DropdownButtonHideUnderline(
                            child: DropdownButton2<String>(
                              isExpanded: true,
                              value: selectedValue,
                              icon: const Icon(Icons.keyboard_arrow_down),
                              items: m == null
                                  ? []
                                  : m!
                                      .map((item) => DropdownMenuItem<String>(
                                            value: item.Major_Name,
                                            child: Text(
                                              item.Major_Name,
                                              style: const TextStyle(
                                                fontSize: 14,
                                              ),
                                            ),
                                          ))
                                      .toList(),
                              onChanged: (String? newValue) {
                                setState(() {
                                  selectedValue = newValue!;
                                });
                                checkdropdown();
                              },
                              buttonHeight: 40,
                              buttonWidth: 250,
                              buttonPadding:
                                  const EdgeInsets.only(left: 14, right: 14),
                              buttonDecoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(14),
                                border: Border.all(
                                  color: Colors.black26,
                                ),
                                color: Colors.white,
                              ),
                              buttonElevation: 2,
                              itemHeight: 40,
                              itemPadding:
                                  const EdgeInsets.only(left: 14, right: 14),
                              dropdownMaxHeight: 200,
                              dropdownWidth: 200,
                              dropdownPadding: null,
                              dropdownDecoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(14),
                                color: Colors.white,
                              ),
                              dropdownElevation: 8,
                              scrollbarRadius: const Radius.circular(40),
                              scrollbarThickness: 6,
                              scrollbarAlwaysShow: true,
                              offset: const Offset(-20, 0),
                            ),
                          ),
                        ],
                      ),
                    ],
                  ),
                  SizedBox(height: 10.0),
                  Column(
                    children: [
                      Container(
                        height: 45,
                        decoration: BoxDecoration(
                            color: Colors.grey.shade300,
                            borderRadius: BorderRadius.circular(10.0)),
                        child: TabBar(
                          indicator: BoxDecoration(
                              color: Colors.blueAccent,
                              borderRadius: BorderRadius.circular(10.0)),
                          labelColor: Colors.white,
                          unselectedLabelColor: Colors.black,
                          tabs: [
                            Tab(text: 'ยังไม่ได้\nตรวจสอบ'),
                            Tab(text: 'ตรวจสอบ\nแล้ว'),
                            Tab(text: 'ส่งซ่อม\nสำเร็จ'),
                            Tab(text: 'ส่งซ่อม\nไม่สำเร็จ'),
                          ],
                        ),
                      ),
                    ],
                  ),
                  Expanded(
                    child: TabBarView(
                      children: [
                        Center(

                            child: Expanded(
                                child: buildDurable(
                                    listinformrepairnotin == null
                                        ? []
                                        : listinformrepairnotin!)),

                        ),
                        Center(
                          child: Expanded(
                              child: buildDurableverifyedd(listinformrepairin == null
                                  ? []
                                  : listinformrepairin!)),
                        ),
                        Center(
                          child: Expanded(
                              child: buildDurablerepaircomplete(listRepairComplete == null
                                  ? []
                                  : listRepairComplete!)),
                        ),
                        Center(
                          child: Expanded(
                              child: buildDurableverifynotmaintenance(listinformrepairinnotmaintenance == null
                                  ? []
                                  : listinformrepairinnotmaintenance!)),
                        ),
                      ],
                    ),
                  ),
                 /* Padding(
                    padding: EdgeInsets.all(10.0),
                    child: Row(
                      children: [
                        Column(
                          children: [
                            Text("สถานะการตรวจสอบ :",
                                style: TextStyle(fontSize: 20))
                          ],
                        ),
                        SizedBox(width: 10.0),
                        Center(
                          child: CupertinoSwitch(
                            value: statusswitch,
                            onChanged: (value) {
                              setState(() {
                                statusswitch = value;
                              });
                              checkdropdown();
                            },
                          ),
                        ),
                      ],
                    ),
                  ),*/
                ],
              ),
            );
          },
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
              Navigator.of(context).push(
                  MaterialPageRoute(builder: (context) => screens[index]));
            }
            if (index.toString() == "1") {
              Navigator.of(context).push(
                  MaterialPageRoute(builder: (context) => screens[index]));
            }
            if (index.toString() == "2") {
              showAlert();
            }
          }),
        ),
        backgroundColor: Colors.grey[100],
      ),
    );
  }

  Widget buildDurable(List<inform_repair> durables) => ListView.builder(
        itemCount:
            listinformrepairnotin == null ? 0 : listinformrepairnotin!.length,
        itemBuilder: (context, index) {
          final durable = durables[index];
          var showDate =
              formatter.formatInBuddhistCalendarThai(durable.dateinform);
          String? img;
          if (durable.durable.Durable_image.toString() != "-") {
            img =   Strings.url+"/file/inform_repair/" + durable.durable.Durable_image.toString();
            log.e(img);
          // img =         "http://www.itsci.mju.ac.th/DurableWebservices/file/durable_image/" +  durable.durable.Durable_image.toString();
          } else {
            img =
                "https://w7.pngwing.com/pngs/29/173/png-transparent-null-pointer-symbol-computer-icons-pi-miscellaneous-angle-trademark.png";
          }
          final splitted = durable.dateinform.toString().split(' ');
          log.e(splitted[0].toString());
          log.e(durable.dateinform.year.toString() +
              " " +
              durable.dateinform.month.toString() +
              " " +
              durable.dateinform.day.toString());
          return Card(
            child: Padding(
              padding: const EdgeInsets.only(top: 5, bottom: 5),
              child: Expanded(
                child: ListTile(
                  leading: CircleAvatar(
                    radius: 24,
                    backgroundImage: NetworkImage(img),
                  ),
                  title: Text("ชื่อครุภัณฑ์ : " + durable.durable.Durable_name),
                  subtitle: SizedBox(
                    child: Column(
                      children: [
                        Row(
                          children: [
                            const Text("รหัสครุภัณฑ์ : ",
                                style: TextStyle(fontWeight: FontWeight.bold)),
                            Text(durable.durable.Durable_code),
                          ],
                        ),
                        Row(
                          children: [
                            const Text("วันที่แจ้ง : ",
                                style: TextStyle(fontWeight: FontWeight.bold)),
                            Text(showDate),
                          ],
                        ),
                        Row(
                          children: [
                            const Text("ผู้แจ้ง : ",
                                style: TextStyle(fontWeight: FontWeight.bold)),
                            Text(durable.staff.Staff_name +
                                "  " +
                                durable.staff.Staff_lastname),
                          ],
                        ),
                        Row(
                          children: [
                            const Text("สถานะ : ",
                                style: TextStyle(fontWeight: FontWeight.bold)),
                            durable.Informtype == "ส่งซ่อม"
                                ? Text(durable.Informtype,
                                    style: TextStyle(
                                        color: Colors.blueAccent,
                                        fontWeight: FontWeight.bold))
                                : durable.Informtype == "ซ่อมเอง"
                                    ? Text(durable.Informtype,
                                        style: TextStyle(
                                            color: Colors.red,
                                            fontWeight: FontWeight.bold))
                                    : Text(durable.Informtype,
                                        style: TextStyle(
                                            color: Colors.orange,
                                            fontWeight: FontWeight.bold)),
                          ],
                        ),
                      ],
                    ),
                  ),
                  isThreeLine: true,
                  trailing: Icon(
                    Icons.add_box,
                    // color: Colors.green.shade700
                  ),
                  onTap: () {
                    convertcode(durable.durable.Durable_code.toString());
                  },
                ),
              ),
            ),
          );
        },
      );

  Widget buildDurableverifyedd(List<verifyinform> durables) => ListView.builder(
    itemCount: listinformrepairin == null ? 0 : listinformrepairin!.length,
    itemBuilder: (context, index) {
      final durable = durables[index];
      String? img;
      if (durable.informrepair.durable.Durable_image.toString() != "-") {
        img =  Strings.url+"/file/durable_image/" + durable.informrepair.durable.Durable_image.toString();
      //  img =        "http://www.itsci.mju.ac.th/DurableWebservices/file/durable_image/" +     durable.informrepair.durable.Durable_image.toString();
      } else {
        img =
        "https://w7.pngwing.com/pngs/29/173/png-transparent-null-pointer-symbol-computer-icons-pi-miscellaneous-angle-trademark.png";
      }
      /* final splitted = durable.dateinform.toString().split(' ');
      final time = splitted[1].split('.');
      log.e(splitted[0].toString() +" "+  time[0].toString());

*/
      var showDate = formatter.formatInBuddhistCalendarThai(durable.verify_date);


      return Card(
        child: Padding(
          padding: const EdgeInsets.only(top: 5, bottom: 5),
          child: Expanded(
            child: ListTile(
              leading: CircleAvatar(
                radius: 24,
                backgroundImage: NetworkImage(img),
              ),
              title:
              Text("ชื่อครุภัณฑ์ : " + durable.informrepair.durable.Durable_name),
              subtitle: SizedBox(
                child: Column(
                  children: [
                    Row(
                      children: [
                        const Text("รหัสครุภัณฑ์ : ",style: TextStyle(fontWeight: FontWeight.bold)),
                        Text(durable.informrepair.durable.Durable_code),
                      ],
                    ),
                    Row(
                      children: [
                        const Text("วันที่ตรวจสอบ : ",style: TextStyle(fontWeight: FontWeight.bold)),
                        Text(showDate),
                      ],
                    ),
                    Row(
                      children: [
                        const Text("รายละเอียด : ",style: TextStyle(fontWeight: FontWeight.bold)),
                        Text(durable.verify_detail.toString()),
                      ],
                    ),
                    Row(
                      children: [
                        const Text("สถานะ : ",style: TextStyle(fontWeight: FontWeight.bold)),
                        durable.verify_status== "ส่งซ่อม"
                            ? Text(durable.verify_status,
                            style: TextStyle(
                                color: Colors.blueAccent,
                                fontWeight: FontWeight.bold))
                            : durable.verify_status == "ซ่อมเอง"
                            ? Text(durable.verify_status,
                            style: TextStyle(
                                color: Colors.red,
                                fontWeight: FontWeight.bold))
                            : Text(durable.verify_status,
                            style: TextStyle(
                                color: Colors.orange,
                                fontWeight: FontWeight.bold)),
                      ],
                    ),
                  ],
                ),
              ),
              isThreeLine: true,
              trailing: Icon(
                Icons.build,
                // color: Colors.green.shade700
              ),
              onTap: ()  {
                 convertcode2(durable.informrepair.durable.Durable_code.toString(),durable.verify_id.toString());
              },
            ),
          ),
        ),
      );
    },
  );
  Widget buildDurableverifynotmaintenance(List<verifyinform> durables) => ListView.builder(
    itemCount: listinformrepairinnotmaintenance == null ? 0 : listinformrepairinnotmaintenance!.length,
    itemBuilder: (context, index) {
      final durable = durables[index];
      String? img;
      if (durable.informrepair.durable.Durable_image.toString() != "-") {
        img =  Strings.url+"/file/durable_image/" + durable.informrepair.durable.Durable_image.toString();
      //  img =       "http://www.itsci.mju.ac.th/DurableWebservices/file/durable_image/" + durable.informrepair.durable.Durable_image.toString();
      } else {
        img =
        "https://w7.pngwing.com/pngs/29/173/png-transparent-null-pointer-symbol-computer-icons-pi-miscellaneous-angle-trademark.png";
      }
      /* final splitted = durable.dateinform.toString().split(' ');
      final time = splitted[1].split('.');
      log.e(splitted[0].toString() +" "+  time[0].toString());

*/
      var showDate = formatter.formatInBuddhistCalendarThai(durable.verify_date);


      return Card(
        child: Padding(
          padding: const EdgeInsets.only(top: 5, bottom: 5),
          child: Expanded(
            child: ListTile(
              leading: CircleAvatar(
                radius: 24,
                backgroundImage: NetworkImage(img),
              ),
              title:
              Text("ชื่อครุภัณฑ์ : " + durable.informrepair.durable.Durable_name),
              subtitle: SizedBox(
                child: Column(
                  children: [
                    Row(
                      children: [
                        const Text("รหัสครุภัณฑ์ : ",style: TextStyle(fontWeight: FontWeight.bold)),
                        Text(durable.informrepair.durable.Durable_code),
                      ],
                    ),
                    Row(
                      children: [
                        const Text("วันที่ตรวจสอบ : ",style: TextStyle(fontWeight: FontWeight.bold)),
                        Text(showDate),
                      ],
                    ),
                    Row(
                      children: [
                        const Text("รายละเอียด : ",style: TextStyle(fontWeight: FontWeight.bold)),
                        Text(durable.verify_detail.toString()),
                      ],
                    ),
                    Row(
                      children: [
                        const Text("สถานะ : ",style: TextStyle(fontWeight: FontWeight.bold)),
                        durable.verify_status== "ส่งซ่อม"
                            ? Text(durable.verify_status,
                            style: TextStyle(
                                color: Colors.blueAccent,
                                fontWeight: FontWeight.bold))
                            : durable.verify_status == "ซ่อมเอง"
                            ? Text(durable.verify_status,
                            style: TextStyle(
                                color: Colors.red,
                                fontWeight: FontWeight.bold))
                            : Text(durable.verify_status,
                            style: TextStyle(
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
                //  convertcode(durable.informrepair.durable.Durable_code.toString());
              },
            ),
          ),
        ),
      );
    },
  );
  Widget buildDurablerepaircomplete(List<RepairDurable> durables) => ListView.builder(
    itemCount: listRepairComplete == null ? 0 : listRepairComplete!.length,
    itemBuilder: (context, index) {
      final durable = durables[index];
      String? img;
      if (durable.durable.Durable_image.toString() != "-") {
        img =  Strings.url+"/file/durable_image/" + durable.durable.Durable_image.toString();
       // img =       "http://www.itsci.mju.ac.th/DurableWebservices/file/durable_image/" +durable.durable.Durable_image.toString();
      } else {
        img =
        "https://w7.pngwing.com/pngs/29/173/png-transparent-null-pointer-symbol-computer-icons-pi-miscellaneous-angle-trademark.png";
      }
      /* final splitted = durable.dateinform.toString().split(' ');
      final time = splitted[1].split('.');
      log.e(splitted[0].toString() +" "+  time[0].toString());

*/
      var showDate = formatter.formatInBuddhistCalendarThai(durable.verifyinform_.verify_date);


      return Card(
        child: Padding(
          padding: const EdgeInsets.only(top: 5, bottom: 5),
          child: Expanded(
            child: ListTile(
              leading: CircleAvatar(
                radius: 24,
                backgroundImage: NetworkImage(img),
              ),
              title:
              Text("ชื่อครุภัณฑ์ : " + durable.durable.Durable_name),
              subtitle: SizedBox(
                child: Column(
                  children: [
                    Row(
                      children: [
                        const Text("รหัสครุภัณฑ์ : ",style: TextStyle(fontWeight: FontWeight.bold)),
                        Text(durable.durable.Durable_code),
                      ],
                    ),
                    Row(
                      children: [
                        const Text("วันที่ซ่อม : ",style: TextStyle(fontWeight: FontWeight.bold)),
                        Text(durable.Date_of_repair.toString()),
                      ],
                    ),
                    Row(
                      children: [
                        const Text("รายละเอียดการซ่อม : ",style: TextStyle(fontWeight: FontWeight.bold)),
                        Text(durable.repair_detail),
                      ],
                    ),
                    Row(
                      children: [
                        const Text("จำนวนเงิน : ",style: TextStyle(fontWeight: FontWeight.bold)),
                        Text(durable.repair_charges),
                      ],
                    ),
                    Row(
                      children: [
                        const Text("สถานะ : ",style: TextStyle(fontWeight: FontWeight.bold)),
                        durable.Repair_status == "ดี"
                            ? Text(durable.Repair_status,
                            style: TextStyle(
                                color: Colors.blueAccent,
                                fontWeight: FontWeight.bold))
                            : durable.Repair_status == "ชำรุด"
                            ? Text(durable.Repair_status,
                            style: TextStyle(
                                color: Colors.red,
                                fontWeight: FontWeight.bold))
                            : Text(durable.Repair_status,
                            style: TextStyle(
                                color: Colors.orange,
                                fontWeight: FontWeight.bold)),
                      ],
                    ),
                  ],
                ),
              ),
              isThreeLine: true,
              trailing: Icon(
                Icons.create,
                // color: Colors.green.shade700
              ),
              onTap: () {
                convertcodetoedit(durable.durable.Durable_code.toString(),durable.verifyinform_.verify_id.toString());
              },
            ),
          ),
        ),
      );
    },
  );
  Future<void> convertcode(String code) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    List<String> listcode = code.split(".PNG");
    String durable_code = listcode[0].replaceAll(':', '/');
    preferences.setString('durable_code', durable_code);
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => Verify_Repair_Page()));
  }

  Future<void> convertcode2(String code,String verifyid) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    List<String> listcode = code.split(".PNG");
    String durable_code = listcode[0].replaceAll(':', '/');
    preferences.setString('durable_code', durable_code);
    preferences.setString('verifyid', verifyid);
    Navigator.of(context).push(
        MaterialPageRoute(builder: (context) => Create_Maintenance_Page()));
  }
  Future<void> convertcodetoedit(String code,String verifyid) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    List<String> listcode = code.split(".PNG");
    String durable_code = listcode[0].replaceAll(':', '/');
    preferences.setString('durable_code', durable_code);
    preferences.setString('verifyid', verifyid);
    Navigator.of(context).push(
        MaterialPageRoute(builder: (context) => Edit_Maintenance_Page()));
  }

  void checkdropdown() {
    inform_manager im = inform_manager();

      setState(() {
        im.listinformnotin(selectedValue.toString()).then((value) => {
              listinformrepairnotin = value,
              setState(() {
                isLoading = false;
              }),
            });
      });

      setState(() {
        im.listinformin(selectedValue.toString()).then((value) => {
              listinformrepairin = value,
              setState(() {
                isLoading = false;
              }),
            });
      });

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
