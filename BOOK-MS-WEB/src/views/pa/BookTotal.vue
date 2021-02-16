<template>
  <div class="app-container">
    <div>
      <label style="font-size: 25px;">{{ this.$route.meta.title }}</label>
    </div>
    <div class="top-circle">
      <el-row>
        <el-col :sm="3">
          <i-circle
            :size="120"
            :trail-width="4"
            :stroke-width="5"
            :percent="circleValue"
            stroke-linecap="square"
            stroke-color="#43a3fb"
          >
            <div class="demo-Circle-custom">
              <h1>4256</h1>
              <p>填报总数</p>
              <span>
                填报比例
                <i>{{ circleValue }}%</i>
              </span>
            </div>
          </i-circle>
        </el-col>
        <el-col :sm="4">
          <div style="font-size: 10px">
            <span class="expand-key">填报打回: </span>
            <span class="expand-value">
              <Tag color="volcano">{{ count.bookTotalCount_2 }}</Tag>
            </span><br>
            <span class="expand-key">有效填报: </span>
            <span class="expand-value">
              <Tag color="green">{{ count.bookTotalCount_1 }}</Tag>
            </span><br>
            <span class="expand-key">填报总数: </span>
            <span class="expand-value">
              <Tag color="purple">{{ Number.parseInt(count.bookTotalCount_0) + Number.parseInt(count.bookTotalCount_1) +Number.parseInt(count.bookTotalCount_2) }}</Tag>
            </span><br>
          </div>
        </el-col>
        <el-col :sm="10">
          <div style="font-size: 10px">
            <span class="expand-key">是否设置: </span>
            <span class="expand-value">
              <Tag color="green" v-if="fillTime.isFill"> 已设置</Tag>
              <Tag color="volcano" v-if="!fillTime.isFill"> 未开启</Tag>
            </span><br>
            <span class="expand-key">开启时间: </span>
            <span class="expand-value">
              <Time :time="fillTime.startTime" type="datetime" />&nbsp;
              <router-link :to="{ name: 'TimeChange', params: { startTime: fillTime.startTime,  endTime: fillTime.endTime }}">修改</router-link>
            </span><br>
            <span class="expand-key">结束时间: </span>
            <span class="expand-value">
              <Time :time="fillTime.endTime" type="datetime" />
            </span><br>
            <span class="expand-key">倒计时: </span>
            <span class="expand-value">
               <Tag color="lime" v-if="fillTime.status === 0"><Time :time="fillTime.startTime"/>开启</Tag>
               <Tag color="orange" v-if="fillTime.status === 1"><Time :time="fillTime.endTime"/>结束</Tag>
              <Tag color="error" v-if="fillTime.status === 2">已结束</Tag>
            </span><br>
          </div>
        </el-col>
        <el-col :sm="3" />
      </el-row>
    </div>
    <div class="input_top">
      <div style="display: inline-block">
        <label>学院：</label>
        <Select v-model="searchData.twoLevelCollege" style="width:150px">
          <Option v-for="(val, ind) in twoLevelColleges" :key="ind" :value="val">{{ val }}</Option>
        </Select>
      </div>
      <div style="display: inline-block; margin-left: 20px">
        <label>年级：</label>
        <Select v-model="searchData.grade" style="width:150px">
          <Option v-for="(val, ind) in grades" :key="ind" :value="val">{{ val }}</Option>
        </Select>
      </div>
      <div style="display: inline-block; margin-left: 20px">
        <label>专业：</label>
        <Select v-model="searchData.major" style="width:150px">
          <Option v-for="(val, ind) in majors" :key="ind" :value="val">{{ val }}</Option>
        </Select>
      </div>
      <div style="display: inline-block; margin-left: 20px">
        <label>状态：</label>
        <Select v-model="searchData.submitState" style="width:150px">
          <Option v-for="(val, ind) in submitStates" :key="ind" :value="val.value">{{ val.name }}</Option>
        </Select>
      </div>
      <Button type="success" @click="onSearch">查询</Button>
      <Button type="primary" @click="onReset">重置</Button>
      <Button type="primary" @click="downloadExcel">下载填报总表</Button>
    </div>
    <Table
      :columns="columns"
      :data="list"
      :loading="listLoading"
      @on-current-change="onCurrentChange"
    >
      <template slot="submitState" slot-scope="{ row }">
        <el-tag :type="row.submitState | statusFilter">{{ row.submitState | formatStata }}</el-tag>
      </template>
      <template slot="action" slot-scope="{ row, index }">
        <Button size="small" type="info" style="margin-right: 5px" @click="add(row)">修改</Button>
        <Button size="small" type="error">打回</Button>
      </template>
    </Table>
    <Page
      :current="page.current"
      :page-size="page.size"
      :page-size-opts="pageOpts"
      :total="page.total"
      show-sizer
      @on-change="currentChange"
      @on-page-size-change="pageSizeChange"
    />
  </div>
</template>

<script>
import {
  getBookTotalList
} from '@/api/table'
import { getCountAll, getFillTime, getselectsAllByBookTota } from '@/api/common'
import { downloadFillExcel } from '@/api/table'
import { convertRes2Blob } from '@/utils/bmsUtil'
import expandRow from './table-expand.vue'

export default {
  components: {
    // eslint-disable-next-line vue/no-unused-components
    expandRow
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        0: 'info',
        1: 'success',
        2: 'danger'
      }
      return statusMap[status]
    },
    formatStata(status) {
      const statusMap = {
        0: '未填报',
        1: '已填报',
        2: '被打回'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      searchData: {
        grade: '',
        major: '',
        twoLevelCollege: '',
        submitState: ''
      },
      circleValue: 0,
      count: {
        bookStoreCount: 0,
        bookTotalCount_0: 0,
        bookTotalCount_1: 0,
        bookTotalCount_2: 0,
        curriculumPlanCount: 0,
        studentInfoCount: 0
      },
      fillTime: {
        endTime: '0',
        isFill: true,
        startTime: '0',
        // 未开始，进行中， 已结束
        status: 0
      },
      // 表格加载动画
      listLoading: true,
      search: '',
      addData: {
        author: null,
        bookName: '',
        bookType: '',
        bookVersion: '',
        bookYear: null,
        cla: '',
        courseTitle: '',
        createTime: '',
        grade: '',
        isbn: '',
        lecturer: null,
        major: '',
        pricing: 0,
        publishingHouse: null,
        publishingHouseSupplement: null,
        remark: null,
        staffRoom: '',
        studentBookCount: null,
        submitState: 0,
        teacherBookCount: null,
        tel: null,
        totalBook: null,
        totalPricing: null,
        twoLevelCollege: '',
        updateTime: null,
        uuid: '',
        writeId: null,
        xqid: ''
      },
      currentData: {
        author: null,
        bookName: '',
        bookType: '',
        bookVersion: '',
        bookYear: null,
        cla: '',
        courseTitle: '',
        createTime: '',
        grade: '',
        isbn: '',
        lecturer: null,
        major: '',
        pricing: 0,
        publishingHouse: null,
        publishingHouseSupplement: null,
        remark: null,
        staffRoom: '',
        studentBookCount: null,
        submitState: 0,
        teacherBookCount: null,
        tel: null,
        totalBook: null,
        totalPricing: null,
        twoLevelCollege: '',
        updateTime: null,
        uuid: '',
        writeId: null,
        xqid: ''
      },
      styles: {
        height: 'calc(100% - 55px)',
        overflow: 'auto',
        paddingBottom: '53px',
        position: 'static'
      },
      list: [],
      columns: [
        {
          type: 'expand',
          width: 50,
          render: (h, params) => {
            return h(expandRow, {
              props: {
                row: params.row
              }
            })
          }
        },
        {
          title: '专业',
          key: 'major',
          align: 'center'
        },
        {
          title: '班级',
          key: 'cla',
          align: 'center'
        },
        {
          title: '课程名称',
          key: 'courseTitle',
          align: 'center'
        },
        {
          title: '使用教材名称',
          key: 'bookName',
          width: 220,
          align: 'center',
          tooltip: (h, params) => {
            const texts = params.row.bookName
            return h('div', [
              h('Tooltip', {
                props: {
                  placement: 'bottom',
                  transfer: true
                }
              }, [texts, h('span', {
                slot: 'content',
                style: {
                  whiteSpace: 'normal'
                }
              }, params.row.bookName)
              ])
            ])
          }
        },
        {
          title: '定价(元)',
          key: 'pricing',
          resizable: true,
          width: 90,
          align: 'center'
        },
        {
          title: '出版社全称',
          key: 'publishingHouse',
          align: 'center'
        },
        {
          title: '填报状态',
          slot: 'submitState',
          align: 'center'
        },
        {
          title: '操作',
          slot: 'action',
          width: 150,
          align: 'center'
        }
      ],
      page: {
        current: 1,
        size: 10,
        total: 1
      },
      pageOpts: [10, 20, 50, 100],
      // 下拉列表值
      grades: [],
      majors: [],
      twoLevelColleges: [],
      submitStates: [
        { value: '0', name: '未填报' },
        { value: '1', name: '已填报' },
        { value: '2', name: '打回' }
      ]
    }
  },
  created() {
    // 检验是否开启填报
    getFillTime().then(res => {
      if (!res.data.isFill) {
        this.warning(false)
        this.$router.push('/PA/BookTotalNull')
      }
      this.fillTime = res.data
      if (new Date().getTime() > new Date(this.fillTime.endTime).getTime()) {
        this.fillTime.status = 2
      } else if (new Date().getTime() > new Date(this.fillTime.startTime).getTime()) {
        this.fillTime.status = 1
      } else {
        this.fillTime.status = 0
      }
    })
    this.fetchData()
    // count 获取
    getCountAll().then(res => {
      this.count = res.data
      this.circleValue = (Number.parseInt(this.count.bookTotalCount_1) + Number.parseInt(this.count.bookTotalCount_2)) / (Number.parseInt(this.count.bookTotalCount_0) + Number.parseInt(this.count.bookTotalCount_1) + Number.parseInt(this.count.bookTotalCount_2))
      this.circleValue = Number.parseFloat(this.circleValue) * 100
      if (this.circleValue > 0 && this.circleValue < 0.01) {
        this.circleValue = 0.01
      }
      this.circleValue = Number.parseFloat(this.circleValue.toFixed(2))
    })
    // 获取选择列表值
    getselectsAllByBookTota().then(res => {
      this.grades = res.data.bookTotal_grades
      this.majors = res.data.bookTotal_majors
      this.twoLevelColleges = res.data.bookTotal_twoLevelColleges
    })
  },
  methods: {
    downloadExcel() {
      downloadFillExcel().then(res => {
        convertRes2Blob(res)
      })
    },
    onSearch() {
      this.page.current = 1
      this.fetchData()
    },
    onReset() {
      this.searchData.grade = ''
      this.searchData.major = ''
      this.searchData.twoLevelCollege = ''
      this.searchData.submitState = ''
      this.page.current = 1
      this.fetchData()
    },
    onCurrentChange(currentRow, oldCurrentRow) {
      this.currentData = currentRow
    },
    currentChange(e) {
      this.page.current = e
      this.fetchData()
    },
    pageSizeChange(e) {
      this.page.size = e
      this.fetchData()
    },
    fetchData() {
      this.listLoading = true
      getBookTotalList(this.page.current, this.page.size, this.search, this.searchData).then(response => {
        this.list = response.data.records
        this.page.current = response.data.current
        this.page.size = response.data.size
        this.page.total = response.data.total
        this.listLoading = false
      })
    },
    add(row) {
      const uuid = row.uuid
      this.$router.push({ name: 'formFill', params: { uuid }})
    },
    warning(nodesc) {
      this.$notify({
        title: '警告',
        message: '数据库无记录，请开启填报',
        type: 'warning'
      })
    }
  }
}
</script>
<style lang="less" scoped>
  .ivu-page {
    text-align: center;
    padding-top: 30px;
  }

  .input_top {
    margin: 20px;

    .ivu-btn {
      margin-left: 15px;
    }
  }

  .ivu-table-row-highlight td {
    background-color: pink;
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

  .ivu-input {
    width: 200px;
  }

  .top-circle{
    padding: 20px;
    padding-left: 50px;
    padding-bottom: 10px;
  }

  .demo-Circle-custom{
    & h1{
      color: #3f414d;
      font-size: 14px;
      font-weight: normal;
    }
    & p{
      color: #657180;
      font-size: 1%;
      margin: 5px 0 8px;
    }
    & span{
      display: block;
      padding-top: 8px;
      color: #657180;
      font-size: 7px;
      &:before{
        content: '';
        display: block;
        width: 50px;
        height: 1px;
        margin: 0 auto;
        background: #e0e3e6;
        position: relative;
        top: -15px;
      };
    }
    & span i{
      font-style: normal;
      color: #3f414d;
    }
  }
</style>
