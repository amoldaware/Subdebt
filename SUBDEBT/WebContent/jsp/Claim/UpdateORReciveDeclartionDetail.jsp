<%@ page language="java"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ page import="com.cgtsi.actionform.ClaimActionForm"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.ParsePosition"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","declartionDetails.do?method=getDeclartaionDetailData");%>

<!--<body onload="processForwardedToFirst(),processForwardedToSecond()"> -->
 <SCRIPT LANGUAGE="JavaScript">
   function Submit()
   {
        
        
	if(!isDateValid( document.forms[0].dateOfdeclartionRecive.value))
	{
        

                alert("plz insert valid date");

	} else if(document.forms[0].booleanProperty.checked != true)
        {
         alert("Please select Claim Declartion Recived.   ");
    
        }

               else{
      
                document.forms[0].target ="_self";
                 document.forms[0].method="POST";
                 document.forms[0].action="/cgtsi-ViewController-context-root/declartionDetails.do?method=SaveDeclartaionDetailData";
                 document.forms[0].submit();
                 }

   }
   function isDateValid(thestring)
{
	if(thestring && thestring.length)
	{
		alert("inner");
		for (i = 0; i < thestring.length; i++) {
			ch = thestring.substring(i, i+1);
			if ((ch < "0" || ch > "9")  && ch!="/")
			  {
			  alert("The numbers may contain digits 0 thru 9 only!");
			  
                          return false;
			  }
		}
	}
	else
	{
		return false;
	}
    return true;
}
function chkdate(objName) {
                                       
            var strDatestyle = "US"; //United States date style
                                      
            var strDate;
            var strDateArray;
            var strDay;
            var strMonth;
            var strYear;
            var intday;
            var intMonth;
            var intYear;
            var booFound = false;
            var flag=false;
            var datefield = objName;
           
            var strSeparatorArray = new Array("-");

            var intElementNr;
            var err = 0;

            var strMonthArray = new Array(12);

            strMonthArray[0] = "01";
            strMonthArray[1] = "02";
            strMonthArray[2] = "03";
            strMonthArray[3] = "04";
            strMonthArray[4] = "05";
            strMonthArray[5] = "06";
            strMonthArray[6] = "07";
            strMonthArray[7] = "08";
            strMonthArray[8] = "09";
            strMonthArray[9] = "10";
            strMonthArray[10] ="11";
            strMonthArray[11] ="12";
            
            strDate = datefield;
            
            if (strDate.length < 10) {
            return false;
            }
            strDateArray = strDate.split("/");
            if (strDateArray.length != 3) {
            err = 1;
            return false;
            }
            else {
            strDay = strDateArray[0];
            strMonth = strDateArray[1];
            strYear = strDateArray[2];
            booFound = true;
            }
           intday = parseInt(strDay);
            if (isNaN(intday)) {
            err = 2;
            return false;
            }
            intMonth = parseInt(strMonth);
            
            if (isNaN(intMonth)) {
             
             return false;
             }//if close
             else{
             
            for (i = 0;i<12;i++) {
            if (strMonth == strMonthArray[i]) {
            intMonth = i+1;
            strMonth = strMonthArray[i];
            i = 12;
            flag=true;
               }
               }
            }//else close
            
            
            if (flag==false)
            {
             return false;
            }
            if (strYear==0000)
            {
            return false;
            }
            if (isNaN(strYear)) {
            err = 4;
            return false;
            }
            if (intMonth>12 || intMonth<1) {
            err = 5;
            return false;
            }
            if ((intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8 || intMonth == 10 || intMonth == 12) && (intday > 31 || intday < 1)) {
            err = 6;
            return false;
            }
            if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) && (intday > 30 || intday < 1)) {
            err = 7;
            return false;
            }
            if (intMonth == 2) {
            if (intday < 1) {
            err = 8;
            return false;
            }
            if (LeapYear(intYear) == true) {
            if (intday > 29) {
            err = 9;
            return false;
            }
            }
            else {
            if (intday > 28) {
            err = 10;
            return false;
            }
            }
            }
            if (strDatestyle == "US") {
            datefield.value = intMonth + " " + intday+" " + strYear;
            }
            else {
            datefield.value = intday + " " + intMonth + " " + strYear;
            }
            return true;
            }
            function LeapYear(intYear) {
            if (intYear % 100 == 0) {
            if (intYear % 400 == 0) { return true; }
            }
            else {
            if ((intYear % 4) == 0) { return true; }
            }
          
            return false;
            }
            
       function  corrdate(inputDate)
           {
            var strDateArray;
			var strDay;
                        var strMonth;
		        var strYear;	
                         strDateArray = inputDate.split("/");
			strDay = strDateArray[0];
                        strMonth = strDateArray[1];
                        strYear = strDateArray[2];
                        var newformat =strMonth.concat("/",strDay,"/",strYear);
                        var currentTime = new Date();
                        var dd= new Date(newformat);              

                if (dd > currentTime ){
                return false;
                
                                      }
                                       return true;
           }
  </SCRIPT>

<html:errors/>

<html:form action="declartionDetails.do?method=SaveDeclartaionDetailData" method="POST" enctype="multipart/form-data">
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
    <td>
      <DIV align="right">			
      		<A HREF="javascript:submitForm('helpProcessClaims.do')">
      	        HELP</A>
      </DIV>
          <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
              <td colspan="4">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="35%" class="Heading">&nbsp;Recived Claim
                                                    Applications</td>
                    <td colspan="3">
                      <img src="images/TriangleSubhead.gif" width="19"
                           height="19"/>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="4" class="Heading">
                      <img src="images/Clear.gif" width="5" height="5"/>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>

              <tr>
                <td colspan="4" class="SubHeading">
                  &nbsp;
                  <bean:message key="firstclaim"/>
                </td>
              </tr>
             
             
              <tr>
                <td colspan="4">
                 
                  <table width="100%" border="0" cellspacing="1"
                         cellpadding="0">
       
                    <tr class="TableData">
		            
		                    
		                      <td valign="middle" class="HeadingBg">
		                        <div align="center">
		                          <strong>&nbsp;MemberId<br/>
		                             </strong>
		                        </div>
		                      </td>
		                    
		                      <td >
                                      <div align="center">
                                      <bean:write name="cpTcDetailsForm" property="memberID" scope="session"/>
                                       </div>
                                       </td>
		                      
		                      <td class="HeadingBg">
		                        <div align="center">
		                          Borrower Name
		                        </div>
		                      </td>
		                      
		                      <td>
                                      <div align="center">
		                     <bean:write name="cpTcDetailsForm" property="ssiUnitName" scope="session"/>
                                      </div>
		                      </td>
      				                 
      				</tr>
       
                    <tr class="TableData">  
			                      		<td class="HeadingBg">
				                       	 <div align="center">
                                                        <strong>
			                          	 Claim Reffernce No.&nbsp;
				                        </strong>
                                                         </div>
				                        </td>
			                          
			                           <td>
                                                      <div align="center">
                                                       <bean:write name="cpTcDetailsForm" property="claimRefNum" scope="session"/> 
                                                        </div>
			                           </td>
			                           
				                      <td class="HeadingBg">
				                        <div align="center">
				                          <strong>Accounts NPA Date</strong><br/>
				                        </div>
				                      </td>
				                      
				                       <td>
				                        <div align="center">
                                                       <bean:write name="cpTcDetailsForm" property="dtOfNPAReportedToCGTSI" scope="session"/> 
                                                        </div>
				                      </td>
			                      
			        </tr>
			                      

                    <%
                int i=1;
                int j=0;
                %>
              <tr class="TableData">  
			                      		<td class="HeadingBg">
				                       	 <div align="center">
                                                        <strong>
			                          	 Claim Declartion Recived. &nbsp;
				                        </strong>
                                                         </div>
				                        </td>
                                                        <td>
                                                        <div align="center">
                                                        <html:checkbox property="booleanProperty" name="cpTcDetailsForm" />
                                                        </div>
                                                        </td>
			                           
				                      <td class="HeadingBg">
				                        <div align="center">
				                          <strong>Date Of Declaration Recived</strong><br/>
				                        </div>
				                      </td>
				                      
				                       <td>
				                        <div align="center">
                                                        <html:text property="dateOfdeclartionRecive" size="20" maxlength="10" alt="Reference" name="cpTcDetailsForm"/>
                                                        <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.dateOfdeclartionRecive')" align="center">
                                                        </div>
				                      </td>
			        </tr>
                 </table>
                 <table width="100%"  border="0" cellspacing="1" cellpadding="0">
                 <tr>
                <td colspan="4" class="SubHeading">
                  &nbsp;
                  <bean:message key="cgpanforclaimapplicatio"/>
                </td>
              </tr>
              
              <tr class="TableData">  
                            <td class="HeadingBg">
                             <div align="center">
                            <strong>
                             Sr No. &nbsp;
                            </strong>
                             </div>
                            </td>
                          <td class="HeadingBg">
                            <div align="center">
                              <strong>CGPAN</strong><br/>
                            </div>
                          </td>
                          <td class="HeadingBg">
                            <div align="center">
                              <strong>Gaurantee Approved Date</strong><br/>
                            </div>
                          </td>
                          <td class="HeadingBg">
                            <div align="center">
                              <strong>Gaurantee Approved Amount</strong><br/>
                            </div>
                          </td>
            </tr>         
           
            <logic:iterate id="object" name="cpTcDetailsForm" property="claimdeatil" indexId="index">
            <%ClaimActionForm claimdeclarion=(ClaimActionForm)object; %>
           
            <tr class="TableData">  
                          
                            <td>
                             <div align="center">
                            <strong>
                             <%=i++%>
                            </strong>
                             </div>
                            </td>
                          <td>
                            <div align="center">
                              <strong>
                              <%=claimdeclarion.getCgpanNo()%>
                              </strong><br/>
                            </div>
                          </td>
                           
                         
                           <td>
                            <div align="center">
                              <strong>
                              <%           java.util.Date appAppDate=(java.util.Date)claimdeclarion.getAppApproveDate();
                                            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                            String appDate = formatter.format(appAppDate);
                                            %>
                              <%=appDate%>
                              </strong><br/>
                            </div>
                          </td>
                           
                           <td>
                            <div align="center">
                              <strong><%=claimdeclarion.getAppAmount()%></strong><br/>
                            </div>
                          </td>
                           
            </tr>         
            </logic:iterate>
           
            
              
                 </table>
                </td>
              </tr>
            
            <tr>
              <td>
                <br/>
              </td>
            </tr>
         
          </table>
          <table width="100%" border="0" cellspacing="1" cellpadding="0">
          <tr align="center" valign="baseline">
            <td colspan="4"> 
            <div align="center">            
           <!-- <a href="javascript:submitForm('declartionDetails.do?method=SaveDeclartaionDetailData')"> -->
              <a href="javascript:Submit()">
             <img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></a>
            <a href="javascript:document.cpTcDetailsForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></a>
            <a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></a></div></td>
          </tr>
        </table>
    <td background="images/TableVerticalRightBG.gif">&nbsp;</td>
  
    <tr>
    <td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
    <td background="images/TableBackground2.gif"><div align="center"></div></td>
    <td align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
  </tr>

</html:form>
 

