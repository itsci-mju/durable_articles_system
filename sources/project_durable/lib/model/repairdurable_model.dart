
import 'package:project_durable/model/verifyInform_model.dart';

import 'company_model.dart';
import 'durable_model.dart';


class RepairDurable {
  late int repair_id;
  late String repair_date;
  late String repair_title;
  late String repair_charges;
  late String repair_detail;
  late String picture_invoice;
  late String picture_repairreport;
  late String picture_quatation;
  late String picture_repair;
  late String Date_of_repair;
  late String Repair_status;
  late Durable durable;
  late company company_;
  late verifyinform verifyinform_;

  RepairDurable(this.repair_id, this.repair_date, this.repair_title, this.repair_charges, this.repair_detail,this.picture_invoice,this.picture_repairreport,this.picture_quatation,this.picture_repair,
      this.Date_of_repair,this.Repair_status,this.durable,this.company_,this.verifyinform_);

  RepairDurable.fromJson(Map<String, dynamic> json)
      : repair_id = json['repair_id'],
        repair_date = json['repair_date'],
        repair_title = json['repair_title'],
        repair_charges = json['repair_charges'],
        repair_detail = json['repair_detail'],
        picture_invoice = json['picture_invoice'],
        picture_repairreport = json['picture_repairreport'],
        picture_quatation = json['picture_quatation'],
        picture_repair = json['picture_repair'],
        Date_of_repair = json['date_of_repair'],
        Repair_status = json['repair_status'],
        durable = Durable.fromJson(json['durable']),
        company_ = company.fromJson(json['company']),
        verifyinform_ = verifyinform.fromJson(json['verifyinform']);

  Map<String, dynamic> toMap() {
    return {
      'repair_id': repair_id,
      'repair_date': repair_date,
      'repair_title': repair_title,
      'repair_charges': repair_charges,
      'repair_detail': repair_detail,
      'picture_invoice': picture_invoice,
      'picture_repairreport': picture_repairreport,
      'picture_quatation': picture_quatation,
      'picture_repair': picture_repair,
      'date_of_repair': Date_of_repair,
      'repair_status': Repair_status,
      'durable': durable.toMap(),
      'company': company_.toMap(),
      'verifyinform': verifyinform_.toMap()
    };
  }


  @override
  String toString() {
    return 'RepairDurable{repair_id: $repair_id, repair_date: $repair_date,'
        'repair_title: $repair_title,'
        'repair_charges: $repair_charges,'
        'repair_detail: $repair_detail,'
        'picture_invoice: $picture_invoice,'
        'picture_repairreport: $picture_repairreport,'
        'picture_quatation: $picture_quatation,'
        'picture_repair: $picture_repair,'
        'date_of_repair: $Date_of_repair,'
        'repair_status: $Repair_status,'
        'durable: $durable,'
        'company: $company_,'
        'verifyinform: $verifyinform_}' ;
  }
}
