<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@page import="java.util.List,app.apps.model.*, app.apps.service.Utilitaire" %>
<jsp:include page="jsp/header.jsp" />
<%
    if(film!=null){ %>
    <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                         
                            <div class="" >
                                <div class="row">                                  
                                    <div class="col-lg-12">
                                        <div class="card-body b-l calender-sidebar">
                                             <h1>Planning</h1>
                                             <a class="btn btn-primary col-md-2" href="#">planifier</a>
                                            <div id="calendar"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
    <script type="text/javascript">
        $.CalendarApp.init(<%= film.getId() %>);
    </script>
<%   }
%>
     

<jsp:include page="jsp/footer.jsp" />