<%@page import="com.cgtsi.actionform.NPADateModificationActionForm"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="com.cgtsi.util.SessionConstants"%>

<%! 
String strFormType;
%>
<%
session.setAttribute("CurrentPage","showMemberForViewCgpan.do?method=showMemberForViewCgpan");
System.out.println("formType ="+request.getAttribute("formType") );
strFormType=(String)request.getAttribute("formType");
%>
<script type="text/javascript">
  function checkCGPanDetailsForNPA()
  {
	  var cgpan = document.getElementById("cgpan").value;
	  var npaFormType = document.getElementById("npaFormType").value;
	  if(cgpan=="")
	  {
			//inlineMsg('cgpan','CGPAN should not be blank.',2);	
			alert("CGPAN should not be blank.");		 
	  }
	  else
	  {  
   		  
		  	var xmlhttp;
		    if (window.XMLHttpRequest){
		        xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
		    } else {
		        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
		    }		    
		    xmlhttp.open("POST", "CheckCGPANAction.do?method=checkCGPanAgainstMemberID&cgpan="+cgpan+"&npaFormType="+npaFormType, true);		    
		  //  xmlhttp.open("GET", "CheckCGPANAction.do?method=checkCGPanAgainstMemberID&cgpan", true);
		   		 
		    xmlhttp.onreadystatechange = function() {
		    if (xmlhttp.readyState == 4) 
			{
		            if (xmlhttp.status == 200)
		            {
		            	  var temp = new Array();
			              temp =xmlhttp.responseText;	
			             // alert(temp+"1");		     
			              if(temp=='')
			              {
			            		document.npaActionForm.submit();	
				          }
			              else
			              {			            	  
 								//inlineMsg('cgpan',temp,2);	
			            	  alert(temp+"2");		            	 
			              }
		            }
		            else
		            {
		                alert('Something is wrong !!');
		            }
		        }
		    };
		    xmlhttp.send(null);
		  
		   // document.gmPeriodicInfoForm.action="CheckCGPANAction.do?method=checkCGPanAgainstMemberID&cgpan="+document.getElementById("cgpan").value+"&npaFormType="+document.getElementById("npaFormType").value;
	 	 }
  }
</script>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />

	<html:form action="npaDateModificationGetCGPanAction.do?method=getCGDetailsFromCGPAN" method="POST" >
	
	<head>
		<LINK href="<%=request.getContextPath()%>/css/messages.css" rel="stylesheet" type="text/css">		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/messages.js"></script>
	</head>
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<table width="661" border="0" cellspacing="1" cellpadding="0">
							 <TR>
								<TD colspan="7">
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="31%" class="Heading"><bean:message key="chooseMember" /></TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
										</TR>
										<TR>
											<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							</table>
							
							<table width="661" border="0" cellspacing="1" cellpadding="1">
								<tr align="left">
									<td class="ColumnBackground" width="207">
										<div align="left">
										  &nbsp;<font color="#FF0000" size="2">*</font><bean:message key="enterCgpan" />
										</div>
									 </td>
									 <td colspan="3" class="TableData" width="343">
										<div align="left">
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <html:text property="cgpan"  styleId="cgpan" size="20" alt="Select Member" name="gmPeriodicInfoForm" maxlength="25"  />
										</div>
									 </td>
									 <td class="TableData" width="343">
										<div align="left">
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <html:hidden property="npaFormType"  styleId="npaFormType" name="gmPeriodicInfoForm" value='<%=strFormType%>' />
										</div>
									 </td>
								</tr>
							 </table>									
						</td>
					</tr>

					
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">								
								<A href="#" onclick="checkCGPanDetailsForNPA()"><IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>								
							</DIV>
						</TD>
					</TR>					
				</TABLE>
			</TD>
			<TD width="20" background="images/TableVerticalRightBG.gif">
				&nbsp;
			</TD>
		</TR>
		<TR>
			<TD width="20" align="right" valign="top">
				<IMG src="images/TableLeftBottom1.gif" width="20" height="15">
			</TD>
			<TD background="images/TableBackground2.gif">
				&nbsp;
			</TD>
			<TD width="20" align="left" valign="top">
				<IMG src="images/TableRightBottom1.gif" width="23" height="15">
			</TD>
		</TR>
	</html:form>
</TABLE>				