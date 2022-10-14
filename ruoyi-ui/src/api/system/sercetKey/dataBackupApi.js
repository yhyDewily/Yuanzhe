import request from '@/utils/request'

// 执行数据备份
export function doBackupData(keyData) {
  return request({
    url: '/kms/api/database/doBackupData',
    method: 'get',
    params: keyData
  })
}

// 根据ip获取数据库集合
export function getDatabaseByIp(keyData) {
  return request({
    url: '/kms/api/database/getDatabaseByIp',
    method: 'get',
    params: keyData
  })
}

// 根据数据库获取数据表集合
export function getDatatableByBase(keyData) {
  return request({
    url: '/kms/api/database/getDatatableByBase',
    method: 'get',
    params: keyData
  })
}
export function getAllData(keyData) {
  return request({
    url: '/kms/api/database/getAllData',
    method: 'get',
    params: keyData
  })
}
// 获取所有备份记录
export function getDataBackupRecords(keyData) {
  return request({
    url: '/kms/api/database/getDataBackupRecords',
    method: 'get',
    params: keyData
  })
}

// 数据恢复操作
export function restoreData(keyData) {
  return request({
    url: '/kms/api/database/restoreData',
    headers:{
      "Content-Type": "multipart/form-data",
    },
    method: 'post',
    data: keyData,
  })
}

// 根据id数据恢复操作
export function restoreDataById(keyData) {
  return request({
    url: '/kms/api/database/restoreData',
    method: 'post',
    data: restoreDataById,
  })
}
