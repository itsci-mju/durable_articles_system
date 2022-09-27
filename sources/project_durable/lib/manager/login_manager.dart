import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:logger/logger.dart';
import '../RsponseModel.dart';
import '../Strings.dart';
import '../model/durable_model.dart';
import '../model/staff_model.dart';
import '../model/verifydurable_model.dart';

class login_manager {
  login_manager();

  Future<String> doLogin(String username, String password) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_login),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'username': username,
        'password': password,
      }),
    );

    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      ResponseModel responseModel = ResponseModel.fromJson(
          jsonDecode(response.body));
      String result = responseModel.result.toString();

      return result;
    } else {
      throw Exception('Failed to Login');
    }
  }

  Future<Staff> getStafbyUsername(String username) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.list_staffbyuser),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'username': username,
      }),
    );
    var log = Logger();
    log.e(response.body.toString());
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(
          jsonDecode(response.body));
      var log = Logger();
      log.e(responseModel.toString());
      Map<String, dynamic> staffMap = responseModel.toMap();
      log.e(staffMap.toString());
      Staff staff = Staff.fromJson(staffMap['result']);

      return staff;
    } else {
      throw Exception('Failed to Login');
    }
  }

  Future<List<Staff>> liststaff(String username, String password) async {
    final response = await http.post(
      Uri.parse(Strings.url + Strings.list_staff),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'username': username,
        'password': password,
      }),
    );

    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      ResponseModel responseModel = ResponseModel.fromJson(
          jsonDecode(response.body));
      List<Staff> liststaff = (responseModel.result as List).map((item) =>
          Staff.fromJson(item)).toList();
      return liststaff;
    } else {
      throw Exception('Failed to get Liststaff');
    }
  }

  Future<List<VerifyDurable>> listdurable(String major_id,String room_number,String years) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_listverify),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_id': major_id,
        'room_number': room_number,
        'years': years,
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
  Future<List<Durable>> listdurablenotverify(String major_id,String room_number,String years) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_listnotverify),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_id': major_id,
        'room_number': room_number,
        'years': years,
      }),
    );
    log.e(response.body);
    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<Durable> listdurable = (responseModel.result as List).map((item) => Durable.fromJson(item)).toList();
      log.e(listdurable.toString());

      return listdurable;
    } else {
      throw Exception('Failed to get Listdurable');
    }
  } Future<List<Durable>> listalldurableadmin(String room_number,String years) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_listdurablenotverify_admin),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'room_number': room_number,
        'years': years,
      }),
    );

    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<Durable> listdurable = (responseModel.result as List).map((item) => Durable.fromJson(item)).toList();
      log.e(listdurable.toString());

      return listdurable;
    } else {
      throw Exception('Failed to get Listdurable');
    }
  }
  Future<List<VerifyDurable>> listdurableverifyed_admin(String room_number,String years) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_listdurable_verifyed_byadmin),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'room_number': room_number,
        'years': years,
      }),
    );

    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<VerifyDurable> listdurable = (responseModel.result as List).map((item) => VerifyDurable.fromJson(item)).toList();
      log.e(listdurable.toString());

      return listdurable;
    } else {
      throw Exception('Failed to get Listdurable');
    }
  }

  Future<void> listdurable2(String major_id) async {
    var log = Logger();
    final response = await http.post(
      Uri.parse(Strings.url + Strings.url_listdurable),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_id': major_id,
      }),
    );

    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<Durable> listdurable = (responseModel.result as List).map((item) => Durable.fromJson(item)).toList();
      log.e("listdurable " + listdurable.toString());
    } else {
      throw Exception('Failed to get Listdurable');
    }
  }




}