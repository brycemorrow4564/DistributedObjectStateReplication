package slm;
import java.util.Enumeration;

import util.undo.AbstractCommand;
import util.undo.Undoer;
public class SLPutLabelCommand extends util.undo.AbstractCommand
{
    private ShapesList shapesList;
    private String originalString = null;
    private String labelArg;
    private String keyArg;
	
    public SLPutLabelCommand (ShapesList theShapesList,
       String theKey, String theString)
    {
        shapesList = theShapesList;
        keyArg = theKey;
        labelArg = theString;

    }
	public String getString() {
		return labelArg;
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
        originalString = shapesList.put(keyArg, (String) labelArg);
		//System.out.println("labelArg " + labelArg + "original" + originalString);
    }
    public void undo()
    {
		if (originalString == null)
			shapesList.removeLabel(keyArg);
		else
            shapesList.put(keyArg, (String) originalString);
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

    public static void put (Undoer undoer, ShapesList theShapesList,
        String theKey, String theString)
    {
        SLPutLabelCommand c =  new SLPutLabelCommand(theShapesList, theKey, theString);
        undoer.execute(c);
    }


}

