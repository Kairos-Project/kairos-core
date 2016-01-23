package kairos.core;

/**
 * Created by Felipe on 17/08/2015.
 */
public abstract class FragmentTransaction {
    public abstract FragmentTransaction attach(Fragment f);
    public abstract FragmentTransaction add(String containerId,Fragment f);
    public abstract FragmentTransaction add(String containerId,Fragment f,String tag);
    public abstract FragmentTransaction replace(String containerId,Fragment f);
    public abstract FragmentTransaction replace(String containerId,Fragment f,String tag);
    public abstract FragmentTransaction remove(Fragment f);
    public abstract FragmentTransaction addToBackStack(String name);
    public abstract int commit();
}

