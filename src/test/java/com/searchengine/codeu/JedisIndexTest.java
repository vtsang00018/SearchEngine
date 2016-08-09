package com.searchengine.codeu;

import org.jblas.DoubleMatrix;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
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
        String term1 = "Google";
        ArrayList<String> query = parser.parse_text(term1);

        DoubleMatrix document_matrix = tf_idf.get_document_matrix(query, urls);


//        assert true;
//        System.out.println("TFIDF");
        DoubleMatrix tf_idf_matrix = tf_idf.calculate_TFIDF(document_matrix);
//        for (int i = 0; i < tf_idf_matrix.rows; i++){
//            tf_idf_matrix.getRow(i).print();
//        }
//        System.out.println("MULT");
//        DoubleMatrix mult = document_matrix.mulRowVector(tf_idf_matrix);
//        for (int i = 0; i < mult.rows; i++){
//            mult.getRow(i).print();
//        }

//        DoubleMatrix tf_idf_diag = tf_idf.get_diag_matrix(tf_idf_matrix);

        DoubleMatrix prod = document_matrix.mulRowVector(tf_idf_matrix);


//        DoubleMatrix product_matrix = tf_idf.matrix_mult(document_matrix, tf_idf_diag);
        DoubleMatrix norm_matrix = tf_idf.normalize_rows(prod);
        ArrayList<Double> cosine = tf_idf.cosine_similarity(0, norm_matrix);

        Map<String, Double> urls_relevance = new HashMap<String, Double>();
        for (int i = 0; i < urls.size(); i++){
            urls_relevance.put(urls.get(i), cosine.get(i+1));
        }
        WikiSearch sort = new WikiSearch(urls_relevance);
        sort.print_TF_IDF();
    }
}