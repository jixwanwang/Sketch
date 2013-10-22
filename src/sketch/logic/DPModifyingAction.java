package sketch.logic;

import sketch.panels.DrawPanel;
import sketch.shapes.Vec2i;

/**
 * Action that requires the drawpanel because some drawpanel state is going to be modified. 
 * The update method is overriden because many of these actions are applied immediately.
 * 
 * @author Jixuan Wang
 *
 */
public abstract class DPModifyingAction extends SketchAction {
	protected DrawPanel dp;
	
	public DPModifyingAction(DrawPanel dp) {
		this.dp = dp;
	}

	@Override
	public void update(Vec2i mousePos) {
		// this might not do anything
	}
}
