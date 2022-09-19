import React, {useState, useEffect} from "react";
import 'antd/dist/antd.css'; 
import axios from 'axios'
import Dirt from './Dirt'

function App() {
  let [path] =useState(window.location.pathname.substr(1) || 'com.zk.member.entity.ReserveProduct')
  let [tables, setTables] = useState([])

  useEffect(() => {
    (async () => {
      let res = await axios.get(`http://127.0.0.1:8081/dirt/getTablesNames`)
      if (res.data.code === 0) {
        let tables = res.data.data;
        setTables(tables);
      }
    })()
  }, [])
  return (
    <div >
      {
        tables.length > 0 && tables.map(t => {
          return <a key={t} href={'/' + t}>{t.split(".").pop()} |</a>
        })
      }
      <Dirt entityName={path} />
    </div>
  );
}

export default App;
