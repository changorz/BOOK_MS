package com.swxy.jwbookms.util;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.ReUtil;
import com.swxy.jwbookms.common.exception.ExceptionCast;
import com.swxy.jwbookms.common.response.code.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class AssertUtil {

    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    public static void isExcel(MultipartFile file) throws IOException {
        String type1 = FileTypeUtil.getType(file.getInputStream());
        String type2 = null;
        ;
        int i = file.getOriginalFilename().lastIndexOf('.') + 1;
        if (i > 0) {
            type2 = file.getOriginalFilename().substring(i);
        }
        if (!XLS.equals(type1) && !XLSX.equals(type2) && !XLS.equals(type2)) {
            ExceptionCast.cast(CommonCode.FILE_TYPE_ERR);
        }
    }

    /**
     * 校验学期id
     *
     * @param xqid
     * @return
     */
    public static void isXqid(String xqid) {
        boolean match = ReUtil.isMatch("20[0-9]{2}-20[0-9]{2}-[1-2]", xqid);
        if (StringUtils.isNotEmpty(xqid) && !match) {
            ExceptionCast.cast(CommonCode.Parameter_Xqid_to_Err);
        }
    }

}
