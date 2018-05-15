package slm;
import java.util.Enumeration;

import util.undo.AbstractCommand;
import util.undo.Undoer;
public class SLSetCommand extends util.undo.AbstractCommand
{
    private ShapesList shapesList;
    private ShapesList newShapesList;
    public SLSetCommand (ShapesList theShapesList,
        ShapesList theNewShapesList)
    {
        shapesList = theShapesList;
        newShapesList = theNewShapesList;
    }
    private ShapesList originalShapesList /*= new SLModel()*/;
    public SLSetCommand() {
    	try {
    		originalShapesList = new SLModel();
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    		
    	}
    }
	public ShapesList getNewVal() {
		
		return newShapesList;
	}

    public boolean isUndoable()
    {
        return(true);
    }
    public void execute()
    {
        originalShapesList = (ShapesList) shapesList.clone();
        shapesList.set(newShapesList);
    }
    public void undo()
    {
        shapesList.set(originalShapesList);
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

    public static void set (Undoer undoer,
        ShapesList theShapesList, ShapesList newShapesList)
    {
        SLSetCommand c =  new SLSetCommand(theShapesList, newShapesList);
        undoer.execute(c);
    }


}

