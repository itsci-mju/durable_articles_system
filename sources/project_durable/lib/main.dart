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

class Palette {
  static const MaterialColor kToDark = const MaterialColor(
    0xe9e65100, // 0% comes in here, this will be color picked if no shade is selected when defining a Color property which doesn’t require a swatch.
    const <int, Color>{
      50: const Color(0xc1e65100),//10%
      100: const Color(0xffee7059),//20%
      200: const Color(0xffa04332),//30%
      300: const Color(0xff89392b),//40%
      400: const Color(0xff733024),//50%
      500: const Color(0xff5c261d),//60%
      600: const Color(0xff451c16),//70%
      700: const Color(0xff2e130e),//80%
      800: const Color(0xff170907),//90%
      900: const Color(0xff000000),//100%
    },
  );
}
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
       primarySwatch :  Palette.kToDark,
        primaryColor: Colors.green,
        fontFamily: 'Georgia',
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
