package slgc;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import slgv.SLGView;
//import bus.agent.AutoAllConnect;

public class IncrementalController implements  ItemListener//, AutoAllConnect
{
	SLGController target;
	/*
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
	*/
	public void itemStateChanged(ItemEvent e)
    {
        try
        {
			System.out.println(e.getStateChange());
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
