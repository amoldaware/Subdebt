<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","sendQueryRequest.do?method=sendQueryRequest");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%! String focusProperty = ""; %>
<%! String flag1 = "";%>

<script>


function submitQueryRequest(action)
{

	if(document.forms[0].contPerson.value=="" || document.forms[0].contPerson.value==null )
	{
		alert("please Enter Contact Person ");

		return;

		document.getElementById('contPerson').focus();

	}

	//alert(document.forms[0].phoneNo.value.length);
	if(document.forms[0].phoneNo.value.length < 10 || document.forms[0].phoneNo.value==null )
	{
		alert("please Enter 10 digit valid Mobile Number to contact you ");
		return;
		document.getElementById('phoneNo').focus();

	}


	if(document.forms[0].eMail.value=="" || document.forms[0].eMail.value==null )
	{
		alert("please enter valid email id ");

		return;

		document.getElementById('eMail').focus();

	}
	
		var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;  
			if(!(document.forms[0].eMail.value.match(mailformat)))  
			{  								
			alert("You have entered an invalid email address!");  
			return;
			document.getElementById('eMail').focus();
	
			}  
		

	
	if(document.forms[0].department.value=="" || document.forms[0].department.value==null )
	{
		alert("please select  department  from drop down ");
		return;
		document.getElementById('department').focus();

	}


	if(document.forms[0].qryDesc.value=="" || document.forms[0].qryDesc.value==null )
	{
		alert("please enter Query Description ");
		return;
		document.getElementById('qryDesc').focus();

	}

	

//alert("gfgfgfgf");
document.forms[0].action=action;
document.forms[0].target="_self";
document.forms[0].method="POST";
document.forms[0].submit();

//alert("gfgfgfgfend");
}

</script>





<html:form action="insertQueryRequest.do?method=insertQueryRequest" method="POST" focus="<%=focusProperty%>">
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<TR> 
		<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
		<TD background="images/TableBackground1.gif"></TD>
		<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
	</TR>
	<TR>
		<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
		<TD>
		
			<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
			<TD  align="left" colspan="4"><font size="2"><bold>
	Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
	</td>
				<TR>
					<TD>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
							<TR>
								<TD colspan="4"> 
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="31%" class="Heading"><bean:message key="sendMailHeader" /></TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
										</TR>
										<TR>
											<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>

									</TABLE>
								</TD>
							</TR>

							<TR align="left">
								<TD align="left" valign="top" class="ColumnBackground">
									&nbsp;<font color="#FF0000" size="2">*</font>Contact Person
								</TD>
								
								<TD align="left" class="TableData"> 
									<html:text property="contPerson" size="30" alt="contPerson" name="adminForm" maxlength="250"/>
								</TD>
								
								
								<TD align="left" class="TableData"> 
							
								</TD>
								
							</TR>
							
							
							
							 <TR>
								<TD align="left" valign="top" class="ColumnBackground">
									&nbsp;<font color="#FF0000" size="2">*</font>Mobile Number 
								</TD>
								
								<TD align="left" class="TableData"> 
									<html:text property="phoneNo" size="12" alt="phoneNo" onkeypress="return numbersOnly(this, event)"  name="adminForm" maxlength="10"/>
								</TD>			
								
									
							

							 </TR>
							 
							 
							   <TR>
								<TD align="left" valign="top" class="ColumnBackground">
									&nbsp;<font color="#FF0000" size="2">*</font>Email Id
								</TD>
								
								<TD align="left" class="TableData"> 
									<html:text property="eMail" size="30" alt="email" name="adminForm" maxlength="250"/>
								</TD>			
								
									
							

							 </TR>		
							
							 
							 <tr> <TD align="left" valign="top" class="ColumnBackground">
									&nbsp;<font color="#FF0000" size="2">*</font>Query pertaining to  
								</TD><TD align="left" class="TableData"> 
									<html:select property="department" name="adminForm">
										<html:option value="">Select Department</html:option>
										<html:options property="Departments" name="adminForm" />
									</html:select>
								</TD></tr>
							 
							 
							 
															

							
							 <TR>
								<TD align="left" valign="top" class="ColumnBackground">
									&nbsp;<font color="#FF0000" size="2">*</font>Query Description (only 200 characters)
								</TD>
								
								<TD align="left" class="TableData" colspan="3"> 
									<html:textarea property="qryDesc" cols="100" rows="5" alt="qryDesc" name="adminForm" />
								</TD>			
								
											
								
							 </TR>
						</TABLE>
					</TD>
				</TR>
				<TR >
					<TD height="20" >
						&nbsp;
					</TD>
				</TR>
							
				<TR >
					<TD align="center" valign="baseline" >
						<DIV align="center">
						<A href="javascript:submitQueryRequest('insertQueryRequest.do?method=insertQueryRequest')"><img src="images/Save.gif" alt="Accept" width="49" height="37" border="0"></a>
							
							
							<a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
								<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
							
														
							
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
	
</HTML>		






