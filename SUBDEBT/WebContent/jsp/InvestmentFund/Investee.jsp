<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
String req = (String)session.getAttribute("modFlag");
if (req.equals("0"))
{
	session.setAttribute("CurrentPage","showInvesteeMaster.do?method=showInvesteeMaster");
}
else if (req.equals("1"))
{
	session.setAttribute("CurrentPage","showInvesteeMaster.do?method=showInvesteeList");
}
else if (req.equals("2"))
{
	session.setAttribute("CurrentPage","showInvesteeMaster.do?method=showInvesteeDetail");
}
%>
<body onload="investeeOption(this)">
<html:form action="updateInvesteeMaster.do?method=updateInvesteeMaster" method="POST" enctype="multipart/form-data" focus="investeeGroup">

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
	  <TD>			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpInvesteeMaster.do?method=helpInvesteeMaster')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
        <tr>
            <td height="162"> <table border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="2" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="25%" class="Heading">&nbsp;
						 <bean:message key="InvesteeDetails" />
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
                <tr>
                  <td width="360" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
                      <bean:message key="investeeGroup" />
					  &nbsp;</div></td>
                  <td class="tableData" width="474"> <div align="left"> 
	  				  <html:select property="investeeGroup" styleId="investee"  name="ifForm"
					  onchange="javascript:submitForm('showInvesteeMaster.do?method=showInvesteeList')">
						<html:option value="">Select </html:option>
						<html:options name="ifForm" property="investeeGroups"/>
					  </html:select>
                    </div></td>
                </tr>
                <tr>
                  <td width="360" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
                      <bean:message key="select" />
					  &nbsp;</div></td>
                  <td class="tableData" width="474"> <div align="left"> 
						<html:radio name="ifForm" value="1" property="newInvesteeFlag"  onclick="investeeOption()"> 
						New </html:radio>
						<html:radio name="ifForm" value="2" property="newInvesteeFlag"  onclick="investeeOption()"> Modify </html:radio>
                    </div></td>
                </tr>
				<tr>
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="investee" />
					</TD>
					<TD align="left" class="TableData">
						<html:select property="investee1" name="ifForm" onchange="javascript:submitForm('showInvesteeMaster.do?method=showInvesteeDetail')">
							<html:option value="">Select</html:option>
							<html:options property="investeeNames" name="ifForm"/>			
						</html:select>
					</TD>
				</tr>
			  <bean:define id="mVal" name="ifForm" property="modInvestee"/>
			  <%
			  String reqVal = (String)mVal;
			  if (request.getParameter("modInvestee")!=null)
			  {
				reqVal = (String)request.getParameter("modInvestee");
			  }
			  %>
                <tr>
                  <td width="360" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="modInvestee" />
                      </div></td>
                  <td class="tableData" width="474"> <div align="left"> 
                      <html:text  property="modInvestee" size="20" alt="Investee name" name="ifForm" maxlength="50" value="<%=reqVal%>"/>
                    </div></td>
                </tr>
			  <bean:define id="nVal" name="ifForm" property="newInvestee"/>
			  <%
			  String reqVal1 = (String)nVal;
			  if (request.getParameter("newInvestee")!=null)
			  {
				reqVal1 = (String)request.getParameter("newInvestee");
			  }
			  %>
                <tr>
                  <td width="360" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="investeeEnter" />
                      </div></td>
                  <td class="tableData" width="474"> <div align="left"> 
                      <html:text  property="newInvestee" size="20" alt="Investee name" name="ifForm" maxlength="50" value="<%=reqVal1%>"/>
                    </div></td>
                </tr>
                
                <tr>
                  <td width="360" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="investeeTangibleAssets" />
                      </div></td>
			  <bean:define id="taVal" name="ifForm" property="investeeTangibleAssets"/>
			  <%
			  String reqtaVal = (String)taVal;
			  if (request.getParameter("investeeTangibleAssets")!=null)
			  {
				reqtaVal = (String)request.getParameter("investeeTangibleAssets");
			  }
			  %>
                  <td class="tableData" width="474"> <div align="left"> 
				        <html:text  property="investeeTangibleAssets" size="20" maxlength="16" alt="Investee Tangible Assets" name="ifForm" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" value="<%=reqtaVal%>"/> <bean:message key="inRsCrores" />
                    </div></td>
                </tr>
                <tr>
                  <td width="360" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
					<bean:message key="investeeNetWorth" />
					  </div></td>
			  <bean:define id="nwVal" name="ifForm" property="investeeNetWorth"/>
			  <%
			  String reqnwVal = (String)nwVal;
			  System.out.println("nwq " + reqnwVal);
			  if (request.getParameter("investeeNetWorth")!=null)
			  {
				reqnwVal = (String)request.getParameter("investeeNetWorth");
			  }
  			  System.out.println("nwq 1 " + reqnwVal);
			  %>
                  <td class="tableData" width="474"> <div align="left"> 
                      <html:text  property="investeeNetWorth" size="20" maxlength="16" alt="Investee Net Worth" name="ifForm" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" value="<%=reqnwVal%>"/> <bean:message key="inRsCrores" />
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
          <div align="center"><html:link href="javascript:submitForm('updateInvesteeMaster.do?method=updateInvesteeMaster')"><img src="images/Save.gif" alt="Ok" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link></div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>