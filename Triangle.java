/**
 * Triangle.java -- Implements a simple triangle with hard-coded shape.
 *                  Supports color, location, and size specification, 
 *
 * @author rdb
 * 09/06/2015 - derived loosely from earlier JOGL demos for OpenGL 2.
 * 12/27/16 rdb Modified to use LWJGL MemoryUtil tool rather than BufferUtils
 */
import static org.lwjgl.opengl.GL20.*;

class Triangle extends Shape2D
{
    //-------------------- constructor -----------------------------
    /**
     * The instance needs to know the shaderProgram to be used, in order
     *    to implement color, location and size changes.
     * It might have been better to have the shaderProgram be provided
     *    by a class variable in a GLSL support class.
     *
     * @param shaderProgram the shader program to be used.
     *
     */
    public Triangle()
    {
        shaderPgm = Shape2D.shaderProgram;    // copy current shaderProgram id

        float dx[] = { -0.25f, 0.25f, 0.0f };
        float dy[] = { 0.0f, 0.0f, 0.5f };

        setLocation( 0, 0 );
        setColor( 1, 1, 0 );   // Yellow is default color

        nVerts = 3;
        coords = new float[ nVerts * 2 ];
        
        int c = 0;
        for ( int i = 0; i < nVerts; i++ )
        {
            coords[ c++ ] = dx[ i ];
            coords[ c++ ] = dy[ i ];
        }
        makeBuffers();
    
        if ( unif_vColor == -1 )
        {
            unif_vColor = glGetUniformLocation( shaderPgm, "vColor" );
            unif_model  = glGetUniformLocation( shaderPgm, "model" );
        }
    }
}
