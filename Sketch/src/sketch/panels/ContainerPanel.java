package sketch.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This is just a panel that holds the two main panels
 * for the editor. 
 * 
 * @author Jixuan Wang
 */
public class ContainerPanel extends JPanel{
	private static final long serialVersionUID = -3239841138723987930L;
	private DrawPanel draw;
	private OptionPanel options;
	
	public ContainerPanel(JFrame f, Dimension d) {
		super();
		this.setSize(d);
		this.setPreferredSize(d);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		draw = new DrawPanel(new Dimension((int)(d.getWidth()*0.75), (int)d.getHeight()));
		options = new OptionPanel(new Dimension((int)(d.getWidth()*0.25), (int)d.getHeight()), f, draw);
		draw.setOptions(options);
		this.add(draw, BorderLayout.CENTER);
		this.add(options, BorderLayout.EAST);
	}
	
	@Override
	public void paintComponent(Graphics g){
		
	}
}
