<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","updateCorrespondenceDetails.do?method=updateCorrespondenceDetails");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="updateCorrespondenceDetails.do?method=updateCorrespondenceDetails" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<DIV align="right">			
				</DIV>

				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<table width="100%" border="0" cellspacing="1" cellpadding="0">
							<tr> 
							  <td colspan="4">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="30%" class="Heading">Update Correspondence Details&nbsp;</TD>
										<TD width="70%"><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
									</TR>
									<TR>
										<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
									</TR>
								</TABLE>
							  </td>
							</tr>
							 <tr align="left"> 
							   <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="inwardId"/>			
								</td>
								<td class="TableData">
									 <html:text property="inwardId" size="20" alt="inwardId" name="ioForm" maxlength="10"/>        
								</td>
                 <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="instrumentNumber"/>			
								</td>
								<td class="TableData">
									 <html:text property="sourceIds" size="20" alt="sourceIds" name="ioForm" maxlength="6" onkeypress="return numbersOnly(this, event)"/>        
								</td>
							 </tr>
          	  </table>									
						</td>
					</tr>					
					<TR>
						<TD align="center" valign="baseline">
							<DIV align="center">
								<A href="javascript:submitForm('displayCorrespondenceDetails.do?method=displayCorrespondenceDetails')"><IMG src="images/OK.gif" alt="Save" width="49" height="37" border="0"></A>	
							<!--	<A href="javascript:document.ioForm.reset()">
									<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A> -->
								<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
								<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
							</DIV>
						</TD>
					</TR>					
				</TABLE>
			</TD>
			<TD width="20" background="images/TableVerticalRightBG.gif">
				&nbsp;
			</TD>
		</TR>
		<TR>
			<TD width="20" align="right" valign="top">
				<IMG src="images/TableLeftBottom1.gif" width="20" height="15">
			</TD>
			<TD background="images/TableBackground2.gif">
				&nbsp;
			</TD>
			<TD width="20" align="left" valign="top">
				<IMG src="images/TableRightBottom1.gif" width="23" height="15">
			</TD>
		</TR>
	</html:form>
</TABLE>

