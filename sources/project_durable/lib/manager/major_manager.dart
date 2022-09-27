import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:logger/logger.dart';
import 'package:project_durable/model/room_model.dart';
import '../RsponseModel.dart';
import '../Strings.dart';



import '../model/major_model.dart';

class major_manager {
  major_manager();

  Future<List<Major>> listALLmajor() async {
    final response = await http.post(
      Uri.parse(Strings.url+Strings.url_listAllmajor),
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
      List<Major> listmajor= (responseModel.result as List).map((item) => Major.fromJson(item)).toList();
      return listmajor;
    } else {
      throw Exception('Failed to get listmajor');
    }
  }

}