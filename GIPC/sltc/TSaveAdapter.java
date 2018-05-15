package sltc;
import util.misc.StreamTokenizer;
import slm.ShapesList;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class TSaveAdapter implements sltc.TCommandAdapter
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
        String fileName = null;
        int status;
        try
        {
            fileName = theStreamTokenizer.nextWordOrString();
            ObjectOutputStream fileStream = new ObjectOutputStream(new FileOutputStream (fileName));
            fileStream.writeObject(shapesList.clone());
            fileStream.close();
        }
        catch (IOException ioe)
        {
            System.err.println("Specific handler called: " + ioe.getMessage());
            ioe.printStackTrace();
            System.err.println("Could not open or write: " + fileName);
        }
        catch (Exception e)
        {
            System.err.println("Generic handler called: " + e.getMessage());
           e.printStackTrace();
        }
    }
}

