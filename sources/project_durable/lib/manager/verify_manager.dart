import 'dart:convert';

import 'package:logger/logger.dart';
import 'package:project_durable/model/InformRepair_model.dart';
import 'package:project_durable/model/veify_model.dart';
import 'package:project_durable/model/verifydurable_model.dart';
import 'package:http/http.dart' as http;
import '../RsponseModel.dart';
import '../Strings.dart';
import '../model/repairdurable_model.dart';
import '../model/verifyInform_model.dart';

class verify_manager {
  verify_manager();

  Future<String> insertverifyForm(
      String durable_status,
      String save_date,
      String note,
      String picture_verify,
      String id_staff,
      String years,
      String durable_code) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_addverifyform),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'durable_status': durable_status,
        'save_date': save_date,
        'note': note,
        'picture_verify': picture_verify,
        'id_staff': id_staff,
        'years': years,
        'durable_code': durable_code,
      }),
    );

    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(
          jsonDecode(response.body));
      String result = responseModel.result.toString();
      var log = Logger();
      log.e(result);
      return result;
    } else {
      throw Exception('Failed to Insert');
    }
  }
  Future<String> updateverifyForm(
      String durable_status,
      String save_date,
      String note,
      String picture_verify,
      String id_staff,
      String years,
      String durable_code) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_updateverifyform),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'durable_status': durable_status,
        'save_date': save_date,
        'note': note,
        'picture_verify': picture_verify,
        'id_staff': id_staff,
        'years': years,
        'durable_code': durable_code,
      }),
    );

    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(
          jsonDecode(response.body));
      String result = responseModel.result.toString();
      var log = Logger();
      log.e(result);
      return result;
    } else {
      throw Exception('Failed to Insert');
    }
  }



  Future<List<Verify>> listyears() async {
    final response = await http.post(
      Uri.parse(Strings.url+Strings.url_listyears),
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{

      }),
    );
    var log = Logger();
    log.e(response.body);
    log.e("ลิสปีงบประมาณ"+response.body.toString());
    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<Verify> listverify= (responseModel.result as List).map((item) => Verify.fromJson(item)).toList();
      var log = Logger();
      log.e(listverify.toString());
      return listverify;
    } else {
      throw Exception('Failed to get listverify');
    }
  }
  Future<String> countdurable(String major_name) async {
    final response = await http.post(
      Uri.parse(Strings.url+Strings.url_countalldurable),
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_name': major_name,
      }),
    );
    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      String result = responseModel.result.toString();
      /* var log = Logger();
      log.e(response.body);*/
      return result;
    } else {
      throw Exception('Failed to get countalldurable');
    }
  }
  Future<String> countdurabled(String major_name,String year) async {
    final response = await http.post(
      Uri.parse(Strings.url+Strings.url_countdurabled),
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_name': major_name,
        'year': year,
      }),
    );
    var log = Logger();
    log.e(response.body);
    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      String result = responseModel.result.toString();
      /* var log = Logger();
      log.e(response.body);*/
      return result;
    } else {
      throw Exception('Failed to get countdurabled');
    }
  }
  Future<String> countnotdurabled(String major_name,String year) async {
    final response = await http.post(
      Uri.parse(Strings.url+Strings.url_countnotdurabled),
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_name': major_name,
        'year': year,
      }),
    );
    var log = Logger();
    log.e(response.body);
    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      String result = responseModel.result.toString();
      /* var log = Logger();
      log.e(response.body);*/
      return result;
    } else {
      throw Exception('Failed to get countnotdurabled');
    }
  }
  Future<String> countverifystatus(String major_name,String year) async {
    final response = await http.post(
      Uri.parse(Strings.url+Strings.url_countverifystatus1),
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_name': major_name,
        'year': year,
      }),
    );
    var log = Logger();
    log.e(response.body);
    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      String result = responseModel.result.toString();
      /* var log = Logger();
      log.e(response.body);*/
      return result;
    } else {
      throw Exception('Failed to get countnotdurabled');
    }
  }
  Future<List<VerifyDurable>> listverifystatus(String major_name,String year) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_listverifystatus),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_name': major_name,
        'year': year,
      }),
    );

    log.e(response.body);
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<VerifyDurable> listdurable = (responseModel.result as List).map((item) => VerifyDurable.fromJson(item)).toList();
      log.e(listdurable.toString());

      return listdurable;
    } else {
      throw Exception('Failed to get Listdurable');
    }
  }


  Future<String> countverifystatus2(String major_name,String year) async {
    final response = await http.post(
      Uri.parse(Strings.url+Strings.url_countverifystatus2),
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_name': major_name,
        'year': year,
      }),
    );
    var log = Logger();
    log.e(response.body);
    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      String result = responseModel.result.toString();
      /* var log = Logger();
      log.e(response.body);*/
      return result;
    } else {
      throw Exception('Failed to get countnotdurabled');
    }
  }
  Future<List<VerifyDurable>> listverifystatus2(String major_name,String year) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_listverifystatus2),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_name': major_name,
        'year': year,
      }),
    );

    log.e(response.body);
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<VerifyDurable> listdurable = (responseModel.result as List).map((item) => VerifyDurable.fromJson(item)).toList();
      log.e(listdurable.toString());

      return listdurable;
    } else {
      throw Exception('Failed to get Listdurable');
    }
  }
  Future<String> countverifystatus3(String major_name,String year) async {
    final response = await http.post(
      Uri.parse(Strings.url+Strings.url_countverifystatus3),
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_name': major_name,
        'year': year,
      }),
    );
    var log = Logger();
    log.e(response.body);
    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      String result = responseModel.result.toString();
      /* var log = Logger();
      log.e(response.body);*/
      return result;
    } else {
      throw Exception('Failed to get countnotdurabled');
    }
  }

  Future<List<VerifyDurable>> listverifystatus3(String major_name,String year) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_listverifystatus3),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_name': major_name,
        'year': year,
      }),
    );

    log.e(response.body);
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<VerifyDurable> listdurable = (responseModel.result as List).map((item) => VerifyDurable.fromJson(item)).toList();
      log.e(listdurable.toString());

      return listdurable;
    } else {
      throw Exception('Failed to get Listdurable');
    }
  }
  Future<String> countverifystatus4(String major_name,String year) async {
    final response = await http.post(
      Uri.parse(Strings.url+Strings.url_countverifystatus4),
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_name': major_name,
        'year': year,
      }),
    );
    var log = Logger();
    log.e(response.body);
    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      String result = responseModel.result.toString();
      /* var log = Logger();
      log.e(response.body);*/
      return result;
    } else {
      throw Exception('Failed to get countnotdurabled');
    }
  }
  Future<List<verifyinform>> listverifystatus4(String major_name,String year) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_listverifystatus4),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_name': major_name,
        'year': year,
      }),
    );

    log.e(response.body.toString());
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<verifyinform> listdurable = (responseModel.result as List).map((item) => verifyinform.fromJson(item)).toList();
      log.e(listdurable.toString());

      return listdurable;
    } else {
      throw Exception('Failed to get Listdurable');
    }
  }

  Future<List<inform_repair>> listformrepair(String major_id) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_listrepairform),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_id': major_id,
      }),
    );

    log.e(response.body);
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<inform_repair> listdurable = (responseModel.result as List).map((item) => inform_repair.fromJson(item)).toList();
      log.e(listdurable.toString());

      return listdurable;
    } else {
      throw Exception('Failed to get Listdurable');
    }
  }

  Future<List<verifyinform>> listformrepairverifyed(String major_id) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_listrepairformverifyed),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_id': major_id,
      }),
    );

    log.e(response.body.toString());
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<verifyinform> listdurable = (responseModel.result as List).map((item) => verifyinform.fromJson(item)).toList();
      log.e(listdurable.toString());

      return listdurable;
    } else {
      throw Exception('Failed to get Listverifyinform');
    }
  }
  Future<List<verifyinform>> listverifynotmaintenance(String major_id) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_listverifynotinmaintenance),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_id': major_id,
      }),
    );

    log.e(response.body.toString());
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<verifyinform> listdurable = (responseModel.result as List).map((item) => verifyinform.fromJson(item)).toList();
      log.e(listdurable.toString());

      return listdurable;
    } else {
      throw Exception('Failed to get Listverifyinform');
    }
  }
  Future<List<RepairDurable>> listRepairCompleteMajorid(String major_id) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_urllistRepairCompletemajorid),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_id': major_id,
      }),
    );

    log.e(response.body.toString());
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<RepairDurable> listdurable = (responseModel.result as List).map((item) => RepairDurable.fromJson(item)).toList();
      log.e(listdurable.toString());

      return listdurable;
    } else {
      throw Exception('Failed to get listRepairCompleteMajorid');
    }
  }
  Future<List<RepairDurable>> listRepairHistory(String durable_code) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_urllistRepairHistory),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'durable_code': durable_code,
      }),
    );

    log.e(response.body.toString());
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<RepairDurable> listdurable = (responseModel.result as List).map((item) => RepairDurable.fromJson(item)).toList();
      log.e(listdurable.toString());

      return listdurable;
    } else {
      throw Exception('Failed to get listRepairCompleteMajorid');
    }
  }
  Future<Verify> getverifyinverifydurable2(String durable_code) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_getverifyinverifydurable),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'durable_code': durable_code,
      }),
    );
    var log = Logger();
    log.e(response.body);
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(
          jsonDecode(response.body));
      Map<String, dynamic> staffMap = responseModel.toMap();
      Verify v = Verify.fromJson(staffMap['result']);
      var log = Logger();
      log.e("verifyinverifydurable "+v.toString());
      return v;
    } else {
      throw Exception('Failed to Load data');
    }
  }

  Future<String> getverifyinverifydurable(String durable_code,String year) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_getverifyinverifydurable),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'durable_code': durable_code,
        'year': year,
      }),
    );
    var log = Logger();
    log.e(response.body);
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      String result = responseModel.result.toString();
      var log = Logger();
      log.e("verifyinverifydurable "+result);
      return result;
    } else {
      throw Exception('Failed to Load data');
    }
  }
  Future<VerifyDurable> getverifybydurablecode(String durable_code) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_getverifybydurablecode),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'durable_code': durable_code,
      }),
    );

    log.e(response.body);
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      Map<String, dynamic> staffMap = responseModel.toMap();
      VerifyDurable vd = VerifyDurable.fromJson(staffMap['result']);

      log.e(vd.toString());

      return vd;
    } else {
      throw Exception('Failed to get Listdurable');
    }
  }

  Future<VerifyDurable> getverifybydurablecurrent(String durable_code,String year) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_getverifybydurablecurrent),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'durable_code': durable_code,
        'year': year,
      }),
    );

    log.e(response.body);
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      Map<String, dynamic> staffMap = responseModel.toMap();
      VerifyDurable vd = VerifyDurable.fromJson(staffMap['result']);

      log.e(vd.toString());

      return vd;
    } else {
      throw Exception('Failed to get Listdurable');
    }
  }


}
