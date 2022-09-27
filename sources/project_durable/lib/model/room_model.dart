import 'major_model.dart';

class Room {
  late String Room_number;
  late String Build;
  late String Room_name;
  late String floor;
  late Major major;

  Room(this.Room_number, this.Build, this.Room_name, this.floor, this.major);

  Room.fromJson(Map<String, dynamic> json)
      : Room_number = json['room_number'],
        Build = json['build'],
        Room_name = json['room_name'],
        floor = json['floor'],
        major = Major.fromJson(json['major']);

  Map<String, dynamic> toMap() {
    return {
      'room_number': Room_number,
      'build': Build,
      'room_name': Room_name,
      'floor': floor,
      'major': major.toMap()
    };
  }


  @override
  String toString() {
    return 'Room{room_number: $Room_number, build: $Build,'
        'room_name: $Room_name,'
        'floor: $floor,'
        'major: $major}';
  }
}
