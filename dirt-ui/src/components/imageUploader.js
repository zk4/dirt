import React, {useState} from 'react';
import {Upload, Image} from 'antd';
import ImgCrop from 'antd-img-crop';

function WriteView(props) {
  let urls = (props?.value?.split(",").map(url =>{ return  { uid: '-1', name: 'image.png', status: 'done', url, } }))
  const [fileList, setFileList] = useState(urls?urls:[]);

  const onChange = ({fileList: newFileList}) => {
    const uploadedUrls = newFileList.filter(f => f?.response?.code === 0).map(f => f.response.data?.[0].url)
    props.onChange(uploadedUrls.join(","))

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
        action="http://127.0.0.1:8081/dirt/upload"
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
          return <Image width={50} src={url} />
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
