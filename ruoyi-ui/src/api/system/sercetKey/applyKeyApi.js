import request from '@/utils/request'

// 密钥对申请
export function applyKeyPair(keyData) {
  return request({
    url: '/kms/api/applyKeyPair',
    method: 'post',
    data: keyData
  })
}
