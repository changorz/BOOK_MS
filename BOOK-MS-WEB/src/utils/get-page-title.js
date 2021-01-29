import defaultSettings from '@/settings'

const title = defaultSettings.title || '教材填报管理系统'

export default function getPageTitle(pageTitle) {
  if (pageTitle === 'dashboard') {
    pageTitle = ''
  }
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
