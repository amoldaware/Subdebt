<SCRIPT language="JavaScript" type="text/JavaScript" src="js/CGTSI.js">
</SCRIPT>
<SCRIPT language="JavaScript" type="text/JavaScript" src="js/selectdate.js">
</SCRIPT>
<LINK href="css/StyleSheet.css" rel="stylesheet" type="text/css">

<script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 
</script> 

<script src="/csrfguard"></script>

<script>
<%

if(request.getParameter("subMenu")!=null)
{
	session.setAttribute("subMenuItem",request.getParameter("subMenu"));
}

if(request.getParameter("mainMenu")!=null)
{
	if(!request.getParameter("mainMenu").equals((String)session.getAttribute("mainMenu")))
	{
		session.removeAttribute("subMenus");
		session.setAttribute("mainMenu",request.getParameter("mainMenu"));
	}
}

java.util.ArrayList mainMenusItems =(java.util.ArrayList)session.getAttribute("mainMenus");
java.util.ArrayList mainMenuValues =(java.util.ArrayList)session.getAttribute("mainMenuValues");
java.util.ArrayList subMenus =(java.util.ArrayList)session.getAttribute("subMenus");
java.util.ArrayList subMenuValues =(java.util.ArrayList)session.getAttribute("subMenuValues");
%>

<%if(subMenus!=null){ for(int ctr=0; ctr<subMenus.size(); ctr++) { %>

	subMenus[<%=ctr%>] = '<%=subMenus.get(ctr)%>';
	subMenuValues[<%=ctr%>] = '<%=subMenuValues.get(ctr)%>';
<% }} %>	

<% if(mainMenusItems!=null){for (int i=0;i<mainMenusItems.size();i++){%>
mainMenus[<%=i%>]='<%=mainMenusItems.get(i)%>';
mainMenuValues[<%=i%>]='<%=mainMenuValues.get(i)%>';
<%}}%>

selection='<%=session.getAttribute("menuIcon")%>';
mainMenuItem='<%=session.getAttribute("mainMenu")%>';
subMenuItem='<%=session.getAttribute("subMenuItem")%>';

<% if(mainMenusItems!=null){%>
setSubMenu(subMenus);
<%}%>
</script>
