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

generateUsersBehaviourBiddingTraining <- function(sample_number){
        require('fExtremes')
        require('gldist')
   data = c()
   for(i in 1:sample_number) {
        rgl = rgl(1, med = 4.877112e+08, iqr = 1.735356e+07, chi = -9.483909e-01, xi = 9.777355e-01)
        rglcel = ceiling(rgl)
        rglmin = pmin(492361442, rglcel)
        total_instructions = pmax(400742607, rglmin)

        # instruction arrivals in cpu percentage
        instruction_arrivals <- rgpd(1337, xi = 0.02413, mu = -0.02484, beta = 0.07879)
        for(j in 1:length(instruction_arrivals)) {
                if(instruction_arrivals[j] < 0){
                        instruction_arrivals[j] <- 0
                }
        }
        sum = sum(instruction_arrivals)
        ratio = total_instructions / sum
        reminder = 0
        for(j in 1:length(instruction_arrivals)) {
                instruction_arrivals[j] = instruction_arrivals[j] * ratio + reminder
                reminder = instruction_arrivals[j] %% 1000000
                # instruction arrivals in million of instructions
                instruction_arrivals[j] = instruction_arrivals[j] %/% 1000000
        }

	data = append(data, instruction_arrivals)

   }
   #data <- matrix(unlist(data), ncol = 1, byrow = TRUE)
   return(data)
}
