package org.kairos.core;

import javafx.stage.Stage;

/**
 *It is the core from the implementation of activities in a javafx application. When is created a Stage instance, this
 *  must be used with a ActiveFactory instance to start the main activity from the application.
 *
 * Each stage represents a unique application that has its own life cycle.
 */
public class ActivityFactory extends Context {

    /**
     *
     * @param root Stage from the JavaFX application
     */
    public ActivityFactory(Stage root) {
        if (this.window == null) {
            this.window = new SimpleWindowManager(root);
        }
    }



    @Override
    public void startActivity(Class<? extends Activity> activity) {
            startActivity(new Intent(null,activity));
    }


    @Override
    public void startActivity(Intent intent) {
        try {
            Activity activityInstance = (Activity) intent.activity.newInstance();
            activityInstance.context = this;
            activityInstance.intent=intent;
            activityInstance.onCreate();
            this.window.add(activityInstance);

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        this.window.back();
    }


}
