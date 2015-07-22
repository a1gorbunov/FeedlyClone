import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.net.URL;

public class TestFeed {

    public static void main(String[] args) {
        boolean ok = false;
            try {
                URL feedUrl = new URL("https://hi-tech.mail.ru/rss/all");
                //URL feedUrl = new URL("http://viralpatel.net/blogs/feed");
                //URL feedUrl = new URL("http://news.tut.by/rss/index.rss");


                SyndFeedInput input = new SyndFeedInput();
                XmlReader reader = new XmlReader(feedUrl);

                SyndFeed feed = input.build(reader);

                System.out.println(feed);

                ok = true;
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("ERROR: "+ex.getMessage());
            }

        if (!ok) {
            System.out.println();
            System.out.println("FeedReader reads and prints any RSS/Atom feed type.");
            System.out.println("The first parameter must be the URL of the feed to read.");
            System.out.println();
        }
    }

}
