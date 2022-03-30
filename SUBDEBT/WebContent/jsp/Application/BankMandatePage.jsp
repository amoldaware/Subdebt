<%@ page language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="com.cgtsi.action.CaptchaSession"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<SCRIPT language="JavaScript" type="text/JavaScript" src="js/CGTSI.js"></SCRIPT>

<html>
<head>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous" />
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<script type="text/javascript">
$(document).ready(function() 
{
	$("#flag").val("1");
	var message = document.getElementById('message').value;
	var checker_status = document.getElementById('checker_status').value;
	var roleName = document.getElementById('roleName').value;
	//alert("roleName"+roleName + "checker_status ::" + checker_status);
	
	if(roleName != "" && (roleName.indexOf("MAKER")> -1))
	{
		$( "#btnReset" ).show();$( "#btnSave" ).show();$( "#btnApprove" ).hide();$( "#btnReturn" ).hide();
		if(checker_status == "")
	    {
			setData();
		}
		else if(checker_status != null && (checker_status.indexOf("SAVE")> -1))
		{
			setData();
			if((message != "")){
				$("#errormsg").html(message).css({
		            "color" : "#ff6666",
		            "display" : "block"});
				$("#message").val("");
				resetData();
			}
		}
		else
		{
			$("#targetBankMandateForm :input").attr('readonly', false);	
			$("#memberId").attr('readonly', 'readonly');	
			$("#mliName").attr('readonly', 'readonly');	
			$("#zoneName").attr('readonly', 'readonly');	
			$("#nameOfBeneficiary").attr('readonly', 'readonly');	
		}
	}
	else if(roleName != "" && (roleName.indexOf("NOTHING")> -1))
	{
		$("#targetBankMandateForm :input").attr('readonly', 'readonly');
		$("#targetBankMandateForm :radio").attr('disabled','disabled');
		$("#targetBankMandateForm :button").attr('hidden', 'hidden');
	}
	else
	{
		$("#targetBankMandateForm :input").attr('readonly', 'readonly');
		$("#targetBankMandateForm :radio").attr('disabled','disabled');
		$( "#btnReset" ).hide();$( "#btnSave" ).hide();$( "#btnApprove" ).show();$( "#btnReturn" ).show();
		if((message != "")){
			$("#errormsg").html(message).css({
	            "color" : "#ff6666",
	            "display" : "block"});
			$("#message").val("");
			$( "#btnApprove" ).hide();$( "#btnReturn" ).hide();	
		}
		
	}
});
function setData() {
	$("#memberId").attr("placeholder", "Enter Member Id").attr('readonly', 'readonly');
	$("#mliName").attr("placeholder", "Enter MLI Name").attr('readonly', 'readonly');
	$("#zoneName").attr("placeholder", "Enter Zone Name").attr('readonly', 'readonly');
	$("#contactNo").attr("placeholder", "Enter Contact Number");
	$("#mobileNo").attr("placeholder", "Enter Mobile Number");
	$("#emailId").attr("placeholder", "Enter Email Id");
	$("#nameOfBeneficiary").attr("placeholder", "Name of Beneficiary").attr('readonly', 'readonly');
	$("#beneficiaryBankName").attr("placeholder", "Enter Beneficiary Bank Name");
	$("#Saving").attr('checked',true);
	$("#branchCode").attr("placeholder", "Enter Branch Code");
	$("#micrCode").attr("placeholder", "Enter MICR Id");
	$("#accountNo").attr("placeholder", "Enter Account Numbar");
	$("#rtgsNo").attr("placeholder", "Enter RTGS Numbar");
	$("#neftNo").attr("placeholder", "Enter NEFT Numbar");
}
function blockSpecialChar(e)
{
    var k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || (k >= 48 && k <= 57));
}
function checkEmailFormatValid() {
	var emailVal = document.getElementById('emailId').value;
	var val = "";
	if (emailVal != '') {
		if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(emailVal)) {
			return val;
		}
		val = "You have entered an invalid email address!";
		return val;
	}
}
function resetData()
{
	$("#contactNo").val("");$("#mobileNo").val("");$("#emailId").val("");$("#beneficiaryBankName").val("");
	$("#Saving").attr('checked',true);$("#branchCode").val("");
	$("#micrCode").val("");$("#accountNo").val("");$("#rtgsNo").val("");$("#neftNo").val("");
}
function saveData(id)
{
	var selectedButton = $("#"+id).val();
	$("#checker_status").val(selectedButton);

	var num1 = $("#contactNo").val();
	var num2 = $("#mobileNo").val();
	var count = 0;
	var msg = "<b>Please correct the following Error:</b><ul>";
	if ($("#contactNo").val() == "" || num1.length < 10){msg = msg + "<li>Contact Number can't be Blank!! Or Less than 10 digits</li>"; count = 1;}
	if ($("#mobileNo").val() == "" || num2.length < 10){msg = msg + "<li>Mobile Number can't be Blank!! Or Less than 10 digits</li>"; count = 1;}
	if ($("#emailId").val() == ""){msg = msg + "<li>Email Id can't be Blank!!</li>"; count = 1;}
	if ($("#emailId").val() != "" && checkEmailFormatValid() != "") {msg = msg + "<li>" + checkEmailFormatValid() + "</li>";count = 1;
	}
	if ($("#beneficiaryBankName").val() == ""){msg = msg + "<li>Beneficiary Bank Name can't be Blank!!</li>"; count = 1;}
	if ($("#branchCode").val() == ""){msg = msg + "<li>Branch Code can't be Blank!!</li>"; count = 1;}
	if ($("#micrCode").val() == ""){msg = msg + "<li>MICR Code can't be Blank!!</li>"; count = 1;}
	if ($("#accountNo").val() == ""){msg = msg + "<li>Account Number can't be Blank!!</li>"; count = 1;}
	if ($("#rtgsNo").val() == ""){msg = msg + "<li>RTGS Number can't be Blank!!</li>"; count = 1;}
	if ($("#neftNo").val() == ""){msg = msg + "<li>NEFT Number can't be Blank!!</li>"; count = 1;}
	if (count == 1) {
		$("#errormsg").html(msg).css({
			"color" : "#ff6666",
			"display" : "block"
		});
		msg = msg + "</ul>";
		$(window).scrollTop(0);
		return;
	}  
	else
	{
		document.bankMandateForm.target = "_self";
		document.bankMandateForm.method = "POST";
		document.bankMandateForm.action = "bankMandate.do?method=saveBankMandateData";
		document.bankMandateForm.submit(); 
	}
}
function downloadFile(filename)
{
	document.bankMandateForm.target = "_self";
	document.bankMandateForm.method = "POST";
	document.bankMandateForm.action = "bankMandate.do?method=downloadFile&filename="+filename;
	document.bankMandateForm.submit();
}
function checkListPage(id)
{
	document.bankMandateForm.target = "_self";
	document.bankMandateForm.method = "POST";
	document.bankMandateForm.action = "bankMandate.do?method=getCheckListInfo&id="+id;
	document.bankMandateForm.submit();
}

</script>
</head>

	<body bgcolor="#FFFFFF" topmargin="0"
	data-new-gr-c-s-check-loaded="14.1008.0" data-gr-ext-installed="">
	
	<!-- var accountType = document.getElementById('accountType').value;
	if(accountType != ""){$("#"+accountType).attr('checked',true);} -->
	
	
	<html:form name="bankMandateForm" styleId="targetBankMandateForm"
		type="com.cgtsi.actionform.BankMandateActionForm"
		action="bankMandate.do?method=getBankMandateData" method="POST">
		
		<div class="container row"
			style="margin-left: 20px; padding-top: 10px; padding-bottom: 10px; border-top: 1px solid brown; border-left: 1px solid brown; border-right: 1px solid brown; border-bottom: 1px solid brown;">
		<div class="col-lg-12">
		<div>
			<div class="row" style="margin-top: 10px;">
			
				<div class="col-md-12 alert alert-primary" role="alert">Update Account Details</div>
					<div class="col-lg-12" id="errormsg" style="display: none"></div>
							
					
                       <div class="col-md-6">
                       		<font color="#FF0000" size="2">*</font><label for="memberId ">Member Id</label>
                       		<html:text property="memberId" styleClass="form-control"  styleId="memberId" name="bankMandateForm" />
                       </div>
                       <div class="col-md-6">
                       		<font color="#FF0000" size="2">*</font><label for="mliName">MLI Name</label>
                            <html:text property="mliName" styleClass="form-control"  styleId="mliName" name="bankMandateForm" />                       		
                       </div>
                       <div class="col-md-6">
                       		<font color="#FF0000" size="2">*</font><label for="zoneName ">Zone Name</label>
                       		<html:text property="zoneName" styleClass="form-control"  styleId="zoneName" name="bankMandateForm" />                       		
                       </div>
                       <div class="col-md-6">
                       		<font color="#FF0000" size="2">*</font><label for="contactNo">Contact No</label>
                       		<html:text property="contactNo" styleClass="form-control"  styleId="contactNo" name="bankMandateForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="10"/>                       		
                       </div>
                       <div class="col-md-6">
                       		<font color="#FF0000" size="2">*</font><label for="mobileNo">Mobile No</label>
                       		<html:text property="mobileNo" styleClass="form-control"  styleId="mobileNo" name="bankMandateForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="10"/>                       		
                       </div>
                       <div class="col-md-6">
                       		<font color="#FF0000" size="2">*</font><label for="emailId">Email Id</label>
                       		<html:text property="emailId" styleClass="form-control"  styleId="emailId" name="bankMandateForm" maxlength="50"/>                       		                       		
                       	</div>
                       	<div class="col-md-6">
                       		<font color="#FF0000" size="2">*</font><label for="nameOfBeneficiary">Name of Beneficiary</label>
                       		<html:text property="nameOfBeneficiary" styleClass="form-control"  styleId="nameOfBeneficiary" name="bankMandateForm" />                       		                       		
                       	</div>
                       	<div class="col-md-6">
                       		<font color="#FF0000" size="2">*</font><label for="beneficiaryBankName">Beneficiary Bank Name</label>
                       		<html:text property="beneficiaryBankName" styleClass="form-control"  styleId="beneficiaryBankName" name="bankMandateForm" maxlength="50"/>                       		                       		
                       	</div>
                       	<div class="col-md-6">
                       		<font color="#FF0000" size="2">*</font><label for="accountType">Account Type</label>
              				<div align="left">
                       	  	  <html:radio property="accountType" value="Saving" name="bankMandateForm" styleId="Saving" /><label for="Saving">&nbsp;Saving &nbsp;&nbsp;&nbsp;&nbsp;</label> 
                       	  	  <html:radio property="accountType" value="Current" name="bankMandateForm" styleId="Current"/><label for="Current">&nbsp;Current</label> 
                       	  	</div>
                        </div>
                       	<div class="col-md-6">
                       		<font color="#FF0000" size="2">*</font><label for="branchCode">Branch Code</label>
                       		<html:text property="branchCode" styleClass="form-control"  styleId="branchCode" name="bankMandateForm" onkeypress="return blockSpecialChar(event)" maxlength="20"/>                       		                       		
                       	</div>
                       	<div class="col-md-6">
                       		<font color="#FF0000" size="2">*</font><label for="micrCode">MICR Code</label>
                       		<html:text property="micrCode" styleClass="form-control"  styleId="micrCode" name="bankMandateForm" onkeypress="return blockSpecialChar(event)" maxlength="20"/>                       		                       		                       		
                       	</div>
                       	<div class="col-md-6">
                       		<font color="#FF0000" size="2">*</font><label for="accountNo">Account No</label>
                       		<html:text property="accountNo" styleClass="form-control"  styleId="accountNo" name="bankMandateForm" onkeypress="return blockSpecialChar(event)" maxlength="20"/>                       		                       		                       		                       		
                       	</div>
                       	<div class="col-md-6">
                       		<font color="#FF0000" size="2">*</font><label for="rtgsNo">RTGS No</label>
                       		<html:text property="rtgsNo" styleClass="form-control"  styleId="rtgsNo" name="bankMandateForm" onkeypress="return blockSpecialChar(event)" maxlength="20"/>                       		                       		                       		                       		                       		
                       	</div>
                       	<div class="col-md-6">
                       		<font color="#FF0000" size="2">*</font><label for="neftNo">NEFT No</label>
                       		<html:text property="neftNo" styleClass="form-control"  styleId="neftNo" name="bankMandateForm" onkeypress="return blockSpecialChar(event)" maxlength="20"/>                       		                       		                       		                       		                       		
                       	</div>
                       <div class="col-md-12" style="margin-bottom: 5px;"><b>NOTE:</b> Each member Id should consist the same account number and not be repetative for new Claims(cgpan)of the same member Id</div>
                       <div class="col-md-12" style="margin-bottom: 5px;"><a style="color: red" href="#" onclick="return downloadFile('../Download/SBI/bank mandate form.doc')" name="fileName" id="fileName">CLICK HERE TO DOWNLOAD FOR BANK MANDATE FORM</a></div>
 					   <div class="col-md-12">Send us duly filled in and signed Bank mandate form (physical copy) by the authorized official <b>with name & seal</b> (not below the rank of Asst. General Manager) and pass on the same to us for necessary action at our end</div>
                       
                       <html:hidden property="flag" name="bankMandateForm" styleId="flag" /> 
					   <html:hidden property="message" name="bankMandateForm" styleId="message" />
					   <html:hidden property="roleName" name="bankMandateForm" styleId="roleName" />
					    <html:hidden property="accountType" name="bankMandateForm" styleId="accountType" />
					   <html:hidden property="checker_status" name="bankMandateForm" styleId="checker_status" />
					   	
					 <div class="col-md-4" style="padding-top: 32px;">&nbsp;</div>     
                            <div class="col-md-4" style="padding-top: 32px;">
                            <button id="btnSave" type="button" class="btn btn-primary" onclick="return saveData(id)" value="SAVE">Save</button>
                            <button id="btnReset" type="button" class="btn btn-primary" onclick="return resetData(id)">Reset</button>  
                            <button id="btnApprove" type="button" class="btn btn-primary" onclick="return saveData(id)" value="APPROVE">Approve</button>
                            <button id="btnReturn" type="button" class="btn btn-primary" onclick="return saveData(id)" value="RETURN">Return</button>  
					<div class="col-md-4" style="padding-top: 32px;">&nbsp;</div>
			</div>
		</div>
		</div>
		</div>
		</div>
		
<!-- 		 <div class="col-md-12" style="margin-bottom: 5px;"><a style="color: red" href="#" onclick="return checkListPage('Success')">MLI APPROVAL</a></div>                    
 -->		
	</html:form>
	</body>
</html>