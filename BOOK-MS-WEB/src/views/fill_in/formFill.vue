<template>
  <div class="subForm">
    <Form :label-width="100" :model="fromData" label-colon=":" label-position="right">
      <div>
        <h1 style="text-align: center">书籍填报</h1>
        <el-row :gutter="10" style="width:70%; margin: 30px 15%">
          <el-col :span="4">
            <span class="expand-key">年级: </span>
            <span class="expand-value">{{ info.grade }}</span>
          </el-col>
          <el-col :span="6">
            <span class="expand-key">专业: </span>
            <span class="expand-value">{{ info.major }}</span>
          </el-col>
          <el-col :span="6">
            <span class="expand-key">学院: </span>
            <span class="expand-value">{{ info.twoLevelCollege }}</span>
          </el-col>
          <el-col :span="8">
            <span class="expand-key">课程名称: </span>
            <span class="expand-value">{{ info.courseTitle }}</span>
          </el-col>
        </el-row>
      </div>
      <div>
        <h3 style="display: inline-block; margin: 20px 0">选择书籍</h3>
        <div style="display: inline-block; margin: 20px 10px">
          <Button @click="dialogTableVisible = true" icon="ios-search" shape="circle" size="small" type="info">搜索</Button>
        </div>
        <div>
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
                <Input v-model="info.courseTitle" />
              </FormItem>
            </el-col>
          </el-row>
        </div>
      </div>
      <div>
        <h3 style="margin: 20px 0;">选择填报班级</h3>
      </div>
      <div>
        <h3 style="margin: 20px 0;">其他信息</h3>
      </div>
      <div>
        底部
      </div>
      <div>
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
              <Input v-model="info.courseTitle" />
            </FormItem>
          </el-col>
        </el-row>
      </div>
    </Form>
    要填报的用fromData先存起来，然后看用户选中了那几个班级，然后修改info中的数据，传到后台完成修改
    <!--------------------------------- 分割线 -------------------------------------------->
    <el-dialog :visible.sync="dialogTableVisible" :width="getWidth" title="从书籍库搜索">
      <!-- 搜索框 -->
      <el-select
        :loading="loading"
        :remote-method="remoteMethod"
        @change="selectValueChange"
        filterable
        placeholder="请输入 ISBN、书名、作者"
        remote
        reserve-keyword
        style="width:90%; margin: 0 5%"
        v-model="selectValue"
      >
        <el-option
          :key="item.value"
          :label="item.label"
          :value="item.value"
          v-for="item in options"
        />
      </el-select>
      <div style="padding: 20px 5%">
        <el-row>
          <el-col :lg="6" class="bookView">
            <el-image
              :preview-src-list="[bookDescription.bookImg]"
              :src="bookDescription.bookImg"
              style="width: 100%; height: 90%">
            </el-image>
            <div class="demonstration">图示</div>
          </el-col>
          <el-col :lg="9" class="bookView">
            <div class="bookViewTitle">数据库中数据</div>
            <div class="bookView-data">
              <span class="bookView-expand-key">ISBN: </span>
              <span class="bookView-expand-value">{{ bookDescription.isbn }}</span>
            </div>
            <div class="bookView-data">
              <span class="bookView-expand-key">书名: </span>
              <span class="bookView-expand-value">{{ bookDescription.bookName }}</span>
            </div>
            <div class="bookView-data">
              <span class="bookView-expand-key">作者: </span>
              <span class="bookView-expand-value">{{ bookDescription.author }}</span>
            </div>
            <div class="bookView-data">
              <span class="bookView-expand-key">价格: </span>
              <span class="bookView-expand-value">{{ bookDescription.pricing }}（元）</span>
            </div>
            <div class="bookView-data">
              <span class="bookView-expand-key">出版社: </span>
              <span class="bookView-expand-value">{{ bookDescription.publishingHouse }}</span>
            </div>
            <div class="bookView-data">
              <span class="bookView-expand-key">出版社补充: </span>
              <span class="bookView-expand-value">{{ bookDescription.publishingHouseSupplement }}</span>
            </div>
          </el-col>
          <el-col :lg="9" class="bookView">
            <div class="bookViewTitle">爬取数据（补充）</div>
            <div class="bookView-data">
              <span class="bookView-expand-key">ISBN: </span>
              <span class="bookView-expand-value">{{ bookDescription.isbn }}</span>
            </div>
            <div class="bookView-data">
              <span class="bookView-expand-key">书名: </span>
              <span class="bookView-expand-value">{{ bookDescription.bookName }}</span>
            </div>
            <div class="bookView-data">
              <span class="bookView-expand-key">作者: </span>
              <span class="bookView-expand-value">{{ bookDescription.author }}</span>
            </div>
            <div class="bookView-data">
              <span class="bookView-expand-key">价格: </span>
              <span class="bookView-expand-value">{{ bookDescription.pricing }}（元）</span>
            </div>
            <div class="bookView-data">
              <span class="bookView-expand-key">出版社: </span>
              <span class="bookView-expand-value">{{ bookDescription.publishingHouse }}</span>
            </div>
            <div class="bookView-data">
              <span class="bookView-expand-key">出版社补充: </span>
              <span class="bookView-expand-value">{{ bookDescription.publishingHouseSupplement }}</span>
            </div>
          </el-col>
        </el-row>
      </div>
      <span class="dialog-footer" slot="footer">
        <el-button @click="dialogTableVisible = false" size="mini">取 消</el-button>
        <el-button @click="dialogTableVisible = false" size="mini" type="primary">选 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import { getBookOne, getBookStoreListTop10, getFillInfo } from '@/api/fill'

  export default {
  data() {
    return {
      // 自定义对话框
      options: [],
      dialogTableVisible: false,
      loading: false,
      selectValue: '',
      // 用于渲染页面的数据
      info: { },
      // 选中书籍详细信息
      bookDescription: {},
      // 表单中填的数据
      fromData: {
        a: '1',
        b: '1'
      },
      // 屏幕宽
      screenWidth: document.body.clientWidth,
      // 屏幕高
      screeHeight: document.body.clientHeight
    }
  },
  created() {
    getFillInfo(this.$route.params.uuid).then(res => {
      this.info = res.data
      console.log(res.data)
    })
  },
  computed: {
    getWidth() {
      if (this.screenWidth > 900) return '60%'
      return '95%'
    }
  },
  methods: {
    selectValueChange() {
      getBookOne(this.selectValue).then(res => {
        this.bookDescription = res.data
        console.log(res.data)
      })
    },
    remoteMethod(query) {
      if (query !== '') {
        this.loading = true
        getBookStoreListTop10(query).then(res => {
          this.options = []
          for (let i = 0; i < res.data.records.length; i++) {
            const temp = res.data.records[i]
            let str
            if (this.screenWidth > 900) {
              str = `[ ${temp.isbn} ][ ${this.formatStringLen(temp.bookName, 30)} ][ ${this.formatStringLen(temp.author, 30)} ]`
            } else {
              str = `${this.formatStringLen(temp.bookName, 30)}`
            }
            this.options[i] = { value: `${temp.uuid}`, label: `${str}` }
          }
          // 数据渲染完毕
          this.loading = false
        })
      } else {
        this.options = []
      }
    },
    formatStringLen(str, n) {
      var r = /[^\x00-\xff]/g
      if (str.replace(r, 'mm').length <= n) {
        for (let i = 0; i <= n - str.replace(r, 'mm').length; i++) {
          str += '    '
        }
        return str
      }
      // n = n - 3;
      var m = Math.floor(n / 2)
      for (var i = m; i < str.length; i++) {
        if (str.substr(0, i).replace(r, 'mm').length >= n) {
          return str.substr(0, i) + '...'
        }
      }
      return str
    }
  }
}

</script>

<style scoped>
  .subForm{
    width: 90%;
    margin: 0 5%;
    margin-top: 30px;
  }
  .expand-value{
    color: #20a0ff;
  }
  .bookView{
    height: 340px;
    padding: 20px;
  }
  .demonstration{
    text-align: center;
    margin-top: 10px;
    font-weight: 800;
  }
  .bookViewTitle{
    text-align: center;
    font-size: 20px;
    font-weight: 800;
    margin-bottom: 1rem;
  }
  .bookView-expand-key{
    color: #a0a0a0;
  }
  .bookView-expand-value{
    color: #000000;
  }
  .bookView-data{
    margin: .3rem 0;
  }
</style>
