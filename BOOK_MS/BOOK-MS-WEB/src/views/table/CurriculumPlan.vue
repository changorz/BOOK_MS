<template>
  <div class="app-container">
    <div>
      <label style="font-size: 25px;">{{ this.$route.meta.title }}</label>
    </div>
    <div class="input_top">
      <!--   TODO 按班级分类查询 什么的   -->
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
      border
      highlight-row
      @on-current-change="onCurrentChange"
    />
    <Page
      :current="page.current"
      :page-size="page.size"
      :page-size-opts="pageOpts"
      :total="page.total"
      show-sizer
      @on-change="currentChange"
      @on-page-size-change="pageSizeChange"
    />
    <!-- 抽屉代码 修改 -->
    <Drawer
      v-model="drawer"
      :mask-closable="false"
      :styles="styles"
      title="修改"
      width="720"
    >
      <Form :label-width="100" :model="currentData" label-colon=":" label-position="right">
        <el-row>
          <el-col :span="12">
            <FormItem label="年级" required>
              <Input v-model="currentData.grade" />
            </FormItem>
          </el-col>
          <el-col :span="12">
            <FormItem label="专业" required>
              <Input v-model="currentData.major" />
            </FormItem>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <FormItem label="二级学院">
              <Input v-model="currentData.twoLevelCollege" />
            </FormItem>
          </el-col>
          <el-col :span="12">
            <FormItem label="课程名称" required>
              <Input v-model="currentData.courseTitle" />
            </FormItem>
          </el-col>
        </el-row>
      </Form>
      <div class="demo-drawer-footer">
        <Button style="margin-right: 8px" @click="drawer = false">取消</Button>
        <Button type="primary" @click="drawerCommit">提交</Button>
      </div>
    </Drawer>
    <!-- 抽屉代码 添加 -->
    <Drawer
      v-model="addDrawer"
      :mask-closable="false"
      :styles="styles"
      title="添加书籍"
      width="720"
    >
      <Form :label-width="100" :model="addData" label-colon=":" label-position="right">
        <el-row>
          <el-col :span="12">
            <FormItem label="年级" required>
              <Input v-model="addData.grade" />
            </FormItem>
          </el-col>
          <el-col :span="12">
            <FormItem label="专业" required>
              <Input v-model="addData.major" />
            </FormItem>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <FormItem label="二级学院">
              <Input v-model="addData.twoLevelCollege" />
            </FormItem>
          </el-col>
          <el-col :span="12">
            <FormItem label="课程名称" required>
              <Input v-model="addData.courseTitle" />
            </FormItem>
          </el-col>
        </el-row>
      </Form>
      <div class="demo-drawer-footer">
        <Button style="margin-right: 8px" @click="addDrawer = false">取消</Button>
        <Button type="primary" @click="addBook">确认添加</Button>
      </div>
    </Drawer>
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
        <el-link type="success">年级</el-link>,
        <el-link type="success">专业</el-link>,
        <el-link type="success">二级学院</el-link>,
        <el-link type="success">课程名称</el-link>
      </div>
      <div style="margin: 5px 0 10px 0px">表中选填字段：
        <el-link type="warning">无</el-link>
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
    addCurriculumPlan,
    deleteCurriculumPlan,
    deleteCurriculumPlanByXqid,
    getCurriculumPlanList,
    importCurriculumPlanByExcel,
    putCurriculumPlan
  } from '@/api/table'
  import { clearObject } from '@/utils/bmsUtil'

  export default {
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
        uuid: '',
        grade: '',
        major: '',
        twoLevelCollege: '',
        courseTitle: ''
      },
      currentData: {
        uuid: '',
        grade: '',
        major: '',
        twoLevelCollege: '',
        courseTitle: ''
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
          title: '年级',
          key: 'grade',
          resizable: true,
          width: 150,
          align: 'center'
        },
        {
          title: '专业',
          key: 'major',
          resizable: true,
          align: 'center'
        },
        {
          title: '二级学院',
          key: 'twoLevelCollege',
          resizable: true,
          align: 'center'
        },
        {
          title: '课程名称',
          key: 'courseTitle',
          resizable: true,
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
      await importCurriculumPlanByExcel(param).then(res => {
        this.uploadLoading = false
        this.uploadModal = false
        this.$Message.success('成功添加' + res.data.count + '条数据')
        // eslint-disable-next-line handle-callback-err
      }).catch(err => {
        this.uploadLoading = false
      })
      // 更新成功或刷新表格数据
      await this.fetchData()
    },
    async deleteAll() {
      this.modal_loading = true
      await deleteCurriculumPlanByXqid().then(res => {
        clearObject(this.currentData)
        this.modal_loading = false
        this.deleteXqAll = false
        this.$Message.success('删除成功。')
      })
      // 更新成功或刷新表格数据
      await this.fetchData()
    },
    async addBook() {
      await addCurriculumPlan(this.addData).then(res => {
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
      console.log(this.currentData)
      await putCurriculumPlan(this.currentData).then(res => {
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
        deleteCurriculumPlan(this.currentData.uuid).then(res => {
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
      getCurriculumPlanList(this.page.current, this.page.size).then(response => {
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
