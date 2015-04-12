import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class View extends JFrame {
	private JTextField inputField = new JTextField(5);
	private JTextField metaResultField = new JTextField(5);

	public View() {

		JLabel inputLabel = new JLabel("Game title");
		JLabel metaLabel = new JLabel("MetaMind Review Score");
		inputField = new JTextField(10);
		metaResultField = new JTextField(10);
		metaResultField.setEnabled(false);

		setLayout(new FlowLayout());
		
		/*
		inputLabel.setLocation(100, 60);
		metaLabel.setLocation(200,60);
		inputField.setLocation(150,60);
		metaResultField.setLocation(150,60);
		*/
		add(inputLabel);
		add(inputField);
		add(metaLabel);
		add(metaResultField);
		
		setTitle("Game review");
		setSize(300,200);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		
		
	}
	public static void main(String[]args){
		new View();
	}
	
	/**ADD METAMIND CODE INSIDE OF THIS**/
	private class EventHandler implements ActionListener{
	      public void actionPerformed(ActionEvent e) {
	    	  
	    	  String gameTitle = inputField.getText();
	    	  

	    	  
	    	  metaResultField.setText("WHAT EVER SCORE YOU GET GOES HERE");
	      }
	   }	
}
