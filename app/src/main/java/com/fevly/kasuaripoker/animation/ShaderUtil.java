
package com.fevly.kasuaripoker.animation;

import static android.opengl.GLES20.*;

import android.util.Log;

public class ShaderUtil {
    private static final String TAG = "ShaderUtil";


    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }


    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }


    private static int compileShader(int type, String shaderCode) {
        final int shaderObjectId = glCreateShader(type);

        if (shaderObjectId == 0) {

            return 0;
        }

        glShaderSource(shaderObjectId, shaderCode);

        glCompileShader(shaderObjectId);

        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS,
            compileStatus, 0);


        if (compileStatus[0] == 0) {
            glDeleteShader(shaderObjectId);


            return 0;
        }

        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {

        final int programObjectId = glCreateProgram();

        if (programObjectId == 0) {

            return 0;
        }


        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);
        glLinkProgram(programObjectId);

        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS,
            linkStatus, 0);

        if (linkStatus[0] == 0) {
            glDeleteProgram(programObjectId);

            return 0;
        }
        
        return programObjectId;
    }


    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);
        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS,
            validateStatus, 0);
       

        return validateStatus[0] != 0;
    }


    public static int buildProgram(String vertexShaderSource,
        String fragmentShaderSource) {
        int program;
        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);
        program = linkProgram(vertexShader, fragmentShader);
            validateProgram(program);
        return program;
    }
}
