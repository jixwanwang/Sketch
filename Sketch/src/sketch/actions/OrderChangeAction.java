package sketch.actions;

import java.util.List;

import sketch.panels.DrawPanel;
import sketch.shapes.SketchShape;

public class OrderChangeAction extends RestoringAction{
	public OrderChangeAction(DrawPanel dp, List<SketchShape> oldlist) {
		super(dp, oldlist);
	}
	
	@Override
	public void apply() {
		dp.setShapes(shapesAffected);
	}
}
