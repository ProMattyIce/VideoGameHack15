import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

public class Navigation {
    private String game;


    private Navigation(){

    }

    static public String sanitizeWebsite(Document d){
        String result = "";
        Pattern p = Pattern.compile("/*[a-zA-Z ]");
        Matcher m;
        if (d != null) {
            m = p.matcher(Jsoup.parse(d.text()).text());
            while(m.find()) {
                result += m.group();
            }
        }
        return result;
    }

    static public String open(String url){
        Document doc = null;
        try {
            if(!url.equals("http://www.google.com/sorry/misc/") && !url.contains("youtube")) {
                doc = Jsoup.connect(url).get();
            }  else
                return "";
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sanitizeWebsite(doc);
    }

    static public ArrayList<String> google(String query) {
/*
        String address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
        String query = "programcreek";
        String charset = "UTF-8";

        URL url = new URL(address + URLEncoder.encode(query, charset));
        Reader reader = new InputStreamReader(url.openStream(), charset);
        GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);

        int total = results.getResponseData().getResults().size();
        System.out.println("total: "+total);

        // Show title and URL of each results
        for(int i=0; i<=total-1; i++){
            System.out.println("Title: " + results.getResponseData().getResults().get(i).getTitle());
            System.out.println("URL: " + results.getResponseData().getResults().get(i).getUrl() + "\n");

        }*/

        String address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
        String charset = "UTF-8";
        // Pattern p = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        Pattern p = Pattern.compile("\\(?\\bhttp://[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]");

        Matcher m = null;
        ArrayList<String> results = new ArrayList<String>();

        URL url = null;
        try {
            url = new URL(address + URLEncoder.encode(query, charset));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    url.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str;

        try {
            while ((str = in.readLine()) != null) {
                System.out.println(str);
                m = p.matcher(str);
                while(m.find()) {
                    if(!results.contains(m.group()) && !m.group().contains("google") && !m.group().contains("metacritic")) {
                        if(m.group().contains("youtube")) {
                            if (!results.contains("youtube")) {
                                results.add(m.group());
                            }
                        }else{
                            results.add(m.group());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }

}
