<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire" %>
<jsp:include page="jsp/header.jsp" />
     <div class="card">
                    <div class="card-body">
                         <h4 class="card-title">Ajouter un film</h4>
                         <form action="${pageContext.request.contextPath}/author/formulaire" method="POST"
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
                                                  <input type="number" class="form-control" placeholder="titre"
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
                                                       <input type="date" class="form-control" name="start_date">
                                                  </div>
                                             </div>
                                        </div>
                                        <div class="col-md-4">
                                             <div class="form-group">
                                                  <label for="">Début tournage</label>
                                                  <div class="form-group">
                                                       <input type="date" class="form-control" name="start_date">
                                                  </div>
                                             </div>
                                        </div>
                                        <div class="col-md-6">
                                             <div class="form-group">
                                                  <label for=""></label>
                                                  <select name="type" class="form-control" id="idOption">
                                                       <option value="plateau1">plateau1</option>
                                                       <option value="plateau1">plateau2</option>

                                                  </select>
                                             </div>
                                        </div>
                                        <div class="col-md-6">
                                             <div class="form-group">
                                                  <label for="">Temps de tournage estime</label>
                                                  <input type="text" class="form-control" placeholder="place"
                                                       name="place">
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
                                                                      placeholder="place" name="place">
                                                            </div>
                                                       </div>
                                                       <div class="col-md-4">
                                                            <div class="form-group">
                                                                 <label for="">Genre</label>
                                                                 <select name="type" class="form-control" id="idOption">
                                                                      <option value="plateau1">male</option>
                                                                      <option value="plateau1">female</option>

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
                                                                 <select name="type" class="form-control" id="idOption">
                                                                      <option value="plateau1">male</option>
                                                                      <option value="plateau1">female</option>

                                                                 </select>
                                                            </div>
                                                       </div>
                                                       <div class="col-md-12" id="dialogue_dialogue">
                                                            <div class="form-group">
                                                                 <label for="">description</label>
                                                                 <textarea class="form-control" placeholder="..."
                                                                      rows="3" name="description"></textarea>
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
     var liste_actor=[];
     function appender(){
          ajouter(liste_actor);
     }
</script>
<jsp:include page="jsp/footer.jsp" />
