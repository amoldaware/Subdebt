<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page language="java"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
 <script type="text/JavaScript"> 
 
  </script>
<body>
<html:form  name = "gmPeriodicInfoForm" type="com.cgtsi.actionform.GMActionForm" action="afterTenureApproval.do?method=afterTenureApproval" method="POST"  >
<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">

<html:errors />
	
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="1" cellspacing="1">
					<TR>
						<TD colspan="8"> 
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<!--<TR>
									<TD width="50%" class="Heading">Approve Request for Tenure by MLI </TD>
									<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
								</TR>-->
								<TR>
<!--									<TD colspan="8" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>-->
								</TR>
							</TABLE>
						</TD>
					</TR>
					 <tr >
	         				<td colspan="8"> Search data Remarks  Wise.
	         				</td>
	         				
	         				<td colspan="8">
	         				   <html:select property="remarksOnNpa" name="gmPeriodicInfoForm"  onchange="fetchTenureApprovalData(this.value);">
			    <html:option value="">Select</html:option>
                            <html:option value="Reschedulement_Rephasement">Reschedulement/Rephasement of the account(Live Cases)</html:option>
                            <html:option value="2">Other</html:option>                         								
		    	</html:select>
	         				</td>
	       			</tr>
	       			
	       	
       			</TABLE>
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

</TABLE>

<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	
</TABLE>

<div id="divTenureApproval"></div>

<!--<div id="errorsMessage" class="errorsMessageNew"></div>-->
	</html:form>
</body>

</html>