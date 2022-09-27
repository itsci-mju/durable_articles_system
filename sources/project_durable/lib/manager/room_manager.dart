import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:logger/logger.dart';
import 'package:project_durable/model/room_model.dart';
import '../RsponseModel.dart';
import '../Strings.dart';
import '../model/durable_model.dart';



class room_manager {
  room_manager();

 Future<List<Room>> listroombymajor(String major_id) async {
    final response = await http.post(
      Uri.parse(Strings.url+Strings.url_listroombymajor),
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'major_id': major_id,
      }),
    );
    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      var log = Logger();
      log.e(responseModel.toString());
      List<Room> listroom= (responseModel.result as List).map((item) => Room.fromJson(item)).toList();
      return listroom;
    } else {
      throw Exception('Failed to get listroom');
    }
  }
  Future<List<Room>> listallroom() async {
    final response = await http.post(
      Uri.parse(Strings.url+Strings.url_listallroom),
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
      }),
    );
    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      ResponseModel responseModel = ResponseModel.fromJson(jsonDecode(response.body));
      List<Room> listroom= (responseModel.result as List).map((item) => Room.fromJson(item)).toList();
      return listroom;
    } else {
      throw Exception('Failed to get listroom');
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

    if (response.statusCode == 200) {
      ResponseModel responseModel = ResponseModel.fromJson(
          jsonDecode(response.body));
      Map<String, dynamic> staffMap = responseModel.toMap();
      Durable d = Durable.fromJson(staffMap['result']);
      var log = Logger();
      log.e("loginmanager "+d.toString());
      return d;
    } else {
      throw Exception('Failed to Load data');
    }
  }
}
