<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","appropriateallocatePayments.do?method=submitSFDANPayments");
String danNo ;
int danIndex = 0;
String hiddenFieldName;
String remarks ;
%>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <html:errors />
<html:form action="appropriateallocatePayments.do?method=submitSFDANPayments" method="POST" focus="modeOfPayment">
  
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReceiptsPaymentsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="1" cellspacing="1">
				<TR>
					<TD colspan="12"> 
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="20%" class="Heading"><bean:message key="paymentDetails" /></TD>
							<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
						</TR>
						<TR>
							<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
						</TR>
						</TABLE>
					</TD>
				</TR>
        <tr>
          <td><table width="100%" border="0" cellspacing="1" cellpadding="0">
		  <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="modeOfPayment" /></td>
              <td class="tableData">
				  <html:select property="modeOfPayment" name="rpAllocationForm">
						<html:option value="">Select </html:option>
						<html:options name="rpAllocationForm" property="instruments" />
				  </html:select>
			  </td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground"><bean:message key="collectingBankName"/></td>
              <td class="tableData"><bean:write name="rpAllocationForm" property="collectingBankName"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground"><bean:message key="collectingBankBranch"/></td>
              <td class="tableData"><bean:write property="collectingBankBranch" name="rpAllocationForm"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground"><bean:message key="CGTSIAccountHoldingBranch"/></td>
              <td class="tableData"><bean:write name="rpAllocationForm" property="accountNumber"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;<span id="instdate" name="instdate">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="paymentDate"/></span></td>
              <td class="tableData"><table cellpadding="0" cellspacing="0">
                <tr>
                  <td><html:text property="paymentDate" size="20" alt="Payment Date" name="rpAllocationForm" maxlength="10"/></td>
                  <td><img src="images/CalendarIcon.gif" width="20" align="center"
                  onClick="showCalendar('rpAllocationForm.paymentDate')"></td>
                </tr>
              </table>
              </td>
            </tr>
            <tr>
              <td colspan="2"><img src="images/clear.gif" width="5" height="15"></td>
            </tr>
            <tr>
              <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="35%" class="Heading"><bean:message key="instrumentDetails"/></td>
                  <td><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                </tr>
                <tr>
                  <td colspan="2" class="Heading"><img src="images/clear.gif" width="5" height="5"></td>
                </tr>
              </table>
              </td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrumentNumber"/></td>
              <td class="tableData"><html:text property="instrumentNo" size="20" alt="Instrument Number" name="rpAllocationForm" maxlength="10"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;<span id="instdate" name="instdate">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrumentDate"/></span></td>
              <td class="tableData"><table cellpadding="0" cellspacing="0">
                <tr>
                  <td><html:text property="instrumentDate" size="20" alt="Instrument Date" name="rpAllocationForm" maxlength="10"/></td>
                  <td><img src="images/CalendarIcon.gif" width="20" align="center"
                  onClick="showCalendar('rpAllocationForm.instrumentDate')"></td>
                </tr>
              </table>
              </td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground"><bean:message key="instrumentAmount"/></td>
              <td class="tableData"><bean:write name="rpAllocationForm" property="instrumentAmount"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="drawnAtBank"/></td>
              <td class="tableData"><html:text property="drawnAtBank" size="20" alt="Drawn at Bank" name="rpAllocationForm" maxlength="100"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="drawnAtBranch"/></td>
              <td class="tableData"><html:text property="drawnAtBranch" size="20" alt="Drawn at Branch" name="rpAllocationForm" maxlength="50"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="payableAt"/></td>
              <td class="tableData"><html:text property="payableAt" size="20" alt="Payable at" name="rpAllocationForm" maxlength="50"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;<span id="dateOfRealisation" name="dateOfRealisation">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;Realisation Date</span></td>
              <td class="tableData"><table cellpadding="0" cellspacing="0">
                <tr>
                  <td><html:text property="dateOfRealisation" size="20" alt="dateOfRealisation" name="rpAllocationForm" maxlength="10"/></td>
                  <td><img src="images/CalendarIcon.gif" width="20" align="center"
                  onClick="showCalendar('rpAllocationForm.dateOfRealisation')"></td>
                </tr>
                
              </table>
              </td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;Realisation Amount</td>
              <td class="tableData"><html:text property="receivedAmount" size="20" alt="receivedAmount" name="rpAllocationForm" maxlength="10"/></td>
            </tr>
            
             <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="remarks"/> </td>
              <td class="tableData"><html:textarea cols="25" rows="4" style="font-size:12;" property="remarksforAppropriation"   alt="remarksforAppropriation" name="rpAllocationForm"/></td>
            </tr>
            
             </table>
          </td>
        </tr>
        
        <tr>
          <td><img src="clear.gif" width="5" height="15"></td>
        </tr>
        <tr align="center" valign="baseline">
          <td>
			<a href="javascript:submitForm('appropriateallocatePayments.do?method=appropriateallocatePayments');"><img src="images/Save.gif" alt="Save"
            width="49" height="37" border="0"></a>
		  <a href="javascript:document.rpAllocationForm.reset()">
          <img src="images/Reset.gif" alt="Reset" width="49" height="37"
            border="0"></a>
          </td>
        </tr>
      </table>
      </td>
		<TD width="20" background="images/TableVerticalRightBG.gif">&nbsp;</TD>
		
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
  </table>