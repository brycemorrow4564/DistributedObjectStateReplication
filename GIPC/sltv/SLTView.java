package sltv;
import java.util.Enumeration;
import java.awt.Rectangle;

import util.models.Listenable;
import util.undo.Listener;
import slm.SLModel;
import slm.ShapesList;
import shapes.LineModel;
import shapes.OvalModel;
import shapes.RectangleModel;
import shapes.RemoteShape;
import shapes.ShapeModel;
import slc.SLComposer;

public class SLTView implements Listener
{
    private ShapesList slModel;
    public SLTView (ShapesList theSLModel)
    {
    	
        super();
        try {
        slModel = theSLModel;
        for (Enumeration keys = slModel.keys(); keys.hasMoreElements();)
        {
            RemoteShape shapeModel = slModel.get((String) keys.nextElement());
            shapeModel.addListener(this);
        }
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    }

    public void update(Listenable model, Object arg)
    {
    	try {
        if (arg != null) {
            RemoteShape shapeModel = (RemoteShape) arg;
            shapeModel.addListener(this);
        }
        this.refresh();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    public void refresh()
    {
        System.out.println("Shape List");
        for (Enumeration shapeKeys = slModel.keys();shapeKeys.hasMoreElements();)
        {
            String key = (String) shapeKeys.nextElement();
            RemoteShape shapeModel = slModel.get(key);
            printShapeAndKey(key, shapeModel);
        }
    }
    private static void printShapeAndKey(String key, RemoteShape shapeModel)
    {
    	try {
         Rectangle bounds = shapeModel.getBounds();
         //System.out.print("Key: " + key);
         try
            {
                Class shapeClass = shapeModel.getClass();
                if (shapeClass == Class.forName("shapes.LineModel"))
                {
                    printLine((LineModel) shapeModel);
                }
                else if (shapeClass == Class.forName("shapes.OvalModel"))
                {
                    printOval((OvalModel) shapeModel);
                }
                else if (shapeClass == Class.forName("shapes.RectangleModel"))
                {
                    printRectangle((RectangleModel) shapeModel);
                }
            }
            catch (Exception e)
            {
                System.err.println("Should have stored shape id: " + e.toString());
                e.printStackTrace();
            }
    	} catch (Exception e2) {
    		e2.printStackTrace();
    	}
    }
    static private void printLine(LineModel line)
    {
        System.out.print(" (Line) ");
        printBounds(line);
    }
    static private void printRectangle(RectangleModel rectangle)
    {
        System.out.print(" (Rectangle) ");
        printBounds(rectangle);
    }
    static private void printOval(OvalModel oval)
    {
        System.out.print(" (Oval) ");
        printBounds(oval);
    }
    static private void printBounds(RemoteShape shapeModel)
    {
    	try {
        Rectangle bounds = shapeModel.getBounds();
        System.out.println("Bounds: " + "x:" + bounds.x +
           " y: " + bounds.y + " w: " + bounds.width + " h: " + bounds.height);
    	} catch (Exception e ) {
    		e.printStackTrace();
    		//System.out.println(e);
    	}
    }
}
