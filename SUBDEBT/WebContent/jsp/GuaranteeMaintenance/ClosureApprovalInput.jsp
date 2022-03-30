<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cgtsi.receiptspayments.DANSummary"%>
<%@ page import="com.cgtsi.actionform.GMActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="java.text.DecimalFormat"%>


<%
	session.setAttribute("CurrentPage","showClosureApproval.do?method=showClosureApproval");
  String tempname="";
  String tempname1="";
%>
<% 

String sdanDate;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");

%>

<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form  name = "gmPeriodicInfoForm" type="com.cgtsi.actionform.GMActionForm" action="showClosureApproval.do?method=showClosureApproval" method="POST" >
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="1" cellspacing="1">
				<TR>
					<TD colspan="8"> 
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="20%" class="Heading">Approve Request for Closure by MLI </TD>
							<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
						</TR>
						<TR>
							<TD colspan="8" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
						</TR>
						</TABLE>
					</TD>
				</TR>
				 <tr>
         <td colspan="8"><font color="Green" font="2"> List of Closure Cases where closure date between 01/04/2009 to 31/03/2010 </font></td>
       </tr>
				<TR>
					<TD align="left" valign="top" class="ColumnBackground" width="98">
						<bean:message key="sNo" />
					</TD>
           <TD align="left" valign="top" class="ColumnBackground" width="98">
							<bean:message key="memberId" />
					</TD> 
			
          <TD align="left" valign="top" class="ColumnBackground" width="98">
						<bean:message key="cgpan" />
					</TD>
           <TD align="left" valign="top" class="ColumnBackground" width="98">
                        <bean:message key="sFee"/>
           </TD>
          <TD align="left" valign="top" class="ColumnBackground" width="83">
					Closure Date
					</TD>
           <TD align="left" valign="top" class="ColumnBackground" width="114">
                       Estimated <bean:message key="sFee"/>
           </TD>
          <TD align="left" valign="top" class="ColumnBackground" width="98">
          <bean:message key="ReasonsOfClosure" />          
          </TD>
			    <TD align="left" valign="top" class="ColumnBackground" width="116">
          Decision
          </TD>
				</TR>	
       
        <TR>
				<%int i = 0;

				DecimalFormat df= new DecimalFormat("######################.##");
				df.setDecimalSeparatorAlwaysShown(false);

				int shortDanIndex = 0;
		    String cgpan = "";
        String memberId = "";
        String reason = "";
			  double sFee = 0.0;
        double sFeeNew = 0.0;
				%>
        <html:hidden property="cgpan" name="gmPeriodicInfoForm"/>
				<logic:iterate id="object" name="gmPeriodicInfoForm" property="danSummaries" indexId="index">
				<%
				com.cgtsi.receiptspayments.DANSummary danSummary =(com.cgtsi.receiptspayments.DANSummary)object;
		//		danNo = danSummary.getDanId();
        cgpan = danSummary.getCgpan();
       // System.out.println("CGPAN test:"+cgpan);
        memberId= danSummary.getMemberId();
        reason = danSummary.getReason();
        sFee= danSummary.getAmountDue();
        sFeeNew = danSummary.getAmountBeingPaid();
        %>
        <TR>
					 <td align="left" valign="top" class="tableData" width="98"><div align="center">&nbsp;<%= Integer.parseInt(index+"")+1%></div></td>
          <TD align="left" valign="top" class="tableData" width="98">
          <%=memberId%>
          </TD>
			
          <TD align="left" valign="top" class="tableData" width="98">&nbsp;<%tempname="clearCgpan("+cgpan+")";%>
						<%=cgpan%>
            
					</TD>
          <TD align="left" valign="top" class="tableData" width="98">&nbsp;<%tempname="clearCgpan("+cgpan+")";%>
						<%=sFee%>
            
					</TD>
          <TD align="left" valign="top" class="tableData" width="83">
							<%   java.util.Date utilDate3=danSummary.getClosureDate();
													String formatedDate3 = null;
													if(utilDate3 != null)
													{
														 formatedDate3=dateFormat.format(utilDate3);
													}
													else
													{
														 formatedDate3 = "";
													}
											%>
											<%=formatedDate3%>
					</TD>
          <TD align="left" valign="top" class="tableData" width="114">&nbsp;
						<%=sFeeNew%>
            
					</TD>
          <TD align="left" valign="top" class="tableData" width="98">&nbsp;
						<%=reason%>
            
					</TD>
          <TD align="left" valign="top" class="tableData" width="116">
          <%tempname="clearCgpan("+cgpan+")";%>
         <html:select property="<%=tempname%>" name="gmPeriodicInfoForm">
														<html:option value="">Select</html:option>
                            <html:option value="AP">Accept</html:option>		
                            <html:option value="RE">Reject</html:option>
								
													</html:select>

          
          </TR>
        <%
        ++i;
        %>
        </logic:iterate>
        </TR>
        <tr>
         <td colspan="8"><font color="Green" font="2"> List of Closure Cases where closure date after 01/04/2010</font></td>
       </tr>
        <TR>
        <%int j = 0;

				DecimalFormat df1= new DecimalFormat("######################.##");
				df1.setDecimalSeparatorAlwaysShown(false);

				int shortDanIndex1 = 0;
		    String cgpan1 = "";
        String memberId1 = "";
        String reason1 = "";
			  double sFee1 = 0.0;
        double sFeeNew1 = 0.0;
				%>
        <html:hidden property="cgpan" name="gmPeriodicInfoForm"/>
				<logic:iterate id="object" name="gmPeriodicInfoForm" property="closureDetailsReq" indexId="index">
				<%
				com.cgtsi.receiptspayments.DANSummary danSummaryMod =(com.cgtsi.receiptspayments.DANSummary)object;
		//		danNo = danSummary.getDanId();
        cgpan1 = danSummaryMod.getCgpan();
       // System.out.println("CGPAN test:"+cgpan);
        memberId1= danSummaryMod.getMemberId();
        reason1 = danSummaryMod.getReason();
        sFee1= danSummaryMod.getAmountDue();
        sFeeNew1 = danSummaryMod.getAmountBeingPaid();
        %>
        <TR>
					 <td align="left" valign="top" class="tableData" width="98"><div align="center">&nbsp;<%= Integer.parseInt(index+"")+1%></div></td>
          <TD align="left" valign="top" class="tableData" width="98">
          <%=memberId1%>
          </TD>
			
          <TD align="left" valign="top" class="tableData" width="98">&nbsp;<%tempname1="closureCgpan("+cgpan+")";%>
						<%=cgpan1%>
            
					</TD>
          <TD align="left" valign="top" class="tableData" width="98">&nbsp;
						<%=sFee1%>
            
					</TD>
          <TD align="left" valign="top" class="tableData" width="83">
							<%   java.util.Date utilDate4=danSummaryMod.getClosureDate();
													String formatedDate4 = null;
													if(utilDate4 != null)
													{
														 formatedDate4=dateFormat.format(utilDate4);
													}
													else
													{
														 formatedDate4 = "";
													}
											%>
											<%=formatedDate4%>
					</TD>
          <TD align="left" valign="top" class="tableData" width="114">&nbsp;
						<%=sFeeNew1%>
            
					</TD>
          <TD align="left" valign="top" class="tableData" width="98">&nbsp;
						<%=reason1%>
            
					</TD>
          <TD align="left" valign="top" class="tableData" width="116">
          <%tempname1="closureCgpan("+cgpan1+")";%>
         <html:select property="<%=tempname1%>" name="gmPeriodicInfoForm">
														<html:option value="">Select</html:option>
                            <html:option value="AP">Accept</html:option>		
                            <html:option value="RE">Reject</html:option>
								
		    	</html:select>

          
          </TR>
        <%
        ++j;
        %>
        </logic:iterate>
				</TR>
				<TR>
					<TD align="center" valign="baseline" colspan="10">
					<DIV align="center">
				 	<A href="javascript:submitForm('afterClosureApproval.do?method=afterClosureApproval')">
							<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A> 
        		<A href="javascript:document.gmPeriodicInfoForm.reset()">
							<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>						
								<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
								<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
					</DIV>
					</TD>
				</TR>
			</TABLE>
		</TD>

		<TD width="20" background="images/TableVerticalRightBG.gif">&nbsp;</TD>
		
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
