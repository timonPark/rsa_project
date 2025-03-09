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

## API
- 해당 api는 http 폴더 아래 존재함, 해당 파일 실행하면 됩니다.
### generate
- public key와 private key 생성 API
  - public key는 암호화 할 때 사용
  - private key는 복호화 할 때 사용
### decryptMessage
- 암호화 된 메세지를 복호화 하는 API
  - decryptMessage에 암호화된 메세지를 넣고 api를 실행하면 복호화된 emrUser로 반환