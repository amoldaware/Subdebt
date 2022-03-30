<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>
<%  session.setAttribute("CurrentPage","dailydchpaymentReport.do?method=dailydchpaymentReport");%>
<% SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%@ page import="java.text.DecimalFormat"%>
<% DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
  <html:errors/>
  <html:form action="dailydchpaymentReport.do?method=dailydchpaymentReport" method="POST" enctype="multipart/form-data">
    <TR>
      <TD width="20" align="right" valign="bottom">
        <IMG src="images/TableLeftTop.gif" width="20" height="31"/>
      </TD>
      <TD background="images/TableBackground1.gif">
        <IMG src="images/ReportsHeading.gif" width="121" height="25"/>
      </TD>
      <TD width="20" align="left" valign="bottom">
        <IMG src="images/TableRightTop.gif" width="23" height="31"/>
      </TD>
    </TR>
    <TR>
      <TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
      <TD>
        <TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
          <TR>
            <TD width="88%">
              <TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
                <TR>
                  <TD colspan="3">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td colspan="3" class="Heading1" align="center">
                          <u>
                            <bean:message key="reportHeader"/>
                          </u>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="3">&nbsp;</td>
                      </tr>
                      <TR>
                        <TD class="Heading">DC (Handicrafts)&nbsp;<bean:message key="paymentReportsHeader"/></TD>
                        <td class="Heading" width="40%">&nbsp;<bean:message key="from"/><bean:write name="rsForm" property="dateOfTheDocument16"/>&nbsp;<bean:message key="to"/><bean:write name="rsForm" property="dateOfTheDocument17"/></td>
                        <TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"/></TD>
                      </TR>
                      <TR>
                        <TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"/></TD>
                      </TR>
                    </TABLE>
                  </TD>
                </TR>
                <TR>
                  <td width="3%" align="left" valign="top" class="ColumnBackground">
                    <bean:message key="sNo"/>
                  </td>
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
                    <bean:message key="realisationDate"/>
                  </TD>
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
                    <bean:message key="receivedAmount"/>
                  </TD>
                </TR>
                <% 
										double amount = 0;
										double totalAmount = 0;
									%>
                <tr>
                  <logic:iterate name="rsForm" property="dailydchpayment" id="object" indexId="index">
                    <% 
									      com.cgtsi.reports.PaymentReport pReport =  (com.cgtsi.reports.PaymentReport)object;
									%>
                    <TR>
                      <td align="left" valign="top" class="ColumnBackground1">
                        <%= Integer.parseInt(index+"")+1%>
                      </td>
                      <TD width="20%" align="left" valign="top" class="ColumnBackground1">
                        <%    java.util.Date utilDate2=pReport.getRealisedDate();
                      		String formatedDate2 = null;
													if(utilDate2 != null)
													{
														 formatedDate2=dateFormat.format(utilDate2);
													}
													else
													{
														 formatedDate2 = "";
													}
                          String memberId = pReport.getMemberId();
                          String url = "dailyDCHpaymentReportDetails.do?method=dailyDCHpaymentReportDetails&date="+formatedDate2+"&memId="+memberId;
									      	//	System.out.println("url:"+url);
											%>
                        <html:link href="<%=url%>">
                          <%= formatedDate2%>
                        </html:link>
                      </TD>
                      <TD align="center" width="20%" valign="top" class="ColumnBackground1">
                        <%  amount = pReport.getRealisedAmount();
                       totalAmount = totalAmount + amount; %>
                        <%= decimalFormat.format(amount)%>
                      </TD>
                    </TR>
                  </logic:iterate>
                </tr>
                <TR>
            <TD align="center" valign="top" class="ColumnBackground1"></TD>    
            <TD align="center" valign="top" class="ColumnBackground1"><b>TOTAL</b></TD>
            <TD align="left" valign="top" class="ColumnBackground1">
            <b> <%= decimalFormat.format(totalAmount)%></b>
            </TD>
          </TR>
              </TABLE>
            </TR>
          
         
          <TR>
            <TD align="center" valign="baseline" width="88%">
              <DIV align="center">
                <A href="javascript:submitForm('dailydchpaymentReportInput.do?method=dailydchpaymentReportInput')">
                  <IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"/>
                </A>
                <A href="javascript:printpage()">
                  <IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"/>
                </A>
              </DIV>
            </TD>
          </TR>
        </TABLE>
      </TD>
      <TD width="20" background="images/TableVerticalRightBG.gif">&nbsp;</TD>
    </TR>
    <TR>
      <TD width="20" align="right" valign="top">
        <IMG src="images/TableLeftBottom1.gif" width="20" height="15"/>
      </TD>
      <TD background="images/TableBackground2.gif">&nbsp;</TD>
      <TD width="20" align="left" valign="top">
        <IMG src="images/TableRightBottom1.gif" width="23" height="15"/>
      </TD>
    </TR>
  </html:form>
</TABLE>
