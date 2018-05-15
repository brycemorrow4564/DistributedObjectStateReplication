package slgc;
import java.awt.Frame;
import java.awt.event.ActionListener;
//import bus.agent.AutoAllConnect;
public abstract class ButtonAdapter implements ActionListener//, AutoAllConnect
    {
        protected Object target;
        SLGController slgController;
        public void init(Object theTarget, SLGController theSLGController)
        {
            target = theTarget;
            slgController = theSLGController;
        }
    }

