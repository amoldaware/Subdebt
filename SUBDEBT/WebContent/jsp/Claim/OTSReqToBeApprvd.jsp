<%@ page language="java"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%session.setAttribute("CurrentPage","displayOTSProcessInfo.do?method=displayOTSProcessInfo");%>

<%
String otsdecision = "";
String otsremarks = "";
String url = "";
String otsreqdateStr = "";
%>

<html:errors/>
<html:form action="saveOTSProcessDetails.do?method=saveOTSProcessDetails" method="POST" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    
    <tr>
      <td class="FontStyle">&nbsp;</td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
    <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td width="248" background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></td>
    <td align="right" valign="top" background="images/TableBackground1.gif"> 
      <div align="right"></div></td>
    <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
  </tr>
  <tr> 
    <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2">
    <DIV align="right">			
      		<A HREF="javascript:submitForm('helpProcessOTSDetails.do')">
      	        HELP</A>
      </DIV>            
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="8"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="25%" height="20" class="Heading">&nbsp;OTS Consent</td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
                      <tr> 
                        <td colspan="8" class="SubHeading"><table width="100%" border="0" cellpadding="0" cellspacing="1">
                            <tr class="ColumnBackground"> 
                              <td width="20%"> <div align="center">&nbsp;<bean:message key="cpmliid"/></div></td>
                              <td width="20%"> <div align="center">&nbsp;<bean:message key="borrowerID"/></div></td>
                              <td width="20%"> <div align="center">&nbsp;<bean:message key="otsreqdate"/></div></td>
                              <td width="20%"> <div align="center">&nbsp;<bean:message key="otsdecision"/></div></td>
                              <td width="20%"> <div align="center">&nbsp;<bean:message key="otsremarks"/></div></td>
                            </tr>                            
                            <logic:iterate property="otsprocessdetails" name="cpTcDetailsForm" id="object">
                            <%
                            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                            com.cgtsi.claim.OTSApprovalDetail approvaldtl = (com.cgtsi.claim.OTSApprovalDetail)object;
                            String mliId = approvaldtl.getMliId();
                            String bid = approvaldtl.getCgbid();
                            java.util.Date otsreqdate = approvaldtl.getOtsRequestDate();                            
                            if(otsreqdate != null)
                            {
                            	otsreqdateStr = sdf.format(otsreqdate);
                            }
                            url = "displayOTSReferenceDetails.do?method=displayOTSReferenceDetails&"+ClaimConstants.CLM_BORROWER_ID+"="+bid;
                            %>
                            <tr class="TableData"> 
                              <td > <div align="center">&nbsp;<%=mliId%></div></td>
                              <td > <div align="center"><a href="<%=url%>"><%=bid%></a></div></td>
							  <td > <div align="center">&nbsp;<%=otsreqdateStr%></div></td>
							  <td > <div align="center">
							  <%							  otsdecision="decision("+mliId+"#"+bid+"#"+otsreqdate+")";
							  %>
							  <html:select property="<%=otsdecision%>" name="cpTcDetailsForm">
							  <html:option value="">Select</html:option>							  
							  <html:option value="RE">Reject</html:option>
							  <html:option value="AP">Approve</html:option>
							  </html:select>
							  </div></td>
							  <%
							  otsremarks ="remarks("+mliId+"#"+bid+"#"+otsreqdate+")";
							  %>
							  <td > <div align="center">
							  <html:textarea property="<%=otsremarks%>" name="cpTcDetailsForm"/>
							  </div></td>
                            </tr>		
                            </logic:iterate>
                          </table></td>
                </tr>
             </table>
            </td>
        </tr>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="top"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" align="center" valign="middle" background="images/TableBackground3.gif"> 
        <div> 
          <div align="center">          
          <A href="javascript:submitForm('saveOTSProcessDetails.do?method=saveOTSProcessDetails')"><img src="images/Save.gif" alt="OK" width="49" height="37" border="0" align="middle"></a>
          <a href="javascript:document.cpTcDetailsForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0" align="middle"></a>
          <a href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0" align="middle"></a>
          </div>
      </div></td>
      <td width="23" align="right" valign="top"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>
</html>
