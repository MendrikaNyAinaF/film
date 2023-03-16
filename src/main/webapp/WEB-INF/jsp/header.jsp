<%@ page contentType="text/html;charset=UTF-8" language="java" %>
            <%@page import="java.util.List,app.apps.model.*, java.util.Date" %>
<!DOCTYPE html>
<html dir="ltr" lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Favicon icon -->
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/resources/assets/images/favicon.png">
    <title>Evenement</title>
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/resources/assets/extra-libs/c3/c3.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/libs/chartist/dist/chartist.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/extra-libs/jvector/jquery-jvectormap-2.0.2.css" rel="stylesheet" />
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/resources/dist/css/style.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/extra-libs/prism/prism.css">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/extra-libs/select2/select2.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/extra-libs/select2-bootstrap-theme/select2-bootstrap.min.css">
    <link href="${pageContext.request.contextPath}/resources/assets/libs/fullcalendar/dist/fullcalendar.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/resources/assets/libs/morris.js/morris.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>
<%
    Film fil=null;
    if(request.getSession().getAttribute("current_film")!=null){
        fil=(Film)request.getSession().getAttribute("current_film");
    }
%>
<body>
    <!-- ============================================================== -->
    <!-- Preloader - style you can find in spinners.css -->
    <!-- ============================================================== -->
    <div class="preloader">
        <div class="lds-ripple">
            <div class="lds-pos"></div>
            <div class="lds-pos"></div>
        </div>
    </div>
    <!-- ============================================================== -->
    <!-- Main wrapper - style you can find in pages.scss -->
    <!-- ============================================================== -->
    <div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
        data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">
        <!-- ============================================================== -->
        <!-- Topbar header - style you can find in pages.scss -->
        <!-- ============================================================== -->
        <header class="topbar" data-navbarbg="skin6">
            <nav class="navbar top-navbar navbar-expand-md">
                <div class="navbar-header" data-logobg="skin6">
                    <!-- This is for the sidebar toggle which is visible on mobile only -->
                    <a class="nav-toggler waves-effect waves-light d-block d-md-none" href="javascript:void(0)"><i
                            class="ti-menu ti-close"></i></a>
                    <!-- ============================================================== -->
                    <!-- Logo -->
                    <!-- ============================================================== -->
                    <div class="navbar-brand">
                        <!-- Logo icon -->
                        <a href="liste_element">
                            <b class="logo-icon">
                                <!-- Dark Logo icon -->
                                <img src="${pageContext.request.contextPath}/resources/assets/images/logo-icon.png" alt="homepage" class="dark-logo" />
                                <!-- Light Logo icon -->
                                <img src="${pageContext.request.contextPath}/resources/assets/images/logo-icon.png" alt="homepage" class="light-logo" />
                            </b>
                            <!--End Logo icon -->
                        </a>
                    </div>
                    <!-- ============================================================== -->
                    <!-- End Logo -->
                    <!-- ============================================================== -->
                    <!-- ============================================================== -->
                    <!-- Toggle which is visible on mobile only -->
                    <!-- ============================================================== -->
                    <a class="topbartoggler d-block d-md-none waves-effect waves-light" href="javascript:void(0)"
                        data-toggle="collapse" data-target="#navbarSupportedContent"
                        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><i
                            class="ti-more"></i></a>
                </div>
                <!-- ============================================================== -->
                <!-- End Logo -->
                <!-- ============================================================== -->
                <div class="navbar-collapse collapse" id="navbarSupportedContent">
                    <!--navigation-->
                    <ul class="navbar-nav float-left mr-auto ml-3 pl-1"></ul>
                    <h3 class="page-title text-truncate text-dark font-weight-medium mb-1">Film, <% if(fil!=null){
                        out.print(fil.getTitle());
                    }else{
                        out.print("pas encore choisi");
                    }
                        %></h3>

                </div>
            </nav>
        </header>
        <!-- ============================================================== -->
        <!-- End Topbar header -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- Left Sidebar - style you can find in sidebar.scss  -->
        <!-- ============================================================== -->
        <aside class="left-sidebar" data-sidebarbg="skin6">
            <!-- Sidebar scroll-->
            <div class="scroll-sidebar" data-sidebarbg="skin6">
                <!-- Sidebar navigation-->
                <nav class="sidebar-nav">
                    <ul id="sidebarnav">
                        <li class="sidebar-item"> <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/films/0"
                                aria-expanded="false"><i data-feather="home" class="feather-icon"></i><span
                                    class="hide-menu">Accueil</span></a></li>
                        <li class="list-divider"></li>
                        <li class="nav-small-cap"><span class="hide-menu">FilmArt</span></li>

                        <!--film-->
                        <li class="sidebar-item"> <a class="sidebar-link has-arrow" href="javascript:void(0)"
                                aria-expanded="false"><i data-feather="crosshair" class="feather-icon"></i><span
                                    class="hide-menu">Film</span></a>
                            <ul aria-expanded="false" class="collapse first-level base-level-line">
                                 <li class="sidebar-item">
                                    <a class="sidebar-link" href="${pageContext.request.contextPath}/films/0"><span class="hide-menu">Mes films</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="${pageContext.request.contextPath}/film/create"><span class="hide-menu">Ajouter film</span>
                                    </a>
                                </li>
                            </ul> 
                        </li>

                        <!--scene-->
                        <li class="sidebar-item"> <a class="sidebar-link has-arrow" href="javascript:void(0)"
                                aria-expanded="false"><i data-feather="crosshair" class="feather-icon"></i><span
                                    class="hide-menu">Scene</span></a>
                            <ul aria-expanded="false" class="collapse first-level base-level-line">
                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="${pageContext.request.contextPath}/<% if (fil!=null){
                                            out.print("film/"+fil.getId()+"/scenes/0");
                                        }else{
                                            out.print("films/0");
                                        }%>"><span class="hide-menu">Liste des scènes</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="${pageContext.request.contextPath}/<% if (fil!=null){
                                            out.print("film/"+fil.getId()+"/scene/create");
                                        }else{
                                            out.print("films/0");
                                        }%>"><span class="hide-menu">Ajouter une scene</span>
                                    </a>
                                </li>
                            </ul> 
                        </li>

                        <!--planning-->
                        <li class="sidebar-item"> <a class="sidebar-link has-arrow" href="javascript:void(0)"
                                aria-expanded="false"><i data-feather="crosshair" class="feather-icon"></i><span
                                    class="hide-menu">Planning</span></a>
                            <ul aria-expanded="false" class="collapse first-level base-level-line">
                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="${pageContext.request.contextPath}/<% if (fil!=null){
                                            out.print("film/"+fil.getId()+"/planning");
                                        }else{
                                            out.print("films/0");
                                        }%>"><span class="hide-menu">Planning global</span>
                                    </a>
                                </li>
                                <li class="sidebar-item"><a href="${pageContext.request.contextPath}/<% if (fil!=null){
                                            out.print("film/"+fil.getId()+"/planifier");
                                        }else{
                                            out.print("films/0");
                                        }%>" class="sidebar-link"><span
                                            class="hide-menu">Plannifier</span></a>
                            </ul> 
                        </li>

                        <li class="sidebar-item"> <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/filmsets"
                                aria-expanded="false"><i data-feather="message-square" class="feather-icon"></i><span
                                    class="hide-menu">Plateau</span></a></li>
                        <li class="sidebar-item"> <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/actors"
                                aria-expanded="false"><i data-feather="message-square" class="feather-icon"></i><span
                                    class="hide-menu">Acteur</span></a></li>
                        <li class="sidebar-item"> <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/ferie"
                                aria-expanded="false"><i data-feather="message-square" class="feather-icon"></i><span
                                    class="hide-menu">Jour férié</span></a></li>
                    </ul>
                </nav>
                <!-- End Sidebar navigation -->
            </div>
            <!-- End Sidebar scroll-->
        </aside>
        <div class="page-wrapper">
            <div class="container-fluid">
            <%
                if(request.getAttribute("erreur") != null){
            %>
                <div class="alert alert-danger" role="alert">
                    <strong>Erreur!, </strong> <%=request.getAttribute("erreur")%>
                </div>
            <%    }
            %>
