package app.application;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

/**
 * This rule registers SchedulerHooks for RxJava and RxAndroid to ensure that subscriptions
 * always subscribeOn and observeOn Schedulers.immediate().
 * Warning, this rule will reset RxAndroidPlugins and RxJavaPlugins before and after each test so
 * if the application code uses RxJava plugins this may affect the behaviour of the testing method.
 */
public class RxSchedulersOverrideRule {

    public RxSchedulersOverrideRule(){
        // Override RxJava schedulers
        RxJavaHooks.setOnIOScheduler((Scheduler scheduler) ->
                Schedulers.immediate()
        );

        RxJavaHooks.setOnComputationScheduler((Scheduler scheduler) ->
                Schedulers.immediate()
        );

        RxJavaHooks.setOnNewThreadScheduler((Scheduler scheduler) ->
                Schedulers.immediate()
        );

        // Override RxAndroid schedulers
        final RxAndroidPlugins rxAndroidPlugins = RxAndroidPlugins.getInstance();
        rxAndroidPlugins.registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }
}
