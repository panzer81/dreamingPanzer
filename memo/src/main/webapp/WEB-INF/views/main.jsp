<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Memo</title>
<script src="http://code.jquery.com/jquery-1.7.js" type="text/javascript"></script>
<script type="text/javascript">
	Date.prototype.format = function(f) {
	    if (!this.valueOf()) return " ";
	 
	    var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
	    var d = this;
	     
	    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
	        switch ($1) {
	            case "yyyy": return d.getFullYear();
	            case "yy": return (d.getFullYear() % 1000).zf(2);
	            case "MM": return (d.getMonth() + 1).zf(2);
	            case "dd": return d.getDate().zf(2);
	            case "E": return weekName[d.getDay()];
	            case "HH": return d.getHours().zf(2);
	            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
	            case "mm": return d.getMinutes().zf(2);
	            case "ss": return d.getSeconds().zf(2);
	            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
	            default: return $1;
	        }
	    });
	};
	 
	String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
	String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
	Number.prototype.zf = function(len){return this.toString().zf(len);};

	
	function search(){
		
		var query = "q="+encodeURI($('#q').val())+"&start=0&rows=2147483647&wt=json&defType=edismax&qf=message&lowercaseOperators=true";
			
		$.ajax({
			url : "/solr/memo/select",
			type : "GET",
			data : query,
			dataType : "json",
			success : function(data) {
				
					var docs = data.response.docs;
				
					if(docs){
						$('#contents').html("");
						var content="";
						//console.log(JSON.stringify(docs));
						for(var i in docs){
							
							content+='<tr>'
						        +'<td width="600"><pre>'+escapeXml(docs[i].message)+'</pre></td>'
						        +'</tr>'
						        +'<tr>'
						        +'<td>'+new Date(docs[i].crtdate).format("yyyy-MM-dd HH:mm:ss")+' *********************************************</td>'
						        +'</tr>';
						}
						//console.log(content);
						$('#contents').html(content);
					}
					
				},
				
			error : function(request) {
			
				alert("실패");
			
				
			}
		});

		}
	
	function deltaImport(){
		
		var query = "command=delta-import&wt=json";
			
		$.ajax({
			url : "/solr/memo/dataimport",
			type : "GET",
			data : query,
			dataType : "json",
			success : function(data) {
					alert("성공");
				},
			error : function(request) {
				alert("실패");
				
			}
		});

		}
	
	
	function escapeXml(unsafe) {
	    return unsafe.replace(/[<>&'"]/g, function (c) {
	        switch (c) {
	            case '<': return '&lt;';
	            case '>': return '&gt;';
	            case '&': return '&amp;';
	            case '\'': return '&apos;';
	            case '"': return '&quot;';
	        }
	    });
	}
	
	</script>

</head>
<body>

<input type="text" id="q" size="20"/>
<input type="button" name="send" value="검색" onclick="search();"/>

<br/><br/>

<form method="POST" action="save">
<textarea name="message" rows="25" cols="100">
</textarea>
<input type="submit" value="Submit"/>
<input type="button" name="send" value="deltaImport" onclick="deltaImport();"/>
</form>


<table id="contents">
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