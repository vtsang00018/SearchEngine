package com.searchengine.codeu;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Map;
import java.lang.String;
import java.util.Set;

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

    public void addUniqueTerm(String term){
        if (isIndexed(term)) {
            return;
        }
        jedis.hset(uniqueSetKey(), term, getIndex().toString());
        increaseIndex();
    }

    public Map<String, String> getAll(){
        return jedis.hgetAll(uniqueSetKey());
    }

    public Integer getIndex(){
        String index = jedis.get(termCounterKey());
        return Integer.valueOf(index);
    }

    public void increaseIndex(){
        jedis.incr(termCounterKey());
    }

    public Set<String> getUniqueTerms(){
        return jedis.hkeys(uniqueSetKey());
    }

    public void resetUniqueIndexer(){
        Transaction t = jedis.multi();

        t.del(uniqueSetKey());
        t.del(termCounterKey());
        t.getSet(termCounterKey(), String.valueOf(0));
        t.exec();
    }
}