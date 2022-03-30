<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<script src="/csrfguard"></script>

 
 
 
 
 
  <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
 <html:form action="downLoadDataInExcel.do?method=downLoadDataInExcel"
		method="POST" enctype="multipart/form-data">
	
	  
	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
	   <TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
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
									<TD colspan="12">  
            
	
			
	         <tr>
		   		 <td valign="top" class="HeadingBg" align="center">BATCH ID</td>
		   		 <td valign="top" class="HeadingBg" align="center">MEMBER ID</td>
		   		 <td valign="top" class="HeadingBg" align="center">BANK NAME</td>
		   		 <td valign="top" class="HeadingBg" align="center">UPLOAD ID</td>
		   		 <td valign="top" class="HeadingBg" align="center">PROCESS NAME</td>
		   		 <td valign="top" class="HeadingBg" align="center">UPLOADED BY</td>
		   		 <td width="20%" valign="top" class="HeadingBg" align="center">UPLOADED DATE</td>
		   		 <td valign="top" class="HeadingBg" align="center">STATUS</td>
		   		 <td width="20%" valign="top" class="HeadingBg" align="center">FALIURE COUNT</td>
		   		 <td width="20%" valign="top" class="HeadingBg" align="center">SUCCESS COUNT</td>
		   		 <td width="20%" valign="top" class="HeadingBg" align="center">TOTAL RECORD IN FILE</td>
		   		 <td valign="top" class="HeadingBg" align="center">DOWNLOAD</td>
		   	 </tr>
            	
            <logic:iterate name="bulkFileDownloadForm"  property="bulkFileList" id="object"  >
            	<TR>
           			  <%
	                      com.cgtsi.actionform.BulkFileDownloadForm dataList =  ( com.cgtsi.actionform.BulkFileDownloadForm)object;
					      String bu_id=dataList.getBu_id();
					      String bank_name= dataList.getMEM_BANK_NAME();
					      String member_id=dataList.getMember_id();
					      String upload_id=dataList.getUpload_id();
					      String process_name=dataList.getProcess_name();
					      String uploaded_by=dataList.getUploaded_by();
					      String uploaded_date=dataList.getUploaded_date();
					      String status=dataList.getStatus();
					      int FAILURE_COUNT = dataList.getFAILURE_COUNT();
					      int SUCCESS_COUNT = dataList.getSUCCESS_COUNT();
					      int TOTAL_RECORED_IN_FILE = dataList.getTOTAL_RECORED_IN_FILE();
						%>
		             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=bu_id %></TD>		
		             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=member_id %></TD>
		             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=bank_name %></TD>
		         	 <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=upload_id %></TD>
		         	 <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=process_name %></TD>
		         	 <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=uploaded_by %></TD>
		         	 <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=uploaded_date %></TD>
		         	 <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=status %></TD>
		         	 <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=FAILURE_COUNT %></TD>
		         	 <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=SUCCESS_COUNT %></TD>
		         	 <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=TOTAL_RECORED_IN_FILE %></TD>
	         	 
	         	 	<td>
			         	
			         	
			   <%--  <a href="javascript:submitForm('downLoadDataInExcel.do?method=downLoadDataInExcel&&fileType=CSVType&FlowLevel=ClmSettledExcelReport&&Flag=A&&upload_id=<%=upload_id %>')">Download</a> --%>
			    <a href="javascript:submitForm('downLoadDataInExcel.do?method=downLoadDataInExcel&&fileType=CSVType&FlowLevel=ClmSettledExcelReport&&Flag=A&uploadId=<%=upload_id %>&procesName=<%=process_name %>')">Download</a>
	         	 	</td>
	         	  </tr>
         	 </logic:iterate>
         	        
 </TD>
                
            </TR>
		 </TABLE>	
		     
		       <tr align="center" valign="baseline">
           		 <td colspan="4"> 
           		 <div align="center"> 
		         <a href="javascript:submitForm('applicationStatus.do?')">
		        <IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
		        
		         <A href="javascript:printpage()">
									<IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A>
                
                
                 
		      </div>
		      </td>
		     </tr>
		  	</table>     
		

			<TD width="20" background="images/TableVerticalRightBG.gif">
				&nbsp;
			</TD>
	     </tr>
		
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

  </html:form>
</table>

</body>
</html>
 


	</TABLE> 
	
	
	
 


