@REM scan dir to sources.txt
dir /s /b *.java 1>sources.txt
@REM read sources.txt
javac -Xlint:unchecked -encoding utf8 -cp res/lib/*;. -d . @sources.txt
@REM remove sources.txt
rm sources.txt
@REM run code
java -Dfile.encoding=utf8 -Xmx70M -cp res/lib/*;. com.application.DictionaryApplication
@REM pause cmd
pause