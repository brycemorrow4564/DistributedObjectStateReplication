package slc;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Frame;
import java.awt.Panel;
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
import shapes.OvalModel;
import slm.SLModel;
import slm.ShapesList;
import slm.SLUndoProxy;
import slgv.SLGView;
import sltv.SLTView;
import sltv.SLTokens;
import slgc.SLGController;
import sltc.SLTController;

import java.awt.Dimension;
import java.awt.Color;//import bus.agent.*;import bus.uigen.*;

public class OldSLComposer extends Panel
{
//  public Dimension getPreferredSize() {
//    Dimension d = super.getPreferredSize();
//    if (d.width < 300)
//      d.width = 300;
//    return d;
//  }
//
//    static public void main (String args[]) throws Exception
//    {//		System.out.println("Staring main");
//        Frame frame = new Frame();
//        OldSLComposer slComposer = new OldSLComposer(frame);
//        frame.add(slComposer);
//        frame.setSize(300,300);
//        //frame.setLocation(numGViews*50, numGViews*50);
//        //frame.add(controlPanel, BorderLayout.WEST);
//        //add(slgView);
//        frame.setVisible(true);
//    }
//
//
//    //private GridBagLayout gbLayout;
//    private int numGViews = 0;
//    private Undoer undoer = new HistoryUndoer();
//    //private Undoer undoer = new ToggleUndoer();
//    private SLModel slModel = new SLModel();
//    private ShapesList slModelUndoProxy =
//        new SLUndoProxy(undoer, slModel );//	private Frame myFrame;//	private BusAgent busAgent;
//    public OldSLComposer (Frame frame) throws Exception
//    {
//        //System.out.println("SLComposer called");
//        //Frame frame = new Frame("GraphicsView");//		
//		
//		System.out.println ("Cmposer called");//		myFrame = frame;		
//		busAgent = new BusAgentImpl();	
//		//		System.out.println("model = " + busAgent.register(slModel = new SLModel(), "model"));
//		System.out.println("composer = " + busAgent.register(this, "composer"));//		System.out.println("frame = " + busAgent.register(frame, "frame"));//				
//        System.out.println("undoer = " + busAgent.register(undoer = new HistoryUndoer(), "history_undoer"));//		//System.out.println("udno = " + busAgent.register(slModelUndoProxy = new SLUndoProxy(undoer, slModel ), "pmodel"));//		
//        setLayout(new BorderLayout());
//        this.createSLGEditor(frame);
//        //this.createSLTEditor();
//        //int foo = System.in.read();
//        //System.out.println("Read Value");
//        setSize(300, 300);//		//		
//    }
//
//    public SLModel getModel () { return slModel;}//	public void createSLGEditor  () throws Exception    {
//		Frame frame = new Frame("GraphicsView");//		createSLGEditor(myFrame);//	}//	private SLGView slgView;
//	private SLGController slgController;//	public SLGView getView(){//		return slgView;//	}//	public SLGController getController(){//		return slgController;//	}
//    public void createSLGEditor (Frame frame)throws Exception
//    {
//        ////Frame frame = new Frame("GraphicsView");
//        ////Panel controlPanel = new Panel();//		/*
//        slgView = new SLGView (slModelUndoProxy);		
//        slModel.addListener(slgView);//		*/
//		busAgent.register (slgView = new SLGView(), "view");
//		System.out.println("proxy " + slModelUndoProxy);//		System.out.println ((SLGController.class).getName());
//		//busAgent.registerProxy(slModelUndoProxy,SLGController.class, "Model", "Controller","Model", "Controller", ShapesList.class);
//		//busAgent.registerProxy(slModelUndoProxy,SLGController.class, "Model", null,"Model", null, ShapesList.class);
//		busAgent.registerProxy(slModelUndoProxy,Object.class, "Model", null,"Model", null, ShapesList.class);//		busAgent.connect(slModel, "Listener", slgView, "SLModel");//		//busAgent.connect("model", "Listener", "view", "SLModel");
//        /*//		slgController = new SLGController
//            (this, slModelUndoProxy,slgView, frame, undoer);
//		*///		
//        //slgView.setController(slgController);//		//slgView = new SLGView ();
//		//busAgent.connect(slModel, "Listener");//		/*
//        slgController = new SLGController
//            (this, slModelUndoProxy,slgView, frame, undoer);//		*/
//		
//		busAgent.register(busAgent);
//		System.out.println("Registering Controller");//		busAgent.register(slgController = new SLGController(), "controller");//		System.out.println("Connecting Controller to Model");
//		//busAgent.connect(slgController, "Model", slModel, "Controller");
//		busAgent.connect(slgController, "Model", slModel, null);//		System.out.println("Connected Controller to Model " + busAgent.getConnectedObjects());//		
//		//slgController = new SLGController();
//		//		//busAgent.connect(slgController, "Composer", this, null);//		//busAgent.connect(slgController, "Model", slModelUndoProxy, null);		//		//busAgent.connect(slgController, "View", slgView, null);		//		//busAgent.connect(slgController, "Frame", frame, null);		//		//busAgent.connect(slgController, "Undoer", undoer, null);
//		//busAgent.connect(slgController, "BusAgent", busAgent, null);
//		//		slgController.init();//		
//        numGViews++;
//        //System.out.println("createSLGView");
//        //frame.setSize(300,300);
//        //frame.setLocation(numGViews*50, numGViews*50);
//        //frame.add(controlPanel, BorderLayout.WEST);
//        add(slgView.getContainer(), BorderLayout.CENTER);//		/*
//		uiFrame uiframe = uiGenerator.generateUIFrame(busAgent, null);//		uiframe.setVisible(true);//		*/
//        //slgView.setBackground(java.awt.Color.red);
//	//frame.setVisible(true);
//    }
//
//    public void createSLTEditor ()
//       throws IOException
//    {
//        SLTView slTView = new SLTView (slModelUndoProxy);
//        slModel.addListener(slTView);
//        /*
//        SLTController slTController =
//           new SLTController((SLComposer) this, slModelUndoProxy, undoer, new StreamTokenizer(System.in));
//        slTController.processCommands();
//        */
//    }


}

