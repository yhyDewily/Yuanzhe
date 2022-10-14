import request from '@/utils/request'

// 查询所有在用密钥
export function getAllUseKeyPair(data) {
  return request({
    url: '/kms/api/getAllUseKeyPair',
    method: 'get',
    params:data
  })
}

// 条件查询所有在用密钥
export function getKeyPariByCondition(data) {
  return request({
    url: '/kms/api/getKeyPariByCondition',
    method: 'get',
    params:data
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
