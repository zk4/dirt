import React, {useState} from 'react';
import {Upload, Image} from 'antd';
import ImgCrop from 'antd-img-crop';

function WriteView(props) {
  let accept = props.accept;
  let urls = (props?.value?.split(",").map(url =>{ return  { status: 'done', url, } }))
  const [fileList, setFileList] = useState(urls?urls:[]);

  const onChange = ({fileList: newFileList}) => {
    // const oldDoneUrls = newFileList.filter(f => f.status === 'done').map(f => f.url)
    const uploadedUrls = newFileList.filter(f => f?.response?.code === 0 || f.status === 'done').map(f => f.url || f.response.data?.[0].url)
    if(uploadedUrls.length == newFileList.length)
    {
      const urlsstr=  uploadedUrls.join(",")
      console.log(urlsstr)
      props.onChange(urlsstr)
    }

    setFileList(newFileList);
  };

  const onPreview = async (file) => {
    let src = file.url;

    if (!src) {
      src = await new Promise((resolve) => {
        const reader = new FileReader();
        reader.readAsDataURL(file.originFileObj);

        reader.onload = () => resolve(reader.result);
      });
    }

    const image = new Image();
    image.src = src;
    const imgWindow = window.open(src);
    const a = imgWindow?.document.write(image.outerHTML);
  };

  return (
    <ImgCrop rotate readOnly>
      <Upload
        accept = {accept}
        action="/dirt/upload"
        listType="picture-card"
        fileList={fileList}
        onChange={onChange}
        onPreview={onPreview}
      >
        {fileList.length < 5 && '+ Upload'}
      </Upload>
    </ImgCrop>
  );
};


const contentStyle = {
  height: '160px',
  color: '#fff',
  lineHeight: '160px',
  textAlign: 'center',
  background: '#364d79',
};

const TableRowView = ({value}) => {
  return value !== null ?
    <Image.PreviewGroup>
      {
        value.split(",").map((url) => {
          return <Image style={{'paddingRight':'2px'}} width={50} src={url} key={url} />
        })
      }
    </Image.PreviewGroup>
    : null

};
export default {
  WriteView,
  TableRowView,
  SearchView: null,
  ReadView: null,
}
