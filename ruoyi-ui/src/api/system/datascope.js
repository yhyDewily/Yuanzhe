import request from '@/utils/request'

// 查询系统角色所拥有下属角色创建权限列表
export function listDatascope(query) {
  return request({
    url: '/system/datascope/list',
    method: 'get',
    params: query
  })
}

// 查询系统角色所拥有下属角色创建权限详细
export function getDatascope(roleId) {
  return request({
    url: '/system/datascope/' + roleId,
    method: 'get'
  })
}

// 新增系统角色所拥有下属角色创建权限
export function addDatascope(data) {
  return request({
    url: '/system/datascope',
    method: 'post',
    data: data
  })
}

// 修改系统角色所拥有下属角色创建权限
export function updateDatascope(data) {
  return request({
    url: '/system/datascope',
    method: 'put',
    data: data
  })
}

// 删除系统角色所拥有下属角色创建权限
export function delDatascope(roleId) {
  return request({
    url: '/system/datascope/' + roleId,
    method: 'delete'
  })
}
