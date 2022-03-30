<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
String flag= (String)session.getAttribute("modFlag");
if (flag.equals("0"))
{
	session.setAttribute("CurrentPage","showBudgetHeadMaster.do?method=showBudgetHeadMaster");
}
else if (flag.equals("1"))
{
	session.setAttribute("CurrentPage","showBudgetHeadList.do?method=showBudgetHeadList");
}
else if (flag.equals("2"))
{
	session.setAttribute("CurrentPage","showBudgetHeadMaster.do?method=showBudgetHeadDetail");
}
%>
<body onload="budgetHeadOption()">
<html:form action="updateBudgetHeadMaster.do?method=updateBudgetHeadMaster" method="POST" enctype="multipart/form-data" focus="budgetHead">
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
    <td colspan="2"><table border="0" cellspacing="0" cellpadding="0" height="100">
	<tr>
	  <TD>			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpBudgetHeadMaster.do?method=helpBudgetHeadMaster')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
        <tr>
            <td > <table border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="3" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="25%" class="Heading"><bean:message key="budgetHeadDetailsHeading" /></td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="5" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr>
                  <td  class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="budgetHeadInflowOutflowFlag" /></div></td>
                  <td class="tableData" width="474" colspan="2"> <div align="left"> 
					  <html:radio name="ifForm" value="I" property="budgetHeadType" onclick="javascript:submitForm('showBudgetHeadList.do?method=showBudgetHeadList')">
                      <bean:message key="inflow" /></html:radio>
						<html:radio name="ifForm" value="O" property="budgetHeadType" onclick="javascript:submitForm('showBudgetHeadList.do?method=showBudgetHeadList')">
					  <bean:message key="outflow" /></html:radio>
						<html:radio name="ifForm" value="B" property="budgetHeadType" onclick="javascript:submitForm('showBudgetHeadList.do?method=showBudgetHeadList')">
					  <bean:message key="both" /></html:radio>					  
                    </div></td>
                </tr>
				<tr>
                  <td  class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="selectBudgetHead" />
				  </div></td>
                  <td class="tableData" width="100"> <div align="left"> 
					<html:select property="budgetHead" name="ifForm" onchange="budgetHeadOption();submitForm('showBudgetHeadMaster.do?method=showBudgetHeadDetail')">
						<html:option value="">Select</html:option>
						<html:options property="budgetHeadsList" name="ifForm"/>			
					</html:select>
                  </div></td>
			  <bean:define id="nVal" name="ifForm" property="newBudgetHead"/>
			  <%
			  String reqVal1 = (String)nVal;
			  if (request.getParameter("newBudgetHead")!=null)
			  {
				reqVal1 = (String)request.getParameter("newBudgetHead");
			  }
			  %>
					<TD align="left" class="ColumnBackground"><bean:message key="budgetHeadEnter" />
						<html:text property="newBudgetHead" size="20" alt="Enter" name="ifForm" maxlength="50" value="<%=reqVal1%>"/>
					</TD>
                </tr>
				<tr>
                  <td class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="modBudgetHead" />
				  </div></td>
			  <bean:define id="mVal" name="ifForm" property="modBudgetHead"/>
			  <%
			  String reqVal = (String)mVal;
			  if (request.getParameter("modBudgetHead")!=null)
			  {
				reqVal = (String)request.getParameter("modBudgetHead");
			  }
			  %>
                  <td class="tableData" width="474" colspan="2"> <div align="left"> 
				  <html:text property="modBudgetHead" size="30"  alt="Budget Head Type" name="ifForm" disabled="true" maxlength="50" value="<%=reqVal%>"/>
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
          <div align="center"><html:link href="javascript:submitForm('updateBudgetHeadMaster.do?method=updateBudgetHeadMaster')">
		  <img  src="images/Save.gif" alt="Ok" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link></div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>

</body>