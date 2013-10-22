package sketch.logic;

import java.util.ArrayList;
import java.util.List;

import sketch.panels.DrawPanel;
import sketch.shapes.ShapeState;
import sketch.shapes.SketchShape;
import sketch.shapes.Vec2i;

public class DuplicateAction extends DPModifyingAction {
	private final Vec2i OFFSET = new Vec2i(15,15);
	List<SketchShape> duplicates;
	
	public DuplicateAction(DrawPanel p) {
		super(p);
	}

	@Override
	public void start(Vec2i mousePos){
		super.start(mousePos);
		duplicates = new ArrayList<SketchShape>();
		for (ShapeState ss : originalStates){
			
			ShapeState ss2 = new ShapeState(ss.type, ss.location.plus(OFFSET), ss.size, ss.rotation, ss.color, ss.borderColor, ss.borderWidth);
			duplicates.add(ss2.generateShape());
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
