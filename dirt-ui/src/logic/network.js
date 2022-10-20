import axios from 'axios';
import {message} from 'antd';
import {SpringFilterQueryBuilder as SFQB} from '../lib/query_builder';
import {isObj} from './util'

// respone拦截器

axios.defaults.baseURL = 'http://127.0.0.1:8081/dirt/'

// 添加响应拦截器
axios.interceptors.response.use(function (res) {
  // 2xx 范围内的状态码都会触发该函数。
  // 对响应数据做点什么
  if (res.data.code === 0) {
    message.success('成功');
    return res.data;
  } else {
    const reasson = Array.isArray(res.data.data) ? res.data.data.join(";") : res.data.data;
    message.error(res.data.msg + ":" + reasson);
    return Promise.reject(reasson);
  }
}, function (error) {
    message.error(error);
  // 超出 2xx 范围的状态码都会触发该函数。
  // 对响应错误做点什么
  return Promise.reject(error);
});
const getEntitySchemaAsync = async (entityName) => {
  const ret = await axios.get(`getEntitySchema?entityName=${entityName}`)
  return ret.data;
}

const getDataAsync = async (entityName, id) => {
  const ret = await axios.get(`getData?entityName=${entityName}&id=${id}`)
  return ret.data;
}

const deleteByIdAsync = async (entityName, id, success_cb) => {
  let ret = await axios.post(`deleteById`, {entityName, id})
  success_cb && success_cb()
  return ret.data;
}
const deleteByIdsAsync = async (postData, success_cb) => {
  const ret = axios.post('deleteByIds', postData)
  success_cb && success_cb();
  return ret.data;
}


const createAsync = async (entityName, values, success_cb) => {
  const ret = await axios.post(`create?entityName=${entityName}`, {
    ...values
  })
  success_cb && success_cb();
  return ret.data;
}
const updateAsync = async (entityName, postData, success_cb) => {
  const ret = await axios.post(`update?entityName=${entityName}`, postData)
  success_cb && success_cb();
  return ret.data;
}
const actionAsync = async (postData, success_cb) => {
  const ret = await axios.post(`action`, postData)
  success_cb && success_cb();
  return ret.data;
}

const getDirtFieldTypeAsync = async (postData, success_cb) => {
  const ret = await axios.post(`getDirtFieldType`, postData)
  success_cb && success_cb();
  return ret.data;
}
const getFullDataslAsync = async (entityName, filter, success_cb) => {
  const ret = await axios.post(`getFullDatas?entityName=${entityName}`, {filter})
  success_cb && success_cb();
  return ret.data;
}
const getDatasAsync = async (entityName, columnKeyMap, params = {}, sort, filter, success_cb) => {
  // 映射 current 到 pageNumber
  params.pageNumber = params.current;
  delete params["current"];

  // 制作 JPA filters
  let paramsCpy = Object.assign({}, params)
  delete paramsCpy["pageNumber"];
  delete paramsCpy["pageSize"];


  const filters = Object.entries(params)
    .filter(([key, value]) => {
      return key !== 'pageNumber' && key !== 'pageSize' && value
    })

    // 处理 eFilterOperator 
    .map(([key, value]) => {
      if (Array.isArray(value)) {
        let column = columnKeyMap[key];
        if (column.searchType.valueType === "dateTimeRange"
          || column.searchType.valueType === "dateRange"
          || column.searchType.valueType === "timeRange"
        ) {
          return SFQB.and(SFQB.ge(key, value[0]), SFQB.le(key, value[1]));
        } else {
          throw new Error("未实现");
        }
      } else if (typeof (value) == 'string') {
        if (value.length > 0)
          return SFQB.like(key, `${value}`);
        return null;
      } else {
        if (isObj(value)) {
          // convert key {a:1,b:1} ==>  "key.a : 1 AND key.b : 2"
          return Object.entries(value).map(([k, v]) => SFQB.equal(key + '.' + k, v))
        }
        else {
          return SFQB.equal(key, `${value}`);
        }
      }
    })

  // 手动组装spring-filter 
  let filterParams = ""
  //  全部组装成 and 条件，在 table　里没有例外
  let filterStr = SFQB.and(...filters).toString();
  //  复合spring-filter 标准
  if (filterStr !== "()") {filterParams = {filter: filterStr}}


  // 制作 JPA page 里的sort
  let sortQuery = Object.entries(sort)
    .map(v => {
      let arrow = "desc"
      if (v[1] === "ascend")
        arrow = "asc"
      return v[0] + "," + arrow
    })
    .join('&')
  let sortParams = ""
  if (sortQuery.length)
    sortParams = `&sort=${sortQuery}`;

  // params.pageNumber-1 从 0 页开始
  const pageParam = `&size=${params.pageSize}&page=${params.pageNumber - 1}`

  let url = `getDatas?entityName=${entityName}${pageParam}${sortParams}`;

  // 要符合 ProTalbe 的数据格式
  let ret = await axios.post(url, {...filterParams});
  return new Promise(
    (resolve, reject) => {
      resolve({
        data: ret.data,
        total: ret.page.totalPages * ret.page.pageSize,
        success: true
      });

    }
  );
}
export default {
  getTableHeadersAsync: getEntitySchemaAsync,
  getDataAsync,
  deleteByIdAsync,
  deleteByIdsAsync,
  createAsync,
  updateAsync,
  actionAsync,
  getDirtFieldTypeAsync,
  searchFullAsync: getFullDataslAsync,
  searchAsync: getDatasAsync
}



