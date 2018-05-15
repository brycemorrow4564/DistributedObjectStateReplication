package slgv;
import java.awt.Container;
import java.awt.Panel;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Shape;
import java.awt.Color;
import java.awt.Component;
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
import shapes.RemoteLine;
import shapes.RemoteOval;
import shapes.RemoteRectangle;
import shapes.RemoteShape;
import slc.SLComposer;
//import bus.agent.AutoAllConnect;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import java.util.Vector;
import slm.SLPutCommand;

public class SLGViewPanel extends JPanel 
{
    //private boolean paintKeys = false;
	//private boolean paintLabels = false;
    //private ShapesList slModel;
    //private MouseController mouseController;
	//TextField tryField = new TextField("hello world");
    //public SLGViewPanel (ShapesList theSLModel)
	SLGView slgView;
    public SLGViewPanel (SLGView theSLGView)
    {
        super();
        slgView = theSLGView;
		//init();
		//setSLModel(theSLModel);
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
	void init () {
		this.setLayout(null);
		
	}
	*/
    /*
	public SLGViewPanel ()
    {
        super();
		init();
		//this.add(tryField);
		

    }
    */
	/*
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
			System.out.println(e);
		}

    }
    */
    /*
    public void setMouseController (MouseController theMouseController)
    {
        this.removeMouseListener(mouseController);
        this.removeMouseMotionListener(mouseController);
        mouseController = theMouseController;
        this.addMouseListener(mouseController);
        this.addMouseMotionListener(mouseController);
        this.repaint();
    }
    
	SLGController slgController = null;
	public void setController (SLGController theSLGController)
    {
        slgController = theSLGController;
    }

    public void toggleKeys()
    {
       paintKeys = !paintKeys;
       this.repaint();
    }
	public void toggleLabels()
    {
       paintLabels = !paintLabels;
       this.repaint();
    }
    */
	/*
    public void update(Listenable model, Object arg)
    {
    	try {
		//System.out.println("update called");
        if ((arg != null) && (arg instanceof SLPutCommand)) {
			
		    //System.out.println("Update called on view " + this);
            RemoteShape shapeModel = ((SLPutCommand) arg).getShapeModel();
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
        this.repaint();
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    }
	public void updateComponents() {
		Component[] currentComponents = this.getComponents();
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
					
				 } else
					 add(component);
			 }
		}
		for (Enumeration elements = currentComponentVector.elements();
			 elements.hasMoreElements();) {
			this.remove((Component) elements.nextElement());
		}		 
		
	}
	*/
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
    	super.paint(g);
    	slgView.paint(g);
		/*
        slgView.paintShapes(g);
        if (slgView.paintKeys)
            slgView.paintKeys(g);
		if (slgView.paintLabels)
			this.paintLabels(g);
		if (slgView.mouseController != null)
			slgView.mouseController.paint(g);
		*/
		//paintSelection(g);
    }
    /*
    public void paintShapes(Graphics g)
    {
		//System.out.println("slModel " + slModel);
        for (Enumeration shapeModels = slgView.slModel.elements();shapeModels.hasMoreElements();)
        {
		     //System.out.println("paintShapes " + this);
			 RemoteShape shapeModel = (RemoteShape) shapeModels.nextElement();
			 if ((mouseController == null ) || mouseController.notBuffered(shapeModel)) {
				 //System.out.println("paintShape " + this);
				 
                    paintShape(g, shapeModel);
			 }
        }
    }
    */
    /*
    public Container getContainer () {
    	return container;
    }
    */
    /*
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
    */
    /*
    public void paintShapeAndKey(Graphics g, String key, RemoteShape shapeModel)
    {
        //paintKey(g, key, shapeModel);
		
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
    		System.out.println(e);
    	}
    }
    /*
	

    private static final int KEYOFFSET = 5;

    public void eraseShapeAndKey(Graphics g, String key, RemoteShape shapeModel)
    {
        Color origColor = g.getColor();
        g.setColor(this.getBackground());
		
        paintShape(g, shapeModel);
        paintKey(g, key, shapeModel);
        g.setColor(origColor);
    }
    */
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
    /*
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
                    paintLine(g, (RemoteLine) shapeModel, fill);
                }
                //else if (shapeClass == Class.forName("shapes.OvalModel"))
				else if (shapeModel instanceof RemoteOval) 
                {
                    paintOval(g, (RemoteOval) shapeModel, fill);
                }
                //else if (shapeClass == Class.forName("shapes.RectangleModel"))
				else if (shapeModel instanceof RemoteRectangle)
                {
                    paintRectangle(g, (RemoteRectangle) shapeModel, fill);
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
    		System.out.println(e);
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
    		System.out.println(e);
    	}
    }
    static private void paintRectangle(Graphics g, RemoteRectangle rectangle, boolean fill)
    {
    	try {
        Rectangle bounds = rectangle.getBounds();
		if (fill)
			g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		else			
           g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
    	} catch (Exception e) {
    		System.out.println(e);
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
    		System.out.println(e);
    	}
    }
    */


}
