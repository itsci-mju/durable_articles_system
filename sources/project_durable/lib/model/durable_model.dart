import 'package:project_durable/model/room_model.dart';
import 'package:project_durable/model/verifydurable_model.dart';

import 'major_model.dart';

class Durable {
  late String Durable_code;
  late String Durable_Borrow_Status;
  late String Durable_brandname;
  late String Durable_entrancedate;
  late String Durable_image;
  late String Durable_model;
  late String Durable_name;
  late String Durable_number;
  late String Durable_price;
  late String Durable_statusnow;
  late String Responsible_person;
  late String note;
  late Major major;
  late Room room;


  Durable(this.Durable_code,
      this.Durable_Borrow_Status,
      this.Durable_brandname,
      this.Durable_entrancedate,
      this.Durable_image,
      this.Durable_model,
      this.Durable_name,
      this.Durable_number,
      this.Durable_price,
      this.Durable_statusnow,
      this.Responsible_person,
      this.note,
      this.major,
      this.room);

  Durable.fromJson(Map<String, dynamic> json)
      : Durable_code = json['durable_code'],
        Durable_Borrow_Status = json['durable_Borrow_Status'],
        Durable_brandname = json['durable_brandname'],
        Durable_entrancedate = json['durable_entrancedate'],
        Durable_image = json['durable_image'],
        Durable_model = json['durable_model'],
        Durable_name = json['durable_name'],
        Durable_number = json['durable_number'],
        Durable_price = json['durable_price'],
        Durable_statusnow = json['durable_statusnow'],
        Responsible_person = json['responsible_person'],
        note = json['note'],

        major = Major.fromJson(json['major']),
        room = Room.fromJson(json['room']);


  Map<String, dynamic> toMap() {
    return {
      'durable_code': Durable_code,
      'durable_Borrow_Status': Durable_Borrow_Status,
      'durable_brandname': Durable_brandname,
      'durable_entrancedate': Durable_entrancedate,
      'durable_image': Durable_image,
      'durable_model': Durable_model,
      'durable_name': Durable_name,
      'durable_number': Durable_number,
      'durable_price': Durable_price,
      'durable_statusnow': Durable_statusnow,
      'responsible_person': Responsible_person,
      'note': note,
      'major': major.toMap(),
      'room': room.toMap()
    };
  }

  @override
  String toString() {
    return 'Durable{durable_code: $Durable_code, durable_Borrow_Status: $Durable_Borrow_Status,'
        'durable_brandname: $Durable_brandname,'
        'durable_entrancedate: $Durable_entrancedate,'
        'durable_image: $Durable_image,'
        'durable_model: $Durable_model,'
        'durable_name: $Durable_name,'
        'durable_number: $Durable_number,'
        'durable_price: $Durable_price,'
        'durable_statusnow: $Durable_statusnow,'
        'responsible_person: $Responsible_person,'
        'note: $note,'
        'major: $major},'
        'room: $room}';
  }
}