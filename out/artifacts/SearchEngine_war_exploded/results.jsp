<%@ page import="redis.clients.jedis.Jedis" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="org.jsoup.nodes.Element" %>
<%@ page import="com.searchengine.codeu.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.jblas.DoubleMatrix" %>
<%@ page import="java.util.HashMap" %><%--
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

        Jedis jedis = JedisMaker.make_local();
        StopWords stop_words = new StopWords();
        JedisIndex index = new JedisIndex(jedis, stop_words);
        TextParser parser = new TextParser(stop_words);

        ArrayList<String> urls = index.getAllURLs();
        JedisUniqueWordIndexer unique_index = new JedisUniqueWordIndexer(jedis);

        TF_IDF tf_idf = new TF_IDF(unique_index, index);
        //WikiSearch for getting the terms and WikiFetcher for fetching the heading of each URL

        ArrayList<String> query = parser.parse_text(searchTerm);

        DoubleMatrix document_matrix = tf_idf.get_document_matrix(query, urls);
        DoubleMatrix tf_idf_matrix = tf_idf.calculate_TFIDF(document_matrix);
        DoubleMatrix prod = document_matrix.mulRowVector(tf_idf_matrix);

        DoubleMatrix norm_matrix = tf_idf.normalize_rows(prod);
        ArrayList<Double> cosine = tf_idf.cosine_similarity(0, norm_matrix);

        Map<String, Double> urls_relevance = new HashMap<String, Double>();
        for (int i = 0; i < urls.size(); i++){
            urls_relevance.put(urls.get(i), cosine.get(i+1));
        }
        WikiSearch wSearch = new WikiSearch(urls_relevance);

        //WikiSearch wSearch = WikiSearch.search(searchTerm, index);
        WikiFetcher wikiFetcher = new WikiFetcher();

        List<Map.Entry<String, Double>> entries = wSearch.sort_TF_IDF();
    %>
    <div class="page-header"> <h2>You requested for:<strong><%= searchTerm %></strong></h2> </div>
    <div class="searchResult">
        <ul class="search-list">
            <%
                for (Map.Entry<String, Double> entry: entries) {
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
