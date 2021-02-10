<template>
  <div class="subForm">
    <Form ref="fromData" :label-width="100" :model="fromData" :rules="ruleValidate" label-colon=":" label-position="right">
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
          <Button icon="ios-search" shape="circle" size="small" type="info" @click="dialogTableVisible = true" style="margin-right: 20px">合作出版社搜索</Button>
          <Button size="small" type="error" @click="outsideOrder = false" v-if="outsideOrder">外采订单</Button>
          <Button size="small" type="success" @click="outsideOrder = true" v-if="!outsideOrder">取消外采</Button>
        </div>
        <div>
          <el-row :gutter="10">
            <el-col :lg="6" :md="8" :sm="12">
              <FormItem label="ISBN" prop="isbn" required>
                <Input v-model="fromData.isbn" :disabled="outsideOrder" />
              </FormItem>
            </el-col>
            <el-col :lg="6" :md="8" :sm="12">
              <FormItem label="书名" prop="bookName" required>
                <Input v-model="fromData.bookName" :disabled="outsideOrder" />
              </FormItem>
            </el-col>
            <el-col :lg="6" :md="8" :sm="12">
              <FormItem label="作者" prop="author" required>
                <Input v-model="fromData.author" :disabled="outsideOrder" />
              </FormItem>
            </el-col>
            <el-col :lg="6" :md="8" :sm="12">
              <FormItem label="价格" prop="pricing" required>
                <Input v-model="fromData.pricing" prefix="logo-yen" type="number" :disabled="outsideOrder" />
              </FormItem>
            </el-col>
            <el-col :lg="6" :md="8" :sm="12">
              <FormItem label="出版社名称" prop="publishingHouse" required>
                <Select v-model="fromData.publishingHouse" style="width:100%" :disabled="outsideOrder" v-if="outsideOrder">
                  <Option v-for="item in publishingHouseList" :key="item.uuid" :value="item.publishingHouse">{{ item.publishingHouse }}</Option>
                </Select>
                <Input v-model="fromData.publishingHouse" :disabled="outsideOrder" v-if="!outsideOrder"/>
              </FormItem>
            </el-col>
            <el-col :lg="6" :md="8" :sm="12">
              <FormItem label="出版社补充" prop="publishingHouseSupplement">
                <Select v-model="fromData.publishingHouseSupplement" style="width:100%" :disabled="outsideOrder" >
                  <Option v-for="(val, ind) in publishingHouseSupplements" :key="ind" :value="val">{{ val }}</Option>
                  <Option value="">空</Option>
                </Select>
              </FormItem>
            </el-col>
          </el-row>
        </div>
      </div>
      <div>
        <h3 style="margin: 20px 0;">版本信息</h3>
        <el-row :gutter="10">
          <el-col :lg="6" :md="8" :sm="12">
            <FormItem label="年份" prop="bookYear" required>
              <Input v-model="fromData.bookYear" />
            </FormItem>
          </el-col>
          <el-col :lg="6" :md="8" :sm="12">
            <FormItem label="版次" prop="bookVersion" required>
              <Input v-model="fromData.bookVersion" />
            </FormItem>
          </el-col>
          <el-col :lg="6" :md="8" :sm="12">
            <FormItem label="教材类型" prop="bookType" required>
              <Select v-model="fromData.bookType" style="width:100%" >
                <Option v-for="(val, ind) in bookTypes" :key="ind" :value="val">{{ val }}</Option>
                <Option value="">空</Option>
              </Select>
            </FormItem>
          </el-col>
        </el-row>
      </div>
      <div>
        <h3 style="margin: 20px 0;">选择填报班级</h3>
        <el-row :gutter="10">
          <CheckboxGroup v-model="fromData.claCheckbox" style="margin-left: 3rem" @on-change="checkboxChange">
            <el-col v-for="(val, ind) in claCheckboxs" :key="ind" :lg="6" :md="8" :sm="12" style="margin: .5rem 0">
              <Checkbox :label="val" />
            </el-col>
          </CheckboxGroup>
        </el-row>
      </div>
      <div>
        <h3 style="margin: 20px 0;">用书数量</h3>
        <div v-for="(val, ind) in claCheckboxs" :key="ind">
          <div v-if="clasCount[3][ind]">
            <el-row :gutter="10">
              <el-col :lg="6" :md="8" :sm="12">
                <div align="center" style="margin: .5rem 0">{{ val }}</div>
              </el-col>
              <el-col :lg="6" :md="8" :sm="12">
                <FormItem label="学生用书数" prop="studentBookCount">
                  <InputNumber v-model="clasCount[0][ind]" disabled type="number" />
                </FormItem>
              </el-col>
              <el-col :lg="6" :md="8" :sm="12">
                <FormItem label="教师用书数" required>
                  <InputNumber v-model="clasCount[1][ind]" type="number" :min="0" :max="100" @on-change="totalCountChange(ind)" />
                </FormItem>
              </el-col>
              <el-col :lg="6" :md="8" :sm="12">
                <FormItem label="共计" prop="totalBook">
                  <InputNumber v-model="clasCount[2][ind]" disabled type="number" />
                </FormItem>
              </el-col>
            </el-row>
          </div>
        </div>
      </div>
      <div>
        <h3 style="margin: 20px 0;">其他信息</h3>
        <el-row :gutter="10">
          <el-col :lg="6" :md="8" :sm="12">
            <FormItem label="授课老师" prop="lecturer" required>
              <Input v-model="fromData.lecturer" />
            </FormItem>
          </el-col>
          <el-col :lg="6" :md="8" :sm="12">
            <FormItem label="电话" prop="tel" required>
              <Input v-model="fromData.tel" />
            </FormItem>
          </el-col>
          <el-col :lg="6" :md="8" :sm="12">
            <FormItem label="所属教研室" prop="staffRoom" required>
              <Select v-model="fromData.staffRoom" style="width:100%" >
                <Option v-for="(val, ind) in staffRooms" :key="ind" :value="val">{{ val }}</Option>
              </Select>
            </FormItem>
          </el-col>
          <el-col :lg="24" :md="24" :sm="24">
            <FormItem label="备注" prop="remark" :required="!this.outsideOrder">
              <Input v-model="fromData.remark" placeholder="如果是外采教材订单，必须请填写备注。" type="textarea" class="inpTextarea"/>
            </FormItem>
          </el-col>
        </el-row>
      </div>
      <div align="center">
        <Button style="margin: 20px 10px" type="info" @click="handleReset('fromData')">重置</Button>
        <Button style="width: 15rem; margin: 20px 10px" type="success" @click="handleSubmit('fromData')">提交</Button>
      </div>
      <br>
      <br>
      <br>
    </Form>
    <!--------------------------------- 分割线 -------------------------------------------->
    <el-dialog :visible.sync="dialogTableVisible" :width="getWidth" title="从书籍库搜索">
      <!-- 搜索框 -->
      <el-select
        v-model="selectValue"
        :loading="loading"
        :remote-method="remoteMethod"
        filterable
        placeholder="请输入 ISBN、书名、作者"
        remote
        reserve-keyword
        style="width:90%; margin: 0 5%"
        @change="selectValueChange"
      >
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <div style="padding: 20px 5%">
        <el-row>
          <el-col :md="6" class="bookView" style="align-content: center">
            <div align="center">
              <el-image
                :preview-src-list="[bookDescription.bookImg]"
                :src="bookDescription.bookImg"
                style="width: 8rem; height: 12rem;"
              />
            </div>
            <div class="demonstration">图示</div>
          </el-col>
          <el-col :md="9" class="bookView">
            <div class="bookViewTitle">数据库中数据</div>
            <!-- 除了 出版社补充 都用ISBN来控制 -->
            <div v-if="bookDescription.isbn" class="bookView-data">
              <span class="bookView-expand-key">ISBN: </span>
              <span class="bookView-expand-value">{{ bookDescription.isbn }}</span>
            </div>
            <div v-if="bookDescription.isbn" class="bookView-data">
              <span class="bookView-expand-key">书名: </span>
              <span class="bookView-expand-value">{{ bookDescription.bookName }}</span>
            </div>
            <div v-if="bookDescription.isbn" class="bookView-data">
              <span class="bookView-expand-key">作者: </span>
              <span class="bookView-expand-value">{{ bookDescription.author }}</span>
            </div>
            <div v-if="bookDescription.isbn" class="bookView-data">
              <span class="bookView-expand-key">价格: </span>
              <span class="bookView-expand-value">{{ bookDescription.pricing }}（元）</span>
            </div>
            <div v-if="bookDescription.isbn" class="bookView-data">
              <span class="bookView-expand-key">出版社: </span>
              <span class="bookView-expand-value">{{ bookDescription.publishingHouse }}</span>
            </div>
            <div v-if="bookDescription.publishingHouseSupplement" class="bookView-data">
              <span class="bookView-expand-key">出版社补充: </span>
              <span class="bookView-expand-value">{{ bookDescription.publishingHouseSupplement }}</span>
            </div>
          </el-col>
          <el-col :md="9" class="bookView">
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
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogTableVisible = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="selectOk">选 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getBookOne, getBookStoreListTop10, getFillInfo, postBookTotal } from '@/api/fill'
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
      // 是否是外购订单 取相反 (就是默认是合作出版社的)
      outsideOrder: true,
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
        // 版本信息
        bookYear: '',
        bookVersion: '最新',
        bookType: '',
        // 其他填报信息
        lecturer: '',
        tel: '',
        staffRoom: '',
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
      bookTypes: [],
      staffRooms: [],
      // 表单校验
      ruleValidate: {
        isbn: [{ required: true, message: 'ISBN不能为空', trigger: 'blur' }],
        bookName: [{ required: true, message: '书名不能为空', trigger: 'blur' }],
        author: [{ required: true, message: '作者不能为空', trigger: 'blur' }],
        pricing: [{ required: true, message: '价格不能为空', trigger: 'blur' }],
        publishingHouse: [{ required: true, message: '出版社不能为空', trigger: 'blur' }],
        bookYear: [{ required: true, message: '年份不能为空', trigger: 'blur' }],
        lecturer: [{ required: true, message: '版次不能为空', trigger: 'blur' }],
        bookVersion: [{ required: true, message: '教材类型不能为空', trigger: 'blur' }],
        bookType: [{ required: true, message: '授课老师不能为空', trigger: 'blur' }],
        tel: [
          { required: true, message: '电话不能为空', trigger: 'blur' },
          { pattern: /^1(?:3\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\d|9\d)\d{8}$/, message: '号码格式错误' }
        ],
        staffRoom: [{ required: true, message: '所属教研室不能为空', trigger: 'blur' }]
      }
    }
  },
  computed: {
    getWidth() {
      if (this.screenWidth > 900) return '60%'
      return '95%'
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
        this.clasCount[2][i] = (Number.parseInt(adds[i].studentBookCount) + Number.parseInt(adds[i].teacherBookCount))
        this.clasCount[3][i] = true
        // 全选
        this.fromData.claCheckbox[i] = adds[i].cla
        // 回显数据
        copyBean(res.data, this.fromData)
      }
    })
    // 初始化下拉列表
    this.getPublishingHouse()
    getSelectorList('publishingHouseSupplements').then(res => {
      this.publishingHouseSupplements = res.data
    })
    getSelectorList('bookTypes').then(res => {
      this.bookTypes = res.data
    })
    getSelectorList('staffRooms').then(res => {
      this.staffRooms = res.data
    })
  },
  methods: {
    handleSubmit(name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          const arr = []
          copyBean(this.fromData, this.info)
          this.fromData.cooperativePublishingHouse = this.outsideOrder
          for (let i = 0; i < this.info.adds.length; i++) {
            if (this.clasCount[3][i]) {
              copyBean(this.fromData, this.info.adds[i])
              this.info.adds[i].studentBookCount = this.clasCount[0][i]
              this.info.adds[i].teacherBookCount = this.clasCount[1][i]
              this.info.adds[i].totalBook = this.clasCount[2][i]
              this.info.adds[i].cooperativePublishingHouse = this.outsideOrder
              arr.push(this.info.adds[i])
            }
          }
          this.info.adds = arr
          // 数据赋值完毕，开始填报
          const loading = this.$loading({
            lock: true,
            text: 'Loading',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          })
          postBookTotal(this.info).then(res => {
            loading.close()
            // 提示成功，进行跳转
            this.$Message.success('填报成功。')
            this.$router.push('/fill/tableFill')
          }).catch(err => {
            console.log(err)
            loading.close()
          })
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
      if (this.clasCount[1][i] === null) {
        this.clasCount[1][i] = 0
      } else if (Number.parseInt(this.clasCount[1][i]) < 1) {
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
      for (let i = 0; i < this.clasCount[1].length; i++) {
        this.clasCount[1][i] = 0
        this.clasCount[2][i] = this.clasCount[0][i]
      }
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
  .inpTextarea{
    width: 100%;
  }
</style>
