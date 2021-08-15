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
        <Button type="warning" style="margin: 20px" @click="confirm">确认修改</Button>
      </div>
    </div>
  </div>
</template>

<script>
import { changeTime } from '@/api/table'
export default {
  name: 'BookTotalNull',
  data() {
    return {
      xqidTime: ['', '']
    }
  },
  created() {
    this.xqidTime[0] = this.$route.params.startTime
    this.xqidTime[1] = this.$route.params.endTime
  },
  methods: {
    confirm() {
      this.$Modal.confirm({
        title: '提示',
        content: '<p>提交后，将影响到填报，是否确认？</p>',
        onOk: () => {
          const data = {
            startTime: this.xqidTime[0],
            endTime: this.xqidTime[1]
          }
          console.log(data)
          changeTime(data).then(res => {
            this.$Message.success('修改成功')
            this.$router.push('/PA/BookTotal')
          })
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
