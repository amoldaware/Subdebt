<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
String flag= (String)session.getAttribute("modFlag");
if (flag.equals("0"))
{
	session.setAttribute("CurrentPage","showBudgetSubHeadMaster.do?method=showBudgetSubHeadMaster");
}
else if (flag.equals("1"))
{
	session.setAttribute("CurrentPage","showBudgetSubHeadList.do?method=showBudgetSubHeadList");
}
%>
<body onload="budgetSubHeadOption()">
<html:form action="updateBudgetSubHeadMaster.do?method=updateBudgetSubHeadMaster" method="POST" enctype="multipart/form-data">

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
    <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0" height="100">
	<tr>
	  <TD>			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpBudgetSubHeadMaster.do?method=helpBudgetSubHeadMaster')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
        <tr>
            <td height="100"> <table border="0" cellspacing="1" cellpadding="0" height="90">
                <tr> 
                  <td colspan="3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="25%" class="Heading">&nbsp;<bean:message key="BudgetSubheadDetails" /></td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr>
                  <td  class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="budgetHeadType" />
				  </td>
                  <td class="tableData" colspan="2"> <div align="left"> 
			<bean:message key="inflow" />
			<html:radio property="budgetHeadType" styleId="budgetHeadType"  name="ifForm" value="I" onclick="submitForm('showBudgetSubHeadMaster.do?method=showBudgetSubHeadMaster')" />
			 &nbsp 
			<bean:message key="outflow" />
			<html:radio property="budgetHeadType" styleId="budgetHeadType"  name="ifForm" value="O" onclick="submitForm('showBudgetSubHeadMaster.do?method=showBudgetHeadList')"/>
                    </div></td>
                </tr>
                
                <tr>
                  <td  class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="budgetHead" />
				  </td>
                  <td class="tableData" colspan="2"> <div align="left"> 
					  <html:select property="budgetHead" styleId="budgetHeadType"  name="ifForm" onchange="submitForm('showBudgetSubHeadList.do?method=showBudgetSubHeadList')">
						<html:option value="">Select </html:option>
						<html:options name="ifForm" property="budgetHeadsList"/>
					  </html:select>
                    </div></td>
                </tr>
				<tr>
                  <td  class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="selectBudgetSubHead" />
				  </div></td>
                  <td class="tableData" width="100"> <div align="left"> 
					<html:select property="budgetSubHeadTitle" name="ifForm" onchange="budgetSubHeadOption();submitForm('showBudgetSubHeadMaster.do?method=showBudgetSubHeadDetail')">
						<html:option value="">Select</html:option>
						<html:options property="budgetSubHeadsList" name="ifForm"/>			
					</html:select>
                  </div></td>
			  <bean:define id="nVal" name="ifForm" property="newBudgetSubHeadTitle"/>
			  <%
			  String reqVal1 = (String)nVal;
			  if (request.getParameter("newBudgetSubHeadTitle")!=null)
			  {
				reqVal1 = (String)request.getParameter("newBudgetSubHeadTitle");
			  }
			  %>
					<TD align="left" class="ColumnBackground"><bean:message key="budgetSubHeadEnter" />
						<html:text property="newBudgetSubHeadTitle" size="20" alt="Enter" name="ifForm" maxlength="50" value="<%=reqVal1%>"/>
					</TD>
                </tr>
			  <bean:define id="mVal" name="ifForm" property="modBudgetSubHeadTitle"/>
			  <%
			  String reqVal = (String)mVal;
			  if (request.getParameter("modBudgetSubHeadTitle")!=null)
			  {
				reqVal = (String)request.getParameter("modBudgetSubHeadTitle");
			  }
			  %>
                <tr>
                  <td  class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
  				  <bean:message key="modbudgetSubHeadTitle" />
				  </td>
                  <td class="tableData" width="474" colspan="2"> <div align="left"> 
                      <html:text  property="modBudgetSubHeadTitle" size="30" alt="Budget Sub Head Title" name="ifForm" maxlength="50" value="<%=reqVal%>"/>
                    </div></td>
                </tr>
              </table>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center"><html:link href="javascript:submitForm('updateBudgetSubHeadMaster.do?method=updateBudgetSubHeadMaster')"><img src="images/Save.gif" alt="Ok" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link></div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>

</body>