package slgc;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Frame;
import java.util.Enumeration;
import java.util.Hashtable;

import shapes.RemoteShape;
import shapes.ShapeModel;
import slm.ShapesList;
import slgv.SLGView;

public abstract class MouseController extends MouseAdapter
    implements ItemListener, MouseMotionListener
    {
        protected ShapesList slModel;
        protected SLGView slgView;
        protected SLGController slgController;

        public void init (ShapesList theSLModel, SLGView theSLGView, SLGController theSLGController)
        {
            slModel = theSLModel;
            slgView = theSLGView;
            slgController = theSLGController;
        }
		public void setModel(ShapesList theSLModel) {
			slModel = theSLModel;
		}
		public void setView(SLGView theSLGView) {
			slgView = theSLGView;
		}
		public void setController(SLGController theSLGController) {
			slgController = theSLGController;
		}
        public void itemStateChanged(ItemEvent e)
        {
			//System.out.println("slgView" + slgView + "this" + this);
            slgView.setMouseController(this);
        }
        public boolean notBuffered (RemoteShape theShapeModel)
        {
            return (true);
        }
        public void paint(Graphics g)
        {
        }
		public void mouseDragged(MouseEvent e)
        {
        }

		public void mouseMoved(MouseEvent e)
		{
		}
		
   }

