package org.tsinghua.omedia.ui.fragment;

import org.tsinghua.omedia.OmediaApplication;
import org.tsinghua.omedia.activity.BaseActivity;
import org.tsinghua.omedia.datasource.DataSource;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 
 * @author xuhongfeng
 *
 */
public class BaseFragment extends Fragment {
    protected OmediaApplication omedia = OmediaApplication.getInstance();
    protected DataSource dataSource = DataSource.getInstance();

    protected BaseActivity getBaseActivity() {
        return (BaseActivity)getActivity();
    }
    
    protected View findViewById(int id) {
        return getActivity().findViewById(id);
    }
}
