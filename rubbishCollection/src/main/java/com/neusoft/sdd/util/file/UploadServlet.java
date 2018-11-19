package com.neusoft.sdd.util.file;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.base.exception.IServiceException;
import com.neusoft.sdd.businessConsole.ftp.service.IFtpService;

public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 739410093043225599L;

	private File uploadFileFolder;

	private static DiskFileItemFactory factory;

	private static ServletFileUpload upload;

	private IFtpService ftpService;

	@Override
	public void init(ServletConfig config) throws ServletException {

		ServletContext context = config.getServletContext();
		uploadFileFolder = new File(context.getRealPath("upload"));

		if (!uploadFileFolder.exists()) {
			uploadFileFolder.mkdirs();
		}

		FileCleaningTracker fileCleaningTracker = FileCleanerCleanup.getFileCleaningTracker(context);

		factory = new DiskFileItemFactory();
		factory.setRepository(uploadFileFolder);
		factory.setFileCleaningTracker(fileCleaningTracker);
		upload = new ServletFileUpload(factory);
		 //GlobalConstant.WEB.getBean("IFtpService");
		ftpService = (IFtpService) GlobalConstant.WEB.getBean("ftpServiceImpl");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) {

		String wjjDm = request.getParameter("wjjDm");

		String sjUUID = request.getParameter("sjUUID");
		if (null == wjjDm || "".equals(wjjDm)) {
			wjjDm = "sys";
		}
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (isMultipart) {

			try {
				List<FileItem> items = upload.parseRequest(request);

				/*User loginuser = (User) request.getSession().getAttribute(
						GlobalConstant.LOGIN_USER);*/
				PrintWriter out = resp.getWriter();

				for (FileItem tmp : items) {
					if (!tmp.isFormField()) {
						/*String wjDm = ftpService.uploadFile(
								tmp.getInputStream(), tmp.getName(), wjjDm,
								"admin", sjUUID);*/
						Map<String,Object> wjDm_Url = ftpService.uploadFile(
								tmp.getInputStream(), tmp.getName(), wjjDm,
								"admin", sjUUID);
						out.print("{\"WJDM\":\"" + wjDm_Url.get("WJDM") + "\","+"\"URL\":\"" + wjDm_Url.get("URL") + "\"}");
						out.flush();
					}
				}

			} catch (Exception e) {
				throw new IServiceException(this.getClass() + " --> UploadServlet() Exception : " + e);
			}
		}
	}

}