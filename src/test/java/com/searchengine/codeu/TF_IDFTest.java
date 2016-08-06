package com.searchengine.codeu;

import org.jblas.DoubleMatrix;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 8/4/2016.
 */
public class TF_IDFTest {

    private Map<String, Integer> unique_terms;
    private Map<String, String> document1;
    private Map<String, String> document2;
    private Map<String, String> document3;

    private ArrayList<Map<String, String>> all_docs;
    private TF_IDF matrix;

    @Before
    public void setUp() throws Exception {
        matrix = new TF_IDF();
        unique_terms = new HashMap<String, Integer>();
        document1 = new HashMap<String, String>();
        document2 = new HashMap<String, String>();
//        document3 = new HashMap<String, String>();
        all_docs = new ArrayList<Map<String, String>>();

        unique_terms.put("java", 0);
        unique_terms.put("programming", 1);
        unique_terms.put("hello", 2);
        unique_terms.put("world", 3);
//        unique_terms.put("cargo", 4);
//        unique_terms.put("metrics", 5);
//        unique_terms.put("m", 6);
//        unique_terms.put("me", 7);
//        unique_terms.put("trics", 8);
//        unique_terms.put("rics", 9);
//        unique_terms.put("ics", 10);

        document1.put("java", "5");
        document1.put("programming", "5");
        document1.put("hello", "1");
        document1.put("world", "1");

        document2.put("java", "10");
        document2.put("programming", "5");
        document2.put("hello", "1");
        document2.put("world", "1");

//        document2.put("hello", "11");
//        document2.put("m", "20");
//        document2.put("cargo", "14");
//        document2.put("ics", "8");
//
//        document3.put("metrics", "11");
//        document3.put("java", "20");
//        document3.put("rics", "14");
//        document3.put("ics", "8");

        all_docs.add(0, document1);
        all_docs.add(1, document2);
//        all_docs.add(2, document3);
    }

    @Test
    public void get_IDF_diag() throws Exception {
        double[] idf_vals = {0.69314718, -0.40546511, -0.40546511, 0.0};
        DoubleMatrix idf_diag_matrix = matrix.get_IDF_diag(idf_vals);
        for (int i = 0; i < idf_vals.length; i++)
            assert idf_diag_matrix.get(i,i) == idf_vals[i];
    }

    @Test
    public void matrix_mult() throws Exception {
        double[] idf_vals = {0.69314718, -0.40546511, -0.40546511, 0.0};
        ArrayList<String> query_words = new ArrayList<String>();
        query_words.add(0, "java");
        query_words.add(1, "programming");

        DoubleMatrix idf_diag_matrix = matrix.get_IDF_diag(idf_vals);
        DoubleMatrix doc_matric = matrix.get_document_matrix(unique_terms, query_words, all_docs);

        DoubleMatrix mult_idf_matrix = matrix.matrix_mult(doc_matric, idf_diag_matrix);
//        mult_idf_matrix.print();
        DoubleMatrix normalize = matrix.normalize_rows(mult_idf_matrix);
//        normalize.print();
    }

    @Test
    public void get_document_matrix() throws Exception {
        ArrayList<String> query_words = new ArrayList<String>();
        query_words.add(0, "java");
        query_words.add(1, "programming");

        DoubleMatrix test1 = matrix.get_document_matrix(unique_terms, query_words, all_docs);
        assert test1.rows == 3;
        assert test1.columns == 4;
    }

    @Test
    public void get_query_vector() throws Exception {
        ArrayList<String> query_words = new ArrayList<String>();

        query_words.add(0, "java");

        DoubleMatrix query_vector = matrix.get_query_vector(unique_terms, query_words);

        assert query_vector.get(0) == (double)1;
    }

    @Test
    public void cosine_similarity() throws Exception {
        ArrayList<String> query_words = new ArrayList<String>();
        query_words.add(0, "java");
//        query_words.add(1, "programming");

        DoubleMatrix test1 = matrix.get_document_matrix(unique_terms, query_words, all_docs);

        ArrayList<Double> similarity_rows = matrix.cosine_similarity(0, test1);
        for (Double val: similarity_rows){
            System.out.println(val);
        }
    }

}