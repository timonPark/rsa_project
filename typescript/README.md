# Typescript RSA Project

## ENV Spec
- node.js: v22.14.0
- nest.js : 11.0.1

## 설치 및 실행
먼저 node 22 버전을 설치 합니다 
이후에 아래와 같이 설치를 진행합니다
```
# pnpm 설치
$ npm install -g pnpm
# 설치
$ pnpm install
# 실행
$ pnpm start:dev
```

## 복호화에 사용할 privateKey 세팅
[privateKey.pem 보기](https://locrian-gerbil-117.notion.site/RSA-1a7fafab7c928001ab23d096620b2adb)
- 해당 사이트에 보면 테스트용 publicKey.pem과 privateKey.pem을 볼 수 있습니다.
- private_key.pem을 복사한 후 typescript 하위에 .ssh라는 폴더를 만들고 private_key.pem이라는 파일을 생성합니다.
- 그리고 복사한 내용을 해당 파일에 붙여 넣습니다.

## API
- 해당 api는 http 폴더 아래 존재함, 해당 파일 실행하면 됩니다.
### generate
- public key와 private key 생성 API
  - public key는 암호화 할 때 사용
  - private key는 복호화 할 때 사용
### decryptMessage
- 암호화 된 메세지를 복호화 하는 API
  - decryptMessage에 암호화된 메세지를 넣고 api를 실행하면 복호화된 emrUser로 반환