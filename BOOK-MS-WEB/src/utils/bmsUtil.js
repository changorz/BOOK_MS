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
