<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","AfterNotAppropriatedDetailsfromInward.do?method=AfterNotAppropriatedDetailsfromInward");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("#########0.0##");%>

<TABLE width="800" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="AfterNotAppropriatedDetailsfromInward.do?method=AfterNotAppropriatedDetailsfromInward" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
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
									<TD colspan="13"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
                          <td colspan="6" class="Heading1" align="center"><u>Credit Guarantee Fund Trust for Micro and Small Enterprises&nbsp;</u></td>
                      </tr>
                      <tr> <td colspan="6">&nbsp;</td></tr>
                      
                      <TR>
												<TD width="18%" class="Heading">Not Appropriated Cases Report from Inward</TD>
												<td class="Heading" width="40%">&nbsp;<bean:message key="from"/> <bean:write  name="rsForm" property="dateOfTheDocument26"/>&nbsp;<bean:message key="to"/> <bean:write  name="rsForm" property="dateOfTheDocument27"/></td>
                        <TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
        			</TABLE>
									</TD>

									<TR>
                  <td width="3%" align="left" valign="top" class="ColumnBackground"><bean:message key="sNo"/></td>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										Inward Dt
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										Inward No.
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										Section
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									Bank Name
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									Place
									</TD>
									<TD width="15%" align="left" valign="top" class="ColumnBackground">
										Ltr Ref No.
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										Ltr Dt.
									</TD>
									<TD width="15%" align="left" valign="top" class="ColumnBackground">
									Subject
									</TD>
                  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="instrumentNumber" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="instrumentDate"/>
									</TD>
									<TD width="10%" align="right" valign="top" class="ColumnBackground">
										Instrument Amt.
									</TD>
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Drawn On Bank&nbsp;
									</TD>
									</TR>	
	
								<%
										double amount = 0;
										double totalAmount = 0;
									%>

									<tr>
								<logic:iterate name="rsForm" id="object" property="danRaised" indexId="index">
               	<%
									     com.cgtsi.reports.GeneralReport danReport = (com.cgtsi.reports.GeneralReport)object;
							  %>
										<TR>
                      <td align="left" valign="top" class="ColumnBackground1"><%=Integer.parseInt(index+"")+1%></td>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<% java.util.Date utilDate3=danReport.getDateOfTheDocument1();
													String inwardDt = null;
													if(utilDate3 != null)
													{
														 inwardDt=dateFormat.format(utilDate3);
													}
													else
													{
														 inwardDt = "";
													}
											%>
											<%=inwardDt%> 
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=danReport.getInwardNum()%> 
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=danReport.getInwardSection()%> 
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String bankName= danReport.getBankName();
											if((bankName == null)||(bankName.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=bankName%>
											<%
											}
											%>
               			</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
                      <%String place= danReport.getPlace();
											if((place == null)||(place.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=place%>
											<%
											}
											%>
											</TD>
											<TD width="15%" align="left" valign="top" class="ColumnBackground1">						
											 <%String ltrRefNo= danReport.getLtrRefNo();
											if((ltrRefNo == null)||(ltrRefNo.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=ltrRefNo%>
											<%
											}
											%>
             				</TD>
											<TD width="15%" align="left" valign="top" class="ColumnBackground1">						
											<% java.util.Date utilDate4=danReport.getDateOfTheDocument10();
													String ltrDt = null;
													if(utilDate4 != null)
													{
														 ltrDt=dateFormat.format(utilDate4);
													}
													else
													{
														 ltrDt = "";
													}
											%>
											<%=ltrDt%>

											</TD>
                      <TD width="20%" align="left" valign="top" class="ColumnBackground1">						
											<%String subject= danReport.getSubject();
											if((subject == null)||(subject.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=subject%>
											<%
											}
											%>
                 			</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=danReport.getInstrumentNum()%> 
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											 <% java.util.Date utilDate5=danReport.getDateOfTheDocument11();
													String instrumentDt = null;
													if(utilDate5 != null)
													{
														 instrumentDt=dateFormat.format(utilDate5);
													}
													else
													{
														 instrumentDt = "";
													}
											%>
											<%=instrumentDt%>
			</TD>
      <TD width="10%" align="right" valign="top" class="ColumnBackground1">
      <div align="right"><%totalAmount = totalAmount + danReport.getInstrumentAmt(); %>
      <%=decimalFormat.format(danReport.getInstrumentAmt())%> </div>
      </TD>
      <TD width="10%" align="left" valign="top" class="ColumnBackground1">
      <%String drawnonBank= danReport.getDrawnonBank();
											if((drawnonBank == null)||(drawnonBank.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=drawnonBank%>
											<%
											}
											%>
       </TD>
											</TR>
											</logic:iterate>
        			<TR>
											<TD width="10%" align="left" colspan="2" valign="top" class="ColumnBackground">						
											Total
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">			
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											</TD>
                      <TD width="10%" align="left" valign="top" class="ColumnBackground">		
                       <div align="right">
											<%=decimalFormat.format(totalAmount)%>
											</div>
											</TD>
                      <TD width="10%" align="left" valign="top" class="ColumnBackground">	
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
								<A href="javascript:submitForm('NotAppropriatedDetailsfromInward.do?method=NotAppropriatedDetailsfromInward')">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>									
								<INPUT type="button" value="Export to Excel" onclick="exportToExcel()" />                
        					<!-- <IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A> -->
								
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

