# Hermeto
O objetivo desse projeto é utilizar um tablet Xoom, em conjunto com um kit ADK , para criar um Tenori-on (16x16) tamanho família, que utilizará também sons tipicamente brasileiros, para que qualquer participante possa interagir mixando-os na sonzeira rolando.

[Descrição detalhada] (http://garoa.net.br/wiki/Hermeto)

## License
SCWL 1.0

### Social CachaçaWare License

* Mantenha os créditos
* Informe a utilização do código
* Curtiu? Quando encontrar um dos desenvolvedores pague uma bebida alcoolica pra ele (Ou mais)
* Tire uma foto do momento
* Permita que o desenvolvedor divulgue a foto


## How-To Build

### Eclipse

* Instale o ambiente: http://developer.android.com/sdk/installing.html
* [Instale o Google API (Nível 10 ou superior)] (http://code.google.com/intl/pt-BR/android/add-ons/google-apis/installing.html)
* Instale o plugin para Maven(M2E): http://eclipse.org/m2e/
* Instale o plugin de compatibilidade com Maven p/ Android: https://svn.codespot.com/a/eclipselabs.org/m2eclipse-android-integration/updates/ (Update site, adicionar no eclipse)
* Importar novo projeto Maven
* Abrir subprojeto da aplicação e proceder normalmente (como qualquer aplicação Android).

### Maven

* Instale o ambiente: http://developer.android.com/sdk/installing.html
* [Instale o Google API (Nível 10 ou superior)] (http://code.google.com/intl/pt-BR/android/add-ons/google-apis/installing.html)
* Alguma API de nivel mais antigo é necessária (Não lembro qual :/ sorry).
* export ANDROID_HOME=your/android/sdk/home
* Rodar ./installDeps.sh
* Entrar na pasta da app desejada
* Inicie a AVD ou conecte seu dispositivo
* mvn clean package android:deploy



