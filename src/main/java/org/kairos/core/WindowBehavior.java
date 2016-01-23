package org.kairos.core;

import javafx.scene.Node;

/**
 * Created by Felipe on 20/10/2015.
 */
public interface WindowBehavior {

    public void setContentView(Node content);
    public Node getDecorView();
}
