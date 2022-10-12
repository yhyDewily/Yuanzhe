import request from '@/utils/request'

// 查询所有的备用密钥
export function getAllBackupKey(keyData) {
  return request({
    url: '/kms/api/backup/getAllBackupKey',
    method: 'get',
    params: keyData
  })
}
// 查询所有的备用密钥日志
export function getAllBackupLog(keyData) {
  return request({
    url: '/kms/api/log/getAllBackupLog',
    method: 'get',
    params: keyData
  })
}
