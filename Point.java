import java.util.ArrayList;
import org.joml.Vector2f;

public class Point {
	ArrayList<Vector2f> points = new ArrayList<Vector2f>();
	
	public void add(float x, float y) {
		Vector2f point = new Vector2f();
		point.add(x, y);
		points.add(point);
	}
	
	public ArrayList<Vector2f> getListofPoints() {
		return points;
	}
}
