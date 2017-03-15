import java.util.ArrayList;
import org.joml.Vector2f;


public class SceneMaker {
	
	public ArrayList<Shape2D> shapes = new ArrayList<Shape2D>();
	Scene scene = new Scene();
	
	public SceneMaker() {
		
        Triangle triangle = new Triangle();
        triangle.setLocation( -0.6f, -0.2f);
        triangle.setColor( 0, 1, 1 );
        scene.shapes.add( triangle );
	
		/*
         * The following code tests the Rectangle class
         */
        
        // Rectangle using the default constructor
        Rectangle rectangle1 = new Rectangle();
        rectangle1.setLocation(-1.7f, 1.7f);
        rectangle1.setColor( 1, 1, 0 );
        rectangle1.setSize(0.5f, 0.5f);
        scene.shapes.add(rectangle1);
        
        // Rectangle using the constructor taking the x-location, y-location
        // width and height
        Rectangle rectangle2 = new Rectangle(-1.2f, 1.7f, 0.7f, 0.7f);
        rectangle2.setColor( 0, 0, 1 );
        scene.shapes.add(rectangle2);
        
        // Rectangle using the constructor taking the location and dimensions in
        // the form of Vector2f objects.
        Vector2f location = new Vector2f();
        Vector2f dimensions = new Vector2f();
        location.add(-0.5f, 1.7f);
        dimensions.add(0.9f, 0.9f);
        
        Rectangle rectangle3 = new Rectangle(location, dimensions);
        rectangle3.setColor( 0, 1, 0 );
        scene.shapes.add(rectangle3);
        
        
        
        // Testing the Polygon Class
          
        float x_coordinates[] = {-0.4f, -0.4f, 0.4f, -0.4f, 0.4f, 0.4f, 0.9f, 0.4f, 0.4f};
        float y_coordinates[] = {0.4f, -0.4f, -0.4f, 0.4f, 0.4f, -0.4f, 0, 0.4f, -0.4f};
        
        Polygon pentagon = new Polygon(x_coordinates, y_coordinates);
        pentagon.setLocation(-1.5f, 0.8f);
        pentagon.setColor(1, 1, 0);
        scene.shapes.add(pentagon);
        
        float x_co[] = {0, -0.5f, 0.5f, 0.8f, 0, 0.5f, 0.8f, 0.5f, 0, 0.5f, 0, 0};
        float y_co[] = {0, 0.5f, 0.5f, 0, 0, 0.5f, 0, -0.5f, 0, -0.5f, -0.8f, 0};
        
        Polygon rocket = new Polygon(x_co, y_co);
        rocket.setLocation(0.2f, 0.6f);
        rocket.setColor(255, 0, 255);
        scene.shapes.add(rocket);
        
        float starx[] = {0.5f, 0, -0.5f, 0 , 0.5f, -0.5f};
        float stary[] = {0, 0.5f, 0,  -0.5f,  0,  0};
        
        Polygon star = new Polygon(starx, stary);
        star.setColor(1, 0, 0);
        star.setLocation(-1.5f, -0.2f);
        scene.shapes.add(star);
                
        Point point_object = new Point();
        point_object.add(0.5f, 0f);
        point_object.add(0, 0.5f);
        point_object.add(-0.5f, 0);
        point_object.add(0, -0.5f);
        point_object.add(0.5f, 0);
        point_object.add(-0.5f, 0);
        
        Polygon polygon3 = new Polygon(point_object);
        polygon3.setLocation(-1, -0.7f);
        polygon3.setColor(255, 0, 255);
        polygon3.setSize(1, 1);
        scene.shapes.add(polygon3);
	}
	
	public Scene getScene() {
		return scene;
	}
}