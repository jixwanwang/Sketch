package sketch.logic;

import sketch.shapes.Vec2i;

public class MoveAction extends SketchAction {
	private Vec2i moveAmount;
	
	public MoveAction() {
		super();
		moveAmount = new Vec2i();
	}

	@Override
	public void update(Vec2i mousePos) {
		moveAmount = mousePos.minus(start);
		apply();
	}

	@Override
	public void apply() {
		for (int i = 0; i < shapesAffected.size(); i++){
			shapesAffected.get(i).setLocation(originalStates.get(i).location.plus(moveAmount));
		}
	}

	@Override
	public void undo() {
		for (int i = 0; i < shapesAffected.size(); i++){
			shapesAffected.get(i).setLocation(originalStates.get(i).location);
		}
	}

}
