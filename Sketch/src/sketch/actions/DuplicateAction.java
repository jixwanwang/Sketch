package sketch.actions;

import java.util.ArrayList;
import java.util.List;

import sketch.panels.DrawPanel;
import sketch.shapes.SketchShape;
import sketch.shapes.Vec2f;
import sketch.shapes.Vec2i;

public class DuplicateAction extends DPModifyingAction {
	private final Vec2f OFFSET = new Vec2f(15,15);
	private List<SketchShape> duplicates;
	
	public DuplicateAction(DrawPanel p) {
		super(p);
	}

	@Override
	public void start(Vec2i mousePos){
		super.start(mousePos);
		duplicates = new ArrayList<SketchShape>();
		for (int i = 0; i < shapesAffected.size(); i++){
			SketchShape _temp = shapesAffected.get(i).copy();
			_temp.setLocation(originalStates.get(i).location.plus(OFFSET));
			duplicates.add(_temp);
		}
	}
	
	@Override
	public void apply() {
		dp.addShapesAndSelect(duplicates);
	}

	@Override
	public void undo() {
		dp.removeShapes(duplicates);
	}
}
