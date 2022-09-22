import React, {useState, useRef} from 'react';
import {BetaSchemaForm} from '@ant-design/pro-components';
import Dirt from '../../Dirt'
import {Modal, Input} from 'antd';
import Consts from '../../consts'
import customRender from '../../customRender'
const {Search} = Input;
export default (props) => {
  const {name, triggerCompoent, columns, onFinish, onInit , readOnly} = props;
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

  let createColumns = columns.map(column => {
    const {key:columnKey,idOfEntity,relation} = column
    // 如果有 idOfEntity，则要弹框了选择
    if (idOfEntity) {
      column["renderFormItem"] = (item, {type, defaultRender, formItemProps, fieldProps, ...rest}, form) => {
        const vals = form.getFieldValue(columnKey)
        return <>
          <Search placeholder={column.placeholder} readOnly
            value={form.getFieldValue(columnKey)}
            onChange={e => {
              form.setFieldValue(columnKey, e.target.value)
            }} onSearch={e => showModal(columnKey)} enterButton />
          {
            vals && Array.isArray(vals)?vals.map(v=>customRender.readForm(v.id,idOfEntity,v.id))
            : customRender.readForm(vals?.id,idOfEntity,vals?.id)
          }
          <Modal readOnly destroyOnClose={true} width={"80%"} height={"60%"} title={column.title} open={isModalOpen[columnKey]} onOk={e => handleOk(columnKey)} onCancel={e => handleCancel(columnKey)}>
            <Dirt entityName={idOfEntity} 
               rowSelection={{
                 type: (relation === Consts.OneToMany ||  relation ===Consts.ManyToMany ) ? "checkbox" : "radio",
                 onChange: (selectedRowKeys, selectedRows, info) => {
                  if (relation === Consts.OneToMany  || relation === Consts.ManyToMany ) {
                    const ids = selectedRows.map(v => {return {id: v.id}})
                    form.setFieldValue(columnKey,ids)
                  } else {
                    const ids = selectedRows.map(v => {return {id: v.id}})
                    form.setFieldValue(columnKey,  ids[0])
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
