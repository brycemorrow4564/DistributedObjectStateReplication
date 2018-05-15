package slgc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import slgc.ButtonAdapter;
import slgv.SLGView;
//import bus.agent.AutoAllConnect;

public class IncrementalAdapter extends slgc.ButtonAdapter implements ActionListener//, AutoAllConnect
{
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            SLGController slgController = (SLGController) target;
            slgController.toggleIncremental();
        }
        catch (ClassCastException cce)
        {
            System.err.println("Expected class: SLGController, Actual Class: " + target.getClass().getName());
            cce.printStackTrace();
        }
    }
	
	public void setController (SLGController theSLGController) {
		//System.out.println("load: controller " + theSLGController);
		target = theSLGController;
	}

}
