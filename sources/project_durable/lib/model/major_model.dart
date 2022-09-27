class Major {
  late int ID_Major;
  late String Major_Name;


  Major(this.ID_Major, this.Major_Name);

  Major.fromJson(Map<String, dynamic> json)
      : ID_Major = json['id_Major'],
        Major_Name = json['major_Name'];

  Map<String, dynamic> toMap() {
    // JSON
    // Map<String, String> หมายถึง Map<Key, value>
    // map[key] = value
    // id = map[columnId] ซึ่ง columnId คือ key จะได้ value ออกมาเป็น id
    return {
      'id_Major': ID_Major,
      'major_Name': Major_Name
    };
  }

  @override
  String toString() {
    return 'Major{id_Major: $ID_Major,major_Name: $Major_Name}';
  }
}
