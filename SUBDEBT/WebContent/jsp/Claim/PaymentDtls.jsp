<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","saveSettlementDetails.do?method=saveSettlementDetails");
String danNo ;
int danIndex = 0;
String hiddenFieldName;
String remarks ;
%>
<body onLoad="disableClaimCheque()">
<html:errors />
<html:form action="saveSettlementAndPaymentDtls.do" method="POST" >
  <table width="100%" border="0" cellspacing="0" cellpadding="0">      
    <tr>
      <td align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20"
      height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131"
      height="25"></td>
      <td background="TableBackground1.gif">&nbsp;</td>
      <td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif"
      width="23" height="31"></td>
    </tr>
    <tr>
      <td background="images/TableVerticalLeftBG.gif"></td>
      <td colspan="2"><table width="100%" border="0" cellspacing="1" cellpadding="0">
        <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="35%" class="Heading"><bean:message key="paymentDetails" /></td>
              <td><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
            </tr>
            <tr>
              <td colspan="2" class="Heading"><img src="images/clear.gif" width="5" height="5"></td>
            </tr>
          </table>
          </td>
        </tr>
        <tr>
          <td><table width="100%" border="0" cellspacing="1" cellpadding="0">
		  <tr>
              <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font><bean:message key="modeOfPayment" /></td>
              <td class="tableData">
				  <html:select property="modeOfPayment" name="cpTcDetailsForm" onchange="disableClaimCheque()">
						<html:option value="">Select </html:option>
						<html:options name="cpTcDetailsForm" property="instrumentTypes" />
				  </html:select>
			  </td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground"><bean:message key="collectingBankName"/></td>
              <td class="tableData"><bean:write name="cpTcDetailsForm" property="collectingBank.collectingBankName"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground"><bean:message key="collectingBankBranch"/></td>
              <td class="tableData"><bean:write property="collectingBank.branchName" name="cpTcDetailsForm"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground"><bean:message key="CGTSIAccountHoldingBranch"/></td>
              <td class="tableData"><bean:write name="cpTcDetailsForm" property="collectingBank.accNo"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;<span id="instdate" name="instdate"><bean:message key="paymentDate"/></span></td>
              <td class="tableData"><table cellpadding="0" cellspacing="0">
                <tr>
                  <td><html:text property="paymentDate" size="20" alt="Payment Date" name="cpTcDetailsForm"/></td>
                  <td><img src="images/CalendarIcon.gif" width="20" align="center"
                  onClick="showCalendar('cpTcDetailsForm.paymentDate')"></td>
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

				<TR>
					<TD align="left" valign="top" class="ColumnBackground">
						<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="chqBankName"/>
					</TD>
					<TD align="left" valign="top" class="TableData">
							<html:select property="bnkName" name="cpTcDetailsForm" >
								<html:option value="">Select</html:option>
								<html:options name="cpTcDetailsForm" property="banksList"></html:options>
							</html:select>	
					</TD>

				</TR>

            <tr>
              <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font><bean:message key="instrumentNumber"/></td>
              <td class="tableData"><html:text property="instrumentNo" size="20" alt="Instrument Number" name="cpTcDetailsForm" maxlength="10"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;<span id="instdate" name="instdate"><bean:message key="instrumentDate"/></span></td>
              <td class="tableData"><table cellpadding="0" cellspacing="0">
                <tr>
                  <td><html:text property="instrumentDate" size="20" maxlength="10" alt="Instrument Date" name="cpTcDetailsForm"/></td>
                  <td><img src="images/CalendarIcon.gif" width="20" align="center"
                  onClick="showCalendar('cpTcDetailsForm.instrumentDate')"></td>
                </tr>
              </table>
              </td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground"><bean:message key="instrumentAmount"/></td>
              <td class="tableData"><bean:write name="cpTcDetailsForm" property="instrumenAmount"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground"><bean:message key="drawnAtBank"/></td>
              <td class="tableData"><html:text property="drawnAtBank" size="20" alt="Drawn at Bank" name="cpTcDetailsForm"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground"><bean:message key="drawnAtBranch"/></td>
              <td class="tableData"><html:text property="drawnAtBranch" size="20" alt="Drawn at Branch" name="cpTcDetailsForm"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground"><bean:message key="payableAt"/></td>
              <td class="tableData"><html:text property="payableAt" size="20" alt="Payable at" name="cpTcDetailsForm"/></td>
            </tr>
          </table>
          </td>
        </tr>
        <tr>
          <td><img src="clear.gif" width="5" height="15"></td>
        </tr>
        <tr align="center" valign="baseline">
          <td>
			<a href="javascript:submitForm('saveSettlementAndPaymentDtls.do?method=saveSettlementAndPaymentDtls');"><img src="images/Save.gif" alt="Save"
            width="49" height="37" border="0"></a>
		  <a href="javascript:document.form1.reset()">
          <img src="images/Reset.gif" alt="Reset" width="49" height="37"
            border="0"></a>
          </td>
        </tr>
      </table>
      </td>
      <td background="images/TableVerticalRightBG.gif"></td>
    </tr>
    <tr>
      <td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif"
      width="20" height="15"></td>
      <td colspan="2" background="images/TableBackground2.gif"><div align="center"></div></td>
      <td align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23"
      height="15"></td>
    </tr>
	</html:form>
  </table>
  </body>