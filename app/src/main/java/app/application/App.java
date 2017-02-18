package app.application;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

public class App extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }

}
