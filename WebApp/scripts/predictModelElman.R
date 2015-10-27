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

predictModelElman <- function(newData, userProfile, predictionSize){

# carregando a rede treinada e os dados para desnormalização
fileName = paste("modelElman_", userProfile, ".RData", sep="")
load(fileName)
fileName = paste("min_", userProfile, ".RData", sep="")
load(fileName)
fileName = paste("max_", userProfile, ".RData", sep="")
load(fileName)

#normalização
newData = (newData-min)/(max-min)

# predição 
outputPredict = c()
for (i in 1:predictionSize) {
		newData = tail(newData, modelElman$nInputs)
		lastPredict = predict(modelElman, newData)
		outputPredict = append(outputPredict, lastPredict)
		newData = append(newData, lastPredict)
}

#avaliação da rede
#require(forecast)
#error <- accuracy(outputPredict, output[(numberTraining+1):nrow(input),1], test=NULL)

#desnormalização dos dados
disnorm = outputPredict*(max - min) + min 

return(disnorm)
}

