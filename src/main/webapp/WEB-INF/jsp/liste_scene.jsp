<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire" %>
<jsp:include page="jsp/header.jsp" />
<%
     if(film!=null){ %>
          <a class="nav-link col-12" href="javascript:void(0)">
                         <form action="${pageContext.request.contextPath}/films/<%= film.getId() %>/scenes" method="post">
                              <div class="customize-input row">
                                   <input class="form-control custom-shadow border-0 bg-white col-5" type="search"
                                        placeholder="Search" aria-label="Search" style="display:flex" name="motcle"
                                        value="<%
                                             if(request.getSession().getAttribute("scene_motcle")!=null){
                                                  out.print(request.getSession().getAttribute("scene_motcle"));
                                             }
                                        %>">
                                   <select class="form-control custom-shadow border-0 bg-white col-3" name="status">
                                        <option value="">Tout</option>
                                        <% if(request.getAttribute("status")!=null){
                                             List<StatusPlanning>liste_status=(List<StatusPlanning>)request.getAttribute("status");
                                             for(StatusPlanning s: liste_status){ %>
                                                  <option value="<%= s.getId() %>">s.getName()</option>
                                        <%     }
                                        } %>
                                   </select>
                                   <div class="col-3" style="display:flex; padding:0px 3px;" >
                                        <select class="js-example-basic-multiple form-control custom-shadow border-0 bg-white" multiple="multiple" style="width:100%"
                                             placeholder="acteur">
                                             <% if(request.getAttribute("character")!=null){
                                                  List<Character> liste_chara=(List<Character>)request.getAttribute("liste_chara");
                                                  for(Character c: liste_chara){ %>
                                                       <option value="<%= c.getId() %>"><%= c.getName() %></option>
                                             <%     }
                                             } %>
                                        </select>
                              </div>
                                  

                                   <button type="submit" style="display:flex" class="btn btn-primary col-1"><i
                                             class="form-control-icon" data-feather="search"
                                             style="display:flex">Search</i></button>
                              </div>
                         </form>
                    </a>

                    <div class="row">
                         <h2 class="mb-0">Scenes du film, '<%= film.getTitle() %>'</h2>
                         <% if(request.getAttribute("liste_scene")!=null){
                              List<Scene>liste=(List<Scene>)request.getAttribute("liste_scene");
                              for(Scene s: liste){ %>
                         <div class="col-md-4">
                              <div class="card text-white bg-info">
                                   <div class="card-header">
                                        <h4 class="mb-0 text-white">status: <%= "s" %></h4>
                                   </div>
                                   <div class="card-body">
                                        <h3 class="card-title text-white"><%= s.getTitle() %></h3>
                                        <p class="card-text">Timeline: <%= s.getTime_start() %> - <%= s.getTime_start() %> </p>
                                        <a href="${pageContext.request.contextPath}/film/<%= film.getId() %>/scene/<%= s.getId() %>" class="btn btn-dark">Voir</a>
                                   </div>
                              </div>
                         </div>
                         <%     }
                         } %>
                         
                    </div>

                     <div class="row" style="margin:50px 100px">
                         <ul class="pagination">
                              <li class="page-item">
                                   <a class="page-link" href="${pageContext.request.contextPath}/film/<%
                                        out.print(film.getId());
                                        if((Integer)request.getAttribute("page")==0){
                                             out.print("/scenes/"+((Integer)request.getAttribute("page")));
                                        }else{
                                             out.print("/scenes/"+((Integer)request.getAttribute("page")-1));
                                        }
                                   %>" tabindex="-1">Previous</a>
                              </li>
                              <li class="page-item">
                                   <a class="page-link" href="${pageContext.request.contextPath}/films/<%
                                        out.print(film.getId());
                                        if( (boolean)request.getAttribute("endPage") ){
                                             out.print("/scenes/"+((Integer)request.getAttribute("page")));
                                        }else{
                                             out.print("/scenes/"+((Integer)request.getAttribute("page")+1));
                                        }
                                   %>">Next</a>
                              </li>
                         </ul>
                    </div>
<%     }else{ %>
     <div class="alert alert-danger" role="alert">
          <strong>Choisissez un film!, </strong> 
     </div>
<% }
%>
     
<jsp:include page="jsp/footer.jsp" />