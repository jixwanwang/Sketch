package sketch.logic;

import sketch.shapes.ShapeState;
import sketch.shapes.SketchShape;
import sketch.shapes.Vec2f;
import sketch.shapes.Vec2i;

public class RotateAction extends SketchAction {
	private double rotation;
	
	@Override
	public void update(Vec2i mousePos) {
		//can only be rotating one shape
		SketchShape s = shapesAffected.get(0);
		Vec2f _start = new Vec2f(start.minus(s.getCenter()));
		Vec2f _end = new Vec2f(mousePos.minus(s.getCenter()));
		rotation = - Math.acos(_start.dot(_end)/_start.mag()/_end.mag())*Math.signum(_start.cross(_end));
		apply();
	}

	@Override
	public void apply() {
		SketchShape s = shapesAffected.get(0);
		ShapeState ss = originalStates.get(0);
		s.setRotation(ss.rotation + rotation);
	}

	@Override
	public void undo() {
		SketchShape s = shapesAffected.get(0);
		ShapeState ss = originalStates.get(0);
		s.setRotation(ss.rotation);
	}

}
