import React from 'react';
import ReadForm from './interface/Form/ReadForm'
export default {
  table: (title, cls, id) => {
    return <ReadForm key={cls+id} title={title} id={id} cls={cls} />
  },
  readForm:(title, cls, id)=>{
    return <ReadForm key={cls+id} title={title} id={id} cls={cls} />
  }
}

