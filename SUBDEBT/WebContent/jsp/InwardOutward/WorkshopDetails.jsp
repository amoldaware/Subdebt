<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@page import="org.apache.struts.validator.DynaValidatorActionForm"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<% session.setAttribute("CurrentPage","workshopDetails.do?method=workshopEntry");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<script language="javascript" type="text/javascript"> 	

   function enableAgencyNames() 
   {
       var agencyName = findObj("agencyName");
        mliName  = document.getElementById("mliName").value;
        //alert("mliName:"+mliName);   
         if(mliName.selectedIndex==0)
          { 	 
  	        return; 	 
           } 
           
           var subMenu=findObj("agencyName");
           var govOrgs=findObj("governmentOrgs");
	         //var selObj=subMenu.arguments[0];
	
         if(mliName!=null && mliName!="")
	        {  
            if(mliName=="CGTMSE"){
              document.getElementById("agencyName").disabled = true; 
              document.getElementById("governmentOrgs").disabled=true;
            }             
            else  if(mliName=="Agency on behalf of CGTMSE")
            {
                document.getElementById("agencyName").disabled = false; 
                document.getElementById("governmentOrgs").disabled=true;
            }  
            else {
                document.getElementById("agencyName").disabled = true; 
                document.getElementById("governmentOrgs").disabled=false;
            
            }

    }
}


function enableMLINames() 
{

  var bankNames = findObj("bankNames");
        organisedfor  = document.getElementById("organisedfor").value;
        // alert("organisedfor:"+organisedfor);   
         if(organisedfor.selectedIndex==0)
          { 	 
  	        return; 	 
           }            
           
	
         if(organisedfor!=null && organisedfor!="")
	        {  
            if(organisedfor=="MLI"){
              document.getElementById("bankNames").disabled = false;  
              document.getElementById("sourceName").disabled = true;  
              
            }             
            else 
            {
                document.getElementById("bankNames").disabled = true; 
                document.getElementById("sourceName").disabled = false; 
            } 

    }

}




function enableOfficerDetails() 
{

      var participants = findObj("participants");
      var name = findObj("name");
      var designation = findObj("designation");
      var organisation = findObj("organisation");
      var zone = findObj("zone");
      var mliId = findObj("mliId");
        type  = document.getElementById("type").value;
        // alert("organisedfor:"+organisedfor);   
         if(type.selectedIndex==0)
          { 	 
  	        return; 	 
           }            
           
	
         if(type!=null && type!="")
	        {  
            if(type=="Workshop"){
              document.getElementById("participants").disabled = false;  
              document.getElementById("name").disabled = false;  
              // document.getElementById("designation").disabled = false; 
              document.getElementById("organisation").disabled = false; 
              document.getElementById("zone").disabled = false;  
              document.getElementById("mliId").disabled = false;  
            }             
            else if(type=="Seminar")
            {
                 document.getElementById("participants").disabled = true;  
              document.getElementById("name").disabled = true;  
             // document.getElementById("designation").disabled = true; 
              document.getElementById("organisation").disabled = true;  
              document.getElementById("zone").disabled = true;  
              document.getElementById("mliId").disabled = true;   
            } 
            
            else if(type=="Awareness Programme")
            {
                 document.getElementById("participants").disabled = false;  
              document.getElementById("name").disabled = false;  
           //   document.getElementById("designation").disabled = false; 
              document.getElementById("organisation").disabled = false;  
              document.getElementById("zone").disabled = true;  
              document.getElementById("mliId").disabled = true;    
            } 
           else if(type=="Exhibition")
            {
                 document.getElementById("participants").disabled = true;  
              document.getElementById("name").disabled = true;  
             // document.getElementById("designation").disabled = true; 
              document.getElementById("organisation").disabled = true;  
              document.getElementById("zone").disabled = false;  
              document.getElementById("mliId").disabled = false;  
              document.getElementById("zone").disabled = true;  
              document.getElementById("mliId").disabled = true; 
            }  
            
            else if(type=="Business Developement Meeting")
            {
              document.getElementById("participants").disabled = true;  
              document.getElementById("name").disabled = false;  
            //  document.getElementById("designation").disabled = true; 
              document.getElementById("organisation").disabled = true;   
              document.getElementById("zone").disabled = false;  
              document.getElementById("mliId").disabled = false; 
            }  
            
            else if(type=="GOI Meeting")
            {
              document.getElementById("participants").disabled = true;  
              document.getElementById("name").disabled = false;  
             // document.getElementById("designation").disabled = true; 
              document.getElementById("organisation").disabled = true;  
              document.getElementById("zone").disabled = true;  
              document.getElementById("mliId").disabled = true; 
            } 
            else if(type=="RBI Meeting")
            {
              document.getElementById("participants").disabled = true;  
              document.getElementById("name").disabled = false;  
            //  document.getElementById("designation").disabled = true; 
              document.getElementById("organisation").disabled = true;  
              document.getElementById("zone").disabled = true;  
              document.getElementById("mliId").disabled = true; 
            } 
          else if(type=="SLBC")
            {
              document.getElementById("participants").disabled = true;  
              document.getElementById("name").disabled = false;  
            //  document.getElementById("designation").disabled = true; 
              document.getElementById("organisation").disabled = true;  
              document.getElementById("zone").disabled = true;  
              document.getElementById("mliId").disabled = true; 
            }    
           else if(type=="SLIIC")
            {
              document.getElementById("participants").disabled = true;  
              document.getElementById("name").disabled = false;  
            //  document.getElementById("designation").disabled = true; 
              document.getElementById("organisation").disabled = true;  
              document.getElementById("zone").disabled = true;  
              document.getElementById("mliId").disabled = true; 
            } 

    }

}






  	var req; 
      
  	/* 	 
  	* Get the second options by calling a Struts action 	 
  	*/	 
        
  	function retrieveSecondOptions(){ 
       // alert ("inside retrieveSecondOptions() method");
  	//firstBox = document.getElementById('firstBox'); 
        stateName = document.getElementById('stateName'); 
  	//Nothing selected 	 
  	if(stateName.selectedIndex==0){ 	 
  	return; 	 
        } 	 
  
	selectedOption = stateName.options[stateName.selectedIndex].value; 	 
  	//get the (form based) params to push up as part of the get request 	 
        //afterTcMli.do?method=getDistricts
  	url="workshopDetails.do?method=getDistricts&stateName="+stateName.value; 
      
  	//Do the Ajax call 	 
  	if (window.XMLHttpRequest){ // Non-IE browsers 	 
  	req = new XMLHttpRequest(); 	 
  	//A call-back function is define so the browser knows which function to call after the server gives a reponse back 	 
  	req.onreadystatechange = populateSecondBox; 	 
        try { 	 
  	req.open("GET", url, true); //was get 	 
  	} catch (e) { 	 
  	alert("Cannot connect to server"); 	 
  	} 	 
 	req.send(stateName); 	 
 	} else if (window.ActiveXObject) { // IE 	 
 	req = new ActiveXObject("Microsoft.XMLHTTP"); 	 
 	if (req) { 	 
	req.onreadystatechange = populateSecondBox; 	 
	req.open("GET", url, true); 	 
	
        req.send(); 	 
	} 	 
 	} 	   
	} 	 

	function populateSecondBox(){ 	  
	document.getElementById('districtName').options.length = 0; 	 
  	if (req.readyState == 4) { // Complete 	 
  	if (req.status == 200) { // OK response 	 
  	textToSplit = req.responseText; 
        //alert ("the data: "+textToSplit);
                            if(textToSplit == '803')
                              { 	 
                                alert("No select option available on the server") 	 
                               } 	 //if close

	var returnElements=textToSplit.split("||"); 
  
	for ( var i=0; i<returnElements.length; i++ ){ 	 
               valueLabelPair = returnElements[i].split(";"); 	 
              // alert("the first value is :-=-->"+valueLabelPair[0] );
               document.getElementById('districtName').options[i] = new Option(valueLabelPair[0], valueLabelPair[1]); 	 
       	} 	 //for close
  	} //ok close	 
            else { 	 
        	         alert("Bad response by the server"); 	 
        	       } 
  } //complet close          	 
  
	} 	   
 
	</script>	



<% 
  SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
  Date systemDate  = new Date();
  String sysDate = dateFormat.format(systemDate);
  DynaValidatorActionForm dynaForm = (DynaValidatorActionForm)session.getAttribute("ioForm") ;  
  dynaForm.set("workshopDt",sysDate);
  dynaForm.set("topic","CGTMSE Scheme");
%>

<body >
<html:form action="workshopDetails.do?method=workshopEntry" method="POST" enctype="multipart/form-data">
<html:errors />
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><br></td>
	</tr>
    <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/InwardOutwardHeading.gif" width="169" height="25"></td>
      <td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
    <tr> 
      <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
      <td>
      <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
	  <tr>
	  <TD>			
			<DIV align="right">	
			</DIV>
		</td>
	  </tr>
	<TR>
		<TD colspan="4"> 
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD width="31%" class="Heading">
					<bean:message key="schemepropagationDetails" /></TD>
					<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
				</TR>
				<TR>
					<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
				</TR>

			</TABLE>
		</TD>

	</TR>      
      
          <tr> 
            <td><table width="100%" border="0" cellspacing="1" cellpadding="1">
                <tr > 
                  <td colspan="4"></td>
                </tr>
              <tr>
					<td class="ColumnBackground" colspan="2"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
					<bean:message key="workshopDt"/>&nbsp;&nbsp;
					</div></td>
					<td class="tableData" align="center" colspan="2"><div align="left">
					<html:text property="workshopDt" size="20" alt="workshop Dt" name="ioForm" maxlength="10"/><img src="images/CalendarIcon.gif" width="20" align="center"
                  onClick="showCalendar('ioForm.workshopDt')">
					</div></td>
				</tr> 
        
        <tr>
					<td class="ColumnBackground" colspan="2"><div align="left">&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; 
					<bean:message key="typeofWorkshop"/>
					</div></td>
					<td class="tableData" colspan="2"><div align="left">
            <html:select property="type" name="ioForm" onchange="enableOfficerDetails()">
						<html:option value="">Select</html:option>
            <html:option value="Workshop">Workshop</html:option>
            <html:option value="Seminar">Seminar</html:option>
            <html:option value="Awareness Programme">Awareness Programme</html:option>
            <html:option value="Exhibition">Exhibition</html:option>
            <html:option value="Business Developement Meeting">Business Developement Meeting</html:option>
            <html:option value="GOI Meeting">GOI Meeting</html:option>
            <html:option value="RBI Meeting">RBI Meeting</html:option>
            <html:option value="SLBC">SLBC</html:option>
            <html:option value="SLIIC">SLIIC</html:option> 
            
            </html:select>						
					</div></td>
				</tr>
       
        <% 
         String dtofWorkshop = (String)dynaForm.get("workshopDt");
        // System.out.println("dtofWorkshop:"+dtofWorkshop);
        %>
        <TR align="left">       
          <TD align="left" valign="top" class="ColumnBackground" colspan="2">
						&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="mliNames" />
					</TD>
           <TD align="left" class="TableData" colspan="2">
						<html:select property="mliName" name="ioForm" onchange="enableAgencyNames()" >
							<html:option value="">Select</html:option>
							<html:option value="CGTMSE">CGTMSE</html:option>
              <html:option value="Agency on behalf of CGTMSE">Agency on behalf of CGTMSE</html:option>              
						  <html:option value="Government Organization">Government Organization</html:option>              
					  </html:select>
            
						<html:select property="agencyName" name="ioForm" disabled="true">
							<html:option value="">Select</html:option>
							<html:options property="agencyNames" name="ioForm"/>			
						</html:select>
					 
           <html:select property="governmentOrgs" name="ioForm" disabled="true">
							<html:option value="">Select</html:option>
							<html:options property="governmentOrgsList" name="ioForm"/>			
						</html:select>
            
          </TD> 
          
        
        <%--	<TD align="left" class="TableData" colspan="2">
						<html:select property="mliName" name="ioForm" >
							<html:option value="">Select</html:option>
							<html:options property="mliNames" name="ioForm"/>			
						</html:select>
					</TD> --%>
				</tr>
        
        <TR align="left">
					<TD align="left" valign="top" class="ColumnBackground" colspan="1">
						&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="organisedfor" />
					</TD>
					<TD align="left" class="TableData" colspan="1">
						<html:select property="organisedfor" name="ioForm" onchange="enableMLINames()">
							<html:option value="">Select</html:option>
							<html:option value="MLI">MLI</html:option>	
              <html:option value="NON - MLI">NON-MLI</html:option>
              <html:option value="SETTLORS">SETTLORS</html:option>
						</html:select>
					</TD>
          <TD align="left" class="TableData" colspan="2">
						<html:select property="bankNames" name="ioForm" disabled="true">
							<html:option value="">Select</html:option>
							<html:options property="mliNames" name="ioForm"/>			
						</html:select>
            <html:text property="sourceName"  size="50" name="ioForm" maxlength="100" disabled="true"/>
					</TD>
          
          
          <%-- <TD align="left" class="TableData" colspan="2">
						<html:select property="organisedfor" name="ioForm" >
							<html:option value="">Select</html:option>
							<html:options property="mliNames" name="ioForm"/>			
						</html:select>
					</TD> --%>
				</tr>
        
      <%--  <TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="agencyNames" />
					</TD>
					<TD align="left" class="TableData" colspan="2">
						<html:select property="agencyName" name="ioForm" >
							<html:option value="">Select</html:option>
							<html:options property="agencyNames" name="ioForm"/>			
						</html:select>
					</TD>
				</tr> --%>
        <tr>
              <td class="ColumnBackground" colspan="2"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; 
                  <bean:message key="zoneName"/>
                  </div></td>
                  <td class="tableData" colspan="2"><div align="left">
                    <html:text property="zone" name="ioForm" maxlength="100"/>
                  </div></td>
                </tr>  
                
                <tr>
                <td class="ColumnBackground" colspan="2"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; 
                  <bean:message key="MemberID"/>
                  </div></td>
                  <td class="tableData" colspan="2"><div align="left">
                    <html:text property="mliId" name="ioForm" maxlength="12"/>
                  </div></td>
                </tr>     
        
        <tr>
                  <td class="ColumnBackground" colspan="2"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; 
                  <bean:message key="targetGroup"/>
                  </div></td>
                  <td class="tableData" colspan="2"><div align="left">
            <html:select property="targetGroup" name="ioForm">
						<html:option value="">Select</html:option>
            <html:option value="Bankers">Bankers</html:option>
            <html:option value="Enterprenuers">Enterprenuers</html:option>
           <%-- <html:option value="Bankers & Enterprenuers">Bankers & Enterprenuers</html:option> --%>
            <html:option value="General">General</html:option>            
            
            </html:select>						
                    
                    
                  </div></td>
               </tr>       
      <%--  <tr>
					<td class="ColumnBackground"><div align="left">&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; 
					<bean:message key="place"/>
					</div></td>
					<td class="tableData" colspan="2"><div align="left">
						<html:text property="place" name="ioForm" maxlength="100"/>
					</div></td>
				</tr> --%>
        <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="2">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="statesList" />
									</TD>
									<TD align="left" class="TableData"> 
									<%--	<html:select property="stateName" name="ioForm" onchange="javascript:submitForm('workshopDetails.do?method=getDistricts')"> --%>
                  <html:select property="stateName" name="ioForm" onchange="javascript:retrieveSecondOptions()" styleId="stateName" styleClass="mandatory" > 
											<html:option value="">Select</html:option>
											<html:options property="statesList" name="ioForm"/>					</html:select>
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" colspan="2">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="districtList" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:select property="districtName" name="ioForm" styleId="districtName" styleClass="mandatory">
											<html:option value="">Select</html:option>
											<html:options property="districtList" name="ioForm"/>
                       <html:option value="Others">Others</html:option>
										</html:select>
                                       
									</TD>
								</TR>
                
                <tr>
                  <td class="ColumnBackground" colspan="2"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; 
                  <bean:message key="cityName"/>
                  </div></td>
                  <td class="tableData" colspan="2"><div align="left">
                    <html:text property="city" name="ioForm" maxlength="100"/>
                  </div></td>
                </tr>             
                
          
        
                
                <tr>
                  <td class="ColumnBackground" colspan="2"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; 
                  <bean:message key="topic"/>
                  </div></td>
                  <td class="tableData" colspan="2"><div align="left">
                    <html:text property="topic" name="ioForm" maxlength="100"/>
                  </div></td>
               </tr>
                
                <tr>
                  <td class="ColumnBackground" colspan="2"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; 
                  <bean:message key="participants"/>
                  </div></td>
                  <td class="tableData" colspan="2"><div align="left">
                    <html:text property="participants" name="ioForm" maxlength="6"  onkeypress="return numbersOnly(this, event)" disabled="true"/>
                  </div></td>
               </tr>
               
               
               
               <tr>
                  <td class="ColumnBackground" colspan="2"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; 
                  <bean:message key="name"/>
                  </div></td>
                  <td class="tableData" colspan="2"><div align="left">
                    <html:text property="name" name="ioForm" maxlength="100" disabled="true"/>
                  </div></td>
               </tr>
               
            <%--   <tr>
                  <td class="ColumnBackground" colspan="2"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; 
                  <bean:message key="designation"/>
                  </div></td>
                  <td class="tableData" colspan="2"><div align="left">
                    <html:text property="designation" name="ioForm" maxlength="100" disabled="true"/>
                  </div></td>
               </tr> --%>
               
                <tr>
                  <td class="ColumnBackground" colspan="2"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; 
                  <bean:message key="organisation"/>
                  </div></td>
                  <td class="tableData" colspan="2"><div align="left">
                    <html:text property="organisation" name="ioForm" maxlength="100" disabled="true"/>
                  </div></td>
               </tr>
               
               <tr>
                  <td class="ColumnBackground" colspan="2"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; 
                  <bean:message key="reasons"/>
                  </div></td>
                  <td class="tableData" colspan="2"><div align="left">
                    <html:textarea cols="70" rows="5" style="font-size:14;"  property="reasons" name="ioForm"></html:textarea>	
                  </div></td>
               </tr>
				
              </table></td>
          </tr>
          <tr > 
            <td height="20" >&nbsp;</td>
          </tr>
          <tr > 
            <td align="center" valign="baseline" > <div align="center"><html:link href="javascript:submitForm('insertWorkshopDetails.do?method=insertWorkshopDetails')"><img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></html:link> 
              </div></td>
          </tr>
        </table></td>
      <td width="20" background="images/TableVerticalRightBG.gif">&nbsp;</td>
    </tr>
    <tr> 
      <td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
      <td background="images/TableBackground2.gif">&nbsp;</td>
      <td width="20" align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
    </tr>
  </table>
  <br>
  <p>&nbsp;</p>
</html:form>
</body>