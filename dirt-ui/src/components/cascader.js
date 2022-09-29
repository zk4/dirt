import {Cascader} from 'antd';
import React, {useState, useEffect} from 'react';
import network from '../logic/network'

const dataAdapter = (ds, childAlias) => {
  if (ds) {
    var obj = JSON.parse(JSON.stringify(ds)
      .replaceAll("\"name\":", "\"label\":")
      .replaceAll("\"id\":", "\"value\":")
      .replaceAll("\"" + childAlias + "\":", "\"children\":")
    );
    return obj;
  }
  return ds;
}
const SearchView = (props) => {
  const {idOfEntity, subTreeName,onValueSet} = props;
  const [options, setOptions] = useState([]);

  const request = async (id) => {
    let data = []
    if (id == null) {
      data = await network.searchFullAsync(idOfEntity, "(name : 'root')");
      data = data[0]
    } else {
      data = await network.getDataAsync(idOfEntity, id);
    }
    return data ? dataAdapter(data[subTreeName], subTreeName) : []
  }
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
  WriteView: SearchView,
  TableRowView: null,
  SearchView,
  ReadView: null,
}

