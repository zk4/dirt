import React, {useState, useEffect,useSearchParams} from "react";
import 'antd/dist/antd.css';
import './App.css'
import axios from 'axios'
import Table from './components/dirtTable'
import qs from 'qs'
import Tree from './components/tree'

function App() {
  let [tables, setTables] = useState({})
  let entityName =qs.parse(window.location.search, { ignoreQueryPrefix: true }).entityName
  entityName=entityName 

  const [title, setTitle] = useState(null);
  const [viewType, setViewType] = useState('Table');
  
  
  
  
  useEffect(() => {
    (async () => {
      let data = await axios.get(`getTableMaps`)
      setTables(data.data);
      if(entityName!=null)
        setTitle(data.data[entityName].text)
    })()
  }, [])
  return (
     <div >
      {
        Object.keys(tables).length > 0 && Object.entries(tables).map(([t, v]) => {
          return <a key={t} href="#!" onClick={e => {
            // window.location.entityName = v.className;
          window.location.search = "entityName="+v.className;

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
