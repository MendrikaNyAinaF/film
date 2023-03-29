<%@ page contentType="text/html;charset=UTF-8" language="java" %>
     <%@page
          import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire,app.apps.model.Character,app.apps.model.StatusPlanning"
          %>
          <jsp:include page="header.jsp" />
          <% if(request.getSession().getAttribute("current_film")!=null){ Film
               film=(Film)request.getSession().getAttribute("current_film"); %>
               <div class="card col-lg-12">
                    <div class="card-body">
                         <h4>Le planning commencera le <%= request.getAttribute("start_date") %>
                         </h4>
                         <div id="div-erreur" class="alert alert-danger" role="alert" style="display: none;">
                              <strong id="erreur"></strong>
                         </div>
                         <form id="confirmer_plan">
                              <button class="btn btn-info" type="submit">Confirmer</button>
                              <br />
                              <br />

                              <% if(request.getAttribute("liste_date_planning")!=null){ List<DatePlanning>
                                   liste_date_planning = (List<DatePlanning>)
                                        request.getAttribute("liste_date_planning");
                                        for(DatePlanning dp : liste_date_planning){
                                        %>

                                        <h3>Jour de tournage: <%= dp.getJour_tournage() %>
                                        </h3>
                                        <% for(Filmset f : dp.getList_plateau()){ %>
                                             <h4>Plateau: <%= f.getName() %>
                                             </h4>
                                             <div class="row">
                                                  <% for(Planning p : f.getList_planning()){ %>
                                                       <div class="col-md-6">
                                                            <div class="card border-dark">
                                                                 <div class="card-header bg-dark">
                                                                      <div class="custom-control custom-checkbox">
                                                                           <input type="checkbox"
                                                                                class="custom-control-input valide"
                                                                                id="customChecker<%= p.getScene().getId() %>"
                                                                                name="idscene" value=<%= p.getScene().getId() %> checked
                                                                                  />
                                                                           <label
                                                                                class="mb-0 text-white custom-control-label"
                                                                                for="customChecker<%= p.getScene().getId() %>">
                                                                                Plateau: <%= p.getScene().getFilmset().getName()
                                                                                %>
                                                                           </label>
                                                                      </div>
                                                                 </div>
                                                                 <div class="card-body">
                                                                      <h3 class="card-title">
                                                                           <%= p.getScene().getTitle() %>, <%=
                                                                                     p.getScene().getEstimated_time() %>
                                                                      </h3>
                                                                      <p class="card-text">Tournage:</p>
                                                                      <div class="row">
                                                                           <input type="hidden" class="id"
                                                                                value=<%=p.getScene().getId() %>>
                                                                           <div class="col-md-6">
                                                                                <div class="form-group">
                                                                                     <input type="datetime-local"
                                                                                          class="form-control date_debut"
                                                                                          value="<%= p.getDate_debut().toString() %>">
                                                                                </div>
                                                                           </div>
                                                                           <div class="col-md-6">
                                                                                <div class="form-group">
                                                                                     <input type="datetime-local"
                                                                                          class="form-control date_fin"
                                                                                          value="<%= p.getDate_fin().toString() %>">
                                                                                </div>
                                                                           </div>
                                                                      </div>
                                                                 </div>
                                                            </div>
                                                       </div>
                                                       <% } %>
                                             </div>
                                             <% } %>
                                                  <br />
                                                  <% } } %>

                         </form>
                    </div>
               </div>
               <script type="text/javascript">
                    const nbr_scene = <%= request.getAttribute("nbr_scene") %>;
                    const urlredirect = '<%= request.getContextPath()+"/film/"+film.getId()+"/planning" %>';
                    const url = '<%= request.getContextPath()+"/film/"+film.getId()+"/confirmer_planning" %>';
                    const formulaire = document.getElementById('confirmer_plan');

                    formulaire.addEventListener('submit', function (event) {
                         event.preventDefault(); // Empêche le formulaire d'être soumis
                         const date_debut = Array.from(document.getElementsByClassName("date_debut")).map(option => option.value);
                         const date_fin = Array.from(document.getElementsByClassName("date_fin")).map(option => option.value);
                         const id = Array.from(document.getElementsByClassName("id")).map(option => option.value);
                         const valide = Array.from(document.getElementsByClassName("valide"));

                         //alert("Submit pressed");
                         const data = []
                         for (let i = 0; i < id.length; i++) {
                              if (valide[i].checked) {
                                   data.push({
                                        "scene": { "id": parseInt(id[i]) },
                                        "date_debut": new Date(date_debut[i]),
                                        "date_fin": new Date(date_fin[i])
                                   })
                              }

                         }
                         for (let i = 0; i < id.length; i++) {
                              console.log(data[i]);
                         }
                         $.ajax({
                              url: url,
                              type: 'POST',
                              data: JSON.stringify(data),
                              dataType: 'json',
                              contentType: 'application/json',
                              success: function (response) {
                                   //console.log(response);
                                   window.location.href = urlredirect;
                              },
                              error: function (xhr, status, error) {
                                   console.log(xhr);
                                   if (xhr.status == 200 || xhr.status == "200") {
                                        window.location.href = urlredirect;
                                   }
                                   else {
                                        console.log(xhr.responseText);
                                        document.getElementById("erreur").innerHTML = xhr.responseText;
                                        document.getElementById("div-erreur").style.display = "block";
                                   }
                              }
                         });
                    });
               </script>
               <% }else { out.println("pas encore de film"); } %>
                    <jsp:include page="footer.jsp" />
