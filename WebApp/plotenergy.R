energyGenerateDatatoPlot <- function(file){
	data_dvfs = read.table(file)
	data_dvfs <- sapply(data_dvfs, function(x) as.numeric(as.character(x)))
	output = matrix()
	for(i in 1:nrow(data_dvfs)) {
		for (j in trunc(data_dvfs[i,2]):trunc(data_dvfs[i,3])) {
			output[j] = data_dvfs[i,4]
		}
	}
	return(output)
}
