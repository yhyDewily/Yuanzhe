import request from '@/utils/request'

// 获取备用密钥对集合
export function getKeyPairList(keyData) {
  return request({
    url: '/kms/api/backup/getKeyPairList',
    method: 'post',
    data: keyData
  })
}
