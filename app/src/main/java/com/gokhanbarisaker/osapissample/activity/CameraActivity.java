package com.gokhanbarisaker.osapissample.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.gokhanbarisaker.osapis.utility.CameraUtilities;
import com.gokhanbarisaker.osapissample.R;

import java.io.ByteArrayOutputStream;

public class CameraActivity extends ActionBarActivity {

    ImageView imageView = null;
    Camera camera = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        imageView = (ImageView) findViewById(R.id.camera_imageview);
    }

    @Override
    protected void onStart() {
        super.onStart();

        camera = CameraUtilities.sharedInstance().openBackFacingCamera();
        boolean cameraStarted = CameraUtilities.sharedInstance().streamCameraPreview(CameraActivity.this, camera, ImageFormat.NV21, new Camera.PreviewCallback(){

            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {

                // Convert to JPG
                Camera.Size previewSize = camera.getParameters().getPreviewSize();
                YuvImage yuvimage=new YuvImage(data, ImageFormat.NV21, previewSize.width, previewSize.height, null);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                yuvimage.compressToJpeg(new Rect(0, 0, previewSize.width, previewSize.height), 80, baos);
                data = baos.toByteArray();

                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                imageView.setImageBitmap(bitmap);
            }
        });

        Toast.makeText(CameraActivity.this, "Camera " + ((cameraStarted)?("succeed"):("failed")), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (camera != null)
        {
            camera.stopPreview();
            camera.release();
        }
    }
}
