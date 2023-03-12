<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire,app.apps.model.Character" %>
<jsp:include page="header.jsp" />
<% 
     if(request.getSession().getAttribute("current_film")!=null){
          Film film=(Film)request.getSession().getAttribute("current_film");
%>
    <div class="card">
          <div class="card-body">
                         <h4 class="card-title">Modifier la scene de: </h4>
                         <form action="${pageContext.request.contextPath}/film/<%= film.getId() %>/scene/update" method="POST"
                              enctype="multipart/form-data">
                              <div class="form-body">
                                   <div class="row">
                                        <div class="col-md-12">
                                             <div class="form-group">
                                                  <label for="">Titre</label>
                                                  <input type="text" class="form-control" placeholder="titre" name="titre">
                                             </div>
                                        </div>
                                        <div class="col-md-12">
                                             <div class="form-group">
                                                  <label for="">Action et Description</label>
                                                  <textarea class="form-control" placeholder="..." rows="3" name="description"></textarea>
                                             </div>
                                        </div>

                                        <div class="col-md-12">
                                             <div class="form-group">
                                                  <label for="">Time line</label>
                                                  <div class="row">
                                                       <div class="col-md-4">
                                                            <div class="form-group">
                                                                 <input type="time" value="00:00:00" step="1" class="form-control"
                                                                      name="time_start">
                                                            </div>
                                                       </div>
                                                       <div class="col-md-4">
                                                            <div class="form-group">
                                                                 <input type="time" value="00:00:00" step="1" class="form-control"
                                                                      name="time_end">
                                                            </div>
                                                       </div>
                                                  </div>
                                             </div>
                                        </div>
                                        <div class="col-md-4">
                                             <div class="form-group">
                                                  <label for="">Plateau</label>
                                                  <select name="type" class="form-control" name="filmset">
                                                       <% if(request.getAttribute("plateau")!=null){
                                                            List<Filmset>filmset=(List<Filmset>)request.getAttribute("plateau");
                                                            for(Filmset f: filmset){ %>
                                                                 <option value=<%= f.getId() %>><%= f.getName() %></option>
                                                       <%     }
                                                       } %>

                                                  </select>
                                             </div>
                                        </div>
                                        <div class="col-md-4">
                                             <div class="form-group">
                                                  <label for="">Temps de tournage estime</label>
                                                  <input type="time" value="00:00:00" step="1" class="form-control" 
                                                       name="estimed_time">
                                             </div>
                                        </div>
                                        <div class="col-md-4">
                                             <div class="form-group">
                                                  <label for="">Heure de tournage souhaite</label>
                                                  <input type="time" placeholder="00:00:00" step="1" class="form-control" 
                                                       name="shooting_prefered">
                                             </div>
                                        </div>

                                        <button type="submit" class="btn btn-primary" >Enregistrer</button>
                                   </div>
                              </div>
                         </form>

                    </div>       
<%     }else{ %>
     <div class="alert alert-danger" role="alert">
          <strong>Choisissez un film!, </strong> 
     </div>
<% }
%>

<jsp:include page="footer.jsp" />
