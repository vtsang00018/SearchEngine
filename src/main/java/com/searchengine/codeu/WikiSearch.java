package com.searchengine.codeu;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import org.jblas.DoubleMatrix;
import redis.clients.jedis.Jedis;

import javax.xml.soap.Text;


/**
 * Represents the results of a search query.
 *
 */
public class WikiSearch {

    // map from URLs that contain the term(s) to relevance score
    private Map<String, Integer> map;
    private Integer document_freq;
    private Map<String, Double> map_TF_IDF;

    /**
     * Constructor.
     *
     * @param map
     */
    public WikiSearch(Map<String, Integer> map, int document_freq) {
        this.map = map;
        this.document_freq = document_freq;
        this.map_TF_IDF = assign_TF_IDF(map);
    }

    /**
     * Looks up the TD_IDF relevance of a given URL.
     *
     * @param url
     * @return
     */
    public Double calculate_TF_IDF(String url) {
        Integer raw_freq = map.get(url);
        Double relevance = raw_freq * Math.log((raw_freq.doubleValue() / document_freq.doubleValue()));
        return raw_freq == 0 ? 0: relevance;
    }

    public Map<String, Double> assign_TF_IDF(Map<String, Integer> map_TF){
        Map<String, Double> map_TF_IDF = new HashMap<String, Double>();
        for (String url : map_TF.keySet()){
            Double relevance = calculate_TF_IDF(url);
            map_TF_IDF.put(url,relevance);
        }
        return map_TF_IDF;
    }

    /**
     * Prints the contents in order of term frequency.
     */
    private void print() {
        List<Entry<String, Integer>> entries = sort();
        for (Entry<String, Integer> entry: entries) {
            System.out.println(entry);
        }
    }

    private void print_TF_IDF() {
        List<Entry<String, Double>> entries = sort_TF_IDF();
        for (Entry<String, Double> entry: entries) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    /**
     * Computes the union of two search results.
     *
     * @param that
     * @return New WikiSearch object.
     */
//    public WikiSearch or(WikiSearch that) {
//        Map<String, Integer> intersection = new HashMap<String, Integer>();
//        for (String term: map.keySet()) {
//            if (that.map.containsKey(term)) {
//                int relevance = totalRelevance(this.map.get(term), that.map.get(term));
//                intersection.put(term, relevance);
//            }
//        }
//        return new WikiSearch(intersection);
//    }

    /**
     * Computes the intersection of two search results.
     *
     * @param that
     * @return New WikiSearch object.
     */
//    public WikiSearch and(WikiSearch that) {
//        Map<String, Integer> temp_map = new HashMap<String, Integer>();
//        Set<String> keys = that.map.keySet();
//
//        for (String key : keys){
//            if (map.containsKey(key)){
//                Integer value1 = map.get(key);
//                Integer value2 = that.map.get(key);
//                Integer total = totalRelevance(value1, value2);
//                temp_map.put(key, total);
//            }
//        }
//        WikiSearch intersection = new WikiSearch(temp_map);
//        return intersection;
//    }

    /**
     * Computes the intersection of two search results.
     *
     * @param that
     * @return New WikiSearch object.
     */
//    public WikiSearch minus(WikiSearch that) {
//        Map<String, Integer> difference = new HashMap<String, Integer>(map);
//        for (String term: that.map.keySet()) {
//            difference.remove(term);
//        }
//        return new WikiSearch(difference);
//    }

    /**
     * Computes the relevance of a search with multiple terms.
     *
     * @param rel1: relevance score for the first search
     * @param rel2: relevance score for the second search
     * @return
     */
    protected int totalRelevance(Integer rel1, Integer rel2) {
        // simple starting place: relevance is the sum of the term frequencies.
        return rel1 + rel2;
    }

    /**
     * Sort the results by relevance.
     *
     * @return List of entries with URL and relevance.
     */
    public List<Entry<String, Integer>> sort() {
        Comparator<Entry<String, Integer>> comparator = new Comparator<Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> one, Entry<String, Integer> two) {
                if (one.getValue() < two.getValue()) {
                    return -1;
                }
                if (one.getValue() > two.getValue()) {
                    return 1;
                }
                return 0;
            }
        };

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>();
        // Get a set of the entries
        Set set = map.entrySet();
        // Get an iterator
        Iterator i = set.iterator();
        // Display elements
        while(i.hasNext()) {
            Entry<String, Integer> entry = (Entry<String, Integer>)i.next();
            list.add(entry);
        }
        Collections.sort(list, comparator);
        return list;
    }

    public List<Entry<String, Double>> sort_TF_IDF() {
        Comparator<Entry<String, Double>> comparator = new Comparator<Entry<String, Double>>() {
            @Override
            public int compare(Entry<String, Double> one, Entry<String, Double> two) {
                if (one.getValue() < two.getValue()) {
                    return 1;
                }
                if (one.getValue() > two.getValue()) {
                    return -1;
                }
                return 0;
            }
        };

        List<Entry<String, Double>> list = new LinkedList<Entry<String, Double>>();
        // Get a set of the entries
        Set set = map_TF_IDF.entrySet();
        // Get an iterator
        Iterator i = set.iterator();
        // Display elements
        while(i.hasNext()) {
            Entry<String, Double> entry = (Entry<String, Double>)i.next();
            list.add(entry);
        }
        Collections.sort(list, comparator);
        return list;
    }


    /**
     * Performs a search and makes a WikiSearch object.
     *
     * @param term
     * @param index
     * @return
     */
    public static WikiSearch search(String term, JedisIndex index) {
        Map<String, Integer> map = index.getCounts(term);
        int document_freq = index.getDocumentFreq(term);
        return new WikiSearch(map, document_freq);
    }


    public static void main(String[] args) throws IOException {

        // make a JedisIndex
        Jedis jedis = JedisMaker.make_local();

        StopWords stop_words = new StopWords();
        JedisIndex index = new JedisIndex(jedis, stop_words);
        TextParser parser = new TextParser(stop_words);

        JedisUniqueWordIndexer unique_index = new JedisUniqueWordIndexer(jedis);
        Map<String, String> unique_list = unique_index.getAll();

        TF_IDF tf_idf = new TF_IDF(unique_index, index);

        // search for the first term
        String term1 = "java programming";
        ArrayList<String> query = parser.parse_text(term1);

        DoubleMatrix query_vec = tf_idf.get_query_vector(query);



        System.out.println("Query: " + term1);
        WikiSearch search1 = search(term1, index);
        search1.print_TF_IDF();

        // search for the second term
        String term2 = "programming";
        System.out.println("Query: " + term2);
        WikiSearch search2 = search(term2, index);
        search2.print_TF_IDF();

        // compute the intersection of the searches
//        System.out.println("Query: " + term1 + " AND " + term2);
//        WikiSearch intersection = search1.and(search2);
//        intersection.print();
    }
}