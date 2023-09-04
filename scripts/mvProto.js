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
    console.info('路径', '=>', p1)
	let x = fs.readdirSync(p1).map(x => x.replace('.java', ''))
    console.info('数量', '=>', x.length)
    console.info('-----------------------------')
    console.info('分析中...')
    let f = (m, o) => {
        for (let v of m) {
            search(path.join(p1, v + '.java'), x, o)
        }
        if (o.length) {
            o.push(...f(o, []))
        }
        return o
    }
    let o = match(p2, x, [])
    if (x.length) {
        o.push(...f(o, []))
    }
//    fs.writeFileSync('o.txt', o.join('\n'), 'utf8')
//    fs.writeFileSync('x.txt', x.join('\n'), 'utf8')
    console.info('-----------------------------')
    console.info('有效', '=>', o.length)
    console.info('无效', '=>', x.length)
    console.info('-----------------------------')
    if (x.length) {
        console.info('移动', '=>', p1, '=>', p3)
        for (let v of x) {
            v += '.java'
            fs.renameSync(path.join(p1, v), path.join(p3, v))
        }
        console.info('-----------------------------')
    }
}

function main(p1, p2) {
	let p3 = '../src/proto_oth'
	if (!fs.existsSync(p3)) {
	    fs.mkdirSync(p3)
	}
	move(p1, p2, p3)
}

console.time('耗时')
//by 2y8e9h22
main('../src/generated/main/java/emu/grasscutter/net/proto', '../src/main/java/emu/grasscutter')
console.timeEnd('耗时')
console.info('-----------------------------')
