if [%1]==[/?] goto blank

git add . && git commit -m %1 && git push origin master

goto done
blank:
echo add a commit message
done:
echo done.