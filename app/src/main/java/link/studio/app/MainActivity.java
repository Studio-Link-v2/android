package link.studio.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;

public class MainActivity extends AppCompatActivity {
    private Boolean running = false;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("baresip");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.studioid);
     //   tv.setText(stringFromJNI());


        if (!running) {
            new Thread(new Runnable() {
                public void run() {
                    baresipStart();
                }
            }).start();
            //((Button) v).setText("Running");
            running = true;
        }
        Button quitButton = (Button)findViewById(R.id.quit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    baresipStop();
                    running = false;
                }
/*                finish(); */
                System.exit(0);
            }
        });


    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    //public native String stringFromJNI();

    public native void baresipStart();
    public native void baresipStop();


}
