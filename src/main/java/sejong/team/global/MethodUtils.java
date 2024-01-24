package sejong.team.global;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class MethodUtils {
    public static InputStream getInputStreamFromMultipartFile(MultipartFile multipartFile) throws IOException {
        return multipartFile.getInputStream();
    }
}
