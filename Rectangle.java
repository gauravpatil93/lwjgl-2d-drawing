/**
 * Rectangle.java -- Implements a simple rectangle with hard-coded shape.
 *                  Supports color, location, and size specification, 
 *
 * @author Gaurav Patil
 */
import static org.lwjgl.opengl.GL20.*;
import org.joml.Vector2f;

class Rectangle extends Shape2D
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
    public Rectangle()
    {
        shaderPgm = Shape2D.shaderProgram;    // copy current shaderProgram id

        float dx[] = { -0.3f, 0.3f, -0.3f, -0.3f, 0.3f, 0.3f };
        float dy[] = { 0.3f, -0.3f, -0.3f, 0.3f, 0.3f, -0.3f };

        setLocation( 0, 0 );
        setColor( 1, 1, 0 );   // Yellow is default color

        nVerts = 6;
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

    /*
       *This constructor takes the x-Location, y-Location, width and the height of 
       *the rectangle to be drawn.
       * @param1 xLocation
       * @param2 yLocation
       * @param3 width
       * @param4 height
    */

    public Rectangle(float xLocation, float yLocation, float width, float height) 
    {
        shaderPgm = Shape2D.shaderProgram;    // copy current shaderProgram id

        float dx[] = { -0.3f, 0.3f, -0.3f, -0.3f, 0.3f, 0.3f };
        float dy[] = { 0.3f, -0.3f, -0.3f, 0.3f, 0.3f, -0.3f };

        setLocation( xLocation, yLocation );
        setColor( 1, 1, 0 );
        setSize(width, height);

        nVerts = 6;
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
    
    /*
     * This constructor takes two parameters one is object holding width and height and the second
     * parameter is an object that takes the x and y location
     * @param1 width and height object
     * @param2 x and y location object
    */
    
    public Rectangle(Vector2f location, Vector2f dimensions) {
        shaderPgm = Shape2D.shaderProgram;    // copy current shaderProgram id

        //float dx[] = { -0.3f, 0.3f, -0.3f, -0.3f, 0.3f, 0.3f };
        //float dy[] = { 0.3f, -0.3f, -0.3f, 0.3f, 0.3f, -0.3f };
        
        float dx[] = {-0.4f, -0.4f, 0.8f, 0.8f, -0.4f, 0.8f};
        float dy[] = {0.4f, -0.4f, -0.4f, 0.4f, 0.4f, -0.4f};

        setLocation( location.x, location.y );
        setSize(dimensions.x, dimensions.y);
        
        setColor( 1, 1, 0 );

        nVerts = 6;
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
