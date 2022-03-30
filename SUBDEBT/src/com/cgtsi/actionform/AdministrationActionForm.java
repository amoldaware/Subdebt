// FrontEnd Plus GUI for JAD
// DeCompiled : AdministrationActionForm.class

package com.cgtsi.actionform;

import com.cgtsi.admin.PLRMaster;
import com.cgtsi.common.Log;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;
import org.apache.struts.validator.ValidatorActionForm;

public class AdministrationActionForm extends ValidatorActionForm
{

    private Map roles;
    private Map privileges;
    private String userId;
    private ArrayList roleNames;
    private String memberId;
    private ArrayList plrBanks;
    private Map plrMasters;
    private String shortNameMemId;
    private String bankName;
    private String zoneName;
    private String branchName;
    private ArrayList plrDetails;
    private int plrIndexValue;
    private PLRMaster plrMaster;
    private String empFName;
    private String empMName;
    private String empLName;
    private String empId;
    private String designation;
    private String phoneNo;
    private String emailId;
    private String hintQues;
    private String hintAns;
    private ArrayList npaRegistFormList;
    private String selectAll;
    private String check[];
    private String checkerId;
    private Map empComments;
    private Date dateOfTheDocument;
    private Date dateOfTheDocument1;
    private String check1;
    private ArrayList mliQueryList;
    private String queryStatus;
    private String departments;
    private String queryDescription;
    private String bankId;
    private String zoneId;
    private String branchId;
    private String queryResponse;
    private String queryId;
    private String contPerson;
    private float regFees; 
    

	//Diksha
    private String smtpHost;
    private String smtpUserId;
    private String smtpPassword;
    private String smtpPort;
    
    
    public float getRegFees() {
		return regFees;
	}

	public void setRegFees(float regFees) {
		this.regFees = regFees;
	}
    public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public String getSmtpUserId() {
		return smtpUserId;
	}

	public void setSmtpUserId(String smtpUserId) {
		this.smtpUserId = smtpUserId;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public Date getDateOfTheDocument()
    {
        return dateOfTheDocument;
    }

    public void setDateOfTheDocument(Date dateOfTheDocument)
    {
        this.dateOfTheDocument = dateOfTheDocument;
    }

    public Date getDateOfTheDocument1()
    {
        return dateOfTheDocument1;
    }

    public void setDateOfTheDocument1(Date dateOfTheDocument1)
    {
        this.dateOfTheDocument1 = dateOfTheDocument1;
    }

    public String getCheck1()
    {
        return check1;
    }

    public void setCheck1(String check1)
    {
        this.check1 = check1;
    }

    public ArrayList getMliQueryList()
    {
        return mliQueryList;
    }

    public void setMliQueryList(ArrayList mliQueryList)
    {
        this.mliQueryList = mliQueryList;
    }

    public String getQueryStatus()
    {
        return queryStatus;
    }

    public void setQueryStatus(String queryStatus)
    {
        this.queryStatus = queryStatus;
    }

    public String getDepartments()
    {
        return departments;
    }

    public void setDepartments(String departments)
    {
        this.departments = departments;
    }

    public String getQueryDescription()
    {
        return queryDescription;
    }

    public void setQueryDescription(String queryDescription)
    {
        this.queryDescription = queryDescription;
    }

    public String getBankId()
    {
        return bankId;
    }

    public void setBankId(String bankId)
    {
        this.bankId = bankId;
    }

    public String getZoneId()
    {
        return zoneId;
    }

    public void setZoneId(String zoneId)
    {
        this.zoneId = zoneId;
    }

    public String getBranchId()
    {
        return branchId;
    }

    public void setBranchId(String branchId)
    {
        this.branchId = branchId;
    }

    public String getQueryResponse()
    {
        return queryResponse;
    }

    public void setQueryResponse(String queryResponse)
    {
        this.queryResponse = queryResponse;
    }

    public String getQueryId()
    {
        return queryId;
    }

    public void setQueryId(String queryId)
    {
        this.queryId = queryId;
    }

    public String getContPerson()
    {
        return contPerson;
    }

    public void setContPerson(String contPerson)
    {
        this.contPerson = contPerson;
    }

    public AdministrationActionForm()
    {
        roles = new HashMap();
        privileges = new HashMap();
        userId = "";
        roleNames = new ArrayList();
        memberId = "";
        plrMasters = new HashMap();
        plrMaster = null;
        empComments = new TreeMap();
    }

    public Map getEmpComments()
    {
        return empComments;
    }

    public void setEmpComments(Map empComments)
    {
        this.empComments = empComments;
    }

    public String getCheckerId()
    {
        return checkerId;
    }

    public void setCheckerId(String checkerId)
    {
        this.checkerId = checkerId;
    }

    public String[] getCheck()
    {
        return check;
    }

    public void setCheck(String check[])
    {
        this.check = check;
    }

    public String getSelectAll()
    {
        return selectAll;
    }

    public void setSelectAll(String selectAll)
    {
        this.selectAll = selectAll;
    }

    public ArrayList getNpaRegistFormList()
    {
        return npaRegistFormList;
    }

    public void setNpaRegistFormList(ArrayList npaRegistFormList)
    {
        this.npaRegistFormList = npaRegistFormList;
    }

    public String getHintQues()
    {
        return hintQues;
    }

    public void setHintQues(String hintQues)
    {
        this.hintQues = hintQues;
    }

    public String getHintAns()
    {
        return hintAns;
    }

    public void setHintAns(String hintAns)
    {
        this.hintAns = hintAns;
    }

    public String getEmpFName()
    {
        return empFName;
    }

    public void setEmpFName(String empFName)
    {
        this.empFName = empFName;
    }

    public String getEmpMName()
    {
        return empMName;
    }

    public void setEmpMName(String empMName)
    {
        this.empMName = empMName;
    }

    public String getEmpLName()
    {
        return empLName;
    }

    public void setEmpLName(String empLName)
    {
        this.empLName = empLName;
    }

    public String getEmpId()
    {
        return empId;
    }

    public void setEmpId(String empId)
    {
        this.empId = empId;
    }

    public String getDesignation()
    {
        return designation;
    }

    public void setDesignation(String designation)
    {
        this.designation = designation;
    }

    public String getPhoneNo()
    {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo)
    {
        this.phoneNo = phoneNo;
    }

    public String getEmailId()
    {
        return emailId;
    }

    public void setEmailId(String emailId)
    {
        this.emailId = emailId;
    }

    public Map getPlrMasters()
    {
        return plrMasters;
    }

    public void setPlrMasters(Map object)
    {
        plrMasters = object;
    }

    public Object getPlrMasters(String key)
    {
        return plrMasters.get(key);
    }

    public void setPlrMasters(String key, Object value)
    {
        Log.log(2, "AdministrationActionForm", "setPlrMasters", (new StringBuilder("Printing key :")).append(key).append(" value :").append(value).toString());
        plrMasters.put(key, value);
    }

    public String getZoneName()
    {
        return zoneName;
    }

    public void setZoneName(String zoneName)
    {
        this.zoneName = zoneName;
    }

    public String getBranchName()
    {
        return branchName;
    }

    public void setBranchName(String branchName)
    {
        this.branchName = branchName;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public void setRole(String key, Object value)
    {
        roles.put(key, value);
    }

    public void setPrivilege(String key, Object value)
    {
        privileges.put(key, value);
    }

    public Object getRole(String key)
    {
        return roles.get(key);
    }

    public Object getPrivilege(String key)
    {
        return privileges.get(key);
    }

    public Map getRoles()
    {
        return roles;
    }

    public Map getPrivileges()
    {
        return privileges;
    }

    public void reset(ActionMapping arg0, HttpServletRequest arg1)
    {
        super.reset(arg0, arg1);
        roles.clear();
        privileges.clear();
    }

    public ArrayList getRoleNames()
    {
        return roleNames;
    }

    public void setPrivileges(Map map)
    {
        privileges = map;
    }

    public void setRoleNames(ArrayList list)
    {
        roleNames = list;
    }

    public void setRoles(Map map)
    {
        roles = map;
    }

    public String getMemberId()
    {
        return memberId;
    }

    public void setMemberId(String string)
    {
        memberId = string;
    }

    public ArrayList getPlrBanks()
    {
        return plrBanks;
    }

    public void setPlrBanks(ArrayList banks)
    {
        plrBanks = banks;
    }

    public String getShortNameMemId()
    {
        return shortNameMemId;
    }

    public void setShortNameMemId(String id)
    {
        shortNameMemId = id;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String name)
    {
        bankName = name;
    }

    public ArrayList getPlrDetails()
    {
        return plrDetails;
    }

    public void setPlrDetails(ArrayList obj)
    {
        plrDetails = obj;
    }

    public int getPlrIndexValue()
    {
        return plrIndexValue;
    }

    public void setPlrIndexValue(int val)
    {
        plrIndexValue = val;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = super.validate(mapping, request);
        if(errors.isEmpty())
        {
            Log.log(4, "RPActionForm", "validate", (new StringBuilder("param method value is ")).append(request.getParameter("method")).toString());
            if(mapping.getPath().equals("/modifyPLR"))
            {
                Log.log(4, "AdministrationActionForm", "validate", " PLRMaster");
                Set plrMastersSet = plrMasters.keySet();
                Iterator plrMastersIterator = plrMastersSet.iterator();
                boolean fromDate = false;
                boolean toDate = false;
                boolean shortTermPLRBoolean = false;
                boolean mediumTermPLRBoolean = false;
                boolean longTermPLRBoolean = false;
                boolean shortTermPeriodBoolean = false;
                boolean mediumTermPeriodBoolean = false;
                boolean longTermPeriodBoolean = false;
                boolean validPLRType = false;
                while(plrMastersIterator.hasNext()) 
                {
                    String key = (String)plrMastersIterator.next();
                    Log.log(4, "AdministrationActionForm", "validate", (new StringBuilder(" key ")).append(key).toString());
                    PLRMaster plrmaster = (PLRMaster)plrMasters.get(key);
                    Log.log(4, "AdministrationActionForm", "validate", (new StringBuilder(" Printing PLRMaster :")).append(plrmaster).toString());
                    Date startDateParam = plrmaster.getStartDate();
                    Log.log(4, "AdministrationActionForm", "validate", (new StringBuilder(" startDateParam :")).append(startDateParam).toString());
                    Date endDateParam = plrmaster.getEndDate();
                    Log.log(4, "AdministrationActionForm", "validate", (new StringBuilder(" endDateParam :")).append(endDateParam).toString());
                    double shortTermPLR = plrmaster.getShortTermPLR();
                    Log.log(4, "AdministrationActionForm", "validate", (new StringBuilder(" shortTermPLR :")).append(shortTermPLR).toString());
                    double mediumTermPLR = plrmaster.getMediumTermPLR();
                    Log.log(4, "AdministrationActionForm", "validate", (new StringBuilder(" mediumTermPLR :")).append(mediumTermPLR).toString());
                    double longTermPLR = plrmaster.getLongTermPLR();
                    Log.log(4, "AdministrationActionForm", "validate", (new StringBuilder(" longTermPLR :")).append(longTermPLR).toString());
                    int shortTermPeriod = plrmaster.getShortTermPeriod();
                    Log.log(4, "AdministrationActionForm", "validate", (new StringBuilder(" shortTermPeriod :")).append(shortTermPeriod).toString());
                    int mediumTermPeriod = plrmaster.getMediumTermPeriod();
                    Log.log(4, "AdministrationActionForm", "validate", (new StringBuilder(" mediumTermPeriod :")).append(mediumTermPeriod).toString());
                    int longTermPeriod = plrmaster.getLongTermPeriod();
                    Log.log(4, "AdministrationActionForm", "validate", (new StringBuilder(" longTermPeriod :")).append(longTermPeriod).toString());
                    String plrType = plrmaster.getPLR();
                    Log.log(4, "AdministrationActionForm", "validate", (new StringBuilder(" plrType :")).append(plrType).toString());
                    double bplr = plrmaster.getBPLR();
                    Log.log(4, "AdministrationActionForm", "validate", (new StringBuilder(" bplr :")).append(bplr).toString());
                    if(startDateParam == null || startDateParam.toString().equals(""))
                    {
                        ActionError error = new ActionError("errors.required", "From Date ");
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                        Log.log(4, "RPActionForm", "validate", " From date is null ");
                        fromDate = true;
                    } else
                    if(startDateParam.toString().trim().length() < 10)
                    {
                        String errorStrs[] = new String[2];
                        errorStrs[0] = "From Date ";
                        errorStrs[1] = "10";
                        ActionError error = new ActionError("errors.minlength", errorStrs);
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                        fromDate = true;
                        Log.log(4, "AdministrationActionForm", "validate", " length is less than zero");
                    } else
                    {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Log.log(4, "AdministrationActionForm", "validate", (new StringBuilder(" startDateParam :")).append(startDateParam).toString());
                        Date fromUtilDate = dateFormat.parse(startDateParam.toString(), new ParsePosition(0));
                        Log.log(4, "AdministrationActionForm", "validate", (new StringBuilder(" fromUtilDate :")).append(fromUtilDate).toString());
                        if(fromUtilDate == null || fromUtilDate.toString().equals(""))
                        {
                            ActionError error = new ActionError("errors.date", "From Date");
                            errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                            fromDate = true;
                        }
                    }
                    if(endDateParam == null || endDateParam.toString().equals(""))
                        Log.log(4, "RPActionForm", "validate", " End date is null ");
                    else
                    if(endDateParam.toString().trim().length() < 10)
                    {
                        String errorStrs[] = new String[2];
                        errorStrs[0] = "End Date ";
                        errorStrs[1] = "10";
                        ActionError error = new ActionError("errors.minlength", errorStrs);
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                        toDate = true;
                        Log.log(4, "AdministrationActionForm", "validate", " length is less than zero");
                    } else
                    {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Log.log(4, "AdministrationActionForm", "validate", (new StringBuilder(" endDateParam :")).append(endDateParam).toString());
                        Date toUtilDate = dateFormat.parse(endDateParam.toString(), new ParsePosition(0));
                        Log.log(4, "AdministrationActionForm", "validate", (new StringBuilder(" endDateStr :")).append(toUtilDate).toString());
                        if(toUtilDate == null || toUtilDate.toString().equals(""))
                        {
                            ActionError error = new ActionError("errors.date", "End Date");
                            errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                            toDate = true;
                        } else
                        if(endDateParam.compareTo(startDateParam) < 0)
                        {
                            ActionError error = new ActionError("errors.date", "End Date cannot be earlier than From Date, ");
                            errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                            toDate = true;
                        }
                    }
                    if(shortTermPLR <= 0.0D)
                    {
                        ActionError error = new ActionError("errors.required", "Short Term PLR greater then 0");
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                        shortTermPLRBoolean = true;
                    }
                    if(mediumTermPLR <= 0.0D)
                    {
                        ActionError error = new ActionError("errors.required", "Medium Term PLR greater then 0");
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                        mediumTermPLRBoolean = true;
                    }
                    if(longTermPLR <= 0.0D)
                    {
                        ActionError error = new ActionError("errors.required", "Long Term PLR greater then 0");
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                        longTermPLRBoolean = true;
                    }
                    if(shortTermPeriod <= 0)
                    {
                        ActionError error = new ActionError("errors.required", "Short Term Period greater then 0");
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                        shortTermPeriodBoolean = true;
                    }
                    if(mediumTermPeriod <= 0)
                    {
                        ActionError error = new ActionError("errors.required", "Medium Term Period greater then 0");
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                        mediumTermPeriodBoolean = true;
                    }
                    if(longTermPeriod <= 0)
                    {
                        ActionError error = new ActionError("errors.required", "Long Term Period greater then 0");
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                        longTermPeriodBoolean = true;
                    }
                    if(plrType != null && plrType.equals("B") && bplr <= 0.0D)
                    {
                        ActionError error = new ActionError("errors.required", "Invalid Benchmark PLR value");
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                        validPLRType = true;
                    }
                    if(plrType != null && plrType.equals("T") && bplr >= 0.0D)
                    {
                        ActionError error = new ActionError("errors.required", "Benchmark PLR not required for Tenure PLR.");
                        errors.add("org.apache.struts.action.GLOBAL_ERROR", error);
                        validPLRType = true;
                    }
                    if(fromDate && toDate && shortTermPLRBoolean && mediumTermPLRBoolean && longTermPLRBoolean && shortTermPeriodBoolean && mediumTermPeriodBoolean && longTermPeriodBoolean)
                        break;
                }
            }
        }
        return errors;
    }

    public PLRMaster getPlrMaster()
    {
        return plrMaster;
    }

    public void setPlrMaster(PLRMaster master)
    {
        plrMaster = master;
    }
}