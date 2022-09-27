import 'dart:convert';
import 'dart:io';

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
import 'package:project_durable/model/veify_model.dart';
import 'package:project_durable/model/verifyInform_model.dart';
import 'package:project_durable/pages/scan_page.dart';
import 'package:project_durable/pages/viewdurable.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:image_picker/image_picker.dart';
import '../manager/durable_manager.dart';
import '../manager/inform_manager.dart';
import '../manager/login_manager.dart';
import '../manager/major_manager.dart';
import '../manager/verify_manager.dart';
import '../manager/verifyinform_manager.dart';
import '../model/InformRepair_model.dart';
import '../model/durable_model.dart';
import '../model/staff_model.dart';
import '../model/verifydurable_model.dart';
import '../splash.dart';
import 'Edit_InformRepair_page.dart';
import 'List_repairADMIN_page.dart';
import 'List_repair_page.dart';
import 'Verify_Repair_Page.dart';
import 'create_ maintenance_Page.dart';
import 'home_page.dart';
import 'login_page.dart';
import 'my_drawer_header.dart';

class View_verifybymajor_page extends StatefulWidget {
  @override
  _viewverifybymajor_PageState createState() => _viewverifybymajor_PageState();
}

class _viewverifybymajor_PageState extends State<View_verifybymajor_page> {
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
  List<inform_repair>? listinformrepairin;
  bool isVisiblenotverify = false;
  bool isVisibleverify = true;
  String? selectedValueyears;
  String? numberalldurable;
  String? numberdurabled;
  String? numbernotdurabled;
  String? verifystatus1;
  String? verifystatus2;
  String? verifystatus3;
  String? verifystatus4;
  List<VerifyDurable>? listverifystatus1;
  List<VerifyDurable>? listverifystatus2;
  List<VerifyDurable>? listverifystatus3;
  List<verifyinform>? listverifystatus4;
  VerifyDurable? verifydurablebycode;
  var noteController = TextEditingController();
  List<Major>? m;
  DateTime now = DateTime.now();
  String? selectedValue;
  bool statusswitch = false;
  DateTime datenow = DateTime.now();

  Future<void> getFirstRoom() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();

    staff = preferences.getString('Staff')!;

    Map<String, dynamic> map = jsonDecode(staff);
    s = Staff.fromJson(map);

    setState(() {
      if (s!.major.ID_Major.toString() == "1") {
        selectedValueyears = (datenow.year+543).toString();
        Majorlogin = "IT";
      }
      if (s!.major.ID_Major.toString() == "999") {
        selectedValueyears = (datenow.year+543).toString();
        Majorlogin = "SCI";
      }
    });
  }

  void findUser() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    durable_manager dm = durable_manager();
    inform_manager im = inform_manager();
    major_manager mm = major_manager();
    verify_manager vm = verify_manager();
    verifyinform_manager vim = verifyinform_manager();
    codedurable = preferences.getString('durable_code');
    staff = preferences.getString('Staff')!;
    Map<String, dynamic> map = jsonDecode(staff);
    s = Staff.fromJson(map);

    setState(() {
      if (s!.major.ID_Major.toString() == "999") {
        selectedValue = 'วิชาเทคโนโลยีสารสนเทศ';
        selectedValueyears = (datenow.year + 543).toString();
      }
    });

    mm.listALLmajor().then((value) =>
    {
      m = value,
      setState(() {
        isLoading = false;
      }),
      log.e(m.toString()),
    });

    vm.listyears().then((value) =>
    {
      v = value,
      setState(() {
        isLoading = false;
      }),
    });

    vm.countdurable(selectedValue.toString()).then((value) =>
    {
      numberalldurable = value,
      setState(() {
        isLoading = false;
      }),
      log.e(numberalldurable),
    });

    vm.countdurabled(selectedValue.toString(), selectedValueyears.toString())
        .then((value) =>
    {
      numberdurabled = value,
      setState(() {
        isLoading = false;
      }),
      log.e(numberalldurable),
    });

    vm.countnotdurabled(selectedValue.toString(), selectedValueyears.toString())
        .then((value) =>
    {
      numbernotdurabled = value,
      setState(() {
        isLoading = false;
      }),
      log.e(numberalldurable),
    });

    vm.countverifystatus(
        selectedValue.toString(), selectedValueyears.toString()).then((value) =>
    {
      verifystatus1 = value,
      setState(() {
        isLoading = false;
      }),
    });
    vm.countverifystatus2(
        selectedValue.toString(), selectedValueyears.toString()).then((value) =>
    {
      verifystatus2 = value,
      setState(() {
        isLoading = false;
      }),
    });
    vm.countverifystatus3(
        selectedValue.toString(), selectedValueyears.toString()).then((value) =>
    {
      verifystatus3 = value,
      setState(() {
        isLoading = false;
      }),
    });
    vm.countverifystatus4(
        selectedValue.toString(), selectedValueyears.toString()).then((value) =>
    {
      verifystatus4 = value,
      setState(() {
        isLoading = false;
      }),
    });

    vm.listverifystatus(selectedValue.toString(), selectedValueyears.toString())
        .then((value) =>
    {
      listverifystatus1 = value,
      setState(() {
        isLoading = false;
      }),
      log.e(listverifystatus1),
    });

    vm.listverifystatus2(selectedValue.toString(), selectedValueyears.toString())
        .then((value) =>
    {
      listverifystatus2 = value,
      setState(() {
        isLoading = false;
      }),
      log.e(listverifystatus2),
    });
    vm.listverifystatus3(selectedValue.toString(), selectedValueyears.toString())
        .then((value) =>
    {
      listverifystatus3 = value,
      setState(() {
        isLoading = false;
      }),
      log.e(listverifystatus3),
    });
    vm.listverifystatus4(selectedValue.toString(), selectedValueyears.toString())
        .then((value) =>
    {
      listverifystatus4 = value,
      setState(() {
        isLoading = false;
      }),
      log.e(listverifystatus4),
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
    getFirstRoom();
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
            Majorlogin =="SCI" ? menuItem2():Container(),
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
        title: const Text('ครุภัณฑ์ทั้งหมด'),
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
          return SingleChildScrollView(
            child: Column(
              children: [
                SizedBox(height: 10.0),
                Row(
                  children: [
                    Column(
                      children: const [
                        Padding(
                          padding: EdgeInsets.only(left: 10.0),
                          child: Align(
                            alignment: Alignment.topLeft,
                            child: Text(
                                "สาขา :", style: TextStyle(fontSize: 20)),
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
                                .map((item) =>
                                DropdownMenuItem<String>(
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

                Padding(
                  padding: EdgeInsets.all(10.0),
                  child: Row(
                    children: [
                      Column(
                        children: const [
                          Text("ปีงบประมาณ :",
                              style: TextStyle(fontSize: 16)),
                        ],
                      ),
                      const SizedBox(width: 5),
                      Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          DropdownButtonHideUnderline(
                            child: DropdownButton2<String>(
                              isExpanded: true,
                              hint: Row(
                                children: [
                                  Expanded(
                                    child: Text(
                                      (datenow.year + 543).toString(),
                                      style: TextStyle(
                                        fontSize: 14,
                                        color: Colors.black,
                                      ),
                                      overflow: TextOverflow.ellipsis,
                                    ),
                                  ),
                                ],
                              ),
                              value: selectedValueyears,
                              icon: const Icon(Icons.keyboard_arrow_down),
                              items: v == null
                                  ? []
                                  : v!
                                  .map((item) =>
                                  DropdownMenuItem<String>(
                                    value: item.Years,
                                    child: Text(
                                      item.Years,
                                      style: const TextStyle(
                                        fontSize: 14,
                                      ),
                                    ),
                                  ))
                                  .toList(),
                              onChanged: (String? newValue) {
                                setState(() {
                                  selectedValueyears = newValue!;
                                });
                                checkdropdown();
                              },
                              buttonHeight: 40,
                              buttonWidth: 160,
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
                ),
                const SizedBox(
                  height: 10,
                ),
                _heading('ข้อมูลครุภัณฑ์'),
                _detailsCard(),
                const SizedBox(
                  height: 10,
                ),
                _heading('สถานะ'),
                _detailsStatusCard(),
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
        onTap: (index) =>
            setState(() {
              this.index = index;
              if (index.toString() == "0") {
                Navigator.of(context)
                    .push(
                    MaterialPageRoute(builder: (context) => screens[index]));
              }
              if (index.toString() == "1") {
                Navigator.of(context)
                    .push(
                    MaterialPageRoute(builder: (context) => screens[index]));
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
      width: MediaQuery
          .of(context)
          .size
          .width * 0.80,
      child: Text(
        heading,
        style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold
        ),
      ),
    );
  }

  Widget _detailsCard() {
    return Padding(
      padding: const EdgeInsets.all(10.0),
      child: Card(
        elevation: 4,
        child: Column(
          children: [
            ListTile(
              //leading: const Icon(Icons.event_seat,color: Colors.indigo),
              title: Text("ครุภัณฑ์ทั้งหมด : " + numberalldurable!),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              //leading: const Icon(Icons.favorite,color: Colors.indigo),
              title: Text("ตรวจแล้ว : " + numberdurabled!),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              //leading: const Icon(Icons.favorite_border,color: Colors.indigo),
              title: Text("ยังไม่ได้ตรวจ : " + numbernotdurabled!),
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

  Widget _detailsStatusCard() {
    return Padding(
      padding: const EdgeInsets.all(10.0),
      child: Card(
        elevation: 4,
        child: Column(
          children: [
            ListTile(
              //leading: const Icon(Icons.door_back_door,color: Colors.indigo),
              title: verifystatus1 == null ? Text("ดี : ") : Text(
                  "ดี : " + verifystatus1!),
               onTap: () =>alertDialog1(context),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              //leading: const Icon(Icons.person,color: Colors.indigo),
              title: verifystatus2 == null ? Text("ชำรุด : ") : Text(
                  "ชำรุด : " + verifystatus2!),
              onTap: () =>alertDialog2(context),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              //leading: const Icon(Icons.signal_wifi_statusbar_4_bar,color: Colors.indigo),
              title: verifystatus4 == null ? Text("แทงจำหน่าย : ") : Text(
                  "รอซ่อม : " + verifystatus4!),
              onTap: () =>alertDialog4(context),

            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              // leading: const Icon(Icons.report_problem,color: Colors.indigo),
              title: verifystatus3 == null ? Text("แทงจำหน่าย : ") : Text(
                  "แทงจำหน่าย : " + verifystatus3!),
              onTap: () =>alertDialog3(context),
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

  void alertDialog1(BuildContext context) {
    var alert = SimpleDialog(
      title: Text("รายชื่อครุภัณฑ์"),
      children: [
        SizedBox(
          width: MediaQuery.of(context).size.width,
          child: SingleChildScrollView(
            child: ListView.builder(
              shrinkWrap: true,
              itemBuilder: (ctx, index) {
                return SimpleDialogOption(
                  onPressed: () => viewdurable(listverifystatus1![index].pk.durable.Durable_code,listverifystatus1![index].Save_date.toString()),
                  child: Text(listverifystatus1![index].pk.durable.Durable_name +" "+ listverifystatus1![index].pk.durable.Durable_code),
                );
              },
              itemCount: listverifystatus1!.length,
            ),
          ),
        )
      ],
    );
    showDialog(context: context, builder: (BuildContext context) => alert);
  }

  void alertDialog2(BuildContext context) {
    var alert = SimpleDialog(
      title: Text("รายชื่อครุภัณฑ์"),
      children: [
        SizedBox(
          width: MediaQuery.of(context).size.width,
          child: SingleChildScrollView(
            child: ListView.builder(
              shrinkWrap: true,
              itemBuilder: (ctx, index) {
                return SimpleDialogOption(
                  onPressed: () => viewdurable(listverifystatus2![index].pk.durable.Durable_code,listverifystatus2![index].Save_date.toString()),
                  child: Text(listverifystatus2![index].pk.durable.Durable_name +" "+ listverifystatus2![index].pk.durable.Durable_code),
                );
              },
              itemCount: listverifystatus2!.length,
            ),
          ),
        )
      ],
    );
    showDialog(context: context, builder: (BuildContext context) => alert);
  }
  void alertDialog3(BuildContext context) {
    var alert = SimpleDialog(
      title: Text("รายชื่อครุภัณฑ์"),
      children: [
        SizedBox(
          width: MediaQuery.of(context).size.width,
          child: SingleChildScrollView(
            child: ListView.builder(
              shrinkWrap: true,
              itemBuilder: (ctx, index) {
                return SimpleDialogOption(
                  onPressed: () => viewdurable(listverifystatus3![index].pk.durable.Durable_code,listverifystatus3![index].Save_date.toString()),
                  child: Text(listverifystatus3![index].pk.durable.Durable_name +" "+ listverifystatus3![index].pk.durable.Durable_code),
                );
              },
              itemCount: listverifystatus3!.length,
            ),
          ),
        )
      ],
    );
    showDialog(context: context, builder: (BuildContext context) => alert);
  }
  void alertDialog4(BuildContext context) {
    var alert = SimpleDialog(
      title: Text("รายชื่อครุภัณฑ์"),
      children: [
        SizedBox(
          width: MediaQuery.of(context).size.width,
          child: SingleChildScrollView(
            child: ListView.builder(
              shrinkWrap: true,
              itemBuilder: (ctx, index) {
                return SimpleDialogOption(
                  onPressed: () => viewdurable2(listverifystatus4![index].informrepair.durable.Durable_code),
                  child: Text(listverifystatus4![index].informrepair.durable.Durable_name +" "+ listverifystatus4![index].informrepair.durable.Durable_code),
                );
              },
              itemCount: listverifystatus3!.length,
            ),
          ),
        )
      ],
    );
    showDialog(context: context, builder: (BuildContext context) => alert);
  }
  Widget buildDurableadmin2(List<VerifyDurable> durables) => ListView.builder(
    itemCount: listverifystatus1 == null ? 0 : listverifystatus1!.length,
    itemBuilder: (context, index) {
      final durable = durables[index];
      return  ListTile(
        title: Text("รายชื่อครุภัณฑ์"),
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

  Future<void> convertcode2(String code) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    List<String> listcode = code.split(".PNG");
    String durable_code = listcode[0].replaceAll(':', '/');
    preferences.setString('durable_code', durable_code);
    Navigator.of(context)
        .push(
        MaterialPageRoute(builder: (context) => Create_Maintenance_Page()));
  }
  Future<void> viewdurable(String code,String verifydate) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    List<String> listcode = code.split(".PNG");
    String durable_code = listcode[0].replaceAll(':', '/');
    preferences.setString('durable_code', durable_code);
    preferences.setString("selectyear",  selectedValueyears.toString());
    String dateverify = verifydate;
    preferences.setString('verifydurabledate', dateverify);

    Navigator.of(context)
        .push(
        MaterialPageRoute(builder: (context) => Viewdurablepage()));
  }
  Future<void> viewdurable2(String code) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    List<String> listcode = code.split(".PNG");
    String durable_code = listcode[0].replaceAll(':', '/');
    verify_manager vm = verify_manager();

   /* vm.getverifybydurablecode(durable_code.toString()).then((value) =>
    {
      verifydurablebycode = value,
      setState(() {
        isLoading = false;
      }),
    });*/

    preferences.setString('durable_code', durable_code);
    preferences.setString("selectyear",  selectedValueyears.toString());
   // String dateverify = verifydurablebycode!.Save_date;
   // preferences.setString('verifydurabledate', dateverify);

    Navigator.of(context)
        .push(
        MaterialPageRoute(builder: (context) => Viewdurablepage()));
  }

  void checkdropdown() {
    verify_manager vm = verify_manager();
    setState(() {
      vm.countdurable(selectedValue.toString()).then((value) =>
      {
        numberalldurable = value,
        setState(() {
          isLoading = false;
        }),
        log.e(numberalldurable),
      });

      vm.countdurabled(selectedValue.toString(), selectedValueyears.toString())
          .then((value) =>
      {
        numberdurabled = value,
        setState(() {
          isLoading = false;
        }),
        log.e(numberdurabled),
      });

      vm.countnotdurabled(
          selectedValue.toString(), selectedValueyears.toString()).then((
          value) =>
      {
        numbernotdurabled = value,
        setState(() {
          isLoading = false;
        }),
        log.e(numbernotdurabled),
      });

      vm.countverifystatus(
          selectedValue.toString(), selectedValueyears.toString()).then((
          value) =>
      {
        verifystatus1 = value,
        setState(() {
          isLoading = false;
        }),
      });
      vm.countverifystatus2(
          selectedValue.toString(), selectedValueyears.toString()).then((
          value) =>
      {
        verifystatus2 = value,
        setState(() {
          isLoading = false;
        }),
      });
      vm.countverifystatus3(
          selectedValue.toString(), selectedValueyears.toString()).then((
          value) =>
      {
        verifystatus3 = value,
        setState(() {
          isLoading = false;
        }),
      });
      vm.countverifystatus4(
          selectedValue.toString(), selectedValueyears.toString()).then((
          value) =>
      {
        verifystatus4 = value,
        setState(() {
          isLoading = false;
        }),
      });
    });
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
