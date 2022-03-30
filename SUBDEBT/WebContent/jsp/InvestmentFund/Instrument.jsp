<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showInstrumentMaster.do?method=showInstrumentMaster");%>
<% 
String req = (String)session.getAttribute("modFlag");
if (req.equals("0"))
{
	session.setAttribute("CurrentPage","showInstrumentMaster.do?method=showInstrumentMaster");
}
else if (req.equals("1"))
{
	session.setAttribute("CurrentPage","showInstrumentMaster.do?method=showInstrumentList");
}
else if (req.equals("2"))
{
	session.setAttribute("CurrentPage","showInstrumentMaster.do?method=showInstrumentDetails");
}
%>
<body onload="instrumentTypeOption(),instrumentNameOption()">
<html:form action="updateInstrumentMaster.do?method=updateInstrumentMaster" method="POST" enctype="multipart/form-data" focus="instrumentName">

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
    <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0" height="130">
	<tr>
	  <TD>			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpHolidayMaster.do?method=helpHolidayMaster')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
        <tr>
            <td height="130" align="center"> <table border="0" width="100%" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="3" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="30%" class="Heading">&nbsp;
						<bean:message key="instrumentMasterDetails" />
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
                  <td class="ColumnBackground" height="25"> &nbsp;&nbsp;
				  <bean:message key="instrumentType" /> 
                    </td>
                  <td class="tableData" width="474" height="25" colspan="2"> <div align="left"> 
                      <bean:message key="generalInstrument"/><html:radio  property="instrumentType" name="ifForm" value="G" onclick="instrumentTypeOption();submitForm('showInstrumentMaster.do?method=showInstrumentList')"/>
                      <bean:message key="investmentInstrument"/><html:radio  property="instrumentType" name="ifForm" value="I" onclick="instrumentTypeOption();submitForm('showInstrumentMaster.do?method=showInstrumentList')"/>
                    </div></td>
                </tr>
				<tr>
                  <td  class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="selectInstrument" />
				  </div></td>
                  <td class="tableData" width="100"> <div align="left"> 
					<html:select property="instrumentName" name="ifForm" onchange="submitForm('showInstrumentMaster.do?method=showInstrumentDetails');instrumentNameOption()">
						<html:option value="">Select</html:option>
						<html:options property="instrumentNames" name="ifForm"/>			
					</html:select>
                  </div></td>
			  <bean:define id="nVal" name="ifForm" property="newInstrumentName"/>
			  <%
			  String reqVal1 = (String)nVal;
			  if (request.getParameter("newInstrumentName")!=null)
			  {
				reqVal1 = (String)request.getParameter("newInstrumentName");
			  }
			  %>
					<TD align="left" class="ColumnBackground"><bean:message key="instrumentNameEnter" />
						<html:text property="newInstrumentName" size="20" alt="Enter" name="ifForm" maxlength="50" value="<%=reqVal1%>"/>
					</TD>
                </tr>
			  <bean:define id="mVal" name="ifForm" property="modInstrumentName"/>
			  <%
			  String reqVal = (String)mVal;
			  if (request.getParameter("modInstrumentName")!=null)
			  {
				reqVal = (String)request.getParameter("modInstrumentName");
			  }
			  %>
                <tr>
                  <td  class="ColumnBackground" height="25">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="modInstrumentName" /> 
                    </td>
                  <td class="tableData" width="474" height="25" colspan="2"> <div align="left"> 
                      <html:text  property="modInstrumentName" size="20"  alt="Instrument Type" name="ifForm" maxlength="50" value="<%=reqVal%>"/>
                    </div></td>
                </tr>
                <tr>
                  <td  class="ColumnBackground" height="25"> &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="instrumentDescription" /> 
				  &nbsp;</td>
                  <td class="tableData" width="474" height="25" colspan="2"> <div align="left"> 
<!--                      <html:text  property="instrumentDescription" size="20" alt="Instrument Description" name="ifForm" maxlength="100"/>-->
						<html:textarea name="ifForm" property="instrumentDescription" rows="3" cols="50"/>
                    </div></td>
                </tr>
   
                
                <%--
                <tr>
                  <td width="360" class="ColumnBackground" height="25"> &nbsp;&nbsp;
				  <bean:message key="instrumentPeriod" /> 
                    </td>
                  <td class="tableData" width="474" height="25"> <div align="left"> 
                      <html:text  property="instrumentPeriod" size="20" alt="Instrument Period" name="ifForm"/>
                    </div></td>
                </tr>
                --%>
              </table>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center"><html:link href="javascript:submitForm('updateInstrumentMaster.do?method=updateInstrumentMaster')"><img src="images/Save.gif" alt="Ok" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link></div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>