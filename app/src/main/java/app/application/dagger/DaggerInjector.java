package app.application.dagger;

import app.application.dagger.component.AppComponent;
import app.application.dagger.component.DaggerAppComponent;
import app.application.dagger.module.AppModule;

public class DaggerInjector {

    private static AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();

    public static AppComponent get() {
        return appComponent;
    }
}
