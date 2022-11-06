import { Select, Spin } from 'antd';
import debounce from 'lodash/debounce';
import React, { useMemo, useRef, useState,useCallback } from 'react';
function DebounceSelect({ fetchOptions, debounceTimeout = 100, ...props }) {
  const [fetching, setFetching] = useState(false);
  const [options, setOptions] = useState([]);
  const [loading, setLoading] = useState(false);
  
  const fetchRef = useRef(0);
  const debounceFetcher = useMemo(() => {
    setOptions([])
    const loadOptions = (value) => {
      fetchRef.current += 1;
      const fetchId = fetchRef.current;
      // setOptions([]);
      setFetching(true);
      setLoading(true)
      fetchOptions(value).then((newOptions) => {
        setLoading(false)
        if (fetchId !== fetchRef.current) {
          // for fetch callback order
          return;
        }
        setOptions(newOptions);
        setFetching(false);
      });
    };
    return debounce(loadOptions, debounceTimeout);
  }, [fetchOptions, debounceTimeout]);
  return (
    <Select
      labelInValue
      showArrow
      loading={loading}
      onFocus={debounceFetcher}
      filterOption={false}
      onSearch={debounceFetcher}
      notFoundContent={fetching ? <Spin size="small" /> : null}
      {...props}
      options={options}
    />
  );
}

// Usage of DebounceSelect

const SearchView = (props) => {
  const [value, setValue] = useState(props.value || []);
  let func= useCallback((e)=>{
  let a = e;
  // debugger
  if(props.size){
    if(props.size < e.length)
    {
      // message.error('超出可输大小');
      a = a.splice(a.length-props.size,props.size)
    }
  }
    setValue(a)
    props.onChange(a)
})

  return (
    <DebounceSelect
      mode="tags"
      value={value}
      placeholder="Select users"
      fetchOptions={props.fetchOptions}
      onChange={func}
      style={{
        width: '100%',
      }}
    />
  );
};
// export default App;
export default {
  WriteView: SearchView,
  TableRowView: null,
  SearchView,
  ReadView: null,
}

