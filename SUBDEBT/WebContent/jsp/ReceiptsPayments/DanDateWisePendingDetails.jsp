<%@ page language="java"%>
<%@ page import="com.cgtsi.receiptspayments.RpConstants"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.HashMap"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","displayPPDateWiseFilter.do?method=displayPPDateWiseFilter");%>

<html:errors />
<form name="form1">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    
    <tr>
      <td class="FontStyle">&nbsp;</td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
    <td align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/ReceiptsPaymentsHeading.gif" width="142" height="25"></td>
    <td align="right" valign="top" background="images/TableBackground1.gif"> </td>
    <td align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
  </tr>
  <tr> 
    <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td > <table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="6"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr> 
		      <td class="Heading">&nbsp;Dan Date Wise Details</td>
		      <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td width="144">&nbsp;</td>
                        <td width="151">&nbsp;</td>		      
		      </tr>
                      <tr> 
                        <td class="Heading" colspan="4"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>				
                <tr>
                  <td class="ColumnBackground"> <div align="left">&nbsp; MLI</div></td>
                  <td class="ColumnBackground"> <div align="left">&nbsp; DAN</div></td>
                  <td class="ColumnBackground"> <div align="left">&nbsp; DAN Generated Date</div></td>
                  <td class="ColumnBackground"> <div align="left">&nbsp; CGPAN</div></td>
                  <td class="ColumnBackground"> <div align="left">&nbsp; Amount</div></td>
                  <td class="ColumnBackground"> Penalty</td>
                  <td class="ColumnBackground"> <div align="left">&nbsp;
                      Total Pending Amount</div></td>
                </tr>
                <logic:iterate property="dateWiseDANDetails" name="rpAllocationForm" id="object">
                <%
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                HashMap hashmap = (HashMap)object;
                String memberId = (String)hashmap.get(RpConstants.RP_MEMBER_ID);
                if(memberId == null)
                {
                   memberId = "";  
                }
		String danId = (String)hashmap.get(RpConstants.RP_DAN_ID);
		if(danId == null)
		{
		    danId = ""; 
		}
		String cgpan = (String)hashmap.get(RpConstants.RP_CGPAN);
		if(cgpan == null)
		{
		    cgpan = "";
		}
		Double amount = (Double)hashmap.get(RpConstants.RP_AMOUNT_RAISED);
		String amountStr = null;
		if(amount == null)
		{
		    amountStr = "";
		}
		else
		{
		    amountStr = amount.toString();
		}
		Double penalty = (Double)hashmap.get(RpConstants.RP_PENALTY);
		String penaltyStr = null;
		if(penalty == null)
		{
		     penaltyStr = "";   
		}
		else
		{
		     penaltyStr = penalty.toString();
		}
		Double totalAmount = (Double)hashmap.get(RpConstants.RP_TOTAL_AMOUNT_RAISED);
		String totalAmountStr = null;
		if(totalAmount == null)
		{
		    totalAmountStr = "";
		}
		else
		{
		    totalAmountStr = totalAmount.toString();
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
                %>
                <tr> 
                  <td class="TableData"><div align="center">&nbsp; <%=memberId%></div></td>
                  <td class="TableData"><div align="center">&nbsp;
                    <%=danId%></div></td>
                  <td class="TableData"><div align="center">&nbsp;
                    <%=danGeneratedDtStr%></div></td>
                  <td class="TableData"><div align="center">&nbsp;<%=cgpan%></div></td>
                  <td class="TableData"><div align="center">&nbsp;<%=amountStr%> </div></td>
                  <td class="TableData"><div align="center"><%=penaltyStr%></div></td>
                  <td class="TableData"><div align="center">&nbsp;
                      <%=totalAmountStr%></div></td>
		</tr>
		</logic:iterate>
		
              </table>
      </table></td>
    <td background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td  align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center"><a href="javascript:submitForm('displayPPDateWiseFilter.do?method=displayPPDateWiseFilter')"><img src="images/OK.gif" alt="Cancel" width="49" height="37" border="0"></a></div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</form>
</body>
</html>
