<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire,app.apps.model.Character,app.apps.model.StatusPlanning" %>
<jsp:include page="header.jsp" />
<% 
     if(request.getSession().getAttribute("current_film")!=null){
          Film film=(Film)request.getSession().getAttribute("current_film");%>
          <h1>Film, <%= film.getTitle() %></h1>
               <div class="row">
                    <% if(request.getAttribute("scene")!=null){
                         Scene s=(Scene)request.getAttribute("scene"); %>
                    <div class="card col-6">
                         <div class="card-body">
                              <div class="card p-3 bg-info text-white">
                                   <p>Scene : <%= s.getTitle() %>
                                   </p>
                              </div>
                              <h4 class="card-title">Action globale</h4>
                              <p><%= s.getGlobal_action() %></p>

                              <h4 class="card-title">Time line</h4>
                              <p><%= s.getTime_start() %>-<%= s.getTime_end() %></p>

                              <h4 class="card-title">Temp estime tournage</h4>
                              <p><%= s.getEstimated_time() %></p>

                              <h4 class="card-title">Plateau</h4>
                              <p><%= s.getFilmset().getName() %></p>

                              <h4 class="card-title">Status</h4>
                              <p><%= s.getStatus().getName() %></p>
                              <% if(s.getStatus()==null || s.getStatus().getId()<1){ //si le status est non ok
                              %>
                                   <a class="btn btn-success" href="${pageContext.request.contextPath}/film/<%= film.getId() %>/scene/<%= s.getId() %>/update">modifier</a>
                              <% }
                                   %>
                              
                         </div>
                    </div>
                    <div class="card col-6">
                         <div class="card-body">
                              <h4 class="card-title">Dialogue</h4>
                              <ul>
                                   <% if(request.getAttribute("dialogue")!=null){
                                        List<Dialogue> dialogue=(List<Dialogue>)request.getAttribute("dialogue");
                                        for(Dialogue d: dialogue){                                    
                                   %>
                                        <li><span class="font-weight-bold"><%= d.getCharacter().getName() %>:</span><span class="text-primary"> '<%= d.getTexte() %>'</span> <cite
                                             title="Source Title">(<%= d.getAction() %>)</cite></li>
                                   <% } } %>
                                   
                              </ul>
                         </div>
                    </div>

                    <div class="card col-12">
                         <div class="card-body">
                         <% if(s.getStatus().getId()>0){ %>                        
                              <h3 class="text-primary">Changer status</h3>
                              <form action="${pageContext.request.contextPath}/film/<%= film.getId() %>/scene/<%= s.getId() %>/status" method="POST">
                                   <div class="form-body">
                                        <div class="row">
                                             <div class="col-md-4">
                                                  <div class="form-group">
                                                       <select name="status" class="form-control">
                                                            <% if(request.getAttribute("status_planning")!=null){
                                                                 List<StatusPlanning> status=(List<StatusPlanning>) request.getAttribute("status_planning");
                                                                 for(StatusPlanning st : status){ 
                                                                      if(st.getId()>1){ %>           
                                                                      <option value=<%= st.getId() %>><%= st.getName() %></option>
                                                            <%  }   }
                                                            } %>
                                                       </select>
                                                  </div>
                                             </div>
                                             <div class="col-md-4">
                                                  <button class="btn btn-primary" type="submit">changer</button>
                                             </div>
                                        </div>
                                   </div>
                              </form>
                         <%   
                         } %>
                         </div>
                    </div>
                    <%     
                    } %>
                    

               </div>
<%      }else{ %>
     <div class="alert alert-danger" role="alert">
          <strong>Film non defini </strong> 
     </div>
<% }
%>
     
<jsp:include page="footer.jsp" />
