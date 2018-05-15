package slgc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import slm.ShapesList;
import slm.SLClearCommand;
//import bus.agent.AutoAllConnect;
public class ClearAdapter extends slgc.ButtonAdapter implements ActionListener//,AutoAllConnect
{

      public void actionPerformed(ActionEvent e)
      {
        try
        {
            ShapesList slModel = (ShapesList) target;
            slModel.clear();
            //SLClearCommand.clear(slgController.undoer, slModel);
        }
        catch (ClassCastException cce)
        {
            System.err.println("Expected type: ShapesList, Actual Class: " + target.getClass().getName());
            cce.printStackTrace();
        }
      }
	  
    public void setModel (ShapesList theSLModel) {
		//System.out.println("load: slmodel " + theSLModel);
		target = theSLModel;
	}
	

     public void init(Object theTarget, SLGController theSLGController)
        {
            target = theTarget;
            slgController = theSLGController;
        }


}

