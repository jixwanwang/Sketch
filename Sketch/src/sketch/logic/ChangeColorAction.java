package sketch.logic;

import java.awt.Color;

import sketch.panels.DrawPanel;
import sketch.shapes.SketchShape;

public class ChangeColorAction extends DPModifyingAction {
	private Color color;
	private Color oldcolor;
	
	public ChangeColorAction(DrawPanel dp, Color c, Color oldc) {
		super(dp);
		color = c;
		oldcolor = oldc;
	}

	@Override
	public void apply() {
		for (SketchShape s : shapesAffected){
			s.setColor(color);
		}
		dp.setDefaultColor(color);
	}

	@Override
	public void undo() {
		dp.setDefaultColor(oldcolor);
		for (int i = 0; i < shapesAffected.size(); i++){
			shapesAffected.get(i).setColor(originalStates.get(i).color);
		}
	}

}
