<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%session.setAttribute("CurrentPage","displayUpdatePaymentDetailInput.do?method=displayUpdatePaymentDetailInput");%>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="getPaymentDetailsForMemeberID.do?method=getPaymentDetailsForMemeberID" enctype="multipart/form-data" method="POST">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><img src="images/ReceiptsPaymentsHeading.gif" width="142" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<tr>
				<TD  align="left" colspan="4"><font size="2"><bold></font>
				</td>
				</tr>
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="10"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="27%" class="Heading">Payment Details</TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD align="left" valign="top" class="ColumnBackground">
									<DIV align="center">
										  &nbsp;<font color="#FF0000" size="2">*</font><bean:message key="fromdate" /> 
										  </DIV>
									</TD>
									   <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
										  <html:text property="dateOfTheDocument36" size="20" maxlength="10" alt="from date" name="cpTcDetailsForm" onkeypress="resetColor(this);"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.dateOfTheDocument36')" align="center">
										  <DIV align="left">
									  </TD>
									
									  <TD align="left" valign="top" class="ColumnBackground">
										  &nbsp;<font color="#FF0000" size="2">*</font><bean:message key="toDate"/> 
										 
									</TD>

									    <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
										  <html:text property="dateOfTheDocument37" size="20" maxlength="10" alt="to date" name="cpTcDetailsForm" onkeypress="resetColor(this);" />
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.dateOfTheDocument37')" align="center">
										  <DIV align="left">
									  </TD>
									  </TR>
									  <TR>
											  <TD align="left" valign="top" class="ColumnBackground">
												  <DIV align="center">
												  &nbsp;<font color="#FF0000" size="2">*</font>Memeber Id
												  </DIV>
											</TD>
											<td align="left" valign="center" class="TableData">
											 
												<html:text property="memberID" name="cpTcDetailsForm" size="20" maxlength="12" alt="memener id" onkeypress="return numbersOnly(this, event);" onkeyup="isValidNumber(this);"/>
												
											</td>
											<td class="TableData"></td>
											<td class="TableData"></td>
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
							       <A href="#" onclick="return saveForm();">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
								
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
	<script type="text/javascript" >
		function saveForm(){
		
				var memberid = document.forms[0].memberID.value;
				var fromdate = document.forms[0].dateOfTheDocument36.value;
				var todate = document.forms[0].dateOfTheDocument37.value;
				
				var elem1 = fromdate.split('/');  
				var elem2 = todate.split('/');  
				
				var fromDay = elem1[0];
				var fromMon = elem1[1];
				var fromYear = elem1[2];
				var toDay = elem2[0];
				var toMon = elem2[1];
				var toYear = elem2[2];
				
				/* if(memberid.isNaN()){
					alert("");
				} */
				
				if(fromYear > toYear || (fromYear == toYear && fromMon > toMon) || (fromYear == toYear && fromMon == toMon && fromDay > toDay)){
						alert("from date can not greater than to date");
					 document.forms[0].dateOfTheDocument36.style.backgroundColor = 'yellow';
						return false;				
				}else if(memberid == null || memberid == "" || memberid.length < 0){
						alert("please enter 12 digit member id");
					 document.forms[0].memberID.style.backgroundColor = 'yellow';				
						return false;
				}else{
					 document.cpTcDetailsForm.target ="_self";
					 document.cpTcDetailsForm.method="POST";
					 document.cpTcDetailsForm.action="getPaymentDetailsForMemeberID.do?method=getPaymentDetailsForMemeberID";
					 document.cpTcDetailsForm.submit();
						return true;
				}
		}
		function numbersOnly(myfield, e)
		{
			myfield.style.backgroundColor = 'white';
			var key;
			var keychar;

			if (window.event)
			   key = window.event.keyCode;
			else if (e)
			   key = e.which;
			else
			   return true;
			keychar = String.fromCharCode(key);

			// control keys
			if ((key==null) || (key==0) || (key==8) ||
				(key==9) || (key==13) || (key==27) ){
			   return true;
			}
			// numbers
			else if ((("0123456789").indexOf(keychar) > -1)){
			   return true;
			}else{
			   return false;
			   }
		}

		function isValidNumber(field)
		{
			if(!isValid(field.value))
			{
				field.focus();
				field.value='';
				return false;
			}
		}
		
	</script>
</TABLE>

