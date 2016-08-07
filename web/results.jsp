<%@ page import="redis.clients.jedis.Jedis" %>
<%@ page import="com.searchengine.codeu.JedisMaker" %>
<%@ page import="com.searchengine.codeu.JedisIndex" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.searchengine.codeu.WikiSearch" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="com.searchengine.codeu.WikiFetcher" %>
<%@ page import="org.jsoup.nodes.Element" %><%--
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
    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" type="text/css" href="assets/bootstrap/css/bootstrap.min.css">

    <!-- Font Awesome CSS -->
    <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">


    <!-- Animate CSS -->
    <link rel="stylesheet" type="text/css" href="assets/css/animate.css">

    <!-- Owl-Carousel -->
    <link rel="stylesheet" type="text/css" href="assets/css/owl.carousel.css" >
    <link rel="stylesheet" type="text/css" href="assets/css/owl.theme.css" >
    <link rel="stylesheet" type="text/css" href="assets/css/owl.transitions.css" >

    <!-- Materialize CSS -->
    <link rel="stylesheet" type="text/css" href="assets/css/material.css">


    <!-- Custom CSS -->
    <link rel="stylesheet" type="text/css" href="assets/css/style.css">
    <link rel="stylesheet" type="text/css" href="assets/css/responsive.css">




    <!-- Colors CSS -->
    <link rel="stylesheet" type="text/css" href="assets/css/color/blue.css" title="blue">


    <!-- Custom Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>


    <!-- Modernizer js -->
    <script src="assets/js/modernizr.custom.js"></script>
</head>
<body class="search">
    <%
        //Get the search term(s) from the form
        String searchTerm = request.getParameter("searchTerm");

        Jedis jedis = JedisMaker.make();
        JedisIndex index = new JedisIndex(jedis);

        //WikiSearch for getting the terms and WikiFetcher for fetching the heading of each URL
        WikiSearch wSearch = WikiSearch.search(searchTerm, index);
        WikiFetcher wikiFetcher = new WikiFetcher();

        List<Map.Entry<String, Integer>> entries = wSearch.sort();
    %>
    <div class="page-header"> <h2>You requested for:<strong><%= searchTerm %></strong></h2> </div>
    <div class="searchResult">
        <ul class="search-list">
            <%
                for (Map.Entry<String, Integer> entry: entries) {
            %>
            <li class='search-element well text-center col-md-offset-4'>
                <a href="<%= entry.getKey()%>" style="font-size: 18px;"><%=wikiFetcher.getHeading(entry.getKey())%></a><br>
                <%= entry.getKey()%>
            </li>
            <%
                }
            %>
        </ul>
    </div>
</body>
</html>
