package org.kairos.core;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Created by Felipe on 02/11/2015.
 */
public interface ActionBar {
    public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp);
    public void setDisplayShowHomeEnabled(boolean showHome);
    public void showHomeAsUp();
    public boolean isDisplayHomeAsUpEnabled();
    public void setOnHomeButtonAction(EventHandler<ActionEvent> handler);
}
