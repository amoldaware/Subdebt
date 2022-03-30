<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.cgtsi.guaranteemaintenance.ClosureDetail"%>
<%@ page import="com.cgtsi.guaranteemaintenance.BorrowerInfo"%>
<%@ page import="com.cgtsi.guaranteemaintenance.CgpanInfo"%>
<%@page import ="java.text.DecimalFormat"%>


<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>

<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% String reason = null;
String remarks = null;
String name = null;
int closureIndex=0;
DecimalFormat decimalFormat = new DecimalFormat("##########.##");

session.setAttribute("CurrentPage","closeAppsForFeeNotPaid.do?method=closeAppsForFeeNotPaid");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="closeCgpans.do?method=closeCgpans" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpClosureDetails.do?method=helpClosureDetails')">
			    HELP</A>
			</DIV>

				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="closureHeader"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>
          
								<TR> 
									<TD colspan="4">
										<table width="751" border="0" cellspacing="1" cellpadding="0">
											<TR> 
												<TD  align="center" valign="middle" class="HeadingBg" align="center">
													<bean:message key="SerialNo"/>
												 </TD>
												 <TD class="HeadingBg" align="center"><bean:message key="MemberID"/>
												 </TD>
												 <TD class="HeadingBg" align="center"><bean:message key="BorrowerID"/>
												 </TD>
												 <TD class="HeadingBg" align="center"><bean:message key="borrowerName"/>
												 </TD>
												 <TD class="HeadingBg" align="center"><bean:message key="CGPAN"/>
												 </TD>
												 <TD class="HeadingBg" align="center"><bean:message key="scheme"/>
												 </TD>
												 <TD class="HeadingBg"  align="center"><bean:message key="sanctionedAmount"/><br>
												 </TD>
												 <TD class="HeadingBg"  align="center"><font color="#FF0000" size="2">*</font><bean:message key="ReasonsOfClosure"/> 
												 </TD>
												 <TD class="HeadingBg"  align="center"><font color="#FF0000" size="2">*</font><bean:message key="Closure"/>
												 </TD>
												 <TD class="HeadingBg"  align="center"><bean:message key="otherRemarks"/>
												 </TD>

											</TR>
                                        <%  int i=0;
										String memberId = "";
											String borrowerId = "";
											String cgpan = "";
											String borrowerName = "";
											String scheme = "";
											double sanctionedAmount = 0;
											ArrayList cgpanInfos = null;

											int borrowerIndex = 0;
											int cgpanIndex = 0;
										%>
										<TR align = "center">
										<logic:iterate id="object" name="gmCloseSFGFForm" property="closureDetailsNotPaid">
										<%
												java.util.Map.Entry closureDtlsMap = (java.util.Map.Entry)object;
												memberId = (String)closureDtlsMap.getKey() ;
												
										%>
										  <TD class="tableData" width="25" align="center"><%=++i%>
										  </TD>

										  <TD class="tableData" width="105">&nbsp;<%=memberId%>
										  </TD>
										  <% HashMap borrInfos =   (HashMap)closureDtlsMap.getValue();
									       java.util.Set borrowerSet = borrInfos.keySet() ;
										   java.util.Iterator borrowerIterator = borrowerSet.iterator() ;									   
										   while(borrowerIterator.hasNext()) {
												borrowerId = (String)borrowerIterator.next(); 
											    BorrowerInfo borrowerInfo = (BorrowerInfo)borrInfos.get(borrowerId);
											    borrowerName = borrowerInfo.getBorrowerName();			
				
										   if (borrowerIndex == 0) {
											%>
										  <TD class="tableData">&nbsp;<A
							href="javascript:submitForm('showBorrowerDetailsLink.do?method=showBorrowerDetailsLink&formFlag=clNotPaid&bidLink=<%=borrowerId%>')"><%=borrowerId%></A>
										  </TD>

										  <TD class="tableData">&nbsp;<%=borrowerName%>
										  </TD>
										  <%  
											  ++borrowerIndex;
										  } else { %>

										  <TD class="tableData"></TD>
										  <td class="tableData"></TD>
										  <TD class="tableData">&nbsp;<A
							href="javascript:submitForm('showBorrowerDetailsLink.do?method=showBorrowerDetailsLink&formFlag=clNotPaid&bidLink=<%=borrowerId%>')"><%=borrowerId%></A>
										  </TD>

										  <TD class="tableData">&nbsp;<%=borrowerName%>
										  </TD>
										
										  <% } 
										  
											cgpanInfos = borrowerInfo.getCgpanInfos();
											int cgpanSize=cgpanInfos.size();							
											for(cgpanIndex=0; cgpanIndex<cgpanSize; ++cgpanIndex){
												CgpanInfo cgpanInfo = (CgpanInfo)cgpanInfos.get(cgpanIndex);
												cgpan = cgpanInfo.getCgpan();
												scheme = cgpanInfo.getScheme();
												sanctionedAmount = cgpanInfo.getSanctionedAmount();		
											
										   int ind = 0;
											if(cgpan!=null) 
											{ 
											 if(cgpanIndex == 0) 
											 { 
										%>
										  <TD class="tableData"><A
								href="javascript:submitForm('showCgpanDetailsLink.do?method=showCgpanDetailsLink&cgpanDetail=<%=cgpan%>')"><%=cgpan%></A>
										  </TD>

										  <TD class="tableData"><%=scheme%>
										  </TD>

										  <TD class="tableData"><div align="right"><%=decimalFormat.format(sanctionedAmount)%></div>
										  </TD>

										<% } else { %>
											<td class="tableData"></td>
											<td class="tableData"></td>
											<td class="tableData"></td>
											<td class="tableData"></td>
									  
										  <TD class="tableData"><A
								href="javascript:submitForm('showCgpanDetailsLink.do?method=showCgpanDetailsLink&cgpanDetail=<%=cgpan%>')"><%=cgpan%></A>
										  </TD>

										  <TD class="tableData"><%=scheme%>
										  </TD>

										  <TD class="tableData"><div align="right"><%=decimalFormat.format(sanctionedAmount)%></div>
										  </TD>
										<%}%>
											<%name = "clCgpan("+cgpan+")";%>
											<html:hidden property = "<%=name%>" name="gmCloseSFGFForm" value="<%=cgpan%>"/>

										  <TD class="tableData"><div align="left">
										  <% reason = cgpanInfo.getReasonForClosure();
											  name = "reasonForCl("+cgpan+")";%>
											  
											  <html:select property="<%=name%>" name = "gmCloseSFGFForm" value = "<%=reason%>">
											  <html:option value="">Select</html:option>
											  <html:options property="clReasons" name="gmCloseSFGFForm"/>	
											  </html:select>
										  </div>
										  </TD>

										  <TD class="tableData"> <div align="center"> 
											<% name = "clFlag("+cgpan+")";%>
											 <html:checkbox property="<%=name%>" alt="Close" name="gmCloseSFGFForm" value="Y"/>
											</div>
										 </TD>
			  							  <TD class="tableData" valign="top" align="center">
										  <% remarks = cgpanInfo.getRemarks();
											  name = "remarksForCl("+cgpan+")";%> <html:textarea property = "<%=name%>"  name="gmCloseSFGFForm" 
										  value="<%=remarks%>" />
								          </TD>	
											<% }	 // end of condt cgpan!=null 
										    %>
										</TR>

									<% ++closureIndex;
										}
								   }
									%>
								 </logic:iterate>
<html:hidden property = "clIndex" name="gmCloseSFGFForm" value="<%=String.valueOf(closureIndex)%>"/>
								</TABLE>
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
								
									<A href="javascript:submitForm('closeCgpans.do?method=closeCgpans')">
									<IMG src="images/Save.gif" alt="OK" width="49" height="37" border="0"></A>
									<A href="javascript:document.gmCloseSFGFForm.reset()">
									<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>
								<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
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