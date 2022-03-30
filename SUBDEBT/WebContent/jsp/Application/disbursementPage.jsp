<%@page import="com.cgtsi.actionform.PopuLateData"%>
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
<%@page import="com.cgtsi.actionform.DisbursementActionForm"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Insert title here</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous">
<link href="/SUBDEBT/css/StyleSheet.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>

<style>
.hide {
	display: block;
}

.show {
	display: block;
}
</style>
<script type="text/javascript">
	var isSearchClicked;

	$(document).ready(function() {
		var cgpan = document.getElementById('cgpan').value;
		var isSearchClicked = document.getElementById('isSearchClicked').value;
		var message = document.getElementById('message').value;
	
		if ((cgpan == "" && isSearchClicked == "")) {
			$("#isDisable").css("display", "none");
		} else if ((cgpan != "" && isSearchClicked == "")) {
			$("#isDisable").css("display", "none");
		} else if ((cgpan == "" && isSearchClicked != "")) {
			$("#isDisable").css("display", "none");
		} else {
			$("#isDisable").css("display", "block");
		}
		
		if((message != "" && !(message.indexOf('Success')> -1)))
		{
			$("#isDisable").css("display", "none");
			
			var databasemsg="<b>Please correct the following Error:</b><ul><li>";
		    databasemsg=databasemsg + message +"</li></ul>";   
		    
		    $("#errormsg").html(databasemsg).css({
	            "color" : "#ff6666",
	            "display" : "block"
	        });
	    
		    $("#message").val("");
		}
		
		if((message != "" && (message.indexOf('Success')> -1)))
		{	
			$("#isDisable").css("display", "none");
			var databasemsg1="<b> "+message+" </b>";  
		    
		    $("#errormsg").html(databasemsg1).css({
	            "color" : "#ff6666",
	            "display" : "block"
	        });
	    
		    $("#message").val("");
			$("#cgpan").val('');
			$("#promoterName").empty();
			$("#promoterName").append('<option value="0">Select</option>');
		} 
	});

	function setDateValue(id) {
		var start = new Date("2020-06-24"), end = new Date(), diff = new Date(
				end - start), days = diff / 1000 / 60 / 60 / 24;

		var d = "#" + id;
		$(d).datepicker({
			dateFormat : 'dd/mm/yy',
			//setStartDate:"02-05-2021",
			minDate : days * -1,
			maxDate : end
		}).attr('readonly', 'readonly').focus();
	}

	function showCalendar() {
		p_item = arguments[0];
		if (arguments[1] == null)
			p_month = new String(gNow.getMonth());
		else
			p_month = arguments[1];
		if (arguments[2] == "" || arguments[2] == null)
			p_year = new String(gNow.getFullYear().toString());
		else
			p_year = arguments[2];
		if (arguments[3] == null)
			p_format = "DD/MM/YYYY";
		else
			p_format = arguments[3];

		vWinCal = window.open("", "Calendar",
				"width=200,height=200,status=no,resizable=no,top=200,left=200");
		vWinCal.opener = self;
		vWinCal.focus();
		ggWinCal = vWinCal;

		Build(p_item, p_month, p_year, p_format);
	}

	function getPromoter() {
		var cgpan = document.getElementById('cgpan').value;
		document.disbursementForm.target = "_self";
		document.disbursementForm.method = "POST";
		document.disbursementForm.action = "disbursementDetail.do?method=getDisbursementDetail&cgpan="
				+ cgpan;
		document.disbursementForm.submit();
	}

	function getPromoterAmtInfo() {
		var cgpan = document.getElementById('cgpan').value;
		var promoterName = document.getElementById('promoterName').value;
		var promoterName = document.getElementById('isSearchClicked').value;

		document.disbursementForm.target = "_self";
		document.disbursementForm.method = "POST";
		document.disbursementForm.action = "disbursementDetail.do?method=getDisbursementSearchDetail&cgpan="
				+ cgpan
				+ "&promoterName"
				+ promoterName
				+ "&isSearchClicked"
				+ isSearchClicked;
		document.disbursementForm.submit();
	}

	function remove(div) {
		$('#div' + div).closest("div.row").remove();
		$("#hdnVal").val(parseInt($("#hdnVal").val()) - 1)
		getCheck1();
		calculateAmount();

	}
	function getCheck1() {
		var myElements = $("#hdnVal").val();
		for (var i = 1; i < myElements + 1; i++) {
			if (i == myElements) {
				$("#check" + i).removeAttr('disabled');
				$("#check" + i).removeAttr('checked');
			}

		}
	}
	function getCheck() {
		
		var myElements = $("#hdnVal").val();
		for (var i = 1; i < myElements; i++) {
			$("#check" + i).removeAttr('disabled');
			$("#check" + i).removeAttr('checked');
			$("#check" + i).attr("disabled", true);
			;
		}
	}

	function calculateAmount() {

		var amount = 0;
		var myElements = $("#TextBoxContainer .row");
		var data = [];
		for (var i = 0; i < myElements.length; i++) {

			var amount = amount
					+ parseFloat(myElements.eq(i).find(".clsAmount").val());

		}
		$("#Total")[0].innerText = amount;
		//alert("changed" + data);
	}

	var finalCheck;
	function SaveData() 
	{
		
		var obj = [];
		var CGPAN = $("#cgpan").val();
		var countData = 0;
		var checkCount = 0;
		var count = 0;
		var PromoterName = $("#promoterName").val();
		var sactionDate = $("#sanctionDate").val();
		var startGuaranteeDate = $("#startGuaranteeDate").val();
		var myElements = $("#TextBoxContainer .row");
		var msg1="<b>Please correct the following Error:</b><ul>";
		var data = [];
		
		for (var i = 0; i < myElements.length; i++) {
			var date = myElements.eq(i).find(".clsDate").val();
			var amount = myElements.eq(i).find(".clsAmount").val();
			data.push({
				"date" : date,
				"amount" : amount
			});
		}
		jQuery.each(data,function(i, val) 
		{
			var date = val.date;
			var amount = val.amount;

			var sactionDate1 = sactionDate.split('/');
			var tt1 = sactionDate1[1] + '/' + sactionDate1[0] + '/' + sactionDate1[2];
			var sancDate = new Date(tt1);

			var date1 = date.split('/');
			var tt2 = date1[1] + '/' + date1[0] + '/'+ date1[2];
			var disDate = new Date(tt2);
			
			if(startGuaranteeDate == "")
			{
				msg1=msg1+"<li>Guarantee Not Started Can't Save the Data!!</li>";
				countData = 1;
			}
			if (disDate < sancDate) 
			{
				msg1=msg1+"<li>Selected Disbursement Dates cannot be before sanction date!!</li>";
				countData = 1;
			}
			if (date == "") 
			{
				msg1=msg1+"<li>Date can't be blank!!</li>";
				countData = 1;
			}
			if (amount == "") 
			{
				msg1=msg1+"<li>Amount can't be blank!!</li>";
				countData = 1;
			}
			
			var amount1 = 0;
			var GAmount = $("#guaranteedAmt").val();

			amount1 = amount1 + parseFloat(amount);
			if (amount1 > GAmount) 
			{
				msg1=msg1+"<li>Total disbursement amount can be less than or equal to guaranteed amount!!</li>";
				countData = 1;
			}
		});

		var total = $("#Total")[0].innerText;
		var guaranteedAmt = $("#guaranteedAmt").val();
		
		if(countData == 0)
		{
			if (parseFloat(total)>0)	
			{
				for (var i = 0; i < myElements.length; i++) {
					var check = "";
					//alert("Checked ::" + myElements.eq(i).find(".check").prop("checked"));
					if (myElements.eq(i).find(".check").prop("checked") == true) {
						check = "Y";
						$(".check").val(check);
						checkCount = 0;
						countData = 0;
						
						$("#errormsg").css({
							"color" : "#ff6666",
							"display" : "none"
						});
					} 
					else {
						check = "N";
						$(".check").val(check);
						//msg1=msg1+"<li>Please select one final Disbursement Checkbox!!</li>";
						checkCount = 1;
						countData = 1;
					}
				}
			} 
		}
	    if(countData==1){
	    	 msg1=msg1+"<li>Please select one final Disbursement Checkbox!!</li>";
	    	 msg1=msg1+"</ul>";
            $("#errormsg").html(msg1).css({
                 "color" : "#ff6666",
                 "display" : "block"
             });
            $(window).scrollTop(0);
            return;
        }
        else
        {
        	countData=0;
        	$("#errormsg").html("").css({
                "color" : "#ff6666",
                "display" : "none"
            });
       	}
		 if (checkCount == 0 && countData == 0) 
		 {
			 if (countData == 0) {
				    $("#btnSave").attr("disabled", "disabled");
					$("#btnSave")[0].innerText = 'loading';
					setTimeout(function() {
						$("#btnSave")[0].innerText = 'Save';
					}, 8000);

				}
			 document.disbursementForm.target = "_self";
			document.disbursementForm.method = "POST";
			document.disbursementForm.action = "disbursementDetail.do?method=getDisbursementJsonDetail";
			document.disbursementForm.submit(); 
		} 
	}
	function CancelData() {
		$("#cgpan").val('');
		getPromoter();
		$("#isDisable").css("display", "none");
	}
	function AddnewRow() {
		//alert("Called!!!!");
		var msg2="<b>Please correct the following Error:</b><ul>";
		var myElements = $("#TextBoxContainer .row");
		var data = [];
		for (var i = 0; i < myElements.length; i++) {
			var date = myElements.eq(i).find(".clsDate").val();
			var amount = myElements.eq(i).find(".clsAmount").val();
			data.push({
				"date" : date,
				"amount" : amount
			});
		}
		var count = 0;
		var TotalAmount = 0;
		jQuery.each(data,function(i, val) 
		{
			var date = val.date;
			var amount = val.amount;
							//alert("date is :" + date + "\t amount :" + amount);
							if (date == "") {
								msg2=msg2+"<li>Date can't be blank</li>";
								count = 1;
							}
							if (amount == "") {
								msg2=msg2+"<li>Amount can't be blank</li>";
								count = 1;
							}
							
							var amount1 = 0;
							var GAmount = $("#guaranteedAmt").val();

							amount1 = parseFloat(amount1) + parseFloat(amount);
							if (parseFloat(amount1) > parseFloat(GAmount)) {
								
								msg2=msg2+"<li>Total disbursement amount can be less than or equal to guaranteed amount</li>";
								count = 1;
							}
							if (parseFloat(amount1) == parseFloat(GAmount)) {
								
								msg2=msg2+"<li>You Cannot add new Item because total amount are matched with guaranteed amount</li>";
								count = 1;
							}
							TotalAmount = parseFloat(TotalAmount) + parseFloat(amount1);
							$("#Total")[0].innerText = TotalAmount;

		});
		if(count==1){		
            $("#errormsg").html(msg2).css({
                 "color" : "#ff6666",
                 "display" : "block"
             });
            msg2=msg2+"</ul>";
            $(window).scrollTop(0);
            return;
        }
        else
        {
        	count=0;
       	}
		if (count == 0) 
		{
			var div = $("<div />");
			$("#hdnVal").val(parseInt($("#hdnVal").val()) + 1)
			div.html(GetDynamicTextBox($("#hdnVal").val()));
			$("#TextBoxContainer").append(div);
		}
	}

	$(function() {

		$("#btnGet").bind("click", function() {
			var values = "";
			$("input[name=DynamicTextBox]").each(function() {
				values += $(this).val() + "\n";
			});
		});

		$("#btnSearch").bind("click", function() {
			
			$("#btnSearch").data('clicked', true);
			if ($("#btnSearch").data('clicked')) {
				var isSearchClicked = "Clicked";
				$("#isSearchClicked").val(isSearchClicked);
			} else {
				var isSearchClicked = "";
				$("#isSearchClicked").val(isSearchClicked);
			}
			
			var countErr = 0;
			var msg="<b>Please correct the following Error:</b><ul>";
            if ($("#promoterName").val() == "0") {
            	 msg=msg+"<li>Promoter Name can not be black!!</li>";
                 countErr=1;               
            }
            if ($("#cgpan").val() == "") 
            {
            	msg=msg+"<li>CGPAN can not be black!!</li>";
                countErr=1; 
            } 
            if(countErr==1){
                $("#errormsg").html(msg).css({
                     "color" : "#ff6666",
                     "display" : "block"
                 });
                msg=msg+"</ul>";
                $(window).scrollTop(0);
                return;
            }
            else
            {
            	countErr=0;
           	}
			
			if (countErr == 0) {
				$("#btnSearch").attr("disabled", "disabled");
				$("#btnSearch")[0].innerText = 'loading';
				setTimeout(function() {
					$("#btnSearch")[0].innerText == 'Search';
				}, 8000);
				getPromoterAmtInfo();
			}
		});

		$("#btnClear").bind("click", function() {
			$("#cgpan").val('');
			getPromoter();
		});
	});
	function GetDynamicTextBox(value) {
		return '<div class="row" style="margin-top: 10px;" id="div'+value+'"><div class="col-md-4"><input type="text" name="date" class="form-control clsDate" id="date'
				+ value
				+ '" onclick="setDateValue(id)" aria-describedby="emailHelp" autocomplete="off" style="background-color: #fff;" placeholder="DD/MM/YYYY">'
				+ '</div><div class="col-md-4"><input type="text" name="amount" class="form-control clsAmount" onchange="calculateAmount();" id="amount'
				+ value
				+ '" placeholder="Amount" maxlength="20" placeholder="Amount" step=".01" pattern="^\d+(?:\.\d{1,2})?$" style="text-align: right">'
				+ '</div><div class="col-md-4" style="margin-top: 10px; padding-left: 42px;"><input type="checkbox" style="margin-top: 5px;" class="form-check-input check" name="check" onClick="getCheck()" id="check'
				+ value
				+ '"><i class="fa fa-trash " id="btnAdd" style="font-size: 30px;color:red;padding-left: 30px;" onclick="return remove('
				+ value + ');"></i> </div></div>'
	}
</script>
</head>
<body>
	<html:errors />
	<html:form name="disbursementForm"
		type="com.cgtsi.actionform.DisbursementActionForm"
		action="disbursementDetail.do?method=getDisbursementSearchDetail"
		method="POST">
		
			<div class=" container row"
			style="margin-left: 20px; padding-top: 10px; padding-bottom: 10px; border-top: 1px solid brown; border-left: 1px solid brown; border-right: 1px solid brown; border-bottom: 1px solid brown;">
			
			
			<div class="col-md-12">
				<div class="col-md-12 alert alert-primary" role="alert">Search Details</div>
			</div>
			<div class="col-lg-12" id="errormsg" style="display: none"></div>
			
			<div class="col-lg-12">
				<div class="row">

					<div class="col-md-4">

						<label for="CGPAN">CGPAN</label><font color="#FF0000" size="2">*</font>
						<html:text property="cgpan" styleClass="form-control"
							onchange="return getPromoter();" styleId="cgpan"
							name="disbursementForm" />
					</div>
					<div class="col-md-4">
						<label for="PromoterName">Promoter Name</label><font
							color="#FF0000" size="2">*</font>
						<html:select styleClass="form-control" property="promoterName"
							name="disbursementForm" styleId="promoterName">
							<html:option value="0">Select</html:option>
							<%-- <html:optionsCollection property="promoterNameList" name="apForm"/> --%>
							<html:optionsCollection property="promoterValues"
								name="disbursementForm" label="label" value="value" />
						</html:select>
					</div>
					<div class="col-md-4" style="padding-top: 32px;">

						<button type="button" class="btn btn-primary" id="btnSearch">Search</button>
						<button type="button" class="btn btn-primary" id="btnClear">Clear</button>
					</div>
				</div>
				
				<div id="isDisable" style="display: none">
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">Guarantee
								Details</div>
						</div>

					</div>
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-4">
							<label for="exampleInputPassword1">Promoter Name : </label>

							<html:text property="promoterNameValue" size="20" maxlength="10"
								alt="Reference" name="disbursementForm"
								styleClass="form-control" disabled="true" />
						</div>
						<div class="col-md-4">
							<label for="exampleInputPassword1">Guaranteed Amount : </label>
							<html:text property="guaranteedAmt" size="20" maxlength="10"
								alt="Reference" name="disbursementForm" disabled="true"
								styleClass="form-control" style="text-align:right"
								styleId="guaranteedAmt" />

							<html:hidden property="message" name="disbursementForm"
								styleId="message" />
							<html:hidden property="roleName" name="disbursementForm" />
							<html:hidden property="isSearchClicked" name="disbursementForm"
								styleId="isSearchClicked" />
						</div>
					<!-- </div>
					<div class="row" style="margin-top: 10px;"> -->
						<div class="col-md-4">
							<label for="exampleInputPassword1">Guarantee Start date :
							</label>

							<html:text property="startGuaranteeDate" size="20" maxlength="10"
								alt="Reference" name="disbursementForm" disabled="true"
								styleClass="form-control" style="text-align:left"
								styleId="startGuaranteeDate" />
						</div>
						<div class="col-md-4">
							<label for="exampleInputPassword1">Sanction Date: </label>

							<html:text property="sanctionDate" size="20" maxlength="10"
								alt="Reference" name="disbursementForm" disabled="true"
								styleClass="form-control" style="text-align:left"
								styleId="sanctionDate" />
						</div>
					</div>
					
					<div class="row" style="margin-top: 10px;">

						<div class="col-md-8 alert alert-primary" role="alert">Disbursement
							Details</div>
						<div class="col-md-4 alert alert-primary" role="alert">Final
							Disbursement</div>
					</div>
					
					<%
						String role = (String) request.getAttribute("role");
							System.out.println("ROLE IS ::" + role);
							PopuLateData d1 = new PopuLateData();
							if (role != null && role.contains("MAKER")) {
					%>
					<div class="col-lg-12">
						<div class="row" id="div0">

							<div id="TextBoxContainer" class="col-lg-12">
								<input type="hidden" id="hdnVal" value="1" />
								<div class="row" id="div0">
									<div class="col-md-4">
										<label for="Date">Date</label><font color="#FF0000" size="2">*</font>

										<input type="text" id="date1" class="form-control clsDate"
											name="date" aria-describedby="emailHelp" autocomplete="off"
											placeholder="Enter Date" onclick="setDateValue(id)"
											style="background-color: #fff;">
									</div>
									<div class="col-md-4">
										<label for="exampleInputPassword1">Amount</label> <font
											color="#FF0000" size="2">*</font> <input type="Text"
											name="amount" class="form-control clsAmount" id="amount1"
											onchange="calculateAmount();" maxlength="20"
											placeholder="Amount" step=".01" pattern="^\d+(?:\.\d{1,2})?$"
											style="text-align: right" required="required">
									</div>
									<div class="col-md-4"
										style="margin-top: 37px; padding-left: 42px;">
										<input type="checkbox" class="form-check-input check" name="check"
											onClick="getCheck()" id="check1">
									</div>
								</div>
								<!--Textboxes will be added here -->
							</div>

						</div>

						<div class="row" style="margin-top: 10px;">

							<div class="col-lg-4" style="padding-left: 0px;">
								<i class="fa fa-plus " id="btnAdd" onclick="AddnewRow()"
									style="font-size: 35px; color: #007bff; padding-left: 30px;"></i>
							</div>
							<div class="col-lg-4">
								<button id="btnSave" type="button" onclick="SaveData()"
									class="btn btn-primary">
								Save
								</button>
								<input id="btnAdd" type="button" value="Cancel"
									onclick="return CancelData();" class="btn btn-primary" />

							</div>
							<div class="col-lg-4" style="display: flex; font-weight: 800;">
							
							
								<input type="hidden" id="hdnVal" value="1" /> Total Amount:
								
								<div id="Total" style="color: brown; margin-left: 12px;">0.00
								</div>
								<!--Textboxes will be added here -->
							</div>
						</div>
					</div>
					<%
						} else {
					%>
					<div class="col-lg-12">

						<%
							if (request.getAttribute("disurseData") != null) {
										ArrayList<PopuLateData> list = (ArrayList) request.getAttribute("disurseData");
										Iterator<PopuLateData> iterator = list.iterator();
										while (iterator.hasNext()) {
											PopuLateData d = iterator.next();
						%>
						<div class="row" id="div0">
							<div class="col-md-4">
								<label for="Date">Date</label><font color="#FF0000" size="2">*</font>
								<input type="Text" name="date" value="<%=d.getDate()%>"
									class="form-control clsDate" id="date1" disabled
									aria-describedby="emailHelp" placeholder="DD/MM/YYYY">

							</div>
							<div class="col-md-4">
								<label for="exampleInputPassword1">Amount</label><font
									color="#FF0000" size="2">*</font> <input type="number"
									name="amount" class="form-control clsAmount" id="amount1"
									disabled style="text-align: right" value="<%=d.getAmount()%>">

							</div>
							<div class="col-md-4"
								style="margin-top: 37px; padding-left: 42px;">
								<input type="checkbox" name="check"
									class="form-check-input check" <%=d.getCheckBoxValue()%>
									id="check1" disabled>
							</div>
						</div>


						<%
							}
						%>
						<div class="row" style="margin-top: 10px;">

							<div class="col-lg-4">
								<!--Textboxes will be added here -->
							</div>
							<div class="col-lg-4">
								<input id="btnAdd" type="button" value="Cancel"
									onclick="return CancelData();" class="btn btn-primary" />

							</div>
							<div class="col-lg-4">
								<input type="hidden" id="hdnVal" value="1" />
								<!--Textboxes will be added here -->
							</div>
						</div>
						<%
							}
								}
						%>
					</div>
					<br />
					<!--<input id="btnGet" type="button" value="Get Values" />-->
				</div>
			</div>
	</html:form>
</body>
</html>