<%@ page language="java"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.util.Date"%>
<%@ page import="com.cgtsi.actionform.ClaimActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="java.util.ArrayList"%>
<%@page import="com.cgtsi.claim.ClaimDetail"%>
<% session.setAttribute("CurrentPage","addFirstClaimsPageDetails.do?method=addFirstClaimsPageDetails");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>



<%
String focusField="nameOfOfficial";
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
    focusField="test";
}
 Date systemDate  = new Date();
 String sysDate = dateFormat.format(systemDate);
 ClaimActionForm claimForm = (ClaimActionForm)session.getAttribute("cpTcDetailsForm") ;
claimForm.setClaimSubmittedDate(sysDate);
%>
	<script type="text/javascript">
function openReasBox(field){
			var id = field;
			//alert('id:'+id);
			var target = findObj("view");
			var data = target.value;
			if(data != ''){
				//regex to set checkbox(s) for selected reasons.
			}
			var left = (screen.width/2)-(800/2);
			var top = (screen.height/2)-(400/2);
			//Here child window handler to get selected values.
			var myWin = window.open('jsp/NewreasonList.jsp','_blank','width=800,height=400,resizable=1,scrollbars=1,location=no,top='+top+'left='+left);			
			//pass id to child window.
			setInterval(function(){myTimer();},1000);
			function myTimer() {
    				
    				myWin.document.getElementById('test').value = id;
			}

			
				
		}


function setCPOthersEnabled()
{
	//alert("ggfgf4");

	var claimeligamt;
	var totsancamt=findObj("totGuaranteeAmt").value;
	//alert("totsancamt"+totsancamt);
var isnortheast=document.forms[0].isNorthEastRegion.value;

var isWomeno=document.forms[0].isWomenorNot.value;
	
 if(totsancamt > 0 &&   totsancamt <= 5000000 &&  isnortheast=='yes')
 {
	 claimeligamt=totsancamt*80/100;
	// alert("ggfgf1");
 }
 if(totsancamt > 0 &&   totsancamt <= 5000000 &&  isWomeno=='yes')
 {
	 claimeligamt=totsancamt*80/100;
	// alert("ggfgf2");
 }

 if(totsancamt > 0 &&   totsancamt <= 5000000 &&  isWomeno=='no' && isnortheast=='no' )
 {
	 claimeligamt=totsancamt*75/100;
	 //alert("ggfgf3");
 }

 if(totsancamt > 5000000)
 {
	 claimeligamt=totsancamt*50/100;
	 //alert("ggfgf5");
	 
 }

 //alert("claimeligamthh"+claimeligamt);
//	if(claimeligamt >0)
	//{
		//alert("ggfgf6");
	// document.getElementById("RAJU").disabled=false;
	//}
	//else
	//{
		//alert("ggfgf7");
	//	 document.getElementById("RAJU").disabled=true;
	//}
	 

	
}



function submitclaimform(action)
{

	//alert("ggfgf7");
	


	
	if(document.forms[0].nameOfOfficial.value=="" || document.forms[0].nameOfOfficial.value==null )
	{
		alert("please enter nameOfOfficial ");
		document.getElementById('nameOfOfficial').focus();

	}

	if(document.forms[0].designationOfOfficial.value=="" || document.forms[0].designationOfOfficial.value==null )
	{
		alert("please enter designationOfOfficial ");
		document.getElementById('designationOfOfficial').focus();

	}
	if(document.forms[0].place.value=="" || document.forms[0].place.value==null )
	{
		alert("please enter place ");
		document.getElementById('place').focus();

	}


	//alert("tststkkoo");

	//alert(document.forms[0].view.checked);


var totsancamt=findObj("totGuaranteeAmt").value;
	//alert("totsancamt"+totsancamt);
var isnortheast=document.forms[0].isNorthEastRegion.value;
//alert("isnortheast"+isnortheast);

var isWomeno=document.forms[0].isWomenorNot.value;
//alert("isWomeno"+isWomeno);

if(totsancamt > 0  &&   totsancamt <= 5000000 &&  isnortheast=='yes')
	//if(totsancamt > 500000 &&   totsancamt <= 5000000 &&  isnortheast=='yes')
 {
	 claimeligamt=totsancamt*80/100;
	// alert("ggfgf1");
 }



 if(totsancamt > 0 &&   totsancamt <= 5000000 &&  isWomeno=='yes')
	// if(totsancamt > 500000 &&   totsancamt <= 5000000 &&  isWomeno=='yes')
 {
	 claimeligamt=totsancamt*80/100;
	//alert("ggfgf2");
 }

 if(totsancamt > 0 &&   totsancamt <= 5000000 &&  isWomeno=='no' && isnortheast=='no' )
	// if(totsancamt > 500000 &&   totsancamt <= 5000000 &&  isWomeno=='no' && isnortheast=='no' )
 {
	 claimeligamt=totsancamt*75/100;
	 //alert("ggfgf3");
 }

 if(totsancamt > 5000000)
 {
	 claimeligamt=totsancamt*50/100;
	 // alert("ggfgf5");
	 
 }

// alert("claimeligamthh"+claimeligamt);

var id=document.forms[0].view.value
  //alert(id);




//if(claimeligamt >=2000000)
//{

	if(id==null || id=='')
	{
		alert("Please fill check list view");
		document.getElementById('view').focus();
	}
	//else
	//{
	
	
//}
//}

	


	//alert("gfgfgfgf");
	document.forms[0].action=action;
	document.forms[0].target="_self";
	document.forms[0].method="POST";
	document.forms[0].submit();

	//alert("gfgfgfgfend");


	
}


</script>

<html:form action="saveClaimApplication.do?method=saveClaimApplication" method="POST" focus="<%=focusField%>" enctype="multipart/form-data">
<html:errors/>
<body onload="setCPOthersEnabled()"/>
<html:hidden name="cpTcDetailsForm" property="test"/>


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
      		<A HREF="javascript:submitForm('helpDisclaimer.do')">
      	        HELP</A>
      </DIV>        
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" border="0"  cellpadding="0">
                <tr> 
                  <td colspan="8"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="35%" height="20" class="Heading">&nbsp;<bean:message key="declarationandundertakingbymli"/></td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                      </tr>                      
                      
                    </table></td>
                </tr>                                
                <tr> 
                  <td colspan="4" class="SubHeading"><br> &nbsp;<bean:message key="declaration"/></td>
                </tr>
                <tr> 
                  <td class="TableData" >&nbsp; <bean:message key="para1"/></td>
			
                </tr>
                <tr> 
                  <td class="TableData" >&nbsp;<bean:message key="para2"/></td>
			
                </tr>
                <tr> 
                  <td colspan="4" class="SubHeading"><br> &nbsp;<bean:message key="undertaking"/></td>
                </tr>
				<tr>
				<td class="TableData"> &nbsp;<bean:message key="undertaking1"/></td>
                </tr> 
				<tr>
				<td class="TableData"> &nbsp;<bean:message key="undertaking2"/></td>
                </tr> 
				<tr>
				<td class="TableData"> &nbsp;<bean:message key="undertaking3"/></td>
                </tr> 
				<tr>
				<td class="TableData"> &nbsp;<bean:message key="undertaking4"/></td>
                </tr> 
				</table></td>
			</tr>
			
                    <tr> 
                  <td><table width="100%" border="0" >
					<tr>
					<td  class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="nameofofficial"/></td>
					<td  colspan="5" class="TableData"><html:text property="nameOfOfficial" name="cpTcDetailsForm" maxlength="100"/></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="designationOfOfficial"/></td>
					<td colspan="5" class="TableData"><html:text property="designationOfOfficial" name="cpTcDetailsForm" maxlength="50"/></td>
					</tr>
					<tr>
					<td   class="ColumnBackground">&nbsp;<bean:message key="mliName"/></td>
					<td colspan="5" class="TableData"><bean:write property="memberDetails.memberBankName" name="cpTcDetailsForm"/>, <bean:write property="memberDetails.memberBranchName" name="cpTcDetailsForm"/></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font>Date Of Claim Intiation</td>
					<td colspan="5" class="TableData" align="center"><!--<html:text property="claimSubmittedDate" name="cpTcDetailsForm" maxlength="10"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.claimSubmittedDate')" align="center">--><bean:write property="claimSubmittedDate" name="cpTcDetailsForm"/></td>
					</tr>
					<tr>
					<td  class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="place"/></td>
					<td  colspan="5" class="TableData"><html:text property="place"  name="cpTcDetailsForm" maxlength="100"/></td>		
					
					<td><html:hidden property="totGuaranteeAmt"  name="cpTcDetailsForm" /></td>
					
					<td><html:hidden property="isNorthEastRegion"  name="cpTcDetailsForm" />
					
					<td><html:hidden property="isWomenorNot"  name="cpTcDetailsForm" /></td>					
					</tr>
			     <tr>
			                    
					  <% 
    ArrayList arraylist = null;
    String AsfStringArray[]=null;
    String size=(String)request.getAttribute("claimViewArraySize");
    
    if(size=="0")
    {
    out.println("<tr><td class=\"Heading\" colspan=\"11\"><center>No Data Found</center</td></tr>");
    }
    if(size!=null && size!="0")
    {
    arraylist=(ArrayList)request.getAttribute("claimViewArray");
   
    %> 
                       
              <tr> 
                  <td colspan="3" class="SubHeading"><br> &nbsp;CLAIM ACCOUNT DETAIL</td>
                </tr>      
                   
						 <td  class="ColumnBackground">
						<div align="center">MEMBER ID</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">MLI NAME</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">BENEFICIARY</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">ACCOUNT NUMBER</div>
						</td>
							 <td class="ColumnBackground">
						<div align="center">IFSC CODE</div>
						</td>
						 <td class="ColumnBackground" >
						<div align="center">CHECK</div>
						</td>
						
                      							
						</TR>     
						
   <% 
   
	ClaimActionForm claimDetails= new ClaimActionForm();
	for(int count=0;count<arraylist.size();count++)
    {
    
     int sum = 0;
       
      AsfStringArray=new String[6];
      AsfStringArray[0]="";
      AsfStringArray[1]="";
      AsfStringArray[2]="";
      AsfStringArray[3]="";
      AsfStringArray[4]="";
      AsfStringArray[5]="";
    //AsfStringArray=(String[])arraylist.get(count);
    claimDetails = (ClaimActionForm)arraylist.get(count);
    System.out.println("raaaassskkkk"+claimDetails.getCgpan());
 	%>
                <tr>
                  
                     <td class="ColumnBackground1" >&nbsp;
                  <div align="center"><%=claimDetails.getMliid()%></div>
                    
                   
                  </td>  
                  <td class="ColumnBackground1">&nbsp;
                 
                   <%=claimDetails.getMliname()%>
                
                  </td>
                  
                   <td class="ColumnBackground1">&nbsp;
                 
                   <%=claimDetails.getMembenificiary()%>
                
                  </td>
                  
                  
                   <td class="ColumnBackground1" >&nbsp;
                 
                   <%=claimDetails.getMemaccountno()%>
                
                  </td>
                   <td class="ColumnBackground1" >&nbsp;
                 
                   <%=claimDetails.getMemrtgsno()%>
                
                  </td>
                  <td class="ColumnBackground1" >&nbsp;
                  <input type="checkbox"  name="checkbox">                 
                
                  </td>
                
                <%  } %>
                
         <% }%>
         </tr>
                 </td>									
			     </tr>
			      <tr> 
                  <td colspan="5" class="SubHeading"><br> &nbsp;CHECK LIST</td>
                </tr>
                  <tr>
					<td  colspan="1"  align="right">
					Checklist to be mandatorily filled for this claim </td><td colspan="4"> <input  type="button" align="left"  id="RAJU"  onclick="openReasBox(this.id);" value="Please press here for filling the checklist "  />
					<html:hidden property="view" name="cpTcDetailsForm" />
					</td>
                  </tr>
					<tr>
					<td><br><br></td>
					</tr>
				<tr>
				<td class="SubHeading"> &nbsp;<bean:message key="cgtsinote1"/></td>
                </tr> 
				<tr>
				<td class="SubHeading"> &nbsp;<bean:message key="cgtsinote2"/></td>
                </tr> 
              </table>
            </td>
        </tr>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 

          <div align="center">          
         <A href="javascript:submitclaimform('saveClaimApplication.do?method=saveClaimApplication')"><img src="images/Save.gif" alt="Accept" width="49" height="37" border="0"></a>
          <!--   <A href="javascript:submitForm('saveClaimApplication.do?method=saveClaimApplication')"><img src="images/Save.gif" alt="Accept" width="49" height="37" border="0"></a>-->
          <a href="javascript:document.form1.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></a>
          <a href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></a><div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
      
  </tr>
</table>
</html:form>
</body>
</html>
