package com.fevly.kasuaripoker.surface;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.widget.FrameLayout;


import com.fevly.kasuaripoker.animation.FireworkRenderer;

/*==========================================
Author : Fevly Pallar
contact : fevly.pallar@gmail.com
=========================================*/
public class CustomeSurfaceBuilder {

    private Context context;


    private GLSurfaceView glSurfaceView;

    public CustomeSurfaceBuilder(Context context) {
        this.context = context;

    }


    public GLSurfaceView setAndGetGlSurfaceView(int colorFlag) {
        glSurfaceView = new GLSurfaceView(this.context);
        glSurfaceView.setEGLContextClientVersion(2); // GLES 2.0
        FireworkRenderer fireworkRenderer = null;
        /*if (colorFlag == 0) {
            fireworkRenderer = new FireworkRenderer(this.context);
            fireworkRenderer.colorThemeFlag = 0;
        }*/
            fireworkRenderer = new FireworkRenderer(this.context);



        glSurfaceView.setEGLConfigChooser(new TransparentEGLConfigChooser());
        glSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);


        glSurfaceView.setRenderer(fireworkRenderer);
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);


        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        glSurfaceView.setLayoutParams(layoutParams);


        glSurfaceView.setZOrderOnTop(true);

// Set the background color to transparent
        glSurfaceView.setBackgroundColor(Color.TRANSPARENT);


        return glSurfaceView;
    }
}
