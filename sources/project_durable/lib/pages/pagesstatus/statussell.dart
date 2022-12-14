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


import 'package:http/http.dart' as http;


import 'package:dropdown_button2/dropdown_button2.dart';

import '../../Strings.dart';
import '../../manager/verify_manager.dart';
import '../../model/staff_model.dart';
import '../../model/veify_model.dart';
import '../../model/verifydurable_model.dart';
import '../../splash.dart';
import '../List_repairADMIN_page.dart';
import '../List_repair_page.dart';
import '../View_verifybymajor_page.dart';
import '../home_page.dart';
import '../login_page.dart';
import '../my_drawer_header.dart';


class StatusSellPage extends StatefulWidget {
  @override
  _StatusSellState createState() => _StatusSellState();
}

class _StatusSellState extends State<StatusSellPage> {
  bool isLoading = true;
  String nameUser = "";
  String staff = "";
  Staff? s;
  List<VerifyDurable>? listverifystatus1;
  List<Room>? r;
  var log = Logger();
  String? selectedValue;
  List<Verify>? v;
  bool isVisible = true;
  String? year;
  String? majorid;
  String? Majorlogin ;
  Future<void> getFirstRoom() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();

    staff = preferences.getString('Staff')!;
    Map<String, dynamic> map = jsonDecode(staff);
    log.e(map.toString());
    s = Staff.fromJson(map);
    setState(() {
      if (s!.major.ID_Major.toString() == "1") {
        selectedValue = '1101';
        Majorlogin = "IT";
        room_manager rm = room_manager();
      }
      if (s!.major.ID_Major.toString() == "999") {
        selectedValue = '-';
        Majorlogin = "SCI";
        room_manager rm = room_manager();
      }
    });
  }

  void findUser() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    login_manager lm = login_manager();

    verify_manager vm = verify_manager();
    nameUser = preferences.getString('username')!;
    staff = preferences.getString('Staff')!;
    year  = preferences.getString('selectyearstatus')!;
    majorid = preferences.getString('selectid_majorstatus')!;
    log.e(year);
    log.e(majorid);
    Map<String, dynamic> map = jsonDecode(staff);
    s = Staff.fromJson(map);

    vm.listverifystatus3(majorid!, year!)
        .then((value) =>
    {
      listverifystatus1 = value,
      setState(() {
        isLoading = false;
      }),
      log.e(listverifystatus1),
    });

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
        title: "???????????????????????????",
        desc: "???????????????????????????????????????????????????????????????????????????????????? ?",
        buttons: [
          DialogButton(
            child: const Text("??????????????????",
                style: TextStyle(color: Colors.white, fontSize: 20)),
            onPressed: () {
              logout();
            },
            width: 130,
          ),
          DialogButton(
            child: const Text("??????????????????",
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

  Future<bool> _onWillPop() async {
    return (await showDialog(
          context: context,
          builder: (context) => AlertDialog(
            title: new Text('??????????????????????????? !'),
            content: new Text('??????????????????????????????????????????????????? ?'),
            actions: <Widget>[
              TextButton(
                onPressed: () => Navigator.of(context).pop(false),
                child: new Text('??????????????????'),
              ),
              TextButton(
                onPressed: () => Navigator.of(context).pop(true),
                child: new Text('??????????????????'),
              ),
            ],
          ),
        )) ??
        false;
  }


  
  @override
  void initState() {
    super.initState();
    setState(){
      print('refreshing');
    }imageCache!.clear();
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
    // return d==null?CircularProgressIndicator(): Scaffold(
    return GestureDetector(
      onTap: () => FocusScope.of(context).unfocus(),
      child: Scaffold(
        resizeToAvoidBottomInset: true,
        appBar: AppBar(
          title: const Text('?????????????????????????????????????????? ?????????????????????????????????????????????',style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25),),
          leading: IconButton(
            onPressed: () {
              Navigator.of(context)
                  .push(MaterialPageRoute(builder: (context) => View_verifybymajor_page()));
            },
            icon: const Icon(Icons.keyboard_backspace),
          ),
          //  title: Text(s == null ? '??????????????????????????????????????????????????????' : '???????????????????????????'+ s!.staff_name),
          elevation: 0,
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

              const SizedBox(height: 5.0),
              Expanded(
                  child: buildDurable(listverifystatus1 == null ? [] : listverifystatus1!)),
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
      ),
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
                child: Text("??????????????????????????????????????????????????????????????????",style: TextStyle(color: Colors.black,fontSize: 25)),
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
                child: Text("??????????????????????????????????????????",style: TextStyle(color: Colors.black,fontSize: 25)),
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
    log.e("???????????????????????????????????????????????????");
  }

  Widget buildDurable(List<VerifyDurable> durables) => ListView.builder(
        itemCount: listverifystatus1 == null ? 0 : listverifystatus1!.length,
        itemBuilder: (context, index) {
          final durable = durables[index];
          String? img;
          if (durable.pk.durable.Durable_image.toString() != "-") {
            img = Strings.url+"/file/durable_image/" +  durable.pk.durable.Durable_image.toString();
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
                    backgroundImage: NetworkImage(img+"?v=1"),
                  ),

                  subtitle: SizedBox(
                    child: Column(
                      children: [
                        Row(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text("???????????????????????????????????? : ",style: TextStyle(fontSize:25,fontWeight: FontWeight.bold)),
                            Flexible(child: Text( durable.pk.durable.Durable_name,style: TextStyle(fontSize:25,color: Colors.indigo,fontWeight:  FontWeight.bold))),
                          ],
                        ),
                        Row(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text("???????????????????????????????????? : ",style: TextStyle(fontSize:25,fontWeight: FontWeight.bold)),
                            Flexible(child: Text(durable.pk.durable.Durable_code,style: TextStyle(fontSize:25,color: Colors.indigo,fontWeight:  FontWeight.bold))),
                          ],
                        ),
                        Row(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text("??????????????????????????????????????? : ",style: TextStyle(fontSize:25,fontWeight: FontWeight.bold)),
                            Flexible(child: Text(durable.Save_date.toString(),style: TextStyle(fontSize:25,color: Colors.indigo,fontWeight:  FontWeight.bold))),
                          ],
                        ),
                        Row(
                          children: [
                            const Text("???????????? : ",style: TextStyle(fontSize:25,fontWeight: FontWeight.bold)),
                            Text(durable.pk.durable.room.Room_number,style: TextStyle(fontSize:25,color: Colors.indigo,fontWeight:  FontWeight.bold)),
                          ],
                        ),
                        Row(
                          children: [
                            const Text("??????????????????: ",style: TextStyle(fontSize:25,fontWeight: FontWeight.bold)),
                            Text(durable.pk.durable.Responsible_person,style: TextStyle(fontSize:25,color: Colors.indigo,fontWeight:  FontWeight.bold)),
                          ],
                        ),
                        Row(
                          children: [
                            const Text("??????????????? : ",style: TextStyle(fontSize:25,fontWeight: FontWeight.bold)),
                            durable.Durable_status == "??????"
                                ? Text(durable.Durable_status,
                                style: TextStyle(
                                    fontSize:25,
                                    color: Colors.green,
                                    fontWeight: FontWeight.bold))
                                : durable.Durable_status == "???????????????"
                                ? Text(durable.Durable_status,
                                style: TextStyle(
                                    fontSize:25,
                                    color: Colors.red,
                                    fontWeight: FontWeight.bold))
                                : Text(durable.Durable_status,
                                style: TextStyle(
                                    fontSize:25,
                                    color: Colors.orange,
                                    fontWeight: FontWeight.bold)),
                          ],
                        ),
                        Row(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text("?????????????????????????????? : ",style: TextStyle(fontSize:25,fontWeight: FontWeight.bold)),
                            Flexible(child: Text(durable.note,style: TextStyle(fontSize:25,color: Colors.indigo,fontWeight:  FontWeight.bold))),
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
                    convertcode(durable.pk.durable.Durable_code.toString(),durable.pk.verify.Years.toString());
                  },
                ),
              ),
            ),
          );
        },
      );

  Future<void> convertcode(String code,String year) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    List<String> listcode = code.split(".png");
    String durable_code = listcode[0].replaceAll(':', '/');
    preferences.setString('durable_code', durable_code);
    preferences.setString('selectyear', year);

    Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => Viewdurablepage()));
  }



}
