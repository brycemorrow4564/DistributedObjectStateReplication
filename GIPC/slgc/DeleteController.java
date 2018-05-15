package slgc;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Shape;
import shapes.ShapeModel;
import slm.SLRemoveCommand;
//import bus.agent.AutoAllConnect;
class DeleteController extends slgc.ModalSelectionController //implements AutoAllConnect
{
    public void mouseClicked(MouseEvent e)
    {
		
		//System.out.println("delete mouse pressed");
		if (shapeModelWasSelected && selectedKeyIsEditable && slgController.isDeletable(selectedKey)) {
			//System.out.println("selected shape model");
           //SLRemoveCommand.remove(slgController.commandHistory,
            //   slModel, selectedKey);
            slModel.remove(selectedKey);
		}
    }
	public void paintHandle(Graphics g, Shape shapeModel)
    {
		//System.out.println(shapeModel);
		paintDeleteHandle(g, shapeModel);
    }
	public static void paintDeleteHandle(Graphics g, Shape shapeModel)
    {
        Rectangle handle= getHandle(shapeModel.getBounds());
        g.fillRect(handle.x, handle.y, handle.width, handle.height);
        //g.drawString("X", handle.x, handle.y);
    }
}

