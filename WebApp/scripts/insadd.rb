class Array
	def sum
		self.inject{|sum,x| sum + x }
	end
end

class Cloudlet
	# sid <- session id
	# nins <- number of instructions
	attr_accessor :sid, :nins
end

sessions = {}

while l = gets 
	c = Cloudlet.new
	c.sid = l.split("|")[0].to_i
	c.nins = l.split("|")[6].to_f 
	sessions[c.sid] = [] if sessions[c.sid] == nil
	sessions[c.sid] << c
end

puts "# sid | nins"

sessions.each do |sid, cs|
	nins = cs.collect{|c| c.nins}.sum
	puts "#{"%5d"%sid} #{"%6d"%nins}"
end
