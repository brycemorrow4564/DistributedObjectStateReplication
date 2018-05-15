package slgc;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.Point;

import shapes.RemoteShape;
import shapes.ShapeModel;
import shapes.PointModel;
//import bus.agent.AutoAllConnect;
public class ResizeController extends slgc.ModalSelectionController //implements AutoAllConnect
{
	static final int NW = 0;
	static final int N = 1;
	static final int NE = 2;
	static final int W = 3;
	static final int E = 4;
	static final int SW = 5;
	static final int S = 6;
	static final int SE = 7;
	int whichHandle;
	//int startX, startY;
	 public void mousePressed(MouseEvent e) {
	 	super. mousePressed(e);
	 	if (selectedShapeModel != null) {
	 		whichHandle = whichHandle(selectedShapeModel, e.getX(), e.getY());
	 	}
	 	//startX = e.getX();
	 	//startY = e.getY();		
	 	
	 }
    public void mouseDragged(MouseEvent e)
    {
    	
        if (shapeModelWasSelected && selectedKeyIsEditable ) {
           
        	switch (whichHandle) {
           	case NW: setNWCorner(draggableShapeModel, e.getX(), e.getY());
           			 break;
           	default:;
           }
           //setULCorner(draggableShapeModel, e.getX(), e.getY());
           super.mouseDragged(e);
           
        	//resizeSelectedShape (e);
        }
        
    }
    
    public static void resizeSelectedShape (RemoteShape shape, int whichHandle, MouseEvent e) {
    	try {
    	//if (shapeModelWasSelected && selectedKeyIsEditable) {
            switch (whichHandle) {
            	case NW: setNWCorner(shape, e.getX(), e.getY());
            			 break;
    			case N: setNorthEnd(shape, e.getX(), e.getY());
    					 break;
    			case NE: setNECorner(shape, e.getX(), e.getY());
				 		 break;
    			case W: setWestEnd(shape, e.getX(), e.getY());
   			 			 break;
    			case E: setEastEnd(shape, e.getX(), e.getY());
		 			 break;
    			case SW: setSWCorner(shape, e.getX(), e.getY());
		 			 break;
    			case S: setSouthEnd(shape, e.getX(), e.getY());
		 			 break;
    			case SE: setSECorner(shape, e.getX(), e.getY());
		 			 break;
            	default:;
            }
    	} catch (Exception e2) {
    		e2.printStackTrace();
    		//System.out.println(e2);
    	}
    	
    }

    static public void setNWCorner (RemoteShape shape, int ulX, int ulY)
    {
    	try {
        Rectangle curBounds = shape.getBounds();
        shape.setBounds(ulX, ulY,
            curBounds.width + curBounds.x - ulX, curBounds.height + curBounds.y - ulY);
    	} catch (Exception e2) {
    		e2.printStackTrace();
    		//System.out.println(e2);
    	}
    }
    static public void setNorthEnd(RemoteShape shape, int ulX, int ulY)
    {
    	try {
        Rectangle curBounds = shape.getBounds();
        shape.setBounds(curBounds.x, ulY,
            curBounds.width , curBounds.height + curBounds.y - ulY);
    	} catch (Exception e2) {
    		e2.printStackTrace();
    		//System.out.println(e2);
    	}
    }
    static public void setNECorner (RemoteShape shape, int urX, int urY)
    {
    	try {
        Rectangle curBounds = shape.getBounds();
        shape.setBounds(curBounds.x, urY,
            urX - curBounds.x, curBounds.height +  curBounds.y - urY);
    	} catch (Exception e2) {
    		e2.printStackTrace();
    		//System.out.println(e2);
    	}
    }
    static public void setWestEnd (RemoteShape shape, int westX, int westY)
    {
    	try {
        Rectangle curBounds = shape.getBounds();
        shape.setBounds(westX, curBounds.y,
            curBounds.width + curBounds.x - westX, curBounds.height);
    	} catch (Exception e2) {
    		e2.printStackTrace();
    		//System.out.println(e2);
    	}
    }
    static public void setEastEnd (RemoteShape shape, int eastX, int eastY)
    {
    	try {
        Rectangle curBounds = shape.getBounds();
        shape.setBounds(curBounds.x, curBounds.y,
            eastX - curBounds.x, curBounds.height);
    	} catch (Exception e2) {
    		e2.printStackTrace();
    		//System.out.println(e2);
    	}
    }
    static public void setSWCorner(RemoteShape shape, int llX, int llY)
    {
    	try {
        Rectangle curBounds = shape.getBounds();
        shape.setBounds(llX, curBounds.y, curBounds.width + 
            curBounds.x - llX,  llY - curBounds.y);
    	} catch (Exception e2) {
    		e2.printStackTrace();
    		//System.out.println(e2);
    	}
    }
    static public void setSouthEnd (RemoteShape shape, int southX, int southY)
    {
    	try {
        Rectangle curBounds = shape.getBounds();
        shape.setBounds(curBounds.x, curBounds.y,
            curBounds.width,  southY - curBounds.y);
    	} catch (Exception e2) {
    		e2.printStackTrace();
    		//System.out.println(e2);
    	}
    }
    static public void setSECorner (RemoteShape shape, int lrX, int lrY)
    {
    	try {
        Rectangle curBounds = shape.getBounds();
        shape.setBounds(curBounds.x, curBounds.y,
            lrX - curBounds.x, lrY - curBounds.y);
    	} catch (Exception e2) {
    		e2.printStackTrace();
    		//System.out.println(e2);
    	}
    }
	public void paintHandle(Graphics g, RemoteShape shapeModel)
    {
		paintResizeHandle(g, shapeModel);
    }
	public static void paintResizeHandle(Graphics g, RemoteShape shapeModel)
    {
        //Rectangle handle= getHandle(shapeModel.getBounds());
		paintHandle (g, getNWHandle(shapeModel));
		if (shapeModel instanceof PointModel) return;
		paintHandle (g, getNorthHandle(shapeModel));
		paintHandle (g, getNEHandle(shapeModel));
		paintHandle (g, getWestHandle(shapeModel));
		paintHandle (g, getEastHandle(shapeModel));
		paintHandle (g, getSWHandle(shapeModel));
		paintHandle (g, getSouthHandle(shapeModel));
		paintHandle (g, getSEHandle(shapeModel));
        //g.drawOval(handle.x, handle.y, handle.width, handle.height);
    }
	//public static Rectangle getNWHandle (ShapeModel shape) {
	public static Rectangle getNWHandle (RemoteShape shape) {
		try {
		return getHandle (shape.getNWCorner());
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e);
			return null;
		}
	}
	public static Rectangle getNorthHandle (RemoteShape shape) {
		try {
		return getHandle (shape.getNorthEnd());
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e);
			return null;
		}
	}
	public static Rectangle getNEHandle (RemoteShape shape) {
		try {
		return getHandle (shape.getNECorner());
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e);
			return null;
		}
	}
	public static Rectangle getWestHandle (RemoteShape shape) {
		try {
		return getHandle (shape.getWestEnd());
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e);
			return null;
		}
	}
	public static Rectangle getEastHandle (RemoteShape shape) {
		try {
		return getHandle (shape.getEastEnd());
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e);
			return null;
		}
	}
	public static Rectangle getSWHandle (RemoteShape shape) {
		try {
		return getHandle (shape.getSWCorner());
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e);
			return null;
		}
	}
	public static Rectangle getSouthHandle (RemoteShape shape) {
		try {
		return getHandle (shape.getSouthEnd());
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e);
			return null;
		}
	}
	public static Rectangle getSEHandle (RemoteShape shape) {
		try {
		return getHandle (shape.getSECorner());
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e);
			return null;
		}
	}
	public static void paintHandle (Graphics g, Rectangle handle){
		
		java.awt.Color oldColor = g.getColor();
		g.setColor((new javax.swing.JPanel()).getBackground());
		g.fillOval(handle.x, handle.y, handle.width, handle.height);
		g.setColor(oldColor);
		g.drawOval(handle.x, handle.y, handle.width, handle.height);
		
	}
	public static void paintResizeHandle(Graphics g, Shape shapeModel, Point p)
    {
        Rectangle handle= getHandle(shapeModel.getBounds());
        g.drawOval(p.x, p.y, handle.width, handle.height);
    }
	//public static int whichHandle (ShapeModel shapeModel, int x, int y){
	public static int whichHandle (RemoteShape shapeModel, int x, int y){
		if (getNWHandle(shapeModel).contains(x,y))
			return NW;
		else if (getNorthHandle(shapeModel).contains(x,y))
			return N;
		else if (getNEHandle(shapeModel).contains(x,y))
			return NE;
		else if (getWestHandle(shapeModel).contains(x,y))
			return W;
		else if (getEastHandle(shapeModel).contains(x,y))
			return E;
		else if (getSWHandle(shapeModel).contains(x,y))
			return SW;
		else if (getSouthHandle(shapeModel).contains(x,y))
			return S;
		else if (getSEHandle(shapeModel).contains(x,y))
			return SE;
		return -1;
		
		
		
	}
}

