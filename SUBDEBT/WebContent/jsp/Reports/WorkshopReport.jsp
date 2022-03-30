<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","workshopReport.do?method=workshopReport");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("#########0.0##");%>

<SCRIPT language="javascript">
                         function exportToExcel() 
                    {
                    
                 document.addded.target ="_self";
                 document.addded.method="POST";
                 document.addded.action="/cgtsi-ViewController-context-root/HtmlToExcel.do?method=excelConvertForInwardReport";
                 document.addded.submit();
                 }
                 
 </SCRIPT>

<form name="addded" action="workshopReport.do?method=workshopReport">

<TABLE width="800" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="inwardReport.do?method=inwardReport" method="POST" enctype="multipart/form-data">
        hello hello
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
									<TD colspan="16"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
                          <td colspan="6" class="Heading1" align="center"><u>Credit Guarantee Fund Trust for Micro and Small Enterprises&nbsp;</u></td>
                      </tr>
                      <tr> <td colspan="6">&nbsp;</td></tr>
                      
                      <TR>
												<TD width="18%" class="Heading">Workshop Report Details</TD>
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
										<bean:message key="workshopid"/></TD>
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="workshopDt"/></TD> 
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="mliNames"/></TD>   
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="organisedfor"/></TD>  
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="agencyNames"/></TD> 
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="targetGroup"/></TD>   
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="statesList"/></TD>
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="districtList"/></TD>
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="cityName"/></TD>
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="typeofWorkshop"/></TD> 
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="topic"/></TD>  
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="participants"/></TD> 
                 <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="name"/></TD> 
                 <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="designation"/></TD>   
                 <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="organisation"/></TD>  
									</TR>	
	
									<tr>
								<logic:iterate name="rsForm" id="object" property="danRaised" indexId="index">
               	<%
									     com.cgtsi.reports.GeneralReport danReport = (com.cgtsi.reports.GeneralReport)object;
							  %>
										<TR>
                      <td align="left" valign="top" class="ColumnBackground1"><%=Integer.parseInt(index+"")+1%></td>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">
                        <%String workshopId= danReport.getInvestmentId();
											if((workshopId == null)||(workshopId.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=workshopId%>
											<%
											}
											%>
                   </TD>                      
                      <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<% java.util.Date utilDate3=danReport.getDateOfTheDocument1();
													String workshopDt = null;
													if(utilDate3 != null)
													{
														 workshopDt=dateFormat.format(utilDate3);
													}
													else
													{
														 workshopDt = "";
													}
											%>
											<%=workshopDt%> 
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
											<%=danReport.getOrganisedfor()%> 
											</TD>
                       <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String agencyName= danReport.getAgencyName();
											if((agencyName == null)||(agencyName.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=agencyName%>
											<%
											}
											%>
               			</TD>
                    
                    <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String targetGroup= danReport.getTargetGroup();
											if((targetGroup == null)||(targetGroup.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=targetGroup%>
											<%
											}
											%>
               			</TD>
                    
											
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
                      <%String state= danReport.getStateName();
											if((state == null)||(state.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=state%>
											<%
											}
											%>
											</TD>
                      <TD width="10%" align="left" valign="top" class="ColumnBackground1">	
                      <%String district= danReport.getDistrictName();
											if((district == null)||(district.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=district%>
											<%
											}
											%>
											</TD>
                      <TD width="10%" align="left" valign="top" class="ColumnBackground1">	
                      <%String city= danReport.getCity();
											if((city == null)||(city.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=city%>
											<%
											}
											%>
											</TD>                    
                      
																		
                      <TD width="20%" align="left" valign="top" class="ColumnBackground1">						
											<%String typeofWorkshop= danReport.getType();
											if((typeofWorkshop == null)||(typeofWorkshop.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=typeofWorkshop%>
											<%
											}
											%>
                 			</TD>
                       <TD width="20%" align="left" valign="top" class="ColumnBackground1">						
											<%String topic= danReport.getTopic();
											if((topic == null)||(topic.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=topic%>
											<%
											}
											%>
                 			</TD>
                      
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=danReport.getParticipants()%> 
											</TD>
                      
                      <TD width="20%" align="left" valign="top" class="ColumnBackground1">						
											<%String name= danReport.getName();
											if((name == null)||(name.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=name%>
											<%
											}
											%>
                 			</TD>
                      <TD width="20%" align="left" valign="top" class="ColumnBackground1">						
											<%String designation= danReport.getDesignation();
											if((designation == null)||(designation.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=designation%>
											<%
											}
											%>
                 			</TD>
                      
                       <TD width="20%" align="left" valign="top" class="ColumnBackground1">						
											<%String organisation= danReport.getOrganisation();
											if((organisation == null)||(organisation.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=organisation%>
											<%
											}
											%>
                 			</TD>											
											</TR>
											</logic:iterate>
        			
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
								<A href="javascript:submitForm('workshopReportInput.do?method=workshopReportInput')">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0">									
								<!-- <INPUT type="button" value="Export to Excel" onclick="exportToExcel()" />    -->            
        					 <IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A> 
								
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
</form>
