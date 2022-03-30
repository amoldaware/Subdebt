<%@ page language="java"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","saveGenerateCSAOption.do?method=saveGenerateCSAOption");%>

<%
String paymentIdKey = "";
String memberId = "";
String cgclan = "";
String cgcsa = "";
String settlmntAmnt = "";
String settlmntDt = "";
int i = 1;
int j = 1;
%>

<html:errors/>
<html:form action="savePaymentVoucher.do?method=savePaymentVoucher" method="POST" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="1" cellpadding="0">
    <tr> 
      <td><div align="center"> 
          <table width="0%" border="0" cellspacing="0" cellpadding="0">
            <tr align="center"> 
              <td> <p><img src="images/RecordAdded.gif" width="111" height="147"> 
                </p></td>
              <td><font face="Arial, Helvetica, sans-serif" size="4" color="#003399">&nbsp;<bean:message key="csacreatedmsg"/><br>
                </font> <hr noshade> <font size="2" face="Arial, Helvetica, sans-serif">&nbsp; 
                </font> </td>
            </tr>
          </table>
        </div></td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
    </tr>
    <tr> 
      <td ><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
            <td background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></td>
            <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
          </tr>
          <tr> 
            <td background="images/TableVerticalLeftBG.gif">&nbsp;</td>
            <td><table width="100%" border="0" cellspacing="1" cellpadding="0">
            <logic:notEqual property="firstCounter" value="0" name="cpTcDetailsForm">
	    <tr> 
		 <td class="SubHeading" colspan="4"> &nbsp;<bean:message key="adviceforfirstsettlmnt"/></td>
	   </tr>
                <tr valign="middle" class="HeadingBg"> 
                  <td  class="HeadingBg"> 
                    <div align="center"><strong>&nbsp;<bean:message key="SerialNumber"/></strong></div></td>
                  <td > 
                    <div align="center">&nbsp;<bean:message key="cpmemId"/></div></td>
                  <td > 
                    <div align="center"><strong>&nbsp;<bean:message key="cgclannumber"/></strong></div></td>                  
                  <td > 
                    <div align="center">&nbsp;<bean:message key="sadsettlement"/></div></td>
                   <td width="10%" > 
		     <div align="center"><strong>&nbsp;<bean:message key="cpdate"/></strong></div></td>
		   <td > 
		     <div align="center">&nbsp;<bean:message key="paymentvoucherid"/></div></td>
                </tr>
                <logic:iterate property="checkedFirstSettlmntAdviceDtls" name="cpTcDetailsForm" id="object">
                <%
                java.util.HashMap firstSettlmntDtl = (java.util.HashMap)object;
                memberId = (String)firstSettlmntDtl.get(ClaimConstants.CLM_MEMBER_ID);
                cgclan = (String)firstSettlmntDtl.get(ClaimConstants.CLM_CGCLAN);
                // cgcsa = (String)firstSettlmntDtl.get(ClaimConstants.CLM_CGCSA);
                settlmntAmnt = ((Double)firstSettlmntDtl.get(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_AMNT)).toString();
                settlmntDt = (String)firstSettlmntDtl.get(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_DT);
                %>
                <tr class="TableData"> 
                  <td><div align="center"><%=i%></div></td>                  
                  <td>&nbsp;<%=memberId%></td>
                  <td><div align="left">&nbsp;<%=cgclan%></div></td>                  
                  <td><div align="right">&nbsp;<%=settlmntAmnt%></div></td>
                  <td>&nbsp;<%=settlmntDt%></td>
                  <%
		    paymentIdKey = "paymentVoucherIds("+cgclan+ClaimConstants.CLM_DELIMITER1+ClaimConstants.FIRST_INSTALLMENT+")"; 
		   %>
		   <td><div align="center">&nbsp;<html:text property="<%=paymentIdKey%>" name="cpTcDetailsForm"/></div></td>
                </tr>
                <%i++;%>
                </logic:iterate>
                </logic:notEqual>
                                <tr>
					<td><br></tr>
		  		</tr>
		<logic:notEqual property="secondCounter" value="0" name="cpTcDetailsForm">		  		
                <tr> 
			 <td class="SubHeading" colspan="4"> &nbsp;<bean:message key="adviceforsecondsettlmnt"/></td>
		 </tr>

                
		<tr valign="middle" class="HeadingBg"> 
		  <td  class="HeadingBg"> 
		    <div align="center"><strong>&nbsp;<bean:message key="SerialNumber"/></strong></div></td>
		  <td > 
		    <div align="center">&nbsp;<bean:message key="cpmemId"/></div></td>
		  <td > 
		    <div align="center"><strong>&nbsp;<bean:message key="cgclannumber"/></strong></div></td>		  
		  <td > 
		    <div align="center">&nbsp;<bean:message key="sadsettlement"/></div></td>
		   <td width="10%" > 
		     <div align="center"><strong>&nbsp;<bean:message key="cpdate"/></strong></div></td>
		   <td > 
		     <div align="center">&nbsp;<bean:message key="paymentvoucherid"/></div></td>
		</tr>
		
		<logic:iterate property="checkedSecondSettlmntAdviceDtls" name="cpTcDetailsForm" id="object">
		<%
		java.util.HashMap secondSettlmntDtl = (java.util.HashMap)object;
		memberId = (String)secondSettlmntDtl.get(ClaimConstants.CLM_MEMBER_ID);
		cgclan = (String)secondSettlmntDtl.get(ClaimConstants.CLM_CGCLAN);
		// cgcsa = (String)secondSettlmntDtl.get(ClaimConstants.CLM_CGCSA);
		settlmntAmnt = ((Double)secondSettlmntDtl.get(ClaimConstants.CLM_SETTLMNT_SECOND_SETTLMNT_AMNT)).toString();
		settlmntDt = (String)secondSettlmntDtl.get(ClaimConstants.CLM_SETTLMNT_SECOND_SETTLMNT_DT);
		%>
		<tr class="TableData"> 
		  <td><div align="center">&nbsp;<%=j%></div></td>                  
		  <td>&nbsp;<%=memberId%></td>
		  <td><div align="left">&nbsp;<%=cgclan%></div></td>		  
		  <td><div align="right">&nbsp;<%=settlmntAmnt%></div></td>
		  <td>&nbsp;<%=settlmntDt%></td>
		  <%
		    paymentIdKey = "paymentVoucherIds("+cgclan+ClaimConstants.CLM_DELIMITER1+ClaimConstants.SECOND_INSTALLMENT+")"; 
		  %>
		   <td><div align="center">&nbsp;<html:text property="<%=paymentIdKey%>" name="cpTcDetailsForm"/></div></td>
		</tr>                                                                
	      <%j++;%>
             </logic:iterate>
             </logic:notEqual>             
              </table></td>
            <td background="images/TableVerticalRightBG.gif">&nbsp;</td>
          </tr>
          <tr> 
            <td align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
            <td background="images/TableBackground2.gif">&nbsp;</td>
            <td align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
          </tr>
        </table></td>
    </tr>
    <tr> 
      <td><div align="center">
      <a href="javascript:document.cpTcDetailsForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></a>
      <A href="javascript:submitForm('savePaymentVoucher.do?method=savePaymentVoucher')"><img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></a>
      <a href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></a>
      </div></td>
    </tr>
  </table>
  <br>
  <div align="center"> </div>
</html:form>
</body>
</html>
