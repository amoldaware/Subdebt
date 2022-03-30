<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showBroadcastMessage.do?method=showBroadcastMessage");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<body onLoad="reloadBroadcast()">
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="addBroadcastMessage.do" method="POST">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpBroadcastMessage.do?method=helpBroadcastMessage')">
			    HELP</A>
			</DIV>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="broadcastMessageHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>

								 <TR align="left">
									<TD class="ColumnBackground" width="30%">
										<table width="100%" border="0" cellspacing="1" cellpadding="0">
											
											<TR>
												<TD align="left" valign="top" class="ColumnBackground"><html:radio name="regForm" value="AllHos" property="selectBM" onclick="chooseBroadcast(this);javascript:submitForm('showBroadcastMessage.do?method=flush')"  >&nbsp;<bean:message key="allHos" /></html:radio></TD>
											</TR>
											<TR>
												<TD align="left" valign="top" class="ColumnBackground"><html:radio name="regForm" value="membersOfBank" property="selectBM" onclick="chooseBroadcast(this);javascript:submitForm('showBroadcastMessage.do?method=flush')">&nbsp;<bean:message key="membersOfBank" /></html:radio></TD>
											</TR>
											<TR>
												<TD align="left" valign="top" class="ColumnBackground"><html:radio name="regForm" value="membersOfZone" property="selectBM" onclick="chooseBroadcast(this);javascript:submitForm('showBroadcastMessage.do?method=flush')">&nbsp;<bean:message key="membersOfZone" /></html:radio></TD>
											</TR>
											<TR>
												<TD align="left" valign="top" class="ColumnBackground"><html:radio name="regForm" value="membersOfBranch" property="selectBM" onclick="chooseBroadcast(this);javascript:submitForm('showBroadcastMessage.do?method=flush')">&nbsp;<bean:message key="membersOfBranch" /></html:radio></TD>
											</TR>
											<TR>
												<TD align="left" valign="top" class="ColumnBackground"><html:radio name="regForm" value="noOfBank" property="selectBM" onclick="chooseBroadcast(this);javascript:submitForm('showBroadcastMessage.do?method=flush')">&nbsp;<bean:message key="noOfBank" /></html:radio></TD>
											</TR>											
											<TR>
												<TD align="left" valign="top" class="ColumnBackground"><html:radio name="regForm" value="noOfBranches" property="selectBM" onclick="chooseBroadcast(this);javascript:submitForm('showBroadcastMessage.do?method=flush')">&nbsp;<bean:message key="noOfBranches" /></html:radio></TD>
											</TR>
											<TR>
												<TD align="left" valign="top" class="ColumnBackground"><html:radio name="regForm" value="noOfZones" property="selectBM" onclick="chooseBroadcast(this);javascript:submitForm('showBroadcastMessage.do?method=flush')">&nbsp;<bean:message key="noOfZone" /></html:radio></TD>
											</TR>
											<TR>
												<TD align="left" valign="top" class="ColumnBackground"><html:radio name="regForm" value="allMembers" property="selectBM" onclick="chooseBroadcast(this);javascript:submitForm('showBroadcastMessage.do?method=flush')"  >&nbsp;<bean:message key="allMembers" /></html:radio></TD>
											</TR>
										</table>
									</TD>			
									<TD align="left" class="TableData" width="20%"><bean:message 			key="bankNames" /><br>
										<html:select property="bankName" name="regForm" onchange="javascript:submitForm('showBroadcastMessage.do?method=actionTaken')" >
											<html:option value="Select">Select</html:option>
											<html:options property="allBanks" name="regForm"/>
												
										</html:select>
									</TD>
									<bean:define id="zonesTemp" name="regForm" property="zones" type="java.util.ArrayList"/>

									<% 
										int size=zonesTemp.size();									

										String style="";

										if(size==0)
										{
											style="width:75";
										}
										
									%>

									<TD align="left" class="TableData" width="25%"><bean:message 				key="zoneRegionNames" />
										<html:select property="zoneRegionNames" name="regForm" multiple="true" style="<%=style%>">
										<html:options property="zones" name="regForm"/>		
										</html:select><br>
									</TD>

									<bean:define id="branchesTemp" name="regForm" property="branches" type="java.util.ArrayList"/>

									<% 
										int branchSize=branchesTemp.size();									

										String branchStyle="";

										if(branchSize==0)
										{
											branchStyle="width:75";
										}
										
									%>
									<TD align="left" class="TableData" width="25%"><bean:message 			key="branchNames" /><br>
										<html:select property="branchNames" name="regForm" multiple="true" style="<%=branchStyle%>">
										<html:options property="branches" name="regForm"/> 
										</html:select><br>
									</TD>
								 </TR>
			
								 <TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="broadcastMessage" />
									</TD>
									<TD align="left" class="TableData" colspan="3"> 
										<html:textarea property="broadcastMessage" cols="55" rows="3" alt="Message" name="regForm"/>
									</TD>			
								 </TR>
							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
							<A				
								href="javascript:submitForm('addBroadcastMessage.do?method=addBroadcastMessage')"><IMG src="images/Submit.gif" alt="Save" width="49" height="37" border="0"></A>
								<A
								href="javascript:document.regForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
							<a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
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
</body >



