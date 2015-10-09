generateUsersBehaviourBrowsing <- function(){
	gev = rgev(1, xi = -1.324516e+00, mu = 4.783323e+08, beta = 1.839221e+07)
	gevcel = ceiling(gev)
	gevmin = pmin(492361442, gevcel)
	total_instructions = pmax(400742607, gevmin)
	
	# instruction arrivals in cpu percentage
	instruction_arrivals <- rgpd(1337, xi = 0.87049, mu = -0.02547, beta = 0.08006)
	for(i in 1:length(instruction_arrivals)) { 
		if(instruction_arrivals[i] < 0){
			instruction_arrivals[i] <- 0
		} 
	}
	sum = sum(instruction_arrivals)
	ratio = total_instructions / sum
	reminder = 0
	for(i in 1:length(instruction_arrivals)) {
		instruction_arrivals[i] = instruction_arrivals[i] * ratio + reminder
		reminder = instruction_arrivals[i] %% 1000000
		# instruction arrivals in million of instructions
		instruction_arrivals[i] = instruction_arrivals[i] %/% 1000000
	}
	
	return(instruction_arrivals)
}

generateUsersBehaviourBidding <- function(){
        rgl = rgl(1, med = 4.877112e+08, iqr = 1.735356e+07, chi = -9.483909e-01, xi = 9.777355e-01)
        rglcel = ceiling(rgl)
        rglmin = pmin(492361442, rglcel)
        total_instructions = pmax(400742607, rglmin)

        # instruction arrivals in cpu percentage
        instruction_arrivals <- rgpd(1337, xi = 0.02413, mu = -0.02484, beta = 0.07879)
        for(i in 1:length(instruction_arrivals)) {
                if(instruction_arrivals[i] < 0){
                        instruction_arrivals[i] <- 0
                }
        }
        sum = sum(instruction_arrivals)
        ratio = total_instructions / sum
        reminder = 0
        for(i in 1:length(instruction_arrivals)) {
                instruction_arrivals[i] = instruction_arrivals[i] * ratio + reminder
                reminder = instruction_arrivals[i] %% 1000000
                # instruction arrivals in million of instructions
                instruction_arrivals[i] = instruction_arrivals[i] %/% 1000000
        }

        return(instruction_arrivals)

}

generateUsersBehaviour <- function(userProfile){
    
    library('fExtremes')
    library('gldist')
    instruction_arrivals_rate <- NULL
    switch(userProfile, 
		Browsing={ instruction_arrivals_rate <- generateUsersBehaviourBrowsing() },
		Bidding={ instruction_arrivals_rate <- generateUsersBehaviourBidding() }
    ) 
    return(instruction_arrivals_rate)	
}
