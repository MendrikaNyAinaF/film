<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire,app.apps.model.Character,app.apps.model.StatusPlanning" %>
<jsp:include page="header.jsp" />
<div class="card col-lg-12">
     <div class="card-body">
          <h4>Le planning commencera le </h4>
          <button class="btn btn-info">Confirmer</button>
          <br />
          <br />
          <div class="row">
               <div class="col-md-6">
                    <div class="card border-dark">
                         <div class="card-header bg-dark">
                              <div class="custom-control custom-checkbox">
                                   <h4 class="mb-0 text-white" for="customCheck1">
                                        Scene, temps
                                   </h4>
                              </div>
                         </div>
                         <div class="card-body">
                              <h3 class="card-title">Plateau: </h3>
                              <p class="card-text">Tournage:</p>
                              <div class="row">
                                   <div class="col-md-6">
                                        <div class="form-group">
                                             <input type="datetime-local" class="form-control"
                                                  placeholder="1">
                                        </div>
                                   </div>
                                   <div class="col-md-6">
                                        <div class="form-group">
                                             <input type="datetime-local" class="form-control"
                                                  placeholder="col-md-11">
                                        </div>
                                   </div>
                              </div>

                         </div>
                    </div>
               </div>
          </div>
     </div>
</div>
<jsp:include page="footer.jsp" />
