<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire,app.apps.model.Character,app.apps.model.StatusPlanning" %>
<jsp:include page="header.jsp" />
<div class="col-lg-12">
     <div class="card">
          <div class="card-body">
               <h4 class="card-title">Bar Chart</h4>
               <div id="morris-bar-chart"></div>
          </div>
     </div>
     <br />
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
                         <tr>
                              <td>1</td>
                              <td>Nigam</td>
                              <td>
                                   <a href="#" class="btn btn-info" >voir</a>
                              </td>
                         </tr>
                    </tbody>
                    </table>
               </div>
               </div>
          </div>
     </div>
</div>
<jsp:include page="footer.jsp" />
