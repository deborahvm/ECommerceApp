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

generateModelElman <- function(data, userProfile, inputLayerSize){
#normalização dos dados[0,1]
min = min(data)
max = max(data)
data = (data-min)/(max-min)

outputLayerSize = 1
input = c()
output = c()
for (i in 1:(length(data)-inputLayerSize)) {
	lastIndex = i + inputLayerSize - 1
	input = append(input, list(c(data[i:lastIndex])))
	output = append(output, list(data[i+inputLayerSize]))
}
input <- matrix(unlist(input), ncol = inputLayerSize, byrow = TRUE)
output <- matrix(unlist(output), ncol = outputLayerSize, byrow = TRUE)

#numberTraining <- trunc(nrow(input)*80/100)
#treinamento da rede: 100% dos dados utilizados nessa etapa
require(RSNNS)
hiddenLayerSize = trunc((inputLayerSize + outputLayerSize) / 2)
modelElman <- elman(input[1:nrow(input),], output[1:nrow(input),outputLayerSize], size=hiddenLayerSize,learnFunc="JE_BP", learnFuncParams=c(0.4), maxit=1000,linOut=FALSE)
fileName = paste("modelElman_", userProfile, ".RData", sep="")
save(modelElman,list = c("modelElman"), file = fileName)
fileName = paste("min_", userProfile, ".RData", sep="")
save(min,list = c("min"), file = fileName)
fileName = paste("max_", userProfile, ".RData", sep="")
save(max,list = c("max"), file = fileName)
return()
}

