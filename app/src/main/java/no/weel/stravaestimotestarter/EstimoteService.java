package no.weel.stravaestimotestarter;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.BeaconManager.MonitoringListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class EstimoteService extends Service {
    private static final String LOGTAG = "EstimoteService";
    private static BeaconManager beaconManager;
    private static final Region ALL_ESTIMOTE_BEACONS = new Region("regionId",
            "B9407F30-F5F8-466E-AFF9-25556B57FE6D", null, null);

    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setBackgroundScanPeriod(TimeUnit.SECONDS.toMillis(1), 0);
        beaconManager.setMonitoringListener(new MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> beacons) {
                Toast.makeText(getApplicationContext(), "Entered bike! Enjoy the ride!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_RUN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("http://strava.com/nfc/record"));
                intent.putExtra("data", "http://strava.com/nfc/record");
                startActivity(intent);
            }

            @Override
            public void onExitedRegion(Region region) {

                Toast.makeText(getApplicationContext(), "Exited bike! Hope you enjoyed the ride!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_RUN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("http://strava.com/nfc/record/stop"));
                startActivity(intent);
            }
        });

        // Connect to the beacon manager...
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    // ... and start the monitoring
                    beaconManager.startMonitoring(ALL_ESTIMOTE_BEACONS);
                } catch (Exception e) {
                    Log.e(LOGTAG, "Got exception in monitoring" + e);
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}
