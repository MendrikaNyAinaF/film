<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire,app.apps.model.Character,app.apps.model.StatusPlanning" %>
<jsp:include page="header.jsp" />
     <div class="card col-lg-12">
          <div class="card-body">
               <form class="mt-4">
                    <div class="form-check col-lg-5 inline form-check-inline">
                         <label class="form-check-label" for="">Date de commencement</label>
                         <input type="datetime-local" class="form-check-input form-control"
                              value="2008-05-13T22:33:00">
                    </div>
               </form>
               <br />
               <br />
               <div class="row">
                    <div class="col-md-10">
                         <h4>Choisir les scènes</h4>
                    </div>
                    <div class="col-md-2">
                         <button class="btn btn-info">Proposer</button>
                    </div>
               </div>

               <br />
               <div class="row">
                    <div class="col-md-4">
                         <div class="card border-dark">
                              <div class="card-header bg-dark">
                                   <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input"
                                             id="customCheck1" />
                                        <label class="mb-0 text-white custom-control-label"
                                             for="customCheck1">
                                             Scene
                                        </label>
                                   </div>
                              </div>
                              <div class="card-body">
                                   <h3 class="card-title">Plateau: </h3>
                                   <p class="card-text">Temps estime de , avec une preference pour</p>
                              </div>
                         </div>
                    </div>
               </div>
          </div>
     </div>
<jsp:include page="footer.jsp" />
