	<%@ page language="java"%>
	<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
	<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
	<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
	<%@ page import="com.cgtsi.guaranteemaintenance.NPADetails"%>
	<%@ page import="com.cgtsi.guaranteemaintenance.RecoveryProcedure"%>
	<%@ page import="com.cgtsi.admin.MenuOptions"%>
	<%@ include file="/jsp/SetMenuInfo.jsp" %>
	<%@ page import="java.util.Date"%>
	<%@ page import="java.util.ArrayList"%>
	<%@ page import="java.text.SimpleDateFormat"%>
	<%@page import="java.util.HashMap"%>
	<%@page import="java.lang.String"%>
	
<script type="text/javascript">
	function enableDisableSubsidyFields(flag){
		if(flag == 'Y'){
			document.forms[0].subsidyLastRcvdAmt.disabled = false;
			document.forms[0].subsidyLastRcvdDt.disabled = false;
		}
		if(flag == 'N'){
			document.forms[0].subsidyLastRcvdAmt.disabled = true;
			document.forms[0].subsidyLastRcvdAmt.value = '';
			document.forms[0].subsidyLastRcvdDt.disabled = true;
			document.forms[0].subsidyLastRcvdDt.value = '';
		}
	}
	
	function enableDisableFields(flag){
		if(flag == 'Y'){
			//enable all fields 
			document.forms[0].npaConfirm[0].disabled = false;
			document.forms[0].npaConfirm[1].disabled = false;
		//	document.forms[0].npaReason.disabled = false;
			document.forms[0].effortsTaken.disabled = false;
			document.forms[0].isAcctReconstructed[0].disabled = false;
			document.forms[0].isAcctReconstructed[1].disabled = false;
			document.forms[0].subsidyFlag[0].disabled = false;
			document.forms[0].subsidyFlag[1].disabled = false;
			document.forms[0].networthAsOnSancDt.disabled = false;
			document.forms[0].networthAsOnNpaDt.disabled = false;
			document.forms[0].reasonForReductionAsOnNpaDt.disabled = false;			
		}
		if(flag == 'N'){
			//disable all fields
			document.forms[0].npaConfirm[0].disabled = true;
			document.forms[0].npaConfirm[1].disabled = true;
			document.forms[0].npaConfirm[0].checked = false;
			document.forms[0].npaConfirm[1].checked = false;
			
		//	document.forms[0].npaReason.disabled = true;
		//	document.forms[0].npaReason.value = '';
			
			document.forms[0].effortsTaken.disabled = true;
			document.forms[0].effortsTaken.value = '';
			
			document.forms[0].isAcctReconstructed[0].disabled = true;
			document.forms[0].isAcctReconstructed[1].disabled = true;
			document.forms[0].isAcctReconstructed[0].checked = false;
			document.forms[0].isAcctReconstructed[1].checked = false;
			
			document.forms[0].subsidyFlag[0].disabled = true;
			document.forms[0].subsidyFlag[1].disabled = true;
			document.forms[0].subsidyFlag[0].checked = false;
			document.forms[0].subsidyFlag[1].checked = false;
			
			document.forms[0].isSubsidyRcvd[0].disabled = true;
			document.forms[0].isSubsidyRcvd[1].disabled = true;
			document.forms[0].isSubsidyRcvd[0].checked = false;
			document.forms[0].isSubsidyRcvd[1].checked = false;
			
			document.forms[0].isSubsidyAdjusted[0].disabled = true;
			document.forms[0].isSubsidyAdjusted[1].disabled = true;
			document.forms[0].isSubsidyAdjusted[0].checked = false;
			document.forms[0].isSubsidyAdjusted[1].checked = false;
			
			document.forms[0].subsidyLastRcvdAmt.disabled = true;
			document.forms[0].subsidyLastRcvdAmt.value = '';
			
			document.forms[0].subsidyLastRcvdDt.disabled = true;
			document.forms[0].subsidyLastRcvdDt.value = '';
			document.forms[0].lastInspectionDt.value = '';
			document.forms[0].networthAsOnSancDt.disabled = true;
			document.forms[0].networthAsOnNpaDt.disabled = true;
			document.forms[0].networthAsOnNpaDt.value = '';
			document.forms[0].reasonForReductionAsOnNpaDt.disabled = true;
			document.forms[0].reasonForReductionAsOnNpaDt.value = '';
		}
	}
	
	function confirmNpaDate(flag){
		if(flag == 'Y'){
		alert('Any change in NPA date later other than on account of upgradation of a/c, will not be allowed by CGTMSE and may lead to rejection of claim');
		//	document.forms[0].npaReason.disabled = false;
			document.forms[0].effortsTaken.disabled = false;
			document.forms[0].isAcctReconstructed[0].disabled = false;
			document.forms[0].isAcctReconstructed[1].disabled = false;
			document.forms[0].subsidyFlag[0].disabled = false;
			document.forms[0].subsidyFlag[1].disabled = false;
			document.forms[0].networthAsOnSancDt.disabled = false;
			document.forms[0].networthAsOnNpaDt.disabled = false;
			document.forms[0].reasonForReductionAsOnNpaDt.disabled = false;	
		return true;
		}
		if(flag == 'N'){
		//	document.forms[0].npaReason.disabled = true;
		//	document.forms[0].npaReason.value = '';
			
			document.forms[0].effortsTaken.disabled = true;
			document.forms[0].effortsTaken.value = '';
			
			document.forms[0].isAcctReconstructed[0].disabled = true;
			document.forms[0].isAcctReconstructed[1].disabled = true;
			document.forms[0].isAcctReconstructed[0].checked = false;
			document.forms[0].isAcctReconstructed[1].checked = false;
			
			document.forms[0].subsidyFlag[0].disabled = true;
			document.forms[0].subsidyFlag[1].disabled = true;
			document.forms[0].subsidyFlag[0].checked = false;
			document.forms[0].subsidyFlag[1].checked = false;
			
			document.forms[0].isSubsidyRcvd[0].disabled = true;
			document.forms[0].isSubsidyRcvd[1].disabled = true;
			document.forms[0].isSubsidyRcvd[0].checked = false;
			document.forms[0].isSubsidyRcvd[1].checked = false;
			
			document.forms[0].isSubsidyAdjusted[0].disabled = true;
			document.forms[0].isSubsidyAdjusted[1].disabled = true;
			document.forms[0].isSubsidyAdjusted[0].checked = false;
			document.forms[0].isSubsidyAdjusted[1].checked = false;
			
			document.forms[0].subsidyLastRcvdAmt.disabled = true;
			document.forms[0].subsidyLastRcvdAmt.value = '';
			
			document.forms[0].subsidyLastRcvdDt.disabled = true;
			document.forms[0].subsidyLastRcvdDt.value = '';
			document.forms[0].lastInspectionDt.value = '';
			document.forms[0].networthAsOnSancDt.disabled = true;
			document.forms[0].networthAsOnNpaDt.disabled = true;
			document.forms[0].networthAsOnNpaDt.value = '';
			document.forms[0].reasonForReductionAsOnNpaDt.disabled = true;
			document.forms[0].reasonForReductionAsOnNpaDt.value = '';		
		}
	}
	
	function calOutstanding(field){
	//	alert("property:"+field.name);
            
		var index = field.name.slice(-1);
	//	alert("index:"+index);
		var totalDisbAmt = findObj("totalDisbAmt"+index);
		
		var subAmt = 0;
			var subsidyAmt = document.forms[0].subsidyLastRcvdAmt.value;
			if(!isNaN(subsidyAmt) && subsidyAmt > 0){
				subAmt = parseFloat(subsidyAmt);
			}
		
	//	alert("totalDisbAmt:"+totalDisbAmt.name);
        //        alert("totalDisbAmt value:"+totalDisbAmt.value);
                if ((isNaN(totalDisbAmt.value)) || totalDisbAmt.value==""){
                    alert("please enter disbursed amount");
                    return false;
                }
    
                var approvedAmount = findObj("approvedAmount"+index);
           //     alert("approvedAmount:"+approvedAmount.name);
           //     alert("approvedAmount value:"+approvedAmount.value);
                
                var pRepayAmt = findObj("repayPrincipal"+index);
           //     alert("pRepayAmt value:"+pRepayAmt.value);
                
                var repayAmt = 0;
                 if (!(isNaN(pRepayAmt.value)) && pRepayAmt.value!=""){
                    repayAmt = parseFloat(pRepayAmt.value);
                 }
				 
				 var npaDt = document.forms[0].npaDt.value;
	//	alert("npaDt:"+npaDt);
        var subsidyDt = document.forms[0].subsidyLastRcvdDt.value;
	//	alert("subsidyDt:"+subsidyDt);
		var size = document.getElementById('total').value;
	//	alert("size:"+size);
		
		var osAmt = 0;
        var npaDayStr;
		var npaMonStr;
		var npaYearStr;
		var subsidyDayStr;
		var subsidyMonStr;
		var subsidyYearStr;        
        //logic to compare dates.if npaDate>creditDate then go for calculations.
                
		var elem1 = npaDt.split('/');  
		var elem2 = subsidyDt.split('/'); 
		
			    npaDayStr = elem1[0];  
				npaMonStr = elem1[1];  
				npaYearStr = elem1[2];
				
				subsidyDayStr = elem2[0]; 
				subsidyMonStr = elem2[1]; 
				subsidyYearStr = elem2[2];
				
				 var os =0;
			
		if((npaYearStr > subsidyYearStr) || ((npaYearStr == subsidyYearStr) && (npaMonStr > subsidyMonStr)) 
					|| ((npaYearStr == subsidyYearStr) && (npaMonStr == subsidyMonStr && npaDayStr > subsidyDayStr))){
						os = Math.min(totalDisbAmt.value,approvedAmount.value) - (repayAmt+subAmt);
					}else{
						os = Math.min(totalDisbAmt.value,approvedAmount.value) - (repayAmt);
						}
                
                findObj("outstandingPrincipal"+index).value = os;
				
	}
	
 	function calOSAmt(field){
		//alert(field.value);
		var subsidyAmt = 0;
		if(!(isNaN(field.value)))// && subsidyAmt > 0)
		{
			subsidyAmt = parseFloat(field.value);
		}
		
	
        var npaDt = document.forms[0].npaDt.value;
	//	alert("npaDt:"+npaDt);
        var subsidyDt = document.forms[0].subsidyLastRcvdDt.value;
	//	alert("subsidyDt:"+subsidyDt);
		var size = document.getElementById('total').value;
	//	alert("size:"+size);
		
		var osAmt = 0;
        var npaDayStr;
		var npaMonStr;
		var npaYearStr;
		var subsidyDayStr;
		var subsidyMonStr;
		var subsidyYearStr;        
        //logic to compare dates.if npaDate>creditDate then go for calculations.
                
		var elem1 = npaDt.split('/');  
		var elem2 = subsidyDt.split('/'); 
		
			    npaDayStr = elem1[0];  
				npaMonStr = elem1[1];  
				npaYearStr = elem1[2];
				
				subsidyDayStr = elem2[0]; 
				subsidyMonStr = elem2[1]; 
				subsidyYearStr = elem2[2];
			
		if((npaYearStr > subsidyYearStr) || ((npaYearStr == subsidyYearStr) && (npaMonStr > subsidyMonStr)) 
					|| ((npaYearStr == subsidyYearStr) && (npaMonStr == subsidyMonStr && npaDayStr > subsidyDayStr))){
			
					//alert("npa > subsidy credit");
					for(var i = 1; i<= size;i++){
						var cgpan = findObj("cgpan"+i);
						var loanType = cgpan.value.slice(-2);
						if(loanType == 'TC' || loanType == 'CC'){
							var totalDisbAmt = findObj("totalDisbAmt"+i);
							/* if ((isNaN(totalDisbAmt.value)) || totalDisbAmt.value==""){
								alert("please enter disbursed amount");
								return false;
							} */
				
							var approvedAmount = findObj("approvedAmount"+i);
							var pRepayAmt = findObj("repayPrincipal"+i);
							var repayAmt = 0;
							if (!(isNaN(pRepayAmt.value)) && pRepayAmt.value!=""){
								repayAmt = parseFloat(pRepayAmt.value);
							}
							//alert("subsidyAmt:"+subsidyAmt);
							var os = Math.min(totalDisbAmt.value,approvedAmount.value) - (repayAmt + subsidyAmt);
							
							findObj("outstandingPrincipal"+i).value = os;
						
						}
					}
		}else{	
			
				for(var j=1;j<=size;j++){
					var cgpan = findObj("cgpan"+j);
					var loanType = cgpan.value.slice(-2);
					
					if(loanType == 'TC' || loanType == 'CC'){
						var totalDisbAmt = findObj("totalDisbAmt"+j);
						if ((isNaN(totalDisbAmt.value)) || totalDisbAmt.value==""){
							//alert("please enter disbursed amount");
							return false;
						}
						
						var approvedAmount = findObj("approvedAmount"+j);
						var pRepayAmt = findObj("repayPrincipal"+j);
						var repayAmt = 0;
						if (!(isNaN(pRepayAmt.value)) && pRepayAmt.value!=""){
							repayAmt = parseFloat(pRepayAmt.value);
						}
						
						var os = Math.min(totalDisbAmt.value,approvedAmount.value) - (repayAmt);
						
						findObj("outstandingPrincipal"+j).value = os;
					}
				}
		}
            
	} 
	
	
	function enableDisableSubRcvd(flag){
		if(flag == 'Y'){
			document.forms[0].isSubsidyRcvd[0].disabled = false;
			document.forms[0].isSubsidyRcvd[1].disabled = false;
		}else{
			document.forms[0].isSubsidyRcvd[0].disabled = true;
			document.forms[0].isSubsidyRcvd[0].checked = false;
			document.forms[0].isSubsidyRcvd[1].disabled = true;
			document.forms[0].isSubsidyRcvd[1].checked = false;
			document.forms[0].isSubsidyAdjusted[0].disabled = true;
			document.forms[0].isSubsidyAdjusted[0].checked = false;
			document.forms[0].isSubsidyAdjusted[1].disabled = true;
			document.forms[0].isSubsidyAdjusted[1].checked = false;
			document.forms[0].subsidyLastRcvdAmt.disabled = true;
			document.forms[0].subsidyLastRcvdAmt.value = '';
			document.forms[0].subsidyLastRcvdDt.disabled = true;
			document.forms[0].subsidyLastRcvdDt.value = '';
		}
	}
	
	function enableDisableSubAdj(flag){
		if(flag == 'Y'){
			document.forms[0].isSubsidyAdjusted[0].disabled = false;
			document.forms[0].isSubsidyAdjusted[1].disabled = false;
		}else{
			document.forms[0].isSubsidyAdjusted[0].disabled = true;
			document.forms[0].isSubsidyAdjusted[0].checked = false;
			document.forms[0].isSubsidyAdjusted[1].disabled = true;
			document.forms[0].isSubsidyAdjusted[1].checked = false;
			document.forms[0].subsidyLastRcvdAmt.disabled = true;
			document.forms[0].subsidyLastRcvdAmt.value = '';
			document.forms[0].subsidyLastRcvdDt.disabled = true;
			document.forms[0].subsidyLastRcvdDt.value = '';
		}
	}
	
	function calculatePsTotalNew(){
	var psTotal=0;
	//var landValue=findObj("landValue");	
	var landValue = findObj("securityAsOnSancDt(LAND)");
	if(landValue!=null && landValue!="")
	{
	var landVal=landValue.value;
	//alert(landVal);
	}
	if (!(isNaN(landVal)) && landVal!="")
	{
		psTotal+=parseFloat(landVal);	
	}

	var bldgValue=findObj("securityAsOnSancDt(BUILDING)");	
	if(bldgValue!=null && bldgValue!="")
	{
	var bldgVal=bldgValue.value;
	}
	if (!(isNaN(bldgVal)) && bldgVal!="")
	{
		psTotal+=parseFloat(bldgVal);	
	}

	var machineValue=findObj("securityAsOnSancDt(MACHINE)");	
	if(machineValue!=null && machineValue!="")
	{
	var machineVal=machineValue.value;
	}
	if (!(isNaN(machineVal)) && machineVal!="")
	{
		psTotal+=parseFloat(machineVal);	
	}

	var assetsValue=findObj("securityAsOnSancDt(OTHER_FIXED_MOVABLE_ASSETS)");	
	if(assetsValue!=null && assetsValue!="")
	{
	var assetsVal=assetsValue.value;
	}
	if (!(isNaN(assetsVal)) && assetsVal!="")
	{
		psTotal+=parseFloat(assetsVal);	
	}

	var currentAssetsValue=findObj("securityAsOnSancDt(CUR_ASSETS)");	
	if(currentAssetsValue!=null && currentAssetsValue!="")
	{
	var currentAssetsVal=currentAssetsValue.value;
	}
	if (!(isNaN(currentAssetsVal)) && currentAssetsVal!="")
	{
		psTotal+=parseFloat(currentAssetsVal);	
	}

	var othersValue=findObj("securityAsOnSancDt(OTHERS)");	
	if(othersValue!=null && othersValue!="")
	{
	var othersVal=othersValue.value;
	}
	if (!(isNaN(othersVal)) && othersVal!="")
	{
		psTotal+=parseFloat(othersVal);	
	}

	document.forms[0].totalSecurityAsOnSanc.value=psTotal; 

}
	
	function calPsTotalNpa(){
		
		var psTotal=0;
	//var landValue=findObj("landValue");	
	var landValue = findObj("securityAsOnNpaDt(LAND)");
	if(landValue!=null && landValue!="")
	{
	var landVal=landValue.value;
	}
	if (!(isNaN(landVal)) && landVal!="")
	{
		psTotal+=parseFloat(landVal);
		//alert("land:"+psTotal);		
	}

	var bldgValue=findObj("securityAsOnNpaDt(BUILDING)");	
	if(bldgValue!=null && bldgValue!="")
	{
	var bldgVal=bldgValue.value;
	}
	if (!(isNaN(bldgVal)) && bldgVal!="")
	{
		psTotal+=parseFloat(bldgVal);	
		//alert("building:"+psTotal);
	}

	var machineValue=findObj("securityAsOnNpaDt(MACHINE)");	
	if(machineValue!=null && machineValue!="")
	{
	var machineVal=machineValue.value;
	}
	if (!(isNaN(machineVal)) && machineVal!="")
	{
		psTotal+=parseFloat(machineVal);
	//alert("machine:"+psTotal);		
	}

	var assetsValue=findObj("securityAsOnNpaDt(OTHER_FIXED_MOVABLE_ASSETS)");	
	if(assetsValue!=null && assetsValue!="")
	{
	var assetsVal=assetsValue.value;
	}
	if (!(isNaN(assetsVal)) && assetsVal!="")
	{
		psTotal+=parseFloat(assetsVal);	
		//alert("fixed:"+psTotal);
	}

	var currentAssetsValue=findObj("securityAsOnNpaDt(CUR_ASSETS)");	
	if(currentAssetsValue!=null && currentAssetsValue!="")
	{
	var currentAssetsVal=currentAssetsValue.value;
	}
	if (!(isNaN(currentAssetsVal)) && currentAssetsVal!="")
	{
		psTotal+=parseFloat(currentAssetsVal);
		//alert("current assets:"+psTotal);		
	}

	var othersValue=findObj("securityAsOnNpaDt(OTHERS)");	
	if(othersValue!=null && othersValue!="")
	{
	var othersVal=othersValue.value;
	}
	if (!(isNaN(othersVal)) && othersVal!="")
	{
		psTotal+=parseFloat(othersVal);	
		//alert("others:"+psTotal);
	}
	//alert(psTotal);
	document.forms[0].totalSecurityAsOnNpa.value=psTotal; 
		
	}
	
	function onOffFields(){
		 if(document.forms[0].isAsPerRBI[1].checked == true){
			document.forms[0].npaConfirm[0].disabled = true;
			document.forms[0].npaConfirm[1].disabled = true;
			document.forms[0].npaConfirm[0].checked = false;
			document.forms[0].npaConfirm[1].checked = false;
			
		//	document.forms[0].npaReason.disabled = true;
		//	document.forms[0].npaReason.value = '';
			
			document.forms[0].effortsTaken.disabled = true;
			document.forms[0].effortsTaken.value = '';
			
			document.forms[0].isAcctReconstructed[0].disabled = true;
			document.forms[0].isAcctReconstructed[1].disabled = true;
			document.forms[0].isAcctReconstructed[0].checked = false;
			document.forms[0].isAcctReconstructed[1].checked = false;
			
			document.forms[0].subsidyFlag[0].disabled = true;
			document.forms[0].subsidyFlag[1].disabled = true;
			document.forms[0].subsidyFlag[0].checked = false;
			document.forms[0].subsidyFlag[1].checked = false;
			
			document.forms[0].isSubsidyRcvd[0].disabled = true;
			document.forms[0].isSubsidyRcvd[1].disabled = true;
			document.forms[0].isSubsidyRcvd[0].checked = false;
			document.forms[0].isSubsidyRcvd[1].checked = false;
			
			document.forms[0].isSubsidyAdjusted[0].disabled = true;
			document.forms[0].isSubsidyAdjusted[1].disabled = true;
			document.forms[0].isSubsidyAdjusted[0].checked = false;
			document.forms[0].isSubsidyAdjusted[1].checked = false;
			
			document.forms[0].subsidyLastRcvdAmt.disabled = true;
			document.forms[0].subsidyLastRcvdAmt.value = '';
			
			document.forms[0].subsidyLastRcvdDt.disabled = true;
			document.forms[0].subsidyLastRcvdDt.value = '';
			document.forms[0].lastInspectionDt.value = '';
			document.forms[0].networthAsOnSancDt.disabled = true;
			document.forms[0].networthAsOnNpaDt.disabled = true;
			document.forms[0].networthAsOnNpaDt.value = '';
			document.forms[0].reasonForReductionAsOnNpaDt.disabled = true;
			document.forms[0].reasonForReductionAsOnNpaDt.value = '';
		 }
		 
		 if(document.forms[0].npaConfirm[1].checked == true){
			
			document.forms[0].npaConfirm[0].checked = false;
			document.forms[0].npaConfirm[1].checked = false;
			
		//	document.forms[0].npaReason.disabled = true;
		//	document.forms[0].npaReason.value = '';
			
			document.forms[0].effortsTaken.disabled = true;
			document.forms[0].effortsTaken.value = '';
			
			document.forms[0].isAcctReconstructed[0].disabled = true;
			document.forms[0].isAcctReconstructed[1].disabled = true;
			document.forms[0].isAcctReconstructed[0].checked = false;
			document.forms[0].isAcctReconstructed[1].checked = false;
			
			document.forms[0].subsidyFlag[0].disabled = true;
			document.forms[0].subsidyFlag[1].disabled = true;
			document.forms[0].subsidyFlag[0].checked = false;
			document.forms[0].subsidyFlag[1].checked = false;
			
			document.forms[0].isSubsidyRcvd[0].disabled = true;
			document.forms[0].isSubsidyRcvd[1].disabled = true;
			document.forms[0].isSubsidyRcvd[0].checked = false;
			document.forms[0].isSubsidyRcvd[1].checked = false;
			
			document.forms[0].isSubsidyAdjusted[0].disabled = true;
			document.forms[0].isSubsidyAdjusted[1].disabled = true;
			document.forms[0].isSubsidyAdjusted[0].checked = false;
			document.forms[0].isSubsidyAdjusted[1].checked = false;
			
			document.forms[0].subsidyLastRcvdAmt.disabled = true;
			document.forms[0].subsidyLastRcvdAmt.value = '';
			
			document.forms[0].subsidyLastRcvdDt.disabled = true;
			document.forms[0].subsidyLastRcvdDt.value = '';
			document.forms[0].lastInspectionDt.value = '';
			document.forms[0].networthAsOnSancDt.disabled = true;
			document.forms[0].networthAsOnNpaDt.disabled = true;
			document.forms[0].networthAsOnNpaDt.value = '';
			document.forms[0].reasonForReductionAsOnNpaDt.disabled = true;
			document.forms[0].reasonForReductionAsOnNpaDt.value = '';
		 }
		
		if(document.forms[0].subsidyFlag[1].checked == true 
		|| (document.forms[0].subsidyFlag[0].checked == false && document.forms[0].subsidyFlag[1].checked == false)){
		//alert('subFlag:');
			document.forms[0].isSubsidyRcvd[0].checked = false;
			document.forms[0].isSubsidyRcvd[0].value = '';
			document.forms[0].isSubsidyRcvd[1].checked = false;
			document.forms[0].isSubsidyRcvd[1].value = '';
			document.forms[0].isSubsidyAdjusted[0].checked = false;
			document.forms[0].isSubsidyAdjusted[0].value = '';
			document.forms[0].isSubsidyAdjusted[1].checked = false;
			document.forms[0].isSubsidyAdjusted[1].value = '';
			document.forms[0].subsidyLastRcvdAmt.value = '';
			document.forms[0].subsidyLastRcvdDt.value = '';
		}else if(document.forms[0].subsidyFlag[0].checked == true && document.forms[0].isSubsidyRcvd[1].checked == true){
		//alert('isSubsidyRcvd:');
			document.forms[0].isSubsidyAdjusted[0].checked = false;
			document.forms[0].isSubsidyAdjusted[0].value = '';
			document.forms[0].isSubsidyAdjusted[1].checked = false;
			document.forms[0].isSubsidyAdjusted[1].value = '';
			document.forms[0].subsidyLastRcvdAmt.value = '';
			document.forms[0].subsidyLastRcvdDt.value = '';
		}else if(document.forms[0].subsidyFlag[0].checked == true && document.forms[0].isSubsidyRcvd[0].checked == true 
							&& document.forms[0].isSubsidyAdjusted[1].checked == true ){
		//alert('isSubsidyAdjusted:');
			document.forms[0].subsidyLastRcvdAmt.value = '';
			document.forms[0].subsidyLastRcvdDt.value = '';
		
		}else{
		//alert('no problem');
		}
		
		
	}
</script>

       <% 

		// session.setAttribute("CurrentPage","showNPADetails.do?method=showNPADetails");
		String menu = (String)session.getAttribute("mainMenu");
		String subMenu = (String)session.getAttribute("subMenuItem");
		// System.out.println("**** - Main Menu :" + menu);
		// System.out.println("**** - Sub Menu :" + subMenu);
		if(menu!=null && subMenu!=null)
		{
			if((menu.equals(MenuOptions.getMenu(MenuOptions.GM_PERIODIC_INFO))) &&
			  (subMenu.equals(MenuOptions.getMenu(MenuOptions.GM_PI_NPA_DETAILS))))
			  {
				 session.setAttribute("CurrentPage","showNPADetails.do?method=showNPADetails");
			  }
			if((menu.equals(MenuOptions.getMenu(MenuOptions.CP_CLAIM_FOR))) &&
			  (subMenu.equals(MenuOptions.getMenu(MenuOptions.CP_CLAIM_FOR_FIRST_INSTALLMENT))))
			  {
				 session.setAttribute("CurrentPage","getBorrowerId.do?method=setBankId");
			  }
		}
	%>
<body onload="onOffFields();">
<table width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="saveNpaDetails.do?method=saveNpaDetails" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<DIV align="right"><A HREF="javascript:submitForm('helpNpaDetails.do?method=helpNpaDetails')">HELP</A>
				</DIV>
				<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0"> 
					<TR>
						<TD>
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<!--header of form -->
							<TR>
								<TD>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<TR>
											<td class="Heading" WIDTH="55%">&nbsp; 
											<bean:message key="NPADetailsHeader"/>&nbsp; for 
											&nbsp;<bean:write name="newNpaForm" property="unitName"/> 
											&nbsp;(<bean:write name="newNpaForm" property="borrowerId"/>)
											</TD>

											<td  align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
											<td align="right"> <div align="right"> </div></td>
										</TR>
											
									</TABLE>
								</TD>
							</TR>			
                        </TABLE>
						</TD>
					</TR>
					<TR>
						<TD class="SubHeading" COLSPAN="12">&nbsp;General Details
						</TD>
					</TR>
					<TR>
						<TD>
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<!--sanction details -->
					<!--loop start -->
								<TR>
									<TD rowspan="2" class="ColumnBackground"><div align="center">cgpan</TD>
									<TD rowspan="2" class="ColumnBackground"><div align="center">Guarantee start Date<font color="#FF0000" size="2">*</font></div></TD>
									<td rowspan="2" class="ColumnBackground"><div align="center">Sanction Date<font color="#FF0000" size="2">*</font></div></TD>
									<td rowspan="2" class="ColumnBackground"><div align="center">First Disbursement Date<font color="#FF0000" size="2">*</font></div></TD>
									<td rowspan="2" class="ColumnBackground"><div align="center">Last Disbursement Date<font color="#FF0000" size="2">*</font></div></TD>
									<TD rowspan="2" class="ColumnBackground"><div align="center">First Installment Date<font color="#FF0000" size="2">*</font></div></TD>
									
									<td colspan="2" class="ColumnBackground">&nbsp;&nbsp;Moratorium<br>(in months)<font color="#FF0000" size="2">*</font></TD>
								</TR>
								<TR>									
									<td class="ColumnBackground"><div align="center"><font color="#FF0000" size="2">*</font>principal</div></td>
									<td class="ColumnBackground"><div align="center"><font color="#FF0000" size="2">*</font>interest</div></td>									
                                </TR>
								<%
									//java.lang.Double totalGuaranteeAmt = (java.lang.Double)request.getAttribute("totalGuaranteedAmt");
									
								//	java.lang.Double totalApprovedAmount = (java.lang.Double)request.getAttribute("totalApprovedAmount");
								
								
									org.apache.struts.validator.DynaValidatorActionForm newNpaForm2 = (org.apache.struts.validator.DynaValidatorActionForm)session.getAttribute("newNpaForm") ;
									
									
									java.lang.Double totalApprovedAmount = (java.lang.Double)newNpaForm2.get("totalApprovedAmount");
									//	out.println("totalApprovedAmount:"+totalApprovedAmount);
									double totalAmount = 0;
									if(totalApprovedAmount != null){
										totalAmount = totalApprovedAmount.doubleValue();
									}
									
									//java.lang.Integer size = (java.lang.Integer)session.getAttribute("size");
									
									java.lang.Integer size = (java.lang.Integer)newNpaForm2.get("size");
									
									//out.println("size:"+size);
									int total = 0;
									if(size != null){
										total = size.intValue();
									}
								//	out.println("total:"+total);
									
								//	request.setAttribute("size",size);
								//	java.util.Vector cgpansVector = (java.util.Vector)request.getAttribute("cgpansVector");
									
									java.util.Vector cgpansVector = (java.util.Vector)newNpaForm2.get("cgpansVector");
								
									String cgpan = null;
									String guarStartDt = null;
									String sanctionDt = null;
									String firstDisbDt = null;
									String lastDisbDt = null;
									String firstInstDt = null;
									
									String moratoriumPrincipal = null;
									String moratoriumInterest = null;
									
									
									for(int i=1;i<=total;i++){
									java.util.HashMap map = (java.util.HashMap)cgpansVector.get(i-1);
									String cgpanNo = (String)map.get("CGPAN");
									String loanType = (String)map.get("CGPAN_LOAN_TYPE");
									String guarStartDate = (String)map.get("GUARANTEE_START_DT");
									String sanctionDate = (String)map.get("SANCTION_DT");	
								//	out.println("cgpan:"+cgpanNo+"--guarStartDt:"+guarStartDate+"--sanctionDt:"+sanctionDate);
									
									cgpan = "cgpan"+i;
									guarStartDt = "guarStartDt"+i;
									sanctionDt = "sanctionDt"+i;
									firstDisbDt = "firstDisbDt"+i;
									lastDisbDt = "lastDisbDt"+i;
									firstInstDt = "firstInstDt"+i;
									
									moratoriumPrincipal = "moratoriumPrincipal"+i;
									moratoriumInterest = "moratoriumInterest"+i;
								%>
                                <TR>
									<TD class="tableData">&nbsp;<%=cgpanNo%><html:hidden name="newNpaForm" property="<%=cgpan%>" value="<%=cgpanNo%>"/></TD>
									<TD class="tableData"><html:text name="newNpaForm" property="<%=guarStartDt%>" value="<%=guarStartDate%>" size="10" maxlength="10" readonly="true"/><IMG src="images/CalendarIcon.gif" width="20" align="center" disabled="true"></TD>
									<TD class="tableData"><html:text name="newNpaForm" property="<%=sanctionDt%>" value="<%=sanctionDate%>" size="10" maxlength="10" readonly="true"/><IMG src="images/CalendarIcon.gif" width="20" align="center" disabled="true"></TD>
								<%if("TC".equals(loanType) || "CC".equals(loanType)){%>
									<TD class="tableData"><html:text name="newNpaForm" property="<%=firstDisbDt%>" size="10" maxlength="10"/><IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('newNpaForm.<%=firstDisbDt%>')" align="center" ></TD>
									<TD class="tableData"><html:text name="newNpaForm" property="<%=lastDisbDt%>" size="10" maxlength="10"/><IMG src="images/CalendarIcon.gif"  width="20" onClick="showCalendar('newNpaForm.<%=lastDisbDt%>')" align="center"></TD>
									<TD class="tableData"><html:text name="newNpaForm" property="<%=firstInstDt%>" size="10" maxlength="10"/><IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('newNpaForm.<%=firstInstDt%>')" align="center"></TD>
									<TD class="tableData"><div align="center">
									
										<html:select name="newNpaForm" property="<%=moratoriumPrincipal%>">
										<%for(int j=0;j<30;j++){%>
											<html:option value="<%=String.valueOf(j)%>"><%=String.valueOf(j)%></html:option>
										<%}%>	
										</html:select>
									</DIV></TD>
									<TD class="tableData"><div align="center">
									
										<html:select name="newNpaForm" property="<%=moratoriumInterest%>">
										<%for(int k=0;k<30;k++){%>
											<html:option value="<%=String.valueOf(k)%>"><%=String.valueOf(k)%></html:option>
										<%}%>	
										</html:select>
									</DIV></TD>
								<%}else{%>
									<TD class="tableData"><html:text name="newNpaForm" property="<%=firstDisbDt%>" disabled="true" size="10" maxlength="10"/><IMG src="images/CalendarIcon.gif" width="20" align="center" disabled="true"></TD>
									<TD class="tableData"><html:text name="newNpaForm" property="<%=lastDisbDt%>" disabled="true" size="10" maxlength="10"/><IMG src="images/CalendarIcon.gif"  width="20" align="center" disabled="true"></TD>
									<TD class="tableData"><html:text name="newNpaForm" property="<%=firstInstDt%>" disabled="true" size="10" maxlength="10"/><IMG src="images/CalendarIcon.gif" width="20" align="center" disabled="true"></TD>
									<TD class="tableData"><div align="center">
										<html:select name="newNpaForm" property="<%=moratoriumPrincipal%>" disabled="true">
											<html:option value="">0</html:option>
										</html:select>
									</DIV></TD>
									
									<TD class="tableData"><div align="center">
										<html:select name="newNpaForm" property="<%=moratoriumInterest%>" disabled="true">
											<html:option value="">0</html:option>
										</html:select>
									</DIV></TD>
								<%}%>
							   </TR>
							    <%
									}
							    %>
					<!--loop end -->
						</TABLE>
						</TD>
					</TR>
					<tr>
						<td><input type="hidden" name="total" id="total" value="<%=total%>" /></td>
					</tr>
					<TR>
						<TD class="SubHeading">Npa Details</TD>
					</TR> 
					
					<TR>
						<TD>
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<!--npa details -->			
								<TR>
									<TD colspan="1" class="ColumnBackground">&nbsp;Npa date<font color="#FF0000" size="2">*</font>
									</TD>
									<TD colspan="3" class="tableData">
										<table cellpadding="0" cellspacing="0">
											<TR>
												
                                                    <logic:equal name="newNpaForm" property="operationType" value="NCU" >
                                                        <TD><font size="1"><bean:write name="newNpaForm" property="npaDt" /></font>
														<html:hidden name="newNpaForm" property="npaDt" />
														</TD>
                                                    </logic:equal>
													<logic:equal name="newNpaForm" property="operationType" value="OCU" >
                                                        <TD><font size="1"><bean:write name="newNpaForm" property="npaDt" /></font>
														<html:hidden name="newNpaForm" property="npaDt" />
														</TD>
                                                    </logic:equal>
                                                    <logic:notEqual name="newNpaForm" property="operationType" value="NCU" >
														<logic:notEqual name="newNpaForm" property="operationType" value="OCU" >
															<TD><html:text name="newNpaForm" property="npaDt" size="10" maxlength="10" /></TD>
															<TD><IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('newNpaForm.npaDt')" align="center">
															</TD>
														</logic:notEqual>
													</logic:notEqual>
											</TR>
										</table>
									</TD>
								</TR>
								<TR>
									<TD colspan="1" class="ColumnBackground" align="left">&nbsp;Is the NPA date as per the RBI guidelines<font color="#FF0000" size="2">*</font>
									</TD>
									<TD colspan="3" class="tableData">
										<logic:equal name="newNpaForm" property="operationType" value="NCU" >
											<bean:write name="newNpaForm" property="isAsPerRBI" />
										</logic:equal>
										
										<logic:notEqual name="newNpaForm" property="operationType" value="NCU" >
										
											<html:radio name="newNpaForm" value="Y" property="isAsPerRBI" onclick="enableDisableFields('Y');"><bean:message key="yes" /></html:radio>&nbsp; 
											<html:radio name="newNpaForm" value="N" property="isAsPerRBI" onclick="enableDisableFields('N');"><bean:message key="no" /></html:radio>
										
										</logic:notEqual>
									</TD>
								</TR>
								<TR>
									<TD colspan="1" class="ColumnBackground" align="left">&nbsp;Do you confirm the NPA date<font color="#FF0000" size="2">*</font>
									</TD>
									<TD colspan="3" class="tableData">
										<logic:equal name="newNpaForm" property="operationType" value="NCU" >
											<bean:write name="newNpaForm" property="npaConfirm" />
										</logic:equal>
										<logic:notEqual name="newNpaForm" property="operationType" value="NCU" >
										
											<html:radio name="newNpaForm" value="Y" property="npaConfirm" onclick="confirmNpaDate('Y');"><bean:message key="yes"/></html:radio>&nbsp; 
											<html:radio name="newNpaForm" value="N" property="npaConfirm" onclick="confirmNpaDate('N');"><bean:message key="no"/></html:radio>
										
										</logic:notEqual>
									</TD>
								</TR>
								<TR>
									<TD colspan="1" class="ColumnBackground">&nbsp;<bean:message key="npaReason"/><font color="#FF0000" size="2">*</font>
									</TD>
									<TD colspan="3" class="tableData">
										<logic:equal name="newNpaForm" property="operationType" value="NCU" >
											<bean:write name="newNpaForm" property="npaReason" />
										</logic:equal>
										<logic:equal name="newNpaForm" property="operationType" value="OCU" >
											<bean:write name="newNpaForm" property="npaReason" />
										</logic:equal>
										<logic:notEqual name="newNpaForm" property="operationType" value="NCU" >
										<logic:notEqual name="newNpaForm" property="operationType" value="OCU" >
										<html:select name="newNpaForm" property="npaReason">
											<html:option value="">Select</html:option>
											<html:option value="Borrower's Death">Borrower's Death</html:option>
											<html:option value="Business Failure">Business Failure</html:option>
											<html:option value="Delay in commencement/implementation of project">Delay in commencement/<br>implementation of project</html:option>
											<html:option value="Incompetent Management">Incompetent Management</html:option>
											<html:option value="Loss of market">Loss of market</html:option>
											<html:option value="Misappropriation/ Diversufication of fund">Misappropriation/ Diversufication of fund</html:option>
											<html:option value="Non receipt of dues from debtors">Non receipt of dues from debtors</html:option>
											<html:option value="Recession in the economy">Recession in the economy</html:option>
											<html:option value="Unit / Activity Closed">Unit / Activity Closed</html:option>
											<html:option value="Wilful defaulter">Wilful defaulter</html:option>
										</html:select>
										</logic:notEqual>
										</logic:notEqual>
									</TD>
								</TR>										
								<TR>
									<TD colspan="1" class="ColumnBackground">&nbsp;<bean:message key="enumerateEfforts"/><font color="#FF0000" size="2">*</font>
									</TD>
									<TD colspan="3" class="tableData">
										<html:select name="newNpaForm" property="effortsTaken">
											<html:option value="">Select</html:option>
											<html:option value="Legal action">Legal action</html:option>
											<html:option value="Notice Issued">Notice Issued</html:option>
											<html:option value="Frequent visits">Frequent visits</html:option>
											<html:option value="Sale of assets">Sale of assets</html:option>
										</html:select>
									</TD>
								</TR>
								<TR>
									<TD colspan="1" class="ColumnBackground">&nbsp;Has the account being restructured/rescheduled<font color="#FF0000" size="2">*</font></TD>
									<TD colspan="3" class="tableData">
										
										<html:radio name="newNpaForm" value="Y" property="isAcctReconstructed"><bean:message key="yes"/></html:radio>&nbsp; 
										<html:radio name="newNpaForm" value="N" property="isAcctReconstructed"><bean:message key="no"/></html:radio>
										
									</TD>
								</TR>
					<!--Subsidy Details -->
								<TR>
									<TD colspan="1" class="ColumnBackground">&nbsp;Does the project covered under CGTMSE guarantee,involve any subsidy?
									</TD>                                           
                                    <TD colspan="3" class="tableData">
										
										<html:radio name="newNpaForm" value="Y" property="subsidyFlag" onclick="enableDisableSubRcvd('Y');"><bean:message key="yes"/></html:radio>&nbsp; 
										<html:radio name="newNpaForm" value="N" property="subsidyFlag" onclick="enableDisableSubRcvd('N');"><bean:message key="no" /></html:radio>
										
									</TD>
								</TR>
								<TR>
									<TD colspan="1" class="ColumnBackground">&nbsp;Has the subsidy been received?
									</TD>
									<TD colspan="3" class="tableData">
										<logic:equal name="newNpaForm" property="subsidyFlag" value="Y">
												<html:radio name="newNpaForm" value="Y" property="isSubsidyRcvd" onclick="enableDisableSubAdj('Y');" ><bean:message key="yes"/></html:radio>&nbsp; 
												<html:radio name="newNpaForm" value="N" property="isSubsidyRcvd" onclick="enableDisableSubAdj('N');" ><bean:message key="no"/></html:radio>
										</logic:equal>
										<logic:notEqual name="newNpaForm" property="subsidyFlag" value="Y">
												<html:radio name="newNpaForm" value="Y" property="isSubsidyRcvd" onclick="enableDisableSubAdj('Y');" disabled="true" ><bean:message key="yes"/></html:radio>&nbsp; 
												<html:radio name="newNpaForm" value="N" property="isSubsidyRcvd" onclick="enableDisableSubAdj('N');" disabled="true" ><bean:message key="no"/></html:radio>
										</logic:notEqual>
										<%--	<html:hidden name="newNpaForm" value="N" property="isSubsidyRcvd" />  --%>
										
									</TD>
								</TR>
								<TR>
									<TD colspan="1" class="ColumnBackground" >&nbsp;Has the subsidy been adjusted?
									</TD>
									<TD colspan="3" class="tableData"><!-- on yes below two fields should be enabled-->
										<logic:equal name="newNpaForm" property="isSubsidyRcvd" value="Y">
												<html:radio name="newNpaForm" value="Y" property="isSubsidyAdjusted" onclick="enableDisableSubsidyFields('Y');"><bean:message key="yes"/></html:radio>&nbsp; 
												<html:radio name="newNpaForm" value="N" property="isSubsidyAdjusted" onclick="enableDisableSubsidyFields('N');"><bean:message key="no"/></html:radio>
										</logic:equal>
										<logic:notEqual name="newNpaForm" property="isSubsidyRcvd" value="Y">
												<html:radio name="newNpaForm" value="Y" property="isSubsidyAdjusted" onclick="enableDisableSubsidyFields('Y');" disabled="true" ><bean:message key="yes"/></html:radio>&nbsp; 
												<html:radio name="newNpaForm" value="N" property="isSubsidyAdjusted" onclick="enableDisableSubsidyFields('N');" disabled="true" ><bean:message key="no"/></html:radio>
										</logic:notEqual>
										<%--	<html:hidden name="newNpaForm" value="N" property="isSubsidyAdjusted" />  --%>
										
									</TD>												
								</TR>
								<TR>
									<TD colspan="1" class="ColumnBackground">&nbsp;Subsidy Last Received Date
									</TD>
									<TD colspan="3" class="tableData">
									<table cellpadding="0" cellspacing="0">
										<TR>
											<TD>
												<logic:equal name="newNpaForm" property="isSubsidyAdjusted" value="Y">
													<html:text name="newNpaForm" property="subsidyLastRcvdDt" size="10" maxlength="10"/>
												</logic:equal>
												<logic:notEqual  name="newNpaForm" property="isSubsidyAdjusted" value="Y">
													<html:text name="newNpaForm" property="subsidyLastRcvdDt" size="10" maxlength="10" disabled="true"/>
												</logic:notEqual>											
											</TD>
											
											<TD><IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('newNpaForm.subsidyLastRcvdDt')" align="center">
											</TD>
                                            
										</TR>
									</table>
									</TD>
								</TR>
								<TR>
									<TD colspan="1" class="ColumnBackground" >&nbsp;Subsidy Last Received Amount
									</TD>
									<TD colspan="3" class="tableData">
										<logic:equal name="newNpaForm" property="isSubsidyAdjusted" value="Y">
												<html:text name="newNpaForm" property="subsidyLastRcvdAmt" size="10" maxlength="10" onchange="calOSAmt(this);"/>
										</logic:equal>
										<logic:notEqual  name="newNpaForm" property="isSubsidyAdjusted" value="Y">
												<html:text name="newNpaForm" property="subsidyLastRcvdAmt" size="10" maxlength="10" disabled="true" onchange="calOSAmt(this);"/>
										</logic:notEqual>
									</TD>												
								</TR>
								
								
								<TR>
									<TD colspan="1" class="ColumnBackground">&nbsp;Last Inspection Date<font color="#FF0000" size="2">*</font>
									</TD>
									<TD colspan="3" class="tableData">
									<table cellpadding="0" cellspacing="0">
										<TR>
										<%if(totalAmount > 750000){%>
											<TD>
											<html:text name="newNpaForm" property="lastInspectionDt" size="10" maxlength="10" /><!-- enable when total amount guaranteed greater than 7.5lac-->
											</TD>
											<TD><IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('newNpaForm.lastInspectionDt')" align="center">
											</TD>
											<%}else{%>
											<TD>
											<html:text name="newNpaForm" property="lastInspectionDt" size="10" maxlength="10" disabled="true"/>
											</TD>
											<TD><IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('newNpaForm.lastInspectionDt')" align="center" disabled="true">
											</TD>
											<%}%>
										</TR>
									</table>
									</TD>
								</TR>
								
						</TABLE>
						</TD>
					</TR>
					<TR>
						<TD class="SubHeading" colspan="12">Repayment (before NPA date exclusive of subsidy) and Outstanding amount Details
						</TD>
					</TR>
					<TR>
						<TD>
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
								
                    <!--Repayment (before NPA date exclusive of subsidy) and Outstanding details -->  
								<!--loop start -->
								<TR>
									<TD rowspan="2" class="ColumnBackground">&nbsp;&nbsp;cgpan</TD>
									<TD rowspan="2" class="ColumnBackground">&nbsp;&nbsp;Total Amount Disbursed<font color="#FF0000" size="2">*</font></TD>
									<TD colspan="2" class="ColumnBackground"><div align="center">Repayment(before NPA date)<font color="#FF0000" size="2">*</font></DIV></TD>
									<td colspan="2" class="ColumnBackground"><div align="center">Outstanding amount(as on NPA date)<font color="#FF0000" size="2">*</font></DIV></TD>
								</TR>
								<TR>									
									<td class="ColumnBackground"><div align="center"><font color="#FF0000" size="2">*</font>principal(in Rs.)</div></td>
									<td class="ColumnBackground"><div align="center"><font color="#FF0000" size="2">*</font>interest(in Rs.)</div></td>	
									<td class="ColumnBackground"><div align="center"><font color="#FF0000" size="2">*</font>principal(in Rs.)</div></td>
									<td class="ColumnBackground"><div align="center"><font color="#FF0000" size="2">*</font>interest(in Rs.)</div></td>										
                                </TR>
								<% 
									String totalDisbAmt = null;
									String repayPrincipal = null;
									String repayInterest = null;
									String outstandingPrincipal = null;
									String outstandingInterest = null;
									String approvedAmount = null;
									String interestRate = null;
									
									for(int i=1;i<=total;i++){
										java.util.HashMap map = (java.util.HashMap)cgpansVector.get(i-1);
										String cgpanNo = (String)map.get("CGPAN");
										String loanType = (String)map.get("CGPAN_LOAN_TYPE");
										Double approvedAmt = (java.lang.Double)map.get("APPROVED_AMOUNT");
										double guarAmt = approvedAmt.doubleValue();
										Double rate = (java.lang.Double)map.get("RATE");
										double r = rate.doubleValue();
									
										totalDisbAmt = "totalDisbAmt"+i;
										repayPrincipal = "repayPrincipal"+i;
										repayInterest = "repayInterest"+i;
										outstandingPrincipal = "outstandingPrincipal"+i;
										outstandingInterest = "outstandingInterest"+i;
										approvedAmount = "approvedAmount"+i;
										interestRate = "interestRate"+i;
								%>
								<TR>
									<TD class="tableData">&nbsp;<%=cgpanNo%><html:hidden name="newNpaForm" property="<%=approvedAmount%>" value="<%=String.valueOf(guarAmt)%>"/>
									<html:hidden name="newNpaForm" property="<%=interestRate%>" value="<%=String.valueOf(r)%>"/>
									</TD>
									<%if("TC".equals(loanType) || "CC".equals(loanType)){%>
									<TD class="tableData"><div align="center"><html:text name="newNpaForm" property="<%=totalDisbAmt%>" onchange="calOutstanding(this);"/></DIV></TD>
									<TD class="tableData"><div align="center"><html:text name="newNpaForm" property="<%=repayPrincipal%>" onchange="calOutstanding(this);"/></DIV></TD>
									<TD class="tableData"><div align="center"><html:text name="newNpaForm" property="<%=repayInterest%>" /></DIV></TD>
									<TD class="tableData"><div align="center"><html:text name="newNpaForm" property="<%=outstandingPrincipal%>" readonly="true"/></DIV></TD>
									<TD class="tableData"><div align="center"><html:text name="newNpaForm" property="<%=outstandingInterest%>" /></DIV></TD>
									<%}else{%>
									<TD class="tableData"><div align="center"><html:text name="newNpaForm" property="<%=totalDisbAmt%>" disabled="true"/></DIV></TD>
									<TD class="tableData"><div align="center"><html:text name="newNpaForm" property="<%=repayPrincipal%>" disabled="true"/></DIV></TD>
									<TD class="tableData"><div align="center"><html:text name="newNpaForm" property="<%=repayInterest%>" disabled="true"/></DIV></TD>
									<TD class="tableData"><div align="center"><html:text name="newNpaForm" property="<%=outstandingPrincipal%>"/></DIV></TD>
									<TD class="tableData"><div align="center"><html:text name="newNpaForm" property="<%=outstandingInterest%>" /></DIV></TD>
									<%}%>
									
								</TR>
								<%}%>
					<!--loop end -->
								<TR>
									<TD style="color:green;font:15px;" COLSPAN="12">Note: The Outstanding amount for Term Loan as on date of NPA has been derived by deducting 
											Principal repayment from Total disbursement/ Guarantee amount whichever is lower.	
									</TD>
								</TR>
						</TABLE>
						</TD>
					</TR>
					
					<TR>
						<TD>
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<!--Primary Security Details -->
							
								<TR>
									<TD colspan="6" class="SubHeading" style="backgroundcolor:yellow;">&nbsp;Primary Security Details</TD>
								</TR>
								<tr>
									<td class="ColumnBackground" rowspan="2"><div align="center"><bean:message key="particulars"/></div></td>
									<td class="ColumnBackground" colspan="2"><div align="center"><bean:message key="security"/></div></td>
									<td class="ColumnBackground" rowspan="2"><div align="center">Networth of guaranotr/Promoter(in Rs.)</div></td>
									<td class="ColumnBackground" rowspan="2"><div align="center"><bean:message key="reasonsforreductioninsecurity"/></div></td>
								</tr>
								<tr>
									<td class="ColumnBackground"><div align="center"><bean:message key="nature"/></div></td>
									<td class="ColumnBackground"><div align="center"><bean:message key="value"/><br><bean:message key="inRs"/></div></td>
								</tr>
								
					<!-- as on sanction date fields -->	

								<%
								//	java.lang.Double totalSecurityAsOnSanc = (java.lang.Double)request.getAttribute("totalSecurityAsOnSanc");
									
									java.lang.Double totalSecurityAsOnSanc = (java.lang.Double)newNpaForm2.get("totalSecurityAsOnSanc");
									
									double totalSecurityAsOnSanc2 = 0.0;
									if(totalSecurityAsOnSanc != null){
										totalSecurityAsOnSanc2 = totalSecurityAsOnSanc.doubleValue();
									}
									String totalSecurityAsOnSancDt = String.valueOf(totalSecurityAsOnSanc2);
									
									String totalLandValueStr = "";
                                    String totalMachineValueStr = "";
                                    String totalBldgValueStr = "";
                                    String totalOFMAValueStr = "";
                                    String totalCurrAssetsValueStr = "";
                                    String totalOthersValueStr = "";
                                           
                                    String landAsOnDtOfSnctnDtl = "";                              
                                    String bldgAsOnDtOfSnctn = "";
                                    String machinecAsOnDtOfSnctn = "";
                                    String otherAssetsAsOnDtOfSnctn = "";
                                    String currAssetsAsOnDtOfSnctn = "";
                                    String otherValAsOnDtOfSnctn = "";
									
									String landAsOnDtOfNPA = "";
                                    String bldgAsOnDtOfNPA = "";
                                    String machinecAsOnDtOfNPA = "";
                                    String otherAssetsAsOnDtOfNPA = "";
                                    String currAssetsAsOnDtOfNPA = "";
                                    String otherValAsOnDtOfNPA = "";
								%>
								<%  						
									   landAsOnDtOfSnctnDtl = "securityAsOnSancDt(LAND)";
									   bldgAsOnDtOfSnctn ="securityAsOnSancDt(BUILDING)";
									   machinecAsOnDtOfSnctn ="securityAsOnSancDt(MACHINE)";
									   otherAssetsAsOnDtOfSnctn = "securityAsOnSancDt(OTHER_FIXED_MOVABLE_ASSETS)";
									   currAssetsAsOnDtOfSnctn="securityAsOnSancDt(CUR_ASSETS)";
									   otherValAsOnDtOfSnctn="securityAsOnSancDt(OTHERS)";
									   
									   landAsOnDtOfNPA = "securityAsOnNpaDt(LAND)";
									   bldgAsOnDtOfNPA ="securityAsOnNpaDt(BUILDING)";
									   machinecAsOnDtOfNPA ="securityAsOnNpaDt(MACHINE)";
									   otherAssetsAsOnDtOfNPA = "securityAsOnNpaDt(OTHER_FIXED_MOVABLE_ASSETS)";	
									   currAssetsAsOnDtOfNPA="securityAsOnNpaDt(CUR_ASSETS)";
									   otherValAsOnDtOfNPA="securityAsOnNpaDt(OTHERS)";
								%>
					
								<tr>
									<td class="TableData" rowspan="6"><div align="center"><bean:message key="asondateofsanctionofcredit"/></div></td>
									<td class="TableData"><div align="center"><bean:message key="land"/></div></td>
									<td class="TableData"><div align="center">
										<html:text property="<%=landAsOnDtOfSnctnDtl%>" name="newNpaForm"  maxlength="10" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calculatePsTotalNew();"/>							
									</div></td>
									<td class="TableData" rowspan="6"><div align="center">
										<html:text name="newNpaForm" property="networthAsOnSancDt" size="10" maxlength="10"/>
									</div></td>
									<td class="TableData" rowspan="6"><div align="center">
										<html:select property="reasonForReductionAsOnSancDt" name="newNpaForm" disabled="true">
											<html:option value="">Select</html:option>
											<html:option value="Activity/ Unit closed">Activity/ Unit closed</html:option>
											<html:option value="Depreciation">Depreciation</html:option>
											<html:option value="Assets disposed">Assets disposed</html:option>
											<html:option value="Wear & Tear">Wear & Tear</html:option>
											<html:option value="Recession">Recession</html:option>
											<html:option value="Obsolete">Obsolete</html:option>
											<html:option value="High competition in market">High competition in market</html:option>
											<html:option value="Borrower not traceable">Borrower not traceable</html:option>
											<html:option value="Buisness Failure">Buisness Failure</html:option>
										</html:select>
										</div>
									</td>
								</tr>
								<tr>
									<td class="TableData"><div align="center"><bean:message key="bldg"/></div></td>
									<td class="TableData"><div align="center">
									<html:text property="<%=bldgAsOnDtOfSnctn%>" name="newNpaForm" maxlength="10" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calculatePsTotalNew();"/>
									</div></td>
								</tr>
								<tr>
									<td class="TableData"><div align="center"><bean:message key="machine"/></div></td>
									<td class="TableData"><div align="center">
									<html:text property="<%=machinecAsOnDtOfSnctn%>"  name="newNpaForm" maxlength="10" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calculatePsTotalNew();"/>
									</div></td>
								</tr>
								<tr>
									<td class="TableData"><div align="center"><bean:message key="assets"/></div></td>
									<td class="TableData"><div align="center">
									<html:text property="<%=otherAssetsAsOnDtOfSnctn%>"  name="newNpaForm" maxlength="10" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calculatePsTotalNew();"/>
									</div></td>
								</tr>
								<tr>
									<td class="TableData"><div align="center"><bean:message key="currentAssets"/></div></td>
									<td class="TableData"><div align="center">
									<html:text property="<%=currAssetsAsOnDtOfSnctn%>"   name="newNpaForm" maxlength="10" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calculatePsTotalNew();"/>
									</div></td>
								</tr>
								<tr>
									<td class="TableData"><div align="center"><bean:message key="psOthers"/></div></td>
									<td class="TableData"><div align="center">
									<html:text property="<%=otherValAsOnDtOfSnctn%>" name="newNpaForm" maxlength="10" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calculatePsTotalNew();"/>
									</div></td>
								</tr>
								
								<TR>
									<td class="TableData"></TD>
									<td class="TableData"><b>Total</b></TD>
									<td class="TableData" id="totalsecurityasonsancdt">
										<html:text name="newNpaForm" property="totalSecurityAsOnSanc" maxlength="10" size="10" readonly="true"/>
									</TD>  
									<td class="TableData" colspan="2"></TD>
								</TR>
					<!-- as on date of npa--onchange totalsecurityasonsancdt should be compared with totalsecurityasonnpadt -->
								<tr>
									<td class="TableData" rowspan="6"><div align="center"><bean:message key="asonthedateofnpa"/></div></td>
									<td class="TableData"><div align="center"><bean:message key="land"/></div></td>
									<td class="TableData"><div align="center">
									<html:text property="<%=landAsOnDtOfNPA%>" name="newNpaForm" maxlength="10" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalNpa();"/>
									</div></td>
									<td class="TableData" rowspan="6"><div align="center">
										<html:text name="newNpaForm" property="networthAsOnNpaDt" size="10" maxlength="10"/>
									</div></td>
									<td class="TableData" rowspan="6"><div align="center">
										<html:select property="reasonForReductionAsOnNpaDt" name="newNpaForm">
											<html:option value="">Select</html:option>
											<html:option value="Activity/ Unit closed">Activity/ Unit closed</html:option>
											<html:option value="Depreciation">Depreciation</html:option>
											<html:option value="Assets disposed">Assets disposed</html:option>
											<html:option value="Wear & Tear">Wear & Tear</html:option>
											<html:option value="Recession">Recession</html:option>
											<html:option value="Obsolete">Obsolete</html:option>
											<html:option value="High competition in market">High competition in market</html:option>
											<html:option value="Borrower not traceable">Borrower not traceable</html:option>
											<html:option value="Buisness Failure">Buisness Failure</html:option>
										</html:select>
										</div>
									</td>
								</tr>
								<tr>
									<td class="TableData"><div align="center"><bean:message key="bldg"/></div></td>
									<td class="TableData"><div align="center">
									<html:text property="<%=bldgAsOnDtOfNPA%>" name="newNpaForm" maxlength="10" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalNpa();"/>						
									</div></td>
								</tr>
								<tr>
									<td class="TableData"><div align="center"><bean:message key="machine"/></div></td>
									<td class="TableData"><div align="center">
									<html:text property="<%=machinecAsOnDtOfNPA%>"  name="newNpaForm" maxlength="10" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalNpa();"/>
									</div></td>
								</tr>
								<tr>
									<td class="TableData"><div align="center"><bean:message key="assets"/></div></td>
									<td class="TableData"><div align="center">
									<html:text property="<%=otherAssetsAsOnDtOfNPA%>" name="newNpaForm" maxlength="10" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalNpa();"/>
									</div></td>
								</tr>
								<tr>
									<td class="TableData"><div align="center"><bean:message key="currentAssets"/></div></td>
									<td class="TableData"><div align="center">
									<html:text property="<%=currAssetsAsOnDtOfNPA%>" name="newNpaForm" maxlength="10" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalNpa();"/>
									</div></td>
								</tr>
								<tr>
									<td class="TableData"><div align="center"><bean:message key="psOthers"/></div></td>
									<td class="TableData"><div align="center">
									<html:text property="<%=otherValAsOnDtOfNPA%>" name="newNpaForm" maxlength="10" size="10" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalNpa();"/>
									</div></td>
								</tr>
								<TR>
									<td class="TableData"></TD>
									<td class="TableData">Total</TD>
									<td class="TableData" id="totalsecurityasonnpadt">
										<html:text name="newNpaForm" property="totalSecurityAsOnNpa" maxlength="10" size="10" readonly="true"/>
									</TD>
									<TD class="TableData" colspan="2"></TD>
								</TR>
						</TABLE>
						</TD>
					</TR>
						
			<%--	<TR>
						<TD>
						<table>
							<tr>
								<TD align="center" valign="baseline" >
									<DIV align="center">
									
									<A href="javascript:submitForm('saveNpaDetails.do?method=saveNpaDetailsNew')">
									
										<IMG src="images/Save.gif" alt="OK" width="49" height="37" border="0"></A>
									
									<A href="javascript:document.newNpaForm.reset()">
										<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
										<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
										<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
									</DIV>
								</TD>
							</TR>
						</table>
						</TD>
						<TD width="20" background="images/TableVerticalRightBG.gif">
									&nbsp;
						</TD>
					</TR> --%>
					
				</table>  
			</TD>
		</TR>
		<tr> 
			<td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
			<td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
			  <div>
				<div align="center">
					<A href="javascript:submitForm('saveNpaDetails.do?method=saveNpaDetails')">
					<IMG src="images/Save.gif" alt="OK" width="49" height="37" border="0"></A>
					<A href="javascript:document.newNpaForm.reset()">
					<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
					<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
					<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
				</div>
			</td>
			<td width="20" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
		</tr>
	<!--	<TR>
			<TD width="20" align="right" valign="top"><IMG src="images/TableLeftBottom1.gif" width="20" height="15"></TD>
			<TD background="images/TableBackground2.gif">&nbsp;</TD>
			<TD width="20" align="left" valign="top"><IMG src="images/TableRightBottom1.gif" width="23" height="15"></TD>
		</TR> -->
	</html:form>
</table>
</body>

