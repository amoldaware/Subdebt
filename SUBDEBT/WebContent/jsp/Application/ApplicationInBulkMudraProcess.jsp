<!-- 
Description : Bulk Upload for 2.5Cr result page.
Creaded By  : Deepak Kr Ranjan
Created Date: 14-Apr-2020
 -->
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
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map.Entry"%>
<%@ page import="com.cgtsi.action.CaptchaSession"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<head>
<script src="/csrfguard"></script>
<script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 
  //============================================================      
        function clickIE4(){
            if (event.button==2){
                             //  alert(message);
                               return false;
                               }
                 }
   
                 function clickNS4(e){
                               if (document.layers||document.getElementById&&!document.all){
                                              if (e.which==2||e.which==3){
                                                       // alert(message);
                                                        return false;
                               }
                       }
                 }
   
                 if (document.layers){
                               document.captureEvents(Event.MOUSEDOWN);
                               document.onmousedown=clickNS4;
                 }
   
                 else if (document.all&&!document.getElementById){
                               document.onmousedown=clickIE4;
                 }   
                 document.oncontextmenu=new Function(";return false;");
</script> 
<% 
if(request.getSession(false)!=null){
String value="";
String strException="Something went wrong , kindly contact to CGTMSE Support team.";
String strSuccessful="Record successfully uploaded.";
String strError="Please verify record and upload again.";

HashMap<String,ArrayList> map =null;
ArrayList successRecords = null;
ArrayList unsuccessRecords = null;
ArrayList errorRecords = null;
ArrayList error = null;
int success_cnt = 0;
int unsuccess_cnt = 0;
int all_eror_cnt = 0;
int eror_cnt = 0;

if(request.getAttribute("UploadedStatus")!=null)
{
	
	map = (HashMap)request.getAttribute("UploadedStatus");
	
	if(null!=map.get("successRecord")){		
		successRecords = (ArrayList)map.get("successRecord");
		//out.println("successRecords.size() :"+successRecords.size());	
		if(successRecords.size()==2){
	  		ArrayList SuccessDataList=(ArrayList)successRecords.get(1);
	  		success_cnt = SuccessDataList.size();
		}
		
	}
	
	if(null!=map.get("unsuccessRecord")){
			unsuccessRecords = (ArrayList)map.get("unsuccessRecord");
			//out.println("unsuccessRecords.size() :"+unsuccessRecords.size());
			if(unsuccessRecords.size()==2){				
			  ArrayList UnSuccessDataList=(ArrayList)unsuccessRecords.get(1);
		  	 unsuccess_cnt = UnSuccessDataList.size();		
			}
			unsuccess_cnt=0;
	}
	
	if(null!=map.get("allerror")){
		errorRecords = (ArrayList)map.get("allerror");
		//out.println("errorRecords.size() :"+errorRecords.size());
		if(errorRecords.size()==2){				
		  ArrayList errorDataList=(ArrayList)errorRecords.get(1);
		  all_eror_cnt = errorDataList.size();		
		}
		
	if(null!=map.get("error")){
		   error = (ArrayList)map.get("error");
		   eror_cnt=error.size();
			//out.println("errorRecords.size() :"+error.size());
	}	
}
	System.out.println("Result::"+map);	
	//out.println("successRecords::"+success_cnt);
	//out.println("unsuccessRecords::"+unsuccess_cnt);
	//out.println("errorRecords cnt::"+eror_cnt);
}
	
  	

%>
<script type="text/javascript">
<%-- alert('Records Uploaded Successfully: '+<%=success_cnt%>+'\n\n UnSuccessful Records: '+<%=unsuccess_cnt%>+'\n\n Error Records: '+<%=all_eror_cnt%>); --%>
</script>
</head>
<BODY onkeydown="return (event.keyCode != 116);">

<%
  String captchaSession=CaptchaSession.getCaptchaSession(request);
%>
<TABLE width="900" border="0" cellpadding="0" cellspacing="0">
	<html:form action="ApplicationInBulkMudraInput.do?method=ApplicationInBulkMudraInput"
		method="POST" enctype="multipart/form-data">

		<html:errors />
		<input type="hidden" name="csrfCode" id="csrfCode"  value="<%=captchaSession%>"/>
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
			<TABLE width="100%" border="1" align="left" cellpadding="0"
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
							<TD style="color: blue; size: 5pt;">Uploaded Summary for Mudra Application Lodgement Detail</TD>
						</TR>
					</TABLE>
					
					 <% if(error.size()==0){ %>
					
					<TABLE width="100%" border="0" cellspacing="5" cellpadding="5" class="TableData" style="text-align: center;" > 
					<tr>	
						<td><font style="color:black; font-size: 12px;"><b>Sr.no</b></font></td>
						<td><font style="color:black; font-size: 12px;"><b>Response Type</b></font></td>
						<td><font style="color:black; font-size: 12px;"><b>Download</b></font></td>
					</tr>
					<tr>
						<td><font style="color:black; font-size: 12px;">1</font></td>
						<td><font style="color:green; font-size: 15px;"><blink><b>Records To Be Processed</b></blink></font></td>
						 <%=(String)request.getAttribute("BatchId")%>
						      
						<td><font style="color:green; font-size: 15px;"><b> <%=(String)request.getAttribute("BatchId")%></b></font></td>
						<%-- <%if(success_cnt>0){%>
						      
						<td><a href="ApplicationInBulkMudraProcess.do?method=ExportToFile&&fileType=CSVType&FlowLevel=SuccessDataList&csrfCode=<%=captchaSession%>"><font style="color:green; font-size: 15px;"><b><%=success_cnt%></b></font></a></td>
					    <%}else{ %>
						<td><b>0</b></td>
						<%}%> --%>
						
					</tr>
					<%-- <tr>	
						<td><font style="color:black; font-size: 12px;">2</font></td>
						<td><font style="color:red; font-size: 15px;"><b>UnSuccessful Records</b></font></td>
						<%if(unsuccess_cnt>0){%>
						<td><a href="ApplicationInBulkProcess.do?method=ExportToFile&&fileType=XLSType&FlowLevel=UnSuccessDataList&csrfCode=<%=captchaSession%>"><font style="color:red; font-size: 15px;"><b><%=unsuccess_cnt%></b></font></a></td>
						<%}else{ %>
						<td><b>0</b></td>
						<%}%>
					</tr> --%>					 
					<tr>	
						<td><font style="color:black; font-size: 12px;">2</font></td>
						<td><font style="color:red; font-size: 15px;"><b>Error Records</b></font></td>
						<%if(all_eror_cnt>0){%>
						<td><a href="ApplicationInBulkMudraProcess.do?method=ExportToFile&&fileType=CSVType&FlowLevel=Allerrors&csrfCode=<%=captchaSession%>"><font style="color:red; font-size: 15px;"><b><%=all_eror_cnt%></b></font></a></td>
						<%}else{ %>
						<td><b>0</b></td>
						<%}%>
					</tr>
					
					</TABLE>
					<%}else{ %>
			            <TABLE width="100%" border="0" cellspacing="5" cellpadding="5" class="TableData" style="text-align: center;" > 
					<tr>	
						<td><font color=red><h2><%=error%></h2></font></td>
					</tr>
					</TABLE>
			            <%} %>
					 	 		 
				     </TD>
				</TR> 			
				    
				<%--s --%>
				<TR align="center" valign="baseline" >
					<TD colspan="2" width="700">
						<DIV align="center">
						 	<A href="javascript:submitForm('ApplicationInBulkMudraInput.do?method=ApplicationInBulkMudraInput')">
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
<!-- class="ColumnBackground" -->
<TABLE  width="850" border="0" cellpadding="0" cellspacing="0">
<tr>
							<TD class="ColumnBackground"><font style="color: red; font-size: 11px;"><b>Important Note: </b></TD>
							
							<td class="ColumnBackground">						
								<div>
                                    <font style="color:black; font-size: 11px;">
                            -  Your uploaded file has error pertaining to data format and filed values, please correct and re-upload the same.   
                            </font></div></br>
                            <div><font style="color:black; font-size: 11px;">
                            - Please check your successful uploaded batch status in "Reports and MIS-->Batch Upload Status"   
                            </font></div>
                            </br>
                            <div><font style="color:black; font-size: 11px;">
                             - Your successful submitted file may have some business logics / validations failed, which you will get in processed details. You need to tune failed records as per scheme guidelines and re-upload as fresh file.
                            </font></div>
														
							<%-- <td class="ColumnBackground">						
								<div>
									<font style="color: red; font-size: 11px;"><b>
											Please upload guarantee application records only once. Only Unsuccessful / Error records may be re-uploaded again.</b></font>
											<br>
									<font style="color: red; font-size: 11px;"><b>Please refer to the last column of excel sheet downloaded for error details.</b></font><br>								
									<font style="color: green; font-size: 11px;">Pass - </font>
									<font style="color: black; font-size: 11px;"> <font style="color:green; font-size: 15px;"><b><%=(Integer)request.getAttribute("successCount")%></b></font></font> <br>
									<font style="color: red; font-size: 11px;">Fail Records - </font> 
									<font style="color: black; font-size: 11px;"><font style="color:red; font-size: 15px;"><b><%=(Integer)request.getAttribute("unsuccessCount")%></b></font></font><br>
									<font style="color: red; font-size: 11px;">Error Records - </font> 
									<font style="color: black; font-size: 11px;"><font style="color:red; font-size: 15px;"><b><%=all_eror_cnt%></b></font><br> 
								</div>								
							</TD> --%>
						</TR>
</TABLE>

</BODY>
</HTML>
<%
	   }else{		  
		  request.getSession().invalidate();
		  response.sendRedirect(request.getContextPath() + "/showLogin");
	  } 
  %>