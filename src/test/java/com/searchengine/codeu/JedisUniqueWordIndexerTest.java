package com.searchengine.codeu;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 8/6/2016.
 */
public class JedisUniqueWordIndexerTest {

    private JedisUniqueWordIndexer unique_word_index;
    @Before
    public void setUp() throws Exception {
        Jedis jedis = JedisMaker.make_local();

        unique_word_index = new JedisUniqueWordIndexer(jedis);
        unique_word_index.resetUniqueIndexer();
    }

    @Test
    public void isIndexed() throws Exception {
//        unique_word_index.addUniqueTerm("hello");
//        unique_word_index.addUniqueTerm("bye");
//        unique_word_index.addUniqueTerm("world");
//        unique_word_index.addUniqueTerm("steve");
//        unique_word_index.addUniqueTerm("hkitty");
//        unique_word_index.addUniqueTerm("cat");
//
//        assert unique_word_index.isIndexed("hello");
//        assert unique_word_index.isIndexed("bye");
//        assert unique_word_index.isIndexed("world");
//        assert unique_word_index.isIndexed("steve");
//        assert unique_word_index.isIndexed("hkitty");
//        assert unique_word_index.isIndexed("cat");

    }

    @Test
    public void addUniqueTerm() throws Exception {

    }

    @Test
    public void getIndex() throws Exception {
//        unique_word_index.addUniqueTerm("hello");
//        unique_word_index.addUniqueTerm("bye");
//        unique_word_index.addUniqueTerm("world");
//        unique_word_index.addUniqueTerm("steve");
//        unique_word_index.addUniqueTerm("hkitty");
//        unique_word_index.addUniqueTerm("cat");

        System.out.println(unique_word_index.getIndex());
    }

    @Test
    public void increaseIndex() throws Exception {

    }

    @Test
    public void getUniqueTerms() throws Exception {
//        unique_word_index.addUniqueTerm("hello");
//        unique_word_index.addUniqueTerm("bye");
//        unique_word_index.addUniqueTerm("world");
//        unique_word_index.addUniqueTerm("steve");
//        unique_word_index.addUniqueTerm("hkitty");
//        unique_word_index.addUniqueTerm("cat");
//
//        Set<String> terms = unique_word_index.getUniqueTerms();
//        for (String word: terms){
//            System.out.println(word);
//        }
        Map <String, String> words = unique_word_index.getAll();
        Iterator<Map.Entry<String, String>> iter = words.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    @Test
    public void resetUniqueIndexer() throws Exception {

    }

}