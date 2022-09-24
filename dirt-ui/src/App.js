import React, {useState, useEffect} from "react";
import 'antd/dist/antd.css';
import axios from 'axios'
import Dirt from './Dirt'
import Tree from './interface/Tree'
import style from './App.module.css'

function App() {
  // let [path] = useState(window.location.pathname.substr(1) || 'com.zk.experiment.Member')
  let [tables, setTables] = useState({})
  const [path, setPath] = useState(window.location.pathname.substr(1));
  const [name, setName] = useState('');
  
  

  
  //   <div style={{display:'flex'}}>
  //   <div style={{width: '20%'}}>
  //     <Tree />
  //   </div>
  //   <div style={{width: '79%', height: '100vh', backgroundColor: 'red'}}>
  //
  //   </div>
  // </div>

  useEffect(() => {
    (async () => {
      let res = await axios.get(`http://127.0.0.1:8081/dirt/getTableMaps`)
      if (res.data.code === 0) {
        let tables = res.data.data;
        setTables(tables);
        setPath(Object.entries(tables)[0][1].className);
        setName(Object.entries(tables)[0][1].text);
      }
    })()
  }, [])
  return (
    <div >
      
      {
        Object.keys(tables).length > 0 && Object.entries(tables).map(([t, v]) => {
      return <a key={t} href="#!" onClick={e=>{
        setPath(v.className)
        setName(v.text)
        // window.location.pathname=v.className
      }}>{v.text} |</a>
        })
      }
      <h1>{name}</h1>
      < Dirt entityName={path} />
    </div>
  );
}

export default App;
