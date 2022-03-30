<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="java.text.DecimalFormat"%>
<% session.setAttribute("CurrentPage","showPlanInvestmentDetails.do?method=showPlanInvestmentDetails");%>

<html:form action="updatePlanInvestmentDetails.do?method=updatePlanInvestmentDetails" method="POST" >

  <table width="100%" border="0" cellpadding="0" cellspacing="0">

	<tr>
		<td><br></td>
	</tr>
    <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
      <td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
    <tr> 
      <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
      <td><table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
	  <tr>
	  <TD>			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpPlanInvestmentDetails.do?method=helpPlanInvestmentDetails')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
          <tr> 
            <td><table width="100%" border="0" cellspacing="1" cellpadding="1">
                <tr> 
                  <td colspan="2" >
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="35%" class="Heading">&nbsp;
						<bean:message key="plan_investment" /> 
						</td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
				<%
					DecimalFormat df= new DecimalFormat("######################.##");
					df.setDecimalSeparatorAlwaysShown(false);
				%>
                
				<tr>
					<td class="ColumnBackground" width="40%">&nbsp;
					<bean:message key="availableBalanceAsOfClosingTimeyesterday"/>
					</td>
					<bean:define id="abVal" name="ifForm" property="availableBalance"/>
					<%
						String availBalVal = df.format(Double.parseDouble((String)abVal));
					%>
					<td class="tableData">
<!--						<bean:write name="ifForm" property="availableBalance"/> -->
					<%=availBalVal%>
					</td>
				</tr>
				<tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="expensesForTheMonth"/> </td>
					<td class="tableData" align="center">
					<bean:define id="meVal" name="ifForm" property="monthExpenses"/>
					<%
						String monthlyExpVal = df.format(Double.parseDouble((String)meVal));
					%>
					<%=monthlyExpVal%>
<!--					<bean:write name="ifForm" property="monthExpenses"/> -->
					</td>
				</tr>
				<tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="expensesForTheDay"/> </td>
					<td class="tableData" align="center">
					<bean:define id="deVal" name="ifForm" property="dayExpenses"/>
					<%
						String dayExpVal = df.format(Double.parseDouble((String)deVal));
					%>
					<%=dayExpVal%>
<!--						<bean:write name="ifForm" property="dayExpenses"/> 						-->
					</td>
				</tr>
				<tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="buyOrSellRequest"/> 
					<td class="tableData" align="center">
						<html:radio  property="isBuyOrSellRequest" value="Y"/>Yes
						<html:radio  property="isBuyOrSellRequest" value="N"/>No
					</td> 
				</tr>
              </table></td>
          </tr>
          <tr > 
            <td height="20" >&nbsp;</td>
          </tr>
          <tr > 
            <td align="center" valign="baseline" > <div align="center"><html:link href="javascript:submitForm('updatePlanInvestmentDetails.do?method=updatePlanInvestmentDetails')"><img src="images/OK.gif" alt="Ok" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></html:link> 
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
