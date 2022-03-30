<%@ page language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cgtsi.actionform.ReportActionForm"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.cgtsi.actionform.ClaimProcessingActionForm"%>
<%@page import="com.cgtsi.actionform.NPAMarkingPopulateData"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="/SUBDEBT/css/StyleSheet.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
<title>Claim Processing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<!-- <head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous" />
<link href="/SUBDEBT/css/StyleSheet.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script> 

<title>NPA</title> -->
<script type="text/javascript">
	var isSearchClicked;
	function isNumberKey(evt, obj) {

        var charCode = (evt.which) ? evt.which : event.keyCode
        var value = obj.value;
        var dotcontains = value.indexOf(".") != -1;
        if (dotcontains)
            if (charCode == 46) return false;
        if (charCode == 46) return true;
        if (charCode > 31 && (charCode < 48 || charCode > 57))
            return false;
        return true;
    }
	function CancelData() {
		$("#cgpan2").val('');
		getPromoter();
		$("#isDisable").css("display", "none");
	}

	$(document).ready(
					function() {//alert("okkk");
						var cgpan = document.getElementById('cgpan2').value;
						//alert("Server cgpan ::" + cgpan); 
						var isSearchClicked = document.getElementById('isSearchClicked').value;
						//alert("Server cgpan ::" + isSearchClicked); 
						var message = document.getElementById('message').value;
						
						var legalProceedings = $("#legalProceedings").val();	
						 if(legalProceedings=="Others"){
								$("#legalOtherForm").css("display", "block");
							}
							else{
								$("#legalOtherForm").css("display", "none");
							}
						 if(legalProceedings=="SARFAESI ACT"){//alert("okkk");
								$("#dateofpossessionofasset").css("display", "block");
							}
							else{
								$("#dateofpossessionofasset").css("display", "none");
							}
						
						if ((cgpan == "" && isSearchClicked == "")) {
							$("#isDisable").css("display", "none");
						} else if ((cgpan != "" && isSearchClicked == "")) {
							$("#isDisable").css("display", "none");
						} else if ((cgpan == "" && isSearchClicked != "")) {
							$("#isDisable").css("display", "none");
						} else {
							$("#isDisable").css("display", "block");
						}

						if ((message != "" && !(message.indexOf('Success') > -1))) {
							$("#isDisable").css("display", "none");
							var databasemsg = "<b>Please correct the following Error:</b><ul><li>";
							databasemsg = databasemsg + message + "</li></ul>";

							$("#errormsg").html(databasemsg).css({
								"color" : "#ff6666",
								"display" : "block"
							});

							$("#message").val("");
						}
						if ((message != "" && (message.indexOf('Success') > -1))) {
							$("#isDisable").css("display", "none");
							var databasemsg1 = "<b>" + message + "</b>";

							$("#errormsg").html(databasemsg1).css({
								"color" : "#ff6666",
								"display" : "block"
							});

							$("#message").val("");
							$("#cgpan2").val('');
							$("#promoterNamee").empty();
							$("#promoterNamee").append(
							'<option value="0">Select</option>');
						}
					});

	
 	
	$(document).ready(function() {

		console.log($("#rolenAME").val());
		if ($("#rolenAME").val() == "NOTHING") {
			$("#NPADate").prop('disabled', true);
			$("#dateofclaim0").prop('disabled', true);
			$("#dateofClaim1").prop('disabled', true);
			$("#dateofClaim2").prop('disabled', true);
			$("#dateofClaim3").prop('disabled', true);
			$("#dateofClaim4").prop('disabled', true);
			$("#dateofClaim5").prop('disabled', true);
			
			$("#Networthofguarantor0").prop('disabled', true);
			$("#Networthofguarantor1").prop('disabled', true);
			$("#Networthofguarantor2").prop('disabled', true);
			$("#Networthofguarantor3").prop('disabled', true);
			$("#Networthofguarantor4").prop('disabled', true);
			$("#Networthofguarantor5").prop('disabled', true);
			
			$("#ReasonsforReduction0").prop('disabled', true);
			$("#ReasonsforReduction2").prop('disabled', true);
			$("#ReasonsforReduction3").prop('disabled', true);
			$("#ReasonsforReduction4").prop('disabled', true);
			$("#ReasonsforReduction5").prop('disabled', true);
			$("#ReasonsforReduction6").prop('disabled', true);
			
			
			$("#generalMLIComment").prop('disabled', true);
			$("#generalMLIFinancial").prop('disabled', true);
			$("#creditsupportforanyotherproject").prop('disabled', true);
			$("#generalMLIBank").prop('disabled', true);
			$("#promoterunderwatchListofCGTMSE").prop('disabled', true);
			$("#generalMLIRemark").prop('disabled', true);
			
			
			$("#recoveryPrincipal").prop('disabled', true);
			$("#recoveryInterest").prop('disabled', true);
			$("#recoveryMode").prop('disabled', true);
			$("#recoveryafterNPAindicated").prop('disabled', true);
			$("#valueasrecoveriesafterNPA").prop('disabled', true);
			$("#personalAmountclaim").prop('disabled', true);
			
			
			
			$("#legalProceedings").prop('disabled', true);
			$("#legalOtherForms").prop('disabled', true);
			$("#legalRegistration").prop('disabled', true);
			$("#legalSatisfactoryRreason").prop('disabled', true);
			$("#legalLocation").prop('disabled', true);
			$("#dateofpossessionofassets").prop('disabled', true);
			
			
			$("#legalAmountClaimed").prop('disabled', true);
			$("#tcOutstandingStatedCivil").prop('disabled', true);
			$("#tcOutstandingLodgement").prop('disabled', true);
			$("#tcAccountRestructed").prop('disabled', true);
			
			
			
			$("#dateofpossessionofassets").prop('disabled', true);
			$("#legalsuitFilingDate").prop('disabled', true);
			$("#accountsWilFulDefaulter").prop('disabled', true);
			
			
			$("#dateofIssueofRecallNotice").prop('disabled', true);
			$("#accountsClassified").prop('disabled', true);
			$("#accountsEnquiry").prop('disabled', true);
			$("#accountsMliReported").prop('disabled', true);
			$("#accountSatisfactoryReason").prop('disabled', true);
			
			$("#checkList1").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList2").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList3").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList4").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList5").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList6").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList7").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList8").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList9").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList10").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList11").attr("style", "pointer-events: none;background-color: #eee;");
			
					
			//$("#Upload").hide();
			 $("#Display").show();
			 
			$("#btnapprove").hide();
			$("#btnReturn").hide();
			 
			//$("#btnAdd").hide();
			$("#btnAdd1").hide();
			$("#btnAdd2").hide();
		} 
	});
	
	
	
 $(document).ready(function() {

		console.log($("#rolenAME").val());
		if ($("#rolenAME").val() == "MAKER") {
			$("#NPADate").prop('disabled', true);
			$("#dateofclaim0").prop('disabled', false);
			$("#dateofClaim1").prop('disabled', false);
			$("#dateofClaim2").prop('disabled', false);
			$("#dateofClaim3").prop('disabled', false);
			$("#dateofClaim4").prop('disabled', false);
			$("#dateofClaim5").prop('disabled', false);
			
			$("#Networthofguarantor0").prop('disabled', false);
			$("#Networthofguarantor1").prop('disabled', false);
			$("#Networthofguarantor2").prop('disabled', false);
			$("#Networthofguarantor3").prop('disabled', false);
			$("#Networthofguarantor4").prop('disabled', false);
			$("#Networthofguarantor5").prop('disabled', false);

			$("#ReasonsforReduction0").prop('disabled', false);
			$("#ReasonsforReduction2").prop('disabled', false);
			$("#ReasonsforReduction3").prop('disabled', false);
			$("#ReasonsforReduction4").prop('disabled', false);
			$("#ReasonsforReduction5").prop('disabled', false);
			$("#ReasonsforReduction6").prop('disabled', false);
			
			
			
			$("#generalMLIComment").prop('disabled', false);
			$("#generalMLIFinancial").prop('disabled', false);
			$("#creditsupportforanyotherproject").prop('disabled', false);
			$("#generalMLIBank").prop('disabled', false);
			$("#promoterunderwatchListofCGTMSE").prop('disabled', false);
			$("#generalMLIRemark").prop('disabled', false);
			
			
			$("#recoveryPrincipal").prop('disabled', false);
			$("#recoveryInterest").prop('disabled', false);
			$("#recoveryMode").prop('disabled', false);
			$("#recoveryafterNPAindicated").prop('disabled', false);
			$("#valueasrecoveriesafterNPA").prop('disabled', false);
			$("#personalAmountclaim").prop('disabled', false);
			
			
			
			$("#legalProceedings").prop('disabled', false);
			$("#legalOtherForms").prop('disabled', false);
			$("#legalRegistration").prop('disabled', false);
			$("#legalSatisfactoryRreason").prop('disabled', false);
			$("#legalLocation").prop('disabled', false);
			$("#dateofpossessionofassets").prop('disabled', false);
			
			
			$("#legalAmountClaimed").prop('disabled', false);
			$("#tcOutstandingStatedCivil").prop('disabled', false);
			$("#tcOutstandingLodgement").prop('disabled', false);
			$("#tcAccountRestructed").prop('disabled', false);
			
		
			
			$("#dateofpossessionofassets").prop('disabled', false);
			$("#legalsuitFilingDate").prop('disabled', false);
			$("#accountsWilFulDefaulter").prop('disabled', false);
			
			
			
			$("#dateofIssueofRecallNotice").prop('disabled', false);
			$("#accountsClassified").prop('disabled', false);
			$("#accountsEnquiry").prop('disabled', false);
			$("#accountsMliReported").prop('disabled', false);
			$("#accountSatisfactoryReason").prop('disabled', false);
			
			$("#Upload").show();
			$("#Display").hide();
			$("#btnapprove").hide();
			$("#btnReturn").hide();
			$("#btnAdd").show();
			$("#btnAdd1").show();
			
			
		} 
	}); 
	
	
 $(document).ready(function() {

		console.log($("#rolenAME").val());
		if ($("#rolenAME").val() == "CHECKER") {
			$("#NPADate").prop('disabled', true);
			$("#dateofclaim0").prop('readonly', true);
			$("#dateofClaim1").prop('readonly', true);
			$("#dateofClaim2").prop('readonly', true);
			$("#dateofClaim3").prop('readonly', true);
			$("#dateofClaim4").prop('readonly', true);
			$("#dateofClaim5").prop('readonly', true);
			
			$("#Networthofguarantor0").prop('disabled', true);
			$("#Networthofguarantor1").prop('disabled', true);
			$("#Networthofguarantor2").prop('disabled', true);
			$("#Networthofguarantor3").prop('disabled', true);
			$("#Networthofguarantor4").prop('disabled', true);
			$("#Networthofguarantor5").prop('disabled', true);

			$("#ReasonsforReduction0").prop('disabled', true);
			$("#ReasonsforReduction2").prop('disabled', true);
			$("#ReasonsforReduction3").prop('disabled', true);
			$("#ReasonsforReduction4").prop('disabled', true);
			$("#ReasonsforReduction5").prop('disabled', true);
			$("#ReasonsforReduction6").prop('disabled', true);
			
			
			$("#generalMLIComment").prop('readonly', true);
			$("#generalMLIFinancial").prop('readonly', true);
			$("#creditsupportforanyotherproject").attr("style", "pointer-events: none;background-color: #eee;");
			$("#generalMLIBank").prop('readonly', true);
			$("#promoterunderwatchListofCGTMSE").attr("style", "pointer-events: none;background-color: #eee;");
			$("#generalMLIRemark").prop('readonly', true);
			
			
			$("#recoveryPrincipal").prop('readonly', true);
			$("#recoveryInterest").prop('readonly', true);
			$("#recoveryMode").prop('readonly', true);
			$("#recoveryafterNPAindicated").attr("style", "pointer-events: none;background-color: #eee;");
			$("#valueasrecoveriesafterNPA").attr("style", "pointer-events: none;background-color: #eee;");
			$("#personalAmountclaim").prop('readonly', true);
			

			
			
			$("#legalProceedings").attr("style", "pointer-events: none;background-color: #eee;");
			$("#legalOtherForms").prop('readonly', true);
			$("#legalRegistration").prop('readonly', true);
			$("#legalSatisfactoryRreason").prop('readonly', true);
			$("#legalLocation").prop('readonly', true);
			$("#dateofpossessionofassets").prop('readonly', true);
			
			
			$("#legalAmountClaimed").prop("readonly", true);
			$("#tcOutstandingStatedCivil").prop('readonly', true);
			$("#tcOutstandingLodgement").prop('readonly', true);
			$("#tcAccountRestructed").attr("style", "pointer-events: none;background-color: #eee;");
			
		
		
			$("#dateofpossessionofassets").prop('readonly', true);
			$("#legalsuitFilingDate").prop('readonly', true);
			$("#accountsWilFulDefaulter").attr("style", "pointer-events: none;background-color: #eee;");
			
			
			$("#dateofIssueofRecallNotice").attr("style", "pointer-events: none;background-color: #eee;");
			$("#accountsClassified").attr("style", "pointer-events: none;background-color: #eee;");
			$("#accountsEnquiry").attr("style", "pointer-events: none;background-color: #eee;");
			$("#accountsMliReported").attr("style", "pointer-events: none;background-color: #eee;");
			$("#accountSatisfactoryReason").prop('readonly', true);
			
			$("#checkList1").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList2").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList3").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList4").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList5").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList6").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList7").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList8").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList9").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList10").attr("style", "pointer-events: none;background-color: #eee;");
			$("#checkList11").attr("style", "pointer-events: none;background-color: #eee;");
			
			 $("#Upload").hide();
			 $("#Display").show();
			 
			 $("#btnapprove").show();
			 $("#btnReturn").show();
			 
			$("#btnAdd").hide();
			$("#btnAdd1").hide();
			$("#btnAdd2").show();
		} 
	});

	function calculateNPA() {
		var total = parseFloat($("#dateofNPA0").val())
				+ parseFloat($("#dateofNPA1").val())
				+ parseFloat($("#dateofNPA2").val())
				+ parseFloat($("#dateofNPA3").val())
				+ parseFloat($("#dateofNPA4").val())
				+ parseFloat($("#dateofNPA5").val());
			
		
		$("#divNPA").val(total.toFixed(2));
	}
	function calculatedateofSanctionClaim() {
		var total = parseFloat($("#dateofclaim0").val())
				+ parseFloat($("#dateofClaim1").val())
				+ parseFloat($("#dateofClaim2").val())
				+ parseFloat($("#dateofClaim3").val())
				+ parseFloat($("#dateofClaim4").val())
				+ parseFloat($("#dateofClaim5").val());
			
		
		$("#divClaim").val(total.toFixed(2));
	}
	function calculateNetworthofguarantor() {
		var total = parseFloat($("#Networthofguarantor0").val())
				+ parseFloat($("#Networthofguarantor1").val())
				+ parseFloat($("#Networthofguarantor2").val())
				+ parseFloat($("#Networthofguarantor3").val())
				+ parseFloat($("#Networthofguarantor4").val())
				+ parseFloat($("#Networthofguarantor5").val());
		$("#Networthofguarantor").val(total.toFixed(2));
	}
	function calculatedateofSanction() {
		var total = parseFloat($("#dateofSanction0").val())
				+ parseFloat($("#dateofSanction1").val())
				+ parseFloat($("#dateofSanction2").val())
				+ parseFloat($("#dateofSanction3").val())
				+ parseFloat($("#dateofSanction4").val())
				+ parseFloat($("#dateofSanction5").val());
		$("#dateofSanction").val(total.toFixed(2));
	}
	
	function setIssueDateValue() {
		var start = new Date("2020-06-24"), end = new Date(), diff = new Date(
				end - start), days = diff / 1000 / 60 / 60 / 24;
		$("#dateofIssueofRecallNotice").datepicker({
			dateFormat : 'dd/mm/yy',
			minDate : days * -1,
			maxDate : end
		}).attr('readonly', 'readonly').focus();
		
		
	}
	function setAssetDateValue() {
		var start = new Date("2020-06-24"), end = new Date(), diff = new Date(
				end - start), days = diff / 1000 / 60 / 60 / 24;
		$("#dateofpossessionofassets").datepicker({
			dateFormat : 'dd/mm/yy',
			minDate : days * -1,
			maxDate : end
		}).attr('readonly', 'readonly').focus();
		
		
	}
	function setSuitDateValue() {
		var start = new Date("2020-06-24"), end = new Date(), diff = new Date(
				end - start), days = diff / 1000 / 60 / 60 / 24;
		$("#legalsuitFilingDate").datepicker({
			dateFormat : 'dd/mm/yy',
			minDate : days * -1,
			maxDate : end
		}).attr('readonly', 'readonly').focus();
		
	}
	
	
	
	
	$(function() {
		$("#btnSearch").bind("click", function() {

			$("#btnSearch").data('clicked', true);
			if ($("#btnSearch").data('clicked')) {
				var isSearchClicked = "Clicked";
				$("#isSearchClicked").val(isSearchClicked);
			} else {
				var isSearchClicked = "";
				$("#isSearchClicked").val(isSearchClicked);
			}

			var msg = "<b>Please correct the following Error:</b><ul>";
			var val = 0;
			if ($("#promoterNamee").val() == "0") {
				msg = msg + "<li>Promoter Name can not be black!!</li>";
				val = 1;
			}
			if ($("#cgpan2").val() == "") {
				msg = msg + "<li>CGPAN can not be black!!</li>";
				val = 1;

			}
			if (val == 1) {
				$("#errormsg").html(msg).css({
					"color" : "#ff6666",
					"display" : "block"
				});
				msg = msg + "</ul>";
				$(window).scrollTop(0);
				return;
			}
			if (val == 0) {//alert("okk");
				$("#btnSearch")[0].innerText = 'loading';
				$("#btnSearch").prop('disabled', true);
				setTimeout(function() {
					$("#btnSearch")[0].innerText = 'Search';
					$("#btnSearch").prop('disabled', false);
				}, 8000);

				getPromoterDetails();

			} else {
				$("#btnSearch").prop('disabled', true);
			}
		});

		$("#btnClear").bind("click", function() {
			$("#cgpan2").val('');
			getPromoter();
		});
	});

	function getPromoter() {//alert("okk");
		var cgpan = document.getElementById('cgpan2').value;
		//alert(cgpan);
		$("#btnSearch")[0].innerText = 'loading';
		$("#btnSearch").prop('disabled', true);
		setTimeout(function() {
			$("#btnSearch")[0].innerText = 'Search';
			$("#btnSearch").prop('disabled', false);
		}, 8000);
		document.claimProcessingForm.target = "_self";
		document.claimProcessingForm.method = "POST";
		document.claimProcessingForm.action = "claimProcessing.do?method=getClaimProcessing&cgpan="
				+ cgpan;
		document.claimProcessingForm.submit();
	}
	function getPromoterDetails() {
		var cgpan = document.getElementById('cgpan2').value;
		var promoterName = document.getElementById('promoterNamee').value;

		document.claimProcessingForm.target = "_self";
		document.claimProcessingForm.method = "POST";
		document.claimProcessingForm.action = "claimProcessing.do?method=getClaimProcessingSearchDetail&cgpan="
				+ cgpan
				+ "&promoterNamee"
				+ promoterName
				+ "&isSearchClicked"
				+ isSearchClicked;
		document.claimProcessingForm.submit();

	}
	
	</script>

<script type="text/javascript">

function OthersValue(){
	var legalProceedings = $("#legalProceedings").val();	
	
	//alert(legalProceedings);
	
	if(legalProceedings=="Others"){//alert("okkk");
		$("#legalOtherForm").css("display", "block");
	}
	else{
		$("#legalOtherForm").css("display", "none");
	}
	
	if(legalProceedings=="SARFAESI ACT"){//alert("okkk");
		$("#dateofpossessionofasset").css("display", "block");
	}
	else{
		$("#dateofpossessionofasset").css("display", "none");
	}
}


/* function setSuitDate(){alert("okk");
	
	var legalsuitFilingDate= $("#legalsuitFilingDate").val();	
	
	if(legalsuitFilingDate!=""){
		$("#legalsuitFilingDat").css("display", "block");
	}
	else{
		$("#legalsuitFilingDat").css("display", "none");
	}
} */


function SaveData(id) {//alert("okk");

var dateofIssueofRecallNotice = $("#dateofIssueofRecallNotice").val();	
var dateofpossessionofassets = $("#dateofpossessionofassets").val();	
var legalsuitFilingDate= $("#legalsuitFilingDate").val();	
//Account
var accountsWilFulDefaulter=$("#accountsWilFulDefaulter").val();	
var accountsClassified=$("#accountsClassified").val();	
varaccountsEnquiry=$("#varaccountsEnquiry").val();	
var accountsMliReported=$("#accountsMliReported").val();	
//var dateofIssueofRecallNotice=$("#dateofIssueofRecallNotice").val();	
var accountSatisfactoryReason=$("#accountSatisfactoryReason").val();	

//legal
var legalProceedings=$("#legalProceedings").val();	
var legalOtherForms=$("#legalOtherForms").val();	
var legalRegistration=$("#legalRegistration").val();
var legalSatisfactoryRreason=$("#legalSatisfactoryRreason").val();
//var dateofpossessionofassets=$("#dateofpossessionofassets").val();
var legalLocation=$("#legalLocation").val();
var legalAmountClaimed=$("#legalAmountClaimed").val();


//TC	
var tcOutstandingStatedCivil=$("#tcOutstandingStatedCivil").val();
var tcOutstandingLodgement=$("#tcOutstandingLodgement").val();		
var tcAccountRestructed=$("#tcAccountRestructed").val();

//recovery
var recoveryPrincipal=$("#recoveryPrincipal").val();
var recoveryInterest=$("#recoveryInterest").val();
var recoveryMode=$("#recoveryMode").val();

//security personal
var recoveryafterNPAindicated=$("#recoveryafterNPAindicated").val();
var valueasrecoveriesafterNPA=$("#valueasrecoveriesafterNPA").val();
var personalAmountclaim=$("#personalAmountclaim").val();

//General Information
var generalMLIComment=$("#generalMLIComment").val();
var generalMLIFinancial=$("#generalMLIFinancial").val();
var creditsupportforanyotherproject=$("#creditsupportforanyotherproject").val();
var generalMLIBank=$("#generalMLIBank").val();
var promoterunderwatchListofCGTMSE=$("#promoterunderwatchListofCGTMSE").val();
var generalMLIRemark=$("#generalMLIRemark").val();


var Networthofguarantor0 = $("#Networthofguarantor0").val();
var Networthofguarantor1 = $("#Networthofguarantor1").val();
var Networthofguarantor2 = $("#Networthofguarantor2").val();
var Networthofguarantor3 = $("#Networthofguarantor3").val();
var Networthofguarantor4 = $("#Networthofguarantor4").val();
var Networthofguarantor5 = $("#Networthofguarantor5").val();

var ReasonsforReduction0 = $("#ReasonsforReduction0").val();
var ReasonsforReduction2 = $("#ReasonsforReduction2").val();
var ReasonsforReduction3 = $("#ReasonsforReduction3").val();
var ReasonsforReduction4 = $("#ReasonsforReduction4").val();
var ReasonsforReduction5 = $("#ReasonsforReduction5").val();
var ReasonsforReduction6 = $("#ReasonsforReduction6").val();
var msg = "<b>Please correct the following Error:</b><ul>";
var countErr = 0;


if (accountsWilFulDefaulter == 0 || accountsWilFulDefaulter == "") {
	msg = msg
			+ "<li>Please select  Wilful defaulter..!</li>";
	countErr = 1;

}
if (accountsClassified == 0 || accountsClassified == "") {
	msg = msg
			+ "<li>Please select classified as fraud..!</li>";
	countErr = 1;

}
if (varaccountsEnquiry == 0 || varaccountsEnquiry == "") {
	msg = msg
			+ "<li>Please select Internal and/or external enquiry has been concluded..!</li>";
	countErr = 1;

}
if (accountsMliReported == 0 || accountsMliReported == "") {
	msg = msg
			+ "<li>Please select Involvement of staff of MLI has been reported..!</li>";
	countErr = 1;

}
if (dateofIssueofRecallNotice == "") {
	msg = msg
			+ "<li>Date of issue of Recall Notice can't be blank ..!</li>";
	countErr = 1;

}
/* if (accountSatisfactoryReason == "") {
	msg = msg
			+ "<li>Please Provide satisfactory reason for issuing recall notice prior to NPA date ..!</li>";
	countErr = 1;

}

if (legalSatisfactoryRreason == "") {
	msg = msg
			+ "<li>Please Provide satisfactory reason for issuing recall notice prior to NPA date can't be blank ..!</li>";
	countErr = 1;
} */

if (tcOutstandingStatedCivil == "") {
	msg = msg
			+ "<li> Outstanding stated in the Civil Suit /Case filed(Rs.) can't be blank ..!</li>";
	countErr = 1;
}
if (tcOutstandingLodgement == "") {
	msg = msg
			+ "<li>Outstanding As On Date of Lodgement of Claim can't be blank ..!</li>";
	countErr = 1;
}
if (tcAccountRestructed == "0") {
	msg = msg
			+ "<li>Account Restructed or not can't be blank ..!</li>";
	countErr = 1;
}
if (legalsuitFilingDate == "") {
	msg = msg
			+ "<li>Suit Filing Date can't be balnk..!</li>";
	countErr = 1;

}


var NPADate = $("#NPADate").val().split('/');
var tt1 = NPADate[1] + '/' + NPADate[0] + '/' + NPADate[2];
var dateNPA = new Date(tt1);

var legalsuitFilingDate = $("#legalsuitFilingDate").val().split('/');
var tt = legalsuitFilingDate[1] + '/' + legalsuitFilingDate[0] + '/'
		+ legalsuitFilingDate[2];
		

var dateofIssueofRecallNoticeDate = $("#dateofIssueofRecallNotice").val().split('/');
var tt2 = dateofIssueofRecallNoticeDate[1] + '/' + dateofIssueofRecallNoticeDate[0] + '/'
+ dateofIssueofRecallNoticeDate[2];


var date = new Date(tt);
var newdate = new Date(date);
newdate.setDate(newdate.getDate());

if ((dateNPA > newdate) && (legalSatisfactoryRreason == "")) {
	msg = msg
			+ "<li>Please Provide satisfactory reason for filing suit before NPA date </li>";
	countErr = 1;
}


var date = new Date(tt2);
var newdate1 = new Date(date);
newdate1.setDate(newdate1.getDate());

if((dateNPA > newdate1) && (accountSatisfactoryReason == "")){
	msg = msg
	+ "<li>Please Provide satisfactory reason for issuing recall notice prior to NPA date ..!</li>";
    countErr = 1;
}

if (legalProceedings == 0 || legalProceedings == "") {
	msg = msg
			+ "<li>Please select legal proceedings..!</li>";
	countErr = 1;

}



 if(legalProceedings=="Others"){//alert("legalProceedings"+legalProceedings);
	 if(legalOtherForms==""){//alert("legalOtherForms"+legalOtherForms);
			msg = msg
			+ "<li>Other Forms can't be blank ..!</li>";
			countErr = 1;
		   }else{
			countErr = 0;
		   }
	} 


if (legalRegistration == "") {
	msg = msg
			+ "<li>Suit / Case Registration No. can't be blank ..!</li>";
	countErr = 1;
}


 if(legalProceedings=="SARFAESI ACT"){//alert("legalProceedings"+legalProceedings);
	if(dateofpossessionofassets==""){
	msg = msg
	+ "<li>Date of possession of assets under sarfaesi act can't be blank ..!</li>";
	countErr = 1;
   }else{
	countErr = 0;
   }
}

if (legalLocation == "") {
	msg = msg
			+ "<li>Loaction can't be blank ..!</li>";
	countErr = 1;
}
if (legalAmountClaimed == "") {
	msg = msg
			+ "<li>Amount Claimed in the Suit in Rs. can't be blank ..!</li>";
	countErr = 1;
}
if (recoveryPrincipal == "") {
	msg = msg
			+ "<li>Term Loan / Composite Loan Principal(Rs) can't be blank ..!</li>";
	countErr = 1;
}
if (recoveryInterest == "") {
	msg = msg
			+ "<li>Interest & Other Charges can't be blank ..!</li>";
	countErr = 1;
}
if (recoveryMode == "") {
	msg = msg
			+ "<li>Mode of Recovery can't be blank ..!</li>";
	countErr = 1;
}

if (recoveryafterNPAindicated == 0 || recoveryafterNPAindicated == "") {
	msg = msg
			+ "<li>Please select have you ensured inclusion of unappropriated receipts also in the amount of recovery after NPA indicated above..!</li>";
	countErr = 1;

}
if (valueasrecoveriesafterNPA == 0 || valueasrecoveriesafterNPA == "") {
	msg = msg
			+ "<li>Please select  Do you confirm feeding of correct value as recoveries after NPA..!</li>";
	countErr = 1;

}
if (personalAmountclaim == "") {
	msg = msg
			+ "<li>Total amount for which guarantee claim is being preferred (Amount to be claimed) can't be blank ..!</li>";
	countErr = 1;
}

if (generalMLIComment == "") {
	msg = msg
			+ "<li>MLI's Comment on financial position of Borrower/Unit can't be blank ..!</li>";
	countErr = 1;
}
if (generalMLIFinancial == "") {
	msg = msg
			+ "<li>Details of Financial Assistance provided/being considered by MLI to minimize default  can't be blank ..!</li>";
	countErr = 1;
}
if (creditsupportforanyotherproject == 0 || creditsupportforanyotherproject == "") {
	msg = msg
			+ "<li>Please select  Does the MLI propose to provide credit support to Chief Promoter/Borrower for any other project..!</li>";
	countErr = 1;

}
if (generalMLIBank == "") {
	msg = msg
			+ "<li>Details Of Bank Facility already provided to Borrower can't be blank ..!</li>";
	countErr = 1;
}
if (promoterunderwatchListofCGTMSE == 0 || promoterunderwatchListofCGTMSE == "") {
	msg = msg
			+ "<li>Please select  Does the MLI advise placing the Borrower and/or Chief Promoter under watch-List of CGTMSE..!</li>";
	countErr = 1;

}


var divClaim = parseFloat($("#divClaim").val());
var Networthofguarantor = parseFloat($("#Networthofguarantor").val());
if(divClaim == 0 && Networthofguarantor == 0 && generalMLIRemark == "")
{
	msg = msg+ "<li>Please Provide the reason of  Why Value of Security  Available is 0 in Remark Column!</li>";
    countErr = 1;
}
/* if (generalMLIRemark == "") {
	msg = msg
			+ "<li>Please Provide the reason of  Why Value of Security  Available is 0 in Remark Column!</li>";
	countErr = 1;
} */


/* if (dateofclaim0 == "") {
	msg = msg
			+ "<li>Land security As on Date of Preferrment of Claim..!</li>";
	countErr = 1;

}

if (dateofClaim1 == "") {
	msg = msg
			+ "<li>Building security As on Date of Preferrment of Claim..!</li>";
	countErr = 1;

}

if (dateofClaim2 == "") {
	msg = msg
			+ "<li>Plant security As on Date of Preferrment of Claim..!</li>";
	countErr = 1;

}

if (dateofClaim3 == "") {
	msg = msg
			+ "<li>Other Fixed security As on Date of Preferrment of Claim..!</li>";
	countErr = 1;

}

if (dateofClaim4 == "") {
	msg = msg
			+ "<li>Current Assets security As on Date of Preferrment of Claim..!</li>";
	countErr = 1;

}

if (dateofClaim5 == "") {
	msg = msg
			+ "<li>Others security As on Date of Preferrment of Claim..!</li>";
	countErr = 1;

}

if (Networthofguarantor0 == "") {
	msg = msg
			+ "<li>Land networth of guarantor/Promoter(in Rs.) can't be blank</li>";
	countErr = 1;

}
if (Networthofguarantor1 == "") {
	msg = msg
			+ "<li>Building networth of guarantor/Promoter(in Rs.) can't be blank</li>";
	countErr = 1;

}
if (Networthofguarantor2 == "") {
	msg = msg
			+ "<li>Plant and Machinery networth of guarantor/Promoter(in Rs.) can't be blank</li>";
	countErr = 1;

}
if (Networthofguarantor3 == "") {
	msg = msg
			+ "<li>Other Fixed networth of guarantor/Promoter(in Rs.) can't be blank</li>";
	countErr = 1;

}
if (Networthofguarantor4 == "") {
	msg = msg
			+ "<li>Current Assets networth of guarantor/Promoter(in Rs.) can't be blank</li>";
	countErr = 1;

}
if (Networthofguarantor5 == "") {
	msg = msg
			+ "<li>Others networth of guarantor/Promoter(in Rs.) can't be blank</li>";
	countErr = 1;

}

if (ReasonsforReduction0 == "" || ReasonsforReduction0==0) {
	msg = msg
			+ "<li>Please select land  Reasons for Reduction in the value of Security, if any</li>";
	countErr = 1;

}
if (ReasonsforReduction2 == "") {
	msg = msg
			+ "<li>Please select building  Reasons for Reduction in the value of Security, if any</li>";
	countErr = 1;

}
if (ReasonsforReduction3 == "") {
	msg = msg
			+ "<li>Please select plant and machinery  Reasons for Reduction in the value of Security, if any</li>";
	countErr = 1;

}
if (ReasonsforReduction4 == "") {
	msg = msg
			+ "<li>Please select other fixed  Reasons for Reduction in the value of Security, if any</li>";
	countErr = 1;

}
if (ReasonsforReduction5 == "") {
	msg = msg
			+ "<li>Please select current Assets  reasons for Reduction in the value of Security, if any</li>";
	countErr = 1;

}
if (ReasonsforReduction6 == "") {
	msg = msg
			+ "<li>Please select Others  Reasons for Reduction in the value of Security, if any.</li>";
	countErr = 1;

} */

var dateofclaim0 = $("#dateofclaim0").val();
var dateofClaim1 = $("#dateofClaim1").val();
var dateofClaim2 = $("#dateofClaim2").val();
var dateofClaim3 = $("#dateofClaim3").val();
var dateofClaim4 = $("#dateofClaim4").val();
var dateofClaim5 = $("#dateofClaim5").val();

var securityDetails = [];
securityDetails.push({
	"dateofclaim0":dateofclaim0,
	"dateofClaim1":dateofClaim1,
	"dateofClaim2":dateofClaim2,
	"dateofClaim3":dateofClaim3,
	"dateofClaim4":dateofClaim4,
	"dateofClaim5":dateofClaim5,
	"Networthofguarantor0":Networthofguarantor0,
	"Networthofguarantor1":Networthofguarantor1,
	"Networthofguarantor2":Networthofguarantor2,
	"Networthofguarantor3":Networthofguarantor3,
	"Networthofguarantor4":Networthofguarantor4,
	"Networthofguarantor5":Networthofguarantor5,
	"ReasonsforReduction0" : ReasonsforReduction0,
    "ReasonsforReduction2" : ReasonsforReduction2,
    "ReasonsforReduction3" : ReasonsforReduction3,
    "ReasonsforReduction4" : ReasonsforReduction4,
    "ReasonsforReduction5" : ReasonsforReduction5,
    "ReasonsforReduction6" : ReasonsforReduction6,
});
var securityDetailsErr = 0;
var dateOfClaimErr = 0;
var promoterErr = 0;
jQuery.each(securityDetails,function(i, val) 
{
	 if((parseFloat(val.dateofclaim0)<0) && (parseFloat(val.dateofClaim1)<0) && 
	   (parseFloat(val.dateofClaim2)<0) && (parseFloat(val.dateofClaim3)<0) && 
	   (parseFloat(val.dateofClaim4)<0) && (parseFloat(val.dateofClaim5)<0) ){
				dateOfClaimErr = 1;} 
	if((parseFloat(val.Networthofguarantor0)<0) && (parseFloat(val.Networthofguarantor1)<0) && 
	  (parseFloat(val.Networthofguarantor2)<0) && (parseFloat(val.Networthofguarantor3)<0) && 
	  (parseFloat(val.Networthofguarantor4)<0) && (parseFloat(val.Networthofguarantor5)<0) ){
	  promoterErr = 1;}
	if((val.ReasonsforReduction0 == "0") && (val.ReasonsforReduction2 == "0") && 
	   (val.ReasonsforReduction3 == "0") && (val.ReasonsforReduction4 == "0") && 
	   (val.ReasonsforReduction5 == "0") && (val.ReasonsforReduction6 == "0") ){
		securityDetailsErr = 1;}
});
if(dateOfClaimErr == 1)
{
	msg = msg+ "<li>Please select alleast one Date of Permanant Claim Value</li>";
    countErr = 1;
}
if(promoterErr == 1)
{
	msg = msg+ "<li>Please select alleast one Networth of guarantor/Promoter Value</li>";
    countErr = 1;
}
if(securityDetailsErr == 1)
{
	msg = msg+ "<li>Please select alleast one Reasons for Reduction in the value of Security, if any</li>";
    countErr = 1;
}

if ($("#rolenAME").val() == "MAKER") {//alert("maker");
var legaAattachment=$("#legaAattachment").val();
if (legaAattachment == "") {
	msg = msg
			+ "<li>Lega Aattachment can't be blank...!</li>";
	countErr = 1;

}
else{
	
	var currentFileSize=Math.round($("#legaAattachment")[0].files[0].size/ 1024);
	var fileExtType = $("#legaAattachment")[0].files[0].type.trim();
	if (currentFileSize >= Math.round(2048)) { //KB 
		
		msg = msg
		+ "<li>File too Big, Please select a file less than 2 mb</li>";
		countErr = 1;
	
	}
	switch (fileExtType) {
		case 'application/pdf':
		break;
		default:
			
			msg = msg
			+ "<li>Only allow to upload PDF file...!</li>";
			countErr = 1;	
	
		} 
	}
}
//alert("Id is :::" + id);

var returnForm = $("#"+id).val();
$("#checkerReturn").val(returnForm);
if ($("#rolenAME").val() == "MAKER" && (returnForm!=null && returnForm!="Return")) {//alert("checker");
	
	var checkList1 = $("#checkList1").val();
	var checkList2 = $("#checkList2").val();
	var checkList3 = $("#checkList3").val();
	var checkList4 = $("#checkList4").val();
	var checkList5 = $("#checkList5").val();
	var checkList6 = $("#checkList6").val();
	var checkList7 = $("#checkList7").val();
	var checkList8 = $("#checkList8").val();
	var checkList9 = $("#checkList9").val();
	var checkList10 = $("#checkList10").val();
	var checkList11 = $("#checkList11").val();
	
	
	
	
	if (checkList1 == 0 || checkList1 == "") {
		msg = msg
				+ "<li>Please select Activity is eligible as per Credit Guarantee Scheme(CGS)..!</li>";
		countErr = 1;

	}

	if (checkList2 == 0 || checkList2 == "") {
		msg = msg
				+ "<li>Please select  Wheather CIBIL done/CIR/KYC obtained and findings are satisfactory...!</li>";
				countErr = 1;

	}

	if (checkList3 == 0 || checkList3 == "") {
		msg = msg
				+ "<li>Please select Rate charged on loan is as per CGS guidelines...!</li>";
				countErr = 1;

	}

	if (checkList4 == 0 || checkList4 == "") {
		msg = msg
				+ "<li>Please select Third party gaurantee/collateral security stipulated...!</li>";
				countErr = 1;

	}

	if (checkList5 == 0 || checkList5 == "") {
		msg = msg
				+ "<li>Please select Date of NPA as fed in the system is as per RBI guidelines..!</li>";
				countErr = 1;

	}

	if (checkList6 == 0 || checkList6 == "") {
		msg = msg
				+ "<li>Please select Wheather outstanding amount mentioned in the claim application form..!</li>";
				countErr = 1;

	}
	
	if (checkList7 == 0 || checkList7 == "") {
		msg = msg
				+ "<li>Please select Whether serious deficiencies have been observed in the matter of appraisal..!</li>";
				countErr = 1;

	}

	if (checkList8 == 0 || checkList8 == "") {
		msg = msg
				+ "<li>Please select Major deficiencies observed in Pre sanction/Post disbursement inspections..!</li>";
				countErr = 1;

	}
	if (checkList9 == 0 || checkList9 == "") {
		msg = msg
				+ "<li>Please select Wheather deficiencies observed on the part of internal staff as per the Staff Accountability exercise carried out..!</li>";
				countErr = 1;

	}

	if (checkList10 == 0 || checkList10 == "") {
		msg = msg
				+ "<li>Please select Internal rating was carried out and the proposal was found of Investment Grade..!</li>";
				countErr = 1;

	}
	
	if (checkList11 == 0 || checkList11 == "") {
		msg = msg
				+ "<li>Please select Wheather all the recoveries pertaining to the account after the date of NPA..!</li>";
				countErr = 1;

	}
	
}

//======================================================================
	if ($("#rolenAME").val() == "CHECKER" && (returnForm!=null && returnForm!="Return")){//alert("checker");
	
	var checkList1 = $("#checkList1").val();
	var checkList2 = $("#checkList2").val();
	var checkList3 = $("#checkList3").val();
	var checkList4 = $("#checkList4").val();
	var checkList5 = $("#checkList5").val();
	var checkList6 = $("#checkList6").val();
	var checkList7 = $("#checkList7").val();
	var checkList8 = $("#checkList8").val();
	var checkList9 = $("#checkList9").val();
	var checkList10 = $("#checkList10").val();
	var checkList11 = $("#checkList11").val();
	
	
	var checklist1="Y";
	var checklist2="Y";
	var checklist3="Y";
	var checklist4="N";
	var checklist5="Y";
	var checklist6="Y";
	var checklist7="N";
	var checklist8="N";
	var checklist9="N";
	var checklist10="Y";
	var checklist11="Y";
	
	if (checkList1!=checklist1) {//alert("okk");
		msg = msg
		+ "<li>Activity is eligible as per Credit Guarantee Scheme(CGS) Should be Y...!</li>";
		countErr = 1;
	}
	
	if (checkList2!= checklist2) {
		msg = msg
		+ "<li>Wheather CIBIL done/CIR/KYC obtained and findings are satisfactory Should be Y...!</li>";
		countErr = 1;

	}
	
	
	if (checkList3!= checklist3) {
		msg = msg
		+ "<li>Rate charged on loan is as per CGS guidelines Should be Y...!</li>";
		countErr = 1;
	}
	
	if (checkList4!= checklist4) {
		msg = msg
		+ "<li>Third party gaurantee/collateral security stipulated Should be N...!</li>";
		countErr = 1;
	}
	
	if (checkList5!= checklist5) {
		msg = msg
		+ "<li>Date of NPA as fed in the system is as per RBI guidelines Should be Y...!</li>";
		countErr = 1;
	}
	
	if (checkList6!= checklist6) {
		msg = msg
		+ "<li>Wheather outstanding amount mentioned in the claim application form Should be Y...!</li>";
		countErr = 1;
	}
	
	if (checkList7!= checklist7) {
		msg = msg
		+ "<li>Whether serious deficiencies have been observed in the matter of appraisal be N...!</li>";
		countErr = 1;
	}

	if (checkList8!= checklist8) {
		msg = msg
		+ "<li>Major deficiencies observed in Pre sanction/Post disbursement inspections should be N...!</li>";
		countErr = 1;
	}
	
	if (checkList9!= checklist9) {
		msg = msg
		+ "<li>Wheather deficiencies observed on the part of internal staff as per the Staff Accountability exercise carried out Should be N...!</li>";
		countErr = 1;
	}
	
	if (checkList10!= checklist10) {
		msg = msg
		+ "<li>Internal rating was carried out and the proposal was found of Investment Grade Should be Y...!</li>";
		countErr = 1;
	}
	
	if (checkList11!= checklist11) {
		msg = msg
		+ "<li>Wheather all the recoveries pertaining to the account after the date of NPA Should be Y...!</li>";
		countErr = 1;
	}
	
}
	
//=====================================================================	
	

if (countErr == 1) {
	$("#errormsg").html(msg).css({
		"color" : "#ff6666",
		"display" : "block"
	});
	msg = msg + "</ul>";
	
	$(window).scrollTop(0);
	return;
}

if (countErr == 0) {

	/* $("#btnAdd1")[0].innerText = 'loading...';
	$("#btnAdd1").prop('disabled', true);
	setTimeout(function() {
		$("#btnAdd1")[0].innerText = 'Save';
		$("#btnAdd1").prop('disabled', false);
	}, 8000); */
	 $("#"+id)[0].innerText = 'loading...';
	$("#"+id).prop('disabled', true);
	setTimeout(function() {
		$("#"+id)[0].innerText = $("#"+id).val();
		$("#"+id).prop('disabled', false);
	}, 8000);
 	  document.claimProcessingForm.target = "_self";
	document.claimProcessingForm.method = "POST";
	document.claimProcessingForm.action = "claimProcessing.do?method=getClaimProcessingDetail&dateofpossessionofassets="
			+ dateofpossessionofassets
			+ "&dateofIssueofRecallNotice="
			+ dateofIssueofRecallNotice;
			+ "&legalsuitFilingDate="
			+ legalsuitFilingDate;
	document.claimProcessingForm.submit();  

}

}


$(document).on('click', '#downloadFile', function() {
	 var cgPan = $('#cgpan2').val();
	// alert("okk"+cgPan);
		
	 	document.claimProcessingForm.target = "_self";
		document.claimProcessingForm.method = "POST";
		document.claimProcessingForm.action = "claimProcessing.do?method=downloadFile&cgPan="+cgPan;
		document.claimProcessingForm.submit();
	
});
</script>
</head>
<body>
	<html:errors />
	<html:form name="claimProcessingForm"
		type="com.cgtsi.actionform.ClaimProcessingActionForm"
		action="claimProcessing.do?method=getClaimProcessingDetail"
		method="POST" enctype="multipart/form-data">
		<div class="container row"
			style="margin-left: 20px; border-top: 1px solid brown; border-left: 1px solid brown; border-right: 1px solid brown; border-bottom: 1px solid brown; padding-top: 10px; padding-bottom: 10px;">


			<div class="col-md-12">
				<div class="col-md-12 alert alert-primary" role="alert">Search
					Details</div>
			</div>
			<div class="col-lg-12" id="errormsg" style="display: none"></div>
			<div class="col-lg-12">
				<div class="row">


					<div class="col-md-4">
						<label for="CGPAN">CGPAN</label><font color="#FF0000" size="2">*</font>
						<html:text property="cgpan2" styleClass="form-control"
							onchange="return getPromoter();" styleId="cgpan2"
							name="claimProcessingForm" />
					</div>

					<div class="col-md-4">
						<label for="PromoterName">Promoter Name</label><font
							color="#FF0000" size="2">*</font>
						<html:select styleClass="form-control" property="promoterNamee"
							name="claimProcessingForm" styleId="promoterNamee">
							<html:option value="0">Select</html:option>
							<html:optionsCollection property="promoterValues"
								name="claimProcessingForm" label="label" value="value" />
						</html:select>
					</div>
					<div class="col-md-4" style="padding-top: 24px;">

						<button type="button" class="btn btn-primary" id="btnSearch">Search</button>
						&nbsp;&nbsp;
						<button type="button" class="btn btn-primary" id="btnClear">Clear</button>
					</div>
				</div>

				<div id="isDisable">

					<!-- ===============Promoter Section==========Start============== -->
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">PromoterData</div>
						</div>
						<div class="col-md-3">
							<label for="App_Ref_No">Promoter Name</label>
							<html:text property="promoterNameValuee" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="romoterNameValuee" />
						</div>
						<div class="col-md-3">
							<label for="UnitName">Unit Name</label>
							<html:text property="promoterUnitName" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="promoterUnitName" />
						</div>
						<div class="col-md-3">
							<label for="GuranteeAmount">Guarantee amount</label>
							<html:text property="promoterGuranteeAmount" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="guranteeAmount" />
						</div>
						<div class="col-md-3">
							<label for="SactionDate">Sanction date</label>
							<html:text property="promoterSactionDate" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="promoterSactionDate" />
						</div>
						<div class="col-md-3">
							<label for="GuaranteeStartDate">Guarantee Start Date</label>
							<html:text property="promoterGuaranteeStartDate" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="GuaranteeStartDate" />
						</div>

					</div>
					<!-- ===============Promoter Section==========End============== -->


					<!-- ===============Branch Section==========Start============== -->
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert"> Details
								of Operating Office and Lending Branch</div>
						</div>
						<div class="col-md-3">
							<label for="BranchMemberId ">Member Id</label>
							<html:text property="branchMemberId" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="branchMemberId" />
						</div>
						<div class="col-md-3">
							<label for="MliName ">MLI Name </label>
							<html:text property="branchMliName" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="branchMliName" />
						</div>
						<div class="col-md-3">
							<label for="BranchCity">City</label>
							<html:text property="branchCity" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="branchCity" />
						</div>
						<div class="col-md-3">
							<label for="District">District</label>
							<html:text property="branchDistrict" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="branchDistrict" />
						</div>
						<div class="col-md-3">
							<label for="State">State:</label>
							<html:text property="branchState" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="branchState" />
						</div>
						<div class="col-md-3">
							<label for="DealingOfficerName">Dealing Officer Name</label>
							<html:text property="branchDealingOfficerName" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="branchDealingOfficerName" />
						</div>
						<div class="col-md-3">
							<label for="GSTNO">GSTIN No</label>
							<html:text property="branchGstNo" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="branchGstNo" />
						</div>

					</div>
					<!-- ===============Promoter Section==========End============== -->


					<!-- ===============Borrower's Section==========End============== -->
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">Borrower's/Unit's
								Details</div>
						</div>
						<div class="col-md-3">
							<label for="BorrowerCompleteAddress ">Complete Address</label>
							<html:text property="borrowerCompleteAddress" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="borrowerCompleteAddress" />
						</div>
						<div class="col-md-3">
							<label for="BorrowerCity ">City</label>
							<html:text property="borrowerCity" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="borrowerCity" />
						</div>
						<div class="col-md-3">
							<label for="ITPAN ">District</label>
							<html:text property="borrowerDistrict" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="borrowerDistrict" />
						</div>
						<div class="col-md-3">
							<label for="AdharNumber">State</label>
							<html:text property="borrowerState" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="borrowerState" />
						</div>
						<div class="col-md-3">
							<label for="InvestedEquity">Pin Code</label>
							<html:text property="borrowerPinCode" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="borrowerPinCode" />
						</div>


					</div>
					<!-- ===============Borrower's Section==========End============== -->


					<!-- ===============Account Section==========Start============== -->

					<%
						String wilFulDefaulter = "";
						if ((String) request.getAttribute("accountsWilFulDefaulter") != null)
						{
							wilFulDefaulter = (String) request.getAttribute("accountsWilFulDefaulter");
							
						}
						%>

					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">Status
								of Account(s)</div>
						</div>


						<%
								String date="";
								if ((String) request.getAttribute("date") != null)
								{
									date = (String) request.getAttribute("date");
								}
							%>

						<%if (date != null && !(date.equals(""))) {%>

						<div class="col-md-4">
							<label for="PromoterName ">Date on which Account was
								Classified as NPA</label><input type="Text" class="form-control"
								name="claimProcessingForm" disabled id="NPADate"
								placeholder="Enter Promoter Name " value="<%=date%>" />
						</div>
						<%}else{%>
						<div class="col-md-4">
							<label for="PromoterName ">Date on which Account was
								Classified as NPA</label><input type="Text" class="form-control"
								disabled id="NPADate" name="claimProcessingForm"
								placeholder="Enter Promoter Name " />
						</div>
						<%}	%>



						<div class="col-md-4">
							<label for="ITPAN ">Wilful defaulter</label>

							<%if(wilFulDefaulter.equals("Yes")){ %>
							<html:select styleClass="form-control"
								property="accountsWilFulDefaulter" name="claimProcessingForm"
								styleId="accountsWilFulDefaulter">
								<html:option value="0">Select</html:option>
								<option value="Y" selected="selected">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%}else if(wilFulDefaulter.equals("No")){%>
							<html:select styleClass="form-control"
								property="accountsWilFulDefaulter" name="claimProcessingForm"
								styleId="accountsWilFulDefaulter">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>

							<%}else{ %>

							<html:select styleClass="form-control"
								property="accountsWilFulDefaulter" name="claimProcessingForm"
								styleId="accountsWilFulDefaulter">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>

							<% }%>

						</div>

						<%
							String accountsClassified = "";
							if ((String) request.getAttribute("accountsClassified") != null)
							{
								accountsClassified = (String) request.getAttribute("accountsClassified");
								
							}
							%>

						<div class="col-md-4">
							<label for="AdharNumber">Has the account been classified
								as fraud</label>

							<%if(accountsClassified.equals("Yes")){ %>
							<html:select styleClass="form-control"
								property="accountsClassified" name="claimProcessingForm"
								styleId="accountsClassified">
								<html:option value="0">Select</html:option>
								<option value="Y" selected="selected">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%}else if(accountsClassified.equals("No")){%>
							<html:select styleClass="form-control"
								property="accountsClassified" name="claimProcessingForm"
								styleId="accountsClassified">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>

							<%}else{ %>

							<html:select styleClass="form-control"
								property="accountsClassified" name="claimProcessingForm"
								styleId="accountsClassified">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>

							<%} %>

						</div>

						<%
							String accountsEnquiry = "";
							if ((String) request.getAttribute("accountsEnquiry") != null)
							{
								accountsEnquiry = (String) request.getAttribute("accountsEnquiry");
								
								//System.out.println("accountsEnquiry============"+accountsEnquiry);
							}
							%>


						<div class="col-md-4">
							<label for="InvestedEquity">Internal and/or external
								enquiry has been concluded</label>

							<%if(accountsEnquiry.equals("Yes")){ %>
							<html:select styleClass="form-control" property="accountsEnquiry"
								name="claimProcessingForm" styleId="accountsEnquiry">
								<html:option value="0">Select</html:option>
								<option value="Y" selected="selected">Yes</option>
								<option value="N">No</option>
							</html:select>
							<%}else if(accountsEnquiry.equals("No")){%>
							<html:select styleClass="form-control" property="accountsEnquiry"
								name="claimProcessingForm" styleId="accountsEnquiry">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>

							<%}else{%>

							<html:select styleClass="form-control" property="accountsEnquiry"
								name="claimProcessingForm" styleId="accountsEnquiry">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%}%>
						</div>

						<%
						String accountsMliReported="";
						if ((String) request.getAttribute("accountsMliReported") != null)
						{
							accountsMliReported = (String) request.getAttribute("accountsMliReported");
							
							//System.out.println("accountsMliReported============"+accountsMliReported);
						}
						%>



						<div class="col-md-4">
							<label for="InvestedEquity">Involvement of staff of MLI
								has been reported </label>
							<%if(accountsMliReported.equals("Yes")){ %>
							<html:select styleClass="form-control"
								property="accountsMliReported" name="claimProcessingForm"
								styleId="accountsMliReported">
								<html:option value="0">Select</html:option>
								<option value="Y" selected="selected">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%}else if(accountsMliReported.equals("No")){%>
							<html:select styleClass="form-control"
								property="accountsMliReported" name="claimProcessingForm"
								styleId="accountsMliReported">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>

							<%}else {%>

							<html:select styleClass="form-control"
								property="accountsMliReported" name="claimProcessingForm"
								styleId="accountsMliReported">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%} %>
						</div>


						<div class="col-md-4">
							<label for="AccountReasonTurning">Reasons for Account
								turning </label>
							<html:text property="accountReasonTurning" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								disabled="true" styleId="accountReasonTurning" />
						</div>


						<%
							  String dateofIssueofRecallNotice="";
								if ((String) request.getAttribute("dateofIssueofRecallNotice") != null)
								{
									dateofIssueofRecallNotice = (String) request.getAttribute("dateofIssueofRecallNotice");
									//System.out.println("dateofIssueofRecallNotice===" +dateofIssueofRecallNotice);
								}
							%>

						<div class="col-md-4">
							<label for="InvestedEquity">Date of issue of Recall
								Notice <font color="#FF0000" size="3">*</font>
							</label><input type="text" name="" name="claimProcessingForm"
								class="form-control" autocomplete="off"
								id="dateofIssueofRecallNotice"
								onclick="setIssueDateValue();"
								placeholder="Date of issue of Recall Notice"
								value="<%=dateofIssueofRecallNotice%>" />
						</div>




						<div class="col-md-8">
							<label for="AccountSatisfactoryReason">Provide
								satisfactory reason for issuing recall notice prior to NPA date
							</label>
							<html:text property="accountSatisfactoryReason" alt="Reference"
								name="claimProcessingForm" styleClass="form-control"
								styleId="accountSatisfactoryReason" />
						</div>

						<!-- ===============Account Section==========Start============== -->


						<!-- ===============Legal Section==========Start============== -->
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12">
								<div class="col-md-12 alert alert-primary" role="alert">Details
									of Legal Proceedings</div>
							</div>
							<div class="col-md-8">
								<label for="PromoterName">Forum through which legal
									proceedings were initiated against borrower </label>
								<!-- <select onchange="OthersValue();" class="form-control" id="creditrisk"
								placeholder="Enter Promoter Name">
								<option value="0">Select</option>
								<option value="Civil Court">Civil Court</option>
								<option value="Debt Recovery Tribunal">Debt Recovery Tribunal</option>
								<option value="Revenue Recovery Authority">Revenue Recovery Authority</option>
								<option value="Lok Adalat">Lok Adalat</option>
								<option value="SARFAESI ACT">SARFAESI ACT</option>
								<option value="Others">Others</option>
							</select>-->


								<html:select styleClass="form-control"
									property="legalProceedings" name="claimProcessingForm"
									styleId="legalProceedings" onchange="OthersValue();">
									<html:option value="0">Select</html:option>
									<html:optionsCollection property="legalProceedingsValue"
										name="claimProcessingForm" label="label" value="value" />
								</html:select>


							</div>
							<div class="col-md-4" style="display: none" id="legalOtherForm">
								<label for="OtherForum">Other Forum(s)</label>
								<html:text property="legalOtherForms" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									styleId="legalOtherForms" />
							</div>
							<div class="col-md-4">
								<label for="RegistrationNo">Suit / Case Registration No.</label>
								<html:text property="legalRegistration" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									styleId="legalRegistration" />
							</div>

							<%
								String legalsuitFilingDate="";
								if ((String) request.getAttribute("legalsuitFilingDate") != null)
								{
									legalsuitFilingDate = (String) request.getAttribute("legalsuitFilingDate");
									//System.out.println("legalsuitFilingDate=====================" + legalsuitFilingDate);
								}
							%>

							<%if (legalsuitFilingDate != null && !(legalsuitFilingDate.equals(""))) {%>
							<div class="col-md-4">
								<label for="SuitFilingDate">Suit Filing Date</label> <input
									type="text" name="legalsuitFilingDate"
									name="claimProcessingForm" class="form-control" 
									id="legalsuitFilingDate" value="<%=legalsuitFilingDate%>"
									onclick="setSuitDateValue();" />
							</div>
							<%}else{ %>
							<div class="col-md-4">
								<label for="SuitFilingDate">Suit Filing Date</label> <input
									type="text" name="legalsuitFilingDate" autocomplete="off" style="background-color: #fff;"
									name="claimProcessingForm" class="form-control"
									id="legalsuitFilingDate" value="<%=legalsuitFilingDate%>"
									onclick="setSuitDateValue();" />
							</div>
							<%} %>

							<div class="col-md-8">
								<label for="satisfactoryRreason">Provide satisfactory
									reason for filing suit before NPA date </label>
								<html:text property="legalSatisfactoryRreason" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									styleId="legalSatisfactoryRreason" />
							</div>
							<%
							  String dateofpossessionofassets="";
								if ((String) request.getAttribute("dateofpossessionofassets") != null)
								{
									dateofpossessionofassets = (String) request.getAttribute("dateofpossessionofassets");
									//System.out.println("dateofpossessionofassets===" +dateofpossessionofassets);
								}
							%>
							<%
							if (dateofpossessionofassets != null && !(dateofpossessionofassets.equals(""))) {
							%>
							<div class="col-md-4" style="display: none"
								id="dateofpossessionofasset">
								<label for="InvestedEquity">Date of possession of assets
									under sarfaesi act <font color="#FF0000" size="3">*</font>
								</label><input type="text" name="dateofpossessionofassets"
									name="claimProcessingForm" class="form-control"
									id="dateofpossessionofassets" onclick="setAssetDateValue();"
									placeholder="Date of possession of assets under sarfaesi act "
									value="<%=dateofpossessionofassets%>" />
							</div>
							<%}else{ %>

							<div class="col-md-4" style="display: none"
								id="dateofpossessionofasset">
								<label for="InvestedEquity">Date of possession of assets
									under sarfaesi act <font color="#FF0000" size="3">*</font>
								</label><input type="text" name="dateofpossessionofassets"
									name="claimProcessingForm" class="form-control"
									id="dateofpossessionofassets" autocomplete="off"
									onclick="setAssetDateValue();"
									placeholder="Date of possession of assets under sarfaesi act " />
							</div>
							<%} %>

							<div class="col-md-4">
								<label for="Location">Location </label>
								<html:text property="legalLocation" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									styleId="legalLocation" />
							</div>
							<div class="col-md-4">
								<label for="AmountClaimed">Amount Claimed in the Suit in
									Rs.</label>
								<html:text property="legalAmountClaimed" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									maxlength="11" onkeypress="return isNumberKey(event,this)"
									styleId="legalAmountClaimed" />
							</div>

							<%
						   String roleName = "";
						String filenameName="";
						if ((String) request.getAttribute("role") != null)
							
						{
							roleName = (String) request.getAttribute("role");
						}
						
						
						  if ((String) request.getAttribute("filenameName") != null)
						   {
						filenameName = (String) request.getAttribute("filenameName");
						
						System.out.println("filenameName============"+filenameName);
						   }
						%>


							<%
						System.out.println("Rolename is ::" + roleName);
						if(roleName.contains("CHECKER") ||  roleName.contains("NOTHING")){%>
							<div class="col-md-8" id="Display">
								<label for="InvestedEquity">View Attachments</label> <a href="#"
									id="downloadFile"><%=filenameName%></a>
							</div>
							<%}else{%>
							<div class="col-md-8" id="Upload">
								<label for="InvestedEquity">Any Attachments </label>
								<html:file property="nocDocument1" name="claimProcessingForm"
									styleClass="file1" styleId="legaAattachment"></html:file>
							</div>
							<%} %>
						</div>
						<!-- ===============Legal Section==========End============== -->

						<!-- ===============TC Section==========Start============== -->
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12">
								<div class="col-md-12 alert alert-primary" role="alert">Term
									Loans (TC) limit Details</div>
							</div>
							<div class="col-md-4">
								<label for="TotalDisbursement ">Total Disbursement
									Amount</label>
								<html:text property="tcTotalDisbursement" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									disabled="true" styleId="tcTotalDisbursement" />
							</div>
							<div class="col-md-4">
								<label for="DateofLastDisbursement ">Date of Last
									Disbursement</label>
								<html:text property="tcDateofLastDisbursement" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									disabled="true" styleId="tcDateofLastDisbursement" />
							</div>
							<div class="col-md-4">
								<label for="Principal Amount">Repayments Principal
									Amount(Rs.)</label>
								<html:text property="tcPrincipalAmount" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									disabled="true" styleId="tcPrincipalAmount" />
							</div>
							<div class="col-md-4">
								<label for="Repayments Interest">Repayments Interest and
									Other Charges</label>
								<html:text property="tcRepaymentsInterest" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									disabled="true" styleId="tcRepaymentsInterest" />
							</div>

							<div class="col-md-4">
								<label for="NPAOutstanding"> NPA Outstanding Principal
									Amount(Rs.)</label>
								<html:text property="tcOutstandingAmount" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									disabled="true" styleId="tcOutstandingAmount" />
							</div>
							<div class="col-md-4">
								<label for="OutstandingInterest"> NPA Outstanding
									Interest and Other Charges</label>
								<html:text property="tcOutstandingInterest" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									disabled="true" styleId="tcOutstandingInterest" />
							</div>
							<div class="col-md-4">
								<label for="OutstandingStated">Outstanding stated in the
									Civil Suit /Case filed(Rs.)</label>
								<html:text property="tcOutstandingStatedCivil" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									maxlength="11" onkeypress="return isNumberKey(event,this)"
									styleId="tcOutstandingStatedCivil" />
							</div>
							<div class="col-md-4">
								<label for="OutstandingLodgement">Outstanding As On Date
									of Lodgement of Claim </label>
								<html:text property="tcOutstandingLodgement" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									maxlength="11" onkeypress="return isNumberKey(event,this)"
									styleId="tcOutstandingLodgement" />
							</div>
							
							<%
						String tcAccountRestructed="";
						if ((String) request.getAttribute("tcAccountRestructed") != null)
						{
							tcAccountRestructed = (String) request.getAttribute("tcAccountRestructed");
							
							//System.out.println("accountsMliReported============"+accountsMliReported);
						}
						%>



						<div class="col-md-4">
							<label for="InvestedEquity">Account Restructed or not</label>
							<%if(tcAccountRestructed.equals("Yes")){ %>
							<html:select styleClass="form-control"
								property="tcAccountRestructed" name="claimProcessingForm"
								styleId="tcAccountRestructed">
								<html:option value="0">Select</html:option>
								<option value="Y" selected="selected">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%}else if(tcAccountRestructed.equals("No")){%>
							<html:select styleClass="form-control"
								property="tcAccountRestructed" name="claimProcessingForm"
								styleId="tcAccountRestructed">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>

							<%}else {%>

							<html:select styleClass="form-control"
								property="tcAccountRestructed" name="claimProcessingForm"
								styleId="tcAccountRestructed">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%} %>
						</div>
						</div>
						<!-- ===============TC Section==========End============== -->

						<!-- =========Recovery======Borrower Section==========Start======== -->
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12">
								<div class="col-md-12 alert alert-primary" role="alert"> Recovery
									made from Borrower / Unit after account classified as NPA</div>
							</div>
							<div class="col-md-12">
								<label for="PromoterName ">Term Loan / Composite Loan </label>
							</div>
							<div class="col-md-4">
								<label for="RecoveryPrincipal ">Principal(Rs)</label>
								<html:text property="recoveryPrincipal" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									maxlength="11" onkeypress="return isNumberKey(event,this)"
									styleId="recoveryPrincipal" />
							</div>
							<div class="col-md-4">
								<label for="RecoveryInterest">Interest & Other Charges </label>
								<html:text property="recoveryInterest" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									maxlength="11" onkeypress="return isNumberKey(event,this)"
									styleId="recoveryInterest" />
							</div>
							<div class="col-md-4">
								<label for="RecoveryMode">Mode of Recovery</label>
								<html:text property="recoveryMode" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									styleId="recoveryMode" />
							</div>
						</div>
						<!-- ===============Borrower Section==========End======== -->

						<!-- ===============Security Section==========Start======== -->
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12">
								<div class="col-md-12 alert alert-primary" role="alert"> Security
									and Personal Guarantee Details</div>
							</div>

							<div class="col-md-10">
								<label for="PromoterName "> Have you ensured inclusion
									of unappropriated receipts also in the amount of recovery after
									NPA indicated above?</label>
							</div>


							<%
						String recoveryafterNPAindicated="";
						if ((String) request.getAttribute("recoveryafterNPAindicated") != null)
						{
							recoveryafterNPAindicated = (String) request.getAttribute("recoveryafterNPAindicated");
							
							//System.out.println("recoveryafterNPAindicated============"+recoveryafterNPAindicated);
						}
						%>


							<div class="col-md-2">
								<!-- <select onchange="" class="form-control" id="creditrisk"
							placeholder="Enter Promoter Name">
							<option value="0">Select</option>recoveryafterNPAindicatedYN
							<option value="Y">Yes</option>
							<option value="N">No</option>
						</select>-->
								<%if(recoveryafterNPAindicated.equals("Yes")){%>
								<html:select styleClass="form-control"
									property="recoveryafterNPAindicated" name="claimProcessingForm"
									styleId="recoveryafterNPAindicated">
									<html:option value="0">Select</html:option>
									<option value="Y" selected="selected">Yes</option>
									<option value="N">No</option>
								</html:select>
								<%}else if(recoveryafterNPAindicated.equals("No")){%>
								<html:select styleClass="form-control"
									property="recoveryafterNPAindicated" name="claimProcessingForm"
									styleId="recoveryafterNPAindicated">
									<html:option value="0">Select</html:option>
									<option value="Y">Yes</option>
									<option value="N" selected="selected">No</option>
								</html:select>
								<%}else{%>

								<html:select styleClass="form-control"
									property="recoveryafterNPAindicated" name="claimProcessingForm"
									styleId="recoveryafterNPAindicated">
									<html:option value="0">Select</html:option>
									<option value="Y">Yes</option>
									<option value="N">No</option>
								</html:select>

								<%} %>
							</div>


							<div class="col-md-10">
								<label for="ITPAN "> Do you confirm feeding of correct
									value as recoveries after NPA?</label>
							</div>



							<%
						String valueasrecoveriesafterNPA="";
						if ((String) request.getAttribute("valueasrecoveriesafterNPA") != null)
						{
							valueasrecoveriesafterNPA = (String) request.getAttribute("valueasrecoveriesafterNPA");
							
							//System.out.println("valueasrecoveriesafterNPA============"+valueasrecoveriesafterNPA);
						}
						%>


							<div class="col-md-2">
								<!-- <select onchange="" class="form-control" id="creditrisk"
								placeholder="Enter Promoter Name">
								<option value="0">Select</option>
								<option value="Y">Yes</option>
								<option value="N">No</option>
							</select>-->
								<%if(valueasrecoveriesafterNPA.equals("Yes")){%>
								<html:select styleClass="form-control"
									property="valueasrecoveriesafterNPA" name="claimProcessingForm"
									styleId="valueasrecoveriesafterNPA">
									<html:option value="0">Select</html:option>
									<option value="Y" selected="selected">Yes</option>
									<option value="N">No</option>
								</html:select>
								<%}else if(valueasrecoveriesafterNPA.equals("No")){%>
								<html:select styleClass="form-control"
									property="valueasrecoveriesafterNPA" name="claimProcessingForm"
									styleId="valueasrecoveriesafterNPA">
									<html:option value="0">Select</html:option>
									<option value="Y">Yes</option>
									<option value="N" selected="selected">No</option>
								</html:select>

								<%}else{%>

								<html:select styleClass="form-control"
									property="valueasrecoveriesafterNPA" name="claimProcessingForm"
									styleId="valueasrecoveriesafterNPA">
									<html:option value="0">Select</html:option>
									<option value="Y">Yes</option>
									<option value="N" selected="selected">No</option>
								</html:select>
								<%} %>
							</div>



							<div class="col-md-10">
								<label for="Totalamount"> Total amount for which
									guarantee claim is being preferred (Amount to be claimed):</label>
							</div>
							<div class="col-md-2">
								<html:text property="personalAmountclaim" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									maxlength="11" onkeypress="return isNumberKey(event,this)"
									styleId="personalAmountclaim" />
							</div>
						</div>
						<!-- ===============Security Section==========End======== -->


						<!-- ===========MLI====General inforamtion Section==Start======== -->
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12">
								<div class="col-md-12 alert alert-primary" role="alert"> General
									information - to be filled by the MLI</div>
							</div>

							<div class="col-md-9">
								<label for="MLI'sComment  ">  MLI's Comment on financial
									position of Borrower/Unit</label>
							</div>
							<div class="col-md-3">
								<html:text property="generalMLIComment" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									styleId="generalMLIComment" />
							</div>

							<div class="col-md-9">
								<label for="ITPAN ">  Details of Financial Assistance
									provided/being considered by MLI to minimize default </label>
							</div>
							<div class="col-md-3">
								<html:text property="generalMLIFinancial" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									styleId="generalMLIFinancial" />
							</div>
							<div class="col-md-9">
								<label for="AdharNumber">  Does the MLI propose to
									provide credit support to Chief Promoter/Borrower for any other
									project</label>
							</div>



							<%
						String creditsupportforanyotherproject="";
						if ((String) request.getAttribute("creditsupportforanyotherproject") != null)
						{
							creditsupportforanyotherproject = (String) request.getAttribute("creditsupportforanyotherproject");
							
							//System.out.println("creditsupportforanyotherproject============"+creditsupportforanyotherproject);
						}
						%>


							<div class="col-md-3">
								<!-- <select onchange="" class="form-control" id="creditrisk"
								placeholder="Enter Promoter Name">
								<option value="0">Select</option>
								<option value="Y">Yes</option>
								<option value="N">No</option>
							</select>-->
								<%if(creditsupportforanyotherproject.equals("Yes")){%>
								<html:select styleClass="form-control"
									property="creditsupportforanyotherproject"
									name="claimProcessingForm"
									styleId="creditsupportforanyotherproject">
									<html:option value="0">Select</html:option>
									<option value="Y" selected="selected">Yes</option>
									<option value="N">No</option>
								</html:select>
								<%}else if(creditsupportforanyotherproject.equals("No")){%>

								<html:select styleClass="form-control"
									property="creditsupportforanyotherproject"
									name="claimProcessingForm"
									styleId="creditsupportforanyotherproject">
									<html:option value="0">Select</html:option>
									<option value="Y">Yes</option>
									<option value="N" selected="selected">No</option>
								</html:select>

								<%}else{%>
								<html:select styleClass="form-control"
									property="creditsupportforanyotherproject"
									name="claimProcessingForm"
									styleId="creditsupportforanyotherproject">
									<html:option value="0">Select</html:option>
									<option value="Y">Yes</option>
									<option value="N">No</option>
								</html:select>
								<%} %>
							</div>


							<div class="col-md-9">
								<label for="AdharNumber">   Details Of Bank Facility
									already provided to Borrower</label>
							</div>
							<div class="col-md-3">
								<html:text property="generalMLIBank" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									styleId="generalMLIBank" />
							</div>
							<div class="col-md-9">
								<label for="AdharNumber"> Does the MLI advise placing
									the Borrower and/or Chief Promoter under watch-List of CGTMSE</label>
							</div>



							<%
						String promoterunderwatchListofCGTMSE="";
						if ((String) request.getAttribute("promoterunderwatchListofCGTMSE") != null)
						{
							promoterunderwatchListofCGTMSE = (String) request.getAttribute("promoterunderwatchListofCGTMSE");
							
							//System.out.println("promoterunderwatchListofCGTMSE============"+promoterunderwatchListofCGTMSE);
						}
						%>



							<div class="col-md-3">
								<!-- <select onchange="" class="form-control" id="creditrisk"
								placeholder="Enter Promoter Name">
								<option value="0">Select</option>
								<option value="Y">Yes</option>
								<option value="N">No</option>
							</select>-->

								<%if(promoterunderwatchListofCGTMSE.equals("Yes")){%>
								<html:select styleClass="form-control"
									property="promoterunderwatchListofCGTMSE"
									name="claimProcessingForm"
									styleId="promoterunderwatchListofCGTMSE">
									<html:option value="0">Select</html:option>
									<option value="Y" selected="selected">Yes</option>
									<option value="N">No</option>
								</html:select>
								<%}else if(promoterunderwatchListofCGTMSE.equals("No")){%>

								<html:select styleClass="form-control"
									property="promoterunderwatchListofCGTMSE"
									name="claimProcessingForm"
									styleId="promoterunderwatchListofCGTMSE">
									<html:option value="0">Select</html:option>
									<option value="Y">Yes</option>
									<option value="N" selected="selected">No</option>
								</html:select>
								<%}else{%>
								<html:select styleClass="form-control"
									property="promoterunderwatchListofCGTMSE"
									name="claimProcessingForm"
									styleId="promoterunderwatchListofCGTMSE">
									<html:option value="0">Select</html:option>
									<option value="Y">Yes</option>
									<option value="N">No</option>
								</html:select>
								<%} %>
							</div>
							<div class="col-md-12">
								<label for="AdharNumber">Remark</label>
								<html:text property="generalMLIRemark" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									styleId="generalMLIRemark" />
							</div>
						</div>
						<!-- ===========MLI====General inforamtion Section==Start======== -->


						<!-- ==========Value=of Securities available==Start======== -->
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12">
								<div class="col-md-12 alert alert-primary" role="alert">Value
									of Securities available</div>
							</div>

							<div class="col-md-2 alert alert-secondary" role="alert"
								style="margin-top: 10px;">Nature</div>
							<div class="col-md-2 alert alert-secondary" role="alert"
								style="margin-top: 10px;">As on date of Sanction</div>
							<div class="col-md-2 alert alert-secondary" role="alert"
								style="margin-top: 10px;">As on date of NPA</div>
							<div class="col-md-2 alert alert-secondary" role="alert"
								style="margin-top: 10px;">As on Date of Preferrment of
								Claim</div>
							<div class="col-md-2 alert alert-secondary" role="alert"
								style="margin-top: 10px;">Networth of guarantor /
								Promoter(in Rs.)</div>
							<div class="col-md-2 alert alert-secondary" role="alert"
								style="margin-top: 10px;">Reasons for Reduction in the
								value of Security, if any</div>
						</div>




						<div class="row" style="margin-top: 10px; margin-left: 2px;">
							<div class="col-md-2" style="margin-top: 5px;">
								Land<font color="#FF0000" size="2">*</font>
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction0" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->

								<html:text property="landSection" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;"
									onchange="calculatedateofSanction();" disabled="true"
									styleId="dateofSanction0" maxlength="11" />
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction0" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->

								<html:text property="landDataOfNPA" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" onchange="calculateNPA();"
									styleId="dateofNPA0" maxlength="11" disabled="true" />
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNPA();" id="dateofNPA0" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->

								<html:text property="landDataOfClaim" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" maxlength="11" onkeypress="return isNumberKey(event,this)"
									onchange="calculatedateofSanctionClaim();"
									styleId="dateofclaim0" />
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								<!--  <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNetworthofguarantor();" id="Networthofguarantor0" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
								<html:text property="landNetWorth" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" maxlength="11" onkeypress="return isNumberKey(event,this)"
									onchange="calculateNetworthofguarantor();"
									styleId="Networthofguarantor0" />
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								<!--  <select class="form-control" id="ReasonsforReduction0" placeholder="Enter Promoter Name">
                                    <option value="volvo">Volvo</option>
                                    <option value="saab">Saab</option>
                                    <option value="mercedes">Mercedes</option>
                                    <option value="audi">Audi</option>
                                </select>-->

								<html:select styleClass="form-control" property="landReason"
									name="claimProcessingForm" styleId="ReasonsforReduction0">
									<html:option value="0">Select</html:option>
									<html:optionsCollection property="reproductionValue"
										name="claimProcessingForm" label="label" value="value" />
								</html:select>
							</div>
						</div>

						<div class="row" style="margin-top: 10px; margin-left: 2px;">
							<div class="col-md-2" style="margin-top: 5px;">
								Building<font color="#FF0000" size="2">*</font>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								<!--  <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction1" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->

								<html:text property="buildingSection" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" maxlength="11" onkeypress="return isNumberKey(event,this)"
									onchange="calculatedateofSanction();" disabled="true"
									styleId="dateofSanction1" />
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								<!--  <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction1" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->

								<html:text property="buildingDateOfNpa" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" onchange="calculateNPA();"
									maxlength="11"
									styleId="dateofNPA1" disabled="true" />
							</div>


							<div class="col-md-2" style="margin-top: 5px;">
								<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNPA();" id="dateofNPA1" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->

								<html:text property="buildingDateOfClaim" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" maxlength="11" onkeypress="return isNumberKey(event,this)"
									onchange="calculatedateofSanctionClaim();"
									styleId="dateofClaim1" />
							</div>



							<div class="col-md-2" style="margin-top: 5px;">
								<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNetworthofguarantor();" id="Networthofguarantor1" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->

								<html:text property="buildingNetWorth" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" maxlength="11" onkeypress="return isNumberKey(event,this)"
									onchange="calculateNetworthofguarantor();"
									styleId="Networthofguarantor1" />
							</div>


							<div class="col-md-2" style="margin-top: 5px;">
								<!-- <select class="form-control" id="ReasonsforReduction1" placeholder="Enter Promoter Name">
                                    <option value="volvo">Volvo</option>
                                    <option value="saab">Saab</option>
                                    <option value="mercedes">Mercedes</option>
                                    <option value="audi">Audi</option>
                                </select>-->

								<html:select styleClass="form-control" property="buildingReason"
									name="claimProcessingForm" styleId="ReasonsforReduction2">
									<html:option value="0">Select</html:option>
									<html:optionsCollection property="reproductionValue"
										name="claimProcessingForm" label="label" value="value" />
								</html:select>
							</div>
						</div>

						<div class="row" style="margin-top: 10px; margin-left: 2px;">
							<div class="col-md-2" style="margin-top: 5px;">
								Plant and Machinery<font color="#FF0000" size="2">*</font>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								<!--  <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction2" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
								<html:text property="plantMachinerySection" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" maxlength="11" onkeypress="return isNumberKey(event,this)"
									onchange="calculatedateofSanction();" disabled="true"
									styleId="dateofSanction2" />
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								<!--  <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction2" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->


								<html:text property="plantMachineryDateNpa" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" onchange="calculateNPA();"
									maxlength="11"
									styleId="dateofNPA2" disabled="true" />

							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								<!--  <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNPA();" id="dateofNPA2" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
								<html:text property="plantMachineryClaim" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" maxlength="11" onkeypress="return isNumberKey(event,this)"
									onchange="calculatedateofSanctionClaim();"
									styleId="dateofClaim2" />
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNetworthofguarantor();" id="Networthofguarantor2" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->


								<html:text property="plantMachineryNetWorth" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" maxlength="11" onkeypress="return isNumberKey(event,this)"
									onchange="calculateNetworthofguarantor();"
									styleId="Networthofguarantor2" />
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								<!--  <select class="form-control" id="ReasonsforReduction2" placeholder="Enter Promoter Name">
                                    <option value="volvo">Volvo</option>
                                    <option value="saab">Saab</option>
                                    <option value="mercedes">Mercedes</option>
                                    <option value="audi">Audi</option>
                                </select>-->

								<html:select styleClass="form-control"
									property="plantMachineryReason" name="claimProcessingForm"
									styleId="ReasonsforReduction3">
									<html:option value="0">Select</html:option>
									<html:optionsCollection property="reproductionValue"
										name="claimProcessingForm" label="label" value="value" />
								</html:select>
							</div>
						</div>

						<div class="row" style="margin-top: 10px; margin-left: 2px;">
							<div class="col-md-2" style="margin-top: 5px;">
								Other Fixed /Movable Assets<font color="#FF0000" size="2">*</font>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction3" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
								<html:text property="otherFixedMovableSection" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" maxlength="11" onkeypress="return isNumberKey(event,this)"
									onchange="calculatedateofSanction();" disabled="true"
									styleId="dateofSanction3" />
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction3" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->

								<html:text property="otherFixedMovableDateOfNpa" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" onchange="calculateNPA();"
									maxlength="11"
									styleId="dateofNPA3" disabled="true" />


							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								<!--   <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNPA();" id="dateofNPA3" placeholder="Enter CGPAN" value="0.00" />
                           </div> -->

								<html:text property="otherFixedMovableClaim" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" maxlength="11" onkeypress="return isNumberKey(event,this)"
									onchange="calculatedateofSanctionClaim();"
									styleId="dateofClaim3" />

							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNetworthofguarantor();" id="Networthofguarantor3" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
								<html:text property="otherFixedMovableNetWorth" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" maxlength="11" onkeypress="return isNumberKey(event,this)"
									onchange="calculateNetworthofguarantor();"
									styleId="Networthofguarantor3" />
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								<!-- <select class="form-control" id="ReasonsforReduction3" placeholder="Enter Promoter Name">
                                    <option value="volvo">Volvo</option>
                                    <option value="saab">Saab</option>
                                    <option value="mercedes">Mercedes</option>
                                    <option value="audi">Audi</option>
                                </select>-->
								<html:select styleClass="form-control"
									property="otherFixedReason" name="claimProcessingForm"
									styleId="ReasonsforReduction4">
									<html:option value="0">Select</html:option>
									<html:optionsCollection property="reproductionValue"
										name="claimProcessingForm" label="label" value="value" />
								</html:select>
							</div>
						</div>
						<div class="row" style="margin-top: 10px; margin-left: 2px;">
							<div class="col-md-2" style="margin-top: 5px;">
								Current Assets<font color="#FF0000" size="2">*</font>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction4" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->

								<html:text property="currentAssetSection" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" disabled="true" maxlength="11" onkeypress="return isNumberKey(event,this)"
									onchange="calculatedateofSanction();" styleId="dateofSanction4" />
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction4" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
								<html:text property="currentAssetDateOfNpa" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" onchange="calculateNPA();"
									maxlength="11"
									styleId="dateofNPA4" disabled="true" />

							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNPA();" id="dateofNPA4" placeholder="Enter CGPAN" value="0.00" />
                           </div> -->
								<html:text property="currentAssetClaim" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" maxlength="11" onkeypress="return isNumberKey(event,this)"
									onchange="calculatedateofSanctionClaim();"
									styleId="dateofClaim4" />

							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								<!--  <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNetworthofguarantor();" id="Networthofguarantor4" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
								<html:text property="currentAssetNetWorth" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" maxlength="11" onkeypress="return isNumberKey(event,this)"
									onchange="calculateNetworthofguarantor();"
									styleId="Networthofguarantor4" />
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								<!-- <select class="form-control" id="ReasonsforReduction5" placeholder="Enter Promoter Name">
                                    <option value="volvo">Volvo</option>
                                    <option value="saab">Saab</option>
                                    <option value="mercedes">Mercedes</option>
                                    <option value="audi">Audi</option>
                                </select>-->
								<html:select styleClass="form-control"
									property="currentAssetReson" name="claimProcessingForm"
									styleId="ReasonsforReduction5">
									<html:option value="0">Select</html:option>
									<html:optionsCollection property="reproductionValue"
										name="claimProcessingForm" label="label" value="value" />
								</html:select>
							</div>
						</div>
						<div class="row" style="margin-top: 10px; margin-left: 2px;">
							<div class="col-md-2" style="margin-top: 5px;">
								Others<font color="#FF0000" size="2">*</font>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								<!--  <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction5" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
								<html:text property="otherSection" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" disabled="true" maxlength="11" onkeypress="return isNumberKey(event,this)"
									onchange="calculatedateofSanction();" styleId="dateofSanction5" />
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								<!--  <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction5" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
								<html:text property="otherNpa" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" onchange="calculateNPA();"
									maxlength="11" onkeypress="return isNumberKey(event,this)"
									styleId="dateofNPA5" disabled="true" />

							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								<!--   <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNPA();" id="dateofNPA5" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->

								<html:text property="otherClaim" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" maxlength="11" onkeypress="return isNumberKey(event,this)"
									onchange="calculatedateofSanctionClaim();"
									styleId="dateofClaim5" />
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								<!--  <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNetworthofguarantor();" id="Networthofguarantor5" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
								<html:text property="otherNetWorth" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									style="text-align: right;" maxlength="11" onkeypress="return isNumberKey(event,this)"
									onchange="calculateNetworthofguarantor();"
									styleId="Networthofguarantor5" />
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								<!--  <select class="form-control" id="ReasonsforReduction5" placeholder="Enter Promoter Name">
                                    <option value="volvo">Volvo</option>
                                    <option value="saab">Saab</option>
                                    <option value="mercedes">Mercedes</option>
                                    <option value="audi">Audi</option>
                                </select>-->

								<html:select styleClass="form-control" property="otherReason"
									name="claimProcessingForm" styleId="ReasonsforReduction6">
									<html:option value="0">Select</html:option>
									<html:optionsCollection property="reproductionValue"
										name="claimProcessingForm" label="label" value="value" />
								</html:select>
							</div>
						</div>


						<div class="row" style="margin-top: 10px; margin-left: 2px;">
							<div class="col-md-2" style="margin-top: 5px; font-weight: 800;">Total</div>

							<!-- <div class="col-md-2 alert alert-success" role="alert" style="margin-top: 10px; text-align: right;" id="dateofSanction">0.00</div> -->
							<div class="col-md-2 " style="margin-top: 5px;">
								<html:text property="dateOfSectionTotal" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									disabled="true" style="margin-top: 10px; text-align: right;"
									styleId="dateofSanction" />
							</div>

							<!--  <div class="col-md-2 alert alert-success" role="alert" style="margin-top: 10px; text-align: right;" id="divNPA">0.00</div> -->
							<div class="col-md-2 " style="margin-top: 5px;">
								<html:text property="dateOfNpaTotal" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									disabled="true" style="margin-top: 10px; text-align: right;"
									styleId="divNPA" />
							</div>

							<div class="col-md-2 " style="margin-top: 5px;">
								<html:text property="dateOfClaimTotal" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									disabled="true" style="margin-top: 10px; text-align: right;"
									styleId="divClaim" />
							</div>
							<!-- <div class="col-md-2 alert alert-success" role="alert" style="margin-top: 10px; text-align: right;" id="Networthofguarantor">0.00</div> -->

							<div class="col-md-2 " style="margin-top: 5px;">
								<html:text property="netWorthTotal" alt="Reference"
									name="claimProcessingForm" styleClass="form-control"
									disabled="true" style="margin-top: 10px; text-align: right;"
									styleId="Networthofguarantor" />
							</div>
							<div class="col-md-2 " style="margin-top: 5px;">
								<html:hidden property="message" name="claimProcessingForm"
									styleId="message" />
								<html:hidden property="role" name="claimProcessingForm"
									styleId="rolenAME" />
								<html:hidden property="isSearchClicked"
									name="claimProcessingForm" styleId="isSearchClicked" />

								<html:hidden property="checkerReturn" name="claimProcessingForm"
									styleId="checkerReturn" />
							</div>

						</div>

						<!-- ==========Value=of Securities available==End======== -->
					</div>
					<div class="row"
						style="margin-top: 10px; BACKGROUND-COLOR: GRAY; COLOR: WHITE; FONT-WEIGHT: 700; TEXT-ALIGN: center;">
						<div class="col-md-1">S.No.</div>
						<div class="col-md-7"> Description</div>
						<div class="col-md-4">Yes/No</div>
					</div>
					<div class="col-lg-12" id="errormsge" style="display: none"></div>

					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">1</div>
						<div class="col-md-7">Activity is eligible as per Credit
							Guarantee Scheme(CGS)</div>
						<%
						String checkList1="";
						if ((String) request.getAttribute("checkList1") != null)
						{
							checkList1 = (String) request.getAttribute("checkList1");
							
							//System.out.println("checkList1============"+checkList1);
						}
						%>
						<div class="col-md-4">
							<%if(checkList1.equals("Yes")){%>
							<html:select styleClass="form-control" property="checkList1"
								name="claimProcessingForm" styleId="checkList1">
								<html:option value="0">Select</html:option>
								<option value="Y" selected="selected">Yes</option>
								<option value="N">No</option>
							</html:select>
							<%}else if(checkList1.equals("No")){%>
							<html:select styleClass="form-control" property="checkList1"
								name="claimProcessingForm" styleId="checkList1">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>
							<%} else{%>
							<html:select styleClass="form-control" property="checkList1"
								name="claimProcessingForm" styleId="checkList1">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%} %>
						</div>
					</div>
					<%
						String checkList2="";
						if ((String) request.getAttribute("checkList2") != null)
						{
							checkList2 = (String) request.getAttribute("checkList2");
							
							//System.out.println("checkList2============"+checkList2);
						}
						%>
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">2</div>
						<div class="col-md-7">Wheather CIBIL done/CIR/KYC obtained
							and findings are satisfactory.</div>
						<div class="col-md-4">
							<%if(checkList2.equals("Yes")){%>
							<html:select styleClass="form-control" property="checkList2"
								name="claimProcessingForm" styleId="checkList2">
								<html:option value="0">Select</html:option>
								<option value="Y" selected="selected">Yes</option>
								<option value="N">No</option>
							</html:select>
							<%}else if(checkList2.equals("No")){%>
							<html:select styleClass="form-control" property="checkList2"
								name="claimProcessingForm" styleId="checkList2">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>
							<%} else{%>
							<html:select styleClass="form-control" property="checkList2"
								name="claimProcessingForm" styleId="checkList2">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%} %>
						</div>
					</div>
					<%
						String checkList3="";
						if ((String) request.getAttribute("checkList3") != null)
						{
							checkList3 = (String) request.getAttribute("checkList3");
							
							//System.out.println("checkList3============"+checkList3);
						}
						%>
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">3</div>
						<div class="col-md-7">Rate charged on loan is as per CGS
							guidelines.</div>
						<div class="col-md-4">
							<%if(checkList3.equals("Yes")){%>
							<html:select styleClass="form-control" property="checkList3"
								name="claimProcessingForm" styleId="checkList3">
								<html:option value="0">Select</html:option>
								<option value="Y" selected="selected">Yes</option>
								<option value="N">No</option>
							</html:select>
							<%}else if(checkList3.equals("No")){%>
							<html:select styleClass="form-control" property="checkList3"
								name="claimProcessingForm" styleId="checkList3">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>
							<%} else{%>
							<html:select styleClass="form-control" property="checkList3"
								name="claimProcessingForm" styleId="checkList3">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%} %>
						</div>
					</div>
					<%
						String checkList4="";
						if ((String) request.getAttribute("checkList4") != null)
						{
							checkList4 = (String) request.getAttribute("checkList4");
							
							//System.out.println("checkList3============"+checkList4);
						}
						%>

					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">4</div>
						<div class="col-md-7">Third party gaurantee/collateral
							security stipulated.</div>
						<div class="col-md-4">
							<%if(checkList4.equals("Yes")){%>
							<html:select styleClass="form-control" property="checkList4"
								name="claimProcessingForm" styleId="checkList4">
								<html:option value="0">Select</html:option>
								<option value="Y" selected="selected">Yes</option>
								<option value="N">No</option>
							</html:select>
							<%}else if(checkList4.equals("No")){%>
							<html:select styleClass="form-control" property="checkList4"
								name="claimProcessingForm" styleId="checkList4">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>
							<%} else{%>
							<html:select styleClass="form-control" property="checkList4"
								name="claimProcessingForm" styleId="checkList4">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%} %>
						</div>
					</div>
					<%
						String checkList5="";
						if ((String) request.getAttribute("checkList5") != null)
						{
							checkList5 = (String) request.getAttribute("checkList5");
							
							//System.out.println("checkList5============"+checkList5);
						}
						%>

					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">5</div>
						<div class="col-md-7">Date of NPA as fed in the system is as
							per RBI guidelines.</div>
						<div class="col-md-4">
							<%if(checkList5.equals("Yes")){%>
							<html:select styleClass="form-control" property="checkList5"
								name="claimProcessingForm" styleId="checkList5">
								<html:option value="0">Select</html:option>
								<option value="Y" selected="selected">Yes</option>
								<option value="N">No</option>
							</html:select>
							<%}else if(checkList5.equals("No")){%>
							<html:select styleClass="form-control" property="checkList5"
								name="claimProcessingForm" styleId="checkList5">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>
							<%} else{%>
							<html:select styleClass="form-control" property="checkList5"
								name="claimProcessingForm" styleId="checkList5">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%} %>
						</div>
					</div>
					<%
						String checkList6="";
						if ((String) request.getAttribute("checkList6") != null)
						{
							checkList6 = (String) request.getAttribute("checkList6");
							
							//System.out.println("checkList6============"+checkList6);
						}
						%>
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">6</div>
						<div class="col-md-7">Wheather outstanding amount mentioned
							in the claim application form is with respect to the NPA date as
							reported in claim form.</div>
						<div class="col-md-4">
							<%if(checkList6.equals("Yes")){%>
							<html:select styleClass="form-control" property="checkList6"
								name="claimProcessingForm" styleId="checkList6">
								<html:option value="0">Select</html:option>
								<option value="Y" selected="selected">Yes</option>
								<option value="N">No</option>
							</html:select>
							<%}else if(checkList6.equals("No")){%>
							<html:select styleClass="form-control" property="checkList6"
								name="claimProcessingForm" styleId="checkList6">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>
							<%} else{%>
							<html:select styleClass="form-control" property="checkList6"
								name="claimProcessingForm" styleId="checkList6">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%} %>
						</div>
					</div>
					<%
						String checkList7="";
						if ((String) request.getAttribute("checkList7") != null)
						{
							checkList7 = (String) request.getAttribute("checkList7");
							
							//System.out.println("checkList7============"+checkList7);
						}
						%>
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">7</div>
						<div class="col-md-7">Whether serious deficiencies have been
							observed in the matter of
							appraisal/renewal/disbursement/followup/conduct of the account.</div>
						<div class="col-md-4">
							<%if(checkList7.equals("Yes")){%>
							<html:select styleClass="form-control" property="checkList7"
								name="claimProcessingForm" styleId="checkList7">
								<html:option value="0">Select</html:option>
								<option value="Y" selected="selected">Yes</option>
								<option value="N">No</option>
							</html:select>
							<%}else if(checkList7.equals("No")){%>
							<html:select styleClass="form-control" property="checkList7"
								name="claimProcessingForm" styleId="checkList7">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>
							<%} else{%>
							<html:select styleClass="form-control" property="checkList7"
								name="claimProcessingForm" styleId="checkList7">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%} %>
						</div>
					</div>
					<%
						String checkList8="";
						if ((String) request.getAttribute("checkList8") != null)
						{
							checkList8 = (String) request.getAttribute("checkList8");
							
							//System.out.println("checkList8============"+checkList8);
						}
						%>
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">8</div>
						<div class="col-md-7">Major deficiencies observed in Pre
							sanction/Post disbursement inspections</div>
						<div class="col-md-4">
							<%if(checkList8.equals("Yes")){%>
							<html:select styleClass="form-control" property="checkList8"
								name="claimProcessingForm" styleId="checkList8">
								<html:option value="0">Select</html:option>
								<option value="Y" selected="selected">Yes</option>
								<option value="N">No</option>
							</html:select>
							<%}else if(checkList8.equals("No")){%>
							<html:select styleClass="form-control" property="checkList8"
								name="claimProcessingForm" styleId="checkList8">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>
							<%} else{%>
							<html:select styleClass="form-control" property="checkList8"
								name="claimProcessingForm" styleId="checkList8">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%} %>
						</div>
					</div>
					<%
						String checkList9="";
						if ((String) request.getAttribute("checkList9") != null)
						{
							checkList9 = (String) request.getAttribute("checkList9");
							
							//System.out.println("checkList9============"+checkList9);
						}
						%>
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">9</div>
						<div class="col-md-7">Wheather deficiencies observed on the
							part of internal staff as per the Staff Accountability exercise
							carried out.</div>
						<div class="col-md-4">
							<%if(checkList9.equals("Yes")){%>
							<html:select styleClass="form-control" property="checkList9"
								name="claimProcessingForm" styleId="checkList9">
								<html:option value="0">Select</html:option>
								<option value="Y" selected="selected">Yes</option>
								<option value="N">No</option>
							</html:select>
							<%}else if(checkList9.equals("No")){%>
							<html:select styleClass="form-control" property="checkList9"
								name="claimProcessingForm" styleId="checkList9">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>
							<%} else{%>
							<html:select styleClass="form-control" property="checkList9"
								name="claimProcessingForm" styleId="checkList9">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%} %>
						</div>
					</div>
					<%
						String checkList10="";
						if ((String) request.getAttribute("checkList10") != null)
						{
							checkList10 = (String) request.getAttribute("checkList10");
							
							//System.out.println("checkList10============"+checkList10);
						}
						%>
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">10</div>
						<div class="col-md-7">Internal rating was carried out and
							the proposal was found of Investment Grade.(applicable for loans
							sanctioned above 50 lakh)</div>
						<div class="col-md-4">
							<%if(checkList10.equals("Yes")){%>
							<html:select styleClass="form-control" property="checkList10"
								name="claimProcessingForm" styleId="checkList10">
								<html:option value="0">Select</html:option>
								<option value="Y" selected="selected">Yes</option>
								<option value="N">No</option>
							</html:select>
							<%}else if(checkList10.equals("No")){%>
							<html:select styleClass="form-control" property="checkList10"
								name="claimProcessingForm" styleId="checkList10">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>
							<%} else{%>
							<html:select styleClass="form-control" property="checkList10"
								name="claimProcessingForm" styleId="checkList10">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%} %>
						</div>
					</div>
					<%
						String checkList11="";
						if ((String) request.getAttribute("checkList11") != null)
						{
							checkList11 = (String) request.getAttribute("checkList11");
							
							//System.out.println("checkList11============"+checkList11);
						}
						%>
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">11</div>
						<div class="col-md-7">Wheather all the recoveries pertaining
							to the account after the date of NPA and before the claim
							lodgement have been duly incorporated in the claim form.</div>
						<div class="col-md-4">
							<%if(checkList11.equals("Yes")){%>
							<html:select styleClass="form-control" property="checkList11"
								name="claimProcessingForm" styleId="checkList11">
								<html:option value="0">Select</html:option>
								<option value="Y" selected="selected">Yes</option>
								<option value="N">No</option>
							</html:select>
							<%}else if(checkList11.equals("No")){%>
							<html:select styleClass="form-control" property="checkList11"
								name="claimProcessingForm" styleId="checkList11">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N" selected="selected">No</option>
							</html:select>
							<%} else{%>
							<html:select styleClass="form-control" property="checkList11"
								name="claimProcessingForm" styleId="checkList11">
								<html:option value="0">Select</html:option>
								<option value="Y">Yes</option>
								<option value="N">No</option>
							</html:select>

							<%} %>
						</div>
					</div>
					<div class="col-md-4 " style="margin-top: 10px;">&nbsp;</div>
					<div class="col-md-4 " style="margin-top: 10px;">
						<button id="btnapprove" type="button" onclick="SaveData(id);"
							class="btn btn-primary" value="Approve">Approve</button>

						<button type="button" class="btn btn-primary" data-toggle="modal"
							 id="btnAdd1" onclick="SaveData(id);">Save</button>



						<button type="button" class="btn btn-Danger" data-toggle="modal"
							value="Return" id="btnReturn" onclick="SaveData(id);">Return</button>


						<%-- <html:button property="returnn" styleClass="btn btn-Danger" data-toggle="modal"
							styleId="btnReturn"  value="RETURN" onclick="SaveData();">Return</html:button> --%>


						<input id="btnAdd" type="button" value="Cancel"
							onclick="return CancelData();" class="btn btn-primary" />
						<button type="button" class="btn btn-primary" data-toggle="modal"
							data-target="#myModal1">View History</button>

					</div>
					<div class="col-md-4 " style="margin-top: 10px;">&nbsp;</div>
				</div>


				<div class="modal fade" id="myModal1" role="dialog">
							<div class="modal-dialog modal-lg">
								<div class="modal-content">
									<div class="modal-header">
										<div class="col-sm-11" style='font-weight: 800;'>
											<h4 class="modal-title">CGPAN and Promoter History</h4>
										</div>
										<div class="col-sm-1">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
										</div>
				
									</div>
									<div class="modal-body">
										<table class="table table-striped" style="font-size: 13px;">
											<thead>
												<tr>
													<th scope="col">S.No.</th>
													<th scope="col">Level</th>
													<th scope="col">User Name</th>
													<th scope="col">Date</th>
													<th scope="col">Status</th>
													<th scope="col">Remark</th>
												</tr>
											</thead>
											<tbody>
												<%
													if (request.getAttribute("npaPopulateData") != null) {
															ArrayList<NPAMarkingPopulateData> list = (ArrayList) request.getAttribute("npaPopulateData");
															Iterator<NPAMarkingPopulateData> iterator = list.iterator();
															while (iterator.hasNext()) {
																NPAMarkingPopulateData d = iterator.next();
												%>
												<tr>
													<th scope="row"><%=d.getClm_au_id()%></th>
													<td><%=d.getClm_LVEL()%></td>
													<td><%=d.getUser_id()%></td>
													<td><%=d.getClm_dttime()%></td>
													<td><%=d.getClm_status()%></td>
													<td><%=d.getClm_remark()%></td>
												</tr>
												<%
													}
														} else {
												%>
												<tr>
													<td>No Data Found</td>
												</tr>
												<%
													}
												%>
											</tbody>
										</table>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									</div>
								</div>
							</div>
						</div>


				
	</html:form>
</body>
</html>