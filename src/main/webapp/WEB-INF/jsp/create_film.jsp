<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire" %>
<jsp:include page="jsp/header.jsp" />
     <div class="card">
                    <div class="card-body">
                         <h4 class="card-title">Ajouter un film</h4>
                         <form action="${pageContext.request.contextPath}/film/create" method="POST"
                              enctype="multipart/form-data">
                              <div class="form-body">
                                   <div class="row">
                                        <div class="col-md-8">
                                             <div class="form-group">
                                                  <label for="">Titre</label>
                                                  <input type="text" class="form-control" placeholder="titre"
                                                       name="titre">
                                             </div>
                                        </div>
                                        <div class="col-md-4">
                                             <div class="form-group">
                                                  <label for="">Equipe</label>
                                                  <input type="number" class="form-control" placeholder="0"
                                                       name="team">
                                             </div>
                                        </div>
                                        <div class="col-md-12">
                                             <div class="form-group">
                                                  <label for="">Description</label>
                                                  <textarea class="form-control" placeholder="..." rows="3"
                                                       name="description"></textarea>
                                             </div>
                                        </div>

                                        <div class="col-md-4">
                                             <div class="form-group">
                                                  <label for="">Durée</label>
                                                  <div class="form-group">
                                                       <input type="time" class="form-control" name="duree">
                                                  </div>
                                             </div>
                                        </div>
                                        <div class="col-md-4">
                                             <div class="form-group">
                                                  <label for="time">Début tournage</label>
                                                  <div class="form-group">
                                                       <input type="date" class="form-control" name="tournage_debut">
                                                  </div>
                                             </div>
                                        </div>
                                        <div class="col-md-12">
                                             <h4>Personnage</h4>

                                             <div id="personnage">
                                                  <div class="row custom-shadow">
                                                       <div class="col-md-4">
                                                            <div class="form-group">
                                                                 <label for="">Nom</label>
                                                                 <input type="text" class="form-control"
                                                                      placeholder="place" name="personnage_nom">
                                                            </div>
                                                       </div>
                                                       <div class="col-md-4">
                                                            <div class="form-group">
                                                                 <label for="">Genre</label>
                                                                 <select name="type" class="form-control" name="personnage_genre">
                                                                      <% if(request.getAttribute("gender")!=null){
                                                                           List<Gender>gender=(List<Gender>)request.getAttribute("gender");
                                                                           for(Gender g:gender){ %>
                                                                                <option value="<%= g.getId() %>"><%= g.getName() %></option>
                                                                      <%     }
                                                                       } %>
                                                                 </select>
                                                            </div>
                                                       </div>
                                                       <div class="col-md-2"></div>
                                                       <div class="col-md-2">
                                                            <div class="form-group">
                                                                 <br/>
                                                                 <button class="btn btn-danger" type="button"
                                                                      onClick="effacer(this)">- enlever</button>
                                                            </div>
                                                       </div>
                                                       <div class="col-md-6" id="dialogue_action">
                                                            <div class="form-group">
                                                                 <label for="">Acteur</label>
                                                                 <select name="type" class="form-control" name="personnage_acteur">
                                                                      <% if(request.getAttribute("actor")!=null){
                                                                           List<Actor>actor=(List<Actor>)request.getAttribute("actor");
                                                                           for(Actor a:actor){ %>
                                                                                <option value="<%= a.getId() %>"><%= a.getName() %></option>
                                                                      <%     }
                                                                       } %>
                                                                 </select>
                                                            </div>
                                                       </div>
                                                       <div class="col-md-12" id="dialogue_dialogue">
                                                            <div class="form-group">
                                                                 <label for="">description</label>
                                                                 <textarea class="form-control" placeholder="..."
                                                                      rows="3" name="personnage_description"></textarea>
                                                            </div>
                                                       </div>
                                                       <br/>
                                                  </div>
                                                  
                                             </div>
                                        </div>

                                        <button class="btn btn-success" style="margin:20px" type="button"
                                             onClick="appender()">+ ajouter
                                             personnage</button>

                                   </div>

                                   <button type="submit" class="btn btn-primary">Enregistrer</button>
                              </div>
                    </div>
                    </form>
               </div>
<script src="${pageContext.request.contextPath}/resources/js/film_form.js"></script>
<script type="text/javascript">
     var tempactor=<%= request.getAttribute("liste_actor_json") %>;
     var tempgender=<%= request.getAttribute("liste_gender_json") %>
     var liste_actor=[];
     var liste_gender=[];
     if(tempactor!=null){
          liste_actor=JSON.stringify(tempactor);
     }
     if(tempgender!=null){
          liste_gender=JSON.stringify(tempgender);
     }
     function appender(){
          ajouter(liste_actor, liste_gender);
     }
</script>
<jsp:include page="jsp/footer.jsp" />
