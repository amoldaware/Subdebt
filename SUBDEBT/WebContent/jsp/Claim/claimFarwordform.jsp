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
<% session.setAttribute("CurrentPage","RecoveryAfterOTS.do?method=farwordData");%>

<!--<body onload="processForwardedToFirst(),processForwardedToSecond()"> -->
 <SCRIPT LANGUAGE="JavaScript">
        function submitPage()
        {
      
        document.forms[0].target ="_self";
        document.forms[0].method="POST";
        document.forms[0].action="/cgtsi-ViewController-context-root/RecoveryAfterOTS.do?method=saveOTSData";
        document.forms[0].submit();
//        document.forms[0].target ="_self";
//        document.forms[0].method="POST";
//        document.forms[0].action="/cgtsi-ViewController-context-root/RecoveryAfterOTS.do?method=farwordData";
//        document.forms[0].submit();
       	
        }
        function retrieveSecondOptions(){ 
        cp_ots_userChoice = document.getElementById('cp_ots_userChoice'); 
  	//Nothing selected 	 
  	if(cp_ots_userChoice.selectedIndex==0){ 	 
  	return; 	 
        } 	 
  
	selectedOption = cp_ots_userChoice.options[cp_ots_userChoice.selectedIndex].value; 
   //alert("the value is :--->"+selectedOption);
     
          var st=cp_ots_userChoice.value;
     	//url="afterTcMli.do?method=getDistricts&state="+state.value; 
        url="RecoveryAfterOTS.do?method=getuserList&cp_ots_userChoice="+st; 
     
  	if (window.XMLHttpRequest){ // Non-IE browsers 	 
  	req = new XMLHttpRequest(); 	 
  	//A call-back function is define so the browser knows which function to call after the server gives a reponse back 	 
  	req.onreadystatechange = populateSecondBox; 	 
        try { 	 
  	req.open("GET", url, true); //was get 	 
  	} catch (e) { 	 
  	alert("Cannot connect to server"); 	 
  	} 	 
 	req.send(cp_ots_userChoice); 	 
 	} else if (window.ActiveXObject) { // IE 	 
 	req = new ActiveXObject("Microsoft.XMLHTTP"); 	 
 	if (req) { 	 
	req.onreadystatechange = populateSecondBox; 	 
	req.open("GET", url, true); 	 
	
        req.send(); 	 
	} 	 
 	} 	 
	} 	 
	function populateSecondBox(){ 	 
 
	document.getElementById('cp_ots_user').options.length = 0; 	 
  	if (req.readyState == 4) { // Complete 	 
  	if (req.status == 200) { // OK response 	 
  	textToSplit = req.responseText 	 
       // alert ("the data: "+textToSplit);
                            if(textToSplit == '803'){ 	 
                                alert("No select option available on the server") 	 
                                                    } 	 //if close
 	var returnElements=textToSplit.split("||") 
      
	for ( var i=0; i<returnElements.length; i++ ){ 	 
          valueLabelPair = returnElements[i].split(";") 	 
          document.getElementById('cp_ots_user').options[i] = new Option(valueLabelPair[0], valueLabelPair[1]); 	 
        //  document.getElementById('district').options[i] =new Option(returnElements[i],returnElements[i]) ; 	 
                                              	} 	 //for close
  	} //ok close	 
         else { 	 
        	alert("Bad response by the server"); 	 
        	} 	 
   } //complet close
           
	} 
        </SCRIPT>

<html:errors/>

<html:form action="RecoveryAfterOTS.do?method=saveOTSData" method="POST" enctype="multipart/form-data">
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
         
          <table width="100%" border="0" cellspacing="1"
                         cellpadding="0">
                         
                        
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
                               OTS Processing
                             
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
                <strong>Unit Name.</strong>
                 </div>
              </td>
              <td width="11%" >
                <div align="center">
                  <strong>Claim Ref NO.</strong><br/>
                </div>
              </td>
               <td  width="13%" >
                <div align="center">
                  <strong>First Installment Amount.</strong><br/>
                </div>
              </td>
              <td width="13%">
                <div align="center">
                  <strong>Net Recovery </strong><br/>
                </div>
              </td>
              <td  width="13%">
                <div align="center">
                  <strong>Second Installment Amount.</strong><br/>
                </div>
              </td>
              <td  width="15%">
                <div align="center">
                  <strong>Final Payout Amount.</strong><br/>
                </div>
              </td>
              <td  width="13%">
                <div align="center">
                  <strong>Select Action </strong><br/>
                </div>
              </td>
              <td  width="11%">
                <div align="center">
                  <strong>Farword To User.</strong><br/>
                </div>
              </td>
              <td  width="11%">
                <div align="center">
                  <strong>Remark</strong><br/>
                </div>
              </td>
                          
            </tr>         
           
           <% int i=1;%>
           
           
           
            <tr class="TableData">  
            <td>
             <div align="center">
             <%=i++%>
             </div>
             </td>
            <td>
             <div align="left">
             <bean:write name="cpRecoveryOTS" property="cp_ots_unitName" scope="session"/>
           
             </div>
             </td>
            <td>
              <div align="center">
               <bean:write name="cpRecoveryOTS" property="cp_ots_appRefNo" scope="session"/>
               <br/>
              </div>
            </td>
                       
             <td>
              <div align="center">
           <bean:write name="cpRecoveryOTS" property="cp_ots_firstInstallmentPaidTotal" scope="session"/>
          
           <br/>
              </div>
            </td>
             
             <td>
              <div align="center">
               <bean:write name="cpRecoveryOTS" property="cp_ots_totnetRecovAmt" scope="session"/>
             
              <br/>
              </div>
            </td>
             <td>
              <div align="center">
               <bean:write name="cpRecoveryOTS" property="cp_ots_secinstamentAmtTotal" scope="session"/>
            
                <br/>
              </div>
            </td>
             <td>
              <div align="center">
                <bean:write name="cpRecoveryOTS" property="cp_ots_finalPayoutAmtTotal" scope="session"/>
               <br/>
              </div>
            </td>
             <td>
              <div align="center">
             	<html:select property="cp_ots_userChoice" name="cpRecoveryOTS" onchange="retrieveSecondOptions()" styleId="cp_ots_userChoice"  >
														<html:option value="NE">Select</html:option>
														<html:option value="FW">Farword</html:option>
							</html:select>	
                <br/>
              </div>
            </td>
             <td>
              <div align="center">
                  <html:select property="cp_ots_user" name="cpRecoveryOTS" onchange="javascript:enableDistrictOthers()" styleId="cp_ots_user" >
														<html:option value="Select">Select</html:option>
														<html:options name="cpRecoveryOTS" property="cp_ots_userList"/>	
                           	</html:select>
                 <br/>
              </div>
            </td>
             <td>
              <div align="center">
              <div align="left"><html:textarea property="cp_ots_remarks2" cols="30" name="cpRecoveryOTS" rows="4"/></div>
               <br/>
              </div>
            </td>
             <td>
              <div align="center">
                <br/>
              </div>
            </td>
             
            </tr> 
            
           
                 </table>
                 
                </td>
              </tr>
            
                   
          </table>
		 
               
          <table width="100%" border="0" cellspacing="1" cellpadding="0">
          <tr align="center" valign="baseline">
            <td colspan="4"> 
            <div align="center">  
                       <!-- <a href="javascript:submitForm('declartionDetails.do?method=SaveDeclartaionDetailData')"> -->
           <!-- <A href="javascript:printpage()"> -->
            <a href="javascript:submitPage()">
             <img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></a>
            <a href="javascript:document.cpRecoveryOTS.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></a>
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
 

