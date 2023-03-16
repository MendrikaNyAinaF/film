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
                    <div class="card col-12">
                         <div class="card-body">
                              <div class="card p-3 bg-info text-white">
                                   <p>Scene : <%= s.getTitle() %>
                                   </p>
                              </div>
                              <h4 class="card-title">Action globale</h4>
                              <p><%= s.getGlobal_action() %></p>
                         </div>
                    </div>
                    <div class="card col-12">
                         <div class="card-body">
                              <h4 class="card-title">Dialogue</h4>
                              <form action="" method="post">
                              <button type="submit" class="btn btn-primary" >Enregistrer</button>
                              <div id="dialogue">
                                   
                                   
                                        <% if(request.getAttribute("dialogue")!=null && request.getAttribute("liste_chara")!=null){
                                             List<Dialogue> dialogue=(List<Dialogue>)request.getAttribute("dialogue");
                                             List<Character>liste_chara=(List<Character>)request.getAttribute("liste_chara");
                                             for(Dialogue d: dialogue){                                    
                                        %>
                                             <input type="hidden" name="iddialogue" value="<%= d.getId() %>" id="iddialogue">
                                             <div class="col-md-12" id="dialogue_personnage">
                                                  <label for="">Personnage</label>
                                                  <div class="form-group row">
                                                                 <select name="dialogue_personnage" class="form-control col-md-2" >
                                                                 <%
                                                                      for(Character c: liste_chara){ 
                                                                           if(d.getCharacter().getId()==c.getId()){  %>
                                                                                <option value="<%= c.getId() %>" selected><%= c.getName() %></option>
                                                                 <%    }else{ %>
                                                                                <option value="<%= c.getId() %>"><%= c.getName() %></option>
                                                                 <% }      }                                           
                                                                 %>                                                                       
                                                                 </select>
                                                                 <div class="col-md-7"></div>
                                                                 <button class="btn btn-danger col-md-3"  type="button" onClick="effacer(this)">- enlever</button>
                                                  </div>
                                             </div>
                                             <div class="col-md-6" id="dialogue_dialogue">
                                                  <div class="form-group">
                                                  <label for="">Le texte</label>
                                                  <textarea class="form-control" placeholder="..." rows="3"
                                                       name="dialogue_texte"><%= d.getTexte() %></textarea>
                                             </div>   
                                             <div class="col-md-6" id="dialogue_action">
                                                  <div class="form-group">
                                                       <label for="">Action</label>
                                                       <textarea class="form-control" placeholder="..." rows="3"
                                                            name="dialogue_action"><%= d.getAction() %></textarea>
                                                  </div>
                                             </div> 
                                        <% } } %>
                                        
                                   <button class="btn btn-success" style="margin:20px" type="button" onClick="appender()">+ ajouter
                                                  dialogue</button>

                              </div>
                              </form>
                         </div>
                    </div>
<script src="${pageContext.request.contextPath}/resources/js/dialogue_form.js"></script>
<script type="text/javascript">
     var tempchara=<%= request.getAttribute("liste_character_json").toString() %>;
     var liste_chara=[];
     if(tempchara!=null){
          liste_chara=tempchara;
     }
     function appender(){
          //console.log(tempchara)
          ajouter(liste_chara);
     }
</script>
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
