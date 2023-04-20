package jmmunoza.jmmunoza.bluechat;

import android.app.Application;
import android.app.Service;
import android.content.Context;

public class BlueChatApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
