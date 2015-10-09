fname = ARGV[0]
$dir = ARGV[1]

$f = File.open(fname)

def startnewfile(n)
	name = "#{$dir}/out_%07d.txt"%n
	out = File.new(name, "w")
	while l = $f.gets
		l.chomp!
		break if l == ""
		out.puts(l)
	end
	out.close
end

i = 1
while l = $f.gets
	l.chomp!
	if l == "SESSION ID | Session Start | REQUISITION ID | REQUISITION STATUS | VM ID | Arrival Time | Size | Submission Time | Finish Time | Runtime"
		startnewfile(i)
		i = i + 1
	end
end
