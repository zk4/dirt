import React from 'react';
import ReadForm from './readForm'
import {isObj} from '../logic/util'
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
      return <ReadForm key={cls + idObj} title={title} id={idObj} cls={cls}/>
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
      return <ReadForm key={cls + idObj} title={title} id={idObj} cls={cls} />
      }
  }
}

