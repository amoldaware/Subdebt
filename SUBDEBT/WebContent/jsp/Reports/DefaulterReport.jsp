<%@ page language ="java"%>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");%>


<% session.setAttribute("CurrentPage", "showDefaulterReport.do?method=defaulterReport");%>
<BODY>
<TABLE width="725" border="0" cellspacing="0" cellpadding="0">
<html:errors/>
<html:form action="showDefaulterReport.do?method=defaulterReport" method="POST" enctype="multipart/form-data">

<TR>
		<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
		<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
		<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
	</TR>
	<TR>
		<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
		<TD>
			<TABLE width="550" border="0" cellspacing="1" cellpadding="1">
				<TR>
					<TD colspan="3">
						<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
						<TR>
							<TD width="160" class="Heading"><bean:message key="defaulterDetailsReport"/></TD>
							<TD><IMG src="images/TriangleSubhead.gif" width="35" height="25"></TD>
						</TR>
						<TR>
							<TD colspan="3" class="heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
						</TR>
						</TABLE>
					</TD>
				</TR>
				
				<TR>
				<TD>
					
					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<TR>
							<TD width="10%" align="left" valign="top" class="ColumnBackground">
								<bean:message key="borrUnitName"/>
							</TD>

							<TD width="10%" align="left" valign="top" class="ColumnBackground">
								<bean:message key="address"/>
							</TD>

							<TD width="10%" align="left" valign="top" class="ColumnBackground">
								<bean:message key="itpanOfTheUnit"/>
							</TD>

							<TD width="10%" align="left" valign="top" class="ColumnBackground">
								<bean:message key="chiefPromoterName"/>
							</TD>

							<TD width="10%" align="left" valign="top" class="ColumnBackground">
								<bean:message key="firstPromoterName"/>
							</TD>

							<TD width="10%" align="left" valign="top" class="ColumnBackground">
								<bean:message key="secondPromoterName"/>
							</TD>

							<TD width="10%" align="left" valign="top" class="ColumnBackground">
								<bean:message key="thirdPromoterName"/>
							</TD>

							<TD width="10%" align="left" valign="top" class="ColumnBackground">
								<bean:message key="chiefPromoterDOB"/>
							</TD>

							<TD width="10%" align="left" valign="top" class="ColumnBackground">
								<bean:message key="frPromoterDOB"/>
							</TD>

							<TD width="10%" align="left" valign="top" class="ColumnBackground">
								<bean:message key="scPromoterDOB"/>
							</TD>

							<TD width="10%" align="left" valign="top" class="ColumnBackground">
								<bean:message key="tdPromoterDOB"/>
							</TD>

							<TD width="10%" align="left" valign="top" class="ColumnBackground">
								<bean:message key="itpanOfTheChiefPromoter"/>
							</TD>

							<TD width="10%" align="left" valign="top" class="ColumnBackground">
								<bean:message key="legalIDOfThechiefPromoter"/>
							</TD>

							<TD width="10%" align="left" valign="top" class="ColumnBackground">
								<bean:message key="operatingOffice"/>
							</TD>

							<TD width="10%" align="left" valign="top" class="ColumnBackground">
								<bean:message key="mli"/>
							</TD>
						</TR>
						<TR>
							<logic:iterate name="defaulterReport" property="default" id="object">

							<% 
								com.cgtsi.reports.DefaulterInputFields defaulterfields = (com.cgtsi.reports.DefaulterInputFields)object;
							%>

								<TR>
									<TD width="10%" align="left" class="ColumnBackground">

									<%=defaulterfields.getBorrUnitName()%>
									</TD>

									<TD width="10%" align="left" class="ColumnBackground">

									<%=defaulterfields.getAddress()%>
									</TD>

									<TD width="10%" align="left" class="ColumnBackground">

									<%=defaulterfields.getItpanOfTheUnit()%>
									</TD>

									<TD width="10%" align="left" class="ColumnBackground">

									<%=defaulterfields.getChiefPromoterName()%>
									</TD>
									<%
									     String firstPromoterName = defaulterfields.getFirstPromoterName();
									     if(firstPromoterName == null)
									     {
									         firstPromoterName = "";
									     }
									%>							
									<TD width="10%" align="left" class="ColumnBackground">
                                                                             <%=firstPromoterName%>
									</TD>
				                                        <%
				                                             String secPromoterName = defaulterfields.getSecondPromoterName();
				                                             if(secPromoterName == null)
				                                             {
				                                                  secPromoterName = "";
				                                             }
				                                        %>
									<TD width="10%" align="left" class="ColumnBackground">
                                                                        
									<%=secPromoterName%>
									</TD>
                                                                        <%
                                                                             String thirdPromoName = defaulterfields.getThirdPromoterName();
                                                                             if(thirdPromoName == null)
                                                                             {
                                                                                 thirdPromoName = "";
                                                                             }
                                                                        %>
									<TD width="10%" align="left" class="ColumnBackground">

									<%=thirdPromoName%>
									</TD>
								
								
									<TD width="10%" align="left" class="ColumnBackground">
									<% java.util.Date utilDate=defaulterfields.getChiefPromoterDOB();
									String formatedDate = null;
									if(utilDate != null)
									{
										formatedDate=dateFormat.format(utilDate);
									}
									else
									{
										formatedDate="";
									}
									%>
									<%=formatedDate%>
									</TD>

									<TD width="10%" align="left" class="ColumnBackground">
									<% java.util.Date utilDate1 = defaulterfields.getFrPromoterDOB();
									String formatedDate1 = null;
									if(utilDate1 != null)
									{
										formatedDate1=dateFormat.format(utilDate1);
									}
									else
									{
										formatedDate1="";
									}
									%>
									<%=formatedDate1%>
									</TD>

									<TD width="10%" align="left" class="ColumnBackground">
									<% java.util.Date utilDate2 = defaulterfields.getScPromoterDOB();
									String formatedDate2 = null;
									if(utilDate2 != null)
									{
										formatedDate2=dateFormat.format(utilDate1);
									}
									else
									{
										formatedDate2="";
									}
									%>
									<%=formatedDate2%>
									</TD>

									<TD width="10%" align="left" class="ColumnBackground">
									<% java.util.Date utilDate3 = defaulterfields.getTdPromoterDOB();
									String formatedDate3 = null;
									if(utilDate3 != null)
									{
										formatedDate3=dateFormat.format(utilDate3);
									}
									else
									{
										formatedDate3="";
									}
									%>
									<%=formatedDate3%>
									</TD>

									<TD width="10%" align="left" class="ColumnBackground">
									<%=defaulterfields.getItpanOfTheChiefPromoter()%>
									</TD>
                                                                        <%
                                                                            String legalIdOfChiefPromoter = defaulterfields.getLegalIDOfTheChiefPromoter();
                                                                            if(legalIdOfChiefPromoter == null)
                                                                            {
                                                                                legalIdOfChiefPromoter = "";
                                                                            }
                                                                        %>
									<TD width="10%" align="left" class="ColumnBackground">
									<%=legalIdOfChiefPromoter%>
									</TD>

									<TD width="10%" align="left" class="ColumnBackground">
									<%=defaulterfields.getOperatingOffName()%>
									</TD>

									<TD width="10%" align="left" class="ColumnBackground">
									<%=defaulterfields.getMli()%>
									</TD>
								</TR>
								</logic:iterate>
							</TABLE>
							</TD>
						</TR>
						
						<TR>
							<TD colspan="3" align="center" valign="baseLine">
								<DIV align="center">
									<A href="javascript:submitForm('showDefaulterInput.do?method=showDefaulterInput')"><IMG src="images/Back.gif" alt="Submit" width="49" height="37" border="0"></A>
									
								</DIV>
							</TD>
						</TR>
					</TABLE>
				</TD>
				<TD width="20" background="images/TableVerticalRightBG.gif">&nbsp;
				</TD>
			</TR>

			<TR>
				<TD width="20" align="right" valign="top">
					<IMG src="images/TableLeftBottom1.gif" width="20" height="15">
				</TD>

				<TD background="images/TableBackground2.gif">&nbsp;</TD>
				<TD width="20" align="left" valign="top">
					<IMG src="images/TableRightBottom1.gif" width="23" height="15">
				</TD>
			</TR>
		</html:form>
		<TABLE>
		</BODY>
								
								
								
								

								

							
							

						