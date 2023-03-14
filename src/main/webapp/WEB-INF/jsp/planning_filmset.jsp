<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire,app.apps.model.Character" %>
<jsp:include page="header.jsp" />
<% if(request.getAttribute("plateau")!=null){
               Filmset filmset=(Filmset)request.getAttribute("plateau"); %>
          
    <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                         
                            <div class="" >
                                <div class="row">                                  
                                    <div class="col-lg-12">
                                        <div class="card-body b-l calender-sidebar">
                                             <h1>Planning du plateau: <%= filmset.getName() %></h1>
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
        const dis=<%= request.getAttribute("liste_filmsetplanning") %>;
        const scenes=<%= request.getAttribute("liste_scene") %>
            $.CalendarApp2.init(dis, scenes);

    </script>
<%   } else{ %>
     <div class="alert alert-danger" role="alert">
          <strong>Choisissez un plateau!, </strong> 
     </div>
<% }
%>
     

<jsp:include page="footer.jsp" />
