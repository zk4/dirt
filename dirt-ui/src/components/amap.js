import React from 'react';
import {Map} from 'react-amap';

const SearchView = () => {
  const plugins = [
    'MapType',
    'Scale',
    'OverView',
    'ControlBar', // v1.1.0 新增
    {
      name: 'ToolBar',
      options: {
        visible: true,  // 不设置该属性默认就是 true
        onCreated(ins) {
          console.log(ins);
        },
      },
    }
  ]
  return <div style={{width: '100%', height: '400px'}}>
    <Map
      plugins={plugins}
    />
  </div>

}

export default {
  WriteView: SearchView,
  TableRowView: null,
  SearchView,
  ReadView: null,
}


