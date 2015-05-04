<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Memo</title>
</head>
<body>

<form method="POST" action="save">
<textarea name="message" rows="25" cols="100">
</textarea>
<input type="submit" value="Submit"/>
</form>

<table>
<c:forEach items="${result}" var="memo">
         <tr>
             <td width="600"><pre><c:out value="${memo.message}" /></pre></td>
         </tr>
         <tr>
         	<td>${memo.crtDate} *********************************************</td>
         </tr>
</c:forEach>
</table>

</body>
</html>