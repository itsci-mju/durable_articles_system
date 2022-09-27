class Login {
  late String username;
  late String password;
  late String status;


  Login(this.username, this.password, this.status);
  Login.fromJson(Map<String, dynamic> json)
      : username = json['username'],
        password = json['password'],
        status = json['status'];

  Map<String, dynamic> toMap() {
    // JSON
    // Map<String, String> หมายถึง Map<Key, value>
    // map[key] = value
    // id = map[columnId] ซึ่ง columnId คือ key จะได้ value ออกมาเป็น id
    return {'username': username, 'password': password, 'status': status};
  }

  @override
  String toString() {
    return 'Login{username: $username, password: $password, status: $status}';
  }

}
