<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showRatingWiseCeiling.do?method=showRatingWiseCeiling");%>

<html:form action="setRatingWiseCeiling.do?method=setRatingWiseCeiling" method="POST" focus="investeeGroup">

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
				<A HREF="javascript:submitForm('helpRatingWiseCeiling.do?method=helpRatingWiseCeiling')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
        <tr>
            <td height="162"> <table border="0" cellspacing="1" cellpadding="0" height="112">
                <tr> 
                  <td colspan="2" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="35%" class="Heading">&nbsp;
						<bean:message key="ratingWiseCeiling" /> 
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
                  <td width="360" class="ColumnBackground" height="25"> &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="investeeGroup" /> 
                    </td>
                  <td class="tableData" width="474" height="25"> <div align="left"> 
                      <html:select name="ifForm" property="investeeGroup" onchange="submitForm('getInvesteeNamesForRating.do?method=getInvesteeNames')">
                      <html:option value="">Select </html:option>
                      <html:options name="ifForm" property="investeeGroups"/>
                      </html:select>
                    </div>
                    </td>
                </tr>
                  <td width="360" class="ColumnBackground" height="25"> &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="investee" /> 
                    </td>
                  <td class="tableData" width="474" height="25"> <div align="left"> 
                      <html:select name="ifForm" property="investeeName">
                      <html:option value="">Select </html:option>
                      <html:options name="ifForm" property="investeeNames"/>
                      </html:select>
                    </div>
                    </td>
                </tr>
                <tr>
                  <td width="360" class="ColumnBackground" height="25"> &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="instrumentName" /> 
                    </td>
                  <td class="tableData" width="474" height="25"> <div align="left"> 
                      <html:select name="ifForm" property="instrumentName" onchange="javascript:submitForm('fetchRatingWiseCeiling.do?method=fetchRatingWiseCeiling')">
                      <html:option value="">Select </html:option>
                      <html:options name="ifForm" property="instrumentNames"/>
                      </html:select>
                    </div></td>
                </tr>                
		<TR align="left" valign="top">
		  <TD align="left" valign="top" class="ColumnBackground" > <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="ratingAgencyName"/></div></TD>
		  <TD align="left" class="tableData" >
				<html:select property="ratingAgency" name="ifForm" onchange="javascript:submitForm('showRatingWiseCeiling.do?method=showRatingsForAgencyName')">
						<html:option value="">Select</html:option>
						<html:options property="agencies" name="ifForm"/>
					</html:select>

		  </TD>
		</TR>                

		<TR align="left" valign="top">
		  <TD align="left" valign="top" class="ColumnBackground" > <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="rating"/></div></TD>
		  <TD align="left" class="tableData" >
                      <html:select name="ifForm" property="rating" >
                      <html:option value="">Select </html:option>
                      <html:options name="ifForm" property="instrumentRatings"/>
                      </html:select>
		  </TD>
		</TR>
		<TR>
		  <TD align="left" valign="top" class="ColumnBackground" > <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="ceilingStartDate"/></div></TD>
		  <TD align="left" class="tableData" align="center" >
			<html:text property="ceilingStartDate" name="ifForm" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/><img src="images/CalendarIcon.gif" onClick="showCalendar('ifForm.ceilingStartDate')" align="center">
		  </TD>
		</TR>
		<TR align="left" valign="top">
		  <TD align="left" valign="top" class="ColumnBackground" > <div align="left">&nbsp;<bean:message key="ceilingEndDate"/></div></TD>
		  <TD align="left" class="tableData" align="center" >
			<html:text property="ceilingEndDate" name="ifForm" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/><img src="images/CalendarIcon.gif" onClick="showCalendar('ifForm.ceilingEndDate')" align="center">
		  </TD>
		</TR>
              </table>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center"><html:link href="javascript:submitForm('setRatingWiseCeiling.do?method=setRatingWiseCeiling')"><img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link></div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
