package com.searchengine.codeu;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import redis.clients.jedis.Jedis;


public class WikiCrawler {
    // keeps track of where we started
    private final String source;

    // the index where the results go
    private JedisIndex index;

    // queue of URLs to be indexed
    private Queue<String> queue = new LinkedList<String>();

    // fetcher used to get pages from Wikipedia
    final static WikiFetcher wf = new WikiFetcher();

    /**
     * Constructor.
     *
     * @param source
     * @param index
     */
    public WikiCrawler(String source, JedisIndex index) {
        this.source = source;
        this.index = index;
        queue.offer(source);
    }

    /**
     * Returns the number of URLs in the queue.
     *
     * @return
     */
    public int queueSize() {
        return queue.size();
    }

    /**
     * Gets a URL from the queue and indexes it.
     *
     * @return Number of pages indexed.
     * @throws IOException
     */
    public String crawl(boolean testing) throws IOException {
        // 20k indexes is around 2.2 gb
        int threshold = 200;
        if (queue.isEmpty() || index.urls_exceeds_threshold(threshold)) {
            return null;
        }
        String url = queue.poll();
        System.out.println("Crawling " + url);

        if (testing==false && index.isIndexed(url)) {
            System.out.println("Already indexed.");
            return url;
        }

        Elements paragraphs;
        if (testing) {
            paragraphs = wf.readWikipedia(url);
        } else {
            paragraphs = wf.fetchWikipedia(url);
        }
        index.indexPage(url, paragraphs);
        queueInternalLinks(paragraphs);
        return url;
    }

    /**
     * Parses paragraphs and adds internal links to the queue.
     *
     * @param paragraphs
     */
    // NOTE: absence of access level modifier means package-level
    void queueInternalLinks(Elements paragraphs) {
        for (Element paragraph: paragraphs) {
            queueInternalLinks(paragraph);
        }
    }

    /**
     * Parses a paragraph and adds internal links to the queue.
     *
     * @param paragraph
     */
    private void queueInternalLinks(Element paragraph) {
        Elements elts = paragraph.select("a[href]");
        for (Element elt: elts) {
            String relURL = elt.attr("href");

            if (relURL.startsWith("/wiki/")) {
                String absURL = "https://en.wikipedia.org" + relURL;
                //System.out.println(absURL);
                queue.offer(absURL);
            }
        }
    }

    private void queue_seed_urls(String[] urls){
        for (String url: urls){
            System.out.println(url);
            queue.offer(url);
        }
    }

    public static void main(String[] args) throws IOException {

        // make a WikiCrawler
        Jedis jedis = JedisMaker.make_local();
        StopWords stop_words = new StopWords();
        JedisIndex index = new JedisIndex(jedis, stop_words);
        String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        WikiCrawler wc = new WikiCrawler(source, index);

        String[] urls_seeds = {
//            "https://en.wikipedia.org/wiki/Institute_for_Global_Maritime_Studies",
//            "https://en.wikipedia.org/wiki/Tufts_University",
//            "https://en.wikipedia.org/wiki/Harvard_University",
//            "https://en.wikipedia.org/wiki/Climate_change",
//            "https://en.wikipedia.org/wiki/Apple",
//            "https://en.wikipedia.org/wiki/Tiger",
//            "https://en.wikipedia.org/wiki/University_at_Buffalo",
//            "https://en.wikipedia.org/wiki/Java_virtual_machine",
//            "https://en.wikipedia.org/wiki/Giraffe"
//              "https://en.wikipedia.org/wiki/Purple",
//                "https://en.wikipedia.org/wiki/Rose",
//                "https://en.wikipedia.org/wiki/Wine",
//                "https://en.wikipedia.org/wiki/Google",
//                "https://en.wikipedia.org/wiki/Pet",
//                 "https://en.wikipedia.org/wiki/Dog",
//                "https://en.wikipedia.org/wiki/Mammal",
//                "https://en.wikipedia.org/wiki/Google_self-driving_car",
//                "https://en.wikipedia.org/wiki/Machine_learning"
//                "https://en.wikipedia.org/wiki/Cat",
//                "https://en.wikipedia.org/wiki/Whiskers",
//                "https://en.wikipedia.org/wiki/Jungle",
//                "https://en.wikipedia.org/wiki/Cartoon",
//                "https://en.wikipedia.org/wiki/Habitat"
                
        };
        wc.queue_seed_urls(urls_seeds);

        // loop until we index a new page
        String res;
        do {
            res = wc.crawl(false);
        } while (res != null);

        Map<String, Integer> map = index.getCounts("the");
        for (Entry<String, Integer> entry: map.entrySet()) {
            System.out.println(entry);
        }
        wc.index.printURLs();
    }
}