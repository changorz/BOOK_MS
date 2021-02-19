package com.swxy.jwbookms.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.style.StyleUtil;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Component
public final class BMSWriterExcelUtil {

    public void writerClaExcel(Map map, List<List<String>> lists, OutputStream out) throws IOException {
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 表头信息
        writer.merge(9, map.getOrDefault("title", "XX教材发放表"));
        writer.writeCellValue(1,1,map.getOrDefault("twoLevelCollege", "XX学院"));
        writer.writeCellValue(1,2,map.getOrDefault("claName", "XX1班"));
//        writer.writeCellValue(2,2,"4班");
        writer.writeCellValue(5,2,map.getOrDefault("totel", "0"));
        writer.passCurrentRow();
        writer.passCurrentRow();
        // 写数据
        writer.write(lists);
        // 下面信息
        writer.writeCellValue(1,19,"1、请仔细核对所领取教材名称、出版社、书号、数量、单价，并如实填写。有专业方向时，合计数非每个人领书款。");
        writer.writeCellValue(1,20,"2、请核对以下班级学生名单，如有异动，多余数请退还教材科，否则多余的教材款由班级均摊。");
        writer.writeCellValue(1,21,"3、凡教材领取签字登记出库后，教材科不再管理（班级发放中缺失，责任自负，请自行购买）。");
        writer.writeCellValue(1,22,"4、新教材如有缺损请及时更换、凡教材上写字后将不能更换。");
        writer.writeCellValue(1,23,"以上注意事项已认真阅读，共领_______人教材，签字：___________  电话：___________________________");

        // 学生名单
        String s = (String) map.getOrDefault("names", "");
        writer.merge(26,34,0,9, s, false);

        // 列宽设置
        writer.setColumnWidth(0,4);
        writer.setColumnWidth(1,30);
        writer.setColumnWidth(2,20);
        writer.setColumnWidth(3,40);
        writer.setColumnWidth(4,20);
        writer.setColumnWidth(5,8);
        writer.setColumnWidth(6,8);
        writer.setColumnWidth(7,8);
        writer.setColumnWidth(8,13);
        writer.setColumnWidth(9,20);

        // 行高设置
        for (int i = 0; i < 24; i++) {
            writer.setRowHeight(i, 18);
        }

        // 头样式设置
        CellStyle cellStyleHead = writer.createCellStyle();
        writer.setRowHeight(0, 36);
        cellStyleHead = StyleUtil.setBorder(cellStyleHead, BorderStyle.NONE, IndexedColors.WHITE1);
        cellStyleHead.setAlignment(HorizontalAlignment.CENTER);
        cellStyleHead.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font1 = writer.createFont();
        font1 = StyleUtil.setFontStyle(font1, (short)0, (short) 15, null);
        cellStyleHead.setFont(font1);
        for (int i = 0; i < 10; i++) {
            writer.setStyle(cellStyleHead,i,0);
        }
        // 头部下面的样式
        CellStyle cellStyleNoneBorder = writer.createCellStyle();
        cellStyleNoneBorder = StyleUtil.setBorder(cellStyleNoneBorder, BorderStyle.NONE, IndexedColors.WHITE1);
        writer.setStyle(cellStyleNoneBorder,1,1);
        writer.setStyle(cellStyleNoneBorder,1,2);
        writer.setStyle(cellStyleNoneBorder,2,2);
        writer.setStyle(cellStyleNoneBorder,4,2);

        // 全局字体设置
        Font font = writer.createFont();
        font.setFontName("宋体");
        font.setFontHeight((short)230);
        //第二个参数表示是否忽略头部样式
        writer.getStyleSet().setFont(font, true);

        for (int i = 19; i < 24; i++) {
            // 获取单元格样式
            CellStyle columnStyle = writer.createCellStyle(1, i);
            // 给样式赋值
            columnStyle.setAlignment(HorizontalAlignment.LEFT);
            columnStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            // 修改样式
            writer.setStyle(columnStyle, 1, i);
        }
        // 学生名单样式
        CellStyle columnStyle = writer.createCellStyle(0, 26);
        columnStyle.setAlignment(HorizontalAlignment.LEFT);
        columnStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        columnStyle.setWrapText(true);
        writer.setStyle(columnStyle, 0, 26);


        writer.flush(out);
        writer.close();
        IoUtil.close(out);
    }
}
