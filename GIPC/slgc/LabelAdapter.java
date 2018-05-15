package slgc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import slgc.ButtonAdapter;
import slgv.SLGView;
import shapes.RemoteShape;
import slm.ShapesList;
//import bus.agent.AutoAllConnect;

public class LabelAdapter extends slgc.ButtonAdapter implements ActionListener//, AutoAllConnect
{
	ShapesList slModel;
    public void actionPerformed(ActionEvent e)
    {
        try
        {
			String key = slgController.getSelection();
			slModel = (ShapesList) target;
			if (key == null) return;
			RemoteShape shape = slModel.get(key);
			slgController.newShapeDialog.setLocation(shape.getBounds().getLocation());	
			String label = slModel.getLabel(key);
			if (label != null)				
			   slgController.newShapeDialog.inputString = label;
			slgController.newShapeDialog.setVisible(true);
			label = slgController.newShapeDialog.inputString;
			slModel.put(key,label);
			slgController.repaintView();
            
        }
        catch (ClassCastException cce)
        {
            System.err.println("Expected class: SLModel, Actual Class: " + target.getClass().getName());
            cce.printStackTrace();
        }
        catch (Exception e1) {
        	System.out.println(e1);
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
