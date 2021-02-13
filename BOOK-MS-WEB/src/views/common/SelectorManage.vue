<template>
  <div class="app-container">
    <div>
      <label style="font-size: 25px;">{{ this.$route.meta.title }}</label>
    </div>
    <div>
      <el-row>
        <div v-for="(val, ind) in selectList" :key="ind">
          <el-col :lg="8">
            <div class="grid-content bg-purple sle-table">
              <Divider>
                {{val.name}}
              </Divider>
              <div style="padding: 0 10px">
                <label>总数：{{ val.length }}</label>
                <Button size="small" style="float: right" type="success" @click="addBut(ind)">添加</Button>
              </div>
              <Table :columns="val.columns" :data="val.data" height="300">
                <template slot="name" slot-scope="{ row }">
                  <strong>{{ row.name }}</strong>
                </template>
                <template :slot="val.columns[1].slot" slot-scope="{ row, index }">
                  <Button size="small" type="error" @click="remove(ind, row.value)">删除</Button>
                </template>
              </Table>
            </div>
          </el-col>
        </div>
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
      selectList: [
        {
          name: '出版社补充',
          type: 'publishingHouseSupplements',
          data: [],
          length: 0,
          columns: [
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
          ]
        },
        {
          name: '二级学院',
          type: 'twoLevelColleges',
          data: [],
          length: 0,
          columns: [
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
          ]
        },
        {
          name: '教材类型',
          type: 'bookTypes',
          data: [],
          length: 0,
          columns: [
            {
              title: '名称',
              key: 'value',
              align: 'center'
            },
            {
              title: '操作',
              slot: 'bookType',
              width: 150,
              align: 'center'
            }
          ]
        },
        {
          name: '教研室',
          type: 'staffRooms',
          data: [],
          length: 0,
          columns: [
            {
              title: '名称',
              key: 'value',
              align: 'center'
            },
            {
              title: '操作',
              slot: 'staffRoom',
              width: 150,
              align: 'center'
            }
          ]
        },
        {
          name: '年份',
          type: 'bookYears',
          data: [],
          length: 0,
          columns: [
            {
              title: '名称',
              key: 'value',
              align: 'center'
            },
            {
              title: '操作',
              slot: 'bookYear',
              width: 150,
              align: 'center'
            }
          ]
        },
        {
          name: '版次',
          type: 'bookVersions',
          data: [],
          length: 0,
          columns: [
            {
              title: '名称',
              key: 'value',
              align: 'center'
            },
            {
              title: '操作',
              slot: 'bookVersion',
              width: 150,
              align: 'center'
            }
          ]
        }
      ]
    }
  },
  created() {
    for (let i = 0; i < this.selectList.length; i++) {
      this.getCommon(i)
    }
  },
  methods: {
    addbookType() {
      this.$Modal.confirm({
        render: (h) => {
          return h('Input', {
            props: {
              autofocus: true,
              placeholder: '请输入添加的名称'
            },
            on: {
              input: (val) => {
                this.bookTypeValue = val
              }
            }
          })
        },
        onOk: () => {
          this.doAddbookType()
        }
      })
    },
    // 公共的添加按钮方法
    addBut(i) {
      this.$Modal.confirm({
        render: (h) => {
          return h('Input', {
            props: {
              autofocus: true,
              placeholder: '请输入添加的名称'
            },
            on: {
              input: (val) => {
                this.inputValue = val
              }
            }
          })
        },
        onOk: () => {
          this.add(i, this.inputValue)
        }
      })
    },
    // 公共的添加方法
    async add(i, value) {
      await addSelector(this.selectList[i].type, value).then(res => {
        this.$Message.success('添加成功。')
      })
      await this.getCommon(i)
    },
    // 公共的删除方法
    async remove(i, value) {
      await deleteSelector(this.selectList[i].type, value).then(res => {
        this.$Message.success('删除成功。')
      })
      await this.getCommon(i)
    },
    // 公共的刷新列表
    async getCommon(i) {
      const selectJson = this.selectList[i]
      await getSelectorList(selectJson.type).then(res => {
        selectJson.length = res.data.length
        selectJson.data = []
        for (let i = 0; i < res.data.length; i++) {
          selectJson.data.push({ value: res.data[i] })
        }
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
