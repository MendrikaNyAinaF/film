<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire,app.apps.model.Character" %>
<jsp:include page="header.jsp" />
<% if(request.getAttribute("actor")!=null){
               Actor ac=(Actor)request.getAttribute("actor"); %>
          
    <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                         
                            <div class="" >
                                <div class="row">                                  
                                    <div class="col-lg-12">
                                        <div class="card-body b-l calender-sidebar">
                                            <h2>Indisponibilite</h2>
                                                <form action="${pageContext.request.contextPath}/search_scene" method="post" class="row">
                                                    <div class="customize-input col-md-12">
                                                        <input class="form-control custom-shadow border-0 bg-white col-3" type="date"
                                                                placeholder="Search"  name="date_debut">
                                                        <input class="form-control custom-shadow border-0 bg-white col-3" type="date"
                                                                placeholder="Search"  name="date_fin">
                                                        <input class="form-control custom-shadow border-0 bg-white col-3" type="text"
                                                                placeholder="Search"  name="observation">
                                                        <button type="submit" class="btn btn-primary col-1">ajouter</button>
                                                    </div>
                                                </form>
                                             <h1>Planning de l'acteur: <%= ac.getName() %></h1>
                                             <div id="calendar"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
<script src="${pageContext.request.contextPath}/resources/assets/jquery/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/libs/jquery/dist/jquery.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/assets/libs/moment/min/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/libs/fullcalendar/dist/fullcalendar.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/dist/js/pages/calendar/cal-filmset.js"></script>
<script type="text/javascript">
        const indis=<%= request.getAttribute("liste_actor_unavailable") %>;
        const scenes=<%= request.getAttribute("liste_scene") %>
            $.CalendarApp2.init(dis, scenes);

    </script>
<%   } else{ %>
     <div class="alert alert-danger" role="alert">
          <strong>Choisissez un acteur!, </strong> 
     </div>
<% }
%>
     

<jsp:include page="footer.jsp" />
