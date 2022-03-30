<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.actionform.*"%>
<%@page import="java.util.HashSet"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<script type="text/javascript">
function funcdisable(id)
{
	
	var valfromtextboxes='';
	if(id=='A')
	{		
		
		document.forms[0].isActivityEligible.disabled = false;

		
	return true;
	}
	if(id=='B')
	{		
		
		document.forms[0].isActivityEligible.disabled = false;
			
	return true;
	}

	if(id=='C')
	{		
		
		document.forms[0].whetherCibil.disabled = false;
		return true;
	}

	if(id=='D')
	{		
		
		document.forms[0].whetherCibil.disabled = false;
		return true;
	}

	if(id=='E')
	{		
	
		document.forms[0].rateCharge.disabled = false;
		return true;
	}

	if(id=='F')
	{		
		
		document.forms[0].rateCharge.disabled = false;
		return true;
	}
	if(id=='G')
	{		
	
		document.forms[0].thirdpartyGuarantee.disabled = false;
		return true;
	}

	if(id=='H')
	{		
		
		document.forms[0].thirdpartyGuarantee.disabled = false;
		return true;
	}
	if(id=='I')
	{		
		
		document.forms[0].dateofNPA.disabled = false;
		return true;
	}

	if(id=='J')
	{		
		
		document.forms[0].dateofNPA.disabled = false;
		return true;
	}
	if(id=='K')
	{		
		
		document.forms[0].outstandingAmount.disabled = false;
		return true;
	}

	if(id=='L')
	{		
	
		document.forms[0].outstandingAmount.disabled = false;
		return true;
	}
	if(id=='M')
	{		
		
		document.forms[0].seriousDeficienies.disabled = false;
		return true;
	}

	if(id=='N')
	{		
		
		document.forms[0].seriousDeficienies.disabled = false;
		return true;
	}
	if(id=='O')
	{		
		
		document.forms[0].majorDeficienciesObserved.disabled = false;
		return true;
	}

	if(id=='P')
	{		
		
		document.forms[0].majorDeficienciesObserved.disabled = false;
		return true;
	}
	if(id=='Q')
	{		
		
		document.forms[0].deficienciesObserved.disabled = false;
		return true;
	}

	if(id=='R')
	{		
		
		document.forms[0].deficienciesObserved.disabled = false;
		return true;
	}
	if(id=='S')
	{		
		
		document.forms[0].internalRating.disabled = false;
		return true;
	}

	if(id=='T')
	{		
		
		document.forms[0].internalRating.disabled = false;
		return true;
	}
	if(id=='U')
	{		
		
		document.forms[0].alltheRecoveries.disabled = false;
		return true;
	}

	if(id=='V')
	{		
		
		document.forms[0].alltheRecoveries.disabled = false;
		return true;
	}

	
}


function postData(){      

	//alert("dgfgfg");

	
	 data = "";

	 var isActivityEligibleVal=""
	 var whetherCibilVal=""	 
	 var rateChargeVal=""
	 var thirdpartyGuaranteeVal=""
	 var dateofNPAval=""
	 var outstandingAmountVal=""
	 var seriousDeficieniesVal=""
	 var majorDeficienciesObservedVal=""
	 var deficienciesObservedVal=""
	 var internalRatingVal=""
	 var alltheRecoveriesVal=""
    	
	var isActivityEligibleVal=document.forms[0].isActivityEligible.value;


	 if((isActivityEligibleVal==null) || (isActivityEligibleVal==''))
	 {
		
		 isActivityEligibleVal="NA";
	 }
	    
	    var whetherCibilVal=document.forms[0].whetherCibil.value;

	  
		 if((whetherCibilVal==null) || (whetherCibilVal==''))
		 {
			
			 whetherCibilVal="NA";
		 }


	    
	    var rateChargeVal=document.forms[0].rateCharge.value;


	  
		 if((rateChargeVal==null) || (rateChargeVal==''))
		 {
		
			 rateChargeVal="NA";
		 }
	    
	    var thirdpartyGuaranteeVal=document.forms[0].thirdpartyGuarantee.value;


	   
		 if((thirdpartyGuaranteeVal==null) || (thirdpartyGuaranteeVal==''))
		 {
			
			 thirdpartyGuaranteeVal="NA";
		 }
	    
	    
	    var dateofNPAval=document.forms[0].dateofNPA.value;


	   
		 if((dateofNPAval==null) || (dateofNPAval==''))
		 {
			
			 dateofNPAval="NA";
		 }
	    
	    var outstandingAmountVal=document.forms[0].outstandingAmount.value;


	    if((outstandingAmountVal==null) || (outstandingAmountVal==''))
		 {
		
			 outstandingAmountVal="NA";
		 }

	    
	    var seriousDeficieniesVal=document.forms[0].seriousDeficienies.value;

	    if((seriousDeficieniesVal==null) || (seriousDeficieniesVal==''))
		 {
			
			 seriousDeficieniesVal="NA";
		 }
	    
	    
	    var majorDeficienciesObservedVal=document.forms[0].majorDeficienciesObserved.value;

	    if((majorDeficienciesObservedVal==null) || (majorDeficienciesObservedVal==''))
		 {
		
			 majorDeficienciesObservedVal="NA";
		 }
	    
	    var deficienciesObservedVal=document.forms[0].deficienciesObserved.value;

	 
		 if((deficienciesObservedVal==null) || (deficienciesObservedVal==''))
		 {
		
			 deficienciesObservedVal="NA";
		 }
	    
	    var internalRatingVal=document.forms[0].internalRating.value;

	
		 if((internalRatingVal==null) || (internalRatingVal==''))
		 {
			
			 internalRatingVal="NA";
		 }
	    
	    var alltheRecoveriesVal=document.forms[0].alltheRecoveries.value;


	    if((alltheRecoveriesVal==null) || (alltheRecoveriesVal==''))
		 {
		
			 alltheRecoveriesVal="NA";
		 }


       if(!document.forms[0].isActivityEligibleflag[0].checked && !document.forms[0].isActivityEligibleflag[1].checked )
         {
       	alert("please select isActivity Flag");
        return false;	
              }	    
         else  if(document.forms[0].isActivityEligibleflag[0].checked)
		{
			
			isActivityEligibleflagData=document.forms[0].isActivityEligibleflag[0].value;

		}
		else
		{
			
			isActivityEligibleflagData=document.forms[0].isActivityEligibleflag[1].value;
		
		}

       if(document.forms[0].isActivityEligibleflag[0].checked || document.forms[0].isActivityEligibleflag[1].checked)
       {    	  
    	  

           
           
           if(document.forms[0].isActivityEligible.value)
           {
               var raju=document.forms[0].isActivityEligible.value;
        	
        	   var iChars = "!@$%^&*()+=-[]\\\’;,./{}|\":?~_";
        	   for (var i = 0; i < raju.length; i++) {
        	   if (iChars.indexOf(raju.charAt(i)) != -1) {
        	   alert("1 special(,!@$%^&*) characters are not allowed.");
             
               return false;
                  }
        	   }
           }

       }




if(!document.forms[0].whetherCibilflag[0].checked && !document.forms[0].whetherCibilflag[1].checked )
{
	alert("please select whetherCibilflag Flag");
return false;	
     }	    
else  if(document.forms[0].whetherCibilflag[0].checked)
{

	
	whetherCibilflagData=document.forms[0].whetherCibilflag[0].value;


}
else
{
	
	whetherCibilflagData=document.forms[0].whetherCibilflag[1].value;
	

}

if(document.forms[0].whetherCibilflag[0].checked || document.forms[0].whetherCibilflag[1].checked)
{    	  
	
    
    if(document.forms[0].whetherCibil.value)
    {
        var raju=document.forms[0].whetherCibil.value;
 	 
 	   var iChars = "!@$%^&*()+=-[]\\\’;,./{}|\":?~_";
 	   for (var i = 0; i < raju.length; i++) {
 	   if (iChars.indexOf(raju.charAt(i)) != -1) {
 	   alert("2 special(,!@$%^&*) characters are not allowed.");
        
 	 
        return false;
    }
 	   }
    }

}


if(!document.forms[0].rateChargeflag[0].checked && !document.forms[0].rateChargeflag[1].checked )
{
	alert("please select rateChargeflag Flag");
return false;	
     }	    
else  if(document.forms[0].rateChargeflag[0].checked)
{
	
	
	rateChargeflagData=document.forms[0].rateChargeflag[0].value;
	
}
else
{
	
	rateChargeflagData=document.forms[0].rateChargeflag[1].value;
	
}

if(document.forms[0].rateChargeflag[0].checked || document.forms[0].rateChargeflag[1].checked)
{    	  

    
    if(document.forms[0].rateCharge.value)
    {
        var raju=document.forms[0].rateCharge.value;
 
 	   var iChars = "!@$%^&*()+=-[]\\\’;,./{}|\":?~_";
 	   for (var i = 0; i < raju.length; i++) {
 	   if (iChars.indexOf(raju.charAt(i)) != -1) {
 	   alert("3 special(,!@$%^&*) characters are not allowed.");
        
 	 
        return false;
    }
 	}
    }



}
if(!document.forms[0].thirdpartyGuaranteeflag[0].checked && !document.forms[0].thirdpartyGuaranteeflag[1].checked )
{
  alert("please select thirdpartyGuaranteeflag Flag");
return false;	
     }	    
else  if(document.forms[0].thirdpartyGuaranteeflag[0].checked)
{
	
	thirdpartyGuaranteeflagData=document.forms[0].thirdpartyGuaranteeflag[0].value;
	
}
else
{

	thirdpartyGuaranteeflagData=document.forms[0].thirdpartyGuaranteeflag[1].value;
	


}

if(document.forms[0].thirdpartyGuaranteeflag[0].checked || document.forms[0].thirdpartyGuaranteeflag[1].checked)
{    	  
 
   

    if(document.forms[0].thirdpartyGuarantee.value)
    {
        var raju=document.forms[0].thirdpartyGuarantee.value;
 
 	   var iChars = "!@$%^&*()+=-[]\\\’;,./{}|\":?~_";
 	   for (var i = 0; i < raju.length; i++) {
 	   if (iChars.indexOf(raju.charAt(i)) != -1) {
 	   alert("4 special(,!@$%^&*) characters are not allowed.");
        
 	
        return false;
    }
 	}
    }

}
if(!document.forms[0].dateofNPAflag[0].checked && !document.forms[0].dateofNPAflag[1].checked )
{
	alert("please select dateofNPAflag Flag");
return false;	
     }	    
else  if(document.forms[0].dateofNPAflag[0].checked)
{

	
	dateofNPAflagData=document.forms[0].dateofNPAflag[0].value;

}
else
{

	dateofNPAflagData=document.forms[0].dateofNPAflag[1].value;
	

}

if(document.forms[0].dateofNPAflag[0].checked || document.forms[0].dateofNPAflag[1].checked)
{    	  

   

    if(document.forms[0].dateofNPA.value)
    {
        var raju=document.forms[0].dateofNPA.value;
 
 	   var iChars = "!@$%^&*()+=-[]\\\’;,./{}|\":?~_";
 	   for (var i = 0; i < raju.length; i++) {
 	   if (iChars.indexOf(raju.charAt(i)) != -1) {
 	   alert("5 special(,!@$%^&*) characters are not allowed.");
        
 	
        return false;
    }
 	}
    }



}



if(!document.forms[0].outstandingAmountflag[0].checked && !document.forms[0].outstandingAmountflag[1].checked )
{
	alert("please select outstandingAmount Flag");
return false;	
     }	    
else  if(document.forms[0].outstandingAmountflag[0].checked)
{

	
	outstandingAmountflagData=document.forms[0].outstandingAmountflag[0].value;
	
}
else
{

	outstandingAmountflagData=document.forms[0].outstandingAmountflag[1].value;

}

if(document.forms[0].outstandingAmountflag[0].checked || document.forms[0].outstandingAmountflag[1].checked)
{    	  
  
   
    if(document.forms[0].outstandingAmount.value)
    {
        var raju=document.forms[0].outstandingAmount.value;
 	 
 	   var iChars = "!@$%^&*()+=-[]\\\’;,./{}|\":?~_";
 	   for (var i = 0; i < raju.length; i++) {
 	   if (iChars.indexOf(raju.charAt(i)) != -1) {
 	   alert("6 special(,!@$%^&*) characters are not allowed.");
        
 
        return false;
    }
 	}
    }
  
}


if(!document.forms[0].seriousDeficieniesflag[0].checked && !document.forms[0].seriousDeficieniesflag[1].checked )
{
	alert("please select seriousDeficienies Flag");
return false;	
     }	    
else  if(document.forms[0].seriousDeficieniesflag[0].checked)
{
	
	
	seriousDeficieniesflagData=document.forms[0].seriousDeficieniesflag[0].value;
}
else
{

	seriousDeficieniesflagData=document.forms[0].seriousDeficieniesflag[1].value;
	

}

if(document.forms[0].seriousDeficieniesflag[0].checked || document.forms[0].seriousDeficieniesflag[1].checked)
{    	  
  
   
    if(document.forms[0].seriousDeficienies.value)
    {
        var raju=document.forms[0].seriousDeficienies.value;
 
 	   var iChars = "!@$%^&*()+=-[]\\\’;,./{}|\":?~_";
 	   for (var i = 0; i < raju.length; i++) {
 	   if (iChars.indexOf(raju.charAt(i)) != -1) {
 	   alert("7 special(,!@$%^&*) characters are not allowed.");
        
 	  
        return false;
    }
 	}
    }
    

}

if(!document.forms[0].majorDeficienciesObservedflag[0].checked && !document.forms[0].majorDeficienciesObservedflag[1].checked )
{
	alert("please select majorDeficienciesObserved Flag");
return false;	
     }	    
else  if(document.forms[0].majorDeficienciesObservedflag[0].checked)
{
	
	
	majorDeficienciesObservedflagData=document.forms[0].majorDeficienciesObservedflag[0].value;
}
else
{

	majorDeficienciesObservedflagData=document.forms[0].majorDeficienciesObservedflag[1].value;
	

}

if(document.forms[0].majorDeficienciesObservedflag[0].checked || document.forms[0].majorDeficienciesObservedflag[1].checked)
{    	  
  
   

    if(document.forms[0].majorDeficienciesObserved.value)
    {
        var raju=document.forms[0].majorDeficienciesObserved.value;
 	 
 	   var iChars = "!@$%^&*()+=-[]\\\’;,./{}|\":?~_";
 	   for (var i = 0; i < raju.length; i++) {
 	   if (iChars.indexOf(raju.charAt(i)) != -1) {
 	   alert("8 special(,!@$%^&*) characters are not allowed.");
        
 
        return false;
    }
 	}
    }
}

if(!document.forms[0].deficienciesObservedflag[0].checked && !document.forms[0].deficienciesObservedflag[1].checked )
{
	alert("please select deficienciesObserved yes/no");
return false;	
     }	    
else  if(document.forms[0].deficienciesObservedflag[0].checked)
{
	
	
	deficienciesObservedflagData=document.forms[0].deficienciesObservedflag[0].value;

}
else
{

	deficienciesObservedflagData=document.forms[0].deficienciesObservedflag[1].value;
	

}

if(document.forms[0].deficienciesObservedflag[0].checked)
{    	  
  
  
    if(document.forms[0].deficienciesObserved.value)
    {
        var raju=document.forms[0].deficienciesObserved.value;
 	
 	   var iChars = "!@$%^&*()+=-[]\\\’;,./{}|\":?~_";
 	   for (var i = 0; i < raju.length; i++) {
 	   if (iChars.indexOf(raju.charAt(i)) != -1) {
 	   alert("9 special(,!@$%^&*) characters are not allowed.");
        
 	 
        return false;
    }
 	}
    }

}

if(!document.forms[0].internalRatingflag[0].checked && !document.forms[0].internalRatingflag[1].checked )
{
	alert("please select internalRating Flag");
return false;	
     }	    
else  if(document.forms[0].internalRatingflag[0].checked)
{
	
	
	internalRatingflagData=document.forms[0].internalRatingflag[0].value;
}
else
{

	internalRatingflagData=document.forms[0].internalRatingflag[1].value;



}

if(document.forms[0].internalRatingflag[0].checked || document.forms[0].internalRatingflag[0].checked)
{    	  
  
  
    if(document.forms[0].internalRating.value)
    {
        var raju=document.forms[0].internalRating.value;
 
 	   var iChars = "!@$%^&*()+=-[]\\\’;,./{}|\":?~_";
 	   for (var i = 0; i < raju.length; i++) {
 	   if (iChars.indexOf(raju.charAt(i)) != -1) {
 	   alert("10 special(,!@$%^&*) characters are not allowed.");
        

        return false;
    }
 	}
    }
}

if(!document.forms[0].alltheRecoveriesflag[0].checked && !document.forms[0].alltheRecoveriesflag[1].checked )
{
	alert("please select alltheRecoveries Flag");
return false;	
     }	    
else  if(document.forms[0].alltheRecoveriesflag[0].checked)
{
	
	
	alltheRecoveriesflagData=document.forms[0].alltheRecoveriesflag[0].value;

}
else
{

	alltheRecoveriesflagData=document.forms[0].alltheRecoveriesflag[1].value;

}

if(document.forms[0].alltheRecoveriesflag[0].checked || document.forms[0].alltheRecoveriesflag[1].checked)
{    	  
  
  
    if(document.forms[0].alltheRecoveries.value)
    {
        var raju=document.forms[0].alltheRecoveries.value;
 
 	   var iChars = "!@$%^&*()+=-[]\\\’;,./{}|\":?~_";
 	   for (var i = 0; i < raju.length; i++) {
 	   if (iChars.indexOf(raju.charAt(i)) != -1) {
 	   alert("11 special(,!@$%^&*) characters are not allowed.");
        
 	 
        return false;
    }
 	}
    }

}
		  
        

data=data+"-"+isActivityEligibleflagData+","+isActivityEligibleVal+"-"+whetherCibilflagData+","+whetherCibilVal+"-"+rateChargeflagData+","+rateChargeVal+"-"+thirdpartyGuaranteeflagData+","+thirdpartyGuaranteeVal+"-"+dateofNPAflagData+","+dateofNPAval+"-"+outstandingAmountflagData+","+outstandingAmountVal+"-"+seriousDeficieniesflagData+","+seriousDeficieniesVal+"-"+majorDeficienciesObservedflagData+","+majorDeficienciesObservedVal+"-"+deficienciesObservedflagData+","+deficienciesObservedVal+"-"+internalRatingflagData+","+internalRatingVal+"-"+alltheRecoveriesflagData+","+alltheRecoveriesVal;
//alert(data);


 
   opener.findObj("view").value = data;//old

    //alert("last line"+data);
 
 
    window.close();
}



</script>

   <html>
    <head>
        <meta http-equiv="Content-Type"
              content="text/html; charset=windows-1252"></meta>
       
 </head>
   
    <html:errors />
	<html:form action="displayClaimProcessingSubmitDU.do?method=displayClaimProcessingSubmitDU" method="POST" enctype="multipart/form-data">
    <LINK href="<%=request.getContextPath()%>/css/StyleSheet.css" rel="stylesheet" type="text/css">
      <td colspan="12">
<marquee style="color:red;" scrollamount="200" scrolldelay="1200">
<b> All Fields Are Mandatory </b></marquee>
</td> 
 <table  border="1" cellpadding="0"   cellspacing="0">
  <tbody>
 <%
String more="";
String name="";
String name1="";
String mlicomments="";
String thiskey="";
%>
                           <%
								int j=0;
                                int k=0;
								%>
								
				<logic:iterate id="object" name="cpTcDetailsForm" property="claimDandU" indexId="index">
							<%
								ClaimActionForm   cReport = (ClaimActionForm)object;
								
								%>
	
								
			 <TD width="10%" align="left" valign="top" >						
	             <%=cReport.getBranchname()%>
	           </TD>
	              <TD width="10%" align="left" valign="top" >						
	             <%=cReport.getBranchname()%>
	           </TD>
	           
	              <TD width="10%" align="left" valign="top">						
	             <%=cReport.getBranchname()%>
	           </TD>
				 </logic:iterate>				  
       
                <tr>
                  <th class="SubHeading">S.No</th>
                    <th class="SubHeading">Description</th>
                    <th class="SubHeading">Yes/No</th>
                      <th class="SubHeading">Comments/Observations</th>
                </tr>
                 <tr>
                   <td>1.</td>
                    <td align="left">Activity is eligible as per Emergency Credit Guarantee Scheme(CGS)</td>
                   
                   
                <td width="15%" align="left" valign="top">
	            <%name="duCertifyDecisionYes("+more+")";
	          
	            %>
	            <%name1="duCertifyDecisionNo(key-"+k+")";
	          
	            %>
                <html:radio name="cpTcDetailsForm" value="Y"   property="isActivityEligibleflag"  onclick="funcdisable('A');" >Yes</html:radio>
	            <html:radio name="cpTcDetailsForm" value="N"  property="isActivityEligibleflag"   onclick="funcdisable('B');">No</html:radio>
                    </td>
                    <td align="left">
                     
                      		<html:textarea   cols="100" rows="2"  property="isActivityEligible" name="cpTcDetailsForm" />
                    </td>
                </tr>
                <tr>
                     <td>2.</td>
                    <td align="left">Whether CIBIL done/CIR/KYC obtained and findings are satisfactory.</td>
                    <td width="15%" align="left" valign="top">
                     <%name="whetherCibilYes("+more+")";
	          
	            %>
	            <%name1="whetherCibilNo(key-"+k+")";
	          
	            %>
	            
	             
               	<html:radio name="cpTcDetailsForm" property="whetherCibilflag" value="Y" onclick="funcdisable('C');"><bean:message key="yes"/></html:radio>
				<html:radio name="cpTcDetailsForm" property="whetherCibilflag" value="N" onclick="funcdisable('D');"><bean:message key="no"/></html:radio>
						
       
                     
                    </td>
                    
                      
                   <td align="left">
                  
                  	<html:textarea   cols="100" rows="2" property="whetherCibil" name="cpTcDetailsForm"  />
                     	 					
                  </td>
                   
                </tr>
                <tr>
                    <td>3.</td>
                    <td align="left">Rate charged on loan is as per CGS guidelines.</td>
                    <td width="15%" align="left" valign="top">
                     <%name="rateChargeYes("+more+")";
	          
	            %>
	            <%name1="rateChargeNo(key-"+k+")";	          
	            %>
	            <html:radio name="cpTcDetailsForm" value="Y"   property="rateChargeflag"  onclick="funcdisable('E');" >Yes</html:radio>
	            <html:radio name="cpTcDetailsForm" value="N"  property="rateChargeflag"   onclick="funcdisable('F');">No</html:radio>
                            </td>
                    <td align="left">
                       <html:textarea   cols="100" rows="2" property="rateCharge" name="cpTcDetailsForm"  />
                    </td>
                </tr>
                <tr>
                    <td>4.</td>
                    <td align="left">Third party gaurantee/collateral security stipulated.</td>
                    <td width="15%" align="left" valign="top">
                     <%name="thirdpartyGuaranteeYes("+more+")";
	          
	            %>
	            <%name1="thirdpartyGuaranteeNo(key-"+k+")";
	          
	            %>
	            <html:radio name="cpTcDetailsForm" property="thirdpartyGuaranteeflag" value="Y" onclick="funcdisable('G');"><bean:message key="yes"/></html:radio>
				<html:radio name="cpTcDetailsForm" property="thirdpartyGuaranteeflag" value="N" onclick="funcdisable('H');"><bean:message key="no"/></html:radio>
                      </td>
                    <td align="left">
                        <html:textarea   cols="100" rows="2" property="thirdpartyGuarantee" name="cpTcDetailsForm"  />
                    </td>
                </tr>
                <tr>
                    <td>5.</td>
                    <td align="left">Date of NPA as fed in the system is as per RBI guidelines.</td>
                    <td width="15%" align="left" valign="top">
                     <%name="dateofNPAYes("+more+")";
	          
	            %>
	            <%name1="dateofNPANo(key-"+k+")";
	          
	            %>
	            <html:radio name="cpTcDetailsForm" property="dateofNPAflag" value="Y" onclick="funcdisable('I');"><bean:message key="yes"/></html:radio>
				<html:radio name="cpTcDetailsForm" property="dateofNPAflag" value="N" onclick="funcdisable('J');"><bean:message key="no"/></html:radio>
                     </td>
                    <td align="left">
                        <html:textarea   cols="100" rows="2" property="dateofNPA" name="cpTcDetailsForm"  />
                    </td>
                </tr>
                <tr>
                   <td>6.</td>
                    <td align="left">Whether outstanding amount mentioned in the claim application form is with respect to the NPA date as reported in claim form.</td>
                    
                    <td width="15%" align="left" valign="top">
                     <%name="outstandingAmountYes("+more+")";
	          
	            %>
	            <%name1="outstandingAmountNo(key-"+k+")";
	          
	            %>
	            <html:radio name="cpTcDetailsForm" property="outstandingAmountflag" value="Y" onclick="funcdisable('K');"><bean:message key="yes"/></html:radio>
				<html:radio name="cpTcDetailsForm" property="outstandingAmountflag" value="N" onclick="funcdisable('L');"><bean:message key="no"/></html:radio>
                       </td>
                    <td align="left">
                        <html:textarea   cols="100" rows="2" property="outstandingAmount" name="cpTcDetailsForm"  />
                    </td>
                </tr>
                <tr>
                    <td>7.</td>
                    <td align="left">Whether serious deficiencies/Irrugularities have been observed in the matter of appraisal/renewal/disbursement/followup/conduct of the account.</td>
                    <td width="15%" align="left" valign="top">
                     <%name="seriousDeficieniesYes("+more+")";
	          
	            %>
	            <%name1="seriousDeficieniesNo(key-"+k+")";
	          
	            %>
	            <html:radio name="cpTcDetailsForm" property="seriousDeficieniesflag" value="Y" onclick="funcdisable('M');"><bean:message key="yes"/></html:radio>
				<html:radio name="cpTcDetailsForm" property="seriousDeficieniesflag" value="N" onclick="funcdisable('N');"><bean:message key="no"/></html:radio>
                     </td>
                    <td align="left">
                        <html:textarea   cols="100" rows="2" property="seriousDeficienies" name="cpTcDetailsForm"  />
                    </td>
                </tr>
                <tr>
                    <td>8.</td>
                    <td align="left">Major deficiencies observed in Pre sanction/Post disbursement inspections</td>
                    <td width="15%" align="left" valign="top">
                     <%name="majorDeficienciesObservedYes("+more+")";
	          
	            %>
	            <%name1="majorDeficienciesObservedNo(key-"+k+")";
	          
	            %>
	            <html:radio name="cpTcDetailsForm" property="majorDeficienciesObservedflag" value="Y" onclick="funcdisable('O');"><bean:message key="yes"/></html:radio>
				<html:radio name="cpTcDetailsForm" property="majorDeficienciesObservedflag" value="N" onclick="funcdisable('P');"><bean:message key="no"/></html:radio>
                     </td>
                    <td align="left">
                        <html:textarea   cols="100" rows="2" property="majorDeficienciesObserved" name="cpTcDetailsForm"  />
                    </td>
                </tr>
                
                <tr>
                    <td>9.</td>
                    <td align="left">Whether deficiencies observed on the part of internal staff as per the Staff Accountability exercise carried out.</td>
                   <td width="15%" align="left" valign="top">
                     <%name="deficienciesObservedYes("+more+")";
	          
	            %>
	            <%name1="deficienciesObservedNo(key-"+k+")";
	          
	            %>
	            <html:radio name="cpTcDetailsForm" property="deficienciesObservedflag" value="Y" onclick="funcdisable('Q');"><bean:message key="yes"/></html:radio>
				<html:radio name="cpTcDetailsForm" property="deficienciesObservedflag" value="N" onclick="funcdisable('R');"><bean:message key="no"/></html:radio>
                     </td>
                    <td align="left">
                        <html:textarea   cols="100" rows="2" property="deficienciesObserved" name="cpTcDetailsForm"  />
                    </td>
                </tr>
               
                <tr>
                   <td>10.</td>
                    <td align="left">Internal rating was carried out and the proposal was found of Investment Grade.(applicable for loans sanctioned above 50 lakh)</td>
                    <td width="15%" align="left" valign="top">
                     <%name="internalRatingYes("+more+")";
	          
	            %>
	            <%name1="internalRatingNo(key-"+k+")";
	          
	            %>
	            <html:radio name="cpTcDetailsForm" property="internalRatingflag" value="Y" onclick="funcdisable('S');"><bean:message key="yes"/></html:radio>
				<html:radio name="cpTcDetailsForm" property="internalRatingflag" value="N" onclick="funcdisable('T');"><bean:message key="no"/></html:radio>
                    </td>
                    <td align="left">
                        <html:textarea   cols="100" rows="2" property="internalRating" name="cpTcDetailsForm"  />
                    </td>
                </tr>
                <tr>
                   <td>11.</td>
                    <td align="left">Whether all the recoveries pertaining to the account after the date of NPA and before the claim lodgement have been duly incorporated in the claim form.</td>
                    <td width="15%" align="left" valign="top">
                     <%name="alltheRecoveriesYes("+more+")";
	          
	            %>
	            <%name1="alltheRecoveriesNo(key-"+k+")";
	          
	            %>
	            <html:radio name="cpTcDetailsForm" property="alltheRecoveriesflag" value="Y" onclick="funcdisable('U');"><bean:message key="yes"/></html:radio>
				<html:radio name="cpTcDetailsForm" property="alltheRecoveriesflag" value="N" onclick="funcdisable('V');"><bean:message key="no"/></html:radio>
                   </td>
                    <td align="left">
                        <html:textarea   cols="100" rows="2" property="alltheRecoveries" name="cpTcDetailsForm"  />
                    </td>
                </tr>
                 </tbody>
                 </table> 
                     
                   <td></td><td></td>
                   <td align="center">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="button" value="Save" onclick="postData();"/>
                               
                    </TD>
          
         </html:form>
    
       
</html>