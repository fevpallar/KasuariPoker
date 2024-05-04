
package com.fevly.kasuaripoker.animation;

import static android.opengl.Matrix.multiplyMV;
import static android.opengl.Matrix.setRotateEulerM;

import com.fevly.kasuaripoker.animation.Geometry.Point;
import com.fevly.kasuaripoker.animation.Geometry.Vector;

import java.util.Random;

public class FireworkShooter {
    private final Point position;
    private final Vector direction;
    private final int color;

    private final float angleVariance; // varian (sudut)
    private final float speedVariance;  // varian (speed)

    private final Random random = new Random();

    private float[] rotationMatrix = new float[16];
    private float[] directionVector = new float[4];
    private float[] resultVector = new float[4];


    public FireworkShooter(
            Point position, Vector direction, int color,
            float angleVarianceInDegrees, float speedVariance) {
        this.position = position;
        this.direction = direction;
        this.color = color;
        this.angleVariance = angleVarianceInDegrees;
        this.speedVariance = speedVariance;

        directionVector[0] = direction.x;
        directionVector[1] = direction.y;
        directionVector[2] = direction.z;
    }

    public void addParticles(FireworkSystem particleSystem, float currentTime,
                             int count) {
        for (int i = 0; i < count; i++) {


            // alter sudut terlemparnya partikel2 firework
            // di ketiga arah (x,y,z)
            // sudut yg acak kasih efek 'air mancur'
            setRotateEulerM(rotationMatrix, 0,
                    (random.nextFloat() - 0.5f) * angleVariance,
                    (random.nextFloat() - 0.5f) * angleVariance,
                    (random.nextFloat() - 0.5f) * angleVariance);

            multiplyMV(
                    resultVector, 0,
                    rotationMatrix, 0,
                    directionVector, 0);

            float speedAdjustment = 1f + random.nextFloat() * speedVariance;

            Vector thisDirection = new Vector(
                    resultVector[0] * speedAdjustment*0.5f,// perlambat di sumbuh x
                    resultVector[1] * speedAdjustment,
                    resultVector[2] * speedAdjustment);

            particleSystem.addParticle(position, color, thisDirection, currentTime);
        }
    }
}
