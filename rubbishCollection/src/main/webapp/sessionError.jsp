<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="utf-8"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>sessionError</title>
    <script type="text/javascript">
        function topWin(){
            var top, eWindow = window;
            try {
                while(eWindow.location.host == eWindow.parent.location.host && eWindow.location.href != eWindow.parent.location.href){
                    eWindow = eWindow.parent;
                }
                top = eWindow;
            } catch(e) {
                top = eWindow;
            }
            top.location.href = "" == "${pageContext.request.contextPath}" ? "/" : "${pageContext.request.contextPath}";
            
        }
    </script>
</head>
<body onload="topWin()"></body>
</html>