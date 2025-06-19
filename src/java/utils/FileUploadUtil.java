package utils;

import jakarta.servlet.http.Part;
import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUploadUtil {
    
    // Default upload directory relative to web application root
    private static final String DEFAULT_UPLOAD_DIR = "assets/images/blog/uploaded";
    
    /**
     * Uploads a file to the specified directory
     * 
     * @param part The file part from the multipart request
     * @param uploadDir The directory to upload to (relative to web app root)
     * @param context The ServletContext to get the real path
     * @return The path to the uploaded file (relative to web app root)
     * @throws IOException If an error occurs during file upload
     */
    public static String uploadFile(Part part, String uploadDir, ServletContext context) throws IOException {
        // Validate part
        if (part == null || part.getSize() == 0) {
            throw new IOException("No file selected for upload");
        }
        
        // Get the application's real path
        String applicationPath = context.getRealPath("/");
        if (applicationPath == null) {
            applicationPath = context.getRealPath("");
        }
        
        // Create the upload directory if it doesn't exist
        String uploadPath = applicationPath + File.separator + uploadDir.replace("/", File.separator);
        Path uploadDirPath = Paths.get(uploadPath);
        
        if (!Files.exists(uploadDirPath)) {
            Files.createDirectories(uploadDirPath);
        }
        
        // Get the original filename and create a unique filename
        String originalFileName = getFileName(part);
        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new IOException("Invalid filename");
        }
        
        // Check if filename has extension
        if (!originalFileName.contains(".")) {
            throw new IOException("File must have an extension");
        }
        
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        
        // Save the file
        String fullPath = uploadPath + File.separator + uniqueFileName;
        part.write(fullPath);
        
        // Verify file was created
        File uploadedFile = new File(fullPath);
        if (!uploadedFile.exists()) {
            throw new IOException("File upload failed - file not created");
        }
        
        // Return the filename only (not full path)
        return uniqueFileName;
    }
    
    /**
     * Uploads a file to the default blog upload directory
     * 
     * @param part The file part from the multipart request
     * @param context The ServletContext to get the real path
     * @return The path to the uploaded file (relative to web app root)
     * @throws IOException If an error occurs during file upload
     */
    public static String uploadBlogImage(Part part, ServletContext context) throws IOException {
        return uploadFile(part, DEFAULT_UPLOAD_DIR, context);
    }
    
    /**
     * Extract the filename from the content-disposition header of the part
     * 
     * @param part The part to extract the filename from
     * @return The extracted filename
     */
    private static String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        
        return "";
    }
} 