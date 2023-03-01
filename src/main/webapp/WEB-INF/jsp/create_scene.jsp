<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire" %>
<jsp:include page="jsp/header.jsp" />
<% 
     if(film!=null){
   %>
    <div class="card">
          <div class="card-body">
                         <h4 class="card-title">Ajouter une scène</h4>
                         <form action="${pageContext.request.contextPath}/film/<%= film.getId() %>/scene/create" method="POST"
                              enctype="multipart/form-data">
                              <div class="form-body">
                                   <div class="row">
                                        <div class="col-md-12">
                                             <div class="form-group">
                                                  <label for="">Titre</label>
                                                  <input type="text" class="form-control" placeholder="titre"
                                                       name="titre">
                                             </div>
                                        </div>
                                        <div class="col-md-12">
                                             <div class="form-group">
                                                  <label for="">Action et Description</label>
                                                  <textarea class="form-control" placeholder="..." rows="3"
                                                       name="description"></textarea>
                                             </div>
                                        </div>

                                        <div class="col-md-12">
                                             <div class="form-group">
                                                  <label for="">Time line</label>
                                                  <div class="row">
                                                       <div class="col-md-4">
                                                            <div class="form-group">
                                                                 <input type="time" class="form-control"
                                                                      name="time_start">
                                                            </div>
                                                       </div>
                                                       <div class="col-md-4">
                                                            <div class="form-group">
                                                                 <input type="time" class="form-control"
                                                                      name="time_end">
                                                            </div>
                                                       </div>
                                                  </div>
                                             </div>
                                        </div>
                                        <div class="col-md-6">
                                             <div class="form-group">
                                                  <label for="">Plateau</label>
                                                  <select name="type" class="form-control" name="filmset">
                                                       <% if(request.getAttribute("plateau")!=null){
                                                            ArrayList<Filmset>filmset=(ArrayList<Filmset>)request.getAttribute("plateau");
                                                            for(Filmset f: filmset){ %>
                                                                 <option value="<% f.getId() %>">f.getName()</option>
                                                       <%     }
                                                       } %>

                                                  </select>
                                             </div>
                                        </div>
                                        <div class="col-md-6">
                                             <div class="form-group">
                                                  <label for="">Temps de tournage estime</label>
                                                  <input type="time" class="form-control" 
                                                       name="estimed_time">
                                             </div>
                                        </div>
                                        <div class="col-md-12" >
                                             <h4>Dialogue</h4>

                                             <div id="dialogue">
                                                  <div class="row custom-shadow" >
                                                       <div class="col-md-12" id="dialogue_personnage">
                                                            <label for="">Personnage</label>
                                                            <div class="form-group row">
                                                                 <select name="dialogue_personnage" class="form-control col-md-2" >
                                                                      <%
                                                                           if(request.getAttribute("liste_chara")!=null){
                                                                                ArrayList<Character>liste_chara=(ArrayList<Character>)request.getAttribute("liste_chara");
                                                                                for(Character c: liste_chara){ %>
                                                                                <option value="<%= c.getId() %>"><%= c.getName() %></option>
                                                                      <%          }
                                                                           }
                                                                      %>
                                                                 </select>
                                                                 <div class="col-md-7"></div>
                                                                 <button class="btn btn-danger col-md-3"  type="button" onClick="effacer(this)">- enlever</button>
                                                            </div>
                                                       </div>
                                                       <div class="col-md-6" id="dialogue_dialogue">
                                                            <div class="form-group">
                                                                 <label for="">Dialogue</label>
                                                                 <textarea class="form-control" placeholder="..." rows="3"
                                                                      name="dialogue_texte"></textarea>
                                                            </div>
                                                       </div>
                                                       <div class="col-md-6" id="dialogue_action">
                                                            <div class="form-group">
                                                                 <label for="">Action</label>
                                                                 <textarea class="form-control" placeholder="..." rows="3"
                                                                      name="dialogue_action"></textarea>
                                                            </div>
                                                       </div>
                                                  </div>    
                                             </div>
                                             
                                             <button class="btn btn-success" style="margin:20px" type="button" onClick="appender()">+ ajouter
                                                  dialogue</button>

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
    

<script src="${pageContext.request.contextPath}/resources/js/scene_form.js"></script>
<script type="text/javascript">
     var tempchara=<%= request.getAttribute("liste_character_json") %>;
     var liste_chara=[];
     if(tempchara!=null){
          liste_chara=JSON.stringify(chara);
     }
     function appender(){
          ajouter(liste_chara);
     }
</script>
<jsp:include page="jsp/footer.jsp" />