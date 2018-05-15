package slm;
import java.util.Enumeration;

import util.undo.AbstractCommand;
import util.undo.Undoer;
import shapes.RemoteShape;
import shapes.ShapeModel;
import java.awt.Rectangle;
public class SLSetBoundsCommand extends util.undo.AbstractCommand
{
    private ShapesList shapesList;
    private Rectangle originalBounds = null;
    private String keyArg;
    private Rectangle boundsArg;
	
    public SLSetBoundsCommand (ShapesList theShapesList,
       String theKey, Rectangle theBounds)
    {
        shapesList = theShapesList;
        boundsArg = theBounds;
        keyArg = theKey;

    }
	public String getKey() {
		return keyArg;
	}
	public Rectangle getBoundsArg() {
		return boundsArg;
	}
    public boolean isUndoable()
    {
        return(true);
    }
    public void execute()
    {
    	try {
		RemoteShape shapeModel;
		shapeModel = shapesList.get(keyArg);
		if (shapeModel != null)
           originalBounds = new Rectangle (shapeModel.getBounds());
		shapesList.setBounds(keyArg, boundsArg);
		//System.out.println("shapeModelArg " + shapeModelArg + "original" + originalShapeModel);
    	} catch (Exception e ) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    public void undo()
    {
    	try {
        (shapesList.get(keyArg)).setBounds(originalBounds.x, originalBounds.y, originalBounds.width, originalBounds.height);
    	} catch (Exception e ) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
    public void redo()
    {
        execute();
    }

    public static void printKeys(ShapesList theShapesList)
    {
        System.out.println("Printing Keys");
        for (Enumeration keys = theShapesList.keys();
                keys.hasMoreElements();)
            {
                String key = (String) keys.nextElement();
                System.out.println("Key" + (String) key);
            }
    }

    public static void setBounds (Undoer undoer, ShapesList theShapesList,
       String theKey, Rectangle theBounds)
    {
        SLSetBoundsCommand c =  new SLSetBoundsCommand(theShapesList, theKey,theBounds );
        undoer.execute(c);
    }


}

