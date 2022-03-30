<%@ page language="java"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","getSettlementAdviceFilterDtl.do?method=getSettlementAdviceFilterDtl");%>
<%
String memberId = "";
String cgclan = "";
int settlementAmnt = 0;
java.util.Date settlementDt = null;
String settlementDtStr = "";
int i =1;
int j =1;
String settlmntAdvceFirstInstllmntKey = "";
String settlmntAdvceSecndInstllmntKey = "";
SimpleDateFormat sdf = null;
%>

<html:errors/>
<html:form action="saveGenerateCSAOption.do?method=saveGenerateCSAOption" method="POST" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="FontStyle">&nbsp;</td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></td>
    <td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
  </tr>
  <tr>
    <td background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td><table width="100%" border="0" cellspacing="1" cellpadding="0">
          <tr> 
            <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="35%" class="Heading">&nbsp;<bean:message key="generateclmsettllmntadvice"/></td>
                  <td colspan="3"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                </tr>
                <tr> 
                  <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                </tr>
              </table></td>
          </tr>
          <tr> 
            <td colspan="4"><table width="100%" border="0" cellspacing="1" cellpadding="0">
                 <logic:notEqual property="firstCounter" value="0" name="cpTcDetailsForm">
                 <tr> 
		         <td class="SubHeading" colspan="4"> &nbsp;<bean:message key="adviceForFirstSettlement"/></td>
                 </tr>
                <tr class="HeadingBg"> 
                  <td> <div align="center">&nbsp;<bean:message key="SerialNumber"/></div></td>
                  <td><div align="center">&nbsp;<bean:message key="cpmemId"/></div></td>
                  <td> <div align="center"><strong>&nbsp;<bean:message key="cgclannumber"/></strong></div></td>
                  <td><div align="center"> &nbsp;<bean:message key="tieronesettlemnt"/></div></td>
	          <td> <div align="center"><strong>&nbsp;<bean:message key="cpsettlemntdt"/></strong></div></td>
                  <td> <div align="center">&nbsp;<bean:message key="generatecsa"/></div></td>
                </tr>
                <logic:iterate property="settlmntAdviceDtlsFirstSttlmnt" name="cpTcDetailsForm" id="object">
                <%
                java.util.HashMap dtl = (java.util.HashMap)object;
                memberId = (String)dtl.get(ClaimConstants.CLM_MEMBER_ID);
                cgclan = (String)dtl.get(ClaimConstants.CLM_CGCLAN);
                settlementAmnt = ((Double)dtl.get(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_AMNT)).intValue();
                settlementDt = (java.util.Date)dtl.get(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_DT);
                if(settlementDt != null)                
                {
                     sdf = new SimpleDateFormat("dd/MM/yyyy");
                     // settlementDtStr = settlementDt.toString();
                     settlementDtStr = sdf.format(settlementDt);
                }
                else
                {
                     settlementDtStr = "-";
                }
                settlmntAdvceFirstInstllmntKey = "settlementAdviceFlags("+
                memberId+ClaimConstants.CLM_DELIMITER1+
                cgclan+
                ClaimConstants.CLM_DELIMITER1+
                ClaimConstants.FIRST_INSTALLMENT+
                ClaimConstants.CLM_DELIMITER1+
                settlementAmnt+
                ClaimConstants.CLM_DELIMITER1+
                settlementDtStr+")";
                               
                %>
		<tr class="TableData"> 
		  <td><div align="center"><%=i%></div></td>		  
		  <td>&nbsp;<%=memberId%></td>
		  <td><div align="center">&nbsp;<%=cgclan%></div></td>
		  <td><div align="center">&nbsp;<%=settlementAmnt%></div></td>
		  <td>&nbsp;<%=settlementDtStr%></td>
		  <td><div align="center"> 
		      <html:checkbox property="<%=settlmntAdvceFirstInstllmntKey%>" name="cpTcDetailsForm" value="Y"/>
		    </div></td>
		</tr> 
		<%i++;%>
                </logic:iterate>                                                               
                <tr>
			<td><br></tr>
  		</tr>                                
  		</logic:notEqual>
  		<logic:notEqual property="secondCounter" value="0" name="cpTcDetailsForm">
                <tr> 
			 <td class="SubHeading" colspan="4"> &nbsp;<bean:message key="adviceForSecondSettlement"/></td>
		 </tr>
		 <tr class="HeadingBg"> 
		   <td> <div align="center">&nbsp;<bean:message key="SerialNumber"/></div></td>		   
		   <td><div align="center">&nbsp;<bean:message key="cpmemId"/></div></td>
		   <td> <div align="center"><strong>&nbsp;<bean:message key="cgclannumber"/></strong></div></td>
		   <td><div align="center"> &nbsp;<bean:message key="tiertwosettlement"/></div></td>
		   <td> <div align="center"><strong>&nbsp;<bean:message key="cpsettlemntdt"/></strong></div></td>
		   <td> <div align="center">&nbsp;<bean:message key="generatecsa"/></div></td>
		 </tr>
		<logic:iterate property="settlmntAdviceDtlsSecondSttlmnt" name="cpTcDetailsForm" id="object">
		<%
		java.util.HashMap anotherdtl = (java.util.HashMap)object;
		memberId = (String)anotherdtl.get(ClaimConstants.CLM_MEMBER_ID);
		cgclan = (String)anotherdtl.get(ClaimConstants.CLM_CGCLAN);
		settlementAmnt = ((Double)anotherdtl.get(ClaimConstants.CLM_SETTLMNT_SECOND_SETTLMNT_AMNT)).intValue();
		settlementDt = (java.util.Date)anotherdtl.get(ClaimConstants.CLM_SETTLMNT_SECOND_SETTLMNT_DT);
		if(settlementDt != null)
		{
		    // settlementDtStr = settlementDt.toString();
		    sdf = new SimpleDateFormat("dd/MM/yyyy");
		    settlementDtStr = sdf.format(settlementDt);
		}
		else
		{
		    settlementDtStr = "-";
		}
		settlmntAdvceSecndInstllmntKey = "settlementAdviceFlags("+memberId+ClaimConstants.CLM_DELIMITER1+cgclan+ClaimConstants.CLM_DELIMITER1+ClaimConstants.SECOND_INSTALLMENT+ClaimConstants.CLM_DELIMITER1+settlementAmnt+ClaimConstants.CLM_DELIMITER1+settlementDtStr+")";
		%>
                <tr class="TableData"> 
		  <td><div align="center"><%=j%></div></td>		  
		  <td>&nbsp;<%=memberId%></td>
		  <td><div align="center">&nbsp;<%=cgclan%></div></td>
		  <td><div align="center">&nbsp;<%=settlementAmnt%></div></td>
		  <td>&nbsp;<%=settlementDtStr%></td>
		  <td><div align="center"> 
		      <html:checkbox property="<%=settlmntAdvceSecndInstllmntKey%>" name="cpTcDetailsForm" value="Y"/>
		    </div></td>
		</tr> 
		<%j++;%>
                </logic:iterate>
		</logic:notEqual>
              </table></td>
          </tr>
          <tr align="center" valign="baseline"> 
            <td colspan="4"> <div align="center">
            <a href="javascript:document.cpTcDetailsForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></a>
            <A href="javascript:submitForm('saveGenerateCSAOption.do?method=saveGenerateCSAOption')"><img src="images/Submit.gif" alt="Save" width="49" height="37" border="0"></a>
            <a href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></a></div></td>
          </tr>
        </table></td>
    <td background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr>
    <td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
    <td background="images/TableBackground2.gif"><div align="center"></div></td>
    <td align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
  </tr>
</table>
</html:form>
</body>
</html>
