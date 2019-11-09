# Key Format:
`ABCDE-ABCDE-ABCDE-ABCDE-ABCDE-ABCDE-12345`

#### ABCDE, #n
1. `A` - n'th character of `foobar`
2. `BC` - n'th and n'th+1 characters of `foobar`, AES encrypted with clientKey+hash
3. `D` - n, AES encrypted with clientKey + appSigHash
4. `E` - Key part checksum