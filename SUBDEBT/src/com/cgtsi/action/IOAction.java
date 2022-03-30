package com.cgtsi.action;

import com.cgtsi.admin.User;
import com.cgtsi.common.Log;
import com.cgtsi.common.NoDataException;
import com.cgtsi.inwardoutward.IOProcessor;
import com.cgtsi.inwardoutward.Inward;
import com.cgtsi.inwardoutward.Outward;
import com.cgtsi.knowledge.Document;
import com.cgtsi.knowledge.KnowledgeManager;
import com.cgtsi.knowledge.SearchCriteria;
import com.cgtsi.util.PropertyLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
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

public class IOAction extends BaseAction
{
  public ActionForward addInward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IOAction", "addInward", "Entered");
    String ht = null;

    DynaActionForm dynaForm = (DynaActionForm)form;
    FormFile file = (FormFile)dynaForm.get("filePathInward");
    Inward inward = new Inward();
    String path = "";
    if ((file != null) && (!file.toString().equals("")))
    {
      String contextPath = request.getSession().getServletContext().getRealPath("");
      path = PropertyLoader.changeToOSpath(contextPath + "/" + "WEB-INF/FileUpload" + File.separator + file.getFileName());

      String fileName = file.getFileName();
      int index = fileName.lastIndexOf(".");
      String name = fileName.substring(0, index);
      String type = fileName.substring(index);

      BeanUtils.populate(inward, dynaForm.getMap());
      inward.setFilePath(path);

      String memberId = null;
      ArrayList OfmemberIds = new ArrayList();
      IOProcessor ioprocessor = new IOProcessor();
      String mappedOutward = inward.getMappedOutwardID();
      ArrayList outwardIds = new ArrayList();
      outwardIds = ioprocessor.getAllOutwardIds();
      int count = 0;
      String tempId = "";

      if ((mappedOutward != null) && (!mappedOutward.equals("")))
      {
        StringTokenizer stringTokenizer = new StringTokenizer(mappedOutward, ",");
        while (stringTokenizer.hasMoreTokens())
        {
          memberId = stringTokenizer.nextToken();
          String newMemberId1 = memberId.trim();
          String newMemberId2 = newMemberId1.toUpperCase();
          if (!OfmemberIds.contains(newMemberId2))
          {
            OfmemberIds.add(newMemberId2);
          }
          else;
        }

        int OfmemberIdsSize = OfmemberIds.size();
        for (int i = 0; i < OfmemberIdsSize; i++)
        {
          String id = (String)OfmemberIds.get(i);

          String newId = id.trim();

          String newOutwardId = newId.toUpperCase();

          if (outwardIds.contains(newOutwardId))
          {
            count++;

            if (count == OfmemberIdsSize)
            {
              for (int j = 0; j < OfmemberIdsSize; j++)
              {
                String mappedInwardId = (String)OfmemberIds.get(j);

                String newMappedInwardId = mappedInwardId.trim();
                String mappedInwardId1 = newMappedInwardId.toUpperCase();
                String mappedInwardId2 = mappedInwardId1 + ",";

                tempId = tempId + mappedInwardId2;
              }

              inward.setMappedOutwardID(tempId);
              User creatingUser = getUserInformation(request);
              String user = creatingUser.getUserId();
              inward.setProcessedBy(user);
              ht = ioprocessor.addInwardDetail(inward);
              int index1 = ht.indexOf("/");
              int index2 = ht.lastIndexOf("/");
              String part1 = ht.substring(0, index1);
              String part2 = ht.substring(++index1, index2);
              String part3 = ht.substring(++index2);
              String inwardId = part1 + part2 + part3;
              String nameOfFile = name + inwardId + type;

              uploadFile(file, contextPath, nameOfFile);

              if (ht != null)
              {
                dynaForm.set("inwardId", ht);
              }creatingUser = null;
            }
            else;
          }

        }

      }
      else
      {
        User creatingUser = getUserInformation(request);
        String user = creatingUser.getUserId();
        inward.setProcessedBy(user);
        ht = ioprocessor.addInwardDetail(inward);
        int index1 = ht.indexOf("/");
        int index2 = ht.lastIndexOf("/");
        String part1 = ht.substring(0, index1);
        String part2 = ht.substring(++index1, index2);
        String part3 = ht.substring(++index2);
        String inwardId = part1 + part2 + part3;
        String nameOfFile = name + inwardId + type;

        uploadFile(file, contextPath, nameOfFile);

        if (ht != null)
        {
          dynaForm.set("inwardId", ht);
        }creatingUser = null;
      }
      inward = null;
      ioprocessor = null;
      Log.log(4, "IOAction", "addInward", "Exited");
    }
    else
    {
      BeanUtils.populate(inward, dynaForm.getMap());
      String memberId = null;
      ArrayList OfmemberIds = new ArrayList();
      IOProcessor ioprocessor = new IOProcessor();
      String mappedOutward = inward.getMappedOutwardID();
      ArrayList outwardIds = new ArrayList();
      outwardIds = ioprocessor.getAllOutwardIds();
      int count = 0;
      String tempId = "";

      if ((mappedOutward != null) && (!mappedOutward.equals("")))
      {
        StringTokenizer stringTokenizer = new StringTokenizer(mappedOutward, ",");
        while (stringTokenizer.hasMoreTokens())
        {
          memberId = stringTokenizer.nextToken();
          String newMemberId1 = memberId.trim();
          String newMemberId2 = newMemberId1.toUpperCase();
          if (!OfmemberIds.contains(newMemberId2))
          {
            OfmemberIds.add(newMemberId2);
          }
          else;
        }

        int OfmemberIdsSize = OfmemberIds.size();
        for (int i = 0; i < OfmemberIdsSize; i++)
        {
          String id = (String)OfmemberIds.get(i);
          String newId = id.trim();
          String newOutwardId = newId.toUpperCase();

          if (outwardIds.contains(newOutwardId))
          {
            count++;

            if (count == OfmemberIdsSize)
            {
              for (int j = 0; j < OfmemberIdsSize; j++)
              {
                String mappedInwardId = (String)OfmemberIds.get(j);
                String newMappedInwardId = mappedInwardId.trim();
                String mappedInwardId1 = newMappedInwardId.toUpperCase();
                String mappedInwardId2 = mappedInwardId1 + ",";
                tempId = tempId + mappedInwardId2;
              }

              inward.setMappedOutwardID(tempId);

              User creatingUser = getUserInformation(request);
              String user = creatingUser.getUserId();

              inward.setProcessedBy(user);
              ht = ioprocessor.addInwardDetail(inward);

              if (ht != null)
              {
                dynaForm.set("inwardId", ht);
              }creatingUser = null;
            }
            else;
          }

        }

      }
      else
      {
        User creatingUser = getUserInformation(request);
        String user = creatingUser.getUserId();
        inward.setProcessedBy(user);
        ht = ioprocessor.addInwardDetail(inward);

        if (ht != null)
        {
          dynaForm.set("inwardId", ht);
        }creatingUser = null;
      }
    }
    Log.log(4, "IOAction", "addInward", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showAddInward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IOAction", "showAddInward", "Entered");
    ArrayList Documents = new ArrayList();
    IOProcessor ioprocessor = new IOProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    Documents = ioprocessor.getAllDocumentTypes();
    dynaForm.set("documentTypes", Documents);
    Documents = null;
    ioprocessor = null;
    Log.log(4, "IOAction", "showAddInward", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showAddOutward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IOAction", "showAddOutward", "Entered");
    ArrayList Documents = new ArrayList();
    IOProcessor ioprocessor = new IOProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    Documents = ioprocessor.getAllDocumentTypes();

    dynaForm.set("documentTypes", Documents);
    Documents = null;
    ioprocessor = null;
    Log.log(4, "IOAction", "showAddOutward", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward addOutward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IOAction", "addOutward", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;

    FormFile file = (FormFile)dynaForm.get("filePathOutward");
    Outward outward = new Outward();
    String outwardId = null;
    String path = "";
    if ((file != null) && (!file.toString().equals("")))
    {
      String contextPath = request.getSession().getServletContext().getRealPath("");

      path = PropertyLoader.changeToOSpath(contextPath + "/" + "WEB-INF/FileUpload" + File.separator + file.getFileName());

      String fileName = file.getFileName();
      int index = fileName.lastIndexOf(".");
      String name = fileName.substring(0, index);
      String type = fileName.substring(index);

      BeanUtils.populate(outward, dynaForm.getMap());
      outward.setFilePath(path);

      IOProcessor ioprocessor = new IOProcessor();
      ArrayList inwardIds = new ArrayList();
      inwardIds = ioprocessor.getAllInwardIds();
      String memberId = null;
      ArrayList OfmemberIds = new ArrayList();
      String mappedInward = outward.getMappedInward();
      int count = 0;
      String tempId = "";

      if ((mappedInward != null) && (!mappedInward.equals("")))
      {
        StringTokenizer stringTokenizer = new StringTokenizer(mappedInward, ",");
        while (stringTokenizer.hasMoreTokens())
        {
          memberId = stringTokenizer.nextToken();
          String newMemberId1 = memberId.trim();
          String newMemberId2 = newMemberId1.toUpperCase();

          if (!OfmemberIds.contains(newMemberId2))
          {
            OfmemberIds.add(newMemberId2);
          }
          else;
        }

        int OfmemberIdsSize = OfmemberIds.size();
        for (int i = 0; i < OfmemberIdsSize; i++)
        {
          String id = (String)OfmemberIds.get(i);

          String newId = id.trim();

          String newInwardId = newId.toUpperCase();

          if (inwardIds.contains(newInwardId))
          {
            count++;

            if (count == OfmemberIdsSize)
            {
              for (int j = 0; j < OfmemberIdsSize; j++)
              {
                String mappedInwardId = (String)OfmemberIds.get(j);

                String newMappedInwardId = mappedInwardId.trim();
                String mappedInwardId1 = newMappedInwardId.toUpperCase();
                String mappedInwardId2 = mappedInwardId1 + ",";
                tempId = tempId + mappedInwardId2;
              }

              outward.setMappedInward(tempId);
              User creatingUser = getUserInformation(request);
              String user = creatingUser.getUserId();
              outward.setProcessedBy(user);
              outwardId = ioprocessor.addOutwardDetail(outward);

              int index1 = outwardId.indexOf("/");
              int index2 = outwardId.lastIndexOf("/");
              String part1 = outwardId.substring(0, index1);
              String part2 = outwardId.substring(++index1, index2);
              String part3 = outwardId.substring(++index2);
              String inwardId = part1 + part2 + part3;
              String nameOfFile = name + inwardId + type;

              uploadFile(file, contextPath, nameOfFile);

              dynaForm.set("outwardId", outwardId);
              creatingUser = null;
            }
            else;
          }

        }

      }
      else
      {
        User creatingUser = getUserInformation(request);
        String user = creatingUser.getUserId();
        outward.setProcessedBy(user);

        outwardId = ioprocessor.addOutwardDetail(outward);
        int index1 = outwardId.indexOf("/");
        int index2 = outwardId.lastIndexOf("/");
        String part1 = outwardId.substring(0, index1);
        String part2 = outwardId.substring(++index1, index2);
        String part3 = outwardId.substring(++index2);
        String inwardId = part1 + part2 + part3;
        String nameOfFile = name + inwardId + type;

        uploadFile(file, contextPath, nameOfFile);

        if (outwardId != null)
        {
          dynaForm.set("outwardId", outwardId);
        }creatingUser = null;
      }

      inwardIds = null;
      outward = null;
      ioprocessor = null;
      Log.log(4, "IOAction", "addOutward", "Exited");
    }
    else
    {
      BeanUtils.populate(outward, dynaForm.getMap());
      outward.setFilePath(path);

      IOProcessor ioprocessor = new IOProcessor();
      ArrayList inwardIds = new ArrayList();
      inwardIds = ioprocessor.getAllInwardIds();
      String memberId = null;
      ArrayList OfmemberIds = new ArrayList();
      String mappedInward = outward.getMappedInward();
      int count = 0;
      String tempId = "";

      if ((mappedInward != null) && (!mappedInward.equals("")))
      {
        StringTokenizer stringTokenizer = new StringTokenizer(mappedInward, ",");
        while (stringTokenizer.hasMoreTokens())
        {
          memberId = stringTokenizer.nextToken();
          String newMemberId1 = memberId.trim();
          String newMemberId2 = newMemberId1.toUpperCase();
          if (!OfmemberIds.contains(newMemberId2))
          {
            OfmemberIds.add(newMemberId2);
          }
          else;
        }

        int OfmemberIdsSize = OfmemberIds.size();
        for (int i = 0; i < OfmemberIdsSize; i++)
        {
          String id = (String)OfmemberIds.get(i);

          String newId = id.trim();

          String newInwardId = newId.toUpperCase();

          if (inwardIds.contains(newInwardId))
          {
            count++;

            if (count == OfmemberIdsSize)
            {
              for (int j = 0; j < OfmemberIdsSize; j++)
              {
                String mappedInwardId = (String)OfmemberIds.get(j);
                String newMappedInwardId = mappedInwardId.trim();
                String mappedInwardId1 = newMappedInwardId.toUpperCase();
                String mappedInwardId2 = mappedInwardId1 + ",";
                tempId = tempId + mappedInwardId2;
              }

              outward.setMappedInward(tempId);

              User creatingUser = getUserInformation(request);
              String user = creatingUser.getUserId();
              outward.setProcessedBy(user);
              outwardId = ioprocessor.addOutwardDetail(outward);

              dynaForm.set("outwardId", outwardId);
              creatingUser = null;
            }
            else;
          }

        }

      }
      else
      {
        User creatingUser = getUserInformation(request);
        String user = creatingUser.getUserId();
        outward.setProcessedBy(user);

        outwardId = ioprocessor.addOutwardDetail(outward);

        if (outwardId != null)
        {
          dynaForm.set("outwardId", outwardId);
        }creatingUser = null;
      }
    }

    Log.log(4, "IOAction", "addOutward", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward searchResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IOAction", "searchResult", "Entered");
    KnowledgeManager knowledgeManager = new KnowledgeManager();
    HashMap searchResult = new HashMap();
    DynaActionForm dynaForm = (DynaActionForm)form;

    SearchCriteria searchCriteria = new SearchCriteria();
    BeanUtils.populate(searchCriteria, dynaForm.getMap());

    searchResult = knowledgeManager.searchForDocument(searchCriteria);

    dynaForm.set("documentDetails", searchResult);
    if ((searchResult == null) || (searchResult.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    searchResult = null;
    knowledgeManager = null;
    Log.log(4, "IOAction", "searchResult", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward uploadDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IOAction", "uploadDocument", "Entered");
    ArrayList Documents = new ArrayList();
    IOProcessor ioprocessor = new IOProcessor();
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    Documents = ioprocessor.getAllDocumentTypes();

    dynaForm.set("documentTypes", Documents);
    Documents = null;
    ioprocessor = null;
    Log.log(4, "IOAction", "uploadDocument", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward uploadResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IOAction", "uploadResult", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    FormFile file = (FormFile)dynaForm.get("filePath");

    Document document = new Document();
    if (file != null)
    {
      String contextPath = request.getSession().getServletContext().getRealPath("");

      String path = PropertyLoader.changeToOSpath(contextPath + "/" + "WEB-INF/FileUpload" + File.separator + file.getFileName());

      BeanUtils.populate(document, dynaForm.getMap());
      document.setDocumentPath(path);
      uploadFile(file, contextPath);
    }
    KnowledgeManager knowledgeManager = new KnowledgeManager();
    User creatingUser = getUserInformation(request);
    String user = creatingUser.getUserId();
    document.setUser(user);
    knowledgeManager.storeDocument(document);
    Log.log(4, "IOAction", "uploadResult", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward viewInward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IOAction", "viewInward", "Entered");
    Log.log(4, "IOAction", "viewInward", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward viewOutward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IOAction", "viewOutward", "Entered");
    Log.log(4, "IOAction", "viewOutward", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward showInwardSummary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IOAction", "showInwardSummary", "Entered" + Runtime.getRuntime().freeMemory());
    IOProcessor ioprocessor = new IOProcessor();
    ArrayList inwardSummary = ioprocessor.showInwardStatusSummary();
    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.set("inwardSummary", inwardSummary);
    inwardSummary = null;
    ioprocessor = null;
    Log.log(4, "IOAction", "showInwardSummary", "Exited" + Runtime.getRuntime().freeMemory());

    return mapping.findForward("success");
  }

  public ActionForward showOutwardSummary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IOAction", "showOutwardSummary", "Entered" + Runtime.getRuntime().freeMemory());
    IOProcessor ioprocessor = new IOProcessor();
    ArrayList outwardSummary = ioprocessor.showOutwardStatusSummary();
    DynaActionForm dynaForm = (DynaActionForm)form;

    dynaForm.set("outwardSummary", outwardSummary);
    outwardSummary = null;
    ioprocessor = null;
    Log.log(4, "IOAction", "showOutwardSummary", "Exited" + Runtime.getRuntime().freeMemory());

    return mapping.findForward("success");
  }

  public ActionForward documentDetailsInward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IOAction", "documentDetailsInward", "Entered" + Runtime.getRuntime().freeMemory());
    String id = request.getParameter("outwardID");
    IOProcessor ioprocessor = new IOProcessor();
    HttpSession session = request.getSession(false);
    Outward outward = null;

    if ((id != null) && (!id.equals("")))
    {
      session.setAttribute("INWARDID", id);
      outward = ioprocessor.getOutwardDetail(id);
    }
    else
    {
      String sameId = (String)session.getAttribute("INWARDID");
      outward = ioprocessor.getOutwardDetail(sameId);
    }

    DynaActionForm dynaForm = (DynaActionForm)form;

    outward.setDateOfDoc(outward.getDocumentSentDate());

    BeanUtils.copyProperties(dynaForm, outward);
    outward = null;
    ioprocessor = null;
    Log.log(4, "IOAction", "documentDetailsInward", "Exited" + Runtime.getRuntime().freeMemory());

    return mapping.findForward("success");
  }

  public ActionForward documentDetailsInward1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IOAction", "documentDetailsInward1", "Entered" + Runtime.getRuntime().freeMemory());
    String id = request.getParameter("outwardID");
    IOProcessor ioprocessor = new IOProcessor();
    HttpSession session = request.getSession(false);
    Outward outward = null;

    if ((id != null) && (!id.equals("")))
    {
      session.setAttribute("INWARDID", id);
      outward = ioprocessor.getOutwardDetail(id);
    }
    else
    {
      String sameId = (String)session.getAttribute("INWARDID");
      outward = ioprocessor.getOutwardDetail(sameId);
    }

    DynaActionForm dynaForm = (DynaActionForm)form;

    outward.setDateOfDoc(outward.getDocumentSentDate());

    BeanUtils.copyProperties(dynaForm, outward);
    outward = null;
    ioprocessor = null;
    Log.log(4, "IOAction", "documentDetailsInward1", "Exited" + Runtime.getRuntime().freeMemory());

    return mapping.findForward("success");
  }

  public ActionForward documentDetailsOutward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IOAction", "documentDetailsOutward", "Entered");
    String id = request.getParameter("inwardID");
    IOProcessor ioprocessor = new IOProcessor();
    HttpSession session = request.getSession(false);
    Inward inward = null;

    if ((id != null) && (!id.equals("")))
    {
      session.setAttribute("INWARDID", id);
      inward = ioprocessor.getInwardDetail(id);
    }
    else
    {
      String sameId = (String)session.getAttribute("INWARDID");
      inward = ioprocessor.getInwardDetail(sameId);
    }
    DynaActionForm dynaForm = (DynaActionForm)form;

    inward.setDateOfDoc(inward.getDateOfDocument());

    BeanUtils.copyProperties(dynaForm, inward);
    inward = null;
    ioprocessor = null;
    Log.log(4, "IOAction", "documentDetailsOutward", "Exited");

    return mapping.findForward("success");
  }

  public ActionForward documentDetailsOutward1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IOAction", "documentDetailsOutward1", "Entered" + Runtime.getRuntime().freeMemory());
    String id = request.getParameter("inwardID");

    IOProcessor ioprocessor = new IOProcessor();
    HttpSession session = request.getSession(false);
    Inward inward = null;

    if ((id != null) && (!id.equals("")))
    {
      session.setAttribute("INWARDID", id);
      inward = ioprocessor.getInwardDetail(id);
    }
    else
    {
      String sameId = (String)session.getAttribute("INWARDID");
      inward = ioprocessor.getInwardDetail(sameId);
    }

    DynaActionForm dynaForm = (DynaActionForm)form;

    inward.setDateOfDoc(inward.getDateOfDocument());

    BeanUtils.copyProperties(dynaForm, inward);
    inward = null;
    ioprocessor = null;

    Log.log(4, "IOAction", "documentDetailsOutward1", "Exited " + Runtime.getRuntime().freeMemory());

    return mapping.findForward("success");
  }

  public ActionForward documentDetailsSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "IOAction", "documentDetailsSearch", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    String id = request.getParameter("id");
    String fileName = null;

    HttpSession session = request.getSession(false);
    KnowledgeManager knowledgeManager = new KnowledgeManager();
    Document documentInfo = null;

    if ((id != null) && (!id.equals("")))
    {
      session.setAttribute("INWARDID", id);
      documentInfo = knowledgeManager.getDocumentDetails(id);
    }
    else
    {
      String sameId = (String)session.getAttribute("INWARDID");
      documentInfo = knowledgeManager.getDocumentDetails(sameId);
    }

    IOProcessor ioprocessor = new IOProcessor();
    fileName = ioprocessor.getFile(id);

    if ((fileName == null) || (fileName.equals("")))
    {
      dynaForm.set("search", documentInfo);
      documentInfo = null;
      knowledgeManager = null;
      Log.log(4, "IOAction", "documentDetailsSearch", "Exited");

      return mapping.findForward("success");
    }

    int length = fileName.length();

    int index2 = fileName.lastIndexOf("/");
    int i = index2 + 1;
    String file = fileName.substring(i, length);

    String contextPath1 = request.getSession(false).getServletContext().getRealPath("");

    String contextPath = PropertyLoader.changeToOSpath(contextPath1);

    String filePath = contextPath + File.separator + "Download" + File.separator + file;

    if (filePath != null)
    {
      filePath = PropertyLoader.changeToOSpath(filePath);
    }

    String formattedToOSPath = request.getContextPath() + File.separator + "Download" + File.separator + file;

    session.setAttribute("file", formattedToOSPath);

    File inputFile = new File(fileName);
    File outputFile = new File(filePath);

    if (outputFile.exists())
    {
      outputFile.createNewFile();
    }

    FileInputStream fis = new FileInputStream(inputFile);
    FileOutputStream fos = new FileOutputStream(outputFile);
    byte[] temp = null;
    int n = -1;

    while ((n = fis.available()) > 0)
    {
      temp = new byte[n];
      fis.read(temp);
      fos.write(temp);
    }

    dynaForm.set("search", documentInfo);
    documentInfo = null;
    knowledgeManager = null;
    Log.log(4, "IOAction", "documentDetailsSearch", "Exited");

    return mapping.findForward("success");
  }
}