<template>
  <div id="printContent" class="app-container">
    <div>
      <label style="font-size: 25px;">{{ this.$route.meta.title }}</label>
    </div>
    <div class="input_top">
      <Select v-model="publishingHouse" style="width:200px" placeholder="选择出版社">
        <Option v-for="item in publishingHouseList" :key="item.uuid" :value="item.publishingHouse">{{
          item.publishingHouse }}
        </Option>
      </Select>
      <Select v-model="publishingHouseSupplement" style="width:200px; margin-left: 10px" placeholder="出版社补充">
        <Option v-for="(val, ind) in publishingHouseSupplements" :key="ind" :value="val">{{ val }}</Option>
        <Option value="">空</Option>
      </Select>
      <Button type="info" @click="onSearch">搜索</Button>
      <div style="display: inline-block; float: right;margin-right: 10%;margin-top: 10px; font-size: 15px">
        <span class="expand-key">共计: </span>
        <span class="expand-value">
          <Tag color="volcano">123</Tag>元
        </span>
      </div>
    </div>
    <Table
      :columns="columns"
      :data="listTable"
      :loading="listLoading"
    />
  </div>
</template>

<script>
import { getPublishingHouse } from '@/api/table'
import { getSelectorList } from '@/api/common'
export default {
  data() {
    return {
      // 班级列表
      phList: [],
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
          title: '学校',
          key: 'shool',
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
          title: '作者',
          key: 'author',
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
          title: '总数量',
          key: 'totalBook',
          resizable: true,
          width: 90,
          align: 'center'
        },
        {
          title: '码洋',
          key: 'totalPricing',
          resizable: true,
          width: 90,
          align: 'center'
        }
      ],
      publishingHouse: '',
      publishingHouseSupplement: '',
      publishingHouseList: [],
      publishingHouseSupplements: []
    }
  },
  created() {
    // 初始化下拉列表
    getPublishingHouse().then(res => {
      this.publishingHouseList = res.queryResult.list
    })
    getSelectorList('publishingHouseSupplements').then(res => {
      this.publishingHouseSupplements = res.data
    })
  },
  methods: {
    onSearch() {
      console.log(this.publishingHouseSupplement)
      console.log(this.publishingHouse)
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
