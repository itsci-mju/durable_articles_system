import 'dart:io';

import 'package:firebase_storage/firebase_storage.dart' as firebase_storage;
import 'package:firebase_core/firebase_core.dart' as firebase_core;
import 'package:logger/logger.dart';

class Storage{
  final firebase_storage.FirebaseStorage storage =
      firebase_storage.FirebaseStorage.instance;

  Future<void> uploadFile  (String filePath,String fileName) async {
    File file = File(filePath);

    try{
      await storage.ref('imageverify/$fileName').putFile(file);
    }on firebase_core.FirebaseException catch (e){
      var log = Logger();
      log.e("EROR: $e");
    }

  }



  Future<void> uploadinform  (String filePath,String fileName) async {
    File file = File(filePath);

    try{
      await storage.ref('imageinform/$fileName').putFile(file);
    }on firebase_core.FirebaseException catch (e){
      var log = Logger();
      log.e("EROR: $e");
    }

  }

  Future<String> getimagerepair(String imageName)async{
    String downloadURL = await storage.ref('imageinform/$imageName').getDownloadURL();
    return downloadURL;
  }

  Future<String> downloadURL(String imageName)async{
    String downloadURL = await storage.ref('imageverify/$imageName').getDownloadURL();
    return downloadURL;
  }

}