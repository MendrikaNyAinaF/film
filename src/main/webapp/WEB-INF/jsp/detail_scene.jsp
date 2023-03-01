<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire" %>
<jsp:include page="jsp/header.jsp" />
     <h1>Film, rose</h1>
               <div class="row">
                    <div class="card col-6">
                         <div class="card-body">
                              <div class="card p-3 bg-info text-white">
                                   <p>Scene : titre
                                   </p>
                              </div>
                              <h4 class="card-title">Action globale</h4>
                              <p>Lorem ipsum</p>

                              <h4 class="card-title">Time line</h4>
                              <p>Lorem ipsum</p>

                              <h4 class="card-title">Temp estime tournage</h4>
                              <p>Lorem ipsum</p>

                              <h4 class="card-title">Plateau</h4>
                              <p>Lorem ipsum</p>
                         </div>
                    </div>
                    <div class="card col-6">
                         <div class="card-body">
                              <h4 class="card-title">Dialogue</h4>
                              <ul>
                                   <li><span class="text-primary">Perso name: </span>Je parle <cite
                                             title="Source Title">()</cite></li>
                              </ul>
                         </div>
                    </div>

                    <div class="card col-12">
                         <div class="card-body">
                              <h3 class="text-primary">Planifier</h3>
                              <form action="${pageContext.request.contextPath}/author/formulaire" method="POST">
                                   <div class="form-body">
                                        <div class="row">
                                             <div class="col-md-4">
                                                  <div class="form-group">
                                                       <input type="text" class="form-control" placeholder="titre"
                                                            name="titre">
                                                  </div>
                                             </div>
                                             <div class="col-md-4">
                                                  <button class="btn btn-primary">Planifier</button>
                                             </div>
                                             <div class="col-md-4">
                                                  <button class="btn btn-success">Planifier auto</button>
                                             </div>
                                        </div>
                                   </div>

                              </form>
                              <br/>
                              <h3 class="text-primary">Changer status</h3>
                              <form action="${pageContext.request.contextPath}/author/formulaire" method="POST">
                                   <div class="form-body">
                                        <div class="row">
                                             <div class="col-md-4">
                                                  <div class="form-group">
                                                       <select name="type" class="form-control" id="idOption">
                                                            <option value="plateau1">plateau1</option>
                                                            <option value="plateau1">plateau2</option>    
                                                       </select>
                                                  </div>
                                             </div>
                                             <div class="col-md-4">
                                                  <button class="btn btn-primary">changer</button>
                                             </div>
                                        </div>
                                   </div>
                              </form>
                         </div>
                    </div>

               </div>
<jsp:include page="jsp/footer.jsp" />