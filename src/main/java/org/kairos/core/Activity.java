package org.kairos.core;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

/**
 * An activity is a single, focused thing that the user can do. Almost all activities interact with the user, so the
 * Activity class takes care of creating a window for you in which you can place your UI with setContentView(URL).
 */
public class Activity extends ContextWrapper {

    static protected final int INITIALIZING = 0;
    static protected final int CREATED = 1;
    static protected final int STOPPED = 2;
    static protected final int STARTED = 3;
    static protected final int RESUMED = 4;

    protected FragmentManagerImpl fragmentManager;
    protected ActionBar actionBar;
    private boolean homeAsUp=false;


    protected int state = INITIALIZING;

    /**
     * Called when the activity is starting, This is where most initialization should go: calling setContentView(URL) to
     * inflate the activity's UI
     */
    public void onCreate() {
        fragmentManager = new FragmentManagerImpl(this);
        state = CREATED;
    }

    /**
     * Called after onCreate
     */
    protected void onStart() {
        state = STARTED;
    }

    /**
     * Called for your activity to start interacting with the user
     */
    protected void onResume() {
        state = RESUMED;
        for(Fragment fragment:fragmentManager.added){
            if(fragment.state<Fragment.RESUMED) {
                fragment.onResume();
            }
        }
    }

    /**
     * Called as part of the activity lifecycle when an activity is going into the background
     */
    protected void onPause() {

    }

    /**
     * Called when you are no longer visible to the user.
     */
    protected void onStop() {
        for(Fragment fragment:fragmentManager.added){
            if(fragment.state>Fragment.STOPPED) {
                fragment.onStop();
            }
        }
        state = STOPPED;

    }

    /**
     * This can happen because the activity is finishing
     */
    protected void onDestroy() {
    }

    /**
     * Set the activity content from a layout resource.
     * @param content URL from the location of the FXML file.
     */
    public void setContentView(URL content) {
        FXMLLoader loader = new FXMLLoader(content);
        loader.setController(this);
        try {
            this.context.window.setContentView(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set a ActionBar object or subclass to act as the ActionBar for this Activity window.
     * @param actionBar  to set as the Activity's action bar
     */
    public void setActionBar(ActionBar actionBar) {
        this.actionBar = actionBar;
     }

    /**
     * Retrieve a reference to this activity's ActionBar
     * @return
     */
    public ActionBar getActionBar() {
        return actionBar;
    }

    /**
     * Return the FragmentManager for interacting with fragments associated with this activity.
     * @return
     */
    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    protected void setHomeAsUp(boolean homeAsUp) {
        this.homeAsUp = homeAsUp;
        if(homeAsUp && actionBar.isDisplayHomeAsUpEnabled()){
            actionBar.showHomeAsUp();
            actionBar.setOnHomeButtonAction(evt->{
                onBackPressed();
            });
        }
    }

    protected boolean isHomeAsUp() {
        return homeAsUp;
    }
}
