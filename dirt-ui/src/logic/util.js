
function isObj(o) {
  return typeof o === 'object' &&
    !Array.isArray(o) &&
    o !== null
}

function dot(obj, desc) {
    var arr = desc.split(".");
    while(arr.length && (obj = obj[arr.shift()]));
    return obj;
}
export {
  isObj,
  dot
}
