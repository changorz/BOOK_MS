<template>
    <div  class="app-container">
      <div style="width: 1000px;margin:0 auto;">
        <label style="font-size: 25px;">{{ this.$route.meta.title }}</label>
        <div style="width:100%; margin: 10px; float: right">
          <div style="float: right; ">
            <Button @click="handleRender">新建学期</Button>
          </div>
        </div>
        <div style="margin: 5px;">
          <Table :columns="xqidColumns" :data="xqidData" :loading="Tloading" border >
            <template slot="name" slot-scope="{ row }">
              <strong>{{ row.name }}</strong>
            </template>
            <template slot="action" slot-scope="{ row, index }">
              <Button @click="active(index)" size="small" style="margin-right: 5px" type="primary">激活</Button>
              <Button @click="remove(index)" size="small" type="error">删除</Button>
            </template>
          </Table>
        </div>
      </div>
    </div>
</template>

<script>
  import { getXqidInfo } from '@/api/user'
  import { addXq, deleteXq, setActiveXq } from '@/api/common'
  import store from '@/store'

  export default {
  name: 'XqidManage',
  data() {
    return {
      Tloading: true,
      xqidColumns: [
        {
          title: '学期ID',
          key: 'value'
        },
        {
          title: '状态',
          key: 'state'
        },
        {
          title: '操作',
          slot: 'action',
          width: 150,
          align: 'center'
        }
      ],
      xqidData: [],
      value: ''
    }
  },
  created() {
    this.Tloading = true
    getXqidInfo().then(res => {
      this.xqidData = []
      this.Tloading = false
      this.setTable(res)
    })
  },
  methods: {
    // 激活
    async active(index) {
      this.Tloading = true
      await setActiveXq(this.xqidData[index].value).then(res => {
        this.$Message.success('操作成功')
        this.setTable(res)
        this.Tloading = false
      })
      // 修改store中的数据
      await store.dispatch('user/getXqidInfo')
      // await this.$router.replace('/dashboard')
      await this.$router.replace('/selectorManage/XqidManage')
      await this.$router.go(0)
    },
    handleRender() {
      this.$Modal.confirm({
        render: (h) => {
          return h('Input', {
            props: {
              value: this.value,
              autofocus: true,
              placeholder: '请输入学期ID，例如：2020-2021-1'
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
      addXq(this.value).then(res => {
        this.$Message.success('新建成功。')
        this.setTable(res)
      })
      this.value = ''
    },
    remove(index) {
      deleteXq(this.xqidData[index].value).then(res => {
        this.$Message.success('删除成功。')
        this.setTable(res)
        console.log(res)
      })
    },
    setTable(res) {
      this.xqidData = []
      this.Tloading = false
      for (let i = 0; i < res.data.xqidList.length; i++) {
        if (res.data.xqidList[i] === res.data.activeXqid) {
          this.xqidData.push({ value: res.data.xqidList[i], state: '已激活' })
          continue
        }
        this.xqidData.push({ value: res.data.xqidList[i] })
      }
    }
  }
}
</script>

<style scoped>

</style>
