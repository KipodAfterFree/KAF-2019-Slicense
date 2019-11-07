# Key Format:
`ABCDE-ABCDE-ABCDE-ABCDE-ABCDE-ABCDE-12345`

#### ABCDE, #n
1. `A` - n'th character of `foobar`
2. `BC` - n'th and n'th+1 characters of `foobar`, AES encrypted with clientKey
3. `D` - n'th, n'th+1, n'th+2 characters of appSigHash, AES encrypted with clientKey
4. `E` - Key checksum so far

#### 12345
1. `12` - Key checksum
2. `34` - Key checksum, AES encrypted with clientKey
3. `5` - `N`