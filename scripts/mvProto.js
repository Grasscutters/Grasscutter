const fs = require('node:fs')
const path = require('node:path')

function search(p, n, o) {
    let c = fs.readFileSync(p, 'utf8')
    for (let i = n.length - 1; i > -1; i--) {
        if (c.includes(n[i])) {
            o.push(...n.splice(i, 1))
        }
    }
}

function match(p, n, o) {
	for (let f of fs.readdirSync(p, { withFileTypes: true })) {
        let fp = path.join(f.path, f.name)
        if (f.isDirectory()) {
	        match(fp, n, o)
            continue
        }
        if (!f.isFile()) {
            continue
	    }
	    search(fp, n, o)
	}
	return o
}

function move(p1, p2, p3) {
    console.info('-----------------------------')
    console.info('路径', '=>', p1, '=>', p2)
	let n = fs.readdirSync(p1).map(x => x.replace('.java', ''))
    console.info('数量', '=>', n.length)
    let f = (p, m, o) => {
        for (let v of m) {
            search(path.join(p, v + '.java'), n, o)
        }
        if (o.length) {
            o.push(...f(p1, o, []))
        }
        return o
    }
    let p = match(p2, n, [])
    p.push(...f(p1, p, []))
    console.info('有效', '=>', p.length)
    console.info('无效', '=>', n.length)
    console.info('移动', '=>', p1, '=>', p3)
    console.info('-----------------------------')
    for (let v of n) {
        v += '.java'
        fs.renameSync(path.join(p1, v), path.join(p3, v))
    }
}

function main(p1, p2) {
	let p3 = 'proto_out'
	if (!fs.existsSync(p3)) {
	    fs.mkdirSync(p3)
	}
	move(p1, p2, p3)
}

//by 2y8e9h22
main('../src/generated/main/java/emu/grasscutter/net/proto', '../src/main/java')
