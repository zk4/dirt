import {Cascader} from 'antd';
import React, {useState, useEffect} from 'react';
import network from '../network'

const dataAdapter = (ds) => {
  if (ds) {
    var obj = JSON.parse(JSON.stringify(ds)
      .replaceAll("\"name\":", "\"label\":")
      .replaceAll("\"id\":", "\"value\":")
    );
    return obj;
  }
  return ds;
}
const App = (props) => {
  const {optionLists, onValueSet} = props;
  const [options, setOptions] = useState(optionLists || []);

  useEffect(() => {
    (async () => {
      let data = await network.getDataAsync("com.zk.mall.entity.Address", 3);
      let d = dataAdapter(data.subAddress)
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
    let data = await network.getDataAsync("com.zk.mall.entity.Address", id);
    targetOption.children = dataAdapter(data.subAddress);
    targetOption.loading = false;
    setOptions([...options]);
  };

  return <Cascader options={options} loadData={loadData} onChange={onChange} changeOnSelect />;
};

export default App;
