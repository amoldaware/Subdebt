package com.cgtsi.action;

import com.cgtsi.admin.User;
import com.cgtsi.common.Log;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.util.PropertyLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

public class BaseAction extends DispatchAction
{
  protected User getUserInformation(HttpServletRequest request)
  {
    Log.log(4, "BaseAction", "getUserInformation", "Entered");
    HttpSession session = request.getSession(false);

    String userId = (String)session.getAttribute("USER_ID");

    Log.log(5, "BaseAction", "getUserInformation", "User id obtained from session is " + userId);

    User user = (User)session.getAttribute(userId);

    Log.log(5, "BaseAction", "getUserInformation", "User info is " + user);

    Log.log(4, "BaseAction", "getUserInformation", "Exited");

    return user;
  }

  protected MLIInfo getMemberInfo(HttpServletRequest request)
  {
    Log.log(4, "BaseAction", "getMemberInfo", "Entered");

    HttpSession session = request.getSession(false);
    MLIInfo mliInfo = (MLIInfo)session.getAttribute("MEMBER_INFO");

    Log.log(4, "BaseAction", "getMemberInfo", "Exited");

    return mliInfo;
  }

  protected File uploadFile(FormFile formFile, String contextPathTemp)
    throws Exception
  {
    try
    {
      Log.log(4, "CommonAction", "uploadFile", "Entered");

      InputStream input = formFile.getInputStream();
      String contextPath = PropertyLoader.changeToOSpath(contextPathTemp);

      Log.log(5, "CommonAction", "uploadFile", "contextPath: " + contextPath);

      int fileSize = formFile.getFileSize();

      Log.log(5, "CommonAction", "uploadFile", "fileSize: " + fileSize + " " + formFile.getFileName());

      if (fileSize == 0)
      {
        Log.log(3, "CommonAction", "uploadFile", "File Size is Zero");
        return null;
      }

      String fileName = contextPath + File.separator + "WEB-INF/FileUpload" + File.separator + formFile.getFileName();

      FileOutputStream fileOut = new FileOutputStream(fileName);
      int readByte = 0;
      byte[] buffer = new byte[1024];
      while ((readByte = input.read(buffer, 0, buffer.length)) != -1)
      {
        fileOut.write(buffer, 0, readByte);
      }

      buffer = null;
      fileOut.flush();
      fileOut.close();
      input.close();
      formFile.destroy();
      File file = new File(fileName);

      return file;
    }
    catch (IOException io)
    {
      Log.log(2, "CommonAction", "uploadFile ", io.getMessage());
      Log.logException(io);
    }

    return null;
  }

  protected File uploadFile(FormFile formFile, String contextPathTemp, String nameOfFile)
    throws Exception
  {
    try
    {
      Log.log(4, "CommonAction", "uploadFile", "Entered");

      InputStream input = formFile.getInputStream();
      String contextPath = PropertyLoader.changeToOSpath(contextPathTemp);
      ////System.out.println("contextPath :"+contextPath);

      Log.log(5, "CommonAction", "uploadFile", "contextPath: " + contextPath);

      int fileSize = formFile.getFileSize();
      ////System.out.println("file size : "+fileSize);
      
      Log.log(5, "CommonAction", "uploadFile", "fileSize: " + fileSize + " " + formFile.getFileName());

      if (fileSize == 0)
      {
        Log.log(3, "CommonAction", "uploadFile", "File Size is Zero");
        return null;
      }

      String fileName = contextPath + File.separator + "WEB-INF/FileUpload" + File.separator + nameOfFile;
      ////System.out.println("fileName  :"+fileName);
      FileOutputStream fileOut = new FileOutputStream(fileName);
      int readByte = 0;
      byte[] buffer = new byte[1024];
      while ((readByte = input.read(buffer, 0, buffer.length)) != -1)
      {
        fileOut.write(buffer, 0, readByte);
      }

      buffer = null;
      fileOut.flush();
      fileOut.close();
      input.close();
      formFile.destroy();
      File file = new File(fileName);
      //System.out.println(" file : "+file);

      return file;
    }
    catch (IOException io)
    {
      Log.log(2, "CommonAction", "uploadFile ", io.getMessage());
      Log.logException(io);
    }

    return null;
  }
}