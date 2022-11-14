
import ReactDOM from 'react-dom';
import React, {useEffect, useRef, Fragment, useState} from 'react';
import { Map, APILoader, ScaleControl, ToolBarControl, ControlBarControl, Geolocation} from '@uiw/react-amap';


// https://uiwjs.github.io/react-amap/#/
const Demo = () => {
  return (<div>
    <Map style={{height: 400}}>
      <ScaleControl offset={[16, 30]} position="LB" />
      <ToolBarControl offset={[16, 10]} position="RB" />
      <ControlBarControl offset={[16, 180]} position="RB" />
      <Geolocation
        maximumAge={100000}
        borderRadius="5px"
        position="RB"
        offset={[16, 80]}
        zoomToAccuracy={true}
        showCircle={true}
      />
    </Map>
  </div>)
};

const SearchView = () => (
  <APILoader version="2.0.5" akay="a7a90e05a37d3f6bf76d4a9032fc9129">
    <Demo />
  </APILoader>
)

export default {
  WriteView: SearchView,
  TableRowView: null,
  SearchView,
  ReadView: null,
}

