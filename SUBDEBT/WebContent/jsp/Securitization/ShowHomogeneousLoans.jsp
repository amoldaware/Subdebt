<%@ page language="java"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>

<% session.setAttribute("CurrentPage","getHomogenousLoans.do?method=getHomogenousLoans");%>
<HTML>
	<BODY>
		<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
			<html:errors />
			<html:form action="showPoolDetails.do" method="POST">
				<TR>
					<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
					<TD background="images/TableBackground1.gif">
					<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
				</TR>
				<TR>
					<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
					<TD>
						<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
							<TR>
								<TD>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
										<TR>
											<TD colspan="5">
												<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
													<TR>
														<TD width="31%" class="Heading"><bean:message key="loanPool"/></TD>
														<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
													</TR>
													<TR>
														<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
													</TR>

												</TABLE>
											</TD>
										</TR>
										<TR>
											<TD class="ColumnBackground">
												<bean:message key="industrySector"/>
											</TD>
											<TD class="ColumnBackground">
												<bean:message key="state"/>
											</TD>
											<TD class="ColumnBackground">
												<bean:message key="noOfLoans"/>
											</TD>
											<TD class="ColumnBackground">
												<bean:message key="totalSanctionedAmt"/>(<bean:message key="inRs"/>)
											</TD>
											<TD class="ColumnBackground">
												<bean:message key="totalGuaranteedAmount"/>(<bean:message key="inRs"/>)
											</TD>
										</TR>
										<%

										boolean isAvailable=false;
										String sectorTemp="";
										int counter=0;
										double gcTotal=0;
										double sanctionedTotal=0;
										double grandGcTotal=0;
										double grandSanctionedTotal=0;
										int totalApps=0;
										int grandTotalApps=0;
										String loanTypeTemp="TC";

										%>
										<bean:define id="loanType" name="securitizationForm" property="loanType" type="java.lang.String"/>
										<bean:define id="consolidatedLoans" name="securitizationForm" property="homogeneousLoans" type="java.util.ArrayList"/>
										<%if(loanType.equals("BO")){%>
										<logic:iterate id="loans" name="consolidatedLoans">

										<TR>
											<TD class="SubHeading">
												<%=(counter==0?"TC Applications":"WC Applications")%>
											</TD>
										</TR>
										<%
											gcTotal=0;
											sanctionedTotal=0;
											grandGcTotal=0;
											grandSanctionedTotal=0;
											totalApps=0;
											grandTotalApps=0;
											if(counter==0)
											{
												loanTypeTemp="TC";
											}
											else
											{
												loanTypeTemp="WC";
											}
											counter++;
										%>
										<logic:iterate id="object" name="loans" >

										<%
											com.cgtsi.securitization.HomogeneousLoan loan=(com.cgtsi.securitization.HomogeneousLoan)object;

											String sector=loan.getSector();

											java.util.ArrayList states=loan.getStates();
											pageContext.setAttribute("states",states);
											isAvailable=true;
											sectorTemp=sector;
											gcTotal=0;
											sanctionedTotal=0;
											totalApps=0;
										%>
											<logic:iterate id="sectorWise" name="states" >
											<%
												com.cgtsi.securitization.SectorWise sectorWiseData=(com.cgtsi.securitization.SectorWise)sectorWise;

												String stateName=sectorWiseData.getState();
												gcTotal+=sectorWiseData.getTotalGC();
												sanctionedTotal+=sectorWiseData.getTotalSanctionedAmt();
												grandGcTotal+=sectorWiseData.getTotalGC();
												grandSanctionedTotal+=sectorWiseData.getTotalSanctionedAmt();
												totalApps+=sectorWiseData.getNoOfLoans();
												grandTotalApps+=sectorWiseData.getNoOfLoans();
											%>
											<TR>
												<TD class="ColumnBackground">
												<%=sector%>
												</TD>
												<% sector=""; %>
												<TD class="ColumnBackground">
													<A href="showStateWise.do?method=getStateWiseLoans&state=<%=stateName%>&sector=<%=sectorTemp%>&loanType=<%=loanTypeTemp%>"><%= stateName %></A>
												</TD>
												<TD class="ColumnBackgroundRight">
													<bean:write property="noOfLoans" name="sectorWise"/>
												</TD>
												<TD class="ColumnBackgroundRight">
													<bean:write property="totalSanctionedAmt" name="sectorWise"/>
												</TD>
												<TD class="ColumnBackgroundRight">
													<bean:write property="totalGC" name="sectorWise"/>
												</TD>

											</TR>
											</logic:iterate>
											<TR>
												<TD class="TableDataCenter" colspan="2">
												Total
												</TD>
												<TD class="TableData">
													<%=totalApps%>
												</TD>
												<TD class="TableData">
													<%=gcTotal%>
												</TD>
												<TD class="TableData">
													<%=sanctionedTotal%>
												</TD>
											</TR>
										</logic:iterate>
										<%
										if(!isAvailable){
										%>
										<TR>
											<TD colspan="5" class="TableData">
												No Data is available. Please select other combination.
											</TD>
										</TR>
										<%}else{%>

										<TR>
											<TD colspan="2" class="HeadingBg">
												Grand Total
											</TD>
											<TD class="HeadingBg">
												<%=grandTotalApps%>
											</TD>
											<TD class="HeadingBg" align="right">
												<%=grandSanctionedTotal%>
											</TD>
											<TD  class="HeadingBg" >
												<%=grandSanctionedTotal%>
											</TD>
										</TR>
										<%}%>
										</logic:iterate>
										<%-- If the loan type is TC or WC....--%>
										<%}else{%>

											<logic:iterate id="object" property="homogeneousLoans" name="securitizationForm" >

											<%
												com.cgtsi.securitization.HomogeneousLoan loan=(com.cgtsi.securitization.HomogeneousLoan)object;

												String sector=loan.getSector();

												java.util.ArrayList states=loan.getStates();
												pageContext.setAttribute("states",states);
												isAvailable=true;
												sectorTemp=sector;
												gcTotal=0;
												sanctionedTotal=0;
												totalApps=0;
												counter++;
											%>
												<logic:iterate id="sectorWise" name="states" >
												<%
													com.cgtsi.securitization.SectorWise sectorWiseData=(com.cgtsi.securitization.SectorWise)sectorWise;

													String stateName=sectorWiseData.getState();
													gcTotal+=sectorWiseData.getTotalGC();
													sanctionedTotal+=sectorWiseData.getTotalSanctionedAmt();
													grandGcTotal+=sectorWiseData.getTotalGC();
													grandSanctionedTotal+=sectorWiseData.getTotalSanctionedAmt();
													totalApps+=sectorWiseData.getNoOfLoans();
													grandTotalApps+=sectorWiseData.getNoOfLoans();
												%>
												<TR>
													<TD class="ColumnBackground">
													<%=sector%>
													</TD>
													<% sector=""; %>
													<TD class="ColumnBackground">
														<A href="showStateWise.do?method=getStateWiseLoans&state=<%=stateName%>&sector=<%=sectorTemp%>&loanType=<%=loanType%>"><%= stateName %></A>
													</TD>
													<TD class="ColumnBackgroundRight">
														<bean:write property="noOfLoans" name="sectorWise"/>
													</TD>
													<TD class="ColumnBackgroundRight">
														<bean:write property="totalSanctionedAmt" name="sectorWise"/>
													</TD>
													<TD class="ColumnBackgroundRight">
														<bean:write property="totalGC" name="sectorWise"/>
													</TD>

												</TR>
												</logic:iterate>
												<TR>
													<TD class="TableDataCenter" colspan="2">
													Total
													</TD>
													<TD class="TableDataRight">
														<%=totalApps%>
													</TD>
													<TD class="TableDataRight">
														<%=gcTotal%>
													</TD>
													<TD class="TableDataRight">
														<%=sanctionedTotal%>
													</TD>
												</TR>
											</logic:iterate>
											<%
											if(!isAvailable){
											%>
											<TR>
												<TD colspan="5" class="TableData">
													No Data is available. Please select other combination.
												</TD>
											</TR>
											<%}else{%>

											<TR>
												<TD colspan="2" class="HeadingBg">
													Grand Total
												</TD>
												<TD class="HeadingBgRight">
													<%=grandTotalApps%>
												</TD>
												<TD class="HeadingBgRight">
													<%=grandSanctionedTotal%>
												</TD>
												<TD  class="HeadingBgRight">
													<%=grandGcTotal%>
												</TD>
											</TR>
											<%}%>
										<%}%>
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

										<%
										String imageName="images/Save.gif";
										String action="javascript:submitForm('showPoolDetails.do?method=showPoolDetails')";

										if(!isAvailable)
										{
											imageName="images/Back.gif";
											action="javascript:history.back()";
										}
										if(isAvailable){
										%>
										<A href="<%=action%>">
										<IMG src="<%=imageName%>" alt="OK" width="49" height="37" border="0"></A>
										<%}%>
										<A href="javascript:history.back()">
										<IMG src="images/Back.gif" alt="Cancel" width="49" height="37" border="0"></A>

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
	</BODY>
</HTML>



