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

        TF_IDF tf_idf = new TF_IDF(unique_index, index);

        // search for the first term
        String term1 = "java programming language";
        ArrayList<String> query = parser.parse_text(term1);

        DoubleMatrix document_matrix = tf_idf.get_document_matrix(query, urls);
        document_matrix.getRow(0).print();
        document_matrix.getRow(1).print();
        document_matrix.getRow(11).print();
        DoubleMatrix tf_idf_matrix = tf_idf.calculate_TFIDF(document_matrix);
        DoubleMatrix tf_idf_diag = tf_idf.get_diag_matrix(tf_idf_matrix);

        DoubleMatrix product_matrix = tf_idf.matrix_mult(document_matrix, tf_idf_diag);
        DoubleMatrix norm_matrix = tf_idf.normalize_rows(product_matrix);
        ArrayList<Double> cosine = tf_idf.cosine_similarity(0, norm_matrix);

        for (int i = 0; i < urls.size(); i++){
            System.out.println(urls.get(i) + ": " + cosine.get(i+1));
        }
    }
}