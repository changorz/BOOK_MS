<style lang="less" scoped>
  .expand-row{
    margin-bottom: 16px;
  }
  .ivu-table-expanded-cell{
    .el-row{
      margin: 5px 0;
    }
  }
  .expand-key{
    color: rgba(3, 95, 253, 0.64);
  }
</style>
<template>
  <div>
    <el-row>
      <el-col :span="8">
        <span class="expand-key">二级学院: </span>
        <span class="expand-value">{{ row.twoLevelCollege }}</span>
      </el-col >
      <el-col :span="8">
        <span class="expand-key">专业: </span>
        <span class="expand-value">{{ row.major }}</span>
      </el-col>
      <el-col :span="8">
        <span class="expand-key">年级: </span>
        <span class="expand-value">{{ row.grade }}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <span class="expand-key">班级: </span>
        <span class="expand-value">{{ row.cla }}</span>
      </el-col>
      <el-col :span="8">
        <span class="expand-key">课程名称: </span>
        <span class="expand-value">{{ row.courseTitle }}</span>
      </el-col>
      <el-col :span="8">
        <span class="expand-key">ISBN: </span>
        <span class="expand-value">{{ row.isbn }}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <span class="expand-key">书名: </span>
        <span class="expand-value">《{{ row.bookName }}》</span>
      </el-col>
      <el-col :span="8">
        <span class="expand-key">出版社全称: </span>
        <span class="expand-value">{{ row.publishingHouse }}</span>
      </el-col>
      <el-col :span="8">
        <span class="expand-key">出版社全称补充: </span>
        <span class="expand-value">{{ row.publishingHouseSupplement }}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <span class="expand-key">作者: </span>
        <span class="expand-value">{{ row.author }}</span>
      </el-col>
      <el-col :span="8">
        <span class="expand-key">定价: </span>
        <span class="expand-value">{{ row.pricing }}元</span>
      </el-col>
      <el-col :span="8">
        <span class="expand-key">年份: </span>
        <span class="expand-value">{{ row.bookYear }}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <span class="expand-key">版次: </span>
        <span class="expand-value">{{ row.bookVersion }}</span>
      </el-col>
      <el-col :span="8">
        <span class="expand-key">教材类型: </span>
        <span class="expand-value">{{ row.bookType }}</span>
      </el-col>
      <el-col :span="8">
        <span class="expand-key">授课所属教研室: </span>
        <span class="expand-value">{{ row.staffRoom }}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <span class="expand-key">学生用书数: </span>
        <span class="expand-value">{{ row.studentBookCount }}</span>
      </el-col>
      <el-col :span="8">
        <span class="expand-key">教师用书数: </span>
        <span class="expand-value">{{ row.teacherBookCount }}</span>
      </el-col>
      <el-col :span="8">
        <span class="expand-key">总计: </span>
        <span class="expand-value">{{ row.totalBook }}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <span class="expand-key">码洋(总计金额): </span>
        <span class="expand-value">{{ row.totalPricing }}</span>
      </el-col>
      <el-col :span="8">
        <span class="expand-key">授课老师姓名: </span>
        <span class="expand-value">{{ row.lecturer }}</span>
      </el-col>
      <el-col :span="8">
        <span class="expand-key">联系方式: </span>
        <span class="expand-value">{{ row.tel }}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <span class="expand-key">创建时间: </span>
        <span class="expand-value">{{ row.createTime }}</span>
      </el-col>
      <el-col :span="8">
        <span class="expand-key">修改时间: </span>
        <span class="expand-value">{{ row.updateTime }}</span>
      </el-col>
      <el-col :span="8">
        <span class="expand-key">填入账号: </span>
        <span class="expand-value">{{ row.writeId }}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <span class="expand-key">备注: </span>
        <span class="expand-value">{{ row.remark }}</span>
      </el-col>
    </el-row>
  </div>
</template>
<script>
export default {
  props: {
    row: Object
  }
}
</script>
