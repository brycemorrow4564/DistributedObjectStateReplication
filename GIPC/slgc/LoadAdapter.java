package slgc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import slgc.ButtonAdapter;
import slm.SLModel;
import slm.ShapesList;
//import bus.agent.AutoAllConnect;
public class LoadAdapter extends slgc.ButtonAdapter implements ActionListener//, AutoAllConnect
{
	ShapesList slModel;
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            slModel = (ShapesList) target;
            slgController.fileNameDialog.setVisible(true);
            ObjectInputStream fileStream = new ObjectInputStream
               (new FileInputStream (slgController.fileNameDialog.inputString));
            slModel.set((ShapesList) fileStream.readObject());
            fileStream.close();
        }
        catch (ClassCastException cce)
        {
            System.err.println("Expected class: SLModel, Actual Class: " + target.getClass().getName());
        }
        catch (IOException ioe)
        {
            System.err.println("Could not open or read: " + slgController.fileNameDialog.inputString );
        }
        catch (Exception e)
        {
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

