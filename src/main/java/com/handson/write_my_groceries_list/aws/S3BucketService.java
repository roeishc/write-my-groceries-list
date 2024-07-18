package com.handson.write_my_groceries_list.aws;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class S3BucketService {

    private static final Logger logger = LoggerFactory.getLogger(S3BucketService.class);

    private final String INVALID = "invalid";

    @Value("${bucket.url}")
    String bucket;

    @Autowired  
    private AmazonS3 s3Client;


    public String uploadImage(MultipartFile image) {
        String format = getImageFileType(image);
        if (format.equals(INVALID)) {
            logger.error("File is not a valid image (PNG/JPG/JPEG).");
            return null;
        }

        String fileName = generateFileName(image);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(image.getSize());
        metadata.setContentType("image/" + format);

        try {
            s3Client.putObject(new PutObjectRequest(bucket, fileName, image.getInputStream(), metadata));
        } catch (IOException e) {
            logger.error("Failed to save image in bucket.");
            return null;
        }
        catch (Exception e){
            logger.error("Unexpected exception: \n" + e.toString());
            return null;
        }
        return s3Client.getUrl(bucket, fileName).toString();
    }

    private String generateFileName(MultipartFile file) {
        return Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_")
                + new Date().getTime();
    }

    public String getImageFileType(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null)
            return INVALID;
        String lowerCaseFileName = fileName.toLowerCase();
        if (lowerCaseFileName.endsWith(".png")) {
            return "png";
        } else if (lowerCaseFileName.endsWith(".jpg")) {
            return "jpg";
        } else if (lowerCaseFileName.endsWith(".jpeg")) {
            return "jpeg";
        }
        return INVALID;
    }


    /**
     * creates a local temporary file, and assigns it a name with a random UUID
     * @param file the file
     * @param path file's path
     */
    public void putInBucketWithRandomName(MultipartFile file, String path) {
        try {
            Path tempFile = Files.createTempFile("file" + UUID.randomUUID(), null);
            FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile.toFile());
            saveAndSendPNG(tempFile, path);
            Files.deleteIfExists(tempFile);
        } catch (Exception e) {
            logger.error("Error uploading file to bucket: " + bucket + "/ " + path, e);
        }
    }

    /**
     * saves the file in `uploadFile` path in the S3 bucket, as a png image
     * @param uploadFile
     * @param destPath
     */
    private void saveAndSendPNG(Path uploadFile, String destPath) throws IOException {
        PutObjectRequest request = new PutObjectRequest(bucket, destPath, uploadFile.toFile());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/png");
        request.setMetadata(metadata);
        s3Client.putObject(request);
    }


    /*
    * the functions below generate a presigned link for temporary public access to the
    * newly added png image in the bucket
    * */
    public String generateLink(String fileUrl) {
        return generateLink(bucket, fileUrl);
    }

    public String generateLink(String bucketName, String fileUrl) {
        // Set the presigned URL to expire after one min.
        if (fileUrl == null) return null;
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
        return generateLink(bucketName, fileUrl, expTimeMillis);
    }

    public String generateLink(String bucketName, String fileUrl, long expTimeMillis) {
        if (fileUrl.lastIndexOf(bucketName) >= 0) {
            fileUrl = fileUrl.substring(fileUrl.lastIndexOf(bucketName) + bucketName.length() + 1);
        }
        try {
            Date expiration = new Date();
            expiration.setTime(expTimeMillis);

            // generate the pre-signed URL
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, fileUrl)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            return url.toString();
        } catch (Exception e) {
            logger.error("Error generating presigned link", e);
        }
        return null;
    }

}