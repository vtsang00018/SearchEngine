package com.searchengine.codeu;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * Created by Vincent on 8/6/2016.
 */
public class JedisUniqueWordIndexer {

    private Jedis jedis;

    /**
     * Constructor.
     *
     * @param jedis
     */
    public JedisUniqueWordIndexer(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * Returns the Redis key for a given search term.
     *
     * @return Redis key.
     */
    private String uniqueSetKey() { return "UniqueWordSet"; }
    private String termCounterKey() {
        return "UniqueTermCounter";
    }

    public boolean isIndexed(String term) {
        String redisKey = uniqueSetKey();
        return jedis.hexists(redisKey, term);
    }

//    public boolean getIndex(){
//        String index_val = jedis.
//    }


}
