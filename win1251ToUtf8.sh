for a in `find ./Develop/pammDevelop/ -name '*.java'`; do 
	mv $a $a.1251;
	iconv -f cp1251 -t utf-8 $a.1251 > $a;
	rm $a.1251 ;
done

