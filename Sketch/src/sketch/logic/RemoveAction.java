package sketch.logic;

import java.util.ArrayList;
import java.util.List;

import sketch.panels.DrawPanel;
import sketch.shapes.SketchShape;

public class RemoveAction extends DPModifyingAction {
	private List<SketchShape> oldList;
	
	public RemoveAction(DrawPanel dp, List<SketchShape> oldList) {
		super(dp);
		this.oldList = new ArrayList<SketchShape>();
		for (SketchShape s : oldList){
			this.oldList.add(s);
		}
	}

	@Override
	public void apply() {
		for (SketchShape s : shapesAffected){
			dp.removeShape(s);
		}
	}

	@Override
	public void undo() {
		dp.setShapes(oldList);
	}

}
