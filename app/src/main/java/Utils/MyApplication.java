package Utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by meng on 2017/7/4.
 */

public class MyApplication extends Application {
   private static Context context;

    @Override
    public void onCreate() {

        context=getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}

