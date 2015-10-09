args <- commandArgs(trailingOnly = TRUE)
fileA <- args[1]
fileB <- args[2]
paired <- as.logical(args[3])

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

a <- a[1:min]
b <- b[1:min]

print(wilcox.test(a, b, paired=paired))
