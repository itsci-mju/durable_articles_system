import 'dart:async';
import 'dart:convert';

import 'package:curved_navigation_bar/curved_navigation_bar.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:logger/logger.dart';
import 'package:project_durable/manager/login_manager.dart';
import 'package:project_durable/manager/room_manager.dart';
import 'package:project_durable/model/durable_model.dart';
import 'package:project_durable/model/room_model.dart';
import 'package:project_durable/pages/scan_page.dart';
import 'package:project_durable/pages/viewdurable.dart';
import 'package:provider/provider.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:smart_select/smart_select.dart';

//import 'package:qrscan/qrscan.dart' as scanner;
import '../RsponseModel.dart';
import '../Strings.dart';

import 'package:http/http.dart' as http;

import '../manager/verify_manager.dart';
import '../model/durable_model.dart';
import 'package:dropdown_button2/dropdown_button2.dart';

import '../model/durable_model.dart';
import '../model/durable_model.dart';
import '../model/staff_model.dart';
import '../model/staff_model.dart';
import '../model/veify_model.dart';
import '../model/verifydurable_model.dart';
import '../splash.dart';
import 'List_repairADMIN_page.dart';
import 'List_repair_page.dart';
import 'View_verifybymajor_page.dart';
import 'login_page.dart';
import 'my_drawer_header.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  bool isLoading = true;
  String nameUser = "";
  String staff = "";
  String Listdurable = "";
  Staff? s;
  List<VerifyDurable>? listverify;
  List<Durable>? listnotverify;
  List<Durable>? listdurableadmin;
  List<VerifyDurable>? listdurableverifyed_admin;
  List<Room>? r;
  var log = Logger();
  String? selectedValue;
  String? selectedValueyears;
  List<Verify>? v;
  bool isVisible = true;
  bool isVisiblenotverify = true;
  bool isVisibleverify = false;
  bool isVisibldurableadmin = false;
  bool isVisibldurableadmin2 = false;
  String? Majorlogin;

  bool statusswitch = true;
  String _value = "2";
  int _value2 = 1;
  DateTime datenow = DateTime.now();
  final List<String> liststatusverify = [
    'ตรวจแล้ว',
    'ยังไม่ได้ตรวจ',
  ];
  String? selectedValuestatus = "ตรวจแล้ว";

  Future<void> getFirstRoom() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();

    staff = preferences.getString('Staff')!;
    Map<String, dynamic> map = jsonDecode(staff);
    log.e(map.toString());
    s = Staff.fromJson(map);

    setState(() {
      if (s!.major.ID_Major.toString() == "1") {
        selectedValue = '1101';
        if(datenow.month >= 10 && datenow.month <= 12){
          selectedValueyears = (datenow.year + 544).toString();
        }else {
          selectedValueyears = (datenow.year + 543).toString();
        }

        Majorlogin = "IT";
        room_manager rm = room_manager();
        rm.listroombymajor(s!.major.ID_Major.toString()).then((value) => {
              r = value,
              setState(() {
                isLoading = false;
              }),
              log.e(r.toString()),
            });
      }
      if (s!.major.ID_Major.toString() == "999") {
        selectedValue = '-';
        if(datenow.month >= 10 && datenow.month <= 12){
          selectedValueyears = (datenow.year + 544).toString();
        }else {
          selectedValueyears = (datenow.year + 543).toString();
        }
        Majorlogin = "SCI";
        room_manager rm = room_manager();
        rm.listallroom().then((value) => {
              r = value,
              setState(() {
                isLoading = false;
              }),
              log.e(r.toString()),
            });
      }
    });
  }

  void findUser() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    login_manager lm = login_manager();

    verify_manager vm = verify_manager();
    nameUser = preferences.getString('username')!;
    staff = preferences.getString('Staff')!;
    Map<String, dynamic> map = jsonDecode(staff);
    s = Staff.fromJson(map);

    vm.listyears().then((value) => {
          v = value,
          setState(() {
            isLoading = false;
          }),
          log.e("ลิสปี" + v.toString()),
        });

    lm
        .listdurable(s!.major.ID_Major.toString(), selectedValue.toString(),
            selectedValueyears.toString())
        .then((value) => {
              listverify = value,
              setState(() {
                isLoading = false;
              }),
            });

    lm
        .listdurablenotverify(s!.major.ID_Major.toString(),
            selectedValue.toString(), selectedValueyears.toString())
        .then((value) => {
              listnotverify = value,
              setState(() {
                isLoading = false;
              }),
            });

    lm
        .listalldurableadmin(
            selectedValue.toString(), selectedValueyears.toString())
        .then((value) => {
              listdurableadmin = value,
              setState(() {
                isLoading = false;
              }),
            });
  print(datenow.month);
  }

  final navigationKey = GlobalKey<CurvedNavigationBarState>();
  int index = 0;
  final screens = [
    HomePage(),
    QRViewExample(),
    Login_Page(),
  ];

  showAlert() async {
    Alert(
        context: context,
        type: AlertType.info,
        title: "แจ้งเตือน",
        desc: "ท่านต้องการออกจากระบบหรือไม่ ?",
        style : AlertStyle(titleStyle: TextStyle(fontWeight: FontWeight.bold,fontSize: 25),descStyle: TextStyle(fontWeight: FontWeight.bold,fontSize: 25,color: Colors.indigoAccent) ),
        buttons: [
          DialogButton(
            child: const Text("ยืนยัน",
                style: TextStyle(color: Colors.white, fontSize: 25,fontWeight: FontWeight.bold)),
            onPressed: () {
              logout();
            },
            width: 130,
          ),
          DialogButton(
            child: const Text("ยกเลิก",
                style: TextStyle(color: Colors.white, fontSize: 25,fontWeight: FontWeight.bold)),
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

  Future<bool> _onWillPop() async {
    return (await showDialog(
          context: context,
          builder: (context) => AlertDialog(
            title: new Text('แจ้งเตือน !'),
            content: new Text('ต้องการออกจากระบบ ?'),
            actions: <Widget>[
              TextButton(
                onPressed: () => Navigator.of(context).pop(false),
                child: new Text('ยกเลิก'),
              ),
              TextButton(
                onPressed: () => Navigator.of(context).pop(true),
                child: new Text('ยืนยัน'),
              ),
            ],
          ),
        )) ??
        false;
  }

  @override
  void initState() {
    super.initState();
    setState() {
      print('refreshing');
    }

    imageCache!.clear();
    imageCache!.clearLiveImages();
    getFirstRoom();
    findUser();
  }

  @override
  Widget build(BuildContext context) {
    if (isLoading) {
      return const Center(
        child: CircularProgressIndicator(),
      );
    }
    Color colors = Colors.black54;
    final itemslink = <Widget>[
      const Icon(Icons.home, size: 30, color: Colors.white),
      const Icon(Icons.camera_alt, size: 30, color: Colors.white),
      const Icon(Icons.logout, size: 30, color: Colors.white),
    ];
    return Scaffold(
      appBar: AppBar(
        title: const Text('รายงานการตรวจสอบครุภัณฑ์ประจำปี',
            style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
        leading: IconButton(
          onPressed: () {

          },
          icon: const Icon(Icons.inbox),
        ),
      ),
      endDrawer: Drawer(
        child: SingleChildScrollView(
          child: Container(
            child: Column(
              children: [
                MyHeaderDrawer(),
                MyDrawerList(),
              ],
            ),
          ),
        ),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Padding(
            padding: const EdgeInsets.only(left: 16),
            child: Column(
              children: [
                SizedBox(height: 8),
                Visibility(
                  visible: isVisible,
                  child: Container(
                      child: Column(
                    children: [
                      Row(
                        children: [
                          Column(
                            children: const [
                              Text("หมายเลขห้อง",
                                  style: TextStyle(
                                      fontSize: 25,
                                      fontWeight: FontWeight.w200)),
                            ],
                          ),
                          const SizedBox(width: 5),
                          Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              DropdownButtonHideUnderline(
                                child: DropdownButton2<String>(
                                  isExpanded: true,
                                  value: selectedValue,
                                  icon:
                                      const Icon(Icons.keyboard_arrow_down),
                                  items: r == null
                                      ? []
                                      : r!
                                          .map((item) =>
                                              DropdownMenuItem<String>(
                                                value: item.Room_number,
                                                child: Text(
                                                    item.Room_number,
                                                    style: TextStyle(
                                                        fontSize: 25,
                                                        fontWeight:
                                                            FontWeight
                                                                .w200)),
                                              ))
                                          .toList(),
                                  onChanged: (String? newValue) {
                                    setState(() {
                                      selectedValue = newValue!;
                                    });
                                    checkdropdown();
                                  },
                                  buttonHeight: 40,
                                  buttonWidth: 230,
                                  buttonPadding: const EdgeInsets.only(
                                      left: 14, right: 14),
                                  buttonDecoration: BoxDecoration(
                                    borderRadius: BorderRadius.circular(14),
                                    border: Border.all(
                                      color: Colors.black26,
                                    ),
                                    color: Colors.white,
                                  ),
                                  buttonElevation: 2,
                                  itemHeight: 50,
                                  itemPadding: const EdgeInsets.only(
                                      left: 14, right: 14),
                                  dropdownMaxHeight: 200,
                                  dropdownWidth: 250,
                                  dropdownPadding: null,
                                  dropdownDecoration: BoxDecoration(
                                    borderRadius: BorderRadius.circular(14),
                                    color: Colors.white,
                                  ),
                                  dropdownElevation: 8,
                                  scrollbarRadius:
                                      const Radius.circular(40),
                                  scrollbarThickness: 6,
                                  scrollbarAlwaysShow: true,
                                  offset: const Offset(-20, 0),
                                ),
                              ),
                            ],
                          ),
                        ],
                      ),
                      SizedBox(height: 5),
                      Row(
                        children: [
                          Column(
                            children: const [
                              Text("ปีงบประมาณ :",
                                  style: TextStyle(
                                      fontSize: 25,
                                      fontWeight: FontWeight.w200)),
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
                                     /* Expanded(
                                        child: Text(
                                          (datenow.year).toString(),
                                          style: TextStyle(
                                            fontSize: 14,
                                            color: Colors.black,
                                          ),
                                          overflow: TextOverflow.ellipsis,
                                        ),
                                      ),*/
                                    ],
                                  ),
                                  value: selectedValueyears,
                                  icon:
                                      const Icon(Icons.keyboard_arrow_down),
                                  items: v == null
                                      ? []
                                      : v!
                                          .map((item) =>
                                              DropdownMenuItem<String>(
                                                value: item.Years,
                                                child: Text(
                                                  item.Years,
                                                  style: TextStyle(
                                                      fontSize: 25,
                                                      fontWeight:
                                                          FontWeight.w200),
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
                                  buttonPadding: const EdgeInsets.only(
                                      left: 14, right: 14),
                                  buttonDecoration: BoxDecoration(
                                    borderRadius: BorderRadius.circular(14),
                                    border: Border.all(
                                      color: Colors.black26,
                                    ),
                                    color: Colors.white,
                                  ),
                                  buttonElevation: 2,
                                  itemHeight: 40,
                                  itemPadding: const EdgeInsets.only(
                                      left: 14, right: 14),
                                  dropdownMaxHeight: 200,
                                  dropdownWidth: 200,
                                  dropdownPadding: null,
                                  dropdownDecoration: BoxDecoration(
                                    borderRadius: BorderRadius.circular(14),
                                    color: Colors.white,
                                  ),
                                  dropdownElevation: 8,
                                  scrollbarRadius:
                                      const Radius.circular(40),
                                  scrollbarThickness: 6,
                                  scrollbarAlwaysShow: true,
                                  offset: const Offset(-20, 0),
                                ),
                              ),
                            ],
                          ),
                        ],
                      ),
                      SizedBox(height: 5),
                      Row(
                        children: [
                          /* Column(
                                  children: const [
                                    Text("สถานะการตรวจ :",
                                        style: TextStyle(fontSize: 16)),
                                  ],
                                ),*/
                          const SizedBox(width: 5),
                          Row(
                            children: [
                              Radio(
                                value: 1,
                                groupValue: _value2,
                                onChanged: (value) {
                                  setState(() {
                                    _value2 = 1;
                                  });
                                  checkdropdown();
                                },
                              ),
                              Text("ยังไม่ได้ตรวจสอบ",
                                  style: TextStyle(
                                      fontSize: 25,
                                      fontWeight: FontWeight.w200)),
                            ],
                          ),
                          Row(
                            children: [
                              Radio(
                                value: 2,
                                groupValue: _value2,
                                onChanged: (value) {
                                  setState(() {
                                    _value2 = 2;
                                  });
                                  checkdropdown();
                                },
                              ),
                            ],
                          ),
                          Text("ตรวจสอบแล้ว",
                              style: TextStyle(
                                  fontSize: 25,
                                  fontWeight: FontWeight.w200)),
                          /* CupertinoSwitch(
                                  value: statusswitch,
                                  onChanged: (value) {
                                    setState(() {
                                      statusswitch = value;
                                    });
                                    checkdropdown();
                                  },
                                  activeColor: CupertinoColors.activeGreen,
                                  trackColor: CupertinoColors.systemRed,
                                ),*/
                        ],
                      ),
                    ],
                  )),
                ),
              ],
            ),
          ),
          const SizedBox(height: 5.0),
          listverify == null? Container() :
          Visibility(
            visible: isVisibleverify,
            child: Expanded(
                child: listverify!.length == 0?
                Center(child: Text("ไม่พบข้อมูลที่ตรวจสอบแล้ว",style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)))
                    : buildDurable( listverify!)),
          ),
          listnotverify == null? Container() :
          Visibility(
            visible: isVisiblenotverify,
            child: Expanded(
                child: listnotverify!.length == 0 ?
                Center(child: Text("ไม่พบข้อมูลที่ยังไม่ตรวจสอบ",style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)))
                    : buildDurable2(listnotverify!)),
          ),
          listdurableadmin == null? Container() :
          Visibility(
            visible: isVisibldurableadmin,
            child: Expanded(
                child:  listdurableadmin!.length == 0 ?
                Center(child: Text("ไม่พบข้อมูลที่ยังไม่ตรวจสอบ",style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)))
                    : buildDurableadmin(listdurableadmin!)),
          ),
          listdurableverifyed_admin == null? Container() :
          Visibility(
            visible: isVisibldurableadmin2,
            child: Expanded(
                child: listdurableverifyed_admin!.length == 0 ?
                Center(child: Text("ไม่พบข้อมูลที่ตรวจสอบแล้ว",style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)))
                    :   buildDurableadmin2(listdurableverifyed_admin!)),
          )
        ],
      ),
      bottomNavigationBar: CurvedNavigationBar(
        key: navigationKey,
        color: Color(0xdfe65100),
        backgroundColor: Colors.transparent,
        height: 50,
        animationCurve: Curves.easeInOut,
        animationDuration: const Duration(milliseconds: 300),
        index: index,
        items: itemslink,
        onTap: (index) => setState(() {
          this.index = index;
          /* if (index.toString() == "0") {
            Navigator.of(context)
                .push(MaterialPageRoute(builder: (context) => screens[index]));
          }*/
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
    );
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
            Majorlogin == "SCI" ? menuItem2() : Container(),
          ],
        ),
      ),
    );
  }

  Widget menuItem() {
    return Material(
      child: InkWell(
        onTap: () {
          if (Majorlogin == "IT") {
            Navigator.of(context).push(
                MaterialPageRoute(builder: (context) => List_repair_page()));
          }
          if (Majorlogin == "SCI") {
            Navigator.of(context).push(MaterialPageRoute(
                builder: (context) => List_repairadmin_page()));
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
              )),
              Expanded(
                flex: 3,
                child: Text("รายการแจ้งซ่อมครุภัณฑ์",
                    style: TextStyle(color: Colors.black, fontSize: 25)),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget menuItem2() {
    return Material(
      child: InkWell(
        onTap: () {
          Navigator.of(context).push(MaterialPageRoute(
              builder: (context) => View_verifybymajor_page()));
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
              )),
              Expanded(
                flex: 3,
                child: Text("ข้อมูลครุภัณฑ์",
                    style: TextStyle(color: Colors.black, fontSize: 25)),
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

  Widget buildDurable(List<VerifyDurable> durables) => ListView.builder(
        itemCount: listverify == null ? 0 : listverify!.length,
        itemBuilder: (context, index) {
          final durable = durables[index];
          String? img;
          if (durable.pk.durable.Durable_image.toString() != "-") {
            img = Strings.url +
                "/file/durable_image/" +
                durable.pk.durable.Durable_image.toString();
            /*  img =
                "http://www.itsci.mju.ac.th/DurableWebservices/file/durable_image/" +
                    durable.pk.durable.Durable_image.toString();*/
          } else {
            img =
                "https://w7.pngwing.com/pngs/29/173/png-transparent-null-pointer-symbol-computer-icons-pi-miscellaneous-angle-trademark.png";
          }
          final splitted = durable.Save_date.toString().split(' ');
          log.e(splitted[0].toString());
          log.e(durable.Save_date.toString());
          return Card(
            child: Padding(
              padding: const EdgeInsets.only(top: 5, bottom: 5),
              child: Expanded(
                child: ListTile(
                  leading: CircleAvatar(
                    radius: 24,
                    backgroundImage: NetworkImage(img + "?v=1"),
                  ),
                  subtitle: SizedBox(
                    child: Column(
                      children: [
                        Row(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text("ชื่อครุภัณฑ์ : ",
                                style: TextStyle(
                                    fontSize: 22, fontWeight: FontWeight.bold)),
                            Flexible(
                              child: Text(durable.pk.durable.Durable_name,
                                  style: TextStyle(
                                      fontSize: 22, fontWeight: FontWeight.bold,color: Colors.indigo)),
                            ),
                          ],
                        ),
                        Row(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text("รหัสครุภัณฑ์ : ",
                                style: TextStyle(
                                    fontSize: 22, fontWeight: FontWeight.bold)),
                            Flexible(
                              child: Text(durable.pk.durable.Durable_code,
                                  style: TextStyle(
                                      fontSize: 22, fontWeight: FontWeight.bold,color: Colors.indigo)),
                            ),
                          ],
                        ),
                        Row(
                          children: [
                            const Text("วันที่ตรวจสอบ : ",
                                style: TextStyle(
                                    fontSize: 22, fontWeight: FontWeight.bold)),
                            Text(durable.Save_date.toString(),
                                style: TextStyle(
                                    fontSize: 22, fontWeight: FontWeight.bold,color: Colors.indigo)),
                          ],
                        ),
                        Row(
                          children: [
                            const Text("ห้อง : ",
                                style: TextStyle(
                                    fontSize: 22, fontWeight: FontWeight.bold)),
                            Text(durable.pk.durable.room.Room_number,
                                style: TextStyle(
                                    fontSize: 22, fontWeight: FontWeight.bold,color: Colors.indigo)),
                          ],
                        ),
                        Row(
                          children: [
                            const Text("ผู้ใช้: ",
                                style: TextStyle(
                                    fontSize: 22, fontWeight: FontWeight.bold)),
                            Text(durable.pk.durable.Responsible_person,
                                style: TextStyle(
                                    fontSize: 22, fontWeight: FontWeight.bold,color: Colors.indigo)),
                          ],
                        ),
                        Row(
                          children: [
                            const Text("สถานะ : ",
                                style: TextStyle(
                                    fontSize: 22, fontWeight: FontWeight.bold)),
                            durable.Durable_status == "ดี"
                                ? Text(durable.Durable_status,
                                    style: TextStyle(
                                        fontSize: 22,
                                        color: Colors.green,
                                        fontWeight: FontWeight.bold))
                                : durable.Durable_status == "ชำรุด"
                                    ? Text(durable.Durable_status,
                                        style: TextStyle(
                                            fontSize: 22,
                                            color: Colors.red,
                                            fontWeight: FontWeight.bold))
                                    : Text(durable.Durable_status,
                                        style: TextStyle(
                                            fontSize: 22,
                                            color: Colors.orange,
                                            fontWeight: FontWeight.bold)),
                          ],
                        ),
                        Row(
                          children: [
                            const Text("รายละเอียด : ",
                                style: TextStyle(
                                    fontSize: 22, fontWeight: FontWeight.bold)),
                            Text(durable.note,
                                style: TextStyle(
                                    fontSize: 22, fontWeight: FontWeight.bold,color: Colors.indigo)),
                          ],
                        ),
                      ],
                    ),
                  ),
                  isThreeLine: true,
                  /*  trailing: Icon(
                    Icons.remove_red_eye,
                    // color: Colors.green.shade700
                  ),*/
                  onTap: () {
                    convertcode2(durable.pk.durable.Durable_code.toString(),
                        durable.Save_date.toString());
                  },
                ),
              ),
            ),
          );
        },
      );

  Widget buildDurable2(List<Durable> durables) => ListView.builder(
        itemCount: listnotverify == null ? 0 : listnotverify!.length,
        itemBuilder: (context, index) {
          final durable = durables[index];
          String? img;
          if (durable.Durable_image.toString() != "-") {
            img = Strings.url +
                "/file/durable_image/" +
                durable.Durable_image.toString();
            /* img =
                "http://www.itsci.mju.ac.th/DurableWebservices/file/durable_image/" +
                    durable.Durable_image.toString();*/
          } else {
            img =
                "https://w7.pngwing.com/pngs/29/173/png-transparent-null-pointer-symbol-computer-icons-pi-miscellaneous-angle-trademark.png";
          }
          return Card(
            child: Padding(
              padding: const EdgeInsets.only(top: 5, bottom: 5),
              child: ListTile(
                leading: CircleAvatar(
                  radius: 24,
                  backgroundImage: NetworkImage(img + "?v=1"),
                ),

                subtitle: Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [

                      Row(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text("ชื่อครุภัณฑ์ : ",
                              style: TextStyle(
                                  fontSize: 22, fontWeight: FontWeight.bold)),
                          Flexible(
                            child: Text(durable.Durable_name,maxLines: 20,
                                style: TextStyle(
                                    fontSize: 22, fontWeight: FontWeight.bold,color: Colors.indigo)),
                          ),
                        ],
                      ),

                      Row(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          const Text("รหัสครุภัณฑ์ : ",
                              style: TextStyle(
                                  fontSize: 22, fontWeight: FontWeight.bold)),
                          Flexible(
                            child: Text(durable.Durable_code,
                                style: TextStyle(
                                    fontSize: 22, fontWeight: FontWeight.bold,color: Colors.indigo)),
                          ),
                        ],
                      ),
                      Row(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          const Text("วันที่ได้รับ : ",
                              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 22)),
                          Text(durable.Durable_entrancedate,
                              style: TextStyle(
                                  fontSize: 22, fontWeight: FontWeight.bold,color: Colors.indigo)),
                        ],
                      ),
                      Row(
                        children: [
                          const Text("ห้อง : ",
                              style: TextStyle(fontWeight: FontWeight.bold , fontSize: 22)),
                          Text(durable.room.Room_number,
                              style: TextStyle(
                                  fontSize: 22, fontWeight: FontWeight.bold,color: Colors.indigo)),
                        ],
                      ),
                      Row(
                        children: [
                          const Text("ผู้ใช้ : ",
                              style: TextStyle(fontWeight: FontWeight.bold , fontSize: 22)),
                          Text(durable.Responsible_person,
                              style: TextStyle(
                                  fontSize: 22, fontWeight: FontWeight.bold,color: Colors.indigo)),
                        ],
                      ),
                      Row(
                        children: [
                          const Text("สถานะ : ",
                              style: TextStyle(fontWeight: FontWeight.bold , fontSize: 22)),
                          durable.Durable_statusnow == "ดี"
                              ? Text(durable.Durable_statusnow,
                                  style: TextStyle(
                                      fontSize: 22,
                                      color: Colors.green,
                                      fontWeight: FontWeight.bold))
                              : durable.Durable_statusnow == "ชำรุด"
                                  ? Text(durable.Durable_statusnow,
                                      style: TextStyle(
                                          fontSize: 22,
                                          color: Colors.red,
                                          fontWeight: FontWeight.bold))
                                  : Text(durable.Durable_statusnow,
                                      style: TextStyle(
                                          fontSize: 22,
                                          color: Colors.orange,
                                          fontWeight: FontWeight.bold)),
                        ],
                      ),
                    ],
                  ),
                ),
                isThreeLine: true,
                /*trailing: Icon(
                  Icons.remove_red_eye,
                  // color: Colors.green.shade700
                ),*/
                onTap: () {
                  convertcode2(durable.Durable_code.toString(), "");
                },
              ),
            ),
          );
        },
      );

  Widget buildDurableadmin(List<Durable> durables) => ListView.builder(
        itemCount: listdurableadmin == null ? 0 : listdurableadmin!.length,
        itemBuilder: (context, index) {
          final durable = durables[index];
          String? img;
          if (durable.Durable_image.toString() != "-") {
            img = Strings.url +
                "/file/durable_image/" +
                durable.Durable_image.toString();
            /* img =
            "http://www.itsci.mju.ac.th/DurableWebservices/file/durable_image/" +
                durable.Durable_image.toString();*/
          } else {
            img =
                "https://w7.pngwing.com/pngs/29/173/png-transparent-null-pointer-symbol-computer-icons-pi-miscellaneous-angle-trademark.png";
          }
          return Card(
            child: Padding(
              padding: const EdgeInsets.only(top: 5, bottom: 5),
              child: ListTile(
                leading: CircleAvatar(
                  radius: 24,
                  backgroundImage: NetworkImage(img + "?v=1"),
                ),

                subtitle: Column(
                  children: [
                    Row(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Text("ชื่อครุภัณฑ์ : ",
                            style: TextStyle(fontWeight: FontWeight.bold, fontSize: 22)),
                        Flexible(
                          child: Text(durable.Durable_name,
                              style: TextStyle(
                                  fontSize: 22, fontWeight: FontWeight.bold,color: Colors.indigo)),
                        ),
                      ],
                    ),
                    Row(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                         Flexible(
                           child: Text("รหัสครุภัณฑ์ : ",
                              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 22)),
                         ),
                        Flexible(
                          child: Text(durable.Durable_code,
                              style: TextStyle(
                                  fontSize: 22, fontWeight: FontWeight.bold,color: Colors.indigo)),
                        ),
                      ],
                    ),
                    Row(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Text("วันที่ได้รับ : ",
                            style: TextStyle(fontWeight: FontWeight.bold, fontSize: 22)),
                        Flexible(
                          child: Text(durable.Durable_entrancedate,
                              style: TextStyle(
                                  fontSize: 22, fontWeight: FontWeight.bold,color: Colors.indigo)),
                        ),
                      ],
                    ),
                    Row(
                      children: [
                        const Text("ห้อง : ",
                            style: TextStyle(fontWeight: FontWeight.bold, fontSize: 22)),
                        Text(durable.room.Room_number,
                            style: TextStyle(
                                fontSize: 22, fontWeight: FontWeight.bold,color: Colors.indigo)),
                      ],
                    ),
                    Row(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Text("ผู้ใช้ : ",
                            style: TextStyle(fontWeight: FontWeight.bold, fontSize: 22)),
                        Flexible(
                          child: Text(durable.Responsible_person,
                              style: TextStyle(
                                  fontSize: 22, fontWeight: FontWeight.bold,color: Colors.indigo)),
                        ),
                      ],
                    ),
                    Row(
                      children: [
                        const Text("สถานะ : ",
                            style: TextStyle(fontWeight: FontWeight.bold, fontSize: 22)),
                        durable.Durable_statusnow == "ดี"
                            ? Text(durable.Durable_statusnow,
                                style: TextStyle(
                                    fontSize: 22,
                                    color: Colors.green,
                                    fontWeight: FontWeight.bold))
                            : durable.Durable_statusnow == "ชำรุด"
                                ? Text(durable.Durable_statusnow,
                                    style: TextStyle(
                                     fontSize: 22,
                                        color: Colors.red,
                                        fontWeight: FontWeight.bold))
                                : Text(durable.Durable_statusnow,
                                    style: TextStyle(
                                        fontSize: 22,
                                        color: Colors.orange,
                                        fontWeight: FontWeight.bold)),
                      ],
                    ),
                  ],
                ),
                isThreeLine: true,
                /*trailing: Icon(
              Icons.remove_red_eye,
              // color: Colors.green.shade700
            ),*/
                onTap: () {
                  convertcode2(durable.Durable_code.toString(), "");
                },
              ),
            ),
          );
        },
      );

  Widget buildDurableadmin2(List<VerifyDurable> durables) => ListView.builder(
        itemCount: listdurableverifyed_admin == null
            ? 0
            : listdurableverifyed_admin!.length,
        itemBuilder: (context, index) {
          final durable = durables[index];
          String? img;
          if (durable.pk.durable.Durable_image.toString() != "-") {
            img = Strings.url +
                "/file/durable_image/" +
                durable.pk.durable.Durable_image.toString();
            /* img =
            "http://www.itsci.mju.ac.th/DurableWebservices/file/durable_image/" +
                durable.pk.durable.Durable_image.toString();*/
          } else {
            img =
                "https://w7.pngwing.com/pngs/29/173/png-transparent-null-pointer-symbol-computer-icons-pi-miscellaneous-angle-trademark.png";
          }
          final splitted = durable.Save_date.toString().split(' ');
          log.e(splitted[0].toString());
          log.e(durable.Save_date.toString());
          return Card(
            child: Padding(
              padding: const EdgeInsets.only(top: 5, bottom: 5),
              child: Expanded(
                child: ListTile(
                  leading: CircleAvatar(
                    radius: 24,
                    backgroundImage: NetworkImage(img + "?v=1"),
                  ),
                  subtitle: SizedBox(
                    child: Column(
                      children: [
                        Row(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text("ชื่อครุภัณฑ์ : ",
                                style: TextStyle(fontSize:25,fontWeight: FontWeight.bold)),
                            Flexible(
                              child: Text(durable.pk.durable.Durable_name,
                                  style: TextStyle(
                                      fontSize: 25, fontWeight: FontWeight.bold,color: Colors.indigo)),
                            ),
                          ],
                        ),
                        Row(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                             Text("รหัสครุภัณฑ์ : ",
                                style: TextStyle(fontSize:25,fontWeight: FontWeight.bold)),
                            Flexible(
                              child: Text(durable.pk.durable.Durable_code,
                                  style: TextStyle(
                                      fontSize: 25, fontWeight: FontWeight.bold,color: Colors.indigo)),
                            ),
                          ],
                        ),
                        Row(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text("วันที่ตรวจสอบ : ",
                                style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
                            Flexible(
                              child: Text(durable.Save_date.toString(),
                                  style: TextStyle(
                                      fontSize: 25, fontWeight: FontWeight.bold,color: Colors.indigo)),
                            ),
                          ],
                        ),
                        Row(
                          children: [
                            const Text("ห้อง : ",
                                style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
                            Text(durable.pk.durable.room.Room_number,
                                style: TextStyle(
                                    fontSize: 25, fontWeight: FontWeight.bold,color: Colors.indigo)),
                          ],
                        ),
                        Row(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text("ผู้ใช้: ",
                                style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
                            Flexible(
                              child: Text(durable.pk.durable.Responsible_person,
                                  style: TextStyle(
                                      fontSize: 25, fontWeight: FontWeight.bold,color: Colors.indigo)),
                            ),
                          ],
                        ),
                        Row(
                          children: [
                            const Text("สถานะ : ",
                                style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
                            durable.Durable_status == "ดี"
                                ? Text(durable.Durable_status,
                                    style: TextStyle(
                                        fontSize: 25,
                                        color: Colors.green,
                                        fontWeight: FontWeight.bold))
                                : durable.Durable_status == "ชำรุด"
                                    ? Text(durable.Durable_status,
                                        style: TextStyle(
                                            fontSize: 25,
                                            color: Colors.red,
                                            fontWeight: FontWeight.bold))
                                    : Text(durable.Durable_status,
                                        style: TextStyle(
                                            fontSize: 25,
                                            color: Colors.orange,
                                            fontWeight: FontWeight.bold)),
                          ],
                        ),
                        Row(
                          children: [
                            const Text("รายละเอียด : ",
                                style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
                            Text(durable.note,
                                style: TextStyle(
                                    fontSize: 25, fontWeight: FontWeight.bold,color: Colors.indigo)),
                          ],
                        ),
                      ],
                    ),
                  ),
                  isThreeLine: true,
                  /* trailing: Icon(
                Icons.remove_red_eye,
                // color: Colors.green.shade700
              ),*/
                  onTap: () {
                    convertcode2(durable.pk.durable.Durable_code.toString(),
                        durable.Save_date.toString());
                  },
                ),
              ),
            ),
          );
        },
      );

  Future<void> convertcode(String code) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    List<String> listcode = code.split(".png");
    String durable_code = listcode[0].replaceAll(':', '/');
    preferences.setString('durable_code', durable_code);
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => Viewdurablepage()));
  }

  Future<void> convertcode2(String code, String verifydate) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    List<String> listcode = code.split(".png");
    String durable_code = listcode[0].replaceAll(':', '/');
    String dateverify = verifydate;

    preferences.setString("selectroom", selectedValue.toString());
    preferences.setString("selectyear", selectedValueyears.toString());
    //   preferences.setString('verifydurabledate', dateverify);
    preferences.setString('durable_code', durable_code);
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => Viewdurablepage()));
  }



  void checkdropdown() {
    login_manager lm = login_manager();
    if (Majorlogin == "IT") {
      if (_value2 == 1) {
        setState(() {
          lm
              .listdurablenotverify(s!.major.ID_Major.toString(),
                  selectedValue.toString(), selectedValueyears.toString())
              .then((value) => {
                    listnotverify = value,
                    setState(() {
                      isLoading = false;
                    }),
                  });
          isVisiblenotverify = true;
          isVisibleverify = false;
          isVisibldurableadmin = false;
        });
      } else if (_value2 == 2) {
        setState(() {
          lm
              .listdurable(s!.major.ID_Major.toString(),
                  selectedValue.toString(), selectedValueyears.toString())
              .then((value) => {
                    listverify = value,
                    setState(() {
                      isLoading = false;
                    }),
                  });
          isVisibleverify = true;
          isVisiblenotverify = false;
          isVisibldurableadmin = false;
        });
      }
    } else if (Majorlogin == "SCI") {
      if (_value2 == 1) {
        setState(() {
          setState(() {
            lm
                .listalldurableadmin(
                    selectedValue.toString(), selectedValueyears.toString())
                .then((value) => {
                      listdurableadmin = value,
                      setState(() {
                        isLoading = false;
                      }),
                    });
            isVisibleverify = false;
            isVisiblenotverify = false;
            isVisibldurableadmin = true;
            isVisibldurableadmin2 = false;
          });
        });
      } else if (_value2 == 2) {
        setState(() {
          lm
              .listdurableverifyed_admin(
                  selectedValue.toString(), selectedValueyears.toString())
              .then((value) => {
                    listdurableverifyed_admin = value,
                    setState(() {
                      isLoading = false;
                    }),
                  });
          isVisibleverify = false;
          isVisiblenotverify = false;
          isVisibldurableadmin = false;
          isVisibldurableadmin2 = true;
        });
      }
    }
  }
}
