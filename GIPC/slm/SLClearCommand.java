package slm;
import java.util.Enumeration;

import util.undo.AbstractCommand;
import util.undo.Undoer;
public class SLClearCommand extends util.undo.AbstractCommand
{
    private ShapesList shapesList;
    public SLClearCommand (ShapesList theShapesList)
    {
        shapesList = theShapesList;
    }
    private ShapesList originalShapesList /*= new SLModel()*/;
    public SLClearCommand() {
    	try {
    	originalShapesList = new SLModel();
    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }

    public boolean isUndoable()
    {
        return(true);
    }
    public void execute()
    {
        this.saveShapesList();
        shapesList.clear();
    }
    public void undo()
    {
        shapesList.set(originalShapesList);
    }
    public void redo()
    {
        execute();
    }
    private void saveShapesList()
    {
        originalShapesList = (ShapesList) shapesList.clone();
        if (shapesList == originalShapesList)
            System.out.println("Pointer Copy");

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

    public static void clear (Undoer undoer, ShapesList theShapesList)
    {
        SLClearCommand c =  new SLClearCommand(theShapesList);
        undoer.execute(c);
    }


}

