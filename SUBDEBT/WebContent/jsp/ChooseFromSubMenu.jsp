<SCRIPT language="JavaScript" type="text/JavaScript" src="js/CGTSI.js">
</SCRIPT>
<script src="/csrfguard"></script>
<% session.setAttribute("CurrentPage","subHome.do?method=getSubMenu&menuIcon="+request.getParameter("menuIcon")+"&mainMenu="+request.getParameter("mainMenu"));%>
<TABLE border="0" cellspacing="0" cellpadding="0" align="center">
	<form name="chooseFromSubMenu">
	<TR>
		<BR>
		<BR>
		<BR>
		<BR>
		<BR>
		<BR>
	</TR>
	<TR>
		<TD align="center" class="SubHeading">
			Choose from Sub Menu
		</TD>
	<TR>
	</form>
	<script>
	<%
		String menuIcon=request.getParameter("menuIcon");
		String mainMenu=request.getParameter("mainMenu");
		String subMenu=request.getParameter("subMenu");
		
		if(request.getParameter("menuIcon")!=null 
			&& !request.getParameter("menuIcon").equals("undefined"))
		{
			session.setAttribute("menuIcon",request.getParameter("menuIcon"));		
		}
		session.removeAttribute("mainMenu");	
		session.removeAttribute("subMenuItem");
		
		if(request.getParameter("mainMenu")!=null 
			&& !request.getParameter("mainMenu").equals("undefined"))
		{
			session.setAttribute("mainMenu",request.getParameter("mainMenu")); 
		}	%>
		
	/*
	alert("Menu Icon in Choose Sub menu is"+"<%=menuIcon%>");
	alert("Main Menu in Choose Sub menu is"+"<%=mainMenu%>");
	alert("Menu Icon From Session in Choose Sub menu is"+"<%=session.getAttribute("menuIcon")%>");
	alert("Menu Icon From Session in Choose Sub menu is"+"<%=session.getAttribute("mainMenu")%>");
	
	alert("Current Page is in CSM"+"<%=session.getAttribute("CurrentPage")%>");
	*/
    selection='<%=menuIcon%>';
    mainMenuItem='<%=session.getAttribute("mainMenu")%>';
    
    //<%if(subMenu!=null){%>
    //subMenuItem='<%=session.getAttribute("subMenuItem")%>';
    
    
    <%}%>
	<% 
	   java.util.ArrayList mainMenusItems =(java.util.ArrayList)session.getAttribute("mainMenus");		
	   java.util.ArrayList mainMenuValues =(java.util.ArrayList)session.getAttribute("mainMenuValues");
  	   java.util.ArrayList subMenus = (java.util.ArrayList)request.getAttribute("subMenus");
	   java.util.ArrayList subMenuValues=(java.util.ArrayList)request.getAttribute("subMenuValues");
	   
	   if(menuIcon!=null && mainMenu!=null)// menuIcon.equals("IO"))
	   {
			/*mainMenusItems.add("Select");
			mainMenusItems.add("Inward");
			mainMenusItems.add("Outward");
			mainMenusItems.add("Upload Documents");
			mainMenusItems.add("Search Documents");
			*/
			//if(mainMenu!=null)
			//{
				/*
				if(mainMenu.equals("Inward"))
				{
				   subMenus.add("Select");
				   subMenuValues.add("subHome.do");
				   subMenus.add("Add Inward");
				   subMenuValues.add("showAddInward.do");
				   subMenus.add("Show Summary");
				   subMenuValues.add("showAddInward.do");
				}
				else if(mainMenu.equals("Outward"))
				{
				   subMenus.add("Select");
				   subMenuValues.add("subHome.do");
				   subMenus.add("Add Outward");
				   subMenuValues.add("showAddInward.do");
				   subMenus.add("Show Summary");
				   subMenuValues.add("showAddInward.do");
				}
				else
				{
					subMenus.add("Select");
					subMenuValues.add("subHome.do");
				}
				*/
				//session.setAttribute("subMenus",subMenus);
				//session.setAttribute("subMenuValues",subMenuValues);
			//}
			
		session.setAttribute("subMenus",subMenus);
		session.setAttribute("subMenuValues",subMenuValues);
			
	  }
	  
		
		//java.util.ArrayList mainMenus=(java.util.ArrayList)request.getAttribute("mainMenus");
	%>
    <%for(int ctr=0; ctr<subMenus.size(); ctr++) { %>
    	
    	subMenus[<%=ctr%>] = '<%=subMenus.get(ctr)%>';
    	subMenuValues[<%=ctr%>] = '<%=subMenuValues.get(ctr)%>';
    <% } %>	
    
    <% for (int i=0;i<mainMenusItems.size();i++){%>
    mainMenus[<%=i%>]='<%=mainMenusItems.get(i)%>';
    mainMenuValues[<%=i%>]='<%=mainMenuValues.get(i)%>';
    <%}%>
    
    
    setSubMenu(subMenus);
	</script>
</TABLE>