package sketch.logic;

import java.util.ArrayList;
import java.util.List;

import sketch.shapes.ShapeState;
import sketch.shapes.SketchShape;
import sketch.shapes.Vec2i;

/**
 * This is the superclass of all the actions.
 * This might be too specific, however, perhaps I should 
 * write an "Undoable" interface which only has the 
 * undo and apply methods, since several of the actions
 * only really exist for undo purposes. Maybe.
 * TODO: the above.
 * 
 * @author Jixuan Wang
 */
public abstract class SketchAction {
	protected List<SketchShape> shapesAffected;
	protected List<ShapeState> originalStates;
	protected Vec2i start;
	
	protected SketchAction(){
		shapesAffected = new ArrayList<SketchShape>();
		originalStates = new ArrayList<ShapeState>();
	}
	
	public void addAffectedShapes(List<SketchShape> shapes){
		shapesAffected.addAll(shapes);
	}
	
	public void addAffectedShape(SketchShape s){
		shapesAffected.add(s);
	}
	
	public void start(Vec2i mousePos){
		for (SketchShape s : shapesAffected){
			originalStates.add(s.getState());
		}

		start = mousePos;
	}
	
	public abstract void update(Vec2i mousePos);
	public abstract void apply();
	public abstract void undo();
}
