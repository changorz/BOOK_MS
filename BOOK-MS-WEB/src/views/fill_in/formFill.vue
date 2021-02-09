<template>
  <div class="subForm">
    <Form :label-width="100" :model="fromData" :rules="ruleValidate" label-colon=":" label-position="right" ref="fromData">
      <div>
        <h1 style="text-align: center">书籍填报</h1>
        <el-row :gutter="10" style=" margin: 20px 0">
          <el-col :md="4" :sm="12">
            <span class="expand-key">年级: </span>
            <span class="expand-value">{{ info.grade }}</span>
          </el-col>
          <el-col :md="6" :sm="12">
            <span class="expand-key">专业: </span>
            <span class="expand-value">{{ info.major }}</span>
          </el-col>
          <el-col :md="6" :sm="12">
            <span class="expand-key">学院: </span>
            <span class="expand-value">{{ info.twoLevelCollege }}</span>
          </el-col>
          <el-col :md="8" :sm="12">
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
            <el-col :lg="6" :md="8" :sm="12">
              <FormItem  label="ISBN" prop="isbn" required>
                <Input v-model="fromData.isbn" />
              </FormItem>
            </el-col>
            <el-col :lg="6" :md="8" :sm="12">
              <FormItem  label="书名" prop="bookName" required>
                <Input v-model="fromData.bookName" />
              </FormItem>
            </el-col>
            <el-col :lg="6" :md="8" :sm="12">
              <FormItem  label="作者" prop="author" required>
                <Input v-model="fromData.author" />
              </FormItem>
            </el-col>
            <el-col :lg="6" :md="8" :sm="12">
              <FormItem  label="价格" prop="pricing" required>
                <Input prefix="logo-yen" type="number" v-model="fromData.pricing"/>
              </FormItem>
            </el-col>
            <el-col :lg="6" :md="8" :sm="12">
              <FormItem  label="出版社名称" prop="publishingHouse" required>
                <Select style="width:100%" v-model="fromData.publishingHouse">
                  <Option :key="item.uuid" :value="item.publishingHouse" v-for="item in publishingHouseList">{{ item.publishingHouse }}</Option>
                </Select>
              </FormItem>
            </el-col>
            <el-col :lg="6" :md="8" :sm="12">
              <FormItem  label="出版社补充" prop="publishingHouseSupplement">
                <Select style="width:100%" v-model="fromData.publishingHouseSupplement">
                  <Option :key="ind" :value="val" v-for="(val, ind) in publishingHouseSupplements">{{ val }}</Option>
                  <Option value=''>空</Option>
                </Select>
              </FormItem>
            </el-col>
          </el-row>
        </div>
      </div>
      <div>
        <h3 style="margin: 20px 0;">选择填报班级</h3>
        <el-row :gutter="10">
          <CheckboxGroup @on-change="checkboxChange" style="margin-left: 3rem" v-model="fromData.claCheckbox">
            <el-col :key="ind" :lg="6" :md="8" :sm="12" style="margin: .5rem 0" v-for="(val, ind) in claCheckboxs">
              <Checkbox :label="val" ></Checkbox>
            </el-col>
          </CheckboxGroup>
        </el-row>
      </div>
      <div>
        <h3 style="margin: 20px 0;">版本信息</h3>
        <el-row :gutter="10">
          <el-col :lg="6" :md="8" :sm="12">
            <FormItem  label="年份" prop="bookYear" required>
              <Input v-model="fromData.bookYear" />
            </FormItem>
          </el-col>
          <el-col :lg="6" :md="8" :sm="12">
            <FormItem  label="版次" prop="bookYersion" required>
              <Input v-model="fromData.bookYersion" />
            </FormItem>
          </el-col>
          <el-col :lg="6" :md="8" :sm="12">
            <FormItem  label="教材类型" prop="bookType" required>
              <Input v-model="fromData.bookType" />
            </FormItem>
          </el-col>
        </el-row>
      </div>
      <div>
        <h3 style="margin: 20px 0;">用书数量</h3>
        <div :key="ind" v-for="(val, ind) in claCheckboxs" v-if="clasCount[3][ind]">
          <el-row :gutter="10">
            <el-col :lg="6" :md="8" :sm="12">
              <div align="center" style="margin: 20px 0">{{val}}</div>
            </el-col>
            <el-col :lg="6" :md="8" :sm="12">
              <FormItem  label="学生用书数" prop="studentBookCount" >
                <Input disabled type="number" v-model="clasCount[0][ind]" />
              </FormItem>
            </el-col>
            <el-col :lg="6" :md="8" :sm="12">
              <FormItem label="教师用书数" required>
                <Input @on-change="totalCountChange(ind)" type="number" v-model="clasCount[1][ind]"/>
              </FormItem>
            </el-col>
            <el-col :lg="6" :md="8" :sm="12">
              <FormItem  label="总数" prop="totalBook" >
                <Input disabled type="number" v-model="clasCount[2][ind]"/>
              </FormItem>
            </el-col>
          </el-row>
        </div>
      </div>
      <div>
        <h3 style="margin: 20px 0;">其他信息</h3>
        <el-row :gutter="10">
          <el-col :lg="6" :md="8" :sm="12">
            <FormItem  label="授课老师" prop="lecturer" required>
              <Input v-model="fromData.lecturer" />
            </FormItem>
          </el-col>
          <el-col :lg="6" :md="8" :sm="12">
            <FormItem  label="电话" prop="tel" required>
              <Input v-model="fromData.tel" />
            </FormItem>
          </el-col>
          <el-col :lg="6" :md="8" :sm="12">
            <FormItem  label="所属教研室" prop="staff_room" required>
              <Input v-model="fromData.staff_room" />
            </FormItem>
          </el-col>
          <el-col :lg="24" :md="24" :sm="24">
            <FormItem  label="备注" prop="remark">
              <Input placeholdert="如果是自选教材，请填写备注。" style="width: 90%;" type="textarea" v-model="fromData.remark"/>
            </FormItem>
          </el-col>
        </el-row>
      </div>
      <div align="center">
        <Button @click="handleReset('fromData')" style="margin: 20px 10px" type="info">重置</Button>
        <Button @click="handleSubmit('fromData')" style="width: 15rem; margin: 20px 10px" type="success">提交</Button>
      </div>
      <br>
      <br>
      <br>
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
          <el-col   :md="6" class="bookView" style="align-content: center">
            <div align="center">
              <el-image
                :preview-src-list="[bookDescription.bookImg]"
                :src="bookDescription.bookImg"
                style="width: 8rem; height: 12rem;"
              >
              </el-image>
            </div>
            <div class="demonstration">图示</div>
          </el-col>
          <el-col   :md="9" class="bookView">
            <div class="bookViewTitle">数据库中数据</div>
            <!-- 除了 出版社补充 都用ISBN来控制 -->
            <div class="bookView-data" v-if="bookDescription.isbn">
              <span class="bookView-expand-key">ISBN: </span>
              <span class="bookView-expand-value">{{ bookDescription.isbn }}</span>
            </div>
            <div class="bookView-data" v-if="bookDescription.isbn">
              <span class="bookView-expand-key">书名: </span>
              <span class="bookView-expand-value">{{ bookDescription.bookName }}</span>
            </div>
            <div class="bookView-data" v-if="bookDescription.isbn">
              <span class="bookView-expand-key">作者: </span>
              <span class="bookView-expand-value">{{ bookDescription.author }}</span>
            </div>
            <div class="bookView-data" v-if="bookDescription.isbn">
              <span class="bookView-expand-key">价格: </span>
              <span class="bookView-expand-value">{{ bookDescription.pricing }}（元）</span>
            </div>
            <div class="bookView-data" v-if="bookDescription.isbn">
              <span class="bookView-expand-key">出版社: </span>
              <span class="bookView-expand-value">{{ bookDescription.publishingHouse }}</span>
            </div>
            <div class="bookView-data" v-if="bookDescription.publishingHouseSupplement">
              <span class="bookView-expand-key">出版社补充: </span>
              <span class="bookView-expand-value">{{ bookDescription.publishingHouseSupplement }}</span>
            </div>
          </el-col>
          <el-col   :md="9" class="bookView">
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
        <el-button @click="selectOk" size="mini" type="primary">选 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import { getBookOne, getBookStoreListTop10, getFillInfo } from '@/api/fill'
  import { copyBean } from '@/utils/bmsUtil'
  import { getPublishingHousePublic, getSelectorList } from '@/api/common'

  export default {
  data() {
    return {
      // 自定义对话框
      options: [],
      dialogTableVisible: false,
      loading: false,
      // 搜索框绑定value
      selectValue: '',
      // 选中书籍详细信息
      bookDescription: {},
      // 用于渲染页面的数据
      info: { },
      // 表单中填的数据
      fromData: {
        // 书籍信息
        isbn: '',
        bookName: '',
        author: '',
        pricing: '',
        publishingHouse: '',
        publishingHouseSupplement: '',
        // 班级选择
        claCheckbox: [],
        // // 用书数量
        // studentBookCount: 0,
        // teacherBookCount: '0',
        totalBook: 0,
        // 版本信息
        bookYear: '',
        bookYersion: '',
        bookType: '',
        // 其他填报信息
        lecturer: '',
        tel: '',
        staff_room: '',
        remark: ''
      },
      // 班级复选框
      claCheckboxs: [],
      // 每个班级用书数
      clasCount: [
        // 学生
        [],
        // 老师
        [],
        // 总数
        [],
        // 是否选择
        []
      ],
      // 屏幕宽
      screenWidth: document.body.clientWidth,
      // 屏幕高
      screeHeight: document.body.clientHeight,
      // 下拉列表
      publishingHouseList: [],
      publishingHouseSupplements: [],
      // 表单校验
      ruleValidate: {
        isbn: [{ required: true, message: 'ISBN不能为空', trigger: 'blur' }],
        bookName: [{ required: true, message: '书名不能为空', trigger: 'blur' }],
        author: [{ required: true, message: '作者不能为空', trigger: 'blur' }],
        pricing: [{ required: true, message: '价格不能为空', trigger: 'blur' }],
        publishingHouse: [{ required: true, message: '出版社不能为空', trigger: 'blur' }],
        bookYear: [{ required: true, message: '年份不能为空', trigger: 'blur' }],
        lecturer: [{ required: true, message: '版次不能为空', trigger: 'blur' }],
        bookYersion: [{ required: true, message: '教材类型不能为空', trigger: 'blur' }],
        bookType: [{ required: true, message: '授课老师不能为空', trigger: 'blur' }],
        tel: [{ required: true, message: '电话不能为空', trigger: 'blur' }],
        staff_room: [{ required: true, message: '所属教研室不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    getFillInfo(this.$route.params.uuid).then(res => {
      this.info = res.data
      // 设置班级选项，并且默认全选
      const adds = res.data.adds
      this.claCheckboxs = []
      this.fromData.claCheckbox = []
      for (let i = 0; i < adds.length; i++) {
        this.claCheckboxs[i] = adds[i].cla
        // 后面转成字符串是校验回显校验不通过问题
        this.clasCount[0][i] = adds[i].studentBookCount
        this.clasCount[1][i] = adds[i].teacherBookCount
        this.clasCount[2][i] = (Number.parseInt(adds[i].studentBookCount) + Number.parseInt(adds[i].teacherBookCount)) + ''
        this.clasCount[3][i] = true
        // 全选
        this.fromData.claCheckbox[i] = adds[i].cla
      }
      console.log(res.data)
    })
    // 初始化下拉列表
    this.getPublishingHouse()
    getSelectorList('publishingHouseSupplements').then(res => {
      this.publishingHouseSupplements = res.data
    })
  },
  computed: {
    getWidth() {
      if (this.screenWidth > 900) return '60%'
      return '95%'
    }
  },
  methods: {
    handleSubmit(name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          console.log(this.fromData)
        } else {
          this.$Message.error('校验失败!')
        }
      })
    },
    selectValueChange() {
      getBookOne(this.selectValue).then(res => {
        this.bookDescription = res.data
        console.log(res.data)
      })
    },
    selectOk() {
      this.dialogTableVisible = false
      copyBean(this.bookDescription, this.fromData)
    },
    totalCountChange(i) {
      console.log(i)
      console.log()
      if (this.clasCount[1][i] === '') {
        this.clasCount[1][i] = 0
      } else {
        this.clasCount[1][i] = Number.parseInt(this.clasCount[1][i])
      }
      this.clasCount[2][i] = Number.parseInt(this.clasCount[0][i]) + Number.parseInt(this.clasCount[1][i])
    },
    checkboxChange(data) {
      for (let i = 0; i < this.claCheckboxs.length; i++) {
        if (data.indexOf(this.claCheckboxs[i]) !== -1) {
          this.clasCount[3][i] = true
        } else {
          this.clasCount[3][i] = false
        }
      }
    },
    handleReset(name) {
      this.$refs[name].resetFields()
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
    },
    // 获取出版社列表
    getPublishingHouse() {
      getPublishingHousePublic().then(res => {
        this.publishingHouseList = res.queryResult.list
      })
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
    height: 17rem;
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
