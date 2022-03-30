<%@page import="java.util.Iterator"%>
<%@page import="com.cgtsi.claim.ClaimDetail"%>
<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.actionform.ClaimActionForm"%>
<%
	DecimalFormat decimalFormat = new DecimalFormat("##########.##");
%>
<%
	

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	String claimDate;

	String iseligcom = "";

	String Isallrecinclmformcomm1 = "";
	String Isinternratinvestgradcomm1 = "";
	String Whetdeficinvolbystaffcomm1 = "";
	String Whetmajordeficinvolvdcomm1 = "";
	String Isrataspercgscomm1 = "";
	String Isthirdcollattakencomm1 = "";
	String Isnpadtasperguidcomm1 = "";

	String Isclmoswrtnpadtcomm1 = "";
	String Whetcibildonecomm1 = "";

	String Whetseriousdeficinvolcomm1 = "";
%>
  
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">

<html:errors />
	
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><img  width="131" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR> 
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="16"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
                        
                      </tr>
                      <tr>
                        <td colspan="6">&nbsp;</td>
                      </tr>
                      
                     	<TR>
    <%
    	ArrayList arraylist = null;
    	String AsfStringArray[] = null;
    	String size1 = (String) request.getAttribute("accountdetailviewSize");
    	System.out.println("size1===" + size1);

    	if (size1.equals("0")) {
    		out.println("<tr><td class=\"Heading\" colspan=\"11\"><center>No Data Found</center</td></tr>");
    	}
    	if (size1 != null && size1 != "0") {
    		arraylist = (ArrayList) request.getAttribute("accountdetailview");
    %> 
                           <tr>
                      <td colspan="4">
                       <table width="100%" border="0" cellspacing="1" cellpadding="0">
                        <tr class="ColumnBackground">
                     
                       <td class="ColumnBackground">
						<div align="center">Sr.No</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">QUESTION</div>
						</td>
						
						
						 
                      							
						</TR>     
   <%
        	ClaimDetail claimDetailsss = new ClaimDetail();
        		for (int count = 0; count < arraylist.size(); count++) {

        			int sum = 0;

        			AsfStringArray = new String[3];
        			AsfStringArray[0] = "";
        			AsfStringArray[1] = "";
        			
        			claimDetailsss = (ClaimDetail) arraylist.get(count);
        			// System.out.println("raaaassskkkk"+claimDetailsss.getCgpan());
        %>
                  <tr>
                  
                  
                     <td class="ColumnBackground1" >&nbsp;
                    
                      <%=claimDetailsss.getCgpan()%>
                     
                      </td>  
                    
                   <td class="ColumnBackground1" >&nbsp;
                  <%
                    String ClmreferenceNumber =  claimDetailsss.getMliid();
                    String url = "sendQueryRequestAnswer.do?method=sendQueryRequestAnswer&clmRefNumber="+ClmreferenceNumber;%>
         	      <html:link href="<%=url%>"><%=ClmreferenceNumber%></html:link>                
                 
                  </TD>
                    
                    
                  
                  
                  <%
                                                      	}
                                                      %>
                  
           <%
                             	}
                             %>
          
          </TR>
        </TABLE>
                                                        
           </td>
           </tr>
           </TR>
           </TABLE>      
				
					<TR>
						<TD align="center" valign="baseline" >
							<DIV align="center">
						
								<A href="javascript:history.back()">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
									
									<A href="javascript:printpage()">
									<IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A>
								
							</DIV>
						</TD>
					</TR>
				</TABLE>
			 </table> 
			</TD>
			<TD width="20" background="images/TableVerticalRightBG.gif">
				&nbsp;
			</TD>
		</TR>
		<TR>
			<TD width="20" align="right" valign="top">
				<IMG src="images/TableLeftBottom1.gif" width="20" height="15">
			</TD>
			<TD background="images/TableBackground2.gif">
				&nbsp;
			</TD>
			<TD width="20" align="left" valign="top">
				<IMG src="images/TableRightBottom1.gif" width="23" height="15">
			</TD>
		</TR>
    </TABLE>