package link.studio.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.view.*;

public class MainActivity extends AppCompatActivity {
    private Boolean running = false;
    private Thread mythread;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("baresip");
    }


    public void requestRecordAudioPermission() {
        //check API version, do nothing if API version < 23!
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion > android.os.Build.VERSION_CODES.LOLLIPOP){

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method



        if (!running) {
            mythread = new Thread(new Runnable() {
                public void run() {
                    baresipStart();
                }
            });
            mythread.start();
            //((Button) v).setText("Running");
            running = true;
        }
        Button quitButton = (Button)findViewById(R.id.quit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    baresipStop1();

                    try {
                        mythread.join(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    baresipStop2();
                    running = false;
                }
                //finish();
                System.exit(0);
            }
        });


        try {
            Utils.installFiles(getApplicationContext());
        } catch (java.io.IOException e) {
            Log.e("Baresip", "Failed to install files: " + e.toString());
        }

        requestRecordAudioPermission();

        for(int x = 1; x < 50; x = x + 1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!getAccount().isEmpty())
                break;
        }

        TextView tv = (TextView) findViewById(R.id.studioid);
        tv.setText(getAccount());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (running) {
            baresipStop1();
            try {
                mythread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            baresipStop2();
            running = false;
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    //public native String stringFromJNI();

    public native void baresipStart();
    public native void baresipStop1();
    public native void baresipStop2();
    public native String getAccount();

}
