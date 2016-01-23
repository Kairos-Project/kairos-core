package org.kairos.core;

/**
 * Created by Felipe on 09/09/2015.
 */
public abstract class Context {

    protected ActivityTransition window;

    /**
     * Launch a new Activity.
     * @param activity SubClass from @Activity
     */
    public abstract void startActivity(Class<? extends Activity> activity);
    /**
     * Launch a new Activity using a Intent instance.
     * @param intent Intent instance at least contains a SubClass from @Activity
     */
    public abstract void startActivity(Intent intent);

    /**
     * Return to a previous Activity and destroy de current Activity.
     */
    public abstract void onBackPressed();
}
