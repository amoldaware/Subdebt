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
<% session.setAttribute("CurrentPage","RecoveryAfterOTS.do?method=getRecoveryDetailData");%>

<!--<body onload="processForwardedToFirst(),processForwardedToSecond()"> -->

 <SCRIPT LANGUAGE="JavaScript">
      function checkFinal(tableID)
{
 //alert(document.forms[0].booleanFinalRecovery[0].checked);
//alert(document.forms[0].isRecoveryInitiated[1].checked);

	if(document.forms[0].booleanFinalRecovery[0].checked)
	{
//totalNetRecovery	
var tot1=document.forms[0].totalNetRecovery.value;
var tot2= document.forms[0].netRecovery.value;
var firstInstll=document.forms[0].firstinstalmentrelease.value;
 //  alert ("The First Installment Amount is :--->"+firstInstll);
   var tot=parseFloat(tot1)+parseFloat(tot2);
//alert (""+document.forms[0].totalNetRecovery.value);
//alert (""+document.forms[0].netRecovery.value);
//alert ("The Total is :-->"+tot);

     if (parseFloat(firstInstll) <= parseFloat(tot))
     {
     //alert ("Enalbe Lower Part value ...!");
      document.forms[0].closerrequest.disabled=false;
			document.forms[0].ltr_ref.disabled=false;
      
//       var table = document.getElementById(tableID);  
//               var rowCount = table.rows.length;  
               //before save code needed.
//                 document.forms[0].target ="_self";
//                 document.forms[0].method="POST";
//                 document.forms[0].action="/cgtsi-ViewController-context-root/RecoveryAfterOTS.do?method=SaveClaimwithclosure";
//                 document.forms[0].submit();
      
     }
     else{
     if (farwar())
     {
    // alert ("farward Request");
    if (validata(tableID)){
     document.forms[0].target ="_self";
        document.forms[0].method="POST";
        //document.forms[0].action="/cgtsi-ViewController-context-root/RecoveryAfterOTS.do?method=forwardtoOTSAfterRecovery";
       document.forms[0].action="/cgtsi-ViewController-context-root/RecoveryAfterOTS.do?method=forwardtoOTSAfterRecovery";
        //RecoveryAfterOTS.do?method=getRecoveryDetail
        //+cp_ots_enterMember=
        document.forms[0].submit();
        }
     }else {
     document.forms[0].booleanFinalRecovery[0].checked=false ;
     
     alert (" Save with No final recovery option !  ");
//               var table = document.getElementById(tableID);  
//               var rowCount = table.rows.length;  
//               //before save code needed.
//                 document.forms[0].target ="_self";
//                 document.forms[0].method="POST";
//                 document.forms[0].action="/cgtsi-ViewController-context-root/RecoveryAfterOTS.do?method=SaveClaimrecovryData&rowcount="+rowCount;
//                 document.forms[0].submit();
     
     }
     //return confirm("Do you really want this ");
       
     }//else close

  }//if close
   	if(!document.forms[0].booleanFinalRecovery[0].checked)
	{
    document.forms[0].closerrequest.disabled=true;
			document.forms[0].ltr_ref.disabled=true;
  }
  
  }//function close
 function farwar()
  {
  return confirm("Do You Want To  Go For OTS. ");
  }
  
  
   function isDateValid(thestring)
{
	if(thestring && thestring.length)
	{
		
		for (i = 0; i < thestring.length; i++) {
			ch = thestring.substring(i, i+1);
			if ((ch < "0" || ch > "9")  && ch!="/")
			  {
			  //alert("The numbers may contain digits 0 thru 9 only!");
			  
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
   
  
       
       function calnetpay()
       {
  //alert ("inside calnetpay !"+document.forms[0].booleanExpInc.value);
//     alert ("inside calnetpay !"+document.forms[0].expIncforRecovery.value);
     if ('N' == document.forms[0].booleanExpInc.value)
         {
     document.forms[0].netRecovery.value=document.forms[0].amtRecipt.value - document.forms[0].expIncforRecovery.value;
     }
   if ('Y' == document.forms[0].booleanExpInc.value)
     {
      document.forms[0].netRecovery.value=document.forms[0].amtRecipt.value;
       }
       }
      function chkvalue(){
      if ('N' == document.forms[0].booleanExpInc.value)
     {
     document.forms[0].netRecovery.value=document.forms[0].amtRecipt.value - document.forms[0].expIncforRecovery.value;
     }
     if ('Y' == document.forms[0].booleanExpInc.value){
      document.forms[0].netRecovery.value=document.forms[0].amtRecipt.value;
       }
      }
       
      function pasvalue()
      {
     
      document.forms[0].netRecovery.value=document.forms[0].amtRecipt.value;
      }
        function Submit(tableID)
      {
      var tot1=document.forms[0].totalNetRecovery.value;
      var tot2= document.forms[0].netRecovery.value;
      var firstInstll=document.forms[0].firstinstalmentrelease.value;

       var tot=parseFloat(tot1)+parseFloat(tot2);
               if (!validata(tableID))
                {
//                   document.forms[0].target ="_self";
//                 document.forms[0].method="POST";
//                 document.forms[0].action="/cgtsi-ViewController-context-root/RecoveryAfterOTS.do?method=getRecoveryDetailData";
//                 document.forms[0].submit();
                }
//               code for msg 

               else if (parseFloat(firstInstll) <= parseFloat(tot))
                {
                      if (chkConOtstandgreater())
                      {
                       var table = document.getElementById(tableID);  
                     var rowCount = table.rows.length;  
               //before save code needed.
                 document.forms[0].target ="_self";
                 document.forms[0].method="POST";
                 document.forms[0].action="/cgtsi-ViewController-context-root/RecoveryAfterOTS.do?method=SaveClaimrecovryData&rowcount="+rowCount;
                 document.forms[0].submit();
                      }
                 }
             

              else{
               var table = document.getElementById(tableID);  
               var rowCount = table.rows.length;  
               //before save code needed.
                 document.forms[0].target ="_self";
                 document.forms[0].method="POST";
                 document.forms[0].action="/cgtsi-ViewController-context-root/RecoveryAfterOTS.do?method=SaveClaimrecovryData&rowcount="+rowCount;
                 document.forms[0].submit();
      
                }
      }
       function chkConOtstandgreater()
              {
             var tot1=document.forms[0].totalNetRecovery.value;
             var tot2= document.forms[0].netRecovery.value;
             var firstInstll=document.forms[0].firstinstalmentrelease.value;
             var tot=parseFloat(tot1)+parseFloat(tot2);
              var accessRec=parseFloat(tot)-parseFloat(firstInstll);              
            return confirm("The Net Recovery is greater than Outstanding do you want to proced ! Excess Recovery ="+accessRec );
              
              }
       function validata(tableID)
              {
               var table = document.getElementById(tableID);  
               var rowCount = table.rows.length;  
               var len=rowCount-1;
             
                 for (var i=0;i<rowCount;i++)
                {
                 var row = table.rows[len]; 
			       var T1 = row.cells[0].childNodes[0].value;
             var T2 = row.cells[1].childNodes[0].value;
			       var T3 = row.cells[2].childNodes[0].value;
			       var T4 = row.cells[3].childNodes[0].value;
			       var T5 = row.cells[4].childNodes[0].value;
			       var T6 = row.cells[5].childNodes[0].value;
			       var T7 = row.cells[6].childNodes[0].value;
			       var T8 = row.cells[7].childNodes[0].value;
              var T9 = row.cells[8].childNodes[0].value;
               // var datefield = T1;
              
              
               if ( T2!="" ) {
               
//             var thetime=new Date();
//             var date = thetime.getDate() + "/" + thetime.getMonth() + "/" + thetime.getYear();
//
//               var dat=thetime.setMonth(thetime - 6));
//               alert ("the date is :--->"+dat);
               
                if (!chkdate(T2)) {
                alert("The reciept date is invalid.");
                return false;
                }
                else if (!corrdate(T2))
                {
                    alert("Please Enter Date Of  Reciept less than or Equal to Current Date..");               
                    return false;
                }
                }//if close
            else {
            alert("Please Enter Date Of  Reciept");
            return false;
            }
            if ( T3 =="" ) {
            alert("Please Enter Amount Reciept .");
            return false;
               }
               if ( T4 =="" ) {
            alert("Please Enter Expenses incurred for Recovery . ");
            return false;
               }
                if ( T7 =="" ) {
            alert("Please Enter DD Number . ");
            return false;
               }
                if ( parseInt(T3,10) < parseInt(T4,10))
					{
			               alert("Amount Recipt should be greater then Expenses incurred For Recovery . ");
                  return false;
			         }

               
               if ( T8!="" ) {
                if (!chkdate(T2)) {
                alert("The DD date is invalid.");
                return false;
                }
                else if (!corrdate(T8))
                {
                    alert("Please Enter DD Date less than or Equal to Current Date..");               
                    return false;
                }
                }//if close
            else {
            alert("Please Enter DD Date ");
            return false;
            }
         
            }//for
         
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
            strMonthArray[10] = "11";
            strMonthArray[11] = "12";
            
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
            
           intday = parseInt(strDay,10);
           
            if (isNaN(intday)) {
             
            err = 2;
            return false;
            }
            intMonth = parseInt(strMonth,10);
            
            if (isNaN(intMonth)) {
             alert ("the date is :---> 2");
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
               }//for 
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
            if ((intMonth == 04 || intMonth == 6 || intMonth == 9 || intMonth == 11) && (intday > 30 || intday < 1)) {
            err = 7;
            
            return false;
            }
            if (intMonth == 02) {
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

<html:form action="RecoveryAfterOTS.do?method=SaveDeclartaionDetailData" method="POST" enctype="multipart/form-data">
  <table id="mainTable" width="100%" border="0" cellspacing="0" cellpadding="0">
   
    <tr>
      <td class="FontStyle">&nbsp;</td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif"></td>
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
                    <td width="35%" class="Heading">&nbsp;Recovery From MLI
                                                    </td>
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
                    </td>
              </tr>
             
             
              <tr>
                <td colspan="4">
                 
                  <table width="100%" border="0" cellspacing="1"
                         cellpadding="0">
       
                    <tr class="TableData">
		            
		                    
		                      <td valign="middle" class="HeadingBg">
		                        <div align="center">
		                          <strong>&nbsp;Member Id<br/>
		                             </strong>
		                        </div>
		                      </td>   
		                    
		                      <td >
                                      <div align="center">
                                       <bean:write name="cpRecoveryOTS" property="mliId" scope="session"/>
                                       </div>
                                       </td>
		                      
		                      <td class="HeadingBg">
		                        <div align="center">
		                          CGPAN NO.
		                        </div>
		                      </td>
		                      
		                      <td>
                                      <div align="center">
                                      
                                     <bean:write name="cpRecoveryOTS" property="enterCgpan" scope="session"/>
                                      <%//=session.getAttribute("cgpan").toString()%>
		                   
                                      </div>
		                      </td>
      				                 
      				</tr>
       
                    <tr class="TableData">  
			                      		<td class="HeadingBg">
				                       	 <div align="center">
                                                        <strong>
			                          	 MLI Name &nbsp;
				                        </strong>
                                                         </div>
				                        </td>
			                          
			                           <td>
                                                      <div align="center">
                                                         <bean:write name="cpRecoveryOTS" property="mliName" scope="session"/>
                                                        </div>
			                           </td>
			                           
				                      <td class="HeadingBg">
				                        <div align="center">
				                          <strong>Unit Name</strong><br/>
				                        </div>
				                      </td>
				                      
				                       <td>
				                        <div align="center">
                                                       <bean:write name="cpRecoveryOTS" property="unitName" scope="session"/>
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
			                          	 Zone &nbsp;
				                        </strong>
                                                         </div>
				                        </td>
                                                        <td>
                                                        <div align="center">
                                                       <bean:write name="cpRecoveryOTS" property="placeforClmRecovery" scope="session"/>
                                                        </div>
                                                        </td>
			                           
				                      <td class="HeadingBg">
				                        <div align="center">
				                          <strong>Claim Eligible Amt.</strong><br/>
				                        </div>
				                      </td>
				                      
				                       <td>
				                        <div align="center">
                                                        <bean:write name="cpRecoveryOTS" property="clmEligibleAmt" scope="session"/>
                                                        </div>
				                      </td>
			        </tr>
                                 <tr class="TableData">  
                                   <td class="HeadingBg" >
                                         <div align="center">
                                        <strong>
                                         First Installment Released (Amt) &nbsp;
                                        </strong>
                                         </div>
                                        </td>
                                        <td>
                                        <div align="center" >
                                       <bean:write name="cpRecoveryOTS" property="firstinstalmentrelease" scope="session"/>
                                       <html:hidden  property="firstinstalmentrelease"   alt="Reference" name="cpRecoveryOTS" />
                                        </div>
                                        </td>
                                        <td class="HeadingBg">
                                        <div align="center" >
                                        <strong>
                                        Date Of settlement
                                        </strong>
                                        </div>
                                        </td>
                                        <td>
                                        <div align="center" >
                                       <bean:write name="cpRecoveryOTS" property="firstInsatllDate" scope="session"/> 
                                        </div>
                                        </td>
                                        
                                 </tr>
                 </table>
                 <table width="100%"  border="0" cellspacing="1" cellpadding="0" id="dataTable">
                 <tr>
                <td colspan="8" class="SubHeading">
                  &nbsp;
                 Recovery After First Installment Release
              <!-- <INPUT type="button" value="Add Row" onclick="addRow('dataTable')" /> -->
                </td>
              </tr>
              
              <tr class="TableData">  
                      <td class="HeadingBg" width="11%" >
                            <div align="center">
                              <strong>CGPAN</strong><br/>
                            </div>
                          </td>
                            
                          <td class="HeadingBg" width="11%" >
                            <div align="center">
                              <strong>Date of Receipt</strong><br/>
                            </div>
                          </td>
                           <td class="HeadingBg" width="13%" >
                            <div align="center">
                              <strong>Amt Received</strong><br/>
                            </div>
                          </td>
                          <td class="HeadingBg" width="13%">
                            <div align="center">
                              <strong>Expenses incurred for Recovery</strong><br/>
                            </div>
                          </td>
                          <td class="HeadingBg" width="15%">
                            <div align="center">
                              <strong>Expenses Incurred for Recovery Deducted by MLI.</strong><br/>
                            </div>
                          </td>
                          <td class="HeadingBg" width="13%">
                            <div align="center">
                              <strong>Net Recovery</strong><br/>
                            </div>
                          </td>
                          <td class="HeadingBg" width="11%">
                            <div align="center">
                              <strong>Instrument No.</strong><br/>
                            </div>
                          </td>
                          <td class="HeadingBg" width="11%">
                            <div align="center">
                              <strong>Instrument Date</strong><br/>
                            </div>
                          </td>
                         <td class="HeadingBg" width="13%">
                            <div align="center">
                              <strong>Remarks</strong><br/>
                            </div>
                          </td>
            </tr>         
           
           
            <logic:iterate id="object" name="cpRecoveryOTS" property="viewRecArr" indexId="index">
            <%ClaimActionForm claimdeclarion=(ClaimActionForm)object; %>
           
            <tr class="TableData">  
                      <td>
                            <div align="center">
                              <strong>
                               
                               <%=claimdeclarion.getEnterCgpan()%>
                              </strong><br/>
                            </div>
                          </td>
                            
                          <td>
                            <div align="center">
                              <strong>
                               <%           java.util.Date recptdate=(java.util.Date)claimdeclarion.getDateOfreciept();
                                            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                            String recDate = formatter.format(recptdate);
                                            %>
                              <%=recDate%>
                              </strong><br/>
                            </div>
                          </td>
                           
                         
                           <td>
                            <div align="center">
                              <strong>
                              
                              <%=claimdeclarion.getAmtRecipt()%>
                              </strong><br/>
                            </div>
                          </td>
                           
                           <td>
                            <div align="center">
                              <strong><%=claimdeclarion.getExpIncforRecovery()%></strong><br/>
                            </div>
                          </td>
                           <td>
                            <div align="center">
                              <strong><%=claimdeclarion.getExpDeducted()%></strong><br/>
                            </div>
                          </td>
                           <td>
                            <div align="center">
                              <strong><%=claimdeclarion.getNetRecovery()%></strong><br/>
                            </div>
                          </td>
                           <td>
                            <div align="center">
                              <strong><%=claimdeclarion.getDdNo()%></strong><br/>
                            </div>
                          </td>
                           <td>
                            <div align="center">
                              <strong> <%        
                              java.util.Date dddate=(java.util.Date)claimdeclarion.getDdDate();
                                           // DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
                                            String recddDate = formatter.format(dddate);
                                            %>
                                            <%=recddDate%>
                                             <% //=recDate%>
                                            </strong><br/>
                            </div>
                          </td>
                           <td>
                            <div align="center">
                              <strong><%=claimdeclarion.getRemark()%></strong><br/>
                            </div>
                          </td>
                           
            </tr> 
             </logic:iterate>
             <tr class="TableData">
                 <td class="HeadingBg" width="11%">
                           <div align="center">
                              <strong>Total Net Outstanding</strong><br/>
                            </div>
                  </td>
                  <td>
                  <div align="center">
                    
                  </div>
                  </td>
                  <td>
                  <div align="center">
                    
                  </div>
                  </td>
                  <td>
                  <div align="center">
                    
                  </div>
                  </td>
                  <td>
                  <div align="center">
                    
                  </div>
                  </td>
                  <td>
                  <div align="center" style="color:rgb(255,0,0);">
                    <strong>
                   <html:text property="totalNetRecovery" size="10" maxlength="10" alt="Reference" name="cpRecoveryOTS" readonly="true"/>  
                  
                 </strong>
                  </div>
                  </td>
                  <td>
                  <div align="center">
                    
                  </div>
                  </td>
                  <td>
                  <div align="center">
                    
                  </div>
                  </td>
                  <td>
                  <div align="center">
                    
                  </div>
                  </td>
             </tr>
             
             
           
              
                 
              
		 
                 <%for (int k=1;k<2;k++){%>
                 <tr class="TableData">  
                 <td width="10%">
                 <bean:write name="cpRecoveryOTS" property="enterCgpan" scope="session"/>        
                 </td>
                         <td width="10%" >
                          
                            <html:text property="dateOfreciept" size="10" maxlength="10" alt="Reference" name="cpRecoveryOTS"/>
                            <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpRecoveryOTS.dateOfreciept')" align="center">
                            
                          </td>
                           <td >
                          
                              <html:text property="amtRecipt" size="13" maxlength="10" alt="Reference" name="cpRecoveryOTS"   onkeyup="isValidNumber(this)" onkeypress="return numbersOnly(this, event)" onblur="pasvalue()"/>
                             
                          </td>
                            <td >
                          
                           <html:text property="expIncforRecovery" size="13" maxlength="10" alt="Reference" name="cpRecoveryOTS"  onkeyup="isValidNumber(this)" onkeypress="return numbersOnly(this, event)" onblur="chkvalue()"/>
                            
                          </td>
                            
                            <td  >
                             <html:select property="booleanExpInc" onchange="calnetpay()" >
                            <html:option value="Y">yes</html:option>
                            <html:option value="N">No</html:option>
                            </html:select> 
                           
                           </td>
                           
                            <td >
                           
                           <html:text property="netRecovery" size="13" maxlength="10" alt="Reference" name="cpRecoveryOTS"  readonly="true"/>
                             
                          </td>
                          
                            <td >
                        
                            
                             <html:text property="ddNo" size="11" maxlength="6" alt="Reference" name="cpRecoveryOTS"  onkeyup="isValidNumber(this)" onkeypress="return numbersOnly(this, event)"/>
                            
                          </td>
                            <td >
                       <html:text property="ddDate" size="11" maxlength="10" alt="Reference" name="cpRecoveryOTS"/>
                        <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpRecoveryOTS.ddDate')" align="center">
                          </td>
                            <td >
                          
                             <html:text property="remark" size="13" maxlength="30" alt="Reference" name="cpRecoveryOTS"/>
                             
                          </td>
                          
                 </tr> 
                 
                 <%}%>
                 </table>
                 <table width="100%" border="0" cellspacing="1" cellpadding="0">
                 <tr class="TableData">
                 <td class="HeadingBg" width="50%">
                 <div align="center">
                <strong>
                 Final  Recovery. &nbsp;
                </strong>
                 </div>
                </td>
                 
                 <td width="50%">
                  <div align="center">
                  <!-- <html:checkbox property="booleanFinalRecovery" name="cpRecoveryOTS" value="true" onchange="chkmethod()"/> -->
                  
                  <html:radio value="true" name="cpRecoveryOTS" property="booleanFinalRecovery" 	onclick="checkFinal('dataTable')"/><bean:message key="yes" />
									  <html:radio value="false" name="cpRecoveryOTS" property="booleanFinalRecovery"   onclick="checkFinal('dataTable')" /><bean:message key="no"/>
                   <!--<input  type="CHECKBOX" name="booleanFinalRecovery"  value="F" onchange="chkmethod()" /> -->
                  </div>
                 </td>
                 </tr>
                 </tablet>
                 
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                 <tr class="TableData">
                 <td class="HeadingBg" width="50%">
                 <div align="center">
                <strong>
                 MLI requested for case Closure
                </strong>
                 </div>
                </td>
                 
                 <td width="50%">
                  <div align="center">
                  <html:checkbox property="closerrequest" name="cpRecoveryOTS" value="true"  disabled="true"/> 
                  <!-- <input  type="CHECKBOX" name="closerrequest"  value="F" disabled="true" /> -->
                   
                  </div>
                 </td>
                 </tr>
                 
                 <tr class="TableData">
                 <td class="HeadingBg" width="50%">
                  <div align="center">
                   <strong>
                    Ltr Ref No & Date 
                   </strong>
                   </div>
                  </td>
                  <td>
                   <div align="center">
                   <!-- <html:textarea property="ltr_ref" cols="75" disabled="true" name="cpRecoveryOTS" rows="3" /> -->
                   <html:textarea property="ltr_ref" name="cpRecoveryOTS" cols="75"  rows="4"  disabled="true" />
                    </div>
                  </td>
                 </tr>
                 </td>
                 </table>
              </tr>
            
                       
         
          </table>
               
          <table width="100%" border="0" cellspacing="1" cellpadding="0">
          <tr align="center" valign="baseline">
            <td colspan="4"> 
            <div align="center">  
                       <!-- <a href="javascript:submitForm('declartionDetails.do?method=SaveDeclartaionDetailData')"> -->
              <a href="javascript:Submit('dataTable')">
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


