<template>
  <div class="app-container">
    <div>
      <label style="font-size: 25px;">{{ this.$route.meta.title }}</label>
    </div>
    <div class="input_top">
      <Input v-model="search" placeholder="搜索书籍" style="width: auto;" @keyup.enter.native="onSearch" />
      <Button type="info" @click="onSearch">搜索</Button>
      <div style="float: right; margin-right: 20px">
        <Button @click="uploadModal = true">excel上传</Button>
        <Button type="success" @click="addDrawer = true">新增</Button>
        <Button type="primary" @click="updataBut">修改</Button>
        <Button type="warning" @click="deleteObj">删除</Button>
        <Button type="error" @click="deleteXqAll = true">学期数据删除</Button>
      </div>
    </div>
    <Table
      :columns="columns"
      :data="list"
      :loading="listLoading"
      highlight-row
      @on-current-change="onCurrentChange"
    >
      <template slot="submitState" slot-scope="{ row }">
        <el-tag :type="row.submitState | statusFilter">{{ row.submitState | formatStata }}</el-tag>
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
    <Modal v-model="deleteXqAll" width="360">
      <p slot="header" style="color:#f60; text-align:center">
        <Icon type="ios-information-circle" />
        <span>删除确认</span>
      </p>
      <div style="text-align:center">
        <p>{{ this.$store.getters.activeXqidValue }}的数据将会全部删除，无法恢复。</p>
        <p>是否继续删除？</p>
      </div>
      <div slot="footer">
        <Button :loading="modal_loading" long size="large" type="error" @click="deleteAll">删除</Button>
      </div>
    </Modal>
    <Modal
      v-model="uploadModal"
      :loading="uploadLoading"
      :styles="{top: '180px'}"
      ok-text="上传"
      title="Excel导入"
      @on-ok="fileload"
    >
      <div>表中必要字段：
        <el-link type="success">ISBN</el-link>,
        <el-link type="success">书名</el-link>,
        <el-link type="success">出版社全称</el-link>,
        <el-link type="success">作者</el-link>,
        <el-link type="success">定价</el-link>
      </div>
      <div style="margin: 5px 0 10px 0px">表中选填字段：
        <el-link type="warning">出版社补充</el-link>
      </div>
      <Upload
        :before-upload="handleUpload"
        :format="['xlsx','xls']"
        action=""
        type="drag"
      >
        <div style="padding: 20px 0">
          <Icon size="52" style="color: #3399ff" type="ios-cloud-upload" />
          <p>请选择正确的Excel文件</p>
        </div>
      </Upload>
      <div v-if="file !== null" style="padding: 10px 0 0 10px">上传文件: <p style="color: deeppink; display: inline-block">
        {{ file.name }}</p></div>
    </Modal>
  </div>
</template>

<script>
import {
  addBookStore,
  deleteBookStore,
  deleteBookStoreAllByXqid,
  getBookTotalList,
  importBookStoreByExcel,
  putBookStore
} from '@/api/table'
import { getFillTime } from '@/api/common'
import { clearObject } from '@/utils/bmsUtil'
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
      // 按学期删除
      deleteXqAll: false,
      modal_loading: false,
      // 文件上传组件
      uploadModal: false,
      uploadLoading: true,
      file: '',
      // 表格加载动画
      listLoading: true,
      // 抽屉控制
      drawer: false,
      addDrawer: false,
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
    // 检验是否开启填报
    getFillTime().then(res => {
      if (!res.data.isFill) {
        this.warning(false)
        this.$router.push('/PA/BookTotalNull')
      }
    })
    this.fetchData()
  },
  methods: {
    handleUpload(file) {
      this.file = file
      return false
    },
    async fileload() {
      if (this.file === '') {
        this.$Message.warning('未选择文件')
        this.uploadModal = false
        return
      }
      // 创建FormData对象
      const param = new FormData()
      // 将得到的文件流添加到FormData对象
      param.append('file', this.file, this.file.name)
      await importBookStoreByExcel(param).then(res => {
        this.uploadModal = false
        this.$Message.success('成功添加' + res.data.count + '条数据')
      })
      this.uploadLoading = false
      // 更新成功或刷新表格数据
      await this.fetchData()
    },
    async deleteAll() {
      this.modal_loading = true
      await deleteBookStoreAllByXqid().then(res => {
        clearObject(this.currentData)
        this.modal_loading = false
        this.deleteXqAll = false
        this.$Message.success('删除成功。')
      })
      // 更新成功或刷新表格数据
      await this.fetchData()
    },
    async addBook() {
      await addBookStore(this.addData).then(res => {
        clearObject(this.addData)
        this.addDrawer = false
        this.$Message.success('新增成功。')
      })
      // 更新成功或刷新表格数据
      await this.fetchData()
    },
    updataBut() {
      // 判断是否有数据选中
      if (this.currentData.uuid === '') {
        this.$Modal.warning({
          title: '警告',
          content: '未选中数据无法修改。'
        })
        return
      }
      this.drawer = true
    },
    async drawerCommit() {
      await putBookStore(this.currentData).then(res => {
        clearObject(this.currentData)
        this.drawer = false
        this.$Message.success('修改成功。')
      })
      // 更新成功或刷新表格数据
      await this.fetchData()
    },
    onSearch() {
      this.page.current = 1
      this.fetchData()
    },
    async deleteObj() {
      // 判断是否有数据选中
      if (this.currentData.uuid === '') {
        this.$Modal.warning({
          title: '警告',
          content: '未选中数据无法修改。'
        })
        return
      }
      await this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 确定删除
        deleteBookStore(this.currentData.uuid).then(res => {
          // 清除选择的数据
          clearObject(this.currentData)
          this.$Message.success('删除成功。')
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
      // 更新成功或刷新表格数据
      await this.fetchData()
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
      const data = {
      }
      getBookTotalList(this.page.current, this.page.size, this.search, data).then(response => {
        this.list = response.data.records
        this.page.current = response.data.current
        this.page.size = response.data.size
        this.page.total = response.data.total
        this.listLoading = false
      })
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
