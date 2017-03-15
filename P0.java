/**
 * BasicLWJGL.java - demonstrates some of the most fundamental concepts
 *                   of programming in LWJGL3 within the context of an
 *                   object oriented data management methodology.
 *       This simple demo is not considered with efficient use of GPU 
 *       computational power, nor in minimizing data transfer between the
 *       CPU and GPU. That will come later.
 *
 * @author rdb
 * 09/06/15 version 1.0
 *          This version is not a great example of good style or organization 
 *          or cleanliness! But it works. 
 * 09/08/15 version 1.2
 *          Improved testing for OS to determine what OpenGL version can be
 *             used and to edit GLSL version specification when running on
 *             Linux machines
 *          version 1.2b 
 *             added warning message if input shaders are not version 330
 * 09/09/15 version 1.2c
 *             moved keyboard handler setup to its own method
 * 09/13/15 version 2.0
 *             moved and refactored openWindow, makeShaderProgram to 
 *             UtilsLWJGL (derived from DemoUtils).
 * 11/27/16 version 2.1
 *              Modified for new LWJGL 3.1 conventions
 *             
 * This program makes use of code from demos found at lwjgl.org accessed as
 * lwjgl3-demo-master and downloaded in late August 2015. It also uses a
 * slightly modified complete class from that package, UtilsLWJGL.
 */
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import org.lwjgl.system.MemoryUtil;
import java.nio.*;
import java.io.*;
import java.util.ArrayList;

public class P0
{
    //---------------------- instance variables ----------------------
    // window size parameters
    int windowW = 600;
    int windowH = 640;
    
    // shader program id.
    int shaderPgm; 

    // storage for shapes created
    ArrayList<Shape2D> shapes = new ArrayList<Shape2D>();
    
    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
    
    // The window handle
    private long window;
    
    //--------------- Constructor  ----------------------------------------
    public P0()
    {
        // Setup error callback to print to System.err.
        errorCallback = GLFWErrorCallback.createPrint( System.err );
        errorCallback.set(); // make it active

        window = UtilsLWJGL.openWindow( "BasicLWJGL", windowW, windowH );

        // The next line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities(); 
        
        try 
        {
            shaderPgm = UtilsLWJGL.makeShaderProgram( "basicLWJGL.vsh", 
                                                      "basicLWJGL.fsh" );
            glUseProgram( shaderPgm ); 
            Shape2D.shaderProgram = shaderPgm;  // tell the Shape class about it
        } 
        catch ( IOException iox )  
        {
            System.err.println( "Shader construction failed." );
            System.exit( -1 );
        }
        setupView();
        //makeScene();
        setupKeyHandler();
        renderLoop();
            
        // Clean up glfw stuff
		glfwFreeCallbacks( window );
		glfwDestroyWindow( window );
		glfwSetErrorCallback( null ).free();
		glfwTerminate();
    }
        
    //--------------------- setupKeyHandler ----------------------
    /**
     * void setupKeyHandler
     */
    private void setupKeyHandler()
    {
        // Setup a key callback. It is called every time a key is pressed, 
        //      repeated or released.
        glfwSetKeyCallback( window, 
            keyCallback = new GLFWKeyCallback()
            {
               @Override
                public void invoke( long keyWindow, int key, 
                                    int scancode, int action, int mods )
                {
                   // escape key means terminate; set flag; test in render loop
                   if (  key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE 
                         || key == GLFW_KEY_Q && action == GLFW_RELEASE )
                       glfwSetWindowShouldClose( window, true );
                   if ( key == GLFW_KEY_P ) {
                	   glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
                   }
            }
        });
    }
    
    //-------------------------- loop ----------------------------
    /**
     * Loop until user closes the window or kills the program.
     */
    private void renderLoop() 
    {
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( ! glfwWindowShouldClose( window ) )
        {
            // redraw the frame
            redraw();

            glfwSwapBuffers( window ); // swap the color buffers

            // Wait for window events. The key callback above will only be
            // invoked during this call.
            // lwjgl demos use glfwPollEvents(), which uses nearly 2X
            //    the cpu time for simple demos as glfwWaitEvents.
            glfwWaitEvents();
        }
    }
    
    //------------------ makeScene --------------------------
    /**
     * Creates the scene.
     */
    private void makeScene()
    {
    	GL11.glClearColor( 0.0f, 0.0f, 0.0f, 0.0f );
    	
    }

    //------------------ setupView --------------------------
    /**
     * We have a constant viewing and projection specification.
     * Can define it once and send the spec to the shader.
     */
    void setupView()
    {
        // equivalent to glOrtho2D( -2, 2, -2, 2 )
        float[] pXv={ 0.5f, 0,   0, 0,  
                        0, 0.5f, 0, 0,  
                        0,  0,   1, 0,  
                        0,  0,   0, 1 };

        // Best LWJGL practice is to use C-like memory allocation outside
        //    Java's storage management; done with MemoryUtil functions.
        FloatBuffer pXvBuf = MemoryUtil.memAllocFloat( pXv.length );

        pXvBuf.put( pXv ).flip();

        /**********
        // default: equivalent to glOrtho2D( -1, 1, -1, 1 )
        float pXv[]={ 1,  0, 0, 0,  
                           0,  1, 0, 0,  
                           0,  0, 1, 0,  
                           0,  0, 0, 1 };
        /**************/
        
        //--- now push the composite into a uniform var in vertex shader
        //  this id does not need to be global since we never change 
        //  projection or viewing specs in this program.
        int unif_pXv = glGetUniformLocation( shaderPgm, "projXview" );

        // Transfer the matrix to the GPU
        glUniformMatrix4fv( unif_pXv, false, pXvBuf );

        // Free the non-Java memory used
        MemoryUtil.memFree( pXvBuf );  
    }
    //------------------------ redraw() ----------------------------
    void redraw()
    {
        // clear the framebuffer
        glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT ); 

        for (Shape2D s: shapes)
            s.redraw();

        GL11.glFlush();
    }
    
    //------------------------- main ----------------------------------
    /**
     * main constructions the object
     */
    
    public static void main( String args[] )
    {
        new P0();
    }
}
