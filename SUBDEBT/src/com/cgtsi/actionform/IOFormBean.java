package com.cgtsi.actionform;

import com.cgtsi.common.Log;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.ServletRequest;

import org.apache.struts.validator.ValidatorActionForm;


public class IOFormBean extends ValidatorActionForm 
{
  private Date inwardDts;
  private String bankNames;
  private String places;
  private String subjects;
  private String sourceIds;
  private String instrumentNo;
  private Date instrumentDts;
  private String referenceIds;
  private Date ltrDts;
  private String section;
  private String drawnOnBank;
  private int instrumentAmt;
  
  private Date outwardDt;
  private String outwardId;
  //added by sukumar @path on 24-Nov-2010 for entering Workshop details
  private String workshopDt;
  private ArrayList mliNames;
  private ArrayList agencyNames;
  private ArrayList statesList;
  private ArrayList districtList;
  private String place;
  private String city;
  private String type;
  private String topic;
  private int participants;
  private String targetGroup;
  private String organisation;
  private String name;
  private String designation;
  private String reasons;
  private String stateName;
  private String districtName;
  private String mliName;
  
  private Date propagationDt;
  
  private String assignedTo="";
  
  private String agencyName;
  
  private String organisedfor;
  private String sourceName;
  
  private String workshopId;
    
  public IOFormBean()
  {
  }
  /**
   * 
   * @return outwardId
   */
  public String getOutwardId()
  {
   return this.outwardId;
  }
  /**
   * 
   * @param outwardId
   */
  public void setOutwardId(String outwardId)
  {
   this.outwardId = outwardId;
  }
  /**
   * 
   * @return outwardId
   */
  public Date getOutwardDt(){
   return this.outwardDt;
  }
  /**
   * 
   * @param outwardDt
   */
  public void setOutwardDt(Date outwardDt) {
   this.outwardDt = outwardDt;
  }
  
  /**
   * 
   * @return instrumentNo
   */
  public String getInstrumentNo()
  {
   return this.instrumentNo;
  }
  /**
   * 
   * @param instrumentNo
   */
  public void setInstrumentNo(String instrumentNo)
  {
   this.instrumentNo=instrumentNo;
  }
  /**
   * 
   * @return instrumentAmt
   */
  public int getInstrumentAmt()
  {
   return this.instrumentAmt;
  }
 /**
   * 
   * @param instrumentAmt
   */
  public void setInstrumentAmt(int instrumentAmt)
  {
   this.instrumentAmt=instrumentAmt;
  }
  /**
   * 
   * @return drawnOnBank
   */
  public String getDrawnOnBank()
  {
   return this.drawnOnBank;
  }
  /**
   * 
   * @param drawnOnBank
   */
  public void setDrawnOnBank(String drawnOnBank){
   this.drawnOnBank=drawnOnBank;
  }
  /**
   * 
   * @return section
   */
  public String getSection()
  {
   return this.section;
  }
  /**
   * 
   * @param section
   */
  public void setSection(String section)
  {
   this.section=section;
  }
  
  /**
   * 
   * @return inwardDts
   */
  public Date getInwardDts()
	{
		return this.inwardDts;
	}
  /**
   * 
   * @param dtls
   */
	public void setInwardDts(Date dt)
	{
		this.inwardDts = dt;
	}
   /**
   * 
   * @return bankNames
   */
  public String getBankNames()
	{
		return this.bankNames;
	}
  /**
   * 
   * @param dtls
   */
	public void setBankNames(String dtls)
	{
		this.bankNames = dtls;
	}
  
  /**
   * 
   * @return places
   */
  public String getPlaces()
	{
		return this.places;
	}
  /**
   * 
   * @param dtls
   */
	public void setPlaces(String dtls)
	{
		this.places = dtls;
	}
  
   /**
   * 
   * @return subjects
   */
  public String getSubjects()
	{
		return this.subjects;
	}
  /**
   * 
   * @param dtls
   */
	public void setSubjects(String dtls)
	{
		this.subjects = dtls;
	}
  
   /**
   * 
   * @return sourceIds
   */
  public String getSourceIds()
	{
		return this.sourceIds;
	}
  /**
   * 
   * @param dtls
   */
	public void setSourceIds(String dtls)
	{
		this.sourceIds = dtls;
	}
  
  /**
   * 
   * @return instrumentDts
   */
  public Date getInstrumentDts()
	{
		return this.instrumentDts;
	}
  /**
   * 
   * @param dtls
   */
	public void setInstrumentDts(Date dtls)
	{
		this.instrumentDts = dtls;
	}
  
  /**
   * 
   * @return referenceIds
   */
  public String getReferenceIds()
	{
		return this.referenceIds;
	}
  /**
   * 
   * @param dtls
   */
	public void setReferenceIds(String dtls)
	{
		this.referenceIds = dtls;
	}
  
  /**
   * 
   * @return ltrDts
   */
  public Date getLtrDts()
	{
		return this.ltrDts;
	}
  /**
   * 
   * @param dtls
   */
	public void setLtrDts(Date dtls)
	{
		this.ltrDts = dtls;
	}


  public void setWorkshopDt(String workshopDt)
  {
    this.workshopDt = workshopDt;
  }


  public String getWorkshopDt()
  {
    return workshopDt;
  }


  public void setMliNames(ArrayList mliNames)
  {
    this.mliNames = mliNames;
  }


  public ArrayList getMliNames()
  {
    return mliNames;
  }


  public void setAgencyNames(ArrayList agencyNames)
  {
    this.agencyNames = agencyNames;
  }


  public ArrayList getAgencyNames()
  {
    return agencyNames;
  }


  public void setStatesList(ArrayList statesList)
  {
    this.statesList = statesList;
  }


  public ArrayList getStatesList()
  {
    return statesList;
  }


  public void setDistrictList(ArrayList districtList)
  {
    this.districtList = districtList;
  }


  public ArrayList getDistrictList()
  {
    return districtList;
  }


  public void setPlace(String place)
  {
    this.place = place;
  }


  public String getPlace()
  {
    return place;
  }


  public void setCity(String city)
  {
    this.city = city;
  }


  public String getCity()
  {
    return city;
  }


  public void setType(String type)
  {
    this.type = type;
  }


  public String getType()
  {
    return type;
  }


  public void setTopic(String topic)
  {
    this.topic = topic;
  }


  public String getTopic()
  {
    return topic;
  }


  public void setParticipants(int participants)
  {
    this.participants = participants;
  }


  public int getParticipants()
  {
    return participants;
  }


  public void setTargetGroup(String targetGroup)
  {
    this.targetGroup = targetGroup;
  }


  public String getTargetGroup()
  {
    return targetGroup;
  }


  public void setOrganisation(String organisation)
  {
    this.organisation = organisation;
  }


  public String getOrganisation()
  {
    return organisation;
  }


  public void setName(String name)
  {
    this.name = name;
  }


  public String getName()
  {
    return name;
  }


  public void setDesignation(String designation)
  {
    this.designation = designation;
  }


  public String getDesignation()
  {
    return designation;
  }


  public void setReasons(String reasons)
  {
    this.reasons = reasons;
  }


  public String getReasons()
  {
    return reasons;
  }


  public void setStateName(String stateName)
  {
    this.stateName = stateName;
  }


  public String getStateName()
  {
    return stateName;
  }


  public void setDistrictName(String districtName)
  {
    this.districtName = districtName;
  }


  public String getDistrictName()
  {
    return districtName;
  }


  public void setAgencyName(String agencyName)
  {
    this.agencyName = agencyName;
  }


  public String getAgencyName()
  {
    return agencyName;
  }


  public void setMliName(String mliName)
  {
    this.mliName = mliName;
  }


  public String getMliName()
  {
    return mliName;
  }


  public void setAssignedTo(String assignedTo)
  {
    this.assignedTo = assignedTo;
  }


  public String getAssignedTo()
  {
    return assignedTo;
  }


  public void setPropagationDt(Date propagationDt)
  {
    this.propagationDt = propagationDt;
  }


  public Date getPropagationDt()
  {
    return propagationDt;
  }


  public void setOrganisedfor(String organisedfor)
  {
    this.organisedfor = organisedfor;
  }


  public String getOrganisedfor()
  {
    return organisedfor;
  }


  public void setSourceName(String sourceName)
  {
    this.sourceName = sourceName;
  }


  public String getSourceName()
  {
    return sourceName;
  }


  public void setWorkshopId(String workshopId)
  {
    this.workshopId = workshopId;
  }


  public String getWorkshopId()
  {
    return workshopId;
  }
  
}