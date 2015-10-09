class Array
	def sum
		self.inject{|sum,x| sum + x }
	end
end

class Cloudlet
	attr_accessor :vmid, :start, :finish
end

cloudlets = []
vms = {}

while l = gets 
	c = Cloudlet.new
	c.vmid = l.split("|")[4].to_i
	c.start = l.split("|")[7].to_f 
	c.finish = l.split("|")[8].to_f
	vms[c.vmid] = [] if vms[c.vmid] == nil
	cloudlets << c
	vms[c.vmid] << c
end

lastsec = cloudlets.collect{|c| c.finish}.max.floor
vms.each { |k, v| v.sort_by{|c| c.start} }

print "# time/vm"
vms.each do |vmid, cs|
	print " %3d"%vmid
end
puts ""

0.upto(lastsec) do |sec|
	print "%9d"%sec
	sec = sec.to_f
	vms.each do |vmid, cs|
#		cs.each do |c|
#			$stderr.puts "#{sec}; #{c.start}; #{c.finish}; #{c.start <= sec and c.finish >= sec}; #{c.start >= sec and ( c.start <= ( sec + 1 ) )}"
#		end
		selected = cs.select do |c|
			( ( c.start <= sec ) and ( c.finish >= sec ) ) or
			( ( c.start >= sec ) and ( c.start <= ( sec + 1 ) ) )
		end
#		$stderr.puts "selected.size = #{selected.size}"
		selected = selected.collect{|c| c.clone}
		range = Array.new(10000, 0)
		selected.each do |c|
#			$stderr.puts "entrou"
			c.start = sec if c.start < sec
			c.finish = sec + 1 if c.finish > sec + 1
			start = ( ( c.start.to_f - sec ) * 10000 ).to_i
			finish = ( ( c.finish.to_f - sec ) * 10000 ).to_i
#			$stderr.puts "start = #{start}; finish = #{finish}"
			start.upto(finish) do |x|
				range[x] = 1
			end
		end
		rangesum = range.sum
#		$stderr.puts rangesum
		rangesum = 10000 if rangesum > 10000
		rangesum = rangesum.to_f / 100.0
		print " %3.2f"%rangesum
	end
	puts ""
end
