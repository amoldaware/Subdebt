<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%! String focusField = "";%>
<%! String flag = "";%>
<%    
    flag = (String)session.getAttribute("MemberSelected");
    if((flag != null) && (flag.equals("Y")))
    {
        focusField = "userId";
    }
    if((flag == null)||(flag != null) && (flag.equals("N")))
    {
        focusField = "memberId";
    }    
%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","clmSettldReport.do?method=clmSettldReport");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	 <html:form action="clmSettledExcelReportNew.do?method=clmSettledExcelReportNew&fileType=CSVType&FlowLevel=ClmSettledExcelReport&Flag=A
	" method="POST" enctype="multipart/form-data" styleId="formID">

		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
			<A HREF="securitizationReportHelp.do?method=securitizationReportHelp">
			HELP</A>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<tr>
				<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
				</tr>
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="10"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="27%" class="Heading"><bean:message key="danReportHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

											<TR>
								<TD align="left" valign="top" class="ColumnBackground">
									<DIV align="center"><font color="#FF0000" size="2">*</font> <bean:message key="fromdate" /></DIV>
								</TD>
								<TD align="left" valign="middle" class="TableData">
									<DIV align="left">
										<html:text property="dateOfTheDocument26" size="15" maxlength="10" alt="Reference" name="rsForm"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('reportForm.dateOfTheDocument26')" align="middle" alt="">
										<DIV align="left"></DIV>
									</DIV>
								</TD>

								<TD align="left" valign="top" class="ColumnBackground">
									<font color="#FF0000" size="2">*</font> <bean:message key="toDate" />
								</TD>

								<TD align="left" valign="middle" class="TableData">
									<DIV align="left">
										<html:text property="dateOfTheDocument27" size="15" maxlength="10" alt="Reference" name="rsForm" />
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('reportForm.dateOfTheDocument27')" align="middle" alt="">
										<DIV align="left"></DIV>
									</DIV>
								</TD>
								<td class="TableData"></td>
							</TR>
							<!-- added by vivek -->
									<tr>
										<TD align="left" valign="top" class="ColumnBackground">
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>
										<bean:message key="memberId" />
										</TD>
										<TD align="left" valign="top" class="TableData"><DIV align="left"><html:select
												property="memberId" name="rsForm"
												onchange="javascript:submitForm('showModifyUser.do?method=getUsersForMember')">
												<html:option value="">Select</html:option>
												<html:options property="memberIds" name="rsForm" />
											</html:select></DIV></TD>
											<%
											if ((flag != null) && (flag.equals("Y"))) {
													session.setAttribute("MemberSelected", "N");
												}
										%>
									</tr>
									<%-- <tr>
									<TD align="left" valign="top" class="ColumnBackground" >
										&nbsp;<DIV align="center"><font color="#FF0000" size="2">*</font><bean:message key="memberId" /></DIV>
			                       </TD>
										<TD align="left" valign="top" class="TableData"><html:select
												property="memberId"  name="rsForm"
												onchange="javascript:submitForm('showModifyUser.do?method=getUsersForMember')">
												<html:option value="">Select</html:option>
												<html:options property="memberIds"  name="rsForm"  />
											</html:select></TD>
										<TD width="20%" align="left" valign="top"
											class="ColumnBackground">
											<DIV align="left">
												&nbsp;<font color="#FF0000" size="2">*</font>
												<bean:message key="userId" />
											</DIV>
										</TD>
										<TD width="27%" align="left" valign="top" class="TableData">
											<html:select property="userId" name="adminForm">
												<html:option value="">Select</html:option>
												<html:options property="activeUsers" name="adminForm" />
											</html:select>
										</TD>
										<%
											if ((flag != null) && (flag.equals("Y"))) {
													session.setAttribute("MemberSelected", "N");
												}
										%>
									</tr> --%>

<%-- 						  <logic:equal property="zone_Id" value="0000" name="rsForm"> 	
									  <TR>
									  <TD  align="center" valign="center"  class="ColumnBackground" >
									  <DIV align="center"><font color="#FF0000" size="2">*</font> 
									  <bean:message key="memberId" /> 
									   </DIV>
									  </TD>
									   <TD align="left" valign="center" class="ColumnBackground" colspan=3>
									   <DIV align="left">
										 <html:text property="memberId" size="20"  alt="memberId" name="rsForm"  maxlength="12" onkeypress="return numbersOnly(this, event);" onkeyup="isValidNumber(this);"/>  
										 </DIV>
									  </TD>
									  <td class="TableData"></td>
									  </TR>
									   </logic:equal> 
									 <logic:notEqual property="bankId" value="0000" name="rsForm">	
									  <TR>
									  <TD  align="center" valign="center"  class="ColumnBackground" >
									  <DIV align="center">
									  <bean:message key="memberId" /> 
									   </DIV>
									  </TD>
									   <TD align="left" valign="center" class="ColumnBackground1" colspan=3>
									   <DIV align="left">
										<bean:write property="memberId" name="rsForm"/>
										 </DIV>
									  </TD>
									  <td class="TableData"></td>
									  </TR>
									  </logic:notEqual> --%>
									  
									  
									  <TR>
                                                                                
  										<td  class="TableData">UNIT ITPAN: <html:text property="itPanVal"   size="20"  alt="Reference" name="rsForm" onkeypress="resetColor(this);"/>&nbsp;&nbsp;</td>
  										<td  class="TableData">UNIT NAME:      <html:text property="unitNameVal"   size="20"  alt="Reference" name="rsForm" onkeypress="resetColor(this);"/>&nbsp;&nbsp;</td>
 										<td  class="TableData"  > APP REF NO: <html:text property="appRefNoVal"   size="20"  alt="Reference" name="rsForm" onkeypress="resetColor(this);"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
										  <td  class="TableData">CGPAN:      <html:text property="CGPANVal"   size="20"  alt="Reference" name="rsForm" onkeypress="resetColor(this);"/>&nbsp;&nbsp;</td>
										 <td  class="TableData">LOAN ACCOUNT NO: <html:text property="loanAccVal"   size="20"  alt="Reference" name="rsForm" onkeypress="resetColor(this);"/>&nbsp;&nbsp;</td>
										

                                                                                
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
							   
								<A href="javascript:document.rsForm.reset()">
									<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>
							<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
									<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
                          <DIV align="center">
                             <input type="image" src="images/ExcelDownload.png" name="saveForm" class="btTxt submit" id="formID" width="49" height="37" border="0" />
                            </DIV>
<!-- <a href="javascript:submitForm('clmSettledExcelReportNew.do?method=clmSettledExcelReportNew&&fileType=XLSType&FlowLevel=ClmSettledExcelReport&&Flag=A')">
								<IMG src="images/ExcelDownload.png" width="50" height="35" alt="Export to Excel">
								</a>
 -->
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
		function submitForm(){
			
				//var memberid = document.forms[0].memberId.value;
				var fromdate = document.forms[0].dateOfTheDocument26.value;
				var todate = document.forms[0].dateOfTheDocument27.value;
				
				var result1 = chkdate(fromdate);
				if(result1 == false){
				
					document.forms[0].dateOfTheDocument26.style.backgroundColor = 'yellow';
					return false;
				}
				var result2 = chkdate(todate);
				if(result2 == false){
					document.forms[0].dateOfTheDocument27.style.backgroundColor = 'yellow';
					return false;
				}
				
				var elem1 = fromdate.split('/');  
				var elem2 = todate.split('/');  
				
				var fromDay = elem1[0];
				var fromMon = elem1[1];
				var fromYear = elem1[2];
				var toDay = elem2[0];
				var toMon = elem2[1];
				var toYear = elem2[2];
				
				
				if(fromYear > toYear || (fromYear == toYear && fromMon > toMon) || (fromYear == toYear && fromMon == toMon && fromDay > toDay)){
						alert("from date can not greater than to date");
					 document.forms[0].dateOfTheDocument26.style.backgroundColor = 'yellow';
						return false;				
				}
		
			//	var approvedDate = document.getElementById("approvedDate");
			/* 	var paymentDate = document.getElementById("paymentDate");
				
				var condition;
				
				
					if(paymentDate.checked){
                                      
						condition = 'paymentDateWiseReport';
                                                  document.getElementById("condition").value = 'paymentDateWiseReport';
					}else{
						alert("please select condition");
						return false;
					}				
						document.rsForm.target ="_self";
						document.rsForm.method="POST";
						document.rsForm.action="claimSettledReport.do?method=claimSettledReport"
						document.rsForm.submit();		 */
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
		
		function chkdate(objName) 
		{
   
                              var dtCh= "/";
                              var minYear=1900;
                              var maxYear=2050;
                              var strDate;
                              var strDay;
                              var strMonth;
                              var strYear;
                              var strYr;
                              var intday;
                              var intMonth;
                              var intYear;                           
                              var datefield = objName;                              
                              var intElementNr;                          
                             
                         
							  
                             if (datefield.length >= 11 || datefield.length < 10) {
                              alert("Please correct Date, date format should be : dd/MM/yyyy")
                              return false;
                              }
                      
					
					
					  
                    var daysInMonth = DaysArray(12);
					var pos1=datefield.indexOf(dtCh);
					var pos2=datefield.indexOf(dtCh,pos1+1);
					var strDay=datefield.substring(0,pos1);
					var strMonth=datefield.substring(pos1+1,pos2);
					var strYear=datefield.substring(pos2+1);
					
				
				
                  if (pos1==-1 || pos2==-1){
                      alert("Please correct Date, date format should be : dd/MM/yyyy");
                      return false;
                    }
                	if (strMonth.length<1 || strMonth<1 || strMonth>12){
                  	alert("Please enter a valid month");
                  	return false;
                  }
                  if (strDay.length<1 || strDay<1 || strDay>31 || (strMonth==2 && strDay > daysInFebruary(strYear)) || strDay > daysInMonth[strMonth]){
                  	alert("Please enter a valid day");
                    return false;
                }
                  if (strYear.length != 4 || strYear==0 || strYear<minYear || strYear>maxYear){
                  alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear);
                	return false;
                  }
                 if (datefield.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(datefield, dtCh))==false){
                    alert("Please enter a valid date");
                  	return false;
                   } 
                    return true;     
        }  
				
			function daysInFebruary (year){
				// February has 29 days in any year evenly divisible by four,
				// EXCEPT for centurial years which are not also divisible by 400.
				return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
			}
			function DaysArray(n) {
				for (var i = 1; i <= n; i++) {
					this[i] = 31
					if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
					if (i==2) {this[i] = 29}
			   } 
			   return this;
			}
			function stripCharsInBag(s, bag){
				var i;
				var returnString = "";
				// Search through string's characters one by one.
				// If character is not in bag, append to returnString.
				for (i = 0; i < s.length; i++){   
					var c = s.charAt(i);
					if (bag.indexOf(c) == -1) returnString += c;
				}
				return returnString;
			}
			
			function isInteger(s){
				var i;
				for (i = 0; i < s.length; i++){   
					// Check that current character is number.
					var c = s.charAt(i);
					if (((c < "0") || (c > "9"))) return false;
				}
				// All characters are numbers.
				return true;
			}
			
			function resetColor(field){	
				field.style.backgroundColor = 'white';
			}
	</script>
</TABLE>

