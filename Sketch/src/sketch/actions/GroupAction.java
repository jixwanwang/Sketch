package sketch.actions;

import java.util.List;

import sketch.panels.DrawPanel;
import sketch.shapes.Compound;
import sketch.shapes.SketchShape;

public class GroupAction extends RestoringAction{
	//index to put the compound shape
	private int index;
	private Compound compound = null;
	
	public GroupAction(DrawPanel p, List<SketchShape> oldList, int index) {
		super(p, oldList);
		this.index = index;
	}

	@Override
	public void apply() {
		if (compound == null){
			SketchShape[] shapes = new SketchShape[shapesAffected.size()];
			shapesAffected.toArray(shapes);
			compound = new Compound(shapes);
		}
		dp.addShapeAndSelect(compound, index);
		for (SketchShape ss : shapesAffected){
			dp.removeShape(ss);
		}
	}
	
	@Override
	public void undo(){
		super.undo();
		dp.setSelected(shapesAffected);
	}
}
