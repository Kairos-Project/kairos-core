package kairos.core;

import com.sun.istack.internal.NotNull;

import java.util.HashMap;

/**
 * Created by Felipe on 02/11/2015.
 */
public class Intent {
     protected Class<?> activity;
     protected HashMap extras;

     public Intent(HashMap extras,@NotNull Class<?> activity){
         this.extras=extras;
         this.activity=activity;
     }

    public HashMap getExtras() {
        return extras;
    }
    public void putExtras(HashMap extras) {
        this.extras = extras;
    };


}
