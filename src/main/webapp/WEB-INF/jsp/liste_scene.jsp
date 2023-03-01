<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire" %>
<jsp:include page="jsp/header.jsp" />
     <a class="nav-link col-12" href="javascript:void(0)">
                         <form action="${pageContext.request.contextPath}/admin/recherche" method="post">
                              <div class="customize-input row">
                                   <input class="form-control custom-shadow border-0 bg-white col-5" type="search"
                                        placeholder="Search" aria-label="Search" style="display:flex" name="motcle"
                                        value="">
                                   <select class="form-control custom-shadow border-0 bg-white col-3" name="status">
                                        <option value="">Tout</option>
                                        <option value="1">Planifie</option>
                                        <option value="2">Fini</option>
                                        <option value="2">Pas encore fini</option>
                                        <option value="2">Non planifie</option>
                                   </select>
                                   <div class="col-3" style="display:flex; padding:0px 3px;" >
                                        <select class="js-example-basic-multiple form-control custom-shadow border-0 bg-white" multiple="multiple" style="width:100%"
                                             placeholder="acteur">
                                             <option value="AL">Alabama</option>
                                             <option value="WY">Wyoming</option>
                                             <option value="AM">America</option>
                                             <option value="CA">Canada</option>
                                             <option value="RU">Russia</option>
                                        </select>
                              </div>
                                  

                                   <button type="submit" style="display:flex" class="btn btn-primary col-1"><i
                                             class="form-control-icon" data-feather="search"
                                             style="display:flex">Search</i></button>
                              </div>
                         </form>
                    </a>

                    <div class="row">
                         <h2 class="mb-0">Scenes du film, </h2>
                         <div class="col-12 mt-4">
                         </div>
                         <div class="col-md-4">
                              <div class="card text-white bg-dark">
                                   <div class="card-header">
                                        <h4 class="mb-0 text-white">Card Title</h4>
                                   </div>
                                   <div class="card-body">
                                        <h3 class="card-title text-white">Special title treatment</h3>
                                        <p class="card-text">With supporting text below as a natural lead-in to
                                             additional
                                             content.</p>
                                        <a href="javascript:void(0)" class="btn btn-primary">voir</a>
                                   </div>
                              </div>
                         </div>
                         <div class="col-md-4">
                              <div class="card text-white bg-info">
                                   <div class="card-header">
                                        <h4 class="mb-0 text-white">Card Title</h4>
                                   </div>
                                   <div class="card-body">
                                        <h3 class="card-title text-white">Special title treatment</h3>
                                        <p class="card-text">With supporting text below as a natural lead-in to
                                             additional
                                             content.</p>
                                        <a href="javascript:void(0)" class="btn btn-dark">Go somewhere</a>
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