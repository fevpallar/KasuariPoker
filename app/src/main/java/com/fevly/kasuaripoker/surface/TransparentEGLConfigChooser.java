package com.fevly.kasuaripoker.surface;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.util.Log;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;

public class TransparentEGLConfigChooser implements EGLConfigChooser {

    private int[] configAttribs = {
            EGL10.EGL_RED_SIZE, 8,
            EGL10.EGL_GREEN_SIZE, 8,
            EGL10.EGL_BLUE_SIZE, 8,
            EGL10.EGL_ALPHA_SIZE, 8,
            EGL10.EGL_DEPTH_SIZE, 16,
            EGL10.EGL_NONE
    };

    @Override
    public EGLConfig chooseConfig(EGL10 egl, javax.microedition.khronos.egl.EGLDisplay display) {
        int[] numConfigs = new int[1];
        egl.eglChooseConfig(display, configAttribs, null, 0, numConfigs);

        int numConfigsFound = numConfigs[0];
        if (numConfigsFound <= 0) {
            Log.e("TransparentEGLConfig", "No EGL configs found!");
            return null;
        }

        EGLConfig[] configs = new EGLConfig[numConfigsFound];
        egl.eglChooseConfig(display, configAttribs, configs, numConfigsFound, numConfigs);

        // Iterate through the available configs and choose the first one with an alpha channel
        for (EGLConfig config : configs) {
            int[] alphaSize = new int[1];
            egl.eglGetConfigAttrib(display, config, EGL10.EGL_ALPHA_SIZE, alphaSize);
            if (alphaSize[0] > 0) {
                return config;
            }
        }

        return null; // No config with alpha channel found
    }
}
