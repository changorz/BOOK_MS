package chang;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.swxy.jwbookms.JwBookMsApplication;
import com.swxy.jwbookms.util.BMSUtil;
import com.swxy.jwbookms.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Locale;

@SpringBootTest(classes = JwBookMsApplication.class)
public class HutoolTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void t1() {
        String str = PinyinUtil.getFirstLetter("t1 ： 数据结构", "").toLowerCase();
        System.out.println(str);
    }

    @Test
    public void t3() {
        String s = BMSUtil.xqidToZh("2020-2021-3");
        System.out.println(s);
    }

    @Test
    public void t4() {
        // redisUtil.set("activeXQID", "2021春季");
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        for (int i = 0; i < 3; i++) {
            String encode = bc.encode("123456");
            System.out.println(encode);
        }
    }

}
