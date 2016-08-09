<%--
  Created by IntelliJ IDEA.
  User: aishanibhalla
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

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
  <body class="index">
  <!-- Start Off=Canvas Navigation Section -->
  <div class="menu-wrap">
    <nav class="menu">
      <div class="icon-list">
        <a href="#home" class="logo page-scroll waves-effect">Wiki Search</a>
        <a href="#about-us" class="page-scroll waves-effect"><i class="fa fa-fw fa-user"></i><span>About Us</span></a>
        <a href="#portfolio" class="page-scroll waves-effect"><i class="fa fa-fw fa-bell-o"></i><span>Portfolio</span></a>
        <!-- <a href="#service" class="page-scroll waves-effect"><i class="fa fa-fw fa-bar-chart-o"></i><span>Services</span></a> -->
        <a href="#team" class="page-scroll waves-effect"><i class="fa fa-fw fa-users"></i><span>Our Team</span></a>
        <a href="#latest-news" class="page-scroll waves-effect"><i class="fa fa-fw fa-mortar-board"></i><span>Challenges</span></a>
        <a href="#search" class="page-scroll waves-effect"><i class="fa fa-fw fa-comment-o"></i><span>Wiki Search</span></a>
        <!-- <a href="#contact" class="page-scroll waves-effect"><i class="fa fa-fw fa-envelope-o"></i><span>Contact</span></a> -->
      </div>
    </nav>
    <button class="close-button" id="close-button">Close Menu</button>
  </div>
  <button class="menu-button waves-effect" id="open-button">Open Menu</button>
  <!-- End Off-Canvas Navigation Section -->


  <!-- ***************************************************************** -->
  <!-- Start Header Section -->
  <!-- ***************************************************************** -->
  <section class="header" id="home">
    <div class="container">
      <div class="intro-text">
        <h1>Welcome To The <span>Wikipedia Search Engine</span></h1>
        <p>As a part of Google's CodeU program, we (Team 39) built this amazing Search Engine for Wikipedia</p>
        <!-- <a href="#feature" class="page-scroll waves-effect btn btn-primary">Read More</a> -->
      </div>
    </div>
  </section>
  <!-- End Header Section -->




  <!-- Start About Us Section -->
  <section id="about-us" class="about-us-section-1">
    <div class="container">
      <div class="row">
        <div class="col-md-12 col-sm-12">
          <div class="section-title text-center">
            <h2>About Us</h2>
            <p>Two developers on an adventure to learn something cool and new</p>
          </div>
        </div>
      </div>
      <div class="row">

        <div class="col-md-4 col-sm-4">
          <div class="welcome-section text-center waves-effect">
            <img src="assets/images/photo-2.jpg" class="img-responsive" alt="..">
            <h4>Philosophy</h4>
            <div class="border"></div>
            <!-- <p>Duis aute irure dolor in reprehen derit in voluptate velit essecillum dolore eu fugiat nulla pariatur. Lorem reprehenderit</p> -->
          </div>
        </div>

        <div class="col-md-4 col-sm-4">
          <div class="welcome-section text-center waves-effect">
            <img src="assets/images/photo-1.jpg" class="img-responsive" alt="..">
            <h4>Our Mission & Vission</h4>
            <div class="border"></div>
            <!-- <p>Duis aute irure dolor in reprehen derit in voluptate velit essecillum dolore eu fugiat nulla pariatur. Lorem reprehenderit</p> -->
          </div>
        </div>

        <div class="col-md-4 col-sm-4">
          <div class="welcome-section text-center waves-effect">
            <img src="assets/images/photo-3.jpg" class="img-responsive" alt="..">
            <h4>Value & Rules</h4>
            <div class="border"></div>
            <!-- <p>Duis aute irure dolor in reprehen derit in voluptate velit essecillum dolore eu fugiat nulla pariatur. Lorem reprehenderit</p> -->
          </div>
        </div>

      </div><!-- /.row -->

    </div><!-- /.container -->
  </section>
  <!-- End About Us Section -->



  <!-- Start About Us Section 2 -->
  <div class="about-us-section-2">
    <div class="container">
      <div class="row">
        <div class="col-md-12 col-sm-12">
          <div class="section-title text-center">
            <h2>Our Goals and Progress</h2><br>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-8">
          <ul id="skill-section" class="clearfix skill-graph waves-effect">

            <!-- single skill -->
            <li>
              <div class="after-li">
                <div class="singel-hr">
                  <div class="singel-hr-inner waves-effect" data-height="90%"></div>
                  <div class="skill-visiable">
                    <span class="skill-count">90%</span>
                  </div>
                  <span class="skill-title">March</span>
                </div>
              </div>
            </li>
            <!-- /single skill-->

            <!-- single skill -->
            <li>
              <div class="after-li">
                <div class="singel-hr">
                  <div class="singel-hr-inner waves-effect" data-height="100%"></div>
                  <div class="skill-visiable">
                    <span class="skill-count">100%</span>
                  </div>
                  <span class="skill-title">April</span>
                </div>
              </div>
            </li>
            <!-- /single skill-->

            <!-- single skill -->
            <li>
              <div class="after-li">
                <div class="singel-hr">
                  <div class="singel-hr-inner waves-effect" data-height="80%"></div>
                  <div class="skill-visiable">
                    <span class="skill-count">80%</span>
                  </div>
                  <span class="skill-title">May</span>
                </div>
              </div>
            </li>
            <!-- /single skill-->

            <!-- single skill -->
            <li>
              <div class="after-li">
                <div class="singel-hr">
                  <div class="singel-hr-inner waves-effect" data-height="60%"></div>
                  <div class="skill-visiable">
                    <span class="skill-count">60%</span>
                  </div>
                  <span class="skill-title">June</span>
                </div>
              </div>
            </li>
            <!-- /single skill-->

            <!-- single skill -->
            <li>
              <div class="after-li">
                <div class="singel-hr">
                  <div class="singel-hr-inner waves-effect" data-height="85%"></div>
                  <div class="skill-visiable">
                    <span class="skill-count">85%</span>
                  </div>
                  <span class="skill-title">July</span>
                </div>
              </div>
            </li>
            <!-- /single skill-->

            <!-- single skill -->
            <li>
              <div class="after-li">
                <div class="singel-hr">
                  <div class="singel-hr-inner waves-effect" data-height="80%"></div>
                  <div class="skill-visiable">
                    <span class="skill-count">80%</span>
                  </div>
                  <span class="skill-title">August</span>
                </div>
              </div>
            </li>
            <!-- /single skill-->

          </ul>
        </div>

        <div class="col-md-4">
          <div class="custom-accordion waves-effect">
            <!-- Start Accordion Section -->
            <div class="panel-group" id="accordion">

              <!-- Start Accordion 1 -->
              <div class="panel panel-default">
                <div class="panel-heading waves-effect">
                  <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse-1">
                      <i class="fa fa-angle-left control-icon"></i>Challenges
                    </a>
                  </h4>
                </div>
                <div id="collapse-1" class="panel-collapse collapse in">
                  <div class="panel-body">Our first challenge was:</div>
                </div>
              </div>
              <!-- End Accordion 1 -->

              <!-- Start Accordion 2 -->
              <div class="panel panel-default">
                <div class="panel-heading waves-effect">
                  <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse-2" class="collapsed">
                      <i class="fa fa-angle-left control-icon"></i>Challenge 2
                    </a>
                  </h4>
                </div>
                <div id="collapse-2" class="panel-collapse collapse">
                  <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec iaculis metus vitae ligula elementum ut luctus lorem facilisis. Sed non leo nisl, ac euismod nisi. Aenean augue dolor, facilisis id fringilla ut, tempus vitae nibh.</div>                               </div>
              </div>
              <!-- End Accordion 2 -->

              <!-- Start Accordion 3 -->
              <div class="panel panel-default">
                <div class="panel-heading waves-effect">
                  <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse-3" class="collapsed">
                      <i class="fa fa-angle-left control-icon"></i> Challenge 3
                    </a>
                  </h4>
                </div>
                <div id="collapse-3" class="panel-collapse collapse">
                  <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec iaculis metus vitae ligula elementum ut luctus lorem facilisis. Sed non leo nisl, ac euismod nisi. Aenean augue dolor, facilisis id fringilla ut, tempus vitae nibh.</div>
                </div>
              </div>
              <!-- End Accordion 3 -->

              <!-- Start Accordion 4 -->
              <div class="panel panel-default">
                <div class="panel-heading waves-effect">
                  <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse-4" class="collapsed">
                      <i class="fa fa-angle-left control-icon"></i> What we did
                    </a>
                  </h4>
                </div>
                <div id="collapse-4" class="panel-collapse collapse">
                  <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec iaculis metus vitae ligula elementum ut luctus lorem facilisis. Sed non leo nisl, ac euismod nisi. Aenean augue dolor, facilisis id fringilla ut, tempus vitae nibh.</div>
                </div>
              </div>
              <!-- End Accordion 4 -->

            </div>
            <!-- End Accordion section -->
          </div>
        </div>

      </div>
    </div>
  </div>
  <!-- End About Us Section 2 -->


  <!-- Start Fun Facts Section -->
  <!-- <section class="fun-facts">
      <div class="container">
          <div class="row">
              <div class="col-xs-12 col-sm-6 col-md-3">
                    <div class="counter-item waves-effect">
                      <i class="fa fa-cloud-upload"></i>
                      <div class="timer" id="item1" data-to="991" data-speed="5000"></div>
                      <h5>Files uploaded</h5>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-6 col-md-3">
                    <div class="counter-item waves-effect">
                      <i class="fa fa-check"></i>
                      <div class="timer" id="item2" data-to="7394" data-speed="5000"></div>
                      <h5>Projects completed</h5>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-6 col-md-3">
                    <div class="counter-item waves-effect">
                      <i class="fa fa-code"></i>
                      <div class="timer" id="item3" data-to="18745" data-speed="5000"></div>
                      <h5>Lines of code written</h5>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-6 col-md-3">
                    <div class="counter-item waves-effect">
                      <i class="fa fa-male"></i>
                      <div class="timer" id="item4" data-to="8423" data-speed="5000"></div>
                      <h5>Happy clients</h5>
                    </div>
                  </div>
          </div>
      </div>
  </section> -->
  <!-- End Fun Facts Section -->



  <!-- Start Team Member Section -->
  <section id="team" class="team-member-section">
    <div class="container">
      <div class="row">
        <div class="col-md-12 col-sm-12">
          <div class="section-title text-center">
            <h2>Team 39</h2>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-md-6 col-sm-12">
          <div class="team-member">
            <img src="assets/images/team/face_1.png" class="img-responsive" alt="">
            <div class="team-details">
              <h4>Vincent Tsang</h4>
              <div class="designation">Tufts University</div>
              <p class="description"></p>
              <ul>
                <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                <li><a href="#"><i class="fa fa-linkedin"></i></a></li>
              </ul>
            </div>
          </div>
        </div>
        <div class="col-md-6 col-sm-12">
          <div class="team-member">
            <img src="assets/images/team/face_2.png" class="img-responsive" alt="">
            <div class="team-details">
              <h4>Aishani Bhalla</h4>
              <div class="designation">University at Buffalo, SUNY</div>
              <p class="description"></p>
              <ul>
                <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                <li><a href="#"><i class="fa fa-linkedin"></i></a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!-- End Team Member Section -->



  <!-- Start Call to Action Section -->
  <section id="search" class="call-to-action">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <h1>Wiki Search</h1>
          <form method="post" action="WikiServletPath" class="search-form">
            <input type="text" name="searchTerm" size=70 placeholder="What are you looking for?">
          </form>
        </div>
      </div>
    </div>
  </section>
  <!-- End Call to Action Section -->



  <!-- Start Latest News Section -->
  <section id="latest-news" class="latest-news-section">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <div class="section-title text-center">
            <h2>Challenges</h2>
            <p>Duis aute irure dolor in reprehenderit in voluptate</p>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="latest-news">
          <div class="col-md-4 col-sm-4">
            <div class="latest-post waves-effect">
              <img src="assets/images/post-1.jpg" class="img-responsive" alt="">
              <h4><a href="#">Standard Post with Image</a></h4>

              <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p>
              <a class="btn btn-primary">Read More</a>
            </div>
          </div>
          <div class="col-md-4 col-sm-4">
            <div class="latest-post waves-effect">
              <img src="assets/images/post-2.jpg" class="img-responsive" alt="">
              <h4><a href="#">Standard Post with Image</a></h4>

              <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p>
              <a class="btn btn-primary">Read More</a>
            </div>
          </div>
          <div class="col-md-4 col-sm-4">
            <div class="latest-post waves-effect">
              <img src="assets/images/post-3.jpg" class="img-responsive" alt="">
              <h4><a href="#">Standard Post with Image</a></h4>

              <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p>
              <a class="btn btn-primary">Read More</a>
            </div>
          </div>

        </div>
      </div>
    </div>
  </section>
  <!-- End Latest News Section -->



  <!-- jQuery Version 2.1.3 -->
  <script src="assets/js/jquery-2.1.3.min.js"></script>

  <!-- Bootstrap Core JavaScript -->
  <script src="assets/bootstrap/js/bootstrap.min.js"></script>

  <!-- Plugin JavaScript -->
  <script src="assets/js/jquery.easing.1.3.js"></script>
  <script src="assets/js/classie.js"></script>
  <script src="assets/js/count-to.js"></script>
  <script src="assets/js/jquery.appear.js"></script>
  <script src="assets/js/owl.carousel.min.js"></script>
  <script src="assets/js/jquery.fitvids.js"></script>

  <!-- Materialize js -->
  <script src="assets/js/material.js"></script>
  <script src="assets/js/waypoints.min.js"></script>

  <!-- Custom JavaScript -->
  <script src="assets/js/script.js"></script>
  </body>
</html>
