echo "Start Run"
ps -ef | grep java | grep backoffice-be | awk '{print $2}' | xargs kill -9
git checkout dev-cloud
git fetch
git pull
#. .env
./mvnw package -Dmaven.test.skip=true -Pprod
#nohup java -jar target/backoffice-be-0.0.1-SNAPSHOT.jar &
echo "End Run"
