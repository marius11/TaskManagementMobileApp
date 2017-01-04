package com.example.marius.taskmanager.Network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import static com.example.marius.taskmanager.SQLiteDB.TaskOperations.LOGTAG;

public class NetworkStateReceiver extends BroadcastReceiver {
    public interface NetworkListener {
        void onNetworkAvailable();
        void onNetworkUnavailable();
    }

    private NetworkListener networkListener;

    public NetworkStateReceiver(NetworkListener networkListener) {
        this.networkListener = networkListener;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getExtras() != null) {
            NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);
            if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
                Log.i(LOGTAG, "Network " + ni.getTypeName() + " connected");
                networkListener.onNetworkAvailable();
            }
        }
        if (intent.getExtras().getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
            Log.i(LOGTAG, "There's no network connectivity");
            networkListener.onNetworkUnavailable();
        }
    }
}