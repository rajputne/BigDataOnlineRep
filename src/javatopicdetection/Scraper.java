/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javatopicdetection;

import org.jsoup.*;
import org.jsoup.helper.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.io.*; // Only needed if scraping a local File.
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scraper {

    public Scraper() {

        Document doc = null;

        try {
            doc = Jsoup.connect("https://twitter.com/whatyou_sayin/status/208556427084697600").get();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        Elements table = doc.getElementsByClass("TweetTextSize TweetTextSize--26px js-tweet-text tweet-text");
        Elements rows = table.tagName("p");

        for (Element row : rows) {
            Elements tds = row.getElementsByTag("p");
            for (Element r : tds) {
                System.out.println(r.text());
            }
        }
    }

    public static void main(String args[]) {

        BufferedReader br = null;

        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader("C:\\Users\\User\\Desktop\\Big Data\\BigDataProject\\replab2013-dataset\\background\\tweet_info\\RL2013D01E001.dat"));

            while ((sCurrentLine = br.readLine()) != null) {
                //System.out.println(sCurrentLine);
                Pattern p = Pattern.compile("\"([^\"]*)\"");
                Matcher m = p.matcher(sCurrentLine);
                while (m.find()) {

                    if (m.group(1).contains("https://twitter")) {
                        System.out.print(m.group(1));

                        Document doc = null;

                        try {
                            doc = Jsoup.connect(m.group(1)).get();
                        } catch (IOException ioe) {
                            //ioe.printStackTrace();
                        } catch (NullPointerException e) {
                            // TODO Auto-generated catch block
                            //e.printStackTrace();
                        }

                        try {
                            Elements table = doc.getElementsByClass("TweetTextSize TweetTextSize--26px js-tweet-text tweet-text");
                            Elements rows = table.tagName("p");

                            for (Element row : rows) {
                                Elements tds = row.getElementsByTag("p");
                                for (Element r : tds) {
                                    System.out.println(r.text());
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Tweet Deleted");
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        new Scraper();
    }

}
