package org.kairos.core;

public abstract class ActivityTransition implements WindowBehavior{

    public abstract void add(Activity activity);
    public abstract ActivityTransition back();
}
