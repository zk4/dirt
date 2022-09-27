import React, {useState, useCallback} from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';


//TODO: 回车才设值，测试先这样吧 
function WriteView(props) {
  const [value, setValue] = useState(props.value || '');
  let innerChange = useCallback((value) => {
    setValue(value);
    props.onChange && props.onChange(value);
  }, [props.onChange])
  return <ReactQuill onBlur={e => {
  }} theme="snow" value={value} onChange={v => innerChange(v)} />;
}

function TableRowView(props) {
  return <div dangerouslySetInnerHTML={{__html: props.value}} />;
}
export default {
  WriteView,
  TableRowView,
  SearchView: null,
  ReadView: null,
}
