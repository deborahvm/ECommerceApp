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

