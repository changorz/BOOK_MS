<template>
  <div>
    <el-container>
      <el-main >
        <el-col >
          <div class="demo-basic--circle">
            <div>
              <el-avatar
                :size="100"
                :src="message.img"
              ></el-avatar>
            </div>
          </div>
        </el-col>
        <el-input
          v-model="message.username"
          type="text"
          :disabled="true"
          show-word-limit
        >
          <template slot="prepend">账号:</template>
        </el-input>
        <el-input
          v-model="message.alias"
          type="text"
          show-word-limit
          :disabled="true"
        >
          <template slot="prepend">姓名:</template>
        </el-input>
        <el-input
          v-model="message.des"
          type="text"
          show-word-limit
          :disabled="true"
        >
          <template slot="prepend">描述:</template>
        </el-input>
        <el-input
          v-model="message.authority"
          type="text"
          :disabled="true"
          show-word-limit
        >
          <template slot="prepend">权限:</template>
        </el-input>

        <el-button-group style="margin-left: 47%;; padding: 3px 0" type="text">
          <el-button type="primary" size="medium" round @click="drawer = true">修改</el-button>
        </el-button-group>
      </el-main>
    </el-container>

    <Drawer
      v-model="drawer"
      :mask-closable="false"
      :styles="styles"
      title="修改"
      width="600"
    >
      <Form :label-width="100" :model="message" label-position="right"  label-colon=":">
        <el-row>
          <el-col :span="12">
            <FormItem label="账号"  required>
              <Input v-model="message.username" :disabled="true"/>
            </FormItem>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <FormItem label="姓名" required>
              <Input v-model="message.alias" />
            </FormItem>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12" required>
            <FormItem label="描述" required>
              <Input v-model="message.des" />
            </FormItem>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <FormItem label="权限" required>
              <Input v-model="message.authority" :disabled="true" />
            </FormItem>
          </el-col>
        </el-row>
      </Form>
      <div class="demo-drawer-footer">
        <Button style="margin-right: 8px" @click="drawer = false">取消</Button>
        <Button type="primary" @click="updateMessage">提交</Button>
      </div>
    </Drawer>
  </div>

</template>

<script>
import { getUserMessage,putUserMessage } from '@/api/message'



export default {
  name: "my_message",
  data() {
    return {
      sizeList: ['large'],
      message:{
        alias: "",
        authority: "",
        createTime: "",
        des: "",
        enabled: "",
        img: "",
        username: ""
      },
      drawer: false,
      styles: {
        height: 'calc(100% - 55px)',
        overflow: 'auto',
        paddingBottom: '53px',
        position: 'static'
      },
    }
  },
  created(){
    getUserMessage().then(res=>{
      this.message = res.data
      // console.log(res)
    })
  },
  methods:{
    async updateMessage() {
      await putUserMessage(this.message).then(res => {
        this.drawer = false
        this.$Message.success('修改成功。')
      })
      // 更新成功或刷新表格数据
      await getUserMessage().then(res=>{
        this.message = res.data
      })
    },
  }


}
</script>

<style scoped>


.demo-basic--circle {
  margin-top: 30px;
  margin-left: 45%;
}

.demo-drawer-footer {
  width: 100%;
  position: absolute;
  bottom: 0;
  left: 0;
  border-top: 1px solid #e8e8e8;
  padding: 10px 16px;
  text-align: right;
  background: #fff;
}

</style>
