// 把对象赋值为空
export function clearObject(data) {
  for (var key in data) {
    data[key] = ''
  }
}

// 拷贝对象
export function copyBean(source, target) {
  for (var key in target) {
    if (source[key] !== undefined && source[key] !== null && source[key] !== '') {
      target[key] = source[key] + ''
    }
  }
}

// 文件下载
export function convertRes2Blob(res) {
  if (!res) {
    this.$message.error('下载文件失败')
    return false
  }
  const blob = new Blob([res.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8' })
  const downloadElement = document.createElement('a')
  const href = window.URL.createObjectURL(blob)
  const contentDisposition = res.headers['content-disposition']
  const patt = new RegExp(/filename\*=utf-8''(.*)/)
  const result = patt.exec(contentDisposition)
  const filename = decodeURI(result[1])
  downloadElement.style.display = 'none'
  downloadElement.href = href
  downloadElement.download = filename
  document.body.appendChild(downloadElement)
  downloadElement.click()
  document.body.removeChild(downloadElement)
  window.URL.revokeObjectURL(href)
}

