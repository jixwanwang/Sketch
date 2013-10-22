package sketch.logic;

import java.util.ArrayList;
import java.util.List;

import sketch.panels.DrawPanel;
import sketch.shapes.SketchShape;

public class OrderChangeAction extends DPModifyingAction{
	private List<SketchShape> oldList;
	
	public OrderChangeAction(DrawPanel dp, List<SketchShape> oldlist) {
		super(dp);
		//copy the lists
		this.oldList = new ArrayList<SketchShape>();
		for (SketchShape s : oldlist){
			this.oldList.add(s);
		}
	}
	
	@Override
	public void apply() {
		dp.setShapes(shapesAffected);
	}

	@Override
	public void undo() {
		dp.setShapes(oldList);
	}

}
