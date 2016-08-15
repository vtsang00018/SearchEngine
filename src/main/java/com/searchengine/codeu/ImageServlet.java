package com.searchengine.codeu;

import com.google.api.services.vision.v1.model.EntityAnnotation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.List;
import javax.servlet.http.Part;

/**
 * Created by aishanibhalla on 8/9/16.
 */
@WebServlet(name = "ImageServlet")
@MultipartConfig(fileSizeThreshold = 1024*1024*10, //10MB)
                maxFileSize = 1024*1024*50, //50MB
                maxRequestSize = 1024*1024*100, //100MB
                location="/")
public class ImageServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // gets absolute path of the web application
        String applicationPath = request.getServletContext().getRealPath("");

        // constructs path of the directory to save uploaded file
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

        // creates the save directory if it does not exists
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());

        String fileName = null;
        //Get all the parts from request and write it to the file on server
        for (Part part : request.getParts()) {
            fileName = getFileName(part);
            part.write(uploadFilePath + File.separator + fileName);
        }

        Path imagePath = Paths.get(uploadFilePath+File.separator+fileName);
        List<EntityAnnotation> labels = null;
        try {
            LabelApp app = new LabelApp(LabelApp.getVisionService());
            labels = app.labelImage(imagePath, 3);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        request.setAttribute("message", labels);
        getServletContext().getRequestDispatcher("/ImageResults.jsp").forward(
                request, response);
    }
        //RequestDispatcher requestDispatcher;
        //requestDispatcher = request.getRequestDispatcher("/ImageResults.jsp");
        //requestDispatcher.forward(request, response);
    /**
     * Utility method to get file name from HTTP header content-disposition
     */
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }
}

