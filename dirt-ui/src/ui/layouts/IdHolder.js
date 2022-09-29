import {Select, Tag} from 'antd';
import React, {useState} from 'react';
import customRender from '../components/customRender'

const handleChange = (value) => {
  console.log(`selected ${value}`);
};

export default (props) => {
  const [idObjs, setIdObjs] = useState(props.idObjs || []);
  const preventDefault = (e) => {
    e.preventDefault();
    console.log('Clicked! But prevent default.');
  };
  return (<Select
    mode="multiple"
    style={{
      width: '100%',
    }}
    placeholder="select ids"
    defaultValue={idObjs.map(idObj => idObj?.id)}
    tagRender={(p) => {
      return <Tag closable onClose={preventDefault}>
        {
          customRender.readForm(p.value, props.idOfEntity, p.value)
        }
      </Tag>
    }}
    dropdownStyle={{display: 'none'}}
    onChange={handleChange}
    optionLabelProp="label"
  >
    {/* {
      idObjs.map(idObj => {
        return (<Option key={idObj?.id} value="china" label="China" >
          <div className="demo-option-label-item">
            <span role="img" aria-label="China">
              🇨🇳
            </span>
            China (中国)
          </div>
        </Option>
        )
      })
    } */}
  </Select >)
};

