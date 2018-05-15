package sltc;
import util.misc.StreamTokenizer;
import util.undo.Undoer;
public class TRedoAdapter implements sltc.TCommandAdapter
{
    Undoer undoer;

    public void init(Object theTarget)
    {
        undoer = (Undoer) theTarget;
    }

    public void invoke(StreamTokenizer st)
    {
        if (!undoer.redo())
            System.out.println("Cannot redo");
    }

}

