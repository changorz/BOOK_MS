package chang;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.swxy.jwbookms.JwBookMsApplication;
import com.swxy.jwbookms.service.BookTotalService;
import com.swxy.jwbookms.util.BMSUtil;
import com.swxy.jwbookms.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Map;

@SpringBootTest(classes = JwBookMsApplication.class)
public class ServiceTest {

    @Autowired
    private BookTotalService bookTotalService;

    @Test
    public void t1() {
        Map<String, List> selectAllByBookTotal = bookTotalService.getSelectAllByBookTotal("2020-2021-1");
        System.out.println(selectAllByBookTotal);
    }


}
