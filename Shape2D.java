/* 
 * Shape2D Class
 * Authors: Dan Bergeron & Stephen Dunn
 * Date: 8/8/2014
 *
 * 12/27/16 rdb Modified to use LWJGL MemoryUtil tool rather than BufferUtils
 */
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glUniform4fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;


import org.lwjgl.system.MemoryUtil;

import java.nio.*;

abstract public class Shape2D
{  
	//---------------------- class variables    ----------------------
	public static int shaderProgram;    // the shader program id
	
	//---------------------- instance variables -----------------------
    protected float xLoc, yLoc;
    protected float xSize = 1.0f, ySize = 1.0f;
    protected float red, green, blue;
    protected FloatBuffer colorBuf = null;
    static protected int unif_model = -1;  // uniform id for model matrix
    static protected int unif_vColor = -1; // uniform var for vertex color
    protected int     vaoId; // not sure this needs to be instance var
    protected int     bufferId;       // VBO id for vertices
    protected int     attrLoc_vpos;   // VAO id for vPosition   
    protected int     shaderPgm;    // shader id for this triangle
    protected float[] coords; 
    protected int     nVerts;
    
    
    //------------------------ constructor ---------------------------
    /**
     * Constructor just creates a color object and initializes it to a 
     *   default (Black) color.
     */
    public Shape2D()
    {
        colorBuf = MemoryUtil.memAllocFloat( 4 );
        setColor( 0, 0, 0 );
    }
    //------------------------ finalize -----------------------------
    /**
     * The constructor creates Buffer object for color via MemoryUtil. 
     * These are not * managed by the Java memory manager, so we have to do it.
     */
    @Override
	public void finalize()
    {
        MemoryUtil.memFree( colorBuf );
    }
    
    //---------------------  setLocation -----------------------------
    /**
     * Set the location of the Shape2D.
     */
    void setLocation( float x, float y )
    {
        xLoc = x; yLoc = y;
    }
    
    float getX() 
    { 
        return xLoc; 
    }
    float getY() 
    { 
        return yLoc; 
    }
    
    void setColor( float r, float g, float b )
    {
        red = r; green = g; blue = b;
        colorBuf = MemoryUtil.memAllocFloat( 4 );
        colorBuf.put( r ).put( g ).put( b ).put( 1 ).flip();
    }
    
    void setSize( float xs, float ys )
    {
        xSize = xs; ySize = ys;
    }
    
    //------------------------------ makeBuffers -----------------------
    /**
    *  Create VertexArrayObject and VertexBufferObject.
    */
    void makeBuffers()
    {
        // ---- set up to transfer points to gpu
        // 1. Create a vertex array object
        this.vaoId = glGenVertexArrays();
        glBindVertexArray( vaoId );  // binding => this VAO is "current" one
        
        // 2. Create a vertex buffer
        this.bufferId = glGenBuffers();
        // make it the current buffer
        glBindBuffer( GL_ARRAY_BUFFER, this.bufferId ); 
    
        FloatBuffer fbuf = MemoryUtil.memAllocFloat( coords.length );
        fbuf.put( coords ).flip();
    
        glBufferData( GL_ARRAY_BUFFER, fbuf, GL_STATIC_DRAW );
    
        // define a variable, "vPosition"
        // Note: could use predefined locations glBindAttrLocation
        attrLoc_vpos = glGetAttribLocation( shaderPgm, "vPosition" );
        glEnableVertexAttribArray( attrLoc_vpos );
    
        glVertexAttribPointer( attrLoc_vpos, 2, GL_FLOAT, false, 0, 0L );
        
        // debug: test if correct data is in the buffer
        //   Could add code here to define a float array and read
        //   the VertexAttribArray just uploaded and compare it to the
        //   coords array -- or just print it.
    
        glBindVertexArray( 0 );               // unbind the VAO
        glBindBuffer( GL_ARRAY_BUFFER, 0 );   // unbind the VBO
    }
    
    
    //------------------------- redraw ----------------------------
    /**
     * Update the specifications for the shape.
     */
	void redraw()
    {
        // Simple modeling: only size and location. 
        //   we can write down the desired matrix.
        // This specification and the FloatBuffer we create does NOT have
        //   to be done on every re-draw; it only needs to be done when
        //   the location or size changes: setLocation or setSize.
        //   These methods are defined in Shape2D, but could be overridden
        //   here and modelBuf could be an instance variable.
        //
        float[] model = { xSize,   0,   0, 0,  
                           0,    ySize, 0, 0,  
                           0,      0,   1, 0,  
                          xLoc,  yLoc,  0, 1 };

        // MemoryUtil is an lwjgl utility class
        FloatBuffer modelBuf = MemoryUtil.memAllocFloat( model.length );
        modelBuf.put( model ).flip();
 
        glUniformMatrix4fv( unif_model, false, modelBuf );
        glUniform4fv( unif_vColor, colorBuf );
 
        glBindVertexArray( vaoId );
        glDrawArrays( GL_TRIANGLES, 0, nVerts );
        glBindVertexArray( 0 );

        MemoryUtil.memFree( modelBuf ); // can free vertex buffer space
    }
}


