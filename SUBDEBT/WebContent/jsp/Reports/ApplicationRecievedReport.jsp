<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","applicationRecievedReport.do?method=applicationRecievedReport");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form  action="applicationRecievedReportDetails.do?method=applicationRecievedReportDetails" method="POST" >
		<TR>
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
			<A HREF="applicationRecievedReportHelp.do?method=applicationRecievedReportHelp">
			HELP</A>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">

					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="recievedApplicationReportHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>

									<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="field" />
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="select" />
									</TD>
									</TR>
									    
									<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground1">
										<bean:message key="selectAll" />
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<html:checkbox property="selectAll" onclick="selectAllItems(this)" alt="Close" name="rsForm" value="Y"/>
									</TD>
									</TR>	
									<%
									String req="";
									String req1="";
									String req2="";
									String req3="";
									String req4="";
									String req5="";
									String req6="";
									String req7="";
									String req8="";
									String req9="";
									String req10="";
									String req11="";
									String req12="";
									String req13="";
									String req14="";

									if (request.getParameter("selectAll")!=null)
									{
										req=(String)request.getParameter("selectAll");
									}
									else{

										req=null;
									}

									if (request.getParameter("applicationDate")!=null)
									{
										req1=(String)request.getParameter("applicationDate");
									}
									else{

										req1=null;
									}

									if (request.getParameter("promoter")!=null)
									{
										req2=(String)request.getParameter("promoter");
									}
									else{
										req2=null;
									}
									if (request.getParameter("itpan")!=null)
									{
										req3=(String)request.getParameter("itpan");
									}
									else{
										req3=null;
									}

									if (request.getParameter("ssiDetails")!=null)
									{
										req4=(String)request.getParameter("ssiDetails");
									}
									else{
										req4=null;
									}

									if (request.getParameter("industryType")!=null)
									{
										req5=(String)request.getParameter("industryType");
									}
									else{
										req5=null;
									}

									if (request.getParameter("termCreditSanctioned")!=null)
									{
										req6=(String)request.getParameter("termCreditSanctioned");
									}
									else{
										req6=null;
									}

									if (request.getParameter("tcInterest")!=null)
									{
										req7=(String)request.getParameter("tcInterest");
									}
									else{
										req7=null;
									}

									if (request.getParameter("tcTenure")!=null)
									{
										req8=(String)request.getParameter("tcTenure");
									}
									else{
										req8=null;
									}

									if (request.getParameter("tcPlr")!=null)
									{
										req9=(String)request.getParameter("tcPlr");
									}
									else{
										req9=null;
									}

									if (request.getParameter("tcOutlay")!=null)
									{
										req10=(String)request.getParameter("tcOutlay");
									}
									else{
										req10=null;
									}

									if (request.getParameter("workingCapitalSanctioned")!=null)
									{
										req11=(String)request.getParameter("workingCapitalSanctioned");
									}
									else{
										req11=null;
									}

									if (request.getParameter("wcPlr")!=null)
									{
										req12=(String)request.getParameter("wcPlr");
									}
									else{
										req12=null;
									}

									if (request.getParameter("wcOutlay")!=null)
									{
										req13=(String)request.getParameter("wcOutlay");
									}
									else{
										req13=null;
									}

									if (request.getParameter("rejection")!=null)
									{
										req14=(String)request.getParameter("rejection");
									}
									else{
										req14=null;
									}

									%>
									<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground1">
										<bean:message key="applicationDate" />
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<html:checkbox property="applicationDate" alt="Close" name="rsForm" value="<%=req1%>"/>
									</TD>
									</TR>	

									<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground1">
										<bean:message key="promoter" />
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<html:checkbox property="promoter" alt="Close" name="rsForm" value="<%=req2%>"/>
									</TD>
									</TR>	

									<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground1">
										<bean:message key="itpan" />
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<html:checkbox property="itpan" alt="Close" name="rsForm" value="<%=req3%>"/>
									</TD>
									</TR>	

									<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground1">
										<bean:message key="ssi" />
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<html:checkbox property="ssiDetails" alt="Close" name="rsForm" value="<%=req4%>"/>
									</TD>
									</TR>	

									<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground1">
										<bean:message key="industryType" />
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<html:checkbox property="industryType" alt="Close" name="rsForm" value="<%=req5%>"/>
									</TD>
									</TR>	

									<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground1">
										<bean:message key="termCreditSanctioned" />
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<html:checkbox property="termCreditSanctioned" alt="Close" name="rsForm" value="<%=req6%>"/>
									</TD>
									</TR>	

									<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground1">
										<bean:message key="tcInterest" />
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<html:checkbox property="tcInterest" alt="Close" name="rsForm" value="<%=req7%>"/>
									</TD>
									</TR>	

									<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground1">
										<bean:message key="tcTenure" />
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<html:checkbox property="tcTenure" alt="Close" name="rsForm" value="<%=req8%>"/>
									</TD>
									</TR>	

									<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground1">
										<bean:message key="tcPlr" />
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<html:checkbox property="tcPlr" alt="Close" name="rsForm" value="<%=req9%>"/>
									</TD>
									</TR>	

									<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground1">
										<bean:message key="tcOutlay" />
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<html:checkbox property="tcOutlay" alt="Close" name="rsForm" value="<%=req10%>"/>
									</TD>
									</TR>	

									<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground1">
										<bean:message key="workingCapitalSanctioned" />
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<html:checkbox property="workingCapitalSanctioned" alt="Close" name="rsForm" value="<%=req11%>"/>
									</TD>
									</TR>
									
									<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground1">
										<bean:message key="wcPlr" />
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<html:checkbox property="wcPlr" alt="Close" name="rsForm" value="<%=req12%>"/>
									</TD>
									</TR>

									<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground1">
										<bean:message key="wcOutlay" />
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<html:checkbox property="wcOutlay" alt="Close" name="rsForm" value="<%=req13%>"/>
									</TD>
									</TR>

									<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground1">
										<bean:message key="rejection" />
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<html:checkbox property="rejection" alt="Close" name="rsForm" value="<%=req14%>"/>
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
							    <A href="javascript:submitForm('applicationRecievedReportDetails.do?method=applicationRecievedReportDetails')">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
							    <A href="javascript:submitForm('applicationRecievedReportInput.do?method=applicationRecievedReportInput')">
									<IMG src="images/Back.gif" alt="OK" width="49" height="37" border="0"></A>
								<A href="javascript:document.rsForm.reset()">
									<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>
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

