package slc;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Frame;
import java.awt.Panel;import java.awt.ScrollPane;import javax.swing.JScrollPane;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.io.IOException;

import util.misc.StreamTokenizer;
import util.undo.CommandHistory;
import util.undo.HistoryUndoer;
import util.undo.ToggleUndoer;
import util.undo.Undoer;
import shapes.LineModel;
import shapes.OvalModel;
//import shapes.SLComposer;
import slc.SLComposer;
import slm.SLModel;
import slm.ShapesList;
import slm.SLUndoProxy;
import slgv.SLGView;
import sltv.SLTView;
import sltv.SLTokens;
import slgc.SLGController;
import sltc.SLTController;import shapes.ShapeModel;

import java.awt.Dimension;
import java.awt.Color;//import bus.agent.*;//import bus.uigen.*;import java.util.Enumeration;

public class SLBusComposer extends SLComposer
{  

//    static public void main (String args[]) throws Exception
//    {//		System.out.println("Starting main");
//        Frame frame = new Frame();
//        SLComposer slComposer = new SLBusComposer(frame);
//        frame.add(slComposer.getContainer());
//        frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
//        //frame.setLocation(numGViews*50, numGViews*50);
//        //frame.add(controlPanel, BorderLayout.WEST);
//        //add(slgView);
//        frame.setVisible(true);
//        System.out.println("Set main frame visible");
//    }
//
//
//    public SLBusComposer (Frame frame) throws Exception
//    {//		super(frame);
//		//		//		
//    }
//	//	public SLBusComposer (Frame frame, SLModel newSLModel) throws Exception
//    {//		super(frame, newSLModel);
//		//		//		
//    }
//	BusAgent busAgent;//	public void connect() throws Exception {//		busAgent = new BusAgentImpl("Drawing", true);
//		busAgent.register(busAgent);
//		/*//		System.out.println("model = " + busAgent.register(slModel, "model"));
//		System.out.println("composer = " + busAgent.register(this, "composer"));//		System.out.println("frame = " + busAgent.register(myFrame, "frame"));				
//        System.out.println("undoer = " + busAgent.register(undoer , "history_undoer"));//		*/
//		System.out.println("model = " + register(slModel, "model"));
//		System.out.println("composer = " + register(this, "composer"));//		System.out.println("frame = " + register(myFrame, "frame"));				
//        System.out.println("undoer = " + busAgent.register(undoer , "history_undoer"));//	}//	public void connectEditor() throws Exception {
//		busAgent.register (slgView); //		//System.out.println ((SLGController.class).getName());
//		//busAgent.registerProxy(slModelUndoProxy,SLGController.class, "Model", "Controller","Model", "Controller", ShapesList.class);
//		//busAgent.registerProxy(slModelUndoProxy,SLGController.class, "Model", null,"Model", null, ShapesList.class);
//		busAgent.registerProxy(slModelUndoProxy,Object.class, "Model", null,"Model", null, ShapesList.class);
//		busAgent.autoAllConnect(slModelUndoProxy);//		busAgent.connect(slModel, "Listener", slgView, "SLModel");//		//busAgent.connect("model", "Listener", "view", "SLModel");
//        /*//		slgController = new SLGController
//            (this, slModelUndoProxy,slgView, frame, undoer);
//		*///		
//        //slgView.setController(slgController);//		//slgView = new SLGView ();
//		//busAgent.connect(slModel, "Listener");//		/*
//        slgController = new SLGController
//            (this, slModelUndoProxy,slgView, frame, undoer);//		*/
//		
//		//		//busAgent.register(slgController);//		register(slgController, "controller");//		//slgController.setUndoer(undoer);
//		slgController.init(true);//		//System.out.println("Connecting Controller to Model");
//		//busAgent.connect(slgController, "Model", slModel, "Controller");
//		busAgent.connect(slgController, "Model", slModel, null);
//		busAgent.connect(slgView, "Controller", slgController, null);//	}//	//	Object register(Object o, String name) throws Exception {//		Object retVal = busAgent.register(o, name);//		busAgent.autoAllConnect(o);
//		return retVal;//	}

   


}

