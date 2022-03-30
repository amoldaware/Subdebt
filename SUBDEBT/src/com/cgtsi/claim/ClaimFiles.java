/**
 * 
 */
package com.cgtsi.claim;

import org.apache.struts.upload.FormFile;

/**
 * @author pradeep
 *
 */
public class ClaimFiles {

	/**
	 * 
	 */
	public ClaimFiles() {
		
	}
	private String cgpan;
	private FormFile npaReportFile;
	private FormFile diligenceReportFile;
	private FormFile postInspectionReportFile;
	private FormFile postNpaReportFile;
	private String insuranceFileFlag;
	private String insuranceReason;
	private FormFile suitReportFileFile;
	private FormFile finalVerdictFile;
	private FormFile idProofFile;
	private FormFile otherFile;
	private FormFile staffReportFile;
	private String bankRateType;
	private double interstRate;
	private String securityRemarks;
	private String recoveryEffortsTaken;
	private String rating;
	private String branchAddress;
	
	private FormFile[] statementFile;
	private FormFile[] appraisalReportFile;
	private FormFile[] sanctionLetterFile;
	private FormFile[] complianceFile;
	private FormFile[] preInspectionFile;
	private FormFile[] insuranceCopyFile;
	private double principalRepayBeforeNpaAmts;
	private double interestRepayBeforeNpaAmts;
	private double principalRecoveryAfterNpaAmts;
	private double interestRecoveryAfterNpaAmts;
	private double interestRate;
        
    
    
    private FormFile branchAddress1[];
        
        
    private FormFile additionalstatementReportFiles1;
    private FormFile additionalAppraisalReportFiles1;    
    private FormFile additionalSanctionLetterFiles1;    
    private FormFile additionalComplianceReportFiles1;       
    private FormFile additionalPreInspectionReportFiles1;   
    private FormFile additionalInsuranceCopyFiles1;
          
    
    private FormFile additionalstatementReportFiles2;
    private FormFile additionalAppraisalReportFiles2; 
    private FormFile additionalSanctionLetterFiles2;     
    private FormFile additionalComplianceReportFiles2;       
    private FormFile additionalPreInspectionReportFiles2; 
    private FormFile additionalInsuranceCopyFiles2;
    
    
    private FormFile additionalstatementReportFiles3;
    private FormFile additionalAppraisalReportFiles3;   
    private FormFile additionalSanctionLetterFiles3;      
    private FormFile additionalComplianceReportFiles3;      
    private FormFile additionalPreInspectionReportFiles3;  
    private FormFile additionalInsuranceCopyFiles3;
    
	
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public FormFile[] getStatementFile() {
		return statementFile;
	}
	public void setStatementFile(FormFile[] statementFile) {
		this.statementFile = statementFile;
	}
	public FormFile[] getAppraisalReportFile() {
		return appraisalReportFile;
	}
	public void setAppraisalReportFile(FormFile[] appraisalReportFile) {
		this.appraisalReportFile = appraisalReportFile;
	}
	public FormFile[] getSanctionLetterFile() {
		return sanctionLetterFile;
	}
	public void setSanctionLetterFile(FormFile[] sanctionLetterFile) {
		this.sanctionLetterFile = sanctionLetterFile;
	}
	public FormFile[] getComplianceFile() {
		return complianceFile;
	}
	public void setComplianceFile(FormFile[] complianceFile) {
		this.complianceFile = complianceFile;
	}
	public FormFile[] getPreInspectionFile() {
		return preInspectionFile;
	}
	public void setPreInspectionFile(FormFile[] preInspectionFile) {
		this.preInspectionFile = preInspectionFile;
	}
	public FormFile[] getInsuranceCopyFile() {
		return insuranceCopyFile;
	}
	public void setInsuranceCopyFile(FormFile[] insuranceCopyFile) {
		this.insuranceCopyFile = insuranceCopyFile;
	}
	public double getPrincipalRepayBeforeNpaAmts() {
		return principalRepayBeforeNpaAmts;
	}
	public void setPrincipalRepayBeforeNpaAmts(double principalRepayBeforeNpaAmts) {
		this.principalRepayBeforeNpaAmts = principalRepayBeforeNpaAmts;
	}
	public double getInterestRepayBeforeNpaAmts() {
		return interestRepayBeforeNpaAmts;
	}
	public void setInterestRepayBeforeNpaAmts(double interestRepayBeforeNpaAmts) {
		this.interestRepayBeforeNpaAmts = interestRepayBeforeNpaAmts;
	}
	public double getPrincipalRecoveryAfterNpaAmts() {
		return principalRecoveryAfterNpaAmts;
	}
	public void setPrincipalRecoveryAfterNpaAmts(
			double principalRecoveryAfterNpaAmts) {
		this.principalRecoveryAfterNpaAmts = principalRecoveryAfterNpaAmts;
	}
	public double getInterestRecoveryAfterNpaAmts() {
		return interestRecoveryAfterNpaAmts;
	}
	public void setInterestRecoveryAfterNpaAmts(double interestRecoveryAfterNpaAmts) {
		this.interestRecoveryAfterNpaAmts = interestRecoveryAfterNpaAmts;
	}
	public String getCgpan() {
		return cgpan;
	}
	public void setCgpan(String cgpan) {
		this.cgpan = cgpan;
	}
	
	public FormFile getNpaReportFile() {
		return npaReportFile;
	}
	public void setNpaReportFile(FormFile npaReportFile) {
		this.npaReportFile = npaReportFile;
	}
	public FormFile getDiligenceReportFile() {
		return diligenceReportFile;
	}
	public void setDiligenceReportFile(FormFile diligenceReportFile) {
		this.diligenceReportFile = diligenceReportFile;
	}
	public FormFile getPostInspectionReportFile() {
		return postInspectionReportFile;
	}
	public void setPostInspectionReportFile(FormFile postInspectionReportFile) {
		this.postInspectionReportFile = postInspectionReportFile;
	}
	public FormFile getPostNpaReportFile() {
		return postNpaReportFile;
	}
	public void setPostNpaReportFile(FormFile postNpaReportFile) {
		this.postNpaReportFile = postNpaReportFile;
	}
	public String getInsuranceFileFlag() {
		return insuranceFileFlag;
	}
	public void setInsuranceFileFlag(String insuranceFileFlag) {
		this.insuranceFileFlag = insuranceFileFlag;
	}
	public String getInsuranceReason() {
		return insuranceReason;
	}
	public void setInsuranceReason(String insuranceReason) {
		this.insuranceReason = insuranceReason;
	}
	public FormFile getSuitReportFileFile() {
		return suitReportFileFile;
	}
	public void setSuitReportFileFile(FormFile suitReportFileFile) {
		this.suitReportFileFile = suitReportFileFile;
	}
	public FormFile getFinalVerdictFile() {
		return finalVerdictFile;
	}
	public void setFinalVerdictFile(FormFile finalVerdictFile) {
		this.finalVerdictFile = finalVerdictFile;
	}
	public FormFile getIdProofFile() {
		return idProofFile;
	}
	public void setIdProofFile(FormFile idProofFile) {
		this.idProofFile = idProofFile;
	}
	public FormFile getOtherFile() {
		return otherFile;
	}
	public void setOtherFile(FormFile otherFile) {
		this.otherFile = otherFile;
	}
	public FormFile getStaffReportFile() {
		return staffReportFile;
	}
	public void setStaffReportFile(FormFile staffReportFile) {
		this.staffReportFile = staffReportFile;
	}
	public String getBankRateType() {
		return bankRateType;
	}
	public void setBankRateType(String bankRateType) {
		this.bankRateType = bankRateType;
	}
	public double getInterstRate() {
		return interstRate;
	}
	public void setInterstRate(double interstRate) {
		this.interstRate = interstRate;
	}
	public String getSecurityRemarks() {
		return securityRemarks;
	}
	public void setSecurityRemarks(String securityRemarks) {
		this.securityRemarks = securityRemarks;
	}
	public String getRecoveryEffortsTaken() {
		return recoveryEffortsTaken;
	}
	public void setRecoveryEffortsTaken(String recoveryEffortsTaken) {
		this.recoveryEffortsTaken = recoveryEffortsTaken;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getBranchAddress() {
		return branchAddress;
	}
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
        
        
        
    public void setAdditionalstatementReportFiles1(FormFile additionalstatementReportFiles1) {
        this.additionalstatementReportFiles1 = additionalstatementReportFiles1;
    }

    public FormFile getAdditionalstatementReportFiles1() {
        return additionalstatementReportFiles1;
    }

    public void setAdditionalAppraisalReportFiles1(FormFile additionalAppraisalReportFiles1) {
        this.additionalAppraisalReportFiles1 = additionalAppraisalReportFiles1;
    }

    public FormFile getAdditionalAppraisalReportFiles1() {
        return additionalAppraisalReportFiles1;
    }

    public void setAdditionalSanctionLetterFiles1(FormFile additionalSanctionLetterFiles1) {
        this.additionalSanctionLetterFiles1 = additionalSanctionLetterFiles1;
    }

    public FormFile getAdditionalSanctionLetterFiles1() {
        return additionalSanctionLetterFiles1;
    }

    public void setAdditionalComplianceReportFiles1(FormFile additionalComplianceReportFiles1) {
        this.additionalComplianceReportFiles1 = additionalComplianceReportFiles1;
    }

    public FormFile getAdditionalComplianceReportFiles1() {
        return additionalComplianceReportFiles1;
    }

    public void setAdditionalPreInspectionReportFiles1(FormFile additionalPreInspectionReportFiles1) {
        this.additionalPreInspectionReportFiles1 = additionalPreInspectionReportFiles1;
    }

    public FormFile getAdditionalPreInspectionReportFiles1() {
        return additionalPreInspectionReportFiles1;
    }

    public void setAdditionalInsuranceCopyFiles1(FormFile additionalInsuranceCopyFiles1) {
        this.additionalInsuranceCopyFiles1 = additionalInsuranceCopyFiles1;
    }

    public FormFile getAdditionalInsuranceCopyFiles1() {
        return additionalInsuranceCopyFiles1;
    }

    public void setAdditionalstatementReportFiles2(FormFile additionalstatementReportFiles2) {
        this.additionalstatementReportFiles2 = additionalstatementReportFiles2;
    }

    public FormFile getAdditionalstatementReportFiles2() {
        return additionalstatementReportFiles2;
    }

    public void setAdditionalAppraisalReportFiles2(FormFile additionalAppraisalReportFiles2) {
        this.additionalAppraisalReportFiles2 = additionalAppraisalReportFiles2;
    }

    public FormFile getAdditionalAppraisalReportFiles2() {
        return additionalAppraisalReportFiles2;
    }

    public void setAdditionalSanctionLetterFiles2(FormFile additionalSanctionLetterFiles2) {
        this.additionalSanctionLetterFiles2 = additionalSanctionLetterFiles2;
    }

    public FormFile getAdditionalSanctionLetterFiles2() {
        return additionalSanctionLetterFiles2;
    }

    public void setAdditionalComplianceReportFiles2(FormFile additionalComplianceReportFiles2) {
        this.additionalComplianceReportFiles2 = additionalComplianceReportFiles2;
    }

    public FormFile getAdditionalComplianceReportFiles2() {
        return additionalComplianceReportFiles2;
    }

    public void setAdditionalPreInspectionReportFiles2(FormFile additionalPreInspectionReportFiles2) {
        this.additionalPreInspectionReportFiles2 = additionalPreInspectionReportFiles2;
    }

    public FormFile getAdditionalPreInspectionReportFiles2() {
        return additionalPreInspectionReportFiles2;
    }

    public void setAdditionalInsuranceCopyFiles2(FormFile additionalInsuranceCopyFiles2) {
        this.additionalInsuranceCopyFiles2 = additionalInsuranceCopyFiles2;
    }

    public FormFile getAdditionalInsuranceCopyFiles2() {
        return additionalInsuranceCopyFiles2;
    }

    public void setAdditionalstatementReportFiles3(FormFile additionalstatementReportFiles3) {
        this.additionalstatementReportFiles3 = additionalstatementReportFiles3;
    }

    public FormFile getAdditionalstatementReportFiles3() {
        return additionalstatementReportFiles3;
    }

    public void setAdditionalAppraisalReportFiles3(FormFile additionalAppraisalReportFiles3) {
        this.additionalAppraisalReportFiles3 = additionalAppraisalReportFiles3;
    }

    public FormFile getAdditionalAppraisalReportFiles3() {
        return additionalAppraisalReportFiles3;
    }

    public void setAdditionalSanctionLetterFiles3(FormFile additionalSanctionLetterFiles3) {
        this.additionalSanctionLetterFiles3 = additionalSanctionLetterFiles3;
    }

    public FormFile getAdditionalSanctionLetterFiles3() {
        return additionalSanctionLetterFiles3;
    }

    public void setAdditionalComplianceReportFiles3(FormFile additionalComplianceReportFiles3) {
        this.additionalComplianceReportFiles3 = additionalComplianceReportFiles3;
    }

    public FormFile getAdditionalComplianceReportFiles3() {
        return additionalComplianceReportFiles3;
    }

    public void setAdditionalPreInspectionReportFiles3(FormFile additionalPreInspectionReportFiles3) {
        this.additionalPreInspectionReportFiles3 = additionalPreInspectionReportFiles3;
    }

    public FormFile getAdditionalPreInspectionReportFiles3() {
        return additionalPreInspectionReportFiles3;
    }

    public void setAdditionalInsuranceCopyFiles3(FormFile additionalInsuranceCopyFiles3) {
        this.additionalInsuranceCopyFiles3 = additionalInsuranceCopyFiles3;
    }

    public FormFile getAdditionalInsuranceCopyFiles3() {
        return additionalInsuranceCopyFiles3;
    }


    public void setBranchAddress1(FormFile[] branchAddress1) {
        this.branchAddress1 = branchAddress1;
    }

    public FormFile[] getBranchAddress1() {
        return branchAddress1;
    }
    
    private String isSameAppraisalFile;
    private String isSameSanctionFile;
    private String isSameComplianceFile;
    private String isSamePreInspectionFile;
    private String isSameInsuranceFile;
    private String investmentGradeFlag;


	public String getInvestmentGradeFlag() {
		return investmentGradeFlag;
	}
	public void setInvestmentGradeFlag(String investmentGradeFlag) {
		this.investmentGradeFlag = investmentGradeFlag;
	}
	public String getIsSameAppraisalFile() {
		return isSameAppraisalFile;
	}
	public void setIsSameAppraisalFile(String isSameAppraisalFile) {
		this.isSameAppraisalFile = isSameAppraisalFile;
	}
	public String getIsSameSanctionFile() {
		return isSameSanctionFile;
	}
	public void setIsSameSanctionFile(String isSameSanctionFile) {
		this.isSameSanctionFile = isSameSanctionFile;
	}
	public String getIsSameComplianceFile() {
		return isSameComplianceFile;
	}
	public void setIsSameComplianceFile(String isSameComplianceFile) {
		this.isSameComplianceFile = isSameComplianceFile;
	}
	public String getIsSamePreInspectionFile() {
		return isSamePreInspectionFile;
	}
	public void setIsSamePreInspectionFile(String isSamePreInspectionFile) {
		this.isSamePreInspectionFile = isSamePreInspectionFile;
	}
	public String getIsSameInsuranceFile() {
		return isSameInsuranceFile;
	}
	public void setIsSameInsuranceFile(String isSameInsuranceFile) {
		this.isSameInsuranceFile = isSameInsuranceFile;
	}

	private FormFile[] diligenceReportFiles = new FormFile[3];
    private FormFile[] postInspectionReportFiles = new FormFile[3];
    private FormFile[] postNpaReportFiles = new FormFile[3];
    private FormFile[] idProofFiles = new FormFile[3];
    private FormFile[] otherFiles = new FormFile[3];


	public FormFile[] getDiligenceReportFiles() {
		return diligenceReportFiles;
	}
	public void setDiligenceReportFiles(FormFile[] diligenceReportFiles) {
		this.diligenceReportFiles = diligenceReportFiles;
	}
	public FormFile[] getPostInspectionReportFiles() {
		return postInspectionReportFiles;
	}
	public void setPostInspectionReportFiles(FormFile[] postInspectionReportFiles) {
		this.postInspectionReportFiles = postInspectionReportFiles;
	}
	public FormFile[] getPostNpaReportFiles() {
		return postNpaReportFiles;
	}
	public void setPostNpaReportFiles(FormFile[] postNpaReportFiles) {
		this.postNpaReportFiles = postNpaReportFiles;
	}
	public FormFile[] getIdProofFiles() {
		return idProofFiles;
	}
	public void setIdProofFiles(FormFile[] idProofFiles) {
		this.idProofFiles = idProofFiles;
	}
	public FormFile[] getOtherFiles() {
		return otherFiles;
	}
	public void setOtherFiles(FormFile[] otherFiles) {
		this.otherFiles = otherFiles;
	}
	
	private double plr;
	private double rate;


	public double getPlr() {
		return plr;
	}
	public void setPlr(double plr) {
		this.plr = plr;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
    
	private FormFile internalRatingFile;


	public FormFile getInternalRatingFile() {
		return internalRatingFile;
	}
	public void setInternalRatingFile(FormFile internalRatingFile) {
		this.internalRatingFile = internalRatingFile;
	}
}
