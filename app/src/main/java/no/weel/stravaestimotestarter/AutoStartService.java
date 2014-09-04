package no.weel.stravaestimotestarter;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AutoStartService extends BroadcastReceiver {
    private Intent serviceIntent;


    @Override
    public void onReceive(final Context context, final Intent intent) {
        Toast.makeText(context, "Test", Toast.LENGTH_LONG).show();
        if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_TURNING_OFF:
                    if (serviceIntent != null) {
                        context.stopService(serviceIntent);
                        serviceIntent = null;
                    }
                    break;
                case BluetoothAdapter.STATE_ON:
                    if (serviceIntent == null) {
                        serviceIntent = new Intent(context,
                                EstimoteService.class);
                        context.startService(serviceIntent);
                    }
                    break;
            }
        }
    }
}
