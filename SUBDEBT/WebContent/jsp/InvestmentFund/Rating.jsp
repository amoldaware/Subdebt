<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
String req = (String)session.getAttribute("modFlag");
if (req.equals("0"))
{
	session.setAttribute("CurrentPage","showRatingMaster.do?method=showRatingMaster");
}
else if (req.equals("1"))
{
	session.setAttribute("CurrentPage","showRatingMaster.do?method=showRatingDetails");
}
%>
<body onload="ratingOption()">
<html:form action="updateRatingMaster.do?method=updateRatingMaster" method="POST" enctype="multipart/form-data" focus="rating">

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
                        <td width="25%" class="Heading">&nbsp;
						<bean:message key="ratingDetails" />
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
				<TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="selectRating" />
					</TD>
					<TD align="left" class="TableData">
						<html:select property="rating" name="ifForm" onchange="javascript:ratingOption(this);submitForm('showRatingMaster.do?method=showRatingDetails')">
							<html:option value="">Select</html:option>
							<html:options property="instrumentRatings" name="ifForm"/>			
						</html:select>
					</TD>
			  <bean:define id="nVal" name="ifForm" property="newRating"/>
			  <%
			  String reqVal1 = (String)nVal;
			  if (request.getParameter("newRating")!=null)
			  {
				reqVal1 = (String)request.getParameter("newRating");
			  }
			  %>
					<TD align="left" class="ColumnBackground"><bean:message key="ratingEnter" />
						<html:text property="newRating" size="20" alt="Enter" name="ifForm" maxlength="10" value="<%=reqVal1%>"/>
					</TD>									
				</TR>
			  <bean:define id="mVal" name="ifForm" property="modRating"/>
			  <%
			  String reqVal = (String)mVal;
			  if (request.getParameter("modRating")!=null)
			  {
				reqVal = (String)request.getParameter("modRating");
			  }
			  %>
                <tr>
                  <td  class="ColumnBackground" height="25">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="modRating" /> &nbsp;</td>
                  <td class="tableData" width="474" height="25" colspan="2"> <div align="left"> 
                      <html:text  property="modRating" size="20" alt="Rating" name="ifForm" maxlength="10" value="<%=reqVal%>"/>
                    </div></td>
                </tr>
                <tr>
                  <td  class="ColumnBackground" height="25"> &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="ratingDescription" /> &nbsp;</td>
                  <td class="tableData" width="474" height="25" colspan="2"> <div align="left"> 
<!--                      <html:text  property="ratingDescription" size="20" alt="Rating Description" name="ifForm" maxlength="100"/>-->
						<html:textarea name="ifForm" property="ratingDescription" rows="3" cols="50"/>
                    </div></td>
                </tr>
<!--
                <tr>
                  <td  class="ColumnBackground" height="25"> &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
 				  <bean:message key="ratingGivenBy" /> 
				  
                    </td>
                  <td class="tableData" width="474" height="25" colspan="2"> <div align="left"> 
                      <html:text  property="ratingGivenBy" size="20"  alt="Rating GivenBy" name="ifForm" maxlength="100"/>
                    </div></td>
                </tr>
-->
              </table>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center"><html:link href="javascript:submitForm('updateRatingMaster.do?method=updateRatingMaster')"><img src="images/Save.gif" alt="Ok" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link></div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
