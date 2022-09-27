class company {
  late int company_id;
  late String address;
  late String companyname;
  late String tell;

  company(this.company_id, this.address, this.companyname, this.tell);

  company.fromJson(Map<String, dynamic> json)
      : company_id = json['company_id'],
        address = json['address'],
        companyname = json['companyname'],
        tell = json['tell'];

  Map<String, dynamic> toMap() {
    // JSON
    // Map<String, String> หมายถึง Map<Key, value>
    // map[key] = value
    // id = map[columnId] ซึ่ง columnId คือ key จะได้ value ออกมาเป็น id
    return {
      'company_id': company_id,
      'address': address,
      'companyname': companyname,
      'tell': tell
    };
  }

  @override
  String toString() {
    return 'company{company_id: $company_id, address: $address,'
        'companyname: $companyname,'
        'tell: $tell}';
  }
}
