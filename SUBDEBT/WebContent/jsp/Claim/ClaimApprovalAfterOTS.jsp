   <%@ page language="java"%>
<%@ page import="com.cgtsi.actionform.ClaimActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>  
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","RecoveryAfterOTS.do?method=getRecoveryAfterOTSDetail");%>

<!--<body onload="processForwardedToFirst(),processForwardedToSecond()"> -->

 <SCRIPT LANGUAGE="JavaScript">
        function Validation()
        {  
        
//        document.forms[0].target ="_self";
//        document.forms[0].method="POST";
//        document.forms[0].action="/cgtsi-ViewController-context-root/RecoveryProcessingOTS.do?method=saveApproval";
//        document.forms[0].submit();
       	return true;
        }
        </SCRIPT>

 

<html:errors/>

<html:form action="RecoveryProcessingOTS.do?method=saveApproval" method="POST" enctype="multipart/form-data">
  <table id="mainTable" width="100%" border="0" cellspacing="0" cellpadding="0">
   
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
    <td>
      <DIV align="right">			
      		<A HREF="javascript:submitForm('helpProcessClaims.do')">
      	        HELP</A>
      </DIV>
      
      <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
              <td colspan="4">
              <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                                                                
                         <tr> <td colspan="6">&nbsp;</td></tr>
                          <TR>
                             <TD width="22%" class="Heading"><bean:message key="ots_details" /></TD>
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
                 
                 </td>
                 </tr>
                 <tr>
                <td colspan="4" class="SubHeading">
                  &nbsp;
                 CGTMSE Claim Processing.
                </td>
              </tr>
              <tr>
              <td>
               <table width="100%" border="0" cellspacing="1"
                         cellpadding="0">
       
                    <tr class="TableData">
                      <td valign="middle" class="HeadingBg" width="7%">
                        <div align="center">
                          <strong>Member Id<br/>
                             </strong>
                        </div>
                      </td>
                                        <td valign="middle" class="HeadingBg" width="60%">
                        <div align="center">
                          <strong>Claim Reference No.<br/>
                             </strong>
                        </div>
                      </td>
					  
                         <td valign="middle" class="HeadingBg" width="10%">
                         <div align="center">
                          <strong>Unit Name<br/>
                             </strong>
                         </div>
                         </td>
                         <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>Gurantee Amount<br/>
                             </strong>
                         </div>
                         </td>
                         <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>Amount in Defaulton NPA Date<br/>
                             </strong>
                         </div>
                         </td>


						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>Recovery from primary Security Remitted to CGTMSE at the time of cliam lodgment.<br/>
                             </strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>Net Outstanding <br/>
                             </strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>Total Liable Amount <br/>
                             </strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>First Installment paid by CGTMSE.<br/>
                             </strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>Recovery From Primary Security Remitted to CGTMSE After Claim lodgment.<br/>
                             </strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong><br/>
                             Legal Expences if Not Deducted.</strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>Legal Expences if Deducted.<br/>
                             </strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>Net Recovery After Claim Lodgement. <br/>
                             </strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>Net Amount in Default .<br/>
                             </strong>
                         </div>
                         </td>
                         
                         <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>Second Installment Payble by CGTMSE.<br/>
                             </strong>
                         </div>
                         </td> 
                          <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>Final Payout.<br/>
                             </strong>
                         </div>
                         </td> 
                         <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>Action.<br/>
                             </strong>
                         </div>
                         </td> 
                          <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>Remarks.<br/>
                             </strong>
                         </div>
                         </td> 
			
                    </tr>
                    <%
//                    ClaimActionForm_one obj=(ClaimActionForm_one)session.getAttribute("clmForm"); 
//                     java.util.ArrayList ab=obj.getDataforapp();
//                   for (int i=0;i<ab.size();i++)
//                   {
//                   ClaimActionForm_one claimdeclarion=(ClaimActionForm_one)ab.get(i);
                   //otsClmProcess
                    %>
              
             <logic:iterate id="object" name="cpOTSProcess" property="otsClmProcess" indexId="index" scope="session">
              <%ClaimActionForm clm=(ClaimActionForm)object;
              System.out.println("The member is is :--->"+clm.getOts_memberId());              %>
       
             <tr class="TableData">  
              
                                        <td width="30%">
                                        <div align="center">
                                         <%=clm.getOts_memberId()%>
                                          <!--<bean:write name="cpOTSProcess" property="ots_memberId" scope="session"/> -->
                                          
                                        <%// =claimdeclarion.getCp_ots_enterMember()%>
                                        </div>
                                        </td>
                                         <td width="35%">
                                        <div align="center">
                                        
                                      <%=clm.getOts_clmRefNo()%>
                                      
                                        </div>
                                        </td>
                                      
                                         <td width="35%">
                                        <div align="center">
                                        <% //=claimdeclarion.getCp_ots_npaTcTotal()%>
                                      
                                     <%=clm.getOts_unitName()%>
                                        </div>
                                        </td>
                                         <td width="35%">
                                        <div align="center">
                                     
                                    <%=clm.getOts_gaurnteeAmt()%>
                                        </div>
                                        </td>
                                         <td width="35%">
                                        <div align="center">
                                       <%=clm.getOts_amtInDefault()%>
                                   
                                        </div>
                                        </td>
                                        
                                        
                                         <td width="35%">
                                        <div align="center">
                                       <%=clm.getOts_recoveryPrimary()%>
                                     
                                        </div>
                                        </td>
                                       
                                       
                                         <td width="35%">
                                        <div align="center">
                                         <%=clm.getOts_netOutstanding()%>
                                  
                                        </div>
                                        </td>
                                        
                                          <td width="35%">
                                        <div align="center">
                                        <%=clm.getOts_liableAmt()%>
	                               
                                        </div>
                                        </td>
                                          <td width="35%">
                                        <div align="center">
                                          <%=clm.getOts_firstInstallPaidAmt()%>
                                       </div>
                                        </td>
                                          <td width="35%">
                                        <div align="center">
                                        <%=clm.getOts_recoveryAfterPrimary()%>
                                        </div>
                                        </td>
                                          <td width="35%">
                                        <div align="center">
                                        <%=clm.getOts_legalExpencesNotDeducted()%>
                                
                                        </div>
                                        </td>
                                          <td width="35%">
                                        <div align="center">
                                      <%=clm.getOts_legalExpencesDeducted()%>
                                  
                                        </div>
                                        </td>
                                          <td width="35%">
                                        <div align="center">
                                       <%=clm.getOts_netRecovery()%>
                                   
                                        </div>
                                        </td>
                                          <td width="35%">
                                        <div align="center">
                                        <%=clm.getOts_netDefaultAmt()%>
                                  
                                        </div>
                                        </td>
                                        <td width="35%">
                                        <div align="center">
                                       <%=clm.getOts_secondInstallmentAmt()%>	
                                   
                                        </div>
                                        </td>
                                          <td width="35%">
                                        <div align="center">
                                         <%=clm.getOts_finalPayout()%>
                                        </div>
                                        </td>
                                  <td width="35%">
                                        <div align="center">
                                        <html:select name="cpOTSProcess"  property="ots_decisionTaken">
                                            <html:option value="select" >Select</html:option>
                                            <html:option value="approve" >Approval</html:option>
                                            <html:option value="reject" >Reject</html:option>
                                            <html:option value="hold" >Hold</html:option>
                                        </html:select>
                                       </div>
                                  </td>
                                        <td width="35%">
                                        <div align="center">
                   <html:text property="ots_remark" size="20" maxlength="50"  name="cpOTSProcess" />
                   
                    
                    
                    
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
                        <a href="javascript:submitForm('RecoveryProcessingOTS.do?method=saveApproval')"> 
             <!-- <a href="javascript:Validation()">-->
             <img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></a>
            <a href="javascript:document.cpOTSProcess.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></a>
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





 
