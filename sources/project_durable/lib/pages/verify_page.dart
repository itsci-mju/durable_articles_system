import 'dart:convert';

import 'dart:io';
import 'package:path_provider/path_provider.dart';
import 'package:curved_navigation_bar/curved_navigation_bar.dart';
import 'package:dropdown_button2/dropdown_button2.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:intl/intl.dart';
import 'package:logger/logger.dart';
import 'package:project_durable/model/veify_model.dart';
import 'package:project_durable/pages/scan_page.dart';
import 'package:project_durable/pages/viewdurable.dart';
import 'package:project_durable/storage_service.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:image_picker/image_picker.dart';
import '../Strings.dart';
import '../manager/durable_manager.dart';
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
class Verify_Page extends StatefulWidget {
  @override
  _Verify_PageState createState() => _Verify_PageState();
}

class _Verify_PageState extends State<Verify_Page> {
  var log = Logger();
  bool isLoading = true;
  bool hide = true;
  bool? isVisibleupload ;
  var emailController = TextEditingController();
  var passController = TextEditingController();
  String? codedurable;
  String? Majorlogin;
  Durable? d;
  String staff = "";
  Staff? s;
  List<Verify>? v;
  var noteController = TextEditingController();
  var durablename_controller = TextEditingController();
  var durablecode_controller = TextEditingController();
  DateTime now = DateTime.now();

  String? imagepathname;
  String? selectedValuestatus;
  String? selectedValueyears;
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


  void _openFileExplorer()async{
    _files = (await FilePicker.platform.pickFiles(
        type: FileType.any,
        allowMultiple: false,
        allowedExtensions: null
    ))!.files;

    setState(() {
      _image = File(_files!.first.path.toString());
    });
    print("????????????????????????:+++++ :"+_files!.first.path.toString());
  }

  Future pickImageCamera() async {
    try {
      final image = await ImagePicker().pickImage(source: ImageSource.camera);
      if (image == null) return;
       _image = File(image.path);
      imagepathname = image.name.toString();
      setState(() => this.image = _image);

      print("???????????????:+++++ :"+image.path.toString());
    } on PlatformException catch (e) {
      log.e("Failed to pick image: $e");
    }
  }


  void findUser() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    durable_manager dm = durable_manager();
    verify_manager vm = verify_manager();
    codedurable = preferences.getString('durable_code')!;
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

    vm.listyears().then((value) => {
          v = value,
          setState(() {
            isLoading = false;
          }),
        });
    if(now.month >= 10 && now.month <= 12){
      selectedValueyears = (now.year + 544).toString();
    }else {
      selectedValueyears = (now.year + 543).toString();
    }
    dm.getdurablebyCode(codedurable.toString()).then((value) => {
          d = value,
      durablename_controller.text = d!.Durable_name.toString(),
      durablecode_controller.text = d!.Durable_code.toString(),

      selectedValuestatus= "??????",
          setState(() {
            isLoading = false;
          }),
        });
  }
  AlertConfirm(String title) async {
    Alert(
        context: context,
        type: AlertType.info,
        title: "???????????????????????????",
        desc: title,
        buttons: [
          DialogButton(
            child: const Text("??????????????????",
                style: TextStyle(color: Colors.white, fontSize: 20)),
            onPressed: () {
              checkInsertverifyform();
            },
            width: 130,
          ),
          DialogButton(
            child: const Text("??????????????????",
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
  editinform_suc(String alert) async {
    Alert(
        context: context,
        type: AlertType.success,
        title: "???????????????????????????",
        desc: alert,
        buttons: [
          DialogButton(
            child: const Text("??????????????????",
                style: TextStyle(color: Colors.white, fontSize: 20)),
            onPressed: () {
              Navigator.of(context)
                  .push(MaterialPageRoute(builder: (context) => Viewdurablepage()));
            },
            width: 130,
          ),
        ]).show();
  }
  editinform_error(String alert) async {
    Alert(
        context: context,
        type: AlertType.error,
        title: "???????????????????????????",
        desc: alert,
        buttons: [
          DialogButton(
            child: const Text("??????????????????",
                style: TextStyle(color: Colors.white, fontSize: 20)),
            onPressed: () {
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
    setState(){
      print('refreshing');
    }
    imageCache!.clear();
    imageCache!.clearLiveImages();
    findUser();
  }

  final List<String> statusdurable = [
    '??????',
    '???????????????',
    '??????????????????????????????',
  ];

  @override
  Widget build(BuildContext context) {
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
    String durableimg = d == null ? "" : d!.Durable_image.toString();
    if (durableimg != "-") {
      img =     Strings.url+"/file/durable_image/" +
              durableimg;
    /*  img =
          "http://www.itsci.mju.ac.th/DurableWebservices/file/durable_image/" +
              durableimg;*/
    } else {
      img =
          "https://w7.pngwing.com/pngs/29/173/png-transparent-null-pointer-symbol-computer-icons-pi-miscellaneous-angle-trademark.png";
    }
    return GestureDetector(
      onTap: () => FocusScope.of(context).unfocus(),
      child: Scaffold(
        appBar: AppBar(
          title: const Text('??????????????????????????????????????????????????????',
              style: TextStyle(fontWeight: FontWeight.bold , fontSize: 25)),
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
                              SizedBox(width: 5),
                             Image.network(img!, width: 300, height: 300),
                              SizedBox(width: 5),
                              Column(
                                children: [
                                  SizedBox(height: 20),
                                  Row(
                                    children: [
                                      Text("??????????????????????????????????????????????????????????????????",
                                          style: TextStyle(
                                              fontSize: 27, fontWeight: FontWeight.bold)),
                                    ],
                                  ),
                                  SizedBox(height: 20),
                                  Row(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      Text("???????????????????????????????????? : ", style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold)),
                                      Flexible(child: Text(durablename_controller.text, style: TextStyle(fontSize: 25,fontWeight: FontWeight.w300))),
                                    ],
                                  ),
                                  SizedBox(height: 10),
                                  Row(
                                    children: [
                                      Text("???????????????????????????????????? : ", style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold)),
                                      Flexible(child: Text(durablecode_controller.text, style: TextStyle(fontSize: 25,fontWeight: FontWeight.w300))),
                                    ],
                                  ),
                                  SizedBox(height: 10),
                                  Row(
                                    children: [
                                      Text("???????????? : ", style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold)),
                                      Flexible(child: Text(d!.room.Room_number.toString(), style: TextStyle(fontSize: 25,fontWeight: FontWeight.w300))),
                                    ],
                                  ),
                                  SizedBox(height: 10),
                                  Row(
                                    children: [
                                      Column(
                                        children: [
                                          Text("?????????????????????????????? : ", style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold)),
                                        ],
                                      ),
                                      Column(
                                        children: [
                                          Text(selectedValueyears!, style: TextStyle(fontSize: 25,fontWeight: FontWeight.w300)),
                                        ],
                                      ),
                                    ],
                                  ),
                                  SizedBox(height: 10),
                                  Row(
                                    children: [
                                      Column(
                                        children: [
                                          Text("??????????????? :", style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold)),
                                        ],
                                      ),
                                    ],
                                  ),
                                  Row(
                                    children: [
                                      Column(
                                        children: [
                                          DropdownButtonHideUnderline(
                                            child: DropdownButton2<String>(
                                              isExpanded: true,
                                              hint: Row(
                                                children: const [
                                                  Expanded(
                                                    child: Text('??????',
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
                                                            fontSize: 20,
                                                          ),
                                                        ),
                                                      ))
                                                  .toList(),
                                              onChanged: (String? newValue) {


                                                setState(() {
                                                  selectedValuestatus = newValue!;
                                                });
                                               /* if(selectedValuestatus != "??????"){
                                                  isVisibleupload = true;
                                                }else{
                                                  isVisibleupload = false;
                                                }*/
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
                                  SizedBox(height: 10),
                                  Row(
                                    children: [
                                      Expanded(
                                        child: Container(
                                          child: SizedBox(
                                            width: 50,
                                            height: 80,
                                            child: TextFormField(
                                              style: TextStyle(fontSize:25),
                                              decoration: InputDecoration(
                                                border: OutlineInputBorder(),
                                                label:  Text("??????????????????????????????", style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold)),
                                                hintText: "?????????????????????????????????????????????????????????",
                                              ),
                                              controller: noteController,
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
                                  SizedBox(height: 10),
                                  Visibility(
                                    visible: isVisibleupload =true,
                                    child: Row(
                                      children: [
                                        Column(
                                          children: const [
                                            Text("?????????????????? :", style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold)),
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
                                                                "??????????????????????????????????????????????????????", style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold,
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
                                                                                "???????????????", style: TextStyle(fontSize: 25,fontWeight: FontWeight.w300))
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
                                                                                "????????????????????????", style: TextStyle(fontSize: 25,fontWeight: FontWeight.w300))
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
                                                  child: Text("?????????????????????", style: TextStyle(fontSize: 25,fontWeight: FontWeight.w300,color: Colors.white)),
                                                  color: Colors.blueAccent,
                                                )
                                              ],
                                            ),
                                          ],
                                        ),
                                      ],
                                    ),
                                  ),
                                  const SizedBox(height: 10),
                                  _files != null
                                      ?  _image!=null? Column(
                                    children: [
                                      Row(
                                        crossAxisAlignment: CrossAxisAlignment.start,
                                        children: [
                                          Text("????????????????????? : ", style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold,color: Colors.red)),
                                          Flexible(child: Text("**???????????????????????????????????????????????????????????????????????????????????????????????????**", style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold,color: Colors.red))),
                                        ],
                                      ),
                                      SizedBox(height: 10),
                                      Row(
                                        mainAxisAlignment: MainAxisAlignment.center,
                                        crossAxisAlignment: CrossAxisAlignment.center,
                                        children: [
                                          Image.file(_image!,
                                              width: 160,
                                              height: 160,
                                              fit: BoxFit.cover),
                                        ],
                                      ),
                                    ],
                                  ):Text(""):
                                  _image!=null? Image.file(_image!,
                                      width: 160,
                                      height: 160,
                                      fit: BoxFit.cover):Text(""),

                                ],
                              ),
                              const SizedBox(
                                height: 10,
                              ),
                              Row(
                                children: [
                                  Column(
                                    children: [
                                      Text("????????????????????????????????? : ", style: TextStyle(fontSize: 25,fontWeight: FontWeight.bold)),
                                    ],
                                  ),
                                  Column(
                                    children: [
                                      s == null? Text(""):Text(s!.Staff_name+"  "+s!.Staff_lastname, style: TextStyle(fontSize: 25,fontWeight: FontWeight.w300)),
                                    ],
                                  ),
                                ],
                              ),
                             /* Row(
                                children: [

                                  ElevatedButton(onPressed: uploadfile, child: Text('?????????????????????')),
                                ],
                              ),*/
                              const SizedBox(
                                height: 20,
                              ),
                              ElevatedButton(

                                  onPressed: () async {
                                    AlertConfirm("??????????????????????????????????????????????????????????????????????????????????????? ?");
                                  },
                                  style: ButtonStyle(
                                    backgroundColor:  MaterialStateProperty.all(Colors.blueAccent),
                                  ),
                                  child: const Text("????????????????????????????????????", style: TextStyle(fontSize: 25))),
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

  Future<void> checkInsertverifyform() async {
    String? result;
    final Storage storage = Storage();
    verify_manager vm = verify_manager();
     if (selectedValuestatus.toString() == "null") {
    editinform_error('????????????????????????????????????????????? !');
    }
    else if ((selectedValuestatus.toString() == "???????????????" ||selectedValuestatus.toString() == "??????????????????????????????" )&& noteController.text== "") {
      editinform_error('????????????????????????????????????????????????????????? !');
    }
     else if ((selectedValuestatus.toString() == "???????????????" ||selectedValuestatus.toString() == "??????????????????????????????" )&& _image == null) {
       editinform_error('??????????????????????????????????????????????????????????????? !');
     }
    else if (result.toString() != "") {
     if (noteController.text== ""){
      setState(() {
        noteController.text = "-";
      });
    }
      result = await vm.insertverifyForm(
          selectedValuestatus.toString(),
          now.toString(),
          noteController.text,
          d!.Durable_code+".jpg",
          s!.id_staff.toString(),
          selectedValueyears.toString(),
          d!.Durable_code.toString());
          uploadfile();
          editinform_suc('?????????????????????????????????????????????????????? !');

    } else {
      editinform_error('??????????????????????????????????????????????????????????????????????????????????????? !');
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

}
