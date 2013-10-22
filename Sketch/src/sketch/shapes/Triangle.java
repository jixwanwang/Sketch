package sketch.shapes;

import java.awt.Shape;
import java.awt.geom.Path2D;

public class Triangle extends SingleSketchShape {

	@Override
	public ShapeState getState() {
		return new ShapeState(ShapeType.TRIANGLE, location, size, rotation, color, borderColor, borderWidth);
	}

	@Override
	protected Shape getShape() {
		Path2D.Double _path = new Path2D.Double();
		Vec2i v1 = location.plus(size.x/2, 0);
		Vec2i v2 = location.plus(0, size.y);
		Vec2i v3 = location.plus(size);
		_path.moveTo(v1.x, v1.y);
		_path.lineTo(v2.x, v2.y);
		_path.lineTo(v3.x, v3.y);
		_path.lineTo(v1.x, v1.y);
		return _path;
	}

}
