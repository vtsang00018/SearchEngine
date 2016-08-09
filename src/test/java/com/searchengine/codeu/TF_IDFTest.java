package com.searchengine.codeu;

import org.jblas.DoubleMatrix;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 8/4/2016.
 */
public class TF_IDFTest {
    @Test
    public void calculate_TFIDF() throws Exception {
        Jedis jedis = JedisMaker.make_local();

        StopWords stop_words = new StopWords();
        JedisIndex index = new JedisIndex(jedis, stop_words);

        JedisUniqueWordIndexer unique_index = new JedisUniqueWordIndexer(jedis);
        TF_IDF tf_idf = new TF_IDF(unique_index, index);

        ArrayList<String> query_words = new ArrayList<String>();
        query_words.add(0, "java");
        query_words.add(1, "programming");

        DoubleMatrix query_vec = tf_idf.get_query_vector(query_words);
        query_vec.print();

        double[] first = {0, 1, 1, 1};
        double[] second = {0, 2, 1, 0};

        DoubleMatrix first_row = new DoubleMatrix(first).transpose();
        DoubleMatrix second_row = new DoubleMatrix(second).transpose();
        DoubleMatrix final_matrix = DoubleMatrix.concatVertically(first_row, second_row);

        DoubleMatrix test_idf = tf_idf.calculate_TFIDF(final_matrix);
        test_idf = tf_idf.normalize_rows(test_idf);

        assert test_idf.rows == 1 && test_idf.columns == 4;

        DoubleMatrix doc_diag_matrix = tf_idf.get_diag_matrix(test_idf);

        assert doc_diag_matrix.rows == 4 && doc_diag_matrix.columns == 4;
        DoubleMatrix product_matrix = tf_idf.matrix_mult(final_matrix, doc_diag_matrix);
        assert product_matrix.rows == 2 && product_matrix.columns == 4;
        DoubleMatrix norm_matrix = tf_idf.normalize_rows(product_matrix);
        System.out.println("COSINE");
        ArrayList<Double> cosine = tf_idf.cosine_similarity(0,norm_matrix);
        for (Double i : cosine){
            System.out.println(i);
        }
        System.out.println("COSINE");

        doc_diag_matrix.print();

        test_idf.print();
    }
}

//    private Map<String, Integer> unique_terms;
//    private Map<String, String> document1;
//    private Map<String, String> document2;
//    private Map<String, String> document3;
//
//    private ArrayList<Map<String, String>> all_docs;
//    private TF_IDF matrix;
//
//    @Before
//    public void setUp() throws Exception {
//        Jedis jedis = JedisMaker.make();
//        JedisUniqueWordIndexer unique_index = new JedisUniqueWordIndexer(jedis);
//        matrix = new TF_IDF();
//        unique_terms = new HashMap<String, Integer>();
//        document1 = new HashMap<String, String>();
//        document2 = new HashMap<String, String>();
////        document3 = new HashMap<String, String>();
//        all_docs = new ArrayList<Map<String, String>>();
//
//        unique_terms.put("java", 0);
//        unique_terms.put("programming", 1);
//        unique_terms.put("hello", 2);
//        unique_terms.put("world", 3);
////        unique_terms.put("cargo", 4);
////        unique_terms.put("metrics", 5);
////        unique_terms.put("m", 6);
////        unique_terms.put("me", 7);
////        unique_terms.put("trics", 8);
////        unique_terms.put("rics", 9);
////        unique_terms.put("ics", 10);
//
//        document1.put("java", "5");
//        document1.put("programming", "5");
//        document1.put("hello", "1");
//        document1.put("world", "1");
//
//        document2.put("java", "10");
//        document2.put("programming", "5");
//        document2.put("hello", "1");
//        document2.put("world", "1");
//
////        document2.put("hello", "11");
////        document2.put("m", "20");
////        document2.put("cargo", "14");
////        document2.put("ics", "8");
////
////        document3.put("metrics", "11");
////        document3.put("java", "20");
////        document3.put("rics", "14");
////        document3.put("ics", "8");
//
//        all_docs.add(0, document1);
//        all_docs.add(1, document2);
////        all_docs.add(2, document3);
//    }
//
//    @Test
//    public void get_IDF_diag() throws Exception {
//        double[] idf_vals = {0.69314718, -0.40546511, -0.40546511, 0.0};
//        DoubleMatrix idf_diag_matrix = matrix.get_IDF_diag(idf_vals);
//        for (int i = 0; i < idf_vals.length; i++)
//            assert idf_diag_matrix.get(i,i) == idf_vals[i];
//    }
//
//    @Test
//    public void matrix_mult() throws Exception {
//        double[] idf_vals = {0.69314718, -0.40546511, -0.40546511, 0.0};
//        ArrayList<String> query_words = new ArrayList<String>();
//        query_words.add(0, "java");
//        query_words.add(1, "programming");
//
//        DoubleMatrix idf_diag_matrix = matrix.get_IDF_diag(idf_vals);
//        DoubleMatrix doc_matric = matrix.get_document_matrix(unique_terms, query_words, all_docs);
//
//        DoubleMatrix mult_idf_matrix = matrix.matrix_mult(doc_matric, idf_diag_matrix);
////        mult_idf_matrix.print();
//        DoubleMatrix normalize = matrix.normalize_rows(mult_idf_matrix);
////        normalize.print();
//    }
//
//    @Test
//    public void get_document_matrix() throws Exception {
//        ArrayList<String> query_words = new ArrayList<String>();
//        query_words.add(0, "java");
//        query_words.add(1, "programming");
//
//        DoubleMatrix test1 = matrix.get_document_matrix(unique_terms, query_words, all_docs);
//        assert test1.rows == 3;
//        assert test1.columns == 4;
//    }
//


//    @Test
//    public void cosine_similarity() throws Exception {
//        ArrayList<String> query_words = new ArrayList<String>();
//        query_words.add(0, "java");
//        query_words.add(1, "programming");
//
//        DoubleMatrix test1 = matrix.get_document_matrix(unique_terms, query_words, all_docs);
//
//        ArrayList<Double> similarity_rows = matrix.cosine_similarity(0, test1);
//        for (Double val: similarity_rows){
//            System.out.println(val);
//        }
//    }
//
//}