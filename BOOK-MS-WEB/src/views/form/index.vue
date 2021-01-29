<template>
  <div class="app-container">
    <el-form :model="form" label-width="120px" ref="form">
      <el-form-item label="Activity name">
        <el-input v-model="form.name"/>
      </el-form-item>
      <el-form-item label="Activity zone">
        <el-select placeholder="please select your zone" v-model="form.region">
          <el-option label="Zone one" value="shanghai"/>
          <el-option label="Zone two" value="beijing"/>
        </el-select>
      </el-form-item>
      <el-form-item label="Activity time">
        <el-col :span="11">
          <el-date-picker placeholder="Pick a date" style="width: 100%;" type="date" v-model="form.date1"/>
        </el-col>
        <el-col :span="2" class="line">-</el-col>
        <el-col :span="11">
          <el-time-picker placeholder="Pick a time" style="width: 100%;" type="fixed-time" v-model="form.date2"/>
        </el-col>
      </el-form-item>
      <el-form-item label="Instant delivery">
        <el-switch v-model="form.delivery"/>
      </el-form-item>
      <el-form-item label="Activity type">
        <el-checkbox-group v-model="form.type">
          <el-checkbox label="Online activities" name="type"/>
          <el-checkbox label="Promotion activities" name="type"/>
          <el-checkbox label="Offline activities" name="type"/>
          <el-checkbox label="Simple brand exposure" name="type"/>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="Resources">
        <el-radio-group v-model="form.resource">
          <el-radio label="Sponsor"/>
          <el-radio label="Venue"/>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="Activity form">
        <el-input type="textarea" v-model="form.desc"/>
      </el-form-item>
      <el-form-item>
        <el-button @click="onSubmit" type="primary">Create</el-button>
        <el-button @click="onCancel">Cancel</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        }
      }
    },
    methods: {
      onSubmit() {
        this.$message('submit!')
      },
      onCancel() {
        this.$message({
          message: 'cancel!',
          type: 'warning'
        })
      }
    }
  }
</script>

<style scoped>
  .line {
    text-align: center;
  }
</style>

