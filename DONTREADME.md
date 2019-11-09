# Key Format:
`ABCDE-ABCDE-ABCDE-ABCDE-ABCDE-ABCDE-12345`

#### ABCDE, #n
1. `A` - n'th character of `foobar`
2. `BC` - n'th and n'th+1 characters of `foobar`, AES encrypted with clientKey+hash
3. `D` - n, AES encrypted with clientKey + appSigHash
4. `E` - Key part checksum

# 2
let appname be F00Bar?!
let block be F00Bar?!F00Bar?!
let encrypted be AES(block)
let license be ABCD-ABCD-ABCD-ABCD-ABCD-ABCD-ABCD-ABCD
let A be nth character of appname
let BC be nth pair of bytes of encrypted
let D be sum(ABC)^n%256