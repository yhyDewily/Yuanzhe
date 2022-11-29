import request from '@/utils/request'

// SM4_CBC 加密（padding）/ SM3 摘要
export function encryptWithSM4CBCPadding(data) {
  return request({
    url: '/SM/encryptCbcOrAbstarct',
    method: 'post',
    data: data
  })
}

// SM4_CBC 解密（padding）
export function decryptWithSM4CBCPadding(data) {
  return request({
    url: '/SM/decryptCbc',
    method: 'post',
    data: data
  })
}


