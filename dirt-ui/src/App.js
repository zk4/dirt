import React, {useState, useEffect} from "react";
import 'antd/dist/antd.css';
import axios from 'axios'
import Table from './components/dirtTable'
import Tree from './components/tree'

function App() {
  let [tables, setTables] = useState({})
  const pathname= window.location.pathname.substr(1);
  const [title, setTitle] = useState(null);
  const [viewType, setViewType] = useState('Table');
  
  
  
  
  useEffect(() => {
    (async () => {
      let data = await axios.get(`getTableMaps`)
      setTables(data.data);
      setTitle(data.data[pathname].text)
    })()
  }, [])
  return (
     <div >
      {
        Object.keys(tables).length > 0 && Object.entries(tables).map(([t, v]) => {
          return <a key={t} href="#!" onClick={e => {
            window.location.pathname = v.className;
          }}>{v.text} |</a>
        })
      }
      <h1>{title}</h1>
      <hr style={{marginBottom: '25px'}} />
      {viewType === 'Table' && (< Table entityName={pathname} />)}
      {viewType === 'Tree' && (< Table entityName={pathname} />)}
    </div>
  );
}

export default App;
