
import 'package:project_durable/model/staff_model.dart';
import 'package:project_durable/model/veify_model.dart';

import 'durable_model.dart';

class VerifyDurableID{
  late Staff staff;
  late Verify verify;
  late Durable durable;

  VerifyDurableID( this.staff,
      this.verify,
      this.durable);

  VerifyDurableID.fromJson(Map<String, dynamic> json)
      :
        staff = Staff.fromJson(json['staff']),
        verify = Verify.fromJson(json['verify']),
        durable = Durable.fromJson(json['durable']);

  Map<String, dynamic> toMap() {
    return {
      'staff': staff.toMap(),
      'verify': verify.toMap(),
      'durable': durable.toMap()
    };
  }

  @override
  String toString() {
    return 'pk{staff: $staff,'
        'verify: $verify,'
        'durable: $durable';
  }


}