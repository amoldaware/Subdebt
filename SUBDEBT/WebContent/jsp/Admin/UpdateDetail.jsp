<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","updateAccount.do?method=updateAccount");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<HTML>
<head>
	<script type="text/javascript">
	function isNumber(evt) 
	{
    	var iKeyCode = (evt.which) ? evt.which : evt.keyCode;	        	
    	if (iKeyCode != 46 && iKeyCode > 31 && (iKeyCode < 48 || iKeyCode > 57))
        	return false;
    	return true;
	}					
	</script>
	<script>

	

function submitFinalMakePayment(action)
{
  var x;

	if(document.forms[0].zoneList.value=="" || document.forms[0].zoneList.value==null )
	{
		alert("please enter Zoneid ");
		document.getElementById('zoneList').focus();

	}

	
	if(document.forms[0].phoneNo.value=="" || document.forms[0].phoneNo.value==null )
	{
		alert("please enter Phone number ");
		document.getElementById('phoneNo').focus();

	}
	if(document.forms[0].phone.value=="" || document.forms[0].phone.value==null )
	{
		alert("please enter Mobile number ");
		document.getElementById('phone').focus();

	}
	if(document.forms[0].emailId.value=="" || document.forms[0].emailId.value==null )
	{
		alert("please enter Email Id");
		document.getElementById('emailId').focus();

	}
	

	  
	
	if(document.forms[0].branchId.value=="" || document.forms[0].branchId.value==null )
	{
		alert("please enter BranchId ");
		document.getElementById('branchId').focus();

	}

	if(document.forms[0].beneficiarymli.value=="" || document.forms[0].beneficiarymli.value==null )
	{
	alert("please enter  beneficiary bank name ");
		document.getElementById('beneficiarymli').focus();

	}
	if(document.forms[0].accNo.value=="" || document.forms[0].accNo.value==null )
	{
		alert("please enter accNo ");
		document.getElementById('accNo').focus();

	}
	if(document.forms[0].rtgsNO.value=="" || document.forms[0].rtgsNO.value==null )
	{
		alert("please enter rtgsNO ");
		document.getElementById('rtgsNO').focus();

	}
	if(document.forms[0].neftNO.value=="" || document.forms[0].neftNO.value==null )
	{
		alert("please enter neftNO ");
		document.getElementById('neftNO').focus();

	}
    if (confirm("please Check your account details Before updating") == true) {
        x = "You pressed PROCEED!";
        document.forms[0].action=action;
    	document.forms[0].target="_self";
    	document.forms[0].method="POST";
    	document.forms[0].submit();
                  
    } else {
        x = "You pressed CANCEL!";
    }
    document.getElementById("demo").innerHTML = x;




	
}

</script>
</head>
	<BODY>
		<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
			<html:form action="updateAccDetail.do?method=updateAccDetail" method="POST">			
				<html:errors />
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
									<DIV align="right">			
										<A HREF="#">HELP</A>
									</DIV>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr>
											<TD  align="left" colspan="4"><font size="2"><b>Fields marked as</b></font>
												<font color="#FF0000" size="2">*</font><font size="2"><b>are mandatory</b></font>
											</td>										
										</tr>
										<TR>
											<TD colspan="4"> 
												<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
													<TR>
														<TD width="31%" class="Heading">Update Account Details</TD>
														<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
													</TR>
													<TR>
														<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
													</TR>
												</TABLE>
											</TD>
										</TR>
											<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">&nbsp;
												<font color="#FF0000" size="2">*</font><bean:message key="mliID" />
											</TD>
											<TD align="left" class="TableData"> 
												<!--<html:text property="memberId" name="regForm" maxlength="12" size="12" onkeypress="javascript:return isNumber (event)"/>-->
												<bean:write property="memberId" name="regForm"/>
											</TD>
										</TR>		
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">&nbsp;
												<font color="#FF0000" size="2">*</font>MLI Name
											</TD>
											<TD align="left" class="TableData"> 
												<bean:write property="beneficiary" name="regForm"/>
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">&nbsp;
												<font color="#FF0000" size="2">*</font>Zone Name
											</TD>
											<TD align="left" class="TableData">
											<html:text property="zoneList" name="regForm"  onkeyup="this.value = this.value.toUpperCase();"/> 
											
											</TD>
										</TR>
									
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">&nbsp;
												<font color="#FF0000" size="2">*</font>Contact No
											</TD>
											<TD align="left" class="TableData"> 
												<html:text property="phoneNo" name="regForm" maxlength="12" size="20"/>
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">&nbsp;
												<font color="#FF0000" size="2">*</font>Mobile No
											</TD>
											<TD align="left" class="TableData"> 
											
												<html:text property="phone" name="regForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="10"/>
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">&nbsp;
												<font color="#FF0000" size="2">*</font><bean:message key="emailId" />
											</TD>
											<TD align="left" class="TableData"> 
											<html:text property="emailId" name="regForm" />
											
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">&nbsp;
												<font color="#FF0000" size="2">*</font>Name of Beneficiary
											</TD>
											<TD align="left" class="TableData"> 
		
											<bean:write property="beneficiary" name="regForm"/>
											</TD>
										</TR>
											<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">&nbsp;
												<font color="#FF0000" size="2">*</font> Beneficiary Bank Name
											</TD>
											<TD align="left" class="TableData"> 
											<html:text property="beneficiarymli" name="regForm"  onkeyup="this.value = this.value.toUpperCase();"/>
												
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">&nbsp;
												<font color="#FF0000" size="2">*</font><bean:message key="accountType" />
											</TD>
											<TD align="left" class="TableData">
											
										  <html:radio name="regForm" value="Saving"   property="accountType"  onclick="funcdisable('E');" >Savings</html:radio>
	                                     <html:radio name="regForm" value="Current"  property="accountType"   onclick="funcdisable('F');">Current</html:radio>
											
											<!-- 
												<html:text property="accountType" name="regForm" />
											--></TD>
										</TR>	
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">&nbsp;
												<font color="#FF0000" size="2">*</font>Branch Code
											</TD>
											<TD align="left" class="TableData"> 
												<html:text property="branchId" name="regForm"  onkeyup="this.value = this.value.toUpperCase();"/>	
												
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">&nbsp;
											&nbsp;&nbsp;MICR Code
											</TD>
											<TD align="left" class="TableData"> 
												<html:text property="micrCode" name="regForm" />
											</TD>
										</TR>								
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">&nbsp;
												<font color="#FF0000" size="2">*</font>Account No
											</TD>
											<TD align="left" class="TableData">
												<html:text property="accNo" name="regForm" />
											</TD>
										</tr>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">&nbsp;
												<font color="#FF0000" size="2">*</font>RTGS No
											</TD>
											<TD align="left" valign="top" class="TableData">
												<html:text property="rtgsNO" name="regForm"  onkeyup="this.value = this.value.toUpperCase();"/>	
											</TD>
										</TR>
										<TR align="left">
											<TD align="left" valign="top" class="ColumnBackground">&nbsp;
												<font color="#FF0000" size="2">*</font>NEFT No
											</TD>
											<TD align="left" valign="top" class="TableData">
									
																	
										
									
											<html:text property="neftNO" name="regForm"  onkeyup="this.value = this.value.toUpperCase();"/>
											</TD>
										</TR>
										
										 <html:hidden property="DataFlag" name="regForm" />
									</TABLE>
								</TD>
							</TR>
							
							<TR ><TD height="20" >&nbsp;NOTE:Each member Id should consist the same account number and not be repetative for new Claims(cgpans)of the same member Id.</TD></TR>
							<TR >
							<TR ><TD height="20" >&nbsp;</TD></TR>
								<TD>
					<A target="_blank" href="Download/bank mandate form.doc" ><font color="red" >
					CLICK HERE TO DOWNLOAD FOR BANK MANDATE FORM.</font></A>&nbsp;&nbsp;&nbsp;
					<p>
					Send us duly filled in and signed Bank mandate form (physical copy) by the authorised officiail&nbsp;&nbsp;&nbsp;<b>with name & seal </b>&nbsp;&nbsp;&nbsp;(not below the rank of Asst.Genral Manager) and pass on the same to us for necessary action at our end.
					</p></TD>
				
					
				
							<TR >
								<TD align="center" valign="baseline" >
									<DIV align="center">
										<A href="javascript:submitFinalMakePayment('updateAccDetail.do?method=updateAccDetail')">
											<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0">
										</A>
										<A href="javascript:document.regForm.reset()">
											<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0">
										</A>
                                        <A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
											<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0">
										</A>																
									</DIV>
								</TD>
							</TR>
						</TABLE>
					</TD>
					<TD width="20" background="images/TableVerticalRightBG.gif">&nbsp;</TD>
				</TR>
				<TR>
					<TD width="20" align="right" valign="top">
						<IMG src="images/TableLeftBottom1.gif" width="20" height="15">
					</TD>
					<TD background="images/TableBackground2.gif">&nbsp;</TD>
					<TD width="20" align="left" valign="top">
						<IMG src="images/TableRightBottom1.gif" width="23" height="15">
					</TD>
				</TR>
			</html:form>			
		</TABLE>		
	</BODY>
</HTML>