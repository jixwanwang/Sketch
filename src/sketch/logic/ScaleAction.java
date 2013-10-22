package sketch.logic;

import sketch.shapes.Vec2i;

public class ScaleAction extends SketchAction {
	/**
	 * Whether to scale up or down when moving in the x and y directions
	 */
	private Vec2i scaling;
	private Vec2i diff;
	
	public ScaleAction(Vec2i scaling) {
		super();
		this.scaling = scaling;
	}

	@Override
	public void update(Vec2i mousePos) {
		//TODO: make this work with rotation properly?
		diff = mousePos.minus(start).pmult(scaling);
		apply();
	}

	@Override
	public void apply() {
		for (int i = 0; i < shapesAffected.size(); i++){
			shapesAffected.get(i).resize(originalStates.get(i).size.plus(diff), scaling);
		}
	}

	@Override
	public void undo() {
		for (int i = 0; i < shapesAffected.size(); i++){
			shapesAffected.get(i).resize(originalStates.get(i).size, scaling);
		}
	}

}
