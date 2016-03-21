import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class HelloWorldFrame{
    private String message = "Hello World";
    private String fontName = "Lucida Regular";

    public HelloWorldFrame(){
        JFrame frame = new JFrame();
        JLabel label = new JLabel(message);
        label.setFont(new Font(fontName, Font.BOLD, 72));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args){
        new HelloWorldFrame();
    }
}