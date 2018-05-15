package slgc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import slm.SLModel;
import slm.ShapesList;
//import bus.agent.AutoAllConnect;
public class SaveAdapter extends slgc.ButtonAdapter implements ActionListener//, AutoAllConnect
{
    public void actionPerformed(ActionEvent ae)
    {
        String fileName = null;
        try
        {
            ShapesList slModel = (ShapesList) target;
            slgController.fileNameDialog.setVisible(true);
            fileName = slgController.fileNameDialog.inputString;
            ObjectOutputStream fileStream = new ObjectOutputStream(new FileOutputStream (fileName));
            fileStream.writeObject(slModel.clone());
            fileStream.close();
        }
        catch (ClassCastException cce)
        {
            System.err.println("Specific handler called: " + cce.getMessage());
            cce.printStackTrace();
            System.err.println("Expected class: ShapesList, Actual Class: " + target.getClass().getName());
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
	public void setModel (ShapesList theSLModel) {
		//System.out.println("load: slmodel " + theSLModel);
		target = theSLModel;
	}
	public void setController (SLGController theSLGController) {
		//System.out.println("load: controller " + theSLGController);
		slgController = theSLGController;
	}
}

