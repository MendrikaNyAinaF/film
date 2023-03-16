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
                                                <form action="${pageContext.request.contextPath}/search_scene" method="post" class="row">
                                                    <div class="customize-input col-md-12">
                                                        <input class="form-control custom-shadow border-0 bg-white col-5" type="search"
                                                                placeholder="Search" aria-label="Search" style="display:flex" name="motcle"
                                                                value="<%
                                                                    if(request.getSession().getAttribute("scene_motcle")!=null){
                                                                        out.print(request.getSession().getAttribute("scene_motcle"));
                                                                    }
                                                                %>">
                                                        <select class="form-control custom-shadow border-0 bg-white col-3" name="status">
                                                                <option value=-1>Tout</option>
                                                                <% if(request.getAttribute("status")!=null){
                                                                    List<StatusPlanning>liste_status=(List<StatusPlanning>)request.getAttribute("status");
                                                                    for(StatusPlanning s: liste_status){ %>
                                                                        <option value=<%= s.getId() %>><%= s.getName() %></option>
                                                                <%     }
                                                                } %>
                                                        </select>
                                                        <div class="col-3" style="display:flex; padding:0px 3px;" >
                                                                <select class="js-example-basic-multiple form-control custom-shadow border-0 bg-white" multiple="multiple" style="width:100%"
                                                                    placeholder="acteur" name="actors">
                                                                    <% if(request.getAttribute("character")!=null){
                                                                        List<Actor> liste_chara=(List<Actor>)request.getAttribute("character");
                                                                        for(Actor c: liste_chara){ %>
                                                                            <option value=<%= c.getId() %>><%= c.getName() %></option>
                                                                    <%     }
                                                                    } %>
                                                                </select>
                                                    </div>
                                                        

                                                        <button type="submit" style="display:flex" class="btn btn-primary col-1"><i
                                                                    class="form-control-icon" data-feather="search"
                                                                    style="display:flex">Search</i></button>
                                                    </div>
                                                </form>
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
