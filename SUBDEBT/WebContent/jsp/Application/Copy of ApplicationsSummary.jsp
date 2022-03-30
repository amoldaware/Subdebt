<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.FileOutputStream"%>
<%@ page import="java.io.IOException"%>
<%@ page import="org.apache.poi.hpsf.HPSFException"%>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFCell"%>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFCellStyle"%>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFRow"%>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFSheet"%>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map.Entry"%>
<%

 
HashMap<String,HashMap<Integer,String>> map =null;

if(request.getAttribute("FinalResultRedirectToJSP")!=null)
{
	
	 map =  (HashMap<String,HashMap<Integer,String>>)request.getAttribute("FinalResultRedirectToJSP");
}
else
{
	System.out.println("request.getAttribute(FinalResultRedirectToJSP)!=null");
}

%>

<HTML>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<BODY>

<%		
String exportToExcel = request.getParameter("exportToExcel");  
if (exportToExcel != null && exportToExcel.toString().equalsIgnoreCase("YES")) 
{        	
	response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-Disposition", "inline; filename=" + "excel.xls"); 
}
%> 


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:form action="uploadGuaranteeApp.do?method=uploadGuaranteeApp"
		method="POST" enctype="multipart/form-data">

		<html:errors />
		<TR>
			<TD width="20" align="right" valign="bottom"><IMG
				src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG
				src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<TABLE width="100%" border="0" align="left" cellpadding="0"
				cellspacing="0">
				<TR>
					<TD>
					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<TR>
							<TD colspan="4">
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="31%" class="Heading">&nbsp;<bean:message
										key="FileUpload" /> Summary</TD>
									<TD><IMG src="images/TriangleSubhead.gif" width="19"
										height="19"></TD>
								</TR>
								<TR>
									<TD colspan="3" class="Heading"><IMG
										src="images/Clear.gif" width="5" height="5"></TD>
								</TR>

							</TABLE>
							</TD>
						</TR>
					</table>
					<%
					
					%>
					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<TR>
						
						</TR>
					</TABLE>

					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<TR>
							<TD style="color: red; size: 5pt;">Please correct following
							record(s) from the excel file.</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<tr>
							
							<TD class="ColumnBackground" width="95%"
								style="text-align: center;">&nbsp;Incorrect/Invalid Data</TD>
						</tr>
					
					

							<%
							String value="";
							//String strException="Something went wrong , kindly contact to CGTMSE Support team.";
							String strException="Please check excel record contains duplicate values.";
							if(map!=null)
							{
							   
								/* 
								for (Map.Entry<String,HashMap<Integer,String>> entry : map.entrySet()) 
								{ */

									for (Entry<String, HashMap<Integer,String>> entry : map.entrySet()) {
										  String itemKey = entry.getKey();
										  for (Entry<Integer, String> innerEntry : entry.getValue().entrySet()) {
											  int innerKey = innerEntry.getKey();
											   									   
									%><TR><%
									
									//out.println(entry.getValue());
									//m2=entry.getValue();
								
								%><td><%=innerKey%></td>
								<td colspan="2" class="TableData" style="text-align: left;">&nbsp;&nbsp;&nbsp;<%=innerEntry.getValue()%>&nbsp;&nbsp;</td>
							<%
							//}
										  }
									}

							}
							else 
							{    ArrayList   invalidApps = (ArrayList) request.getAttribute("DUPAPPS");
								
								if(invalidApps != null)
								{
									System.out.println("invalid apps ! null");
									System.out.println("invalid apps:" + invalidApps.size());
									Iterator itr = invalidApps.iterator();
									int no = 1;
									while (itr.hasNext())
									{
										ArrayList errors = (ArrayList) itr.next();
										int total_errors = errors.size();
										String data1 = "";
										String data2 = "";
										System.out.println("total errors:" + total_errors);
								%>
								<TR>
								<%
								for (int i = 0; i < total_errors; i++)
								{
									if (i == 0)
									{
										data1 = (String) errors.get(i);

									}
									else if (i >= 1)
									{
										data2 = data2 + (String) errors.get(i) + ".";
									}
								%>
								<%
								}
								%>
									<td class="TableData" style="text-align: right;"><%=data1%>&nbsp;&nbsp;</td>
									<td class="TableData"><%=data2%>&nbsp;&nbsp;</td>
								</TR>
								<%
									no++;
								}
								}								
								System.out.println("map!=null");
								%><%-- <td colspan="2" class="TableData" style="text-align: left;">&nbsp;&nbsp;&nbsp;<%=strException%>&nbsp;&nbsp;</td> --%>
								<%
							}
							
							%></TR>

						
					</TABLE>

					</TD>
				</TR>
				<%--s --%>
				<TR align="center" valign="baseline" >
					<TD colspan="2" width="700">
						<DIV align="center">
						 	<A href="javascript:submitForm('uploadGuaranteeAppInput.do')">
								<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0">
							</A>								
						</DIV>
					</TD>
				</TR>
				<%--e --%>
			</TABLE>
			</TD>
			<TD width="20" background="images/TableVerticalRightBG.gif">
			&nbsp;</TD>
		</TR>
		<TR>
			<TD width="20" align="right" valign="top"><IMG
				src="images/TableLeftBottom1.gif" width="20" height="15"></TD>
			<TD background="images/TableBackground2.gif">&nbsp;</TD>
			<TD width="20" align="left" valign="top"><IMG
				src="images/TableRightBottom1.gif" width="23" height="15"></TD>
		</TR>
	</html:form>
</TABLE>

 <%
if (exportToExcel == null) 
{
%>
<a href="jsp/Application/ApplicationsSummary.jsp?exportToExcel=YES">Export to Excel</a>
<%
}
%> 

<%--add by vinod@path 03-july-15 for download invalidApps file --%>

		


</BODY>
</HTML>