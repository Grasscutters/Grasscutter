const fs = require('node:fs')
const path = require('node:path')

function match(p, n) {
	for (let f of fs.readdirSync(p, { withFileTypes: true })) {
        let fp = path.join(f.path, f.name)
        if (f.isDirectory()) {
	        n = match(fp, n)
            continue
        }
        if (!f.isFile()) {
            continue
	    }
        let c = fs.readFileSync(fp, 'utf8')
        for (let i = n.length - 1; i > -1; i--) {
            if (c.includes(n[i])) {
                n.splice(i, 1)
            }
        }
	}
	return n
}

function move(p1, p2, p3) {
    console.info('-----------------------------')
    console.info('路径', '=>', p1, '=>', p2)
	let n = fs.readdirSync(p1).map(x => x.replace('.java', ''))
    console.info('数量', '=>', n.length)
	let p = match(p2, n)
    console.info('无效', '=>', p.length)
    console.info('移动', '=>', p1, '=>', p3)
    console.info('-----------------------------')
    for (let v of p) {
        v += '.java'
        fs.renameSync(path.join(p1, v), path.join(p3, v))
    }
}

function fix(p1, p3) {
    console.info('路径', '=>', p3, '=>', p1)
	let n = fs.readdirSync(p3).map(x => x.replace('.java', ''))
    console.info('数量', '=>', n.length)
    let f = (p, m) => {
        let o = []
        for (let v of m) {
            let c = fs.readFileSync(path.join(p, v + '.java'), 'utf8')
            for (let i = n.length - 1; i > -1; i--) {
                if (c.includes(n[i])) {
                    o.push(...n.splice(i, 1))
                }
            }
        }
        if (o.length) {
            o.push(...f(p3, o))
        }
        return o
    }
	let k = fs.readdirSync(p1).map(x => x.replace('.java', ''))
    let m = f(p1, k)
    console.info('有效', '=>', m.length)
    console.info('移动', '=>', p3, '=>', p1)
    console.info('-----------------------------')
    for (let v of m) {
        v += '.java'
        fs.renameSync(path.join(p3, v), path.join(p1, v))
    }
}

function main(p1, p2) {
	let p3 = 'proto_out'
	if (!fs.existsSync(p3)) {
	    fs.mkdirSync(p3)
	}
	move(p1, p2, p3)
    fix(p1, p3)
}

//by 2y8e9h22
main('../src/generated/main/java/emu/grasscutter/net/proto', '../src/main/java')
