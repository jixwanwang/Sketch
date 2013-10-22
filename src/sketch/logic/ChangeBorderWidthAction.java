package sketch.logic;

import sketch.panels.DrawPanel;
import sketch.shapes.SketchShape;
import sketch.shapes.Vec2i;

public class ChangeBorderWidthAction extends DPModifyingAction {
	private int width;
	private int oldWidth;
	
	public ChangeBorderWidthAction(DrawPanel dp, int width, int oldw){
		super(dp);
		this.width = width;
		this.oldWidth = oldw;
	}
	
	@Override
	public void update(Vec2i mousePos) {
		width = mousePos.x;
		for (SketchShape s : shapesAffected){
			s.setBorderWidth(width);
		}
	}

	@Override
	public void apply() {
		for (SketchShape s : shapesAffected){
			s.setBorderWidth(width);
		}
		dp.setDefaultBorderWidth(width);
	}

	@Override
	public void undo() {
		for (int i = 0; i < shapesAffected.size(); i++){
			shapesAffected.get(i).setBorderWidth(originalStates.get(i).borderWidth);;
		}
		dp.setDefaultBorderWidth(oldWidth);
	}

}
