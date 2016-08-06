package com.searchengine.codeu;

import javafx.beans.binding.DoubleExpression;
import org.jblas.DoubleMatrix;
import org.jblas.Geometry;
import org.jblas.Geometry;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by Vincent on 8/3/2016.
 */
public class TF_IDF {

    /**
     * Takes a list of query words and creates a vector
     * @param unique_words dictionary of unique words to their indices in the 1 x n vector
     * @param query_words words to create vector
     * @return vector representation of query
     */
    DoubleMatrix get_query_vector(Map<String, Integer> unique_words, ArrayList<String> query_words){
        int num_features = unique_words.size();
        double[] query_vector = new double[num_features];

        for (String word : query_words){
            Integer index = unique_words.get(word);
            if (index != null){
                query_vector[index] = query_vector[index] + 1;
            }
        }
        return new DoubleMatrix(query_vector);
    }

    /**
     * Takes in a document termcounter and returns the vector representation
     * @param unique_words dictionary of unique words mapping to their indices in the 1 x n vector
     * @param url_tc termcounter mapping from all terms to their freq for the document
     * @return vector version of document
     */
    DoubleMatrix get_document_vector(Map<String, Integer> unique_words, Map<String, String> url_tc){
        int num_features = unique_words.size();
        double[] query_vector = new double[num_features];

        for (String term : url_tc.keySet()){
            Integer index = unique_words.get(term);
            if (index != null){
                query_vector[index] = Double.parseDouble(url_tc.get(term));
            }
        }
        return new DoubleMatrix(query_vector);
    }

    /**
     * Takes in the set of urls and returns matrix representation where each row is a document and each column is a
     * feature (1 unique word)
     * @param unique_words dictionary of unique words mapping to their indices in the 1 x n vector
     * @param urls set of urls and their term counters
     * @return matrix representation of all the documents relevant to the search query
     */
    DoubleMatrix get_document_matrix(Map<String, Integer> unique_words, ArrayList<String> query_words,
                                     ArrayList<Map<String, String>> urls){
        int num_features = unique_words.size();
        int num_rows = urls.size() + 1;
        DoubleMatrix doc_matrix = DoubleMatrix.zeros(num_rows, num_features);

        DoubleMatrix query_vector = get_query_vector(unique_words, query_words);
        doc_matrix.putRow(0, query_vector);

        int row = 1;
        for (Map<String, String> document : urls){
            DoubleMatrix row_vector = get_document_vector(unique_words, document);
            doc_matrix.putRow(row, row_vector);
            row = row + 1;
        }
        return doc_matrix;
    }

    DoubleMatrix matrix_mult(DoubleMatrix m1, DoubleMatrix m2){
        return m1.mmul(m2);
    }

    DoubleMatrix normalize_rows(DoubleMatrix m1){
        Geometry geo = new Geometry();
        return geo.normalizeRows(m1);
    }

    DoubleMatrix get_diag_matrix(DoubleMatrix diag_vals){
        return DoubleMatrix.diag(diag_vals);
    }

    DoubleMatrix get_IDF_diag(double[] idf_vals){
        DoubleMatrix idf_matrix = new DoubleMatrix(idf_vals);
        return get_diag_matrix(idf_matrix);
    }


    ArrayList<Double> cosine_similarity(int row_index, DoubleMatrix m1) {
        double dot_product = 0.0, first_norm = 0.0, second_norm = 0.0;
        double cosin_similarity;
        int num_rows = m1.rows;
        int num_cols = m1.columns;

        ArrayList<Double> similar_rows = new ArrayList<Double>();

        for(int row = 0; row < num_rows; row++){
            for (int column = 0; column < num_cols; column++) {
                dot_product += (m1.get(row_index, column) * m1.get(row, column));
                first_norm += pow(m1.get(row_index, column),2);
                second_norm += pow(m1.get(row, column), 2);
            }
            cosin_similarity = (dot_product / (sqrt(first_norm) * sqrt(second_norm)));
            similar_rows.add(row, cosin_similarity);
        }
        return similar_rows;
    }
}
