import com.aliyun.dataworks_public20240518.models.GetDataSourceRequest;
import com.aliyun.dataworks_public20240518.models.UpdateDataSourceRequest;

import com.aliyuncs.gpdb.model.v20160503.CreateDBInstanceRequest;
import org.springframework.beans.BeanUtils;

/**
 * @author 匠承
 * @Date: 2024/8/12 16:04
 */
public class OpenAPITest {
    public static void main(String[] args) {

        String a = "aaa";
        String b = new String();

        BeanUtils.copyProperties(a, b);
        System.out.println(b);
    }
}
