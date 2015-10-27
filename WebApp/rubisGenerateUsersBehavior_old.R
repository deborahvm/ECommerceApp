# Copyright 2015 Deborah Maria Vieira Magalhães
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#     http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

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
