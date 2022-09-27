import 'dart:convert';

import 'package:logger/logger.dart';

import 'package:http/http.dart' as http;
import '../RsponseModel.dart';
import '../Strings.dart';
import '../model/repairdurable_model.dart';

class Repairdurable_manager {
  Repairdurable_manager();

  Future<String> insertrepairdurable(
      String companyname,
      String repair_charges,
      String repair_detail,
      String repair_status,
      String durable_code,
      String verify_id) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_urlinsertrepairdurable),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'companyname': companyname,
        'repaircharges': repair_charges,
        'repair_detail': repair_detail,
        'repair_status': repair_status,
        'durable_code': durable_code,
        'verify_id': verify_id,
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

  Future<String> updaterepairdurable(
      String companyname,
      String repair_charges,
      String repair_detail,
      String repair_status,
      String durable_code,
      String verify_id) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_urlupdaterepairdurable),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'companyname': companyname,
        'repaircharges': repair_charges,
        'repair_detail': repair_detail,
        'repair_status': repair_status,
        'durable_code': durable_code,
        'verify_id': verify_id,
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

  Future<List<RepairDurable>> listRepairComplete(String major_name) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_urllistRepairComplete),
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
      List<RepairDurable> listdurable = (responseModel.result as List).map((item) => RepairDurable.fromJson(item)).toList();
      log.e(listdurable.toString());

      return listdurable;
    } else {
      throw Exception('Failed to get Listinform');
    }
  }
  Future<RepairDurable> getdurablerepair(String durable_code,String verify_id) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_urlgetRepairdurable),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'durable_code': durable_code,
        'verify_id': verify_id,
      }),
    );
    var log = Logger();
    log.e(response.body);
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(
          jsonDecode(response.body));
      Map<String, dynamic> staffMap = responseModel.toMap();
      RepairDurable rd = RepairDurable.fromJson(staffMap['result']);
      var log = Logger();
      log.e(rd);
      return rd;
    } else {
      throw Exception('Failed to Load data');
    }
  }
  Future<RepairDurable> getdurablerepairHistory(String durable_code,String repair_id) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_urlgetRepairHistory),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'durable_code': durable_code,
        'repair_id': repair_id,
      }),
    );
    var log = Logger();
    log.e(response.body);
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(
          jsonDecode(response.body));
      Map<String, dynamic> staffMap = responseModel.toMap();
      RepairDurable rd = RepairDurable.fromJson(staffMap['result']);
      var log = Logger();
      log.e(rd);
      return rd;
    } else {
      throw Exception('Failed to Load data');
    }
  }


}
