<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.lang.Double"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
String flag = (String) session.getAttribute("flag");
if (flag.equals("I"))
{
	session.setAttribute("CurrentPage","showInsertCorpusMaster.do?method=showInsertCorpusMaster");
}
else if (flag.equals("U"))
{
	session.setAttribute("CurrentPage","showUpdateCorpusMaster.do?method=showCorpusDetail");
}
%>

<html:form action="updateCorpusMaster.do?method=updateCorpusMaster" method="POST" enctype="multipart/form-data" focus="corpusContributor">


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
                  <td colspan="2" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
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
                  <td width="200" class="ColumnBackground" height="25"> &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="corpusContributor" />
				  </td>
                  <td class="tableData" width="474" height="25"> <div align="left"> 
                      <html:text  property="corpusContributor" size="20" alt="Corpus Contributor" name="ifForm" maxlength="50"/>
                    </div></td>	
                </tr>
                <tr>
                  <td width="200" class="ColumnBackground" height="25">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="corpusAmount" />
				  </td>
				  <%
                       org.apache.struts.validator.DynaValidatorActionForm form = (org.apache.struts.validator.DynaValidatorActionForm)session.getAttribute("ifForm");
					   double val = 0;
					   if (form.get("corpusAmount")!=null)
					   {
							val = ((Double)form.get("corpusAmount")).doubleValue();
					   }
						DecimalFormat df= new DecimalFormat("######################.##");
						df.setDecimalSeparatorAlwaysShown(false);
					   String strVal = df.format(val);
				  %>
                  <td class="tableData" width="474" height="25"> <div align="left"> 
                      <html:text  property="corpusAmount" size="20" alt="Corpus Amount" name="ifForm" onkeypress="return numbersOnly(this, event, 13)" onkeyup="isValidNumber(this)" maxlength="16" value="<%=strVal%>"/> &nbsp;&nbsp; <bean:message key="inRs" />
                    </div></td>
                </tr>
                <tr>
                  <td width="200" class="ColumnBackground" height="25">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="corpusDate" />
				  </td>
                  <td class="tableData" width="474" height="25" align="center"> <div align="left"> 
<!--                  <%
                       java.util.Date corpusDt = (java.util.Date)form.get("corpusDate");
                       SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                       String strDate = dateFormat.format(corpusDt);
                  %>-->
                      <html:text  property="corpusDate" size="20" maxlength="10" name="ifForm"/><img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.corpusDate')" align="center">
                    </div></td>
                </tr>
				<html:hidden property="corpusId" name="ifForm"/>
              </table>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center"><html:link href="javascript:submitForm('updateCorpusMaster.do?method=updateCorpusMaster')"> <img src="images/Save.gif" alt="Ok" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link>
		  <%
		  if (flag.equals("U"))
		  {
		  %>
		            <A href="javascript:submitForm('showUpdateCorpusMaster.do?method=showCorpusList')">
						<IMG src="images/Back.gif" alt="Save" width="49" height="37" border="0">
						</A>
		<%
		  }
			  %>
		  </div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>

