<%@ page language="java"%>
<%@ page import="com.cgtsi.receiptspayments.RpConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="java.text.SimpleDateFormat"%>
<% session.setAttribute("CurrentPage","getPPMLIWiseDetails.do?method=getPPMLIWiseDetails");%>

<html:errors />
<html:form action="displayPPMLIWiseFilter.do?method=displayPPMLIWiseFilter" method="POST" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    
    <tr>
      <td class="FontStyle">&nbsp;</td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
    <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td width="248" background="images/TableBackground1.gif"><img src="images/ReceiptsPaymentsHeading.gif"></td>
    <td align="right" valign="top" background="images/TableBackground1.gif"> </td>
    <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
  </tr>
  <tr> 
    <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td> <table width="651" border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="6" width="673"><table width="671" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="248" class="Heading"><a name="AD" id="AD"></a>&nbsp;Pending Details for <bean:write property="memberId" name="rpAllocationForm"/></td>
                        <td align="left" valign="bottom" width="120"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td width="144">&nbsp;</td>
                        <td width="151">&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="4" class="Heading" width="669"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr> 
                  <td class="ColumnBackground" width="144"> <div align="left">&nbsp; DAN</div></td>
                  <td class="ColumnBackground" width="97"> <div align="left">&nbsp; Date of DAN</div></td>
                  <td class="ColumnBackground" width="137"> <div align="left">&nbsp; CGPAN</div></td>
                  <td class="ColumnBackground" width="59"> <div align="left">&nbsp; Amount</div></td>
                  <td class="ColumnBackground" width="47"> Penalty</td>
                  <td class="ColumnBackground" width="169"> Total Pending Amount</td>
                </tr>                
                <logic:iterate property="mliWiseDanDetails" name="rpAllocationForm" id="object">
                <%
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                java.util.HashMap hashmap = (java.util.HashMap)object;
                String danId = (String)hashmap.get(RpConstants.RP_DAN_ID);
                if(danId == null)                
                {
                    danId = "";
                }
                java.util.Date danGeneratedDt = (java.util.Date)hashmap.get(RpConstants.RP_DAN_GEN_DATE);
                String danGeneratedDtStr = null;
                if(danGeneratedDt != null)
                {
                    danGeneratedDtStr = sdf.format(danGeneratedDt);
                }
                else
                {
                    danGeneratedDtStr = "";
                }
                Double penalty = (Double)hashmap.get(RpConstants.RP_PENALTY);
                String penaltyStr = null;
                if(penalty != null)
                {
                    penaltyStr = penalty.toString();
                }
                else
                {
                    penaltyStr = "";
                }
                String cgpan = (String)hashmap.get(RpConstants.RP_CGPAN);
                if(cgpan == null)                
                {
                     cgpan = "";
                }
                Double amount = (Double)hashmap.get(RpConstants.RP_AMOUNT_RAISED);
                String amountStr = null;
                if(amount != null)
                {
                    amountStr = amount.toString();
                }
                else
                {
                    amountStr = "";
                }
                Double totalAmount = (Double)hashmap.get(RpConstants.RP_TOTAL_AMOUNT_RAISED);
                String totalAmountStr = null;
                if(totalAmount != null)
                {
                    totalAmountStr = totalAmount.toString();
                }
                else
                {
                    totalAmountStr = "";
                }
                %>
                <tr> 
                  <td class="TableData" width="144"><div align="center">&nbsp; <%=danId%></div></td>
                  <td class="TableData" width="97"><div align="center">&nbsp; <%=danGeneratedDtStr%></div></td>
                  <td class="TableData" width="137"><div align="center">&nbsp; <%=cgpan%></div></td>
                  <td class="TableData" width="59"><div align="right">&nbsp;<%=amountStr%> </div></td>
                  <td class="TableData" width="47"><div align="center"><%=penaltyStr%></div></td>
                  <td class="TableData" width="169"><div align="center"><%=totalAmountStr%></div></td>
		</tr>
		</logic:iterate>
                <tr> 
                  <td colspan="4" width="449"><img src="images/Clear.gif" width="5" height="15"></td>
                  <td width="47"></td>
                  <td width="169"></td>
                </tr>
              </table>
      </table></td>
           
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center">
		  <logic:equal property="bankId" value="0000" name="rpAllocationForm">
		  <a href="javascript:submitForm('displayPPMLIWiseFilter.do?method=displayPPMLIWiseFilter')"><img src="images/OK.gif" alt="OK" width="49" height="37" border="0"></a>
		  </logic:equal>
		  <logic:notEqual property="bankId" value="0000" name="rpAllocationForm">
		  <a href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><img src="images/OK.gif" alt="OK" width="49" height="37" border="0"></a>
		  </logic:notEqual>
		  </div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>
</html>
