<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>

<%@page import="com.cgtsi.actionform.NPADateModificationActionForm"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>


<%! Iterator itr=null;
String cgpan,NPAformType;
%>
<%
//session.setAttribute("CurrentPage","showMemberForViewCgpan.do?method=showMemberForViewCgpan");
System.out.println(" cgpan "+request.getAttribute("cgpan"));
cgpan=(String)request.getAttribute("cgpan");
NPAformType=(String)request.getAttribute("formTypeforJsp");
System.out.println(" NPAformType "+NPAformType);
Object o= request.getAttribute("NPAUpgradationDetails");
System.out.println("O value "+o);
if(o!=null)
{
ArrayList<NPADateModificationActionForm> arrayListNPADateModificationActionForm= (ArrayList)o;

itr=arrayListNPADateModificationActionForm.iterator();  
}
 
%>
<script type="text/javascript">
function compareDates(date1, date2) {


	 return new Date(date1) >= new Date(date2);
	// return new Date(startdate) >= new Date(enddate);
}

function compareDates1(date1, date2) {


	var tempstartdate = new Array();
	 tempstartdate = document.getElementById("lastNpaReportedDate").value.split("/");

	 var tempenddate = new Array();
	 tempenddate = document.getElementById("npaupgradationDate").value.split("/");
	 
	 var startdate = new Date(tempstartdate[2],tempstartdate[1],tempstartdate[0]);
	 var enddate = new Date(tempenddate[2],tempenddate[1],tempenddate[0]);
	
	 //return new Date(date1) >= new Date(date2);
	 return new Date(startdate) >= new Date(enddate);
}



function saveNPAUpgradationDetailsNew()
{

	var xmlhttp;
    if (window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
    }
    
    xmlhttp.open("POST", "CheckNPAUpgradationDateValidation.do?method=CheckNPAUpgradationDateValidation&cgpan="+document.getElementById("strCgpan").value+"&LastNPAReportingDate="+document.getElementById("lastNpaReportedDate").value+"&NPAUpgradationDate="+document.getElementById("npaupgradationDate").value, true);		    
	xmlhttp.onreadystatechange = function() {
    if (xmlhttp.readyState == 4) 
	{
            if (xmlhttp.status == 200)
            {
            	  var temp;
	              temp =xmlhttp.responseText;	         	     
	              if(temp.length==4)
	              {	            	
	            	  document.npaActionForm.submit();	
		          }
	              else
	              {	
	            	
							alert(temp);		            	 
	              }
            }
            else
            {
                alert('Something is wrong !!');
            }
        }
    };
    xmlhttp.send(null);
  
}


function saveNPAUpgradationDetails()
{
	
	var now = new Date();
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!

    var yyyy = today.getFullYear();
	if(dd<10){
	        dd='0'+dd;
	} 
	if(mm<10){
	        mm='0'+mm;
	} 
	var today = dd+'/'+mm+'/'+yyyy;
	//alert('saveNPAUpgradationDetails 1 ='+document.getElementById("NPAformType").value);	
	if(document.getElementById("NPAformType").value=="upgradation")
	{		

	//	alert(document.getElementById("lastNpaReportedDate").value);
		if(document.getElementById("npaupgradationDate").value!="")
		{
			//alert('saveNPAUpgradationDetails npaupgradationDate not blank');
			//alert(compareDates(document.getElementById("lastNpaReportedDate").value, document.getElementById("npaupgradationDate").value));
			if(compareDates(document.getElementById("npaupgradationDate").value, today)==true)
			{				
				if(document.getElementById("npaupgradationDate").value == today)
				{	
					
					if(compareDates1(document.getElementById("lastNpaReportedDate").value, document.getElementById("npaupgradationDate").value)==false)
					{				
						document.npaActionForm.submit();	
						//alert('saved -1');
					}		
					else
					{
						alert('NPA-Upgradation date should not be less than or equal to Account turned NPA date['+document.getElementById("lastNpaReportedDate").value+'].');
					}	
				}
				else
				{
					alert('NPA-Upgradation date should not be greater than current date.');					
				}			
			}
			else
			{
				//alert('date1'+document.getElementById("lastNpaReportedDate").value+'date2'+document.getElementById("npaupgradationDate").value);
			//	alert(compareDates(document.getElementById("lastNpaReportedDate").value, document.getElementById("npaupgradationDate").value));
				
				if(compareDates1(document.getElementById("lastNpaReportedDate").value, document.getElementById("npaupgradationDate").value)==false)
				{				
					document.npaActionForm.submit();
					//alert('saved -2');	
				}		
				else
				{
					/* var tempstartdate = new Array();
					 tempstartdate = document.getElementById("lastNpaReportedDate").value.split("/");

					 var tempenddate = new Array();
					 tempenddate = document.getElementById("npaupgradationDate").value.split("/");
					 
					 var startdate = new Date(tempstartdate[2],tempstartdate[1],tempstartdate[0]);
					 var enddate = new Date(tempenddate[2],tempenddate[1],tempenddate[0]);

						alert('2='+tempenddate[2]);
						alert('1='+tempenddate[1]);
						alert('0='+tempenddate[0]);
					 if(startdate > enddate)
					 {
						 alert('startdate > enddate');
					 }
					 else
					 {
						 alert('startdate < enddate');
					 }

					 if(startdate == enddate)
					 {
						 alert('startdate == enddate');
					 }*/
					alert('NPA-Upgradation date should not be less than or equal to Account turned NPA date['+document.getElementById("lastNpaReportedDate").value+'].');
				}
			}		
			
		}
		else
		{		
			//inlineMsg('dtOfFilingLegalSuit','You must enter NPA up-gradation Date .',2);
			alert('You must enter NPA up-gradation Date .');
		}
		
	}
	else if(document.getElementById("NPAformType").value=="modification")
	{
		var flag=true;
				
		if(document.getElementById("npaupgradationDate").value=="")
		{
			//inlineMsg('dtOfFilingLegalSuit','NPA up-gradation Date is mandatory.',2);
			alert('NPA up-gradation Date is mandatory.');
			flag=false;
		}
		else
		{
			if(compareDates(document.getElementById("npaupgradationDate").value, today)==true)
			{
			
				if(document.getElementById("npaupgradationDate").value == today)
				{
				}
				else
				{
					//inlineMsg('dtOfFilingLegalSuit','NPA date not equal to  current date.',2);
					alert('NPA-Upgradation date should not be greater than current date.');
					flag=false;
				}
				
				
			}
		}
		
		if(document.getElementById("newNpaDate").value=="" && flag==true)
		{
			//inlineMsg('effortsConclusionDate','New-NPA Date is mandatory.',2);
			alert('New-NPA Date is mandatory.');
			flag=false;
		}
		
		if(flag==true)
		{
   
			var upgradationDate =document.getElementById("npaupgradationDate").value;
			var upgradationDateYear = upgradationDate.substr(6, 10);
			var upgradationDateMonth = upgradationDate.substr(3, 2);
			var upgradationDateDay = upgradationDate.substr(0, 2);
			
			var newNPADate =document.getElementById("newNpaDate").value;
			var newNPADateYear = newNPADate.substr(6, 10);
			var newNPADateMonth = newNPADate.substr(3, 2);
			var newNPADateDay = newNPADate.substr(0, 2);


			var a = new Date(newNPADateYear, newNPADateMonth, newNPADateDay);
			var b = new Date(upgradationDateYear, upgradationDateMonth, upgradationDateDay);

			var c = a - b;
			var days = (a - b) / (60 * 60 * 24 * 1000);

			if(days <90)
			{
				//inlineMsg('effortsConclusionDate','New NPA date should be after 3 months of NPA-Upgradation date.',2);
				alert('New NPA date should be after 3 months of NPA-Upgradation date.');
			}
			else
			{
			  	var xmlhttp;
			    if (window.XMLHttpRequest){
			        xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
			    } else {
			        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
			    }
			    
			    xmlhttp.open("GET", "checkNewNPADtWithExpiryDtAction.do?method=checkNewNPADtWithExpiryDtMethod&cgpan="+document.getElementById("strCgpan").value+"&newNPADate="+document.getElementById("newNpaDate").value, true);		    
			    xmlhttp.onreadystatechange = function() {
			    if (xmlhttp.readyState == 4) 
				{
			            if (xmlhttp.status == 200)
			            {
			            	 var temp = new Array();
				             temp =xmlhttp.responseText;
				             if(temp=='')
				             {
				            	 saveData(document.getElementById("strCgpan").value,document.getElementById("npaupgradationDate").value,document.getElementById("remark").value,document.getElementById("newNpaDate").value);
				            	/* var xmlhttp;
				 			     if (window.XMLHttpRequest){
				 			        xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
				 			     } else {
				 			        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
				 			     }
				            	 xmlhttp.open("GET", "npaDateUpgradationAction.do?method=saveNPAUpgradationDetails&cgpan="+document.getElementById("strCgpan").value+"&npaUpgradationDate="+document.getElementById("dtOfFilingLegalSuit").value+"&remark="+document.getElementById("remark").value+"&NPAformType=modification&NewNPAEffectiveDate="+document.getElementById("effortsConclusionDate").value, true);
								 xmlhttp.send(null);		*/			             
				             }
				             else
				             {
				            	// inlineMsg('effortsConclusionDate',temp,2);
				            	 alert(temp);
				             }
				             
			            }
			            else
			            {
			                alert('Something is wrong !!');
			            }
			        }
			    };
			     
			    xmlhttp.send(null);
			    
			    
				/* 
				  
				var xmlhttp;
				 if (window.XMLHttpRequest)
				 {
					 	xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
				 } else {
					    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
				 }
				 xmlhttp.open("GET", "npaDateUpgradationAction.do?method=saveNPAUpgradationDetails&cgpan="+document.getElementById("strCgpan").value+"&npaUpgradationDate="+document.getElementById("dtOfFilingLegalSuit").value+"&remark="+document.getElementById("remark").value+"&NPAformType=modification&NewNPAEffectiveDate="+document.getElementById("effortsConclusionDate").value, true);
				 xmlhttp.send(null); */
				
			}
		}
	}
	
		
}

function saveData(cgpan,npaUpgradationDate,remark,NewNPAEffectiveDate)
{

	 /*var xmlhttp;
	 if (window.XMLHttpRequest)
	 {
		 	xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
	 } else {
		    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
	 }
	 xmlhttp.open("GET", "npaDateUpgradationAction.do?method=saveNPAUpgradationDetails&cgpan="+cgpan+"&npaUpgradationDate="+npaUpgradationDate+"&remark="+remark+"&NPAformType=modification&NewNPAEffectiveDate="+NewNPAEffectiveDate, true);
	 xmlhttp.send(null);*/

	document.npaActionForm.submit();
	
	 window.location.href = 'jsp/Success.jsp?message=NPA Modification Details Saved Successfully';
}


</script>



 <TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
		
	
	

	<html:form action="npaDateUpgradationAction.do?method=saveNPAUpgradationDetails" method="POST" focus="memberId"   >
	<HEAD>
		
		<LINK href="<%=request.getContextPath()%>/css/messages.css" rel="stylesheet" type="text/css">
		
		
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/CGTSI.js"></script>
	</HEAD>
	<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<table width="661" border="0" cellspacing="1" cellpadding="0">
							 <TR>
								<TD colspan="7">
									<TABLE width="100%" border="1" cellpadding="0" cellspacing="0">
										<TR>
										<% if(request.getAttribute("formTypeforJsp").equals("modification")) {%>
											<TD colspan="7" width="31%" class="Heading">NPA Upgradation Details</TD>
										<%} else { %>
										<TD colspan="7" width="31%" class="Heading">NPA Upgradation Details</TD>
										<%}  %>
										</TR>
										<tr>
											<td colspan="7" width="31%" class="Heading" ><html:messages id="message" message="true"></html:messages></td>
										</tr>
										<tr>
											<th class="ColumnBackground" >UNIT NAME</th>
											<th class="ColumnBackground" >BANK NAME</th>
											<th class="ColumnBackground" >CGPAN</th>
											<th class="ColumnBackground" >MLI-ID</th>
											<th class="ColumnBackground" >APPLICATION STATUS</th>
											<th class="ColumnBackground" >DATE ON WHICH ACCOUNT TURNED NPA</th>
											<th class="ColumnBackground" >REASON FOR A/C TURNING NPA</th>
<!--											<th class="ColumnBackground" >NPA ID</th>-->
										</tr>
										<% while(itr.hasNext()){ 
										NPADateModificationActionForm st=(NPADateModificationActionForm)itr.next();  
    									System.out.println("Bank Name in jsp"+st.getLstNpaDate()); 
    									String color="";
    									if(cgpan.equals(st.getCgPan()))
    									{
    										color="background-color:#87CEEB";
    									}
    									else
    									{
    										color="";
    									}
    									%>
										<TR   >
											<td class="ColumnBackground"><html:text property="bankName"  readonly="true"  name="frmNpaUpgradaionForm"  value="<%= st.getUnitName()%>" /></td>
											<td class="ColumnBackground"><html:text property="bankName"  readonly="true"   name="frmNpaUpgradaionForm"  value="<%= st.getBankName()%>" /></td>
											<td class="ColumnBackground"><html:text property="bankName"  readonly="true"  style="<%=color%>" name="frmNpaUpgradaionForm"  value="<%= st.getCgPan()%>" /></td>
											<td class="ColumnBackground"><html:text property="bankName"  readonly="true"   name="frmNpaUpgradaionForm"  value="<%= st.getMliID()%>" /></td>
											<td class="ColumnBackground"><html:text property="bankName"  readonly="true"   name="frmNpaUpgradaionForm"  value="<%= st.getApplicationStatus()%>" /></td>
											<td class="ColumnBackground"><html:text property="lastNpaReportedDate"  readonly="true"  name="frmNpaUpgradaionForm"  value="<%= st.getLstNpaDate()%>" /></td>
											<td class="ColumnBackground"><html:text property="bankName"  readonly="true"  name="frmNpaUpgradaionForm"  value="<%= st.getReasonForAccNpa()%>" /></td>
											
										</TR>
										<% }%>
										
										<tr>
										<TD colspan="2" align="left" valign="center" class="ColumnBackground">Select NPA up-gradation Date </TD>
										<TD colspan="6" align="left" valign="center" class="ColumnBackground">	
										  <DIV align="left">
											  <html:text property="npaupgradationDate"  readonly="false"  name="npaActionForm"   />
											 <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('npaActionForm.npaupgradationDate')" >
										  </DIV>
  									   </TD>
										</tr>
										<% if(request.getAttribute("formTypeforJsp").equals("modification")) {%>
										
										<tr>
										<TD colspan="2" align="left" valign="center" class="ColumnBackground">Select New NPA Date </TD>
										<TD colspan="6" align="left" valign="center" class="ColumnBackground">	
										  <DIV align="left">
											  <html:text property="newNpaDate" readonly="false" name="npaActionForm"  value="" />
											 <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('npaActionForm.newNpaDate')" >
										  </DIV>
  									   </TD>
										</tr>
										<%} %>
										<tr>
										<TD colspan="2" align="left" valign="center" class="ColumnBackground">Enter Remarks</TD>
										<TD colspan="6" align="left" valign="center" class="ColumnBackground">	
										 <DIV align="left">
																	
											<html:textarea property="remark"  cols="65" rows="4"  name="npaActionForm"   />
											<DIV align="left">
  									   </TD>
										</tr>
										<tr><td><html:hidden property="strCgpan" name="npaActionForm" value='<%=cgpan%>' /></td>
										<td><html:hidden property="NPAformType" name="npaActionForm" value='<%=NPAformType%>' /></td></tr>
										<TR>
<!--											<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>-->
											
										</TR>
									</TABLE>
								</TD>
							</TR>
							</table>
							
															
						</td>
					</tr>

					
					<TR >
						<TD align="center" valign="baseline" >
<!--							<DIV align="center">	-->
<!--							<html:button property="" value="SAVE" onclick="saveNPAUpgradationDetails()"></html:button>							-->
<!--															-->
<!--							</DIV>-->
							
							<DIV align="center">								
								<A href="#" onclick="saveNPAUpgradationDetailsNew()"><IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>								
							</DIV>
						</TD>
					</TR>					
				</TABLE>
			</TD>
			<TD width="20" background="images/TableVerticalRightBG.gif">
				&nbsp;
			</TD>
		</TR>
		<TR>
			<TD width="20" align="right" valign="top">
				<IMG src="images/TableLeftBottom1.gif" width="20" height="15">
			</TD>
			<TD background="images/TableBackground2.gif">
				&nbsp;
			</TD>
			<TD width="20" align="left" valign="top">
				<IMG src="images/TableRightBottom1.gif" width="23" height="15">
			</TD>
		</TR>
	</html:form>
</TABLE>
			