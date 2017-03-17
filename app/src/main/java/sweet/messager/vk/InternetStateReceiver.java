package sweet.messager.vk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import sweet.messager.vk.model.LongPollModel;
import sweet.messager.vk.services.LongPoll;

public class InternetStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(ApplicationName.getAppContext(), LongPoll.class);
         ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
         android.net.NetworkInfo wifi = connMgr.getActiveNetworkInfo();//(ConnectivityManager.TYPE_WIFI);
        if (wifi != null && wifi.isConnected())// || mobile != null && mobile.isAvailable())
             {
           ApplicationName.getAppContext().startService(intent1);
        } else {
           ApplicationName.getAppContext().stopService(intent1);

        }
    }
}