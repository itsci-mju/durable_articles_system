import 'dart:convert';


import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import 'package:intl/date_symbol_data_local.dart';

import 'package:intl/intl.dart';

import 'package:project_durable/pages/home_page.dart';
import 'package:buddhist_datetime_dateformat_sns/buddhist_datetime_dateformat_sns.dart';

import 'package:project_durable/splash.dart';
import 'RsponseModel.dart';
import 'Strings.dart';


Future<ResponseModel> reponseModel() async {
  final response = await http
      .post(Uri.parse(Strings.url + Strings.list_staff));

  if (response.statusCode == 200) {
    // If the server did return a 200 OK response,
    // then parse the JSON.
    return ResponseModel.fromJson(jsonDecode(response.body));
  } else {
    throw Exception('Failed to load data');
  }
}

Future<void> main() async {
  Intl.defaultLocale = "th";
  initializeDateFormatting();

  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
  runApp(const MyApp());

}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.indigo,
      ),
      //home: const MyHomePage(title: 'ระบบจัดการครุภัณฑ์'),
      //home: Login_Page(),
      home: Splash(),
      routes: <String, WidgetBuilder>{
        '/home': (BuildContext contxt) => new HomePage(),
        '/main': (BuildContext contxt) => new MyHomePage(),
      //  '/login': (BuildContext contxt) => new LoginPage(),
      },
      debugShowCheckedModeBanner: false,
    );
  }
}

class MyHomePage extends StatefulWidget {

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  late Future<ResponseModel> futureRespone;

  @override
  void initState() {
    super.initState();
    futureRespone = reponseModel();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('หน้าหลัก'),
      ),
      body: Center(
        child: FutureBuilder<ResponseModel>(
          future: futureRespone,
          builder: (context, snapshot) {
            if (snapshot.hasData) {
              return Text(snapshot.data!.result.toString(),
                  style: TextStyle(fontSize: 15));
            } else if (snapshot.hasError) {
              return Text('${snapshot.error}');
            }
            return const CircularProgressIndicator();
          },
        ),
      ),
    );
  }


}
