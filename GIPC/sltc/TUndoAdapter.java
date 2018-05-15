package sltc;
import util.misc.StreamTokenizer;
import util.undo.Undoer;
public class TUndoAdapter implements sltc.TCommandAdapter
{
    Undoer undoer;

    public void invoke(StreamTokenizer st)
    {
        if (!undoer.undo())
            System.out.println("Cannot undo");
    }
    public void init(Object theTarget)
    {
        undoer = (Undoer) theTarget;
    }

}

