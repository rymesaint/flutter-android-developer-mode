import 'package:flutter/services.dart';

const String CHANNEL_NAME = 'flutter_android_developer_mode';

enum Methods {
  get_developer_mode,
}

class FlutterAndroidDeveloperMode {
  static const MethodChannel _channel = MethodChannel(CHANNEL_NAME);

  static Future<bool> get isAndroidDeveloperModeEnabled async {
    try {
      final bool isEnabled =
          await _channel.invokeMethod(Methods.get_developer_mode.name);
      return isEnabled;
    } catch (e) {
      print("Error checking developer mode: $e");
      return false;
    }
  }
}
