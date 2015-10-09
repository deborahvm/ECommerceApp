require( Hmisc )

args <- commandArgs(trailingOnly = TRUE)
fileA <- args[1]
fileB <- args[2]
out <- args[3]
main <- args[4]

tmp <- read.table(fileA, sep=";")
a <- c()
for (i in 1:nrow(tmp)) {
	if(tmp[i,4] == -1) {
		a <- append(a, 100 - tmp[i,10])
	}
}
b <- (read.table(fileB)$V2)

sizeA <- length(a)
sizeB <- length(b)
min <- min(sizeA, sizeB)

obs <- a[1:min]
sim <- b[1:min]

pdf(out, width = 5, height = 5)
Ecdf(sim, lty=1, col="red", xlab="x: CPU utilization", main=main, ylab="F(x)", subtitles=FALSE)
Ecdf(obs, lty=2, add=TRUE, col="blue", subtitles=FALSE)
legend("bottomright", c("observed", "simulated"), col=c("blue", "red"), lty=c(2, 1))
dev.off()
