mvn package -f "/home/julian/github/jogos/cartas/outubro24/pom.xml";


aws lambda update-function-code --function-name jogo-cartas-coracao-java8 --zip-file fileb://target/outubro24-1.0-SNAPSHOT.jar;

curl -iX GET https://45of55mka4.execute-api.us-west-2.amazonaws.com/default/jogo-cartas-coracao-java8;
