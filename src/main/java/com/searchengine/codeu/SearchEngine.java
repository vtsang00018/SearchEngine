package com.searchengine.codeu;


import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Vincent on 8/6/2016.
 */
public class SearchEngine {
    public static void main(String[] args) throws IOException {

        Jedis jedis = JedisMaker.make_local();
        StopWords stop_words = new StopWords();
        JedisIndex index = new JedisIndex(jedis, stop_words);
        JedisUniqueWordIndexer unique_words = new JedisUniqueWordIndexer(jedis);
    }
}