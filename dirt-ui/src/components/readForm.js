import React, {
  useState,
  useEffect,
} from 'react';
import {BetaSchemaForm, } from '@ant-design/pro-components';
import network from '../logic/network'
import Consts from "../consts/consts"
import customRender from './customRender'
import {isObj} from '../logic/util';

export default function ({title, cls, id, name}) {
  let [formData, setFormData] = useState([])
  // 如果没有 name，则使用 id
  if (name == null) name = id
  const [show, setShow] = useState(false);

  useEffect(
    () => {
      show && (async () => {
        let headers = await network.getTableHeadersAsync(cls)
        let data = await network.getDataAsync(cls, id)
        headers = headers.map(d => {
          const {relation, idOfEntity: cls2, id, title, dataIndex} = d;
          if (relation === Consts.None) {
            d.initialValue = data[d.key];
          }
          // 有 relation，要做嵌套显示处理
          else if (relation === Consts.OneToOne || relation === Consts.ManyToOne) {
            d.initialValue = customRender.readForm(title, cls2, data[dataIndex]?.id);
          }
          else if (relation === Consts.OneToMany || relation === Consts.ManyToMany) {
            d.initialValue = <>
              {
                data[dataIndex]?.map(c => {return customRender.readForm(title, cls2, id)})
              }
            </>
          } else {
            // why here ????
            debugger
          }

          return d;
        }).sort((a, b) => a.index - b.index);
        setFormData(headers)
      })()
    }
    , [show, cls, id])

  return <BetaSchemaForm
    title={title + ":" + id}
    readonly={true}
    onInit={e => setShow(true)}
    trigger={<a href="#/"> {name} </a>}
    layoutType='ModalForm'
    columns={formData}
    autoFocusFirstInput
    submitTimeout={4000}
    rowProps={{gutter: [16, 16]}}
    colProps={{span: 12, }}
    grid={true}
    onFinish={v => {setShow(false);}} >
  </BetaSchemaForm>
}
