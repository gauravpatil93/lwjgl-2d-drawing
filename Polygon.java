/**
 * Polygon.java -- Implements a polygon with hard-coded shape.
 *                 Supports color, location, and size specification.
 *
 * @author Gaurav Patil
 */
import static org.lwjgl.opengl.GL20.*;
import org.joml.Vector2f;

class Polygon extends Shape2D
{   
    //-------------------- constructor -----------------------------
    /**
     * The instance needs to know the shaderProgram to be used, in order
     * to implement color, location and size changes.
     * It might have been better to have the shaderProgram be provided
     * by a class variable in a GLSL support class.
     */
    public Polygon()
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
     * This constructor takes the arrays of x and y coordinates
     * @param1 array of x_coordinates
     * @param2 array of y_coordinates
     */

    public Polygon(float[] x_coordinates, float[] y_coordinates) {

        shaderPgm = Shape2D.shaderProgram;    // copy current shaderProgram id

        setLocation( 0, 0 );
        setColor( 1, 1, 0 );   // Yellow is default color

        nVerts = x_coordinates.length;
        coords = new float[ nVerts * 2 ];
        
        int c = 0;
        for ( int i = 0; i < nVerts; i++ )
        {
            coords[ c++ ] = x_coordinates[ i ];
            coords[ c++ ] = y_coordinates[ i ];
        }
        makeBuffers();
    
        if ( unif_vColor == -1 )
        {
            unif_vColor = glGetUniformLocation( shaderPgm, "vColor" );
            unif_model  = glGetUniformLocation( shaderPgm, "model" );
        }
    }
    
    /*
     * This constructor takes an object i.e a collection of points.
    */
    
    public Polygon(Point points) {

        shaderPgm = Shape2D.shaderProgram;    // copy current shaderProgram id

        setLocation( 0, 0 );
        setColor( 1, 1, 0 );   // Yellow is default color

        nVerts = points.getListofPoints().size();
        coords = new float[ nVerts * 2 ];
        
        int c = 0;
        for (Vector2f obj : points.getListofPoints())
        {
            coords[ c++ ] = obj.x;
            coords[ c++ ] = obj.y;
        }
        makeBuffers();
    
        if ( unif_vColor == -1 )
        {
            unif_vColor = glGetUniformLocation( shaderPgm, "vColor" );
            unif_model  = glGetUniformLocation( shaderPgm, "model" );
        }
    }
}
