package sketch.frames;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoFrame extends JFrame {
	private static final long serialVersionUID = -3931494782188885208L;
	
	public InfoFrame(){
		super("How to use Sketch");
		
		Dimension d = new Dimension(800,500);
		this.setSize(d);
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setResizable(false);
		this.setVisible(true);
		
		JPanel _text = new JPanel();
		_text.setLayout(new GridLayout(0,1));
		String indent = "        ";
		_text.add(new JLabel("  How to use this awesome program:"));
		_text.add(new JLabel(indent + "To start, click on \"Create a ...\", then click and drag on the drawing panel to make a new shape!"));
		_text.add(new JLabel(indent + "You can click a shape to selected it. Use *Ctrl+Click* to select multiple shapes."));
		_text.add(new JLabel(indent + "Move shapes by dragging them around"));
		_text.add(new JLabel(indent + "Resize a shape by clicking and dragging on one of the little circles on the boundary."));
		_text.add(new JLabel(indent + "Rotate a shape by clicking and dragging on the green little circle above the shape."));
		_text.add(new JLabel(indent + "Remove shapes with *Ctrl+x*. Duplicate shapes with *Ctrl+d*. Or use the buttons on the side."));
		_text.add(new JLabel(indent + "Undo actions with *Ctrl+z*. Redo actions you just undid with *Ctrl+y*. Or use the buttons on the side."));
		_text.add(new JLabel(indent + "Change the fill and border color of shapes with the colored buttons on the side panel."));
		_text.add(new JLabel(indent + "The color of the button indicates the current default color. When you create new shapes they will have that color."));
		_text.add(new JLabel(indent + "Change the border width of shapes with the slider."));
		_text.add(new JLabel(indent + "Change the z-order of shapes with the buttons at the bottom of the side panel."));
		this.add(_text);
		
		this.pack();
	}
}
