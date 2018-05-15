package slgc;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import java.awt.Point;

import shapes.RemoteShape;
import shapes.ShapeModel;
import java.awt.Graphics;
import java.awt.Shape;
//import bus.agent.AutoAllConnect;
class MoveController extends slgc.ModalSelectionController //implements AutoAllConnect
{
    public void mouseDragged(MouseEvent e)
    {
        if (shapeModelWasSelected && selectedKeyIsEditable) {
           moveULCorner(draggableShapeModel, e.getX(), e.getY());
           super.mouseDragged(e);
        }
    }

    static public void moveULCorner (RemoteShape shape, int ulX, int ulY)
    {
    	try {
		//System.out.println(shape);
        Rectangle curBounds = shape.getBounds();
        shape.setBounds(ulX, ulY,
            curBounds.width, curBounds.height);
    	} catch (Exception e2) {
    		e2.printStackTrace();
    		System.out.println(e2);
    	}
    }
	public void paintHandle(Graphics g, RemoteShape shapeModel)
    {
		paintMoveHandle(g, shapeModel);
    }
	public static void paintMoveHandle(Graphics g, RemoteShape shapeModel)
    {
		
		try {
        Rectangle handle= getHandle(shapeModel.getBounds());
        g.drawOval(handle.x, handle.y, handle.width, handle.height);
		} catch (Exception e2) {
    		System.out.println(e2);
    	}
		
		
    }
	public static void paintMoveHandle(Graphics g, Shape shapeModel,Point p)
    {
        Rectangle handle= getHandle(shapeModel.getBounds());
        g.drawOval(p.x, p.y, handle.width, handle.height);
    }
}

