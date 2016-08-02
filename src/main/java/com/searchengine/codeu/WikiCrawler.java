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
        int threshold = 20000;
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
        JedisIndex index = new JedisIndex(jedis);
        String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        WikiCrawler wc = new WikiCrawler(source, index);

        System.out.print(wc.index.getURLs().size());
        System.exit(0);
//        String[] urls_seeds = {
//            "https://en.wikipedia.org/wiki/Institute_for_Global_Maritime_Studies",
//            "https://en.wikipedia.org/wiki/Tufts_University",
//            "https://en.wikipedia.org/wiki/Harvard_University",
//            "https://en.wikipedia.org/wiki/Climate_change"
//        };
//        wc.queue_seed_urls(urls_seeds);
//
//        // loop until we index a new page
//        String res;
//        do {
//            res = wc.crawl(false);
//        } while (res != null);

//        Map<String, Integer> map = index.getCounts("the");
//        for (Entry<String, Integer> entry: map.entrySet()) {
//            System.out.println(entry);
//        }
//      wc.index.printURLs();
    }
}
