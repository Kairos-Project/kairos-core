package kairos.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * Created by Felipe on 17/08/2015.
 */
public class BackStackRecord extends FragmentTransaction {

    static final int ATTACH = 7;
    static final int ADD = 1;
    static final int REMOVE = 2;

    final FragmentManagerImpl fm;
    Op head, tail;
    int numOp;
    boolean backStack = false;

    public BackStackRecord(FragmentManagerImpl fm) {
        this.fm = fm;
    }

    @Override
    public FragmentTransaction attach(Fragment f) {
        Op op = new Op();
        op.fragment = f;
        op.cmd = ATTACH;
        addOp(op);
        return this;
    }

    @Override
    public FragmentTransaction add(String containerId, Fragment f) {
        add(containerId, f, null);
        return this;
    }

    @Override
    public FragmentTransaction add(String containerId, Fragment f, String tag) {
        doAddOp(containerId, f, tag, ADD);
        return this;
    }

    @Override
    public FragmentTransaction replace(String containerId, Fragment f) {
        replace(containerId, f, null);
        return this;
    }

    @Override
    public FragmentTransaction replace(String containerId, Fragment f, String tag) {
        for (Fragment fragment : fm.added) {
            if (tag != null) {
                if (fragment.tag.equals(tag) && fragment.containerId.equals(containerId)) {
                    remove(fragment);
                }
            } else {
                if (fragment.containerId.equals(containerId)) {
                    remove(fragment);
                }
            }
        }
        add(containerId, f);
        return null;
    }

    @Override
    public FragmentTransaction remove(Fragment f) {
        doAddOp(f.containerId, f, null, REMOVE);
        return this;
    }

    @Override
    public FragmentTransaction addToBackStack(String name) {
        backStack = true;
        fm.setBackStack(this);
        return this;
    }

    @Override
    public int commit() {

        do {
            transaction(head);
            numOp--;
        } while ((head = head.next) != null);
        backStack = false;
        return 0;
    }

    private void transaction(Op op) {
        Pane nodeHost = (Pane) fm.activityHost.context.window.getDecorView().lookup("#" + op.fragment.containerId);
        if (op.cmd == ADD) {
            nodeHost.getChildren().add(op.fragment);
            FXMLLoader loader = new FXMLLoader();
            loader.setRoot(op.fragment);
            loader.setController(op.fragment);
            op.fragment.onCreateView(loader);
            op.cmd = ATTACH;
            fm.added.add(op.fragment);
            op.fragment.onResume();
            if (backStack) {
                op.cmd = REMOVE;
            }

        } else if (op.cmd == REMOVE) {
            op.fragment.onPause();
            op.fragment.onStop();
            op.fragment.onDestroyView();
            nodeHost.getChildren().remove(op.fragment);
            op.fragment.onDestroy();
            fm.added.remove(op.fragment);
            op.fragment.onDetach();
            if (backStack) {
                op.cmd = ADD;
            }
        }
    }

    protected void backStack() {
        do {
            transaction(tail);
        } while ((tail = tail.prev) != null);

    }


    private void addOp(Op op) {
        if (head == null) {
            head = tail = op;
        } else {
            op.prev = tail;
            tail.next = op;
            tail = op;
        }
        numOp++;

    }

    private void doAddOp(String containerId, Fragment f, String tag, int cmd) {
        f.fragmentManager = fm;
        if (tag != null) {
            f.tag = tag;
        } else {
            f.tag = f.getClass().getSimpleName();
        }
        if (!containerId.equals("")) {
            f.fragmentId = f.containerId = containerId;
        }
        Op op = new Op();
        op.fragment = f;
        op.cmd = cmd;
        addOp(op);
    }


    static final class Op {
        Op next;
        Op prev;
        int cmd;
        Fragment fragment;
    }

}
