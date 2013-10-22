package sketch.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sketch.frames.InfoFrame;
import sketch.frames.OutputFormatFrame;
import sketch.shapes.ShapeType;

/**
 * This panel holds all the options for the editor. 
 * I could have put it in the menu, but this is supposed to be an easy to use
 * editor, so it's nice to have all the functionality just in buttons on the
 * side. If I even need more functionality, this would probably not be JPanel
 * but a logical class for the options, which might include menu items.
 * 
 * @author Jixuan Wang
 */

public class OptionPanel extends JPanel{
	private static final long serialVersionUID = -3785198265691746848L;
	
	private DrawPanel draw;
	private JFrame frame;
	
	//buttons that we have to enable/disable, so we need a reference to them
	private JButton sendBack;
	private JButton sendFront;
	private JButton sendFullBack;
	private JButton sendFullFront;
	
	private JButton group;
	private JButton ungroup;
	
	private JLabel colorChooser;
	private JLabel bordercolorChooser;
	private JLabel borderValue;
	private JSlider borderWidth;
	
	//stores the last file that was opened/saved so we can use it as the default directory
	private File lastfile;
	
	//saves the output text format
	private String lastOutputText;
	
	public OptionPanel(Dimension d, JFrame frame, DrawPanel p) {
		super();
		this.setSize(d);
		this.setPreferredSize(d);
		this.setFocusable(false);
		Color _bg = new Color(200,200,230);
		
		this.setBackground(_bg);
		
		this.frame = frame;
		
		lastOutputText = "";
		draw = p;
		
		//exit button
		JButton _exit = new JButton("Exit");
		_exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		//instructions button
		JButton _info = new JButton("WUT DIS?");
		_info.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new InfoFrame();
			}
		});
		
		//buttons for ordering and grouping
		group = new JButton("Group");
		group.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				draw.groupSelected();
			}
		});
		
		ungroup = new JButton("Ungroup");
		ungroup.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				draw.ungroup();
			}
		});
		
		sendFront = new JButton("Move forward");
		sendFront.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				draw.moveSelectedForward();
			}
		});
		sendBack = new JButton("Move back");
		sendBack.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				draw.moveSelectedBackwards();
			}
		});
		sendFullFront = new JButton("Move to front");
		sendFullFront.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				draw.moveSelectedFullyForward();
			}
		});
		sendFullBack = new JButton("Move to back");
		sendFullBack.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				draw.moveSelectedFullyBackwards();
			}
		});
		
		//buttons for edit panel
		JButton _remove = new JButton("Remove (Ctrl+x)");
		_remove.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				draw.removeSelected();
			}
		});
		JButton _undo = new JButton("Undo (Ctrl+z)");
		_undo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				draw.undo();
			}
		});
		JButton _redo = new JButton("Redo (Ctrl+y)");
		_redo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				draw.redo();
			}
		});
		JButton _duplicate = new JButton("Duplicate (Ctrl+d)");
		_duplicate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				draw.duplicateSelected();
			}
		});
		
		//labels which act as buttons for color panel
		colorChooser = new JLabel("Change fill color", JLabel.CENTER);
		colorChooser.setBackground(Color.BLUE);
		colorChooser.setOpaque(true);
		colorChooser.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				Color c = JColorChooser.showDialog(null, "Choose A Color!", Color.BLACK);
				if (c != null){
					draw.setSelectedColor(c);
					((JLabel) e.getSource()).setBackground(c);
				}
			}
		});
		bordercolorChooser = new JLabel("Change border color", JLabel.CENTER);
		bordercolorChooser.setBackground(Color.BLUE);
		bordercolorChooser.setOpaque(true);
		bordercolorChooser.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				Color c = JColorChooser.showDialog(null, "Choose A Color!", Color.BLACK);
				if (c != null){
					draw.setSelectedBorderColor(c);
					((JLabel) e.getSource()).setBackground(c);
				}
			}
		});
		//border width stuff
		borderValue = new JLabel("Border width: 0", JLabel.CENTER);
		borderWidth = new JSlider(0,20,0);
		borderWidth.setSnapToTicks(true);
		borderWidth.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e){
				JSlider source = (JSlider)e.getSource();
				if (source.getValueIsAdjusting())
					draw.changeBorderWidth(source.getValue());
				else
					draw.finalizeBorderWidth(source.getValue());
				borderValue.setText("Border width: " + source.getValue());
			}
		});
		
		//buttons for file panel
		JButton _save = new JButton("Save/Output");
		_save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				JFileChooser fc = new JFileChooser(lastfile);
				int returnVal = fc.showSaveDialog(null);
		
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            lastfile = fc.getSelectedFile();
		            draw.writeTo(lastfile);
		        }
		        
		        getOutput();
			}
		});
		JButton _open = new JButton("Open...");
		_open.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				JFileChooser fc = new JFileChooser(lastfile);
				int returnVal = fc.showOpenDialog(null);
		
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        	lastfile = fc.getSelectedFile();
		            draw.readFrom(lastfile);
		        }
			}
		});
		
		//start adding stuff to the options panel
		this.setLayout(new GridLayout(0,1, 10, 10));
		
		JPanel _filePanel = new JPanel();
		_filePanel.setBackground(_bg);
		_filePanel.setLayout(new GridLayout(0, 1, 20, 0));
		_filePanel.add(_info);
		_filePanel.add(_save);
		_filePanel.add(_open);
		this.add(_filePanel);
		
		JPanel _addPanel = new JPanel();
		_addPanel.setBackground(_bg);
		_addPanel.setLayout(new GridLayout(0,1, 20, 3));
		_addPanel.add(new CreateShapeButton(ShapeType.RECTANGLE));
		_addPanel.add(new CreateShapeButton(ShapeType.ELLIPSE));
		_addPanel.add(new CreateShapeButton(ShapeType.TRIANGLE));
		this.add(_addPanel);
		
		JPanel _editPanel = new JPanel();
		_editPanel.setBackground(_bg);
		_editPanel.setLayout(new GridLayout(2,2, 10, 10));
		_editPanel.add(_remove);
		_editPanel.add(_duplicate);
		_editPanel.add(_undo);
		_editPanel.add(_redo);
		this.add(_editPanel);
		
		JPanel _colorPanel = new JPanel();
		_colorPanel.setBackground(_bg);
		_colorPanel.setLayout(new GridLayout(0, 1, 0, 5));
		_colorPanel.add(colorChooser);
		_colorPanel.add(bordercolorChooser);
		_colorPanel.add(borderValue);
		_colorPanel.add(borderWidth);
		this.add(_colorPanel);
		
		JPanel _orderPanel = new JPanel();
		_orderPanel.setBackground(_bg);
		_orderPanel.setLayout(new GridLayout(0, 2, 10, 10));
		_orderPanel.add(group);
		_orderPanel.add(ungroup);
		_orderPanel.add(sendFront);
		_orderPanel.add(sendBack);
		_orderPanel.add(sendFullFront);
		_orderPanel.add(sendFullBack);
		this.add(_orderPanel);
		
		disableOrdering();
		disableGrouping();
		disableUngroup();
	}
	
	public void enableGrouping(){
		group.setEnabled(true);
	}
	
	public void disableGrouping(){
		group.setEnabled(false);
	}
	
	public void enableUngroup(){
		ungroup.setEnabled(true);
	}
	
	public void disableUngroup(){
		ungroup.setEnabled(false);
	}
	
	public void disableOrdering(){
		sendFront.setEnabled(false);
		sendBack.setEnabled(false);
		sendFullFront.setEnabled(false);
		sendFullBack.setEnabled(false);
	}
	
	public void enableOrdering(){
		sendFront.setEnabled(true);
		sendBack.setEnabled(true);
		sendFullFront.setEnabled(true);
		sendFullBack.setEnabled(true);
	}
	
	public void enablePartialOrdering(){
		sendFullFront.setEnabled(true);
		sendFullBack.setEnabled(true);
	}
	
	public void setColor(Color c){
		colorChooser.setBackground(c);
	}

	public void setBorderColor(Color c){
		bordercolorChooser.setBackground(c);
	}
	
	public void setBorderWidth(int width) {
		borderValue.setText("Border width: " + width);
		borderWidth.setValue(width);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	public void getOutput(){
		new OutputFormatFrame(this, lastOutputText);
		frame.setVisible(false);
	}
	
	public void output(String text) {
		lastOutputText = text;
		draw.output(text);
		frame.setVisible(true);
	}
	
	/*
	 * Didn't really need a class for this, but whatever. 
	 * It was a first listener I wrote in this project.
	 */
	class CreateShapeButton extends JButton implements ActionListener{
		private static final long serialVersionUID = -5400713617251573114L;
		private ShapeType shape;
		public CreateShapeButton(ShapeType shape){
			super(shape.getButtonText());
			this.addActionListener(this);
			this.shape = shape;
		}
		
		@Override
		public void actionPerformed(ActionEvent e){
			draw.addShape(shape);
		}
	}
}
