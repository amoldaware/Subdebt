 <%@ page language="java"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ page import="com.cgtsi.actionform.ClaimActionForm"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.ParsePosition"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>  
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","ClaimOtsReport.do?method=getOtsReport");%>

<!--<body onload="processForwardedToFirst(),processForwardedToSecond()"> -->

<html:errors/>

<html:form action="ClaimOtsReport.do?method=getOtsReportDetails" method="POST" enctype="multipart/form-data">
  <table id="mainTable" width="100%" border="0" cellspacing="0" cellpadding="0">
   
    <tr>
      <td class="FontStyle">&nbsp;</td>
    </tr>
  </table>
 
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
    <td background="images/TableBackground1.gif"><img src="images/Clear.gif" width="131" height="25"></td>
    <td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
  </tr>
  <tr>
    <td background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td>
      <DIV align="right">			
      		<A HREF="javascript:submitForm('helpProcessClaims.do')">
      	        HELP</A>
      </DIV>
         
          <table width="100%" border="0" cellspacing="1"
                         cellpadding="0">
                         
                         <tr>
                          <td colspan="6" class="Heading1" align="center"><u><bean:message key="reportHeader"/></u></td>
                          </tr>
                          </br>
                    <TR>
                    <TD>
                    <BR></BR>
                    </TD>
                    </TR>
                    <TR>
                    </TR>
                   
              </table>
          <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
                     <td colspan="4">
                        <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                                                                
                         <tr> <td colspan="6">&nbsp;</td></tr>
                          <TR>
                             <TD width="22%" class="Heading">
                             <!--<bean:message key="claimReport" /> -->
                               OTS Report
                             
                             </TD>
                             </td>
                             <TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
                             </TR>
                    <TR>
                            <TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
                    </TR>

                </TABLE>

              </td>
            </tr>
              <tr>
                <td colspan="4">
              <table width="100%"  border="0" cellspacing="1" cellpadding="0">
                              
              <tr class="TableData">  
               <td>
                 <div align="center">
                <strong> Sr. NO.</strong>
                 </div>
              </td>
              <td>
                 <div align="left">
                <strong> Member Id.</strong>
                 </div>
              </td>
              <td width="11%" >
                <div align="center">
                  <strong>Claim Ref NO.</strong><br/>
                </div>
              </td>
               <td  width="13%" >
                <div align="center">
                  <strong>CGPAN NO.</strong><br/>
                </div>
              </td>
              <td width="13%">
                <div align="center">
                  <strong>Unit Name</strong><br/>
                </div>
              </td>
              <td  width="13%">
                <div align="center">
                  <strong>Gaurantee Amount</strong><br/>
                </div>
              </td>
              <td  width="15%">
                <div align="center">
                  <strong>Claim Eligible Amount.</strong><br/>
                </div>
              </td>
              <td  width="13%">
                <div align="center">
                  <strong>First Installment Amount. </strong><br/>
                </div>
              </td>
              <td  width="11%">
                <div align="center">
                  <strong> Recovery Total.</strong><br/>
                </div>
              </td>
              <td  width="11%">
                <div align="center">
                  <strong>Second Installment Amount</strong><br/>
                </div>
              </td>
              <td  width="11%">
                <div align="center">
                  <strong>Final Payout Amount</strong><br/>
                </div>
              </td>
             
            </tr>         
           
           <% int i=1;%>
            <logic:iterate id="object" name="cpRecoveryOTS" property="claimformdataReport" indexId="index">
            <%ClaimActionForm clm=(ClaimActionForm)object; %>
           
            <tr class="TableData">  
            <td>
             <div align="center">
             <%=i++%>
             </div>
             </td>
            <td>
             <div align="left">
            <%=clm.getCp_ots_enterMember()%>
             </div>
             </td>
            <td>
              <div align="center">
              <%=clm.getCp_ots_appRefNo()%>
                <br/>
              </div>
            </td>
             
           
             <td>
              <div align="center">
                
                
                <%=clm.getCp_ots_enterCgpan()%>
                <br/>
              </div>
            </td>
             
             <td>
              <div align="center">
                <%=clm.getCp_ots_unitName()%><br/>
              </div>
            </td>
             <td>
              <div align="center">
                <%=clm.getCp_ots_cgpanGaurnteeAmt()%><br/>
              </div>
            </td>
             <td>
              <div align="center">
                <%=clm.getCp_ots_clmeligibleamt()%><br/>
              </div>
            </td>
             <td>
              <div align="center">
                <%=clm.getCp_ots_firstIntalpaidAmount()%><br/>
              </div>
            </td>
             <td>
              <div align="center">
                   <%=clm.getCp_ots_netRecovAmt()%>
                              <br/>
              </div>
            </td>
             <td>
              <div align="center">
                <%=clm.getCp_ots_secIntalAMt()%><br/>
              </div>
            </td>
             <td>
              <div align="center">
                <%=clm.getCp_ots_finalPayout()%><br/>
              </div>
            </td>
             
            </tr> 
             </logic:iterate>
           
                 </table>
                 
                </td>
              </tr>
            
                   
          </table>
		 
               
          <table width="100%" border="0" cellspacing="1" cellpadding="0">
          <tr align="center" valign="baseline">
            <td colspan="4"> 
            <div align="center">  
                       <!-- <a href="javascript:submitForm('declartionDetails.do?method=SaveDeclartaionDetailData')"> -->
            <A href="javascript:printpage()">
            <IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A>
            <a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></a></div></td>
          </tr>
        </table>
    <td background="images/TableVerticalRightBG.gif">&nbsp;</td>
  
    <tr>
    <td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
    <td background="images/TableBackground2.gif"><div align="center"></div></td>
    <td align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
  </tr>
</table>
</html:form>
 

