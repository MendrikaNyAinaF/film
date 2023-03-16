<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire,app.apps.model.Character" %>
<jsp:include page="header.jsp" />
    
 <div class="card">
     <div class="card-body">
          <h1 class="card-title">Ajouter un jour ferie</h1> 
          <form action="${pageContext.request.contextPath}/ferie" method="POST">
               <div class="col-md-6">
                    <div class="form-group">
                         <label for="">Event</label>
                         <input type="text" class="form-control" placeholder="evenement" name="nom" />
                    </div>
               </div>
               <div class="col-md-6">
                    <div class="form-group">
                         <label for="">Jour</label>
                         <input type="date" class="form-control" placeholder="evenement" name="date" />
                    </div>
               </div>
               <button type="submit" class="btn btn-primary" >Enregistrer</button>
          </form>
<jsp:include page="footer.jsp" />
