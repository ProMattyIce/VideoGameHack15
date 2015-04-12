import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Main extends JFrame {
    private JTextField inputField = new JTextField(5);
    private JTextField metaResultField = new JTextField(5);
    private JTextField positiveField = new JTextField(5);
    private JTextField negativeField = new JTextField(5);
    private JTextField neutralField = new JTextField(5);

    public Main() {

        JLabel inputLabel = new JLabel("Game title");
        JLabel metaLabel = new JLabel("Twitter");
        JLabel positiveLabel = new JLabel("Positive");
        JLabel negativeLabel = new JLabel("Negative");
        JLabel neutralLabel = new JLabel("Neutral");

        inputField = new JTextField(10);
        metaResultField = new JTextField(10);
        positiveField = new JTextField(10);
        negativeField = new JTextField(10);
        neutralField = new JTextField(10);
        metaResultField.setEnabled(false);
        positiveField.setEnabled(false);
        neutralField.setEnabled(false);
        negativeField.setEnabled(false);

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
        add(positiveLabel);
        add(positiveField);
        add(negativeLabel);
        add(negativeField);
        add(neutralLabel);
        add(neutralField);

        setTitle("Game review");
        setSize(900, 1200);
        //setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);



    }
    public static void main(String[]args){
        new Main();
    }

    /**ADD METAMIND CODE INSIDE OF THIS**/
    private class EventHandler implements ActionListener{
        public void actionPerformed(ActionEvent e) {

            String gameTitle = inputField.getText()+" review";
            ArrayList<String> sites = Navigation.google(gameTitle);
            MetaMindResults m;
            double negative = 0;
            double positive = 0;
            double neutral = 0;

            for(String s : sites){
                m = new MetaMindResults(Navigation.open(s));
                negative+=m.getNegative();
                positive+=m.getPostive();
                neutral+=m.getNeutral();
            }

            double total = negative + positive + neutral;

            negativeField.setText((int)(negative / total)+"");
            positiveField.setText((int)(positive / total)+"");
            neutralField.setText((int)(neutral / total)+"");

            neutral = (int)(neutral / total);
            metaResultField.setText("Ignore me");
        }
    }
}