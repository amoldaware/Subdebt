/*
 * Created on Oct 11, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.claim;

import java.io.Serializable;

/**
 * @author VT8150
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UploadFileProperties implements Serializable 
{
	private String fileName;
	private byte[] fileSize;
	private boolean legalDetails;
	/**
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return
	 */
	public byte[] getFileSize() {
		return fileSize;
	}

	/**
	 * @param string
	 */
	public void setFileName(String string) {
		fileName = string;
	}

	/**
	 * @param bs
	 */
	public void setFileSize(byte[] bs) {
		fileSize = bs;
	}

	/**
	 * @return
	 */
	public boolean isLegalDetails() {
		return legalDetails;
	}

	/**
	 * @param b
	 */
	public void setLegalDetails(boolean b) {
		legalDetails = b;
	}

}
