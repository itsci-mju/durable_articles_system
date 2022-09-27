
import 'package:project_durable/model/durable_model.dart';
import 'package:project_durable/model/staff_model.dart';
import 'package:project_durable/model/veify_model.dart';
import 'package:project_durable/model/verifyPK_model.dart';
import 'package:project_durable/model/verifyPK_model.dart';

class VerifyDurable{
  late String Durable_status;
  late String Save_date;
  late String note;
  late String picture_verify;
  late VerifyDurableID pk;



  VerifyDurable(this.Durable_status,
  this.Save_date,
  this.note,
  this.picture_verify,
  this.pk);


  VerifyDurable.fromJson(Map<String, dynamic> json)
      : Durable_status = json['durable_status'],
        Save_date = json['save_date'],
        note = json['note'],
        picture_verify = json['picture_verify'],
        pk = VerifyDurableID.fromJson(json['pk']);

  Map<String, dynamic> toMap() {
    return {
      'durable_status': Durable_status,
      'save_date': Save_date,
      'note': note,
      'picture_verify': picture_verify,
      'pk': pk.toMap()
    };
  }

  @override
  String toString() {
    return 'VerifyDurable{durable_status: $Durable_status, save_date: $Save_date,'
        'note: $note,'
        'picture_verify: $picture_verify,'
        'pk: $pk';
  }



}