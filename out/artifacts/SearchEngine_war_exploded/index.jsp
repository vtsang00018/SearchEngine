<%--
  Created by IntelliJ IDEA.
  User: aishanibhalla
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="wikiSearch">
  <head>
    <title>WikiSearch</title>
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="resources/css/main.css"/>
    <script type="text/javascript" src="resources/js/angular.min.js"></script>
    <script type="text/javascript" src="resources/js/app.js"></script>
  </head>
  <body>
  <div class="MainContainer" ng-controller="searchController">
      <div class="header">
        <h1>{{"Wiki Search"}}</h1>
      </div>
      <div class="content">
        <form class="search form-horizontal" action="WikiServletPath" method="post">
          <input type="text" class="form-control" name="searchTerm" size="70" placeholder="Wikipedia Search" ng-keypress="myFunct($event)">
        </form>
      </div>
    </div>
    <nav class="navbar navbar-default navbar-fixed-bottom">
      <div class="container">
        ...
      </div>
    </nav>
  </body>
</html>
