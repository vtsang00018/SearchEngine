package com.searchengine.codeu;

import javafx.scene.paint.Stop;
import opennlp.tools.stemmer.PorterStemmer;

import java.util.ArrayList;

/**
 * Created by Vincent on 8/6/2016.
 */
public class TextParser {

    private StopWords stop_words;

    public TextParser(StopWords stop_words){
        this.stop_words = stop_words;
    }

    public ArrayList<String> parse_text(String query) {

        PorterStemmer stemmer = new PorterStemmer();
        String[] query_list = query.toLowerCase().split(" ");
        ArrayList<String> parsed_list = new ArrayList<String>();

        for (String term : query_list){
            if (stop_words.words.containsKey(term)){
                continue;
            }
            String stemmed_term = stemmer.stem(term);
            parsed_list.add(stemmed_term);
        }
        return parsed_list;
    }
}