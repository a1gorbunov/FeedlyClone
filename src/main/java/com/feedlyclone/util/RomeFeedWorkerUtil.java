package com.feedlyclone.util;

import com.feedlyclone.domain.entity.FeedMessage;
import com.rometools.rome.feed.synd.SyndFeed;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * provide methods to working with ROME
 */
public class RomeFeedWorkerUtil {

    public static List<FeedMessage> copySyndFeed(SyndFeed syndFeed) {
        List<FeedMessage> result = new ArrayList<>();
        if (syndFeed != null && syndFeed.getEntries() != null && syndFeed.getEntries().size() > 0) {
            syndFeed.getEntries().forEach(syndEntry -> {
                FeedMessage feedMessage = new FeedMessage();
                feedMessage.setAuthor(syndEntry.getAuthor());
                feedMessage.setDescription(syndEntry.getDescription().getValue());
                feedMessage.setLink(syndEntry.getLink());
                feedMessage.setTitle(syndEntry.getTitle());
                feedMessage.setPublishDate(syndEntry.getPublishedDate());
                if (!CollectionUtils.isEmpty(syndEntry.getEnclosures())) {
                    feedMessage.setImage(
                            syndEntry.getEnclosures().stream().filter(syndEnclosure -> syndEnclosure.getType().contains("image"))
                                    .findFirst().get().getUrl());
                }
                result.add(feedMessage);
            });
        }

        return result;
    }
}
