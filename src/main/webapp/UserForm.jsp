'<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="./contents/headerTags.jsp" />
        <title>Aplicação Books Store</title>
    </head>
    <body>

        <div class="container">
            <jsp:include page="contents/cabecalhoAdd.jsp" />
            <div align="left"> 
                        <form action="<%=request.getContextPath()%>/bsuser/register" method="post">
                        <table class="table table-hover">
                            <c:if test="${user != null}">
                                <input type="hidden" name="formId" value="<c:out value='${user.id}' />" />
                            </c:if>
                            <tr>
                                <th> 
                                    <span class="glyphicon glyphicon-user"></span> 
                                    Fullname: 
                                </th>
                                <td>
                                    <input type="text" name="formFullname" size="45" value="<c:out value='${user.email}' />"/>
                                </td>
                            </tr>
                            <tr>
                                <th> 
                                    <span class="glyphicon glyphicon-envelope"></span> 
                                    Email: 
                                </th>
                                <td><input type="text" name="formEmail" size="45" value="<c:out value='${user.password}'/>"/>
                                </td>
                            </tr>
                            <tr>
                                <th>
                                    <span class="glyphicon glyphicon-option-horizontal"></span>
                                    Password: 
                                </th>
                                <td>
                                    <input type="pass" name="formPassword" size="5"
                                           value="<c:out value='${user.password}' />"
                                           />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="center">
                                    <button type="submit">Enviar <span class="glyphicon glyphicon-send"></span></button>
                                </td>
                            </tr>
                        </table>
                    </form>
            </div>
            <jsp:include page="contents/rodape.jsp" />                            
        </div>

    </body>
</html>