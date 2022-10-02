import 'dart:convert';
import 'dart:io';

import 'package:buddhist_datetime_dateformat_sns/buddhist_datetime_dateformat_sns.dart';
import 'package:curved_navigation_bar/curved_navigation_bar.dart';
import 'package:dropdown_button2/dropdown_button2.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:intl/intl.dart';
import 'package:logger/logger.dart';
import 'package:project_durable/model/InformRepair_model.dart';
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
import '../manager/verify_manager.dart';
import '../model/durable_model.dart';
import '../model/staff_model.dart';
import '../splash.dart';
import 'List_repairADMIN_page.dart';
import 'List_repair_page.dart';
import 'View_verifybymajor_page.dart';
import 'home_page.dart';
import 'login_page.dart';
import 'my_drawer_header.dart';
import 'package:http/http.dart' as http;
class Create_Maintenance_Page extends StatefulWidget {
  @override
  Create_Maintenance_PageState createState() => Create_Maintenance_PageState();
}

class Create_Maintenance_PageState extends State<Create_Maintenance_Page> {
  var log = Logger();
  bool isLoading = true;
  bool hide = true;

  String? codedurable;
  Durable? d;
  inform_repair? ir;
  String staff = "";
  String? Majorlogin;
  Staff? s;
  List<Verify>? v;
  String? verifyid;
  var noteController = TextEditingController();
  var moneyController = TextEditingController();
  var companyController = TextEditingController();
  var namedurableController = TextEditingController();
  var durablecodeController = TextEditingController();

  var detailrepairController = TextEditingController();
  String? dateinform ;

  DateTime now = DateTime.now();
  var formatter =  DateFormat.yMMMMd();
  List<PlatformFile>? _files;
  File? image;
  File? _image;
  void uploadfile() async{
    var  url= Uri.parse(Strings.url + Strings.url_uploadimage);
    var request = http.MultipartRequest('Post',url);
    if(_files ==[]){
      request.files.add(await http.MultipartFile.fromPath('file', _image!.path.toString()));
    }else{
      request.files.add(await http.MultipartFile.fromPath('file',_image!.path.toString())) ;
    }


    request.files.add(await http.MultipartFile.fromString('duralbe_code', durablecodeController.text));
    var response = await request.send();

    final res = await http.Response.fromStream(response);
    print(res);
    if(response.statusCode == 200){
      print('Uploaded ...');
    }
    else{
      print('Something went wrong');
    }
  }


  void _openFileExplorer()async{
    _files = (await FilePicker.platform.pickFiles(
        type: FileType.any,
        allowMultiple: false,
        allowedExtensions: null
    ))!.files;

    setState(() {
      _image = File(_files!.first.path.toString());
    });
    print("เลือกรูป:+++++ :"+_files!.first.path.toString());
  }

  Future pickImageCamera() async {
    try {
      final image = await ImagePicker().pickImage(source: ImageSource.camera);
      if (image == null) return;
      _image = File(image.path);
      setState(() => this.image = _image);

      print("กล้อง:+++++ :"+image.path.toString());
    } on PlatformException catch (e) {
      log.e("Failed to pick image: $e");
    }
  }

  void findUser() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    durable_manager dm = durable_manager();
    verify_manager vm = verify_manager();
    codedurable = preferences.getString('durable_code')!;
    verifyid = preferences.getString('verifyid')!;

    log.e(verifyid!);
    staff = preferences.getString('Staff')!;
    Map<String, dynamic> map = jsonDecode(staff);
    s = Staff.fromJson(map);
    inform_manager irm = inform_manager();

    vm.listyears().then((value) => {
      v = value,
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


    dm.getdurablebyCode(codedurable.toString()).then((value) => {
      d = value,
      setState(() {
        isLoading = false;
      }),
    });


    irm.getinform_repairbyID(codedurable.toString()).then((value) => {
      ir = value,
      noteController.text = ir!.details.toString(),
      selectedValuestatus = 'ดี',
      namedurableController.text =  ir!.durable.Durable_name.toString(),
      durablecodeController.text = ir!.durable.Durable_code.toString(),
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
  AlertConfirm(String title) async {
    Alert(
        context: context,
        type: AlertType.info,
        title: "แจ้งเตือน",
        desc: title,
        buttons: [
          DialogButton(
            child: const Text("ยืนยัน",
                style: TextStyle(color: Colors.white, fontSize: 20)),
            onPressed: () {
              checkInsertverifyform();
            },
            width: 130,
          ),
          DialogButton(
            child: const Text("ยกเลิก",
                style: TextStyle(color: Colors.white, fontSize: 20)),
            onPressed: () {
              Navigator.pop(context);
            },
            width: 130,
          ),
        ]).show();
  }
  Alert_error(String alert) async {
    Alert(
        context: context,
        type: AlertType.error,
        title: "แจ้งเตือน",
        desc: alert,
        buttons: [
          DialogButton(
            child: const Text("ยืนยัน",
                style: TextStyle(color: Colors.white, fontSize: 20)),
            onPressed: () {
              Navigator.pop(context);
            },
            width: 130,
          ),
        ]).show();
  }
  Alert_suc(String alert) async {
    Alert(
        context: context,
        type: AlertType.success,
        title: "แจ้งเตือน",
        desc: alert,
        buttons: [
          DialogButton(
            child: const Text("ยืนยัน",
                style: TextStyle(color: Colors.white, fontSize: 20)),
            onPressed: () {
              Navigator.of(context)
                  .push(MaterialPageRoute(builder: (context) => List_repairadmin_page()));
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
  }

  final List<String> statusdurable = [
    'ดี',
    'ชำรุด',
    'แทงจำหน่าย',
  ];
  String? selectedValuestatus ;
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
   // String durableimg = d == null ? "" : d!.Durable_image.toString();
    if (d!.Durable_image.toString() != "-") {
      img =  Strings.url+"/file/durable_image/" + d!.Durable_image.toString();
     // img =  "http://www.itsci.mju.ac.th/DurableWebservices/file/durable_image/" + durableimg;
    } else {
      img =
      "https://w7.pngwing.com/pngs/29/173/png-transparent-null-pointer-symbol-computer-icons-pi-miscellaneous-angle-trademark.png";
    }
    var showDate;
     ir==null? "":  showDate = formatter.formatInBuddhistCalendarThai(ir!.dateinform);;


    return GestureDetector(
      onTap: () => FocusScope.of(context).unfocus(),
      child: Scaffold(
        appBar: AppBar(
          title: const Text('ระบบจัดการครุภัณฑ์'),
          leading: IconButton(
            onPressed: () {
              if (Majorlogin =="IT") {
                Navigator.of(context).push(
                    MaterialPageRoute(builder: (context) => List_repair_page() ));
              }
              if (Majorlogin == "SCI") {
                Navigator.of(context).push(
                    MaterialPageRoute(builder: (context) => List_repairadmin_page() ));
              }
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
        resizeToAvoidBottomInset: true,
        body: Builder(
          builder: (BuildContext context) {
            return Center(
              child: SingleChildScrollView(
                child: Container(
                  margin: EdgeInsets.only(bottom: 30),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      SizedBox(height: 20),
                      Card(
                        elevation: 10,
                        margin: EdgeInsets.symmetric(horizontal: 20),
                        child: Padding(
                          padding: const EdgeInsets.all(16.0),
                          child: Column(
                            children: [
                              Text("บันทึกการซ่อมบำรุง",
                                  style: TextStyle(
                                      fontSize: 22, fontWeight: FontWeight.bold)),
                              Image.network(img!, width: 300, height: 200),
                              SizedBox(height: 10),
                              Column(
                                children: [
                                  Row(children: [
                                    Text("ข้อมูลการส่งซ่อม",
                                        style: TextStyle(
                                            fontSize: 22, fontWeight: FontWeight.bold)),
                                  ]
                                  ),
                                  SizedBox(height: 10),
                                  Row(
                                    children: [
                                      Column(
                                        children: const [
                                          Text("ชื่อครุภัณฑ์ :",
                                              style: TextStyle(fontSize: 16,fontWeight: FontWeight.bold)),
                                        ],
                                      ),
                                      const SizedBox(width: 5),
                                      Text(namedurableController.text,
                                          style: TextStyle(fontSize: 16)),
                                    ],
                                  ),
                                  SizedBox(height: 5),
                                  Row(
                                    children: [
                                      Column(
                                        children: const [
                                          Text("รหัสครุภัณฑ์ :",
                                              style: TextStyle(fontSize: 16,fontWeight: FontWeight.bold)),
                                        ],
                                      ),
                                      const SizedBox(width: 5),
                                      Text(durablecodeController.text,
                                          style: TextStyle(fontSize: 16)),
                                    ],
                                  ),
                                  SizedBox(height: 5),
                                  Row(
                                    children: [
                                      Column(
                                        children: const [
                                          Text("รายละเอียดการส่งซ่อม :",
                                              style: TextStyle(fontSize: 16,fontWeight: FontWeight.bold)),
                                        ],
                                      ),
                                      const SizedBox(width: 5),
                                      Text(noteController.text,
                                          style: TextStyle(fontSize: 16)),
                                    ],
                                  ),
                                  SizedBox(height: 5),
                                  Row(
                                    children: [
                                      Column(
                                        children: const [
                                          Text("วันที่แจ้งซ่อม :",
                                              style: TextStyle(fontSize: 16,fontWeight: FontWeight.bold)),
                                        ],
                                      ),
                                      const SizedBox(width: 5),
                                      Text(showDate,
                                          style: TextStyle(fontSize: 16)),
                                    ],
                                  ),
                                  SizedBox(height: 20),
                                  Row(children: [
                                    Text("ข้อมูลการซ่อม",
                                        style: TextStyle(
                                            fontSize: 22, fontWeight: FontWeight.bold)),
                                  ]

                                  ),
                                  SizedBox(height: 5),
                                  Row(
                                    children: [
                                      Column(
                                        children: [
                                          Text("สถานะ :",
                                              style: TextStyle(fontSize: 16)),
                                        ],
                                      ),
                                      SizedBox(width: 5),
                                      Column(
                                        children: [
                                          DropdownButtonHideUnderline(
                                            child: DropdownButton2<String>(
                                              isExpanded: true,
                                              hint: Row(
                                                children: const [
                                                  Expanded(
                                                    child: Text(
                                                      'ดี',
                                                      style: TextStyle(
                                                        fontSize: 14,
                                                        fontWeight:
                                                        FontWeight.bold,
                                                        color: Colors.black,
                                                      ),
                                                      overflow:
                                                      TextOverflow.ellipsis,
                                                    ),
                                                  ),
                                                ],
                                              ),
                                              value: selectedValuestatus,
                                              icon: const Icon(
                                                  Icons.keyboard_arrow_down),
                                              items: statusdurable
                                                  .map((item) =>
                                                  DropdownMenuItem<String>(
                                                    value: item,
                                                    child: Text(
                                                      item,
                                                      style: const TextStyle(
                                                        fontSize: 14,
                                                      ),
                                                    ),
                                                  ))
                                                  .toList(),
                                              onChanged: (String? newValue) {
                                                setState(() {
                                                  selectedValuestatus = newValue!;
                                                });
                                              },
                                              buttonHeight: 40,
                                              buttonWidth: 160,
                                              buttonPadding:
                                              const EdgeInsets.only(
                                                  left: 14, right: 14),
                                              buttonDecoration: BoxDecoration(
                                                borderRadius:
                                                BorderRadius.circular(14),
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
                                                borderRadius:
                                                BorderRadius.circular(14),
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
                                      )
                                    ],
                                  ),
                                  SizedBox(height: 20),
                                  Row(
                                    children: [
                                      Expanded(
                                        child: Container(
                                          child: SizedBox(
                                            width: 50,
                                            height: 80,
                                            child: TextFormField(
                                              decoration: InputDecoration(
                                                border: OutlineInputBorder(),
                                                label:  Text("รายละเอียดการซ่อม",
                                                    style: TextStyle(
                                                        fontSize: 22, fontWeight: FontWeight.bold)),
                                                hintText: "กรุณากรอกรายละเอียดการซ่อม",
                                              ),
                                              controller: detailrepairController,
                                              keyboardType:
                                              TextInputType.text,
                                              maxLines: null,
                                            ),
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                  SizedBox(height: 10),
                                  Row(
                                    children: [
                                      Expanded(
                                        child: Container(
                                          child: SizedBox(
                                            width: 50,
                                            height: 80,
                                            child: TextFormField(
                                              decoration: InputDecoration(
                                                border: OutlineInputBorder(),
                                                label:  Text("จำนวนเงิน",
                                                    style: TextStyle(
                                                        fontSize: 22, fontWeight: FontWeight.bold)),
                                                hintText: "กรุณากรอกจำนวนเงิน",
                                              ),
                                              controller: moneyController,
                                              keyboardType:
                                              TextInputType.number,
                                              maxLines: null,
                                            ),
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                  SizedBox(height: 10),
                                  Row(
                                    children: [
                                      Expanded(
                                        child: Container(
                                          child: SizedBox(
                                            width: 50,
                                            height: 80,
                                            child: TextFormField(
                                              decoration: InputDecoration(
                                                border: OutlineInputBorder(),
                                                label:  Text("บริษัทที่เข้ารับการซ่อม",
                                                    style: TextStyle(
                                                        fontSize: 22, fontWeight: FontWeight.bold)),
                                                hintText: "กรุณากรอกบริษัทที่เข้ารับการซ่อม",
                                              ),
                                              controller: companyController,
                                              keyboardType:
                                              TextInputType.text,
                                              maxLines: null,
                                            ),
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),

                                  Row(
                                    children: [
                                      Column(
                                        children: const [
                                          Text("รูปภาพ :",
                                              style: TextStyle(fontSize: 16,fontWeight: FontWeight.bold)),
                                        ],
                                      ),
                                      const SizedBox(width: 20),
                                      Column(
                                        children: [
                                          Row(
                                            children: [
                                              SizedBox(width: 20),
                                              MaterialButton(
                                                onPressed: () {
                                                  showDialog(
                                                      context: context,
                                                      builder:
                                                          (BuildContext context) {
                                                        return AlertDialog(
                                                          title: Text(
                                                              "เลือกรายการอัปโหลด",
                                                              style: TextStyle(
                                                                  fontWeight:
                                                                  FontWeight
                                                                      .bold,
                                                                  color: Colors
                                                                      .indigo)),
                                                          content: Container(
                                                            height: 150.0,
                                                            width: 200.0,
                                                            child: Column(
                                                              mainAxisAlignment:
                                                              MainAxisAlignment
                                                                  .start,
                                                              children: [
                                                                Container(
                                                                  child: InkWell(
                                                                    onTap: () {
                                                                      pickImageCamera();
                                                                      Navigator.of(
                                                                          context)
                                                                          .pop(
                                                                          false);
                                                                    },
                                                                    splashColor:
                                                                    Colors
                                                                        .blueAccent,
                                                                    child:
                                                                    Padding(
                                                                      padding: const EdgeInsets
                                                                          .all(
                                                                          16.0),
                                                                      child: Row(
                                                                        children: [
                                                                          Icon(
                                                                              Icons
                                                                                  .camera,
                                                                              color:
                                                                              Colors.indigo),
                                                                          SizedBox(
                                                                              width:
                                                                              10),
                                                                          Text(
                                                                              "กล้อง")
                                                                        ],
                                                                      ),
                                                                    ),
                                                                  ),
                                                                ),
                                                                SizedBox(
                                                                    height: 10),
                                                                Container(
                                                                  child: InkWell(
                                                                    onTap: () {
                                                                      _openFileExplorer();
                                                                      Navigator.of(
                                                                          context)
                                                                          .pop(
                                                                          false);
                                                                    },
                                                                    splashColor:
                                                                    Colors
                                                                        .blueAccent,
                                                                    child:
                                                                    Padding(
                                                                      padding: const EdgeInsets
                                                                          .all(
                                                                          16.0),
                                                                      child: Row(
                                                                        children: [
                                                                          Icon(
                                                                              Icons
                                                                                  .image,
                                                                              color:
                                                                              Colors.indigo),
                                                                          SizedBox(
                                                                              width:
                                                                              10),
                                                                          Text(
                                                                              "แกลลอรี่")
                                                                        ],
                                                                      ),
                                                                    ),
                                                                  ),
                                                                ),
                                                              ],
                                                            ),
                                                          ),
                                                        );
                                                      });
                                                },
                                                child: Text("อัปโหลด",
                                                    style: TextStyle(
                                                        color: Colors.white)),
                                                color: Colors.blueAccent,
                                              )
                                            ],
                                          ),
                                        ],
                                      ),
                                    ],
                                  ),
                                  const SizedBox(width: 5),
                                  _files != null
                                      ?  _image!=null? Image.file(_image!,
                                      width: 160,
                                      height: 160,
                                      fit: BoxFit.cover):Text(""):
                                  _image!=null? Image.file(_image!,
                                      width: 160,
                                      height: 160,
                                      fit: BoxFit.cover):Text(""),

                                  Row(
                                    children: [
                                      Column(
                                        children: const [
                                          Text("ผู้ทำรายการ :",
                                              style: TextStyle(fontSize: 16,fontWeight: FontWeight.bold)),
                                        ],
                                      ),
                                      const SizedBox(width: 5),
                                      Text(s!.Staff_status,
                                          style: TextStyle(fontSize: 16)),
                                    ],
                                  ),
                                ],
                              ),
                              const SizedBox(
                                height: 20,
                              ),
                              ElevatedButton(
                                  onPressed: ()  {
                                    AlertConfirm("ท่านยืนยันที่จะบันทึกข้อมูลนี้หรือไม่ ?");
                                  },
                                  child: const Text("บันทึกข้อมูล")),
                            ],
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
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
      ),
    );
  }

  Future<void> checkInsertverifyform()  async {
    String? result;
    inform_manager vm = inform_manager();
    Repairdurable_manager rdm= Repairdurable_manager();
    if (detailrepairController.text == "") {
      Alert_error("กรุณากรอก รายละเอียดการซ่อม!");
    }else if(moneyController.text =="") {
      Alert_error("กรุณากรอก จำนวนเงิน!");
    }
    else if(companyController.text =="") {
      Alert_error("กรุณากรอก บริษัทที่เข้ารับการซ่อม!");
    }
    else if(_image == null){
      Alert_error("กรุณาอัปโหลดรูปภาพ!");
    }
    else if (result.toString() != "") {
      /* String date =  ir!.dateinform.year.toString()+"-"+ ir!.dateinform.month.toString()+"-"+ir!.dateinform.dayOfMonth.toString()+" "
          +ir!.dateinform.hourOfDay.toString()+":"+ir!.dateinform.minute.toString()+":"+ir!.dateinform.second.toString();
      log.e(date);*/

      result = await rdm.insertrepairdurable(companyController.text, moneyController.text, detailrepairController.text,  selectedValuestatus!,durablecodeController.text, verifyid!);
      uploadfile();
      Alert_suc("บันทึกข้อมูลสำเร็จ");
      /* Scaffold.of(context).showSnackBar(const SnackBar(
        content: Text('บันทึกข้อมูลสำเร็จ !'),
      ));*/
    } else {
      Scaffold.of(context).showSnackBar(const SnackBar(
        content: Text('เกิดข้อผิดพลาดลองใหม่อีกครั้ง !'),
      ));
    }
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
