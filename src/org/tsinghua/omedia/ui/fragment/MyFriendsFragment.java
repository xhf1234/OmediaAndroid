package org.tsinghua.omedia.ui.fragment;

import org.tsinghua.omedia.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @author xuhongfeng
 *
 */
public class MyFriendsFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myfriends, container, false);
    }
    
}
