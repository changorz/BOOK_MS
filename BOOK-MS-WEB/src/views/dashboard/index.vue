<template>
  <div class="dashboard-container">
    <div class="dashboard-text">欢迎您: {{ name }}</div>
    <div>
      <el-row :gutter="40" class="panel-group">
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-people">
              <svg-icon icon-class="BookStore " class-name="card-panel-icon" />
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">
                书籍库
              </div>
              <count-to :start-val="0" :end-val="Number.parseInt(count.bookStoreCount)" :duration="2600" class="card-panel-num" />
            </div>
          </div>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-message">
              <svg-icon icon-class="CurriculumPlan" class-name="card-panel-icon" />
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">
                开课计划
              </div>
              <count-to :start-val="0" :end-val="Number.parseInt(count.curriculumPlanCount)" :duration="3000" class="card-panel-num" />
            </div>
          </div>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-money">
              <svg-icon icon-class="StudentInfo" class-name="card-panel-icon" />
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">
                学生信息
              </div>
              <count-to :start-val="0" :end-val="Number.parseInt(count.studentInfoCount)" :duration="3200" class="card-panel-num" />
            </div>
          </div>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-shopping">
              <svg-icon icon-class="BookTotal" class-name="card-panel-icon" />
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">
                填报
              </div>
              <count-to :start-val="0" :end-val="Number.parseInt(count.bookTotalCount_1) +Number.parseInt(count.bookTotalCount_2)" :duration="3600" class="card-panel-num" />
              <h2 style="display: inline-block">/{{Number.parseInt(count.bookTotalCount_0) + Number.parseInt(count.bookTotalCount_1) +Number.parseInt(count.bookTotalCount_2) }}</h2>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div>
      <Steps :current="1" status="wait">
        <Step content="创建当前学期" title="起始" />
        <Step content="导入excel1" title="导入Excel" />
        <Step content="导入excel2" title="导入Excel" />// 开课计划一定不能错
        <Step content="导入excel3" title="导入Excel" />
        <Step content="开启填报，让老师填写" title="填报1" />
        <Step content="填报停止，可以导出excel。。。" title="导出数据" />
        <Step content="缺书填报" title="填报2" />
        <Step content="打回通知" title="填报 3" />
        <Step content="存档" title="结束" />
      </Steps>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getCountAll } from '@/api/common'
import CountTo from 'vue-count-to'

export default {
  name: 'Dashboard',
  components: {
    CountTo
  },
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  data() {
    return {
      count: {
        bookStoreCount: 0,
        bookTotalCount_0: 0,
        bookTotalCount_1: 0,
        bookTotalCount_2: 0,
        curriculumPlanCount: 0,
        studentInfoCount: 0
      }
    }
  },
  created() {
    getCountAll().then(res => {
      this.count = res.data
    })
  }
}
</script>

<style lang="scss" scoped>
  .dashboard {
    &-container {
      margin: 30px;
    }

    &-text {
      font-size: 30px;
      line-height: 46px;
    }
  }
  .panel-group {
    margin-top: 18px;

    .card-panel-col {
      margin-bottom: 32px;
    }
    .card-panel {
      height: 108px;
      cursor: pointer;
      font-size: 12px;
      position: relative;
      overflow: hidden;
      color: #666;
      background: #fff;
      box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
      border-color: rgba(0, 0, 0, .05);

      &:hover {
        .card-panel-icon-wrapper {
          color: #fff;
        }

        .icon-people {
          background: #40c9c6;
        }

        .icon-message {
          background: #36a3f7;
        }

        .icon-money {
          background: #f4516c;
        }

        .icon-shopping {
          background: #34bfa3
        }
      }

      .icon-people {
        color: #40c9c6;
      }

      .icon-message {
        color: #36a3f7;
      }

      .icon-money {
        color: #f4516c;
      }

      .icon-shopping {
        color: #34bfa3
      }

      .card-panel-icon-wrapper {
        float: left;
        margin: 14px 0 0 14px;
        padding: 16px;
        transition: all 0.38s ease-out;
        border-radius: 6px;
      }

      .card-panel-icon {
        float: left;
        font-size: 48px;
      }

      .card-panel-description {
        float: right;
        font-weight: bold;
        margin: 26px;
        margin-left: 0px;

        .card-panel-text {
          line-height: 18px;
          color: rgba(0, 0, 0, 0.45);
          font-size: 16px;
          margin-bottom: 12px;
        }

        .card-panel-num {
          font-size: 20px;
        }
      }
    }
  }

  @media (max-width:550px) {
    .card-panel-description {
      display: none;
    }

    .card-panel-icon-wrapper {
      float: none !important;
      width: 100%;
      height: 100%;
      margin: 0 !important;

      .svg-icon {
        display: block;
        margin: 14px auto !important;
        float: none !important;
      }
    }
  }
</style>
