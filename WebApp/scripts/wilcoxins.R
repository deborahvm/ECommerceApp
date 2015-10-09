args <- commandArgs(trailingOnly = TRUE)
fileA <- args[1]
fileB <- args[2]
paired <- as.logical(args[3])

a <- ((read.table(fileA)$V2) / 1000000)
b <- (read.table(fileB)$V2)

sizeA <- length(a)
sizeB <- length(b)
min <- min(sizeA, sizeB)

a <- a[1:min]
b <- b[1:min]

print(wilcox.test(a, b, paired=paired))
