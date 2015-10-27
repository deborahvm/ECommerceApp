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
