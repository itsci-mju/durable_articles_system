import 'dart:ffi';

class ResponseModel {
  late int code;
  late Object result;

  ResponseModel({
    this.code = 0,
    required this.result,
  });

  factory ResponseModel.fromJson(Map<String, dynamic> json) {
    return ResponseModel(
        code: json['code'],
        result: json['result']
    );
  }

  Map<String, dynamic> toMap(){
    // JSON
    // Map<String, String> หมายถึง Map<Key, value>
    // map[key] = value
    // id = map[columnId] ซึ่ง columnId คือ key จะได้ value ออกมาเป็น id

    return{
      'code': code,
      'result': result,
    };
  }

  @override
  String toString() {
    return 'ResponseModel{code: $code, result: $result}';
  }
}