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

energyGenerateGraphic <- function(output, output_dvfs, from, to){
	pdf("energy_consumption.pdf", width = 15, height = 5)
	plot(output_dvfs, col="blue", xlim=c(0,1336), ylim=c(0,1500), type="l", xlab="time (s)", ylab="Watt*s", main="Energy Consumption")
	lines(output_dvfs[from:to], lty=1, col="blue")
	lines(output[from:to], lty=2, col="red")
	legend("topleft", c("with DVFS", "without DVFS"), col=c("blue", "red"), 
lty=c(1, 2))
	dev.off()
}
