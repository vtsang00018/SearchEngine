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

    }
}
