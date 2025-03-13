package com.cash.ledger.ledger.controller.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.model.S3Object;
import com.cash.ledger.ledger.config.BuildResponse;
import com.cash.ledger.ledger.config.Constants;
import com.cash.ledger.ledger.entity.aws.FileType;
import com.cash.ledger.ledger.service.aws.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/storage/")
public class AwsController {
    private final BuildResponse buildResponse;
    private final AwsService awsService;
    @Autowired
    public AwsController(
            BuildResponse buildResponse,
            AwsService awsService
    ) {
        this.buildResponse = buildResponse;
        this.awsService = awsService;
    }

    // Endpoint to list files in a bucket
    @GetMapping("files")
    public ResponseEntity<?> listFiles() {
        List<String> body = awsService.listFiles(Constants.BUCKET_NAME);
        return ResponseEntity.ok(body);
    }

    // Endpoint to upload a file to a bucket
    @PostMapping("files/upload")
    public ResponseEntity<?> uploadFiles(
            @RequestParam("file") MultipartFile[] files
    ) throws Exception {
        try {
            if (files.length == 0) {
                return buildResponse.createResponse("files", "Files is empty", "Empty", HttpStatus.OK);
            } else {
                return buildResponse.createResponse("files", awsService.uploadFiles(Constants.BUCKET_NAME, files), "files uploaded", HttpStatus.OK);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }

    }

    @GetMapping("file/{fileName}")
    public ResponseEntity<?> getFile(@PathVariable("fileName") String fileName) {
        try {
            S3Object file = awsService.getFile(Constants.BUCKET_NAME, fileName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, file.getObjectMetadata().getContentType())
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.getObjectMetadata().getContentLength()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .body(file.getObjectContent().readAllBytes());
        } catch (AmazonClientException | IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: " + fileName);
        }
    }


    // Endpoint to download a file from a bucket
    @GetMapping("file/download/{fileName}")
    public ResponseEntity<?> downloadFile(
            @PathVariable("fileName") String fileName
    ) throws IOException {

        ByteArrayOutputStream body = awsService.downloadFile(Constants.BUCKET_NAME, fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(FileType.fromFilename(fileName))
                .body(body.toByteArray());
    }

    // Endpoint to delete a file from a bucket
    @DeleteMapping("file/{fileName}")
    public ResponseEntity<?> deleteFile(
            @PathVariable("fileName") String fileName
    ) {
        awsService.deleteFile(Constants.BUCKET_NAME, fileName);
        return ResponseEntity.ok().build();
    }
}