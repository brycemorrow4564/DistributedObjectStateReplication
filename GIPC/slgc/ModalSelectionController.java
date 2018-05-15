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

public class ModalSelectionController extends  slgc.SelectionController
{
    static private final int HANDLESIZE = 8;
    //protected boolean shapeModelWasSelected;
    //String selectedKey;
    //protected ShapeModel selectedShapeModel = null;

    public void mousePressed(MouseEvent e)
    {
    	try {
		//System.out.println("mouse pressed");
        Point mousePosition = new Point(e.getX(), e.getY());
        this.setSelections(mousePosition);
		//System.out.println(shapeModelWasSelected);
        if (shapeModelWasSelected && selectedKeyIsEditable) {
			//System.out.println("selected shape model");
            draggableShapeModel = (RemoteShape) selectedShapeModel.remoteClone();
            draggedKey = selectedKey;
            super.mousePressed(e);
        }
    	} catch (Exception e2) {
    		e2.printStackTrace();
    		//System.out.println(e2);
    	}
    }
	/*

    public void mouseReleased (MouseEvent e)
    {
        if (shapeModelWasSelected )
            super.mouseReleased(e);
    }
	public void mouseDragged (MouseEvent e)
    {
        if (slgController.getIncremental())
            draggableShapeModel = (ShapeModel) draggableShapeModel.clone();
		super.mouseDragged (e);
    }
	*/
    protected void setSelections (Point mousePosition)
    {
    	try {
        shapeModelWasSelected = false;
		Rectangle handle = new Rectangle();
        for (Enumeration shapeKeys = slModel.keys();
			 shapeKeys.hasMoreElements() && !shapeModelWasSelected;) {
			/*
			handle = getHandle (
              (selectedShapeModel = slModel.get(selectedKey = (String) shapeKeys.nextElement())).getBounds()
              );
			*/
			
            shapeModelWasSelected = getHandle (
              (selectedShapeModel = slModel.get(selectedKey = (String) shapeKeys.nextElement())).getBounds()
              ).contains(mousePosition);
			
			//shapeModelWasSelected = handle.contains(mousePosition);
		}
		if (shapeModelWasSelected)
			selectedKeyIsEditable = slgController.isEditable(selectedKey);
		//System.out.println("handle" + handle + "point" + mousePosition);
		
    	} catch (Exception e2) {
    		e2.printStackTrace();
    		System.out.println(e2);
    	}
		
    }
	/*
    public void paint(Graphics g)
    {
       super.paint(g);
       this.paintHandles(g);
	   
       
    }
	*/
	/*
    public boolean notBuffered(ShapeModel theShapeModel)
    {
        if (shapeModelWasDragged & (theShapeModel == selectedShapeModel))
            return(false);
        else
            return(true);
    }
	*/
    public void paintHandles(Graphics g)
    {
         for (Enumeration shapeModels = slModel.elements();shapeModels.hasMoreElements();)
         {
             RemoteShape shapeModel = (RemoteShape) shapeModels.nextElement();
             if ((shapeModel == selectedShapeModel) && shapeModelWasDragged)
                 paintHandle(g, draggableShapeModel);
             else
                 paintHandle(g, shapeModel);
         }
    }
	/*
    public void paintHandle(Graphics g, Shape shapeModel)
    {
        Rectangle handle= getHandle(shapeModel.getBounds());
        g.drawRect(handle.x, handle.y, handle.width, handle.height);
    }
	*/
	
	/*
    static public Rectangle getHandle (Rectangle bounds)
    {
        return new Rectangle (bounds.x - HANDLESIZE/2,
           bounds.y - HANDLESIZE/2, HANDLESIZE, HANDLESIZE);
    }
	*/

}

