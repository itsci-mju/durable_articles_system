import 'dart:convert';

import 'package:buddhist_datetime_dateformat_sns/buddhist_datetime_dateformat_sns.dart';
import 'package:curved_navigation_bar/curved_navigation_bar.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:logger/logger.dart';
import 'package:project_durable/manager/durable_manager.dart';
import 'package:project_durable/manager/verify_manager.dart';
import 'package:project_durable/model/InformRepair_model.dart';
import 'package:project_durable/pages/scan_page.dart';
import 'package:project_durable/pages/verify_page.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../Strings.dart';
import '../manager/inform_manager.dart';
import '../manager/login_manager.dart';
import '../model/durable_model.dart';
import '../model/staff_model.dart';
import '../model/veify_model.dart';
import '../model/verifydurable_model.dart';
import '../splash.dart';
import 'EDITverify_page.dart';
import 'List_Repair_History_page.dart';
import 'List_repairADMIN_page.dart';
import 'List_repair_page.dart';
import 'View_verifybymajor_page.dart';
import 'home_page.dart';
import 'inform_page.dart';
import 'login_page.dart';
import 'my_drawer_header.dart';

class Viewdurablepage extends StatefulWidget {
  @override
  _ViewdurablePageState createState() => _ViewdurablePageState();
}

class _ViewdurablePageState extends State<Viewdurablepage> {
  String? codedurable;
  Durable? d;
  Staff? s;
  String? staff;

  bool isLoading = true;
  String? Majorlogin;
  String? verifydate;
  inform_repair? inform;
  String? checkverify;
  String? checkinformrepair;
  String? checkinformrepairnotrepair;
  String? selectyear;
  inform_repair? informrepair;
  VerifyDurable? verify;
  VerifyDurable? verify2;
  VerifyDurable? verify3;
  VerifyDurable? verifydurablebycode;
  var log = Logger();
  var formatter = DateFormat.yMMMMd();
  DateTime now = DateTime.now();

  void findUser() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    durable_manager dm = durable_manager();
    inform_manager im = inform_manager();
    verify_manager vm = verify_manager();
    codedurable = preferences.getString('durable_code')!;
   // verifydate = preferences.getString('verifydurabledate')!;
    selectyear = preferences.getString('selectyear')!;

    staff = preferences.getString('Staff')!;
    Map<String, dynamic> map = jsonDecode(staff!);
    s = Staff.fromJson(map);

    im.getinform_repairbyID(codedurable.toString()).then((value) => {
          inform = value,
          setState(() {
            isLoading = false;
          }),
          log.e(inform),
        });
    vm
        .getverifyinverifydurable(codedurable.toString(), selectyear!)
        .then((value) => {
              checkverify = value,
              setState(() {
                isLoading = false;
              }),
              // log.e("ลิสตรวจสอบ" + checkverifynumber.toString()),
            });
    vm
        .getverifybydurablecurrent(
            codedurable.toString(), (now.year + 543).toString())
        .then((value) => {
              verify = value,
              setState(() {
                isLoading = false;
              }),
            });

    vm
        .getverifybydurablecurrent(
        codedurable.toString(),  selectyear!)
        .then((value) => {
      verifydurablebycode = value,
      setState(() {
        isLoading = false;
      }),
    });
    vm
        .getverifybydurablecurrent(
            codedurable.toString(), (now.year + 542).toString())
        .then((value) => {
              verify2 = value,
              setState(() {
                isLoading = false;
              }),
            });
    vm
        .getverifybydurablecurrent(
            codedurable.toString(), (now.year + 541).toString())
        .then((value) => {
              verify3 = value,
              setState(() {
                isLoading = false;
              }),
            });

    im.getdurablein_informrepair(codedurable.toString()).then((value) => {
          checkinformrepair = value,
          setState(() {
            isLoading = false;
          }),
        });
    im.getdurablein_informrepair2(codedurable.toString()).then((value) => {
          informrepair = value,
          setState(() {
            isLoading = false;
          }),
        });

    im
        .getdurableininformrepairnotrepair(codedurable.toString())
        .then((value) => {
              checkinformrepairnotrepair = value,
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
    vm.getverifybydurablecode(codedurable.toString()).then((value) =>
    {
      verifydurablebycode = value,
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

  @override
  void initState() {
    super.initState();
      imageCache!.clear();
      imageCache!.clearLiveImages();
    setState(){
      print('refreshing');
    }
    findUser();
  }

  final navigationKey = GlobalKey<CurvedNavigationBarState>();
  int index = 0;
  final screens = [
    HomePage(),
    QRViewExample(),
    Login_Page(),
  ];

  alerttolistrepair(String alert) async {
    Alert(
        context: context,
        type: AlertType.info,
        title: "แจ้งเตือน",
        desc: alert,
        buttons: [
          DialogButton(
            child: const Text("ยืนยัน",
                style: TextStyle(color: Colors.white, fontSize: 20)),
            onPressed: () {
              Navigator.of(context).push(
                  MaterialPageRoute(builder: (context) => List_repair_page()));
            },
            width: 130,
          ),
        ]).show();
  }

  alerttolistrepairAD(String alert) async {
    Alert(
        context: context,
        type: AlertType.info,
        title: "แจ้งเตือน",
        desc: alert,
        buttons: [
          DialogButton(
            child: const Text("ยืนยัน",
                style: TextStyle(color: Colors.white, fontSize: 20)),
            onPressed: () {
              Navigator.of(context).push(MaterialPageRoute(
                  builder: (context) => List_repairadmin_page()));
            },
            width: 130,
          ),
        ]).show();
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
                Icons.dashboard_outlined,
                size: 20,
                color: Colors.black,
              )),
              Expanded(
                flex: 3,
                child: Text("รายการแจ้งซ่อมครุภัณฑ์",
                    style: TextStyle(color: Colors.black, fontSize: 16)),
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
                    Icons.dashboard_outlined,
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

  Widget MyDrawerList() {
    return Positioned(
      bottom: 0.0,
      child: Container(
        padding: const EdgeInsets.only(top: 15),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.end,
          children: [
            menuItem(),
            Majorlogin =="SCI"? menuItem2():Container(),
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    Color colors = Colors.black54;
    final items = <Widget>[
      const Icon(Icons.home, size: 30, color: Colors.white),
      const Icon(Icons.camera_alt, size: 30, color: Colors.white),
      const Icon(Icons.logout, size: 30, color: Colors.white),
    ];
    if (isLoading) {
      return const Center(
        child: CircularProgressIndicator(),
      );
    }
    return Scaffold(
      appBar: AppBar(
        title: const Text('ข้อมูลครุภัณฑ์'),
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
      /*body: Center(
        child: Text("รหัสครุภัณฑ์ : "+d!.durable_code),
      ),*/
      body: SingleChildScrollView(
        child: Column(
          children: [
            d == null ? Container() : _getHeader(),
            const SizedBox(
              height: 2,
            ),
            _heading("รายละเอียดครุภัณฑ์"),
            const SizedBox(
              height: 2,
            ),
            d == null ? Container() : _detailsCard(),
            const SizedBox(
              height: 2,
            ),
            _heading("สถานะการใช้งาน"),
            const SizedBox(
              height: 2,
            ),
            d == null ? Container() : _detailsStatusCard(),
            const SizedBox(
              height: 2,
            ),
            d == null ? Container() : _button(),
          ],
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

  Widget _getHeader() {
    String? img;

    if (d!.Durable_image.toString() != "-") {
      setState(() {
        img =   Strings.url+"/file/durable_image/" +d!.Durable_image.toString();
        /* img =
          "http://www.itsci.mju.ac.th/DurableWebservices/file/durable_image/" +
              d!.Durable_image.toString();*/
      });
      log.e(img);
    } else {
      img =
          "https://w7.pngwing.com/pngs/29/173/png-transparent-null-pointer-symbol-computer-icons-pi-miscellaneous-angle-trademark.png";
    }
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Padding(
          padding: const EdgeInsets.all(5.0),
          child: Container(
            height: 300,
            width: 300,
            child: Image.network(img!+"?v=1"),
          ),
        )
      ],
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

  Widget _button() {
    var dateinform;
    var dateinform2;
    inform == null
        ? []
        : dateinform =
            formatter.formatInBuddhistCalendarThai(inform!.dateinform);
    informrepair == null
        ? []
        : dateinform2 =
            formatter.formatInBuddhistCalendarThai(informrepair!.dateinform);
    //  var informtime = DateFormat('kk:mm').format(inform!.dateinform);

    return Padding(
      padding: const EdgeInsets.only(
        bottom: 20,
      ),
      child: Container(
        width: MediaQuery.of(context).size.width * 0.9,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                checkverify == "1"
                    ? Align(
                        alignment: Alignment.center,
                        child: Align(
                          alignment: Alignment.center,
                          child: ElevatedButton.icon(
                            style: TextButton.styleFrom(
                              backgroundColor: Colors.blueAccent,
                            ),
                            icon: const Icon(
                              Icons.warning,
                              color: Colors.white,
                              size: 30.0,
                            ),
                            label: Text('เคยตรวจแล้วเมื่อ ' + verifydurablebycode!.Save_date),
                            onPressed: () {
                              getdurable3(d!.Durable_code.toString());
                            },
                          ),
                        ),
                      )
                    : Align(
                        alignment: Alignment.center,
                        child: ElevatedButton.icon(
                          style: TextButton.styleFrom(
                            backgroundColor: Colors.blueAccent,
                          ),
                          icon: const Icon(
                            Icons.zoom_in,
                            color: Colors.white,
                            size: 30.0,
                          ),
                          label: const Text('ตรวจสอบครุภัณฑ์'),
                          onPressed: () {
                            getdurable();
                          },
                        ),
                      ),
                const SizedBox(width: 10),
              ],
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                checkinformrepair == "1"
                    ? Align(
                        alignment: Alignment.center,
                        child: ElevatedButton.icon(
                          style: TextButton.styleFrom(
                            backgroundColor: Colors.green,
                          ),
                          icon: const Icon(
                            Icons.warning,
                            color: Colors.white,
                            size: 30.0,
                          ),
                          label: Text('มีคนแจ้งมาเมื่อวันที่ ' + dateinform),
                          onPressed: () {
                            getdurable2(d!.Durable_code.toString());
                          },
                        ),
                      )
                    : checkinformrepairnotrepair == "1"
                        ? Align(
                            alignment: Alignment.center,
                            child: ElevatedButton.icon(
                              style: TextButton.styleFrom(
                                backgroundColor: Colors.green,
                              ),
                              icon: const Icon(
                                Icons.warning,
                                color: Colors.white,
                                size: 30.0,
                              ),
                              label: Text('เจ้าหน้าที่ทำการตรวจสอบแล้ว สถานะรอยืนยัน'),
                              onPressed: () {
                                getdurable2(d!.Durable_code.toString());
                              },
                            ),
                          )
                        : Align(
                            alignment: Alignment.center,
                            child: ElevatedButton.icon(
                              style: TextButton.styleFrom(
                                backgroundColor: Colors.green,
                              ),
                              icon: const Icon(
                                Icons.build,
                                color: Colors.white,
                                size: 30.0,
                              ),
                              label: const Text('แจ้งซ่อมครุภัณฑ์'),
                              onPressed: () {
                                informdurable();
                              },
                            ),
                          ),
              ],
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const Text(''),
                ElevatedButton.icon(
                  style: TextButton.styleFrom(
                    backgroundColor: Colors.orange,
                  ),
                  icon: const Icon(
                    Icons.schedule,
                    color: Colors.white,
                    size: 30.0,
                  ),
                  label: const Text('ประวัติการส่งซ่อม'),
                  onPressed: () {
                    getdurablegohistory(d!.Durable_code.toString());
                  },
                ),
              ],
            ),
          ],
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
              // leading: const Icon(Icons.favorite,color: Colors.indigo),
              title: Text("รหัสครุภัณฑ์ : " + d!.Durable_code),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              //leading: const Icon(Icons.event_seat,color: Colors.indigo),
              title: Text("ชื่อครุภัณฑ์ : " + d!.Durable_name),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              //leading: const Icon(Icons.key,color: Colors.indigo),
              title: Text("จำนวน : " + d!.Durable_number),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              // leading: const Icon(Icons.branding_watermark_sharp,color: Colors.indigo),
              title: Text("ยี่ห้อ : " + d!.Durable_brandname),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              // leading: const Icon(Icons.payments,color: Colors.indigo),
              title: Text("ราคาต่อหน่วย : " + d!.Durable_price),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              // leading: const Icon(Icons.watch_later,color: Colors.indigo),
              title: Text("วันที่ได้รับ : " + d!.Durable_entrancedate),
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
              title: Text("ห้องที่ใช้ครุภัณฑ์ : " + d!.room.Room_number),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              //leading: const Icon(Icons.person,color: Colors.indigo),
              title: Text("ผู้ใช้ครุภัณฑ์ : " + d!.Responsible_person),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              // leading: const Icon(Icons.signal_wifi_statusbar_4_bar,color: Colors.indigo),
              title: Text("สถานะครุภัณฑ์ : " + d!.Durable_statusnow),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              // leading: const Icon(Icons.report_problem,color: Colors.indigo),
              title: Text("รายละเอียด : " + d!.note),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            verify == null
                ? Container()
                : ListTile(
                    // leading: const Icon(Icons.report_problem,color: Colors.indigo),
                    title: Text("การตรวจสอบปี " +
                        verify!.pk.verify.Years.toString() +
                        " : " +
                        verify!.Durable_status.toString()),
                  ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            verify2 == null
                ? Container()
                : ListTile(
                    // leading: const Icon(Icons.report_problem,color: Colors.indigo),
                    title: Text("การตรวจสอบปี " +
                        verify2!.pk.verify.Years.toString() +
                        " : " +
                        verify2!.Durable_status.toString()),
                  ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            verify3 == null
                ? Container()
                : ListTile(
                    // leading: const Icon(Icons.report_problem,color: Colors.indigo),
                    title: Text("การตรวจสอบปี " +
                        verify3!.pk.verify.Years.toString() +
                        " : " +
                        verify3!.Durable_status.toString()),
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

  void logout() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => Splash()));
    preferences.clear();
    var log = Logger();
    log.e("คุณออกจากระบบแล้ว");
  }

  void getdurable() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    preferences.setString('getdurable', d!.toString());
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => Verify_Page()));
  }

  void getdurable2(String durablecode) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    preferences.setString('durable_code', durablecode);
    /*Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => EditVerify_Page()));*/


    if (Majorlogin == "SCI") {
      Navigator.of(context)
          .push(MaterialPageRoute(builder: (context) => List_repairadmin_page()));
    } else {
      Navigator.of(context)
          .push(MaterialPageRoute(builder: (context) => List_repair_page()));

    }

  }

  void getdurable3(String durablecode) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    preferences.setString('durable_code', durablecode);
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => EditVerify_Page()));
  }

  void getdurablegohistory(String durablecode) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    preferences.setString('durable_code', durablecode);
    Navigator.of(context).push(
        MaterialPageRoute(builder: (context) => ListRepairHistory_Page()));
  }

  void informdurable() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    preferences.setString('getdurable', d!.toString());

   /* if (inform != null && Majorlogin == "SCI") {
      alerttolistrepairAD("มีการแจ้งซ่อมนี้อยู่แล้ว !");
    } else {
      Navigator.of(context)
          .push(MaterialPageRoute(builder: (context) => Inform_Page()));
    }*/
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => Inform_Page()));
    // var log = Logger();
    // log.e(d!.toString());
  }
}
