<%@ page import="redis.clients.jedis.Jedis" %>
<%@ page import="com.searchengine.codeu.JedisMaker" %>
<%@ page import="com.searchengine.codeu.JedisIndex" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.searchengine.codeu.WikiSearch" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: aishanibhalla
  Date: 8/5/16
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WikiSearch</title>
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="resources/css/main.css"/>
    <script type="text/javascript" src="resources/js/angular.min.js"></script>
    <script type="text/javascript" src="resources/js/app.js"></script>
</head>
<body>
    <%
        String searchTerm = request.getParameter("searchTerm");

        Jedis jedis = JedisMaker.make();
        JedisIndex index = new JedisIndex(jedis);

        PrintWriter printWriter = response.getWriter();

        WikiSearch wSearch = WikiSearch.search(searchTerm, index);

        printWriter.println("<h3>You requested for:"+ searchTerm +"</h3>");

        List<Map.Entry<String, Integer>> entries = wSearch.sort();
    %>
    <ul class="search-list">
    <%
        for (Map.Entry<String, Integer> entry: entries) {
    %>
    <li class='search-element thumbnail'>
        <a href='<%entry.getKey();%>'><%entry.getKey();%></a>
    </li>
    <%  }
        printWriter.println("</ul>");
    %>

    </ul>
</body>
</html>
