
function isObj(o) {
  return typeof o === 'object' &&
    !Array.isArray(o) &&
    o !== null
}

export {
  isObj
}
