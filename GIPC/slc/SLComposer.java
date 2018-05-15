package slc;
import java.awt.Container;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Frame;
import java.awt.Panel;import java.awt.ScrollPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import bus.uigen.widgets.swing.DelegateJPanel;

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

//public class SLComposer extends JPanel
public class SLComposer 
{  public final static int FRAME_HEIGHT = 300;
  public final static int FRAME_WIDTH = 300;  Container container;
  public Dimension getPreferredSize() {
    //Dimension d = super.getPreferredSize();
	Dimension d = getContainer().getPreferredSize();	d.width = Math.min(d.width, FRAME_WIDTH);	d.height = Math.min(d.height, FRAME_HEIGHT);
    return d;
  }
   public Container getContainer() {
	   return container;
   }
   public void setContainer(Container theContainer) {
	   container = theContainer;
   }
    static public void main (String args[]) throws Exception
    {		System.out.println("Starting main");
        Frame frame = new Frame();
        SLComposer slComposer = new SLComposer(frame);
        frame.add(slComposer.getContainer());
        //frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        //frame.setLocation(numGViews*50, numGViews*50);
        //frame.add(controlPanel, BorderLayout.WEST);
        //add(slgView);
        frame.setVisible(true);
        System.out.println("Set main frame visible");
    }


    //private GridBagLayout gbLayout;
     int numGViews = 0;
     Undoer undoer;
    //private Undoer undoer = new ToggleUndoer();
     SLModel slModel;// = new SLModel();
     SLUndoProxy slModelUndoProxy;	 Frame myFrame;	 //BusAgent busAgent;
    public SLComposer (Frame frame) throws Exception
    {
		init (frame, new SLModel());		/*
        //System.out.println("SLComposer called");
        //Frame frame = new Frame("GraphicsView");		
		
		System.out.println ("Cmposer called");		myFrame = frame;		
		busAgent = new BusAgentImpl("Drawing", true);	
				System.out.println("model = " + busAgent.register(slModel = new SLModel(), "model"));
		System.out.println("composer = " + busAgent.register(this, "composer"));		System.out.println("frame = " + busAgent.register(frame, "frame"));				
        System.out.println("undoer = " + busAgent.register(undoer = new HistoryUndoer(), "history_undoer"));		//System.out.println("udno = " + busAgent.register(slModelUndoProxy = new SLUndoProxy(undoer, slModel ), "pmodel"));		
        setLayout(new BorderLayout());
        this.createSLGEditor(frame);
        //this.createSLTEditor();
        //int foo = System.in.read();
        //System.out.println("Read Value");		*/
        //setSize(FRAME_WIDTH, FRAME_HEIGHT);				
    }
	boolean showControlPanel = true;	public SLComposer (Frame frame, SLModel newSLModel, boolean initShowControlPanel) throws Exception
    {		showControlPanel = initShowControlPanel;
		init(frame, newSLModel);	}
	public SLComposer (Frame frame, SLModel newSLModel, boolean initShowControlPanel, Container theContainer) throws Exception
    {
		showControlPanel = initShowControlPanel;
		init(frame, newSLModel, theContainer);
	}
	
	public SLComposer() {
		showControlPanel = false;
		undoer = new HistoryUndoer();
		slModelUndoProxy = new SLUndoProxy();
	}
		public SLComposer (Frame frame, SLModel newSLModel) throws Exception
    {
		init(frame, newSLModel);		/*
        //System.out.println("SLComposer called");
        //Frame frame = new Frame("GraphicsView");		
		if (newSLModel == null) return;		
		slModel = newSLModel;		myFrame = frame;		
		setLayout(new BorderLayout());		setSize(FRAME_WIDTH,FRAME_HEIGHT);
		//busAgent = new BusAgentImpl("Drawing", true);		initAgent();		//System.out.println("model = " + busAgent.register(newSLModel, "model"));
		System.out.println("composer = " + busAgent.register(this, "composer"));		System.out.println("frame = " + busAgent.register(frame, "frame"));				
        System.out.println("undoer = " + busAgent.register(undoer = new HistoryUndoer(), "history_undoer"));		//System.out.println("udno = " + busAgent.register(slModelUndoProxy = new SLUndoProxy(undoer, slModel ), "pmodel"));		
        //setLayout(new BorderLayout());
        this.createSLGEditor(frame);
        //this.createSLTEditor();
        //int foo = System.in.read();
        //System.out.println("Read Value");
        //setSize(300, 300);
		*/				
    }
	public void init (Frame frame, SLModel newSLModel) throws Exception {
		init(frame, newSLModel, new JPanel());
	}
	public void init (Frame frame, SLModel newSLModel, Container theContainer) throws Exception {				myFrame = frame;
		setContainer(theContainer);		//this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		getContainer().setLayout(new BorderLayout());		//setSize(FRAME_WIDTH,FRAME_HEIGHT);		
		undoer = new HistoryUndoer();
		slModelUndoProxy = new SLUndoProxy();
		if (newSLModel == null) return;	
		setModel(newSLModel);		/*
		slModel = newSLModel;		connect();		this.createSLGEditor(frame);		//setSize(FRAME_WIDTH, FRAME_HEIGHT);		*/
	}	public void connect() throws Exception {		slModelUndoProxy.setModel(slModel);		slModelUndoProxy.setUndoer(undoer);
		/*		busAgent = new BusAgentImpl("Drawing", true);
		busAgent.register(busAgent);		System.out.println("model = " + busAgent.register(slModel, "model"));
		System.out.println("composer = " + busAgent.register(this, "composer"));		System.out.println("frame = " + busAgent.register(myFrame, "frame"));				
        System.out.println("undoer = " + busAgent.register(undoer = new HistoryUndoer(), "history_undoer"));
		*/
			}	public void setModel(SLModel newSLModel) {
		setModel(newSLModel, null);
	}
	
		public void setModel(SLModel newSLModel, DelegateJPanel viewPanel)  {
		if (newSLModel == null) return;		try {
			if (slModel == null) {
				slModel = newSLModel;
				connect();
				if (viewPanel == null)					createSLGEditor(myFrame);
				else
					createSLGEditor(viewPanel);				//init (myFrame, newSLModel);				/*
				System.out.println("newmodel" + newSLModel);				System.out.println("model = " + busAgent.register(newSLModel, "model"));
				System.out.println("composer = " + busAgent.register(this, "composer"));				System.out.println("frame = " + busAgent.register(myFrame, "frame"));				
				System.out.println("undoer = " + busAgent.register(undoer = new HistoryUndoer(), "history_undoer"));				//System.out.println("udno = " + busAgent.register(slModelUndoProxy = new SLUndoProxy(undoer, slModel ), "pmodel"));				
				//setLayout(new BorderLayout());
				this.createSLGEditor(myFrame);				*/
								
			} else {
				//System.out.println("changing model");				slModel = newSLModel;
				slgController.setModel(slModel);
				slModel.addListener(slgView);
				slgView.setSLModel(slModel);								for (Enumeration elements = slModel.elements();					 elements.hasMoreElements();)					((ShapeModel) elements.nextElement()).addListener(slgView);			}
		} catch (Exception e)
		{ System.out.println("exception ");}	}

    public SLModel getModel () { return slModel;}	public void createSLGEditor  () throws Exception    {
		Frame frame = new Frame("GraphicsView");		createSLGEditor(myFrame);	}	 SLGView slgView;
	 SLGController slgController;	public SLGView getView(){		return slgView;	}	public SLGController getController(){		return slgController;	}
    public void createSLGEditor (Frame frame)throws Exception
    {	    slgView = new SLGView();		slgController = new SLGController();
        connectEditor();
				//slgController.init(true);		
        numGViews++;
        //System.out.println("createSLGView");
        //frame.setSize(300,300);
        //frame.setLocation(numGViews*50, numGViews*50);
        //frame.add(controlPanel, BorderLayout.WEST);
        /*		JScrollPane scrollPane = new JScrollPane();		scrollPane.add(slgView.getContainer());
        //add(slgView, BorderLayout.CENTER);
		add(scrollPane, BorderLayout.CENTER);
		*/
        getContainer().add(slgView.getContainer(), BorderLayout.CENTER);		
		//uiFrame uiframe = uiGenerator.generateUIFrame(busAgent, null);
		//uiframe.setVisible(true);		
        //slgView.setBackground(java.awt.Color.red);
	//frame.setVisible(true);
    }
    public void createSLGEditor (DelegateJPanel panel)throws Exception
    {
	    slgView = new SLGView();
	    slgView.setContainer(panel);
		slgController = new SLGController();
        connectEditor();
		
		//slgController.init(true);
		
        numGViews++;
        
        //getContainer().add(slgView.getContainer(), BorderLayout.CENTER);
		
		
    }	public void connectEditor() throws Exception {
		//busAgent.register (slgView); 
		slModel.addListener(slgView);
		slgView.setSLModel(slModel);		//System.out.println ((SLGController.class).getName());
		//busAgent.registerProxy(slModelUndoProxy,SLGController.class, "Model", "Controller","Model", "Controller", ShapesList.class);
		//busAgent.registerProxy(slModelUndoProxy,SLGController.class, "Model", null,"Model", null, ShapesList.class);
		//busAgent.registerProxy(slModelUndoProxy,Object.class, "Model", null,"Model", null, ShapesList.class);		//busAgent.connect(slModel, "Listener", slgView, "SLModel");		//busAgent.connect("model", "Listener", "view", "SLModel");
        /*		slgController = new SLGController
            (this, slModelUndoProxy,slgView, frame, undoer);
		*/		
        //slgView.setController(slgController);		//slgView = new SLGView ();
		//busAgent.connect(slModel, "Listener");		/*
        slgController = new SLGController
            (this, slModelUndoProxy,slgView, frame, undoer);		*/
		
				slgController.setModel(slModelUndoProxy);		slgController.setFrame(myFrame);		slgController.setView(slgView);		slgController.setComposer(this);
		slgController.setUndoer(undoer);		slgView.getContainer().setSize(FRAME_WIDTH, FRAME_HEIGHT);
		slgController.init(showControlPanel, false);		//System.out.println("Connecting Controller to Model");
		//busAgent.connect(slgController, "Model", slModel, "Controller");
		//busAgent.connect(slgController, "Model", slModel, null);
		//busAgent.connect(slgView, "Controller", slgController, null);
		slgView.setController(slgController);	}

    public void createSLTEditor ()
       throws IOException
    {
        SLTView slTView = new SLTView (slModelUndoProxy);
        slModel.addListener(slTView);
        SLTController slTController =
           new SLTController(this, slModelUndoProxy, undoer, new StreamTokenizer(System.in));
        slTController.processCommands();
    }	


}

