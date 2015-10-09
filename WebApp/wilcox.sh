dirA=$1
dirB=$2
outbase=$3
if [ -z "$3" ] ; then
	outbase="wilcox"
fi

argdirA=$(ruby -e "puts ARGV[0].split('/').last" $dirA)
argdirB=$(ruby -e "puts ARGV[0].split('/').last" $dirB)
printf -v outdir "%s/%s_X_%s" $outbase $argdirA $argdirB
mkdir -p $outdir

listA=$(find $dirA -regextype posix-extended -regex '^.*cpu_[0-9]{4}-[0-9]{2}-[0-9]{2}_[0-9]{2}-[0-9]{2}-[0-9]{2}\.txt$' | sort)
listB=$(find $dirB -regextype posix-extended -regex '^.*cpu_[0-9]{7}\.txt$' | sort)

for fileA in $listA ; do
	for fileB in $listB ; do
		argA=$(ruby -e "puts ARGV[0].split('/').last.gsub('.txt', '')" $fileA)
		argB=$(ruby -e "puts ARGV[0].split('/').last.gsub('.txt', '')" $fileB)
		printf -v tname "%s_X_%s_true.txt" $argA $argB
		printf -v fname "%s_X_%s_false.txt" $argA $argB
#		Rscript scripts/wilcox.R $fileA $fileB TRUE > $outdir/$tname
		Rscript scripts/wilcox.R $fileA $fileB FALSE > $outdir/$fname
	done
done

#Rscript scripts/wilcoxins.R $dirA/instruction_sum.txt $dirB/nins_full.txt TRUE > $outdir/nins_true.txt
#Rscript scripts/wilcoxins.R $dirA/instruction_sum.txt $dirB/nins_full.txt FALSE > $outdir/nins_false.txt
