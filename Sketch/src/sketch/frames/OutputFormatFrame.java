package sketch.frames;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import sketch.panels.OptionPanel;

public class OutputFormatFrame extends JFrame{
	private static final long serialVersionUID = 4304047160171831476L;

	private JTextArea output;
	private OptionPanel options;
	
	public OutputFormatFrame(OptionPanel opanel, String initial) {
		super("Customize your output format!");
		Dimension d = new Dimension(700,800);
		this.setSize(d);
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setResizable(false);
		this.setVisible(true);
		
		this.options = opanel;
		
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosed(WindowEvent e){
				options.output(output.getText());
			}
			
			@Override
			public void windowClosing(WindowEvent e){
				options.output(output.getText());
			}
		});
		
		Font _font = new Font("Consolas", Font.BOLD, 16);
		
		if (initial.equals(""))
			output = new JTextArea("Shape {#} = new {type}({locx}, {locy}, {sizex}, {sizey});" +
								"\n{#}.setColor(new Color({colorr}, {colorg}, {colorb}));" + 
								"\n{#}.setBorderColor(new Color({bcolorr}, {bcolorg}, {bcolorb}));" + 
								"\n{#}.setRotation({rot});" + 
								"\n{#}.setBorderWidth({bwidth});", 10, 80);
		else
			output = new JTextArea(initial, 10, 80);
		
		output.setFont(_font);
		JScrollPane _scroll = new JScrollPane (output, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.setLayout(null);
		
		JPanel _holder = new JPanel();
		_holder.setLocation(50, 0);
		_holder.setSize(new Dimension(600, 800));
		_holder.setPreferredSize(new Dimension(600, 800));
		_holder.setMaximumSize(new Dimension(600, 800));
		_holder.setLayout(new GridLayout(0, 1));
		
		JPanel _info = new JPanel();
		_info.setLayout(new GridLayout(0,1));
		_info.add(new JLabel(" Enter the format you want in the box below."));
		_info.add(new JLabel(" The expressions in the curly braces will be substituted with the values for each shape."));
		_info.add(new JLabel(" This output will be repeated for each shape."));
		_holder.add(_info);

		_holder.add(_scroll);
		
		_holder.add(new JLabel("{type}: RECTANGLE/ELLIPSE/TRIANGLE"));
		_holder.add(new JLabel("{locx}, {locy}: x and y coordinates of the top left of the shape"));
		_holder.add(new JLabel("{sizex}, {sizey}: width and height of the shape"));
		_holder.add(new JLabel("{rot}: rotation of the shape"));
		_holder.add(new JLabel("{colorr}, {colorg}, {colorb}: the color components of the shape"));
		_holder.add(new JLabel("{bcolorr}, {bcolorg}, {bcolorb}: the border color components of the shape"));
		_holder.add(new JLabel("{bwidth}: the border width of the shape"));
		_holder.add(new JLabel("{#}: a unique number for each shape"));
		
		JButton _done = new JButton("Done");
		_done.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				options.output(output.getText());
			}
		});
		_holder.add(_done);
		this.add(_holder);
		
		this.setVisible(true);
		this.pack();
		output.grabFocus();
	}

	public String getOutputFormat(){
		return output.getText();
	}
	
}
