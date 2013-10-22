package sketch.shapes;

/**
 * Basic shape types. These are the building block shapes that can be added directly into the drawing panel.
 * 
 * @author Jixuan Wang
 */

public enum ShapeType {
	RECTANGLE {
		@Override
		public String getButtonText() {
			return "Create a rectangle";
		}

		@Override
		public SketchShape newShape() {
			return new Rectangle();
		}
	},
	
	ELLIPSE {
		@Override
		public String getButtonText() {
			return "Create an ellipse ";
		}

		@Override
		public SketchShape newShape() {
			return new Ellipse();
		}
	},
	
	TRIANGLE {
		@Override
		public String getButtonText() {
			return "Create a triangle";
		}

		@Override
		public SketchShape newShape() {
			return new Triangle();
		}
	};
	
	public abstract String getButtonText();
	public abstract SketchShape newShape();
}
