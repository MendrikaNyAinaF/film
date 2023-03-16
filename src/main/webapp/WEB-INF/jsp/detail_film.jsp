<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire,app.apps.model.Character" %>
<jsp:include page="header.jsp" />
<div class="row">
<% 
     if(request.getAttribute("film_detail")!=null){
          Film f=(Film)request.getAttribute("film_detail");%>

                    <div class="card col-6">
                         <h1 class="text-primary"><%= f.getTitle() %></h1>
                         <img class="card-img-top img-fluid" src="data:image/jpeg;base64,<%= f.getVisuel() %>" alt="Card image cap">
                    </div>
                    <div class="card col-6">
                         <div class="card-body">
                              <p><span class="card-title text-primary">Description :</span> <%= f.getDescription() %>
                              </p>
                              <p><span class="card-title text-primary">Durée : </span><%= f.getDuration() %>
                              </p>
                              </p>
                              <p><span class="card-title text-primary">Personnage :</span></p>
                              <ul>
                                   <% if( request.getAttribute("character_list")!=null){
                                        List<Character>liste=(List<Character>)request.getAttribute("character_list");
                                        for(Character c: liste){  %>
                                             <li><%= c.getName()+": "+c.getActor().getName() %></li>
                                   <%    }
                                   } %>
                              </ul>
                         </div>
                    </div>
</div>
<%      }else{ %>
     <div class="alert alert-danger" role="alert">
          <strong>Film non defini </strong> 
     </div>
<% }
%>
<jsp:include page="footer.jsp" />
