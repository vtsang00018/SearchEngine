package com.searchengine.codeu;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aishanibhalla on 8/3/16.
 */
@javax.servlet.annotation.WebServlet(name = "WikiServlet")
public class WikiServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setContentType("text/html");
        String searchTerm = request.getParameter("searchTerm");
        Jedis jedis = JedisMaker.make();
        JedisIndex index = new JedisIndex(jedis);
        PrintWriter out = response.getWriter();
        out.println("<h1>You requested for:"+ searchTerm +"</h1>");
        WikiSearch wSearch = WikiSearch.search(searchTerm, index);
        List<Map.Entry<String, Integer>> entries = wSearch.sort();
        for (Map.Entry<String, Integer> entry: entries) {
            out.println("<a href='"+entry.getKey()+"'>"+entry.getKey()+"</a><br>");
        }

    }
}
