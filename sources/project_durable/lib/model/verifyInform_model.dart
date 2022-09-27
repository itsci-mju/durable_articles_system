import 'InformRepair_model.dart';


class verifyinform {
  late int verify_id;
  late DateTime verify_date;
  late String verify_status;
  late String verify_detail;
  late inform_repair informrepair;

  verifyinform(this.verify_id, this.verify_date, this.verify_status, this.verify_detail, this.informrepair);

  verifyinform.fromJson(Map<String, dynamic> json)
      : verify_id = json['verify_id'],
        verify_date = DateTime.parse(json['verify_date']),
        verify_status = json['verify_status'],
        verify_detail = json['verify_detail'],
        informrepair = inform_repair.fromJson(json['inform_repair']);

  Map<String, dynamic> toMap() {
    return {
      'verify_id': verify_id,
      'verify_date': verify_date,
      'verify_status': verify_status,
      'verify_detail': verify_detail,
      'inform_repair': informrepair.toMap()
    };
  }


  @override
  String toString() {
    return 'verifyinform{verify_id: $verify_id, verify_date: $verify_date,'
        'verify_status: $verify_status,'
        'verify_detail: $verify_detail,'
        'inform_repair: $informrepair}';
  }
}
