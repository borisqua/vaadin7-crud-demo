@echo off
if [%1]==[] goto blank

echo git add . && git commit -m %1 && git push origin master
git add . && git commit -m %1 && git push origin master

goto done

:blank
echo.
echo  ... You've forgoten a commit message ...
:done
echo.
echo done.
