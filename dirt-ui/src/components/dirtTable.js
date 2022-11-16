import React, {useState, useEffect, useRef} from 'react';
import {PlusOutlined, } from '@ant-design/icons';
import {ProTable} from '@ant-design/pro-components';
import {Button, message, DatePicker, TimePicker, Space, Popconfirm} from 'antd';
import network from '../logic/network'
import ReadForm from './readForm'
import WriteForm from './writeForm'
import Consts from '../consts/consts'
import UIConsts from '../consts/uiConsts'
import customRender from './customRender'
import {isObj} from '../logic/util';
import Cascader from './cascader'
import RichText from './richEditor'
import ImageUploader from './imageUploader'
const {RangePicker} = DatePicker;


export default function DirtTable(props) {
  // excludeIds: default self id
  let {entityName, onSelected, rowSelection, readOnly} = props;
  rowSelection = rowSelection || {}
  let [columns, setColumns] = useState([]);
  let [columnKeyMap, setColumnKeyMap] = useState({})


  // 给 header 加点料，如加上新的 render，以支持在不同的场景下，渲染不同的组件，典型如：日期在创建时，是选择具体的值，但在搜索时，经常性是个范围。
  const redefineHeader = (headers, c) => {
    const {title, idOfEntity: cls, dataIndex, relation} = c

    // json object 与 json 有区别。服务器过来的是 json，json 不支持key为数字，只能放在值里，如果前端需要，先放值里，再转到 key
    if (c.valueEnum) {
      c.valueEnum = Object.entries(c.valueEnum).reduce((a, [k, c]) => {a[c.status] = c; return a;}, {})
    }

    // 自定义搜索栏
    if (c.searchType != null) {
      c.renderFormItem = (item, {type, defaultRender, formItemProps, fieldProps, ...rest}, form) => {
        if (c.searchType.valueType === UIConsts.cascader) {
          return <Cascader.SearchView idOfEntity={c.idOfEntity} subTreeName={c.subTreeName} onValueSet={(valueArrays, optionArrays) => {
            const v = valueArrays?.slice(-1)?.[0]
            if (v) form.setFieldValue(dataIndex, {id: v})
          }} />
        }
        if (c.searchType.valueType === 'dateTimeRange') {
          return <RangePicker showTime />
        } else if (c.searchType.valueType === 'dateRange') {
          return <RangePicker />
        } else if (c.searchType.valueType === 'timeRange') {
          return <TimePicker.RangePicker />
        }
      }
    }

    // 自定义 table row
    // 1.　relation 关系处理优先级更高
    if (relation === Consts.OneToOne || relation === Consts.ManyToOne) {
      c['render'] = (text, record, index) => {return customRender.table(title, cls, record[dataIndex]);}
      return c;
    }
    else if (relation === Consts.OneToMany || relation === Consts.ManyToMany) {
      c['render'] = (text, record, index) => {
        return record[dataIndex]?.map(d => {return customRender.table(title, cls, d)})
      }
      return c;
    }
    // 2. 然后是自定义 table row
    if (c.valueType === UIConsts.richtext) {
      c['render'] = (text, record, index) => {
        return <RichText.TableRowView value={record[dataIndex]} />
      }
      return c;
    }
    if (c.valueType === UIConsts.imageUploader) {
      c['render'] = (text, record, index) => {
        return <ImageUploader.TableRowView value={record[dataIndex]} />
      }
      return c;
    }

    // 生成 id 关连的实际 entity 详情跳转
    // 可以是多对一的关系，也可以是纯 id
    if (cls) {
      let key = c["key"]
      c['render'] = (text, record, index) => {
        const target = record[dataIndex]
        if (target && Array.isArray(target)) {
          return <> {target.map(r => <ReadForm key={r.id} title={title} id={r.id} cls={cls} />)} </>
        }
        else if (isObj(target)) {
          const rid = target?.id;
          return rid ? <ReadForm key={rid} title={title} id={rid} cls={cls} /> : '';
        }
        else {
          const rid = target;
          return rid ? <ReadForm key={rid} title={title} id={rid} cls={cls} /> : '';
        }
      }
    }

    // 处理 actions
    if (c["actions"]) {
      c['render'] = (text, record, index) => Object.entries(c["actions"]).map(([key, action]) => generateAction(headers, action, record, index));
    }


    return c;
  }

  useEffect(() => {
    (async () => {
      let headers = await network.getTableHeadersAsync(entityName)
      if (readOnly) {
        headers = headers.filter(c => c['title'] !== '操作')
      }
      headers = headers.map(c => {return redefineHeader(headers, c);});

      // 形成 key 为键的 headers，方便一些取值，比如：在 searchForm 里生成 filter 参数时
      let maps = headers.reduce((a, c) => {a[c.key] = c; return a;}, columnKeyMap);

      setColumnKeyMap(maps)

      setColumns(headers)

    })()
  }, [columnKeyMap, entityName]);


  const searchAsyncWrapper = async (params = {}, sort, filter) => {
    return network.searchAsync(entityName, columnKeyMap, params, sort, filter)
  }

  const generateCreateForm = () => {
    let submitTypes = columns
      .filter(c => c.submitType != null)
      .map(c => c.submitType)
      .map(c => {
        // TODO:  定义 ManyToOne OneToMany 的 render 筛选器
        return c;
      })
      .sort((a, b) => a.index - b.index);
    return <WriteForm entityName={entityName} name="创建" columns={submitTypes}
      onFinish={values =>
        network.createAsync(entityName, values, () => {actionRef.current.reload()})
      }
      triggerCompoent={<Button type="primary"> <PlusOutlined /> 创建 </Button>} />
  }

  const generateAction = (headers, action, record, index) => {
    let key = action.key
    let text = action.text
    if (key === 'edit' || key === 'detail') {

      let formData = headers
        //  只剔除  actions
        .filter(c => c["actions"] == null)
        //  取出 submitType
        .map(c => c.submitType)
        //   过滤掉 null
        .filter(c => c)
        .sort((a, b) => a.index - b.index);
      // 增加 id  显示
      formData.splice(0, 0, {
        "index": 10000,
        "width": "lg",
        "key": "id",
        "placeholder": "",
        "submitable": true,
        "valueType": "digit",
        "title": "id",
        "tooltip": "id",
        "readonly": true,
        "valueEnum": null,
        "colProps": {
          "md": 12
        },
        "initialValue": record.id
      });
      formData = formData.map(d => {
        d.initialValue = record[d.key];
        return d;
      })
        .sort((a, b) => a.index - b.index);

      // console.log(headers, action, record, index)
      let isDetailed = key === 'detail'

      // 只读模式
      // https://github.com/ant-design/pro-components/issues/3323

      return <WriteForm entityName={entityName} key={key} name={text} readOnly={isDetailed} columns={formData}
        onFinish={(postdata) =>
          network.updateAsync(entityName, postdata, () => {
            actionRef.current.reload();
          }
          )
        } triggerCompoent={<a> {text} </a>} />
    }
    else if (key === 'delete') {

      return <Popconfirm key={key} title="确定删除?" onConfirm={async () => {
        network.deleteByIdAsync(entityName, record.id, () => {
          actionRef.current.reload();
        })
      }}>
        {<span className='link delete'> {text} </span>}
      </Popconfirm>
    } else {
      // action
      const entries = Object.entries(action.argColumnsMap)

      // action 是否显示
      if (key in record && !record[key]) {
        return
      }
      if (entries.length == 0) {
        // 自定义 action 不带任何参数
        return <a key={key} onClick={() => {
          doAction(text, {
            entityName,
            actionName: key,
            id: record.id
          });
        }}>{text}</a>
      } else {
        //  自定义action 带参数，弹出 form 由用户输入
        let actionFormArgs = entries.reduce((a, [k, v]) => {a.push(v); return a;}, [])
        // console.log(actionFormArgs)
        return <WriteForm entityName={entityName} key={key} name={text} columns={action.argColumnsMap.args} onFinish={(postdata) => {
          doAction(text, {
            entityName,
            actionName: key,
            id: record.id,
            args: {args: postdata}
          })
        }
        } triggerCompoent={<a href="#!"> {text} </a>} />

      }
    }
  }

  const doAction = async (text, params) => {

    let res = await network.actionAsync(params, () => {
      message.success(`${text} 成功`);
      actionRef.current.reload();
    })
  }
  // 用来操作 ProTable
  // https://procomponents.ant.design/components/table/#actionref-%E6%89%8B%E5%8A%A8%E8%A7%A6%E5%8F%91
  const actionRef = useRef();


  return (columns && <ProTable
    key={entityName}
    scroll={{x: columns.length * 200}}
    columns={columns}
    actionRef={actionRef}
    cardBordered
    request={searchAsyncWrapper}
    editable={{type: 'multiple', }}
    columnsState={{
      persistenceKey: 'Dirt',
      persistenceType: 'localStorage',
      onChange(value) {},
    }}
    rowKey="id"
    search={{labelWidth: 'auto', }}
    options={{setting: {listsHeight: 400, }, }}
    form={{
      // 由于配置了 transform，提交的参与与定义的不同这里需要转化一下
      // 这个地方会改变 url，可以在一定程度上通过 url 保持 form 的状态
      syncToUrl: (values, type) => {return false;},
    }
    }
    pagination={
      {
        pageSize: 10,
        onChange: (page) => console.log(page),
      }
    }
    dateFormatter="string"
    headerTitle=""
    toolBarRender={() => [generateCreateForm()]}

    // 自定义选择项参考: https://ant.design/components/table-cn/#components-table-demo-row-selection-custom
    rowSelection={{...rowSelection}}

    tableAlertRender={({selectedRowKeys, selectedRows, onCleanSelected}) => (
      <Space size={24} >

        <span>
          已选 {selectedRowKeys.length} 项
          [{
            selectedRowKeys.map(id => customRender.readForm(id, entityName, id))
          }]
          <a href="#!" style={{marginInlineStart: 16}} onClick={() => network.deleteByIdsAsync(
            {
              entityName,
              ids: selectedRowKeys
            }, () => {
              onCleanSelected()
              actionRef.current.reload()
            }
          )}>远程删除</a>
          <a href="#!" style={{marginInlineStart: 16}} onClick={onCleanSelected}>导出数据</a>
          {onSelected && <a href="#!" style={{marginInlineStart: 16}} onClick={e => onSelected(selectedRows)}>选择</a>}
        </span>
      </ Space>
    )}
  />
  );
};
