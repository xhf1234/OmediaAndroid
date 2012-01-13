package org.tsinghua.omedia.ui;

import org.tsinghua.omedia.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @author hanfuye
 *
 */
public class CcnFilesFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.ccn_fileslist, null);
	}
}
