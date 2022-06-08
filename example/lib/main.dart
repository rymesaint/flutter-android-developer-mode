import 'package:flutter/material.dart';
import 'package:flutter_android_developer_mode/flutter_android_developer_mode.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  bool? _isDevMode;

  @override
  void initState() {
    super.initState();

    updateDevMode();
  }

  void updateDevMode() async {
    bool isDevMode =
        await FlutterAndroidDeveloperMode.isAndroidDeveloperModeEnabled;

    setState(() {
      _isDevMode = isDevMode;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title:
              const Text('flutter_android_developer_mode plugin example app'),
        ),
        body: Center(
          child: Column(children: [
            if (_isDevMode != null)
              Text('Is Android Developer Mode Enabled? $_isDevMode'),
            ElevatedButton(
              child: const Text(
                'Get developer mode',
              ),
              onPressed: () {
                updateDevMode();
              },
            ),
          ]),
        ),
      ),
    );
  }
}
