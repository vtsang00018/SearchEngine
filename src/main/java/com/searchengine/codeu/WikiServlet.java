package com.searchengine.codeu;

import redis.clients.jedis.Jedis;

import javax.servlet.*;
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

        RequestDispatcher requestDispatcher;
        requestDispatcher = request.getRequestDispatcher("/results.jsp");
        requestDispatcher.forward(request, response);

//      response.setContentType("text/html");
//        String searchTerm = request.getParameter("searchTerm");

//        Jedis jedis = JedisMaker.make();
//        JedisIndex index = new JedisIndex(jedis);

//        PrintWriter out = response.getWriter();

//        WikiSearch wSearch = WikiSearch.search(searchTerm, index);

//        out.println("<script type='text/javascript' src='https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js'>");
//        out.println("</script>");
//        out.println("<script type='text/javascript'  src='../../../../../../web/resources/js/app.js'>");
//        out.println("</script>");

//        out.println("<h1>You requested for:"+ searchTerm +"</h1>");

//        List<Map.Entry<String, Integer>> entries = wSearch.sort();
//        out.println("<ul class='search-list'>");
//        for (Map.Entry<String, Integer> entry: entries) {
//            out.println("<a href='"+entry.getKey()+"'>"+entry.getKey()+"</a><br>");
//            out.println("<li class='search-element'><a href='"+entry.getKey()+"'>"+entry.getKey()+"</a></li>");
//        }
//        out.println("</ul>");

    }
}
