package sketch.shapes;

import java.awt.Color;

/**
 * Represents the state of a shape, used to restore when doing an action but before applying the action.
 * Also happens to be used to write a shape to a file and to read in shapes from files.
 * 
 * @author Jixuan Wang
 *
 */
public class ShapeState {
	public final ShapeType type;
	public final Vec2i location;
	public final Vec2i size;
	public final double rotation;
	public final Color color;
	public final Color borderColor;
	public final int borderWidth;
	
	public ShapeState(ShapeType t, Vec2i l, Vec2i s, double r, Color c, Color bc, int bw) {
		this.type = t;
		this.location = l;
		this.size = s;
		this.rotation = r;
		this.color = c;
		this.borderColor = bc;
		this.borderWidth = bw;
	}
	
	@Override
	public String toString(){
		return type.toString() + "/" + Vec2iString(location) + Vec2iString(size) + rotation + "/" + colorString(color) + colorString(borderColor) + borderWidth;
	}
	
	private String Vec2iString(Vec2i v){
		return v.x + "/" + v.y + "/";
	}
	
	private String colorString(Color c){
		return c.getRed() + "/" + c.getGreen() + "/" + c.getBlue() + "/";
	}
	
	public SketchShape generateShape(){
		SketchShape s = type.newShape();
		
		if (s == null)
			return null;
		s.setSize(size);
		s.setLocation(location);
		s.setColor(color);
		s.setRotation(rotation);
		s.setBorderColor(borderColor);
		s.setBorderWidth(borderWidth);
		return s;
	}
	
	public static ShapeState read(String line){
		String[] parts = line.split("/");
		if (parts.length != 13)
			throw new IllegalArgumentException();
		try{
			ShapeType type = ShapeType.RECTANGLE;
			//it's ugly and I know it :(
			for (ShapeType s : ShapeType.values()){
				if (s.toString().equals(parts[0])){
					type = s;
				}
			}
			//it's ugly and I still know it
			Vec2i location = new Vec2i(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
			Vec2i size = new Vec2i(Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
			double rotation = Double.parseDouble(parts[5]);
			Color color = new Color(Integer.parseInt(parts[6]), Integer.parseInt(parts[7]), Integer.parseInt(parts[8]));
			Color bcolor = new Color(Integer.parseInt(parts[9]), Integer.parseInt(parts[10]), Integer.parseInt(parts[11]));
			int bwidth = Integer.parseInt(parts[12]);
			return new ShapeState(type, location, size, rotation, color, bcolor, bwidth);
		}
		catch (Exception e){
			throw new IllegalArgumentException();
		}
	}
}
