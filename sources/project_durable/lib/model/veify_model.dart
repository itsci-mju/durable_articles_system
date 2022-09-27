import 'dart:convert';

import 'package:intl/intl.dart';
import 'package:project_durable/model/verifydurable_model.dart';

import 'date_model.dart';


class Verify {
  late String Years;
  late DateTime DateStart;
  late DateTime DateEnd;
  final dateformat = new DateFormat('yyyy-MM-dd hh:mm');

  Verify(this.Years, this.DateStart, this.DateEnd);

  Verify.fromJson(Map<String, dynamic> json)
      : Years = json['years'],
        DateStart = DateTime.parse(json['dateStart']),
        DateEnd =  DateTime.parse(json['dateEnd']);



  Map<String, dynamic> toMap() {

    return {'years': Years,'dateStart': DateStart, 'dateEnd': DateEnd };
  }

  @override
  String toString() {
    return 'Verify{years: $Years,'
        'dateStart: $DateStart}'
        'dateEnd: $DateEnd' ;
  }
}
