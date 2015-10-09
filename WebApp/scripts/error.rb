vet = []
ARGV.each do |fname|
	f = File.open(fname)
	while l = gets
		l.chomp!
		vet << l.split(" ").last.to_f if l.include? "p-value"
	end
end

n = vet.size.to_f
nless = vet.select{|v| v < 0.05}.size.to_f
error = nless / n

puts "error = %0.2f%"%(error * 100)
