/*
 * Created on Sep 11, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.reports;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author VT8150
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GuaranteeCoverIssued implements Serializable 
{
	/*
	 * Added the following three attributes on 11-sep-2004 to fix clientbugs.
	 */
	private String memberId;
	private	String memberShortName;
	private String memberZoneName;
	private String memberBranchName;
	
	private ArrayList memberCgpans;
	/**
	 * @return
	 */
	public String getMemberBranchName() {
		return memberBranchName;
	}

	/**
	 * @return
	 */
	public ArrayList getMemberCgpans() {
		return memberCgpans;
	}

	/**
	 * @return
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @return
	 */
	public String getMemberShortName() {
		return memberShortName;
	}

	/**
	 * @return
	 */
	public String getMemberZoneName() {
		return memberZoneName;
	}

	/**
	 * @param string
	 */
	public void setMemberBranchName(String string) {
		memberBranchName = string;
	}

	/**
	 * @param list
	 */
	public void setMemberCgpans(ArrayList list) {
		memberCgpans = list;
	}

	/**
	 * @param string
	 */
	public void setMemberId(String string) {
		memberId = string;
	}

	/**
	 * @param string
	 */
	public void setMemberShortName(String string) {
		memberShortName = string;
	}

	/**
	 * @param string
	 */
	public void setMemberZoneName(String string) {
		memberZoneName = string;
	}

}
