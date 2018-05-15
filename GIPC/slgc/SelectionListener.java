package slgc;
import shapes.RemoteShape;
import shapes.ShapeModel;
public interface SelectionListener
{
    //public void selectionChanged (String key, ShapeModel newObject);
    public void selectionChanged (String key, RemoteShape newObject);
	//public void update (Listenable listenable, Object info, Object info2);
}