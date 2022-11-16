import React, {useState, useEffect, useSearchParams} from "react";
import 'antd/dist/antd.css';
import './App.css'
import axios from 'axios'
import Table from './components/dirtTable'
import qs from 'qs'
import Tree from './components/tree'
import {message} from "antd";

function App() {
  let [tables, setTables] = useState({})
  let entityName = qs.parse(window.location.search, {ignoreQueryPrefix: true}).entityName

  const [title, setTitle] = useState(null);
  const [viewType, setViewType] = useState('Table');




  useEffect(() => {
    (async () => {
      try {
        let data = await axios.get(`getTableMaps`)
        setTables(data.data);
        let e = data?.data?.[entityName]?.text
        if (!e) {
          e = Object.entries(data.data)?.[0]?.[1]?.text;
          window.location.search = "entityName=" + Object.entries(data.data)?.[0]?.[0];
        }

        setTitle(e)
      } catch (e) {
        message.error("请求错误,请查看链接是否正确")
      }
    })()
  }, [])
  return (
    <div >
      {
        Object.keys(tables).length > 0 && Object.entries(tables).map(([t, v]) => {
          return <a key={t} href="#!" onClick={e => {
            window.location.search = "entityName=" + v.className;

          }}>{v.text} |</a>
        })
      }
      <h1>{title}</h1>
      <hr style={{marginBottom: '25px'}} />
      {viewType === 'Table' && (< Table entityName={entityName} />)}
      {viewType === 'Tree' && (< Table entityName={entityName} />)}
    </div>
  );
}

export default App;
