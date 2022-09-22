import React from 'react';
import ReadForm from './interface/Form/ReadForm'
import {isObj} from './util'
export default {
  table: (title, cls, id) => {
    if (isObj(id)) {
      console.assert(id.id,"必须有 id"+id)
      return <ReadForm key={cls + id.id} title={title} id={id.id} cls={cls} />
    }
    else {
      console.assert(id,"必须有 id"+id)
      return <ReadForm key={cls + id} title={title} id={id} cls={cls} />
    }
  },
  readForm: (title, cls, id) => {
    if (isObj(id))
    {
      console.assert(id.id,"必须有 id"+id)
      return <ReadForm key={cls + id.id} title={title} id={id.id} cls={cls} />
      }
    else
    {
      console.assert(id,"必须有 id"+id)
      return <ReadForm key={cls + id} title={title} id={id} cls={cls} />
      }
  }
}

