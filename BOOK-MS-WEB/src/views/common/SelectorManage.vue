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
              出版社补充
            </Divider>
            <div style="padding: 0 10px">
              <label>总数：12</label>
              <Button type="success" size="small" style="float: right">添加</Button>
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
      </el-row>
    </div>
  </div>
</template>

<script>
import { getSelectorList, addSelector, deleteSelector } from '@/api/common'
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
      publishingHouseSupplementData: []
    }
  },
  created() {
    this.getpublishingHouseSupplements()
  },
  methods: {
    // 出版社补充的方法
    getpublishingHouseSupplements() {
      getSelectorList(this.publishingHouseSupplement).then(res => {
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
    // 公共的添加方法
    add(type, value) {
      addSelector(type, value).then(res => {
        this.$Message.success('添加成功。')
      })
    },
    // 公共的删除方法
    remove(type, value) {
      deleteSelector(type, value).then(res => {
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
