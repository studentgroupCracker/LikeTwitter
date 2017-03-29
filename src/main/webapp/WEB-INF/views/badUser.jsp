<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="messages" />
<%@ page session="true" %>
<html>
<head>
    <title><spring:message
            code="label.badUser.title"></spring:message></title>
</head>
<body>
<h1>
    <div class="alert alert-error">
        ${param.message}
</h1>
<br>
<a href="<c:url value="/signup" />"><spring:message
        code="label.form.loginSignUp"></spring:message></a>

<c:if test="${param.expired}">
    <br>
    <h1>${label.form.resendRegistrationToken}</h1>
    <button onclick="resendToken()">
        <spring:message code="label.form.resendRegistrationToken"></spring:message>
    </button>

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script type="text/javascript">
        function resendToken(){
            $.get("<c:url value="/">
            <c:param name="token" value="${param.token}"/></c:url>", function(data){
            window.location.href =
                "<c:url value="/signin"></c:url>" + "?message=" + data.message;
        }).fail(function(data) {
            if(data.responseJSON.error.indexOf("MailError") > -1) {
                window.location.href = "<c:url value="/emailError"></c:url>";
            }
            else {
                window.location.href =
                    "<c:url value="/signin"></c:url>" + "?message=" + data.responseJSON.message;
            }
        });
        }
    </script>
</c:if>
</body>
</html>