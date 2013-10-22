package sketch.logic;

import java.awt.Color;

import sketch.panels.DrawPanel;
import sketch.shapes.SketchShape;

public class ChangeBorderColorAction extends DPModifyingAction {
	private Color color;
	private Color oldColor;
	
	public ChangeBorderColorAction(DrawPanel dp, Color c, Color oldc) {
		super(dp);
		color = c;
		oldColor = oldc;
	}

	@Override
	public void apply() {
		for (SketchShape s : shapesAffected){
			s.setBorderColor(color);
		}
		dp.setDefaultBorderColor(color);
	}

	@Override
	public void undo() {
		for (int i = 0; i < shapesAffected.size(); i++){
			shapesAffected.get(i).setBorderColor(originalStates.get(i).borderColor);
		}
		dp.setDefaultBorderColor(oldColor);
	}

}
