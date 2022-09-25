import {Cascader} from 'antd';
import React, {useState, useEffect} from 'react';
import network from '../network'

const App = (props) => {
  const {request, onValueSet} = props;
  const [options, setOptions] = useState([]);

  useEffect(() => {
    (async () => {
      let d = await request(null)
      setOptions(d)
    })()
  }, [])

  const onChange = (value, selectedOptions) => {
    console.log(value, selectedOptions);
    onValueSet(selectedOptions)
  };

  const loadData = async (selectedOptions) => {
    const targetOption = selectedOptions[selectedOptions.length - 1];
    targetOption.loading = true; // load options lazily

    let id = targetOption.value
    // let data = await network.getDataAsync("com.zk.mall.entity.Address", id);
    let d = await request(id)
    targetOption.children = d;
    targetOption.loading = false;
    setOptions([...options]);
  };

  return <Cascader options={options} loadData={loadData} onChange={onChange} changeOnSelect />;
};

export default App;
