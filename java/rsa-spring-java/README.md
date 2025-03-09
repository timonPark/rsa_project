# RSA Java 프로젝트
## ENV Spec
- jdk: corretto-18
- spring : 3.4.3
- 필수 라이브러리
```
# RSA 암호화 복호화 할 때 사용
implementation group: 'org.bouncycastle', name: 'bcprov-jdk18on', version: '1.80'
implementation group: 'org.bouncycastle', name: 'bcpkix-jdk18on', version: '1.80'

# JSON 직렬화된 String을 객체로 바꿀 때 사용
implementation group: 'com.google.code.gson', name: 'gson', version: '2.12.1'
```

## 복호화에 사용할 privateKey 세팅
[privateKey.pem 보기](https://locrian-gerbil-117.notion.site/RSA-1a7fafab7c928001ab23d096620b2adb)
- 해당 사이트에 보면 테스트용 publicKey.pem과 privateKey.pem을 볼 수 있습니다.
- private_key.pem을 복사한 후 typescript 하위에 .ssh라는 폴더를 만들고 private_key.pem이라는 파일을 생성합니다.
- 그리고 복사한 내용을 해당 파일에 붙여 넣습니다.

## API 설명

### publicKey, privateKey 생성

```

POST http://localhost:8080/rsa/generate
Content-Type: application/json
```

### privateKey로 암호화된 데이터 복호화
- .ssh 폴더 아래 있는 private_key의 hash값만 직렬화 하여 privateKey에 넣고 실행합니다
```
POST http://localhost:8080/rsa/decrypt
Content-Type: application/json

{
  "data" : "gGKSgH+O0HfdPDfLwa+ccfC7/U8xTFeNzw742b2OToQQLUOHeEL8QeY6V5KRk8kSIZzwgMpdGXqBBBVjE+Bdxe1QFEs94YDvQxw3+p+M+XFMzISfpgWNEPqnrBxrkczkW5bj0+izB3oOQQUcil8JG4MD0ZslaYACIOpRkwAInek0y1sDyI6OjZzxmxn1dT0Nr09PaSq1wmPr83prXemf+o8y9YFt3OApvkoXhIMOYPRB/o61dsNCcMiRrJMKtz2hyvUSOOlC+zAQVCtLIvfrDSy7AwEtrPLvwUpoQzUM84mmff5qlIMtoSkIkxsix3LZmKkhNCTh9NZRRSIDJeRscg==",
  "privateKey" : "MIIEowIBAAKCAQEAor4hdmA/SUwAQKExyIg3FvBG2Zc7AG0fSFcbqbtIWoBcmOA3m6IguFJsoZuwaezOWupMo+zqGb5GyDdXTfeldLTMgrMQ4EOuBI7RXJ0CkUHgHljpRiTC8/lAhieJ1F2zG9gc3XFZhBpyRk6J59FmiIp5b+IEMER7336H3Puhy5Y7VI0JzgmklBIFWUEByv6/1KpDebuczs+FXXQRzK+E5mL1KAzEW5/sxrqMdCNQM7DBigd25LghPVrWSLP+Bgq9F7RoyZdPOX8gSdWG5dAdwkmUr4GwJLtKROgSrJB84JXmyjAbxnYMF0yh5xlcuQI3HiG0QnjLMKg/P2iwPJL1BQIDAQABAoIBAAaHRAZgpAlHsDbngHy/4h3cc6sk4uwIdJnNTMHJToS643wKepCWt2NNgygUe8Y0Bq8k+QAWb+mqdWJwveLVzWBaJF4V2tZ0QJXUONfTQJgdIWV2QZ1buipZVFpNWb0YKSgzyiyj8GhZOz5Qxj1za/6MN4TidzKMfL+hbsfRnFLzWnJjfihBjYiFo3dv7sidx0dZG/jC14zuPXYAl1BQSu6pliBsaPPDOwTXfGC+KolMnbYgnN9jb6UFp0IziFcgQzAzCbZsl+/hhMsnewf2N8gv6taM027u68P2bIRhlEMY3w6tGD+8dxjATBs8pGP9IhSG6Kqa4x5HumpF7EBG8UECgYEA2iOkGU7be/9RRqcrlrJUh7EvuSE+goGXxXKhA7vKnBJdGZdqUR0SfRJcRc92hhfcIsWi80AZYo121PgYYAgLmaRmbbQwj4+KgBPAra920Hz5qhkulnFW5ef7ahZCOty4ILTb+vlwt/8IVeB/R+NFKQY9VvoS3tXb6Asboug7LXUCgYEAvv0csKSZx2Sh+bKhCH+7E1F/3WtlzLcuwMUilHQPiTW8v8/gWCg8WAkJJgAOuNPz4GxVa8k0BKfHi09cwdwNIUNftjG1MrcnfMxnA+rC4bqU2w8aVQadGAgwe4VzS5bj5LQJKAXqeBpF91rCPICtwqkUcvKOdVh55LDPrcjh51ECgYEAntNWdsrQyd6i6cNr3EQpgcbDzZJj9hevTqbgj1xa7/n66Vgyo658OK52mqmsTYPv1y15MI1aLkR74iJldP5Gm+50WanZtZ04pXo8Tvmzk6d7DJtbLVSGhcy7ZImiXUM5mS62AG0/4egBxK05XzkdsPoHDV31AWRKwg/cZmeaNsUCgYB3bWVEXBlBIv9aWcXyv5Sm1t7DAYZtxdp5doxSEmUooq69ruqVlctuLXBID98k2nz0yCl+NhnOE3Bm/6B8JPMc6rlt/5VT+k2570M3otRsPLUaak5H/tO7FFvk0aUwDlHBUIctPM3Kfntj2p/9H2YIhJwh6OUGIeZPB/VFsYj5kQKBgD/oYJXg9D/iOviDHdH5x61TjROiTTREuG9ap88zTynUFfyuoYdMZygVYzOaSzFVxLvOJPJ0bFVqy4b5aCQ+oYuah5VIn+zqn45CJQjr9pMXpbtX/iKJWImKDSOYHMuui0rYTWo85DMnNLY8LU0IXHOZos8cQrtc+czVaLrZ9XLI"
}


response body
{
  "status": "OK",
  "message": "decrypt successful",
  "data": {
    "name": "테스트",
    "birthday": "20250101",
    "gender": "M",
    "phone": "01011112222",
    "emrUserIdx": "6dca3i2d03b15bd56dc76220"
  }
}
```