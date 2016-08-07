package com.searchengine.codeu;

import org.jblas.DoubleMatrix;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 7/26/2016.
 */
public class JedisIndexTest {
    @Test
    public void main() throws Exception {
        // make a JedisIndex
        Jedis jedis = JedisMaker.make_local();

        StopWords stop_words = new StopWords();
        JedisIndex index = new JedisIndex(jedis, stop_words);
        TextParser parser = new TextParser(stop_words);

        ArrayList<String> urls = index.getAllURLs();

        JedisUniqueWordIndexer unique_index = new JedisUniqueWordIndexer(jedis);
        Map<String, String> unique_list = unique_index.getAll();

        TF_IDF tf_idf = new TF_IDF(unique_index, index);

        // search for the first term
        String term1 = "java programming";
        ArrayList<String> query = parser.parse_text(term1);

        DoubleMatrix document_matrix = tf_idf.get_document_matrix(query, urls);




    }

}