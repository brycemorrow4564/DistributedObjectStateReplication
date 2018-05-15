package slm;
import java.util.Enumeration;
import util.undo.CommandHistory;
import util.undo.Undoer;
import shapes.RemoteShape;
import shapes.RemoteShape;
import slm.SLPutCommand;
import slm.SLPutLabelCommand;
import slm.SLRemoveCommand;
import slm.SLRemoveLabelCommand;
import slm.SLSetBoundsCommand;
import slm.SLSetCommand;
//import bus.agent.AutoAllConnect;
import java.awt.Rectangle;
import shapes.RemoteShape;
public class SLUndoProxy  implements ShapesList, java.io.Serializable, Cloneable//, AutoAllConnect
{
    private ShapesList shapesList;
    private Undoer undoer;
    public SLUndoProxy (Undoer theUndoer,
        ShapesList theShapesList)
    {
        shapesList = theShapesList;
        undoer = theUndoer;
    }
	public SLUndoProxy ()
    {
    }
	public void setController(slgc.SLGController c) {
	//public void setController(Object c) {
		System.out.println("Set Controller in Proxy " + c);
	}
	public void setModel(ShapesList theShapesList) {
		shapesList = theShapesList;
		//System.out.println("undo model: " + theShapesList);
	}
	public void setUndoer(Undoer theUndoer) {
		undoer = theUndoer;		
		//System.out.println("undo undoer: " + theUndoer);
	}
    public void clear()
    {
        SLClearCommand.clear(undoer, shapesList);
    }
	
    public RemoteShape get(String key)
    {
        return(shapesList.get(key));
    }
	public String getLabel(String key)
    {
        return(shapesList.getLabel(key));
    }
	public String getKey(String label) {
			return shapesList.getKey(label);
		}
	public String getKey(RemoteShape shape) {
			return shapesList.getKey(shape);
	}
	
    public Enumeration keys()
    {
        return(shapesList.keys());
    }
    public Enumeration elements()
    {
        return(shapesList.elements());
    }
	public Enumeration labels()
    {
        return(shapesList.labels());
    }
	public boolean contains(Object o) {
			return shapesList.contains(o);
		}
	public boolean containsKey(Object o) {
		return shapesList.containsKey(o);
	}
	
    public RemoteShape put(String key, /*ShapeModel*/ RemoteShape value)
    {
    	
        SLPutCommand.put(undoer, shapesList, key, value);
        return(null);
    }
	public String put(String key, String label)
    {
        SLPutLabelCommand.put(undoer, shapesList, key, label);
        return(null);
    }
    public RemoteShape remove(String key)
    {
        SLRemoveCommand.remove(undoer, shapesList, key);
        return(null);
    }
	public String removeLabel(String key)
    {
        SLRemoveLabelCommand.removeLabel(undoer, shapesList, key);
        return(null);
    }
	public void set(ShapesList newShapesList)
	{
	    //shapesList.set(newShapesList);
	    SLSetCommand.set(undoer, shapesList, newShapesList);
	}
	public void setBounds(String key, Rectangle r) {
			SLSetBoundsCommand.setBounds(undoer, shapesList, key, r);
	}
	public Object clone()
	{
	    return (shapesList.clone());
	}
	public void sort() {
		shapesList.sort();
	}

}