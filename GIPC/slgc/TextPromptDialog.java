package slgc;
import java.awt.Dialog;
import java.awt.TextField;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class TextPromptDialog extends Dialog implements ActionListener
{
    private TextField textField = new TextField();
    public String inputString;
    public TextPromptDialog(Frame frame, String title)
    {
        super(frame, title, true);
        textField.addActionListener(this);
        this.add(textField);
        this.setSize(150,50);
    }
    public void actionPerformed(ActionEvent e)
    {
        inputString = e.getActionCommand();
        this.setVisible(false);
    }
}

