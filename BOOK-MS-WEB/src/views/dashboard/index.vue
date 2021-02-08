<template>
  <div class="dashboard-container">
    <div class="dashboard-text">name: {{ name }}</div>
    <div>
      <Steps :current="1" status="wait">
        <Step content="创建当前学期" title="起始" />
        <Step content="导入excel1" title="导入Excel" />
        <Step content="导入excel2" title="导入Excel" />// 开课计划一定不能错
        <Step content="导入excel3" title="导入Excel" />
        <Step content="开启填报，让老师填写" title="填报1" />
        <Step content="填报停止，可以导出excel。。。" title="导出数据" />
        <Step content="缺书填报" title="填报2" />
        <Step content="打回通知" title="填报3" />
        <Step content="存档" title="结束" />
      </Steps>
    </div>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'

  export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
  .dashboard {
    &-container {
      margin: 30px;
    }

    &-text {
      font-size: 30px;
      line-height: 46px;
    }
  }
</style>
