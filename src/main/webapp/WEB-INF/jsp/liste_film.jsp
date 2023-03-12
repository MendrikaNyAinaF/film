<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire,app.apps.model.Character" %>
<jsp:include page="header.jsp" />

                                        <a class="nav-link col-4" href="javascript:void(0)">
                         <form method="POST" action="${pageContext.request.contextPath}/search_film">
                              <div class="customize-input row">
                                   <input class="form-control custom-shadow border-0 bg-white col-9" type="search"
                                        placeholder="Search" aria-label="Search" style="display:flex" name="motcle" value="<%
                              if(request.getSession().getAttribute("film_motcle")!=null){
                                   out.print(request.getSession().getAttribute("film_motcle"));
                              }
                         %>">
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
                                   <% 
                                        if(request.getAttribute("liste_film")!=null){
                                             List<Film> liste=(List<Film>)request.getAttribute("liste_film");
                                             for(Film f:liste){  %>
                                        <div class="card col-md-4 col-lg-4 col-xs-12" >
                                             <img class="card-img-top img-fluid" src="data:image/jpeg;base64,<%= f.getVisuel() %>"
                                                  alt="Card image cap">
                                             <div class="card-body">
                                                  <h4 class="card-title"><%= f.getTitle() %></h4>
                                                  <p class="card-text"><%= f.getDescription() %></p>
                                                  <p class="card-text"><small class="text-muted">Durée: <%= f.getDuration() %>
                                                            </small></p>
                                                  <div class="row">
                                                       <div class="col-md-4 col-lg-4 col-xs-12">
                                                            <a href="${pageContext.request.contextPath}/film/<%= f.getId() %>/current" class="btn btn-primary">Choisir</a>
                                                       </div>
                                                       <div class="col-md-4 col-lg-4 col-xs-12">
                                                            <a href="${pageContext.request.contextPath}/film/<%= f.getId() %>" class="btn btn-secondary">Voir
                                                                 Detail</a>
                                                       </div>
                                                       <div class="col-md-4 col-lg-4 col-xs-12">
                                                            <a href="${pageContext.request.contextPath}/film/<%= f.getId() %>/scenes/0" class="btn btn-info">Voir
                                                                 scene</a>
                                                       </div>
                                                  </div>

                                             </div>
                                        </div>          
                                   <%          }
                                        }
                                   %>
                              </div>
                         </div>
                    </div>

                    <div class="row" style="margin:50px 100px">
                         <ul class="pagination">
                              <li class="page-item">
                                   <a class="page-link" href="${pageContext.request.contextPath}/films/<%
                                        if((Integer)request.getAttribute("page")==0){
                                             out.print(((Integer)request.getAttribute("page")));
                                        }else{
                                             out.print(((Integer)request.getAttribute("page")-1));
                                        }
                                   %>" tabindex="-1">Previous</a>
                              </li>
                              <li class="page-item">
                                   <a class="page-link" href="${pageContext.request.contextPath}/films/<%
                                        if( (boolean)request.getAttribute("endPage") ){
                                             out.print(((Integer)request.getAttribute("page")));
                                        }else{
                                             out.print(((Integer)request.getAttribute("page")+1));
                                        }
                                   %>">Next</a>
                              </li>
                         </ul>
                    </div>
<jsp:include page="footer.jsp" />
