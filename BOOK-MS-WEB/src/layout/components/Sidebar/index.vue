<template>
  <div :class="{'has-logo':showLogo}">
    <logo :collapse="isCollapse" v-if="showLogo" />
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
        :active-text-color="variables.menuActiveText"
        :background-color="variables.menuBg"
        :collapse="isCollapse"
        :collapse-transition="false"
        :default-active="activeMenu"
        :text-color="variables.menuText"
        :unique-opened="false"
        mode="vertical"
      >
        <sidebar-item :base-path="route.path" :item="route" :key="route.path" v-for="route in routes" />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import Logo from './Logo'
  import SidebarItem from './SidebarItem'
  import variables from '@/styles/variables.scss'

  export default {
  components: { SidebarItem, Logo },
  computed: {
    ...mapGetters([
      'sidebar'
    ]),
    routes() {
      // 修改标题
      // eslint-disable-next-line vue/no-side-effects-in-computed-properties
      this.$router.options.routes[2].children[0].meta.title = this.$store.getters.activeXqidValue
      return this.$router.options.routes
    },
    activeMenu() {
      const route = this.$route
      const { meta, path } = route
      // TODO 无需解决 替换标题
      meta.title = meta.title === 'dashboard' ? this.$store.getters.activeXqidValue : meta.title
      // 如果设置了路径，则侧边栏将突出显示您设置的路径
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    },
    showLogo() {
      return this.$store.state.settings.sidebarLogo
    },
    variables() {
      return variables
    },
    isCollapse() {
      return !this.sidebar.opened
    }
  }
}
</script>
