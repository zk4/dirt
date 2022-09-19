// import React, { useState, useEffect } from 'react'
// import { createForm } from '@formily/core'
// import { createSchemaField } from '@formily/react'
// import {
//   Form,
//   FormItem,
//   FormLayout,
//   Input,
//   Select,
//   Cascader,
//   DatePicker,
//   Submit,
//   FormGrid,
//   Upload,
//   ArrayItems,
//   Editable,
//   FormButtonGroup,
// } from '@formily/antd'
// import { action } from '@formily/reactive'
// import { Card, Button, Spin } from 'antd'
// import { UploadOutlined } from '@ant-design/icons'
//
// const form = createForm({
//   validateFirst: true,
// })
//
// const IDUpload = (props) => {
//   return (
//     <Upload
//       {...props}
//       action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
//       headers={{
//         authorization: 'authorization-text',
//       }}
//     >
//       <Button icon={<UploadOutlined />}>Upload a copy</Button>
//     </Upload>
//   )
// }
//
// const SchemaField = createSchemaField({
//   components: {
//     FormItem,
//     FormGrid,
//     FormLayout,
//     Input,
//     DatePicker,
//     Cascader,
//     Select,
//     IDUpload,
//     ArrayItems,
//     Editable,
//   },
//   scope: {
//     fetchAddress: (field) => {
//       const transform = (data = {}) => {
//         return Object.entries(data).reduce((buf, [key, value]) => {
//           if (typeof value === 'string')
//             return buf.concat({
//               label: value,
//               value: key,
//             })
//           const { name, code, cities, districts } = value
//           const _cities = transform(cities)
//           const _districts = transform(districts)
//           return buf.concat({
//             label: name,
//             value: code,
//             children: _cities.length
//               ? _cities
//               : _districts.length
//               ? _districts
//               : undefined,
//           })
//         }, [])
//       }
//
//       field.loading = true
//       fetch('//unpkg.com/china-location/dist/location.json')
//         .then((res) => res.json())
//         .then(
//           action.bound((data) => {
//             field.dataSource = transform(data)
//             field.loading = false
//           })
//         )
//     },
//   },
// })
//
// const schema = {
//   type: 'object',
//   properties: {
//     username: {
//       type: 'string',
//       title: 'Username',
//       required: true,
//       'x-decorator': 'FormItem',
//       'x-component': 'Input',
//     },
//     name: {
//       type: 'void',
//       title: 'Name',
//       'x-decorator': 'FormItem',
//       'x-decorator-props': {
//         asterisk: true,
//         feedbackLayout: 'none',
//       },
//       'x-component': 'FormGrid',
//       properties: {
//         firstName: {
//           type: 'string',
//           required: true,
//           'x-decorator': 'FormItem',
//           'x-component': 'Input',
//           'x-component-props': {
//             placeholder: 'firstName',
//           },
//         },
//         lastName: {
//           type: 'string',
//           required: true,
//           'x-decorator': 'FormItem',
//           'x-component': 'Input',
//           'x-component-props': {
//             placeholder: 'lastname',
//           },
//         },
//       },
//     },
//     email: {
//       type: 'string',
//       title: 'Email',
//       required: true,
//       'x-decorator': 'FormItem',
//       'x-component': 'Input',
//       'x-validator': 'email',
//     },
//     gender: {
//       type: 'string',
//       title: 'Gender',
//       enum: [
//         {
//           label: 'male',
//           value: 1,
//         },
//         {
//           label: 'female',
//           value: 2,
//         },
//         {
//           label: 'third gender',
//           value: 3,
//         },
//       ],
//       'x-decorator': 'FormItem',
//       'x-component': 'Select',
//     },
//     birthday: {
//       type: 'string',
//       required: true,
//       title: 'Birthday',
//       'x-decorator': 'FormItem',
//       'x-component': 'DatePicker',
//     },
//     address: {
//       type: 'string',
//       required: true,
//       title: 'Address',
//       'x-decorator': 'FormItem',
//       'x-component': 'Cascader',
//       'x-reactions': '{{fetchAddress}}',
//     },
//     idCard: {
//       type: 'string',
      required: true,
      title: 'ID',
      'x-decorator': 'FormItem',
      'x-component': 'IDUpload',
    },
    contacts: {
      type: 'array',
      required: true,
      title: 'Contacts',
      'x-decorator': 'FormItem',
      'x-component': 'ArrayItems',
      items: {
        type: 'object',
        'x-component': 'ArrayItems.Item',
        properties: {
          sort: {
            type: 'void',
            'x-decorator': 'FormItem',
            'x-component': 'ArrayItems.SortHandle',
          },
          popover: {
            type: 'void',
            title: 'Contact Informations',
            'x-decorator': 'Editable.Popover',
            'x-component': 'FormLayout',
            'x-component-props': {
              layout: 'vertical',
            },
            'x-reactions': [
              {
                fulfill: {
                  schema: {
                    title: '{{$self.query(".name").value() }}',
                  },
                },
              },
            ],
            properties: {
              name: {
                type: 'string',
                title: 'Name',
                required: true,
                'x-decorator': 'FormItem',
                'x-component': 'Input',
                'x-component-props': {
                  style: {
                    width: 300,
                  },
                },
              },
              email: {
                type: 'string',
                title: 'Email',
                'x-decorator': 'FormItem',
                'x-component': 'Input',
                'x-validator': [{ required: true }, 'email'],
                'x-component-props': {
                  style: {
                    width: 300,
                  },
                },
              },
              phone: {
                type: 'string',
                title: 'Phone Number',
                'x-decorator': 'FormItem',
                'x-component': 'Input',
                'x-validator': [{ required: true }, 'phone'],
                'x-component-props': {
                  style: {
                    width: 300,
                  },
                },
              },
            },
          },
          remove: {
            type: 'void',
            'x-decorator': 'FormItem',
            'x-component': 'ArrayItems.Remove',
          },
        },
      },
      properties: {
        addition: {
          type: 'void',
          title: 'Add Contact',
          'x-component': 'ArrayItems.Addition',
        },
      },
    },
  },
}

export default () => {
  const [loading, setLoading] = useState(true)
  useEffect(() => {
    setTimeout(() => {
      form.setInitialValues({
        username: 'Aston Martin',
        firstName: 'Aston',
        lastName: 'Martin',
        email: 'aston_martin@aston.com',
        gender: 1,
        birthday: '1836-01-03',
        address: ['110000', '110000', '110101'],
        idCard: [
          {
            name: 'this is image',
            thumbUrl:
              'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
            uid: 'rc-upload-1615825692847-2',
            url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
          },
        ],
        contacts: [
          {
            name: 'Zhang San',
            phone: '13245633378',
            email: 'zhangsan@gmail.com',
          },
          { name: 'Li Si', phone: '16873452678', email: 'lisi@gmail.com' },
        ],
      })
      setLoading(false)
    }, 2000)
  }, [])
  return (
    <div
      style={{
        display: 'flex',
        justifyContent: 'center',
        background: '#eee',
        padding: '40px 0',
      }}
    >
      <Card title="Edit User" style={{ width: 620 }}>
        <Spin spinning={loading}>
          <Form
            form={form}
            labelCol={5}
            wrapperCol={16}
            onAutoSubmit={console.log}
          >
            <SchemaField schema={schema} />
            <FormButtonGroup.FormItem>
              <Submit block size="large">
                Submit
              </Submit>
            </FormButtonGroup.FormItem>
          </Form>
        </Spin>
      </Card>
    </div>
  )
}
