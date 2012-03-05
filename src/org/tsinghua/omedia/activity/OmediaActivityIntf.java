package org.tsinghua.omedia.activity;

import java.io.File;

import org.tsinghua.omedia.event.Event;

/**
 * 
 * @author xuhongfeng
 *
 */
public interface OmediaActivityIntf {
    
    public void showAlertDialog(String message);
    public void showAlertDialog(int stringId);
    public void tokenWrong();
    public void openFile(File file);
    public void onEventCatch(Event event);
    public void toast(String s);
    public void toast(int strId);
}
