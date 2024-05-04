
package com.fevly.kasuaripoker.animation;

import static android.opengl.GLES20.*;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class VertexArray {
    private final FloatBuffer vertBuffer;

    public VertexArray(float[] vertexData) {
        vertBuffer = ByteBuffer
            .allocateDirect(vertexData.length * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(vertexData);
    }   
        
    public void setVertexAttribPointer(int dataOffset, int attributeLocation,
        int componentCount, int stride) {        
        vertBuffer.position(dataOffset);
        glVertexAttribPointer(attributeLocation, componentCount,
            GL_FLOAT, false, stride, vertBuffer);
        glEnableVertexAttribArray(attributeLocation);
        
        vertBuffer.position(0);
    }

    public void updateBuffer(float[] vertexData, int start, int count) {
       vertBuffer.position(start);
       vertBuffer.put(vertexData, start, count);
       vertBuffer.position(0);
    }
}
