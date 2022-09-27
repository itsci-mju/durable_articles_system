import 'package:project_durable/model/major_model.dart';
import 'package:project_durable/model/verifydurable_model.dart';

import 'login_model.dart';

class Staff {
  late int id_staff;
  late String Id_card;
  late String Staff_name;
  late String Staff_lastname;
  late String Staff_status;
  late String Email;
  late String Brithday;
  late String Phone_number;
  late String Image_staff;
  //late VerifyDurable  verifydurable;
  late Major major;
  late Login login;



  Staff(this.id_staff,
      this.Id_card,
      this.Staff_name,
      this.Staff_lastname,
      this.Staff_status,
      this.Email,
      this.Brithday,
      this.Phone_number,
      this.Image_staff,
    //  this.verifydurable,
      this.major,
      this.login);


  Staff.fromJson(Map<String, dynamic> json)
      :id_staff = json['id_staff'],
        Id_card = json['id_card'],
        Staff_name = json['staff_name'],
        Staff_lastname = json['staff_lastname'],
        Staff_status = json['staff_status'],
        Email = json['email'],
        Brithday = json['brithday'],
        Phone_number = json['phone_number'],
        Image_staff = json['image_staff'],
      //  verifydurable = json['VerifyDurable'],
        major = Major.fromJson(json['major']),
        login = Login.fromJson(json['login']);

    Staff.fromJson2(Map<String, dynamic> json)
      :id_staff = json['id_staff'],
        Id_card = json['Id_card'],
        Staff_name = json['Staff_name'],
        Staff_lastname = json['Staff_lastname'],
        Staff_status = json['Staff_status'],
        Email = json['Email'],
        Brithday = json['Brithday'],
        Phone_number = json['Phone_number'],
        Image_staff = json['Image_staff'],
  //  verifydurable = json['VerifyDurable'],
        major = Major.fromJson(json['major']),
        login = Login.fromJson(json['login']);

  Map<String, dynamic> toMap() {
    return {
      'id_staff': id_staff,
      'id_card': Id_card,
      'staff_name': Staff_name,
      'staff_lastname': Staff_lastname,
      'staff_status': Staff_status,
      'email': Email,
      'brithday': Brithday,
      'phone_number': Phone_number,
      'image_staff': Image_staff,
     // 'VerifyDurable': verifydurable,
      'major': major.toMap(),
      'login': login.toMap()};
  }

  @override
  String toString() {
    return 'Staff{id_staff: $id_staff,'
        'id_card: $Id_card,'
        'staff_name: $Staff_name,'
        'staff_lastname: $Staff_lastname,'
        'staff_status: $Staff_status,'
        'email: $Email,'
        'brithday: $Brithday,'
        'phone_number: $Phone_number,'
        'image_staff: $Image_staff,'
      //  'VerifyDurable: $verifydurable'
        'major: $major,'
        'login: $login}';
  }
}
