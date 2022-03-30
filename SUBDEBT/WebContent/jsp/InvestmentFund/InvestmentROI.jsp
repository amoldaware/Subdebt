<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","getInvestmentROI.do?method=getInvestmentROI");%>
<body >
<form>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="Fontstyle">&nbsp;</td>
    </tr>
  </table>
  <table width="718" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td align="right" valign="bottom" width="20"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
    <td width="43" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
  </tr>
  <tr>
    <td background="images/TableVerticalLeftBG.gif" width="20">&nbsp;</td>
    <td width="713"><table width="647" border="0" cellspacing="1" cellpadding="0">
	<tr>
	  <TD colspan="2">			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpInvestmentROI.do')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
          <tr> 
            <td colspan="2" width="735"><table width="643" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="207" class="Heading">&nbsp;<bean:message key="instumentWiseInterest"/></td>
                  <td width="19"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                  <td width="32">&nbsp;</td>
                  <td class="subHeading2" width="377"><div align="right">&nbsp;</div></td>
                </tr>
                <tr> 
                  <td colspan="4" class="Heading" width="596"><img src="images/Clear.gif" width="5" height="5"></td>
                </tr>
              </table></td>
          </tr>
          <TR>
			<TD class="HeadingBg"> 
				<bean:message key="instrumentName" />
			</TD>
			<TD class="HeadingBg">
				<bean:message key="rateOfInterest" />
			</TD>
	</TR>
	<TR>
	<TD class="SubHeading">
	<bean:message key="futuristic"/> 
	</TD>
	</TR>
          	<logic:iterate id="roi" name="ifForm" property="rateOfInterests">
          	<logic:notEqual property="instrumentName" value="MUTUAL FUNDS" name="roi">
			<TR>
				<TD class="ColumnBackground">
				
				<bean:write name="roi" property="instrumentName"/>
				</TD class="ColumnBackground">
				
				<TD class="ColumnBackground">
				<bean:write name="roi" property="rateOfInterest"/>

				</TD class="ColumnBackground">				
			</TR>
		</logic:notEqual>
          	</logic:iterate>
          	<logic:iterate id="roiTemp" name="ifForm" property="rateOfInterests">
          	<logic:equal property="instrumentName" value="MUTUAL FUNDS" name="roiTemp">
			<TR>
				<TD class="SubHeading">
					<bean:message key="historical"/> 
				</TD>
			</TR>
			<TR>
				<TD class="ColumnBackground">
				
				<bean:write name="roiTemp" property="instrumentName"/>
				</TD class="ColumnBackground">
				
				<TD class="ColumnBackground">
				<bean:write name="roiTemp" property="rateOfInterest"/>

				</TD class="ColumnBackground">				
			</TR>
		</logic:equal>
		</logic:iterate>
		
          <tr align="center" valign="baseline"> 
            <td colspan="2" width="735"> 
            	<div align="center">
		<A href="javascript:history.back()">
		<IMG src="images/Back.gif" alt="Go Back" width="49" height="37" border="0"></A>
		<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
		<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>		
		</div></td>
          </tr>
        </table></td>
    <td background="images/TableVerticalRightBG.gif" width="43">&nbsp;</td>
  </tr>
  <tr>
    <td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
    <td background="images/TableBackground2.gif" width="713"><div align="center"></div></td>
    <td align="left" valign="top" width="43"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
  </tr>
</table>
</form>
</body>
</HTML>