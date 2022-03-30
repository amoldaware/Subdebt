<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cgtsi.actionform.GMActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<%
	session.setAttribute("CurrentPage","showMemberListForNpaSubmit.do?method=showMemberListForNpaSubmit");	
%>
<% 
	String formType = (String)session.getAttribute("FORMNAME");	
	String tempname1="";
	String userRemark = "";
	SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
%>

<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form  action="npaSubmitSave.do?method=npaSubmitSave" method="POST" >
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>			
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="1" cellspacing="1">
					<TR>
						<TD colspan="12"> 
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="30%" class="Heading">Approval of Request for NPA Upgradation</TD>
									<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
								</TR>
								<TR>
									<TD colspan="12" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<tr><td colspan="8"><font color="Green" size="2">  </font></td></tr>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground" width="98">
							<bean:message key="sNo" />
						</TD>								
						<TD align="left" valign="top" class="ColumnBackground" width="98">
							<bean:message key="cgpan" />
						</TD>
						<TD align="left" valign="top" class="ColumnBackground" width="98">
							<bean:message key="unitNameExisting" />
						</TD>
						<TD align="left" valign="top" class="ColumnBackground" width="83">
							<bean:message key="shortNameMemId" />
						</TD>
						<TD align="left" valign="top" class="ColumnBackground" width="98">
							<bean:message key="memberId" />
						</TD>						
						<TD align="left" valign="top" class="ColumnBackground" width="114">
							Current Npa Date <!-- Npa Effective Date -->
						</TD>
						<TD align="left" valign="top" class="ColumnBackground" width="114">
							Reason Truning Npa 
						</TD>
						<TD align="left" valign="top" class="ColumnBackground" width="114">
							Npa Upgradation Date
						</TD>
						<%
							if(formType.equals("modification"))
							{
						%>
								<TD align="left" valign="top" class="ColumnBackground" width="114">
									New Npa Effective Date						
								</TD>
						<%
							}
						%>																			
						<TD align="left" valign="top" class="ColumnBackground" width="114">
							Upgradation Request Date <!-- Application Submit Date -->					
						</TD>				
						<TD align="left" valign="top" class="ColumnBackground" width="114">
							<!-- <bean:message key="remarks" />  -->
							Reason For Upgradation					
						</TD>
						<TD align="left" valign="top" class="ColumnBackground" width="114">
							Approve/Reject<br/>&nbsp;
							<html:checkbox property="selectAll" alt="cg" name="gmApproveForm" onclick="selectDeselect(this,1)"/>				
						</TD>
						<TD align="left" valign="top" class="ColumnBackground" width="114">
							MLI Comments
						</TD>
					</TR>
					<%
						String cgpan1 = "";
						String cgpan = "";
						String commentCgpan = "";
					%>
					<html:hidden property="cgpan" name="gmApproveForm"/>		
					<logic:iterate id="object" name="gmApproveForm" property="npaUpgraDetailReq" indexId="index">
						<%
							com.cgtsi.actionform.GMActionForm gmActionForm = (com.cgtsi.actionform.GMActionForm)object;
							cgpan1 = gmActionForm.getCgpan();
							//System.out.println("JSP cgpan1 : "+cgpan1);
							//System.out.println("JSP tempname1 : "+tempname1+ "closureCgpan("+cgpan+")");
							//System.out.println(tempname1="closureCgpan("+cgpan+")" );
							//System.out.println("JSP cgpan : "+gmActionForm.getCgpan());
						%>
						<TR>
							<td align="left" valign="top" class="ColumnBackground" width="98"><div align="center">&nbsp;<%= Integer.parseInt(index+"")+1%></div></td>
							<TD align="left" valign="top" class="ColumnBackground" width="98"><%tempname1="closureCgpan("+cgpan+")";%>
								<%=cgpan1%>
							</TD>		
							<TD align="left" valign="top" class="ColumnBackground" width="98"><%=gmActionForm.getUnitName()%></TD>
							<TD align="left" valign="top" class="ColumnBackground" width="98"><%=gmActionForm.getBankName()%></TD>
							<TD align="left" valign="top" class="ColumnBackground" width="83"><%=gmActionForm.getMemberId()%></TD>
							<TD align="left" valign="top" class="ColumnBackground" width="114">&nbsp;<%=gmActionForm.getNpaEffDt() %></TD>
							<TD align="left" valign="top" class="ColumnBackground" width="114">&nbsp;<%=gmActionForm.getNpaReason()%></TD>
							<TD align="left" valign="top" class="ColumnBackground" width="114">&nbsp;<%=gmActionForm.getNpaUpgraDt()%></TD>
							<%
								if(formType.equals("modification"))
								{
							%>
									<TD align="left" valign="top" class="ColumnBackground" width="114">&nbsp;<%=gmActionForm.getNewNpaEffDt()%></TD>
							<%
								}
							%>							
							<TD align="left" valign="top" class="ColumnBackground" width="114">&nbsp;<%=gmActionForm.getNpaLwrlevInDt()%></TD>
							<TD align="left" valign="top" class="ColumnBackground" width="114">&nbsp;
								<%
									userRemark = gmActionForm.getNpaUserRemark();
								%>
								<%=userRemark%>
							</TD>
							<TD align="left" valign="top" class="ColumnBackground" width="114">&nbsp;
								<%
									//tempname1="closureCgpan("+cgpan1+")";
									//System.out.println(tempname1="closureCgpan("+cgpan1+")" );
								%>
								<html:checkbox property="textarea" name="gmApproveForm" value="<%=cgpan1%>" />
							</TD>
							<TD align="left" valign="top" class="ColumnBackground" width="114">
								<%
									commentCgpan="commentCgpan("+cgpan1+")";
									//System.out.println("JAP commentCgpan : "+commentCgpan);
								%>
								<html:textarea property="<%=commentCgpan%>" name="gmApproveForm" styleId="<%=cgpan1%>"/>
							</TD>

	                                                
			
						</TR>
					</logic:iterate>
                                        <tr>
					<TD align="left" valign="top" class="ColumnBackground"  colspan="12" >								
								<html:checkbox property="declaration" name="gmApproveForm"  ></html:checkbox>Ticking the box will tentamount to confirmation of below mentioned conditions.</td></tr><tr><TD align="left" valign="top" class="ColumnBackground"  colspan="100" >


<font color="blue"> NOTE.IF YOU WANT TO REJECT NPA UPGRADATIONS PLEASE  TICK ON THE CHECK BOX, GIVE COMMENTS AND CLICK ON THE  CROSS BUTTON BESIDE SAVE BUTTON.</td></tr><tr>
                           <TD align="left" valign="top" class="ColumnBackground"  colspan="100" >

								<font color="#FF0000"> 1. It is Verified that Upgradation date(s) mentioned above are as per the records maintained by the bank.</td></tr><tr>
                           <TD align="left" valign="top" class="ColumnBackground"  colspan="100" ><font color="#FF0000">
								2. RBI guidlines have be dully followed while Upgrading the above NPA accounts.</font> 
							</TD>	
					</tr>
					<TR>
						<TD align="center" valign="baseline" colspan="10">
							<DIV align="center">
								<%
									String url = "npaSubmitSave.do?method=npaSubmitSave&formName="+formType;
								%>
								<%-- <A href="javascript:submitForm('<%=url%>') "> --%>
								<A href="#" onclick="UpdateCommentValid()">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0">
								</A>
								<%
									String delUrl = "npaDeleteRecord.do?method=npaDeleteRecord&formName="+formType;
								%>
								<A href="#" onclick="DeleteCommentValid()">
								<%-- <A href="javascript:submitForm('<%=delUrl%>') "> --%>
									<IMG src="images/Delete.gif" alt="Save" width="49" height="37" border="0">
								</A>  					
								<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
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

	function UpdateCommentValid()
	{
		//alert("UpdateCommentValid S :");`
		var x = document.getElementById("declaration").checked;
		//alert(x);
		if(x==true)
		{
		var check = false;
		var comment = false;
		document.gmApproveForm.action = "npaSubmitSave.do?method=npaSubmitSave&formName=Upgradation";
		//alert("Action : "+document.gmApproveForm.action);
		var checks = document.getElementsByName('textarea');
		//alert("Checks Length : "+checks.length);
		var count = 0; 
		for(var i=0; i<checks.length; i++)
		{
			if(checks[i].checked)
			{
				check = true;

				if(document.getElementById(checks[i].value) != null)
				{
					if(document.getElementById(checks[i].value).value=='')
					{
					
						comment = true;
					}
				}
			}			
		}

		
		if(check == false)
		{
			alert("Please select atleast one CGPAN for Approval.");
		}

		if(comment==true)
		{
			alert('Comment is Required');
		}

		if(check == true && comment == false)
		{
			document.gmApproveForm.submit();
		}
		//alert("UpdateCommentValid E");
		}
		else
		{
			alert('please select  the Check box');
		}
	}
	/////////////////////////////////////////////

	function DeleteCommentValid()
	{
		//alert("DeleteCommentValid S :");
		var check = false;
		var comment = false;
		document.gmApproveForm.action = "npaDeleteRecord.do?method=npaDeleteRecord&formName=Upgradation";			
		//alert("Action : "+document.gmApproveForm.action);
		var checks = document.getElementsByName('textarea');
		//alert("Checks Length : "+checks.length);			
		for(var i=0; i<checks.length; i++)
		{
			if(checks[i].checked)
			{
				check = true;	
				if(document.getElementById(checks[i].value) != null)
				{
					if(document.getElementById(checks[i].value).value=='')
					//if(document.getElementById(checks[i].value).trim().length == 0)
					{
						alert('Comment is Required');
						comment = true;
					}
				}
			}			
		}
		if(check == false)
		{
			alert("Please select atleast one CGPAN for Delete.");
		}		
		if(check == true && comment == false)
		{
			document.gmApproveForm.submit();
		}
		//alert("DeleteCommentValid E :");
	}	
</script>