import java.io.*;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.util.*;
import java.lang.Object.*;

import javax.comm.*;
public class TwitterStreamConsumer {

	private final static String CONSUMER_KEY = "ERD9xr555QX7JDmWgjzyXCEkY";

	private final static String CONSUMER_KEY_SECRET = "CJDNJ4Fq6n4puNV6doecM5g9K0OYbcp12MbKLLcrvsOPnOHnA4";

	String username;

	public static void main(String[] args) throws Exception {
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
