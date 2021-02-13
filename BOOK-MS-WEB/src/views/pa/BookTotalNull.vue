<template>
  <div class="app-container">
    <div>
      <label style="font-size: 25px;">{{ this.$route.meta.title }}</label>
    </div>
    <div style="text-align:center">
      <div class="block">
        <h3 class="demonstration">选择开启时间段</h3>
        <el-date-picker
          v-model="xqidTime"
          type="datetimerange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="['12:00:00']"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
        <br>
        <Button type="success" style="margin: 20px" @click="confirm">确认开启</Button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BookTotalNull',
  data() {
    return {
      xqidTime: ''
    }
  },
  created() {
    this.$notify({
      title: '警告',
      message: '请确保所有信息已导入，才能开启填报！',
      type: 'warning'
    })
  },
  methods: {
    confirm() {
      this.$Modal.confirm({
        title: '提示',
        content: '<p>提交后将不可逆，请确保' +
          ' 开课计划，' +
          '学生表 ' +
          '已经全部导入再提交！</p>',
        onOk: () => {
          console.log(this.xqidTime)
          this.$Message.info('Clicked ok')
        },
        onCancel: () => {
          this.$Message.info('Clicked cancel')
        }
      })
    }
  }
}
</script>

<style scoped>
  .demonstration{
    margin: 20px;
  }

</style>
