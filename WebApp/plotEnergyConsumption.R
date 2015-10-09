energyGenerateGraphic <- function(output, output_dvfs, from, to){
	pdf("energy_consumption.pdf", width = 15, height = 5)
	plot(output_dvfs, col="blue", xlim=c(0,1336), ylim=c(0,1500), type="l", xlab="time (s)", ylab="Watt*s", main="Energy Consumption")
	lines(output_dvfs[from:to], lty=1, col="blue")
	lines(output[from:to], lty=2, col="red")
	legend("topleft", c("with DVFS", "without DVFS"), col=c("blue", "red"), 
lty=c(1, 2))
	dev.off()
}
