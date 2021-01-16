package chang;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.swxy.jwbookms.JwBookMsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

@SpringBootTest(classes = JwBookMsApplication.class)
public class HutoolTest {

    @Test
    public void t1() {
        String str = PinyinUtil.getFirstLetter("数据结构", "").toLowerCase();
        System.out.println(str);
    }

    @Test
    public void t2() {
        String str = "d\\s-gf/sfd-g7- 7-  7 ";
        String s = str.replaceAll("\\D", "");
        System.out.println();
    }

}
