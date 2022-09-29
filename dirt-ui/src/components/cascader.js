import {Cascader} from 'antd';
import React, {useState, useEffect} from 'react';

const SearchView = (props) => {
  const {request, onValueSet} = props;
  const [options, setOptions] = useState([]);

  useEffect(() => {
    (async () => {
      let d = await request(null)
      setOptions(d)
    })()
  }, [])

  const onChange = (valueArrays, optionArrays) => {
    onValueSet(valueArrays, optionArrays)
  };

  const loadData = async (selectedOptions) => {
    const targetOption = selectedOptions[selectedOptions.length - 1];
    targetOption.loading = true; // load options lazily
    let id = targetOption.value
    let d = await request(id)
    targetOption.children = d;
    targetOption.loading = false;
    setOptions([...options]);
  };

  return <Cascader options={options} loadData={loadData} onChange={onChange} changeOnSelect />;
};

// export default App;
export default {
  WriteView:null,
  TableRowView:null,
  SearchView,
  ReadView: null,
}

