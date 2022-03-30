<%@ page language="java"%>
<%@ page import = "com.cgtsi.claim.ClaimConstants"%>
<%@ page import = "com.cgtsi.actionform.ClaimActionForm"%>
<%@ page import = "java.util.HashMap"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","proceedFromRecoveryFilterPage.do?method=proceedFromRecoveryFilterPage");%>

<%
String npaClassifiedDt = "";
String npaReportingDt = "";
String reasonForTurningNPA = "";
String cgpan = "";
String hiddencgpan = "";
String dsbrsmntdt = "";
String dsbrsmntamt = "";
String appApprovedAmt = "";
String principal = "";
String interestCharges = "";
String osAsOnNpa = "";
String osAsStatedinCivilSuit = "";
String osAsOnLodgementOfClm = "";
String claimFlagTc = "";
String claimFlagWc = "";
String wccgpan = "";
String hidencgpan = "";
String wcAsOnNPA = "";
String wcOsAsOnInCivilSuit = "";
String wcOsAtLdgmntClm = "";
String landAsOnDtOfSnctnDtl = "";
String netwrthAsOnDtOfSnctn = "";
String reasonReductionDtSnctn = "";
String bldgAsOnDtOfSnctn = "";
String machinecAsOnDtOfSnctn = "";
String otherAssetsAsOnDtOfSnctn = "";
String currAssetsAsOnDtOfSnctn = "";
String otherValAsOnDtOfSnctn = "";
String landAsOnDtOfNPA = "";
String netwrthAsOnDtOfNPA = "";
String rsnRdctnDtSnctnAsOnNPA = "";
String bldgAsOnDtOfNPA = "";
String machinecAsOnDtOfNPA = "";
String otherAssetsAsOnDtOfNPA = "";
String currAssetsAsOnDtOfNPA = "";
String otherValAsOnDtOfNPA = "";
String landAsOnLodgemnt = "";
String netwrthAsOnLodgemnt = "";
String rsnRdctnDtSnctnAsOnLodgemnt = "";
String bldgAsOnDtOfLodgemnt = "";
String machinecAsOnLodgemnt = "";
String otherAssetsAsOnLodgemnt = "";
String currAssetsAsOnLodgemnt = "";
String otherValAsOnLodgemnt = "";
String cgpantodisplay = "";
String tcPrincipal1 = "";
String tcInterestCharges1 = "";
String wcAmount1 = "";
String wcOtherCharges1 = "";
String recMode = "";
String cgpn = "";
String amountClaimed = "";
java.util.HashMap hashmap = null;
java.util.Date dsbrsDt = null;
String repaidStr = "";
String approvedAmtStr = "";
String tcfield = "TC".trim();
String wcfield = "WC".trim();
String R1field="R1".trim();
%>
<body onload="setCPOthersEnabled(),onOffFields()"/>
<%
String focusField="dealingOfficerName";//"dateOfRecallNotice";
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
    focusField="test";
}
%>
<script type="text/javascript">
		function enableDisbaleChild(flag){
			if(flag == 'Y'){
				document.forms[0].isEnquiryConcluded[0].disabled = false;
				document.forms[0].isEnquiryConcluded[1].disabled = false;
				
					document.forms[0].dateOfRecallNotice.disabled = false;
					document.forms[0].reasonForIssueRecallNotice.disabled = false;
					document.forms[0].forumthrulegalinitiated.disabled = false;
					document.forms[0].caseregnumber.disabled = false;
					document.forms[0].reasonForFilingSuit.disabled = false;
					document.forms[0].location.disabled = false;
				//	document.forms[0].assetPossessionDate.disabled = false;
					document.forms[0].amountclaimed.disabled = false;
					document.forms[0].legaldate.disabled = false;
					
					document.forms[0].mliCommentOnFinPosition.disabled = false;
					document.forms[0].detailsOfFinAssistance.disabled = false;
					document.forms[0].creditSupport[0].disabled = false;
					document.forms[0].creditSupport[1].disabled = false;
					document.forms[0].placeUnderWatchList[0].disabled = false;
					document.forms[0].placeUnderWatchList[1].disabled = false;
					document.forms[0].remarksOnNpa.disabled = false;
					document.forms[0].bankFacilityDetail.disabled = false;
					
					document.forms[0].subsidyAmt.disabled = false;
					document.forms[0].subsidyDate.disabled = false;
										
					document.forms[0].isSubsidyAdjustedOnDues[0].disabled = false;
					document.forms[0].isSubsidyAdjustedOnDues[1].disabled = false;
					document.forms[0].isSubsidyRcvdAfterNpa[0].disabled = false;
					document.forms[0].isSubsidyRcvdAfterNpa[1].disabled = false;
					document.forms[0].subsidyFlag[0].disabled = false;
					document.forms[0].subsidyFlag[1].disabled = false ; 
			}
			if(flag == 'N'){
				document.forms[0].isEnquiryConcluded[0].disabled = true;
				document.forms[0].isEnquiryConcluded[1].disabled = true;
				document.forms[0].isEnquiryConcluded[0].checked = false;
				document.forms[0].isEnquiryConcluded[1].checked = false;
				document.forms[0].isMLIInvolved[0].disabled = true;
				document.forms[0].isMLIInvolved[1].disabled = true;
				document.forms[0].isMLIInvolved[0].checked = false;
				document.forms[0].isMLIInvolved[1].checked = false;	

			
				    document.forms[0].dateOfRecallNotice.disabled = false;
					document.forms[0].reasonForIssueRecallNotice.disabled = false;
					document.forms[0].forumthrulegalinitiated.disabled = false;
					document.forms[0].caseregnumber.disabled = false;
					document.forms[0].reasonForFilingSuit.disabled = false;
					document.forms[0].location.disabled = false;
				//	document.forms[0].assetPossessionDate.disabled = false;
					document.forms[0].amountclaimed.disabled = false;
					document.forms[0].legaldate.disabled = false;
					
					document.forms[0].mliCommentOnFinPosition.disabled = false;
					document.forms[0].detailsOfFinAssistance.disabled = false;
					document.forms[0].creditSupport[0].disabled = false;
					document.forms[0].creditSupport[1].disabled = false;
					document.forms[0].placeUnderWatchList[0].disabled = false;
					document.forms[0].placeUnderWatchList[1].disabled = false;
					document.forms[0].remarksOnNpa.disabled = false;
					document.forms[0].bankFacilityDetail.disabled = false;
					
					document.forms[0].subsidyAmt.disabled = false;
					document.forms[0].subsidyDate.disabled = false;
										
					document.forms[0].isSubsidyAdjustedOnDues[0].disabled = false;
					document.forms[0].isSubsidyAdjustedOnDues[1].disabled = false;
					document.forms[0].isSubsidyRcvdAfterNpa[0].disabled = false;
					document.forms[0].isSubsidyRcvdAfterNpa[1].disabled = false;
					document.forms[0].subsidyFlag[0].disabled = false;
					document.forms[0].subsidyFlag[1].disabled = false ; 
			}
		}
		
		function enableDisbaleEnquiryChild(flag){
			if(flag == 'Y'){			
					document.forms[0].isMLIInvolved[0].disabled = false;
					document.forms[0].isMLIInvolved[1].disabled = false;
			
				    document.forms[0].dateOfRecallNotice.disabled = false;
					document.forms[0].reasonForIssueRecallNotice.disabled = false;
					document.forms[0].forumthrulegalinitiated.disabled = false;
					document.forms[0].caseregnumber.disabled = false;
					document.forms[0].reasonForFilingSuit.disabled = false;
					document.forms[0].location.disabled = false;
				//	document.forms[0].assetPossessionDate.disabled = false;
					document.forms[0].amountclaimed.disabled = false;
					document.forms[0].legaldate.disabled = false;
					
					document.forms[0].mliCommentOnFinPosition.disabled = false;
					document.forms[0].detailsOfFinAssistance.disabled = false;
					document.forms[0].creditSupport[0].disabled = false;
					document.forms[0].creditSupport[1].disabled = false;
					document.forms[0].placeUnderWatchList[0].disabled = false;
					document.forms[0].placeUnderWatchList[1].disabled = false;
					document.forms[0].remarksOnNpa.disabled = false;
					document.forms[0].bankFacilityDetail.disabled = false;
					
					document.forms[0].subsidyAmt.disabled = false;
					document.forms[0].subsidyDate.disabled = false;
										
					document.forms[0].isSubsidyAdjustedOnDues[0].disabled = false;
					document.forms[0].isSubsidyAdjustedOnDues[1].disabled = false;
					document.forms[0].isSubsidyRcvdAfterNpa[0].disabled = false;
					document.forms[0].isSubsidyRcvdAfterNpa[1].disabled = false;
					document.forms[0].subsidyFlag[0].disabled = false;
					document.forms[0].subsidyFlag[1].disabled = false ; 
			}
			if(flag == 'N'){
				alert('Claim can be lodged only after the conclusion of Internal and/or external enquiry.');
					document.forms[0].isMLIInvolved[0].disabled = true;
					document.forms[0].isMLIInvolved[1].disabled = true;
					document.forms[0].isMLIInvolved[0].checked = false;
					document.forms[0].isMLIInvolved[1].checked = false;
			
				    
					
					document.forms[0].dateOfRecallNotice.disabled = true;
					document.forms[0].reasonForIssueRecallNotice.disabled = true;
					document.forms[0].forumthrulegalinitiated.disabled = true;
					document.forms[0].caseregnumber.disabled = true;
					document.forms[0].reasonForFilingSuit.disabled = true;
					document.forms[0].location.disabled = true;
				//	document.forms[0].assetPossessionDate.disabled = true;
					document.forms[0].amountclaimed.disabled = true;
					document.forms[0].legaldate.disabled = true;
			
					document.forms[0].mliCommentOnFinPosition.disabled = true;
					document.forms[0].detailsOfFinAssistance.disabled = true;
					document.forms[0].creditSupport[0].disabled = true;
					document.forms[0].creditSupport[1].disabled = true;
					document.forms[0].placeUnderWatchList[0].disabled = true;
					document.forms[0].placeUnderWatchList[1].disabled = true;
					document.forms[0].remarksOnNpa.disabled = true;
					document.forms[0].bankFacilityDetail.disabled = true;
					
					document.forms[0].subsidyAmt.disabled = true;
					document.forms[0].subsidyDate.disabled = true;
										
					document.forms[0].isSubsidyAdjustedOnDues[0].disabled = true;
					document.forms[0].isSubsidyAdjustedOnDues[1].disabled = true;
					document.forms[0].isSubsidyRcvdAfterNpa[0].disabled = true;
					document.forms[0].isSubsidyRcvdAfterNpa[1].disabled = true;
					document.forms[0].subsidyFlag[0].disabled = true;
					document.forms[0].subsidyFlag[1].disabled = true; 
			}
		}
		
		function enableDisableFields(flag){
			if(flag == 'Y'){
				alert('Claim is not eligible.');
					document.forms[0].isMLIInvolved[0].checked = false;
					
					document.forms[0].dateOfRecallNotice.disabled = true;
					document.forms[0].reasonForIssueRecallNotice.disabled = true;
					document.forms[0].forumthrulegalinitiated.disabled = true;
					document.forms[0].caseregnumber.disabled = true;
					document.forms[0].reasonForFilingSuit.disabled = true;
					document.forms[0].location.disabled = true;
				//	document.forms[0].dateOfPossesion.disabled = true;
					document.forms[0].amountclaimed.disabled = true;
					document.forms[0].legaldate.disabled = true;
			
					document.forms[0].mliCommentOnFinPosition.disabled = true;
					document.forms[0].detailsOfFinAssistance.disabled = true;
					document.forms[0].creditSupport[0].disabled = true;
					document.forms[0].creditSupport[1].disabled = true;
					document.forms[0].placeUnderWatchList[0].disabled = true;
					document.forms[0].placeUnderWatchList[1].disabled = true;
					document.forms[0].remarksOnNpa.disabled = true;
					document.forms[0].bankFacilityDetail.disabled = true;
					
					document.forms[0].subsidyAmt.disabled = true;
					document.forms[0].subsidyDate.disabled = true;
										
					document.forms[0].isSubsidyAdjustedOnDues[0].disabled = true;
					document.forms[0].isSubsidyAdjustedOnDues[1].disabled = true;
					document.forms[0].isSubsidyRcvdAfterNpa[0].disabled = true;
					document.forms[0].isSubsidyRcvdAfterNpa[1].disabled = true;
					document.forms[0].subsidyFlag[0].disabled = true;
					document.forms[0].subsidyFlag[1].disabled = true;
			
			}
			if(flag == 'N'){
					document.forms[0].dateOfRecallNotice.disabled = false;
					
					document.forms[0].dateOfRecallNotice.disabled = false;
					document.forms[0].reasonForIssueRecallNotice.disabled = false;
					document.forms[0].forumthrulegalinitiated.disabled = false;
					document.forms[0].caseregnumber.disabled = false;
					document.forms[0].reasonForFilingSuit.disabled = false;
					document.forms[0].location.disabled = false;
				//	document.forms[0].dateOfPossesion.disabled = false;
					document.forms[0].amountclaimed.disabled = false;
					document.forms[0].legaldate.disabled = false;
					
					document.forms[0].mliCommentOnFinPosition.disabled = false;
					document.forms[0].detailsOfFinAssistance.disabled = false;
					document.forms[0].creditSupport[0].disabled = false;
					document.forms[0].creditSupport[1].disabled = false;
					document.forms[0].placeUnderWatchList[0].disabled = false;
					document.forms[0].placeUnderWatchList[1].disabled = false;
					document.forms[0].remarksOnNpa.disabled = false;
					document.forms[0].bankFacilityDetail.disabled = false;
					
					document.forms[0].subsidyAmt.disabled = false;
					document.forms[0].subsidyDate.disabled = false;
					
					
					document.forms[0].isSubsidyAdjustedOnDues[0].disabled = false;
					document.forms[0].isSubsidyAdjustedOnDues[1].disabled = false;
					document.forms[0].isSubsidyRcvdAfterNpa[0].disabled = false;
					document.forms[0].isSubsidyRcvdAfterNpa[1].disabled = false;
					document.forms[0].subsidyFlag[0].disabled = false;
					document.forms[0].subsidyFlag[1].disabled = false;		
			}
		}
	
		function enableSubsidyRcvd(flag){
		//alert('flag:'+flag);
			if(flag == 'Y'){
				document.forms[0].isSubsidyRcvdAfterNpa[0].disabled = false;
				document.forms[0].isSubsidyRcvdAfterNpa[1].disabled = false;
			}
			if(flag == 'N'){
				document.forms[0].isSubsidyRcvdAfterNpa[0].disabled = true;
				document.forms[0].isSubsidyRcvdAfterNpa[1].disabled = true;
				document.forms[0].isSubsidyRcvdAfterNpa[0].checked = false;
				document.forms[0].isSubsidyRcvdAfterNpa[1].checked = false;
				document.forms[0].isSubsidyAdjustedOnDues[0].disabled = true;
				document.forms[0].isSubsidyAdjustedOnDues[1].disabled = true;
				document.forms[0].isSubsidyAdjustedOnDues[0].checked = false;
				document.forms[0].isSubsidyAdjustedOnDues[1].checked = false;
				document.forms[0].subsidyDate.disbaled = true;
				document.forms[0].subsidyAmt.disbaled = true;
				document.forms[0].subsidyDate.value = '';
				document.forms[0].subsidyAmt.value = '';
			}
		}
		
		function enableSubsidyAdjusted(flag){
		//alert('flag:'+flag);
			if(flag == 'Y'){
				document.forms[0].isSubsidyAdjustedOnDues[0].disabled = false;
				document.forms[0].isSubsidyAdjustedOnDues[1].disabled = false;
			}
			if(flag == 'N'){
				document.forms[0].isSubsidyAdjustedOnDues[0].disabled = true;
				document.forms[0].isSubsidyAdjustedOnDues[1].disabled = true;
				document.forms[0].isSubsidyAdjustedOnDues[0].checked = false;
				document.forms[0].isSubsidyAdjustedOnDues[1].checked = false;
				document.forms[0].subsidyDate.disabled = true;
				document.forms[0].subsidyAmt.disabled = true;
				document.forms[0].subsidyDate.value = '';
				document.forms[0].subsidyAmt.value = '';
			}
		}
		
		function enableChild(flag){
		//alert('flag:'+flag);
			if(flag == 'Y'){
				document.forms[0].subsidyDate.disabled = false;
				document.forms[0].subsidyAmt.disabled = false;
			}
			if(flag == 'N'){
				document.forms[0].subsidyDate.disabled = true;
				document.forms[0].subsidyAmt.disabled = true;
				document.forms[0].subsidyDate.value = '';
				document.forms[0].subsidyAmt.value = '';
			}	
		}
		
		function checkSuitNumber(field){
			var val = field.value;
			if(val > 0){
			
			}else{
				document.forms[0].caseregnumber.value = '';
				alert('Please enter valid suit/case number');
				return false;
			}
		}
		
		function checkRec(){
			document.forms[0].inclusionOfReciept[0].checked = false;
			document.forms[0].inclusionOfReciept[1].checked = false;
			document.forms[0].confirmRecoveryValues[0].checked = false;
			document.forms[0].confirmRecoveryValues[1].checked = false;
			alert('Please confirm or correct recovery details.');
			return false;
		}
		
		function checkClaimedAmount(field){
			var val = field.value;
			if(val > 0){
			
			}else{
				document.forms[0].amountclaimed.value = '';
				alert('Suit filed amount can not be zero.');
				return false;
			}
		}
		
		function onOffFields(){
		if(document.forms[0].subsidyFlag[1].checked == true 
		|| (document.forms[0].subsidyFlag[0].checked == false && document.forms[0].subsidyFlag[1].checked == false)){
		//alert('subFlag:');
			document.forms[0].isSubsidyRcvdAfterNpa[0].checked = false;
			document.forms[0].isSubsidyRcvdAfterNpa[0].value = '';
			document.forms[0].isSubsidyRcvdAfterNpa[1].checked = false;
			document.forms[0].isSubsidyRcvdAfterNpa[1].value = '';
			document.forms[0].isSubsidyAdjustedOnDues[0].checked = false;
			document.forms[0].isSubsidyAdjustedOnDues[0].value = '';
			document.forms[0].isSubsidyAdjustedOnDues[1].checked = false;
			document.forms[0].isSubsidyAdjustedOnDues[1].value = '';
			document.forms[0].subsidyAmt.value = '';
			document.forms[0].subsidyDate.value = '';
		}else if(document.forms[0].subsidyFlag[0].checked == true && document.forms[0].isSubsidyRcvdAfterNpa[1].checked == true){
		//alert('isSubsidyRcvd:');
			document.forms[0].isSubsidyAdjustedOnDues[0].checked = false;
			document.forms[0].isSubsidyAdjustedOnDues[0].value = '';
			document.forms[0].isSubsidyAdjustedOnDues[1].checked = false;
			document.forms[0].isSubsidyAdjustedOnDues[1].value = '';
			document.forms[0].subsidyAmt.value = '';
			document.forms[0].subsidyDate.value = '';
		}else if(document.forms[0].subsidyFlag[0].checked == true && document.forms[0].isSubsidyRcvdAfterNpa[0].checked == true 
							&& document.forms[0].isSubsidyAdjustedOnDues[1].checked == true ){
		//alert('isSubsidyAdjusted:');
			document.forms[0].subsidyAmt.value = '';
			document.forms[0].subsidyDate.value = '';
		
		}else{
		//alert('no problem');
		}
		
		if(document.forms[0].isAcctFraud[1].checked == true 
		|| (document.forms[0].isAcctFraud[0].checked == false && document.forms[0].isAcctFraud[1].checked == false)){
			//disable
		}else if(document.forms[0].isAcctFraud[0].checked == true && document.forms[0].isEnquiryConcluded[1].checked == true){
			//disable
		}
	}
	
	function calPsTotalSanc(){
	var psTotal=0;
	//var landValue=findObj("landValue");	
	var landValue = findObj("asOnDtOfSanctionDtl(LAND)");
	if(landValue!=null && landValue!="")
	{
	var landVal=landValue.value;
	//alert(landVal);
	}
	if (!(isNaN(landVal)) && landVal!="")
	{
		psTotal+=parseFloat(landVal);	
	}

	var bldgValue=findObj("asOnDtOfSanctionDtl(BUILDING)");	
	if(bldgValue!=null && bldgValue!="")
	{
	var bldgVal=bldgValue.value;
	}
	if (!(isNaN(bldgVal)) && bldgVal!="")
	{
		psTotal+=parseFloat(bldgVal);	
	}

	var machineValue=findObj("asOnDtOfSanctionDtl(MACHINE)");	
	if(machineValue!=null && machineValue!="")
	{
	var machineVal=machineValue.value;
	}
	if (!(isNaN(machineVal)) && machineVal!="")
	{
		psTotal+=parseFloat(machineVal);	
	}

	var assetsValue=findObj("asOnDtOfSanctionDtl(OTHER FIXED MOVABLE ASSETS)");	
	if(assetsValue!=null && assetsValue!="")
	{
	var assetsVal=assetsValue.value;
	}
	if (!(isNaN(assetsVal)) && assetsVal!="")
	{
		psTotal+=parseFloat(assetsVal);	
	}

	var currentAssetsValue=findObj("asOnDtOfSanctionDtl(CURRENT ASSETS)");	
	if(currentAssetsValue!=null && currentAssetsValue!="")
	{
	var currentAssetsVal=currentAssetsValue.value;
	}
	if (!(isNaN(currentAssetsVal)) && currentAssetsVal!="")
	{
		psTotal+=parseFloat(currentAssetsVal);	
	}

	var othersValue=findObj("asOnDtOfSanctionDtl(OTHERS)");	
	if(othersValue!=null && othersValue!="")
	{
	var othersVal=othersValue.value;
	}
	if (!(isNaN(othersVal)) && othersVal!="")
	{
		psTotal+=parseFloat(othersVal);	
	}

	document.forms[0].totSecAsOnSanc.value=psTotal; 

}
	
	function calPsTotalNpa(){
		
		var psTotal=0;
	//var landValue=findObj("landValue");	
	var landValue = findObj("asOnDtOfNPA(LAND)");
	if(landValue!=null && landValue!="")
	{
	var landVal=landValue.value;
	}
	if (!(isNaN(landVal)) && landVal!="")
	{
		psTotal+=parseFloat(landVal);
		//alert("land:"+psTotal);		
	}

	var bldgValue=findObj("asOnDtOfNPA(BUILDING)");	
	if(bldgValue!=null && bldgValue!="")
	{
	var bldgVal=bldgValue.value;
	}
	if (!(isNaN(bldgVal)) && bldgVal!="")
	{
		psTotal+=parseFloat(bldgVal);	
		//alert("building:"+psTotal);
	}

	var machineValue=findObj("asOnDtOfNPA(MACHINE)");	
	if(machineValue!=null && machineValue!="")
	{
	var machineVal=machineValue.value;
	}
	if (!(isNaN(machineVal)) && machineVal!="")
	{
		psTotal+=parseFloat(machineVal);
	//alert("machine:"+psTotal);		
	}

	var assetsValue=findObj("asOnDtOfNPA(OTHER FIXED MOVABLE ASSETS)");	
	if(assetsValue!=null && assetsValue!="")
	{
	var assetsVal=assetsValue.value;
	}
	if (!(isNaN(assetsVal)) && assetsVal!="")
	{
		psTotal+=parseFloat(assetsVal);	
		//alert("fixed:"+psTotal);
	}

	var currentAssetsValue=findObj("asOnDtOfNPA(CURRENT ASSETS)");	
	if(currentAssetsValue!=null && currentAssetsValue!="")
	{
	var currentAssetsVal=currentAssetsValue.value;
	}
	if (!(isNaN(currentAssetsVal)) && currentAssetsVal!="")
	{
		psTotal+=parseFloat(currentAssetsVal);
		//alert("current assets:"+psTotal);		
	}

	var othersValue=findObj("asOnDtOfNPA(OTHERS)");	
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
	document.forms[0].totSecAsOnNpa.value=psTotal; 
		
	}
	
	function calPsTotalClaim(){
		
		var psTotal=0;
	//var landValue=findObj("landValue");	
	var landValue = findObj("asOnLodgemntOfCredit(LAND)");
	if(landValue!=null && landValue!="")
	{
	var landVal=landValue.value;
	}
	if (!(isNaN(landVal)) && landVal!="")
	{
		psTotal+=parseFloat(landVal);
		//alert("land:"+psTotal);		
	}

	var bldgValue=findObj("asOnLodgemntOfCredit(BUILDING)");	
	if(bldgValue!=null && bldgValue!="")
	{
	var bldgVal=bldgValue.value;
	}
	if (!(isNaN(bldgVal)) && bldgVal!="")
	{
		psTotal+=parseFloat(bldgVal);	
		//alert("building:"+psTotal);
	}

	var machineValue=findObj("asOnLodgemntOfCredit(MACHINE)");	
	if(machineValue!=null && machineValue!="")
	{
	var machineVal=machineValue.value;
	}
	if (!(isNaN(machineVal)) && machineVal!="")
	{
		psTotal+=parseFloat(machineVal);
	//alert("machine:"+psTotal);		
	}

	var assetsValue=findObj("asOnLodgemntOfCredit(OTHER FIXED MOVABLE ASSETS)");	
	if(assetsValue!=null && assetsValue!="")
	{
	var assetsVal=assetsValue.value;
	}
	if (!(isNaN(assetsVal)) && assetsVal!="")
	{
		psTotal+=parseFloat(assetsVal);	
		//alert("fixed:"+psTotal);
	}

	var currentAssetsValue=findObj("asOnLodgemntOfCredit(CURRENT ASSETS)");	
	if(currentAssetsValue!=null && currentAssetsValue!="")
	{
	var currentAssetsVal=currentAssetsValue.value;
	}
	if (!(isNaN(currentAssetsVal)) && currentAssetsVal!="")
	{
		psTotal+=parseFloat(currentAssetsVal);
		//alert("current assets:"+psTotal);		
	}

	var othersValue=findObj("asOnLodgemntOfCredit(OTHERS)");	
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
	document.forms[0].totSecAsOnClaim.value=psTotal; 
		
	}
	
	function calOSAmt(field){
		//alert(field.value);
		var subsidyAmt = 0;
		if(!(isNaN(field.value)))// && subsidyAmt > 0)
		{
			subsidyAmt = parseFloat(field.value);
		}
		var npaDt = document.getElementById('npaDt').innerText;
		//var npaDt = document.getElementById('npaDate').value;
		//alert("npaDt:"+npaDt);
        var subsidyDt = document.forms[0].subsidyDate.value;
		//alert("subsidyDt:"+subsidyDt);
		var size = document.getElementById('total').value;
		//alert("size:"+size);
		
		var osAmt = 0;
        var npaDayStr;
		var npaMonStr;
		var npaYearStr;
		var subsidyDayStr;
		var subsidyMonStr;
		var subsidyYearStr;        
        
                
		var elem1 = npaDt.split('/');  
		var elem2 = subsidyDt.split('/'); 
		
			    npaDayStr = elem1[0]-1;  
				npaMonStr = elem1[1]-1;  
				npaYearStr = elem1[2]-1;
				
				subsidyDayStr = elem2[0]-1; 
				subsidyMonStr = elem2[1]-1; 
				subsidyYearStr = elem2[2]-1;
			//alert('npaYearStr'+npaYearStr+'  subsidyYearStr'+subsidyYearStr);
			//alert('npaMonStr'+npaMonStr+'  subsidyMonStr'+subsidyMonStr);
			//alert('npaDayStr'+npaDayStr+'  subsidyDayStr'+subsidyDayStr);
			
			
		if(((npaYearStr) > (subsidyYearStr)) || ((npaYearStr == subsidyYearStr) && (npaMonStr > subsidyMonStr)) 
					|| ((npaYearStr == subsidyYearStr) && (npaMonStr == subsidyMonStr && npaDayStr > subsidyDayStr))){
			
					//alert("npa > subsidy credit");
					for(var i = 1; i< size;i++){
						
							var totalDisbAmt = findObj("totalDisbursementAmt(key-"+i+")");
							//alert('totalDisbAmt'+totalDisbAmt.value);
				
							var approvedAmount = findObj("appApprovedAmt(key-"+i+")");
							var pRepayAmt = findObj("tcprincipal(key-"+i+")");
							var repayAmt = 0;
							if (!(isNaN(pRepayAmt.value)) && pRepayAmt.value!=""){
								repayAmt = parseFloat(pRepayAmt.value);
							}
							//alert("subsidyAmt:"+subsidyAmt);
							var os = Math.min(totalDisbAmt.value,approvedAmount.value) - (repayAmt + subsidyAmt);
							
							findObj("tcOsAsOnDateOfNPA(key-"+i+")").value = os;
						
						
					}
		}else{	
			//alert('else');
				for(var j=1;j<size;j++){
					var totalDisbAmt = findObj("totalDisbursementAmt(key-"+j+")");
					//alert('totalDisbAmt'+totalDisbAmt.value);
						if ((isNaN(totalDisbAmt.value)) || totalDisbAmt.value==""){
							//alert("please enter disbursed amount");
							return false;
						}
						
						var approvedAmount = findObj("appApprovedAmt(key-"+j+")");
						var pRepayAmt = findObj("tcprincipal(key-"+j+")");
						//alert('pRepayAmt'+pRepayAmt.value);
						var repayAmt = 0;
						if (!(isNaN(pRepayAmt.value)) && pRepayAmt.value!=""){
							repayAmt = parseFloat(pRepayAmt.value);
						}
						
						var os = Math.min(totalDisbAmt.value,approvedAmount.value) - (repayAmt);
						
						findObj("tcOsAsOnDateOfNPA(key-"+j+")").value = os;
					
				}
		}
            
	} 
	
	function calOutstanding(field){
	//	alert("property:"+field.name);
            
		var len = field.name.length;
		var index = field.name.charAt(len-2);
		
		//alert("index:"+index);
		var totalDisbAmt = findObj("totalDisbursementAmt(key-"+index+")");
		
		var subAmt = 0;
			var subsidyAmt = document.forms[0].subsidyAmt.value;
			if(!isNaN(subsidyAmt) && subsidyAmt > 0){
				subAmt = parseFloat(subsidyAmt);
			}
		
	//	alert("totalDisbAmt:"+totalDisbAmt.name);
        //        alert("totalDisbAmt value:"+totalDisbAmt.value);
                if ((isNaN(totalDisbAmt.value)) || totalDisbAmt.value==""){
                    alert("please enter disbursed amount");
                    return false;
                }
    
                var approvedAmount = findObj("appApprovedAmt(key-"+index+")");
           //     alert("approvedAmount:"+approvedAmount.name);
           //     alert("approvedAmount value:"+approvedAmount.value);
                
                var pRepayAmt = findObj("tcprincipal(key-"+index+")");
           //     alert("pRepayAmt value:"+pRepayAmt.value);
                
                var repayAmt = 0;
                 if (!(isNaN(pRepayAmt.value)) && pRepayAmt.value!=""){
                    repayAmt = parseFloat(pRepayAmt.value);
                 }
				 
		var npaDt = document.getElementById('npaDt').innerText;
	//	alert("npaDt:"+npaDt);
        var subsidyDt = document.forms[0].subsidyDate.value;
	//	alert("subsidyDt:"+subsidyDt);
	//	var size = document.getElementById('total').value;
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
                
                findObj("tcOsAsOnDateOfNPA(key-"+index+")").value = os;
				
	}
	
	function enableClaimFlagsTc(field){
		var val = field.value;
		
			var name = field.name;
			var len = name.length;
			var index = name.charAt(len-2);
			//alert(index);
			var obj = findObj("claimFlagsTc(key-"+index+")");
			var cg = findObj("cgpandetails(key-"+index+")");
		if(val <= 0 || isNaN(val)){
			alert('Please choose NO for cgpan:'+cg.value);
			obj[0].checked = false;
		}else{
			alert('Please choose YES for cgpan:'+cg.value);
			obj[1].checked = false;
		}
	}
	
	function enableClaimFlagsWc(field){
		var val = field.value;
		
			var name = field.name;
			var len = name.length;
			var index = name.charAt(len-2);
			//alert(index);
			var obj = findObj("claimFlagsWc(key-"+index+")");
			var cg = findObj("wcCgpan(key-"+index+")");
		if(val <= 0 || isNaN(val)){
			alert('Please choose NO for cgpan:'+cg.value);
			obj[0].checked = false;
		}else{
			alert('Please choose YES for cgpan:'+cg.value);
			obj[1].checked = false;
		}
	}
	
	function messageTc(field,flag){
		var name = field.name;
		var len = name.length;
		var index = name.charAt(len-2);
		var f = findObj("tcOsAsOnLodgementOfClaim(key-"+index+")");
		
		if(flag == 'Y' && (f.value <= 0 || isNaN(f.value))){
			alert('Please enter valid outstanding amount.');
			f.value = '';
		}
		
	}
	
	function messageWc(field,flag){
		var name = field.name;
		var len = name.length;
		var index = name.charAt(len-2);
		var f = findObj("wcOsAsOnLodgementOfClaim(key-"+index+")");
		
		if(flag == 'Y' && (f.value <= 0 || isNaN(f.value))){
			alert('Please enter valid outstanding amount.');
			f.value = '';
		}
		
	}
</script>

<html:form action="addFirstClaimsPageDetails.do?method=addFirstClaimsPageDetails" method="POST" focus="<%=focusField%>" enctype="multipart/form-data">
<html:errors />
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
      		<A HREF="javascript:submitForm('helpEnterClaimFirstApplication.do')">
      	        HELP</A>
      </DIV>    
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
  		<table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr> 
                    <td colspan="4" class="SubHeading"><br> <div align="right"><bean:message key="subheading1"/></div></td>
                  </tr>
                  <tr> 
                    <td colspan="4" class="SubHeading"><br> <div align="center"><bean:message key="subheading2"/></div></td>
                  </tr>
                  <tr> 
                    <td colspan="4" class="SubHeading"><br> <div align="center"><bean:message key="subheading3"/></div></td>
                  </tr>
                  <tr> 
                    <td colspan="4" class="SubHeading"><br><bean:message key="subheading4"/><br><br></td>
                  </tr>
                  <tr> 
                    <td colspan="8"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr> 
                          <td width="15%" height="20" class="Heading">&nbsp;<bean:message key="claimtitle"/></td>
                          <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        </tr>
                        <tr> 
                          <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                        </tr>
                      </table></td>
                  </tr>
                  <tr> 
                    <td class="SubHeading" colspan="4">&nbsp;<bean:message key="dtlsOfOperatingOfficeAndLendingBranch"/></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp; <bean:message key="memberId"/></div></td> 
  
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;<bean:write property="memberDetails.memberId" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp; <bean:message key="lendingbankname"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="memberDetails.memberBankName" name="cpTcDetailsForm"/></div></td>
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="city"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="memberDetails.city" name="cpTcDetailsForm"/></div></td>
                  </tr>
				  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="completeaddress"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;
						<bean:write property="memberDetails.memberAddress" name="cpTcDetailsForm"/>
					</div></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="district"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="memberDetails.district" name="cpTcDetailsForm"/></div></td>
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="state"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="memberDetails.state" name="cpTcDetailsForm"/></div></td>
                  </tr>
				  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="eMail"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp; 
						<bean:write property="memberDetails.email" name="cpTcDetailsForm"/>
						<%--<html:text property="memberDetails.email" name="cpTcDetailsForm" />--%>
						</div></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="telephone"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;
						<bean:write property="memberDetails.telephone" name="cpTcDetailsForm"/>
						<%--<html:text property="memberDetails.telephone" name="cpTcDetailsForm" />--%>
					</div></td>
                  </tr>
				  <!--<tr> 
                    <td class="SubHeading" colspan="4">&nbsp;Details Of Dealing Officer</td>
                  </tr>-->
				  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;Dealing Officer Name<font color="#FF0000" size="2">*</font></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;
						<html:text property="dealingOfficerName" name="cpTcDetailsForm" />
					</div></td>
                  </tr>
				  
                  
  				  <tr> 
                    <td class="SubHeading" colspan="4"> &nbsp;<bean:message key="borrower/unitdetails"/></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="name"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;<bean:write property="borrowerDetails.borrowerName" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="completeaddress"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;<bean:write property="borrowerDetails.address" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="district"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="borrowerDetails.district" name="cpTcDetailsForm"/></div></td>
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="state"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="borrowerDetails.state" name="cpTcDetailsForm"/></div></td>
                    <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="pin"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;<bean:write property="borrowerDetails.pinCode" name="cpTcDetailsForm"/></div></td>
					</tr>
                  </tr>
					<tr>
						<td class="ColumnBackground"> <div align="left">&nbsp;Whether the Unit Assisted is an MICRO as per the MSMED Act 2006 definition of MSE:<font color="#FF0000" size="2">*</font></div></td> 
						 <td class="TableData" colspan="3"> <div align="left"> &nbsp;
							<html:radio name="cpTcDetailsForm" property="microCategory" value="Y" ><bean:message key="yes"/></html:radio>
							<html:radio name="cpTcDetailsForm" property="microCategory" value="N" ><bean:message key="no"/></html:radio>
						 </div></td>
				  </tr>				  
				  <tr> 
                    <td class="SubHeading" colspan="4"> &nbsp;<bean:message key="statusofaccounts"/></td>
                  </tr>                  
                  <tr> 
                  
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="dateofnpa"/></div></td>                    
                    <%                    
                    npaClassifiedDt = "npaDetails.".trim() +ClaimConstants.NPA_CLASSIFIED_DT;
                    
                    %>
                    <td class="TableData" colspan="3"> <div align="left" id="npaDt"> &nbsp;<bean:write property="<%=npaClassifiedDt%>" name="cpTcDetailsForm"/> </div>
					<input type="hidden" id="npaDate" value="<%=npaClassifiedDt%>" />	
					</td>
                  </tr>
                  <tr>
						<td class="ColumnBackground"> <div align="left">&nbsp;Wilful defaulter<font color="#FF0000" size="2">*</font></div></td> 
						 <td class="TableData" colspan="3"> <div align="left"> &nbsp;
							<html:radio name="cpTcDetailsForm" property="wilfullDefaulter" value="Y" ><bean:message key="yes"/></html:radio>
							<html:radio name="cpTcDetailsForm" property="wilfullDefaulter" value="N" ><bean:message key="no"/></html:radio>
						 </div></td>
				  </tr>
				  <tr>
						<td class="ColumnBackground"> <div align="left">&nbsp;Has the account been classified as fraud<font color="#FF0000" size="2">*</font></div></td> 
						 <td class="TableData" colspan="3"> <div align="left"> &nbsp;
							<html:radio name="cpTcDetailsForm" property="isAcctFraud" value="Y" onclick="enableDisbaleChild('Y');"><bean:message key="yes"/></html:radio>
							<html:radio name="cpTcDetailsForm" property="isAcctFraud" value="N" onclick="enableDisbaleChild('N');"><bean:message key="no"/></html:radio>
						 </div></td>
				  </tr>
				  <tr>
						<td class="ColumnBackground"> <div align="left">&nbsp;Internal and/or external enquiry has been concluded</div></td> 
						 <td class="TableData" colspan="3"> <div align="left"> &nbsp;
						 <logic:equal name="cpTcDetailsForm" property="isAcctFraud" value="Y">
							<html:radio name="cpTcDetailsForm" property="isEnquiryConcluded" value="Y" onclick="enableDisbaleEnquiryChild('Y');"><bean:message key="yes"/></html:radio>
							<html:radio name="cpTcDetailsForm" property="isEnquiryConcluded" value="N" onclick="enableDisbaleEnquiryChild('N');"><bean:message key="no"/></html:radio>
						 </logic:equal>
						 <logic:notEqual name="cpTcDetailsForm" property="isAcctFraud" value="Y">
							<html:radio name="cpTcDetailsForm" property="isEnquiryConcluded" value="Y" onclick="enableDisbaleEnquiryChild('Y');" disabled="true"><bean:message key="yes"/></html:radio>
							<html:radio name="cpTcDetailsForm" property="isEnquiryConcluded" value="N" onclick="enableDisbaleEnquiryChild('N');" disabled="true"><bean:message key="no"/></html:radio>
						 </logic:notEqual>
						 </div></td>
				  </tr>
				  <tr>
						<td class="ColumnBackground"> <div align="left">&nbsp;Involvement of staff of MLI has been reported</div></td> 
						 <td class="TableData" colspan="3"> <div align="left"> &nbsp;
						 <logic:equal name="cpTcDetailsForm" property="isEnquiryConcluded" value="Y">
							<html:radio name="cpTcDetailsForm" property="isMLIInvolved" value="Y" onclick="enableDisableFields('Y');"><bean:message key="yes"/></html:radio>
							<html:radio name="cpTcDetailsForm" property="isMLIInvolved" value="N" onclick="enableDisableFields('N');"><bean:message key="no"/></html:radio>
						 </logic:equal>
						 <logic:notEqual name="cpTcDetailsForm" property="isEnquiryConcluded" value="Y">
							<html:radio name="cpTcDetailsForm" property="isMLIInvolved" value="Y" onclick="enableDisableFields('Y');" disabled="true"><bean:message key="yes"/></html:radio>
							<html:radio name="cpTcDetailsForm" property="isMLIInvolved" value="N" onclick="enableDisableFields('N');" disabled="true"><bean:message key="no"/></html:radio>
						 </logic:notEqual>
						 </div></td>
				  </tr>
                  <%
                  reasonForTurningNPA = "npaDetails.".trim() + ClaimConstants.REASONS_FOR_TURNING_NPA;
                  %>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="reasonfornpa"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp; <bean:write property="<%=reasonForTurningNPA%>" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  
                  
                  <tr colspan="3"> 
		                      <td class="ColumnBackground"> <div align="left">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="dateofissueofrecallnotice"/></div></td>
		                      <td class="TableData">
		    		  	<html:text property="dateOfRecallNotice" name="cpTcDetailsForm" maxlength="10" size="10"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.dateOfRecallNotice')" align="center" >
		    		  </td>
		    		  <td class="ColumnBackground">&nbsp;<bean:message key="uploadfile"/></td>
 		    		  <td class="TableData">&nbsp;<html:file property="recallnoticefilepath" name="cpTcDetailsForm" disabled="true"/></td>
                  </tr>  
				<tr>
					<td class="ColumnBackground">&nbsp;Provide satisfactory reason for issuing recall notice prior to NPA date</td>
				  	<td class="TableData" colspan="3">
						<html:textarea name="cpTcDetailsForm" property="reasonForIssueRecallNotice" cols="60"/>
				  	</td>
				</tr>
  				<tr>
  				<td><br></tr>
  				</tr>
  				
  				<tr>
				    <td colspan="4" class="SubHeading">&nbsp;<bean:message key="detailsoflegalproceedings"/></td>
				</tr>
				<tr>
				                   <!-- <td colspan="3" class="SubHeading">
									<table width="100%" border="0" cellspacing="1" cellpadding="0"> -->
				  					<tr>
				  						<td class="ColumnBackground">						
				  						&nbsp;<bean:message key="forumthruwhichlegalproceedingsinitiated"/><font color="#FF0000" size="2">*</font></td>						
				  						<td class="TableData"><html:select property="forumthrulegalinitiated" name="cpTcDetailsForm" onchange="setCPOthersEnabled();">
				  								<html:option value="">Select</html:option>
				  								<html:option value="Civil Court">Civil Court</html:option>
				  								<html:option value="DRT">DRT</html:option>
				  								<html:option value="LokAdalat">Lok Adalat</html:option>
				  								<html:option value="Revenue Recovery Autority">Revenue Recovery Autority</html:option>
				  								<html:option value="Securitisation Act, 2002">Securitisation Act, 2002</html:option>
				  								<html:option value="Others">Others</html:option>
				  							</html:select>
				  						</td>						
				  						<td class="ColumnBackground">&nbsp;<bean:message key="otherforum"/></td>
				  						<td class="TableData"><html:text property="otherforums" name="cpTcDetailsForm" disabled="true"/></td>
				  					</tr>
				  					<tr>
										<td class="ColumnBackground">&nbsp;<bean:message key="suit/caseregnumber"/><font color="#FF0000" size="2">*</font></td>
				  						<td class="TableData">						
				  						<html:text property="caseregnumber" name="cpTcDetailsForm" onkeypress="return decimalOnly(this, event,13)" onchange="checkSuitNumber(this);"/></td>
				  						
				  						<td class="ColumnBackground">&nbsp;<bean:message key="filingdate"/><font color="#FF0000" size="2">*</font></td>
				  						<td class="TableData"><html:text property="legaldate" maxlength="10" size="10" name="cpTcDetailsForm"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.legaldate')" align="center"></td>
				  					</tr>
									<tr>
										<td class="ColumnBackground">&nbsp;Provide satisfactory reason for filing suit before NPA date</td>
				  						<td class="TableData" colspan="3">
											<html:textarea name="cpTcDetailsForm" property="reasonForFilingSuit" cols="60"/>
				  						</td>
				  					</tr>
									<tr>
										<td class="ColumnBackground">&nbsp;Date of possession of assets under sarfaesi act<font color="#FF0000" size="2">*</font></td>
				  						<td class="TableData" colspan="3">
											<html:text name="cpTcDetailsForm" property="assetPossessionDate" size="10" maxlength="10"/>
											<img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.assetPossessionDate')" align="center" id="assetPossessionDateCal">
				  						</td>
				  					</tr>
				  					<tr>
				                       
				                         <td class="ColumnBackground">&nbsp;<bean:message key="location"/><font color="#FF0000" size="2">*</font></td>
				  						<td class="TableData" colspan="3">
				  						<html:text property="location" name="cpTcDetailsForm" maxlength="50"/>
				  						</td>
				  					</tr>
				  					<tr>
				                          <td class="ColumnBackground">&nbsp;<bean:message key="amntClaimedInTheSuit"/><font color="#FF0000" size="2">*</font></td>
				  						<td class="TableData" colspan="3">
				  							<html:text property="amountclaimed" name="cpTcDetailsForm" maxlength="16" size="10" onchange="checkClaimedAmount(this);" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/> in Rs.
				  						</td>
										
				  					</tr>
									<TR colspan="3">
										<td class="ColumnBackground">&nbsp;Any Attachments(Legal file)<font color="#FF0000" size="2">*</font></td>
										<td class="TableData" colspan="3">
											<html:file property="legalAttachmentPath" name="cpTcDetailsForm"/>
										</td>
									</TR>
				  					<%--<tr>
				                          <td class="ColumnBackground">&nbsp;<bean:message key="currentStatus"/></td>
				  						<td class="TableData" width="20%" colspan="3">
				  							<html:text property="currentstatusremarks" name="cpTcDetailsForm" maxlength="4000"/>
				  						</td>
				  					</tr>
				  					<tr>
				                          <td class="ColumnBackground">&nbsp;<bean:message key="recoveryProceedingsConcluded"/></td>
				  						<td class="TableData" width="20%" colspan="3">
				  							<html:radio property="proceedingsConcluded"  name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_YES_FLAG%>"><bean:message key="otsyes"/></html:radio> 
				  							<html:radio property="proceedingsConcluded" name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_NO_FLAG%>"><bean:message key="otsno"/></html:radio>
				  						</td>
				  					</tr> 
									--%>
				                   <!--   </table></td> -->
                  </tr>
                  <logic:notEqual property="tcCounter" value="0" name="cpTcDetailsForm">  				
  				<tr>
  				<td><br></td>
  				</tr>

				<tr> 
				  <td  class="SubHeading">&nbsp;Subsidy Details</td>
			 </tr>
			 
												
								<TR>
									<TD colspan="1" class="ColumnBackground">&nbsp;Does the project covered under CGTMSE guarantee,involve any subsidy?
									</TD>                                           
                                    <TD colspan="3" class="tableData" colspan="3">
										<html:radio name="cpTcDetailsForm" value="Y" property="subsidyFlag" onclick="enableSubsidyRcvd('Y');"><bean:message key="yes"/></html:radio>&nbsp; 
										<html:radio name="cpTcDetailsForm" value="N" property="subsidyFlag" onclick="enableSubsidyRcvd('N');"><bean:message key="no" /></html:radio>
									</TD>
								</TR>
								<TR>
									<TD colspan="1" class="ColumnBackground">&nbsp;Has the subsidy been received after NPA?
									</TD>
									<TD colspan="3" class="tableData" colspan="3">
										<logic:equal name="cpTcDetailsForm" property="subsidyFlag" value="Y">
										<html:radio name="cpTcDetailsForm" value="Y" property="isSubsidyRcvdAfterNpa" onclick="enableSubsidyAdjusted('Y');"><bean:message key="yes"/></html:radio>&nbsp; 
										<html:radio name="cpTcDetailsForm" value="N" property="isSubsidyRcvdAfterNpa" onclick="enableSubsidyAdjusted('N');"><bean:message key="no"/></html:radio>
										</logic:equal>
										<logic:notEqual name="cpTcDetailsForm" property="subsidyFlag" value="Y">
										<html:radio name="cpTcDetailsForm" value="Y" property="isSubsidyRcvdAfterNpa" disabled="true" onclick="enableSubsidyAdjusted('Y');"><bean:message key="yes"/></html:radio>&nbsp; 
										<html:radio name="cpTcDetailsForm" value="N" property="isSubsidyRcvdAfterNpa" disabled="true" onclick="enableSubsidyAdjusted('N');"><bean:message key="no"/></html:radio>
										</logic:notEqual>
									</TD>
								</TR>
								<TR>
									<TD colspan="1" class="ColumnBackground" >&nbsp;Has the subsidy been adjusted against principal/interest overdues?
									</TD>
									<TD colspan="3" class="tableData" colspan="3"><!-- on yes below two fields should be enabled-->
									<logic:equal name="cpTcDetailsForm" property="isSubsidyRcvdAfterNpa" value="Y">
										<html:radio name="cpTcDetailsForm" value="Y" property="isSubsidyAdjustedOnDues" onclick="enableChild('Y');"><bean:message key="yes"/></html:radio>&nbsp; 
										<html:radio name="cpTcDetailsForm" value="N" property="isSubsidyAdjustedOnDues" onclick="enableChild('N');"><bean:message key="no"/></html:radio>
									</logic:equal>
									<logic:notEqual name="cpTcDetailsForm" property="isSubsidyRcvdAfterNpa" value="Y">
										<html:radio name="cpTcDetailsForm" value="Y" property="isSubsidyAdjustedOnDues" disabled="true"  onclick="enableChild('Y');"><bean:message key="yes"/></html:radio>&nbsp; 
										<html:radio name="cpTcDetailsForm" value="N" property="isSubsidyAdjustedOnDues" disabled="true"  onclick="enableChild('N');"><bean:message key="no"/></html:radio>
									</logic:notEqual>
									</TD>												
								</TR>
			 
						<tr>
							<td class="ColumnBackground">&nbsp;Subsidy Credit Date</td>
							<logic:equal name="cpTcDetailsForm" property="isSubsidyAdjustedOnDues" value="Y">
							<td class="TableData"><html:text property="subsidyDate" maxlength="10" name="cpTcDetailsForm" size="10"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.subsidyDate')" align="center"></td>
							</logic:equal>
							<logic:notEqual name="cpTcDetailsForm" property="isSubsidyAdjustedOnDues" value="Y">
							<td class="TableData"><html:text property="subsidyDate" maxlength="10" name="cpTcDetailsForm" disabled="true" size="10"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.subsidyDate')" align="center"></td>
							</logic:notEqual>
							<td class="ColumnBackground">&nbsp;Subsidy Amount</td>
							<logic:equal name="cpTcDetailsForm" property="isSubsidyAdjustedOnDues" value="Y">
							<td class="TableData"><html:text property="subsidyAmt" name="cpTcDetailsForm" onchange="calOSAmt(this);" size="10" maxlength="10"/></td>
							</logic:equal>
							<logic:notEqual name="cpTcDetailsForm" property="isSubsidyAdjustedOnDues" value="Y">
							<td class="TableData"><html:text property="subsidyAmt" name="cpTcDetailsForm" disabled="true" onchange="calOSAmt(this);" size="10" maxlength="10"/></td>
							</logic:notEqual>
						</tr>
				
				
  	             <tr> 
                    <td colspan="4" class="SubHeading">&nbsp;<bean:message key="termloancompositeloandtls"/></td>
                  </tr>
  				       <tr>
                    <td colspan="8" class="SubHeading"><table width="100%" border="0" cellspacing="1" cellpadding="0">
                        <tr>
                          <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="sNo"/></td>
                          <td class="ColumnBackground" rowspan="2" width="15%"><div align="center">&nbsp;<bean:message key="cgpan"/></td>
                          <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="dateoflastdisbursement"/><font color="#FF0000" size="2">*</font></td>
						  <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;Total Amnt Disbursed(Rs.)<font color="#FF0000" size="2">*</font></td>
                          <td class="ColumnBackground" colspan="2"><div align="center" >&nbsp;<bean:message key="repayments"/><font color="#FF0000" size="2">*</font></td>
                          <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="outstandingasondateofnpa"/><font color="#FF0000" size="2">*</font></td>
  						<td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="outstandingstatedinthecivilsuit"/><font color="#FF0000" size="2">*</font></td>
  						<td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="osAsOnLodgementOfClaim"/><font color="#FF0000" size="2">*</font></td>
						<td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;Do you want to claim for this cgpan?<font color="#FF0000" size="2">*</font></td>
					  </tr>
  					  <tr>
  						<td class="ColumnBackground" width="15%"><div align="center">Principal<font color="#FF0000" size="2">*</font></div></td>
  						<td class="ColumnBackground" width="15%"><div align="center"><bean:message key="interestandothercharges"/><font color="#FF0000" size="2">*</font></div></td>
  					  </tr>
  					  <% int i=1;%>
  					  <logic:iterate property="tcCgpansVector" id="object" name="cpTcDetailsForm" scope="session">			  					  
  					  <% java.util.HashMap mp = (java.util.HashMap)object;  					  
  					     cgpan = (java.lang.String)mp.get(ClaimConstants.CLM_CGPAN); 
  					     dsbrsDt = (java.util.Date)mp.get(ClaimConstants.CLM_LAST_DISBURSEMENT_DT);
  					     java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
  					     String dateStr = "";
  					     if(dsbrsDt != null)
  					     {
  					          dateStr = sdf.format(dsbrsDt);
  					     }
  					     Double repaidAmnt = (Double)mp.get(ClaimConstants.TOTAL_AMNT_REPAID);
  					     if(repaidAmnt != null)
  					     {  					         
  					         repaidStr = repaidAmnt.toString();
  					     }
  					     else  					     
  					     {
  					         repaidStr = "0.0";
  					     }
						 Double approvedAmt = (Double)mp.get(ClaimConstants.CLM_APPLICATION_APPRVD_AMNT);
						 if(approvedAmt != null){
							approvedAmtStr = approvedAmt.toString();
						 }else{
							approvedAmtStr = "0.0";
						 }
						 //out.println("approved amount:"+approvedAmtStr);
  					     hiddencgpan = "cgpandetails(key-"+i+")";	

						Double tcPrincipalRepayAmt = (java.lang.Double)mp.get("TCPRINREPAMT");
						Double tcInterestRepayAmt = (java.lang.Double)mp.get("TCINTREPAMT");
						Double tcOsAsOnNpa = (java.lang.Double)mp.get("TCPRINNPAOSAMT");
						if(tcPrincipalRepayAmt != null){
							//convert into stringval;
						}
						if(tcInterestRepayAmt != null){
							//convert into stringval;
						}
						if(tcOsAsOnNpa != null){
							//convert into stringval;
						}
						
  					  %>
  					          <html:hidden property="<%=hiddencgpan%>" name="cpTcDetailsForm" value="<%=cgpan%>"/>
  					  <tr>					  
  						  <td class="TableData">&nbsp;<%=i%></td>
  						  <td class="TableData">&nbsp;<%= cgpan %></td>
  						  <td class="TableData"><div align="center">
  						  <%
  						  dsbrsmntdt = "lastDisbursementDate(key-"+i+")";						  
  						  %>
						  
  						  <html:text property="<%=dsbrsmntdt%>" maxlength="10" size="10" name="cpTcDetailsForm"/><!-- <img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.<%=dsbrsmntdt%>')" align="center"> -->
  						  </div></td>
						  <td class="TableData"><div align="center">
  						  <%
  						  dsbrsmntamt = "totalDisbursementAmt(key-"+i+")";						  
  						  %>
						  <%
  						  appApprovedAmt = "appApprovedAmt(key-"+i+")";						  
  						  %>
  						  <html:text property="<%=dsbrsmntamt%>" maxlength="10" size="10" name="cpTcDetailsForm" onchange="calOutstanding(this);"/><!-- <img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.<%=dsbrsmntdt%>')" align="center"> -->
  						  <html:hidden property="<%=appApprovedAmt%>" name="cpTcDetailsForm"/>
						  </div></td>
  						  <%
  						     principal="tcprincipal(key-"+i+")";						  
  						  %>
  						  <td class="TableData"><div align="center">
  							<html:text property="<%=principal%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calOutstanding(this);"/>
  						  </div></td>
  						  <%
  						    interestCharges="tcInterestCharge(key-"+i+")";
  						  %>
  						  <td class="TableData"><div align="center">
  							<html:text property="<%=interestCharges%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						  </div></td>
  						  <%
  						   osAsOnNpa="tcOsAsOnDateOfNPA(key-"+i+")";
  						  %>
  						  <td class="TableData"><div align="center">
  							<html:text property="<%=osAsOnNpa%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" readonly="true"/>
  						  </div></td>
  						  <%
  						   osAsStatedinCivilSuit="tcOsAsStatedInCivilSuit(key-"+i+")";
  						  %>
  						  <td class="TableData"><div align="center">
  							<html:text property="<%=osAsStatedinCivilSuit%>" maxlength="16" size="10" name="cpTcDetailsForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						  </div></td>
  						  <%
  						    osAsOnLodgementOfClm="tcOsAsOnLodgementOfClaim(key-"+i+")";
  						  %>
						  <%
  						    claimFlagTc="claimFlagsTc(key-"+i+")";
  						  %>
  						  <td class="TableData"><div align="center">
							
  							<html:text property="<%=osAsOnLodgementOfClm%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
							
							
						</div></td>
						
						<td class="TableData"><div align="center">
						
							<html:radio name="cpTcDetailsForm" property="<%=claimFlagTc%>" value="Y" onclick="messageTc(this,'Y');"/><bean:message key="yes"/>&nbsp;
							<html:radio name="cpTcDetailsForm" property="<%=claimFlagTc%>" value="N" onclick="messageTc(this,'N');"/><bean:message key="no"/>
						
						</div></td>
  					  </tr>
					  
  					  <%i++;%>
  					  </logic:iterate>
  					  </table></td>
                  </tr>
				  <tr>
					<td><input type="hidden" id="total" value="<%=i%>" /></td>
				  </tr>
  				<tr>
  					<td colspan="4" class="SubHeading">
  						&nbsp;<bean:message key="mentiononlyprincipaloutstanding"/></td>
  				</tr>
  				</logic:notEqual>
  				 
  				<tr>
  				<td><br></td>
  				</tr>
  				
  		    <logic:notEqual property="wcCounter" value="0" name="cpTcDetailsForm">
  	            <tr> 
                    <td colspan="4" class="SubHeading">&nbsp;<bean:message key="workingcapitaldetails"/></td>
                  </tr>
  				<tr>
                    <td colspan="5" class="SubHeading"><table width="100%" border="0" cellspacing="1" cellpadding="0">
                        <tr>
                          <td class="ColumnBackground"><div align="center">&nbsp;<bean:message key="sNo"/></td>
                          <td class="ColumnBackground" width="15%"><div align="center">&nbsp;<bean:message key="cgpan"/></td>
                          <td class="ColumnBackground" ><div align="center">&nbsp;<bean:message key="oswcasonthedateofnpa"/><font color="#FF0000" size="2">*</font></td>
  						<td class="ColumnBackground" ><div align="center">&nbsp;<bean:message key="oswcstatedinthecivilsuit"/><font color="#FF0000" size="2">*</font></td>
  						<td class="ColumnBackground" ><div align="center">&nbsp;<bean:message key="oswcasonthedateoflodgementofclaim"/><font color="#FF0000" size="2">*</font></td>
						<td class="ColumnBackground"><div align="center">&nbsp;Do you want to claim for this cgpan?<font color="#FF0000" size="2">*</font></td>
					  </tr>  					  
  					  <% int j = 1; %>
  					  <logic:iterate property="wcCgpansVector" id="object" name="cpTcDetailsForm">
  					  <% 
  					    // wccgpan = (java.lang.String)object; 
						 java.util.Map m = (java.util.HashMap)object;
						  wccgpan = (String)m.get(ClaimConstants.CLM_CGPAN);
  					     hidencgpan = "wcCgpan(key-"+j+")";
						Double wcOsAsOnNpa = (java.lang.Double)m.get("WCPRINNPAOSAMT");
						if(osAsOnNpa != null){
							
						}
  					  %>
  					  <html:hidden property="<%=hidencgpan%>" name="cpTcDetailsForm" value="<%=wccgpan%>"/>
  					  <tr>
  						  <td class="TableData">&nbsp;<%=j%></td>
  						  <td class="TableData">&nbsp;<%=wccgpan%></td>
  						  <td class="TableData"><div align="center">
  						  <%
  						    wcAsOnNPA = "wcOsAsOnDateOfNPA(key-"+j+")";
  						  %>
  						   <html:text property="<%=wcAsOnNPA%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" readonly="true"/></div>
  						  </td>
  						  <td class="TableData"><div align="center">
  						  <%
  						    wcOsAsOnInCivilSuit = "wcOsAsStatedInCivilSuit(key-"+j+")";
  						  %>
  						  <html:text property="<%=wcOsAsOnInCivilSuit%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></div>
  						  </td>
  						  <td class="TableData"><div align="center">
  						  <%
  						    wcOsAtLdgmntClm= "wcOsAsOnLodgementOfClaim(key-"+j+")";
  						  %>
  						  <html:text property="<%=wcOsAtLdgmntClm%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						  </div>
  						  </td>
						  <%
  						    claimFlagWc="claimFlagsWc(key-"+j+")";
  						  %>
						  <td class="TableData"><div align="center">
						
							<html:radio name="cpTcDetailsForm" property="<%=claimFlagWc%>" value="Y" onclick="messageWc(this,'Y');"/><bean:message key="yes"/>&nbsp;
							<html:radio name="cpTcDetailsForm" property="<%=claimFlagWc%>" value="N" onclick="messageWc(this,'N');"/><bean:message key="no"/>
						
						</div></td>
  					  </tr>
  					  <%j++;%>
  					  </logic:iterate>
  					  </table></td>
                  </tr>
  				<tr>
  					<td colspan="4" class="SubHeading">
  						&nbsp;<bean:message key="mentionamountincludinginterest"/>
  					</td>
  				</tr>
				
  			<%--	<tr>
  					<td class="ColumnBackground">&nbsp;<bean:message key="dateofreleaseofwc"/></td>
  					<td class="TableData" colspan="3">
  						<html:text property="dateOfReleaseOfWC" maxlength="10" name="cpTcDetailsForm"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.dateOfReleaseOfWC')" align="center">
  					</td>
  				</tr> 
				--%>
  				</logic:notEqual>
  				<tr>
  				<td><br></tr>
  				<tr> 
                    <td colspan="4" class="SubHeading">&nbsp;<bean:message key="recoverymadefromborrower"/></td>
                  </tr>
  				<tr> 
  					<td colspan="7"><table width="100%" border="0" cellspacing="1" cellpadding="0">
  						<tr class="ColumnBackground">
  							<td rowspan="2"><div align="center"><bean:message key="SerialNumber"/></div></td>
  							<td rowspan="2" width="15%"><div align="center"><bean:message key="cgpan"/></div></td>
  							<td colspan="2"><div align="center"><bean:message key="termloancompositloaninrs"/><font color="#FF0000" size="2">*</font></div></td>
  							<td colspan="2"><div align="center"><bean:message key="workingcapitalinrs"/><font color="#FF0000" size="2">*</font></div></td>
  							<td rowspan="2"><div align="center"><bean:message key="modeofrecovery"/><font color="#FF0000" size="2">*</font></div></td>
  						</tr>
  						<tr class="ColumnBackground">
  							<td><div align="center">Principal<font color="#FF0000" size="2">*</font></div></td>
  							<td><div align="center"><bean:message key="interestandothercharges"/><font color="#FF0000" size="2">*</font></div></td>
  							<td><div align="center"><bean:message key="amountincludinginterest"/><font color="#FF0000" size="2">*</font></div></td>
  							<td><div align="center"><bean:message key="othercharges"/><font color="#FF0000" size="2">*</font></div></td>
  						</tr>
  						<%int k = 1; %>
  					<logic:iterate property="cgpansVector" id="object" name="cpTcDetailsForm" scope="session">
  					<% 
  					  cgpantodisplay = (java.lang.String)object; 					
  					  hiddencgpan = "cgpandetails(recovery#key-"+k+")";					  					  
  					%>
  					<html:hidden property="<%=hiddencgpan%>" name="cpTcDetailsForm" value="<%=cgpantodisplay%>"/>
  					  <tr>
  						<td class="TableData"><div align="center">&nbsp;<%=k%></div></td>
  						<td class="TableData"><div align="center">&nbsp;<%=cgpantodisplay%></div></td>
  						<%
  						   tcPrincipal1="cgpandetails(tcprincipal$recovery#key-"+k+")";
  						%>
  						<td class="TableData"><div align="center">  						
  						<%
  						if((cgpantodisplay.indexOf(tcfield)) >= 0)
  						{
  						%>
  						<html:text property="<%=tcPrincipal1%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						<%
  						}
  						%>
  						<%
  						   tcInterestCharges1 = "cgpandetails(tcInterestCharges$recovery#key-"+k+")";
  						%>
  						<td class="TableData"><div align="center">  						
						<%
						if((cgpantodisplay.indexOf(tcfield)) >= 0)
						{
						%>
  						<html:text property="<%=tcInterestCharges1%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						<%
  						}
  						%>  						
  						</div></td>
  						<%
  						   wcAmount1 = "cgpandetails(wcAmount$recovery#key-"+k+")";
  						%>
  						<td class="TableData"><div align="center">  						
  						<%
  						
  						if((cgpantodisplay.indexOf(wcfield)) >= 0)
  						{
  						%>  						
  						<html:text property="<%=wcAmount1%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						<%
  						}
  						else if((cgpantodisplay.indexOf(R1field)) >= 0)
  						{
  						%>  						
  						<html:text property="<%=wcAmount1%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						<%
  						}
  						%>  						  						
  						</div></td>
  						<%
  						   wcOtherCharges1="cgpandetails(wcOtherCharges$recovery#key-"+k+")";
  						%>
  						<td class="TableData"><div align="center">  						
  						<%
  						if((cgpantodisplay.indexOf(wcfield)) >= 0)
  						{
  						%>  						  						
  						<html:text property="<%=wcOtherCharges1%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						<%
  						}
  						else if((cgpantodisplay.indexOf(R1field)) >= 0)
  						{
  						%>
  							<html:text property="<%=wcOtherCharges1%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						<%
  						}
  						%>
  						</div></td>
  						<%
  						   recMode = "cgpandetails(recoveryMode$recovery#key-"+k+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:select property="<%=recMode%>" name="cpTcDetailsForm">
  						<html:option value="">Select</html:option>
  				                <html:options property="recoveryModes" name="cpTcDetailsForm"/>
                                                  </html:select>
  						</div></td>
  					  </tr>
  					  <%k++;%>
  					  </logic:iterate>
  				</table></td>
                  </tr>
				  
				  <TR>
							<TD align="left" valign="top" class="ColumnBackground">&nbsp;Have you ensured inclusion of unappropriated receipts  also in the amount of recovery after NPA indicated above?<font color="#FF0000" size="2">*</font></TD>
							<TD align="left" valign="top" class="tableData" colspan="3">
								<html:radio name="cpTcDetailsForm" value="Y" property="inclusionOfReciept" ><bean:message key="yes"/></html:radio>&nbsp; 
								<html:radio name="cpTcDetailsForm" value="N" property="inclusionOfReciept" onclick="checkRec();"><bean:message key="no" /></html:radio>
								<html:radio name="cpTcDetailsForm" value="NA" property="inclusionOfReciept">NA</html:radio>	 
							</TD>
						</TR>
						<TR>
							<TD align="left" valign="top" class="ColumnBackground">&nbsp;Do you confirm feeding of correct value as recoveries after NPA?<font color="#FF0000" size="2">*</font></TD>
							<TD align="left" valign="top" class="TableData" colspan="3">
								<html:radio name="cpTcDetailsForm" value="Y" property="confirmRecoveryValues" ><bean:message key="yes"/></html:radio>&nbsp; 
								<html:radio name="cpTcDetailsForm" value="N" property="confirmRecoveryValues" onclick="checkRec();"><bean:message key="no" /></html:radio>
							</TD>
						</tr>
				<tr>
  					<td class="ColumnBackground">&nbsp;<bean:message key="ifrecoverydonethruots"/></td>
  					<td class="TableData" colspan="3">
  						<html:text property="dateOfSeekingOTS" maxlength="10" size="10" name="cpTcDetailsForm"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.dateOfSeekingOTS')" align="center">
  					</td>
  				</tr>
				  
				  
           			<%
           			/*
           			   String totalNetworthStr = "";
           			   String totalLandValueStr = "";
           			   String totalMachineValueStr = "";
           			   String totalBldgValueStr = "";
           			   String totalOFMAValueStr = "";
           			   String totalCurrAssetsValueStr = "";
           			   String totalOthersValueStr = "";
                                   ClaimActionForm claimForm = (ClaimActionForm)session.getAttribute("cpTcDetailsForm");           			
                                   HashMap details = (HashMap)claimForm.getSecurityDetails();
                                   Double totalNetworthDouble = (Double)details.get(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR);
                                   if(totalNetworthDouble != null)
                                   {
                                       if(totalNetworthDouble.doubleValue() > 0.0)
                                       {
                                           totalNetworthStr = totalNetworthDouble.toString();
                                       }
                                       else
                                       {
                                           totalNetworthStr = "";
                                       }
                                   }
                                   Double totalLandValDouble = (Double)details.get(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND);
                                   if(totalLandValDouble != null)
                                   {
                                       if(totalLandValDouble.doubleValue() > 0.0)
                                       {
                                           totalLandValueStr = totalLandValDouble.toString();
                                       }
                                       else
                                       {
                                           totalLandValueStr = "";
                                       }
                                   }
                                   Double totalMachineValDouble = (Double)details.get(ClaimConstants.CLM_SAPGD_PARTICULAR_MC);
                                   if(totalMachineValDouble != null)
                                   {
                                       if(totalMachineValDouble.doubleValue() > 0.0)
                                       {
                                          totalMachineValueStr = totalMachineValDouble.toString();
                                       }
                                       else
                                       {
                                          totalMachineValueStr = "";
                                       }
                                   }
                                   Double totalBldgValueDouble = (Double)details.get(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG);
                                   if(totalBldgValueDouble != null)
                                   {
                                       if(totalBldgValueDouble.doubleValue() > 0.0)
                                       {
                                       	    totalBldgValueStr = totalBldgValueDouble.toString();
                                       }
                                       else
                                       {
                                           totalBldgValueStr = "";
                                       }
                                   }
                                   Double totalOFMAValDouble = (Double)details.get(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS);
                                   if(totalOFMAValDouble != null)
                                   {
                                       if(totalOFMAValDouble.doubleValue() > 0.0)
                                       {
                                            totalOFMAValueStr = totalOFMAValDouble.toString();
                                       }
                                       else
                                       {
                                           totalOFMAValueStr = ""; 
                                       }
                                   }
                                   Double totalCurrAssetsValDouble = (Double)details.get(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS);
                                   if(totalCurrAssetsValDouble != null)
                                   {
                                       if(totalCurrAssetsValDouble.doubleValue() > 0.0)
                                       {
                                       	   totalCurrAssetsValueStr = totalCurrAssetsValDouble.toString();
                                       }
                                       else
                                       {
                                           totalCurrAssetsValueStr = "";
                                       }
                                   }
                                   Double totalOthersValDouble = (Double)details.get(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS);
                                   if(totalOthersValDouble != null)
                                   {
                                       if(totalOthersValDouble.doubleValue() > 0.0)
                                       {
                                           totalOthersValueStr = totalOthersValDouble.toString();
                                       }
                                       else
                                       {
                                           totalOthersValueStr = "";
                                       }
                                   }
                                 */
           			%>
                  <tr> 
                    <td colspan="4" class="SubHeading">&nbsp;<bean:message key="securityandpersonalguaranteedtls"/></td>
                  </tr>
                  <tr> 
                    <td colspan="5"><table width="100%" border="0" cellspacing="1">
  					<tr>
  						<td class="ColumnBackground" rowspan="2"><div align="center"><bean:message key="particulars"/></div></td>
  						<td class="ColumnBackground" colspan="2"><div align="center"><bean:message key="security"/></div></td>
  						<td class="ColumnBackground" rowspan="2"><div align="center">Networth of Guarantor(s)/Promoter(s)(in Rs.)<font color="#FF0000" size="2">*</font></div></td>
  						<td class="ColumnBackground" rowspan="2"><div align="center"><bean:message key="reasonsforreductioninsecurity"/><font color="#FF0000" size="2">*</font></div></td>
  					</tr>
  					<tr>
  						<td class="ColumnBackground"><div align="center"><bean:message key="nature"/></div></td>
  						<td class="ColumnBackground"><div align="center"><bean:message key="value"/><br><bean:message key="inRs"/></div></td>
  					</tr>
  					<tr>
  						<td class="TableData" rowspan="6"><div align="center"><bean:message key="asondateofsanctionofcredit"/></div></td>
  						<td class="TableData"><div align="center"><bean:message key="land"/></div></td>
  						<%  						
  						   landAsOnDtOfSnctnDtl = "asOnDtOfSanctionDtl("+ ClaimConstants.CLM_SAPGD_PARTICULAR_LAND+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=landAsOnDtOfSnctnDtl%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalSanc();"/>							
  						</div></td>
  						<%  						
  						   netwrthAsOnDtOfSnctn ="asOnDtOfSanctionDtl("+ ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR+")";
  						%>
  						<td class="TableData" rowspan="6"><div align="center">
  						<html:text property="<%=netwrthAsOnDtOfSnctn%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						<%  						
  						   reasonReductionDtSnctn="asOnDtOfSanctionDtl("+ ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION+")";
  						%>
  						<td class="TableData" rowspan="6"><div align="center">
  					<%--	<html:textarea property="<%=reasonReductionDtSnctn%>" name="cpTcDetailsForm"/> --%>
							<html:select property="<%=reasonReductionDtSnctn%>" name="cpTcDetailsForm" disabled="true">
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
  						</div></td>
  					</tr>
  					<tr>
  				        <td class="TableData"><div align="center"><bean:message key="bldg"/></div></td>
  						<%  						
  						   bldgAsOnDtOfSnctn ="asOnDtOfSanctionDtl("+ ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG+")";
  					        %>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=bldgAsOnDtOfSnctn%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalSanc();"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="machine"/></div></td>
  						<% 
  						   machinecAsOnDtOfSnctn ="asOnDtOfSanctionDtl("+ ClaimConstants.CLM_SAPGD_PARTICULAR_MC+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=machinecAsOnDtOfSnctn%>" maxlength="16" size="10" name="cpTcDetailsForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalSanc();"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="assets"/></div></td>
  						<% 
  						   otherAssetsAsOnDtOfSnctn = "asOnDtOfSanctionDtl("+ ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS+")";						
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=otherAssetsAsOnDtOfSnctn%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalSanc();"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="currentAssets"/></div></td>
  						<%  						
  						   currAssetsAsOnDtOfSnctn="asOnDtOfSanctionDtl("+ ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS +")";						
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=currAssetsAsOnDtOfSnctn%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalSanc();"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="psOthers"/></div></td>
  						<%  						
  						   otherValAsOnDtOfSnctn="asOnDtOfSanctionDtl("+ ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=otherValAsOnDtOfSnctn%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalSanc();"/>
  							
  						</div></td>
  					</tr>
					<TR>
									<td class="TableData"></TD>
									<td class="TableData">Total</TD>
									<td class="TableData">
										<html:text name="cpTcDetailsForm" property="totSecAsOnSanc" maxlength="10" size="10" readonly="true"/>
									</TD>
									<TD class="TableData" colspan="2"></TD>
								</TR>
  					<tr>
  						<td class="TableData" rowspan="6"><div align="center"><bean:message key="asonthedateofnpa"/></div></td>
  						<td class="TableData"><div align="center"><bean:message key="land"/></div></td>
  						<%
  						   landAsOnDtOfNPA = "asOnDtOfNPA("+ ClaimConstants.CLM_SAPGD_PARTICULAR_LAND+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=landAsOnDtOfNPA%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalNpa();"/>
  						</div></td>
  						<%
  						   netwrthAsOnDtOfNPA ="asOnDtOfNPA("+ ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR+")";
  						%>
  						<td class="TableData" rowspan="6"><div align="center">
  						<html:text property="<%=netwrthAsOnDtOfNPA%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>						
  						</div></td>
  						<%
  						   rsnRdctnDtSnctnAsOnNPA="asOnDtOfNPA("+ ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION+")";
  						%>
  						<td class="TableData" rowspan="6"><div align="center">
  					<%--	<html:textarea property="<%=rsnRdctnDtSnctnAsOnNPA%>" name="cpTcDetailsForm"/> --%>
						<html:select property="<%=rsnRdctnDtSnctnAsOnNPA%>" name="cpTcDetailsForm">
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
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="bldg"/></div></td>
  						<%  						
  						    bldgAsOnDtOfNPA ="asOnDtOfNPA("+ ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG+")";
  					        %>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=bldgAsOnDtOfNPA%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalNpa();"/>						
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="machine"/></div></td>
  						<%   						
  						   machinecAsOnDtOfNPA ="asOnDtOfNPA("+ ClaimConstants.CLM_SAPGD_PARTICULAR_MC+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=machinecAsOnDtOfNPA%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalNpa();"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="assets"/></div></td>
  						<%   						
  						    otherAssetsAsOnDtOfNPA = "asOnDtOfNPA("+ ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS+")";						
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=otherAssetsAsOnDtOfNPA%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalNpa();"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="currentAssets"/></div></td>
  						<%  						
  						   currAssetsAsOnDtOfNPA="asOnDtOfNPA("+ ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS +")";						
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=currAssetsAsOnDtOfNPA%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalNpa();"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="psOthers"/></div></td>
  						<%  						
  						   otherValAsOnDtOfNPA="asOnDtOfNPA("+ ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=otherValAsOnDtOfNPA%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalNpa();"/>
  						</div></td>
  					</tr>
					<TR>
									<td class="TableData"></TD>
									<td class="TableData">Total</TD>
									<td class="TableData">
										<html:text name="cpTcDetailsForm" property="totSecAsOnNpa" maxlength="10" size="10" readonly="true"/>
									</TD>
									<TD class="TableData" colspan="2"></TD>
								</TR>
  					<tr>
  						<td class="TableData" rowspan="6"><div align="center"><bean:message key="asondateoflodgementofcredit"/></div></td>
  						<td class="TableData"><div align="center"><bean:message key="land"/></div></td>
  						<%
  						   landAsOnLodgemnt = "asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_PARTICULAR_LAND+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=landAsOnLodgemnt%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalClaim();"/>
  						</div></td>
  						<%
  						   netwrthAsOnLodgemnt ="asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR+")";
  						%>
  						<td class="TableData" rowspan="6"><div align="center">
  						<html:text property="<%=netwrthAsOnLodgemnt%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						<%
  						   rsnRdctnDtSnctnAsOnLodgemnt="asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION+")";
  						%>
  						<td class="TableData" rowspan="6"><div align="center">
  					<%--	<html:textarea property="<%=rsnRdctnDtSnctnAsOnLodgemnt%>" name="cpTcDetailsForm"/> --%>
							<html:select property="<%=rsnRdctnDtSnctnAsOnLodgemnt%>" name="cpTcDetailsForm">
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
						
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="bldg"/></div></td>
  						<%  						
  						   bldgAsOnDtOfLodgemnt ="asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG+")";
  					        %>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=bldgAsOnDtOfLodgemnt%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalClaim();"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="machine"/></div></td>
  						<%   						
  						   machinecAsOnLodgemnt ="asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_PARTICULAR_MC+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=machinecAsOnLodgemnt%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalClaim();"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="assets"/></div></td>
  						<%   						
  						   otherAssetsAsOnLodgemnt = "asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS+")";						
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=otherAssetsAsOnLodgemnt%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalClaim();"/>						
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="currentAssets"/></div></td>
  						<%  						
  						   currAssetsAsOnLodgemnt="asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS +")";						
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=currAssetsAsOnLodgemnt%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalClaim();"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="psOthers"/></div></td>
  						<%  						
  						   otherValAsOnLodgemnt="asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=otherValAsOnLodgemnt%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onchange="calPsTotalClaim();"/>
  						</div></td>
  					</tr>
					<TR>
									<td class="TableData"></TD>
									<td class="TableData">Total</TD>
									<td class="TableData" id="totalsecurityasonnpadt">
										<html:text name="cpTcDetailsForm" property="totSecAsOnClaim" maxlength="10" size="10" readonly="true"/>
									</TD>
									<TD class="TableData" colspan="2"></TD>
								</TR>
                    </table></td>
                  </tr>
  				<tr>
  				<td><br></td>
  				</tr>
  	            
  				
  			
												
          <!-- added by sukumar for capturing the Subsidy Details -->
			  
						
			   
		   
		   
		    <%
				ClaimActionForm claimForm = (ClaimActionForm)session.getAttribute("cpTcDetailsForm") ;
				String falgforCasesafet=claimForm.getFalgforCasesafet();
			%>
           <%
				String bid = null;
			//	ClaimDetail clmDtl = null;
				String type = null;
				double tcIssued = 0.0;
				double wcIssued = 0.0;
				double tcRecoveryMade = 0.0;
				double wcRecoveryMade = 0.0;
				double totalNetOutstanding = 0.0;
				double tcClaimApplied = 0.0;
				double wcClaimApplied = 0.0;
				double tcNetOutstanding = 0.0;
				double wcNetOutstanding = 0.0;
				double tcServiceFee = 0.0;
				double wcServiceFee = 0.0;
				double totalServiceFee = 0.0;
				double tcRefundFee = 0.0;
				double wcRefundFee = 0.0;
				double totalRefundFee = 0.0;
				double amt=0.0;
				double amt2=0.0;
				double rate = 0.75;
				double rate1=0.50;
				double r2=0.0;   
				double r1=0.0;
				String microFlag = "N";
				String userId = null;
				String womenOperated = "M";
				String typeofActivity = "";
				String schemeName = "";
				String nerFlag = "N";
				String stateName = "";
			%>
           
           
           <%
               double tcClaimEligible = 0.0;
               double wcClaimEligible = 0.0;
               double tcFirstInstallment = 0.0;
               double wcFirstInstallment = 0.0;
               double totalFirstInstalment = 0.0;
               double testFirstInstalment = 0.0;
               double tcClaimEligible1=0.0;
                double tcClaimEligible2=0.0;
                double wcClaimEligible1=0.0;
                 double wcClaimEligible2=0.0;
               if(schemeName.equals("RSF"))
                 {
                   rate=0.50;
               tcClaimEligible =  Math.round(tcNetOutstanding * rate);
               wcClaimEligible =  Math.round(wcNetOutstanding * rate); 
                    }
              else if((tcIssued+wcIssued)<=500000 && microFlag.equals("Y")){
                 rate = 0.80;
               //  falgforCasesafet = "N";
                 if (falgforCasesafet.equals("Y"))
                       {
                       rate=0.85;
                        tcClaimEligible =  Math.round(tcNetOutstanding * rate);
                        wcClaimEligible =  Math.round(wcNetOutstanding * rate);      
                       }else{
               //jai 2
                 tcClaimEligible =  Math.round(tcNetOutstanding * rate);
                 wcClaimEligible =  Math.round(wcNetOutstanding * rate);   
                 } 
                tcClaimEligible =  Math.round(tcNetOutstanding * rate);
                 wcClaimEligible =  Math.round(wcNetOutstanding * rate); 
                 
                 
               }else if((tcIssued+wcIssued <=5000000) && (womenOperated.equals("F")|| nerFlag.equals("Y"))){
               
                    rate = 0.80;
                    tcClaimEligible =  Math.round(tcNetOutstanding * rate);
                    wcClaimEligible =  Math.round(wcNetOutstanding * rate);                 
               }
               else if((tcIssued+wcIssued)<=500000 && microFlag.equals("N")){
                 tcClaimEligible =  Math.round(tcNetOutstanding * rate);
                  wcClaimEligible =  Math.round(wcNetOutstanding * rate);  
            
               }
               else if(((schemeName.equals("CGFSI"))&&(tcIssued+wcIssued) > 5000000&&(nerFlag.equals("Y")))||((schemeName.equals("CGFSI"))&&(womenOperated.equals("F"))&&(tcIssued+wcIssued) > 5000000&&(nerFlag.equals("N"))))
               {
                rate=0.80;
                      
                     
               long l = (int)Math.round(((tcIssued+wcIssued-5000000)/(tcIssued+wcIssued)) * 1000000000); // truncates 
               r2 = l / 1000000000.0; 
              long l2 = (int)Math.round(((5000000)/(tcIssued+wcIssued)) * 1000000000); // truncates 
              r1 = l2 / 1000000000.0; 
              tcClaimEligible1 =  Math.round(tcNetOutstanding * rate*(r1));
              tcClaimEligible2 =  Math.round(tcNetOutstanding * rate1*(r2));
            tcClaimEligible=tcClaimEligible1+tcClaimEligible2;
               wcClaimEligible1 =  Math.round(wcNetOutstanding * rate*(r1));
              wcClaimEligible2 =  Math.round(wcNetOutstanding * rate1*(r2));
               wcClaimEligible=  wcClaimEligible1+wcClaimEligible2;   
            
                  tcClaimEligible =  Math.round(tcNetOutstanding * rate);
                  wcClaimEligible =  Math.round(wcNetOutstanding * rate);   
                }
               else if((schemeName.equals("CGFSI"))&&(womenOperated.equals("M"))&&(tcIssued+wcIssued) > 5000000&&(nerFlag.equals("N")))
               {
                   
              rate=0.75;  
              long l = (int)Math.round(((tcIssued+wcIssued-5000000)/(tcIssued+wcIssued)) * 1000000000); // truncates 
              r2 = l / 1000000000.0; 
              long l2 = (int)Math.round(((5000000)/(tcIssued+wcIssued)) * 1000000000); // truncates 
              r1 = l2 / 1000000000.0; 
               tcClaimEligible1 =  Math.round(tcNetOutstanding * rate*(r1));
               tcClaimEligible2 =  Math.round(tcNetOutstanding * rate1*(r2));
				  tcClaimEligible=tcClaimEligible1+tcClaimEligible2;
				  wcClaimEligible1 =  Math.round(wcNetOutstanding * rate1*(r2));
				  wcClaimEligible2 =  Math.round(wcNetOutstanding * rate*(r1));
				  wcClaimEligible=  wcClaimEligible1+wcClaimEligible2;            
         
                 }
                else if((tcIssued+wcIssued) > 500000){
                 tcClaimEligible =  Math.round(tcNetOutstanding * rate);
                 wcClaimEligible =  Math.round(wcNetOutstanding * rate);                 
               }
                 
                tcClaimEligible =  Math.round(tcNetOutstanding * rate);
                wcClaimEligible =  Math.round(wcNetOutstanding * rate);                 
      
               claimForm.setTcClaimEligibleAmt(tcClaimEligible);
               claimForm.setWcClaimEligibleAmt(wcClaimEligible);
               claimForm.setTotalClaimEligibleAmt(tcClaimEligible+wcClaimEligible);
               
               tcFirstInstallment = Math.round(tcClaimEligible  * 0.75);
               wcFirstInstallment  = Math.round(wcClaimEligible * 0.75);
               totalFirstInstalment = tcFirstInstallment + wcFirstInstallment;
               testFirstInstalment = tcFirstInstallment + wcFirstInstallment;
               claimForm.setTcFirstInstallment(tcFirstInstallment);
               claimForm.setWcFirstInstallment(wcFirstInstallment);
               claimForm.setTotalFirstInstalment(tcFirstInstallment+wcFirstInstallment);
               claimForm.setTestFirstInstalment(tcFirstInstallment+wcFirstInstallment);               
                %> 
           
                <tr> 
                    <td colspan="4" class="SubHeading">&nbsp;<bean:message key="totalamountforwhichguaranteeispreferred"/></td>
                  </tr>
  				<tr> 
  					<td colspan="4"><table width="100%" border="0" cellspacing="1" cellpadding="0">
  						<tr class="ColumnBackground">
  							<td><div align="center"><bean:message key="SerialNumber"/></div></td>
  							<td><div align="center"><bean:message key="cgpan"/></div></td>
  							<td><div align="center"><bean:message key="loanlimitcoveredundercgfsi"/><font color="#FF0000" size="2">*</font></div></td>
  							<td><div align="center"><bean:message key="amountclaimedinrs"/><font color="#FF0000" size="2">*</font></div></td>
                        <!--    <td><div align="center">Claim Eligible Amount</div></td> -->
  						</tr>
                                                
  						<%int m=1;%>
  						<logic:iterate property="cgpnDetails" name="cpTcDetailsForm" id="object"> 
					<%--<logic:iterate property="tcCgpansVector" name="cpTcDetailsForm" id="object"> --%>
  						<%
  						hashmap =(java.util.HashMap)object;													
  						cgpn = (String)hashmap.get(ClaimConstants.CLM_CGPAN);
						String loanType=cgpn.substring(cgpn.length()-2);
  						%>						
  						<tr>
  							<td class="TableData"><div align="center">&nbsp;<%=m%></div></td>
  							<td class="TableData"><div align="center">&nbsp;<%=cgpn%></div></td>
  							<td class="TableData"><div align="center">&nbsp;<%=hashmap.get(ClaimConstants.CGPAN_APPRVD_AMNT)%></div></td>
  							<%
  							    amountClaimed="claimSummaryDetails("+cgpn+")";
  							%>
  							<td class="TableData"><div align="center">
  							<html:text property="<%=amountClaimed%>" name="cpTcDetailsForm" maxlength="16" size="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  							</div></td>
							
  						</tr>
  						<%m++;%>
  						</logic:iterate>
					
  					</table></td>
           </tr>
			 <tr align="center">
					      <TD class="SubHeading"
							 align="left">General inforamtion
						  </TD>
						</TR>
						<TR>
							<TD align="left" valign="top" class="ColumnBackground">&nbsp;<bean:message key="mliComment"/>
							<font color="#FF0000" size="2">*</font>
							</TD>
							<TD class="TableData" colspan="3"><html:textarea property ="mliCommentOnFinPosition" 	cols="60" name="cpTcDetailsForm"/>
							</TD>
						</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">&nbsp;<bean:message key = "finAssistDetails"/>
						<font color="#FF0000" size="2">*</font>
						</TD>
						<TD class="TableData" colspan="3"><html:textarea property = "detailsOfFinAssistance" cols="60"name="cpTcDetailsForm"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">&nbsp;Does the MLI propose to provide credit support to Chief Promoter/Borrower for any other project
						<font color="#FF0000" size="2">*</font>
						</TD>
						<TD class="TableData" colspan="3">
						<html:radio value="Y" property="creditSupport" name="cpTcDetailsForm" ><bean:message key="yes"/></html:radio>&nbsp;
						
						<html:radio value="N" property="creditSupport" name="cpTcDetailsForm"><bean:message key="no"/></html:radio>
						
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">&nbsp;<bean:message key="bankFacilityProvided"/>
						<font color="#FF0000" size="2">*</font>
						</TD>
						<TD class="TableData" colspan="3"><html:textarea property="bankFacilityDetail" name="cpTcDetailsForm" cols="60"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">&nbsp;Does the MLI advise placing the Borrower and/or Chief Promoter under watch-List of CGTMSE
						<font color="#FF0000" size="2">*</font>
						</TD>
						<TD class="TableData" colspan="3">
						<html:radio name="cpTcDetailsForm" value="Y" property="placeUnderWatchList">
						<bean:message key="yes"/>
						</html:radio>
						<html:radio name="cpTcDetailsForm" value="N" property="placeUnderWatchList">
						<bean:message key="no"/>
						  </html:radio>
					  </TD>
                </TR>
                <TR>
                  <TD align="left" valign="top" class="ColumnBackground">&nbsp;<bean:message key="otherRemarks"/></TD>
                  <TD class="TableData" colspan="3"><html:textarea name="cpTcDetailsForm" property="remarksOnNpa" cols="60"/></TD>
                </TR> 
		   
		   
		   
     <%--      <tr> 
              <td colspan="4" class="SubHeading">&nbsp;RTGS Details</td>
         </tr>
          <tr>
              <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;IFS Code</td>
				  	  <td class="TableData"><html:text property="ifsCode" maxlength="10" name="cpTcDetailsForm"/></td>
				      <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;NEFT Code</td>
				  	  <td class="TableData"><html:text property="neftCode" maxlength="10" name="cpTcDetailsForm"/></td>
			    </tr>
          <tr>
              <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;Bank Account Name</td>
				  	  <td class="TableData"><html:text property="rtgsBankName" maxlength="30" name="cpTcDetailsForm"/></td>
				      <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;Branch Name</td>
				  	  <td class="TableData"><html:text property="rtgsBranchName" maxlength="30" name="cpTcDetailsForm"/></td>
			    </tr>
          <tr>
              <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;Bank Account Number</td>
				  	  <td class="TableData"><html:text property="rtgsBankNumber" maxlength="15" name="cpTcDetailsForm" onkeypress="return numbersOnly(this, event)"/></td>
				      <td class="ColumnBackground" colspan="2">&nbsp;<font color="#FF0000" size="2"></font>&nbsp;</td>
				   </tr>
           <tr><td>&nbsp;</td>
		   </tr> --%>
  				<tr>
  					<td colspan="4" class="SubHeading">
  						&nbsp;<bean:message key="amounteligibleforclaimnote"/>
  						<br><br>
  						<bean:message key="termloannote"/>
  						<br>
  						&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="termloannote1a"/>		(<strong>or</strong>)
  						<br>
  						&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="termloannote1b"/>
  						<br>
  						<bean:message key="workingcapitalnote"/>
  						<br>
  						&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="workingcapitalnote2a"/>		(<strong>or</strong>)
  						<br>
  						&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="workingcapitalnote2b"/>
  					</td>
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
          <div>
            <div align="center">
            <a href="javascript:submitForm('addFirstClaimsPageDetails.do?method=addFirstClaimsPageDetails')"><img src="images/Next.gif" alt="Submit" width="49" height="37" border="0"></a>
            <a href="javascript:document.cpTcDetailsForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></a>            
            <a href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></a></div>
        </div></td>
        <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
    </tr>
</table>
</html:form>
</body>
</html>
