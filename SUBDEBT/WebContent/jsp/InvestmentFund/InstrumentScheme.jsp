<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
String req = (String)session.getAttribute("modFlag");
if (req.equals("0"))
{
	session.setAttribute("CurrentPage","showInstrumentSchemeMaster.do?method=showInstrumentSchemeMaster");
}
else if (req.equals("1"))
{
	session.setAttribute("CurrentPage","showInstrumentSchemeMaster.do?method=getInstrumentSchemes");
}
else if (req.equals("2"))
{
	session.setAttribute("CurrentPage","showInstrumentSchemeMaster.do?method=showInstSchemeDetails");
}
%>
<body onload="instSchemeOption()">
<html:form action="updateInstrumentSchemeMaster.do?method=updateInstrumentSchemeMaster" method="POST" enctype="multipart/form-data" focus="instrument">

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
    <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0" >
	<tr>
	  <TD>			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpInstrumentSchemeMaster.do?method=helpInstrumentSchemeMaster')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
        <tr>
            <td> <table border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="3" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="210" class="Heading">
						<bean:message key="instrumentSchemeDetails" />
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
                  <td  class="ColumnBackground" height="25">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrument" /></td>
                  <td class="tableData"  width="474" height="25" colspan="2"> <div align="left"> 
					  <html:select property="instrument" styleId="instrument"  name="ifForm" onchange="submitForm('showInstrumentSchemeMaster.do?method=getInstrumentSchemes')">
						<html:option value="">Select </html:option>
						<html:options name="ifForm" property="instrumentNames"/>
					  </html:select>
                    </div></td>
                </tr>
				<TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="selectInstrumentSchemeType" />
					</TD>
					<TD align="left" class="TableData">
						<html:select property="instrumentSchemeType" name="ifForm" onchange="javascript:instSchemeOption(this);submitForm('showInstrumentSchemeMaster.do?method=showInstSchemeDetails')">
							<html:option value="">Select</html:option>
							<html:options property="instrumentSchemeTypes" name="ifForm"/>			
						</html:select>
					</TD>
			  <bean:define id="nVal" name="ifForm" property="newInstrumentSchemeType"/>
			  <%
			  String reqVal1 = (String)nVal;
			  if (request.getParameter("newInstrumentSchemeType")!=null)
			  {
				reqVal1 = (String)request.getParameter("newInstrumentSchemeType");
			  }
			  %>
					<TD align="left" class="ColumnBackground"><bean:message key="instSchemeEnter" />
						<html:text property="newInstrumentSchemeType" size="20" alt="Enter" name="ifForm" maxlength="20" value="<%=reqVal1%>"/>
					</TD>									
				</TR>
			  <bean:define id="mVal" name="ifForm" property="modInstrumentSchemeType"/>
			  <%
			  String reqVal = (String)mVal;
			  if (request.getParameter("modInstrumentSchemeType")!=null)
			  {
				reqVal = (String)request.getParameter("modInstrumentSchemeType");
			  }
			  %>
                <tr>
                  <td  class="ColumnBackground" height="25"> &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="modinstrumentSchemeType" />
				  </td>
                  <td class="tableData" width="474" height="25" colspan="2"> <div align="left"> 
                      <html:text  property="modInstrumentSchemeType" size="20" alt="Instrument Scheme Type" name="ifForm" maxlength="20" value="<%=reqVal%>"/>
                    </div></td>
                </tr>
                <tr>
                  <td " class="ColumnBackground" height="25"> &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="instrumentSchemeDescription" />
				 </td>
                  <td class="tableData" width="474" height="25" colspan="2"> <div align="left"> 
<!--                      <html:text  property="instrumentSchemeDescription" size="20"  alt="Instrument Scheme Description" name="ifForm" maxlength="200"/>-->
						<html:textarea name="ifForm" property="instrumentSchemeDescription" rows="3" cols="50"/>
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
          <div align="center"><html:link href="javascript:submitForm('updateInstrumentSchemeMaster.do?method=updateInstrumentSchemeMaster')"><img src="images/Save.gif" alt="Ok" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link></div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>