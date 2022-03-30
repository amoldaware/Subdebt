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
<%@page import="com.cgtsi.actionform.SecondClaimProcessingActionForm"%>
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

	function CancelData() {
		$("#cgpan2").val('');
		getPromoter();
		$("#isDisable").css("display", "none");
	}
debugger;
	$(document).ready(
					function() {//alert("okkk");
						var cgpan = document.getElementById('cgpan2').value;
						var claimReferenceNumber = document.getElementById('claimReferenceNumber').value;
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
						
						if((cgpan == "" || claimReferenceNumber == "") && (isSearchClicked == ""))
						{
							$("#isDisable").css("display", "none");
						}
						else
						{
							$("#isDisable").css("display", "block");
						}
						 
						 
						/* if ((cgpan == "" && isSearchClicked == "")) {
							$("#isDisable").css("display", "none");
						} else if ((cgpan != "" && isSearchClicked == "")) {
							$("#isDisable").css("display", "none");
						} else if ((cgpan == "" && isSearchClicked != "")) {
							$("#isDisable").css("display", "none");
						} else {
							$("#isDisable").css("display", "block");
						} */

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
			
			$("#declaration1").prop('disabled', true);
			$('#declaration1').prop('checked', true);
			$("#declaration2").prop('disabled', true);
			$('#declaration2').prop('checked', true);
			$("#declaration3").prop('disabled', true);
			$('#declaration3').prop('checked', true);
			$("#declaration4").prop('disabled', true);
			$('#declaration4').prop('checked', true);
			$("#legalProceedings").attr("style", "pointer-events: none;background-color: #eee;");
			$("#legalsuitFilingDate").prop('disabled', true);
					
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
			
			$("#declaration1").prop('disabled', false);
			$("#declaration2").prop('disabled', false);
			$("#declaration3").prop('disabled', false);
			$("#declaration4").prop('disabled', false);
		
			$("#legalProceedings").attr("style", "pointer-events: none;background-color: #eee;");
			$("#legalsuitFilingDate").prop('disabled', true);
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
			
			$("#declaration1").prop('disabled', true);
			$('#declaration1').prop('checked', true);
			$("#declaration2").prop('disabled', true);
			$('#declaration2').prop('checked', true);
			$("#declaration3").prop('disabled', true);
			$('#declaration3').prop('checked', true);
			$("#declaration4").prop('disabled', true);
			$('#declaration4').prop('checked', true);
			$("#legalProceedings").attr("style", "pointer-events: none;background-color: #eee;");
			$("#legalsuitFilingDate").prop('disabled', true);	
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

			debugger;
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
		/* if ($("#promoterNamee").val() == "0") {
				msg = msg + "<li>Promoter Name can not be black!!</li>";
				val = 1;
			} */
			
			//alert("pkkk");
			if ($("#cgpan2").val() == ""  && $("#claimReferenceNumber").val() == "" ) {
				msg = msg + "<li>Please Provide Either CGPAN OR Claim Reference Number!!</li>";
				val = 1;

			} 
			else {//alert("else");
				if ($("#cgpan2").val()!= "" && $("#promoterNamee").val() == "0") {
					msg = msg + "<li>Please select Promoter Name!!</li>";
					val = 1;

				} 
				
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
			$("#claimReferenceNumber").val('');
			$("#cgpan2").val('');
			getPromoter();
		});
	});

	function getPromoter() {//alert("okk");
		var cgpan = document.getElementById('cgpan2').value;
		//var cgpan = document.getElementById('cgpan2').value;
		//alert(cgpan);
		$("#btnSearch")[0].innerText = 'loading';
		$("#btnSearch").prop('disabled', true);
		setTimeout(function() {
			$("#btnSearch")[0].innerText = 'Search';
			$("#btnSearch").prop('disabled', false);
		}, 8000);
		document.secondClaimProcessingForm.target = "_self";
		document.secondClaimProcessingForm.method = "POST";
		document.secondClaimProcessingForm.action = "secondClaimProcessing.do?method=getSecondClaimProcessing&cgpan="
				+ cgpan;
		document.secondClaimProcessingForm.submit();
	}
	function getPromoterDetails() {
		var cgpan = document.getElementById('cgpan2').value;
		var promoterName = document.getElementById('promoterNamee').value;

		document.secondClaimProcessingForm.target = "_self";
		document.secondClaimProcessingForm.method = "POST";
		document.secondClaimProcessingForm.action = "secondClaimProcessing.do?method=getClaimProcessingSearchDetail&cgpan="
				+ cgpan
				+ "&promoterNamee"
				+ promoterName
				+ "&isSearchClicked"
				+ isSearchClicked;
		document.secondClaimProcessingForm.submit();

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



//var checkVal = $("#check2").val();
function setCheckValue(id) {//alert("okk"+id);
	if ($("#"+id).prop("checked") == true) {
		$("#"+id).val("Y");
	} else {
		$("#"+id).val("N");
	}

}




function SaveData(id) {//alert("okk");


//legal
/* var legalProceedings=$("#legalProceedings").val();	
var legalOtherForms=$("#legalOtherForms").val();	
var legalRegistration=$("#legalRegistration").val();
var legalSatisfactoryRreason=$("#legalSatisfactoryRreason").val();
//var dateofpossessionofassets=$("#dateofpossessionofassets").val();
var legalLocation=$("#legalLocation").val();
var legalAmountClaimed=$("#legalAmountClaimed").val(); */

var msg = "<b>Please correct the following Error:</b><ul>";
var countErr = 0;


/* if (legalsuitFilingDate == "") {
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

var date = new Date(tt);
var newdate = new Date(date);
newdate.setDate(newdate.getDate() );

if (dateNPA < newdate) {
	msg = msg
			+ "<li>Please Provide satisfactory reason for filing suit before NPA date </li>";
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
} */
if ($("#rolenAME").val() == "MAKER"){
if ($("#declaration1").val() == "N" || $("#declaration2").val() == "N" || $("#declaration3").val() == "N" || $("#declaration4").val() == "N") {
	msg = msg
			+ "<li>All Declaration are Mandatory!!</li>";
	countErr = 1;

}
}


if ($("#rolenAME").val() == "MAKER") {//alert("maker");
var legaAattachment=$("#legaAattachment").val();
if (legaAattachment == "") {
	msg = msg
			+ "<li>Legal Aattachment can't be blank...!</li>";
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
//alert("Error are remaining :::" + countErr);
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
 	document.secondClaimProcessingForm.target = "_self";
	document.secondClaimProcessingForm.method = "POST";
	document.secondClaimProcessingForm.action = "secondClaimProcessing.do?method=getClaimProcessingDetail";
	document.secondClaimProcessingForm.submit();   

}

}


$(document).on('click', '#downloadFile', function() {
	 var cgPan = $('#cgpan2').val();
	 //alert("okk"+cgPan);
	 var claimReferenceNumber = $('#claimReferenceNumber').val();
	// alert("okk"+claimReferenceNumber);
		
	 	document.secondClaimProcessingForm.target = "_self";
		document.secondClaimProcessingForm.method = "POST";
		document.secondClaimProcessingForm.action = "secondClaimProcessing.do?method=downloadFile&claimReferenceNumber="+claimReferenceNumber;
		document.secondClaimProcessingForm.submit();
	
});
</script>
</head>
<body>
	<html:errors />
	<html:form name="secondClaimProcessingForm"
		type="com.cgtsi.actionform.SecondClaimProcessingActionForm"
		action="secondClaimProcessing.do?method=getSecondClaimProcessing"
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
							name="secondClaimProcessingForm" />
					</div>

					<div class="col-md-4">
						<label for="PromoterName">Promoter Name</label><font
							color="#FF0000" size="2">*</font>
						<html:select styleClass="form-control" property="promoterNamee"
							name="secondClaimProcessingForm" styleId="promoterNamee">
							<html:option value="0">Select</html:option>
							<html:optionsCollection property="promoterValues"
								name="secondClaimProcessingForm" label="label" value="value" />
						</html:select>
					</div>
					<div class="col-md-4">&nbsp;</div>
					<div class="col-md-8"
						style="text-align: center; margin-top: 20px; font-weight: 900;">OR</div>
					<div class="col-md-4">&nbsp;</div>
					<div class="col-md-4">
						<label for="Claim Reference Number">Claim Reference Number</label><font
							color="#FF0000" size="2">*</font>
						<html:text property="claimReferenceNumber"
							styleClass="form-control" onchange="return getPromoter();"
							styleId="claimReferenceNumber" name="secondClaimProcessingForm" />
					</div>
					<div class="col-md-4" style="padding-top: 24px;">

						<button type="button" class="btn btn-primary" id="btnSearch">Search</button>
						&nbsp;&nbsp;
						<button type="button" class="btn btn-primary" id="btnClear">Clear</button>
					</div>
				</div>

				<div id="isDisable">

					<!-- ===============MSME Section==========Start============== -->
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">MSME
								Unit Data</div>
						</div>
						<div class="col-md-3">
							<label for="Application reference Number ">Application
								Reference Number</label>
							<html:text property="applicationReferenceNumber" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="applicationReferenceNumber" />
						</div>
						<div class="col-md-3">
							<label for="Claim Reference Number ">Claim Reference
								Number</label>
							<html:text property="claimRefNumber" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								readonly="true" styleId="claimRefNumber" />
						</div>
						<div class="col-md-3">
							<label for="CGPAN ">CGPAN </label>
							<html:text property="cGPan" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="cGPan" />
						</div>
						<div class="col-md-3">
							<label for="PMR CGPAN">PMR CGPAN</label>
							<html:text property="pmarCgpan" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="pmarCgpan" />
						</div>
						<div class="col-md-3">
							<label for="Unit Name">Unit Name</label>
							<html:text property="unitName" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="unitName" />
						</div>
						<div class="col-md-3">
							<label for="Type of Entity">Type of Entity:</label>
							<html:text property="typeofEntity" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="typeofEntity" />
						</div>
						<div class="col-md-3">
							<label for="Constitution">Constitution</label>
							<html:text property="constitution" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="constitution" />
						</div>
						<div class="col-md-3">
							<label for="Guarantee Amount">Guarantee Amount</label>
							<html:text property="guaranteeAmount" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="guaranteeAmount" />
						</div>
						<div class="col-md-3">
							<label for="Guarantee Start Date">Guarantee Start Date</label>
							<html:text property="guaranteeStartDate" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="guaranteeStartDate" />
						</div>
						<div class="col-md-3">
							<label for="Last Disbursement Date">Last Disbursement
								Date</label>
							<html:text property="lastDisbursementDate" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="lastDisbursementDate" />
						</div>
						<div class="col-md-3">
							<label for="Sanction Amount">Sanction Amount</label>
							<html:text property="sanctionAmount" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="sanctionAmount" />
						</div>
						<div class="col-md-3">
							<label for="Sanction Date">Sanction Date</label>
							<html:text property="sanctionDate" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="sanctionDate" />
						</div>

					</div>

					<!-- ===============Promoter Section==========End============== -->

					<!-- ===============Promoter Section==========Start============== -->
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">PromoterData</div>
						</div>
						<div class="col-md-3">
							<label for="App_Ref_No">Promoter Name</label>
							<html:text property="promoterNameValuee" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="romoterNameValuee" />
						</div>
						<div class="col-md-3">
							<label for="ITPAN">ITPAN</label>
							<html:text property="iTPAN" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="iTPAN" />
						</div>
						<div class="col-md-3">
							<label for="Adhar Number">Adhar Number</label>
							<html:text property="adharNumber" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="adharNumber" />
						</div>
						<div class="col-md-3">
							<label for="Invested Equity">Invested Equity</label>
							<html:text property="investedEquity" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="investedEquity" />
						</div>
						<div class="col-md-3">
							<label for="Invested as debt/unsecured loan">Invested as
								debt/unsecured loan</label>
							<html:text property="investedDebtUnsecuredLoan" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="investedDebtUnsecuredLoan" />
						</div>
						<div class="col-md-3">
							<label for="Total">Total</label>
							<html:text property="total" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="total" />
						</div>

						<div class="col-md-3">
							<label for="NPA Date">NPA Date</label>
							<html:text property="nPADate" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								disabled="true" styleId="nPADate" />
						</div>

					</div>

					<!-- ===============Promoter Section==========End============== -->
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">Claim
								Details</div>
						</div>

						<div class="col-md-4">
							<label for=">Net Outstanding Amount">Net Outstanding
								Amount</label>
							<html:text property="netOutstandingAmount" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								styleId="netOutstandingAmount" readonly="true" />
						</div>
						<div class="col-md-4">
							<label for=">Claim Eligible Amount">Claim Eligible Amount</label>
							<html:text property="claimEligibleAmount" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								styleId="claimEligibleAmount" readonly="true" />
						</div>
						<div class="col-md-4">
							<label for=">First Installment Pay Amount">First
								Installment Pay Amount</label>
							<html:text property="firstInstallmentPayAmount" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								styleId="firstInstallmentPayAmount" readonly="true" />
						</div>
					</div>

					<!-- ===============Legal Section==========Start============== -->
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">Legal
								Forum As Per First Claim</div>
						</div>
						<div class="col-md-8">
							<label for="PromoterName">Forum through which legal
								proceedings were initiated against borrower </label>



							<html:select styleClass="form-control"
								property="legalProceedings" name="secondClaimProcessingForm"
								styleId="legalProceedings" onchange="OthersValue();">
								<html:option value="0">Select</html:option>
								<html:optionsCollection property="legalProceedingsValue"
									name="secondClaimProcessingForm" label="label" value="value" />
							</html:select>


						</div>
						<div class="col-md-4" style="display: none" id="legalOtherForm">
							<label for="OtherForum">Other Forum(s)</label>
							<html:text property="legalOtherForms" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								styleId="legalOtherForms" readonly="true" />
						</div>
						<div class="col-md-4">
							<label for="RegistrationNo">Suit / Case Registration No.</label>
							<html:text property="legalRegistration" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								styleId="legalRegistration" readonly="true" />
						</div>

						<%
								String legalsuitFilingDate="";
								if ((String) request.getAttribute("legalsuitFilingDate") != null)
								{
									legalsuitFilingDate = (String) request.getAttribute("legalsuitFilingDate");
									System.out.println("legalsuitFilingDate=====================" + legalsuitFilingDate);
								}
							%>

						<%if (legalsuitFilingDate != null && !(legalsuitFilingDate.equals(""))) {%>
						<div class="col-md-4">
							<label for="SuitFilingDate">Suit Filing Date</label> <input
								type="text" name="legalsuitFilingDate"
								name="secondClaimProcessingForm" class="form-control"
								id="legalsuitFilingDate" value="<%=legalsuitFilingDate%>"
								onclick="setSuitDateValue();" />
						</div>
						<%}else{ %>
						<div class="col-md-4">
							<label for="SuitFilingDate">Suit Filing Date</label> <input
								type="text" name="legalsuitFilingDate" autocomplete="off"
								name="secondClaimProcessingForm" class="form-control"
								id="legalsuitFilingDate" value="<%=legalsuitFilingDate%>"
								onclick="setSuitDateValue();" />
						</div>
						<%} %>

						<%-- 
							<%
							  String dateofpossessionofassets="";
								if ((String) request.getAttribute("dateofpossessionofassets") != null)
								{
									dateofpossessionofassets = (String) request.getAttribute("dateofpossessionofassets");
									System.out.println("dateofpossessionofassets===" +dateofpossessionofassets);
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
									name="secondClaimProcessingForm" class="form-control"
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
									name="secondClaimProcessingForm" class="form-control"
									id="dateofpossessionofassets" autocomplete="off"
									onclick="setAssetDateValue();"
									placeholder="Date of possession of assets under sarfaesi act " />
							</div>
							<%} %> --%>

						<div class="col-md-4">
							<label for="First Installment settled amount">First
								Installment settled amount </label>
							<html:text property="firstInstallmentsSettledAmount"
								alt="Reference" name="secondClaimProcessingForm"
								styleClass="form-control" readonly="true"
								styleId="firstInstallmentsSettledAmount" />
						</div>
						<div class="col-md-4">
							<label for="Recovery Amount">Recovery Amount</label>
							<html:text property="recoveryAmount" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								styleId="recoveryAmount" readonly="true" />
						</div>


						<div class="col-md-4">
							<label for="Type of recovery">Type of recovery (final)</label>
							<html:text property="typeOfRecovery" alt="Reference"
								name="secondClaimProcessingForm" styleClass="form-control"
								styleId="typeOfRecovery" readonly="true" />
						</div>


						<%-- 	<div class="col-md-4">
								<label for=">Legal Forum As Per First Claim">Legal Forum As Per First Claim</label>
								<html:text property="legalForumAsPerFirstClaim" alt="Reference"
									name="secondClaimProcessingForm" styleClass="form-control"
									styleId="legalForumAsPerFirstClaim" />
							</div> --%>


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
						if(roleName.contains("CHECKER") || roleName.contains("NOTHING")){%>
						<div class="col-md-8" id="Display">
							<label for="InvestedEquity">View Final legal action</label> 
							<a style="border: none;color: blue;font-weight: 900;" href="#" id="downloadFile" Class="form-control"><%=filenameName%></a>
						</div>
						
						<%}else{%>
						<div class="col-md-8" id="Upload">
							<label for="InvestedEquity">Final legal action </label>
							<html:file property="nocDocument1"
								name="secondClaimProcessingForm" styleClass="file1 form-control"
								styleId="legaAattachment"></html:file>
						</div>
						<%} %>
						<div class="col-md-4">&nbsp;</div>
						<div class="col-md-8">(In case of OTS please upload the
							Approved OTS Memorandum duly stamped and signed by the competent
							authority)</div>
					</div>
					<!-- ===============Legal Section==========End============== -->



					<!-- ===============Security Section==========End======== -->


					<!-- ===========MLI====General inforamtion Section==Start======== -->
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">Declaration's</div>
						</div>

						<div class="col-md-12"
							style="vertical-align: middle; margin-top: 10px; color: red;">
							<html:checkbox property="generalMLIComment" alt="Reference"
								name="secondClaimProcessingForm" value="N"
								styleId="declaration1" onclick="setCheckValue('declaration1')"></html:checkbox>
							&nbsp;&nbsp;&nbsp;<label for="MLIsComment">Declaration1 :
								Forum through which legal proceedings were initiated against
								borrowe </label>
						</div>


						<div class="col-md-12"
							style="vertical-align: middle; margin-top: 10px; color: red;">
							<html:checkbox property="generalMLIComment" alt="Reference"
								name="secondClaimProcessingForm" value="N"
								styleId="declaration2" onclick="setCheckValue('declaration2')"></html:checkbox>
							&nbsp;&nbsp;&nbsp;<label for="ITPAN ">Declaration2 :
								Forum through which legal proceedings were initiated against
								borrowe</label>
						</div>

						<div class="col-md-12"
							style="vertical-align: middle; margin-top: 10px; color: red;">
							<html:checkbox property="generalMLIComment" alt="Reference"
								name="secondClaimProcessingForm" value="N"
								styleId="declaration3" onclick="setCheckValue('declaration3')"></html:checkbox>
							&nbsp;&nbsp;&nbsp;<label for="AdharNumber">Declaration3
								:Forum through which legal proceedings were initiated against
								borrowe</label>
						</div>


						<div class="col-md-12"
							style="vertical-align: middle; margin-top: 10px; color: red;">
							<html:checkbox property="generalMLIComment" alt="Reference"
								styleId="declaration4" onclick="setCheckValue('declaration4')"></html:checkbox>
							&nbsp;&nbsp;&nbsp;<label for="AdharNumber">Declaration4 :
								Forum through which legal proceedings were initiated against
								borrowe</label>
						</div>


					</div>
					<!-- ===========MLI====General inforamtion Section==Start======== -->


					<!-- ==========Value=of Securities available==Start======== -->





					<div class="row" style="margin-top: 10px; margin-left: 2px;">

						<div class="col-md-2 " style="margin-top: 5px;">
							<html:hidden property="message" name="secondClaimProcessingForm"
								styleId="message" />
							<html:hidden property="role" name="secondClaimProcessingForm"
								styleId="rolenAME" />
							<html:hidden property="isSearchClicked"
								name="secondClaimProcessingForm" styleId="isSearchClicked" />

							<html:hidden property="checkerReturn"
								name="secondClaimProcessingForm" styleId="checkerReturn" />
						</div>

					</div>

					<!-- ==========Value=of Securities available==End======== -->
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