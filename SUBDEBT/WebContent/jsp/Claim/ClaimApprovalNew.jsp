<%@ page language="java"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.ParsePosition"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","displayClaimApprovalNew.do?method=displayClaimApprovalNew");%>
<%
String tempname="";
String mlidecision = "";
String thiskey = "";
String claimAmount = "";
String mlicomments = "";
String memId = "";
String claimrefnumber = "";
double approvedamount = 0.0;
double osAmntAsOnNPA = 0.0;
double appliedClaimAmnt = 0.0;
double eligibleAmnt = 0.0;
String url = "";
String cgclan = "";
java.text.SimpleDateFormat sdf = null;
String approvedClmAmount = null;
String forwardedUserId = "";
String comments ="";
double tcApprovedAmt = 0.0;
double wcApprovedAmt = 0.0;
double tcOutstanding = 0.0;
double wcOutstanding = 0.0;
double tcrecovery = 0.0;
double wcrecovery = 0.0;
double tcEligibleAmt = 0.0;
double wcEligibleAmt = 0.0;
double tcDeduction = 0.0;
double wcDeduction = 0.0;
double tcFirstInstallment = 0.0;
double wcFirstInstallment = 0.0;
String unitName = ""; 
String bid = "";
%>
<html:errors/>
<html:form name="cpTcDetailsForm" type="com.cgtsi.action.ClaimAction" action="saveClaimApproval.do?method=saveClaimApproval" method="POST">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <font color="#FF0000" size="2">The number of Claim Application(s) beyond your Approving Limit are : <bean:write property="limit" name="cpTcDetailsForm"/></font>
    <tr>
      <td class="FontStyle">&nbsp;</td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></td>
    <td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
  </tr>
  <tr>
    <td background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td>
                
    <table width="100%" border="0" cellspacing="1" cellpadding="0">
          <tr>
            <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="35%" class="Heading">&nbsp;Approve Claim Applications</td>
                  <td colspan="3"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                </tr>
                <tr>
                  <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                </tr>
              </table></td>
          </tr>
          
          <tr> 
	      <td colspan="4" class="SubHeading">&nbsp;<bean:message key="firstclaim"/></td>
          </tr>
          
          <tr>
            <td colspan="4"> <table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr class="HeadingBg">
                  <td valign="middle" class="HeadingBg"> <div align="center"><strong>&nbsp;Sr.<br>
                      No</strong></div></td>
                  <td class="HeadingBg"><div align="center">Member<br>
                      ID </div></td>
                  <td class="HeadingBg"><div align="center">
                      Claim <br>
                      Ref <br>
                      Number</div></td>
                <td class="HeadingBg"><div align="center">
                      Unit <br>
                      Name</div></td>    
               <td class="HeadingBg"><div align="center">TC Approved Amt<br><bean:message key="inRs"/></div></td>
               <td class="HeadingBg"><div align="center">WC Approved Amt<br><bean:message key="inRs"/></div></td>
               <td class="HeadingBg"><div align="center">TC O/S Amt as on NPA <br><bean:message key="inRs"/></div></td>
               <td class="HeadingBg"><div align="center">WC O/S Amt as on NPA<br><bean:message key="inRs"/></div></td>
               <td class="HeadingBg"><div align="center">TC Recovery as on NPA<br><bean:message key="inRs"/></div></td>
               <td class="HeadingBg"><div align="center">WC Recovery as on NPA<br><bean:message key="inRs"/></div></td>
               <td class="HeadingBg"><div align="center">TC Eligible Amt<br><bean:message key="inRs"/></div></td>
               <td class="HeadingBg"><div align="center">WC Eligible Amt<br><bean:message key="inRs"/></div></td>
               <td class="HeadingBg"><div align="center">TC First Inst<br><bean:message key="inRs"/></div></td>
               <td class="HeadingBg"><div align="center">WC First Inst<br><bean:message key="inRs"/></div></td>
               <td class="HeadingBg"><div align="center">TC Deductions<br><bean:message key="inRs"/></div></td>
               <td class="HeadingBg"><div align="center">WC Deductions<br><bean:message key="inRs"/></div></td>
               <td class="HeadingBg"><div align="center">Existing Remarks</div></td>
               <td class="HeadingBg"><div align="center">Net Payable as First Installment<br><bean:message key="inRs"/></div></td>
               <td align="center" valign="top"> <div align="center">
                      <p class="HeadingBg"><strong>&nbsp;Decision</strong></p>
                    </div></td>		
               <td class="HeadingBg"><div align="center">Approved Claim Amount<br><bean:message key="inRs"/></div></td>
               <td class="HeadingBg"><div align="center">Comments</div></td>
                </tr>
                <%
               
                int j=0;
                %>
              <logic:iterate id="object" name="cpTcDetailsForm"  property="firstInstallmentClaims"   indexId="index">
		         <%
                com.cgtsi.claim.ClaimDetail claimdetail = (com.cgtsi.claim.ClaimDetail)object;
                memId = claimdetail.getMliId();
                claimrefnumber = claimdetail.getClaimRefNum();
             //   url = "displayClaimRefNumberDtlsNew.do?method=displayClaimRefNumberDtlsNew&"+ClaimConstants.CLM_CLAIM_REF_NUMBER+"="+claimrefnumber;           
                  url = "displayClmApplicationDtlModified.do?method=displayClmApplicationDtlModified&"+ClaimConstants.CLM_CLAIM_REF_NUMBER+"="+claimrefnumber;           
            //  System.out.println("url:"+url);
                unitName = claimdetail.getSsiUnitName();
                bid = claimdetail.getBorrowerId();
                tcApprovedAmt = claimdetail.getTcApprovedAmt();
                wcApprovedAmt = claimdetail.getWcApprovedAmt();
                tcOutstanding = claimdetail.getTcOutstanding();
                wcOutstanding = claimdetail.getWcOutstanding();
                tcrecovery = claimdetail.getTcrecovery();
                wcrecovery = claimdetail.getWcrecovery();
                tcEligibleAmt = claimdetail.getTcClaimEligibleAmt();
                wcEligibleAmt = claimdetail.getWcClaimEligibleAmt();
                tcDeduction = claimdetail.getAsfDeductableforTC();
                wcDeduction = claimdetail.getAsfDeductableforWC();
                tcFirstInstallment = claimdetail.getTcFirstInstallment();
                wcFirstInstallment = claimdetail.getWcFirstInstallment();
                approvedamount = claimdetail.getApplicationApprovedAmount();
                String id1 = "MEMBERID#" + j;
                String id2 = "CLMREFNUMBERID#" + j;                
                approvedClmAmount = claimdetail.getTotalAmtPayNow();
                comments = claimdetail.getComments();
              //  System.out.println("Comments:"+comments);
                %>
                <tr class="TableData">
                 <td align="left" valign="top" class="tableData" width="98"><div align="center">&nbsp;<%= Integer.parseInt(index+"")+1%></div></td>
                 <td id="<%=id1%>"><%=memId%></td>
                  <td id="<%=id2%>"><!--<%tempname="approveClaims("+claimrefnumber+")";%> --> <a href="<%=url%>"><%=claimrefnumber%></a></td>
		  <%
		      String clmRefNumIndex = "CLMREFNUM##" + j;
		  %>
		  <html:hidden property = "<%=clmRefNumIndex%>" name="cpTcDetailsForm" value="<%=claimrefnumber%>"/>
            <td><div align="right"><%=unitName%></div></td>
           <td><div align="right"><%=tcApprovedAmt%></div></td>
           <td><div align="right"><%=wcApprovedAmt%></div></td>
           <td><div align="right"><%=tcOutstanding%></div></td>
           <td><div align="right"><%=wcOutstanding%></div></td>
           <td><div align="right"><%=tcrecovery%></div></td>
           <td><div align="right"><%=wcrecovery%></div></td>
           <td><div align="right"><%=tcEligibleAmt%></div></td>
           <td><div align="right"><%=wcEligibleAmt%></div></td>
           <td><div align="right"><%=tcFirstInstallment%></div></td>
           <td><div align="right"><%=wcFirstInstallment%></div></td>
           <td><div align="right"><%=tcDeduction%></div></td>
           <td><div align="right"><%=wcDeduction%></div></td>
           <td><div align="right"><%=comments%></div></td>
           <td><div align="right"><%=(tcFirstInstallment+wcFirstInstallment)-(tcDeduction+wcDeduction)%></div></td>
         	<td>
				  <%
				  thiskey=ClaimConstants.FIRST_INSTALLMENT+ClaimConstants.CLM_DELIMITER1+memId+ClaimConstants.CLM_DELIMITER1+claimrefnumber;
				  mlidecision = "decision("+thiskey+")";
				  				  
				  %>
           <%tempname="approveClaims("+claimrefnumber+")";%>
     <!--    <html:select property="<%=tempname%>" name="cpTcDetailsForm">
														<html:option value="">Select</html:option>
                            <html:option value="AP">Accept</html:option>		
                            <html:option value="RE">Reject</html:option>
													</html:select> -->
      	<html:select property="<%=mlidecision%>" name="cpTcDetailsForm">
						<html:option value="">Select</html:option>
            <html:option value="AP">Accept</html:option>		
            <html:option value="RE">Reject</html:option>
         </html:select>   
				  </td>
				  <%
				  claimAmount = "approvedClaimAmount("+thiskey+")";
				  %>
				  <td align="center">				  
				  <html:text property="<%=claimAmount%>" name="cpTcDetailsForm" value="<%=(tcFirstInstallment+wcFirstInstallment)-(tcDeduction+wcDeduction)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)"/>					
				  </td>
				  <%
				  mlicomments = "remarks("+thiskey+")";
				  %>
                  		<td align="center">
                        <html:textarea property="<%=mlicomments%>" name="cpTcDetailsForm"/>					
                  </td>
                </tr>
             
                <%j++;%>
                </logic:iterate>
             <!--   <html:hidden property = "firstClmDtlIndexValue" name="cpTcDetailsForm" value="<%=String.valueOf(j)%>"/> -->
              </table></td>
          </tr>
         
     <tr>
	<td><br></td>
     </tr>                       
     <tr>
      <td colspan="4"> <table width="100%" border="0" cellspacing="1" cellpadding="0">
	</table></td>
    </tr> 
  
          <tr align="center" valign="baseline">
            <td colspan="4"> 
            <div align="center">  
      <!--    <% url = "saveClaimApproval.do?method=saveClaimApproval";
         System.out.println("url:"+url);
         %>
        
         <A href="<%=url%>"><img src="images/OK.gif" alt="Apply" width="49" height="37" border="0"></A> -->
         <A href="javascript:submitForm('saveClaimApproval.do?method=saveClaimApproval')">
							<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A> 
         <a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></a></div></td>
          </tr>
        </table></td>
    <td background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr>
    <td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
    <td background="images/TableBackground2.gif"><div align="center"></div></td>
    <td align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
  </tr>
</table>
</html:form>
</body>
</html>
