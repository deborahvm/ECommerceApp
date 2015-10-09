generateUsersBehaviour <- function(users_number, users_arrival_rate_in_s, userProfile){
    
    #library('fExtremes')
    users_arrivals = rpois(users_number, users_arrival_rate_in_s)
    think_time = pmin(8,ceiling(rexp(users_number, 1/7)))
    section_length = rgev(users_number, xi = -1.324516e+00, mu = 
4.783323e+08, beta = 1.839221e+07)
    #to transform for millions of instructions
    section_length = trunc(section_length/1000000)
    #section_length = pmin(1800, ceiling(rexp(users_number, 1/900)))
    

    behavior <- data.frame(users_arrivals,section_length,think_time)
    write.table(behavior, file="rubisUsersBehavior.txt" , quote=FALSE)
}
