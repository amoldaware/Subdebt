package com.cgtsi.action;

import com.cgtsi.actionform.AdministrationActionForm;
import com.cgtsi.admin.AdminDAO;
import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.AlertMaster;
import com.cgtsi.admin.BroadCastMessage;
import com.cgtsi.admin.DesignationMaster;
import com.cgtsi.admin.DistrictMaster;
import com.cgtsi.admin.ExceptionMaster;
import com.cgtsi.admin.Hint;
import com.cgtsi.admin.InvalidDataException;
import com.cgtsi.admin.MenuOptions;
import com.cgtsi.admin.Message;
import com.cgtsi.admin.PLRMaster;
import com.cgtsi.admin.ParameterMaster;
import com.cgtsi.admin.PasswordManager;
import com.cgtsi.admin.Privileges;
import com.cgtsi.admin.RegionMaster;
import com.cgtsi.admin.Role;
import com.cgtsi.admin.ScheduledProcessManager;
import com.cgtsi.admin.StateMaster;
import com.cgtsi.admin.User;
import com.cgtsi.admin.ZoneMaster;
import com.cgtsi.application.ExcelModuleMethods;
import com.cgtsi.application.LogClass;
import com.cgtsi.claim.ClaimsProcessor;
import com.cgtsi.common.AuditDetails;
import com.cgtsi.common.CommonDAO;
import com.cgtsi.common.CommonUtility;
import com.cgtsi.common.FileUploader;
import com.cgtsi.common.InactiveUserException;
import com.cgtsi.common.Log;
import com.cgtsi.common.MailerException;
import com.cgtsi.common.NoDataException;
import com.cgtsi.common.NoUserFoundException;
import com.cgtsi.common.UploadFailedException;
import com.cgtsi.receiptspayments.RpProcessor;
import com.cgtsi.registration.CollectingBank;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.NoMemberFoundException;
import com.cgtsi.registration.Registration;
import com.cgtsi.util.CSRFUtil;
import com.cgtsi.util.CustomisedDate;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;



import com.cgtsi.common.DatabaseException;

import com.cgtsi.common.MessageException;

import com.cgtsi.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import sun.misc.BASE64Decoder;

@SuppressWarnings("unused")
public class AdministrationAction extends BaseAction
{
	
	ExcelModuleMethods objExcelModuleMethods= new ExcelModuleMethods();
	
  public ActionForward upload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    HttpSession session = request.getSession(false);

    FileUploading fileUpload = (FileUploading)session.getAttribute("fileUploading");
    fileUpload.setRequest(request);

    Integer uploadStatus = (Integer)session.getAttribute("FILE_UPLOAD_STATUS");

    Log.log(4, "AdministrationAction", "upload", "EnteredFILE_UPLOAD_STATUS");

    Log.log(5, "AdministrationAction", "upload", "uploadStatus " + uploadStatus);
        if (uploadStatus != null)
    {
      if (uploadStatus.intValue() == 0)
      {
        Log.log(4, "AdministrationAction", "upload", "Exited..success");

        return mapping.findForward("success");
      }
      if (uploadStatus.intValue() == 1)
      {
        Log.log(4, "AdministrationAction", "upload", "Exited..upload error");

        throw new UploadFailedException("Upload Failed....");
      }

      Log.log(4, "AdministrationAction", "upload", "Exited..failed");

      return mapping.findForward("failed");
    }

    Log.log(4, "AdministrationAction", "upload", "Exited..failed");

    return mapping.findForward("failed");
  }

  public ActionForward showAddRole(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showAddRole", "Entered");

    Administrator admin = new Administrator();

    ArrayList privileges = admin.getAllPrivileges();

    if (privileges != null)
    {
      Log.log(5, "AdministrationAction", "showAddRole", "privileges size is: " + privileges.size());
    }

    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);

    Log.log(4, "AdministrationAction", "showAddRole", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showModifyRole(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showModifyRole", "Entered");
    Administrator admin = new Administrator();
    ArrayList roles = admin.getAllRoles();
    ArrayList roleNames = new ArrayList();
    if (roles != null)
    {
      Log.log(5, "AdministrationAction", "showModifyRole", "roles size is: " + roles.size());

      for (int i = 0; i < roles.size(); i++)
      {
        Role role = (Role)roles.get(i);

        if (role != null)
        {
          String roleName = role.getRoleName();
          if ((!roleName.equalsIgnoreCase("DEMOUSER")) && (!roleName.equalsIgnoreCase("AUDITOR")))
          {
            roleNames.add(roleName);
          }
        }
      }
    }

    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);

    HttpSession session = request.getSession(false);
    session.setAttribute("ROLE_NAMES", roleNames);

    roles = null;
    roleNames = null;

    Log.log(4, "AdministrationAction", "showModifyRole", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getPrivilegesForRole(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "getPrivilegesForRole", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    Administrator admin = new Administrator();

    ArrayList roles = admin.getAllRoles();
    ArrayList roleNames = new ArrayList();

    String roleDescription = "";
    String roleName = (String)dynaForm.get("roleName");

    Log.log(5, "AdministrationAction", "getPrivilegesForRole", " role name is  " + roleName);

    if (roles != null)
    {
      Log.log(5, "AdministrationAction", "getPrivilegesForRole", "roles size is: " + roles.size());

      for (int i = 0; i < roles.size(); i++)
      {
        Role role = (Role)roles.get(i);

        if (role != null)
        {
          roleNames.add(role.getRoleName());

          Log.log(5, "AdministrationAction", "getPrivilegesForRole", " role name...  " + role.getRoleName());

          if (roleName.equals(role.getRoleName()))
          {
            roleDescription = role.getRoleDescription();

            Log.log(5, "AdministrationAction", "getPrivilegesForRole", " role desc is  " + roleDescription);
          }
        }
      }
    }

    ArrayList privileges = admin.getPrivilegesForRole(roleName);

    dynaForm.initialize(mapping);

    dynaForm.set("roleNames", roleNames);
    dynaForm.set("roleName", roleName);
    dynaForm.set("roleDescription", roleDescription);

    if (privileges != null)
    {
      for (int i = 0; i < privileges.size(); i++)
      {
        String privilege = (String)privileges.get(i);

        Log.log(5, "AdministrationAction", "getPrivilegesForRole", " privilege is  " + privilege);

        dynaForm.set(privilege, privilege);
      }
    }

    roles = null;
    roleNames = null;
    privileges = null;

    Log.log(4, "AdministrationAction", "getPrivilegesForRole", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward displayPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "displayPassword", "Entered");
    String password = "Password Will be displayed here.";

    request.setAttribute("displayPassword", password);
    Log.log(4, "AdministrationAction", "displayPassword", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showCGTSIUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showCGTSIUser", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    Administrator admin = new Administrator();

    ArrayList designation = admin.getAllDesignations();
    dynaForm.set("designations", designation);

    designation = null;

    Log.log(4, "AdministrationAction", "showCGTSIUser", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addCGTSIUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "addCGTSIUser", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    Administrator admin = new Administrator();
    User user = new User();

    BeanUtils.populate(user, dynaForm.getMap());

    user.setBankId("0000");
    user.setZoneId("0000");
    user.setBranchId("0000");

    User creatingUser = getUserInformation(request);
    String createdBy = creatingUser.getUserId();

    String memberId = "000000000000";
    String userId = null;
    try
    {
      userId = admin.createUser(createdBy, user, true);
      request.setAttribute("message", "MemberId is " + memberId + "  UserId is " + userId);
    }
    catch (MailerException mailerException)
    {
      String errorMessage = mailerException.getMessage();
      request.setAttribute("message", errorMessage + "  MemberId is  " + memberId);
    }

    user = null;
    creatingUser = null;

    Log.log(4, "AdministrationAction", "addCGTSIUser", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showMLIUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showMLIUser", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    Administrator admin = new Administrator();

    ArrayList designation = admin.getAllDesignations();
    dynaForm.set("designations", designation);

    designation = null;

    Log.log(4, "AdministrationAction", "showMLIUser", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addMLIUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
	  
	  DynaActionForm dynaForm = (DynaActionForm)form;
	    Administrator admin = new Administrator();
	    User user = new User();

	    BeanUtils.populate(user, dynaForm.getMap());

	    String bankId = (String)dynaForm.get("bankId");
	    String zoneId = (String)dynaForm.get("zoneId");
	    String branchId = (String)dynaForm.get("branchId");
	    String memberId = bankId + zoneId + branchId;

	    Registration registration = new Registration();
	    registration.getMemberDetails(bankId, zoneId, branchId);

	    User creatingUser = getUserInformation(request);
	    String createdBy = creatingUser.getUserId();

	    String loggedInUserBankId = creatingUser.getBankId();
	    String loggedInUserZoneId = creatingUser.getZoneId();
	    String loggedInUserBranchId = creatingUser.getBranchId();

	    if ((loggedInUserBankId.equals("0000")) || (loggedInUserBankId.equals(bankId))||
	    		((loggedInUserZoneId.equals("0000"))||(loggedInUserZoneId.equals(zoneId))
	    		&&(loggedInUserBranchId.equals("0000"))||(loggedInUserBranchId.equals(branchId))))
	    {
	      String userId = null;
	      try {
	        userId = admin.createUser(createdBy, user, true);
	        request.setAttribute("message", "MemberId is " + memberId + "  UserId is " + userId);
	      }
	      catch (MailerException mailerException)
	      {
	        String errorMessage = mailerException.getMessage();
	        request.setAttribute("message", errorMessage + "  MemberId is  " + memberId);
	      }

	    }
	    else
	    {
	      request.setAttribute("message", "Cannot create users for other MLI");
	    }

	    user = null;
	    creatingUser = null;

	    Log.log(4, "AdministrationAction", "addMLIUser", "Exited");
	  
	  /*String target1="";
	  Log.log(4, "AdministrationAction", "addMLIUser", "Entered");
	  String csrfCode = request.getParameter("csrfCode");
	  //System.out.println("csrfCode :::::  " + csrfCode);

	  User creatingUser=null;
	  User user=null;
	  DynaActionForm dynaForm = (DynaActionForm)form;
	  Administrator admin = new Administrator();
	  user = new User();

	  boolean check=CSRFUtil.getCSRFflag(csrfCode, request.getSession(false), request);
	  //System.out.println("check=="+check);
	  if(csrfCode != null && !"".equals(csrfCode) && check){
		  BeanUtils.populate(user, dynaForm.getMap());
		  String bankId = (String)dynaForm.get("bankId");
		  String zoneId = (String)dynaForm.get("zoneId");
		  String branchId = (String)dynaForm.get("branchId");
		  String memberId = bankId + zoneId + branchId;
		  
		  Registration registration = new Registration();
		  registration.getMemberDetails(bankId, zoneId, branchId);

		  creatingUser = getUserInformation(request);
		  String createdBy = creatingUser.getUserId();

		  String loggedInUserBankId = creatingUser.getBankId();

		  if ((loggedInUserBankId.equals("0000")) || (loggedInUserBankId.equals(bankId))){
		    String userId = null;
		    try {
		      userId = admin.createUser(createdBy, user, true);
		      request.setAttribute("message", "MemberId is " + memberId + "  UserId is " + userId);
		    }
		    catch (MailerException mailerException)
		    {
		      String errorMessage = mailerException.getMessage();
		      request.setAttribute("message", errorMessage + "  MemberId is  " + memberId);
		    }

		  }
		  target1 = "success";
	  
	  }else{
	  		request.setAttribute("message", "Cannot create users for other MLI");
	  		target1 = "success";
	  }
	  
	  user = null;
	  creatingUser = null;

	  Log.log(4, "AdministrationAction", "addMLIUser", "Exited");
	  */

	  return mapping.findForward("success");
	}

  public ActionForward showModifyUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showmodifyUser", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);

    User loggedUser = getUserInformation(request);
    String userBankId = loggedUser.getBankId();
    String userZoneId = loggedUser.getZoneId();
    String userBranchId = loggedUser.getBranchId();
    String userMemberId = userBankId + userZoneId + userBranchId;

    Registration registration = new Registration();
    ArrayList memberIds = new ArrayList();
    ArrayList zones = null;
    ArrayList branches = null;
    ArrayList allMembers = null;
    MLIInfo mliInfo = null;

    memberIds.add(userMemberId);

    if ((userBankId.equals("0000")) && (userZoneId.equals("0000")) && (userBranchId.equals("0000")))
    {
      allMembers = registration.getAllMembers();
      int memberSize = allMembers.size();
      for (int i = 0; i < memberSize; i++) {
        mliInfo = (MLIInfo)allMembers.get(i);
        String bankId = mliInfo.getBankId();
        String zoneId = mliInfo.getZoneId();
        String branchId = mliInfo.getBranchId();
        String memberId = bankId + zoneId + branchId;
        memberIds.add(memberId);
      }

    }
    else if ((!userBankId.equals("0000")) && (userZoneId.equals("0000")) && (userBranchId.equals("0000")))
    {
      zones = registration.getZones(userBankId);
      int zoneSize = zones.size();
      for (int i = 0; i < zoneSize; i++) {
        mliInfo = (MLIInfo)zones.get(i);
        String zoneId = mliInfo.getZoneId();
        String branchId = mliInfo.getBranchId();
        String memberId = userBankId + zoneId + branchId;
        memberIds.add(memberId);
      }

      branches = registration.getAllBranches(userBankId);
      int branchSize = branches.size();
      for (int i = 0; i < branchSize; i++) {
        mliInfo = (MLIInfo)branches.get(i);
        String branchId = mliInfo.getBranchId();
        String zoneId = mliInfo.getZoneId();
        String memberId = userBankId + zoneId + branchId;
        memberIds.add(memberId);
      }
    }
    else if ((!userBankId.equals("0000")) && (!userZoneId.equals("0000")) && (userBranchId.equals("0000")))
    {
      branches = registration.getAllBranches(userBankId);
      int branchSize = branches.size();
      for (int i = 0; i < branchSize; i++) {
        mliInfo = (MLIInfo)branches.get(i);
        String branchId = mliInfo.getBranchId();
        String zoneId = mliInfo.getZoneId();

        if (zoneId.equals(userZoneId)) {
          String members = userBankId + zoneId + branchId;
          memberIds.add(members);
        }
      }

    }

    dynaForm.set("memberIds", memberIds);

    memberIds = null;
    zones = null;
    branches = null;
    allMembers = null;
    mliInfo = null;

    Log.log(4, "AdministrationAction", "showmodifyUser", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getUsersForMember(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "getUsersForMember", "Entered");

    Administrator admin = new Administrator();
    DynaActionForm dynaForm = (DynaActionForm)form;

    String memberId = (String)dynaForm.get("memberId");

    ArrayList userIds = admin.getUsers(memberId);

    User loggedUser = getUserInformation(request);
    String loggedUserId = loggedUser.getUserId();
    userIds.remove(loggedUserId);
    userIds.remove("ADMIN");
    if (userIds.contains("DEMOUSER"))
    {
      userIds.remove("DEMOUSER");
    }
    if (userIds.contains("AUDITOR"))
    {
      userIds.remove("AUDITOR");
    }

    HttpSession session = request.getSession(false);
    session.setAttribute("MemberSelected", "Y");

    dynaForm.set("activeUsers", userIds);

    userIds = null;

    Log.log(4, "AdministrationAction", "getUsersForMember", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getInactiveUsersForMember(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "getInactiveUsersForMember", "Entered");

    Administrator admin = new Administrator();
    DynaActionForm dynaForm = (DynaActionForm)form;

    String memberId = (String)dynaForm.get("memberId");

    ArrayList userIds = admin.getDeactivatedUsers(memberId);
    HttpSession session = request.getSession(false);
    session.setAttribute("MemberSelected", "Y");
    dynaForm.set("deactiveUsers", userIds);

    userIds = null;

    Log.log(4, "AdministrationAction", "getInactiveUsersForMember", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward modifyUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "modifyUser", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    Administrator admin = new Administrator();

    ArrayList designation = admin.getAllDesignations();
    dynaForm.set("designations", designation);

    String userId = (String)dynaForm.get("userId");
    User selectedUser = admin.getUserInfo(userId);
    BeanUtils.copyProperties(dynaForm, selectedUser);

    selectedUser = null;

    Log.log(4, "AdministrationAction", "modifyUser", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward afterModifyUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
	    Log.log(4, "AdministrationAction", "afterModifyUser", "Entered");
	    DynaActionForm dynaActionForm = (DynaActionForm)form;
	    Administrator admin =null;
	    User user=null;
	    User creatingUser=null;
	    String csrfCode = request.getParameter("csrfCode");
	    //System.out.println("csrfCode :::::  " + csrfCode);
	    String target="";
	    boolean check=CSRFUtil.getCSRFflag(csrfCode, request.getSession(false), request);
	    //System.out.println("check=="+check);
	    if(csrfCode != null && !"".equals(csrfCode) && check){
	    	admin = new Administrator();
		    user = new User();
		    BeanUtils.populate(user, dynaActionForm.getMap());
		    creatingUser = getUserInformation(request);
		    String createdBy = creatingUser.getUserId();
		    admin.modifyUser(user, createdBy);
		    request.setAttribute("dMessage", "User details modified");
		    Log.log(4, "AdministrationAction", "afterModifyUser", "Exited");
	    	target = "success";
	    }else{
	    	request.setAttribute("dMessage", "User details unable to update.There may some issue.");
	        target = "success";
	    }
	    
	    user = null;
	    creatingUser = null;
	    return mapping.findForward(target);
	  }


  public ActionForward showDeactivateUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showDeactivateUser", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);

    User loggedUser = getUserInformation(request);
    String userBankId = loggedUser.getBankId();
    String userZoneId = loggedUser.getZoneId();
    String userBranchId = loggedUser.getBranchId();
    String userMemberId = userBankId + userZoneId + userBranchId;

    Registration registration = new Registration();
    ArrayList memberIds = new ArrayList();
    ArrayList zones = null;
    ArrayList branches = null;
    ArrayList allMembers = null;
    MLIInfo mliInfo = null;

    memberIds.add(userMemberId);

    if ((userBankId.equals("0000")) && (userZoneId.equals("0000")) && (userBranchId.equals("0000")))
    {
      allMembers = registration.getAllMembers();
      int memberSize = allMembers.size();
      for (int i = 0; i < memberSize; i++) {
        mliInfo = (MLIInfo)allMembers.get(i);
        String bankId = mliInfo.getBankId();
        String zoneId = mliInfo.getZoneId();
        String branchId = mliInfo.getBranchId();
        String memberId = bankId + zoneId + branchId;
        memberIds.add(memberId);
      }

    }
    else if ((!userBankId.equals("0000")) && (userZoneId.equals("0000")) && (userBranchId.equals("0000")))
    {
      zones = registration.getZones(userBankId);
      int zoneSize = zones.size();
      for (int i = 0; i < zoneSize; i++) {
        mliInfo = (MLIInfo)zones.get(i);
        String zoneId = mliInfo.getZoneId();
        String branchId = mliInfo.getBranchId();
        String memberId = userBankId + zoneId + branchId;
        memberIds.add(memberId);
      }

      branches = registration.getAllBranches(userBankId);
      int branchSize = branches.size();
      for (int i = 0; i < branchSize; i++) {
        mliInfo = (MLIInfo)branches.get(i);
        String branchId = mliInfo.getBranchId();
        String zoneId = mliInfo.getZoneId();
        String memberId = userBankId + zoneId + branchId;
        memberIds.add(memberId);
      }
    }
    else if ((!userBankId.equals("0000")) && (!userZoneId.equals("0000")) && (userBranchId.equals("0000")))
    {
      branches = registration.getAllBranches(userBankId);
      int branchSize = branches.size();
      for (int i = 0; i < branchSize; i++) {
        mliInfo = (MLIInfo)branches.get(i);
        String branchId = mliInfo.getBranchId();
        String zoneId = mliInfo.getZoneId();

        if (zoneId.equals(userZoneId)) {
          String members = userBankId + zoneId + branchId;
          memberIds.add(members);
        }
      }

    }

    Administrator admin = new Administrator();
    ArrayList filteredMemIds = new ArrayList();
    for (int i = 0; i < memberIds.size(); i++)
    {
      String memId = (String)memberIds.get(i);
      if (memId != null)
      {
        String bankId = memId.substring(0, 4);
        String zoneId = memId.substring(4, 8);
        String branchId = memId.substring(8, 12);

        int count = admin.getUsersCount(bankId, zoneId, branchId, "A");
        if (count == 0)
        {
          Log.log(4, "AdministrationAction", "showDeactivateUser", "Leaving Member Id :" + memId);
        }
        else
        {
          Log.log(4, "AdministrationAction", "showDeactivateUser", "Adding Member Id :" + memId);
          filteredMemIds.add(memId);
        }
      }
    }
    dynaForm.set("memberIds", filteredMemIds);

    memberIds = null;
    zones = null;
    branches = null;
    allMembers = null;
    mliInfo = null;

    Log.log(4, "AdministrationAction", "showDeactivateUser", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward deactivateUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "deactivateUser", "Entered");

    DynaActionForm dynaActionForm = (DynaActionForm)form;
    Administrator admin = new Administrator();
    String userId = (String)dynaActionForm.get("userId");
    String reason = (String)dynaActionForm.get("reason");

    User creatingUser = getUserInformation(request);
    String deactivatedBy = creatingUser.getUserId();

    admin.deactivateUser(userId, reason, deactivatedBy);

    creatingUser = null;

    request.setAttribute("message", "User Deactivated");

    Log.log(4, "AdministrationAction", "deactivateUser", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showAssignRoles(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showAssignRoles", "Entered");

    AdministrationActionForm adminActionForm = (AdministrationActionForm)form;

    Log.log(4, "AdministrationAction", "showAssignRoles", "user Id " + adminActionForm.getUserId());

    Administrator admin = new Administrator();

    ArrayList roles = admin.getAllRoles();
    ArrayList roleNames = new ArrayList();

    for (int i = 0; i < roles.size(); i++)
    {
      Role role = (Role)roles.get(i);
      if (role != null)
      {
        String roleName = role.getRoleName();
        if ((!roleName.equalsIgnoreCase("DEMOUSER")) && (!roleName.equalsIgnoreCase("AUDITOR")))
        {
          roleNames.add(roleName);
        }
      }
    }
    adminActionForm.setRoleNames(roleNames);

    roles = null;
    roleNames = null;

    Log.log(4, "AdministrationAction", "showAssignRoles", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showModifyRoles(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showModifyRoles", "Entered");

    AdministrationActionForm adminActionForm = (AdministrationActionForm)form;

    String userId = adminActionForm.getUserId();

    Log.log(5, "AdministrationAction", "showModifyRoles", "userId " + userId);

    Administrator admin = new Administrator();

    ArrayList allRoles = admin.getAllRoles();

    ArrayList roleNames = new ArrayList();

    Log.log(5, "AdministrationAction", "showModifyRoles", "Displaying all roles ");

    for (int i = 0; i < allRoles.size(); i++)
    {
      Role role = (Role)allRoles.get(i);

      if (role != null)
      {
        String roleName = role.getRoleName();

        Log.log(5, "AdministrationAction", "showModifyRoles", "roleName " + roleName);

        if ((!roleName.equalsIgnoreCase("DEMOUSER")) && (!roleName.equalsIgnoreCase("AUDITOR")))
        {
          roleNames.add(roleName);
        }
      }
    }
    adminActionForm.setRoleNames(roleNames);

    ArrayList roles = admin.getRoles(userId);

    ArrayList privileges = admin.getPrivileges(userId);

    Log.log(5, "AdministrationAction", "showModifyRoles", "Displaying all roles ");
    Log.log(5, "AdministrationAction", "showModifyRoles", "setting the user roles ");

    for (int i = 0; i < roles.size(); i++)
    {
      String role = (String)roles.get(i);

      Log.log(5, "AdministrationAction", "showModifyRoles", "Displaying all roles "); Log.log(5, "AdministrationAction", "showModifyRoles", "role " + role);

      adminActionForm.setRole(role, role);
    }
    Map privilegeMap = new HashMap();

    for (int i = 0; i < privileges.size(); i++)
    {
      String privilege = (String)privileges.get(i);

      privilegeMap.put(privilege, privilege);
    }

    adminActionForm.setPrivileges(privilegeMap);

    allRoles = null;
    roleNames = null;
    roles = null;
    privileges = null;
    privilegeMap = null;

    Log.log(4, "AdministrationAction", "showModifyRoles", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showPrivilegesForRole(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showPrivilegesForRole", "Entered");

    AdministrationActionForm adminActionForm = (AdministrationActionForm)form;

    Administrator admin = new Administrator();

    Map roles = adminActionForm.getRoles();

    HttpSession session = request.getSession(false);

    Map rolesInSession = (Map)session.getAttribute("ROLES_IN_SESSION");

    Map privileges = adminActionForm.getPrivileges();

    if (rolesInSession == null)
    {
      rolesInSession = new HashMap();
      rolesInSession.putAll(roles);
      session.setAttribute("ROLES_IN_SESSION", rolesInSession);
    }
    else
    {
      Log.log(5, "AdministrationAction", "showPrivilegesForRole", "Displaying roles in Session");

      rolesInSession.putAll(roles);

      Set keys = rolesInSession.keySet();
      Iterator iterator = keys.iterator();

      ArrayList keysToBeRemoved = new ArrayList();

      while (iterator.hasNext())
      {
        Object key = (String)iterator.next();

        Log.log(5, "AdministrationAction", "showPrivilegesForRole", "key " + key);

        if (roles.containsKey(key))
        {
          Log.log(5, "AdministrationAction", "showPrivilegesForRole", "available in the selected roles...continue");
        }
        else
        {
          Log.log(5, "AdministrationAction", "showPrivilegesForRole", "unavailable ...remove from privileges");

          String value = (String)rolesInSession.get(key);

          keysToBeRemoved.add(key);

          ArrayList rolePrivileges = admin.getPrivilegesForRole(value);

          if (!privileges.containsKey("select_all"))
          {
            for (int i = 0; i < rolePrivileges.size(); i++)
            {
              privileges.remove(rolePrivileges.get(i));
            }
          }
        }
      }
      for (int i = 0; i < keysToBeRemoved.size(); i++)
      {
        Object removedKey = rolesInSession.remove(keysToBeRemoved.get(i));

        Log.log(5, "AdministrationAction", "showPrivilegesForRole", " removedKey: " + removedKey);
      }

    }

    Map selectedRoles = new HashMap();

    Map selectedPrivileges = new HashMap();

    Set keys = roles.keySet();
    Iterator iterator = keys.iterator();

    ArrayList rolesPrivileges = new ArrayList();

    Log.log(5, "AdministrationAction", "showPrivilegesForRole", "Displaying roles ");

    while (iterator.hasNext())
    {
      String key = (String)iterator.next();
      String value = (String)roles.get(key);

      Log.log(5, "AdministrationAction", "showPrivilegesForRole", "key and value " + key + " " + value);

      selectedRoles.put(key, value);

      ArrayList rolePrivileges = admin.getPrivilegesForRole(value);

      rolesPrivileges.addAll(rolePrivileges);
    }

    Log.log(5, "AdministrationAction", "showPrivilegesForRole", "displaying selected privileges...");

    Set privilegeKeys = privileges.keySet();
    Iterator privilegeIterator = privilegeKeys.iterator();

    while (privilegeIterator.hasNext())
    {
      String key = (String)privilegeIterator.next();
      String value = (String)privileges.get(key);

      Log.log(5, "AdministrationAction", "showPrivilegesForRole", " key and value " + key + " " + value);
      selectedPrivileges.put(key, value);
    }

    Log.log(5, "AdministrationAction", "showPrivilegesForRole", " Assigning role's privileges...");

    for (int i = 0; i < rolesPrivileges.size(); i++)
    {
      String privilege = (String)rolesPrivileges.get(i);

      Log.log(5, "AdministrationAction", "showPrivilegesForRole", "privilege " + privilege);
      selectedPrivileges.put(privilege, privilege);
    }

    adminActionForm.reset(mapping, request);

    adminActionForm.setRoles(selectedRoles);
    adminActionForm.setPrivileges(selectedPrivileges);

    roles = null;

    rolesInSession = null;

    privileges = null;

    keys = null;

    selectedRoles = null;

    selectedPrivileges = null;

    rolesPrivileges = null;

    privilegeKeys = null;

    privilegeIterator = null;

    Log.log(4, "AdministrationAction", "showPrivilegesForRole", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward assignRolesAndPrivileges(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "assignRolesAndPrivileges", "Entered");

    AdministrationActionForm adminActionForm = (AdministrationActionForm)form;

    Map roles = adminActionForm.getRoles();

    Map privileges = adminActionForm.getPrivileges();

    ArrayList rolesArray = new ArrayList();

    Set roleKeys = roles.keySet();
    Iterator rolesIterator = roleKeys.iterator();

    while (rolesIterator.hasNext())
    {
      rolesArray.add(rolesIterator.next());
    }

    ArrayList privilegesArray = new ArrayList();

    Set privilegeKeys = privileges.keySet();
    Iterator privilegesIterator = privilegeKeys.iterator();

    while (privilegesIterator.hasNext())
    {
      privilegesArray.add(privilegesIterator.next());
    }

    privilegesArray.remove("select_all");

    Administrator admin = new Administrator();

    String userId = adminActionForm.getUserId();

    Log.log(4, "AdministrationAction", "assignRolesAndPrivileges", "userId selected " + userId);

    User user = getUserInformation(request);

    admin.assignRolesAndPrivileges(rolesArray, privilegesArray, userId, user.getUserId());

    adminActionForm.reset(mapping, request);

    roles = null;

    privileges = null;

    rolesArray = null;
    rolesArray = null;

    roleKeys = null;

    rolesIterator = null;

    privilegesArray = null;

    privilegeKeys = null;

    privilegesIterator = null;

    user = null;

    request.setAttribute("message", "Roles and Privileges assigned for the user");

    Log.log(4, "AdministrationAction", "assignRolesAndPrivileges", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward modifyRolesAndPrivileges(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "modifyRolesAndPrivileges", "Entered");

    AdministrationActionForm adminActionForm = (AdministrationActionForm)form;

    Map selectedPrivileges = adminActionForm.getPrivileges();
    Map selectedRoles = adminActionForm.getRoles();

    String userId = adminActionForm.getUserId();

    Log.log(5, "AdministrationAction", "modifyRolesAndPrivileges", "userId " + userId);

    Administrator admin = new Administrator();

    ArrayList roleValues = new ArrayList();

    Set selectedRoleKeys = selectedRoles.keySet();
    Iterator roleIterator = selectedRoleKeys.iterator();

    Log.log(5, "AdministrationAction", "modifyRolesAndPrivileges", "displaying selected roles...");

    while (roleIterator.hasNext())
    {
      Object key = roleIterator.next();
      String value = (String)selectedRoles.get(key);

      Log.log(5, "AdministrationAction", "modifyRolesAndPrivileges", "key and values are  " + key + " " + value);

      roleValues.add(value);
    }

    ArrayList privilegeValues = new ArrayList();

    Set selectedPrivilegeKeys = selectedPrivileges.keySet();
    Iterator privilegeIterator = selectedPrivilegeKeys.iterator();

    Log.log(5, "AdministrationAction", "modifyRolesAndPrivileges", "displaying selected Privileges...");

    while (privilegeIterator.hasNext())
    {
      Object key = privilegeIterator.next();
      String value = (String)selectedPrivileges.get(key);

      Log.log(5, "AdministrationAction", "modifyRolesAndPrivileges", "key and values are  " + key + " " + value);

      privilegeValues.add(value);

      privilegeValues.remove("select_all");
    }

    User user = getUserInformation(request);

    admin.modifyRolesAndPrivileges(roleValues, privilegeValues, userId, user.getUserId());

    selectedPrivileges = null;

    selectedRoles = null;

    roleValues = null;

    selectedRoleKeys = null;

    roleIterator = null;

    privilegeValues = null;

    selectedPrivilegeKeys = null;

    privilegeIterator = null;

    user = null;
    request.setAttribute("message", "Roles and Privileges modified for the user");
    Log.log(4, "AdministrationAction", "modifyRolesAndPrivileges", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showReactivateUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "reactivateUser", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);

    User loggedUser = getUserInformation(request);
    String userBankId = loggedUser.getBankId();
    String userZoneId = loggedUser.getZoneId();
    String userBranchId = loggedUser.getBranchId();
    String userMemberId = userBankId + userZoneId + userBranchId;

    Registration registration = new Registration();
    ArrayList memberIds = new ArrayList();
    ArrayList zones = null;
    ArrayList branches = null;
    MLIInfo mliInfo = null;
    ArrayList allMembers = null;

    memberIds.add(userMemberId);

    if ((userBankId.equals("0000")) && (userZoneId.equals("0000")) && (userBranchId.equals("0000")))
    {
      allMembers = registration.getAllMembers();
      int memberSize = allMembers.size();
      for (int i = 0; i < memberSize; i++) {
        mliInfo = (MLIInfo)allMembers.get(i);
        String bankId = mliInfo.getBankId();
        String zoneId = mliInfo.getZoneId();
        String branchId = mliInfo.getBranchId();
        String memberId = bankId + zoneId + branchId;
        memberIds.add(memberId);
      }

    }
    else if ((!userBankId.equals("0000")) && (userZoneId.equals("0000")) && (userBranchId.equals("0000")))
    {
      zones = registration.getZones(userBankId);
      int zoneSize = zones.size();
      for (int i = 0; i < zoneSize; i++) {
        mliInfo = (MLIInfo)zones.get(i);
        String zoneId = mliInfo.getZoneId();
        String branchId = mliInfo.getBranchId();
        String memberId = userBankId + zoneId + branchId;
        memberIds.add(memberId);
      }

      branches = registration.getAllBranches(userBankId);
      int branchSize = branches.size();
      for (int i = 0; i < branchSize; i++) {
        mliInfo = (MLIInfo)branches.get(i);
        String branchId = mliInfo.getBranchId();
        String zoneId = mliInfo.getZoneId();
        String memberId = userBankId + zoneId + branchId;
        memberIds.add(memberId);
      }
    }
    else if ((!userBankId.equals("0000")) && (!userZoneId.equals("0000")) && (userBranchId.equals("0000")))
    {
      branches = registration.getAllBranches(userBankId);
      int branchSize = branches.size();
      for (int i = 0; i < branchSize; i++) {
        mliInfo = (MLIInfo)branches.get(i);
        String branchId = mliInfo.getBranchId();
        String zoneId = mliInfo.getZoneId();

        if (zoneId.equals(userZoneId)) {
          String members = userBankId + zoneId + branchId;
          memberIds.add(members);
        }
      }

    }

    Administrator admin = new Administrator();
    ArrayList filteredMemIds = new ArrayList();
    for (int i = 0; i < memberIds.size(); i++)
    {
      String memId = (String)memberIds.get(i);
      if (memId != null)
      {
        String bankId = memId.substring(0, 4);
        String zoneId = memId.substring(4, 8);
        String branchId = memId.substring(8, 12);

        int count = admin.getUsersCount(bankId, zoneId, branchId, "I");
        if (count == 0)
        {
          Log.log(4, "AdministrationAction", "showDeactivateUser", "Leaving Member Id :" + memId);
        }
        else
        {
          Log.log(4, "AdministrationAction", "showReactivateUser", "Adding Member Id :" + memId);
          filteredMemIds.add(memId);
        }
      }
    }
    dynaForm.set("memberIds", filteredMemIds);

    memberIds = null;
    zones = null;
    branches = null;
    mliInfo = null;
    allMembers = null;

    Log.log(4, "AdministrationAction", "reactivateUser", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward reactivateUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "reactivateUser", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;
    Administrator admin = new Administrator();
    String userId = (String)dynaActionForm.get("userId");
    String reason = (String)dynaActionForm.get("reason");

    User creatingUser = getUserInformation(request);
    String reactivatedBy = creatingUser.getUserId();

    admin.reactivateUser(userId, reason, reactivatedBy);

    creatingUser = null;
    request.setAttribute("message", "User Reactivated");
    Log.log(4, "AdministrationAction", "reactivateUser", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward changePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "changePassword", "Entered");
    
    String targetValue="";
	

    DynaActionForm dynaActionForm = (DynaActionForm)form;

    PasswordManager passwordMgr = new PasswordManager();
    
    byte[] strConvertedPass=null;
	byte[] strPassKey=null;
	byte[] strConvertedPass1=null;
	byte[] strPassKey1=null;
	byte[] strConvertedPass2=null;
	byte[] strPassKey2=null;
	String oldPassword=request.getParameter("oldPassword");
	String newPassword=request.getParameter("newPassword");
	String confirm=request.getParameter("confirm");
	////System.out.println("oldPassword Change Password Screen=="+oldPassword);
	////System.out.println("newPassword Change Password Screen=="+newPassword);
	//////System.out.println("confirm Change Password Screen=="+confirm);
	try{
		BASE64Decoder decoder = new BASE64Decoder();
		strConvertedPass=decoder.decodeBuffer(oldPassword);
		String decodeString=new String(strConvertedPass);
		//////System.out.println("Conversion 1 of oldPassword Change Password Screen:"+decodeString);
		strPassKey=decoder.decodeBuffer(decodeString);
		//////System.out.println("Conversion 2 of oldPassword Change Password Screen:"+new String(strPassKey));
		
		strConvertedPass1=decoder.decodeBuffer(newPassword);
		String decodeString1=new String(strConvertedPass1);
		////System.out.println("Conversion 1 of newPassword Change Password Screen:"+decodeString1);
		strPassKey1=decoder.decodeBuffer(decodeString1);
		////System.out.println("Conversion 2 of newPassword Change Password Screen:"+new String(strPassKey1));
		
		
		strConvertedPass2=decoder.decodeBuffer(confirm);
		String decodeString2=new String(strConvertedPass2);
		////System.out.println("Conversion 1 of confirm Change Password Screen:"+decodeString2);
		strPassKey2=decoder.decodeBuffer(decodeString2);
		////System.out.println("Conversion 2 of confirm Change Password Screen:"+new String(strPassKey2));
		
		
	}catch(Exception e){
		//System.out.println("Error::"+e.getMessage());
		e.printStackTrace();
	}
	if((String)dynaActionForm.get("oldPassword")!=null && (String)dynaActionForm.get("newPassword")!=null){

	    oldPassword = new String(strPassKey);//(String)dynaActionForm.get("oldPassword");
	
	     newPassword = new String(strPassKey1);//(String)dynaActionForm.get("newPassword");
	
	    User user = getUserInformation(request);
	
	    Log.log(5, "AdministrationAction", "changePassword", "User info is " + user);
	
	    char[] array1 = newPassword.toCharArray();
	    String invalids = "!@#$%";
	    int specialCharactersCount = 0;
	    int digitCount = 0;
	    int letterCount = 0;
	    for (int i = 0; i < invalids.length(); i++)
	    {
	      if (newPassword.indexOf(invalids.charAt(i)) >= 0)
	      {
	        specialCharactersCount += 1;
	      }
	
	    }
	
	    for (int j = 0; j < newPassword.length(); j++) {
	      if (Character.isLetter(array1[j])) {
	        letterCount += 1;
	      }
	      else if (Character.isDigit(array1[j])) {
	        digitCount += 1;
	      }
	
	    }
	
	    if (digitCount == 0) {
	      throw new InvalidDataException("Password should comprise of atleast one digit, one special characer and one letter");
	    }
	    if (specialCharactersCount == 0) {
	      throw new InvalidDataException("Password should comprise of atleast one digit, one special characer and one letter");
	    }
	    if (letterCount == 0) {
	      throw new InvalidDataException("Password should comprise of atleast one digit, one special characer and one letter");
	    }
	
	    passwordMgr.changePassword(oldPassword, newPassword, user, null);
	
	    String encryptedPassword = passwordMgr.encryptPassword(newPassword);
	
	    user.setPassword(encryptedPassword);
	
	    user = null;
	    request.setAttribute("message", "Password Changed");
	    targetValue="success";
	    Log.log(4, "AdministrationAction", "changePassword", "Exited");
	}else{
		 request.getSession().invalidate();
		 targetValue="success";
	}
    return mapping.findForward("success");
  }

  public ActionForward changeExpiredPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "changeExpiredPassword", "Entered");

    DynaActionForm dynaActionForm = (DynaActionForm)form;

    PasswordManager passwordMgr = new PasswordManager();

    String oldPassword = (String)dynaActionForm.get("oldPassword");

    String newPassword = (String)dynaActionForm.get("newPassword");
    String emailId = (String)dynaActionForm.get("emailId");

    char[] array1 = newPassword.toCharArray();
    String invalids = "!@#$%";
    int specialCharactersCount = 0;
    int digitCount = 0;
    int letterCount = 0;
    for (int i = 0; i < invalids.length(); i++)
    {
      if (newPassword.indexOf(invalids.charAt(i)) >= 0)
      {
        specialCharactersCount += 1;
      }

    }

    for (int j = 0; j < newPassword.length(); j++) {
      if (Character.isLetter(array1[j])) {
        letterCount += 1;
      }
      else if (Character.isDigit(array1[j])) {
        digitCount += 1;
      }

    }

    if (digitCount == 0) {
      throw new InvalidDataException("Password should comprise of atleast one digit, one special characer and one letter");
    }
    if (specialCharactersCount == 0) {
      throw new InvalidDataException("Password should comprise of atleast one digit, one special characer and one letter");
    }
    if (letterCount == 0) {
      throw new InvalidDataException("Password should comprise of atleast one digit, one special characer and one letter");
    }

    String hintQuestion = (String)dynaActionForm.get("newQuestion");

    String hintAnswer = (String)dynaActionForm.get("newAnswer");

    User user = getUserInformation(request);

    Log.log(5, "AdministrationAction", "changePassword", "User info is " + user);

    passwordMgr.changePassword(oldPassword, newPassword, user, emailId);

    String encryptedPassword = passwordMgr.encryptPassword(newPassword);

    user.setPassword(encryptedPassword);

    String userId = user.getUserId();

    passwordMgr.changeHintQuestionAndAnswer(hintQuestion, hintAnswer, userId);

    Hint hint = new Hint();
    hint.setHintQuestion(hintQuestion);
    hint.setHintAnswer(hintAnswer);
    user.setHint(hint);

    user = null;
    hint = null;

    Log.log(4, "AdministrationAction", "changeExpiredPassword", "Exited");

    return mapping.findForward("showMain");
  }

  public ActionForward showResetPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showResetPassword", "Entered");

    DynaActionForm dynaActionForm = (DynaActionForm)form;
    dynaActionForm.initialize(mapping);

    User loggedUser = getUserInformation(request);
    String userBankId = loggedUser.getBankId();
    String userZoneId = loggedUser.getZoneId();
    String userBranchId = loggedUser.getBranchId();
    String userMemberId = userBankId + userZoneId + userBranchId;

    Registration registration = new Registration();
    ArrayList memberIds = new ArrayList();
    ArrayList zones = null;
    ArrayList branches = null;
    MLIInfo mliInfo = null;
    ArrayList allMembers = null;

    memberIds.add(userMemberId);

    if ((userBankId.equals("0000")) && (userZoneId.equals("0000")) && (userBranchId.equals("0000")))
    {
      allMembers = registration.getAllMembers();
      int memberSize = allMembers.size();
      for (int i = 0; i < memberSize; i++) {
        mliInfo = (MLIInfo)allMembers.get(i);
        String bankId = mliInfo.getBankId();
        String zoneId = mliInfo.getZoneId();
        String branchId = mliInfo.getBranchId();
        String memberId = bankId + zoneId + branchId;
        memberIds.add(memberId);
      }

    }
    else if ((!userBankId.equals("0000")) && (userZoneId.equals("0000")) && (userBranchId.equals("0000")))
    {
      zones = registration.getZones(userBankId);
      int zoneSize = zones.size();
      for (int i = 0; i < zoneSize; i++) {
        mliInfo = (MLIInfo)zones.get(i);
        String zoneId = mliInfo.getZoneId();
        String branchId = mliInfo.getBranchId();
        String memberId = userBankId + zoneId + branchId;
        memberIds.add(memberId);
      }

      branches = registration.getAllBranches(userBankId);
      int branchSize = branches.size();
      for (int i = 0; i < branchSize; i++) {
        mliInfo = (MLIInfo)branches.get(i);
        String branchId = mliInfo.getBranchId();
        String zoneId = mliInfo.getZoneId();
        String memberId = userBankId + zoneId + branchId;
        memberIds.add(memberId);
      }
    }
    else if ((!userBankId.equals("0000")) && (!userZoneId.equals("0000")) && (userBranchId.equals("0000")))
    {
      branches = registration.getAllBranches(userBankId);
      int branchSize = branches.size();
      for (int i = 0; i < branchSize; i++) {
        mliInfo = (MLIInfo)branches.get(i);
        String branchId = mliInfo.getBranchId();
        String zoneId = mliInfo.getZoneId();

        if (zoneId.equals(userZoneId)) {
          String members = userBankId + zoneId + branchId;
          memberIds.add(members);
        }
      }

    }

    dynaActionForm.set("memberIds", memberIds);

    memberIds = null;
    zones = null;
    branches = null;
    mliInfo = null;
    allMembers = null;

    Log.log(4, "AdministrationAction", "showResetPassword", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward resetPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "resetPassword", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;
    PasswordManager passwordManager = new PasswordManager();

    String userId = (String)dynaActionForm.get("userId");

    User creatingUser = getUserInformation(request);
    String loginUserId = creatingUser.getUserId();

    String userEmailId = passwordManager.resetPassword(userId, loginUserId);
    //passwordManager.changeHintQuestionAndAnswer(null, null, userId);

    creatingUser = null;

		
		  request.setAttribute("message", "Password Reset, Kindly check mail for Password !!");
		 
    
    //request.setAttribute("message", "Password has ben reset and sent to HO");

    Log.log(4, "AdministrationAction", "resetPassword", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showChangeHintQA(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showChangeHintQA", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;

    User user = getUserInformation(request);
    Hint hint = user.getHint();
    String oldQuestion = hint.getHintQuestion();
    String oldAnswer = hint.getHintAnswer();

    dynaActionForm.set("newQuestion", oldQuestion);
    dynaActionForm.set("newAnswer", oldAnswer);

    user = null;
    hint = null;

    Log.log(4, "AdministrationAction", "showChangeHintQA", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward changeHintQA(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "changeHintQA", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;
    PasswordManager passwordManager = new PasswordManager();

    String newQuestion = (String)dynaActionForm.get("newQuestion");
    String newAnswer = (String)dynaActionForm.get("newAnswer");

    User user = getUserInformation(request);
    String userId = user.getUserId();
    passwordManager.changeHintQuestionAndAnswer(newQuestion, newAnswer, userId);

    Hint newHint = user.getHint();
    newHint.setHintAnswer(newAnswer);
    newHint.setHintQuestion(newQuestion);

    passwordManager = null;
    user = null;

    request.setAttribute("message", "Hint Question and Answer saved");

    Log.log(4, "AdministrationAction", "changeHintQA", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addRole(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "addRole", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;

    ArrayList privileges = new ArrayList();

    Set keys = Privileges.getKeys();
    Iterator iterator = keys.iterator();

    while (iterator.hasNext())
    {
      String key = (String)iterator.next();
      String dynaValue = (String)dynaActionForm.get(key);
      if ((dynaValue != null) && (!dynaValue.equals("")))
      {
        privileges.add(dynaValue);
      }

    }

    Log.log(5, "AdministrationAction", "addRole", "After getting the privileges");

    Role role = new Role();

    BeanUtils.populate(role, dynaActionForm.getMap());

    Log.log(5, "AdministrationAction", "addRole", "Role name and desc are " + role.getRoleName() + " " + role.getRoleDescription());

    role.setPrivileges(privileges);

    Administrator admin = new Administrator();

    User user = getUserInformation(request);

    Log.log(5, "AdministrationAction", "addRole", "user obj is " + user);

    admin.addRole(role, user);

    privileges.clear();
    privileges = null;

    keys = null;
    iterator = null;
    role = null;
    user = null;
    request.setAttribute("message", "Role added");
    Log.log(4, "AdministrationAction", "addRole", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward modifyRole(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "modifyRole", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;

    Role role = new Role();

    BeanUtils.populate(role, dynaActionForm.getMap());

    Log.log(5, "AdministrationAction", "modifyRole", "Role name and desc are " + role.getRoleName() + " " + role.getRoleDescription());

    ArrayList privileges = new ArrayList();

    Set keys = Privileges.getKeys();
    Iterator iterator = keys.iterator();

    while (iterator.hasNext())
    {
      String key = (String)iterator.next();
      String dynaValue = (String)dynaActionForm.get(key);
      if ((dynaValue != null) && (!dynaValue.equals("")))
      {
        privileges.add(dynaValue);
      }
    }

    role.setPrivileges(privileges);

    User user = getUserInformation(request);

    Administrator admin = new Administrator();

    admin.modifyRole(role, user);

    privileges.clear();
    privileges = null;

    keys = null;
    iterator = null;
    role = null;
    user = null;
    request.setAttribute("message", "Role Modified");
    Log.log(4, "AdministrationAction", "modifyRole", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward assignCB(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "assignCB", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;

    String[] members = (String[])dynaActionForm.get("memberBank");
    String collectingBank = (String)dynaActionForm.get("collectingBank");

    Log.log(5, "AdministrationAction", "assignCB", "collectingBank  " + collectingBank);

    Registration registration = new Registration();

    int size = members.length;
    boolean areMemsToAssigned = false;
    if (size == 0)
    {
      areMemsToAssigned = false;
    }
    for (int i = 0; i < size; i++)
    {
      String member = members[i];
      if ((member == null) || ((member == null) || (!member.equals(""))))
      {
        Log.log(5, "AdministrationAction", "assignCB", "member " + member);
        int start = member.indexOf("(");
        int finish = member.indexOf(")");
        String memberId = member.substring(start + 1, finish);
        Log.log(5, "AdministrationAction", "assignCB", "memberId" + memberId);

        User creatingUser = getUserInformation(request);
        String createdBy = creatingUser.getUserId();
        creatingUser = null;

        registration.assignCollectingBank(memberId, collectingBank, createdBy);
        areMemsToAssigned = true;
      }
    }
    if (areMemsToAssigned)
    {
      request.setAttribute("message", "Collecting Bank Assigned for member");
    }
    if (!areMemsToAssigned)
    {
      request.setAttribute("message", "There are no members for which Collecting Bank is to be assigned");
    }
    Log.log(4, "AdministrationAction", "assignCB", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward modifyCB(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "modifyCB", "Entered");

    DynaActionForm dynaActionForm = (DynaActionForm)form;

    String memberId = (String)dynaActionForm.get("member");

    Registration registration = new Registration();
    CollectingBank collectingBankObject = registration.getCollectingBank(memberId);

    String bankName = collectingBankObject.getCollectingBankName();
    String branchName = collectingBankObject.getBranchName();
    String collectingBank = bankName + "," + branchName;

    String cbMessage = "The present collecting bank for " + memberId + " is " + collectingBank;
    dynaActionForm.set("cbMessage", cbMessage);

    ArrayList collectingBanks = new ArrayList();
    collectingBanks = registration.getCollectingBanks();

    collectingBanks.remove(collectingBank);
    dynaActionForm.set("collectingBanks", collectingBanks);

    collectingBanks = null;

    Log.log(4, "AdministrationAction", "modifyCB", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward modifyExposureLimits(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "modifyExposureLimits", "Entered");

    DynaActionForm dynaActionForm = (DynaActionForm)form;

    String memberId = (String)dynaActionForm.get("member");

    String bankId = memberId.substring(1, 5);

    String bankName = memberId.substring(14);

    Registration registration = new Registration();
    double presentExposureLimit = registration.getPresentExposureLimit(bankId);

    dynaActionForm.set("bank", bankName);
    String cbMessage = "The present Exposure Limit for " + memberId + " is " + presentExposureLimit + "(In Cr.)";
    dynaActionForm.set("cbMessage", cbMessage);

    Log.log(4, "AdministrationAction", "modifyExposureLimits", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward afterModifyCB(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "afterModifyCB", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;

    String newCb = (String)dynaActionForm.get("newCollectingBank");
    String member = (String)dynaActionForm.get("member");
    int start = member.indexOf("(");
    int finish = member.indexOf(")");
    String memberId = member.substring(start + 1, finish);

    User creatingUser = getUserInformation(request);
    String createdBy = creatingUser.getUserId();

    Registration registration = new Registration();
    registration.assignCollectingBank(memberId, newCb, createdBy);

    creatingUser = null;
    registration = null;
    request.setAttribute("message", "Collecting Bank modified for member");

    Log.log(4, "AdministrationAction", "afterModifyCB", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward afterModifyExposureLimits(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "afterModifyExposureLimits", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;

    double newExposureLimit = ((Double)dynaActionForm.get("exposureLimit")).doubleValue();

    String member = (String)dynaActionForm.get("member");
    String bankName = (String)dynaActionForm.get("bank");

    String memberId = member.substring(1, 5);

    User creatingUser = getUserInformation(request);
    String createdBy = creatingUser.getUserId();

    Registration registration = new Registration();
    registration.assignNewExposureLimit(memberId, newExposureLimit);

    creatingUser = null;

    request.setAttribute("message", "The Exposure Limit has been modified for bank " + bankName + " is " + newExposureLimit + "(In Cr.)");

    Log.log(4, "AdministrationAction", "afterModifyExposureLimits", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward deactivateMem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "deactivateMem", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;
    Administrator administrator = new Administrator();

    String memberId = (String)dynaActionForm.get("memberId");
    String reason = (String)dynaActionForm.get("reason");

    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);

    String memId = bankId + zoneId + branchId;
    if ((memId != null) && (memId.equals("000000000000")))
    {
      throw new InvalidDataException("CGTSI ID can not be deactivated.");
    }

    User creatingUser = getUserInformation(request);
    String deactivatedBy = creatingUser.getUserId();

    Registration registration = new Registration();
    MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId, branchId);
    String status = mliInfo.getStatus();

    if (status.equals("I")) {
      request.setAttribute("message", "Member Already Deactivated");
    }
    else {
      administrator.deactivateMember(bankId, zoneId, branchId, deactivatedBy, reason);
      request.setAttribute("message", "Member Deactivated");
    }

    creatingUser = null;
    dynaActionForm.initialize(mapping);

    Log.log(4, "AdministrationAction", "deactivateMem", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward reactivateMem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "reactivateMem", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;
    Administrator administrator = new Administrator();

    String memberId = (String)dynaActionForm.get("memberId");
    String reason = (String)dynaActionForm.get("reason");

    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);

    String memId = bankId + zoneId + branchId;
    if ((memId != null) && (memId.equals("000000000000")))
    {
      throw new InvalidDataException("CGTSI ID can not be reactivated.");
    }

    User creatingUser = getUserInformation(request);
    String reactivatedBy = creatingUser.getUserId();

    Registration registration = new Registration();
    MLIInfo mliInfo = registration.getAllMemberDetails(bankId, zoneId, branchId);
    String status = mliInfo.getStatus();

    if (status.equals("A")) {
      request.setAttribute("message", "Member Already Reactivated");
    }
    else {
      administrator.reactivateMember(bankId, zoneId, branchId, reactivatedBy, reason);
      request.setAttribute("message", "Member Reactivated");
    }

    creatingUser = null;
    dynaActionForm.initialize(mapping);

    Log.log(4, "AdministrationAction", "reactivateMem", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showRegisterMLI(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showRegisterMLI", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    Administrator admin = new Administrator();
    dynaForm.initialize(mapping);

    ArrayList states = admin.getAllStates();
    dynaForm.set("states", states);

    states = null;
    //The below code aded by parmanand start
    ArrayList mliTypes = admin.getAllMliTypes();
    ////System.out.println("mliTypes size=="+mliTypes.size());
    dynaForm.set("mliTypes", mliTypes);
    //The below code aded by parmanand End

    Log.log(4, "AdministrationAction", "showRegisterMLI", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward registerMLI(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "registerMLI", "Entered");

    Registration registration = new Registration();

    registration = null;

    Log.log(4, "AdministrationAction", "registerMLI", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward afterRegisterMLI(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{   
	    String csrfCode = request.getParameter("csrfCode");
	    //System.out.println("csrfCode :::::  " + csrfCode);
	    String target="";
	    boolean check=CSRFUtil.getCSRFflag(csrfCode, request.getSession(false), request);
	    //System.out.println("check=="+check);
	    if(csrfCode != null && !"".equals(csrfCode) && check){
	    	Log.log(4, "AdministrationAction", "afterRegisterMLI", "Entered");

	        DynaActionForm dynaForm = (DynaActionForm)form;
	        Registration registration = new Registration();

	        User creatingUser = getUserInformation(request);
	        String createdBy = creatingUser.getUserId();

	        MLIInfo mliInfo = new MLIInfo();

	        BeanUtils.populate(mliInfo, dynaForm.getMap());
	        String memberId = registration.registerMLI(mliInfo, createdBy);

	        String bankId = memberId.substring(0, 4);

	        String zoneId = memberId.substring(4, 8);

	        String branchId = memberId.substring(8, 12);

	        User NOuser = new User();

	        BeanUtils.populate(NOuser, dynaForm.getMap());

	        String noEmailId = (String)dynaForm.get("noEmailId");
	        NOuser.setEmailId(noEmailId);

	        NOuser.setBankId(bankId);
	        NOuser.setBranchId(branchId);
	        NOuser.setZoneId(zoneId);

	        String noUserId = null;
	        String message = null;
	        try
	        {
	          noUserId = registration.createNO(createdBy, NOuser, true);
	          message = "memberId is " + memberId + "  NO userId is " + noUserId;
	          Log.log(5, "AdministrationAction", "afterRegisterMLI", "noUserId  " + noUserId);
	          request.setAttribute("message", message);
	        }
	        catch (MailerException mailerException) {
	          String errorMessage = mailerException.getMessage();
	          message = " memberId is " + memberId;
	          request.setAttribute("message", errorMessage + message);
	        }
	    	target = "success";
	    	
	    }else{
	        target = "success";
	    }
	    
	    Log.log(4, "AdministrationAction", "afterRegisterMLI", "Exited");
	    return mapping.findForward(target);
	  }


  public ActionForward showDefOrgStr(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showDefOrgStruct", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;

    dynaActionForm.initialize(mapping);

    User loggedUser = getUserInformation(request);
    String bankId = loggedUser.getBankId();
    String zoneId = loggedUser.getZoneId();
    String branchId = loggedUser.getBranchId();
    if ((bankId.equals("0000")) && (bankId.equals("0000")) && (bankId.equals("0000")))
    {
      return mapping.findForward("CGTSIUser");
    }

    Administrator admin = new Administrator();
    Registration registration = new Registration();

    ArrayList states = admin.getAllStates();
    dynaActionForm.set("states", states);

    dynaActionForm.set("bankId", bankId);
    dynaActionForm.set("zoneId", zoneId);
    dynaActionForm.set("branchId", branchId);

    MLIInfo mliInfo = new MLIInfo();
    ArrayList reportingZones = new ArrayList();
    ArrayList zonesList = registration.getZones(bankId);
    int size = zonesList.size();

    for (int i = 0; i < size; i++) {
      mliInfo = (MLIInfo)zonesList.get(i);
      String reportingZoneId = mliInfo.getReportingZoneID();
      Log.log(5, "RegistrationDAO", "showDefOrgStr", "reportingZoneId " + reportingZoneId);
      if ((reportingZoneId == null) || (reportingZoneId.equals("")) || (reportingZoneId.equals("NULL"))) {
        String reportingZoneName = mliInfo.getZoneName();
        reportingZones.add(reportingZoneName);
      }
    }
    dynaActionForm.set("reportingZones", reportingZones);

    admin = null;
    registration = null;
    mliInfo = null;

    states = null;
    reportingZones = null;
    zonesList = null;

    Log.log(4, "AdministrationAction", "showDefOrgStruct", "Exited");

    return mapping.findForward("MLIUser");
  }

  public ActionForward memberSelected(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "memberSelected", "Entered");
    Registration registration = new Registration();
    DynaActionForm dynaActionForm = (DynaActionForm)form;

    String memberId = (String)dynaActionForm.get("memberId");
    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);

    registration.getMemberDetails(bankId, zoneId, branchId);

    dynaActionForm.set("bankId", bankId);
    dynaActionForm.set("zoneId", zoneId);
    dynaActionForm.set("branchId", branchId);

    Administrator admin = new Administrator();

    ArrayList states = admin.getAllStates();
    dynaActionForm.set("states", states);

    MLIInfo mliInfo = new MLIInfo();
    ArrayList reportingZones = new ArrayList();

    ArrayList zonesList = registration.getZones(bankId);
    int size = zonesList.size();

    for (int i = 0; i < size; i++) {
      mliInfo = (MLIInfo)zonesList.get(i);
      String reportingZoneId = mliInfo.getReportingZoneID();
      Log.log(5, "RegistrationDAO", "memberSelected", "reportingZoneId " + reportingZoneId);
      if ((reportingZoneId == null) || (reportingZoneId.equals("")) || (reportingZoneId.equals("NULL"))) {
        String reportingZoneName = mliInfo.getZoneName();
        reportingZones.add(reportingZoneName);
      }
    }
    dynaActionForm.set("reportingZones", reportingZones);

    admin = null;
    registration = null;
    mliInfo = null;

    states = null;
    reportingZones = null;
    zonesList = null;

    Log.log(4, "AdministrationAction", "memberSelected", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward defOrgStr(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "defOrgStruct", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;
    MLIInfo mliInfo = new MLIInfo();
    Registration registration = new Registration();

    String zoneFromCombo = (String)dynaActionForm.get("zoneList");
    Log.log(5, "AdministrationAction", "defOrgStr", "zoneFromCombo " + zoneFromCombo);

    String zoneFromTextBox = (String)dynaActionForm.get("zoneName");
    Log.log(5, "AdministrationAction", "defOrgStr", "zoneFromTextBox " + zoneFromTextBox);

    BeanUtils.populate(mliInfo, dynaActionForm.getMap());

    if ((zoneFromTextBox != null) && (!zoneFromTextBox.equals(""))) {
      mliInfo.setZoneName(zoneFromTextBox);
    }
    else if ((zoneFromCombo != null) && (!zoneFromCombo.equals(""))) {
      mliInfo.setZoneName(zoneFromCombo);
    }

    User user = getUserInformation(request);

    String userBankId = (String)dynaActionForm.get("bankId");
    String userZoneId = (String)dynaActionForm.get("zoneId");
    String userBranchId = (String)dynaActionForm.get("branchId");
   // //System.out.println("User MLI Id:" + userBankId.concat(userZoneId).concat(userBranchId));
    MLIInfo mliDetails = registration.getMemberDetails(userBankId, userZoneId, userBranchId);
    String bankName = mliDetails.getBankName();
    ////System.out.println("Bank Name:" + bankName);
    
		/*
		 * String brnGstNo=(String) dynaActionForm.get("brnGstNo");
		 * mliInfo.setBrnGstNo(brnGstNo);
		 */
    mliInfo.setBankId(userBankId);
    mliInfo.setBankName(bankName);
    Log.log(5, "AdministrationAction", "defOrgStr", "MCGF Flag is :" + mliDetails.getSupportMCGF());
    mliInfo.setSupportMCGF(mliDetails.getSupportMCGF());
    
  
    String createdBy = user.getUserId();
    String memberId = registration.registerBranchMLI(mliInfo, createdBy);
   // //System.out.println("Member Id:" + memberId);

    dynaActionForm.initialize(mapping);

    String bankId = memberId.substring(0, 4);
    mliInfo.setBankId(bankId);
    String zoneId = memberId.substring(4, 8);

    mliInfo.setZoneId(zoneId);
    String branchId = memberId.substring(8, 12);

    mliInfo.setBranchId(branchId);
    dynaActionForm.set("bankId", bankId);
    dynaActionForm.set("zoneId", zoneId);
    dynaActionForm.set("branchId", branchId);

    Administrator administration = new Administrator();
    ArrayList designations = administration.getAllDesignations();
    dynaActionForm.set("designations", designations);

    mliInfo = null;
    registration = null;
    user = null;
    mliDetails = null;
    administration = null;
    designations = null;

    Log.log(4, "AdministrationAction", "defOrgStruct", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward afterDefOrgStr(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "afterDefOrgStr", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    Administrator administrator = new Administrator();
    User MLIuser = new User();
    String createdUserId = null;
    String status = request.getParameter("flag");
    int flagNo = Integer.parseInt(status);
    String bankId = null;
    String branchId = null;
    String zoneId = null;
    

    if (flagNo == 1)
    {
      User user = getUserInformation(request);
      String createdBy = user.getUserId();
      BeanUtils.populate(MLIuser, dynaForm.getMap());
      String userEmailId = (String)dynaForm.get("userEmailId");
      MLIuser.setEmailId(userEmailId);
      try
      {
    	  createdUserId =  administrator.createUser(createdBy, MLIuser, true);
      }
      catch (MailerException mailerException) {
        request.setAttribute("message", "User created successfully. User id is : " + createdUserId );
      }

       bankId = (String)dynaForm.get("bankId");
       branchId = (String)dynaForm.get("branchId");
       zoneId = (String)dynaForm.get("zoneId");
      dynaForm.initialize(mapping);
      dynaForm.set("bankId", bankId);
      dynaForm.set("branchId", branchId);
      dynaForm.set("zoneId", zoneId);

      ArrayList designations = administrator.getAllDesignations();
      dynaForm.set("designations", designations);
      designations.clear();
      designations = null;
      Log.log(4, "AdministrationAction", "afterDefOrgStr", "Exited");

      return mapping.findForward("add another user");
    }

    Registration registration = new Registration();

    ArrayList states = administrator.getAllStates();
    dynaForm.set("states", states);

     bankId = (String)dynaForm.get("bankId");
     branchId = (String)dynaForm.get("branchId");
     zoneId = (String)dynaForm.get("zoneId");

    MLIInfo mliInfo = new MLIInfo();
    ArrayList reportingZones = new ArrayList();

    ArrayList zonesList = registration.getZones(bankId);
    int size = zonesList.size();

    for (int i = 0; i < size; i++) {
      mliInfo = (MLIInfo)zonesList.get(i);
      String reportingZoneId = mliInfo.getReportingZoneID();
      Log.log(5, "RegistrationDAO", "memberSelected", "reportingZoneId " + reportingZoneId);
      if ((reportingZoneId == null) || (reportingZoneId.equals("")) || (reportingZoneId.equals("NULL"))) {
        String reportingZoneName = mliInfo.getZoneName();
        reportingZones.add(reportingZoneName);
      }
    }
    Log.log(5, "AdminAction", "memberSelected", "reportingZones" + reportingZones);
    dynaForm.set("reportingZones", reportingZones);

    User user = getUserInformation(request);
    String createdBy = user.getUserId();

    BeanUtils.populate(MLIuser, dynaForm.getMap());

    String userEmailId = (String)dynaForm.get("userEmailId");
    MLIuser.setEmailId(userEmailId);
    try
    {
      administrator.createUser(createdBy, MLIuser, true);
    }
    catch (MailerException mailerException)
    {
    	String s = mailerException.getMessage().trim();
      request.setAttribute("message", "User created successfully. User id is: "+ s.substring(s.lastIndexOf(":")) + "  Member id is: " +bankId+zoneId+branchId);
    }

    user = null;

    Log.log(4, "AdministrationAction", "afterDefOrgStr", "Exited");

    //return mapping.findForward("back to define org str");
    return mapping.findForward("success");
    
  }

  public ActionForward addParameter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "addParameter", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    ParameterMaster paramMaster = new ParameterMaster();
    Log.log(4, "AdministrationAction", "addParameter", "Printing DynaActionForm :" + dynaForm);
    BeanUtils.populate(paramMaster, dynaForm.getMap());

    User user = getUserInformation(request);
    Administrator admin = new Administrator();
    String rule = (String)dynaForm.get("rule");
    if (rule.equals("Days"))
    {
      int days = ((Integer)dynaForm.get("noOfDays")).intValue();
      Log.log(5, "AdministrationAction", "addParameter", "no of days -- " + days);
      paramMaster.setApplicationFilingTimeLimit(days);
    }
    else if (rule.equals("Periodicity"))
    {
      int periodicity = ((Integer)dynaForm.get("periodicity")).intValue();
      Log.log(5, "AdministrationAction", "addParameter", "periodicity -- " + periodicity);
      paramMaster.setApplicationFilingTimeLimit(periodicity);
    }
    admin.updateMaster(paramMaster, user.getUserId());

    paramMaster = null;
    user = null;
    admin = null;
    request.setAttribute("message", "Parameter details saved");
    Log.log(4, "AdministrationAction", "addParameter", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showParameter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showParameter", "Entered");

    Administrator admin = new Administrator();

    ParameterMaster parameter = admin.getParameter();

    DynaActionForm dynaForm = (DynaActionForm)form;

    BeanUtils.copyProperties(dynaForm, parameter);

    int applicationFilingTimeLimit = parameter.getApplicationFilingTimeLimit();

    if (applicationFilingTimeLimit > 0)
    {
      dynaForm.set("noOfDays", new Integer(applicationFilingTimeLimit));
      dynaForm.set("rule", "Days");
    }
    else if (applicationFilingTimeLimit < 0)
    {
      dynaForm.set("periodicity", new Integer(applicationFilingTimeLimit));
      dynaForm.set("rule", "Periodicity");
    }

    admin = null;

    parameter = null;

    Log.log(4, "AdministrationAction", "showParameter", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInbox(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showInbox", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    Administrator admin = new Administrator();

    User user = getUserInformation(request);

    ArrayList mails = admin.getAllAdminMails(user.getUserId());

    dynaForm.set("inboxMails", mails);

    admin = null;

    user = null;

    mails = null;

    Log.log(4, "AdministrationAction", "showInbox", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showMessage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showMessage", "Entered");

    String mailId = request.getParameter("mailId");

    Administrator admin = new Administrator();

    Message mail = admin.getMailForId(mailId);

    HttpSession session = request.getSession(false);

    session.setAttribute("adminMail", mail);

    admin = null;

    mail = null;

    Log.log(4, "AdministrationAction", "showMessage", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward deleteReadMails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "deleteReadMails", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    String[] selectedMails = (String[])dynaForm.get("checks");

    Log.log(5, "AdministrationAction", "deleteReadMails", "selectedMails " + selectedMails);

    if ((selectedMails != null) && (selectedMails.length > 0))
    {
      for (int i = 0; i < selectedMails.length; i++)
      {
        Log.log(5, "AdministrationAction", "deleteReadMails", selectedMails[i]);
      }
      Administrator admin = new Administrator();

      admin.deleteAdminMails(selectedMails);

      admin = null;
    }

    Log.log(4, "AdministrationAction", "deleteReadMails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showSendMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showSendMail", "Entered");

    HttpSession session = request.getSession(false);

    session.setAttribute("adminMail", null);

    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.initialize(mapping);

    User user = getUserInformation(request);

    String bankId = user.getBankId();

    Registration registration = new Registration();
    ArrayList members = null;

    if (bankId.equals("0000"))
    {
      members = registration.getAllMembers();
    }

    ArrayList memberIds = new ArrayList();
    if (members != null)
    {
      for (int i = 0; i < members.size(); i++)
      {
        MLIInfo mliInfo = (MLIInfo)members.get(i);

        String memberId = mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId();

        memberIds.add(memberId);

        mliInfo = null;
      }

    }

    memberIds.add("000000000000");

    dynaForm.set("members", memberIds);

    memberIds = null;
    members = null;
    user = null;
    registration = null;

    Log.log(4, "AdministrationAction", "showSendMail", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getUsers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "getUsers", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    String memberId = (String)dynaForm.get("memberId");

    Log.log(5, "AdministrationAction", "getUsers", "memberId " + memberId);

    Administrator admin = new Administrator();

    ArrayList activeUsers = admin.getAllUsers(memberId);

    ArrayList users = new ArrayList();

    for (int i = 0; i < activeUsers.size(); i++)
    {
      User user = (User)activeUsers.get(i);

      Log.log(5, "AdministrationAction", "getUsers", "user id " + user.getUserId());

      users.add(user.getUserId());
    }
    User user = getUserInformation(request);

    users.remove(user.getUserId());

    HttpSession session = request.getSession(false);
    session.setAttribute("MemberSelected", "Y");

    dynaForm.set("users", users);

    user = null;
    users = null;
    activeUsers = null;

    Log.log(4, "AdministrationAction", "getUsers", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward sendMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "sendMail", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    Message message = new Message();

    BeanUtils.populate(message, dynaForm.getMap());

    User user = getUserInformation(request);

    message.setFrom(user.getUserId());

    Log.log(5, "AdministrationAction", "sendMail", "From " + user.getUserId());

    ArrayList to = new ArrayList();

    to.add(dynaForm.get("userId"));

    Log.log(5, "AdministrationAction", "sendMail", "To " + dynaForm.get("userId"));

 //   message.setTo(to);

    Log.log(5, "AdministrationAction", "sendMail", "from " + message.getFrom());

    Log.log(5, "AdministrationAction", "sendMail", "subject " + message.getSubject());
    Log.log(5, "AdministrationAction", "sendMail", "message " + message.getMessage());

    Log.log(4, "AdministrationAction", "sendMail", "Entered");

    Administrator admin = new Administrator();

    admin.sendMail(message);

    message = null;
    user = null;
    to.clear();
    to = null;
    admin = null;

    request.setAttribute("message", "Mail sent to the user");

    Log.log(4, "AdministrationAction", "sendMail", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward replyMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "replyMail", "Entered");
    String bankId = null;
    String zoneId = null;
    String branchId = null;
    String memId = null;
    String userId = null;
    HttpSession session = request.getSession(false);
    DynaActionForm dynaForm = (DynaActionForm)form;
    Message msg = (Message)session.getAttribute("adminMail");
    if (msg != null)
    {
      bankId = msg.getBankId();
      zoneId = msg.getZoneId();
      branchId = msg.getBranchId();
      memId = bankId + zoneId + branchId;
      userId = msg.getFrom();
      dynaForm.set("memberId", memId);
      dynaForm.set("userId", userId);
    }
    Log.log(4, "AdministrationAction", "replyMail", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getDistricts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "getDistricts", "Entered");
    Administrator admin = new Administrator();
    DynaActionForm dynaActionForm = (DynaActionForm)form;
    String state = (String)dynaActionForm.get("state");

    ArrayList districts = admin.getAllDistricts(state);
    dynaActionForm.set("districts", districts);

    request.setAttribute("district", "1");

    admin = null;
    districts = null;

    Log.log(4, "AdministrationAction", "getDistricts", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showAssignCB(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showAssignCB", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;

    dynaActionForm.initialize(mapping);
    Registration registration = new Registration();
    ArrayList members = registration.getCBUnassignedMembers();

    int size = members.size();
    String[] membersArray = new String[size];

    for (int i = 0; i < size; i++)
    {
      String member = (String)members.get(i);
      membersArray[i] = member;
    }
    dynaActionForm.set("members", membersArray);

    ArrayList collectingBanks = registration.getCollectingBanks();
    dynaActionForm.set("collectingBanks", collectingBanks);

    registration = null;
    members = null;
    collectingBanks = null;

    Log.log(4, "AdministrationAction", "showAssignCB", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showModifyCB(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showModifyCB", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;
    dynaActionForm.initialize(mapping);
    Registration registration = new Registration();

    ArrayList members = registration.getMembersWithCb();
    dynaActionForm.set("cbMembers", members);

    registration = null;
    members = null;

    Log.log(4, "AdministrationAction", "showModifyCB", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showModifyExposureLimit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showModifyExposureLimit", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;
    dynaActionForm.initialize(mapping);
    Registration registration = new Registration();

    ArrayList memberNames = registration.getAllHOMembers();
    ArrayList memberDetails = new ArrayList(memberNames.size());

    for (int i = 0; i < memberNames.size(); i++)
    {
      MLIInfo mliInfo = (MLIInfo)memberNames.get(i);
      String mli = "";
      mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")" + mliInfo.getBankName();

      memberDetails.add(mli);
    }
    dynaActionForm.set("cbMembers", memberDetails);

    registration = null;
    memberDetails = null;

    Log.log(4, "AdministrationAction", "showModifyExposureLimit", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showRegCollBank(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showRegCollBank", "Entered");

    DynaActionForm dynaActionForm = (DynaActionForm)form;
    dynaActionForm.initialize(mapping);
    Administrator administrator = new Administrator();

    ArrayList states = administrator.getAllStates();
    dynaActionForm.set("states", states);

    administrator = null;
    states = null;

    Log.log(4, "AdministrationAction", "showRegCollBank", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addCollBank(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "addCollBank", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    Registration registration = new Registration();
    CollectingBank collectingBank = new CollectingBank();

    BeanUtils.populate(collectingBank, dynaForm.getMap());

    User creatingUser = getUserInformation(request);
    String createdBy = creatingUser.getUserId();

    registration.registerCollectingBank(collectingBank, createdBy);

    registration = null;
    collectingBank = null;
    creatingUser = null;

    Log.log(4, "AdministrationAction", "addCollBank", "Exited");
    request.setAttribute("message", "Collecting Bank Registered");

    return mapping.findForward("success");
  }

  public ActionForward showModifyCollBank(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showModifyCollBank", "Entered");

    DynaActionForm dynaActionForm = (DynaActionForm)form;
    dynaActionForm.initialize(mapping);

    Registration registration = new Registration();
    ArrayList collectingBanks = registration.getCollectingBanks();
    dynaActionForm.set("collectingBanks", collectingBanks);

    registration = null;
    collectingBanks = null;

    Log.log(4, "AdministrationAction", "showModifyCollBank", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward modifyCollBank(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "modifyCollBank", "Entered");

    DynaActionForm dynaActionForm = (DynaActionForm)form;

    Administrator administrator = new Administrator();
    ArrayList states = administrator.getAllStates();
    dynaActionForm.set("states", states);

    String cbName = (String)dynaActionForm.get("collectingBank");
    Registration registration = new Registration();
    int comma = cbName.indexOf(",");
    int length = cbName.length();
    String bankName = cbName.substring(0, comma);
    String branchName = cbName.substring(comma + 1, length);
    CollectingBank collectingBank = registration.viewCollectingBank(bankName, branchName);

    dynaActionForm.set("collectingBankName", bankName);
    dynaActionForm.set("branchName", branchName);

    BeanUtils.copyProperties(dynaActionForm, collectingBank);

    administrator = null;
    states = null;
    registration = null;
    collectingBank = null;

    Log.log(4, "AdministrationAction", "modifyCollBank", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward afterModifyCollBank(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "afterModifyCollBank", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    Registration registration = new Registration();
    CollectingBank collectingBank = new CollectingBank();

    BeanUtils.populate(collectingBank, dynaForm.getMap());

    String cbName = (String)dynaForm.get("collectingBankName");
    String branchName = (String)dynaForm.get("branchName");

    collectingBank.setCollectingBankName(cbName);
    collectingBank.setBranchName(branchName);

    User creatingUser = getUserInformation(request);
    String createdBy = creatingUser.getUserId();

    registration.updateCollectingBankDtls(collectingBank, createdBy);

    registration = null;
    collectingBank = null;
    creatingUser = null;

    Log.log(4, "AdministrationAction", "afterModifyCollBank", "Exited");
    request.setAttribute("message", "Collecting Bank Details Modified");

    return mapping.findForward("success");
  }

  public ActionForward addZone(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "addZone", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    ZoneMaster zoneMaster = new ZoneMaster();

    User user = getUserInformation(request);
    String createdBy = user.getUserId();
    zoneMaster.setCreatedBy(createdBy);

    BeanUtils.populate(zoneMaster, dynaForm.getMap());
    zoneMaster.updateMaster();

    dynaForm.initialize(mapping);

    zoneMaster = null;
    user = null;

    Log.log(4, "AdministrationAction", "addZone", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showRegion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showRegion", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    Administrator administrator = new Administrator();

    ArrayList zones = administrator.getAllZones();
    dynaForm.set("zones", zones);

    administrator = null;
    zones = null;

    Log.log(4, "AdministrationAction", "showRegion", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addRegion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "addRegion", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    RegionMaster regionMaster = new RegionMaster();

    User user = getUserInformation(request);
    String createdBy = user.getUserId();
    regionMaster.setCreatedBy(createdBy);

    BeanUtils.populate(regionMaster, dynaForm.getMap());
    regionMaster.updateMaster();

    regionMaster = null;
    user = null;

    Log.log(4, "AdministrationAction", "addRegion", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showState", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    Administrator administrator = new Administrator();

    ArrayList regions = administrator.getAllRegions();
    dynaForm.set("regions", regions);

    administrator = null;
    regions = null;

    Log.log(4, "AdministrationAction", "showState", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "addState", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    StateMaster stateMaster = new StateMaster();

    BeanUtils.populate(stateMaster, dynaForm.getMap());

    User user = getUserInformation(request);
    String createdBy = user.getUserId();
    stateMaster.setCreatedBy(createdBy);

    stateMaster.updateMaster();

    stateMaster = null;
    user = null;
    request.setAttribute("message", "State added");
    Log.log(4, "AdministrationAction", "addState", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showDistrict(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showDistrict", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    Administrator administrator = new Administrator();

    ArrayList states = administrator.getAllStates();
    dynaForm.set("states", states);

    administrator = null;
    states = null;

    Log.log(4, "AdministrationAction", "showDistrict", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addDistrict(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "addDistrict", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    DistrictMaster districtMaster = new DistrictMaster();
    BeanUtils.populate(districtMaster, dynaForm.getMap());

    User user = getUserInformation(request);
    String createdBy = user.getUserId();
    districtMaster.setCreatedBy(createdBy);

    districtMaster.updateMaster();

    districtMaster = null;
    user = null;
    request.setAttribute("message", "District added");
    Log.log(4, "AdministrationAction", "addDistrict", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showDesignation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showDesignation", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    Administrator administrator = new Administrator();

    ArrayList designations = administrator.getAllDesignations();
    dynaForm.set("designations", designations);

    administrator = null;
    designations = null;

    Log.log(4, "AdministrationAction", "showDesignation", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addDesignation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "addDesignation", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    DesignationMaster designationMaster = new DesignationMaster();
    BeanUtils.populate(designationMaster, dynaForm.getMap());
    designationMaster.updateMaster();

    designationMaster = null;
    request.setAttribute("message", "Designation details saved");
    Log.log(4, "AdministrationAction", "addDesignation", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getDesigDescr(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "getDesigDescr", "Entered");
    Administrator admin = new Administrator();
    DynaActionForm dynaActionForm = (DynaActionForm)form;
    String designation = (String)dynaActionForm.get("desigName");

    String desigDescr = admin.getDesigDescr(designation);
    dynaActionForm.set("desigDesc", desigDescr);

    admin = null;
    Log.log(4, "AdministrationAction", "getDesigDescr", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showPLR(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showPLR", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    Registration registration = new Registration();

    ArrayList mliInfo = registration.getAllMLIs();
    ArrayList plrBanks = new ArrayList();
    int size = mliInfo.size();
    for (int i = 0; i < size; i++) {
      MLIInfo mliDetail = (MLIInfo)mliInfo.get(i);

      String bankId = mliDetail.getBankId();
      String zoneId = mliDetail.getZoneId();
      String branchId = mliDetail.getBranchId();
      String shortName = mliDetail.getShortName();

      String concatString = "(" + bankId + zoneId + branchId + ")" + shortName;

      plrBanks.add(concatString);
    }

    dynaForm.set("plrBanks", plrBanks);

    plrBanks = null;

    Log.log(4, "AdministrationAction", "showPLR", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addPLR(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "addPLR", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    PLRMaster plrMaster = new PLRMaster();
    BeanUtils.populate(plrMaster, dynaForm.getMap());

    User user = getUserInformation(request);
    String createdBy = user.getUserId();
    plrMaster.setCreatedBy(createdBy);

    plrMaster.updateMaster();

    plrMaster = null;
    user = null;
    request.setAttribute("message", "PLR details saved");
    Log.log(4, "AdministrationAction", "addPLR", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showModifyPLRFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showModifyPLRFilter", "Entered");
    AdministrationActionForm dynaForm = (AdministrationActionForm)form;

    Registration registration = new Registration();

    ArrayList mliInfo = registration.getAllMLIs();
    ArrayList plrBanks = new ArrayList();
    int size = mliInfo.size();
    for (int i = 0; i < size; i++) {
      MLIInfo mliDetail = (MLIInfo)mliInfo.get(i);

      String bankId = mliDetail.getBankId();
      String zoneId = mliDetail.getZoneId();
      String branchId = mliDetail.getBranchId();
      String shortName = mliDetail.getShortName();

      String concatString = "(" + bankId + zoneId + branchId + ")" + shortName;

      plrBanks.add(concatString);
    }

    dynaForm.setPlrBanks(plrBanks);
    dynaForm.setShortNameMemId("");

    plrBanks = null;

    Log.log(4, "AdministrationAction", "showModifyPLRFilter", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getPLRSummary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "getPLRSummary", "Entered");
    Administrator administrator = new Administrator();
    AdministrationActionForm dynaActionForm = (AdministrationActionForm)form;
    Registration registration = new Registration();

    String shortNameMemberId = dynaActionForm.getShortNameMemId();
    String memberId = null;

    memberId = shortNameMemberId.substring(1, 13);
    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);

    Log.log(5, "AdministrationAction", "getPLRDetails", "bankId :" + bankId);
    Log.log(5, "AdministrationAction", "getPLRDetails", "zoneId :" + zoneId);
    Log.log(5, "AdministrationAction", "getPLRDetails", "branchId :" + branchId);

    MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId, branchId);

    String bankName = mliInfo.getBankName();
    Log.log(5, "AdministrationAction", "getPLRDetails", "bankName :" + bankName);

    dynaActionForm.setMemberId(bankId + zoneId + branchId);
    Log.log(5, "AdministrationAction", "getPLRDetails", "memberId :" + bankId + zoneId + branchId);

    ArrayList plrMasters = administrator.getPlrDetails(bankId);

    for (int i = 0; i < plrMasters.size(); i++)
    {
      PLRMaster plrMaster = (PLRMaster)plrMasters.get(i);
      CustomisedDate custome = new CustomisedDate();
      custome.setDate(plrMaster.getStartDate());
      plrMaster.setStartDate(custome);

      custome = new CustomisedDate();
      custome.setDate(plrMaster.getEndDate());
      plrMaster.setEndDate(custome);
    }

    Log.log(5, "AdministrationAction", "getPLRDetails", "plrMasters :" + plrMasters);

    dynaActionForm.setPlrDetails(plrMasters);

    Log.log(5, "AdministrationAction", "getPLRDetails", "bankName " + bankName);
    dynaActionForm.setBankName(bankName);

    Log.log(4, "AdministrationAction", "getPLRDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getPLRDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "getPLRDetails", "Entered");
    Administrator administrator = new Administrator();
    AdministrationActionForm dynaActionForm = (AdministrationActionForm)form;

    ArrayList plrMasters = dynaActionForm.getPlrDetails();
    String plrIdStr = request.getParameter("plrId");
    int plrId = 0;

    if ((plrIdStr != null) && (!plrIdStr.equals("")))
    {
      plrId = Integer.parseInt(plrIdStr);
    }
    dynaActionForm.setPlrIndexValue(plrId);

    for (int i = 0; i < plrMasters.size(); i++)
    {
      PLRMaster plrObj = (PLRMaster)plrMasters.get(i);

      Log.log(5, "AdministrationAction", "getPLRDetails", "start date " + plrObj.getStartDate());
      Log.log(5, "AdministrationAction", "getPLRDetails", "start date " + plrObj.getEndDate());
      Log.log(5, "AdministrationAction", "getPLRDetails", "start date " + plrObj.getPlrId());

      if (plrId == plrObj.getPlrId())
      {
        dynaActionForm.setPlrMaster(plrObj);
        break;
      }

    }

    Log.log(4, "AdministrationAction", "getPLRDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward modifyPLR(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "modifyPLR", "Entered");
    AdministrationActionForm dynaForm = (AdministrationActionForm)form;
    Administrator admin = new Administrator();
    PLRMaster plrMaster = dynaForm.getPlrMaster();

    Log.log(5, "AdministrationAction", "modifyPLR", "Dyna Form id " + plrMaster.getPlrId());
    Log.log(5, "AdministrationAction", "modifyPLR", "Dyna Form: start date " + plrMaster.getStartDate());
    Log.log(5, "AdministrationAction", "modifyPLR", "Dyna Form: end date " + plrMaster.getEndDate());

    int plrId = dynaForm.getPlrIndexValue();

    Log.log(5, "AdministrationAction", "modifyPLR", "plrId " + plrId);

    User user = getUserInformation(request);
    String createdBy = user.getUserId();

    if (plrMaster != null)
    {
      ArrayList tempList = dynaForm.getPlrDetails();

      Log.log(5, "AdministrationAction", "modifyPLR", " before size " + dynaForm.getPlrDetails().size());

      for (int i = 0; i < tempList.size(); i++)
      {
        PLRMaster plrMasterTemp = (PLRMaster)tempList.get(i);
        if (plrMasterTemp.getPlrId() == plrId)
        {
          tempList.remove(i);
          i--;
        }

      }

      Log.log(5, "AdministrationAction", "modifyPLR", " after size " + dynaForm.getPlrDetails().size());

      boolean isValidDates = admin.validatePLRDetails(plrMaster, tempList, createdBy);

      Log.log(5, "AdministrationAction", "modifyPLR", " isValidDates " + isValidDates);

      if (isValidDates)
      {
        admin.modifyPLRMaster(plrMaster, createdBy);
        request.setAttribute("message", "PLR details saved");
      }
      else
      {
        request.setAttribute("message", "PLR details already exists for this period");
      }
    }
    else
    {
      Log.log(2, "AdministrationAction", "modifyPLR", "Unable to get PLR object");
    }

    user = null;

    Log.log(4, "AdministrationAction", "modifyPLR", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showAlertMessages(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showAlertMessages", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    Administrator administrator = new Administrator();
    dynaForm.initialize(mapping);

    ArrayList alerts = administrator.getAlertTitles();
    dynaForm.set("alertTitles", alerts);

    administrator = null;
    alerts = null;

    Log.log(4, "AdministrationAction", "showAlertMessages", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addAlertMessages(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "addAlertMessages", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    AlertMaster alertMaster = new AlertMaster();
    BeanUtils.populate(alertMaster, dynaForm.getMap());

    User user = getUserInformation(request);
    String createdBy = user.getUserId();
    alertMaster.setCreatedBy(createdBy);

    alertMaster.updateMaster();

    alertMaster = null;
    user = null;
    request.setAttribute("message", "Alert Message added");
    Log.log(4, "AdministrationAction", "addAlertMessages", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getAlertMessage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "getAlertMessage", "Entered");
    Administrator administrator = new Administrator();
    DynaActionForm dynaActionForm = (DynaActionForm)form;

    String alertTitle = (String)dynaActionForm.get("alertTitle");
    String alertMessage = administrator.getAlertMessage(alertTitle);
    dynaActionForm.set("alertMessage", alertMessage);

    administrator = null;

    Log.log(4, "AdministrationAction", "getAlertMessage", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showExceptionMessages(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showExceptionMessages", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    Administrator administrator = new Administrator();
    dynaForm.initialize(mapping);

    ArrayList exceptions = administrator.getExceptionTitles();
    dynaForm.set("exceptionTitles", exceptions);

    administrator = null;
    exceptions = null;

    Log.log(4, "AdministrationAction", "showExceptionMessages", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getExceptionMessage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "getExceptionMessage", "Entered");
    Administrator administrator = new Administrator();
    DynaActionForm dynaActionForm = (DynaActionForm)form;
    ExceptionMaster exceptionMaster = new ExceptionMaster();

    String exceptionTitle = (String)dynaActionForm.get("exceptionTitle");
    exceptionMaster = administrator.getExceptionMessage(exceptionTitle);
    dynaActionForm.set("exceptionMessage", exceptionMaster.getExceptionMessage());
    dynaActionForm.set("exceptionType", exceptionMaster.getExceptionType().trim());

    exceptionMaster = null;

    Log.log(4, "AdministrationAction", "getExceptionMessage", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addExceptionMessages(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "addExceptionMessages", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    ExceptionMaster exceptionMaster = new ExceptionMaster();
    BeanUtils.populate(exceptionMaster, dynaForm.getMap());

    exceptionMaster.updateMaster();
    exceptionMaster = null;
    Log.log(4, "AdministrationAction", "addExceptionMessages", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addEnterAudit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "addEnterAudit", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    CommonUtility commonUtility = new CommonUtility();
    String remarks = (String)dynaForm.get("auditComments");
    String CGPAN = (String)dynaForm.get("cgpan");

    CGPAN = CGPAN.trim();

    Log.log(4, "AdministrationAction", "addEnterAudit", "CGPAN :" + CGPAN);
    if ((CGPAN == null) || ((CGPAN != null) && (CGPAN.equals(""))))
    {
      throw new NoDataException("Please enter a valid CGPAN.");
    }
    Administrator admin = new Administrator();
    ArrayList cgpans = admin.getAllCgpans();
    boolean isCGPANInTheSystem = false;
    for (int i = 0; i < cgpans.size(); i++)
    {
      String pan = (String)cgpans.get(i);
      if (pan != null)
      {
        if (CGPAN.equals(pan))
        {
          isCGPANInTheSystem = true;
        }
      }
    }
    if (!isCGPANInTheSystem)
    {
      throw new NoDataException("The CGPAN does not exist in the Database or may have been expired.\n Please enter a valid CGPAN");
    }

    User user = getUserInformation(request);
    String createdBy = user.getUserId();
    commonUtility.enterAuditDetails(CGPAN, createdBy, remarks);

    commonUtility = null;
    user = null;
    request.setAttribute("message", "Audit comments saved");
    Log.log(4, "AdministrationAction", "addEnterAudit", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showReviewAudit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showReviewAudit", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    CommonUtility commonUtility = new CommonUtility();
    AuditDetails auditDetails = new AuditDetails();

    dynaForm.initialize(mapping);

    auditDetails.setAuditId(-1);
    auditDetails = commonUtility.viewAuditDetails(auditDetails);

    String message = auditDetails.getMessage();

    String auditId = "" + auditDetails.getAuditId();

    HttpSession session = request.getSession(false);

    if (session.getAttribute("mainMenu").equals(MenuOptions.getMenu("CP_SETTLEMENT")))
    {
      String memId = request.getParameter("MemberId");
      dynaForm.set("memberId", memId);
    }

    dynaForm.set("auditId", auditId);
    dynaForm.set("comment", message);

    commonUtility = null;
    auditDetails = null;

    Log.log(4, "AdministrationAction", "showReviewAudit", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addReviewAudit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showReviewAudit", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;

    CommonUtility commonUtility = new CommonUtility();

    String reviewedComment = (String)dynaForm.get("reviewedComments");

    User user = getUserInformation(request);
    String userId = user.getUserId();

    int auditId = Integer.parseInt((String)dynaForm.get("auditId"));

    String CGPAN = (String)dynaForm.get("cgpan");

    commonUtility.updateAuditReview(CGPAN, auditId, userId, reviewedComment);

    commonUtility = null;

    request.setAttribute("message", "Review details saved");

    Log.log(4, "AdministrationAction", "showReviewAudit", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showReviewAuditNext(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showReviewAuditNext", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.set("cgpan", "");
    CommonUtility commonUtility = new CommonUtility();
    AuditDetails auditDetails = new AuditDetails();

    String previousAuditId = "" + (String)dynaForm.get("auditId");
    int previousId = Integer.parseInt(previousAuditId);
    auditDetails.setAuditId(previousId);

    auditDetails = commonUtility.viewAuditDetails(auditDetails);

    String message = auditDetails.getMessage();
    String auditId = "" + auditDetails.getAuditId();

    dynaForm.set("auditId", auditId);
    dynaForm.set("comment", message);

    commonUtility = null;
    auditDetails = null;

    Log.log(4, "AdministrationAction", "showReviewAuditNext", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showReviewAuditPrev(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showReviewAuditPrev", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.set("cgpan", "");

    CommonUtility commonUtility = new CommonUtility();
    AuditDetails auditDetails = new AuditDetails();

    String previousAuditId = "" + (String)dynaForm.get("auditId");
    Log.log(5, "AdministrationAction", "showReviewAuditPrevious", "previousAuditId " + previousAuditId);
    int previousId = Integer.parseInt(previousAuditId);
    auditDetails.setAuditId(previousId);

    auditDetails = commonUtility.viewRevAuditDetails(auditDetails);

    String message = auditDetails.getMessage();
    String auditId = "" + auditDetails.getAuditId();
    dynaForm.set("auditId", auditId);
    dynaForm.set("comment", message);

    commonUtility = null;
    auditDetails = null;

    Log.log(4, "AdministrationAction", "showReviewAuditPrev", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getAuditForCgpan(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "getAuditForCgpan", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    CommonUtility commonUtility = new CommonUtility();

    String CGPAN = (String)dynaForm.get("cgpan");
    Log.log(4, "AdministrationAction", "getAuditForCgpan", "CGPAN :" + CGPAN);
    if ((CGPAN == null) || ((CGPAN != null) && (CGPAN.equals(""))))
    {
      throw new NoDataException("Please enter a valid CGPAN.");
    }
    Administrator admin = new Administrator();
    ArrayList cgpans = admin.getAllCgpans();
    boolean isCGPANInTheSystem = false;
    for (int i = 0; i < cgpans.size(); i++)
    {
      String pan = (String)cgpans.get(i);
      if (pan != null)
      {
        if (CGPAN.equals(pan))
        {
          isCGPANInTheSystem = true;
        }
      }
    }
    if (!isCGPANInTheSystem)
    {
      throw new NoDataException("The CGPAN does not exist in the Database or may have been expired");
    }

    AuditDetails auditDetails = commonUtility.getAuditForCgpan(CGPAN);
    String auditId = "" + auditDetails.getAuditId();
    dynaForm.set("auditId", auditId);
    dynaForm.set("comment", auditDetails.getMessage());

    commonUtility = null;

    Log.log(4, "AdministrationAction", "getAuditForCgpan", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getHintQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "getHintQuestion", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
LogClass.StepWritterForXXXModule("getHintQuestion dynaForm="+dynaForm, "hindAnsIssue.txt");
    String userId = (String)dynaForm.get("userId");
    LogClass.StepWritterForXXXModule("getHintQuestion userId="+userId, "hindAnsIssue.txt");
    String memberId = (String)dynaForm.get("memberId");
    LogClass.StepWritterForXXXModule("getHintQuestion memberId="+memberId, "hindAnsIssue.txt");
    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);

    Registration registration = new Registration();
    registration.getMemberDetails(bankId, zoneId, branchId);

    Administrator administrator = new Administrator();

    User userObj = administrator.getUserInfo(memberId, userId);

    if (userObj == null)
    {
      //throw new NoUserFoundException("No User Found");
    	throw new NoUserFoundException("Invalid Credential");
    }
    String userMemberId = userObj.getBankId() + userObj.getZoneId() + userObj.getBranchId();

    if (!userMemberId.equals(memberId))
    {
      throw new NoUserFoundException("User does not belong to the member");
    }
    if (!userObj.isUserActive())
    {
      Log.log(3, "Login", "login", "User is inactive ");

      throw new InactiveUserException("Inactive User");
    }

    Log.log(5, "AdministrationAction", "getHintQuestion", "userId " + userId);

    Hint hint = userObj.getHint();

    String hintQuestion = hint.getHintQuestion();
    String hintAnswer = hint.getHintAnswer();

    if ((hintQuestion == null) && (hintAnswer == null))
    {
      throw new NoDataException("No Hint Question/Answer Available for the user.");
    }

    Log.log(5, "AdministrationAction", "getHintQuestion", "hintQuestion " + hintQuestion);

    dynaForm.set("hintQuestion", hintQuestion);
    dynaForm.set("hintAnswer", "");

    hint = null;

    Log.log(4, "AdministrationAction", "getHintQuestion", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward answerHintQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "answerHintQuestion", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    byte[] strConvertedPass=null;
    byte[] strPassKey=null;
    String hintAnswer=request.getParameter("hintAnswer");
    BASE64Decoder decoder = new BASE64Decoder();
    strConvertedPass=decoder.decodeBuffer(hintAnswer);
    String decodeString=new String(strConvertedPass);
    strPassKey=decoder.decodeBuffer(decodeString);
    PasswordManager passwordManager = new PasswordManager();
    hintAnswer = new String(strPassKey);

   // String hintAnswer = (String)dynaForm.get("hintAnswer");

    String userId = (String)dynaForm.get("userId");

    String memberId = (String)dynaForm.get("memberId");
    
   // String forward = "failure";

    Log.log(5, "AdministrationAction", "answerHintQuestion", "userId " + userId);

    Log.log(5, "AdministrationAction", "answerHintQuestion", "hintAnswer " + hintAnswer);

    Administrator admin = new Administrator();

    User user = admin.getUserInfo(memberId, userId);

    Hint hint = user.getHint();

    String forward = "failure";

    if (hintAnswer.equalsIgnoreCase(hint.getHintAnswer()))
    {
      Log.log(5, "AdministrationAction", "answerHintQuestion", "Hint Answer is same");

     // PasswordManager passwordManager = new PasswordManager();

      String decryptedPassword = passwordManager.decryptPassword(user.getPassword());

    //  request.setAttribute("displayPassword", decryptedPassword);

      AdminDAO adminDao = new AdminDAO();

      //ParameterMaster parameterMaster = adminDao.getParameter();

      ///int displayPeriod = parameterMaster.getPasswordDisplayPeriod();

      //String pwdDisplayPeriod = new Integer(displayPeriod).toString();

      //Log.log(5, "AdministrationAction", "answerHintQuestion", "pwdDisplayPeriod " + pwdDisplayPeriod);

   //   HttpSession session = request.getSession(false);

     // session.setAttribute("pwdDisplayPeriod", pwdDisplayPeriod);*/
    
   // String userId = (String)dynaActionForm.get("userId");

    User creatingUser = getUserInformation(request);
   // String loginUserId = creatingUser.getUserId();

    String userEmailId = passwordManager.resetPassword(userId, userId);
   // passwordManager.changeHintQuestionAndAnswer(null, null, userId);

    creatingUser = null;

		/*
		 * request.setAttribute("message", "Password Reset and Mail Send to " +
		 * userEmailId);
		 */
    
    request.setAttribute("message", "Password reset, Kindly check mail for Password !!");
    
    forward = "success";

    }
    else
    {
    	 request.setAttribute("message", "Invalid Answer!! !!");
    	  forward = "failure";
    	 
    }
    admin = null;
    user = null;
    hint = null;
    
   
    return mapping.findForward(forward);
  }

  public ActionForward showBroadcastMessage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showBroadcastMessage", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);

    Registration registration = new Registration();
    ArrayList mliInfo = registration.getAllMLIs();

    int size = mliInfo.size();
    ArrayList banks = new ArrayList();
    for (int i = 0; i < size; i++)
    {
      MLIInfo mli = (MLIInfo)mliInfo.get(i);
      String shortName = mli.getShortName();
      String bankId = mli.getBankId().trim();
      String zoneId = mli.getZoneId().trim();
      String branchId = mli.getBranchId().trim();
      String memberId = bankId + zoneId + branchId;
      String bank = shortName + "(" + memberId + ")";
      banks.add(bank);
    }
    dynaForm.set("allBanks", banks);

    registration = null;
    mliInfo = null;
    banks = null;
    Log.log(4, "AdministrationAction", "showBroadcastMessage", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward actionTaken(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "actionTaken", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    Registration registration = new Registration();

    String radio = (String)dynaForm.get("selectBM");
    String bankName = (String)dynaForm.get("bankName");
    String bankId = null;

    if (bankName.equals("Select")) {
      ArrayList blank = new ArrayList();
      dynaForm.set("zones", blank);
      dynaForm.set("branches", blank);
      blank = null;
    }
    else {
      int start = bankName.indexOf("(");
      int finish = bankName.indexOf(")");
      String memberId = bankName.substring(start + 1, finish);
      dynaForm.set("memberId", memberId);
      bankId = memberId.substring(0, 4);
    }

    if ((radio.equals("membersOfZone")) || (radio.equals("noOfZones")))
    {
      ArrayList zoneInfo = new ArrayList();
      if ((bankId != null) && (!bankId.equals(""))) {
        zoneInfo = registration.getZones(bankId);
        ArrayList zones = new ArrayList();
        MLIInfo mliInfo = new MLIInfo();
        int size = zoneInfo.size();
        if (size == 0) {
          zones.add("None");
        }
        else {
          zones.add("All");
        }

        for (int i = 0; i < size; i++) {
          mliInfo = (MLIInfo)zoneInfo.get(i);
          String zoneName = mliInfo.getZoneName();
          String zoneId = mliInfo.getZoneId();
          String branchId = mliInfo.getBranchId();
          String zone = zoneName + "(" + bankId + zoneId + branchId + ")";
          zones.add(zone);
          mliInfo = null;
        }

        dynaForm.set("zones", zones);
        zoneInfo = null;
      }
    }
    else if (((radio.equals("membersOfBranch")) || (radio.equals("noOfBranches"))) && 
      (bankId != null) && (!bankId.equals("")))
    {
      ArrayList branchInfo = registration.getAllBranches(bankId);
      ArrayList zoneInfo = registration.getZones(bankId);
      ArrayList branches = new ArrayList();
      int branchSize = branchInfo.size();
      int zoneSize = zoneInfo.size();
      if ((branchSize == 0) && (zoneSize == 0)) {
        branches.add("None");
      }
      else {
        branches.add("All");
      }
      for (int i = 0; i < branchSize; i++) {
        MLIInfo mliInfo = (MLIInfo)branchInfo.get(i);
        String branchName = mliInfo.getBranchName();
        String branchId = mliInfo.getBranchId();
        String zoneId = mliInfo.getZoneId();
        String branch = branchName + "(" + bankId + zoneId + branchId + ")";
        branches.add(branch);
      }
      for (int i = 0; i < zoneSize; i++) {
        MLIInfo mliInfo = (MLIInfo)zoneInfo.get(i);
        String zoneName = mliInfo.getZoneName();
        String zoneId = mliInfo.getZoneId();
        String branchId = mliInfo.getBranchId();
        String zone = zoneName + "(" + bankId + zoneId + branchId + ")";
        branches.add(zone);
        mliInfo = null;
      }

      dynaForm.set("branches", branches);
      branches = null;
      branchInfo = null;
    }

    registration = null;

    Log.log(4, "AdministrationAction", "actionTaken", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward flush(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "flush", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    String radio = (String)dynaForm.get("selectBM");

    ArrayList blank = new ArrayList();

    if ((radio.equals("membersOfZone")) || (radio.equals("noOfZones")) || (radio.equals("membersOfBranch")) || (radio.equals("noOfBranches")))
    {
      dynaForm.set("branches", blank);
      dynaForm.set("zones", blank);
    }
    else if ((radio.equals("AllHos")) || (radio.equals("membersOfBank")) || (radio.equals("noOfBank")) || (radio.equals("allMembers")))
    {
      dynaForm.set("bankName", "");
      dynaForm.set("zones", blank);
      dynaForm.set("branches", blank);
    }

    blank = null;

    Log.log(4, "AdministrationAction", "flush", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addBroadcastMessage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "addBroadcastMessage", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    Administrator administrator = new Administrator();
    BroadCastMessage broadCastMessage = new BroadCastMessage();

    String radio = (String)dynaForm.get("selectBM");
    String message = (String)dynaForm.get("broadcastMessage");
    broadCastMessage.setMessage(message);

    User user = getUserInformation(request);
    String createdBy = user.getUserId();

    if (radio.equals("AllHos")) {
      broadCastMessage.setAllHos(true);
    }
    else if (radio.equals("membersOfBank")) {
      broadCastMessage.setMemberOfBank(true);
      String bankName = (String)dynaForm.get("bankName");
      broadCastMessage.setBankName(bankName);
    }
    else if (radio.equals("membersOfZone"))
    {
      broadCastMessage.setMembersOfZoOrRO(true);

      String[] zoneNames = (String[])dynaForm.get("zoneRegionNames");

      if (zoneNames[0].equalsIgnoreCase("All"))
      {
        ArrayList allZones = new ArrayList();

        allZones = (ArrayList)dynaForm.get("zones");

        allZones.remove("All");

        int size = allZones.size();

        String[] zones = new String[size];

        for (int j = 0; j < size; j++)
        {
          zones[j] = ((String)allZones.get(j));
        }

        broadCastMessage.setZoneRegions(zones);
      }
      else
      {
        broadCastMessage.setZoneRegions(zoneNames);
      }
    }
    else if (radio.equals("membersOfBranch"))
    {
      broadCastMessage.setMembersOfBranch(true);

      String[] branchNames = (String[])dynaForm.get("branchNames");

      if (branchNames[0].equalsIgnoreCase("All"))
      {
        ArrayList allBranches = new ArrayList();

        allBranches = (ArrayList)dynaForm.get("branches");

        allBranches.remove("All");

        int size = allBranches.size();

        String[] branches = new String[size];

        for (int j = 0; j < size; j++)
        {
          branches[j] = ((String)allBranches.get(j));
        }

        broadCastMessage.setBranchNames(branches);
      }
      else
      {
        broadCastMessage.setBranchNames(branchNames);
      }

    }
    else if (radio.equals("noOfBank")) {
      broadCastMessage.setNoOfBank(true);
      String bankName = (String)dynaForm.get("bankName");
      broadCastMessage.setBankName(bankName);
    }
    else if (radio.equals("noOfZones"))
    {
      broadCastMessage.setNoOfZonesRegions(true);

      String[] zoneNames = (String[])dynaForm.get("zoneRegionNames");

      if (zoneNames[0].equalsIgnoreCase("All"))
      {
        ArrayList allZones = new ArrayList();

        allZones = (ArrayList)dynaForm.get("zones");

        allZones.remove("All");

        int size = allZones.size();

        String[] zones = new String[size];

        for (int j = 0; j < size; j++)
        {
          zones[j] = ((String)allZones.get(j));
        }

        broadCastMessage.setZoneRegions(zones);

        allZones.clear();

        allZones = null;
      }
      else
      {
        broadCastMessage.setZoneRegions(zoneNames);
      }

      zoneNames = null;
    }
    else if (radio.equals("noOfBranches"))
    {
      broadCastMessage.setNoOfBranches(true);

      String[] branchNames = (String[])dynaForm.get("branchNames");

      if (branchNames[0].equalsIgnoreCase("All"))
      {
        ArrayList allBranches = new ArrayList();

        allBranches = (ArrayList)dynaForm.get("branches");

        allBranches.remove("All");

        int size = allBranches.size();

        String[] branches = new String[size];

        for (int j = 0; j < size; j++)
        {
          branches[j] = ((String)allBranches.get(j));
        }

        broadCastMessage.setBranchNames(branches);

        allBranches.clear();

        allBranches = null;
      }
      else
      {
        broadCastMessage.setBranchNames(branchNames);
      }

      branchNames = null;
    }
    else if (radio.equals("allMembers"))
    {
      broadCastMessage.setAllMembers(true);
    }

    administrator.broadcastMessage(broadCastMessage, createdBy);

    administrator = null;
    broadCastMessage = null;
    request.setAttribute("message", "Message to be Broadcasted saved");
    Log.log(4, "AdministrationAction", "addBroadcastMessage", "Exited");

    return mapping.findForward("success");
  }

    public ActionForward uploadFile(
                                            ActionMapping mapping,
                                            ActionForm form,
                                            HttpServletRequest request,
                                            HttpServletResponse response)
                                            throws Exception
    {

            Log.log(Log.INFO,"AdministrationAction","uploadFile","Entered");

            DynaActionForm dynaForm=(DynaActionForm)form;

            String mliId="";
            String bankId="";
            String zoneId="";
            String branchId="";
            // ArrayList errorMessagesList=new ArrayList();


            if(dynaForm.get("selectMember")!=null && !(dynaForm.get("selectMember").equals("")))
            {

                    mliId=(String)dynaForm.get("selectMember");
                    ////System.out.println("mliId :" + mliId);
                    bankId=mliId.substring(0,4);
                    zoneId=mliId.substring(4,8);
                    branchId=mliId.substring(8,12);

            }else{

                    User user=getUserInformation(request);
                    bankId=user.getBankId();
                    zoneId=user.getZoneId();
                    branchId=user.getBranchId();

            }
            String forward="";

            FormFile formFile=(FormFile)dynaForm.get("uploadFile");

            if(formFile!=null)
            {
                    String contextPath=request.getSession(false).getServletContext().getRealPath("");
                    FileUploading fileUploading=new FileUploading(formFile,contextPath,request,bankId,zoneId,branchId);

                    // HttpSession session=request.getSession(false);
                    fileUploading.process();

                    ArrayList msgList = fileUploading.messagesList;

                    if(msgList!=null && msgList.size()!=0)
                    {
                            dynaForm.set("errorMessagesList",msgList);
                            forward="errorMessagesPage";

                    }else{
                            request.setAttribute("message","Details Uploaded Successfully");

                            forward="success";
                    }

                    //session.setAttribute("fileUploading",fileUploading);

                    //Thread thread=new Thread(fileUploading);
                    //thread.start();
                    //uploadFile(formFile,contextPath);

            }

            Log.log(Log.INFO,"AdministrationAction","uploadFile","Exited");

            return mapping.findForward(forward);
    }

  public ActionForward uploadFileNew(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "uploadFileNew", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    String mliId = "";
    String bankId = "";
    String zoneId = "";
    String branchId = "";

    if ((dynaForm.get("selectMember") != null) && (!dynaForm.get("selectMember").equals("")))
    {
      mliId = (String)dynaForm.get("selectMember");

      bankId = mliId.substring(0, 4);
      zoneId = mliId.substring(4, 8);
      branchId = mliId.substring(8, 12);
    }
    else
    {
      User user = getUserInformation(request);
      bankId = user.getBankId();
      zoneId = user.getZoneId();
      branchId = user.getBranchId();
    }

    String forward = "";
   // //System.out.println("MemberId:" + bankId + zoneId + branchId);
    FormFile formFile = (FormFile)dynaForm.get("uploadFile");

    if (formFile != null)
    {
      CommonDAO commonDAO = new CommonDAO();
      String contextPath = request.getSession(false).getServletContext().getRealPath("/") + "Uploaded Files";

      BufferedInputStream bis = null;
      DataInputStream dis = null;
      try {
    //    //System.out.println("File Name:" + formFile.getFileName());

        if (!formFile.getFileName().equals(""))
        {
          File fileToCreate = new File(contextPath, formFile.getFileName());

          if (!fileToCreate.exists()) {
            FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
            fileOutStream.write(formFile.getFileData());

            fileOutStream.flush();
            fileOutStream.close();
          }

        }

      }
      catch (FileNotFoundException e)
      {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

      forward = "success";
    }

    Log.log(4, "AdministrationAction", "uploadFileNew", "Exited");

    return mapping.findForward(forward);
  }

  public ActionForward getUsersWithNoRolesForMember(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "getUsersForMember", "Entered");

    Administrator admin = new Administrator();
    DynaActionForm dynaForm = (DynaActionForm)form;

    String memberId = (String)dynaForm.get("memberId");

    ArrayList userIds = admin.getUsersWithoutRolesAndPrivileges(memberId);
    HttpSession session = request.getSession(false);
    session.setAttribute("MemberSelected", "Y");
    dynaForm.set("activeUsers", userIds);

    userIds = null;

    Log.log(4, "AdministrationAction", "getUsersForMember", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward selectUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "selectUser", "Entered");

    DynaActionForm dynaActionForm = (DynaActionForm)form;

    dynaActionForm.initialize(mapping);

    Registration registration = new Registration();

    ArrayList members = registration.getAllMembers();

    ArrayList memberIds = new ArrayList(members.size());

    for (int i = 0; i < members.size(); i++)
    {
      MLIInfo mliInfo = (MLIInfo)members.get(i);

      memberIds.add(mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId());
    }

    memberIds.add("000000000000");

    dynaActionForm.set("memberIds", memberIds);

    registration = null;
    members = null;
    memberIds = null;

    Log.log(4, "AdministrationAction", "selectUser", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward dayEndProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "dayEndProcess", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    String[] dayEndProcesses = (String[])dynaForm.get("dayEndProcesses");

    int size = dayEndProcesses.length;

    Log.log(5, "AdministrationAction", "dayEndProcess", "size of day end process array  " + size);

    User user = getUserInformation(request);
    if (size != 0) {
      for (int i = 0; i < size; i++) {
        String optionChosen = dayEndProcesses[i];
        Log.log(5, "AdministrationAction", "dayEndProcess", "optionChosen is " + optionChosen);
        RpProcessor rpProcessor = new RpProcessor();
        ScheduledProcessManager scheduledProcessManager = new ScheduledProcessManager();

        if (optionChosen.equals("removeData")) {
          scheduledProcessManager.procRemDemoUser();
          Log.log(5, "AdministrationAction", "dayEndProcess", "Data entered by demo user removed from internet database ");
        }

        if (optionChosen.equals("loanClosing")) {
          scheduledProcessManager.updateAppExpiry();
          Log.log(5, "AdministrationAction", "dayEndProcess", "Loans at tenure end date closed ");
        }

        if (optionChosen.equals("moveData")) {
          scheduledProcessManager.adminMailInfo();
          scheduledProcessManager.tcOutstanding();
          scheduledProcessManager.wcOutstanding();
          scheduledProcessManager.dbrDetail();
          scheduledProcessManager.donorDetail();
          scheduledProcessManager.memberInfo();
          scheduledProcessManager.npaDetail();
          scheduledProcessManager.legalDetail();
          scheduledProcessManager.plr();
          scheduledProcessManager.recoveryDetail(user);
          scheduledProcessManager.recoveryActionDetail();
          scheduledProcessManager.repaymentDetail();

          scheduledProcessManager.userInfo();
          scheduledProcessManager.userRoles();
          scheduledProcessManager.userPrivileges();
          scheduledProcessManager.userActiveDeactiveLog();

          Log.log(5, "AdministrationAction", "dayEndProcess", "Data not requiring CGTSI approval moved from temporary database to Intranet database ");
        }

        if (optionChosen.equals("generateCGDan"))
        {
          rpProcessor.generateCGDAN(user, "All");

          Log.log(5, "AdministrationAction", "dayEndProcess", "generateCGDAN executed");
        }

        if (optionChosen.equals("generateCLDan"))
        {
          rpProcessor.generateCLDAN(user, null, null, null);

          Log.log(5, "AdministrationAction", "dayEndProcess", "generateCLDAN executed");
        }

        if (optionChosen.equals("generateSFDan"))
        {
          rpProcessor.generateSFDAN(user, null, null, null);

          Log.log(5, "AdministrationAction", "dayEndProcess", "generateSFDan executed");
        }

        if (optionChosen.equals("generateShortDan"))
        {
          rpProcessor.generateSHDAN(user, "All");

          Log.log(5, "AdministrationAction", "dayEndProcess", "generateShortDan executed");
        }
        if (optionChosen.equals("calculatePenalty"))
        {
          rpProcessor.calculatePenaltyForOverdueDANs(user);

          Log.log(5, "AdministrationAction", "dayEndProcess", "generateShortDan executed");
        }
        if (optionChosen.equals("batchProcess"))
        {
          Administrator admin = new Administrator();

          admin.batchProcess();

          Log.log(5, "AdministrationAction", "dayEndProcess", "batchProcess executed");
        }

        if (optionChosen.equals("archiveData"))
        {
          Administrator admin = new Administrator();

          admin.archiveData();

          Log.log(5, "AdministrationAction", "dayEndProcess", "batchProcess executed");
        }
      }

    }

    dayEndProcesses = null;
    user = null;
    request.setAttribute("message", "Day end processes performed");
    Log.log(4, "AdministrationAction", "dayEndProcess", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward getMliInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);

    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String forward;
    if (bankId.equals("0000"))
    {
      forward = "uploadMliPage";
    }
    else
    {
      MLIInfo mliInfo = getMemberInfo(request);
      bankId = mliInfo.getBankId();
      String branchId = mliInfo.getBranchId();
      String zoneId = mliInfo.getZoneId();

      forward = "uploadPage";
    }

    return mapping.findForward(forward);
  }

  public ActionForward getMliInfoNew(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
   // //System.out.println("getMliInfoNew Entered:");

    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String forward;
    if (bankId.equals("0000"))
    {
      forward = "uploadMliPage";
    }
    else
    {
      MLIInfo mliInfo = getMemberInfo(request);
      bankId = mliInfo.getBankId();
      String branchId = mliInfo.getBranchId();
      String zoneId = mliInfo.getZoneId();

      forward = "uploadPage";
    }

    return mapping.findForward(forward);
  }

  public ActionForward showFileUploadApp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    DynaActionForm dynaForm = (DynaActionForm)form;

    String memberId = (String)dynaForm.get("selectMember");

    Log.log(5, "ApplicationProcessingAction", "getApps", "MEmber Id :" + memberId);

    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);

    Registration registration = new Registration();

    MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId, branchId);

    if (mliInfo != null)
    {
      String statusFlag = mliInfo.getStatus();
      if (statusFlag.equals("I"))
      {
        throw new NoDataException("Member Id:" + memberId + "  has been deactivated.");
      }
    }

    ClaimsProcessor cpProcessor = new ClaimsProcessor();
    Vector memberIds = cpProcessor.getAllMemberIds();
    if (!memberIds.contains(memberId))
    {
      throw new NoMemberFoundException("The Member ID does not exist");
    }

    return mapping.findForward("uploadPage");
  }

  public ActionForward showFileUploadAppNew(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
  //  //System.out.println("showFileUploadAppNew Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;

    String memberId = (String)dynaForm.get("selectMember");

    Log.log(5, "ApplicationProcessingAction", "getApps", "MEmber Id :" + memberId);

    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);

    Registration registration = new Registration();

    MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId, branchId);

    if (mliInfo != null)
    {
      String statusFlag = mliInfo.getStatus();
      if (statusFlag.equals("I"))
      {
        throw new NoDataException("Member Id:" + memberId + "  has been deactivated.");
      }
    }

    ClaimsProcessor cpProcessor = new ClaimsProcessor();
    Vector memberIds = cpProcessor.getAllMemberIds();
    if (!memberIds.contains(memberId))
    {
      throw new NoMemberFoundException("The Member ID does not exist");
    }

    return mapping.findForward("uploadPage");
  }

  public ActionForward downloadThinClient(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "downloadThinClient", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();

    if (bankId.equals("0000")) {
      Log.log(4, "AdministrationAction", "downloadThinClient", "Exited");

      return mapping.findForward("CGTSIUser");
    }

    Registration registration = new Registration();
    MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId, branchId);
    String mcgfStatus = mliInfo.getSupportMCGF();
    dynaActionForm.set("MCGFStatus", mcgfStatus);
    Log.log(5, "AdministrationAction", "downloadThinClient", "mcgfStatus" + mcgfStatus);
    Log.log(4, "AdministrationAction", "downloadThinClient", "Exited");

    return mapping.findForward("MLIUser");
  }

  public ActionForward getMemberDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "getMemberDetails", "Entered");

    DynaActionForm dynaActionForm = (DynaActionForm)form;

    Registration registration = new Registration();

    String memberId = (String)dynaActionForm.get("memberId");

    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);

    MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId, branchId);

    Administrator admin = new Administrator();
    ArrayList states = admin.getAllStates();
    dynaActionForm.set("states", states);

    String state = mliInfo.getState();

    ArrayList districts = admin.getAllDistricts(state);
    dynaActionForm.set("districts", districts);

    BeanUtils.copyProperties(dynaActionForm, mliInfo);

    String reportingZoneId = mliInfo.getReportingZoneID();

    Log.log(5, "AdministrationAction", "getMemberDetails", "reportingZoneId " + reportingZoneId);

    if ((reportingZoneId == null) || (reportingZoneId.equals("")))
    {
      dynaActionForm.set("reportingZone", "NIL");
    }
    else
    {
      dynaActionForm.set("reportingZone", reportingZoneId);
    }

    String address = mliInfo.getAddress();
    dynaActionForm.set("address", address);

    Log.log(5, "AdministrationAction", "getMemberDetails", "address " + address);

    String mcgfOption = mliInfo.getSupportMCGF();

    Log.log(5, "AdministrationAction", "getMemberDetails", "mcgfOption " + mcgfOption);

    dynaActionForm.set("supportMCGF", mcgfOption);

    Log.log(5, "AdministrationAction", "getMemberDetails", "memberId " + memberId);

    Log.log(4, "AdministrationAction", "getMemberDetails", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateMemberDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "getMemberDetails", "Entered");

    DynaActionForm dynaActionForm = (DynaActionForm)form;

    String emailId = (String)dynaActionForm.get("emailId");

    MLIInfo mliDetails = new MLIInfo();

    BeanUtils.populate(mliDetails, dynaActionForm.getMap());

    String memberId = (String)dynaActionForm.get("memberId");

    String bankId = memberId.substring(0, 4);

    String zoneId = memberId.substring(4, 8);

    String branchId = memberId.substring(8, 12);

    mliDetails.setBankId(bankId);

    mliDetails.setZoneId(zoneId);

    mliDetails.setBranchId(branchId);

    mliDetails.setEmailId(emailId);

    Registration registration = new Registration();

    Log.log(5, "AdministrationAction", "updateMemberDetails", "memberId " + memberId);

    User user = getUserInformation(request);

    String memberBankId = user.getBankId();

    String createdby = user.getUserId();

    registration.updateMemberDetails(createdby, mliDetails);

    Log.log(4, "AdministrationAction", "getMemberDetails", "Exited");

    request.setAttribute("message", "Member Details Updated");

    return mapping.findForward("success");
  }

  public ActionForward updateMemberAddressDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "updateMemberAddressDetails", "Entered");

    DynaActionForm dynaActionForm = (DynaActionForm)form;

    String emailId = (String)dynaActionForm.get("emailId");

    MLIInfo mliDetails = new MLIInfo();

    BeanUtils.populate(mliDetails, dynaActionForm.getMap());

    String memberId = (String)dynaActionForm.get("memberId");

    String bankId = memberId.substring(0, 4);

    String zoneId = memberId.substring(4, 8);

    String branchId = memberId.substring(8, 12);

    mliDetails.setBankId(bankId);

    mliDetails.setZoneId(zoneId);

    mliDetails.setBranchId(branchId);

    mliDetails.setEmailId(emailId);

    Registration registration = new Registration();

    Log.log(5, "AdministrationAction", "updateMemberAddressDetails", "memberId " + memberId);

    User user = getUserInformation(request);

    String memberBankId = user.getBankId();

    String createdby = user.getUserId();
   // //System.out.println("Member Id:" + bankId.concat(zoneId).concat(branchId));
   // //System.out.println("Email Id:" + emailId);
   // //System.out.println("User Id:" + createdby);

    registration.updateMemberAddressDetails(createdby, mliDetails, emailId);

    Log.log(4, "AdministrationAction", "updateMemberAddressDetails", "Exited");

    request.setAttribute("message", "Member Details Updated");

    return mapping.findForward("success");
  }

  public ActionForward showModifyMLI(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showModifyMLI", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;
    dynaActionForm.initialize(mapping);

    User loggedUser = getUserInformation(request);
    String bankId = loggedUser.getBankId();
    String zoneId = loggedUser.getZoneId();
    String branchId = loggedUser.getBranchId();
    if ((bankId.equals("0000")) && (bankId.equals("0000")) && (bankId.equals("0000")))
    {
      return mapping.findForward("CGTSIUser");
    }

    String memberId = bankId + zoneId + branchId;
    dynaActionForm.set("memberId", memberId);

    Registration registration = new Registration();

    MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId, branchId);

    Administrator admin = new Administrator();
    ArrayList states = admin.getAllStates();
    dynaActionForm.set("states", states);

    String state = mliInfo.getState();

    ArrayList districts = admin.getAllDistricts(state);
    dynaActionForm.set("districts", districts);

    BeanUtils.copyProperties(dynaActionForm, mliInfo);

    String reportingZoneId = mliInfo.getReportingZoneID();

    Log.log(5, "AdministrationAction", "showModifyMLI", "reportingZoneId " + reportingZoneId);

    if ((reportingZoneId == null) || (reportingZoneId.equals("")))
    {
      dynaActionForm.set("reportingZone", "NIL");
    }
    else
    {
      dynaActionForm.set("reportingZone", reportingZoneId);
    }

    String address = mliInfo.getAddress();
    dynaActionForm.set("address", address);

    Log.log(5, "AdministrationAction", "showModifyMLI", "address " + address);

    String mcgfOption = mliInfo.getSupportMCGF();

    Log.log(5, "AdministrationAction", "showModifyMLI", "mcgfOption " + mcgfOption);

    dynaActionForm.set("supportMCGF", mcgfOption);

    Log.log(5, "AdministrationAction", "showModifyMLI", "memberId " + memberId);

    Log.log(4, "AdministrationAction", "showModifyMLI", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showModifyMLIAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showModifyMLIAddress", "Entered");
    DynaActionForm dynaActionForm = (DynaActionForm)form;
    dynaActionForm.initialize(mapping);
    HttpSession session = request.getSession(false);

    User loggedUser = getUserInformation(request);
    String bankId = loggedUser.getBankId();
    String zoneId = loggedUser.getZoneId();
    String branchId = loggedUser.getBranchId();
    if ((bankId.equals("0000")) && (bankId.equals("0000")) && (bankId.equals("0000")))
    {
      dynaActionForm.set("memberId", "");
      session.setAttribute("TARGET_URL", "selectMemberForUpdate.do?method=selectMemberForUpdate");

      return mapping.findForward("CGTSIUser");
    }

    String memberId = bankId + zoneId + branchId;
    dynaActionForm.set("memberId", memberId);

    Registration registration = new Registration();

    MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId, branchId);

    Administrator admin = new Administrator();
    ArrayList states = admin.getAllStates();
    dynaActionForm.set("states", states);

    String state = mliInfo.getState();

    ArrayList districts = admin.getAllDistricts(state);
    dynaActionForm.set("districts", districts);

    BeanUtils.copyProperties(dynaActionForm, mliInfo);

    String reportingZoneId = mliInfo.getReportingZoneID();

    Log.log(5, "AdministrationAction", "showModifyMLIAddress", "reportingZoneId " + reportingZoneId);

    if ((reportingZoneId == null) || (reportingZoneId.equals("")))
    {
      dynaActionForm.set("reportingZone", "NIL");
    }
    else
    {
      dynaActionForm.set("reportingZone", reportingZoneId);
    }

    String zoneName = mliInfo.getZoneName();
   // //System.out.println("zoneName:" + zoneName);
    if ((zoneName == null) || (zoneName.equals(""))) {
      dynaActionForm.set("zoneName", "NIL");
    }
    else {
      dynaActionForm.set("zoneName", zoneName);
    }
    String branchName = mliInfo.getBranchName();
  //  //System.out.println("branch Name:" + branchName);
    if ((branchName == null) || (branchName.equals(""))) {
      dynaActionForm.set("branchName", "NIL");
    }
    else {
      dynaActionForm.set("branchName", branchName);
    }

    String address = mliInfo.getAddress();
    dynaActionForm.set("address", address);

    Log.log(5, "AdministrationAction", "showModifyMLI", "address " + address);

    String mcgfOption = mliInfo.getSupportMCGF();

    Log.log(5, "AdministrationAction", "showModifyMLI", "mcgfOption " + mcgfOption);

    dynaActionForm.set("supportMCGF", mcgfOption);

    Log.log(5, "AdministrationAction", "showModifyMLI", "memberId " + memberId);

    Log.log(4, "AdministrationAction", "showModifyMLI", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward selectMemberForUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "selectMemberForUpdate", "Entered");
    Registration registration = new Registration();
    DynaActionForm dynaActionForm = (DynaActionForm)form;

    String memberId = (String)dynaActionForm.get("memberId");
    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);

    dynaActionForm.set("memberId", memberId);

    MLIInfo mliInfo = registration.getMemberDetails(bankId, zoneId, branchId);

    Administrator admin = new Administrator();
    ArrayList states = admin.getAllStates();
    dynaActionForm.set("states", states);

    String state = mliInfo.getState();

    ArrayList districts = admin.getAllDistricts(state);
    dynaActionForm.set("districts", districts);

    BeanUtils.copyProperties(dynaActionForm, mliInfo);

    String reportingZoneId = mliInfo.getReportingZoneID();

    Log.log(5, "AdministrationAction", "selectMemberForUpdate", "reportingZoneId " + reportingZoneId);

    if ((reportingZoneId == null) || (reportingZoneId.equals("")))
    {
      dynaActionForm.set("reportingZone", "NIL");
    }
    else
    {
      dynaActionForm.set("reportingZone", reportingZoneId);
    }

    String zoneName = mliInfo.getZoneName();
 //   //System.out.println("zoneName:" + zoneName);
    if ((zoneName == null) || (zoneName.equals(""))) {
      dynaActionForm.set("zoneName", "NIL");
    }
    else {
      dynaActionForm.set("zoneName", zoneName);
    }
    String branchName = mliInfo.getBranchName();
  //  //System.out.println("branch Name:" + branchName);
    if ((branchName == null) || (branchName.equals(""))) {
      dynaActionForm.set("branchName", "NIL");
    }
    else {
      dynaActionForm.set("branchName", branchName);
    }

    String address = mliInfo.getAddress();
    dynaActionForm.set("address", address);

    Log.log(5, "AdministrationAction", "selectMemberForUpdate", "address " + address);

    String mcgfOption = mliInfo.getSupportMCGF();

    Log.log(5, "AdministrationAction", "selectMemberForUpdate", "mcgfOption " + mcgfOption);

    dynaActionForm.set("supportMCGF", mcgfOption);

    Log.log(4, "AdministrationAction", "selectMemberForUpdate", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showUpdateState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showUpdateState", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    Administrator administrator = new Administrator();

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "0");

    ArrayList states = administrator.getAllStateCodes();
    dynaForm.set("states", states);

    administrator = null;

    Log.log(4, "AdministrationAction", "showUpdateState", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showStateName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showStateName", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    Administrator administrator = new Administrator();

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "1");

    String stateCode = (String)dynaForm.get("stateCode");

    String state = "";
    if (!stateCode.equals(""))
    {
      state = administrator.getStateName(stateCode);
    }
    dynaForm.set("stateName", state);

    administrator = null;

    Log.log(4, "AdministrationAction", "showStateName", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "updateState", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;

    Administrator administrator = new Administrator();

    StateMaster stateMaster = new StateMaster();

    BeanUtils.populate(stateMaster, dynaForm.getMap());

    User user = getUserInformation(request);
    String createdBy = user.getUserId();
    stateMaster.setCreatedBy(createdBy);

    administrator.modifyStateMaster(stateMaster);

    administrator = null;

    request.setAttribute("message", "State Updated successfully");

    Log.log(4, "AdministrationAction", "updateState", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showUpdateDistrict(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showUpdateDistrict", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    Administrator administrator = new Administrator();

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "0");

    ArrayList states = administrator.getAllStates();
    dynaForm.set("states", states);

    administrator = null;
    states = null;

    Log.log(4, "AdministrationAction", "showUpdateDistrict", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showDistricts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showDistricts", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    Administrator administrator = new Administrator();

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "1");

    String stateName = (String)dynaForm.get("stateName");
    ArrayList districtList = administrator.getAllDistricts(stateName);
    dynaForm.set("districts", districtList);

    administrator = null;
    districtList = null;

    Log.log(4, "AdministrationAction", "showDistricts", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showDistrictName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "showDistrictName", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;

    HttpSession session = request.getSession(false);
    session.setAttribute("modFlag", "2");

    String distName = (String)dynaForm.get("districtName");

    dynaForm.set("modDistrictName", distName);

    Log.log(4, "AdministrationAction", "showDistrictName", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward updateDistrict(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "AdministrationAction", "updateDistrict", "Entered");

    DynaActionForm dynaForm = (DynaActionForm)form;
    DistrictMaster districtMaster = new DistrictMaster();
    BeanUtils.populate(districtMaster, dynaForm.getMap());
    Administrator administrator = new Administrator();

    User user = getUserInformation(request);
    String createdBy = user.getUserId();
    districtMaster.setCreatedBy(createdBy);
    String modDistrict = (String)dynaForm.get("modDistrictName");

    administrator.modifyDistrictmaster(districtMaster, modDistrict);

    districtMaster = null;
    user = null;
    request.setAttribute("message", "District Updated Successfully");
    Log.log(4, "AdministrationAction", "updateDistrict", "Exited");

    return mapping.findForward("success");
  }
//Diksha 26/05/2017
  
  class FileUploading
  {
    private FormFile formFile = null;
    private String contextPath = null;
    private HttpServletRequest request = null;
    private String bankId = "";
    private String zoneId = "";
    private String branchId = "";
    private ArrayList messagesList = new ArrayList();

    public void setRequest(HttpServletRequest request)
    {
      Log.log(5, "AdministrationAction", "run", "Setting the request ");
      this.request = request;
    }

    FileUploading(FormFile formFile, String path, HttpServletRequest request, String memberBankId, String memberZoneId, String memberBranchId)
    {
      this.formFile = formFile;
      this.contextPath = path;
      this.request = request;
      this.bankId = memberBankId;
      this.zoneId = memberZoneId;
      this.branchId = memberBranchId;
      Log.log(5, "AdministrationAction", "run", "constructor request ... " + request);
    }

    public void process() {
      Log.log(5, "AdministrationAction", "run", "request ... " + this.request);

      HttpSession session = this.request.getSession(false);
      try
      {
        session.setAttribute("FILE_UPLOAD_STATUS", new Integer(-1));

        Log.log(5, "AdministrationAction", "run", "before uploading");
        Log.log(5, "AdministrationAction", "run", "context path  " + this.contextPath);

        File uploadedFile = AdministrationAction.this.uploadFile(this.formFile, this.contextPath);

        Log.log(5, "AdministrationAction", "run", "After uploading");

        String subMenuItem = (String)session.getAttribute("subMenuItem");

        Log.log(5, "AdministrationAction", "run", "subMenuItem " + subMenuItem);

        Log.log(5, "AdministrationAction", "run", "filename " + uploadedFile.getName());

        if (uploadedFile.getName().endsWith(".ARC"))
        {
          FileInputStream fileInput = new FileInputStream(uploadedFile);
          ObjectInputStream objInput = new ObjectInputStream(fileInput);
          Hashtable fileDetails = (Hashtable)objInput.readObject();

          objInput.close();
          objInput = null;
          fileInput.close();
          fileInput = null;

          File file = new File(this.contextPath + "\\Download\\Version.properties");
          Properties versionProp = new Properties();
          File versionFile = new File(file.getAbsolutePath());
          FileInputStream fin = new FileInputStream(versionFile);
          versionProp.load(fin);

          String version = versionProp.getProperty("version");

       //   //System.out.println("version " + version);

          String uploadVersion = (String)fileDetails.get("version");
          Log.log(5, "AdministrationAction", "run", "upload version " + uploadVersion);

          if (!version.equalsIgnoreCase(uploadVersion))
          {
            this.messagesList.add("ThinClient version has been changed. Please dowonload the latest version.");
          }
        }
        else
        {
          this.messagesList.add("Not a valid File for Upload.");
        }

        FileUploader fileUploader = new FileUploader();
        if (this.messagesList.size() == 0)
        {
          User user = AdministrationAction.this.getUserInformation(this.request);
          String userId = user.getUserId();

          ArrayList errorMessages = new ArrayList();

          if (subMenuItem.equals(MenuOptions.getMenu("THIN_CLIENT_AP_FILE_UPLOAD")))
          {
            Log.log(5, "AdministrationAction", "run", "Process applications");

            errorMessages = fileUploader.uploadFiles(uploadedFile, 1, userId, this.bankId, this.zoneId, this.branchId);
            this.messagesList = errorMessages;
          }
          else if (subMenuItem.equals(MenuOptions.getMenu("THIN_CLIENT_CP_FILE_UPLOAD")))
          {
            Log.log(5, "AdministrationAction", "run", "Process claim applications");

            errorMessages = fileUploader.uploadFiles(uploadedFile, 2, userId, this.bankId, this.zoneId, this.branchId);
            this.messagesList = errorMessages;
          //  //System.out.println(this.messagesList.size());
          }
          else if (subMenuItem.equals(MenuOptions.getMenu("THIN_CLIENT_GM_FILE_UPLOAD")))
          {
            Log.log(5, "AdministrationAction", "run", "Process periodic info");

            errorMessages = fileUploader.uploadFiles(uploadedFile, 3, userId, this.bankId, this.zoneId, this.branchId);
            this.messagesList = errorMessages;
          }
          else
          {
            Log.log(3, "AdministrationAction", "run", "Invalid entry. ignore");
          }
        }

        fileUploader = null;

        session.setAttribute("FILE_UPLOAD_STATUS", new Integer(0));

        Log.log(5, "AdministrationAction", "run", "After setting the upload status");
      }
      catch (Exception e)
      {
        Log.log(2, "AdministrationAction", "run", e.getMessage());
        Log.logException(e);
        session.setAttribute("FILE_UPLOAD_STATUS", new Integer(1));

        Log.log(5, "AdministrationAction", "run", "Constants.FILE_UPLOAD_FAILED 1");
      }
    }
  }

  //26/05/2017 end
  
  public ActionForward showRegistrationForm(ActionMapping mapping,ActionForm form,
		  HttpServletRequest request,HttpServletResponse response)throws Exception
  {
	  Log.log(5, "AdministrationAction", "showRegistrationForm", "Entered");
	  ////System.out.println("AA showRegistrationForm S");	  
	  HttpSession session = request.getSession(false);
	  AdministrationActionForm adminForm = (AdministrationActionForm)form;	  
	  User user = getUserInformation(request);
	  String bankId = user.getBankId();
	  String zoneId = user.getZoneId();
	  String branchId = user.getBranchId();
	  String memberId = bankId+zoneId+branchId;
	  ////System.out.println("AA memberId : "+memberId);
	  
	  Log.log(5, "AdministrationAction", "showRegistrationForm", "Exited");
	  ////System.out.println("AA showRegistrationForm E");
	  return mapping.findForward("registForm");
  }
  
  public ActionForward showRegistrationFormSubmit(ActionMapping mapping,ActionForm form,
		  HttpServletRequest request,HttpServletResponse response)throws Exception
  {
	  Log.log(5, "AdministrationAction", "showRegistrationFormSubmit", "Entered");	  
	  HttpSession session = request.getSession(false);
	  AdministrationActionForm adminForm = (AdministrationActionForm)form;
	  //adminForm.reset(mapping, request);
	  java.util.Date sysDate = new java.util.Date();
	  java.sql.Date sDate = new java.sql.Date(sysDate.getTime());
	  ////System.out.println("AA utilD : "+sysDate+ "\t sql Date : "+sDate);
	  Connection connection = DBConnection.getConnection(false);
	  PreparedStatement pst = null;
	  
      Statement statement=connection.createStatement();
      
      
      
      ResultSet rs=null;
      ResultSet rs1=null;
	  Statement st=null;
	  User user = getUserInformation(request);
	  String bankId = user.getBankId();
	  String zoneId = user.getZoneId();
	  String branchId = user.getBranchId();
	  String memberId = bankId+zoneId+branchId;
	  String usreId = user.getUserId();
	  ////System.out.println("AA memberId : "+memberId);
	  
//	  int dup=0;
//		 String userid="";
//		 int dupsecond=0;
		 
	
	  
	  String empFirstName =adminForm.getEmpFName().toUpperCase();
	  ////System.out.println("AA empFirstName : "+empFirstName);
	  
	  String empMiddleName = adminForm.getEmpMName().toUpperCase();
	  ////System.out.println("AA empMiddleName : "+empMiddleName);
	  
	  String empLastName = adminForm.getEmpLName().toUpperCase();
	  ////System.out.println("AA empLastName : "+empLastName);
	  
	  String empId = adminForm.getEmpId();
	  ////System.out.println("AA empId : "+empId);
	  
	  String empDesignation = adminForm.getDesignation().toUpperCase();
	  ////System.out.println("AA empDesignation : "+empDesignation);
	  
	  String phoneNo = adminForm.getPhoneNo();
	  ////System.out.println("AA phoneNo : "+phoneNo);
	  
	  String emailId = adminForm.getEmailId().toUpperCase();
	  ////System.out.println("AA emailId : "+emailId);
	  
	  String hintQues = adminForm.getHintQues();
	  ////System.out.println("AA hintQues : "+hintQues);
	  
	  String hintAns = adminForm.getHintAns();
	  String subject = "MLI checker User Registration from CGTMSE" ;
	//  String mailBody = "Dear User This is Your UserId Generated from CGTMSE "+empFirstName.substring(0,4).concat(empLastName.substring(0,4))+" ";
	  
	
	  ////System.out.println("AA subject : "+subject+ "\t mailBody : "+mailBody);
	  
	//  String mailBodyMli = "Dear MLI This is Your UserId Generated from CGTMSE" +
		//"  to your member id "+bankId.concat(zoneId).concat(branchId)+" is  " +
			//	" "+empFirstName.substring(0,4).concat(empLastName.substring(0,4))+" so please confirm";
	  
	  String mailBodyMli =  "Dear Sir/Madam,  \n \n This with reference to CGTMSE Circular no-111/2015-16 dated on 22/02/2016. \n Request for creating new user id has been submitted by user id "+usreId+"" +
		".\n Your New User Id created(Subject to HO verification) : "+empFirstName.substring(0,4).concat(empLastName.substring(0,4)).toUpperCase()+" \n First Name: "+empFirstName+" \n Middle Name : "+empMiddleName+"" +
			" \n Last Name: "+empLastName+"  \n Emp Id: "+empId+" \n Email Id: "+emailId+" \n Designation: "+empDesignation+"." +
					" \n The above Employee details are pending for verification at Head Office.Please  Approve/verify at  SysAdmin-->Approve MLI Checker User Ids, in our CGTMSE Portal.Post verification the new user id created will" +
					" be active and password will be generated. \n \n Regards,\n CGTMSE. ";
	//  //System.out.println("AA mailBodyMli : "+mailBodyMli);
	  String filePath = "";
	  
	  ArrayList emailids=new  ArrayList();
		//koteswr
	  
	  emailids.add(emailId);
	  
	  String dupQry="select count(*) from mli_checker_info   where  mem_bnk_id='"+bankId+"' and mem_zne_id='"+zoneId+"'  " +
		" and mem_brn_id='"+branchId+"'  and USR_STATUS in ('MO','CA','MA')  and  mem_bnk_id!='0063' ";
	  ////System.out.println("AA dupQry : "+dupQry);
	  rs=statement.executeQuery(dupQry); 
		while(rs.next())
		{
			if(rs.getInt(1)>0)			
			{				
				throw new MessageException("Please check you hava already given requst for user creation");
		}	
		}
	  
		String emailQry="select distinct MEM_EMAIL from member_info  where  mem_bnk_id='"+bankId+"' and mem_zne_id='0000'  " +
				" and mem_brn_id='0000'   and mem_status='A' ";
	
		rs=statement.executeQuery(emailQry); 
		while(rs.next())
		{
			////System.out.println("AA mail List : "+rs.getString(1));
			emailids.add(rs.getString(1));
		}		
	//changed	emailids.add(emailId);
	
		
	  
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String toadsDate = formatter.format(sDate);
		
	 java.util.Date nullval=null;
	 
	  String userID="";
		
	  
	  
	  Map<String,ArrayList<String>> userIDData=objExcelModuleMethods.userID(empFirstName.substring(0,4).concat(empLastName.substring(0,4)));
	  
		ArrayList secondaryData=(ArrayList)userIDData.get("secondaryUserIDData");
		ArrayList primaryData=(ArrayList)userIDData.get("primaryUserIDData");
		int batchSize = 10;
        int start = 0;
        int end = batchSize;
        ArrayList<String> groupeduserIDData=new ArrayList<String>();
		int count = secondaryData.size()/batchSize;
		//System.out.println("Count ="+count);
	innerLoop:    for(int i=0 ; i < count ; i ++)
		{
			    	for(int counter = start ; counter < end ; counter ++)
				    {			             	  
			    		groupeduserIDData.add(secondaryData.get(counter).toString());				    	      
			    	      
				    }	
					String query="select rtrim(ltrim(a)) from "+
					" (   select ltrim(rtrim(regexp_substr('"+primaryData.toString().replace("[", "").replace("]", "")+"','[^,]+', 1, level))) a from dual "+
					"   connect by regexp_substr('"+primaryData.toString().replace("[", "").replace("]", "")+"', '[^,]+', 1, level) is not null  "+
					"         minus  "+
					"     (select rtrim(ltrim(usr_id)) from user_info "+
					"     union all "+
					"     select rtrim(ltrim(usr_id)) from mli_checker_info)  "+
					"     ) where rownum <2";
				
					 LogClass.StepWritterForXXXModule("showRegistrationFormSubmit query="+query ,"checkerMakerDub.txt");
					 
				    st=connection.createStatement();
					rs1=st.executeQuery(query);
					  
					//System.out.println("showRegistrationFormSubmit query="+query);						
					if(rs1.next())
					{
						 userID=rs1.getString(1);		
						 LogClass.StepWritterForXXXModule("showRegistrationFormSubmit userID="+userID+"=","checkerMakerDub.txt");
						 groupeduserIDData.clear();
						 primaryData.clear();
						 secondaryData.clear();
						 //System.out.println("showRegistrationFormSubmit userID : "+userID);
						 break innerLoop;
					}
					else
					{
						primaryData.clear();
						primaryData= new ArrayList<String>(groupeduserIDData);
					}
					  
				    start = start + batchSize;
				    end = end + batchSize;		
		 }

	 
	 
	  
	  String npaQury = "INSERT INTO mli_checker_info (USR_ID, MEM_BNK_ID, MEM_ZNE_ID, MEM_BRN_ID, USR_FIRST_NAME, USR_MIDDLE_NAME, USR_LAST_NAME, USR_EMP_ID, USR_DSG_NAME, USR_PHONE_NO," +
		" USR_EMAIL_ID, USR_HINT_QUESTION, USR_HINT_ANSWER, USR_STATUS, USR_FIRST_LOGIN, USR_CREATED_MODIFIED_BY, USR_CREATED_MODIFIED_DT, MLI_CHECKER_ID,USR_PWD_CHANGED_ON,USR_UNSUCCESSFUL_LOGIN_ATMPTS,USR_LAST_LOGIN_DT)" +
		" VALUES('"+userID.trim()+"','"+bankId+"','"+zoneId+"','"+branchId+"','"+empFirstName+"','"+empMiddleName+"'," +
				"'"+empLastName+"','"+empId+"','"+empDesignation+"','"+phoneNo+"','"+emailId+"'" +
				",'"+hintQues+"','"+hintAns+"','MO','N','"+usreId+"',to_char(sysdate),seq_mli_checker.NEXTVAL,"+nullval+",0,"+nullval+")";
	 
	 
	  String mailBody = "Dear Sir/Madam,  \n \n This with reference to CGTMSE Circular no-111/2015-16 dated on 22/02/2016 .\n Request for creating new user id has been submitted by user id "+usreId+"" +
		".\n Your New User Id created(Subject to HO verification) : "+userID.trim().toUpperCase()+" \n First Name: "+empFirstName+" \n Middle Name : "+empMiddleName+"" +
				" \n Last Name: "+empLastName+" \n Emp Id: "+empId+" \n Email Id: "+emailId+" \n Designation: "+empDesignation+"." +
						" \n The above Employee details are pending for verification at Head Office.Post verification the new user id created will be active " +
						"and password will be sent to you.\n \n  Regards\n CGTMSE. ";

	  try
	  {
		 int resno=statement.executeUpdate(npaQury);
		  if(resno > 0)
		  {
			  connection.commit();
			  sendMailBisenessLogic(subject, mailBody, emailids, filePath,mailBodyMli);
			  request.setAttribute("message","<b>The Requested Form Submitted.</b><br>");
		  }
		  else
		  {
			  connection.rollback();
		  }		
			 
	  }
	  catch(Exception e)
	  {
		  //request.setAttribute("message","<b>A Problem Occured While Inserting the Records.</b><br>"+e.getMessages());
		  throw new DatabaseException("A Problem Occured While Inserting the Records"+e.getMessage());
	  }	  	 
      finally
	  {
		  DBConnection.freeConnection(connection);
	  }
	  adminForm.reset(mapping, request);
	  Log.log(5, "AdministrationAction", "showRegistrationFormSubmit", "Exited");
	  return mapping.findForward("success");
  }
//mail send start
  public boolean sendMailBisenessLogic(String mailSubject,String mailBody1, ArrayList mailIDs , String FilePath,String mailBodyMli )
	{
		//LogClass.StepWritter("In side sendMailBisenessLogic"+mailID);
	  ////System.out.println("AA sendMailBisenessLogic S");
		////System.out.println("sendMailBisenessLogic called"+mailIDs.size());
		////System.out.println("AA mailBody1 : "+mailBody1);
		////System.out.println("AA mailBodyMli : "+mailBodyMli);
		boolean sendMailStatus=true;
		try
		{
			String  mailBody="";
			int emilids=mailIDs.size()-1;
			//int emilids=mailIDs.size();
			
			
			for(int i=0;i<=emilids;i++)
			{
				if(i==0)
				{
					
					//mailBody=mailBody1;		
					mailBody=mailBody1;
				}
				else
				{
					
					//mailBody=mailBodyMli;	
					mailBody=mailBodyMli;
				}
			String	mailID=(String)mailIDs.get(i);
			////System.out.println("AA mailID : "+mailID);
			
			String host = "192.168.10.118";
			
			boolean sessionDebug = false;			
			Properties props = System.getProperties();
		    props.put("mail.host", host);
		    props.put("mail.transport.protocol", "smtp");
		    props.put("mail.smtp.host", host);
		    props.put("mail.from", "support@cgtmse.in");
			
			Session session1= null;
	        session1 = Session.getDefaultInstance(props, null);
	        session1.setDebug(sessionDebug);
	               
	        javax.mail.internet.MimeMessage msg = new javax.mail.internet.MimeMessage(session1);
	        msg.setFrom(new javax.mail.internet.InternetAddress("support@cgtmse.in"));
	     //   //System.out.println("AA send Mail new : "+mailID);
	        javax.mail.internet.InternetAddress[] Toaddress = {new javax.mail.internet.InternetAddress(mailID)};     
	        msg.setRecipients(javax.mail.Message.RecipientType.TO, Toaddress);
	        msg.setSubject(mailSubject);
	        msg.setSentDate(new Date());
	           
	      //  //System.out.println(" AA send Mail new1 : "+mailID);
          
          if(!FilePath.equals(""))
          {
        	 // //System.out.println(" FilePath : "+mailID);
          	  	BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(mailBody);
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                messageBodyPart = new MimeBodyPart();
	            DataSource source = new FileDataSource(FilePath);
	            messageBodyPart.setDataHandler(new DataHandler(source));
	            messageBodyPart.setFileName(FilePath);
	            multipart.addBodyPart(messageBodyPart);
	            msg.setContent(multipart);
          }
          else
          {
        	  
        	//  //System.out.println(" else : "+mailID);
          	msg.setText(mailBody);
          }          
      //    //System.out.println(" before  send to   mailID : "+mailID);
       Transport.send(msg);
        
      //  //System.out.println(" after mail send to   mailID : "+mailID);
        
		}  
			//  //System.out.println(" after mail send to   mailID1 :");
		}
		catch(Exception e)
		{
			sendMailStatus=false;
		//	 //System.out.println(" error from catch is"+e.getMessage()
				//	 );
			 
			// //System.out.println(" error in send mail");
		}
	//	 //System.out.println(" sendMailStatus : "+sendMailStatus);
		return sendMailStatus;
	}
  //mail send end
  
  //display registration form data as list
  public ActionForward showApprRegistrationForm(ActionMapping mapping,ActionForm form,
		  HttpServletRequest request,HttpServletResponse response)throws Exception
  {
	  Log.log(5, "AdministrationAction", "showApprRegistrationForm", "Entered");
	  HttpSession session = request.getSession(false);	  	 
	  AdministrationActionForm adminForm = (AdministrationActionForm)form;
	  User user = getUserInformation(request);
	  String bankId = user.getBankId();
	  String zoneId = user.getZoneId();
	  String branchId = user.getBranchId();
	  String memberId = bankId+zoneId+branchId;
	  ////System.out.println("AA memberId : "+memberId);
	  ArrayList formList = displayNpaRegistrationFormList(memberId);
	  
	  String forward = "";
	  if(formList == null || formList.size() == 0)
	  {
		  request.setAttribute("message", "No Applications Available For Approval");
		  forward = "success";
	  }
	  else
	  {
		  adminForm.setNpaRegistFormList(formList);
		  forward = "npaRegistList";
	  }
	
	  Log.log(5, "AdministrationAction", "showApprRegistrationForm", "Exited");
	  return mapping.findForward(forward);
  }
  
  public ArrayList displayNpaRegistrationFormList(String memberId)throws DatabaseException
  {
	  Log.log(5, "AdministrationAction", "displayNpaRegistrationFormList", "Entered");
	  ////System.out.println("AA displayNpaRegistrationFormList S : "+memberId);
	  ArrayList NpaRegistList = new ArrayList();
	  AdministrationActionForm npaRegistForm = null;
	  Connection connection = DBConnection.getConnection(false);
	  ResultSet rs = null;
	  PreparedStatement pst = null;
	  String bankid=memberId.substring(0,4);
	  String npaRegistQuery = "SELECT MI.MEM_BNK_ID || MI.MEM_ZNE_ID || MI.MEM_BRN_ID, MEM_ZONE_NAME, USR_FIRST_NAME, USR_MIDDLE_NAME, USR_LAST_NAME, USR_EMP_ID, USR_DSG_NAME, USR_PHONE_NO, USR_EMAIL_ID, MLI_CHECKER_ID" +
	  		" FROM member_info mi, mli_checker_info mci" +
	  		" WHERE mi.mem_bnk_id || mi.mem_zne_id || mi.mem_brn_id = mci.mem_bnk_id || mci.mem_zne_id || mci.mem_brn_id" +
	  		" AND MCI.USR_STATUS = 'MO' AND mi.MEM_STATUS = 'A'" +
	  		" AND mci.mem_bnk_id = ?";
	  		//" AND mci.mem_bnk_id || mci.mem_zne_id || mci.mem_brn_id = ?";
	  		
	  try
	  {		  
		  pst = connection.prepareStatement(npaRegistQuery);
		  pst.setString(1, bankid);
		  ////System.out.println("ÄA npaRegistQuery : "+npaRegistQuery);
		  rs = pst.executeQuery();
		  while(rs.next())
		  {
			  npaRegistForm = new AdministrationActionForm();
			  npaRegistForm.setMemberId(rs.getString(1));
			  ////System.out.println("AA MLI Id : "+rs.getString(1));
			  
			  npaRegistForm.setZoneName(rs.getString(2));
			  ////System.out.println("AA Zone Name : "+rs.getString(2));
			  
			  npaRegistForm.setEmpFName(rs.getString(3));
			  ////System.out.println("AA Fname : "+rs.getString(3));
			  
			  npaRegistForm.setEmpMName(rs.getString(4));
			  ////System.out.println("AA Mname : "+rs.getString(4));
			  
			  npaRegistForm.setEmpLName(rs.getString(5));
			  ////System.out.println("AA Lname : "+rs.getString(5));
			  
			  npaRegistForm.setEmpId(rs.getString(6));
			  ////System.out.println("AA EID : "+rs.getString(6));
			  
			  npaRegistForm.setDesignation(rs.getString(7));
			  ////System.out.println("AA Designation : "+rs.getString(7));
			  
			  npaRegistForm.setPhoneNo(rs.getString(8));
			  ////System.out.println("AA Phone No : "+rs.getString(8));
			  
			  npaRegistForm.setEmailId(rs.getString(9));
			  ////System.out.println("AA EMAIL_ID : "+rs.getString(9));
			  
			  npaRegistForm.setCheckerId(rs.getString(10));
			  ////System.out.println("AA checker ID : "+rs.getString(10));
			  
			  NpaRegistList.add(npaRegistForm);
		  }
		  connection.commit();
		  pst.close();
		  pst = null;
		  rs.close();
		  rs = null;
	  }
	  catch(Exception sql)
	  {
		  sql.printStackTrace();
	  }
	  finally
	  {
		  DBConnection.freeConnection(connection);
	  }
	  ////System.out.println("AA displayNpaRegistrationFormList E");
	  Log.log(5, "AdministrationAction", "displayNpaRegistrationFormList", "Exited");
	  return NpaRegistList;
  }
  
  public ActionForward showApprRegistrationFormSubmit(ActionMapping mapping,ActionForm form,
		  HttpServletRequest request,HttpServletResponse response)throws Exception
  {
	  Log.log(5, "AdministrationAction", "showApprRegistrationFormSubmit", "Entered");
	  HttpSession session = request.getSession(false);
	  Connection connection = DBConnection.getConnection(false);
	  PreparedStatement pst = null;
	  String approvalQry = "";
	  String checkId = "";
	  String empCommentVal = "";
	  AdministrationActionForm apprNpaForm = (AdministrationActionForm)form;
	  String action = request.getParameter("action");
	  ////System.out.println("AA action : "+action);
	  Map empComment = apprNpaForm.getEmpComments();;
	  ////System.out.println("AA empComment : "+empComment);
	  String checkIdArr[] = apprNpaForm.getCheck();
		ResultSet rs = null;
		Statement statement = null;
	  
		String	emailid="";
	  try
	  {
		  for(int i=0; i<checkIdArr.length; i++)
		  {
			  checkId = checkIdArr[i];
			  empCommentVal = (String)empComment.get(checkId);
			  ////System.out.println("AA checkId : "+checkId+ "\t empCommentVal : "+empCommentVal);
		  //}
		  if(action.equals("update"))
		  {
			  approvalQry = " UPDATE mli_checker_info SET USR_STATUS = 'MA',mli_approvar_remarks='"+empCommentVal+"'  where  MLI_CHECKER_ID = ?";
			  request.setAttribute("message", "<b>The Request For Submission Completed.</b>");
		  }
		  else if(action.equals("delete"))
		  {
			//  approvalQry = " UPDATE mli_checker_info SET USR_STATUS = 'MR' where  MLI_CHECKER_ID = ?";
			  statement = connection.createStatement();
				  
			  String emailQry = "select distinct USR_EMAIL_ID  from mli_checker_info where  MLI_CHECKER_ID='"+checkId+"'   ";
				
			rs = statement.executeQuery(emailQry);
			while (rs.next()) {
				emailid = rs.getString(1);
							
			
			}
						
			String subject = "Rejection of Checker User Id From HO";
			//	String mailBody = "Dear User, \n \n This is Your Password for the new User Id :"+usrIdqry+" has been generated from CGTMSE. \n" +
						//" Your Password is : "+password+" '\n \n Regards, \n CGTMSE.";
				
				String mailBody = "Dear User, \n \n Sorry,\n Your request for creation of Checker User Id  has been rejected by your HO due to the reason "+empCommentVal+" .\n You are requested to your operating level Officer to resubmit the request \n for generation of Checker User Id with correct data. \n \n Regards, \n CGTMSE.   " ;
			  
			  
			  String host = "192.168.10.118";
				boolean sessionDebug = false;
				Properties props = System.getProperties();
				props.put("mail.host", host);
				props.put("mail.transport.protocol", "smtp");
				props.put("mail.smtp.host", host);
				props.put("mail.from", "support@cgtmse.in");

				Session session1 = null;
				session1 = Session.getDefaultInstance(props, null);
				session1.setDebug(sessionDebug);

				javax.mail.internet.MimeMessage msg = new javax.mail.internet.MimeMessage(
						session1);
				msg.setFrom(new javax.mail.internet.InternetAddress("support@cgtmse.in"));
				////System.out.println("GMA emailid send mail : "+emailid);
				javax.mail.internet.InternetAddress[] Toaddress = { new javax.mail.internet.InternetAddress(emailid) };
				msg.setRecipients(javax.mail.Message.RecipientType.TO,Toaddress);
				msg.setSubject(subject);
				msg.setSentDate(new Date());
            //	//System.out.println("GMA else : "+mailBody);
					msg.setText(mailBody);
			
				Transport.send(msg);
			  
				// approvalQry = " delete  mli_checker_info  where  MLI_CHECKER_ID = ?";
				 
				 
				 approvalQry =	 " UPDATE mli_checker_info SET USR_STATUS = 'MR',mli_approvar_remarks='"+empCommentVal+"'  where  MLI_CHECKER_ID = ?   ";
			  
			  
			  
			//String delqry   = " delete  mli_checker_info  where  MLI_CHECKER_ID = ?";
			  request.setAttribute("message", "<b>The Request For Deletion Completed.</b>");
		  }
		  else
		  {
			  request.setAttribute("message", "<b>A Problem Occured While Performing Action.</b>");
		  }
		  
		  pst = connection.prepareStatement(approvalQry);
		  pst.setString(1, checkId);
		  int counter = pst.executeUpdate();
		  
		  if(counter > 0)
		  {
			  connection.commit();
		  }
		  else
		  {
			  connection.rollback();			  
		  }
		  }
	  }
	  catch(Exception sql)
	  {
		  sql.printStackTrace();
	  }
	  finally
	  {
		  if(pst != null)
		  {
			  try{pst.close();}
			  catch(SQLException s){s.printStackTrace();}
		  }
		  DBConnection.freeConnection(connection);
	  }
	  Log.log(5, "AdministrationAction", "showApprRegistrationFormSubmit", "Exited");	  
	  return mapping.findForward("success");
  }
  
  
  public ActionForward sendQueryRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  throws Exception
{
  Log.log(4, "AdministrationAction", "showSendMail", "Entered");

  HttpSession session = request.getSession(false);

  session.setAttribute("adminMail", null);

  DynaActionForm dynaForm = (DynaActionForm)form;

  dynaForm.initialize(mapping);

  User user = getUserInformation(request);

  String bankId = user.getBankId();

  Registration registration = new Registration();
  ArrayList cgtDepartments = null;

 // if (bankId.equals("0000"))
 // {
	 cgtDepartments = registration.getDepartments();
  //}

  ArrayList Department = new ArrayList();
  if (cgtDepartments != null)
  {
    for (int i = 0; i < cgtDepartments.size(); i++)
    {
      MLIInfo mliInfo = (MLIInfo)cgtDepartments.get(i);

      String department = mliInfo.getDepartment();
  	////System.out.println("department1");
  	//System.out.println(department);

      Department.add(department);

      mliInfo = null;
    }

  }

 // memberIds.add("000000000000");

  dynaForm.set("Departments", Department);

  Department = null;
 // members = null;
  user = null;
  registration = null;

  Log.log(4, "AdministrationAction", "showSendMail", "Exited");

  return mapping.findForward("success");
}

  
  
  public ActionForward insertQueryRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  throws Exception
{
	  
	  
	  String url=request.getRequestURL().toString();
	  
	

  DynaActionForm dynaForm = (DynaActionForm)form;
  Message message = new Message();

  MLIInfo mliInfo = new MLIInfo();
  
  Administrator admin = new Administrator();
  
  User user = getUserInformation(request);
  
 

  String bankId = user.getBankId();
  
  String userid = user.getUserId();
  
  String zoneId = user.getZoneId();
  
  String branchId = user.getBranchId();
  
    String contPerson=(String) dynaForm.get("contPerson");
  
  String phoneNo=(String) dynaForm.get("phoneNo");
  
  
  
  String emaiId=(String) dynaForm.get("eMail");
  
  String department=(String) dynaForm.get("department");
  
  String queryDesc=(String) dynaForm.get("qryDesc");
  
  
  
 // //System.out.println("mobile number is"+phoneNo);
  
  Registration registration = new Registration();
  
 // //System.out.println("emaiId  is"+emaiId);
  
  
ArrayList emailids=new ArrayList();
  
  mliInfo.setBankId(bankId);
  
  mliInfo.setZoneId(zoneId);
  
  mliInfo.setBranchId(branchId);
  
  mliInfo.setUserid(userid);
  
  
  mliInfo.setContPerson(contPerson);
  
  mliInfo.setPhone(phoneNo);  
  
  mliInfo.setEmailId(emaiId); 
  
  mliInfo.setDepartment(department);
  
  mliInfo.setQueryDesc(queryDesc);
 
  String complaintid=admin.insertQryDetails(mliInfo);
  emailids.add(emaiId);
  
  ////System.out.println("complaintid  is"+complaintid);
  
  
  ////System.out.println("department  is"+department);
 
  admin.sendMailtoMli(department,emailids,complaintid);
  
  
	
  request.setAttribute("message", "Your Query id is "+complaintid+". We will revert you by TeleCall/Mail soon. ");
  

  return mapping.findForward("complaintid");
}

}