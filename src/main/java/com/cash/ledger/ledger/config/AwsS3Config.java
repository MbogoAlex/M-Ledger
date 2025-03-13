package com.cash.ledger.ledger.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {

    // Injecting access key from application.properties
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    // Injecting secret key from application.properties
    @Value("${cloud.aws.credentials.secretKey}")
    private String accessSecret;


    // Creating a bean for Amazon S3 client
    @Bean
    public AmazonS3 s3Client() {
        // Creating AWS credentials using access key and secret key
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);

        // Building Amazon S3 client with specified credentials and region
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Constants.AWS_LOCATION)
                .build();
    }
}
