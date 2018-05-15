package slgc;import java.util.Vector;
import java.awt.Frame;
import java.awt.Panel;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Button;import java.awt.Component;import java.awt.Color;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.BorderLayout;import java.awt.ScrollPane;
import util.undo.Undoer;import slm.SLModel;
import slm.ShapesList;import shapes.RemoteShape;import shapes.ShapeModel;
import slc.SLComposer;
import slgc.ButtonAdapter;import slgc.NewComponentController;import slgv.SLGView;import java.util.Hashtable;import java.util.Dictionary;import java.util.Enumeration;//import bus.agent.AutoAllConnect;//import bus.agent.BusAgent;//import bus.agent.BusAgentImpl;//import bus.agent.AlwaysExclude;import java.awt.TextField;import java.awt.event.ItemListener;public class SLGController //implements AutoAllConnect//, AlwaysExclude
    {
        //private SLComposer slComposer;
        //private SLModel slModel;
        //private SLGView slGView;
        //private Panel controlPanel;
        private MouseController mouseController;
        public TextPromptDialog newShapeDialog, fileNameDialog;		Panel controlPanel = new Panel();		int numComponents = 14;
		
				
        public SLGController (SLComposer theSLComposer, ShapesList theSLModel,
            SLGView theSLGView, Frame theFrame, Undoer theUndoer)
        {			initialize(theSLComposer, theSLModel, theSLGView, theFrame, theUndoer, true);			/*
            //slComposer = theSLComposer;
            //slModel = theSLModel;
            //slGView = theSLGView;
            undoer = theUndoer;
            //Panel theControlPanel = new Panel();
            //theControlPanel.setLayout(new GridLayout(14,1));
            newShapeDialog = new TextPromptDialog(theFrame, "Enter Shape Label");
            fileNameDialog = new TextPromptDialog(theFrame, "Enter File Name");
	    CreateCommands(controlPanel, theSLModel, theSLGView, theSLComposer);
            CreateModes(controlPanel, theSLModel, theSLGView);
            //theSLGView.setMouseController(mouseController);
			numComponents = components.size();			setControlPanelLayout();
            theSLComposer.add(controlPanel, BorderLayout.WEST);
            */		}		
		boolean initShowControlPanel = true;
				public SLGController (SLComposer theSLComposer, ShapesList theSLModel,
            SLGView theSLGView, Frame theFrame, Undoer theUndoer, boolean initShowControlPanel)
        {
			//showControlPanel = initShowControlPanel;			initialize(theSLComposer, theSLModel, theSLGView, theFrame, theUndoer, initShowControlPanel);			/*
            //slComposer = theSLComposer;
            //slModel = theSLModel;
            //slGView = theSLGView;
            undoer = theUndoer;
            //Panel theControlPanel = new Panel();
            //theControlPanel.setLayout(new GridLayout(14,1));
            newShapeDialog = new TextPromptDialog(theFrame, "Enter Shape Label");
            fileNameDialog = new TextPromptDialog(theFrame, "Enter File Name");
	    CreateCommands(controlPanel, theSLModel, theSLGView, theSLComposer);
            CreateModes(controlPanel, theSLModel, theSLGView);
            //theSLGView.setMouseController(mouseController);
			numComponents = components.size();			setControlPanelLayout();
            theSLComposer.add(controlPanel, BorderLayout.WEST);
            */		}
		
		/*		public SLGController (SLComposer theSLComposer, ShapesList theSLModel,
            SLGView theSLGView, Frame theFrame, Undoer theUndoer)
        {
            setComposer(theSLComposer);			setModel(theSLModel);			setView(theSLGView);			setUndoer(theUndoer);			setFrame(theFrame);			init();
        }
		*/		public SLGController ()
        {
            
        }		public static Vector toVector(Object[] objectArray) {			Vector retVal = new Vector ();			for (int i = 0; i < objectArray.length; i++)				retVal.addElement(objectArray[i]);
			return retVal; 		}		/*
		public Vector getAlwaysExcludeFacets() {			Object[] tempArray = {"Prompt", 								  //"Model",
								"EnabledComponent", 
								 "EnabledComponents", 
								 "VisibleComponent", 
								 "VisibleComponents" ,
								 "Incremental",
								  "NewShapeDialog",								  "fileNameDialog",								  "ControlPanelBackground"
								 												};
			return toVector (tempArray);		}
		*/	
		
				
		SLComposer slComposer;		public void setComposer (SLComposer theSLComposer) {			
			//System.out.println("composer" + theSLComposer);			slComposer = theSLComposer;		}				
		ShapesList slModel;		public void setModel (ShapesList theSLModel) {			
			//System.out.println("set model in controller" + theSLModel);			slModel = theSLModel;		}
				SLGView slgView;					
		public void setView (SLGView theSLGView) {			
			//System.out.println("view" + theSLGView);
			slgView = theSLGView;
		}		
		Undoer undoer;	
				public void setUndoer (Undoer theUndoer) {
			//System.out.println("undoer" + theUndoer);			undoer = theUndoer;		}				
		Frame frame;				public void setFrame (Frame theFrame) {
			//System.out.println ("frame" + theFrame);			frame = theFrame;		}		/*
		BusAgent busAgent;		public void setBusAgent(BusAgent theBusAgent) {
			System.out.println ("busAgent" + theBusAgent);			busAgent = theBusAgent;		}		*/
		ScrollPane spane = new ScrollPane();
		boolean register = false;
		public void init() {			init (false);
		}
		public void init(boolean initShowControlPanel, boolean registerParam) {			if (initShowControlPanel)				init(registerParam);			else				createSelectOperandController();
		}		public void init( boolean  registerParam) {				register = registerParam;
			//System.out.println ("frame" + frame);
			newShapeDialog = new TextPromptDialog(frame, "Enter Shape Label");
            fileNameDialog = new TextPromptDialog(frame, "Enter File Name");						CreateTextField(controlPanel);
			//CreateCommands(controlPanel, slModel, slgView, slComposer);			CreateCommands(controlPanel);
            //CreateModes(controlPanel, slModel, slgView);			CreateModes(controlPanel);
            //theSLGView.setMouseController(mouseController);
			numComponents = components.size();			setControlPanelLayout();			//spane.add(controlPanel);
			//slComposer.add(spane, BorderLayout.WEST);
			//slComposer.getContainer().add
            slComposer.getContainer().add(controlPanel, BorderLayout.WEST);		}		public void initialize(SLComposer theSLComposer, ShapesList theSLModel,
							   SLGView theSLGView, Frame theFrame, Undoer theUndoer, boolean initShowControlPanel) {			slComposer = theSLComposer;
            slModel = theSLModel;
            slgView = theSLGView;
            undoer = theUndoer;			init();
		}
        private void CreateCommands(Panel parentPanel, ShapesList slModel,
            SLGView slgView, SLComposer slComposer)
        {
            try
            {
                //CreateButton(parentPanel, "Refresh", slgView,
                    //Class.forName("slgc.RefreshAdapter"));
                CreateButton(parentPanel, "Undo", slgView,
                    Class.forName("slgc.UndoAdapter"));
                CreateButton(parentPanel, "Redo", slgView,
                    Class.forName("slgc.RedoAdapter"));
                CreateButton(parentPanel, "Clear", slModel,
                    Class.forName("slgc.ClearAdapter"));
                CreateButton(parentPanel, "Load", slModel,
                    Class.forName("slgc.LoadAdapter"));				//CreateButton(parentPanel, "Load",
                    //Class.forName("slgc.LoadAdapter"));
                CreateButton(parentPanel, "Save", slModel,
                    Class.forName("slgc.SaveAdapter"));
                CreateButton(parentPanel, "Keys", slgView,
                    Class.forName("slgc.KeysAdapter"));				
                CreateButton(parentPanel, "Labels", slgView,
                    Class.forName("slgc.LabelsAdapter"));
                CreateButton(parentPanel, "Prompt", this,
                    Class.forName("slgc.PromptAdapter"));				CreateButton(parentPanel, "Immediate", this,
                    Class.forName("slgc.IncrementalAdapter"));				/*
                CreateButton(parentPanel, "NewView", slComposer,
                    Class.forName("slgc.NewViewAdapter"));				*/
            }
            catch (Exception e)
            {
                System.err.println("The perils of extreme parameterization:" + e.toString());
            }
        }
		/*		private void CreateCommands(Panel parentPanel,SLGView slgView, SLComposer slComposer)
        {
            try
            {
                //CreateButton(parentPanel, "Refresh", slgView,
                    //Class.forName("slgc.RefreshAdapter"));
                CreateButton(parentPanel, "Undo", slgView,
                    Class.forName("slgc.UndoAdapter"));
                CreateButton(parentPanel, "Redo", slgView,
                    Class.forName("slgc.RedoAdapter"));
                CreateButton(parentPanel, "Clear", slModel,
                    Class.forName("slgc.ClearAdapter"));
                //CreateButton(parentPanel, "Load", slModel,
                    //Class.forName("slgc.LoadAdapter"));				CreateButton(parentPanel, "Load",
                    Class.forName("slgc.LoadAdapter"));
                CreateButton(parentPanel, "Save", slModel,
                    Class.forName("slgc.SaveAdapter"));
                CreateButton(parentPanel, "Keys", slgView,
                    Class.forName("slgc.KeysAdapter"));
                CreateButton(parentPanel, "Prompt", this,
                    Class.forName("slgc.PromptAdapter"));				CreateButton(parentPanel, "Incremental", this,
                    Class.forName("slgc.IncrementalAdapter"));				
                //CreateButton(parentPanel, "NewView", slComposer,
                    //Class.forName("slgc.NewViewAdapter"));				
            }
            catch (Exception e)
            {
                System.err.println("The perils of extreme parameterization:" + e.toString());
            }
        }
		*/		private void CreateCommands(Panel parentPanel)
        {
			ButtonAdapter button;			
            try
            {
                //CreateButton(parentPanel, "Refresh", slgView,
                    //Class.forName("slgc.RefreshAdapter"));
                button = CreateButton(parentPanel, "Undo",
                    Class.forName("slgc.UndoAdapter"));
				if (!register) {				   button.init(slgView, this);
				   ((UndoAdapter) button).setUndoer(undoer);				}
                button = CreateButton(parentPanel, "Redo", 
                    Class.forName("slgc.RedoAdapter"));				if (!register) {				   button.init(slgView, this);
				   ((RedoAdapter) button).setUndoer(undoer);				}
                button = CreateButton(parentPanel, "Clear", 
                    Class.forName("slgc.ClearAdapter"));								if (!register)				   button.init(slModel, this);				button = CreateButton(parentPanel, "Load",
                    Class.forName("slgc.LoadAdapter"));												if (!register)				   button.init(slModel, this);
                button = CreateButton(parentPanel, "Save", 
                    Class.forName("slgc.SaveAdapter"));																if (!register)				   button.init(slModel, this);
                button = CreateButton(parentPanel, "Keys", 
                    Class.forName("slgc.KeysAdapter"));																				if (!register)				   button.init(slgView, this);
                button = CreateButton(parentPanel, "Labels", 
                    Class.forName("slgc.LabelsAdapter"));																if (!register)				   button.init(slgView, this);				/*
                CreateButton(parentPanel, "Prompt", 
                    Class.forName("slgc.PromptAdapter"));				*/
				button = CreateButton(parentPanel, "Label", 
                    Class.forName("slgc.LabelAdapter"));				if (!register)				   button.init(slModel, this);
				/*				CreateButton(parentPanel, "Immediate", 
                    Class.forName("slgc.IncrementalAdapter"));
				*/				/*
                CreateButton(parentPanel, "NewView", slComposer,
                    Class.forName("slgc.NewViewAdapter"));				*/
            }
            catch (Exception e)
            {
                System.err.println("The perils of extreme parameterization:" + e.toString());
            }
        }
    
        private void CreateModes (Panel parentPanel, ShapesList slModel, SLGView slgView)
        {
            try
            {
                CheckboxGroup modes = new CheckboxGroup();
                CreateCommandMode(modes,"Move", parentPanel, slModel, slgView,
                    Class.forName("slgc.MoveController"));
                CreateCommandMode(modes,"Resize", parentPanel, slModel, slgView,
                    Class.forName("slgc.ResizeController"));
                CreateCommandMode(modes,"Delete", parentPanel, slModel, slgView,
                    Class.forName("slgc.DeleteController"));
                CreateCommandMode(modes,"Line", parentPanel, slModel, slgView,
                    Class.forName("slgc.NewLineController"));
                CreateCommandMode(modes,"Oval", parentPanel, slModel, slgView,
                    Class.forName("slgc.NewOvalController"));
                CreateCommandMode(modes,"Rectangle", parentPanel, slModel, slgView,
                    Class.forName("slgc.NewRectangleController"));				//CreateCommandMode(modes,"ComponentController", parentPanel, slModel, slgView,
                    //Class.forName("slgc.ComponentController"));
            }
            catch (Exception e)
            {
                System.err.println("The perils of extreme parameterization:" + e.toString());
                e.printStackTrace();
            }
        }
			private void CreateModes (Panel parentPanel)
        {
            try
            {
                CheckboxGroup modes = new CheckboxGroup();
                CreateCommandMode(modes,"Select", parentPanel,
                    Class.forName("slgc.SelectOperandController"));				 CreateCommandMode(modes,"Move", parentPanel,
                    Class.forName("slgc.MoveController"));
                CreateCommandMode(modes,"Resize", parentPanel, 
                    Class.forName("slgc.ResizeController"));
                CreateCommandMode(modes,"Delete", parentPanel, 
                    Class.forName("slgc.DeleteController"));
                CreateCommandMode(modes,"Line", parentPanel, 
                    Class.forName("slgc.NewLineController"));
                CreateCommandMode(modes,"Oval", parentPanel, 
                    Class.forName("slgc.NewOvalController"));
                CreateCommandMode(modes,"Rectangle", parentPanel,
                    Class.forName("slgc.NewRectangleController"));				CreateCommandMode(modes,"Component", parentPanel,                      					Class.forName("slgc.NewComponentController"));								incrementalCheckbox = CreateCommandMode(null,"Immediate", parentPanel,
                    Class.forName("slgc.IncrementalController"));
				incrementalCheckbox.setState(incremental);				promptCheckbox = CreateCommandMode(null,"Prompt", parentPanel,
                    Class.forName("slgc.PromptController"));				promptCheckbox.setState(prompt);
            }
            catch (Exception e)
            {
                System.err.println("The perils of extreme parameterization:" + e.toString());
                e.printStackTrace();
            }
        }				Checkbox incrementalCheckbox, promptCheckbox;
		public void repaintView() {			slgView.getContainer().repaint();
		}
		Vector selectionListeners = new Vector();
		public void addSelectionListener(SelectionListener listener) {			selectionListeners.addElement(listener);
		}
		public void removeSelectionListener(SelectionListener listner) {			selectionListeners.addElement(listner);
		}		String selection = null;	    RemoteShape selectedShape = null;		//public void select(String key, ShapeModel shapeModel) {		public void select(String key, RemoteShape shapeModel) {
			selectOperandController.select(key, shapeModel);		}		//public void unselect(String key, ShapeModel shapeModel) {		public void unselect(String key, RemoteShape shapeModel) {
			selectOperandController.unselect(key, shapeModel);		}
		public void shapeSelected(String newVal) {			selection =  newVal;			if (newVal != null) 
			  selectedShape = slModel.get(selection);
		    else
				selectedShape = null;
			notifySelectionListeners();			//slgViewrepaint();
		}		public void notifySelectionListeners() {
			for (Enumeration elements = selectionListeners.elements();
				 elements.hasMoreElements();)
				((SelectionListener) elements.nextElement()).selectionChanged(selection, selectedShape);		}
		public String getSelection() {			return selection;
		}
		public RemoteShape getSelectedShape() {
						return selectedShape;
		}
			    SelectOperandController selectOperandController = null;
		public void setSelectOperandController(SelectOperandController newVal) {			selectOperandController = newVal;
		}
		public SelectOperandController getSelectOperandController() {			return selectOperandController;
		}		
		Dictionary components = new Hashtable();		
        private void CreateButton(Panel parentPanel,String buttonName,
            Object target, Class adapterClass)
        {
            try
            {
                Button button = new Button( buttonName);				components.put(buttonName, button);
                ButtonAdapter adapter = (ButtonAdapter) adapterClass.newInstance();
                adapter.init(target, this);
                button.addActionListener(adapter);
                parentPanel.add(button);
            }
            catch (Exception e)
            {
                System.err.println("Tried to instantiate " + adapterClass.getName());
                e.printStackTrace();
            }

        }		private ButtonAdapter CreateButton(Panel parentPanel,String buttonName, Class adapterClass)
        {
            try
            {				//ButtonAdapter adapter;
                Button button = new Button( buttonName);				components.put(buttonName, button);					ButtonAdapter adapter = (ButtonAdapter) adapterClass.newInstance();					if (register)
                   //busAgent.register (adapter); 					register (adapter); 
                button.addActionListener(adapter);
                parentPanel.add(button);				return adapter;
            }
            catch (Exception e)
            {
                System.err.println("Tried to instantiate " + adapterClass.getName());				System.out.println(e);
                e.printStackTrace();				return null;
            }

        }
		TextField textField;		private TextField CreateTextField(Panel parentPanel)
        {
			try {				textField = new TextField("TextField");				components.put("textField", textField);				
                //busAgent.register (textField);
				if (register)				   register(textField);
                parentPanel.add(textField);
				return textField;			} catch (Exception e) {
				return null;			}
        }

        private Checkbox CreateCommandMode(CheckboxGroup modes, String modeName,
            Panel parentPanel, ShapesList slModel, SLGView slgView,
            Class mouseControllerClass)
        {
            try
            {
                Checkbox checkbox = new Checkbox(modeName, modes, false);
                components.put(modeName, checkbox);				mouseController =
                    (MouseController) mouseControllerClass.newInstance();
				//mouseControllers.put(modeName, mouseController);
                mouseController.init(slModel, slgView, this);
                checkbox.addItemListener(mouseController);
                parentPanel.add(checkbox);				return checkbox;
            }
            catch (Exception e)
            {
                System.err.println("Tried to instantiate " + mouseControllerClass.getName());				return null;
            }
        }				private void createSelectOperandController() {
			SelectOperandController controller = new SelectOperandController();
			controller.init(slModel, slgView, this);
			setSelectOperandController((SelectOperandController) controller);					
			slgView.setMouseController(controller);		}
				private Checkbox CreateCommandMode(CheckboxGroup modes, String modeName,
            Panel parentPanel, Class controllerClass)
        {
            try
            {
                Checkbox checkbox = new Checkbox(modeName, modes, false);
                components.put(modeName, checkbox);
				Object controller;
				if (controllerClass.equals(Class.forName("slgc.SelectOperandController")))
					controller = this.getSelectOperandController();
				else				   controller = controllerClass.newInstance();
				if (controller instanceof MouseController) {					mouseController = (MouseController) controller;					
				} else
					mouseController = null;				if (register)
				    //busAgent.register (controller); 				    register (controller); 
				else if (mouseController != null) {
					mouseController.init(slModel, slgView, this);
					if (mouseController instanceof NewComponentController) 						((NewComponentController) mouseController).setTextField(textField);				} else if (controller instanceof IncrementalController) {					((IncrementalController) controller).setController(this);
				} else if (controller instanceof PromptController) {					((PromptController) controller).setController(this);
				}								if (controller instanceof SelectOperandController) {					setSelectOperandController((SelectOperandController) controller);					
				    //slgView.setMouseController(mouseController);				}
				/*				busAgent.register (mouseController =
                    (MouseController) mouseControllerClass.newInstance());				*/
				//mouseControllers.put(modeName, mouseController);
                //mouseController.init(slModel, slgView, this);
                //checkbox.addItemListener(mouseController);				checkbox.addItemListener((ItemListener) controller);
                parentPanel.add(checkbox);				return checkbox;
            }
            catch (Exception e)
            {
                System.err.println(e + "Tried to instantiate " + controllerClass.getName());				return null;
            }
        }		
				public void setEnabledComponents(boolean isEnabled) {			Enumeration e = components.elements();			while (e.hasMoreElements())				((Component) e.nextElement()).setEnabled(isEnabled);		}		public void setEnabledComponent (String name, boolean isEnabled) {			((Component) components.get(name)).setEnabled(isEnabled);		}
		public void setVisibleComponents(boolean isVisible) {			Enumeration e = components.elements();			while (e.hasMoreElements())				((Component) e.nextElement()).setVisible(isVisible);					}		public void setVisibleComponent (String name, boolean isVisible) {			((Component) components.get(name)).setVisible(isVisible);
					}
		public void removeComponents() {			controlPanel.removeAll();					}		public void removeComponent (String name) {			controlPanel.remove((Component) components.get(name));
			numComponents--;
			setControlPanelLayout();		}		private void setControlPanelLayout() {			
            controlPanel.setLayout(new GridLayout(numComponents,1));
			//controlPanel.setLayout(new GridLayout(1, numComponents));		}
		public void setControlPanelBackground (Color c) {			controlPanel.setBackground(c);
			Enumeration e = components.elements();			while (e.hasMoreElements())				((Component) e.nextElement()).setBackground(c);		}
				private boolean prompt = false;
		public void togglePrompt() {			prompt = !prompt;		}		public boolean getPrompt() {			return prompt;		}
		
		public void setPrompt(boolean newPrompt) {			prompt = newPrompt;
			if (promptCheckbox != null)
				promptCheckbox.setState(prompt);		}
		private boolean incremental = false;
		public void toggleIncremental() {			incremental = !incremental;
			if (incrementalCheckbox != null)
				incrementalCheckbox.setState(incremental);		}		public boolean getIncremental() {			return incremental;		}
		public void setIncremental(boolean newIncremental) {			incremental = newIncremental;		}
		
		Vector uneditableKeys = new Vector();
		public void setUneditable(String key) {			uneditableKeys.addElement(key);
		}
		public boolean isEditable(String key) {			return ! uneditableKeys.contains(key);
		}
		Vector undeletableKeys = new Vector();
		public void setUndeletable(String key) {			undeletableKeys.addElement(key);
		}
		public boolean isDeletable(String key) {			return ! undeletableKeys.contains(key);
		}		
		Object register(Object o) throws Exception {			return null;
			/*		    Object retVal = busAgent.register(o);		    busAgent.autoAllConnect(o);
		    return retVal;
			*/	}
					

    }

