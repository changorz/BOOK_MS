<template>
  <div class="app-container">
    <div style="width: 1000px;margin:0 auto;">
      <label style="font-size: 25px;">{{ this.$route.meta.title }}</label>
      <div style="width:100%; margin: 10px; float: right">
        <div style="float: right; ">
          <Button @click="handleRender">添加出版社</Button>
        </div>
      </div>
      <div style="margin: 5px;">
        <Table :columns="publishingHouseColumns" :data="publishingHouseList" :loading="Tloading" border>
          <template slot="name" slot-scope="{ row }">
            <strong>{{ row.name }}</strong>
          </template>
          <template slot="action" slot-scope="{ row, index }">
            <Button size="small" type="error" @click="remove(index)">删除</Button>
          </template>
        </Table>
      </div>
    </div>
  </div>
</template>

<script>

import { getPublishingHouse, addPublishingHouse, deletePublishingHouse } from '@/api/table'

export default {
  name: 'XqidManage',
  data() {
    return {
      Tloading: true,
      publishingHouseColumns: [
        {
          title: '出版社全称',
          key: 'publishingHouse'
        },
        {
          title: '操作',
          slot: 'action',
          width: 150,
          align: 'center'
        }
      ],
      value: '',
      publishingHouseList: []
    }
  },
  created() {
    // 初始化表格数据
    this.getPublishingHouse()
  },
  methods: {
    handleRender() {
      this.$Modal.confirm({
        render: (h) => {
          return h('Input', {
            props: {
              value: this.value,
              autofocus: true,
              placeholder: '请输入出版社全称'
            },
            on: {
              input: (val) => {
                this.value = val
              }
            }
          })
        },
        onOk: () => {
          this.doAddXqid()
        }
      })
    },
    doAddXqid() {
      this.Tloading = true
      const data = {
        publishingHouse: this.value
      }
      addPublishingHouse(data).then(res => {
        this.Tloading = false
        this.$Message.success('添加成功。')
        this.getPublishingHouse()
      })
      this.value = ''
    },
    remove(index) {
      const uuid = this.publishingHouseList[index].uuid
      this.Tloading = true
      deletePublishingHouse(uuid).then(res => {
        this.Tloading = false
        this.$Message.success('删除成功。')
        this.getPublishingHouse()
      })
    },
    // 获取出版社列表
    getPublishingHouse() {
      this.Tloading = true
      getPublishingHouse().then(res => {
        this.publishingHouseList = res.queryResult.list
        this.Tloading = false
      })
    }
  }
}
</script>

<style scoped>

</style>
