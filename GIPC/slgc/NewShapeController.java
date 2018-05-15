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
import slm.SLPutCommand;
import slgv.SLGView;
import java.net.InetAddress;

public abstract class NewShapeController extends slgc.DragController
{
    protected String newKey;
	//protected String newName;
    //protected boolean shapeDragged;

    public void mousePressed(MouseEvent e)
    {
        //System.out.println("Mouse pressed");
        draggableShapeModel = newShape(e.getX(),e.getY());
        //draggedKey = "";	
		draggedKey = nextKey();	
		newName = null;
		if (slgController.getIncremental()){
			processNewShape(e);
		}		
        super.mousePressed(e);
    }
    public void mouseReleased(MouseEvent e)
    {
		if (processNewShape(e))
			 super.mouseReleased(e);
		/*
        if (shapeModelWasDragged && !slgController.getIncremental())
        {
			if (slgController.getPrompt()) {
				slgController.newShapeDialog.setVisible(true);
				newName = slgController.newShapeDialog.inputString;
				draggedKey = nextKey();
				newName = slgController.newShapeDialog.inputString;
				//draggedKey = slgController.newShapeDialog.inputString;
			} else {
				draggedKey = nextKey();
				newName = "";
			}
           super.mouseReleased(e);
        }
		*/
    }
	protected boolean processNewShape (MouseEvent e) {
		if (shapeModelWasDragged && !slgController.getIncremental())
        {
			if (slgController.getPrompt()) {
				slgController.newShapeDialog.setLocation(e.getPoint());
				slgController.newShapeDialog.setVisible(true);
				//newName = slgController.newShapeDialog.inputString;
				//draggedKey = nextKey();
				newName = slgController.newShapeDialog.inputString;
				//draggedKey = slgController.newShapeDialog.inputString;
			} else {
				//draggedKey = nextKey();
				newName = null;
			}
           return true;
        }
		return false;
		
		/*
		if (slgController.getPrompt() && !slgController.getIncremental()) {
				slgController.newShapeDialog.setVisible(true);
				draggedKey = slgController.newShapeDialog.inputString;
			} else
				draggedKey = nextKey();
		*/
	}
    public void mouseDragged(MouseEvent e)
    {
        setLRCorner(draggableShapeModel, e.getX(), e.getY());
        super.mouseDragged(e);
    }
    public static void setLRCorner (RemoteShape shape, int lrX, int lrY)
    {
    	try {
        Rectangle curBounds = shape.getBounds();
        shape.setBounds(curBounds.x, curBounds.y,
            lrX - curBounds.x, lrY - curBounds.y);
    	} catch (Exception e2) {
    		e2.printStackTrace();
    		System.out.println(e2);
    	}

    }
	protected String myLocation; 
	//protected String myNumber = Math.random();
	protected String myName;
	protected String nextName() {
		return  keyPrefix() + nextKeyNum();		
	}
	protected String key(String name) {
		try {
			if (myName == null) {
				myLocation = InetAddress.getLocalHost().getHostName();
				myName = myLocation + ":";
			}
		    return myName + name;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			return name;
		}
		
	}
	protected String nextKey() {
		try {
			if (myName == null) {
				myLocation = InetAddress.getLocalHost().getHostName();
				myName = myLocation + ":";
			}
		    return myName + keyPrefix() + nextKeyNum();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			return keyPrefix() + nextKeyNum();
		}
	}
	
	private  int nextKeyNum = 0;
	protected  int nextKeyNum() {
		return (nextKeyNum++);
	}
    abstract protected RemoteShape newShape(int x, int y);
	abstract protected String keyPrefix();

}

