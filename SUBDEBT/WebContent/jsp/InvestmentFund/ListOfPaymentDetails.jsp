<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="com.cgtsi.investmentfund.PaymentDetails" %>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="java.util.Date"%>
<% session.setAttribute("CurrentPage","showListOfPaymentDetails.do?method=showListOfPaymentDetails");%>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<html:errors />
	<html:form action="showListOfPaymentDetails.do?method=showListOfPaymentDetails" method="POST" enctype="multipart/form-data">	
    <tr>
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
      <td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
    <tr>
      <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
      <td><table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
          <tr>
            <td><table width="100%" border="0" cellspacing="1" cellpadding="1">
                <tr >
                  <td colspan="4">

				  <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="31%" class="Heading">
							<bean:message key="paymentDetailHeading" /></TD>
							<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
						</TR>
						<TR>
							<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
						</TR>

				 </TABLE>
				  </td>
                </tr>
                <tr align="left" valign="top" class="ColumnBackground">
					<td align="center"><bean:message key="payId"/> </td>
					<td align="center"><bean:message key="paymentsTo"/></td>
					<td align="center"><bean:message key="amount"/></td>
					<td align="center"><bean:message key="toDate"/></td>
                </tr>
				<%
					DecimalFormat df= new DecimalFormat("#############.##");
					df.setDecimalSeparatorAlwaysShown(false);

					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				%>

				
                <logic:iterate id="object" property="payments" name="payDetails" >

                <tr align="left" valign="top" class="tableData">
					<%
							com.cgtsi.investmentfund.PaymentDetails paydetails = (com.cgtsi.investmentfund.PaymentDetails)object;

							double amount = paydetails.getAmount();
					String strAmount = df.format(amount);
					System.out.println(strAmount+"");

					Date date = paydetails.getPaymentDate();
					String strDate = dateFormat.format(date);
					%>

									
									<td width="200" class="tableData" height="25">
									<div align="center">
									
									<a  href="javascript:submitForm('showModifyPaymentDetails.do?method=getPaymentDetails&Id=<%=paydetails.getPayId()%>')"><%=paydetails.getPayId()%></a></div></td>
											
									<% String paymentsto = paydetails.getPaymentsTo();
									%>

					<td align="left"><%=paymentsto%></td>

									
					<td align="left"><%=strAmount%></td>


									
					<td align="left"><%=strDate%></td>
                </tr>
                
                </logic:iterate>

                
              </table></td>
          </tr>
          <tr >
            <td height="20" >&nbsp;</td>
          </tr>
          <tr >
            <td align="center" valign="baseline" > <div align="center">
			<html:link
			href="javascript:window.history.back()">
			<img src="images/Back.gif" alt="Back" width="49" height="37" border="0"></html:link>
			</div></td>
          </tr>
        </table></td>
      <td width="20" background="images/TableVerticalRightBG.gif">&nbsp;</td>
    </tr>
    <tr>
      <td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
      <td background="images/TableBackground2.gif">&nbsp;</td>
      <td width="20" align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
    </tr>
  </table>

  <br>
  <p>&nbsp;</p>
</html:form>

