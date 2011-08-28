cd /tmp
rm -rf maven-android-sdk-deployer
git clone https://github.com/gutomaia/maven-android-sdk-deployer.git
cd maven-android-sdk-deployer
mvn install -P 2.3.3
