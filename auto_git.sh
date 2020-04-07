# git commit & push
echo Done to copy paste job!
read -p 'Message for commit:' msg
git status
git add .
echo Done to stage changes!
git commit -m "$msg"
echo Done to commit code!
git push
echo Done to push code!
