package slgv;
import java.awt.BasicStroke;
import java.awt.Container;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Panel;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.TextField;
import java.awt.Shape;
import java.awt.Color;
import java.awt.Component;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import slgc.MouseController;
import slgc.SLGController;
import slgc.ResizeController;
import util.models.Listenable;
import util.undo.Listener;
import shapes.ShapeModel;
import slm.SLModel;
import slm.ShapesList;
import shapes.LineModel;
import shapes.OvalModel;
import shapes.RectangleModel;
import shapes.ComponentModel;
import shapes.RemoteArc;
import shapes.RemoteCurve;
import shapes.RemoteLine;
import shapes.RemoteOval;
import shapes.RemoteRectangle;
import shapes.RemoteShape;
import shapes.RemoteText;
import slc.SLComposer;
//import bus.agent.AutoAllConnect;
import bus.uigen.widgets.Painter;
import bus.uigen.widgets.swing.DelegateJPanel;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import java.util.Vector;
import slm.SLPutCommand;

public class SLGView extends UnicastRemoteObject implements Listener, Remote, Painter
{
	
    transient private boolean paintKeys = false;
	transient private boolean paintLabels = false;
    transient private ShapesList slModel;
    transient private MouseController mouseController;
	//transient TextField tryField = new TextField("hello world");
	//transient Container container = new SLGViewPanel (this);
	transient TextField tryField;
	transient DelegateJPanel container;
	public SLGView (ShapesList theSLModel, DelegateJPanel theContainer) throws RemoteException
    {
        super();
		setContainer(theContainer);
		setSLModel(theSLModel);
    }
    public SLGView (ShapesList theSLModel) throws RemoteException
    {
        super();
		setContainer(new DelegateJPanel());
		setSLModel(theSLModel);
		/*
        slModel = theSLModel;
        //slModel.addObserver(this);
        for (Enumeration keys = slModel.keys(); keys.hasMoreElements();)
        {
            ShapeModel shapeModel = slModel.get((String) keys.nextElement());
            //(slModel.get(keys.nextElement())).addObserver(this);
            //shapeModel.addObserver(this);
            shapeModel.addListener(this);
        }
		//this.add(tryField);
		*/

    }
    /*
	void init (SLGViewPanel container) {
		//tryField = new TextField("hello world");
		//container = new SLGViewPanel (this);
		//container = new SLGViewPanel();
		
		
		//container.setLayout(null);
		
	}
	*/
	public void setContainer(DelegateJPanel newValue) {
		container = newValue;
		container.setLayout(null);
		container.addPainter(this);
	}
	public DelegateJPanel getContainer() {
		return container;
	}
	public SLGView () throws RemoteException
    {
        super();
        setContainer(new DelegateJPanel());
		//init();
		//this.add(tryField);
		

    }
	public void setSLModel (ShapesList theSLModel)
    {
		try {
		//System.out.println("slmodel " + theSLModel);
        slModel = theSLModel;
        //slModel.addObserver(this);
        for (Enumeration keys = slModel.keys(); keys.hasMoreElements();)
        {
            RemoteShape shapeModel = slModel.get((String) keys.nextElement());
            //(slModel.get(keys.nextElement())).addObserver(this);
            //shapeModel.addObserver(this);
            shapeModel.addListener(this);
        }
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e);
		}

    }
    public void setMouseController (MouseController theMouseController)
    {
        container.removeMouseListener(mouseController);
        container.removeMouseMotionListener(mouseController);
        mouseController = theMouseController;
        container.addMouseListener(mouseController);
        container.addMouseMotionListener(mouseController);
        container.repaint();
    }
	transient SLGController slgController = null;
	public void setController (SLGController theSLGController)
    {
        slgController = theSLGController;
    }

    public void toggleKeys()
    {
       paintKeys = !paintKeys;
       container.repaint();
    }
	public void toggleLabels()
    {
       paintLabels = !paintLabels;
       container.repaint();
    }
    public void update(Listenable model, Object arg)
    {
    	try {
    		//System.out.println ("Listenable " + model + " arg " + arg);
		//System.out.println("update called");
    		if (arg != null && arg instanceof Boolean && (Boolean) arg)
    			slModel.sort();
    		else if ((arg != null) && (arg instanceof SLPutCommand)) {
			
		    //System.out.println("Update called on view " + this);
            /*ShapeModel*/ RemoteShape shapeModel = ((SLPutCommand) arg).getShapeModel();
           // System.out.println("Shape model:" + shapeModel);
            //shapeModel.addObserver(this);
            shapeModel.addListener(this);
			if (shapeModel instanceof ComponentModel) {
				ComponentModel componentModel = (ComponentModel) shapeModel;
			    Rectangle b = componentModel.getBounds();
				Component component = componentModel.getComponent();
				//System.out.println("comp model" + componentModel + "comp" + component);
				//System.out.println ("model bounds" + b + "comp bounds" + component.getBounds());
				component.setBounds(b.x, b.y, b.width, b.height);
			}
        }
		if (model instanceof SLModel)
		     updateComponents();
		//System.out.println("calling repaint");
        container.repaint();
    	} catch (Exception e) {
    		System.out.println(e);
    		e.printStackTrace();
    	}
    }
	public void updateComponents() {
		Component[] currentComponents = container.getComponents();
		Vector currentComponentVector = new Vector();
		for (int i = 0; i < currentComponents.length; i++)
			currentComponentVector.addElement(currentComponents[i]);		
		for (Enumeration shapeModels = slModel.elements();shapeModels.hasMoreElements();)
        {
		     //System.out.println("paintShapes " + this);
			 RemoteShape shapeModel = (RemoteShape) shapeModels.nextElement();
			 if (shapeModel instanceof ComponentModel) {
				 ComponentModel componentModel = (ComponentModel) shapeModel;
				 Component component = componentModel.getComponent();
				 if (currentComponentVector.contains(component)) {
					 currentComponentVector.removeElement(component);
					
				 } else {
//					 if (component.getParent() != null)
//						 System.out.println("Non null parent");
					 container.add(component);
//					 if (component instanceof JTextField) {
//						 System.out.println ("Adding JtextField:" + ((JTextField) component).getText());
//					 }
//					 System.out.println("component x, y(" + component.getX() + "," + component.getY() + ")");
//					 System.out.println("component width, height(" + component.getBounds().getWidth() + "," + component.getBounds().getHeight() + ")");
				 }
			 }
		}
		for (Enumeration elements = currentComponentVector.elements();
			 elements.hasMoreElements();) {
			Component component = (Component) elements.nextElement();
//			System.out.println("Remving component:" + component.getX() + "," + component.getY());	
			container.remove(component);
			//container.remove((Component) elements.nextElement());
		}
		//container.validate();
		
	}
	/*
	String selection = null;	ShapeModel selectedShape = null;
		public void setSelection(String newVal) {			selection =  newVal;			if (newVal != null)
			  selectedShape = slModel.get(selection);
			else
				selectedShape = null;			repaint();
		}
		public String getSelection() {			return selection;
		}
	*/
	
	
	
    public void paint(Graphics g)
    {
		//System.out.println(Thread.currentThread());
		//System.out.println("graphics" + g);
		//System.out.println(slgController);
		//System.out.println("paint called");
        this.paintShapes(g);
        if (paintKeys)
            this.paintKeys(g);
		if (paintLabels)
			this.paintLabels(g);
		if (mouseController != null)
			mouseController.paint(g);
		//paintSelection(g);
    }
    public void paintShapes(Graphics g)
    {
		//System.out.println("slModel " + slModel);
        for (Enumeration shapeModels = slModel.elements();shapeModels.hasMoreElements();)
        {
		     //System.out.println("paintShapes " + this);
			 RemoteShape shapeModel = (RemoteShape) shapeModels.nextElement();
			 if ((mouseController == null ) || mouseController.notBuffered(shapeModel)) {
				 //System.out.println("paintShape " + this);
				 /*
				 if (shapeModel == selectedShape)
					 paintSelection(g);
				 else
				 */
                    paintShape(g, shapeModel);
			 }
        }
    }
    /*
    public Container getContainer () {
    	return container;
    }
    */
    public void paintKeys(Graphics g)
    {
        for (Enumeration shapeKeys = slModel.keys();shapeKeys.hasMoreElements();)
        {
            String key = (String) shapeKeys.nextElement();	
            RemoteShape shapeModel = slModel.get(key);
            if ((mouseController == null ) || mouseController.notBuffered(shapeModel))
                paintKey(g, key, shapeModel);
        }
    }
	public void paintLabels(Graphics g)
    {
        for (Enumeration shapeKeys = slModel.keys();shapeKeys.hasMoreElements();)
        {
            String key = (String) shapeKeys.nextElement();	
            RemoteShape shapeModel = slModel.get(key);
			if ((mouseController == null ) || mouseController.notBuffered(shapeModel)) {
                paintKey(g, slModel.getLabel(key), shapeModel);
			}
        }
    }
    public void paintShapeAndKey(Graphics g, String key, RemoteShape shapeModel)
    {
        //paintKey(g, key, shapeModel);
		/*
		if (shapeModel == selectedShape)
			paintSelection(g);
		else
		*/
           paintShape(g, shapeModel);
        //MouseController.paintHandle(g, shapeModel);

    }
    //private static void paintKey(Graphics g, String key, Shape shapeModel)
    private static void paintKey(Graphics g, String key, RemoteShape shapeModel)
    {
    	try {
		if (key == null)
			return;
        Rectangle bounds = shapeModel.getBounds();		
		int xOffset =  KEYOFFSET;
		int yOffset =  KEYOFFSET;
		if (shapeModel instanceof LineModel) {
			if (bounds.height > 0)
				yOffset = - KEYOFFSET*2;
			if (bounds.width >= 0)
				xOffset = - KEYOFFSET*2;
			else
				xOffset = + KEYOFFSET*12;
		}
        g.drawString(key, bounds.x - xOffset, bounds.y - yOffset);
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println(e);
    	}
    }
	

    private static final int KEYOFFSET = 5;

    public void eraseShapeAndKey(Graphics g, String key, RemoteShape shapeModel)
    {
        Color origColor = g.getColor();
        g.setColor(container.getBackground());
		/*
		if (key == getSelection())
	       paintShape(g, shapeModel, true);
		*/
        paintShape(g, shapeModel);
        paintKey(g, key, shapeModel);
        g.setColor(origColor);
    }
	/*
	Color selectionColor = Color.cyan;
	public void setSelectionColor(Color newVal) {
		selectionColor = newVal;
	}
	public Color getSelectionColor() {
		return selectionColor;
	}
	
	public void paintSelection(Graphics g)
    {
		//System.out.println("apint Selection");
		String key = getSelection();
		if (key == null) return;
		ShapeModel shapeModel = slModel.get(key);
		if (shapeModel == null) return;	
		
        Color origColor = g.getColor();
        g.setColor(getSelectionColor());
        paintShape(g, shapeModel, false);
        //paintKey(g, key, shapeModel);
		//ResizeController.paintResizeHandle(g, shapeModel);
        g.setColor(origColor);
		
    }
	*/
	static public void paintShape(Graphics g, RemoteShape shapeModel)
    {
		try {
		paintShape(g, shapeModel, shapeModel.isFilled());
		} catch (Exception e) {
			System.out.println();
		}
	}

    static public void paintShape(Graphics g, RemoteShape shapeModel, boolean fill)
    {
    	try {
		Color shapeColor = shapeModel.getColor();
		Color graphicsColor = g.getColor();
		Graphics2D g2d = (Graphics2D) g;
		Stroke basicStroke = shapeModel.getStroke();
    	if (basicStroke != null)
    		g2d.setStroke(basicStroke);
    	Paint gradientPaint = shapeModel.getPaint();
    	if (gradientPaint != null)
    		g2d.setPaint(gradientPaint);   
		if (shapeColor != null)
			g.setColor(shapeColor);
		
         try
            {
                //Class shapeClass = shapeModel.getClass();
                //if (shapeClass == Class.forName("shapes.LineModel"))
				//if (shapeModel instanceof LineModel)
				if (shapeModel instanceof RemoteLine)
                {
					//System.out.println(shapeModel.getBounds().x + " " + shapeModel.getBounds().y);
                    //paintLine(g, (RemoteLine) shapeModel, fill);
					paintLine2D(g2d, (RemoteLine) shapeModel, fill);
                }
                //else if (shapeClass == Class.forName("shapes.OvalModel"))
				else if (shapeModel instanceof RemoteOval) 
                {
                    paintOval(g, (RemoteOval) shapeModel, fill);
                }
                //else if (shapeClass == Class.forName("shapes.RectangleModel"))
				else if (shapeModel instanceof RemoteRectangle)
                {
                    //paintRectangle(g, (RemoteRectangle) shapeModel, fill);
                    paintRectangle2D(g, (RemoteRectangle) shapeModel, fill);
                } else if (shapeModel instanceof RemoteArc) {
                	//paintArc(g, (RemoteArc) shapeModel, fill);
                	paintArc2D(g2d, (RemoteArc) shapeModel, fill);
                } else if (shapeModel instanceof RemoteCurve) {
                	paintCurve2D(g2d, (RemoteCurve) shapeModel, fill);
                } else if (shapeModel instanceof shapes.StringModel) {
                	paintString(g, (RemoteText) shapeModel, fill);
                }
            }
            catch (Exception e)
            {
                System.err.println("Should have stored shape id: " + e.toString());
                e.printStackTrace();
            }
		 if (shapeColor != null)
			g.setColor(graphicsColor);
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    static private void paintString(Graphics g, RemoteText text, boolean fill)
    {
    	try {
    		Rectangle bounds = text.getBounds();
    		g.drawString(text.getText(), text.getX(), text.getY());
		
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    static private void paintLine(Graphics g, RemoteLine line, boolean fill)
    {
    	try {
        Rectangle bounds = line.getBounds();
		if (fill)
			g.drawLine(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height);
		else			
          g.drawLine(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height);
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    static private void paintLine2D(Graphics g, RemoteLine line, boolean fill)
    {
    	Graphics2D g2d = (Graphics2D) g;
    	

    	try {
    		Line2D.Double line2D = new Line2D.Double(line.getNWCorner(), line.getSECorner());
    		
    	
    		
        //Rectangle bounds = line.getBounds();
		if (fill)
			//g2d.fill(line2D);
			g2d.draw(line2D);
		else			
			g2d.draw(line2D);
    	
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    static private void paintArc2D(Graphics g, RemoteArc arc, boolean fill)
    {
    	Graphics2D g2d = (Graphics2D) g;
    	try {
        //Rectangle bounds = arc.getBounds();
    		Arc2D.Double arc2D = new Arc2D.Double(arc.getX(), arc.getY(), arc.getWidth(), arc.getHeight(), arc.getStartAngle(), arc.getEndAngle(), Arc2D.OPEN);
		if (fill)
			g2d.fill(arc2D);
		else			
           g2d.draw(arc2D);
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    static final int ARC_WIDTH = 10;
    static final int ARC_HEIGHT = 10;
    static private void paintRectangle2D(Graphics g, RemoteRectangle rectangle, boolean fill)
    {
    	
    	Graphics2D g2d = (Graphics2D) g;
    	try {
    		if (rectangle.isRounded()) {
        		paintRoundedRectangle2D(g, rectangle, fill);
        		return;
        	}
        //Rectangle bounds = rectangle.getBounds();
    		Rectangle2D.Double rectangle2D = new Rectangle2D.Double(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    		
		if (fill) {
			g2d.fill(rectangle2D);
			Color origColor = g.getColor();
			g.setColor(Color.BLACK);
			g2d.draw(rectangle2D);
			g.setColor(origColor);
			//g2d.draw(rectangle2D);
		}
		else
		
           g2d.draw(rectangle2D);
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    static private void paintRoundedRectangle2D(Graphics g, RemoteRectangle rectangle, boolean fill)
    {
    	Graphics2D g2d = (Graphics2D) g;
    	try {
        //Rectangle bounds = rectangle.getBounds();
    		RoundRectangle2D.Double rectangle2D= new RoundRectangle2D.Double(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), ARC_WIDTH, ARC_HEIGHT);
    		
		if (fill) {
			g2d.fill(rectangle2D);
			Color origColor = g.getColor();
			g.setColor(Color.BLACK);
			g2d.draw(rectangle2D);
			g.setColor(origColor);
			//g2d.draw(rectangle2D);
		}
		else
		
           g2d.draw(rectangle2D);
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    static private void paintCurve2D(Graphics2D g, RemoteCurve curve, boolean fill)
    {
    	try {
    	if (curve.getControlX2() != null && curve.getControlY2() != null)
    		paintCubeCurve2D(g, curve, fill);
    	else
    		paintQuadCurve2D(g, curve, fill);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    static private void paintQuadCurve2D(Graphics g, RemoteCurve curve, boolean fill)
    {
    	Graphics2D g2d = (Graphics2D) g;
    	try {
        //Rectangle bounds = arc.getBounds();
    		QuadCurve2D.Double arc2D = new QuadCurve2D.Double(curve.getX(), curve.getY(), curve.getWidth(), curve.getHeight(), curve.getControlX(), curve.getControlY());
		if (fill)
			g2d.fill(arc2D);
		else			
           g2d.draw(arc2D);
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    
    static private void paintCubeCurve2D(Graphics g, RemoteCurve curve, boolean fill)
    {
    	Graphics2D g2d = (Graphics2D) g;
    	try {
        //Rectangle bounds = arc.getBounds();
    		CubicCurve2D.Double arc2D = new CubicCurve2D.Double(curve.getX(), curve.getY(), curve.getWidth(), curve.getHeight(), curve.getControlX(), curve.getControlY(), curve.getControlX2(), curve.getControlY2());
		if (fill)
			g2d.fill(arc2D);
		else			
           g2d.draw(arc2D);
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    
    static private void paintRectangle(Graphics g, RemoteRectangle rectangle, boolean fill)
    {
    	
    	try {
        Rectangle bounds = rectangle.getBounds();
		if (fill)
			g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		else			
           //g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		   g.draw3DRect(bounds.x, bounds.y, bounds.width, bounds.height, false);
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    static private void paintArc(Graphics g, RemoteArc arc, boolean fill)
    {
    	try {
        Rectangle bounds = arc.getBounds();
		if (fill)
			g.fillArc(bounds.x, bounds.y, bounds.width, bounds.height, arc.getStartAngle(), arc.getEndAngle());
		else			
           g.drawArc(bounds.x, bounds.y, bounds.width, bounds.height, arc.getStartAngle(), arc.getEndAngle());
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }

    static private void paintOval(Graphics g, RemoteOval oval, boolean fill)
    {
    	try {
        Rectangle bounds = oval.getBounds();
		//System.out.println("painting oval" + bounds.x + " " + bounds.y + " " + bounds.width + " " + bounds.height);
		if (fill)
			g.fillOval(bounds.x, bounds.y, bounds.width, bounds.height);
		else
            g.drawOval(bounds.x, bounds.y, bounds.width, bounds.height);
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }


}
