<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload Multiple File Request Page</title>
</head>
<body>
<form method="POST" action="uploadMultipleFile" enctype="multipart/form-data">
        File1 to upload: <input type="file" name="file"><br /> 
        Name1: <input type="text" name="name"><br /> <br /> 
        File2 to upload: <input type="file" name="file"><br /> 
        Name2: <input type="text" name="name"><br /> <br />
        <input type="submit" value="Upload"> Press here to upload the file!
</form>

</body>
</html>