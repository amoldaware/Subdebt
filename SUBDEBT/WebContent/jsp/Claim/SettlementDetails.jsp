<%@ page language="java"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ page import="com.cgtsi.actionform.ClaimActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","getSettlementMemId.do?method=getSettlementMemId");%>

<%
String memberId = "";
String borrowerId = "";
String cgclan = "";
double osnpa = 0.0;
double apprvdclmamnt = 0.0;
String tierone = "";
double tiertwo = 0.0;
double recoveramnt = 0.0;
String penaltyAmntStr = "";
String firstSettlementAmt = "";
String finalSettlementFlag = "";
String penaltyFirstSettlement = "";
String pendingFrmMLI = "CALCULATE";
String secondSettlementAmt = "";
String penaltySecondSettlement = "";
java.util.Date tierOneDt = null;
java.util.Date tierTwoDt = null;
String tierOneInstlmntDt = "";
String tierOneDtStringFormat = "";
String firstSettlementDt = "";
String secondSettlementDt = "";
%>
<body onLoad="calculateFirstSettlementAmount(),calculateSecondSettlementAmount()">
<html:errors/>
<html:form action="saveSettlementDetails?method=saveSettlementDetails" method="POST" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></td>
    <td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
  </tr>
  <tr>
    <td background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td>
    <DIV align="right">			
      		<A HREF="javascript:submitForm('helpProcessSettlementDtls.do')">
      	        HELP</A>
      </DIV>                
    <table width="100%" border="0" cellspacing="1" cellpadding="0">
          <tr> 
            <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="250" class="Heading">&nbsp;Settlement Details for <%=(String)request.getParameter("MEMBERID")%></td>
                  <td colspan="3"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                </tr>
                <tr> 
                  <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                </tr>
              </table></td>
          </tr>
          <logic:notEqual property="firstCounter" value="0" name="cpTcDetailsForm">
          <tr> 
            <td colspan="4" ><table width="100%" border="0" cellspacing="1" cellpadding="0">                
                <tr> 
		      <td colspan="4" class="SubHeading"><bean:message key="sttlmntfirstinstallment"/></td>
                </tr>
                <tr colspan="2" class="HeadingBg"> 
                  <td  valign="middle" class="HeadingBg"> <div align="center"><strong>&nbsp;<bean:message key="SerialNumber"/></strong></div></td>
                  <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="borrowerID"/></div></td>
                  <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="cgclannumber"/></div></td>                  
                  <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="totalosamntasonnpa"/></div></td>
		  <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="cprecoveredamnt"/></div>
		  <div align="center"></div></td>
                  <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="cpapprvdclmamnt"/></div></td>                  
                  <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="cpfinalinstllmntflag"/></div></td>
                  <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="cppenaltyfee"/><br><bean:message key="inRs"/></div></td>
                  <td  align="center" valign="top"><div align="center">&nbsp;<bean:message key="cpbalanceamnt"/></div></td>                  
                  <td  align="center" valign="top"><div align="center">&nbsp;<bean:message key="cpsettlemntdt"/></div></td>
                  <td  align="center" valign="top">&nbsp;<bean:message key="cpsettleamntamnt"/></td>
                </tr>
                <%
                 int i=1;
                 int j=0;
                %>
                <logic:iterate property="settlementsOfFirstClaim" name="cpTcDetailsForm" id="object">
                <%                
                com.cgtsi.claim.SettlementDetail clmfirstsettlementdtl = (com.cgtsi.claim.SettlementDetail)object;
                borrowerId = clmfirstsettlementdtl.getCgbid();
                cgclan = clmfirstsettlementdtl.getCgclan();
                osnpa = clmfirstsettlementdtl.getOsAmtAsonNPA();
                apprvdclmamnt = clmfirstsettlementdtl.getApprovedClaimAmt();
                tierone = new Double(clmfirstsettlementdtl.getTierOneSettlement()).toString();
                tiertwo = clmfirstsettlementdtl.getTierTwoSettlement();
                recoveramnt = clmfirstsettlementdtl.getRecoveryAmt();
                String id1 = "BORROWERID#" + j;
                String id2 = "cgclan#" + j;
                String id3 = "ApprovedAmount#" + j;
                penaltyAmntStr = new Double(clmfirstsettlementdtl.getPenaltyAmnt()).toString();
                %>                
                <tr class="TableData"> 
                  <td><%=i%></div></td>
		  <%
		      String borrowerIndex = "BORROWERID##" + j;
		  %>
		  <html:hidden property = "<%=borrowerIndex%>" name="cpTcDetailsForm" value="<%=borrowerId%>"/>                  
                  <td><A href="javascript:submitForm('showBorrowerDetailsLink.do?method=showBorrowerDetailsLink&formFlag=closure&bidLink=<%=borrowerId%>')"><%=borrowerId%></A></td>
                  <td id="<%=id2%>"><%=cgclan%></td>
                  <td><div align="center"><%=osnpa%></div></td>
                  <td><div align="center"><%=recoveramnt%></div></td>
                  <td id="<%=id3%>"><%=apprvdclmamnt%></td>
                  <%
                  finalSettlementFlag = "finalSettlementFlags("+ClaimConstants.FIRST_INSTALLMENT+ClaimConstants.CLM_DELIMITER1+borrowerId+ClaimConstants.CLM_DELIMITER1+cgclan+")";
                  %>
                  <td><html:radio property="<%=finalSettlementFlag%>" name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_YES_FLAG%>"><bean:message key="otsyes"/></html:radio>
                  <html:radio property="<%=finalSettlementFlag%>" name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_NO_FLAG%>"><bean:message key="otsno"/></html:radio>
                  </td>
                  <%
                  penaltyFirstSettlement = "penaltyFees("+ClaimConstants.FIRST_INSTALLMENT + ClaimConstants.CLM_DELIMITER1+borrowerId +ClaimConstants.CLM_DELIMITER1+ cgclan +")";
                  %>
                  <%
		    pendingFrmMLI = "pendingAmntsFromMLI("+ClaimConstants.FIRST_INSTALLMENT + ClaimConstants.CLM_DELIMITER1+borrowerId +ClaimConstants.CLM_DELIMITER1+ cgclan +")";
                  %>
                  <td><div align="right"><html:text property="<%=penaltyFirstSettlement%>" name="cpTcDetailsForm"  maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur= "javascript:calculateFirstSettlementAmount()"/></div></td>
                  <td><html:text property="<%=pendingFrmMLI%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur= "javascript:calculateFirstSettlementAmount()"/></td>
                  <%
                  firstSettlementDt ="settlementDates(" + ClaimConstants.FIRST_INSTALLMENT + ClaimConstants.CLM_DELIMITER1 + borrowerId + ClaimConstants.CLM_DELIMITER1 + cgclan+")";
                  %>
                  <td><html:text property="<%=firstSettlementDt%>" maxlength="10" name="cpTcDetailsForm"/><!-- <img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.<%=firstSettlementDt%>')" align="center"> --> </td>
                  <%
                  firstSettlementAmt = "settlementAmounts("+ ClaimConstants.FIRST_INSTALLMENT + ClaimConstants.CLM_DELIMITER1+borrowerId +ClaimConstants.CLM_DELIMITER1 +cgclan +")";
                  %>
                  <td><html:text property="<%=firstSettlementAmt%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
                </tr>
                <%
                 i++;
                 j++;
                %>
                </logic:iterate>
                <html:hidden property = "firstSettlementIndexValue" name="cpTcDetailsForm" value="<%=String.valueOf(j)%>"/>
              </table></td>
          </tr>                             
            </logic:notEqual>               
          <tr>
	      <td><br></td>
          </tr>          
          
          <logic:notEqual property="secondCounter" value="0" name="cpTcDetailsForm">
	    <tr> 
	      <td colspan="4" class="SubHeading">&nbsp;<bean:message key="sttlmntsecondinstallment"/></td>
	    </tr>
	    <tr> 
	      <td colspan="4" ><table width="100%" border="0" cellspacing="1" cellpadding="0">
		  <tr colspan="2" class="HeadingBg"> 
		    <td valign="middle" class="HeadingBg"> <div align="center"><strong>&nbsp;<bean:message key="SerialNumber"/></strong></div></td>
		    <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="borrowerID"/></div></td>
		    <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="cgclannumber"/></div></td>                  
		    <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="totalosamntasonnpa"/></div></td>
		    <td  class="HeadingBg"><div align="center"><strong>&nbsp;<bean:message key="cprecoveredamnt"/></strong></div>
		      <div align="center"><strong></strong></div></td>
		    <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="cpapprvdclmamnt"/></div></td>
		    <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="cpcgtsisettlement"/></div></td>			    
		    <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="cpfinalinstllmntflag"/></div></td>
		    <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="cppenaltyfee"/><br><bean:message key="inRs"/></div></td>
		    <td  align="center" valign="top"><div align="center">&nbsp;<bean:message key="cpbalanceamnt"/></div></td>
		    <td  align="center" valign="top"><div align="center">&nbsp;<bean:message key="cpsettlemntdt"/></div></td>
		    <td  align="center" valign="top">&nbsp;<bean:message key="cpsettleamntamnt"/></td>
		  </tr>
		  <%int k=0;%>
		  <logic:iterate property="settlementsOfSecondClaim" name="cpTcDetailsForm" id="object">
		  <%
		  com.cgtsi.claim.SettlementDetail clmsecsettlementdtl = (com.cgtsi.claim.SettlementDetail)object;
		  borrowerId = clmsecsettlementdtl.getCgbid();
		  cgclan = clmsecsettlementdtl.getCgclan();
		  osnpa = clmsecsettlementdtl.getOsAmtAsonNPA();
		  apprvdclmamnt = clmsecsettlementdtl.getApprovedClaimAmt();
		  tierone = new Double(clmsecsettlementdtl.getTierOneSettlement()).toString();
		  tiertwo = clmsecsettlementdtl.getTierTwoSettlement();
		  recoveramnt = clmsecsettlementdtl.getRecoveryAmt();
                  tierOneDt = clmsecsettlementdtl.getTierOneSettlementDt();
                  tierOneDtStringFormat = (new java.text.SimpleDateFormat("dd/mm/yyyy")).format(tierOneDt);
		  String id4 = "BORROWERID@" + k;
		  String id5 = "cgclan@" + k;
		  String id6 = "ApprovedAmount@" + k;
		  penaltyAmntStr = new Double(clmsecsettlementdtl.getPenaltyAmnt()).toString();
		  %>
		  <tr class="TableData"> 
		    <td height="40"> <div align="center">1</div></td>
		    <%
		         String borrowerIndex = "BORROWERID@" + k;
		    %>	
		    <html:hidden property = "<%=borrowerIndex%>" name="cpTcDetailsForm" value="<%=borrowerId%>"/>
		    <td><A href="javascript:submitForm('showBorrowerDetailsLink.do?method=showBorrowerDetailsLink&formFlag=closure&bidLink=<%=borrowerId%>')"><%=borrowerId%></A></td>
		    <td id="<%=id5%>"><%=cgclan%></td>
		    <td><div align="center"><%=osnpa%></div></td>
		    <td><div align="center"><%=recoveramnt%></div></td>
		    <td id="<%=id6%>"><%=apprvdclmamnt%></td>
		    <td><div align="right"><%=tierone%></div></td>		    
                    <%
		     finalSettlementFlag = "finalSettlementFlags("+ClaimConstants.SECOND_INSTALLMENT+ClaimConstants.CLM_DELIMITER1+borrowerId+ClaimConstants.CLM_DELIMITER1+cgclan+")";
		    %>
		    <td><html:radio property="<%=finalSettlementFlag%>" name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_YES_FLAG%>"><bean:message key="otsyes"/></html:radio>
		    <html:radio property="<%=finalSettlementFlag%>" name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_NO_FLAG%>"><bean:message key="otsno"/></html:radio></td>		    
		    <%
		    penaltySecondSettlement = "penaltyFees("+ClaimConstants.SECOND_INSTALLMENT+ClaimConstants.CLM_DELIMITER1+borrowerId + ClaimConstants.CLM_DELIMITER1+cgclan +")";
		    %>
		    <%
		    pendingFrmMLI = "pendingAmntsFromMLI("+ClaimConstants.SECOND_INSTALLMENT + ClaimConstants.CLM_DELIMITER1+borrowerId +ClaimConstants.CLM_DELIMITER1+ cgclan +")";
                   %>
		    <td><div align="right"><html:text property="<%=penaltySecondSettlement%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur= "javascript:calculateSecondSettlementAmount()"/></div></td>
	            <td><html:text property="<%=pendingFrmMLI%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur= "javascript:calculateSecondSettlementAmount()"/></td>
	            <%
	            secondSettlementDt = "settlementDates(" + ClaimConstants.SECOND_INSTALLMENT + ClaimConstants.CLM_DELIMITER1 + borrowerId + ClaimConstants.CLM_DELIMITER1 + cgclan +")";
	            %>
	            <td><html:text property="<%=secondSettlementDt%>" maxlength="10" name="cpTcDetailsForm"/><!-- <img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.<%=secondSettlementDt%>')" align="center" >--> </td>
	            <%
	            secondSettlementAmt = "settlementAmounts("+ClaimConstants.SECOND_INSTALLMENT+ClaimConstants.CLM_DELIMITER1+borrowerId+ClaimConstants.CLM_DELIMITER1+cgclan+")";
	            %>
	            <td><html:text property="<%=secondSettlementAmt%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>

		  </tr>
		  <%k++;%>
		  </logic:iterate>
		  <html:hidden property = "secondSettlementIndexValue" name="cpTcDetailsForm" value="<%=String.valueOf(k)%>"/>
		</table></td>
	    </tr>
	    </logic:notEqual>
          <tr> 
            <td colspan="4" class="SubHeading">&nbsp;</td>
          </tr>
          <tr align="center" valign="baseline"> 
            <td colspan="4"> 
            <div align="left">
            <%
               ClaimActionForm claimForm = (ClaimActionForm)session.getAttribute("cpTcDetailsForm") ;
               memberId = claimForm.getMemberId();
               // System.out.println("Member Id from Claim Action Form :" +memberId);
            %>
            <a href="showReviewAudit.do?method=showReviewAudit&MemberId=<%=memberId%>">Auditor's Reviews</a>
            </div>
            <div align="center">            
            <A href="javascript:history.back()"><img src="images/Back.gif" alt="Back" width="49" height="37" border="0"></a>
            <A href="javascript:submitForm('saveSettlementDetails.do?method=saveSettlementDetails')"><img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></a>
            <a href="javascript:document.cpTcDetailsForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></a>
            <A href="javascript:submitForm('getAllPendingSettlements.do?method=getAllPendingSettlements')"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></a></div></td>
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
