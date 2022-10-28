import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';

import 'package:buddhist_datetime_dateformat_sns/buddhist_datetime_dateformat_sns.dart';
import 'package:curved_navigation_bar/curved_navigation_bar.dart';
import 'package:dropdown_button2/dropdown_button2.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:intl/intl.dart';
import 'package:logger/logger.dart';
import 'package:path_provider/path_provider.dart';
import 'package:project_durable/model/InformRepair_model.dart';
import 'package:project_durable/model/veify_model.dart';
import 'package:project_durable/pages/scan_page.dart';
import 'package:project_durable/pages/viewdurable.dart';
import 'package:project_durable/storage_service.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:image_picker/image_picker.dart';
import 'package:uri_to_file/uri_to_file.dart';
import '../Strings.dart';
import '../manager/durable_manager.dart';
import '../manager/inform_manager.dart';
import '../manager/login_manager.dart';
import '../manager/verify_manager.dart';
import '../manager/verifyinform_manager.dart';
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
class Verify_Repair_Page extends StatefulWidget {
  @override
  Verify_Repair_PageState createState() => Verify_Repair_PageState();
}

class Verify_Repair_PageState extends State<Verify_Repair_Page> {
  var log = Logger();
  bool isLoading = true;
  bool hide = true;
  var emailController = TextEditingController();
  var passController = TextEditingController();
  String? codedurable;
  String? informid;
  Durable? d;
  inform_repair? ir;
  String staff = "";
  String? Majorlogin;
  Staff? s;
  List<Verify>? v;
  var detailController = TextEditingController();
  var noteController = TextEditingController();
  var durablecode_controller = TextEditingController();
  final List<String> statusdurable = [
    'ส่งซ่อม',
    'ซ่อมเอง',
    'ไม่สามารถซ่อมได้',
    'ยกเลิกการแจ้งซ่อม',
  ];
  String? selectedValuestatus ;
  String? selectedValueyears;

  DateTime now = DateTime.now();
  var formatter =  DateFormat.yMMMMd();
  List<PlatformFile>? _files;
  File? image;
  File? _image;
  String? urlimg;

  Future<void> convertUriToFile() async {
    try {

      _image = await toFile(urlimg.toString()); // Converting uri to file
    } on UnsupportedError catch (e) {
      print(e.message); // Unsupported error for uri not supported
    } on IOException catch (e) {
      print(e); // IOException for system error
    } catch (e) {
      print(e); // General exception
    }
  }

  void uploadfile() async{
    var  url = Uri.parse(Strings.url + Strings.url_uploadimage2);
    var request = http.MultipartRequest('Post',url);
    var _urlimg = Uri.parse(urlimg.toString());
    convertUriToFile();
 /*   if(_files ==[]){
      request.files.add(await http.MultipartFile.fromPath('file', _image!.path.toString()));
    }else{
      request.files.add(await http.MultipartFile.fromPath('file',_image!.path.toString())) ;
    }*/


    request.files.add(await http.MultipartFile.fromString('duralbe_code', durablecode_controller.text));
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



  /*Future<File> saveImagePermanently(String imagePath)async {
    final directory = await getApplicationDocumentsDirectory();
    final name = basename(imagePath);
    final image = File('${directory.path}/$name');

    return File(imagePath).copy(image.path);
  }*/

  void findUser() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    durable_manager dm = durable_manager();
    verify_manager vm = verify_manager();
    codedurable = preferences.getString('durable_code')!;
    informid =  preferences.getString('id_informid')!;
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



    dm.getdurablebyCode(codedurable.toString()).then((value) => {
      d = value,
      setState(() {
        isLoading = false;
      }),
    });


    irm.getinform_repairbyID2(codedurable.toString(),informid!).then((value) => {
      ir = value,
      noteController.text = ir!.details.toString(),
      selectedValuestatus = "ส่งซ่อม",
      durablecode_controller.text = ir!.durable.Durable_code.toString(),
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
  AlertDelete(String title) async {
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
              checkdelete();
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
  alertverify_suc(String alert) async {
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
  alertverify_error(String alert) async {
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
  }


  @override
  Widget build(BuildContext context) {
    var showDate;
    var informtime;
    ir==null? "": showDate = formatter.formatInBuddhistCalendarThai(ir!.dateinform);
     ir==null? "": informtime = DateFormat('kk:mm').format(ir!.dateinform);

    final Storage storage = Storage();
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
    String? img2;
    String durableimg = d == null ? "" : d!.Durable_image.toString();
    if (durableimg != "-") {
      img = Strings.url+"/file/durable_image/" + durableimg;
      d == null ? "" : img2 =  Strings.url+"/file/inform_repair/" + d!.Durable_image.toString();
      log.e(img);
      setState(() {
        urlimg = img;
      });
     // img =  "http://www.itsci.mju.ac.th/DurableWebservices/file/durable_image/" +       durableimg;
    } else {
      img =
      "https://w7.pngwing.com/pngs/29/173/png-transparent-null-pointer-symbol-computer-icons-pi-miscellaneous-angle-trademark.png";
    }
    return GestureDetector(
      onTap: () => FocusScope.of(context).unfocus(),
      child: Scaffold(
        appBar: AppBar(
          title: const Text('ระบบจัดการครุภัณฑ์',style: TextStyle(fontWeight: FontWeight.bold,fontSize: 25)),
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

        resizeToAvoidBottomInset: false,
        body: Builder(
          builder: (BuildContext context) {
            return Center(
              child: SingleChildScrollView(
                child: Container(
                  margin: EdgeInsets.only(bottom: 30),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: [
                      SizedBox(height: 20),
                      Card(
                        elevation: 10,
                        margin: EdgeInsets.symmetric(horizontal: 20),
                        child: Padding(
                          padding: const EdgeInsets.all(16.0),
                          child: Column(
                            children: [
                              SizedBox(height: 10),
                              Column(
                                children: [
                                  SizedBox(height: 10),
                                  Text("รูปครุภัณฑ์ :",
                                      style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold,color: Colors.green)),
                                  SizedBox(height: 10),
                                  Column(
                                    children: [
                                      Image.network(img!, width: 250, height: 200),
                                    ],
                                  ),

                                  SizedBox(height: 10),
                                  Text("รูปครุภัณฑ์ชำรุดที่แจ้ง :",
                                      style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold,color: Colors.red)),
                                  SizedBox(height: 10),
                                  Column(
                                    children: [
                                      Image.network(img2==""?"":img2!, width: 250, height: 200),
                                    ],
                                  ),
                                  SizedBox(height: 10),
                                  Text("ตรวจสอบการแจ้งซ่อม",
                                      style: TextStyle(
                                          fontSize: 25, fontWeight: FontWeight.bold)),

                                  SizedBox(height: 10),
                                  Row(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      Text("ชื่อครุภัณฑ์ :",
                                          style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
                                      SizedBox(width: 5),
                                      Flexible(
                                        child: Text(
                                            ir == null
                                                ? ""
                                                : ir!.durable.Durable_name.toString(),
                                            style: TextStyle(fontSize: 25)),
                                      )
                                    ],
                                  ),
                                  SizedBox(height: 10),
                                  Row(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      Text("รหัสครุภัณฑ์ :",
                                          style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
                                      SizedBox(width: 5),
                                      Flexible(
                                        child: Text(
                                            ir == null
                                                ? ""
                                                : ir!.durable.Durable_code.toString(),
                                            style: TextStyle(fontSize: 25)),
                                      )
                                    ],
                                  ),
                                  SizedBox(height: 10),
                                  Row(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      Column(
                                        children: [
                                          Text("ห้อง :",
                                              style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
                                        ],
                                      ),
                                      SizedBox(width: 5),
                                      Column(
                                        children: [
                                          Text(
                                              ir == null
                                                  ? ""
                                                  : ir!.durable.room.Room_number.toString(),
                                              style: TextStyle(fontSize: 25)),
                                        ],
                                      )
                                    ],
                                  ),
                                  SizedBox(height: 10),
                                  Row(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      Text("อาการเสีย :",
                                          style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
                                      SizedBox(width: 5),
                                      Flexible(
                                        child: Text(noteController.text,
                                            style: TextStyle(fontSize: 25)),
                                      )
                                    ],
                                  ),
                                  SizedBox(height: 10),
                                  Row(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      Text("วันที่แจ้งซ่อม :",
                                          style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
                                      SizedBox(width: 5),
                                      Flexible(
                                        child: Text(
                                            ir == null
                                                ? ""
                                                : showDate+" " +informtime+" น." ,
                                            style: TextStyle(fontSize: 25)),
                                      )
                                    ],
                                  ),
                                  SizedBox(height: 10),
                                  Row(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      Text("ผู้แจ้ง :",
                                          style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
                                      SizedBox(width: 5),
                                      Flexible(
                                        child: Text(
                                            ir == null
                                                ? ""
                                                : ir!.staff.Staff_status.toString(),
                                            style: TextStyle(fontSize: 25)),
                                      )
                                    ],
                                  ),
                                  SizedBox(height: 10),
                                  Row(
                                    children: [
                                      Column(
                                        children: [
                                          Text("สถานะ :",
                                              style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
                                        ],
                                      ),
                                      SizedBox(width: 5),
                                      Column(
                                        children: [
                                          DropdownButtonHideUnderline(
                                            child: DropdownButton2<String>(
                                              isExpanded: true,
                                              hint: Row(
                                                children:  [
                                                  Expanded(
                                                    child: Text(
                                                      ir == null
                                                          ? "ส่งซ่อม"
                                                          : ir!.Informtype.toString(),
                                                      style: const TextStyle(
                                                        fontSize: 25,
                                                        color: Colors.black,
                                                      ),
                                                      overflow:
                                                      TextOverflow.ellipsis,
                                                    ),
                                                  ),
                                                ],
                                              ),
                                              value:  selectedValuestatus,
                                              icon: const Icon(
                                                  Icons.keyboard_arrow_down),
                                              items: statusdurable
                                                  .map((item) =>
                                                  DropdownMenuItem<String>(
                                                    value: item,
                                                    child: Text(
                                                      item,
                                                      style: const TextStyle(
                                                        fontSize: 25,
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
                                              buttonWidth: 180,
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
                                  SizedBox(height: 10),
                                  Row(
                                    children: [
                                      Column(
                                        children:  [
                                          selectedValuestatus == "ไม่สามารถซ่อมได้"? Text("เหตุผล :",
                                              style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)): selectedValuestatus == "ยกเลิกการแจ้งซ่อม"?  Text("เหตุผล :",
                                              style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)):Text("รายละเอียด :",
                                              style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
                                        ],
                                      ),
                                      const SizedBox(width: 5),
                                      Expanded(
                                        child: Container(
                                          child: SizedBox(
                                            child: TextFormField(
                                              style: TextStyle(fontSize: 25),
                                              decoration: InputDecoration(
                                                border: OutlineInputBorder(),
                                              ),
                                              controller: detailController,
                                              minLines: 2,
                                              maxLength: 1000,
                                              keyboardType:
                                              TextInputType.multiline,
                                              maxLines: null,
                                            ),
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                ],
                              ),
                             /* const SizedBox(
                                height: 10,
                              ),
                              Row(
                                children: [
                                  Column(
                                    children: [
                                      Text("คำเตือน : ", style: TextStyle(fontSize: 16,fontWeight: FontWeight.bold,color: Colors.red)),
                                    ],
                                  ),
                                  Column(
                                    children: [
                                      Text("**รูปจะไปแทนที่รูปเดิมที่อยู่ในระบบ**", style: TextStyle(fontSize: 16,fontWeight: FontWeight.bold,color: Colors.red)),
                                    ],
                                  ),
                                ],
                              ),*/
                              const SizedBox(
                                height: 10,
                              ),
                              Row(
                                  mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  ElevatedButton(
                                      onPressed: ()  {
                                        AlertConfirm("ท่านยืนยันที่จะบันทึกข้อมูลนี้ ?");
                                      },
                                      style: ButtonStyle(
                                        backgroundColor:  MaterialStateProperty.all(Colors.blueAccent),
                                      ),
                                      child: const Text("บันทึกข้อมูล",style: TextStyle(fontSize: 25))),
                                 /* SizedBox(width: 10),
                                  ElevatedButton(
                                      onPressed: ()  {
                                        AlertDelete("ท่านลบการแจ้งซ่อมนี้ ?");
                                      },
                                      style: ButtonStyle(
                                        backgroundColor:  MaterialStateProperty.all(Colors.red),
                                      ),
                                      child: const Text("ลบการแจ้งซ่อม")),*/


                                ],
                              ),

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
      ),
    );
  }

  Future<void> checkInsertverifyform()  async {
    String? result;
    verifyinform_manager vrm = verifyinform_manager();

    if (selectedValuestatus.toString() == "") {
      alertverify_error('กรุณาเลือกสถานะ !');
    }else if ((selectedValuestatus.toString() == "ไม่สามารถซ่อมได้" || selectedValuestatus.toString() == "ยกเลิกการแจ้งซ่อม" ) && detailController.text == "") {
        alertverify_error('กรุณากรอกเหตุผล !');
    }
    else if(selectedValuestatus.toString() != "ส่งซ่อม" && selectedValuestatus.toString() != "ซ่อมเอง" ){
      result = await vrm.insertverifyinForm(ir!.Informid.toString(),selectedValuestatus!,detailController.text);
      alertverify_suc('บันทึกข้อมูลสำเร็จ !');
    }
    else if (selectedValuestatus.toString() == "ส่งซ่อม" || selectedValuestatus.toString() == "ซ่อมเอง" ) {
      result = await vrm.insertverifyinForm(ir!.Informid.toString(),selectedValuestatus!,detailController.text);
     // uploadfile();
      alertverify_suc('บันทึกข้อมูลสำเร็จ !');
    }else {
      alertverify_error('เกิดข้อผิดพลาดลองใหม่อีกครั้ง !');
    }
  }
  Future<void> checkdelete()  async {
    inform_manager vrm = inform_manager();
    String result = await vrm.deleteinformrepair(ir!.Informid.toString(),ir!.durable.Durable_code.toString());
    log.e(result.toString());
    alertverify_suc('ลบข้อมูลสำเร็จ !');
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
