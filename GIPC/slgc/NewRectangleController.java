package slgc;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import shapes.RectangleModel;
import shapes.RemoteShape;
import shapes.ShapeModel;
//import bus.agent.AutoAllConnect;
public class NewRectangleController extends slgc.NewShapeController //implements AutoAllConnect
{
    protected RemoteShape newShape(int x, int y)
    {
    	try {
        return (new RectangleModel(new Rectangle(x,y,0,0)));
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println(e);
    		return null;
    	}
    }
	protected String keyPrefix() {
		return ("r");
	}
}
