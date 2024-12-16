import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      home: ServiceControlScreen(),
    );
  }
}

class ServiceControlScreen extends StatelessWidget {
  final MethodChannel _channel =
      const MethodChannel('com.example.switch_on_off/service');

  const ServiceControlScreen({super.key});

  Future<void> _startService() async {
    try {
      final result = await _channel.invokeMethod('startService');
      print("Response from Android: $result");
    } catch (e) {
      print("Error starting service: $e");
    }
  }

  Future<void> _stopService() async {
    try {
      final result = await _channel.invokeMethod('stopService');
      print("Response from Android: $result");
    } catch (e) {
      print("Error stopping service: $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Service Control")),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          ElevatedButton(
            onPressed: _startService,
            child: const Text("Start Service"),
          ),
          const SizedBox(height: 20),
          ElevatedButton(
            onPressed: _stopService,
            child: const Text("Stop Service"),
          ),
        ],
      ),
    );
  }
}
