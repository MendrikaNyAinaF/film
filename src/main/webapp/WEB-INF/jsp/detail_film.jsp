<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire" %>
<jsp:include page="jsp/header.jsp" />
<div class="row">
                    <div class="card col-6">
                         <h1 class="text-primary">Titre</h1>
                         <img class="card-img-top img-fluid" src="${pageContext.request.contextPath}/resources/assets/images/big/img1.jpg" alt="Card image cap">
                    </div>
                    <div class="card col-6">
                         <div class="card-body">
                              <p><span class="card-title text-primary">Description : </span>
                                   Lorem ipsum
                              </p>
                              <p><span class="card-title text-primary">Durée : </span>
                                   Lorem ipsum
                              </p>
                              <p><span class="card-title text-primary">Debut tournage : </span>
                                   Lorem ipsum
                              </p>

                              <p><span class="card-title text-primary">Equipe : </span>
                                   Lorem ipsum
                              </p>
                         </div>
                    </div>
</div>
<jsp:include page="jsp/footer.jsp" />