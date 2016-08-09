package com.searchengine.codeu;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.jsoup.select.Elements;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * Represents a Redis-backed web search index.
 *
 */
public class JedisIndex {

    private Jedis jedis;
    private StopWords stop_words;
    private JedisUniqueWordIndexer unique_words;
    /**
     * Constructor.
     *
     * @param jedis
     */
    public JedisIndex(Jedis jedis, StopWords stop_words) {
        this.unique_words = new JedisUniqueWordIndexer(jedis);
        this.jedis = jedis;
        this.stop_words = stop_words;
    }

    /**
     * Returns the Redis key for a given search term.
     *
     * @return Redis key.
     */
    private String urlSetKey(String term) {
        return "URLSet:" + term;
    }

    /**
     * Returns the Redis key for a URL's TermCounter.
     *
     * @return Redis key.
     */
    private String termCounterKey(String url) {
        return "TermCounter:" + url;
    }

    /**
     * Checks whether we have a TermCounter for a given URL.
     *
     * @param url
     * @return
     */
    public boolean isIndexed(String url) {
        String redisKey = termCounterKey(url);
        return jedis.exists(redisKey);
    }

    /**
     * Looks up a search term and returns a set of URLs.
     *
     * @param term
     * @return Set of URLs.
     */
    public Set<String> getURLs(String term) {
        return jedis.smembers(urlSetKey(term));
    }

    /**
     * Retrives the document frequencies of a term
     * @param term
     * @return
     */
    public int getDocumentFreq(String term) {

        Set<String> termDocuments = jedis.smembers(urlSetKey(term));
        return termDocuments.size();
    }

    /**
     * Looks up a term and returns a map from URL to count.
     *
     * @param term
     * @return Map from URL to count.
     */
    public Map<String, Integer> getCounts(String term) {
        Map<String, Integer> counts = new HashMap<String, Integer>();
        Set<String> urls = getURLs(term);

        for (String url : urls){
            String tc_key = termCounterKey(url);
            String value = jedis.hget(tc_key, term);
            counts.put(url, Integer.parseInt(value));
        }
        return counts;
    }

    /**
     * Returns the number of times the given term appears at the given URL.
     *
     * @param url
     * @param term
     * @return
     */
    public Integer getCount(String url, String term) {
        String key = termCounterKey(url);
        String value = jedis.hget(key, term);
        return Integer.parseInt(value);
    }

    /**
     * Add a page to the index.
     *
     * @param url         URL of the page.
     * @param paragraphs  Collection of elements that should be indexed.
     */
    public void indexPage(String url, Elements paragraphs) {
        TermCounter tc = new TermCounter(url, unique_words, stop_words);
        tc.processElements(paragraphs);
        addURLSet(url, tc);
        addTCSet(url, tc);
    }

    private void addURLSet(String url, TermCounter tc){
        Transaction t = jedis.multi();
        for (String term : tc.keySet()){
            String key = urlSetKey(term);
            t.sadd(key, url);
        }
        t.exec();
    }

    private void addTCSet(String url, TermCounter tc){
        Transaction t = jedis.multi();
        for (String term : tc.keySet()){
            String key = termCounterKey(url);
            Integer value = tc.get(term);
            t.hset(key, term, Integer.toString(value));
        }
        t.exec();
    }

    public Map<String, String> get_url_TC(String url){
        String key = termCounterKey(url);
        return jedis.hgetAll(key);
    }

    /**
     * Prints the contents of the index.
     *
     * Should be used for development and testing, not production.
     */
    public void printIndex() {
        // loop through the search terms
        for (String term: termSet()) {
            System.out.println(term);

            // for each term, print the pages where it appears
            Set<String> urls = getURLs(term);
            for (String url: urls) {
                Integer count = getCount(url, term);
                System.out.println("    " + url + " " + count);
            }
        }
    }

    /**
     * Prints the URLs that are stored in the indexer
     */
    public ArrayList<String> getAllURLs() {
        Set<String> termKeys = termCounterKeys();
        ArrayList<String> urls = new ArrayList<String>();
        for (String key: termKeys) {
            String[] array = key.split(":");
            if (array.length < 2) {
                continue;
            } else {
                urls.add(array[1] + ":" + array[2]);
            }
        }
        return urls;
    }

    /**
     * Prints the number of urls and what they are
     */
    public void printURLs(){
        ArrayList<String> urls = getAllURLs();
        System.out.println(urls.size());
        for (String url: urls){
            System.out.println(url);
        }
    }

    /**
     * Checks whether the number of URLs exceed the passed in value
     */
    public Boolean urls_exceeds_threshold(int threshold){
        int num_of_urls = getAllURLs().size();
        return num_of_urls > threshold ? true : false;
    }

    /**
     * Returns the set of terms that have been indexed.
     *
     * Should be used for development and testing, not production.
     *
     * @return
     */
    public Set<String> termSet() {
        Set<String> keys = urlSetKeys();
        Set<String> terms = new HashSet<String>();
        for (String key: keys) {
            String[] array = key.split(":");
            if (array.length < 2) {
                terms.add("");
            } else {
                terms.add(array[1]);
            }
        }
        return terms;
    }

    /**
     * Returns URLSet keys for the terms that have been indexed.
     *
     * Should be used for development and testing, not production.
     *
     * @return
     */
    public Set<String> urlSetKeys() {
        return jedis.keys("URLSet:*");
    }

    /**
     * Returns TermCounter keys for the URLS that have been indexed.
     *
     * Should be used for development and testing, not production.
     *
     * @return
     */
    public Set<String> termCounterKeys() {
        return jedis.keys("TermCounter:*");
    }

    /**
     * Deletes all URLSet objects from the database.
     *
     * Should be used for development and testing, not production.
     *
     * @return
     */
    public void deleteURLSets() {

        Set<String> keys = urlSetKeys();
        Transaction t = jedis.multi();
        for (String key: keys) {
            t.del(key);
        }
        t.exec();
    }

    /**
     * Deletes all URLSet objects from the database.
     *
     * Should be used for development and testing, not production.
     *
     * @return
     */
    public void deleteTermCounters() {
        Set<String> keys = termCounterKeys();
        Transaction t = jedis.multi();
        for (String key: keys) {
            t.del(key);
        }
        t.exec();
    }

    /**
     * Deletes all keys from the database.
     *
     * Should be used for development and testing, not production.
     *
     * @return
     */
    public void deleteAllKeys() {
        Set<String> keys = jedis.keys("*");
        Transaction t = jedis.multi();
        for (String key: keys) {
            t.del(key);
        }
        t.exec();
    }
    public void clearDB(){
        jedis.flushDB();
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Jedis jedis = JedisMaker.make_local();
//        JedisIndex index = new JedisIndex(jedis);

        //index.deleteTermCounters();
        //index.deleteURLSets();
//        index.clearDB();
        //index.loadIndex(index);

    }

    /**
     * Stores two pages in the index for testing purposes.
     *
     * @return
     * @throws IOException
     */
    private static void loadIndex(JedisIndex index) throws IOException {
        WikiFetcher wf = new WikiFetcher();

        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        Elements paragraphs = wf.fetchWikipedia(url);
        index.indexPage(url, paragraphs);

        url = "https://en.wikipedia.org/wiki/Programming_language";
        paragraphs = wf.fetchWikipedia(url);
        index.indexPage(url, paragraphs);
    }
}