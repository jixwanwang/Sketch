package sketch.actions;

import java.util.List;

import sketch.panels.DrawPanel;
import sketch.shapes.SketchShape;

public class RemoveAction extends RestoringAction {
	public RemoveAction(DrawPanel dp, List<SketchShape> oldList) {
		super(dp, oldList);
	}

	@Override
	public void apply() {
		for (SketchShape s : shapesAffected){
			dp.removeShape(s);
		}
	}
}
