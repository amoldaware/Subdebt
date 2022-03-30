<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="com.cgtsi.investmentfund.CorpusDetail" %>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Map.Entry"%>
<% session.setAttribute("CurrentPage","showUpdateCorpusMaster.do?method=showCorpusList");%>

<html:form action="showUpdateCorpusMaster.do?method=showCorpusList" method="POST" enctype="multipart/form-data">


  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <html:errors />
    <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td width="323" background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
      <td align="right" background="images/TableBackground1.gif"> </td>
      <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
  <tr> 
    <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0" height="162">
	<tr>
	  <TD>			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpCorpusMaster.do?method=helpCorpusMaster')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
        <tr>
            <td height="162"> <table border="0" cellspacing="1" cellpadding="0" height="108">
                <tr> 
                  <td colspan="4" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="25%" class="Heading">
						<bean:message key="CorpusDetails" /></td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr>
                  <td width="360" class="ColumnBackground" height="25"> <div align="center">
				  <bean:message key="corpusId" /></div>
				  </td>
				  <td width="360" class="ColumnBackground" height="25"> <div align="center">
				  <bean:message key="corpusContributor" /></div>
				  </td>
                  <td width="360" class="ColumnBackground" height="25"> <div align="center">
				  <bean:message key="corpusAmount" /></div>
				  </td>
                  <td width="360" class="ColumnBackground" height="25"> <div align="center">
				  <bean:message key="corpusDate" /></div>
				  </td>
                </tr>
				<%
					DecimalFormat df= new DecimalFormat("######################.##");
					df.setDecimalSeparatorAlwaysShown(false);

					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					double total = 0;
				%>
				<logic:iterate name="ifForm" property="corpusList" id="object">
				<%
					com.cgtsi.investmentfund.CorpusDetail corpus = (com.cgtsi.investmentfund.CorpusDetail) object;

					double amount = corpus.getCorpusAmount();
					total+=amount;
					String strAmount = df.format(amount);

					Date date = corpus.getCorpusDate();
					String strDate = dateFormat.format(date);
				%>
					<tr>
					  <td width="360" class="tableData" height="25">
					  <div align="center">
				<a href="javascript:submitForm('showUpdateCorpusMaster.do?method=showCorpusDetail&id=<%=corpus.getCorpusId()%>')">
					 <%=corpus.getCorpusId()%></a>
					  </div>
					  </td>
					  <td width="360" class="tableData" height="25">
					  <div align="center">
					  <%=corpus.getCorpusContributor()%>
					  </div>
					  </td>
					  <td width="360" class="tableData" height="25">
					  <div align="center">
					  <%=strAmount%>
					  </div>
					  </td>
					  <td width="360" class="tableData" height="25">
					  <div align="center">
					  <%=strDate%>
					  </div>
					  </td>
					</tr>
				</logic:iterate>
                <tr>
                  <td width="360" class="ColumnBackground" height="25" colspan="2"> <div align="center">
				  <bean:message key="total" /></div>
				  </td>
                  <td width="360" class="ColumnBackground" height="25"> <div align="center">
				  <%=df.format(total)%></div>
				  </td>
                  <td width="360" class="ColumnBackground" height="25">
				  </td>
                </tr>
              </table>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div align="center">
          <A href="javascript:history.back()">
						<IMG src="images/Back.gif" alt="Save" width="49" height="37" border="0">
						</A>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>

