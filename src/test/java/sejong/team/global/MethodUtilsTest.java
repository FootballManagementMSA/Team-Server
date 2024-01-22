package sejong.team.global;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static com.amazonaws.util.ValidationUtils.assertNotNull;

public class MethodUtilsTest {
    @Test
    @DisplayName("MultipartFile로부터 InputStream을 반환하는 테스트")
    public void getInputStreamFromMultipartFileTest() throws IOException {
        // given
        MultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "filename.txt",
                "text/plain",
                "some xml".getBytes());

        // when
        InputStream inputStream = MethodUtils.getInputStreamFromMultipartFile(mockMultipartFile);

        // then
        assertNotNull(inputStream, "InputStream should not be null");
    }
}
