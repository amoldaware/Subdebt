<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
String req = (String)session.getAttribute("modFlag");
if (req.equals("0"))
{
	session.setAttribute("CurrentPage","showInstrumentFeatureMaster.do?method=showInstrumentFeatures");
}
else if (req.equals("1"))
{
	session.setAttribute("CurrentPage","showInstrumentFeatureMaster.do?method=showInstFeaturesDetails");
}
%>
<body onload="instFeatureOption()">
<html:form action="updateInstrumentFeatureMaster.do?method=updateInstrumentFeatureMaster" method="POST" enctype="multipart/form-data" focus="instrumentFeatures">
  
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
				<A HREF="javascript:submitForm('helpInstrumentFeatureMaster.do?method=helpInstrumentFeatureMaster')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
        <tr>
            <td height="100"> <table border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="3"><table width="100%" border="0" cellspacing="0" cellpadding="0" >
                      <tr> 
                        <td width="30%" class="Heading">&nbsp;
						<bean:message key="instrumentFeatureDetailsHeading" />
                          </td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="4" class="Heading" ><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
				<TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="selectInstFeature" />
					</TD>
					<TD align="left" class="TableData">
						<html:select property="instrumentFeatures" name="ifForm" onchange="javascript:instFeatureOption(this);submitForm('showInstrumentFeatureMaster.do?method=showInstFeaturesDetails')">
							<html:option value="">Select</html:option>
							<html:options property="instrumentFeaturesList" name="ifForm"/>			
						</html:select>
					</TD>
			  <bean:define id="nVal" name="ifForm" property="newInstrumentFeatures"/>
			  <%
			  String reqVal1 = (String)nVal;
			  if (request.getParameter("newInstrumentFeatures")!=null)
			  {
				reqVal1 = (String)request.getParameter("newInstrumentFeatures");
			  }
			  %>
					<TD align="left" class="ColumnBackground"><bean:message key="instFeatureEnter" />
						<html:text property="newInstrumentFeatures" size="20" alt="Enter" name="ifForm" maxlength="20" value="<%=reqVal1%>"/>
					</TD>									
				</TR>
			  <bean:define id="mVal" name="ifForm" property="modInstrumentFeatures"/>
			  <%
			  String reqVal = (String)mVal;
			  if (request.getParameter("modInstrumentFeatures")!=null)
			  {
				reqVal = (String)request.getParameter("modInstrumentFeatures");
			  }
			  %>
                <tr>
                  <td  class="ColumnBackground"> 
				  &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="modInstrumentFeatures" /></td>
                  <td class="tableData" width="474" colspan="2"> <div align="left"> 
                      <html:text  property="modInstrumentFeatures" size="20" alt="Instrument Feature" name="ifForm" maxlength="20" value="<%=reqVal%>"/>
                    </div></td>
                </tr>
                <tr>
                  <td  class="ColumnBackground"> &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrumentFeatureDescription" />
					</td>
                  <td class="tableData" width="474" colspan="2"> <div align="left"> 
<!--                      <html:text  property="instrumentFeatureDescription" size="20" alt="Instrument Feature Description" name="ifForm" maxlength="200"/>-->
						<html:textarea name="ifForm" property="instrumentFeatureDescription" rows="3" cols="50"/>
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
          <div align="center"><html:link href="javascript:submitForm('updateInstrumentFeatureMaster.do?method=updateInstrumentFeatureMaster')"><img src="images/Save.gif" alt="Ok" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link></div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>
