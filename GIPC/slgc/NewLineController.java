package slgc;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import shapes.LineModel;
import shapes.RemoteShape;
import shapes.ShapeModel;
//import bus.agent.AutoAllConnect;
public class NewLineController extends  slgc.NewShapeController //implements AutoAllConnect
{
    protected RemoteShape newShape(int x, int y)
    {
    	try {
        return (new LineModel(new Rectangle(x,y,0,0)));
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println(e);
    		return null;
    		
    	}
    }
	protected String keyPrefix() {
		return ("l");
	}
}

