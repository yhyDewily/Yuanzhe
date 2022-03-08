import request from '@/utils/request'

export function fuck() {
  return request({
    url: '/ocsp/fuck',
    method: 'get'
  })
}

// 生成证书列表
export function genCer() {
  return request({
    url: '/system/cer/gen_cer',
    method: 'get'
  })
}

// 查询证书管理列表
export function listCer(query) {
  return request({
    url: '/system/cer/list',
    method: 'get',
    params: query
  })
}

// 查询证书管理详细
export function getCer(version) {
  return request({
    url: '/system/cer/' + version,
    method: 'get'
  })
}

// 新增证书管理
export function addCer(data) {
  return request({
    url: '/system/cer',
    method: 'post',
    data: data
  })
}

// 修改证书管理
export function updateCer(data) {
  return request({
    url: '/system/cer',
    method: 'put',
    data: data
  })
}

// 删除证书管理
export function delCer(version) {
  return request({
    url: '/system/cer/' + version,
    method: 'delete'
  })
}
