class Date{
    late int year;
    late int month;
    late int dayOfMonth;
    late int hourOfDay;
    late int minute;
    late int second;

    Date(this.year, this.month, this.dayOfMonth,this.hourOfDay,this.minute,this.second);

    Date.fromJson(Map<String, dynamic> json)
        : year = json['year'],
            month = json['month'],
            dayOfMonth = json['dayOfMonth'],
            hourOfDay = json['hourOfDay'],
            minute = json['minute'],
            second = json['second'];



    Map<String, dynamic> toMap() {
        return {'year': year, 'month': month, 'dayOfMonth': dayOfMonth,'hourOfDay': hourOfDay,
            'minute': minute,'second': second,};
    }

    @override
    String toString() {
        return 'Date{year: $year, month: $month,'
            'dayOfMonth: $dayOfMonth,hourOfDay: $hourOfDay,minute: $minute,second: $second,}';
    }

}