<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
String req = (String)session.getAttribute("modFlag");
if (req.equals("0"))
{
	session.setAttribute("CurrentPage","showHolidayMaster.do?method=showHolidayMaster");
}
else if (req.equals("1"))
{
	session.setAttribute("CurrentPage","showHolidayMaster.do?method=showHolidayDetail");
}
%>
<body onload="holidayDateOption()">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
   <html:errors />
  <html:form action="updateHolidayMaster.do?method=updateHolidayMaster" method="POST" enctype="multipart/form-data" focus="holidayDate">
 
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
						<bean:message key="holidayDetailsHeading" />
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
						&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="selectHolidayDate" />
					</TD>
					<TD align="left" class="TableData">
						<html:select property="holidayDate" name="ifForm" onchange="javascript:holidayDateOption(this);javascript:submitForm('showHolidayMaster.do?method=showHolidayDetail')">
							<html:option value="">Select</html:option>
							<html:options property="holidayDates" name="ifForm"/>			
						</html:select>
					</TD>
					<TD align="left" class="ColumnBackground"><bean:message key="holidayDateEnter" />
						<html:text property="newHolidayDate" size="20" alt="Enter" name="ifForm" maxlength="10"/>
						<img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.newHolidayDate')" align="center">
					</TD>									
				</TR>
			  <bean:define id="mVal" name="ifForm" property="modHolidayDate"/>
			  <%
			  String reqVal = (String)mVal;
			  if (request.getParameter("modHolidayDate")!=null)
			  {
				reqVal = (String)request.getParameter("modHolidayDate");
			  }
			  %>
                <tr>
                  <td class="ColumnBackground"> &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; <bean:message key="modHolidayDate" /> </td></td>
                  <td class="tableData" width="474" align="center" colspan="2"> <div align="left"> 
                      <html:text  property="modHolidayDate" size="20" alt="Holiday Date" name="ifForm" maxlength="10" value="<%=reqVal%>"/>
					  <img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.modHolidayDate')" align="center">
                    </div></td>
                </tr>
                <tr>
                  <td class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; <bean:message key="holidayDescription" /> </td>
                  <td class="tableData" width="474" colspan="2"> <div align="left"> 
                      <html:text  property="holidayDescription" size="50" alt="Holiday Description" name="ifForm" maxlength="50"/>
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
          <div align="center"><html:link href="javascript:submitForm('updateHolidayMaster.do?method=updateHolidayMaster')"><img src="images/Save.gif" alt="Ok" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link></div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51" ></td>
  </tr>

 </html:form>
</table>
</body>