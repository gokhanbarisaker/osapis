package com.gokhanbarisaker.osapis.utility;

import android.app.Activity;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceView;

import java.util.List;

/**
 * Created by gokhanbarisaker on 19/01/14.
 *
 * TODO: Add camera2 api support for api level higher than 21, Android L.
 */
public class CameraUtilities
{
    /***************************************
     * Singleton reference
     */
    private static CameraUtilities sharedInstance = null;


    /***************************************
     * Constructors & instance providers
     */
    private CameraUtilities(){/*No public constructor*/}

    public static CameraUtilities sharedInstance()
    {
        if(CameraUtilities.sharedInstance == null)
        {
            CameraUtilities.sharedInstance = new CameraUtilities();
        }

        return CameraUtilities.sharedInstance;
    }


    /***************************************
     * Tool set
     */

    public int getCameraId(int cameraFacing)
    {
        int cameraId = -1;

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int cameraQuantity = Camera.getNumberOfCameras();

        for ( int id = 0; id < cameraQuantity; id++ )
        {
            Camera.getCameraInfo( id, cameraInfo );

            if ( cameraInfo.facing == cameraFacing)
            {
                cameraId = id;

                break;
            }
        }

        return cameraId;
    }

    public boolean isCameraAvailable(int cameraFacing)
    {
        return (getCameraId(cameraFacing) >= 0);
    }

    public boolean isFrontFacingCameraAvailable()
    {
        return isCameraAvailable(Camera.CameraInfo.CAMERA_FACING_FRONT);
    }

    public boolean isBackFacingCameraAvailable()
    {
        return isCameraAvailable(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    public Camera openCamera(int cameraFacing)
    {
        Camera camera = null;

        int cameraId = getCameraId(cameraFacing);

        if (cameraId >= 0)
        {
            try
            {
                camera = Camera.open( cameraId );
            }
            catch (RuntimeException e)
            {
                Log.i(CameraUtilities.class.getSimpleName(), "Camera failed to open", e);
            }
        }

        return camera;
    }

    public Camera openBackFacingCamera()
    {
        return openCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    public Camera openFrontFacingCamera()
    {
        return openCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
    }

    public void captureImage(final Activity context, final Camera camera, final Camera.PictureCallback callback)
    {
        if (camera != null && context != null)
        {
            Camera.Parameters cameraParameters = camera.getParameters();
            cameraParameters.setPictureFormat(ImageFormat.JPEG);

            List<Camera.Size> pictureSizes = cameraParameters.getSupportedPictureSizes();
            Camera.Size optimalPicture = pictureSizes.get(0);
            cameraParameters.setPictureSize(optimalPicture.width, optimalPicture.height);

            try
            {
                final SurfaceView dummy=new SurfaceView(context);

                camera.setPreviewDisplay(dummy.getHolder());
                camera.startPreview();

                camera.takePicture(null, null, callback);
            }
            catch (Exception e)
            {
                Log.i(CameraUtilities.class.getSimpleName(), "Failed to capture image", e);
            }
            finally
            {
                // ...
            }
        }
    }

    public boolean streamCameraPreview(Context context, Camera camera, int format, Camera.PreviewCallback callback)
    {
        boolean streamStarted = false;

        if (camera != null)
        {
            try
            {
                Camera.Parameters cameraParameters = camera.getParameters();

                List<Integer>supportedFormats = cameraParameters.getSupportedPreviewFormats();

                // If requested format supported
                if (supportedFormats.contains(format))
                {
                    cameraParameters.setPreviewFormat(format);

                    List<Camera.Size> previewSizes = cameraParameters.getSupportedPreviewSizes();
                    Camera.Size optimalPreviewSize = previewSizes.get(0);
                    cameraParameters.setPreviewSize(optimalPreviewSize.width, optimalPreviewSize.height);

                    camera.setParameters(cameraParameters);

                    SurfaceView dummy=new SurfaceView(context);

                    camera.setPreviewDisplay(dummy.getHolder());
                    camera.startPreview();

                    camera.setPreviewCallback(callback);

                    streamStarted = true;
                }
            }
            catch (Exception e)
            {
                Log.i(CameraUtilities.class.getSimpleName(), "Failed to preview image");
            }
        }

        return streamStarted;
    }
}
