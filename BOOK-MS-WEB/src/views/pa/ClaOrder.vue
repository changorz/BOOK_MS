<template>
  <div class="app-container"  id="printContent">
    <div>
      <label style="font-size: 25px;">{{ this.$route.meta.title }}</label>
    </div>
    <div class="input_top">
      <el-select
        v-model="claNameValue"
        filterable
        remote
        reserve-keyword
        placeholder="请输入关键词"
        :remote-method="remoteMethod"
        @change="onSearch"
        :loading="loading">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <Button type="info" @click="onSearch(claNameValue)">搜索</Button>
      <Button style="margin-left:5px;" type="primary" v-print="printObj">打印</Button>
      <div style="display: inline-block; float: right;margin-right: 10%;margin-top: 10px; font-size: 15px">
        <span class="expand-key">共计: </span>
        <span class="expand-value">
              <Tag color="volcano">{{ sum }}</Tag>元
        </span>
      </div>
    </div>
    <Table
      :columns="columns"
      :data="listTable"
      :loading="listLoading"
    />
    <div style="margin-top: 50px">
      <label style="font-size: 25px;">班级名单</label>
      <Badge status="success" text="在校" style="margin-left: 20px"/>
      <Badge status="error" text="离校" style="margin-left: 20px" />
    </div>
    <div style="padding: 20px">
      <el-row>
        <div v-for="item in claList" :key="item.uuid">
          <el-col :lg="3" :md="4" :sm="6" :xs="12" style="margin: 5px 0">
            <div style="display: inline-block">
              <Badge status="success" v-if="item.status === '在校'"/>
              <Badge status="error" v-if="item.status !== '在校'"/>
              {{item.xh}}：{{item.studentName}}
            </div>
          </el-col>
        </div>
      </el-row>
    </div>
  </div>
</template>

<script>
import { getAllClasspathByQuery, getClaOrder, getClaStudentList } from '@/api/table'
export default {
  data() {
    return {
      // 打印
      printObj: {
        id: 'printContent',
        popTitle: '打印页面标题文字111',
        extraHead: '<meta http-equiv="Content-Language"content="zh-cn"/>,<style> #printContent {  width: 100%; height: auto !important; } <style>'
      },
      // 远程加载 选择班级
      claNameValue: '2017计算机科学与技术1班',
      loading: false,
      options: [],
      // 一共多少钱
      sum: '0',
      // 班级列表
      claList: [],
      // 表格加载动画
      listLoading: false,
      listTable: [],
      columns: [
        {
          type: 'index',
          width: 60,
          align: 'center'
        },
        {
          title: '课程名称',
          key: 'courseTitle',
          resizable: true,
          width: 140,
          align: 'center'
        },
        {
          title: 'ISBN',
          key: 'isbn',
          resizable: true,
          width: 140,
          align: 'center'
        },
        {
          title: '教材名称',
          key: 'bookName',
          width: 500,
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
          title: '出版社',
          key: 'publishingHouse',
          align: 'center'
        },
        {
          title: '单价',
          key: 'pricing',
          resizable: true,
          width: 90,
          align: 'center'
        },
        {
          title: '人数',
          key: 'studentBookCount',
          resizable: true,
          width: 90,
          align: 'center'
        }
      ]
    }
  },
  created() {
  },
  methods: {
    remoteMethod(query) {
      if (query !== '') {
        this.loading = true
        getAllClasspathByQuery(query).then(res => {
          this.loading = false
          this.options = res.data.records.map(item => {
            return { value: item.cla, label: item.cla }
          })
        })
      } else {
        this.options = []
      }
    },
    onSearch(value) {
      this.listLoading = true
      // 加载表格
      getClaOrder(value).then(res => {
        this.listLoading = false
        this.listTable = res.data
        this.sum = 0
        this.listTable.map(e => { return Number.parseFloat(e.pricing) }).forEach(e => {
          this.sum += Number.parseFloat(e)
        })
      })
      // 加载名单
      getClaStudentList(value).then(res => {
        this.claList = res.queryResult.list
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

  .ivu-input {
    width: 200px;
  }
</style>
