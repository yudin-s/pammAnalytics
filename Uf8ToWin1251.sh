for a in `find ./Develop/pammDevelop/ -name '*.java'`; do 
	mv $a $a.1251;
	iconv -f utf-8 -t cp1251 $a.1251 > $a;
	rm $a.1251 ;
done

