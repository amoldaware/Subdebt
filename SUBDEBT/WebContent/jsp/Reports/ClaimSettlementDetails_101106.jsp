<%@ page language="java"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","getSettlementMemId.do?method=getSettlementMemId");%>

<%
String borrowerId = "";
String cgclan = "";
double osnpa = 0.0;
double apprvdclmamnt = 0.0;
double tierone = 0.0;
double tiertwo = 0.0;
double recoveramnt = 0.0;
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
String voucherId = "";
SimpleDateFormat sdf = null;
%>

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
    <td><table width="100%" border="0" cellspacing="1" cellpadding="0">
          <tr> 
            <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="18%" class="Heading">&nbsp;Settlement Details</td>
                  <td colspan="3"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                </tr>
                <tr> 
                  <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                </tr>
              </table></td>
          </tr>
          <tr> 
            <td colspan="4" ><table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr> 
		      <td colspan="4" class="SubHeading">&nbsp;<bean:message key="sttlmntfirstinstallment"/></td>
                </tr>
                <tr colspan="2" class="HeadingBg"> 
                  <td  valign="middle" class="HeadingBg"> <div align="center"><strong>&nbsp;<bean:message key="SerialNumber"/></strong></div></td>
                  <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="borrowerID"/></div></td>
                  <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="cgclannumber"/></div></td>                  
                  <!-- <td  class="HeadingBg"><div align="center">&nbsp;Payment Voucher Id</div></td> -->
		  <td  class="HeadingBg"><div align="center">&nbsp;Settlement Amount</div><div align="center"><bean:message key="inRs"/></div>
		  <div align="center"></div></td>
                  <td  class="HeadingBg"><div align="center">&nbsp;Settlement Date</div></td>                                    
                </tr>
                <%int i=1;%>
                <logic:iterate property="settlementsOfFirstClaim" name="cpTcDetailsForm" id="object">
                <%
                sdf = new SimpleDateFormat("dd/MM/yyyy");
                com.cgtsi.claim.SettlementDetail clmfirstsettlementdtl = (com.cgtsi.claim.SettlementDetail)object;
                borrowerId = clmfirstsettlementdtl.getCgbid();
                cgclan = clmfirstsettlementdtl.getCgclan();
                voucherId = "";
                tierone = clmfirstsettlementdtl.getTierOneSettlement();
                tierOneDt = clmfirstsettlementdtl.getTierOneSettlementDt();
                if(tierOneDt != null)
                {
                    firstSettlementDt = sdf.format(tierOneDt);
                }
                %>                
                <tr class="TableData"> 
                  <td><%=i%></div></td>
                  <td>&nbsp;<%=borrowerId%></td>
                  <td>&nbsp;<%=cgclan%></td>
                  <!-- <td><div align="center"><%=voucherId%></div></td> -->
                  <td><div align="right"><%=tierone%></div></td>
                  <td><div align="center">&nbsp;<%=firstSettlementDt%></div></td>
                </tr>
                <%i++;%>
                </logic:iterate>
                <%
                sdf = null;
                %>
              </table></td>
          </tr>                             
                           
          <tr>
	      <td><br></td>
          </tr>          
          
          
	    <tr> 
	      <td colspan="4" class="SubHeading">&nbsp;<bean:message key="sttlmntsecondinstallment"/></td>
	    </tr>
	    <tr> 
	      <td colspan="4" ><table width="100%" border="0" cellspacing="1" cellpadding="0">
		  <tr colspan="2" class="HeadingBg"> 
		    <td valign="middle" class="HeadingBg"> <div align="center"><strong>&nbsp;<bean:message key="SerialNumber"/></strong></div></td>
		    <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="borrowerID"/></div></td>
		    <td  class="HeadingBg"><div align="center">&nbsp;<bean:message key="cgclannumber"/></div></td>                  
		    <!-- <td  class="HeadingBg"><div align="center">&nbsp;Payment Voucher</div></td> -->
		    <td  class="HeadingBg"><div align="center"><strong>&nbsp;Second Settlement Amount</strong></div><div align="center"><bean:message key="inRs"/></div>
		      <div align="center"><strong></strong></div></td>
		    <td  class="HeadingBg"><div align="center">&nbsp;Second Settlement Date</div></td>
		  </tr>
		  <%i=1;%>
		  <logic:iterate property="settlementsOfSecondClaim" name="cpTcDetailsForm" id="object">
		  <%
		  sdf = new SimpleDateFormat("dd/MM/yyyy");
		  com.cgtsi.claim.SettlementDetail clmsecsettlementdtl = (com.cgtsi.claim.SettlementDetail)object;
		  borrowerId = clmsecsettlementdtl.getCgbid();
		  cgclan = clmsecsettlementdtl.getCgclan();
		  voucherId = clmsecsettlementdtl.getVoucherId();
		  tiertwo = clmsecsettlementdtl.getTierTwoSettlement();
		  tierTwoDt = clmsecsettlementdtl.getTierTwoSettlementDt();		  
		  if(tierTwoDt != null)
		  {
		      secondSettlementDt = sdf.format(tierTwoDt);
		  }
		  %>
		  <tr class="TableData"> 
		    <td height="40"> <div align="center"><%=i%></div></td>
		    <td>&nbsp;<%=borrowerId%></td>
		    <td>&nbsp;<%=cgclan%></td>
		    <!-- <td><div align="center"><%=voucherId%></div></td> -->
		    <td><div align="right"><%=tiertwo%></div></td>
		    <td><div align="center">&nbsp;<%=secondSettlementDt%></div></td>
		  </tr>
		  <%i++;%>
		  </logic:iterate>
		</table></td>
	    </tr>                                        
          <tr> 
            <td colspan="4" class="SubHeading">&nbsp;</td>
          </tr>
          <tr align="center" valign="baseline"> 
            <td colspan="4"> <div align="center">
		<a href="javascript:submitForm('displayMemberSettlementDtls.do?method=displayMemberSettlementDtls')"><img src="images/Back.gif" alt="Cancel" width="49" height="37" border="0"></a></div></td>
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
