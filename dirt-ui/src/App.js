import React, {useState, useEffect} from "react";
import 'antd/dist/antd.css';
import axios from 'axios'
import Table from './components/dirtTable'
import Tree from './components/tree'

function App() {
  // let [path] = useState(window.location.pathname.substr(1) || 'com.zk.experiment.Member')
  let [tables, setTables] = useState({})
  // const [path, setPath] = useState(window.location.pathname.substr(1));
  // const [name, setName] = useState('');
  const [view, setView] = useState(null);
  useEffect(() => {
    (async () => {
      let data = await axios.get(`getTableMaps`)
      setTables(data.data);
      setView(Object.entries(data.data)[0][1]);
    })()
  }, [])
  return (
    view && <div >

      {
        Object.keys(tables).length > 0 && Object.entries(tables).map(([t, v]) => {
          return <a key={t} href="#!" onClick={e => {
            setView(v)
            // setPath(v.className)
            // setName(v.text)
            // window.location.pathname=v.className
          }}>{v.text} |</a>
        })
      }
      <h1>{view.text}</h1>
      <hr style={{marginBottom: '25px'}} />
      {view.viewType === 'Table' && (< Table entityName={view.className} />)}
      {view.viewType === 'Tree' && (< Table entityName={view.className} />)}
    </div>
  );
}

export default App;
