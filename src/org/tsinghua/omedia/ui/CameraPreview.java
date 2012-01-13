package org.tsinghua.omedia.ui;

import java.io.IOException;

import org.tsinghua.omedia.activity.LandingActivity;
import org.tsinghua.omedia.tool.Logger;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 * @author hanfuye
 * 
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final Logger logger = Logger
            .getLogger(LandingActivity.class);
    
	private SurfaceHolder mHolder;
	private Camera mcamera;

	public CameraPreview(Context context, Camera camera) {
		super(context);
		mcamera = camera;
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		if (mHolder.getSurface() == null) {
			return;
		}
		try {
			mcamera.stopPreview();
		} catch (Exception e) {
		}
		try {
			mcamera.setPreviewDisplay(mHolder);
			mcamera.startPreview();
		} catch (Exception e) {
			logger.error("Error starting camera preview: ", e);
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mcamera.setPreviewDisplay(holder);
			mcamera.startPreview();
		} catch (IOException e) {
			logger.error( "Error setting camera preview:", e);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mcamera.stopPreview();
		mcamera.release();
		mcamera = null;
	}

}
