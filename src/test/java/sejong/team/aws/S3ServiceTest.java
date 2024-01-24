package sejong.team.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class S3ServiceTest {
    @Mock
    private AmazonS3 s3client;

    @InjectMocks
    private S3Service s3Service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 업로드성공테스트() {
        String bucketName = "bucket-name";
        String fileName = "test-file.jpg";
        InputStream inputStream = new ByteArrayInputStream(new byte[10]);
        ObjectMetadata metadata = new ObjectMetadata();

        s3Service.uploadFile(fileName, inputStream, metadata);

        verify(s3client, times(1)).putObject(any(PutObjectRequest.class));
    }
//
//    @Test
//    void S3_URL_가져오기() throws Exception {
//        String bucketName = "bucket-name";
//        String fileName = "test-file.jpg";
//        URL mockUrl = new URL("http://example.com/" + fileName);
//
//        when(s3client.getUrl(bucketName, fileName)).thenReturn(mockUrl);
//
//        String fileUrl = s3Service.getFileUrl(fileName);
//
//        assertNotNull(fileUrl, "null");
//        assertEquals(mockUrl.toString(), fileUrl, "통과");
//    }
}
