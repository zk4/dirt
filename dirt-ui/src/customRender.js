import React from 'react';
import ReadForm from './interface/Form/ReadForm'
import {isObj} from './util'
export default {
  table: (title, cls, idObj) => {
    if(idObj==null) 
    {
      return
    }
    if (isObj(idObj)) {
      console.assert(idObj.id,"必须有 id"+idObj)
      return <ReadForm key={cls + idObj.id} title={title} id={idObj.id} cls={cls} name={idObj?.name} />
    }
    else {
      console.assert(idObj,"必须有 id"+idObj)
      return <ReadForm key={cls + idObj} title={title} id={idObj} cls={cls} name={idObj?.name}/>
    }
  },
  readForm: (title, cls, idObj) => {
    if(idObj==null) 
    {
      return
    }
    if (isObj(idObj))
    {
      console.assert(idObj.id,"必须有 id"+idObj)
      return <ReadForm key={cls + idObj.id} title={title} id={idObj.id} cls={cls} name={idObj?.name} />
      }
    else
    {
      console.assert(idObj,"必须有 id"+idObj)
      return <ReadForm key={cls + idObj} title={title} id={idObj} cls={cls}  name={idObj?.name} />
      }
  }
}

