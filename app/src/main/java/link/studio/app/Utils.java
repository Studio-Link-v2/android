package link.studio.app;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by sreimers on 22.10.17.
 */

public class Utils {
    public static void installFiles(Context myContext) throws IOException {
        String path = myContext.getFilesDir().getAbsolutePath();
        String[] files = {"config"};
        for (String s: files) {
            if (s == "accounts" || s == "contacts") {
                String file = path + "/" + s;
                if ((new File(file)).exists()) {
                    Log.d("Baresip", "Skipping existing file " + file);
                    continue;
                }
            }
            Log.d("Baresip", "Installing new file " + s + " to " + path);
            OutputStream myOutput = new FileOutputStream(path + "/" + s);
            byte[] buffer = new byte[1024];
            int length;
            InputStream myInput = myContext.getAssets().open(s);
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myInput.close();
            myOutput.flush();
            myOutput.close();
        }
    }
}
