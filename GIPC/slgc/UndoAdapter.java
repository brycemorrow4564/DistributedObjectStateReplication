package slgc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import slgc.ButtonAdapter;
import slm.SLModel;
import util.undo.Undoer;
//import bus.agent.AutoAllConnect;
public class UndoAdapter extends slgc.ButtonAdapter implements ActionListener//, AutoAllConnect
{

    public void actionPerformed(ActionEvent e)
    {
        //if (!slgController.undoer.undo())
		if (!undoer.undo())
            System.out.println("Cannot undo");
    }
	Undoer undoer;
	public void setUndoer (Undoer theUndoer) {
		//System.out.println("undo: undoer " + theUndoer);
		undoer = theUndoer;
	}
}

