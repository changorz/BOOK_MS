package chang.jlc;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

public class WorkOrderUtils {

    /**
     * 基本拷贝方法
     * @param fields 对应字段数组（二维数组，一一对应）
     * @param source 原对象
     * @param taeget 目标对象
     */
    public void copy(String[][] fields, Object source, Object taeget){
        int length = fields[0].length;
        for (int i = 0; i < length; i++) {
            String fromField = fields[i][0].substring(0, 1).toUpperCase() + fields[i][0].substring(1);
            String toField =  fields[i][1].substring(0, 1).toUpperCase() + fields[i][1].substring(1);
            String getField = "get" + fromField;
            String setField = "set" + toField;
            Method getFieldMethod = ReflectionUtils.findMethod(source.getClass(), getField);
            Method setFieldMethod = ReflectionUtils.findMethod(taeget.getClass(), setField);
            if(getFieldMethod != null && setFieldMethod != null){
                ReflectionUtils.invokeMethod(setFieldMethod, taeget, ReflectionUtils.invokeMethod(getFieldMethod, source));
            }
        }
    }

    public void copy(String[][] fields, Object source, Object taeget, IConsumer consumer){
        this.copy(fields, source, taeget);
        // 自定义拷贝方法
        consumer.copy(source, taeget);
    }

}
