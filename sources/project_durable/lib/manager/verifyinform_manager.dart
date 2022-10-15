import 'dart:convert';

import 'package:logger/logger.dart';

import 'package:http/http.dart' as http;
import 'package:project_durable/model/verifyInform_model.dart';
import '../RsponseModel.dart';
import '../Strings.dart';

class verifyinform_manager {
  verifyinform_manager();

  Future<String> insertverifyinForm(
      String Informid,
      String verify_status,
      String verify_detail) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_addverifyinform),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'Informid': Informid,
        'verify_status': verify_status,
        'verify_detail': verify_detail,
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

  Future<verifyinform> getverifyinformbyid(String informid) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_getverifyinformbyid),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'informid': informid,
      }),
    );

    log.e(response.body);
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      Map<String, dynamic> staffMap = responseModel.toMap();
      verifyinform vd = verifyinform.fromJson(staffMap['result']);

      log.e(vd.toString());

      return vd;
    } else {
      throw Exception('Failed to get getverifyinformbyid');
    }
  }



}
