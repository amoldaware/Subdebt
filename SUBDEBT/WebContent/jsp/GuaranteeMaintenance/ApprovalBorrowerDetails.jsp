<%@page import="com.cgtsi.actionform.APForm"%>
<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.application.BorrowerDetails"%>
<%@ page import="com.cgtsi.application.SSIDetails"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.Date"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>


<%
session.setAttribute("CurrentPage","showBorrowerDetailsForApproval.do?method=showBorrowerDetailsForApproval");
%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="showBorrowerDetailsForApproval.do?method=showBorrowerDetailsForApproval" >
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
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
									<TD colspan="6"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="40%" class="Heading"><bean:message key="borrowerHeader"/> for <bean:write name="gmApprovalForm" property="borrowerId"/><bean:write name="gmApprovalForm" property="linkedCGPANS"/> </TD>
											 
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
								  </table>
							  </td>
							</tr>

							<tr>
								<td align="center" valign="middle" class="HeadingBg"
								align="center"><center><bean:message key="field"/></td>
								<td align="center" valign="middle" class="HeadingBg"
								align="center"><center><bean:message key="oldBorrowerDetails"/></td>
								<td align="center" valign="middle" class="HeadingBg"
								align="center"><center><bean:message key="newBorrowerDetails"/></td>
							</tr>

							<%
								BorrowerDetails oldBorrower = new BorrowerDetails();
								BorrowerDetails newBorrower = new BorrowerDetails();
								SSIDetails oldSSI = new SSIDetails();
								SSIDetails newSSI = new SSIDetails();
								
								APForm oldApform=new APForm();
							    APForm newApform=new APForm();
							    
								int i=0;
							%>

							<logic:iterate name="gmApprovalForm" property="borrowerDetails" id="object">
							<%
							if (i==0)
							{
								oldBorrower = (BorrowerDetails) object;
								oldSSI = oldBorrower.getSsiDetails(); 
							}
							else if (i==1)
							{
								newBorrower = (BorrowerDetails) object;
								newSSI = newBorrower.getSsiDetails();
							}
							else if (i==2)
							{
								oldApform = (APForm) object;
								///oldApform = newBorrower.getApform();
							}
							else if (i==3)
							{
								newApform = (APForm) object;
								//newApform = newBorrower.getApform();
							}
							i++;
							%>
							</logic:iterate>
							<%
								SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
								DecimalFormat df = new DecimalFormat("#############.##");
								df.setDecimalSeparatorAlwaysShown(false);
                                
								String oldSSIRef = ""+oldSSI.getBorrowerRefNo();
								String oldBid  = oldSSI.getCgbid();
								String oldAssisted = oldBorrower.getAssistedByBank();
								String oldStrOsAmt="";
								System.out.println("branch name OLD "+oldApform.getBranchName());
								System.out.println("branch name NEW "+newApform.getBranchName());
								
								System.out.println("bank A/C OLD "+oldApform.getBankAcNo());
								System.out.println("bank A/C NEW "+newApform.getBankAcNo());
								
								System.out.println("udhyog adhar no OLD "+oldApform.getUdyogAdharNo());
								System.out.println("udhyog adhar no NEW "+newApform.getUdyogAdharNo());
								
								String oldBranchName="";
								String newBranchName="";
								
								if(oldApform.getBranchName()!=null)
								{
									oldBranchName=oldApform.getBranchName();
								}
								if(newApform.getBranchName()!=null)
								{
									newBranchName=newApform.getBranchName();
								}
								
								String oldBankAcNo="";
								String newBankAcNo="";
								
								if(oldApform.getBankAcNo()!=null)
								{
									oldBankAcNo=oldApform.getBankAcNo();
								}
								if(newApform.getBankAcNo()!=null)
								{
									newBankAcNo=newApform.getBankAcNo();
								}
								
								String oldUdyogAdharNo="";
								String newUdyogAdharNo="";
								
								if(oldApform.getUdyogAdharNo()!=null)
								{
									oldUdyogAdharNo=oldApform.getUdyogAdharNo();
								}
								if(newApform.getUdyogAdharNo()!=null)
								{
									newUdyogAdharNo=newApform.getUdyogAdharNo();
								}
								
								if(oldBorrower.getOsAmt()!=0)
								{
									double oldOsAmt = oldBorrower.getOsAmt();
									oldStrOsAmt = df.format(oldOsAmt);
								}
								else{
									double oldOsAmt=0;
								}								
								String oldNpa = "";
								
								if(oldBorrower.getNpa()!=null)
								{
									oldNpa = oldBorrower.getNpa();
								}
								else
								{
									oldNpa = "";
								}
								String oldCovered = oldBorrower.getPreviouslyCovered();
								String oldConst="";
								if(oldSSI.getConstitution()!=null)
								{
									oldConst = oldSSI.getConstitution();
								}
								String oldSSIType="";
								if(oldSSI.getSsiType()!=null)
								{
									oldSSIType = oldSSI.getSsiType();
								}
								String oldSSIName ="";
								if(oldSSI.getSsiName()!=null)
								{
									oldSSIName = oldSSI.getSsiName();								
								}
								String oldRegNo ="";
								if(oldSSI.getRegNo()!=null)
								{
									oldRegNo = oldSSI.getRegNo();
								}
								String oldSSIItpan ="";
								if(oldSSI.getSsiITPan()!=null)
								{
									oldSSIItpan = oldSSI.getSsiITPan();									
								}
								String oldIndNature ="";
								if(oldSSI.getIndustryNature()!=null)
								{
									oldIndNature = oldSSI.getIndustryNature();
								}
								String oldIndSector ="";
								if(oldSSI.getIndustrySector()!=null)
								{
									oldIndSector = oldSSI.getIndustrySector();
								}
								String oldActType ="";
								if(oldSSI.getActivityType()!=null)
								{
									oldActType = oldSSI.getActivityType();
								}								
								String oldNoOfEmp ="";
								if(oldSSI.getEmployeeNos()!=0)
								{
									oldNoOfEmp = ""+oldSSI.getEmployeeNos();
								}
								String oldStrTurnover ="";
								if(oldSSI.getProjectedSalesTurnover()!=0)
								{
									double oldTurnover = oldSSI.getProjectedSalesTurnover();
									oldStrTurnover = df.format(oldTurnover);
								}
								String oldStrExports ="";
								if(oldSSI.getProjectedExports()!=0)
								{
									double oldExports = oldSSI.getProjectedExports();
									oldStrExports = df.format(oldExports);
								}
								String oldAddress ="";
								if(oldSSI.getAddress()!=null)
								{
									oldAddress = oldSSI.getAddress();
								}
								String oldState ="";
								if(oldSSI.getState()!=null)
								{
									oldState = oldSSI.getState();
								}
								String oldDistrict ="";
								if(oldSSI.getDistrict()!=null)
								{
									oldDistrict = oldSSI.getDistrict();
								}
								String oldCity ="";
								if(oldSSI.getCity()!=null)
								{
									oldCity = oldSSI.getCity();
								}
								String oldPincode ="";
								if(oldSSI.getPincode()!=null)
								{
									oldPincode = oldSSI.getPincode();
								}
								String oldCpTitle ="";
								if(oldSSI.getCpTitle()!=null)
								{
									oldCpTitle = oldSSI.getCpTitle();
								}
								String oldCpFirstName ="";
								if(oldSSI.getCpFirstName()!=null)
								{
									oldCpFirstName = oldSSI.getCpFirstName();
								}
								String oldCpMiddleName ="";
								if(oldSSI.getCpMiddleName()!=null)
								{
									oldCpMiddleName = oldSSI.getCpMiddleName();
								}
								String oldCpLastName ="";
								if(oldSSI.getCpLastName()!=null)
								{
									oldCpLastName = oldSSI.getCpLastName();
								}
								String oldCpGender ="";
								if(oldSSI.getCpGender()!=null)
								{
									oldCpGender = oldSSI.getCpGender();
								}
								String oldCpItpan ="";
								if(oldSSI.getCpITPAN()!=null)
								{
									oldCpItpan = oldSSI.getCpITPAN();
								}
								Date oldCpDob = oldSSI.getCpDOB();
								String oldStrDob = "";
								if (oldCpDob != null)
								{
									oldStrDob = dateFormat.format(oldCpDob);
								}
								String oldSocialCat = oldSSI.getSocialCategory();
								if(oldSocialCat==null)
								{
									oldSocialCat="";
								}
								String oldLegalId = oldSSI.getCpLegalID();
								if(oldLegalId==null)
								{
									oldLegalId="";
								}
								String oldLegalValue = oldSSI.getCpLegalIdValue();
								if(oldLegalValue==null)
								{
									oldLegalValue="";
								}
								String oldOtherName1 = oldSSI.getFirstName();
								if(oldOtherName1==null)
								{
									oldOtherName1="";
								}
								String oldOtherItpan1 = oldSSI.getFirstItpan();
								if(oldOtherItpan1==null)
								{
									oldOtherItpan1="";
								}
								Date oldOtherDob1 = oldSSI.getFirstDOB();
								String oldStrOtherDob1 = "";
								if (oldOtherDob1 != null)
								{
									oldStrOtherDob1 = dateFormat.format(oldOtherDob1);
								}
								String oldOtherName2 = oldSSI.getSecondName();
								if(oldOtherName2==null)
								{
									oldOtherName2="";
								}
								String oldOtherItpan2 = oldSSI.getSecondItpan();
								if(oldOtherItpan2==null)
								{
									oldOtherItpan2="";
								}						
								Date oldOtherDob2 = oldSSI.getSecondDOB();
								String oldStrOtherDob2 = "";
								if (oldOtherDob2 != null)
								{
									oldStrOtherDob2 = dateFormat.format(oldOtherDob2);
								}
								String oldOtherName3 = oldSSI.getThirdName();
								if(oldOtherName3==null)
								{
									oldOtherName3="";
								}
								String oldOtherItpan3 = oldSSI.getThirdItpan();
								if(oldOtherItpan3==null)
								{
									oldOtherItpan3="";
								}
								Date oldOtherDob3 = oldSSI.getThirdDOB();
								String oldStrOtherDob3 = "";
								if (oldOtherDob3 != null)
								{
									oldStrOtherDob3 = dateFormat.format(oldOtherDob3);
								}
								String newSSIRef = "";
								String newBid  = "";
								String newAssisted = ""; 
								String newStrOsAmt = "";
								String newNpa = "";
								String newCovered = "";
								String newConst = "";
								String newSSIType = "";
								String newSSIName = "";
								String newRegNo = "";
								String newSSIItpan = "";
								String newIndNature = "";
								String newIndSector = "";
								String newActType = "";
								String newNoOfEmp = "";
								String newStrTurnover = "";
								String newStrExports = "";
								String newAddress = "";
								String newState = "";
								String newDistrict = "";
								String newCity = "";
								String newPincode = "";
								String newCpTitle = "";
								String newCpFirstName = "";
								String newCpMiddleName = "";
								String newCpLastName = "";
								String newCpGender = "";
								String newCpItpan = "";
								String newStrDob = "";
								String newSocialCat = "";
								String newLegalId = "";
								String newLegalValue = "";
								String newOtherName1 = "";
								String newOtherItpan1 = "";
								String newStrOtherDob1 = "";
								String newOtherName2 = "";
								String newOtherItpan2 = "";
								String newStrOtherDob2 = "";
								String newOtherName3 = "";
								String newOtherItpan3 = "";
								String newStrOtherDob3 = "";
								if (newBorrower != null)
								{
									newAssisted = newBorrower.getAssistedByBank();
									double newOsAmt = newBorrower.getOsAmt();
									if (newSSI != null)
									{
									newSSIRef = ""+newSSI.getBorrowerRefNo();
									
									
									newBid  = newSSI.getCgbid();
									newStrOsAmt = df.format(newOsAmt);
									if(newBorrower.getNpa()!=null)
									{
									newNpa = newBorrower.getNpa();
									}
									newCovered = newBorrower.getPreviouslyCovered();
									
									if(newSSI.getConstitution()!=null)
									{
										newConst = newSSI.getConstitution();
									}
									
									if(newSSI.getSsiType()!=null)
									{
										newSSIType = newSSI.getSsiType();
									}
									
									if(newSSI.getSsiName()!=null)
									{
										newSSIName = newSSI.getSsiName();
									}
									
									if(newSSI.getRegNo()!=null)
									{
										newRegNo = newSSI.getRegNo();
									}
									
									if(newSSI.getSsiITPan()!=null)
									{
										newSSIItpan = newSSI.getSsiITPan();
									}
									
									if(newSSI.getIndustryNature()!=null)
									{
										newIndNature = newSSI.getIndustryNature();
									}
									
									if(newSSI.getIndustrySector()!=null)
									{
										newIndSector = newSSI.getIndustrySector();
									}
									
									if(newSSI.getActivityType()!=null)
									{
										newActType = newSSI.getActivityType();
									}
								
									
										newNoOfEmp = ""+newSSI.getEmployeeNos();
									
									double newTurnover = newSSI.getProjectedSalesTurnover();
									newStrTurnover = df.format(newTurnover);
									double newExports = newSSI.getProjectedExports();
									newStrExports = df.format(newExports);
									if(newSSI.getAddress()!=null)
										
									{
										newAddress = newSSI.getAddress();
									}
									if(newSSI.getState()!=null)
									{
										newState = newSSI.getState();
									}
									if(newSSI.getDistrict()!=null)
									{
										newDistrict = newSSI.getDistrict();
									}
									if(newSSI.getCity()!=null)
									{
										newCity = newSSI.getCity();
									}
									if(newSSI.getPincode()!=null)
									{
										newPincode = newSSI.getPincode();
									}
									if(newSSI.getCpTitle()!=null)
									{
										newCpTitle = newSSI.getCpTitle();
									}
									if(newSSI.getCpFirstName()!=null)
									{
										newCpFirstName = newSSI.getCpFirstName();
									}
									if(newSSI.getCpMiddleName()!=null)
									{
										newCpMiddleName = newSSI.getCpMiddleName();
									}
									if(newSSI.getCpLastName()!=null)
									{
										newCpLastName = newSSI.getCpLastName();
									}
									if(newSSI.getCpGender()!=null)
									{
										newCpGender = newSSI.getCpGender();
									}
									if(newSSI.getCpITPAN()!=null)
									{
										newCpItpan = newSSI.getCpITPAN();
									}
									Date newCpDob = newSSI.getCpDOB();
									if (newCpDob != null)
									{
										newStrDob = dateFormat.format(newCpDob);
									}
									if(newSSI.getSocialCategory()!=null)
									{
										newSocialCat = newSSI.getSocialCategory();
									}
									if(newSSI.getCpLegalID()!=null)
									{
										newLegalId = newSSI.getCpLegalID();
									}
									if(newSSI.getCpLegalIdValue()!=null)
									{
										newLegalValue = newSSI.getCpLegalIdValue();
									}
									if(newSSI.getFirstName()!=null)
									{
										newOtherName1 = newSSI.getFirstName();
									}
									if(newSSI.getFirstItpan()!=null)
									{
										newOtherItpan1 = newSSI.getFirstItpan();
									}
									Date newOtherDob1 = newSSI.getFirstDOB();
									if (newOtherDob1 != null)
									{
										newStrOtherDob1 = dateFormat.format(newOtherDob1);
									}
									if(newSSI.getSecondName()!=null)
									{
										newOtherName2 = newSSI.getSecondName();
									}
									if(newSSI.getSecondItpan()!=null)
									{
										newOtherItpan2 = newSSI.getSecondItpan();
									}
									Date newOtherDob2 = newSSI.getSecondDOB();
									if (newOtherDob2 != null)
									{
										newStrOtherDob2 = dateFormat.format(newOtherDob2);
									}
									if(newSSI.getThirdName()!=null)
									{
										newOtherName3 = newSSI.getThirdName();
									}
									if(newSSI.getThirdItpan()!=null)
									{
										newOtherItpan3 = newSSI.getThirdItpan();
									}
									Date newOtherDob3 = newSSI.getThirdDOB();
									if (newOtherDob3 != null)
									{
										newStrOtherDob3 = dateFormat.format(newOtherDob3);
									}
									}
								}
							%>
							
							
							
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="npa"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldNpa%></center>
								</TD>
								<% if(!oldNpa.equalsIgnoreCase(newNpa)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newNpa%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newNpa%></center>
								</TD><%
									}%>
								
							</tr>
							
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="constitution"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldConst%></center>
								</TD>
								
								<% if(!oldConst.equalsIgnoreCase(newConst)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newConst%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newConst%></center>
								</TD><%
									}%>
									
							
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="unitName"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldSSIType%>&nbsp;<%=oldSSIName%></center>
								</TD>
								
									<% if(!oldSSIName.equalsIgnoreCase(newSSIName) || !oldSSIType.equalsIgnoreCase(newSSIType)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newSSIType%>&nbsp;<%=newSSIName%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newSSIType%>&nbsp;<%=newSSIName%></center>
								</TD><%
									}%>
									
								
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="ssiRegNo"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldRegNo%></center>
								</TD>
								
									<% if(!oldRegNo.equalsIgnoreCase(newRegNo)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newRegNo%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newRegNo%></center>
								</TD><%
									}%>
								
									
							
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="firmItpan"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldSSIItpan%></center>
								</TD>
								
									<% if(!oldSSIItpan.equalsIgnoreCase(newSSIItpan)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newSSIItpan%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newSSIItpan%></center>
								</TD><%
									}%>
								
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="industryNature"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldIndNature%></center>
								</TD>
								
									<% if(!oldIndNature.equalsIgnoreCase(newIndNature)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newIndNature%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newIndNature%></center>
								</TD><%
									}%>
															
								
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="industrySector"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldIndSector%></center>
								</TD>
								
								
								<% if(!oldIndSector.equalsIgnoreCase(newIndSector)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newIndSector%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newIndSector%></center>
								</TD><%
									}%>
									
									
								
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="activitytype"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldActType%></center>
								</TD>
								
								<% if(!oldActType.equalsIgnoreCase(newActType)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newActType%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newActType%></center>
								</TD><%
									}%>
									
								
							</tr>
						
							
							
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="address"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldAddress%></center>
								</TD>
								
								<% if(!oldAddress.equalsIgnoreCase(newAddress)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newAddress%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newAddress%></center>
								</TD><%
									}%>
								
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="state"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldState%></center>
								</TD>
								<% if(!oldState.equalsIgnoreCase(newState)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newState%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newState%></center>
								</TD><%
									}%>
								
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="district"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldDistrict%></center>
								</TD>
								
									<% if(!oldDistrict.equalsIgnoreCase(newDistrict)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newDistrict%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newDistrict%></center>
								</TD><%
									}%>
							
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="city"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldCity%></center>
								</TD>
								
										<% if(!oldCity.equalsIgnoreCase(newCity)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newCity%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newCity%></center>
								</TD><%
									}%>
									
								
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="pinCode"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldPincode%></center>
								</TD>
								
										<% if(!oldPincode.equalsIgnoreCase(newPincode)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newPincode%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newPincode%></center>
								</TD><%
									}%>
								
							</tr>
							
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center>Branch Name</center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldBranchName%></center>
								</TD>
								
										<% if(!oldBranchName.equalsIgnoreCase(newBranchName)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newBranchName%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newBranchName%></center>
								</TD><%
									}%>
								
							</tr>
							
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center>Bank A/C No.</center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldBankAcNo%></center>
								</TD>
								
										<% if(!oldBankAcNo.equalsIgnoreCase(newBankAcNo)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newBankAcNo%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newBankAcNo%></center>
								</TD><%
									}%>								
							</tr>
							
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center>Udhyog Adhar No.</center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldUdyogAdharNo%></center>
								</TD>
								
										<% if(!oldUdyogAdharNo.equalsIgnoreCase(newUdyogAdharNo)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newUdyogAdharNo%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newUdyogAdharNo%></center>
								</TD><%
									}%>								
							</tr>
							
							<TR align="left">
								<TD align="left" valign="top" class="ColumnBackground" colspan="3">
								<bean:message key="chiefInfo"/>
								</TD>
							</TR>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="title"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldCpTitle%></center>
								</TD>
								
									<% if(!oldCpTitle.equalsIgnoreCase(newCpTitle)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newCpTitle%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newCpTitle%></center>
								</TD><%
									}%>
							
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="firstName"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldCpFirstName%></center>
								</TD>
								
								<% if(!oldCpFirstName.equalsIgnoreCase(newCpFirstName)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newCpFirstName%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newCpFirstName%></center>
								</TD><%
									}%>
								
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="middleName"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldCpMiddleName%></center>
								</TD>
								
								<% if(!oldCpMiddleName.equalsIgnoreCase(newCpMiddleName)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newCpMiddleName%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newCpMiddleName%></center>
								</TD><%
									}%>
									
							
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="lastName"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldCpLastName%></center>
								</TD>
								
								<% if(!oldCpLastName.equalsIgnoreCase(newCpLastName)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newCpLastName%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newCpLastName%></center>
								</TD><%
									}%>
									
								
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="gender"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldCpGender%></center>
								</TD>
								
								<% if(!oldCpGender.equalsIgnoreCase(newCpGender)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newCpGender%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newCpGender%></center>
								</TD><%
									}%>
									
								
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="chiefItpan"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldCpItpan%></center>
								</TD>
								
									<% if(!oldCpItpan.equalsIgnoreCase(newCpItpan)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newCpItpan%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newCpItpan%></center>
								</TD><%
									}%>
									
								
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="dob"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldStrDob%></center>
								</TD>
								
									<% if(!oldStrDob.equalsIgnoreCase(newStrDob)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newStrDob%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newStrDob%></center>
								</TD><%
									}%>
						
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="socialCategory"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldSocialCat%></center>
								</TD>
								
								<% if(!oldSocialCat.equalsIgnoreCase(newSocialCat)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newSocialCat%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newSocialCat%></center>
								</TD><%
									}%>
						
						
							</tr>
							
							<TR align="left">
								<TD align="left" valign="top" class="ColumnBackground" colspan="3"><bean:message key="otherPromoters" />	
								</TD>
							</TR>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center>1.<bean:message key="promoterName"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldOtherName1%></center>
								</TD>
								
								<% if(!oldOtherName1.equalsIgnoreCase(newOtherName1)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newOtherName1%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newOtherName1%></center>
								</TD><%
									}%>
							
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="promoterItpan"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldOtherItpan1%></center>
								</TD>
								
								<% if(!oldOtherItpan1.equalsIgnoreCase(newOtherItpan1)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newOtherItpan1%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newOtherItpan1%></center>
								</TD><%
									}%>
									
								
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="promoterDob"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldStrOtherDob1%></center>
								</TD>
								
									<% if(!oldStrOtherDob1.equalsIgnoreCase(newStrOtherDob1)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newStrOtherDob1%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newStrOtherDob1%></center>
								</TD><%
									}%>
									
									
							
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center>2.<bean:message key="promoterName"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldOtherName2%></center>
								</TD>
								
								<% if(!oldOtherName2.equalsIgnoreCase(newOtherName2)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newOtherName2%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newOtherName2%></center>
								</TD><%
									}%>
									
								
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="promoterItpan"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldOtherItpan2%></center>
								</TD>
								
								<% if(!oldOtherItpan2.equalsIgnoreCase(newOtherItpan2)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newOtherItpan2%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newOtherItpan2%></center>
								</TD><%
									}%>
									
							
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="promoterDob"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldStrOtherDob2%></center>
								</TD>
								
								<% if(!oldStrOtherDob2.equalsIgnoreCase(newStrOtherDob2)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newStrOtherDob2%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newStrOtherDob2%></center>
								</TD><%
									}%>
									
							
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center>3.<bean:message key="promoterName"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldOtherName3%></center>
								</TD>
								
								<% if(!oldOtherName3.equalsIgnoreCase(newOtherName3)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newOtherName3%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newOtherName3%></center>
								</TD><%
									}%>
									
									
								
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="promoterItpan"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldOtherItpan3%></center>
								</TD>
								
									<% if(!oldOtherItpan3.equalsIgnoreCase(newOtherItpan3)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newOtherItpan3%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newOtherItpan3%></center>
								</TD><%
									}%>
									
							
							</tr>
							<tr>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><bean:message key="promoterDob"/></center>
								</TD>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=oldStrOtherDob3%></center>
								</TD>
								
									<% if(!oldStrOtherDob3.equalsIgnoreCase(newStrOtherDob3)) { %>
								<TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><font color="red"><%=newStrOtherDob3%></font></center>
								</TD>
								<%} else {
									%><TD align="center" valign="middle" class="TableData"
								  align="center">
								  <center><%=newStrOtherDob3%></center>
								</TD><%
									}%>
									
									
								
							</tr>
           
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
													<A href="javascript:window.history.back()">
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
			</body>
		</TABLE>
