class Strings {
  //URL CONNECTION
  static const String url = "http://192.168.43.166:8080/DurableWebservices";
 //static const String url = "http://192.168.42.144:8080/DurableWebservices";
  //USER
  static const String url_login = "/applogin/user/login";
  static const String list_staff = "applogin/user/list";
  //STAFF
  static const String list_staffbyuser = "/applogin/staff/liststaffbyusername";
  //DURABLE
  static const String url_listdurable = "/appdurable/durable/listdurablebymajor";
  static const String url_listalldurable = "/appdurable/durable/listAlldurable";
  static const String get_durable = "/appdurable/durable/getdurable";
  //ROOM
  static const String url_listroombymajor = "/approom/listroombymajor";
  static const String url_listallroom = "/approom/listallroom";
  //VERIFY
  static const String url_addverifyform = "/appverify/verify/addform";
  static const String url_updateverifyform = "/appverify/verify/updateform";
  static const String url_listyears = "/appverify/verify/listyears";
  static const String url_listverify = "/appdurable/verify/listverifydurable";
  static const String url_listnotverify = "/appdurable/verify/listnotverifydurable";
  static const String url_listdurablenotverify_admin = "/appdurable/verify/listdurablenotverify_admin";
  static const String url_listdurable_verifyed_byadmin = "/appdurable/verify/listdurableverifyed_admin";
  static const String url_getverifyinverifydurable = "/appverify/verify/verifyinverifydurable";
  static const String url_getverifybydurablecode = "/appverify/VerifyDurable/getverifybydurablecode";
  static const String url_getverifybydurablecurrent = "/appverify/VerifyDurable/getverifybydurablecurrent";
  //INFORM REPAIR
  static const String url_addinformrepair = "/appinfromrepair/inform/addform";
  static const String url_listrepairform = "/appinfromrepair/informrepair/getlistformrepair";
  static const String url_listrepairformverifyed = "/appinfromrepair/informrepair/getlistformrepairverifyed";
  static const String url_listverifynotinmaintenance = "/appinfromrepair/informrepair/getlistverifynotinmaintenance";
  static const String url_lisAlltrepairform = "/appinfromrepair/informrepair/getAlllistformrepair";
  static const String url_getInform_repairbyID = "/appinfromrepair/inform_repair/getinform_repair";
  static const String url_editinformrepair = "/appinfromrepair/inform/update";
  static const String url_listinformnotinverify = "/appinfromrepair/informrepair/listformrepairNOTIN";
  static const String url_listinforminverify = "/appinfromrepair/informrepair/listformrepairIN";
  static const String url_listinforminverifynotinmaintenance = "/appinfromrepair/informrepair/listformrepairINnotinmaintenance";
  static const String url_getdurableininformrepair = "/appinfromrepair/informrepair/getdurableininformrepair";
  static const String url_getdurableininformrepair2 = "/appinfromrepair/informrepair/getdurableininformrepair2";
  static const String url_getdurableininformrepairnotrepair = "/appinfromrepair/informrepair/getdurableininformrepairnotrepair";
  static const String url_deleteinform_repair = "/appinfromrepair/inform_repair/deleteinform_repair";


  //Major
  static const String url_listAllmajor = "/appmajor/major/listmajor";
  //VERIFY INFORM
  static const String url_addverifyinform = "/appverifyinform/verifyinform/addform";
  //VERIFY BY MAJOR
  static const String url_countalldurable = "/appverify/verifybymajor/countalldurable";
  static const String url_countdurabled = "/appverify/verifybymajor/countdurabled";
  static const String url_countnotdurabled = "/appverify/verifybymajor/countnotdurabled";
  static const String url_countverifystatus1 = "/appverify/verifybymajor/countverifystatus";
  static const String url_listverifystatus = "/appverify/verifybymajor/listverifystatus";
  static const String url_countverifystatus2 = "/appverify/verifybymajor/countverifystatus2";
  static const String url_listverifystatus2 = "/appverify/verifybymajor/listverifystatus2";
  static const String url_countverifystatus3 = "/appverify/verifybymajor/countverifystatus3";
  static const String url_listverifystatus3 = "/appverify/verifybymajor/listverifystatus3";
  static const String url_countverifystatus4 = "/appverify/verifybymajor/countverifystatus4";
  static const String url_listverifystatus4 = "/appverify/verifybymajor/listverifystatus4";
  //REPAIRDURABLE
  static const String url_urlinsertrepairdurable = "/apprepairdurable/repairdurable/addrepairdurable";
  static const String url_urllistRepairComplete = "/apprepairdurable/repairdurable/listRepairComplete";
  static const String url_urllistRepairCompletemajorid = "/apprepairdurable/repairdurable/listRepairCompleteMajorid";
  static const String url_urlupdaterepairdurable = "/apprepairdurable/repairdurable/updaterepairdurable";
  static const String url_urlgetRepairdurable = "/apprepairdurable/repairdurable/getRepairdurable";
  static const String url_urllistRepairHistory = "/apprepairdurable/repairdurable/listRepairHistory";
  static const String url_urlgetRepairHistory = "/apprepairdurable/repairdurable/getRepairHistory";

  //UPLOADIMAGE
  static const String url_uploadimage= "/appverify/verify/uploadimage";
  static const String url_uploadimageinform= "/appinfromrepair/inform/uploadimageinform";
  static const String url_uploadimage2 = "/appverify/verify/uploadimage2";
}