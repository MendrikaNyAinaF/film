<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire" %>
<jsp:include page="jsp/header.jsp" />

                    <a class="nav-link col-4" href="javascript:void(0)">
                         <form>
                              <div class="customize-input row">
                                   <input class="form-control custom-shadow border-0 bg-white col-9" type="search"
                                        placeholder="Search" aria-label="Search" style="display:flex">
                                   <button type="submit" style="display:flex" class="btn btn-primary col-2"><i
                                             class="form-control-icon" data-feather="search"
                                             style="display:flex">Search</i></button>
                              </div>
                         </form>
                    </a>
                    
                    <div class="row">
                         <h1 class="mb-0">Mes films</h1>
                         <div class="col-12 mt-4">                          
                              <div class="card-deck">
                                   <div class="card">
                                        <img class="card-img-top img-fluid" src="assets/images/big/img1.jpg"
                                             alt="Card image cap">
                                        <div class="card-body">
                                             <h4 class="card-title">Card title</h4>
                                             <p class="card-text">This is a wider card with supporting text below as a
                                                  natural
                                                  lead-in to additional content. This content is a little bit longer.
                                             </p>
                                             <p class="card-text"><small class="text-muted">Last updated 3 mins
                                                       ago</small></p>
                                        </div>
                                   </div>
                                   <div class="card">
                                        <img class="card-img-top img-fluid" src="assets/images/big/img2.jpg"
                                             alt="Card image cap">
                                        <div class="card-body">
                                             <h4 class="card-title">Card title</h4>
                                             <p class="card-text">This card has supporting text below as a natural.</p>
                                             <p class="card-text"><small class="text-muted">Last updated 3 mins
                                                       ago</small></p>
                                        </div>
                                   </div>
                                   <div class="card">
                                        <img class="card-img-top img-fluid" src="assets/images/big/img3.jpg"
                                             alt="Card image cap">
                                        <div class="card-body">
                                             <h4 class="card-title">Card title</h4>
                                             <p class="card-text">This is a wider card with supporting text below as a
                                                  natural
                                                  lead-in to additional content. This card has even longer content than
                                                  the first
                                                  to show that equal height action. supporting text below as a natural
                                                  lead-in to
                                                  additional content</p>
                                             <p class="card-text"><small class="text-muted">Last updated 3 mins
                                                       ago</small></p>
                                             <div class="row">
                                                  <div class="col-6">
                                                       <a href="javascript:void(0)" class="btn btn-primary">Choisir</a>
                                                  </div>
                                                  <div class="col-6">
                                                       <a href="javascript:void(0)" class="btn btn-secondary">voir
                                                            scene</a>
                                                  </div>
                                             </div>

                                        </div>
                                   </div>
                              </div>
                         </div>
                    </div>

                    <div class="row" style="margin:50px 100px">
                         <ul class="pagination">
                              <li class="page-item disabled">
                                   <a class="page-link" href="#" tabindex="-1">Previous</a>
                              </li>
                              <li class="page-item">
                                   <a class="page-link" href="#">Next</a>
                              </li>
                         </ul>
                    </div>
              
<jsp:include page="jsp/footer.jsp" />
