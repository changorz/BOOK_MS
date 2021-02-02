<template>
  <div  class="app-container">
    <div>
      <label style="font-size: 25px;">{{ this.$route.meta.title }}</label>
    </div>
    <div>
      <el-row>
        <el-col :span="12">
          <div class="grid-content bg-purple sle-table">
            <Divider>
              出版社补充
            </Divider>
            <div style="padding: 0 10px">
              <label>总数：{{publishingHouseSupplementCount}}</label>
              <Button type="success" size="small" style="float: right" @click="addPublishingHouseSupplement">添加</Button>
            </div>
            <Table :columns="publishingHouseSupplementColumns" :data="publishingHouseSupplementData" height="300">
              <template slot-scope="{ row }" slot="name">
                <strong>{{ row.name }}</strong>
              </template>
              <template slot-scope="{ row, index }" slot="publishingHouseSupplement">
                <Button type="error" size="small" @click="removePublishingHouseSupplement(index)">删除</Button>
              </template>
            </Table>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="grid-content bg-purple sle-table">
            <Divider>
              二级学院
            </Divider>
            <div style="padding: 0 10px">
              <label>总数：{{twoLevelCollegeCount}}</label>
              <Button @click="addTwoLevelCollege" size="small" style="float: right" type="success">添加</Button>
            </div>
            <Table :columns="twoLevelCollegeColumns" :data="twoLevelCollegeData" height="300">
              <template slot-scope="{ row }" slot="name">
                <strong>{{ row.name }}</strong>
              </template>
              <template slot="twoLevelCollege" slot-scope="{ row, index }">
                <Button @click="removeTwoLevelCollege(index)" size="small" type="error">删除</Button>
              </template>
            </Table>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  import { addSelector, deleteSelector, getSelectorList } from '@/api/common'

  export default {
  name: 'SelectorManage',
  data() {
    return {
      publishingHouseSupplement: 'publishingHouseSupplements',
      publishingHouseSupplementCount: 0,
      publishingHouseSupplementValue: '',
      publishingHouseSupplementColumns: [
        {
          title: '名称',
          key: 'value',
          align: 'center'
        },
        {
          title: '操作',
          slot: 'publishingHouseSupplement',
          width: 150,
          align: 'center'
        }
      ],
      publishingHouseSupplementData: [],
      // twoLevelCollege 二级学院
      twoLevelCollege: 'twoLevelColleges',
      twoLevelCollegeCount: 0,
      twoLevelCollegeValue: '',
      twoLevelCollegeColumns: [
        {
          title: '名称',
          key: 'value',
          align: 'center'
        },
        {
          title: '操作',
          slot: 'twoLevelCollege',
          width: 150,
          align: 'center'
        }
      ],
      twoLevelCollegeData: []
    }
  },
  created() {
    this.getpublishingHouseSupplements()
    this.getTwoLevelColleges()
  },
  methods: {
    // 出版社补充的方法
    async getpublishingHouseSupplements() {
      await getSelectorList(this.publishingHouseSupplement).then(res => {
        this.publishingHouseSupplementCount = res.data.length
        this.publishingHouseSupplementData = []
        for (let i = 0; i < res.data.length; i++) {
          this.publishingHouseSupplementData.push({ value: res.data[i] })
        }
      })
    },
    addPublishingHouseSupplement() {
      this.$Modal.confirm({
        render: (h) => {
          return h('Input', {
            props: {
              autofocus: true,
              placeholder: '请输入添加的名称'
            },
            on: {
              input: (val) => {
                this.publishingHouseSupplementValue = val
              }
            }
          })
        },
        onOk: () => {
          this.doAddPublishingHouseSupplement()
        }
      })
    },
    async doAddPublishingHouseSupplement() {
      await this.add(this.publishingHouseSupplement, this.publishingHouseSupplementValue)
      // 刷新这个列表
      await this.getpublishingHouseSupplements()
    },
    async removePublishingHouseSupplement(index) {
      await this.remove(this.publishingHouseSupplement, this.publishingHouseSupplementData[index].value)
      // 刷新这个列表
      await this.getpublishingHouseSupplements()
    },
    // 二级学院的方法 TODO 表格加载动画
    async getTwoLevelColleges() {
      await getSelectorList(this.twoLevelCollege).then(res => {
        this.twoLevelCollegeCount = res.data.length
        this.twoLevelCollegeData = []
        for (let i = 0; i < res.data.length; i++) {
          this.twoLevelCollegeData.push({ value: res.data[i] })
        }
      })
    },
    addTwoLevelCollege() {
      this.$Modal.confirm({
        render: (h) => {
          return h('Input', {
            props: {
              autofocus: true,
              placeholder: '请输入添加的名称'
            },
            on: {
              input: (val) => {
                this.twoLevelCollegeValue = val
              }
            }
          })
        },
        onOk: () => {
          this.doAddTwoLevelCollege()
        }
      })
    },
    async doAddTwoLevelCollege() {
      await this.add(this.twoLevelCollege, this.twoLevelCollegeValue)
      await this.getTwoLevelColleges()
    },
    async removeTwoLevelCollege(index) {
      await this.remove(this.twoLevelCollege, this.twoLevelCollegeData[index].value)
      await this.getTwoLevelColleges()
    },
    // 公共的添加方法
    async add(type, value) {
      await addSelector(type, value).then(res => {
        this.$Message.success('添加成功。')
      })
    },
    // 公共的删除方法
    async remove(type, value) {
      await deleteSelector(type, value).then(res => {
        this.$Message.success('删除成功。')
      })
    }
  }
}
</script>

<style scoped lang="less">
  .sle-table{
    border: 1px rgba(41, 105, 168, 0.76) solid;
    margin: 10px;
    padding: 5px;
    border-radius: 10px;
    .ivu-table-wrapper{
      margin-top: 10px;
    }
  }
</style>
