import 'dart:convert';
import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;
import 'package:logger/logger.dart';
import '../RsponseModel.dart';
import '../Strings.dart';
import '../model/durable_model.dart';
import '../model/staff_model.dart';


class durable_manager {
  durable_manager();

  Future<List<Durable>> listdurable() async {
    final response = await http.post(
      Uri.parse(Strings.url+Strings.url_listalldurable),
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode({
      }),
    );
    var log = Logger();
    log.e(response.body);
    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<Durable> listdurable = (responseModel.result as List).map((item) => Durable.fromJson(item)).toList();
      return listdurable;
    } else {
      throw Exception('Failed to get Liststaff');
    }
  }

  Future<Durable> getdurablebyCode(String durable_code) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.get_durable),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'durable_code': durable_code,
      }),
    );
    var log =Logger();
    log.e(response.body);
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(
          jsonDecode(response.body));
      Map<String, dynamic> staffMap = responseModel.toMap();
      Durable d = Durable.fromJson(staffMap['result']);
      return d;
    } else {
      throw Exception('Failed to Load data');
    }
  }
}
