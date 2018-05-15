package slm;
import java.util.Enumeration;

import util.undo.AbstractCommand;
import util.undo.Undoer;
import shapes.RemoteShape;
import shapes.ShapeModel;
public class SLRemoveLabelCommand extends util.undo.AbstractCommand
{
    private ShapesList slModel;
    private String originalLabel = null;
    private RemoteShape shapeModelArg;
    private String keyArg;
    public SLRemoveLabelCommand (ShapesList theShapesList,
       String theKey)
    {
        slModel = theShapesList;
        keyArg = theKey;
    }
	public String getKey() {
		return keyArg;
	}
    public boolean isUndoable()
    {
        return(true);
    }
    public void execute()
    {
        originalLabel = slModel.removeLabel(keyArg);
    }
    public void undo()
    {
        if (originalLabel != null)
            slModel.put(keyArg, originalLabel);
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

    public static void removeLabel (Undoer undoer, ShapesList theShapesList,
        String theKey)
    {
        SLRemoveLabelCommand c =  new SLRemoveLabelCommand(theShapesList, theKey);
        undoer.execute(c);
    }


}

