<SCRIPT language="JavaScript" type="text/JavaScript" src="js/CGTSI.js">
</SCRIPT>
<script src="/csrfguard"></script>
<% session.setAttribute("CurrentPage","home.do?method=getMainMenu&menuIcon="+request.getParameter("menuIcon")+"&mainMenu="+request.getParameter("mainMenu"));%>
<TABLE border="0" align="center">
<form name="ChooseMenu">
<TR>
	<BR>
	<BR>
	<BR>
	<BR>
	<BR>
	<BR>
</TR>
<TR>
	<TD align="center" class="SubHeading">Choose from Menu</TD>
<TR>
</form>
<script>
	<% 
		
		String menuIcon=request.getParameter("menuIcon"); 
		String mainMenu=request.getParameter("mainMenu");
		String subMenu=request.getParameter("subMenu");
		
		session.removeAttribute("mainMenu");
		
		if(request.getParameter("menuIcon")!=null 
			&& !request.getParameter("menuIcon").equals("undefined"))
		{
			session.setAttribute("menuIcon",request.getParameter("menuIcon"));		
		}
		if(request.getParameter("mainMenu")!=null 
			&& !request.getParameter("mainMenu").equals("undefined"))
		{
			session.setAttribute("mainMenu",request.getParameter("mainMenu")); 
			//session.setAttribute("subMenuItem",null)
		}
		session.removeAttribute("subMenuItem");
		session.removeAttribute("subMenus");
		/*
		%>alert("menu Icon in Choose menu is <%=menuIcon%>");<%
		%>alert("mainMenu in Choose menu is <%=mainMenu%>");<%
		%>alert("Current Page is in CM"+"<%=session.getAttribute("CurrentPage")%>");<%
		//*/
		java.util.ArrayList mainMenus=(java.util.ArrayList)request.getAttribute("mainMenus");
		java.util.ArrayList mainMenuValues=(java.util.ArrayList)request.getAttribute("mainMenuValues");
		
		session.setAttribute("mainMenus",mainMenus);

		session.setAttribute("mainMenuValues",mainMenuValues);
		
	%>
    <%for(int ctr=0; ctr<mainMenus.size(); ctr++) { %>
    	
    	mainMenus[<%=ctr%>] = '<%=mainMenus.get(ctr)%>';
    	mainMenuValues[<%=ctr%>] = '<%=mainMenuValues.get(ctr)%>';
    	
    <% } %>
    selection='<%=menuIcon%>';
    /*
    <% if(mainMenu!=null && !mainMenu.equals("undefined")){%>
    mainMenuItem='<%=mainMenu%>';
    <%}%>
    subMenuItem='<%=subMenu%>';
    */
    /*
    for(ctr =0; ctr < mainMenus.length; ctr++) {
    	alert(mainMenus[ctr]);
    }	
    */
	
	setMainMenu(mainMenus);
    
</script>
</TABLE>

