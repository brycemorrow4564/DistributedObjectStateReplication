package sltc;
import util.misc.StreamTokenizer;
import slm.ShapesList;
import shapes.LineModel;
import java.awt.Rectangle;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class TNewLineController implements sltc.TCommandAdapter
{
    ShapesList shapesList;

    public void init(Object theTarget)
    {
        try
        {
            shapesList = (ShapesList) theTarget;
        }

        catch (ClassCastException cce)
        {
            System.err.println("Specific handler called: " + cce.getMessage());
            cce.printStackTrace();
            System.err.println("Expected class: ShapesList, Actual Class: " + theTarget.getClass().getName());
        }
    }

    public void invoke (StreamTokenizer theStreamTokenizer)
    {
        String key = null;
        int x, y, width, height;
        key = theStreamTokenizer.nextWordOrString();
        x = (int) theStreamTokenizer.nextNumber();
        y = (int)  theStreamTokenizer.nextNumber();
        width = (int) theStreamTokenizer.nextNumber();
        height = (int) theStreamTokenizer.nextNumber();
        try {
        shapesList.put(key, new LineModel(new Rectangle (x,y,width,height)));
        } catch (Exception e) {
        	e.printStackTrace();
        	//System.out.println(e);
        }

    }

}

