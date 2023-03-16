<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire,app.apps.model.Character" %>
<jsp:include page="header.jsp" />
<% if(request.getAttribute("actor")!=null){
               Actor ac=(Actor)request.getAttribute("actor"); %>
          
<div class="row">
    <div class="col-md-12">
        <div class="card">
            <h4 class="card-title">Indisponibilite</h4>
                <div class="card-body">
                <form action="${pageContext.request.contextPath}/actor/<%= ac.getId() %>/indisponible" method="post" class="row">
                    <div class="row">
                        <div class="col-md-3">
                                <div class="form-group">
                                    <label for="">Date debut</label>
                                    <input type="date" class="form-control" placeholder="titre" name="date_debut" />
                                </div>
                        </div>
                        <div class="col-md-3">
                                <div class="form-group">
                                    <label for="">Date fin</label>
                                    <input type="date" class="form-control" placeholder="titre" name="date_fin" />
                                </div>
                        </div>
                        <div class="col-md-6">
                                <div class="form-group">
                                    <label for="">Observation</label>
                                    <input type="text" class="form-control" placeholder="..." name="observation" />
                                </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">ajouter</button>
                </form>  
                </div> 
        </div>  
        <div class="card">     
            <div class="card-body b-l calender-sidebar">  
                <h1>Planning de l'acteur: <%= ac.getName() %></h1>
                <div id="calendar"></div>
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
