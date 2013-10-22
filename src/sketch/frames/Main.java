package sketch.frames;

import java.awt.Dimension;

import javax.swing.JFrame;

import sketch.panels.ContainerPanel;

/**
 * Main class. Get in, get out.
 * 
 * @author Jixuan Wang
 */
public class Main extends JFrame{
	private static final long serialVersionUID = -8013090849217270026L;

	public Main() {
		super("Sketch it! - by jw19");
		Dimension d = new Dimension(1200,800);
		this.setSize(d);
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setResizable(false);
		this.add(new ContainerPanel(this, d));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//new OutputFormatFrame();
		this.pack();
	}

	
	public static void main(String [] argv){
		new Main();
	}
}
