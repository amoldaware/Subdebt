<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<% session.setAttribute("CurrentPage","showRegistrationForm.do?method=showRegistrationForm");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>


	<script type="text/javascript">

function nospaces(t){

if(t.value.match(/\s/g)){

alert('Sorry, you are not allowed to enter any spaces');

t.value=t.value.replace(/\s/g,'');

}

}

</script>

	
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:form action="showRegistrationFormSubmit.do?method=showRegistrationFormSubmit" method="POST">			
		<html:errors />
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
							<DIV align="right"><A HREF="#">HELP</A></DIV>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<tr>
									<TD  align="left" colspan="4"><font size="2"><b>Fields marked as</b></font>
										<font color="#FF0000" size="2">*</font><font size="2"><b>are mandatory</b></font>
									</td>										
								</tr>
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading">Registration Form For Creation of Checker User Id and Password </TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
										</TABLE>
									</TD>
								</TR>										
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">&nbsp;
										<font color="#FF0000" size="2">*</font>Employee First Name
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="empFName" name="adminActionForm" size="25" styleId="fname"  onkeyup="nospaces(this)" maxlength="19"/>
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">&nbsp;
										Employee Middle Name
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="empMName" name="adminActionForm" size="25" styleId="mname" onkeyup="nospaces(this)" maxlength="19" />
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">&nbsp;
										<font color="#FF0000" size="2">*</font>Employee Last Name
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="empLName" name="adminActionForm" size="25" styleId="lname"  onkeyup="nospaces(this)" maxlength="19"/>
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">&nbsp;
										<font color="#FF0000" size="2">*</font>Bank Employee ID Code									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="empId" name="adminActionForm" size="25" styleId="eid" maxlength="10"/>
									</TD>
								</TR>		
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">&nbsp;
										<font color="#FF0000" size="2">*</font>Designation
									</TD>
									<TD align="left" class="TableData">
										<html:text property="designation" name="adminActionForm" size="25" styleId="desig" maxlength="49"/>
										<font color="#FF0000" size="1">Designation Should be Officer not below the rank of AGM(Scale-V Officers of PSU Banks) or Equivalent</font>
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">&nbsp;
										<font color="#FF0000" size="2">*</font>Mobile No
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="phoneNo" name="adminActionForm" maxlength="10" size="25" styleId="phno" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">&nbsp;
										<font color="#FF0000" size="2">*</font>Email Id
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="emailId" name="adminActionForm" size="25" styleId="emId" maxlength="40"/>
									</TD>
								</TR>										
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">&nbsp;
										<font color="#FF0000" size="2">*</font>Hint Question
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="hintQues" name="adminActionForm" size="25" styleId="Hques" maxlength="199"/>
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">&nbsp;
										<font color="#FF0000" size="2">*</font>Hint Answer
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="hintAns" name="adminActionForm" size="25" styleId="Hans" maxlength="199"/>
									</TD>
								</TR>
								<TR><TD colspan="2"><font size="3"><b>Note : </b></font></TD></TR>

<TR>											
									<TD colspan="2"><font size="2" color="#FF0000">1.  The Official for whom the above User Id and Password is being created shall be responsible for approving the transactions as a Chekcer,submitted by their operating level Officer.</font></TD>
								</TR>

								<TR>											
									<TD colspan="2"><font size="2" color="#FF0000">2.  There will be one Id and Password for approving authority not below the rank of AGM or Equivalent which will be termed as Checker User Id and Password.Approving authority will be same for single/multiple users with separate User Id with in one MLI Id.</font></TD>
								</TR>
								<TR>											
									<TD colspan="2"><font size="2" color="#FF0000">3.  ID Password Once Generated Should be Operated Only By The Above Mentioned Official.</font></TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR><TD height="20" >&nbsp;</TD></TR>
					<TR>
						<TD align="center" valign="baseline">
							<DIV align="center">								
								<A href="#" onclick="return submitFormValidate('showRegistrationFormSubmit.do?method=showRegistrationFormSubmit')">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0">
								</A>
								<A href="javascript:document.adminActionForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0">
								</A>
								<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
									<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0">
								</A>																
							</DIV>
						</TD>
					</TR>
				</TABLE>
			</TD>
			<TD width="20" background="images/TableVerticalRightBG.gif">&nbsp;</TD>
		</TR>
		<TR>
			<TD width="20" align="right" valign="top">
				<IMG src="images/TableLeftBottom1.gif" width="20" height="15">
			</TD>
			<TD background="images/TableBackground2.gif">&nbsp;</TD>
			<TD width="20" align="left" valign="top">
				<IMG src="images/TableRightBottom1.gif" width="23" height="15">
			</TD>
		</TR>
	</html:form>			
</TABLE>	



	

<script type="text/javascript">
	function submitFormValidate(action)
	{		
		//alert("submitFormValidate S : "+action);
		var fname = document.getElementById('fname').value;
		//alert("fname : "+fname);

		//var mname = document.getElementById('mname').value;
		//alert("mname : "+mname);

		var lname = document.getElementById('lname').value;
		//alert("lname : "+lname);

		var eid = document.getElementById('eid').value;
		//alert("eid : "+eid);
		
		var desig = document.getElementById('desig').value;
		//alert("desig : "+desig);
		
		var phno= document.getElementById('phno').value;
		//alert("phno : "+phno);
		
		var emId = document.getElementById('emId').value;
		//alert("emId : "+emId);
		
		var Hques = document.getElementById('Hques').value;
		//alert("Hques : "+Hques);
		
		var Hans = document.getElementById('Hans').value;
		//alert("Hans : "+Hans);
		
		var newfname = fname.replace(/(^\s+|\s+$)/g,'');
		var newlname  = lname.replace(/(^\s+|\s+$)/g,'');
		var newphno = phno.replace(/(^\s+|\s+$)/g,'');
		var alphaExp = /^[a-zA-Z]+$/;
		
		if(fname == "")
		{
			alert("You Must Enter Your First Name.");
			return false;
		}
		else if(newfname.length < 4) ///else if(fname.trim().length < 4)
		{
			//alert("First Name Length : "+newfname.length);
			alert("Your First Name Should Not Be Less Than 4 Character.");
			return false;
		}
		else
		{
			if(fname.match(alphaExp))
            		{
                		//Your logice will be here.
            		}
			else{
                		alert("First Name Should  contains alphabets only");
                		return false;
            		    }
		}
		
		
		if(lname == "")
		{
			alert("You Must Enter Your Last Name.");
			return false;
		}
		else if(newlname.length < 4)  ///else if(lname.trim().length < 4)
		{
			//alert("Last Name Length : "+newlname.length);
			alert("Your Last Name Should Not Be Less Than 4 Character.");
			return false;
		}
else
		{
			if(lname.match(alphaExp))
            		{
                		//Your logice will be here.
            		}
			else{
                		alert("Last Name Should  contains alphabets only");
                		return false;
            		    }
		}
		

		if(eid == "")
		{
			alert("You Must Enter Your ID.");
			return false;
		}
		
		if(desig == "")
		{
			alert("You Must Enter Your Designation.");
			return false;
		}
		
		if(phno == "")
		{
			alert("You Must Enter Your Phone No.");
			return false;
		}
		else if(newphno.length < 10)  //else if(phno.trim().length < 10)
		{
			alert("Your Phone No Should Not Be Less Than 10 Digit.");
			return false;
		}

		if(emId == "")
		{
			alert("You Must Enter Your Email ID.");		
			return false;
		}
		else if(emId.length > 0)
		{
			var atpos = emId.indexOf("@");
			var dotpos = emId.lastIndexOf(".");			
			if(atpos<1 || dotpos<atpos+2 || dotpos+2>=emId.length)
			{
				alert("Invalid Email-ID.");
				return false;
			}		
		}

		if(Hques == "")
		{
			alert("You Must Enter Your Hint Question.");
			return false;
		}
		
		if(Hans == "")
		{
			alert("You Must Enter Your Hint Answer.");
			return false;
		}

		if(fname!=null &&  lname!=null && eid!=null && desig!=null && phno!=null &&  emId!=null &&  Hques!=null &&  Hans!=null )
		{
			var r = confirm("Form once submitted cannot be changed.Please confirm the details once again  and then save");
		if (r == true) {
				
			document.forms[0].action=action;  
			document.forms[0].target="_self";
			document.forms[0].method="POST";
			document.adminActionForm.submit();
			//document.adminActionForm.reset();
			return true;
			
			
		   		} else {
		   			return false;
		 
		}
		
		
		}
	
	
	
	
	
	//alert("submitFormValidate E");
	
}			
</script>