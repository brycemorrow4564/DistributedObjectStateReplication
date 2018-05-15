package slgc;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Hashtable;

import shapes.RemoteShape;
import shapes.ShapeModel;
import slm.SLModel;
import slgv.SLGView;

public class SelectionController extends  slgc.DragController
{
    static  final int HANDLE_SIZE = 5;
    protected boolean shapeModelWasSelected;
    protected String selectedKey;
	protected boolean selectedKeyIsEditable = true;
    //protected ShapeModel selectedShapeModel = null;
    protected RemoteShape selectedShapeModel = null;

    /*
	public void mousePressed(MouseEvent e)
    {
        Point mousePosition = new Point(e.getX(), e.getY());
        this.setSelections(mousePosition);
        if (shapeModelWasSelected) {
            draggableShapeModel = (ShapeModel) selectedShapeModel.clone();
            draggedKey = selectedKey;
            super.mousePressed(e);
        }
    }
	*/
	public void paint(Graphics g)
    {
       super.paint(g);
       this.paintHandles(g);
       /*
       if (shapeModelWasDragged)
       {
           slgView.eraseShapeAndKey(g, selectedKey, selectedShapeModel);
       }
	   */
	   
       
    }
	public void paintHandles(Graphics g) {};
    public void mouseReleased (MouseEvent e)
    {
		//System.out.println("Selection mouse released");
        if (shapeModelWasSelected && selectedKeyIsEditable)
            super.mouseReleased(e);
    }
	public void mouseDragged (MouseEvent e)
    {
		try {
        if (slgController.getIncremental())
            draggableShapeModel = (RemoteShape) draggableShapeModel.remoteClone();
		super.mouseDragged (e);
		} catch (Exception e2) {
			e2.printStackTrace();
    		//System.out.println(e2);
    	}
    }
	/*
    protected void setSelections (Point mousePosition)
    {
        shapeModelWasSelected = false;
        for (Enumeration shapeKeys = slModel.keys();
                 shapeKeys.hasMoreElements() && !shapeModelWasSelected;)
            shapeModelWasSelected = getHandle (
              (selectedShapeModel = slModel.get(selectedKey = (String) shapeKeys.nextElement())).getBounds()
              ).contains(mousePosition);
		
		
		
    }
	
    public void paint(Graphics g)
    {
       super.paint(g);
       this.paintHandles(g);
       
    }
*/
    public boolean notBuffered(RemoteShape theShapeModel)
    {
        if (shapeModelWasDragged & (theShapeModel == selectedShapeModel))
            return(false);
        else
            return(true);
    }
	/*
    public void paintHandles(Graphics g)
    {
         for (Enumeration shapeModels = slModel.elements();shapeModels.hasMoreElements();)
         {
             ShapeModel shapeModel = (ShapeModel) shapeModels.nextElement();
             if ((shapeModel == selectedShapeModel) && shapeModelWasDragged)
                 paintHandle(g, draggableShapeModel);
             else
                 paintHandle(g, shapeModel);
         }
    }
	*/
    //public void paintHandle(Graphics g, Shape shapeModel)
    public void paintHandle(Graphics g, RemoteShape shapeModel)
    {
    	try {
        Rectangle handle= getHandle(shapeModel.getBounds());
        g.drawOval(handle.x, handle.y, handle.width, handle.height);
    	} catch (Exception e2) {
    		e2.printStackTrace();
    		//System.out.println(e2);
    	}
    }
	
	
    static public Rectangle getHandle (Rectangle bounds)
    {    	
		return getHandle(bounds, bounds.getLocation());
		/*
        return new Rectangle (bounds.x - HANDLE_SIZE/2,
           bounds.y - HANDLE_SIZE/2, HANDLE_SIZE, HANDLE_SIZE);
		*/
    }
    static public Rectangle getNWHandle (Rectangle bounds)
    {
		return getHandle(bounds, bounds.getLocation());
		/*
        return new Rectangle (bounds.x - HANDLE_SIZE/2,
           bounds.y - HANDLE_SIZE/2, HANDLE_SIZE, HANDLE_SIZE);
		*/
    }
	static public Rectangle getHandle (Rectangle bounds, Point p)
    {
        return new Rectangle (p.x - HANDLE_SIZE/2,
           p.y - HANDLE_SIZE/2, HANDLE_SIZE, HANDLE_SIZE);
    }
	static public Rectangle getHandle (Point p)
    {
        return new Rectangle (p.x - HANDLE_SIZE/2,
           p.y - HANDLE_SIZE/2, HANDLE_SIZE, HANDLE_SIZE);
    }

}

