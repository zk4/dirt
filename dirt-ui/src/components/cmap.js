import React , { Component } from 'react'
import { Modal , Input } from 'antd'
import styles from './index.scss'
import classname from 'classnames'
import { Map ,Marker,InfoWindow} from 'react-amap'
import marker from '../marker.png'
 
const mapKey = '42c177c66c03437400aa9560dad5451e'
 
class Address extends Component {
    constructor (props) {
        super(props)
        this.state = {
            signAddrList:{
                name:'',
                addr:'',
                longitude: 0,
                latitude: 0
            },
            geocoder:'',
            searchContent:'',
            isChose:false
        }
    }
 
    //改变数据通用方法(单层)
 
    changeData = (value, key) => {
        let { signAddrList } = this.state
        signAddrList[key] = value
        this.setState({
            signAddrList:signAddrList
        })
    }
 
    placeSearch = (e) => {
        this.setState({searchContent:e})
    }
 
    searchPlace = (e) => {
        console.log(1234,e)
    }
 
 
 
 
 
    componentDidMount() {
    
    }
 
    render() {
        let { changeModal , saveAddressDetail } = this.props
        let { signAddrList } = this.state
        const selectAddress = {
            created:(e) => {
                let auto
                let geocoder
                window.AMap.plugin('AMap.Autocomplete',() => {
                    auto = new window.AMap.Autocomplete({input:'tipinput'});
                })
 
                window.AMap.plugin(["AMap.Geocoder"],function(){
                    geocoder= new AMap.Geocoder({
                        radius:1000, //以已知坐标为中心点，radius为半径，返回范围内兴趣点和道路信息
                        extensions: "all"//返回地址描述以及附近兴趣点和道路信息，默认"base"
                    });
                });
 
                window.AMap.plugin('AMap.PlaceSearch',() => {
                    let place = new window.AMap.PlaceSearch({})
                    let _this = this
                    window.AMap.event.addListener(auto,"select",(e) => {
                        place.search(e.poi.name)
                        geocoder.getAddress(e.poi.location,function (status,result) {
                            if (status === 'complete'&&result.regeocode) {
                                let address = result.regeocode.formattedAddress;
                                let data = result.regeocode.addressComponent
                                let name = data.township +data.street + data.streetNumber
                                _this.changeData(address,'addr')
                                _this.changeData(name,'name')
                                _this.changeData(e.poi.location.lng,'longitude')
                                _this.changeData(e.poi.location.lat,'latitude')
                                _this.setState({isChose:true})
                            }
                        })
                    })
                })
            },
            click:(e) => {
                const _this = this
                var geocoder
                var infoWindow
                var lnglatXY=new AMap.LngLat(e.lnglat.lng,e.lnglat.lat);
                let content = '<div>定位中....</div>'
 
                window.AMap.plugin(["AMap.Geocoder"],function(){
                    geocoder= new AMap.Geocoder({
                        radius:1000, //以已知坐标为中心点，radius为半径，返回范围内兴趣点和道路信息
                        extensions: "all"//返回地址描述以及附近兴趣点和道路信息，默认"base"
                    });
                    geocoder.getAddress(e.lnglat,function (status,result) {
                        if (status === 'complete'&&result.regeocode) {
                            let address = result.regeocode.formattedAddress;
                            let data = result.regeocode.addressComponent
                            let name = data.township +data.street + data.streetNumber
                          
                            _this.changeData(address,'addr')
                            _this.changeData(name,'name')
                            _this.changeData(e.lnglat.lng,'longitude')
                            _this.changeData(e.lnglat.lat,'latitude')
                            _this.setState({isChose:true})
                        }
                    })
                });
                
            }
        }
        return (
            <div>
                <Modal visible={true}
                       title="办公地点"
                       centered={true}
                       onCancel={() => changeModal('addressStatus',0)}
                       onOk={() => saveAddressDetail(signAddrList)}
                       width={700}>
                    <div className={styles.serach}>
                        <input id="tipinput"
                               className={styles.searchContent}
                               onChange={(e) => this.placeSearch(e.target.value)}
                               onKeyDown={(e) => this.searchPlace(e)} />
                        <i className={classname(styles.serachIcon,"iconfont icon-weibiaoti106")}></i>
                    </div>
                    <div className={styles.mapContainer} id="content" >
                        {
                            this.state.isChose ? <Map amapkey={mapKey}
                                                      plugins={["ToolBar", 'Scale']}
                                                      events={selectAddress}
                                                      center={ [ signAddrList.longitude,signAddrList.latitude] }
                                                      zoom={15}>
                                <Marker position={[ signAddrList.longitude,signAddrList.latitude]}/>
                            </Map> : <Map amapkey={mapKey}
                                          plugins={["ToolBar", 'Scale']}
                                          events={selectAddress}
                                          zoom={15}>
                                <Marker position={[ signAddrList.longitude,signAddrList.latitude]}/>
                            </Map>
                        }
                    </div>
                    <div className="mar-t-20">详细地址:
                        <span className="cor-dark mar-l-10">{signAddrList.addr}</span>
                    </div>
                </Modal>
            </div>
        )
    }
}
 
export default {
  WriteView: Address,
  TableRowView: null,
  SearchView,
  ReadView: null,
}


