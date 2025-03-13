package com.cash.ledger.ledger.service.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

// Interface for AWS service operations
public interface AwsService {
    
    // Method to upload a file to an S3 bucket

    String uploadFile(
            final String bucketName,
            final MultipartFile file
    ) throws AmazonClientException, IOException;
    String uploadFiles(
            final String bucketName,
            final MultipartFile[] files
    ) throws AmazonClientException, IOException;

    String getFileUrl(final String bucketName, final String keyName);

    // Method to download a file from an S3 bucket
    ByteArrayOutputStream downloadFile(
        final String bucketName,
        final String keyName
    ) throws IOException, AmazonClientException;

    S3Object getFile(String bucketName, String fileName) throws AmazonClientException;

    // Method to list files in an S3 bucket
    List<String> listFiles(final String bucketName) throws AmazonClientException;

    // Method to delete a file from an S3 bucket
    void deleteFile(
        final String bucketName,
        final String keyName
    ) throws AmazonClientException;
}