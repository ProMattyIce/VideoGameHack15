import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Enumeration;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Main extends JFrame {
    private JTextField inputField = new JTextField(5);
  //  private JTextField metaResultField = new JTextField(5);
    private JTextField positiveField = new JTextField(5);
    private JTextField negativeField = new JTextField(5);
    private JTextField neutralField = new JTextField(5);
    private JTextArea platformArea = new JTextArea(2,25);

    //arduino and vars
    private final static String CONSUMER_KEY = "ERD9xr555QX7JDmWgjzyXCEkY";
    private final static String CONSUMER_KEY_SECRET = "CJDNJ4Fq6n4puNV6doecM5g9K0OYbcp12MbKLLcrvsOPnOHnA4";
    private String  username;
    static private BufferedReader inStream = null;
    static private OutputStream outStream = null;
    static private FilterQuery fq = new FilterQuery();
    static private String keywords[] = {"@1q3e5t7u"};
    static private TwitterStream twitterStream;

    public Main() {
        JLabel titleLabel = new JLabel("Game Review");
        JLabel inputLabel = new JLabel("Game title");
        //JLabel metaLabel = new JLabel("Twitter");
        JLabel positiveLabel = new JLabel("Positive");
        JLabel negativeLabel = new JLabel("Negative");
        JLabel neutralLabel = new JLabel("Neutral");
        JLabel platformLabel = new JLabel("Platform");
        JButton enterButton = new JButton("Enter");



        enterButton.addActionListener(new EventHandler());
        inputField = new JTextField(10);
        //metaResultField = new JTextField(10);
        positiveField = new JTextField(10);
        negativeField = new JTextField(10);
        neutralField = new JTextField(10);
        //metaResultField.setEnabled(false);
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
        add(titleLabel);
        add(inputLabel);
        add(inputField);
        //add(metaLabel);
        //add(metaResultField);
        add(positiveLabel);
        add(positiveField);
        add(negativeLabel);
        add(negativeField);
        add(neutralLabel);
        add(neutralField);
        add(platformLabel);
        add(platformArea);
        add(enterButton);
        setTitle("Game review");
        setSize(900, 1200);
        //setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


    }
    public static void main(String[]args){

        //**CODE FOr the arduiono**//
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();
        //the main port
        CommPort thePort = null;
        CommPortIdentifier port = null;
        while (ports.hasMoreElements()) {
            port = (CommPortIdentifier) ports.nextElement();
            String type;
            switch (port.getPortType()) {
                case CommPortIdentifier.PORT_PARALLEL:
                    type = "Parallel";
                    break;
                case CommPortIdentifier.PORT_SERIAL:
                    type = "Serial";
                    break;
                default: /// Shouldn't happen
                    type = "Unknown";
                    break;
            }
            System.out.println(port.getName() + ": " + type);


        }

        //port specifically for mac
        try {
            thePort = port.open("/dev/cu.usbmodem1411", 10);
        } catch (PortInUseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            inStream = new BufferedReader(new InputStreamReader(thePort.getInputStream()));
            outStream = new PrintStream(thePort.getOutputStream(), true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("ERD9xr555QX7JDmWgjzyXCEkY");
        cb.setOAuthConsumerSecret("CJDNJ4Fq6n4puNV6doecM5g9K0OYbcp12MbKLLcrvsOPnOHnA4");
        cb.setOAuthAccessToken("3154611982-lrGrLEdaE84yfn01Uz3j8wLtvqJozPosfsL4HgY");
        cb.setOAuthAccessTokenSecret("RGtdPWwrvN7lFOVsMFy6DoEein7hAzwl1NbbY0LaHs7Gs");
        twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        StatusListener listener = new StatusListener() {

            public void onStatus(Status status) {
                String text = status.getText();
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
                MetaMindResults data = new MetaMindResults(text);
                byte[] toSend = new byte[1];
                if(data.getPostive()>=data.getNegative()){
                    toSend[0]='0';
                }
                else {
                    toSend[0] = '1';
                }
                try {
                    outStream.write(toSend, 0, 1);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }

            @Override
            public void onStallWarning(StallWarning arg0) {
                // TODO Auto-generated method stub

            }
        };

        FilterQuery fq = new FilterQuery();
        keywords[0] = "rrrrr10";
        fq.track(keywords);

        twitterStream.addListener(listener);
        twitterStream.filter(fq);


        new Main();
    }

    /**ADD METAMIND CODE INSIDE OF THIS**/
    private class EventHandler implements ActionListener{
        public void actionPerformed(ActionEvent e) {

            String gameTitle = inputField.getText();
            System.out.println(gameTitle);
            keywords[0] = gameTitle;
            fq.track(keywords);

            twitterStream.filter(fq);

            ArrayList<String> sites = Navigation.google(gameTitle+" review");
            MetaMindResults m;
            double negative = 0;
            double positive = 0;
            double neutral = 0;
            String platforms = "";
            for(String s : Game.getPlatforms(gameTitle)){
                platforms += s + ", ";
            }

            if(platforms.length()>2)
                platforms = platforms.substring(0,platforms.length()-2);

            for(String s : sites){
                m = new MetaMindResults(Navigation.open(s));
                negative+=m.getNegative();
                positive+=m.getPostive();
                neutral+=m.getNeutral();
            }
            platformArea.setText(platforms);
            platformArea.setLineWrap(true);
            double total = sites.size();
            negativeField.setText((int)(negative / total)+"");
            positiveField.setText((int)(positive / total)+"");
            neutralField.setText((int)(neutral / total)+"");
            System.out.println(negative + " \np: " + positive + "\nneutral" + neutral);
            neutral = (int)(neutral / total);
            //metaResultField.setText("Ignore me");
        }
    }
}