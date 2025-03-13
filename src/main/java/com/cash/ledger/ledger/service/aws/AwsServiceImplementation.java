package com.cash.ledger.ledger.service.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AwsServiceImplementation implements AwsService {

    private final AmazonS3 s3Client;
    @Autowired
    public AwsServiceImplementation(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFile(String bucketName, MultipartFile file) throws AmazonClientException, IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        String fileName = file.getOriginalFilename();
        System.out.println("FILE NAME: "+fileName);
        String contentType = file.getContentType();
        long fileSize = file.getSize();
        InputStream inputStream = file.getInputStream();

        metadata.setContentLength(fileSize);
        metadata.setContentType(contentType);

        // Create a PutObjectRequest with PublicRead ACL
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);

        // Upload the file
        s3Client.putObject(putObjectRequest);


        return fileName;
    }


    // Method to upload a file to an S3 bucket
    @Override
    public String uploadFiles(
            final String bucketName,
            final MultipartFile[] files
    ) throws AmazonClientException, IOException {
        ObjectMetadata metadata = new ObjectMetadata();

        for(MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            System.out.println("FILE NAME: "+fileName);
            String contentType = file.getContentType();
            long fileSize = file.getSize();
            InputStream inputStream = file.getInputStream();

            metadata.setContentLength(fileSize);
            metadata.setContentType(contentType);

            s3Client.putObject(bucketName, fileName, inputStream, metadata);
        }

        return "Upload successful";

    }

    @Override
    public String getFileUrl(String bucketName, String keyName) {
        // Construct the public URL for the file
        String fileUrl = "https://" + bucketName + ".s3.amazonaws.com/" + keyName;

        return fileUrl; // This URL will not expire
    }

    // Method to download a file from an S3 bucket
    @Override
    public ByteArrayOutputStream downloadFile(
            final String bucketName,
            final String keyName
    ) throws IOException, AmazonClientException {
        S3Object s3Object = s3Client.getObject(bucketName, keyName);
        InputStream inputStream = s3Object.getObjectContent();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int len;
        byte[] buffer = new byte[4096];
        while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
            outputStream.write(buffer, 0, len);
        }

        return outputStream;
    }

    @Override
    public S3Object getFile(String bucketName, String fileName) throws AmazonClientException {
        return s3Client.getObject(bucketName, fileName);
    }

    // Method to list files in an S3 bucket
    @Override
    public List<String> listFiles(final String bucketName) throws AmazonClientException {
        List<String> keys = new ArrayList<>();
        ObjectListing objectListing = s3Client.listObjects(bucketName);

        while (true) {
            List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
            if (objectSummaries.isEmpty()) {
                break;
            }

            objectSummaries.stream()
                    .filter(item -> !item.getKey().endsWith("/"))
                    .map(S3ObjectSummary::getKey)
                    .forEach(keys::add);

            objectListing = s3Client.listNextBatchOfObjects(objectListing);
        }

        return keys;
    }

    // Method to delete a file from an S3 bucket
    @Override
    public void deleteFile(
            final String bucketName,
            final String keyName
    ) throws AmazonClientException {
        s3Client.deleteObject(bucketName, keyName);
    }
}