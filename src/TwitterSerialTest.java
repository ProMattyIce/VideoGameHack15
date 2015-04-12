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

public class TwitterSerialTest {



	private final static String CONSUMER_KEY = "ERD9xr555QX7JDmWgjzyXCEkY";

	private final static String CONSUMER_KEY_SECRET = "CJDNJ4Fq6n4puNV6doecM5g9K0OYbcp12MbKLLcrvsOPnOHnA4";

	String username;

	static BufferedReader inStream = null;
	static OutputStream outStream = null;
	
	public static void main(String[] args) throws Exception {

		/**SERIAL IO PART**/
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
		TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

		StatusListener listener = new StatusListener() {

			public void onStatus(Status status) {
				System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
				byte[] toSend = new byte[1];
				toSend[0] = 1;
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
		String keywords[] = {"@pernix7"};

		fq.track(keywords);

		twitterStream.addListener(listener);
		twitterStream.filter(fq);      


	}
}
