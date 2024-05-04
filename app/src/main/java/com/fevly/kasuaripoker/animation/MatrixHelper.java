
package com.fevly.kasuaripoker.animation;

public class MatrixHelper {
    /*========================================================================
     projection matrix yg sering dipakai
    untuk adjust 'field of vision (FOV)' dan 'aspect ratio'
        itu dlm bentuk :


        a/aspect				0				0
        0			a/aspect 	0  				0
        0			0			-(f+n/f-n)		-(2fn)/(f-n)
        0			0			-1				0

 Inilah faktor dari perpespektif projection itu (matriksnya)


Nilai 'w' tdk boleh diset manual karena perspektif juga
pengaruh dikedalaman (layar monitor belum tentu sama)
 ==================================================================*/
    public static void setPerspectiveMatrix(float[] m, float yFovInDegrees, float aspect,
                                            float n, float f) {
        final float angleInRadians = (float) (yFovInDegrees * Math.PI / 180.0);
        final float focalPoint = (float) (1.0 / Math.tan(angleInRadians / 2.0));

        m[0] = focalPoint / aspect;
        m[1] = 0f;
        m[2] = 0f;
        m[3] = 0f;

        m[4] = 0f;
        m[5] = focalPoint;
        m[6] = 0f;
        m[7] = 0f;

        m[8] = 0f;
        m[9] = 0f;
        m[10] = -((f + n) / (f - n));
        m[11] = -1f;
        
        m[12] = 0f;
        m[13] = 0f;
        m[14] = -((2f * f * n) / (f - n));
        m[15] = 0f;        
    }
}
