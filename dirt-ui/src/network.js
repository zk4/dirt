import axios from 'axios';
import {message} from 'antd';
import {SpringFilterQueryBuilder as SFQB} from './query_builder/index';

// respone拦截器

const getTableHeadersAsync = async (entityName) => {
  let res = await axios.get(`http://127.0.0.1:8081/dirt/getTableHeaders?entityName=${entityName}`)
  if (res.data.code === 0) {
    let headers = res.data.data;
    return headers;
  }
  return [];
}

const getDataAsync = async (entityName, id) => {
  try {
    let res = await axios.get(`http://127.0.0.1:8081/dirt/getData?entityName=${entityName}&id=${id}`)
    if (res.data.code === 0) {
      let headers = res.data.data;
      return headers;
    }
    else {
      const reasson = Array.isArray(res.data.data)?res.data.data.join(";"):res.data.data;
      message.error(res.data.msg + ":" + reasson);
    }
  } catch (e) {
    message.error(' 网络失败,请查看 console');
  }
  return [];
}

const deleteByIdAsync = async (entityName, id, success_cb) => {
  try {
    let res = await axios.post(`http://127.0.0.1:8081/dirt/deleteById`, {
      entityName,
      id
    })
    if (res.data.code === 0) {
      message.success('删除成功');
      success_cb();
    } else {
      const reasson = Array.isArray(res.data.data)?res.data.data.join(";"):res.data.data;
      message.error(res.data.msg + ":" + reasson);
    }
  } catch (e) {
    message.error(' 网络失败,请查看 console');
  }
}
const deleteByIdsAsync = async (postData, success_cb) => {
  try {
    let res = await axios.post('http://127.0.0.1:8081/dirt/deleteByIds', postData)
    if (res.data.code === 0) {
      if (res.data) {
        message.success('删除成功');
        success_cb();
      }
    } else {
      const reasson = Array.isArray(res.data.data)?res.data.data.join(";"):res.data.data;
      message.error(res.data.msg + ":" + reasson);
    }
  } catch (e) {
    message.error(' 网络失败,请查看 console');
  }
  return true;
}


const createAsync = async (entityName, values, success_cb) => {
  try {
    let res = await axios.post(`http://127.0.0.1:8081/dirt/create?entityName=${entityName}`, {
      ...values
    })
    if (res.data.code === 0) {
      if (res.data) {
        message.success('提交成功');
        success_cb();
      }
    } else {
      const reasson = Array.isArray(res.data.data)?res.data.data.join(";"):res.data.data;
      message.error(res.data.msg + ":" + reasson);
    }
  } catch (e) {
    message.error(' 网络失败,请查看 console');
  }
  return true;
}
const updateAsync = async (entityName, postData, success_cb) => {
  try {
    let res = await axios.post(`http://127.0.0.1:8081/dirt/update?entityName=${entityName}`, postData)
    if (res.data.code === 0) {
      if (res.data) {
        message.success('提交成功');
        success_cb();
      }
    } else {
      const reasson = Array.isArray(res.data.data)?res.data.data.join(";"):res.data.data;
      message.error(res.data.msg + ":" + reasson);
    }
  } catch (e) {
    message.error(' 网络失败,请查看 console');
  }
  return true;
}
const actionAsync = async (postData, success_cb) => {
  try {
    let res = await axios.post(`http://127.0.0.1:8081/dirt/action`, postData)
    if (res.data.code === 0) {
      if (res.data) {
        message.success('action 成功');
        success_cb();
      }
    } else {
      const reasson = Array.isArray(res.data.data)?res.data.data.join(";"):res.data.data;
      message.error(res.data.msg + ":" + reasson);
    }
  } catch (e) {
    message.error(' 网络失败,请查看 console');
  }
  return true;
}
// const key = 0;
// function addKey(data) {
//   let d = data.map(m => {
//     m['key'] =++key;
//     if (m.children && m.children.length > 0) {
//       m.children = addKey(m.children)
//     }
//     return m;
//   })
//   return d;
// }
const searchAsync = async (entityName, columnKeyMap, params = {}, sort, filter, success_cb) => {
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
        return SFQB.equal(key, `${value}`);
      }
    })

  // debugger
  let filterParams = ""
  let filterStr = SFQB.and(...filters).toString();
  if (filterStr !== "()") {
    filterParams = {filter: filterStr}
  }


  // 制作 JPA sort
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

  let url = `http://127.0.0.1:8081/dirt/getDatas?entityName=${entityName}${sortParams}`;

  // 要符合 ProTalbe 的数据格式
  let o = await axios.post(url, {...filterParams});
  return new Promise(
    (resolve, reject) => {
      if (o.data && o.data.code === 0) {
        // let d = addKey(o.data.data)
        // console.log("d", d)
        // o.data.data = d;
        resolve(o.data);
      } else {
        reject(o);
      }
    }
  );
}
export default {
  getTableHeadersAsync,
  getDataAsync,
  deleteByIdAsync,
  deleteByIdsAsync,
  createAsync,
  updateAsync,
  actionAsync,
  searchAsync
}



