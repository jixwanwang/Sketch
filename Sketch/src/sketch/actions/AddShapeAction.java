package sketch.actions;

import sketch.panels.DrawPanel;
import sketch.shapes.SketchShape;
import sketch.shapes.Vec2f;
import sketch.shapes.Vec2i;

/**
 * Add a single shape to the drawing panel. This is different from the duplication action.
 * 
 * @author Jixuan Wang
 */
public class AddShapeAction extends DPModifyingAction{
	private Vec2i diff;
	
	public AddShapeAction(DrawPanel p){
		super(p);
	}

	@Override
	public void start(Vec2i mousePos){
		super.start(mousePos);
		shapesAffected.get(0).setLocation(new Vec2f(mousePos));
	}
	
	@Override
	public void update(Vec2i mousePos){
		diff = mousePos.minus(start);
		shapesAffected.get(0).setSize(new Vec2f(diff));
	}
	
	@Override
	public void apply() {
		dp.addShapeAndSelect(shapesAffected.get(0));
	}

	@Override
	public void undo() {
		dp.removeShape(shapesAffected.get(0));
	}

	public SketchShape getShape() {
		return shapesAffected.get(0);
	}
}
