@REM scan dir to sources.txt
dir /s /b *.java 1>sources.txt
@REM read sources.txt
javac -encoding utf8 -cp res/lib/*;. -d . @sources.txt
@REM remove sources.txt
rm sources.txt
@REM run code
java -cp res/lib/*;. com.cmd.DictionaryCommandLine
@REM pause cmd
pause