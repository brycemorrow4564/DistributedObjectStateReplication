package slm;
import java.awt.Rectangle;
import java.io.*;
//import util.Observable;
import util.models.AListenable;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import shapes.RemoteShape;
import shapes.RemoteShape;
import shapes.RemoteText;
@util.annotations.StructurePattern(util.annotations.StructurePatternNames.HASHTABLE_PATTERN)
public class SLModel extends AListenable implements ShapesList, java.io.Serializable, Cloneable
    {
		public SLModel () throws RemoteException {
			
		}
		Hashtable labels = new Hashtable();
     	public void clear()
     	{
     	    shapesTable.clear();
     	    //this.setChanged();
     	    //this.notifyObservers();
     	    this.notifyListeners(new SLClearCommand(this));
     	}
		public static String getKey (Hashtable table, Object element) {
			for (Enumeration elements = table.keys(); elements.hasMoreElements();){
				String key = (String) elements.nextElement();
				if (table.get(key) == element)
					return key;
			}
			return null;	
				
		}
		public String getKey(String label) {
			return getKey(labels, label);
		}
		public String getKey(RemoteShape shape) {
			return getKey(shapesTable, shape);
		}
		//public void setController(slgc.SLGController c) {
		public void setController(Object c) {
			//System.out.println("setController called " + c);
		}
     	public RemoteShape get(String key)
     	{
	        return ((RemoteShape) shapesTable.get(key));
	    }
		public String getLabel(String key)
     	{
	        return ((String) labels.get(key));
	    }
		
	    public Enumeration keys()
	    {
	        return (shapesTable.keys());
	    }
	    public Enumeration elements()
	    {
	        //return (shapesTable.elements());
	    	return sortedList.elements();
	    }
		public Enumeration labels()
	    {
	        return (labels.elements());
	    }
		public boolean contains(Object o) {
			return shapesTable.contains(o);
		}
		public boolean containsKey(Object o) {
			return shapesTable.containsKey(o);
		}
	    //public ShapeModel put(String key, ShapeModel value)
		public RemoteShape put(String key, RemoteShape value)
	    {
	        RemoteShape retVal = (RemoteShape) shapesTable.put(key,value);
	        sortedList.add(value);
	        sort();
//	        if (value instanceof RemoteText) {
//	        	System.out.println("Remote Text");	        
//	        	
//	        }
	        //this.setChanged();
     	    //this.notifyObservers(value);
     	    //this.notifyListeners(new SLChange(key,value));
			this.notifyListeners(new SLPutCommand(this, key, value));
	        return (retVal);
	    }
		public String put(String key, String label)
	    {
	        String retVal = (String) labels.put(key,label);
	        //this.setChanged();
     	    //this.notifyObservers(value);
     	    //this.notifyListeners(new SLChange(key,value));
			this.notifyListeners(new SLPutLabelCommand(this, key, label));
	        return (retVal);
	    }
		public void setBounds(String k, Rectangle r) {
			try {
			RemoteShape s = get(k);
			if (s == null) return;
			s.setBounds(r.x, r.y, r.width, r.height);
			this.notifyListeners(new SLSetBoundsCommand(this, k, r));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	    public RemoteShape remove(String key)
	    {
	    	sortedList.remove(get(key));
	        RemoteShape retVal = (RemoteShape) shapesTable.remove(key);
//	        if (retVal instanceof RemoteText)
//	        	System.out.println("Removing text box");
//	        
	        //this.setChanged();
	        //this.notifyObservers();
	        this.notifyListeners(new SLRemoveCommand(this, key));
	        return(retVal);
	    }
		 public String removeLabel(String key)
	    {
	        String retVal = (String) labels.remove(key);
	        //this.setChanged();
	        //this.notifyObservers();
	        this.notifyListeners(new SLRemoveLabelCommand(this, key));
	        return(retVal);
	    }

	    public void set(ShapesList newShapesList)
	    {
	        //this.shapesTable = newShapesList.shapesTable;
	        /*
	        System.out.println("Set Called");
	        */
	        shapesTable.clear();
	        for (Enumeration keys = newShapesList.keys();
                keys.hasMoreElements();)
            {
                String key = (String) keys.nextElement();
                this.put (key, newShapesList.get(key));
            }
           //this.notifyObservers();
           this.notifyListeners(new SLSetCommand(this, newShapesList));
        }
	   Vector sortedList = new Vector();
	    public void sort() {
	    	
	    	Collections.sort(sortedList);
	    }
	   
	     List getSortedList () {
		   return sortedList;
	   }

    public Object clone()
    {
        SLModel clone = null;
        try
        {
            clone = (SLModel) super.clone();
            clone.shapesTable = (Hashtable) this.shapesTable.clone();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return (clone);
        }
    }
     private void writeObject(java.io.ObjectOutputStream out)
        throws java.io.IOException
    {
        out.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream in)
        throws java.io.IOException, ClassNotFoundException
    {
        in.defaultReadObject();
    }


        protected  Hashtable shapesTable = new Hashtable();
}