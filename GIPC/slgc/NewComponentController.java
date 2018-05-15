package slgc;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Component;
import shapes.RectangleModel;
import shapes.RemoteShape;
import shapes.ShapeModel;
//import bus.agent.AutoAllConnect;
import shapes.ComponentModel;
import slgv.SLGView;
public class NewComponentController extends slgc.NewShapeController //implements AutoAllConnect
{
	TextField textField;
    protected RemoteShape newShape(int x, int y)
    {
		//System.out.println("new component");
		Class componentClass;
		try {
		   componentClass = Class.forName("java.awt." + textField.getText());
         } catch (Exception e) {
			try {
			    componentClass = Class.forName("javax.swing." + textField.getText());	
			} catch (Exception e2) {			  
				componentClass = TextField.class;
			}
			
		}
		try {
		   Component component = (Component) componentClass.newInstance();		   
		   component.setBounds(x, y, 5, 5);	
		   Rectangle bounds = new Rectangle(x,y, 5, 5);
		   component.setVisible(true);
		   slgView.getContainer().add(component);
		   component.addMouseListener(slgController.getSelectOperandController());
		   component.addMouseMotionListener(slgController.getSelectOperandController());
		   //System.out.println("created" + component);
		   return new ComponentModel(component, bounds);
		} catch (Exception e) {
			return null;
		}
			  
    }
	protected String keyPrefix() {
		return ("c");
	}
	
	public void setTextField (TextField theTextField) {
		//System.out.println("connecting textField" + theTextField);
		textField = theTextField;
	}
}
