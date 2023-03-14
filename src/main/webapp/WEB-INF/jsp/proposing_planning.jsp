<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire,app.apps.model.Character,app.apps.model.StatusPlanning" %>
<jsp:include page="header.jsp" />
<%  if(request.getSession().getAttribute("current_film")!=null){
     Film film=(Film)request.getSession().getAttribute("current_film");
%>                 
<div class="card col-lg-12">
     <div class="card-body">
          <h4>Le planning commencera le <%= request.getAttribute("start_date") %></h4>
          <div class="alert alert-danger" role="alert">
                         <strong id="erreur"></strong> 
          </div>
          <form id="confirmer_plan">
          <button class="btn btn-info" type="submit">Confirmer</button>
          <br />
          <br />
               <% if(request.getAttribute("liste_planning")!=null){
                    List<Planning> liste_planning = (List<Planning>) request.getAttribute("liste_planning");
                    for(Planning p: liste_planning){ %>
                         
                         <div class="row">
                              <div class="col-md-6">
                                   <div class="card border-dark">
                                        <div class="card-header bg-dark">
                                             <div class="custom-control custom-checkbox">
                                                  <h4 class="mb-0 text-white" for="customCheck1">
                                                       <%= p.getScene().getTitle() %>, <%= p.getScene().getEstimated_time() %>
                                                  </h4>
                                             </div>
                                        </div>
                                        <div class="card-body">
                                             <h3 class="card-title">Plateau: <%= p.getScene().getFilmset().getName() %></h3>
                                             <p class="card-text">Tournage:</p>
                                             <div class="row">
                                                  <input type="hidden" class="id" value=<%= p.getScene().getId() %>>
                                                  <div class="col-md-6">
                                                       <div class="form-group">
                                                            <input type="datetime-local" class="date_debut" value="<%= p.getDate_debut().toString() %>">
                                                       </div>
                                                  </div>
                                                  <div class="col-md-6">
                                                       <div class="form-group">
                                                            <input type="datetime-local" class="date_fin" value="<%= p.getDate_fin().toString() %>">
                                                       </div>
                                                  </div>
                                             </div>
                                        </div>
                                   </div>
                              </div>
                         </div>
                         
               <%     }
          } %>
                    
        </form>  
     </div>
</div>
<script type="text/javascript">
     const nbr_scene = <%= request.getAttribute("nbr_scene") %>;
     const urlredirect = <%= request.getContextPath()+"/film/"+film.getId()+"/planning" %>;
     const url = <%= request.getContextPath()+"/film/"+film.getId()+"/confirmer_planning" %>;
     const formulaire = document.getElementById('confirmer_plan');

     formulaire.addEventListener('submit', function(event) {
          event.preventDefault(); // Empêche le formulaire d'être soumis
          const date_debut = Array.from(document.getByElementsByClassName("date_debut")).map(option => option.value);
          const date_fin= Array.from(document.getByElementsByClassName("date_fin")).map(option => option.value);
          const id= Array.from(document.getByElementsByClassName("id")).map(option => option.value);
          
          const data=[]
          for(let i=0;i<nbr_scene;i++){
               data.push({
                    id: id[i],
                    date_debut: date_debut[i],
                    date_fin: date_fin[i]
               })
          }
          $.ajax({
               url: url,
               type: "POST",
               data: data,
               success: function(response) {
                    window.location.href = "https://example.com";
               },
               error: function(xhr, status, error) {
                    document.getElementById("erreur").html=xhr.responseText;
               }
          });
     });
</script>
<% }else {
     out.println("pas encore de film");
} %>
<jsp:include page="footer.jsp" />
