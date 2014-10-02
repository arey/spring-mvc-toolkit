<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="jem" uri="http://javaetmoi.com/core/spring-mvc"%>
<jsp:directive.page contentType="text/html;charset=ISO-8859-15" />

<html xmlns:jsp="http://java.sun.com/JSP/Page">
<head>
<title>@SessionAttributes page</title>
<body>

<spring:url value="/dosomething" var="url1" />
<a href=${url1}>/dosomething</a><br:>
<spring:url value="/other" var="url2" />
<a href=${url2}>/other</a><br:>
<spring:url value="/endsession" var="url3" />
<a href=${url3}>/endsession</a><br:>

<h3>Request Scope (key==values)</h3>
<%
    java.util.Enumeration<String> reqEnum = request.getAttributeNames();
    while (reqEnum.hasMoreElements()) {
        String s = reqEnum.nextElement();
        Object val = request.getAttribute(s);
        if (val instanceof com.javaetmoi.core.mvc.demo.model.MyBean || val instanceof com.javaetmoi.core.mvc.demo.model.MyOtherBean) {
	        out.print(s);
	        out.println("==" + val);
	        %><br />
	        <%	        
        }
    }
%>

<h3>Session Scope (key==values)</h3>
<%
  java.util.Enumeration<String> sessEnum = request.getSession()
    .getAttributeNames();
  while (sessEnum.hasMoreElements()) {
    String s = sessEnum.nextElement();
    Object val = request.getSession().getAttribute(s);
    if (val instanceof com.javaetmoi.core.mvc.demo.model.MyBean || val instanceof com.javaetmoi.core.mvc.demo.model.MyOtherBean) {
        out.print(s);
        out.println("==" + val);
        %><br />
        <%        
    }
  }
%>


</body>
</html>
