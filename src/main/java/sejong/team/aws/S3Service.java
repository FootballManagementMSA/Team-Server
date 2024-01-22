package sejong.team.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3Service {
    private final AmazonS3 s3client;
    private final String bucketName;

    public S3Service(@Value("${cloud.aws.s3.bucket}") String bucketName, AmazonS3 s3client) {
        this.bucketName = bucketName;
        this.s3client = s3client;
    }

    public void uploadFile(String fileName, InputStream inputStream, ObjectMetadata metadata) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));
    }

    public String getFileUrl(String fileName) {
        return s3client.getUrl(bucketName, fileName).toString();
    }
}
