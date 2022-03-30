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
String value="";
String strException="Something went wrong , kindly contact to CGTMSE Support team.";
String strSuccessful="Record successfully uploaded.";
String strError="Please verify record and upload again.";
HashMap<String,HashMap<Integer,String>> map =null;
ArrayList invalidListResult = null;
ArrayList validSummary = null;

System.out.println("outstanding application summary error page...........................................................................");


if(request.getAttribute("FinalResultRedirectToJSP")!=null)
{	
	 map = (HashMap<String,HashMap<Integer,String>>)request.getAttribute("FinalResultRedirectToJSP");
}
if(request.getAttribute("validappsSummaryDetails")!=null){
	validSummary = (ArrayList)request.getAttribute("validappsSummaryDetails");	
}
if(request.getAttribute("invalidAppsLstRecord")!=null)
{
	invalidListResult = (ArrayList)request.getAttribute("invalidAppsLstRecord");
	// System.out.println(invalidListResult.toString());	
}  	

%>

<HTML>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<BODY>
<%-- 
<%		
String exportToExcel = request.getParameter("exportToExcel");  
if (exportToExcel != null && exportToExcel.toString().equalsIgnoreCase("YES")) 
{        	
	response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-Disposition", "inline; filename=" + "excel.xls"); 
}
%>
--%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:form action="uploadOutstandingAmt.do?method=uploadOutstandingAmt"
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
										key="FileUpload" />Summary</TD>
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
							<TD style="color: red; size: 5pt;">Please correct following	record(s) from the excel file.</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<tr>
							
							<TD colspan="2" class="TableData" style="text-align: center;">&nbsp;Incorrect/Invalid Data </TD>								
						</tr>
						<tr>
						          <%if(validSummary!=null){%>
									<TD class="ColumnBackground"  style="text-align: center;">&nbsp;<font color="blue"><b><%=strSuccessful%></b></font>&nbsp;&nbsp;</TD>							
							      <%}%>
							       <%if(invalidListResult!=null){%>
									<TD class="ColumnBackground"  style="text-align: center;">&nbsp;<font color="blue"><b><%=strError%></b></font>&nbsp;&nbsp;</TD>							
							      <%}%>
					 </tr>
					          <%
							
							if(map!=null){
								for (Entry<String, HashMap<Integer,String>> entry : map.entrySet()) {
									  String itemKey = entry.getKey();
									  for (Entry<Integer, String> innerEntry : entry.getValue().entrySet()) {
										  int innerKey = innerEntry.getKey();		
										 System.out.println("inner Key raju>>>>>>>>>>>>>>>>>>> "+innerKey);
								/* for (Map.Entry<String,HashMap<Integer,String>> entry : map.entrySet()) 
								{ */
									%><TR><%
									//out.println(entry.getValue());
									//m2=entry.getValue();								
								%>
								<td colspan="2" class="TableData" style="text-align: left;">&nbsp;&nbsp;&nbsp;<%=entry.getValue()%>&nbsp;&nbsp;</td>
							<%	}}
							}  
								if(validSummary!=null){		
									 System.out.println("validSummary Key raju>>>>>>>>dddd>>>>>>>>>>> "+validSummary);
							
								%>
								<td colspan="1" class="TableData" style="text-align: left;">&nbsp;&nbsp;&nbsp;<%=validSummary%>&nbsp;&nbsp;</td>
								<%-- <td colspan="2" class="TableData" style="text-align: left;">&nbsp;&nbsp;&nbsp;<%=strException%>&nbsp;&nbsp;</td> --%>
								<%
							    }
								 if(invalidListResult!=null)
								{				
									 System.out.println("invalidListResult Key raju>>>>>>KKKKK>>>>>>> "+invalidListResult);
								
									%>
									<%-- <td colspan="2" class="TableData" style="text-align: left;">&nbsp;&nbsp;&nbsp;<font color="red"><b><%=strError%></b></font>&nbsp;&nbsp;</td> --%> 
									<td colspan="1" class="TableData" style="text-align: left;">&nbsp;&nbsp;&nbsp;<%=invalidListResult%>&nbsp;&nbsp;</td>
									<%-- <td colspan="2" class="TableData" style="text-align: left;">&nbsp;&nbsp;&nbsp;<%=strException%>&nbsp;&nbsp;</td> --%>
									<%
								} 
								 if((map!=null) ) 
								{				
									 System.out.println("invalidListResult Key raju>>>>invalidListResult!=null)&&(validSummary!=null)....       map.size()>>>>>> "+map.size());
							
									%>
									 <td colspan="2" class="TableData" style="text-align: left;">&nbsp;&nbsp;&nbsp;<font color="black"><b><%=strException%></b></font>&nbsp;&nbsp;</td> 
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
						 	<A href="javascript:submitForm('uploadOutstandingInput.do')">
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
<%-- 
<%
if (exportToExcel == null) 
{
%>
<a href="jsp/Application/ApplicationsSummary.jsp?exportToExcel=YES">Export to Excel</a>
<%
}
%>
--%>
<%--add by vinod@path 03-july-15 for download invalidApps file --%>

		


</BODY>
</HTML>