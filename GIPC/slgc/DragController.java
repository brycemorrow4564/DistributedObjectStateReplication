package slgc;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
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
import slm.SLPutCommand;

public abstract class DragController extends slgc.MouseController
{
    protected boolean shapeModelWasDragged = false;
    //protected ShapeModel draggableShapeModel;
    protected RemoteShape draggableShapeModel;
    protected String draggedKey;
	protected String newName;
    private boolean dragMode = false;

    public void paint(Graphics g)
    {
      super.paint(g);
      if (shapeModelWasDragged)
      {
            //System.out.println("shapeModelWasDragged");
            slgView.paintShapeAndKey(g, draggedKey, draggableShapeModel);
      }

    }
    public void mousePressed(MouseEvent e)
    {
    	try {
		shapeModelWasDragged = false;
		if (!slgController.getIncremental())
			draggableShapeModel.addListener(slgView);
    	} catch (Exception e2) {
    		System.out.println(e2);
    	}
    }
    public void mouseReleased (MouseEvent e)
    {
    	try {
		//System.out.println("mouse released");
		if (!slgController.getIncremental()) {
			draggableShapeModel.removeListener(slgView);
			if (shapeModelWasDragged)
			{
				//System.out.println("was dragged");
			     //SLPutCommand.put (slgController.commandHistory, slModel,
			      //   draggedKey, draggableShapeModel);
				
				if (slModel.get(draggedKey) == null) {
			      slModel.put(draggedKey,draggableShapeModel);
				  if (newName != null)
				    slModel.put(draggedKey,newName);
				}
				else {
				 slModel.setBounds(draggedKey,draggableShapeModel.getBounds());
				}
			}
		}
        shapeModelWasDragged = false;
    	} catch (Exception e2) {
    		System.out.println(e2);
    	}
    }
    public void mouseDragged(MouseEvent e)
    {
    	try {
		//System.out.println(slgController);
        shapeModelWasDragged = true;
		if (slgController.getIncremental())
			if (slModel.get(draggedKey) == null) {
			    slModel.put(draggedKey,draggableShapeModel);
				if (newName != null)
				  slModel.put(draggedKey,newName);
			}
			else 
			    slModel.setBounds(draggedKey,draggableShapeModel.getBounds());
    	} catch (Exception e2) {
    		System.out.println(e2);
    	}
    }

    public void mouseMoved(MouseEvent e)
    {
    }
	
										   
}

