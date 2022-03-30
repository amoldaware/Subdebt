<%@ page language="java"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.cgtsi.actionform.InvestmentForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showOutflowForecastDetails.do?method=showInflowForecastDetails");%>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<html:errors />
	<html:form action="setInflowForecastDetails.do?method=setInflowForecastDetails" method="POST" enctype="multipart/form-data" name="investmentForm" 
	type="com.cgtsi.actionform.investmentForm">

    <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td width="323" background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
      <td align="right" background="images/TableBackground1.gif"> </td>
      <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
    <tr> 
      <td background="images/TableVerticalLeftBG.gif">&nbsp;</td>
      <td colspan="2"><table width="100%" border="0" cellspacing="1" cellpadding="0">
          <tr> 
            <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="35%" class="Heading">&nbsp;<bean:message key="outflowDetails"/></td>
                  <td colspan="3"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                </tr>
                <tr> 
                  <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                </tr>
              </table></td>
          </tr>
          <tr> 
            <td><table border="0" cellspacing="1" cellpadding="0" width="100%">
				
				<%
					int noOfHeads=0;
					int noOfSubHeads=0;
					InvestmentForm ifForm=(InvestmentForm) session.getAttribute("investmentForm");
				%>
				
				<%
					HashMap heads=new HashMap(ifForm.getHeadsToRender());
					HashMap subHeads=new HashMap();
					String headTitle="";
					String subHeadTitle="";

					Set headsSet=heads.keySet();
					Iterator headsIterator=headsSet.iterator();
				%>
				
				<%
					int i = 0;
					while (headsIterator.hasNext())
					{
				%>
				
				<%
					headTitle=headsIterator.next().toString();
					if (heads.get(headTitle) == null)
					{
				%>
						
						

							<tr> 
							  <td class="ColumnBackground">
								<DIV align="left">
								&nbsp;<%= headTitle%>
								</DIV>
							  </td>
							 <TD align="left" valign="top" class="TableData">
							<html:text name="investmentForm" property="head(<%=headTitle%>)" size="20" /> &nbsp;<bean:message key="inRs" />
							</TD>
			 
							</tr>
							<%
							}
							else
							{
								//out.println("jsp getting sub heads");
								subHeads=(HashMap) heads.get(headTitle);
								Set subHeadsSet=subHeads.keySet();
								Iterator subHeadsIterator=subHeadsSet.iterator();
						%>
							<TR align="left" valign="top">
								<TD valign="top" class="ColumnBackground" colspan="2"> 
									<DIV align="left">
										&nbsp;<%=headTitle%>
									</DIV>
								</TD>
							</TR>

							<%

							while (subHeadsIterator.hasNext())
							{	i++;
								subHeadTitle=subHeadsIterator.next().toString();
								//out.println("jsp "+subHeadTitle);
							%>
							
							<TR align="left" valign="top">
								<TD  class="ColumnBackground"> 
									<DIV align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;<%= subHeadTitle%>
									</DIV>
								</TD>
								<% String subHeadsName = "subHead("+headTitle+"_"+subHeadTitle+")"; %>

								<TD align="left" valign="top" class="TableData">
							<html:text name="investmentForm" property="<%=subHeadsName%>" size="20" /> &nbsp;<bean:message key="inRs" />
								</TD>
							</TR>
						<%
								}
							}
					}
				%>
                <tr>
                  <td class="ColumnBackground" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <bean:message key="startDate"/></td>
                  <td class="tableData" align="center">
					<html:text  property="startDate"/><img src="images/CalendarIcon.gif" onclick="showCalendar('investmentForm.startDate')" align="center">
				  </td>
                </tr>
                <tr>
                  <td class="ColumnBackground" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <bean:message key="endDate"/></td>
                  <td class="tableData" align="center">
					<html:text  property="endDate"/><img src="images/CalendarIcon.gif" onclick="showCalendar('investmentForm.endDate')" align="center">
				  </td>
                </tr>
              </table></td>
          </tr>
          <tr align="center" valign="baseline"> 
            <td colspan="2"> <div align="center" >
                <A href="javascript:submitForm('setInflowForecastDetails.do?method=setInflowForecastDetails')"><img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
			<html:link href="javascript:document.investmentForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link>
				<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
				</div>
			</td>
          </tr>
        </table></td>
      <td background="images/TableVerticalRightBG.gif">&nbsp;</td>
    </tr>
    <tr> 
      <td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
      <td colspan="2" background="images/TableBackground2.gif"><div align="center"></div></td>
      <td align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
    </tr>
  </table>
</html:form>
