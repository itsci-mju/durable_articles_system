class position {
  late String position_id;
  late String position_name;


  position(this.position_id, this.position_name);

  position.fromJson(Map<String, dynamic> json)
      : position_id = json['position_id'],
        position_name = json['position_name'];

  Map<String, dynamic> toMap() {
    // JSON
    // Map<String, String> หมายถึง Map<Key, value>
    // map[key] = value
    // id = map[columnId] ซึ่ง columnId คือ key จะได้ value ออกมาเป็น id
    return {
      'position_id': position_id,
      'position_name': position_name
    };
  }

  @override
  String toString() {
    return 'position{position_id: $position_id, position_name: $position_name,}';
  }
}
