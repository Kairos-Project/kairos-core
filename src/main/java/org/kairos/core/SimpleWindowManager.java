package org.kairos.core;

import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Created by Felipe on 20/10/2015.
 */
public class SimpleWindowManager extends WindowManager {

    public SimpleWindowManager(Stage window) {
        super(window);
    }

    @Override
    public void add(Activity activity) {
        super.add(activity);
        activity.onStart();
        if(!tail.equals(head)){
            if (activity.actionBar!=null){
                activity.setHomeAsUp(true);
            }
        }
        tail.content=getDecorView();
        if(tail!=head){
            tail.prev.activity.onPause();
            windowPane.remove(tail.prev.content);
            tail.prev.activity.onStop();
        }
        activity.onResume();
    }

    @Override
    public void setContentView(Node content) {
        super.setContentView(content);
        if(content instanceof Region) {
            checkChangeSize(((Region) content).getPrefWidth(), ((Region) content).getPrefHeight());
        }
    }

    @Override
    public ActivityTransition back() {
        if(tail.activity.fragmentManager.backStack!=null){
            tail.activity.fragmentManager.backStack.backStack();
            tail.activity.fragmentManager.backStack=null;
            return null;
        }
        tail.activity.onPause();
        if(tail.prev!=null){
            windowPane.remove(tail.content);
            windowPane.add(tail.prev.content);
            tail.prev.activity.onStart();
        }
        tail.activity.onStop();
        checkChangeSize(((Region) tail.prev.content).getPrefWidth(), ((Region) tail.prev.content).getPrefHeight());
        return super.back();

    }



    private void checkChangeSize(double width,double height){
        if(width!=window.getWidth() || height!=window.getHeight()){
            window.hide();
            window.setWidth(width);
            window.setHeight(height);
            window.centerOnScreen();
            ((Stage)window).show();
        }

    }
}
