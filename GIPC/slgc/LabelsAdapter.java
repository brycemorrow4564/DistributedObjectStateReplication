package slgc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import slgc.ButtonAdapter;
import slgv.SLGView;
//import bus.agent.AutoAllConnect;

public class LabelsAdapter extends slgc.ButtonAdapter implements ActionListener//, AutoAllConnect
{
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            SLGView slgView = (SLGView) target;
            slgView.toggleLabels();
        }
        catch (ClassCastException cce)
        {
            System.err.println("Expected class: SLGController, Actual Class: " + target.getClass().getName());
            cce.printStackTrace();
        }
    }
	
	public void setView (SLGView theSLGView) {
		//System.out.println("load: view " + theSLGView);
		target = theSLGView;
	}

}
