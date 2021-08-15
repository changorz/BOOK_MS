package chang;

import com.swxy.jwbookms.JwBookMsApplication;
import com.swxy.jwbookms.mapper.UserMapper;
import com.swxy.jwbookms.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = JwBookMsApplication.class)
public class UserTest {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Test
    void t1(){
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

}
