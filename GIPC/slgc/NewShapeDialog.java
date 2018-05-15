package slgc;
import java.awt.Dialog;
import java.awt.TextField;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class NewShapeDialog extends Dialog implements ActionListener
{
    private TextField textField = new TextField();
    public String shapeKey = "shape";
    public NewShapeDialog(Frame frame, String title)
    {
        super(frame, title, true);
        textField.addActionListener(this);
        this.add(textField);
        this.setSize(150,50);
    }
    public void actionPerformed(ActionEvent e)
    {
        shapeKey = e.getActionCommand();
        this.setVisible(false);
    }
}

