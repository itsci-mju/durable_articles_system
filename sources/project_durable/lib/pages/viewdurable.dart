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
  String? selectroom;
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
    //selectroom = preferences.getString('selectroom')!;
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
    String? selectedValueyears;
    String? selectedValueyears2;
    String? selectedValueyears3;
    if(now.month >= 10 && now.month <= 12){
      selectedValueyears = (now.year + 544).toString();
      selectedValueyears2 = (now.year + 543).toString();
      selectedValueyears3 = (now.year + 542).toString();
    }else {
      selectedValueyears = (now.year + 543).toString();
      selectedValueyears2 = (now.year + 542).toString();
      selectedValueyears3 = (now.year + 541).toString();
    }

    vm.getverifybydurablecurrent(
            codedurable.toString(), (selectedValueyears).toString())
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
            codedurable.toString(), (selectedValueyears2).toString())
        .then((value) => {
              verify2 = value,
              setState(() {
                isLoading = false;
              }),
            });
    vm
        .getverifybydurablecurrent(
            codedurable.toString(), (selectedValueyears3).toString())
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
  log.e(checkinformrepairnotrepair);

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
                    style: TextStyle(color: Colors.black, fontSize: 25)),
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
                child: Text("ข้อมูลครุภัณฑ์",style: TextStyle(color: Colors.black,fontSize: 25)),
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
        title: const Text('ข้อมูลครุภัณฑ์',style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
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
            const SizedBox(
              height: 2,
            ),

            verify !=null||verify2 !=null||verify3!=null?
            _heading("สถานะการตรวจสอบ 3ปี ย้อนหลัง"):Container(),
            const SizedBox(
              height: 2,
            ),
            verify != null||verify2 != null||verify3!=null? _detailsStatusCard2():Container(),
            const SizedBox(
              height: 2,
            ),

            d == null ? Container() : _button(),
          ],
        ),
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
        style: const TextStyle(fontSize: 25, fontWeight: FontWeight.bold),
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

    return Column(
      children: [
        checkverify == "1"? Text('**มีการตรวจแล้วเมื่อ ' + verifydurablebycode!.Save_date,style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold,color: Colors.red)) : Text(''),
        checkverify == "1"
            ? Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height *0.060,
          child: Padding(
            padding: const EdgeInsets.only(left: 10,right: 10),
            child: ElevatedButton.icon(
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.blueAccent,

              ),
              icon: const Icon(
                Icons.edit,
                color: Colors.white,
                size: 30.0,
              ),
              label: Text('แก้ไขการตรวจสอบ',style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold)),
              onPressed: () {
                getdurable3(d!.Durable_code.toString());
              },
            ),
          ),
        ):
        Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height *0.060,
         child: Padding(
           padding: const EdgeInsets.only(left: 10,right: 10),
           child: ElevatedButton.icon(
             style: ElevatedButton.styleFrom(
               backgroundColor: Colors.blueAccent,
             ),
             icon: const Icon(
               Icons.zoom_in,
               color: Colors.white,
               size: 30.0,
             ),
             label: const Text('ตรวจสอบครุภัณฑ์',style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold)),
             onPressed: () {
               getdurable();
             },
           ),
         ),
        ),
        SizedBox(height: 10),
        checkinformrepair == "1"?  Text('**มีการแจ้งมาเมื่อวันที่ ' + dateinform,style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold,color: Colors.red)):
        checkinformrepairnotrepair == "1" ?  Text('**เจ้าหน้าที่ทำการตรวจสอบแล้ว สถานะรอซ่อม ' + dateinform,style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold,color: Colors.red)):Text(''),
        checkinformrepair == "1"?
        Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height *0.060,
          child: Padding(
            padding: const EdgeInsets.only(left: 10,right: 10),
            child: ElevatedButton.icon(
              style: TextButton.styleFrom(
                backgroundColor: Colors.green,
              ),
              icon: const Icon(
                Icons.build,
                color: Colors.white,
                size: 30.0,
              ),
              label: Text('ดูรายการแจ้งซ่อม',style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold)),
              onPressed: () {
                getdurable2(d!.Durable_code.toString());
              },
            ),
          ),
        )
            :
        checkinformrepairnotrepair == "1"?
        Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height *0.060,
          child: Padding(
            padding: const EdgeInsets.only(left: 10,right: 10),
            child: ElevatedButton.icon(
              style: TextButton.styleFrom(
                backgroundColor: Colors.green,
              ),
              icon: const Icon(
                Icons.build,
                color: Colors.white,
                size: 30.0,
              ),
              label: Text('ดูรายการแจ้งซ่อม',style: TextStyle(fontSize: 25)),
              onPressed: () {
                getdurable2(d!.Durable_code.toString());
              },
            ),
          ),
        )
        :
        Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height *0.060,
          child: Padding(
            padding: const EdgeInsets.only(left: 10,right: 10),
            child: ElevatedButton.icon(
              style: ElevatedButton.styleFrom(
              primary: Colors.green,
              ),
              icon: const Icon(
                Icons.build,
                color: Colors.white,
                size: 30.0,
              ),
              label: const Text('แจ้งซ่อมครุภัณฑ์',style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold)),
              onPressed: () {
                informdurable();
              },
            ),
          ),
        ),
        SizedBox(height: 15),
        Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height *0.060,
          child: Padding(
            padding: const EdgeInsets.only(left: 10,right: 10),
            child: ElevatedButton.icon(
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.orange,
              ),
              icon: const Icon(
                Icons.schedule,
                color: Colors.white,
                size: 30.0,
              ),
              label: const Text('ประวัติการส่งซ่อม',style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold)),
              onPressed: () {
                getdurablegohistory(d!.Durable_code.toString());
              },
            ),
          ),
        ),
        SizedBox(height: 15),
      ],
    );
  }

  Widget _detailsCard() {
    return Padding(
      padding: const EdgeInsets.all(10.0),
      child: Flexible(
        child: Card(
          elevation: 4,
          child: Column(
            children: [
              ListTile(
                // leading: const Icon(Icons.favorite,color: Colors.indigo),
                title: Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text("รหัสครุภัณฑ์ : ",style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)),
                    Flexible(child: Text(d!.Durable_code,style: TextStyle(color: Colors.indigoAccent,fontWeight: FontWeight.bold,fontSize: 25))),
                  ],
                ),
              ),
              const Divider(
                height: 0.6,
                color: Colors.black87,
              ),
              ListTile(
                //leading: const Icon(Icons.event_seat,color: Colors.indigo),
                title: Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text("ชื่อครุภัณฑ์ : ",style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)),
                    Flexible(child: Text(d!.Durable_name,style: TextStyle(color: Colors.indigoAccent,fontWeight: FontWeight.bold,fontSize: 25))),

                  ],
                ),
              ),
              const Divider(
                height: 0.6,
                color: Colors.black87,
              ),
              ListTile(
                //leading: const Icon(Icons.key,color: Colors.indigo),
                title: Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text("จำนวน : ",style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)),
                    Text(d!.Durable_number,style: TextStyle(color: Colors.indigoAccent,fontWeight: FontWeight.bold,fontSize: 25)),
                  ],
                ),
              ),
              const Divider(
                height: 0.6,
                color: Colors.black87,
              ),
              ListTile(
                // leading: const Icon(Icons.branding_watermark_sharp,color: Colors.indigo),
                title: Row(

                  children: [
                    Text("ยี่ห้อ : ",style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)),
                    Text(d!.Durable_brandname,style: TextStyle(color: Colors.indigoAccent,fontWeight: FontWeight.bold,fontSize: 25)),
                  ],
                ),
              ),
              const Divider(
                height: 0.6,
                color: Colors.black87,
              ),
              ListTile(
                // leading: const Icon(Icons.payments,color: Colors.indigo),
                title: Row(
                  children: [
                    Text("ราคาต่อหน่วย : ",style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)),
                    Text(d!.Durable_price,style: TextStyle(color: Colors.indigoAccent,fontWeight: FontWeight.bold,fontSize: 25)),
                  ],
                ),
              ),
              const Divider(
                height: 0.6,
                color: Colors.black87,
              ),
              ListTile(
                // leading: const Icon(Icons.watch_later,color: Colors.indigo),
                title: Row(
                  children: [
                    Text("วันที่ได้รับ : ",style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)),
                    Text(d!.Durable_entrancedate,style: TextStyle(color: Colors.indigoAccent,fontWeight: FontWeight.bold,fontSize: 25)),
                  ],
                ),
              ),
              const Divider(
                height: 0.6,
                color: Colors.black87,
              ),
            ],
          ),
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
              title: Row(
                children: [
                  Text("ห้องที่ใช้ครุภัณฑ์ : ",style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)),
                  Text(d!.room.Room_number,style: TextStyle(color: Colors.indigoAccent,fontWeight: FontWeight.bold,fontSize: 25)),
                ],
              ),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              //leading: const Icon(Icons.person,color: Colors.indigo),
              title: Row(
                children: [
                  Text("ผู้ใช้ครุภัณฑ์ : " ,style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)),
                  Text(d!.Responsible_person,style: TextStyle(color: Colors.indigoAccent,fontWeight: FontWeight.bold,fontSize: 25)),
                ],
              ),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              // leading: const Icon(Icons.signal_wifi_statusbar_4_bar,color: Colors.indigo),
              title: d!.Durable_statusnow=="ดี"?Row(
                children: [
                  Text("สถานะครุภัณฑ์ : ",style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)),
                  Text(d!.Durable_statusnow,style: TextStyle(color: Colors.green,fontWeight: FontWeight.bold,fontSize: 25)),
                ],
              )
                  :d!.Durable_statusnow == "ชำรุด"
                  ? Row(
                children: [
                  Text("สถานะครุภัณฑ์ : ",style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)),
                  Text(d!.Durable_statusnow,style: TextStyle(color: Colors.red,fontWeight: FontWeight.bold,fontSize: 25)),
                ],
              ):Row(
                children: [
                  Text("สถานะครุภัณฑ์ : ",style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)),
                  Text(d!.Durable_statusnow,style: TextStyle(color: Colors.orange,fontWeight: FontWeight.bold,fontSize: 25)),
                ],
              ),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            ListTile(
              // leading: const Icon(Icons.report_problem,color: Colors.indigo),
              title: Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text("รายละเอียด : ",style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)),
                  Flexible(child: Text(d!.note,style: TextStyle(color: Colors.indigoAccent,fontWeight: FontWeight.bold,fontSize: 25))),
                ],
              ),
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


  Widget _detailsStatusCard2() {
    return Padding(
      padding: const EdgeInsets.all(10.0),
      child: Card(

        elevation: 4,
        child: Column(
          children: [
            verify == null
                ? Container()
                : ListTile(
              tileColor:    verify!.Durable_status.toString()== "ดี"?  Colors.green.shade300 : verify!.Durable_status.toString()== "ชำรุด"? Colors.red.shade300:Colors.amberAccent,
              // leading: const Icon(Icons.report_problem,color: Colors.indigo),
              title: Text("การตรวจสอบปี " +
                  verify!.pk.verify.Years.toString() +
                  " : " +
                  verify!.Durable_status.toString(),style: TextStyle(fontWeight:FontWeight.w600,fontSize: 25)),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            verify2 == null
                ? Container()
                : ListTile(
              tileColor:    verify2!.Durable_status.toString()== "ดี"?  Colors.green.shade300 : verify2!.Durable_status.toString()== "ชำรุด"? Colors.red.shade300:Colors.amberAccent,
              // leading: const Icon(Icons.report_problem,color: Colors.indigo),
              title: Text("การตรวจสอบปี " +
                  verify2!.pk.verify.Years.toString() +
                  " : " +
                  verify2!.Durable_status.toString(),style: TextStyle(fontWeight:FontWeight.w600,fontSize: 25)),
            ),
            const Divider(
              height: 0.6,
              color: Colors.black87,
            ),
            verify3 == null
                ? Container()
                : ListTile(
              tileColor:    verify3!.Durable_status.toString()== "ดี"?  Colors.green.shade300 : verify3!.Durable_status.toString()== "ชำรุด"? Colors.red.shade300:Colors.amberAccent,
              // leading: const Icon(Icons.report_problem,color: Colors.indigo),
              title: Text("การตรวจสอบปี " +
                  verify3!.pk.verify.Years.toString() +
                  " : " +
                  verify3!.Durable_status.toString(),style: TextStyle(fontWeight:FontWeight.w600,fontSize: 25)),
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
