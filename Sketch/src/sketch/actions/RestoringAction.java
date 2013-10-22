package sketch.actions;

import java.util.ArrayList;
import java.util.List;

import sketch.panels.DrawPanel;
import sketch.shapes.SketchShape;

/**
 * SketchAction whose redo is simply to restore the old state of the drawing panel's shape list.
 * 
 * @author Jixuan Wang
 */
public abstract class RestoringAction extends DPModifyingAction {
	private List<SketchShape> oldList;
	
	public RestoringAction(DrawPanel dp, List<SketchShape> oldList) {
		super(dp);
		this.oldList = new ArrayList<SketchShape>();
		//copy the old list
		for (SketchShape s : oldList){
			this.oldList.add(s);
		}
	}

	@Override
	public void apply() {

	}

	@Override
	public void undo() {
		dp.setShapes(oldList);
	}
}
