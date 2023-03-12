<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire, app.apps.model.Character" %>
<jsp:include page="header.jsp" />
     <div class="card">
                    <div class="card-body">
                         <h4 class="card-title">Ajouter un film</h4>
                         <form action="${pageContext.request.contextPath}/film/create" method="POST"
                              >
                              <div class="form-body">
                                   <div class="row">
                                        <div class="col-md-6">
                                             <div class="form-group">
                                                  <label for="">Titre</label>
                                                  <input type="text" class="form-control" placeholder="titre"
                                                       name="titre">
                                             </div>
                                        </div>
                                        <div class="col-md-12">
                                             <div class="form-group">
                                                  <label for="">Description</label>
                                                  <textarea class="form-control" placeholder="..." rows="3"
                                                       name="description"></textarea>
                                             </div>
                                        </div>
                                        <div class="col-md-12">
                                             <div class="form-group">
                                                  <label for="">Image</label>
                                                  <div class="input-group mb-3">
                                                       <div class="input-group-prepend">
                                                            <span class="input-group-text">upload</span>
                                                       </div>
                                                       <div class="custom-file">
                                                            <input type="hidden" class="custom-file-input" id="fileupload_input" name="image">
                                                            <input type="file" class="custom-file-input" id="fileupload">
                                                            <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                                                       </div>
                                                  </div>
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
                                                                 <select class="form-control" name="personnage_genre">
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
                                                                 <select class="form-control" name="personnage_acteur">
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
     // Récupère les éléments DOM pour le input file et le input text
     const fileInput = document.getElementById('fileupload');
     const textInput = document.getElementById('fileupload_input');

     // Ajoute un événement "change" au input file
     fileInput.addEventListener('change', (event) => {
          // Récupère la valeur du fichier sélectionné
          const fileName = event.target.files[0];
          const reader = new FileReader();
          reader.readAsDataURL(fileName)

          reader.onload = () => {
               const spliter = reader.result.split(',')
               const f = spliter?.length > 1 ? spliter[1] : ''
               
               // Copie la valeur du fichier sélectionné dans la valeur du input text
               textInput.value = f;
          }
          
     });

     var tempactor=<%= request.getAttribute("liste_actor_json") %>;
     var tempgender=<%= request.getAttribute("liste_gender_json") %>
     var liste_actor=[];
     var liste_gender=[];
     if(tempactor!=null){
          liste_actor=tempactor;
     }
     if(tempgender!=null){
          liste_gender=tempgender;
     }
     function appender(){
          ajouter(liste_actor, liste_gender);
     }
</script>
<jsp:include page="footer.jsp" />
