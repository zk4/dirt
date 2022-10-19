import React, {useState, useRef} from 'react';
import {BetaSchemaForm} from '@ant-design/pro-components';
import DirtTable from './dirtTable'
import {Modal, Input, Button} from 'antd';
import Consts from '../consts/consts'
import UIConsts from '../consts/uiConsts'
import customRender from './customRender'
import RichText from './richEditor'
import Cascader from './cascader'
import ImageUploader from './imageUploader'
import network from '../logic/network'
import {SearchOutlined} from '@ant-design/icons';

export default (props) => {
  const {name, triggerCompoent, columns, onFinish, onInit} = props;
  // debugger
  // 有可能有多个 modal 需要保持状态，使用{}
  const [isModalOpen, setIsModalOpen] = useState({});

  const showModal = (name) => {
    setIsModalOpen(s => {return {...s, [name]: true}});
  };
  const handleOk = (name) => {
    setIsModalOpen(s => {return {...s, [name]: false}});
  };
  const handleCancel = (name) => {
    setIsModalOpen(s => {return {...s, [name]: false}});
  };

  //TODO: 组织创建表单的联动js 逻辑
  columns[columns.findIndex(c =>c.key ==='columnName' )]= {
    valueType: 'dependency',
    name: ['tableName'],
    columns: ( {tableName} ) => {
      let type = tableName
      if (type === 'com.zk.experiment.Card') {
        return [
          {
            dataIndex: 'money',
            title: '优惠金额',
            width: 'm',
            valueType: 'money',
          },
        ];
      }
      return [];
    },
  }

  console.log(columns)
  let createColumns = columns.map(column => {
    const {key: columnKey, idOfEntity, relation} = column
    // 自定义创建 form
    if (column.valueType === UIConsts.cascader) {
      column["renderFormItem"] = (item, {type, defaultRender, formItemProps, fieldProps, ...rest}, form) => {
        // debugger
        return <Cascader.WriteView idOfEntity={idOfEntity} subTreeName={column.subTreeName} onValueSet={(valueArrays, optionArrays) => {
          const v = valueArrays?.slice(-1)?.[0]
          if (v) form.setFieldValue(columnKey, {id: v})
        }} />
      }
    }
    else if (column.valueType === UIConsts.richtext) {
      column["colProps"] = {xs: 24, md: 24}
      column["renderFormItem"] = (item, {type, defaultRender, formItemProps, fieldProps, ...rest}, form) => {
        return <RichText.WriteView key={item?.id} vaule={form.getFieldValue(columnKey)} onChange={value => {
          form.setFieldValue(columnKey, value)
        }} />
      }
    }
    else if (column.valueType === UIConsts.imageUploader) {
      column["colProps"] = {xs: 24, md: 24}
      column["renderFormItem"] = (item, {type, defaultRender, formItemProps, fieldProps, ...rest}, form) => {
        return <ImageUploader.WriteView key={item?.id} vaule={form.getFieldValue(columnKey)} onChange={value => {
          form.setFieldValue(columnKey, value)
        }} />
      }
    }
    // 如果有 idOfEntity，也就是要处理 relation，弹框去查 relation
    else if (idOfEntity) {
      column["renderFormItem"] = (item, {type, defaultRender, formItemProps, fieldProps, ...rest}, form) => {
        const vals = form.getFieldValue(columnKey)
        return <>
          {/*
          <IdHolder idObjs={form.getFieldValue(columnKey)} idOfEntity = {idOfEntity}/>
          */}
          <Input.Group compact>
            <Input
              allowClear
              style={{
                width: '75%',
              }}
              placeholder={column.placeholder}
              value={form.getFieldValue(columnKey)}
              onChange={
                e => {
                  // clear
                  if (e.target.value == "" || e.target.value == {}) {
                    if (relation === Consts.OneToMany || relation === Consts.ManyToMany) {
                      form.setFieldValue(columnKey, [])
                    } else if (relation === Consts.ManyToOne || relation === Consts.OneToOne) {
                      form.setFieldValue(columnKey, null)
                    } else {
                      form.setFieldValue(columnKey, e.target.value)
                    }
                  } else {
                    // create
                    form.setFieldValue(columnKey, e.target.value)
                  }
                }
              }
            />
            <Button type="primary" onClick={e => {
              showModal(columnKey)
            }}><SearchOutlined /></Button>
          </Input.Group>
          {
            vals && Array.isArray(vals) ? vals.map(v => customRender.readForm(v.id, idOfEntity, v.id))
              : customRender.readForm(vals?.id, idOfEntity, vals?.id)
          }
          <Modal destroyOnClose={true} width={"80%"} height={"60%"} title={column.title} open={isModalOpen[columnKey]} onOk={e => handleOk(columnKey)} onCancel={e => handleCancel(columnKey)}>
            <DirtTable entityName={idOfEntity}
              rowSelection={{
                getCheckboxProps: (record) => {
                  // let disabled = false
                  // if (Array.isArray(vals)) {
                  //   disabled = vals?.map(v => v.id).includes(record.id)
                  // } else {
                  //   disabled = record.id === vals?.id
                  // }
                  // return {
                  //   disabled,
                  // }
                },
                defaultSelectedRowKeys: vals ? Array.isArray(vals) ? vals.map(v => v.id) : [vals] : [],
                type: (relation === Consts.OneToMany || relation === Consts.ManyToMany) ? "checkbox" : "radio",
                onChange: (selectedRowKeys, selectedRows, info) => {
                  // JPA compatiable
                  if (relation === Consts.OneToMany || relation === Consts.ManyToMany) {
                    const ids = selectedRows.map(v => {return {id: v.id}})
                    form.setFieldValue(columnKey, ids)
                  } else if (relation === Consts.ManyToOne || relation === Consts.OneToOne) {
                    const ids = selectedRows.map(v => {return {id: v.id}})
                    form.setFieldValue(columnKey, ids[0])
                  }
                  // Pure Id,Maybe for mabatis compatiable
                  else {
                    const ids = selectedRows.map(v => {return v.id})
                    form.setFieldValue(columnKey, ids[0])
                  }

                }
              }} />
          </Modal>
        </>
      }
    }
    return column
  })

  const actionRef = useRef();
  return <BetaSchemaForm
    trigger={triggerCompoent}
    formRef={actionRef}
    onInit={onInit}
    title={name}
    layoutType='ModalForm'
    columns={createColumns}
    autoFocusFirstInput
    submitTimeout={4000}
    rowProps={{gutter: [16, 16]}}
    colProps={{span: 24, }}
    grid={true}
    onFinish={v => {onFinish(v);}}
  >
  </BetaSchemaForm>
}
