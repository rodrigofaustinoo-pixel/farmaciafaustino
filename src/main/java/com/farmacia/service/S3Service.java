package com.farmacia.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.*;

@Service
public class S3Service {

    private final String bucket = "farmaciafaustino";

    private S3Client s3Client() {
        return S3Client.builder()
                .region(Region.SA_EAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(
                                        System.getenv("AWS_ACCESS_KEY"),
                                        System.getenv("AWS_SECRET_KEY")
                                )
                        )
                )
                .build();
    }

    public String upload(MultipartFile file, String cpf) throws Exception {

        String key = "clientes/" + cpf + "/" + file.getOriginalFilename();

        S3Client s3 = s3Client();

        s3.putObject(
                b -> b.bucket(bucket).key(key),
                software.amazon.awssdk.core.sync.RequestBody.fromInputStream(
                        file.getInputStream(),
                        file.getSize()
                )
        );

        return "https://" + bucket + ".s3.sa-east-1.amazonaws.com/" + key;
    }
}
