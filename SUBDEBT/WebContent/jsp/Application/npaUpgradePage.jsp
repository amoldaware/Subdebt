<%@page import="com.cgtsi.actionform.NPAUpgradePopulateData"%>
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
<title>Emergency Credit Guarantee Fund Trust for Small
	Industries(CGTSI)</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<style>
.ui-datepicker {
	background-color: #fff;
}
</style>

<script type="text/javascript">
 
 
$(document).ready(function() 
{
	var cgpan = document.getElementById('cgpan').value;
	var isSearchClicked = document.getElementById('isSearchClicked').value;
	var message = document.getElementById('message').value;
	var roleName = document.getElementById('roleName').value; 
	//alert("message :: ["+message+"]\t roleName :: ["+roleName+"]\t isSearchClicked :: ["+isSearchClicked+"]\t cgpan :: ["+cgpan+"]");
	
	if ((cgpan == "" && isSearchClicked == "")) {
		$("#isDisable").css("display", "none");
	} else if ((cgpan != "" && isSearchClicked == "")) {
		$("#isDisable").css("display", "none");
	} else if ((cgpan == "" && isSearchClicked != "")) {
		$("#isDisable").css("display", "none");
	} else if((cgpan != "" && isSearchClicked != "" && roleName == "")) {
		$("#isDisable").css("display", "none");
	}
	else {
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
		$("#roleName").val("");
		$("#cgpan").val('');
		$("#promoterName").empty();
		$("#promoterName").append('<option value="0">Select</option>');
	} 
}); 

function setDateValue() {	
	 var start = new Date("2020-06-24"), 
	 end = new Date(), 
	 diff = new Date(end - start), 
	 days = diff/1000/60/60/24;
	 $("#npaUpgradation_date").datepicker({
		dateFormat : 'dd/mm/yy',
		minDate : days * -1,
        maxDate : end
	}).attr('readonly', 'readonly').focus(); 
}

            function calculateNPA() {
                var total =
                    parseFloat($("#dateofNPA0").val()) + parseFloat($("#dateofNPA1").val()) + parseFloat($("#dateofNPA2").val()) + parseFloat($("#dateofNPA3").val()) + parseFloat($("#dateofNPA4").val()) + parseFloat($("#dateofNPA5").val());
                $("#divNPA")[0].innerText = total.toFixed(2);
            }

            function calculateNetworthofguarantor() {
                var total =
                    parseFloat($("#Networthofguarantor0").val()) +
                    parseFloat($("#Networthofguarantor1").val()) +
                    parseFloat($("#Networthofguarantor2").val()) +
                    parseFloat($("#Networthofguarantor3").val()) +
                    parseFloat($("#Networthofguarantor4").val()) +
                    parseFloat($("#Networthofguarantor5").val());
                $("#Networthofguarantor")[0].innerText = total.toFixed(2);
            }
            function calculatedateofSanction() {
                var total =
                    parseFloat($("#dateofSanction0").val()) +
                    parseFloat($("#dateofSanction1").val()) +
                    parseFloat($("#dateofSanction2").val()) +
                    parseFloat($("#dateofSanction3").val()) +
                    parseFloat($("#dateofSanction4").val()) +
                    parseFloat($("#dateofSanction5").val());
                $("#dateofSanction")[0].innerText = total.toFixed(2);
            }

            function calculateIntrest() {
                
                var PO = parseFloat($("#GuaranteeAmount").val()) - parseFloat($("#PrinicipleRepayment").val());
                $("#PrincipleOutstanding").val(PO);
                var intrest = ((PO * parseFloat($("#InterestRate").val())) / 100 / 365) * 90;
                $("#InterestOutstanding").val(parseInt(intrest));
            }
            
            function getNPAPromoter() {
        		var cgpan = document.getElementById('cgpan').value;
        		document.npaUpgradeForm.target = "_self";
        		document.npaUpgradeForm.method = "POST";
        		document.npaUpgradeForm.action = "npaUpgradeDetail.do?method=getNPAUpgradeDetail&cgpan="+ cgpan;
        		document.npaUpgradeForm.submit();
        	}
            
            function getNPAPromoterInfo() {
        		var cgpan = document.getElementById('cgpan').value;
        		var promoterName = document.getElementById('promoterName').value;
        		var isSearchClicked = document.getElementById('isSearchClicked').value;

        		//alert(cgpan + "::" + promoterName + "::" + isSearchClicked);
        		
        		document.npaUpgradeForm.target = "_self";
        		document.npaUpgradeForm.method = "POST";
        		document.npaUpgradeForm.action = "npaUpgradeDetail.do?method=getNPAUpgradeSearchDetail&cgpan="
        				+ cgpan
        				+ "&promoterName"
        				+ promoterName
        				+ "&isSearchClicked"
        				+ isSearchClicked;
        		document.npaUpgradeForm.submit();
        	}
            
            function SaveData() 
            {
            	var npa_date = $("#npa_date").val();
                var npaUpgradation_date = $("#npaUpgradation_date").val();
                var reasonforUpgradation = $("#reasonforUpgradation").val();
              //  alert(npa_date + "\t" + npaUpgradation_date + "\t" + reasonforUpgradation);
              
              	var npa_date1 = npa_date.split('/');
				var tt1 = npa_date1[1] + '/' + npa_date1[0] + '/' + npa_date1[2];
				var npa_date2 = new Date(tt1);

				var npaUpgradation_date1 = npaUpgradation_date.split('/');
				var tt2 = npaUpgradation_date1[1] + '/' + npaUpgradation_date1[0] + '/'+ npaUpgradation_date1[2];
				var npaUpgradation_date2 = new Date(tt2);
				
				//alert("Upgradation Date ::" + npaUpgradation_date2 + "NPA Date ::" + npa_date2);
              
            	var count = 0;
            	var msg1="<b>Please correct the following Error:</b><ul>";
                if($("#npaUpgradation_date").val() == "")
            	{
            		 count = 1;
            		 msg1=msg1+"<li>NPA Upgradation Date can not be blank!!</li>";
            	}
            	if($("#reasonforUpgradation").val() == "")
            	{
            		count = 1;
            		msg1=msg1+"<li>Reason for Upgradation can not be black!!</li>";
            	}
            	
            	if((tt1 == tt2) || (npaUpgradation_date2 < npa_date2))
            	{
            		count = 1;
            		msg1=msg1+"<li>NPA Upgradation Date can not be same or before NPA Date</li>";
            	}
            	if(count==1){
                    $("#errormsg").html(msg1).css({
                         "color" : "#ff6666",
                         "display" : "block"
                     });
                    msg1=msg1+"</ul>";
                    $(window).scrollTop(0);
                    return;
                }
                else
                {
                	count=0;
               	}
  
            	if(count == 0)
            	{
            		$("#btnSave").attr("disabled", "disabled");
            		$("#btnSave")[0].innerText='loading';
        		    setTimeout(function() {
        		    	$("#btnSave")[0].innerText='Save';
        		   }, 8000);
        		    
            		document.npaUpgradeForm.target = "_self";
        			document.npaUpgradeForm.method = "POST";
        			document.npaUpgradeForm.action = "npaUpgradeDetail.do?method=saveNPAUpgradeDetails";
        			document.npaUpgradeForm.submit(); 
            	}
            }
            
            function CancelData() {
        		$("#cgpan").val('');
        		getNPAPromoter();
        		$("#isDisable").css("display", "none");
        	}

            $(function () {
                $("#btnSearch").bind("click", function () {
                	$("#btnSearch").data('clicked', true);
        			if ($("#btnSearch").data('clicked')) {
        				var isSearchClicked = "Clicked";
        				$("#isSearchClicked").val(isSearchClicked);
        			} 
        			else 
        			{
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
                    	$("#btnSearch")[0].innerText='loading';
    				    setTimeout(function() {
    				    	$("#btnSearch")[0].innerText=='Search';
    				   }, 8000);
    				    
                        getNPAPromoterInfo();
                    }
                });
                
                $("#btnClear").bind("click", function() {
        			$("#cgpan").val('');
        			getNPAPromoter();
        		});
            });
        </script>
</head>

<body bgcolor="#FFFFFF" topmargin="0"
	data-new-gr-c-s-check-loaded="14.1008.0" data-gr-ext-installed="">
	<html:form name="npaUpgradeForm"
		type="com.cgtsi.actionform.NPAUpgradeActionForm"
		action="npaUpgradeDetail.do?method=getNPAUpgradeDetail" method="POST">
		<div class=" container row"
			style="margin-left: 20px; padding-top: 10px; padding-bottom: 10px; border-top: 1px solid brown; border-left: 1px solid brown; border-right: 1px solid brown; border-bottom: 1px solid brown;">

			<div class="col-md-12">
				<div class="col-md-12 alert alert-primary" role="alert">
					Search Details</div>
			</div>
			<div class="col-lg-12" id="errormsg" style="display: none"></div>
			<!-- <div class="col-lg-12 alert alert-info" role="alert" id="errormsg" style="display: none"> 
			</div>-->

			<div class="col-lg-12">
				<div class="row">
					<div class="col-md-4">
						<label for="CGPAN">CGPAN</label><font color="#FF0000" size="2">*</font>
						<html:text property="cgpan" styleClass="form-control"
							onchange="return getNPAPromoter();" styleId="cgpan"
							name="npaUpgradeForm" />
						<!-- <input type="Text" class="form-control" id="CGPAN"
							placeholder="Enter CGPAN" /> -->
					</div>
					<div class="col-md-4">
						<label for="PromoterName">Promoter Name</label><font
							color="#FF0000" size="2">*</font>
						<html:select styleClass="form-control" property="promoterName"
							name="npaUpgradeForm" styleId="promoterName">
							<html:option value="0">Select</html:option>
							<html:optionsCollection property="promoterValues"
								name="npaUpgradeForm" label="label" value="value" />
						</html:select>

						<html:hidden property="message" name="npaUpgradeForm"
							styleId="message" />
						<html:hidden property="isSearchClicked" name="npaUpgradeForm"
							styleId="isSearchClicked" />
						<html:hidden property="roleName" name="npaUpgradeForm"
							styleId="roleName" />
					</div>
					<div class="col-md-4" style="padding-top: 32px;">

						<button type="button" class="btn btn-primary" id="btnSearch">Search</button>
						&nbsp;&nbsp;
						<button type="button" class="btn btn-primary" id="btnClear">Clear</button>
					</div>
				</div>

				<%
					String role = (String) request.getAttribute("role");
					System.out.println("ROLE IS ::" + role);
					if (role != null && role.contains("MAKER")) 
					{	
				%>
				<div id="isDisable" style="display: none;">
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">NPA
								Marking Data</div>
						</div>
						<div class="col-md-4">
							<label for="App_Ref_No">NPA Date</label>
							<html:text property="npa_date" size="20" maxlength="10"
								alt="Reference" name="npaUpgradeForm" styleClass="form-control"
								styleId="npa_date" style="background-color: #eee;"
								readonly="readonly" />
						</div>

					</div>
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">NPA
								Upgradation Data</div>
						</div>
						<div class="col-md-4">
							<label for="PromoterName ">NPA Upgradation date </label> <font
								color="#FF0000" size="2">*</font> <input type="text"
								id="npaUpgradation_date" class="form-control clsDate"
								name="npaUpgradation_date" aria-describedby="emailHelp"
								style="background-color: #fff;" autocomplete="off"
								placeholder="DD/MM/YYYY" onclick="setDateValue()">
						</div>
						<div class="col-md-4">
							<label for="ITPAN ">Reason for Upgradation </label> <font
								color="#FF0000" size="2">*</font>
							<html:text property="reasonforUpgradation" size="20"
								styleId="reasonforUpgradation" alt="Reference"
								name="npaUpgradeForm" styleClass="form-control" />
						</div>
						<html:hidden property="npaId" name="npaUpgradeForm"
							styleId="npaId" />
						<html:hidden property="roleName" name="npaUpgradeForm"
							styleId="roleName" />

						<div class="col-md-4" style="padding-top: 32px;">
							<button id="btnSave" type="button" onclick="SaveData()"
								class="btn btn-primary">Save</button>
							<!-- <input id="btnAdd" type="button" value="Save"
									onclick="SaveData();" class="btn btn-primary" />  -->
							<input id="btnAdd" type="button" value="Cancel"
								onclick="return CancelData();" class="btn btn-primary" />
							<button type="button" class="btn btn-primary" data-toggle="modal"
								data-target="#myModal">View History</button>
						</div>
					</div>
				</div>
				<%
						} else {
					%>
				<div id="isDisable" style="display: none;">
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">NPA
								Marking Data</div>
						</div>
						<div class="col-md-4">
							<label for="App_Ref_No">NPA Date</label>
							<html:text property="npa_date" size="20" maxlength="10"
								styleId="npa_date" alt="Reference" name="npaUpgradeForm"
								styleClass="form-control" style="background-color: #eee;"
								readonly="readonly" />
						</div>

					</div>
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">NPA
								Upgradation Data</div>
						</div>
						<div class="col-md-4">
							<label for="PromoterName ">NPA Upgradation date </label> <font
								color="#FF0000" size="2">*</font>
							<!-- <input type="text" id="npaUpgradation_date" class="form-control clsDate"
									   name="npaUpgradeForm" aria-describedby="emailHelp"
									   style="background-color: #fff; disabled="true">	 -->
							<html:text property="npaUpgradation_date" size="20"
								alt="Reference" name="npaUpgradeForm"
								styleId="npaUpgradation_date" styleClass="form-control"
								disabled="true" />
						</div>
						<div class="col-md-4">
							<label for="ITPAN ">Reason for Upgradation </label> <font
								color="#FF0000" size="2">*</font>
							<html:text property="reasonforUpgradation" size="20"
								alt="Reference" name="npaUpgradeForm"
								styleId="reasonforUpgradation" styleClass="form-control"
								disabled="true" />
						</div>
						<html:hidden property="npaId" name="npaUpgradeForm"
							styleId="npaId" />
						<html:hidden property="roleName" name="npaUpgradeForm"
							styleId="roleName" />

						<div class="col-md-4" style="padding-top: 32px;">
							<input id="btnAdd" type="button" value="Cancel"
								onclick="return CancelData();" class="btn btn-primary" />
							<button type="button" class="btn btn-primary" data-toggle="modal"
								data-target="#myModal">View History</button>
						</div>
					</div>
				</div>
				<%
						}
					%>
			</div>
		</div>


		<div class="modal fade" id="myModal" role="dialog">
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
					ArrayList<NPAUpgradePopulateData> list = (ArrayList) request.getAttribute("npaPopulateData");
					Iterator<NPAUpgradePopulateData> iterator = list.iterator();
					while (iterator.hasNext()) {
						NPAUpgradePopulateData d = iterator.next();
				%>
								<tr>
									<th scope="row"><%= d.getClm_au_id() %></th>
									<td><%= d.getClm_LVEL() %></td>
									<td><%= d.getUser_id() %></td>
									<td><%= d.getClm_dttime() %></td>
									<td><%= d.getClm_status() %></td>
									<td><%= d.getClm_remark() %></td>
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
