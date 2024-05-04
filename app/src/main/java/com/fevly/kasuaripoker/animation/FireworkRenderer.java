
package com.fevly.kasuaripoker.animation;

import static android.opengl.GLES20.*;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView.Renderer;


import com.fevly.kasuaripoker.R;

import java.util.Random;
import com.fevly.kasuaripoker.animation.Geometry.Point;
import com.fevly.kasuaripoker.animation.Geometry.Vector;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class FireworkRenderer implements Renderer  {
    private final Context context;

    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];    

    private FireworkShaderProgram fireworkProgram;
    private FireworkSystem fireworkSystem;
    private FireworkShooter fireworkShooter;

    private final Random random = new Random();

    private long globalStartTime;
    private int texture;

    public FireworkRenderer(Context context) {
        this.context = context;
    }
    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        
        fireworkProgram = new FireworkShaderProgram(context);
        fireworkSystem = new FireworkSystem(900);


        globalStartTime = System.nanoTime();
        
        final Vector particleDirection = new Vector(0f, 0.5f, 0f);
        
        final float angleVarianceInDegrees = 5f;
        final float speedVariance = 1f;

        fireworkShooter = new FireworkShooter(
            new Point(-1f, 0f, 0f),
            particleDirection,
            Color.rgb(255, 50, 5),
            angleVarianceInDegrees,
            speedVariance);


        
        texture = TextureUtil.loadTexture(context, R.drawable.firework_texture);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {                
        glViewport(0, 0, width, height);        

        MatrixHelper.setPerspectiveMatrix(projectionMatrix, 45, (float) width
            / (float) height, 1f, 10f);
        
        setIdentityM(viewMatrix, 0);
        translateM(viewMatrix, 0, 0f, -1.5f, -5f);   
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0,
            viewMatrix, 0);
    }

private FireworkShooter createRandomColorParticleShooter(float x, int baseColor) {
    final Vector particleDirection = new Vector(0f, 0.5f, 0f);
    final float angleVarianceInDegrees = 65f;
    final float speedVariance = 1f;

    int red = 0, green = 0, blue = 0;

    int colorChoice = random.nextInt(3);
    switch (colorChoice) {
        case 0: // Red
            red = 255;
            green = random.nextInt(150);
            blue = random.nextInt(50);
            break;
        case 1: // Orange
            red = 255;
            green = random.nextInt(100) + 100;
            blue = random.nextInt(50);
            break;
        case 2: // light magenta
            red = 255;
            green = random.nextInt(50);
            blue = 255;
    }

    int color = Color.rgb(red, green, blue);

    return new FireworkShooter(
            new Point(x, 0f, 0f),
            particleDirection,
            baseColor + color,
            angleVarianceInDegrees,
            speedVariance
    );
}




    @Override
    public void onDrawFrame(GL10 glUnused) {        
        glClear(GL_COLOR_BUFFER_BIT);
        
        float currentTime = (System.nanoTime() - globalStartTime) / 1000000000f;
        
        fireworkShooter.addParticles(fireworkSystem, currentTime, 5);

        fireworkShooter = createRandomColorParticleShooter(-1f, Color.RED);
        fireworkProgram.useProgram();
        fireworkProgram.setUniforms(viewProjectionMatrix, currentTime, texture);
        fireworkSystem.bindData(fireworkProgram);
        fireworkSystem.draw();
    }
}