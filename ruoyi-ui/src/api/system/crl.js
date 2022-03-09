import request from '@/utils/request'

// 生成 crl
export function createCrl(data){
  return request({
    url: '/system/crl/create_crl',
    method: 'post',
    params: data
  })
}

// 修改间隔日期
export function setUpdateSpan(data){
  return request({
    url: '/system/crl/update_span',
    method: 'put',
    params: data
  })
}
