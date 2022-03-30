<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
	session.setAttribute("CurrentPage","showMiscReceiptsFilter.do?method=showMiscReceiptsFilter");
%>
<body>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
   <html:errors />
  <html:form action="showMiscReceipts.do?method=showMiscReceipts" method="POST" enctype="multipart/form-data" focus="miscReceiptsDate">
 
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
				<A HREF="javascript:submitForm('helpMiscReceiptsFilter.do')">
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
						<bean:message key="MiscReceiptsHeading" />
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
						&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="MiscReceiptsDate" />
					</TD>
					<TD align="left" class="ColumnBackground">
						<html:text property="miscReceiptsDate" size="20" alt="Enter" name="investmentForm" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/>
						<img src="images/CalendarIcon.gif" onclick="showCalendar('investmentForm.miscReceiptsDate')" align="center">
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
          <div align="center"><html:link href="javascript:submitForm('showMiscReceipts.do?method=showMiscReceipts')"><img src="images/Save.gif" alt="Ok" width="49" height="37" border="0"></html:link><html:link href="javascript:document.investmentForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link>
		  <a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
								<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
			</div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51" ></td>
  </tr>

 </html:form>
</table>
</body>