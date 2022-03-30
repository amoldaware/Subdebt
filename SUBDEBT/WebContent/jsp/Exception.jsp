<LINK href="css/StyleSheet.css" rel="stylesheet" type="text/css">
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<html:html locale="true">
<head>
    <title><bean:message key="exception" /> </title>
</head>
<body>
<%
String currentPage=(String)session.getAttribute("CurrentPage");

String url=request.getRequestURL().toString();
/*
int index=url.lastIndexOf("/");

String newURL=url.substring(0,index);
newURL+="/"+currentPage;
*/
String newURL=request.getContextPath();

if(currentPage!=null)
{
	newURL=request.getContextPath()+"/"+currentPage;
}
else
{
	newURL=request.getContextPath();
	
}
System.out.println("newURL "+newURL);

if(newURL.endsWith("upload"))
{
	newURL="subHome.do?method=getSubMenu";
}
else if (newURL.endsWith("showSuccessPage.do"))
{
	newURL="home.do?method=getMainMenu&menuIcon="+(String)session.getAttribute("menuIcon");
}
//out.println(newURL);

%>
<form action="<%=newURL%>">

<TABLE width="755" border="0" cellpadding="1" cellspacing="1">
	<TR>
		<TD	class="SubHeading">
			Errors
		</TD>
	</TR>
	<TR>
		<TD>
			&nbsp;
		</TD>
	</TR>
	<TR>
		<TD class="Heading">
			Please correct the following errors before proceeding.
		</TD>
	</TR>
	<OL>
<%
org.apache.struts.action.ActionErrors actionErrors=null;

//out.println(request.getRequestURI());

//out.println(request.getRequestURL());

if(request.getAttribute(org.apache.struts.Globals.ERROR_KEY)!=null)
{
	actionErrors=(org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
}

org.apache.struts.action.ActionError error=null;


if(actionErrors!=null && actionErrors.size()>=1)
{
	java.util.Iterator iterator=actionErrors.get();

	while(iterator.hasNext())
	{
		error=(org.apache.struts.action.ActionError)iterator.next();

		if(error.getValues()!=null)
		{
			//out.println(error.getKey());
			int errorValues=error.getValues().length;



			for(int i=0;i<errorValues;i++)
			{
				out.println("<TR>");
				out.println("<TD class=\"ColumnBackground\">");
				out.println("<LI>"+error.getValues()[i]+"</LI>");
				out.println("</TD>");
				out.println("</TR>");

			}

		}
		else
		{
				out.println("<TR>");
				out.println("<TD class=\"ColumnBackground\">>");
				out.println("Unknown reasons");
				out.println("</TD>");
				out.println("</TR>");

		}
	}
}
else
{
	out.println("<TR>");
	out.println("<TD class=\"ColumnBackground\">>");
	out.println("I do not know why this line is executed!");
	out.println("</TD>");
	out.println("</TR>");

}
%>
</OL>
<TR>
<TD width="755" align="center" valign="top">
	<A href="<%=newURL%>"><IMG src="images/OK.gif" width="49" height="37" border="0"></A></TD>
</TR>
</TABLE>

</form>
</body>
</html:html>

