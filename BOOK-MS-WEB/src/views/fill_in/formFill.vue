<template>
  <div class="subForm">
    <Form :label-width="100" :model="fromData" label-colon=":" label-position="right">
      <el-row :gutter="10">
        <el-col :span="8">
          <FormItem label="ISBN" required>
            <Input v-model="fromData.a" />
          </FormItem>
        </el-col>
        <el-col :span="8">
          <FormItem label="书名" required>
            <Input v-model="fromData.b" />
          </FormItem>
        </el-col>
        <el-col :span="8">
          <FormItem label="书名" required>
            <Input v-model="fromData.b" />
          </FormItem>
        </el-col>
      </el-row>
    </Form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      fromData: {
        a: '1',
        b: '1'
      }
    }
  }
}
</script>

<style scoped>
  .subForm{
    width: 80%;
    margin: 0 10%;
    margin-top: 100px;
  }
</style>
