let a = {
  "location.lontitude": 1.2,
  "location.latitude": 2.2,
  "name": "zk"
}

let b = Object.entries(a).reduce((p, [k, v]) => {
  let ks = k.split(".")
  if (ks.length > 1)
    p[ks[0]] = {[ks[1]]: v, ...p[ks[0]]}
  else
    p[k] = v

  return p
}, {})
console.log(b)
