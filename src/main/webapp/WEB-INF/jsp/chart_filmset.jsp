<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire,app.apps.model.Character,app.apps.model.StatusPlanning" %>
<jsp:include page="header.jsp" />
<div class="row">
     <div class="col-lg-12">
          <div class="card">
               <div class="card-body">
                    <h4 class="card-title">Plateaux et nombre d'utilisation</h4>
                    <div id="morris-bar-chart"></div>
               </div>
          </div>
     </div>
     <br />
     <div class="col-lg-12">
          <div class="card">
               <div class="card-body">
                    <h4 class="card-title">Liste des plateaux</h4>
                    <div class="col-md-8">
                         <div class="table-responsive">
                              <table class="table">
                              <thead class="bg-primary text-white">
                                   <tr>
                                        <th>Plateau</th>
                                        <th>Type</th>
                                        <th>Voir planning</th>
                                   </tr>
                              </thead>
                              <tbody class="border border-primary">
                              <% if(request.getAttribute("liste_filmset")!=null){
                                   List<Filmset> liste_filmset=(List<Filmset>)request.getAttribute("liste_filmset");
                                   for(Filmset f:liste_filmset){ %>
                                        <tr>
                                             <td><%= f.getName() %></td>
                                             <td><%= f.getType().getName() %></td>
                                             <td>
                                                  <a href="${pageContext.request.contextPath}/filmset/<%= f.getId() %>/planning" class="btn btn-info" >voir</a>
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

<script src="${pageContext.request.contextPath}/resources/assets/jquery/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/libs/jquery/dist/jquery.min.js"></script>


<script src="${pageContext.request.contextPath}/resources/assets/libs/raphael/raphael.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/libs/morris.js/morris.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/dist/js/pages/morris/morris-data.js"></script>
<script type="text/javascript">
     const barchart=<%= request.getAttribute("filmset_json") %>
     window.onload = function() {
          stat_filmset(barchart);
     };

</script>
<jsp:include page="footer.jsp" />
