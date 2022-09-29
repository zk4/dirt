import React, {useState, useEffect} from "react";
import 'antd/dist/antd.css';
import axios from 'axios'
import DirtTable from './ui/layouts/DirtTable'
import Tree from './ui/layouts/Tree'

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
      {view.viewType === 'Table' && (< DirtTable entityName={view.className} />)}
      {view.viewType === 'Tree' && (< DirtTable entityName={view.className} />)}
    </div>
  );
}

export default App;
