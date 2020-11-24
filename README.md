## Generating SSL certificates and keys using keytool

# gen keys for client and server
`keytool -genkey -alias client-key -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore client-key.p12 -validity 3650`

# export cert
`keytool -export -keystore client-keystore.p12 -alias client-key -file client.cer`


# import cert - client should trust server & server should trust client
`keytool -import -storetype PKCS12 -keystore client-truststore.p12 -file server.cer`



