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
            v-model="count.name"
            type="text"
            show-word-limit
          ></el-input>
          <el-input
            v-model="count.gender"
            type="text"
            show-word-limit
          ></el-input>
          <el-input
            v-model="count.age"
            type="text"
            show-word-limit
          ></el-input>
          <el-input
            v-model="count.address"
            show-word-limit
          ></el-input>
          <el-input
            v-model="count.qq"
            type="text"
            show-word-limit
          ></el-input>
          <el-input
            v-model="count.email"
            type="text"
            show-word-limit
          ></el-input>

          <el-input
            v-model="count.username"
            type="text"
            show-word-limit
          ></el-input>
          <el-input
            v-model="count.password"
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
          count:{
            address: '北京',
            age: '15',
            email: 'zhangsan@qq.com',
            gender: '女',
            id:'',
            name: '张三',
            password: '123',
            qq: '123456',
            username: 'zhangsan',
          },
          circleUrl:
            'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
          sizeList: ['large'],
        }
      },
      created(){


        getmassage().then(res =>{
          this.count=res.data;
          // console.log(res);
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
