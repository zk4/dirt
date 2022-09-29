import React, {useState, useEffect} from 'react';
import {CarryOutOutlined, FormOutlined} from '@ant-design/icons';
import {Switch, Tree} from 'antd';
import DirtTable from './DirtTable';
import network from '../../logic/network';
const treeData = [
  {
    title: 'parent 1',
    key: '0-0',
    icon: <CarryOutOutlined />,
    children: [
      {
        title: 'parent 1-0',
        key: '0-0-0',
        icon: <CarryOutOutlined />,
        children: [
          {
            title: 'leaf',
            key: '0-0-0-0',
            icon: <CarryOutOutlined />,
          },
          {
            title: (
              <>
                <div>one line title</div>
              </>
            ),
            key: '0-0-0-1',
            icon: <CarryOutOutlined />,
          },
          {
            title: 'leaf',
            key: '0-0-0-2',
            icon: <CarryOutOutlined />,
          },
        ],
      },
      {
        title: 'parent 1-1',
        key: '0-0-1',
        icon: <CarryOutOutlined />,
        children: [
          {
            title: 'leaf',
            key: '0-0-1-0',
            icon: <CarryOutOutlined />,
          },
        ],
      },
      {
        title: 'parent 1-2',
        key: '0-0-2',
        icon: <CarryOutOutlined />,
        children: [
          {
            title: 'leaf',
            key: '0-0-2-0',
            icon: <CarryOutOutlined />,
          },
          {
            title: 'leaf',
            key: '0-0-2-1',
            icon: <CarryOutOutlined />,
            switcherIcon: <FormOutlined />,
          },
        ],
      },
    ],
  },
  {
    title: 'parent 2',
    key: '0-1',
    icon: <CarryOutOutlined />,
    children: [
      {
        title: 'parent 2-0',
        key: '0-1-0',
        icon: <CarryOutOutlined />,
        children: [
          {
            title: 'leaf',
            key: '0-1-0-0',
            icon: <CarryOutOutlined />,
          },
          {
            title: 'leaf',
            key: '0-1-0-1',
            icon: <CarryOutOutlined />,
          },
        ],
      },
    ],
  },
];

const App = (props) => {
  const {entityName} = props;
  const [showLine, setShowLine] = useState(true);
  const [showIcon, setShowIcon] = useState(false);
  const [showLeafIcon, setShowLeafIcon] = useState(true);
  const [data, setData] = useState(treeData);

  const dataAdapter = (ds) => {
    var obj = JSON.parse(JSON.stringify(ds)
      .replaceAll("\"name\":", "\"title\":")
      .replaceAll("\"subMenus\":", "\"children\":")
      .replaceAll("\"id\":", "\"key\":")

    );
    console.log(obj)
    return obj;
  }

  useEffect(() => {
    (async () => {
      const data = await network.getDataAsync('com.zk.mall.entity.Menu', 1)
      setData(dataAdapter(data).children)
    }
    )()
  }, [])
  const onSelect = (selectedKeys, info) => {
    console.log('selected', selectedKeys, info);
  };



  return (
    <>
      <div style={{display: 'flex'}}>
        <div style={{width: '20%'}}>
          <Tree
            showLine={showLine}
            showIcon={showIcon}
            defaultExpandedKeys={['0-0-0']}
            onSelect={onSelect}
            treeData={data}
          />
        </div>
        <div style={{width: '79%', height: '100vh'}}>
          < DirtTable entityName={entityName} />
        </div>
      </div>

    </>
  );
};

export default App;
