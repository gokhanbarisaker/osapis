package com.gokhanbarisaker.osapis.utility;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
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

    public void captureImage(final SurfaceHolder holder, final Camera camera, final Camera.PictureCallback callback)
    {
        if (camera != null && holder != null)
        {
            Camera.Parameters cameraParameters = camera.getParameters();
            cameraParameters.setPictureFormat(ImageFormat.JPEG);

            List<Camera.Size> pictureSizes = cameraParameters.getSupportedPictureSizes();
            Camera.Size optimalPicture = getOptimalPreviewSize(pictureSizes, camera.getParameters().getPictureSize());
            cameraParameters.setPictureSize(optimalPicture.width, optimalPicture.height);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
            {
                holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            }

            try
            {
                camera.setPreviewDisplay(holder);
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

    public boolean streamCameraPreview(final SurfaceHolder holder, final Camera camera, int format, final Camera.PreviewCallback callback)
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
                    Camera.Size optimalPreviewSize = getOptimalPreviewSize(previewSizes, camera.getParameters().getPictureSize());
                    cameraParameters.setPreviewSize(optimalPreviewSize.width, optimalPreviewSize.height);

                    camera.setParameters(cameraParameters);

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
                    {
                        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                    }

                    camera.setPreviewDisplay(holder);
                    camera.startPreview();

                    camera.setPreviewCallback(callback);

                    streamStarted = true;
                }
                else
                {
                    Log.i(CameraUtilities.class.getSimpleName(), format + " format not supported for camera preview on this device");
                }
            }
            catch (Exception e)
            {
                Log.i(CameraUtilities.class.getSimpleName(), "Failed to preview image", e);
            }
        }

        return streamStarted;
    }

    private SurfaceView createSurfaceView(final Context context)
    {
        SurfaceView surfaceView=new SurfaceView(context);
        SurfaceHolder holder = surfaceView.getHolder();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
        {
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        return surfaceView;
    }

    private Camera.Size getOptimalPreviewSize(final List<Camera.Size> sizes, final Camera.Size targetSize)
    {
        final float maxRatioTolerance = 0.1f;
        final float targetRatio = (float) targetSize.width / (float) targetSize.height;

        Camera.Size optimalSize = sizes.get(0);
        int minHeightDeviation = Integer.MAX_VALUE;

        // Try to find an size match aspect ratio and size
        float ratio = .0f;
        int heightDeviation = 0;

        for (Camera.Size size : sizes) {
            ratio = (float) size.width / (float) size.height;

            if (Math.abs(ratio - targetRatio) <= maxRatioTolerance)
            {
                heightDeviation = Math.abs(size.height - targetSize.height);

                if (heightDeviation < minHeightDeviation)
                {
                    optimalSize = size;
                    minHeightDeviation = heightDeviation;
                }
            }
        }

        return optimalSize;
    }
}
