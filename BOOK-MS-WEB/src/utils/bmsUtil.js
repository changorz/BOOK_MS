// 把对象赋值为空
export function clearObject(data) {
  for (var key in data) {
    data[key] = ''
  }
}
