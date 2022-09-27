import 'dart:convert';

import 'package:logger/logger.dart';
import 'package:project_durable/model/veify_model.dart';
import 'package:project_durable/model/verifydurable_model.dart';
import 'package:http/http.dart' as http;
import '../RsponseModel.dart';
import '../Strings.dart';
import '../model/InformRepair_model.dart';
import '../model/verifyInform_model.dart';

class inform_manager {
  inform_manager();

  Future<String> insertinformrepair(
      String Informtype,
      String details,
      String picture_inform,
      String id_staff,
      String durable_code) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_addinformrepair),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'Informtype': Informtype,
        'details': details,
        'picture_inform': picture_inform,
        'id_staff': id_staff,
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

  Future<String> editinformrepair(
      String Informid,
      String Informtype,
      String dateinform,
      String details,
      String picture_inform,
      String id_staff,
      String durable_code) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_editinformrepair),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'Informid': Informid,
        'Informtype': Informtype,
        'dateinform': dateinform,
        'details': details,
        'picture_inform': picture_inform,
        'id_staff': id_staff,
        'durable_code': durable_code,
      }),
    );
    var log = Logger();
    log.e(response.body);
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(
          jsonDecode(response.body));
      String result = responseModel.result.toString();
      var log = Logger();
      log.e(result);
      return result;
    } else {
      throw Exception('Failed to Update');
    }
  }


  Future<inform_repair> getinform_repairbyID(String durable_code) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_getInform_repairbyID),
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
      inform_repair d = inform_repair.fromJson(staffMap['result']);
      var log = Logger();
      log.e(d);
      return d;
    } else {
      throw Exception('Failed to Load data');
    }
  }
  Future<List<inform_repair>> listallformrepair() async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_lisAlltrepairform),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
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

  Future<List<inform_repair>> listinformnotin(String major_name) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_listinformnotinverify),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_name': major_name,
      }),
    );
    log.e(response.body);
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<inform_repair> listdurable = (responseModel.result as List).map((item) => inform_repair.fromJson(item)).toList();
      log.e(listdurable.toString());

      return listdurable;
    } else {
      throw Exception('Failed to get Listinform');
    }
  }
  Future<List<verifyinform>> listinformin(String major_name) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_listinforminverify),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_name': major_name,
      }),
    );

    log.e(response.body);
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<verifyinform> listdurable = (responseModel.result as List).map((item) => verifyinform.fromJson(item)).toList();
      log.e(listdurable.toString());

      return listdurable;
    } else {
      throw Exception('Failed to get Listinform');
    }
  }
  Future<List<verifyinform>> listinforminnotmaintenanadmin(String major_name) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_listinforminverifynotinmaintenance),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_name': major_name,
      }),
    );

    log.e(response.body);
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<verifyinform> listdurable = (responseModel.result as List).map((item) => verifyinform.fromJson(item)).toList();
      log.e(listdurable.toString());

      return listdurable;
    } else {
      throw Exception('Failed to get Listinform');
    }
  }
  Future<String> getdurablein_informrepair(String durable_code) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_getdurableininformrepair),
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
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      String result = responseModel.result.toString();
      var log = Logger();
      log.e("getdurablein_informrepair "+result);
      return result;
    } else {
      throw Exception('Failed to Load data');
    }
  }

  Future<inform_repair> getdurablein_informrepair2(String durable_code) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_getdurableininformrepair2),
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
      inform_repair d = inform_repair.fromJson(staffMap['result']);
      var log = Logger();
      log.e(d);
      return d;
    } else {
      throw Exception('Failed to Load data');
    }
  }
  Future<String> getdurableininformrepairnotrepair(String durable_code) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_getdurableininformrepairnotrepair),
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
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      String result = responseModel.result.toString();
      var log = Logger();
      log.e("getdurablein_informrepair "+result);
      return result;
    } else {
      throw Exception('Failed to Load data');
    }
  }

}
