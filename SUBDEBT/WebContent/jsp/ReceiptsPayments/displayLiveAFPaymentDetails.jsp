<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","danPaymentDetails.do?method=getLiveAFDANsPaymentDetails");
String danNo ;
int danIndex = 0;
String hiddenFieldName;
String remarks ;
%>

<script>
	function savePaymentDetails(){
		var mode = document.forms[0].modeOfPayment.value;
		//alert(mode);
		if('NEFT/RTGS' != mode){
			if(document.forms[0].paymentDate.value == ''){
				alert('Please enter Payment date.');
				return false;
			}
			if(document.forms[0].instrumentNo.value == ''){
				alert('Please enter Instrument number.');
				return false;
			}
			if(document.forms[0].instrumentDate.value == ''){
				alert('Please enter Instrument date.');
				return false;
			}
			if(document.forms[0].drawnAtBank.value == ''){
				alert('Please enter Bank name.');
				return false;
			}
			if(document.forms[0].drawnAtBranch.value == ''){
				alert('Please enter Branch name.');
				return false;
			}
			if(document.forms[0].payableAt.value == ''){
				alert('Please enter Bank Location.');
				return false;
			}
		}
		
		//alert('Hello');
		document.forms[0].target = "_self";
		document.forms[0].method = "POST";
		document.forms[0].action = "submitAFDANsPaymentDetails.do?method=submitAFDANsPaymentDetails";
		document.forms[0].submit();
		
		
	}
	
	function validateInstrument(){
		var instrumentNo = document.forms[0].instrumentNo.value;
		
		if(instrumentNo.length < 6 || instrumentNo.length > 6){
			alert("Invalid Instrument Number<" +instrumentNo +">, number should be 6 digit");
			return false;
		}
		return true;
	}

	function typeOfPayment(){
		var mode = document.forms[0].modeOfPayment.value;
		//alert(mode);
		if('NEFT/RTGS' == mode){
			document.forms[0].instrumentNo.disabled=true;
			document.forms[0].paymentDate.disabled=true;
			document.forms[0].drawnAtBank.disabled=true;
			document.forms[0].drawnAtBranch.disabled=true;
			document.forms[0].payableAt.disabled=true;
			document.forms[0].instrumentDate.disabled=true;
		}else{
			document.forms[0].instrumentNo.disabled=false;
			document.forms[0].paymentDate.disabled=false;
			document.forms[0].drawnAtBank.disabled=false;
			document.forms[0].drawnAtBranch.disabled=false;
			document.forms[0].payableAt.disabled=false;
			document.forms[0].instrumentDate.disabled=false;
		}
		return true;
	}

</script>

  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <html:errors />
<html:form action="submitAFDANsPaymentDetails.do?method=submitAFDANsPaymentDetails" method="POST" focus="modeOfPayment">
  
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
					<TD colspan="10"> 
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
				  <html:select property="modeOfPayment" name="rpAllocationForm" onchange="typeOfPayment();">												
						<html:options name="rpAllocationForm" property="instruments" />
				  </html:select>
			  </td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<bean:message key="collectingBankName"/></td>
              <td class="tableData"><bean:write name="rpAllocationForm" property="collectingBankName"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<bean:message key="collectingBankBranch"/></td>
              <td class="tableData"><bean:write property="collectingBankBranch" name="rpAllocationForm"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<bean:message key="CGTSIAccountHoldingBranch"/></td>
              <td class="tableData"><bean:write name="rpAllocationForm" property="accountNumber"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;<span id="instdate" name="instdate">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="paymentDate"/></span>
					&nbsp;<i>(Date should be in DD/MM/YYYY format.)</i></td>
              <td class="tableData"><table cellpadding="0" cellspacing="0">
                <tr>
                  <td><html:text property="paymentDate" size="10" alt="Payment Date" name="rpAllocationForm" maxlength="10"/></td>
                  <td><img src="images/CalendarIcon.gif" width="20" align="center"
                  onClick="showCalendar('rpAllocationForm.paymentDate')"></td>
                </tr>

              </table>
              </td>
            </tr>
            
            
			<TR>
					<TD colspan="10"> 
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="20%" class="Heading"><bean:message key="instrumentDetails" /></TD>
							<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
						</TR>
						<TR>
							<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
						</TR>
						</TABLE>
					</TD>
			</TR>
			
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrumentNumber"/></td>
              <td class="tableData"><html:text property="instrumentNo" size="10" alt="Instrument Number" name="rpAllocationForm" maxlength="6" onchange="validateInstrument()" onkeypress="return numbersOnly(this, event)" /></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;<span id="instdate" name="instdate">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
			  <bean:message key="instrumentDate"/></span>
			&nbsp;<i>(Date should be in DD/MM/YYYY format.)</i></td>
              <td class="tableData"><table cellpadding="0" cellspacing="0">
                <tr>
                  <td><html:text property="instrumentDate" size="10" alt="Instrument Date" name="rpAllocationForm" maxlength="10"/></td>
                  <td><img src="images/CalendarIcon.gif" width="20" align="center"
                  onClick="showCalendar('rpAllocationForm.instrumentDate')"></td>
                </tr>
              </table>
              </td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<bean:message key="instrumentAmount"/></td>
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
            <tr><td align="left" colspan="4" valign="top" class="SubHeading">The Instrument should be Payable At Mumbai&nbsp;</td></tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="payableAt"/></td>
              <td class="tableData"><html:text property="payableAt" size="20" alt="Payable at" name="rpAllocationForm" maxlength="50"/></td>
            </tr>
          </table>
          </td>
        </tr>
        
        <tr>
        <td>
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
			<TR>
						<TD align="left" colspan="4" valign="top" class="SubHeading">
							Service Category : Other Taxable Services - Other Than The 119 LISTED
						</TD>
					</TR>
					<TR>
						
                        <td align="left" valign="top" class="ColumnBackground"><div align="center">&nbsp;Service Tax Number:</div></td>
						
						<TD align="left" valign="top" class="tableData"><div align="leftt">&nbsp;AAATC2613DSD001</div></td>
						
						<td align="left" valign="top" class="ColumnBackground"><div align="center">&nbsp;PAN Number:</div></td>
										
						<td align="left" valign="top" class="tableData"><div align="leftt">&nbsp;AAATC2613D</div></TD>
						
		</TR>
        </table>
        </td>
        </tr>
        
        
        
        <tr align="center" valign="baseline">
          <td>
			
			<!-- <a href="javascript:submitForm('submitAFDANsPaymentDetails.do?method=submitAFDANsPaymentDetails');"> -->
			<a href="#" onclick="savePaymentDetails();">
			<img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></a> 
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