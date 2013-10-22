package sketch.actions;

import java.util.List;

import sketch.panels.DrawPanel;
import sketch.shapes.Compound;
import sketch.shapes.SketchShape;

public class UngroupAction extends RestoringAction {
	//index of the compound shape
	private int index;
	public UngroupAction(DrawPanel p, List<SketchShape> oldList, int index) {
		super(p, oldList);
		this.index = index;
	}

	@Override
	public void apply() {
		Compound c = (Compound)(shapesAffected.get(0));
		dp.removeShape(c);
		//go backwards since inserting a shape shifts the other shapes up
		for (int i = c.getChildren().size() - 1; i >= 0; i--){
			dp.addShapeAndSelect(c.getChildren().get(i), index);
		}
		dp.setSelected(c.getChildren());
	}
	
	@Override
	public void undo(){
		super.undo();
		dp.setSelected(shapesAffected);
	}
}
