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
        <span class="expand-key">总数量: </span>
        <span class="expand-value">
          <Tag color="volcano">{{count}}</Tag>
        </span>
        <span class="expand-key">总码洋: </span>
        <span class="expand-value">
          <Tag color="volcano">{{countP}}</Tag>元
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
import { getPublishingHouse, getPublishingHouseOrder } from '@/api/table'
import { getSelectorList } from '@/api/common'
export default {
  data() {
    return {
      count: '0',
      countP: '0',
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
          width: 220,
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
          width: 300,
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
      publishingHouse: '清华大学出版社',
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
      if (this.publishingHouse === '') {
        this.$Message.warning('出版社不能为空！')
        return
      }
      getPublishingHouseOrder(this.publishingHouse, this.publishingHouseSupplement).then(res => {
        if (res.data.phOrderlist.length === 0) {
          this.$Message.warning('没有查询到数据！')
        }
        this.listTable = res.data.phOrderlist
        this.count = res.data.phTotal
        this.countP = res.data.phTotalPricing
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
