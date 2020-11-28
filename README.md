## This project implementing 2-way SSL using java spring
 
**Generating SSL certificates and keys using keytool**

* *gen keys*   
```sh
keytool -genkey -alias key-alias -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650 -ext "SAN:c=DNS:localhost,IP:127.0.0.1"
```

* *export cert*  
```sh
keytool -export -keystore keystore.p12 -alias client-key -file certificate.cer
```

* *import cert*    
```sh
keytool -import -storetype PKCS12 -keystore keytore.p12 -file certificate.cer
```



