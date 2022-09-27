
import 'package:project_durable/model/durable_model.dart';
import 'package:project_durable/model/staff_model.dart';
import 'date_model.dart';

class inform_repair{
  late String Informid;
  late String Informtype;
  late DateTime dateinform;
  late String details;
  late String picture_inform;
  late Staff staff;
  late Durable durable;



  inform_repair(this.Informid,
      this.Informtype,
      this.dateinform,
      this.details,
      this.picture_inform,
      this.staff,
      this.durable);


  inform_repair.fromJson(Map<String, dynamic> json)
      : Informid = json['informid'],
        Informtype = json['informtype'],
        dateinform = DateTime.parse(json['dateinform']),
        details = json['details'],
        picture_inform = json['picture_inform'],
        staff = Staff.fromJson(json['staff']),
        durable = Durable.fromJson(json['durable']) ;

  Map<String, dynamic> toMap() {
    return {
      'informid': Informid,
      'informtype': Informtype,
      'dateinform': dateinform,
      'details': details,
      'picture_inform': picture_inform,
      'staff': staff.toMap(),
      'durable': durable.toMap()
    };
  }

  @override
  String toString() {
    return 'inform_repair{informid: $Informid, informtype: $Informtype,'
        'dateinform: $dateinform,'
        'details: $details,'
        'picture_inform: $picture_inform,'
        'staff: $staff,'
        'durable: $durable';
  }



}