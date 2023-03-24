<%@ page contentType="text/html;charset=UTF-8" language="java" %>
     <%@page
          import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire,app.apps.model.Character,app.apps.model.StatusPlanning"
          %>
          <jsp:include page="header.jsp" />
          <div class="card col-lg-12">
               <div class="card-body">
                    <% if(request.getSession().getAttribute("current_film")==null){ %>
                         <div class="alert alert-danger" role="alert">
                              <strong>Choisissez un film!, </strong>
                         </div>
                         <% }else{ Film film=(Film)request.getSession().getAttribute("current_film");
                              if(request.getAttribute("liste_scene")!=null && request.getAttribute("plateau")!=null){
                              List<Scene> liste_scene = (List<Scene>) request.getAttribute("liste_scene");
                                   List<FilmSet>liste_plateau=(List<FilmSet>request.getAttribute("plateau"));
                                             if(liste_scene.size()==0){
                                             out.println("Toute les scènes ont été planifiée");
                                             }else{ %>
                                             <h4>Planning</h4>
                                             <div class="row">

                                                  <div class="col-md-3">
                                                       <% for(Plateau p: liste_plateau){ %>
                                                            <div class="custom-control custom-checkbox">
                                                                 <input type="checkbox"
                                                                      class="custom-control-input plateau-filtre"
                                                                      id="customCheck<%= p.getId() %>" value=<%= p.getId()
                                                                      %> checked />
                                                                 <label
                                                                      class="mb-0 text-white custom-control-label"
                                                                      for="customCheck<%= p.getId() %>">
                                                                      <%= p.getName() %>
                                                                 </label>
                                                            </div>
                                                       <% } %>
                                                  </div>

                                             </div>
                                             <form class="mt-4" method="post"
                                                  action="${pageContext.request.contextPath}/film/<%= film.getId() %>/planifier">
                                                  <div class="row">
                                                       <div class="col-lg-4 col-md-4 col-xs-6">
                                                            <div class="form-group">
                                                                 <label -for="">Date de commencement</label>
                                                                 <input type="datetime-local" class="form-control"
                                                                      name="start_date">
                                                            </div>
                                                       </div>
                                                       <div class="col-lg-4 col-md-4 col-xs-6">
                                                            <div class="form-group">
                                                                 <label for="">Date de fin</label>
                                                                 <input type="datetime-local" class="form-control"
                                                                      name="end_date">
                                                            </div>
                                                       </div>
                                                  </div>

                                                  <br />
                                                  <br />
                                                  <div class="row">
                                                       <div class="col-md-10">
                                                            <h4>Choisir les scènes</h4>
                                                       </div>
                                                       <div class="col-md-2">
                                                            <button class="btn btn-info">Proposer</button>
                                                       </div>
                                                  </div>

                                                  <br />
                                                  <div class="row">
                                                       <% for(Scene s : liste_scene){ %>
                                                            <div class="col-md-4 scene_plate">
                                                                 <input type="hidden" name="idscene_hidden" value=<%=s.getId() %> />
                                                                 <div class="card border-dark">
                                                                      <div class="card-header bg-dark">
                                                                           <div class="custom-control custom-checkbox">
                                                                                <input type="checkbox"
                                                                                     class="custom-control-input"
                                                                                     id="customCheck<%= s.getId() %>"
                                                                                     name="idscene" value=<%=s.getId()
                                                                                     %> />
                                                                                <label
                                                                                     class="mb-0 text-white custom-control-label"
                                                                                     for="customCheck<%= s.getId() %>">
                                                                                     <%= s.getTitle() %>
                                                                                </label>
                                                                           </div>
                                                                      </div>
                                                                      <div class="card-body">
                                                                           <h3 class="card-title">Plateau: <%=
                                                                                     s.getFilmset().getName() %>
                                                                           </h3>
                                                                           <p class="card-text">Temps estime de <%=
                                                                                     s.getEstimated_time() %>,
                                                                                     <% if(s.getPreferred_shooting_time()!=null){
                                                                                          out.println(" avec une
                                                                                          preference pour "+s.getPreferred_shooting_time());
                                                                 }else{
                                                                      out.println(" sans preference"); } %>
                                                                           </p>
                                                                      </div>
                                                                 </div>
                                                            </div>
                                                            <% } %>

                                                  </div>
                                             </form>
                                             <% } } else{ out.println("Toute les scènes ont été planifiée"); } } %>
               </div>
          </div>
          <script type="text/javascript">
               const plat_filtre=document.getElementsByClassName("plateau-filtre");
               const card=document.getElementsByClassName("scene_plate");
               function change(event, id){
                    if(event.checked){
                         for(let i=0;i<card.length;i++){
                              if(id==card[i].firstChild.value){
                                   card.style.display="block";
                              }
                         }
                    }else{
                         for(let i=0;i<card.length;i++){
                              if(id==card[i].firstChild.value){
                                   card.style.display="none";
                              }
                         }
                    }
               }
          </script>
          <jsp:include page="footer.jsp" />
