package org.tsinghua.omedia.activity;

import java.io.File;

import org.tsinghua.omedia.event.Event;


public interface OmediaActivityIntf {
    
    public void showAlertDialog(String message);
    public void showAlertDialog(int stringId);
    public void tokenWrong();
    public void openFile(File file);
    public void onEventCatch(Event event);
}
