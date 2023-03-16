<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire,app.apps.model.Character,app.apps.model.StatusPlanning" %>
<jsp:include page="header.jsp" />
<div class="row">
     <div class="col-lg-12">
          <div class="card">
               <div class="card-body">
                    <h4 class="card-title">Liste des acteurs</h4>
                    <div class="col-md-8">
                         <div class="table-responsive">
                              <table class="table">
                              <thead class="bg-primary text-white">
                                   <tr>
                                        <th>Acteurs</th>
                                        <th>Contact</th>
                                        <th>Genre</th>
                                        <th>Voir planning</th>
                                   </tr>
                              </thead>
                              <tbody class="border border-primary">
                              <% if(request.getAttribute("liste_actor")!=null){
                                   List<Actor> liste_actor=(List<Actor>)request.getAttribute("liste_actor");
                                   for(Actor f:liste_actor){ %>
                                        <tr>
                                             <td><%= f.getName() %></td>
                                             <td><%= f.getContact() %></td>
                                             <td><%= f.getGender().getName() %></td>
                                             <td>
                                                  <a href="${pageContext.request.contextPath}/actor/<%= f.getId() %>/planning" class="btn btn-info" >voir</a>
                                             </td>
                                        </tr>
                              <%     }
                              } %>
                                   
                              </tbody>
                              </table>
                         </div>
                    </div>
               </div>
          </div>
     </div>
</div>

<jsp:include page="footer.jsp" />
