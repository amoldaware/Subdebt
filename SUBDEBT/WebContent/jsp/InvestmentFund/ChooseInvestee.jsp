<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","chooseInvestee.do?method=chooseInvestee");%>

<%
DecimalFormat decimalFormat = new DecimalFormat("##########.##");
%>
<body onLoad="displaySurplusTotal()">

  <html:errors />
<html:form action="getExposureDetails.do?method=getExposureDetails" method="POST" focus="proposedDate">

  <table width="100%" border="0" cellspacing="0" cellpadding="0">

    <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td width="323" background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
      <td align="right" background="images/TableBackground1.gif"> </td>
      <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
  <tr> 
    <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0" >
	<tr>
	  <TD>			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpExposureDetails.do?method=helpExposureDetails')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
        <tr>
           <td colspan="2"> 
			<table border="0" cellspacing="1" cellpadding="0" >
                <tr> 
                  <td colspan="2" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="35%" class="Heading">&nbsp;
						<bean:message key="if_calculate_exposuer" /> 
						</td>
                        <td align="left" valign="bottom" colspan="2"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                      </tr>
                      <tr> 
                        <td colspan="6" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
<!--                <tr>
                  <td width="360" class="ColumnBackground" > &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="investeeGroup" /> 
                    </td>
                  <td class="tableData" width="474" height="25"> <div align="left"> 
                      <html:select name="ifForm" property="investeeGroup" onchange="submitForm('getInvesteeNamesForExposure.do?method=getInvesteeNames')">
                      <html:option value="">Select </html:option>
                      <html:options name="ifForm" property="investeeGroups"/>
                      </html:select>
                    </div>
                    </td>
                </tr>
                  <td width="360" class="ColumnBackground" height="25"> &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="investee" /> 
                    </td>
                  <td class="tableData" width="474" height="25"> <div align="left"> 
                      <html:select name="ifForm" property="investeeName">
                      <html:option value="">Select </html:option>
                      <html:options name="ifForm" property="investeeNames"/>
                      </html:select>
                    </div>
                    </td>
                </tr>   
				<TR align="left">
					<TD width="360" align="left" valign="top" class="ColumnBackground">
						&nbsp;<bean:message key="proposedAmntToBeInvested" />
					</TD>
					<TD width="474" align="left" class="TableData">
						<html:text property="proposedAmntToBeInvested" maxlength="16" size="20" alt="Proposed Amount to be Invested" name="ifForm"  onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)"/>
					</TD>
				</TR>-->

				<TR align="left">
					<TD align="left" valign="top" class="ColumnBackground" width="360">
						&nbsp;<font color="#FF0000" size="2">*</font>Today's Date
					</TD>
					<bean:define id="todayDate" name="ifForm" property="currentDate"/>

					<%
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String sysdate = sdf.format(todayDate);
					%>

					<TD width="474" align="left" class="TableData" >
						<%=sysdate%>
<!--						<html:text property="currentDate" maxlength="10" size="20" alt="Proposed Amount to be Invested" name="ifForm"/>
						<img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.currentDate')" align="center">-->
					</TD>
				</TR>

				<TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="proposedDateofInvestment" />
					</TD>
					<TD width="474" align="left" class="TableData">
						<html:text property="proposedDate" maxlength="10" size="20" alt="Proposed Amount to be Invested" name="ifForm" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/>
						<img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.proposedDate')" align="center">

						&nbsp;&nbsp;<a href="javascript:submitForm('getPositionDetails.do?method=getPositionDetails')">Get Position
					</TD>
					
						
					
				</TR>
				<tr>
					<td>
					</td>
				</tr>
			<tr>
				<td colspan="2">
					<table width="100%" border="0" cellspacing="1" cellpadding="0" >
			        <tr> 				
						<TD align="left" valign="top" class="HeadingBg" width="5%">
							Sl No.
						</td>
						<TD align="left" valign="top" class="HeadingBg">
							<div align="center">
							<bean:message key="exposureCategory" />
							</div>
						</td>
						<TD align="left" valign="top" class="HeadingBg">
							<div align="center">
							<bean:message key="exposureAmount" />
							</div>
						</td>
						<TD align="left" valign="top" class="HeadingBg" colspan="2">
							<div align="center">
							<bean:message key="exposureInvestmentHeading" />
							</div>
						</td>

					</tr>
					<tr>
						<TD align="left" valign="top" class="ColumnBackground">
							<div align="center">
							1.
							</div>
						</td>
						<TD align="left" valign="top" class="ColumnBackground">							
							<bean:message key="liveInvestments" />&nbsp;
						</td>
						<TD align="left" valign="top" class="ColumnBackground">
							<html:text property="liveInvtAmount" size="20" alt="liveAmount" name="ifForm"  maxlength="13" onblur="displaySurplusTotal()"/>
						</td>
						<TD align="left" valign="top" class="ColumnBackground" colspan="2">
							<div align="center">
							<html:radio name="ifForm" property="availableLiveInv" value="Y" onclick="displaySurplusTotal()"></html:radio>				<bean:message key="yes" />&nbsp;&nbsp;

							<html:radio name="ifForm" property="availableLiveInv" value="N" onclick="displaySurplusTotal()"></html:radio>				<bean:message key="no" />&nbsp;&nbsp;
							</div>

						</td>
					</tr>

					<tr>
						<TD align="left" valign="top" class="ColumnBackground">
							<div align="center">
							2.
							</div>
						</td>
						<TD align="left" valign="top" class="ColumnBackground">							
							<bean:message key="investedAmounts" />&nbsp;<%=sysdate%>
						</td>
						<TD align="left" valign="top" class="ColumnBackground">
							<html:text property="investedAmount" size="20" alt="investedAmount" name="ifForm"  maxlength="13" onblur="displaySurplusTotal()"/>
						</td>
						<TD align="left" valign="top" class="ColumnBackground" colspan="2">
							<div align="center">
							<html:radio name="ifForm" property="availableInvAmount" value="Y" onclick="displaySurplusTotal()"></html:radio>				<bean:message key="yes" />&nbsp;&nbsp;

							<html:radio name="ifForm"  property="availableInvAmount" value="N" onclick="displaySurplusTotal()"></html:radio>				<bean:message key="no" />&nbsp;&nbsp;
							</div>

						</td>
					</tr>

					<tr>
						<TD align="left" valign="top" class="ColumnBackground">
							<div align="center">
							3.
							</div>
						</td>
						<TD align="left" valign="top" class="ColumnBackground">							
							<bean:message key="maturingInvestments" />&nbsp;<%=sysdate%> and <bean:message key="proposedDateofInvestment" />
						</td>
						<TD align="left" valign="top" class="ColumnBackground">
							<html:text property="maturedAmount" size="20" alt="maturedAmount" name="ifForm"  maxlength="13" onblur="displaySurplusTotal()"/>
						</td>
						<TD align="left" valign="top" class="ColumnBackground" colspan="2">
							<div align="center">
							<html:radio name="ifForm" property="availableMaturingAmount" value="Y" onclick="displaySurplusTotal()"></html:radio>				<bean:message key="yes" />&nbsp;&nbsp;

							<html:radio name="ifForm" property="availableMaturingAmount" value="N" onclick="displaySurplusTotal()"></html:radio>				<bean:message key="no" />&nbsp;&nbsp;
							</div>

						</td>
					</tr>

					<tr>
						<TD align="left" valign="top" class="ColumnBackground">
							<div align="center">
							4.
							</div>
						</td>

						<TD align="left" valign="top" class="ColumnBackground" colspan="4">
							<div align="left">
							<bean:message key="addtlReceivableAmount" />
							</div>
						</td>
					</tr>

					<tr>
						<TD align="left" valign="top" class="ColumnBackground">
							<div align="center">
							4 a).
							</div>
						</td>
						<TD align="left" valign="top" class="ColumnBackground">							
							<bean:message key="exposureCorpusAmt" />
						</td>
						<TD align="left" valign="top" class="ColumnBackground">
							<html:text property="exposureCorpusAmount" size="20" alt="exposureCorpusAmount" name="ifForm"  maxlength="16" onblur="displaySurplusTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
						</td>
						<TD align="left" valign="top" class="ColumnBackground" colspan="2">
							<div align="center">
							<html:radio name="ifForm" property="availableCorpusAmount" value="Y" onclick="displaySurplusTotal()"></html:radio>				<bean:message key="yes" />&nbsp;&nbsp;

							<html:radio name="ifForm" property="availableCorpusAmount" value="N" onclick="displaySurplusTotal()"></html:radio>				<bean:message key="no" />&nbsp;&nbsp;
							</div>

						</td>
					</tr>

					<tr>
						<TD align="left" valign="top" class="ColumnBackground">
							<div align="center">
							4 b).
							</div>
						</td>
						<TD align="left" valign="top" class="ColumnBackground">							
							<bean:message key="exposureOtherAmounts" />
						</td>
						<TD align="left" valign="top" class="ColumnBackground">
							<html:text property="otherReceiptsAmount" size="20" alt="otherReceiptsAmount" name="ifForm"  maxlength="16" onblur="javascript:displaySurplusTotal()" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
						</td>
						<TD align="left" valign="top" class="ColumnBackground" colspan="2">
							<div align="center">
							<html:radio name="ifForm" property="availableOtherAmount" value="Y" onclick="displaySurplusTotal()"></html:radio>				<bean:message key="yes" />&nbsp;&nbsp;

							<html:radio name="ifForm" property="availableOtherAmount" value="N" onclick="displaySurplusTotal()"></html:radio>				<bean:message key="no" />&nbsp;&nbsp;
							</div>

						</td>
					</tr>

					<tr>
						<TD align="left" valign="top" class="ColumnBackground" colspan="2">		
							<div align="right">
							<bean:message key="psTotalWorth" />
							</div>
						</td>
						<TD align="left" valign="top" class="ColumnBackground" id="corpusTotal">
							<div align="center">
							</div>
						</td>
						<TD align="left" valign="top" class="ColumnBackground" colspan="2">
						</td>

					</tr>

					<tr>
						<TD align="left" valign="top" class="ColumnBackground">
							<div align="center">
							5.
							</div>
						</td>
						<TD align="left" valign="top" class="ColumnBackground">							
							<bean:message key="expenditureAmount" />
						</td>
						<TD align="left" valign="top" class="ColumnBackground">
							<html:text property="expenditureAmount" size="20" alt="expenditureAmount" name="ifForm"  maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="displaySurplusTotal()"/>
						</td>
						<TD align="left" valign="top" class="ColumnBackground" colspan="2">
							<div align="center">
<!--							<html:radio name="ifForm" property="availableExpAmount" value="Y" onclick="displaySurplusTotal()"></html:radio>				<bean:message key="yes" />&nbsp;&nbsp;

							<html:radio name="ifForm" property="availableExpAmount" value="N" onclick="displaySurplusTotal()"></html:radio>				<bean:message key="no" />&nbsp;&nbsp;-->
							</div>

						</td>
					</tr>

					<tr>
						<TD align="left" valign="top" class="ColumnBackground" colspan="2">		
							<div align="right">
							<bean:message key="surplusTotal" />&nbsp;<%=sysdate%>
							</div>
						</td>
						<TD align="left" valign="top" class="ColumnBackground" id="surplusTotal">
							<div align="center">
							</div>
							<html:hidden property="totalSurplusAmount" name="ifForm"/>	

						</td>
						<TD align="left" valign="top" class="ColumnBackground" colspan="2">

						</td>

					</tr>



					</table>
				</td>
			</tr>
	
			  </table>


      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center"><a href="javascript:submitForm('getExposureDetails.do?method=getExposureDetails')"><img src="images/OK.gif" alt="Ok" width="49" height="37" border="0"></a><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link></div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>