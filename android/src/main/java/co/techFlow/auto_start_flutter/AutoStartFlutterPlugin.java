package co.techFlow.auto_start_flutter;

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.BinaryMessenger;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.ComponentName;
import java.util.*;
import android.provider.Settings;
import android.util.Log;
import com.judemanutd.autostarter.AutoStartPermissionHelper;

/** AutoStartFlutterPlugin */
public class AutoStartFlutterPlugin implements FlutterPlugin, MethodCallHandler {

  private MethodChannel channel;
  private Context context;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    onAttach(flutterPluginBinding.getApplicationContext(), flutterPluginBinding.getBinaryMessenger());
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "com.techflow.co/auto_start_flutter");
    channel.setMethodCallHandler(this);
  }

  private void onAttach(Context applicationContext, BinaryMessenger messenger) {
    this.context = applicationContext;
    channel = new MethodChannel(messenger, "com.techflow.co/auto_start_flutter");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("permit-auto-start")) {
      addAutoStartup();
    } else if (call.method.equals("isAutoStartPermission")) {
      result.success(AutoStartPermissionHelper.getInstance().isAutoStartPermissionAvailable(context));
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  private void addAutoStartup() {
    try {
      Intent intent = new Intent();
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      String manufacturer = android.os.Build.MANUFACTURER;
      // Log.d("addAutoStartup", manufacturer);
      if ("xiaomi".equalsIgnoreCase(manufacturer)) {
        intent.setComponent(
            new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
      } else if ("oppo".equalsIgnoreCase(manufacturer)) {
        intent.setComponent(new ComponentName("com.coloros.safecenter",
            "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
      } else if ("vivo".equalsIgnoreCase(manufacturer)) {
        intent.setComponent(new ComponentName("com.vivo.permissionmanager",
            "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
      } else if ("Letv".equalsIgnoreCase(manufacturer)) {
        intent.setComponent(
            new ComponentName("com.letv.android.letvsafe",
                "coComponentNamem.letv.android.letvsafe.AutobootManageActivity"));
      } else if ("Honor".equalsIgnoreCase(manufacturer)) {
        intent.setComponent(
            new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
      } else if ("samsung".equalsIgnoreCase(manufacturer)) {
        intent.setComponent(
            new ComponentName("com.samsung.android.lool", "com.samsung.android.sm.ui.battery.BatteryActivity"));
      } else if ("oneplus".equalsIgnoreCase(manufacturer)) {
        intent.setComponent(new ComponentName("com.oneplus.security",
            "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity"));
      } else if ("nokia".equalsIgnoreCase(manufacturer)) {
        intent.setComponent(new ComponentName("com.evenwell.powersaving.g3",
            "com.evenwell.powersaving.g3.exception.PowerSaverExceptionActivity"));
      } else if ("asus".equalsIgnoreCase(manufacturer)) {
        intent.setComponent(
            new ComponentName("com.asus.mobilemanager", "com.asus.mobilemanager.autostart.AutoStartActivy"));
      } else if ("realme".equalsIgnoreCase(manufacturer)) {
        Log.d("addAutoStartup", manufacturer);
        intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
      } else if (manufacturer.toLowerCase(Locale.ROOT).contains("infinix") | manufacturer.toLowerCase(Locale.ROOT).contains("itel") | manufacturer.toLowerCase(Locale.ROOT).contains("tecno")) {
        Log.d("addAutoStartup", manufacturer);
        intent.setComponent(
            new ComponentName("com.transsion.phonemaster", "com.cyin.himgr.autostart.AutoStartActivity"));
      }
        // Log.d("addAutoStartup", "startActivity");
        context.startActivity(intent);
    } catch (Exception e) {
      Log.e("exc", String.valueOf(e));
    }
  }
}