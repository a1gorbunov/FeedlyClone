package com.feedlyclone.util;

import com.feedlyclone.dto.FeedMessageDTO;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.jsoup.Jsoup;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * methods for process intermediate information from feeds
 */

public class CommonFeedUtil {

    /**
     * process syndFeed to List of FeedMessageDTO objects
     * @param syndFeed feed data by ROME
     * @return List of FeedMessageDTO
     */
    public static List<FeedMessageDTO> copySyndFeed(SyndFeed syndFeed) {
        List<FeedMessageDTO> result = new ArrayList<>();
        if (syndFeed != null && syndFeed.getEntries() != null && syndFeed.getEntries().size() > 0) {
            for (int i = 0; i < syndFeed.getEntries().size();i++){
                SyndEntry syndEntry = syndFeed.getEntries().get(i);
                FeedMessageDTO feedMessage = new FeedMessageDTO();
                feedMessage.setId(i);
                feedMessage.setAuthor(syndEntry.getAuthor());
                feedMessage.setDescriptionClean(removeHtmlTag(syndEntry.getDescription().getValue()));
                feedMessage.setDescriptionFull(syndEntry.getDescription().getValue());
                feedMessage.setLink(syndEntry.getLink());
                feedMessage.setTitle(syndEntry.getTitle());
                feedMessage.setPublishDate(syndEntry.getPublishedDate());
                if (!CollectionUtils.isEmpty(syndEntry.getEnclosures())) {
                            syndEntry.getEnclosures().stream().filter(syndEnclosure -> syndEnclosure.getType().contains("image"))
                                    .findFirst().ifPresent(syndEnclosure1 -> feedMessage.setImage(syndEnclosure1.getUrl()));
                }
                result.add(feedMessage);
            }
        }

        return result;
    }

    public static String removeHtmlTag(String content){
        return Jsoup.parseBodyFragment(content).body().text();
    }
}
