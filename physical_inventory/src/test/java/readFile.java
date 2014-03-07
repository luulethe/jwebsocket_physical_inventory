import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: luult
 * Date: 3/7/14
 */
public class readFile
{
    public static void main(String[] args)
    {
        File file = new File("E:\\temp\\abcdef.jpg");

        try
        {
            byte[] stream = FileUtils.readFileToByteArray(file);
            System.out.println(stream.length);
            String fileContentBase64 = Base64.encode(stream);

            PrintWriter writer = new PrintWriter("E:\\temp\\the-file-name.txt", "UTF-8");
            writer.println(fileContentBase64);
            writer.close();
            System.out.println(fileContentBase64);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
