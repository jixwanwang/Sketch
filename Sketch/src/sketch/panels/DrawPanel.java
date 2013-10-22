package sketch.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import sketch.actions.AddShapeAction;
import sketch.actions.ChangeBorderColorAction;
import sketch.actions.ChangeBorderWidthAction;
import sketch.actions.ChangeColorAction;
import sketch.actions.DuplicateAction;
import sketch.actions.GroupAction;
import sketch.actions.MoveAction;
import sketch.actions.OrderChangeAction;
import sketch.actions.RemoveAction;
import sketch.actions.RotateAction;
import sketch.actions.ScaleAction;
import sketch.actions.SketchAction;
import sketch.actions.UngroupAction;
import sketch.shapes.Compound;
import sketch.shapes.ShapeState;
import sketch.shapes.ShapeType;
import sketch.shapes.SketchShape;
import sketch.shapes.Vec2i;

/**
 * This JPanel serves as the graphical part of the editor, and takes care of both
 * drawing and logic. This class handles the key and mouse events, and as well as 
 * maintaining the shapes. It isn't long enough to warrant breaking up into separate
 * parts, but if enough more features are added then some separation of 
 * functionality might be needed.
 * 
 * @author Jixuan Wang
 */

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
	private static final long serialVersionUID = 3702526871663304868L;
	
	//stores the shapes in draw order. The first element is draw first, so it's on the bottom.
	private List<SketchShape> shapes;
	//list of selected shapes, not necessarily in drawing order
	private List<SketchShape> selected;
	//store actions for undo
	private List<SketchAction> actions;
	//store undone actions for redo
	private List<SketchAction> undoneActions;
	
	private SketchAction activeAction;
	private Point mouseStart;
	private OptionPanel options;
	private Color defaultColor;
	private Color defaultBorderColor;
	private int defaultBorderWidth;
	
	//whether or not we're in add shape mode
	private AddShapeAction addingShape;
	
	public DrawPanel(Dimension d) {
		super();
		this.setSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.getActionMap().put("undo", new AbstractAction(){
			private static final long serialVersionUID = 6939701394394303151L;

			public void actionPerformed(ActionEvent e){
				undo();
			}
		});
		
		this.getActionMap().put("redo", new AbstractAction(){
			private static final long serialVersionUID = 3850435270742958396L;

			public void actionPerformed(ActionEvent e){
				redo();
			}
		});
		
		this.getActionMap().put("remove", new AbstractAction(){
			private static final long serialVersionUID = -3251732403927247194L;

			public void actionPerformed(ActionEvent e){
				removeSelected();
			}
		});
		
		this.getActionMap().put("duplicate", new AbstractAction(){
			private static final long serialVersionUID = -7797955735087895374L;

			public void actionPerformed(ActionEvent e){
				duplicateSelected();
			}
		});
		
		this.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK), "undo");
		this.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK), "redo");
		this.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK), "remove");
		this.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK), "duplicate");
		
		shapes = new ArrayList<SketchShape>();
		
		actions = new ArrayList<SketchAction>();
		undoneActions = new ArrayList<SketchAction>();
		selected = new ArrayList<SketchShape>();
		
		defaultColor = Color.blue;
		defaultBorderColor = Color.blue;
		defaultBorderWidth = 0;
		
		addingShape = null;
	}
	
	public void setOptions(OptionPanel p){
		options = p;
	}
	
	/**
	 * Undo the last action
	 */
	public void undo() {
		//undo, put onto undone list, remove from action list
		if (!actions.isEmpty()){
			actions.get(actions.size() - 1).undo();
			undoneActions.add(actions.get(actions.size() - 1));
			actions.remove(actions.size() - 1);
			repaint();
		}
	}
	
	public void redo(){
		//apply again, put back onto action list, remove from undone list
		if (!undoneActions.isEmpty()){
			undoneActions.get(undoneActions.size() - 1).apply();
			actions.add(undoneActions.get(undoneActions.size() - 1));
			undoneActions.remove(undoneActions.size() - 1);
			repaint();
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D _g2 = (Graphics2D) g;
		_g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (SketchShape s : shapes){
			s.draw(_g2);
		}
		
		if (addingShape != null)
			addingShape.getShape().draw(_g2);
	}

	public void addShape(ShapeType shape) {
		SketchShape s = shape.newShape();
		if (s != null){
			clearSelected();
			s.setColor(defaultColor);
			s.setBorderColor(defaultBorderColor);
			s.setBorderWidth(defaultBorderWidth);
			addingShape = new AddShapeAction(this);
			addingShape.addAffectedShape(s);
		}
		repaint();
	}
	
	public void addShapeAndSelect(SketchShape s){
		clearSelected();
		this.shapes.add(s);
		selected.add(s);
		s.select();
		repaint();
	}
	
	public void addShapeAndSelect(SketchShape s, int index){
		clearSelected();
		this.shapes.add(index, s);
		selected.add(s);
		s.select();
		repaint();
	}
	
	public void addShapesAndSelect(List<SketchShape> shapes){
		clearSelected();
		for (SketchShape s : shapes){
			this.shapes.add(s);
			s.select();
			selected.add(s);
		}
		repaint();
	}

	public void setShapes(List<SketchShape> newlist) {
		this.shapes.clear();
		for (SketchShape s : newlist){
			this.shapes.add(s);
		}
		repaint();
	}
	
	public void removeShape(SketchShape s) {
		selected.remove(s);
		s.unselect();
		shapes.remove(s);
		if (selected.isEmpty()){
			clearSelected();
		}
		repaint();
	}
	
	public void removeShapes(List<SketchShape> shapes) {
		clearSelected();
		for (SketchShape s : shapes){
			System.out.println(s);
			this.shapes.remove(s);
		}
		repaint();
	}
	
	public void setSelected(List<SketchShape> s){
		clearSelected();
		for (SketchShape s2 : s){
			s2.select();
			this.selected.add(s2);
		}
		updateOptions();
	}
	
	public void duplicateSelected(){
		setAction(new DuplicateAction(this));
		doAction();
	}
	
	private void updateOptions(){
		options.disableOrdering();
		options.disableGrouping();
		options.disableUngroup();
		
		if (selected.size() == 1){
			options.enableOrdering();
			if (selected.get(0) instanceof Compound)
				options.enableUngroup();
		}
		else if (selected.size() > 1){
			options.enableGrouping();
			options.disableUngroup();
			options.disableOrdering();
			options.enablePartialOrdering();
		}
	}
	
	private void doAction(){
		if (activeAction != null){
			activeAction.apply();
			actions.add(activeAction);
			activeAction = null;
			//can't redo after doing a new action
			undoneActions.clear();
		}
		updateOptions();
		repaint();
	}
	
	private void setAction(SketchAction a){
		activeAction = a;
		activeAction.addAffectedShapes(selected);
		if (mouseStart != null)
			activeAction.start(new Vec2i(mouseStart));
		else
			activeAction.start(null);
	}
	
	public void removeSelected(){
		setAction(new RemoveAction(this, shapes));
		doAction();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (activeAction != null)
			activeAction.update(new Vec2i(e.getPoint()));
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) { }

	private void clearSelected(){
		for (SketchShape s : selected){
			s.unselect();
		}
		selected.clear();
		options.disableOrdering();
		options.disableGrouping();
		options.disableUngroup();
	}
	
	/**
	 * Selects shapes
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (addingShape != null){
			addingShape = null;
			activeAction = null;
		}
		//iterate from the more front shape down
		for (int i = shapes.size() - 1; i >= 0; i--){
			if (shapes.get(i).contains(e.getPoint())){
				if (!e.isControlDown())
					clearSelected();
				
				if (shapes.get(i).isSelected()){
					shapes.get(i).unselect();
					selected.remove(shapes.get(i));
				}
				else{
					shapes.get(i).select();
					selected.add(shapes.get(i));
				}
				updateOptions();
				repaint();
				return;
			}
		}
		clearSelected();
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseStart = e.getPoint();

		if (addingShape != null){
			setAction(addingShape);
			activeAction.start(new Vec2i(mouseStart));
			return;
		}
		
		if (selected.size() == 1){
			SketchShape s = selected.get(0);
			
			//try scaling
			Vec2i _scaleDir = s.clickedToScale(e.getPoint());
			if (_scaleDir.x != 0 || _scaleDir.y != 0){
				setAction(new ScaleAction(_scaleDir));
				return;
			}
			
			//try rotation
			if (s.clickedToRotate(e.getPoint())){
				setAction(new RotateAction());
				return;
			}
		}
		for (SketchShape s : selected){
			if (s.contains(e.getPoint())){
				setAction(new MoveAction());
				return;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseStart = null;
		if (addingShape != null){
			addingShape = null;
		}
		doAction();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.grabFocus();
	}

	@Override
	public void mouseExited(MouseEvent e) { 
		mouseStart = null;
		doAction();
	}

	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) { }

	@Override
	public void keyReleased(KeyEvent e) { }

	private void moveSelectedFully(boolean forwards){
		List<SketchShape> _other = new ArrayList<SketchShape>();
		List<SketchShape> _selected = new ArrayList<SketchShape>();
		for (SketchShape s : shapes){
			if (s.isSelected())
				_selected.add(s);
			else
				_other.add(s);
		}
		if (_selected.size() > 0){
			//store the old list for undoing purposes
			activeAction = new OrderChangeAction(this, shapes);
			shapes.clear();
			if (forwards){
				shapes.addAll(_selected);
				shapes.addAll(_other);
			}
			else{
				shapes.addAll(_other);
				shapes.addAll(_selected);
			}
			//put the new list as the shapesaffected
			activeAction.addAffectedShapes(shapes);
			activeAction.start(null);
			doAction();
		}
		
	}
	
	public void moveSelectedFullyBackwards() {
		moveSelectedFully(true);
	}

	public void moveSelectedFullyForward() {
		moveSelectedFully(false);
	}
	
	/**
	 * These two move methods can only be called with one selected element
	 */
	public void moveSelectedBackwards() {
		//don't need to look at the first element, it's already last
		for (int i = 1; i < shapes.size(); i++){
			if (shapes.get(i).isSelected()){
				//store the old list for undoing purposes
				activeAction = new OrderChangeAction(this, shapes);
				SketchShape _s = shapes.get(i);
				shapes.remove(i);
				shapes.add(i - 1, _s);
				activeAction.addAffectedShapes(shapes);
				activeAction.start(null);
				doAction();
				return;
			}
		}
	}

	public void moveSelectedForward() {
		//don't need to look at the last element, it's already first
		for (int i = 0; i < shapes.size() - 1; i++){
			if (shapes.get(i).isSelected()){
				//store the old list for undoing purposes
				activeAction = new OrderChangeAction(this, shapes);
				SketchShape _s = shapes.get(i);
				shapes.remove(i);
				shapes.add(i + 1, _s);
				activeAction.addAffectedShapes(shapes);
				activeAction.start(null);
				doAction();
				return;
			}
		}
	}

	public void groupSelected(){
		//find the index to put the new shape at
		for (int i = shapes.size() - 1; i >= 0; i--){
			if (selected.contains(shapes.get(i))){
				activeAction = new GroupAction(this, shapes, i + 1);
				//iterate in order to keep the order consistent
				for (SketchShape s : shapes){
					if (selected.contains(s)){
						activeAction.addAffectedShape(s);
					}
				}
				doAction();
				return;
			}
		}
	}
	
	public void ungroup(){
		for (int i = shapes.size() - 1; i >= 0; i--){
			if (selected.contains(shapes.get(i))){
				setAction(new UngroupAction(this, shapes, i));
				doAction();
				return;
			}
		}
	}
	
	public void setSelectedColor(Color c) {
		setAction(new ChangeColorAction(this, c, defaultColor));
		doAction();
	}
	
	public void setDefaultColor(Color c){
		defaultColor = c;
		options.setColor(c);
	}

	public void setSelectedBorderColor(Color c) {
		setAction(new ChangeBorderColorAction(this, c, defaultBorderColor));
		doAction();
	}
	
	public void setDefaultBorderColor(Color c){
		defaultBorderColor = c;
		options.setBorderColor(c);
	}
	
	public void changeBorderWidth(int width){
		if (activeAction == null)
			setAction(new ChangeBorderWidthAction(this, width, defaultBorderWidth));
		activeAction.update(new Vec2i(width, 0));
		repaint();
	}
	
	public void finalizeBorderWidth(int width){
		doAction();
	}
	
	public void setDefaultBorderWidth(int width){
		defaultBorderWidth = width;
		options.setBorderWidth(width);
	}

	public void writeTo(File file) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for (SketchShape s : shapes){
				bw.write(s.toString());
				bw.newLine();
			}
			
			bw.close();
		} catch (IOException e) {
			System.out.println("Something went wrong when writing to " + file.getAbsolutePath());
		}
	}
	
	public void readFrom(File file){
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			List<SketchShape> _shapes = new ArrayList<SketchShape>();
			while( (line = br.readLine()) != null){
				_shapes.add(ShapeState.read(line).generateShape());
			}
			shapes = _shapes;
			repaint();
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't find " + file.getAbsolutePath());
		} catch (Exception e){
			System.out.println("Couldn't parse " + file.getAbsolutePath());
		}
	}

	public void output(String text) {
		int id = 0;
		
		for (SketchShape s : shapes){
			id = output(text, id, s);
		}
	}
	
	/**
	 * Outputs formatted text for a shape.
	 * 
	 * @param text
	 * @param id
	 * @param shape
	 * @return the next unique id
	 */
	private int output(String text, int id, SketchShape shape){
		if (shape instanceof Compound){
			for (SketchShape s : ((Compound) shape).getChildren()){
				id = output(text, id, s);
			}
			return id;
		}
		else{
			ShapeState ss = shape.getState();
			String format = text;
			format = format.replaceAll("\\{locx\\}", ss.location.x + "").replaceAll("\\{locy\\}", ss.location.y + "");
			format = format.replaceAll("\\{sizex\\}", ss.size.x + "").replaceAll("\\{sizey\\}", ss.size.y + "");
			format = format.replaceAll("\\{colorr\\}", ss.color.getRed() + "").replaceAll("\\{colorg\\}", ss.color.getGreen() + "").replaceAll("\\{colorb\\}", ss.color.getBlue() + "");
			format = format.replaceAll("\\{bcolorr\\}", ss.borderColor.getRed() + "").replaceAll("\\{bcolorg\\}", ss.borderColor.getGreen() + "").replaceAll("\\{bcolorb\\}", ss.borderColor.getBlue() + "");
			format = format.replaceAll("\\{rot\\}", ss.rotation + "").replaceAll("\\{bwidth\\}", ss.borderWidth + "");
			format = format.replaceAll("\\{type\\}", ss.type.toString()).replaceAll("\\{#\\}", "shape" + id);
			System.out.println(format);
			return id + 1;
		}
	}
}
