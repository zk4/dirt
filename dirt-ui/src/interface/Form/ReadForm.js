import React, {
  useState,
  useEffect,
} from 'react';
import {BetaSchemaForm, } from '@ant-design/pro-components';
import network from '../../network'
import Consts from "../../consts"
import customRender from '../../customRender'
import { isObj } from '../../util';

export default function ({title, cls, id}) {
  let [formData, setFormData] = useState([])
  const [show, setShow] = useState(false);

  useEffect(
    () => {
      show && (async () => {
        let headers = await network.getTableHeadersAsync(cls)
        let data = await network.getDataAsync(cls, id)
        headers = headers.map(d => {
          const {relation, idOfEntity: cls2, id, title,dataIndex} = d;
          if (relation === Consts.None) {
            d.initialValue = data[d.key];
          }
          // 有 relation，要做嵌套显示处理
          else if (relation === Consts.OneToOne || relation === Consts.ManyToOne) {
            d.initialValue = customRender.readForm(title, cls2, data[dataIndex]?.id);
          }
          else if (relation === Consts.OneToMany || relation === Consts.ManyToMany) {
            
            // d.initialValue = <>
            //   {
            //     record[dataIndex]?.map(c => {return customRender.table(title, cls2, id)})
            //   }
            // </>
            return <a>未实现</a>

          } else {
            // why here ????
            debugger
          }

          if(isObj(d.initialValue)) d.initialValue=0

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
    trigger={<a href="#/"> {id} </a>}
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
