<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="com.cgtsi.investmentfund.CorpusDetail" %>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Map.Entry"%>
<% session.setAttribute("CurrentPage","showUpdateTDS.do?method=showTDSList");%>

<html:form action="showUpdateTDS.do?method=showUpdateTDSDetail" method="POST" enctype="multipart/form-data">


  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <html:errors />
    <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td width="323" background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
      <td align="right" background="images/TableBackground1.gif"> </td>
      <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
  <tr> 
    <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0" height="162">

        <tr>
            <td height="162"> <table border="0" cellspacing="1" cellpadding="0" height="108">
                <tr> 
                  <td colspan="7" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="25%" class="Heading">
						<bean:message key="TDSDetails" /></td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="7" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr>
                  <td width="360" class="ColumnBackground" height="25">
				  <bean:message key="tdsId" />
				  </td>
				  <td width="360" class="ColumnBackground" height="25">
				  <bean:message key="investee" />
				  </td>
                  <td width="360" class="ColumnBackground" height="25">
				  <bean:message key="instrumentName" />
				  </td>
                  <td width="360" class="ColumnBackground" height="25">
				  <bean:message key="investmentReferenceNumbers" />
				  </td>
				  <td width="360" class="ColumnBackground" height="25">
				  <bean:message key="tdsAmount" />
				  </td>
				  <td width="360" class="ColumnBackground" height="25">
				  <bean:message key="tdsDeductedDate" />
				  </td>
				  <td width="360" class="ColumnBackground" height="25">
				  <bean:message key="tdsCertificateReceived" />
				  </td>
                </tr>
				<%
					DecimalFormat df= new DecimalFormat("######################.##");
					df.setDecimalSeparatorAlwaysShown(false);

					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				%>
				<logic:iterate name="ifForm" property="tdsList" id="object">
				<%
					com.cgtsi.investmentfund.TDSDetail tdsDetail = (com.cgtsi.investmentfund.TDSDetail) object;

					double amount = tdsDetail.getTdsAmount();
					String strAmount = df.format(amount);

					Date date = tdsDetail.getTdsDeductedDate();
					String strDate = dateFormat.format(date);
				%>
					<tr>
					  <td width="360" class="tableData" height="25">
					  <div align="center">
				<a href="javascript:submitForm('showUpdateTDS.do?method=showUpdateTDSDetail&id=<%=tdsDetail.getTdsID()%>')">
					 <%=tdsDetail.getTdsID()%></a>
					  </div>
					  </td>
					  <td width="360" class="tableData" height="25">
					  <div align="center">
					  <%=tdsDetail.getInvesteeName()%>
					  </div>
					  </td>
					  <td width="360" class="tableData" height="25">
					  <div align="center">
					  <%=tdsDetail.getInstrumentName()%>
					  </div>
					  </td>
					  <td width="360" class="tableData" height="25">
					  <div align="center">
					  <%=tdsDetail.getInvestmentRefNumber()%>
					  </div>
					  </td>
					  <td width="360" class="tableData" height="25">
					  <div align="center">
					  <%=strAmount%>
					  </div>
					  </td>
					  <td width="360" class="tableData" height="25">
					  <div align="center">
					  <%=strDate%>
					  </div>
					  </td>
					  <td width="360" class="tableData" height="25">
					  <div align="center">
					  <%=tdsDetail.getIsTDSCertificateReceived()%>
					  </div>
					  </td>
					</tr>
				</logic:iterate>
              </table>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div align="center">
          <A href="javascript:history.back()">
						<IMG src="images/Back.gif" alt="Save" width="49" height="37" border="0">
						</A>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>

