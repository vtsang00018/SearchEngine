package com.searchengine.codeu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Vincent on 8/6/2016.
 */
public class StopWords {
    Map<String, Integer> words;

    public StopWords(){
        this.words = get_stop_words();
    }
    public Map<String, Integer> get_stop_words(){

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("stopwords.txt").getFile());
        Map<String, Integer> stop_words = new HashMap<String, Integer>();

        try (BufferedReader bf = new BufferedReader(new FileReader(file.getPath()))){
            String word;
            while ((word = bf.readLine()) != null) {
                stop_words.put(word, 1);
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stop_words;
    }
}