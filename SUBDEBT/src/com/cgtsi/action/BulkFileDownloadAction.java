package com.cgtsi.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cgtsi.actionform.BulkFileDownloadForm;
import com.cgtsi.admin.User;
import com.cgtsi.util.DBConnection;


public class BulkFileDownloadAction extends BaseAction {
	public ActionForward bulkFileDownload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			System.out.println("getBulkFileDownload method called as part of BulkFileDownload class");
			
			Connection conn = null;

			HttpSession session = request.getSession();
			
			User user = getUserInformation(request);
			String userId = user.getUserId();
			System.out.println("userId>>>>>hoUserCumlativeReportSubmit()>>>>>>"+userId);
			String bankId = user.getBankId();
			String zoneId = user.getZoneId();
			String branchId = user.getBranchId();
			String memberId=bankId+zoneId+branchId;
			System.out.println("Member id===65==="+memberId);
					
			ResultSet rs = null;
			PreparedStatement pst = null;
			int status = -1;
			String errorCode = null;
			ArrayList fileArray = new ArrayList();
						
			BulkFileDownloadForm objFileDown1 = (BulkFileDownloadForm) form;
			//DynaActionForm objFileDown1 = (DynaActionForm)form;
			BulkFileDownloadForm objFileDown=null;
			CallableStatement callableStmt = null;
			//String Query = (new StringBuilder("SELECT bu_id, member_id, upload_id, process_name, uploaded_by, uploaded_date, STATUS FROM egsuat.egs_bulk_upload_file_hdr").toString());
			//String Query = (new StringBuilder("SELECT bu_id, member_id,B.MEM_BANK_NAME, upload_id, process_name, uploaded_by, uploaded_date,CASE WHEN STATUS='P' THEN 'PENDING' WHEN STATUS='C' THEN 'COMPLETE' ELSE 'IN PROCESS' END AS STATUS FROM egsuat.egs_bulk_upload_file_hdr A, member_info B where A.member_id=concat(B.MEM_BNK_ID,B.MEM_ZNE_ID,B.MEM_BRN_ID) AND member_id=?").toString());
			
//			
//			pst = connection.prepareStatement(Query);
//			pst.setString(1, memberId);
			
			
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{call FUNC_GET_BULKUPLOAD_COUNT(?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.INTEGER);
			callableStmt.setString(2,userId );
			callableStmt.setString(3, memberId);
			callableStmt.registerOutParameter(4, Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(4);
			
			
			rs = callableStmt.executeQuery();
			while (rs.next()) {

				objFileDown = new BulkFileDownloadForm();
				
				objFileDown.setBu_id(rs.getString("bu_id"));
				objFileDown.setMember_id(rs.getString("member_id"));
				objFileDown.setUpload_id(rs.getString("upload_id"));
				objFileDown.setProcess_name(rs.getString("process_name"));

				objFileDown.setUploaded_by(rs.getString("uploaded_by"));
				objFileDown.setUploaded_date(rs.getString("uploaded_date"));
				objFileDown.setStatus(rs.getString("STATUS"));
				objFileDown.setMEM_BANK_NAME(rs.getString("MEM_BANK_NAME"));
				objFileDown.setFAILURE_COUNT(rs.getInt("FAILURE_COUNT"));
				objFileDown.setTOTAL_RECORED_IN_FILE(rs.getInt("TOTAL_RECORED_IN_FILE"));
				objFileDown.setSUCCESS_COUNT(rs.getInt("SUCCESS_COUNT"));
				
			
				

				fileArray.add(objFileDown);
			}
			objFileDown1.setBulkFileList(fileArray);
			
			
			return mapping.findForward("bulkFileDownloadReport");
			
	}
	
}
