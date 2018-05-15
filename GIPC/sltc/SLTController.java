package sltc;
import java.util.Hashtable;
import java.io.IOException;

import util.misc.StreamTokenizer;
import util.undo.Undoer;
import slm.SLModel;
import slm.ShapesList;
import slc.SLComposer;
import sltv.SLTView;
public class SLTController
    {
        private StreamTokenizer streamTokenizer;
        private Hashtable commandTable = new Hashtable();
        public SLTController (SLComposer theSLComposer, ShapesList theSLModel,
            Undoer theUndoer, StreamTokenizer theStreamTokenizer)
        {
            streamTokenizer = theStreamTokenizer;
            createCommands(theSLModel, theSLComposer, theUndoer);
        }
        private void createCommands(ShapesList theSLModel, SLComposer theSLComposer,
            Undoer theUndoer)
        {
            Object command;
            try
            {
                command = createCommand(Class.forName("sltc.TUndoAdapter"), theUndoer);
                commandTable.put("u", command);
                commandTable.put("undo", command);
                command = createCommand(Class.forName("sltc.TRedoAdapter"), theUndoer);
                commandTable.put("r", command);
                commandTable.put("R", command);
                command = createCommand(Class.forName("sltc.TSaveAdapter"), theSLModel);
                commandTable.put("s", command);
                commandTable.put("S", command);
                command = createCommand(Class.forName("sltc.TLoadAdapter"), theSLModel);
                commandTable.put("l", command);
                commandTable.put("L", command);
                command = createCommand(Class.forName("sltc.TNewLineController"), theSLModel);
                commandTable.put("nl", command);
                commandTable.put("NL", command);
            }
            catch (Exception e)
            {
                System.err.println("The perils of extreme parameterization:" + e.toString());
            }
        }


        public Object createCommand(Class adapterClass, Object target)
        {
            TCommandAdapter adapter = null;
            try
            {
                adapter = (TCommandAdapter) adapterClass.newInstance();
                adapter.init(target);
            }
            catch (Exception e)
            {
                System.err.println("Tried to instantiate " + adapterClass.getName());
                e.printStackTrace();
            }
            finally
            {
                return(adapter);
            }

        }

        public void processCommands()
            throws IOException
        {
            String commandName;
            TCommandAdapter command;
            for (;;)
            {
                System.out.println("Transcript Editor: Type h for help");
                commandName = streamTokenizer.nextWord ();
                if ((commandName.compareTo("quit") == 0) ||
                    (commandName.compareTo("q") == 0))
                {
                    System.out.println ("Quitting TranscriptView");
                    break;
                }
                command = (TCommandAdapter) commandTable.get(commandName);
                if (command == null)
                    System.out.println("Illegal Comand: Type h for help");
                else
                    command.invoke(streamTokenizer);
            }

        }

    }

