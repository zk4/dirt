import {Select,SelectProps,message} from 'antd';
import React, {useState, useEffect,useCallback} from 'react';
import network from '../logic/network'

const options = [];
for (let i = 10; i < 36; i++) {
  options.push({
    value: i.toString(36) + i,
    label: i.toString(36) + i,
  });
}
const handleChange = (value) => {
  console.log(`selected ${value}`);
};
const SearchView = (props) => {

  const [value, setValue] = useState([]);
  
  let fuc= useCallback((e)=>{
    let a = e;
    // debugger
    if(props.size){
      if(props.size < e.length)
      {
        message.error('超出可输大小');
        a = a.splice(0,props.size)
      }
    }
      setValue(a)
      props.handleChange(a)
  })
  return ( <Select
    mode="tags"
    style={{
      width: '100%',
    }}
    value={value}
    allowClear={true}
    tokenSeparators={[',']}
    placeholder={props.placeholder}
    onChange={fuc}
    options={props.options}
  />)
};

// export default App;
export default {
  WriteView: SearchView,
  TableRowView: null,
  SearchView,
  ReadView: null,
}

