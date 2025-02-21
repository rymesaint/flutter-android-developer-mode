package com.jonatanbortolon.flutter_android_developer_mode

import android.content.Context
import android.provider.Settings
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

const val CHANNEL_NAME = "flutter_android_developer_mode";
enum class Methods(val string: String) {
    GET_DEVELOPER_MODE("get_developer_mode")
}

/** AndroidDeveloperModePlugin */
class FlutterAndroidDeveloperModePlugin: FlutterPlugin, MethodCallHandler {
  private lateinit var context: Context
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, CHANNEL_NAME)
    channel.setMethodCallHandler(this)

    context = flutterPluginBinding.applicationContext
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == Methods.GET_DEVELOPER_MODE.string) {
      result.success(isDevMode())
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  private fun isDevMode(): Boolean {
    context?.let {
        return when {
            android.os.Build.VERSION.SDK_INT == 16 -> {
                Settings.Secure.getInt(it.contentResolver, Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0
            }
            android.os.Build.VERSION.SDK_INT >= 17 -> {
                Settings.Secure.getInt(it.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0
            }
            else -> false
        }
    }
    return false
  }
}
