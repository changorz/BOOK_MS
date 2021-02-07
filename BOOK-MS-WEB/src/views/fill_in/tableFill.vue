<template>
  <div class="app-container">
    <div>
      <label style="font-size: 25px;">{{ this.$route.meta.title }}</label>
    </div>
    <div class="input_top">
      <div style="display: inline-block">
        <label>学院：</label>
        <Select style="width:150px" v-model="searchData.twoLevelCollege">
          <Option :key="ind" :value="val" v-for="(val, ind) in twoLevelColleges">{{ val }}</Option>
        </Select>
      </div>
      <div style="display: inline-block; margin-left: 20px">
        <label>年级：</label>
        <Select style="width:150px" v-model="searchData.grade">
          <Option :key="ind" :value="val" v-for="(val, ind) in grades">{{ val }}</Option>
        </Select>
      </div>
      <div style="display: inline-block; margin-left: 20px">
        <label>专业：</label>
        <Select style="width:150px" v-model="searchData.major">
          <Option :key="ind" :value="val" v-for="(val, ind) in majors">{{ val }}</Option>
        </Select>
      </div>
      <Button @click="onSearch" type="success">查询</Button>
      <Button @click="onReset" type="primary">重置</Button>
    </div>
    <Table
      :columns="columns"
      :data="list"
      :loading="listLoading"
    >
      <template slot="submitState" slot-scope="{ row }">
        <el-tag :type="row.submitState | statusFilter">{{ row.submitState | formatStata }}</el-tag>
      </template>
      <template slot="action" slot-scope="{ row, index }">
        <Button @click="show(index)" size="small" style="margin-right: 5px" type="info">查看</Button>
        <Button @click="remove(index)" size="small" type="warning">填报</Button>
      </template>
    </Table>
    <Page
      :current="page.current"
      :page-size="page.size"
      :page-size-opts="pageOpts"
      :total="page.total"
      @on-change="currentChange"
      @on-page-size-change="pageSizeChange"
      show-sizer
    />
  </div>
</template>

<script>
  import { getBookTotalList } from '@/api/table'
  import { getselectsAllByBookTota } from '@/api/common'

  export default {
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
        twoLevelCollege: ''
      },
      grades: [],
      majors: [],
      twoLevelColleges: [],
      // 表格加载动画
      listLoading: true,
      styles: {
        height: 'calc(100% - 55px)',
        overflow: 'auto',
        paddingBottom: '53px',
        position: 'static'
      },
      list: [],
      columns: [
        {
          title: '学院',
          key: 'twoLevelCollege',
          align: 'center'
        },
        {
          title: '年级',
          key: 'grade',
          align: 'center'
        },
        {
          title: '专业',
          key: 'major',
          align: 'center'
        },
        {
          title: '课程名称',
          key: 'courseTitle',
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
      pageOpts: [10, 20, 50, 100]
    }
  },
  created() {
    this.fetchData()
    // 获取选择列表值
    getselectsAllByBookTota().then(res => {
      this.grades = res.data.bookTotal_grades
      this.majors = res.data.bookTotal_majors
      this.twoLevelColleges = res.data.bookTotal_twoLevelColleges
    })
  },
  methods: {
    onSearch() {
      this.page.current = 1
      this.fetchData()
    },
    onReset() {
      this.searchData.grade = ''
      this.searchData.major = ''
      this.searchData.twoLevelCollege = ''
      this.page.current = 1
      this.fetchData()
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
      getBookTotalList(this.page.current, this.page.size, '', this.searchData).then(response => {
        this.list = response.data.records
        this.page.current = response.data.current
        this.page.size = response.data.size
        this.page.total = response.data.total
        this.listLoading = false
      })
    }
  }
}
</script>
<style lang="less">
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
</style>
