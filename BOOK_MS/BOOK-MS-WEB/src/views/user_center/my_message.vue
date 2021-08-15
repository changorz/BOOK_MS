<template>
  <div>
      <el-container>
        <el-main >
          <el-col >

            <div class="demo-basic--circle">
              <div>
                <el-avatar
                  :size="100"
                  :src="circleUrl"
                ></el-avatar>
              </div>
            </div>
          </el-col>
          <el-input
            v-model="name"
            type="text"
            show-word-limit
          ></el-input>
          <el-input
            v-model="gender"
            type="text"
            show-word-limit
          ></el-input>
          <el-input
            v-model="age"
            type="text"
            show-word-limit
          ></el-input>
          <el-input
            v-model="address"
            show-word-limit
          ></el-input>
          <el-input
            v-model="qq"
            type="text"
            show-word-limit
          ></el-input>
          <el-input
            v-model="email"
            type="text"
            show-word-limit
          ></el-input>

          <el-input
            v-model="username"
            type="text"
            show-word-limit
          ></el-input>
          <el-input
            v-model="password"
            type="text"
            show-password
          ></el-input>
          <el-button-group style="margin-left: 45%;; padding: 3px 0" type="text">
            <el-button type="primary" size="medium" round>保存</el-button>
          </el-button-group>
        </el-main>
      </el-container>
  </div>

</template>

<script>
  import {getmassage} from '@/api/user'

    export default {
        name: "my_message",
      data() {
        return {
          text: '',
          input1: '',
          input2: '',
          input3: '',
          input4: '',
          input5: '',
          input6: '',
          input7: '',
          input8: '',
          circleUrl:
            'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
          sizeList: ['large'],
          name: '张三',
          gender: '女',
          age: '15',
          address: '北京',
          qq: '123456',
          email: 'zhangsan@qq.com',
          username: 'zhangsan',
          password: '123',
        }
      },
      created(){
        getInfo.then(res =>{
          console.log(res);
        })

      }

    }
</script>

<style scoped>


  .demo-basic--circle {
    margin-top: 30px;
    margin-left: 45%;
  }

</style>
