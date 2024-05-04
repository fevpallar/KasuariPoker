uniform mat4 u_Matrix;
uniform float u_timespan;

attribute vec3 a_Position, a_Color, a_DirectionVector;
attribute float a_FireworkStartTime;

varying vec3 v_Color;
varying float v_ElapsedTime;

void main()
{
    v_Color = a_Color;
    v_ElapsedTime = u_timespan - a_FireworkStartTime;
    // control jatuhnya, 64 (arbitary), makin besar (makin keatas )
    float gravityFactor = v_ElapsedTime * v_ElapsedTime / 64.0;
    vec3 currentPosition = a_Position + (a_DirectionVector * v_ElapsedTime);
    currentPosition.y -= gravityFactor;
    gl_Position = u_Matrix * vec4(currentPosition, 1.0);
    gl_PointSize = 14.0;
}   
