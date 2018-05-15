package slgc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import slgc.ButtonAdapter;
import slgv.SLGView;
//import bus.agent.AutoAllConnect;

public class RefreshAdapter extends slgc.ButtonAdapter implements ActionListener//,AutoAllConnect
{
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            SLGView slgView = (SLGView) target;
            slgView.getContainer().repaint();
        }
        catch (ClassCastException cce)
        {
            System.err.println("Expected class: SLGView, Actual Class: " + target.getClass().getName());
            cce.printStackTrace();
        }
    }
	

}
