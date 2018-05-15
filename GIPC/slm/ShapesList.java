package slm;
import java.util.Enumeration;
import java.util.List;
import java.util.Observable;
import java.awt.Rectangle;

import shapes.RemoteShape;
import shapes.ShapeModel;
public interface ShapesList extends Cloneable, java.io.Serializable
{
    public void clear();
    public RemoteShape get(String key);
	public String getKey(String label);
	public String getKey(RemoteShape s);
	public String getLabel(String key);
    public Enumeration keys();
    public Enumeration elements();
	public Enumeration labels();
    //public ShapeModel put(String key, ShapeModel value);
    public RemoteShape put(String key, RemoteShape value);
	public String put(String key, String label);
    public RemoteShape remove(String key);
	public String removeLabel(String key);
	public void set(ShapesList newShapesList);
	public void setBounds(String k, Rectangle r);
	public Object clone();
	public boolean contains(Object o); 
	public boolean containsKey(Object o);
	public void sort();
	//public void writeObject(java.io.ObjectOutputStream out)
	//   throws java.io.IOException;
	//public void readObject(java.io.ObjectInputStream in)
	//   throws java.io.IOException, ClassNotFoundException;
}