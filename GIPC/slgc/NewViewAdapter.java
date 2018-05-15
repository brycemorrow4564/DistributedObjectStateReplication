package slgc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import slc.SLComposer;
//import bus.agent.AutoAllConnect;

public class NewViewAdapter extends slgc.ButtonAdapter implements ActionListener//, AutoAllConnect
{
    public void actionPerformed(ActionEvent e) 
    {
        try
        {
            SLComposer slComposer = (SLComposer) target;
            slComposer.createSLGEditor();
        }
        catch (ClassCastException cce)
        {
            System.err.println("Expected class: SLComposer, Actual Class: " + target.getClass().getName());
            cce.printStackTrace();
        }
		catch (Exception ex)
        {
            
            ex.printStackTrace();
        }
    }
	
	public void setComposer (SLComposer theSLComposer) {
		//System.out.println("load: composer " + theSLComposer);
		target = theSLComposer;
	}

}
