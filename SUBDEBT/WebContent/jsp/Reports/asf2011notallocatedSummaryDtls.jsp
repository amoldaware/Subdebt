<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.util.Date"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","asf2011notallocatedSummaryDtls.do?method=asf2011notallocatedSummaryDtls");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%@page import ="java.text.DecimalFormat"%>
<%@page import ="java.util.StringTokenizer"%>
<%DecimalFormat decimalFormat = new DecimalFormat("#########0.0##");%>
<%@page import = "org.apache.struts.action.DynaActionForm"%>
<% DynaActionForm dynaForm = (DynaActionForm)session.getAttribute("rsForm");
   String name = (String)dynaForm.get("state");
   int proposals=((java.lang.Integer)dynaForm.get("proposals")).intValue();
   double asfdanamtDue =((java.lang.Double)dynaForm.get("asfdanamtDue")).doubleValue();
  // int totalDue = (int)asfdanamtDue ;
  // System.out.println("totalDue :"+totalDue);
   String mliAddress = (String)dynaForm.get("ssiDetails");
   StringTokenizer tokenizer = null;
   tokenizer = new StringTokenizer(mliAddress,",");   
   //System.out.println("mliAddress:"+mliAddress);
   
   Date dt  = new Date();
   SimpleDateFormat dtFormat=new SimpleDateFormat("MMMM dd,yyyy");
   String sysdt = (String)dtFormat.format(dt);
   // System.out.println(dtFormat.format(dt));
   %>
<form name="addded" action="workshopReport.do?method=workshopReport">

<TABLE width="750" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="asf2011notallocatedSummaryDtls.do?method=asf2011notallocatedSummaryDtls" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><%--<IMG src="images/ReportsHeading.gif" width="121" height="25"> --%></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
       <TABLE>
           <%--  <tr>
                          <td colspan="3" class="Heading1" align="center"><u>Credit Guarantee Fund Trust for Micro and Small Enterprises&nbsp;</u></td>
        </tr> --%>
         <tr>
                       <td>&nbsp;<div align="left"><IMG src="images/CgfsiLogoNew.gif" ></div></td> </tr>
                       <tr>
                       <td>&nbsp;</td> </tr>
       <tr>
       
       <tr>
             <td align="left" width="80%">&nbsp;CGTMSE / ASF 2011 /                                 
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;<%=sysdt%></td><td>&nbsp;</td>
             <td>&nbsp;</td></tr>
      </TABLE>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<tr>
                       <td>&nbsp;</td> </tr>
           <tr>
                       <td>&nbsp;</td> </tr>            
          <tr>
                       <td>The Chief Manager</td> </tr>
                       <tr> <td>Advances</td></TR>
                       <tr><td><% 	
                       String token = null;
                       String lasttoken = null;
                       while(tokenizer.hasMoreTokens())
								       	{
             				        token = (String)tokenizer.nextToken();
                            lasttoken = (String)tokenizer.nextToken();
                       %>
                       <%=token%><br>
                       <%
                        }
                       %>
                    <i> <b> <%=lasttoken%></b></i>
                       </td></tr>
                       <tr><td>&nbsp;</td></tr>
                       <tr><td>&nbsp;</td></tr>
                       <tr><td>&nbsp;</td></tr>
                       <tr><td>&nbsp;</td></tr>
          
     
       <tr> <td>Dear Sir,&nbsp;</td></TR>
       <tr> <td colspan="3" class="Heading1" align="center"><u>CGS - Payment of Annual Service Fee-- Accounts closed in FY2011&nbsp;</u></td>
                      </tr> 
        <tr> <td><p>&nbsp;</p></td></tr>    
       <%-- <tr> <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="text1"/><bean:message key="text2"/>&nbsp;<bean:message key="text3"/>&nbsp;<bean:message key="text4"/><font color="red"><b><%=asfdanamtDue%></b></font>&nbsp;<bean:message key="text5"/>&nbsp;<bean:message key="text6"/><bean:message key="text7"/><font color="green"><b>Annexure</b></font>&nbsp;<bean:message key="text8"/></td></tr>  
--%>     <tr> <td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Please refer to your online closure request submitted for <font color="red">&nbsp;<b><%=proposals%></b>&nbsp;</font> account/s. In this connection,
we advise that account/s have been closed as per details provided by you. As per extant guidelines, Annual Service Fee has to be paid by MLI till date of closure.
Accordingly, demand advice for Annual Service Fee 2011 (till date of closure) of &nbsp;Rs.<font color="red"><b><%=Math.round(asfdanamtDue)%>/- </b></font>&nbsp;has been raised.
You are requested to make the payment of ASF in respect of account/s given in the&nbsp;<font color="green"><b>Annexure</b></font>&nbsp;at the earliest.</td></tr>   

<tr> <td>&nbsp;</td></tr> 
       <tr> <td><p>
        &nbsp;
        </p></td></tr> 
        <tr> <td><p>
        &nbsp;
        </p></td></tr> 
        <tr> <td><p>
        &nbsp;
        </p></td></tr> 
        <tr> <td><p>
        &nbsp;
        </p></td></tr> 
        <tr> <td><p>
        &nbsp;
        </p></td></tr> 
       
       <tr> <td><p>
        &nbsp;
        </p></td></tr> 
        <tr> <td><div align="right">
        &nbsp;Yours faithfully,&nbsp;</div>
        </td></tr> 
        <tr> <td><p>
        &nbsp;
        </p></td></tr> 
        <tr> <td><p>
        Encl. as above
        </p></td></tr> 
        <tr> <td><p>
        &nbsp;
        </p></td></tr>
        <tr> <td><p>
        &nbsp;
        </p></td></tr>
        <tr> <td><div align="right">
        &nbsp;&nbsp;</div>
        </td></tr>
        <tr> <td><div align="right">
        &nbsp;For CGTMSE&nbsp;</div>
        </td></tr> 
        <tr> <td><p>
        &nbsp;
        </p></td></tr>
        <tr>
                       <td>&nbsp;<div align="center"><IMG src="images/footer1.gif" ></div></td> </tr>
          <TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="10"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<%--	<tr>
                          <td colspan="3" class="Heading1" align="center"><u>Credit Guarantee Fund Trust for Micro and Small Enterprises&nbsp;</u></td>
                      </tr> --%>
                       <tr style="page-break-before: always"><td>&nbsp;</td></tr>
                      <tr> <td colspan="3"  align="right"><font color="blue">&nbsp;Annexure&nbsp;</font></td></tr>
                      
                      <TR>
												<TD width="65%" class="Heading">ASF 2011 Unit wise not paid cases Report Details for <%=name%></TD>
												<td class="Heading" width="40%">&nbsp;<bean:message key="from"/> <bean:write  name="rsForm" property="dateOfTheDocument26"/>&nbsp;<bean:message key="to"/> <bean:write  name="rsForm" property="dateOfTheDocument27"/></td>
                        <TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
        			</TABLE>
									</TD>

									<TR>
                  <td width="1%" align="left" valign="top" class="ColumnBackground"><bean:message key="sNo"/></td>
									
                  <TD  width="25%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="mliName"/></TD> 
                  <TD width="25%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="zoneName"/></TD> 
                  <TD width="25%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="CBbranchName"/></TD>
                  <TD width="25%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="mliID"/></TD>
                   <TD width="25%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="borrowerUnitName"/></TD> 
                  <TD width="25%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="daNumber"/></TD> 
                  <TD width="25%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="queryBuilderFields.cgpan"/></TD> 
                  <TD width="25%" align="left" valign="top" class="ColumnBackground">
										Dan Amt&nbsp;</TD>  
                   <TD width="25%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="status"/></TD>   
                    
                    
									</TR>	
	                <% 
                      
                      double amount = 0.0;
                      double totalamount = 0.0;
                  %>
									<tr>
								<logic:iterate name="rsForm" id="object" property="statesWiseReport" indexId="index">
               	<%
									     com.cgtsi.reports.GeneralReport danReport = (com.cgtsi.reports.GeneralReport)object;
							  %>
										<TR>
                      <td align="left" valign="top" class="ColumnBackground1"><%=Integer.parseInt(index+"")+1%></td>
								       <TD  width="25%" align="left" valign="top" class="ColumnBackground1">						
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
											<%String zoneName= danReport.getZoneName();
											if((zoneName == null)||(zoneName.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=zoneName%>
											<%
											}
											%>
               			</TD>
                    
                    	<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String branchName= danReport.getName();
											if((branchName == null)||(branchName.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=branchName%>
											<%
											}
											%>
               			</TD>
                    
                    <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String mliId= danReport.getMemberId();  
											if((mliId == null)||(mliId.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
                        <%=mliId%>
											<%
											}
											%>
               			</TD>
                    
                		<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String unitName= danReport.getSsiName();  
											if((unitName == null)||(unitName.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
                        <%=unitName%>
											<%
											}
											%>
               			</TD>	
                    <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String danId= danReport.getDdNum();  
											if((danId == null)||(danId.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
                        <%=danId%>
											<%
											}
											%>
               			</TD>	
                    
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String cgpan= danReport.getCgpan();  
											if((cgpan == null)||(cgpan.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
                        <%=cgpan%>
											<%
											}
											%>
               			</TD>	
                    
                    <TD width="10%" align="left" valign="top" class="ColumnBackground1"><div align="right">&nbsp;
                    <% 
                      amount = danReport.getAmount();
                      totalamount = totalamount+amount;
                    %><%=decimalFormat.format(amount)%></div></TD>
                    <TD width="10%" align="left" valign="top" class="ColumnBackground1">&nbsp;
                    <%=danReport.getStatus()%></TD> 
                    
											</TR>
											</logic:iterate>
        			<tr>
									<TD align="left"   valign="top" colspan="2" class="ColumnBackground">
									Total
									</TD>
                  <TD align="left" valign="top" colspan="2" class="ColumnBackground">									
									</TD>
                   <TD align="left" valign="top" colspan="2" class="ColumnBackground">									
									</TD>
                   <TD align="left" valign="top" colspan="2" class="ColumnBackground">									
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=totalamount%>
									</div>
									</TD>
                  <TD align="left" valign="top" colspan="2" class="ColumnBackground">									
									</TD>
                  </tr>
									</TABLE>
						</TD>
					</TR>
					<TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR>
						<TD align="center" valign="baseline">
							<DIV align="center">
								
								<A href="javascript:history.back()">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>

									<A href="javascript:printpage()">
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
