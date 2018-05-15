package slgc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import slgc.ButtonAdapter;
import slm.SLModel;
import util.undo.Undoer;
//import bus.agent.AutoAllConnect;
public class RedoAdapter extends slgc.ButtonAdapter implements ActionListener//, AutoAllConnect
{

    public void actionPerformed(ActionEvent e)
    {
        //if (!slgController.undoer.redo())
		if (!undoer.redo())
            System.out.println("Cannot redo");
    }
	Undoer undoer;
	public void setUndoer (Undoer theUndoer) {
		//System.out.println("load: undoer " + theUndoer);
		undoer = theUndoer;
	}
}

