<%@ page language="java"%>
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
	setData();
	var message = document.getElementById('message').value;
	var firstName = document.getElementById('firstName').value;
	var userType = document.getElementById('userType').value;
	var userId = document.getElementById('userId').value;
		if (userType == "New") {
		if (message == "") {
			$("#errormsg").css({
			"display" : "none"
		});
		$("#target :input").attr('readonly', false);
		$("#userId").attr('readonly', 'readonly');
		$("#message").val("");
		} else if (message != ""
		&& (message.indexOf('succes') > -1)) {
		var databasemsg = "<b>" + message + "</b>";

		$("#errormsg").html(databasemsg).css({
			"color" : "#ff6666",
			"display" : "block"
	    });
		$("#target :input").attr('readonly', false);
		$("#userId").attr('readonly', 'readonly');
		$("#target :input").val("")
		$("#userType").val(userType);
		$("#userId").val(userId);
		$("#message").val("");
		} else {
		var databasemsg = "<b>Please correct the following Error:</b><ul><li>";
			databasemsg = databasemsg + message
			+ "</li></ul>";

			$("#errormsg").html(databasemsg).css({
				"color" : "#ff6666",
				"display" : "block"
			});
			$("#target :input").attr('readonly', false);
			$("#userId").attr('readonly', 'readonly');
			$("#target :input").val("")
			$("#userType").val(userType);
			$("#userId").val(userId);
			$("#message").val("");
				}
			}
			if ((userType == "Existing")) {
			if (message == "") {
			$("#errormsg").css({
				"display" : "none"
			});
			$("#target :input")
			.attr('readonly', 'readonly');
			$("#userType").attr('readonly', false);
			$("#userId").attr('readonly', false);
			$("#mobile_number").attr('readonly', false);
			$("#user_role").attr('readonly', false);
			$("#employee_id").attr('readonly', false);
			$("#message").val("");
			} 
			else if (message != "" && (message.indexOf('succes') > -1)) 
			{
				var databasemsg = "<b>" + message + "</b>";

				$("#errormsg").html(databasemsg).css({
						"color" : "#ff6666",
						"display" : "block"
				});
				
				$("#target :input").val("");
				$("#message").val("");
				
				/* $("#target :input").val("")
				$("#target :input").attr('readonly', 'readonly');
				$("#target :button").attr('readonly', false);
				$("#userType").attr('readonly', false);
				$("#userId").attr('readonly', false);
				$("#mobile_number").attr('readonly', false);
				$("#user_role").attr('readonly', false);
				$("#employee_id").attr('readonly', false);
				$("#message").val(""); */
				} else {
				var databasemsg = "<b>Please correct the following Error:</b><ul><li>";
				databasemsg = databasemsg + message
					+ "</li></ul>";

					$("#errormsg").html(databasemsg).css({
				    "color" : "#ff6666",
					"display" : "block"
					});
					$("#target :input")
					.attr('readonly', 'readonly');
					$("#target :button").attr('readonly', false);
					$("#userType").attr('readonly', false);
					$("#userId").attr('readonly', false);
					$("#mobile_number").attr('readonly', false);
					$("#user_role").attr('readonly', false);
					$("#employee_id").attr('readonly', false);
					$("#message").val("");
				}
		}
	});

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
	function getUserInfo() {
		//alert("Inside getUserInfo method!!!");
		var userId = $("#userId").val();

		if (userId == "") {
			var msg = "<b>Please correct the following Error:</b><ul><li>User Id can not be blank!!!</li>";
			$("#errormsg").html(msg).css({
				"color" : "#ff6666",
				"display" : "block"
			});
			msg = msg + "</ul>";
			$(window).scrollTop(0);
		} else {
			$("#errormsg").html(msg).css({
				"display" : "none"
			});
			document.mliUserNewForm.target = "_self";
			document.mliUserNewForm.method = "POST";
			document.mliUserNewForm.action = "showMLIUserNew.do?method=getUserInformation&userId="
					+ userId;
			document.mliUserNewForm.submit();
		}
	}

	function changeUserType() {
		//alert("Inside changeUserType method...");
		var userType = $("#userType").val();
		if ((userType == "0")) {
			var msg = "<b>Please correct the following Error:</b><ul><li>Please Select Valid UserType</li>";
			$("#errormsg").html(msg).css({
				"color" : "#ff6666",
				"display" : "block"
			});
			msg = msg + "</ul>";
			$(window).scrollTop(0);
			$("#target :input").val("");
			$("#target :input").attr('readonly', false);
		}
		if ((userType != "0") && (userType == "Existing")) {
			$("#errormsg").html(msg).css({
				"display" : "none"
			});
			$("#target :input").attr('readonly', 'readonly');
			$("#userType").attr('readonly', false);
			$("#userId").attr('readonly', false);
		}
		if ((userType != "0") && (userType == "New")) {
			$("#errormsg").html(msg).css({
				"display" : "none"
			});
			$("#target :input").attr('readonly', false);
			$("#userId").attr('readonly', 'readonly');
			$("#target :input").val("");
			$("#userType").val(userType);
		}
	}
	function SaveData() {
		var count = 0;
		var msg = "<b>Please correct the following Error:</b><ul>";

		if ($("#userType").val() == "0") {
			msg = msg + "<li>Please Select UserType Existing Or New</li>";
			count = 1;
		}
		if ($("#firstName").val() == "") {
			msg = msg + "<li>First Name can't be Blank!!</li>";
			count = 1;
		}
		if ($("#lastName").val() == "") {
			msg = msg + "<li>Last Name can't be Blank!!</li>";
			count = 1;
		}
		if ($("#designation").val() == "") {
			msg = msg + "<li>Designation can't be Blank!!</li>";
			count = 1;
		}
		if ($("#bankId").val() == "") {
			msg = msg + "<li>BankId can't be Blank!!</li>";
			count = 1;
		}
		if ($("#zoneId").val() == "") {
			msg = msg + "<li>ZoneId can't be Blank!!</li>";
			count = 1;
		}
		if ($("#branchId").val() == "") {
			msg = msg + "<li>BranchId can't be Blank!!</li>";
			count = 1;
		}
		if ($("#emailId").val() == "") {
			msg = msg + "<li>EmailId can't be Blank!!</li>";
			count = 1;
		}
		if ($("#emailId").val() != "" && checkEmailFormatValid() != "") {
			msg = msg + "<li>" + checkEmailFormatValid() + "</li>";
			count = 1;
		}
		if ($("#employee_id").val() == "") {
			msg = msg + "<li>Employee Id can't be Blank!!</li>";
			count = 1;
		}
		if ($("#user_role").val() == "0") {
			msg = msg + "<li>Please Select Role Checker Or Maker</li>";
			count = 1;
		}
		if (($("#user_role").val() == "Checker")
				&& ($("#mobile_number").val() == "")) {
			msg = msg
					+ "<li>If Selected Role is Checker then Mobile Number is Mandatory</li>";
			count = 1;
		}
		if (count == 1) {
			$("#errormsg").html(msg).css({
				"color" : "#ff6666",
				"display" : "block"
			});
			msg = msg + "</ul>";
			$(window).scrollTop(0);
			return;
		} else {
			$("#btnAdd").attr("disabled", "disabled");
			$("#btnAdd")[0].innerText = 'loading';
			setTimeout(function() {
				$("#btnAdd")[0].innerText = 'Save';
			}, 10000);

			$("#errormsg").css({
				"display" : "none"
			});
		    document.mliUserNewForm.target = "_self";
			document.mliUserNewForm.method = "POST";
			document.mliUserNewForm.action = "showMLIUserNew.do?method=saveMLIUserNew";
			document.mliUserNewForm.submit();
		}
	}
	function CancelData() {
		$("#errormsg").css({
			"display" : "none"
		});
		$("#target :input").attr('readonly', false);
		$("#target :input").val("");
	}
	function setData() {
		$("#userId").attr("placeholder", "Enter User Id");
		$("#firstName").attr("placeholder", "Enter First Name");
		$("#middleName").attr("placeholder", "Enter Middle Name");
		$("#lastName").attr("placeholder", "Enter Last Name");
		$("#designation").attr("placeholder", "Enter Designation");
		$("#bankId").attr("placeholder", "Enter Bank Id");
		$("#zoneId").attr("placeholder", "Enter Zone Id");
		$("#branchId").attr("placeholder", "Enter Branch Id");
		$("#emailId").attr("placeholder", "Enter Email Id");
		$("#employee_id").attr("placeholder", "Enter Employee Id");
		$("#mobile_number").attr("placeholder", "Enter Mobile Numbar");
	}
</script>
</head>
<body bgcolor="#FFFFFF" topmargin="0"
	data-new-gr-c-s-check-loaded="14.1008.0" data-gr-ext-installed="">
	<%--    <html:form name="form1"> --%>
	<html:form name="mliUserNewForm" styleId="target"
		type="com.cgtsi.actionform.MLIUserActionForm"
		action="showMLIUserNew.do?method=showMLIUserNew" method="POST">
		<div class="container row"
			style="margin-left: 20px; padding-top: 10px; padding-bottom: 10px; border-top: 1px solid brown; border-left: 1px solid brown; border-right: 1px solid brown; border-bottom: 1px solid brown;">
			<div class="col-lg-12">
				<div>
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">Enter
								MLI User Details</div>
						</div>
						<div class="col-lg-12" id="errormsg" style="display: none"></div>
						<div class="col-md-6">
							<font color="#FF0000" size="2">*</font><bean:message key="user_type" />
							<html:select property="userType"
								onchange="return changeUserType()" name="mliUserNewForm"
								styleClass="form-control" styleId="userType">
								<html:option value="0">Select</html:option>
								<html:option value="Existing">Existing</html:option>
								<html:option value="New">New</html:option>
							</html:select>
						</div>

						<div class="col-md-6">
							<bean:message key="userId" />
							<html:text property="userId" onchange="return getUserInfo()"
								styleClass="form-control" name="mliUserNewForm" styleId="userId"
								maxlength="11" />
						</div>
						<div class="col-md-6">
							<font color="#FF0000" size="2">*</font><bean:message key="firstName" />
							<html:text property="firstName" styleClass="form-control"
								name="mliUserNewForm" maxlength="20" styleId="firstName" />
						</div>

						<div class="col-md-6">
							<bean:message key="middleName" />
							<html:text property="middleName" styleClass="form-control"
								name="mliUserNewForm" maxlength="20" styleId="middleName" />
						</div>

						<div class="col-md-6">
							<font color="#FF0000" size="2">*</font><bean:message key="lastName" />
							<html:text property="lastName" styleClass="form-control"
								name="mliUserNewForm" maxlength="20" styleId="lastName" />
						</div>

						<div class="col-md-6">
							<font color="#FF0000" size="2">*</font><bean:message key="designation" />
							<html:text property="designation" styleClass="form-control"
								name="mliUserNewForm" maxlength="50" styleId="designation" />
						</div>

						<div class="col-md-6">
							<font color="#FF0000" size="2">*</font><bean:message key="bankId" />
							<html:text property="bankId" styleClass="form-control"
								name="mliUserNewForm"
								onkeypress="return numbersOnly(this, event)"
								onkeyup="isValidNumber(this)" maxlength="4" styleId="bankId" />
						</div>

						<div class="col-md-6">
							<font color="#FF0000" size="2">*</font><bean:message key="zoneId" />
							<html:text property="zoneId" styleClass="form-control"
								name="mliUserNewForm"
								onkeypress="return numbersOnly(this, event)"
								onkeyup="isValidNumber(this)" maxlength="4" styleId="zoneId" />
						</div>

						<div class="col-md-6">
							<font color="#FF0000" size="2">*</font><bean:message key="branchId" />
							<html:text property="branchId" styleClass="form-control"
								name="mliUserNewForm"
								onkeypress="return numbersOnly(this, event)"
								onkeyup="isValidNumber(this)" maxlength="4" styleId="branchId" />
						</div>

						<div class="col-md-6">
							<font color="#FF0000" size="2">*</font><bean:message key="emailId" />
							<html:text property="emailId" styleClass="form-control"
								name="mliUserNewForm" maxlength="40" styleId="emailId" />
						</div>

						<div class="col-md-6">
							<font color="#FF0000" size="2">*</font><bean:message key="user_role" />
							<html:select property="user_role" name="mliUserNewForm"
								styleClass="form-control" styleId="user_role">
								<html:option value="0">Select</html:option>
								<html:option value="Checker">Checker</html:option>
								<html:option value="Maker">Maker</html:option>
							</html:select>
						</div>

						<div class="col-md-6">
							<font color="#FF0000" size="2">*</font><bean:message key="employee_id" />
							<html:text property="employee_id" styleClass="form-control"
								name="mliUserNewForm" maxlength="20" styleId="employee_id" />
						</div>

						<div class="col-md-6">
							<bean:message key="mobile_number" />
							<html:text property="mobile_number" styleClass="form-control"
								name="mliUserNewForm"
								onkeypress="return numbersOnly(this, event)"
								onkeyup="isValidNumber(this)" maxlength="10"
								styleId="mobile_number" />
						</div>
						<html:hidden property="message" name="mliUserNewForm"
							styleId="message" />

						<div class="col-md-3 " style="margin-top: 10px;">&nbsp;</div>

						<div class="col-md-3 " style="margin-top: 10px;">
							<button id="btnAdd" type="button" onclick="SaveData()"
								class="btn btn-primary">Save</button>
							<button id="btnCancel" type="button" onclick="CancelData()"
								class="btn btn-primary">Cancel</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</html:form>
</body>
</html>
